package com.ecom.Controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ecom.model.Cart;
import com.ecom.model.Category;
import com.ecom.model.OrderRequest;
import com.ecom.model.ProductOrder;
import com.ecom.model.UserDetls;
import com.ecom.service.CartService;
import com.ecom.service.CategoryService;
import com.ecom.service.OrderService;
import com.ecom.service.UserService;
import com.ecom.util.CommonUtil;
import com.ecom.util.OrderStatus;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private CategoryService categoryService;
	
	@Autowired
	private CartService cartService;
	
	@Autowired
	private OrderService orderService;
	
	@Autowired
	private CommonUtil commonUtil;
	
	@GetMapping("/")
	public String home() {
		return "home";
	}
	
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
	
	@GetMapping("/addCart")
	public String addToCart(@RequestParam Integer pid, @RequestParam Integer uid, HttpSession session) {
		
		Cart saveCart = cartService.saveCart(pid, uid);
		if(ObjectUtils.isEmpty(saveCart)) {
			session.setAttribute("errorMsg", "Product add to Cart Failed!!");
		}else {
			session.setAttribute("succMsg", "Product added to Cart Successfully");
		}
		return "redirect:/product/"+pid;
	}
	
	@GetMapping("/cart")
	public String loadCartPage(Principal p,Model m) {

		UserDetls user = getLoggedInUserDetails(p);
		List<Cart> carts = cartService.getCartsByUser(user.getId());
		m.addAttribute("carts", carts);
		if(carts.size() > 0) {
			Double totalOrderPrice = carts.get(carts.size()-1).getTotalOrderPrice();
			m.addAttribute("totalOrderPrice", totalOrderPrice);
		}
		return "/user/cart";
	}
	
	@GetMapping("/cartQuantityUpdate")
	public String updateCartQuantity(@RequestParam String sy, @RequestParam Integer cid) {
		
		 cartService.updateQuantity(sy,cid);
		return "redirect:/user/cart";
	}
	
	private UserDetls getLoggedInUserDetails(Principal p) {
		String email = p.getName();
		UserDetls userDtls = userService.getUserByEmail(email);
		return userDtls;
	}
	
	@GetMapping("/orders")
	public String orderPage(Principal p,Model m) {
		UserDetls user = getLoggedInUserDetails(p);
		List<Cart> carts = cartService.getCartsByUser(user.getId());
		m.addAttribute("carts", carts);
		if(carts.size() > 0) {
			Double orderPrice = carts.get(carts.size()-1).getTotalOrderPrice();
			m.addAttribute("orderPrice", orderPrice);
			Double totalOrderPrice = carts.get(carts.size()-1).getTotalOrderPrice() + 250 + 100;
			m.addAttribute("totalOrderPrice", totalOrderPrice);
		}
		
		return "/user/order";
	}
	
	@PostMapping("/save-order")
	public String saveOrder(@ModelAttribute OrderRequest request,Principal p) throws Exception {
		UserDetls user = getLoggedInUserDetails(p);
		orderService.saveOrder(user.getId(), request);
		
		return "redirect:/user/success";
	}
	
	@GetMapping("/success")
	public String loadSuccess() {
		return "/user/success";
	}
	
	@GetMapping("/user-orders")
	public String myOrder(Model m,Principal p) {
		UserDetls loginUser = getLoggedInUserDetails(p);
		List<ProductOrder> orders = orderService.getOrderByUser(loginUser.getId());
		m.addAttribute("orders", orders);
		return "/user/my_orders";
	}
	
	@GetMapping("/update-status")
	public String updateOrderStatus(@RequestParam Integer id, @RequestParam Integer st, HttpSession session) {
		
	   OrderStatus[] values = OrderStatus.values();
	   String status = null;
	   
	   for(OrderStatus orderSt: values) {
		   if(orderSt.getId().equals(st)) {
			   status = orderSt.getName();
		   }
	   }
	   ProductOrder updateOrder = orderService.updateOrderStatus(id, status);
	   
	   try {
		commonUtil.sendMailForProductOrder(updateOrder, status);
	} catch (Exception e) {
		e.printStackTrace();
	}
	   
	   
	   if(!ObjectUtils.isEmpty(updateOrder)) {
		   session.setAttribute("succMsg", "Status Updated");
	   }else {
		   session.setAttribute("errorMsg", "Something went wrong on server!!");
	   }
		return "redirect:/user/user-orders";
	}
}
