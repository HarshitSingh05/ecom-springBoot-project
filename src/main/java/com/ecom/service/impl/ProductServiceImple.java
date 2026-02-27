package com.ecom.service.impl;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import com.ecom.model.Product;
import com.ecom.repository.ProductRepository;
import com.ecom.service.ImageService;
import com.ecom.service.ProductService;


@Service
public class ProductServiceImple implements ProductService {

	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private ImageService imageService;

	@Override
	public Product saveProduct(Product product) {
		return productRepository.save(product);
	}

	@Override
	public List<Product> getAllProduct() {
		
		return productRepository.findAll();
	}

	@Override
	public boolean deleteProduct(Integer id) {
	 Product product = productRepository.findById(id).orElse(null);
	 
	 if(!ObjectUtils.isEmpty(product)) {
		 
		 productRepository.delete(product);
		 return true;
	 }
	return false;
	}

	@Override
	public Product getProductById(Integer id) {
		Product product = productRepository.findById(id).orElse(null);
		
		return product;
	}

	@Override
	public Product updateProduct(Product product, MultipartFile image) {
		
		Product dbproduct = getProductById(product.getId());
	    if (ObjectUtils.isEmpty(dbproduct)) {
	        return null;
	    }
		
		String imageUrl = dbproduct.getImage();
		 if (image != null && !image.isEmpty()) {
		        imageUrl = imageService.uploadImage(image, "ecom/products");
		    }
		
		dbproduct.setTitle(product.getTitle());
		dbproduct.setDescription(product.getDescription());
		dbproduct.setCategory(product.getCategory());
		dbproduct.setPrice(product.getPrice());
		dbproduct.setStock(product.getStock());
		dbproduct.setImage(imageUrl);
		dbproduct.setIsActive(product.getIsActive());
		dbproduct.setDiscount(product.getDiscount());
		
		
		Double discount = product.getPrice()*(product.getDiscount()/100.0);
		Double discountPrice = product.getPrice()-discount;
		dbproduct.setDiscountPrice(discountPrice);
		
		Product updateProduct = productRepository.save(dbproduct);
		
		if(!ObjectUtils.isEmpty(updateProduct)) {
			return updateProduct;
		}
		return null;
	}

	@Override
	public List<Product> getAllActiveProduct(String category) {
		List<Product> products = null;
		if(ObjectUtils.isEmpty(category)) {
			products = productRepository.findByIsActiveTrue();
		}else {
			products = productRepository.findByCategory(category);
		}
	 
		return products;
	}

	
}
