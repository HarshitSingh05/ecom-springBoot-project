package com.ecom.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.ecom.model.Cart;
import com.ecom.model.Product;
import com.ecom.model.UserDetls;
import com.ecom.repository.CartRepository;
import com.ecom.repository.ProductRepository;
import com.ecom.repository.UserRepository;
import com.ecom.service.CartService;

@Service
public class CartServiceImple implements CartService {

	@Autowired
	private CartRepository cartRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private ProductRepository productRepository;
	
	
	@Override
	public Cart saveCart(Integer productId, Integer userId) {
		
	 Product product = productRepository.findById(productId).get();
	 UserDetls userDetls = userRepository.findById(userId).get();
	 
	 Cart cartStatus = cartRepository.findByProductIdAndUserId(productId, userId);
	 
	 Cart cart = null;
	 
	 if(ObjectUtils.isEmpty(cartStatus)) {
		 cart = new Cart();
		 cart.setProduct(product);
		 cart.setUser(userDetls);
		 cart.setQuantity(1);
		 cart.setTotalPrice(1 * product.getDiscountPrice());
	 }else {
		 cart = cartStatus;
		 cart.setQuantity(cart.getQuantity()+1);
		 cart.setTotalPrice(cart.getQuantity() * cart.getProduct().getDiscountPrice());
	 }
	 
	  Cart saveCart = cartRepository.save(cart);
		
		return saveCart;
	}

	@Override
	public List<Cart> getCartsByUser(Integer userId) {
		List<Cart> carts = cartRepository.findByUserId(userId);
		
		Double totalOrderPrice = 0.0;
		List<Cart> updateCarts = new ArrayList<>();
		for(Cart c: carts) {
			Double totalPrice = (c.getProduct().getDiscountPrice() * c.getQuantity());
			c.setTotalPrice(totalPrice);
			totalOrderPrice = totalOrderPrice + totalPrice;
			totalOrderPrice = Math.round(totalOrderPrice * 100.0)/100.0;
			c.setTotalOrderPrice(totalOrderPrice);
			updateCarts.add(c);
		}
		
		return updateCarts;
	}

	@Override
	public Integer getCountCart(Integer userId) {
		
		Integer countByUserId = cartRepository.countByUserId(userId);
		
		return countByUserId;
	}

	@Override
	public void updateQuantity(String sy, Integer cid) {
		
		Cart cart = cartRepository.findById(cid).get();
		int updateQuantity;
		if(sy.equalsIgnoreCase("de")) {
			updateQuantity = cart.getQuantity() - 1;
			
			if(updateQuantity <= 0) {
				cartRepository.deleteById(cid);
				return;
			}
		}else {
			updateQuantity = cart.getQuantity()+1;
			
		}
		cart.setQuantity(updateQuantity);
		cartRepository.save(cart);
	}

}
