package com.ecom.Controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.ecom.model.Category;
import com.ecom.model.UserDetls;
import com.ecom.service.CategoryService;
import com.ecom.service.UserService;

@Controller
//@RequestMapping("/User")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private CategoryService categoryService;
	
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
		}
		List<Category> allActiveCategory = categoryService.getAllActiveCategory();
		m.addAttribute("categorys", allActiveCategory);
	}
}
