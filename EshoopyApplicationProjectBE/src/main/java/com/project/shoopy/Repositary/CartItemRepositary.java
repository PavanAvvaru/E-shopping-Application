package com.project.shoopy.Repositary;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.PathVariable;

import com.project.shoopy.entity.Cart;
import com.project.shoopy.entity.CartItem;

public interface CartItemRepositary extends JpaRepository<CartItem, Long>{
	Optional<CartItem> findByProductId(Long productId);
	boolean existsByProductId(Long productId);
}
