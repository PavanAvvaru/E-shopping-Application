package com.project.shoopy.Repositary;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.shoopy.entity.Cart;

public interface CartRepositary extends JpaRepository<Cart, Long>{
	
}
