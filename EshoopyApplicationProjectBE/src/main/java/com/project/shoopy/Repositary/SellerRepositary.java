package com.project.shoopy.Repositary;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.shoopy.entity.Seller;

@Repository
public interface SellerRepositary extends JpaRepository<Seller, Long>{
	Seller findByEmail(String email);
	boolean existsByEmail(String email);
}
