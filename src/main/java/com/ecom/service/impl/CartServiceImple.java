package com.ecom.service.impl;

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
		
		return null;
	}

	@Override
	public Integer getCountCart(Integer userId) {
		
		Integer countByUserId = cartRepository.countByUserId(userId);
		
		return countByUserId;
	}

}
