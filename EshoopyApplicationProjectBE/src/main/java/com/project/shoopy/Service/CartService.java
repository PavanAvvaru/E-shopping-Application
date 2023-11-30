package com.project.shoopy.Service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.project.shoopy.entity.CartItem;

@Service
public interface CartService {
	public String addProductToCart(Long productId, int quantity);
	List<CartItem> getAllproductsincart();
	 public String removeProductFromCartItem(Long cartItemId);
}
