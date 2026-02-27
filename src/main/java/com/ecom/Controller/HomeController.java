package com.ecom.Controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.Principal;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.ecom.model.Category;
import com.ecom.model.Product;
import com.ecom.model.UserDetls;
import com.ecom.service.CartService;
import com.ecom.service.CategoryService;
import com.ecom.service.ImageService;
import com.ecom.service.ProductService;
import com.ecom.service.UserService;
import com.ecom.util.CommonUtil;

import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class HomeController {
	
	@Autowired
	private CategoryService categoryService;
	
	@Autowired
	private ProductService productService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private CommonUtil commonUtil;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@Autowired
	private CartService cartService;
	
	@Autowired
	private ImageService imageService;
	
	@ModelAttribute
	public void getUserDetails(Principal p,Model m) {
		if(p != null) {
			String email = p.getName();
			UserDetls userDetls = userService.getUserByEmail(email);
			m.addAttribute("user", userDetls);
			Integer countCart = cartService.getCountCart(userDetls.getId());
			m.addAttribute("countCart", countCart);
		}
		List<Category> allActiveCategory = categoryService.getAllActiveCategory();
		m.addAttribute("categorys", allActiveCategory);
	}
	
	
	@GetMapping({"/","/home"})
	public String home() {
		
		return "home";
	}
	
	@GetMapping("/signin")
	public String login() {
		 return "login";
	}
	
	@GetMapping("/register")
	public String register() {
		return "register";
	}
	
	@GetMapping("/products")
	public String products(Model m, @RequestParam(value = "category" , defaultValue = "") String category){
		List<Category> categories = categoryService.getAllActiveCategory();
		List<Product> products = productService.getAllActiveProduct(category);
		m.addAttribute("categories", categories);
		m.addAttribute("products", products);
		m.addAttribute("paramValue", category);
		return "product";
	}

	@GetMapping("/product/{id}")
	public String product(@PathVariable int id, Model m) {
		Product productById = productService.getProductById(id);
		m.addAttribute("IdProduct", productById);
		return "view_product";
	}
	
	@PostMapping("/saveUser")
	public String saveUser(@ModelAttribute UserDetls user, @RequestParam("img") MultipartFile file, HttpSession session) throws IOException {
		
		String imageUrl = "default.jpg";
	    if (file != null && !file.isEmpty()) {
	        imageUrl = imageService.uploadImage(file, "ecom/users");
	    }
		user.setProfileImage(imageUrl);
		
		UserDetls saveUser = userService.saveUser(user);
		
		if(!ObjectUtils.isEmpty(saveUser)) {
			session.setAttribute("succMsg", "User Registerd Successfully");
		}else {
			session.setAttribute("errorMsg", "Something went Wrong. TryAgain!!");	
			
		}
		
		return "redirect:/register";
	}
	
	
	// Forget Password Code 
	@GetMapping("/forget-password")
	public String showForgetPassword() {
		
		return "forget_password";
	}
	
	@PostMapping("/forget-password")
	public String processForgetPassword(@RequestParam String email, HttpSession session, HttpServletRequest request) throws UnsupportedEncodingException, MessagingException {
		
		UserDetls userByEmail = userService.getUserByEmail(email);
		if(ObjectUtils.isEmpty(userByEmail)) {
			session.setAttribute("errorMsg", "Invalid Email");
		}else {
			
			String resetToken = UUID.randomUUID().toString();
			userService.updateUserResetToken(email,resetToken);
			
			// Generate URL 
			
		    String url = CommonUtil.generateUrl(request)+"/reset-password?token="+resetToken;
			
			Boolean sendMail = commonUtil.sendMail(url,email);
			
			if(sendMail) {
				session.setAttribute("succMsg", "Password reset link send. Please check your email....");
			}else {
				session.setAttribute("errorMsg", "Something wrong on server. Email not send");
			}
		}
	
		return "redirect:/forget-password";
	}
	
	
	@GetMapping("/reset-password")
	public String showResetPassword(@RequestParam String token,Model m) {
		
		UserDetls userByToken = userService.getUserByToken(token);
		if(userByToken==null) {
			m.addAttribute("msg", "Your link is Invalid or Expired!!");
			return "message";
		}
		m.addAttribute("token", token);
		return "reset_password";
	}
	
	@PostMapping("/reset-password")
	public String resetPassword(@RequestParam String token,@RequestParam String password,Model m) {
		
		UserDetls userByToken = userService.getUserByToken(token);
		if(userByToken==null) {
			m.addAttribute("errorMsg", "Your link is Invalid or Expired!!");
			return "message";
		}else {
			userByToken.setPassword(passwordEncoder.encode(password));
			userByToken.setResetToken(null);
			userService.updateUser(userByToken);
			m.addAttribute("msg", "Password changed successfully!");
			return "message";
		}
	}
	
}
