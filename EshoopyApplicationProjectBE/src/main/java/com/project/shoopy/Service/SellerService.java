package com.project.shoopy.Service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.project.shoopy.entity.Product;
import com.project.shoopy.entity.Seller;

@Service
public interface SellerService {
	 Seller findByEmailAddress(String email);
	 String saveSeller(Seller seller);
	 String Login(String email,String pass);
	 String deleteSeller(Long sellerId);
	 String updateSeller(Long sellerId, Seller updatedSeller);
	 String addProductForSeller(Long sellerId, Product product);
	    String updateProductForSeller(Long sellerId, Long productId, Product updatedProduct);
	    String deleteProductForSeller(Long sellerId, Long productId);
		List<Product> getAllProductsForSeller(Long sellerId);
}
