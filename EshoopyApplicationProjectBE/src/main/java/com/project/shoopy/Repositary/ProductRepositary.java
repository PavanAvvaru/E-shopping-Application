package com.project.shoopy.Repositary;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PathVariable;

import com.project.shoopy.entity.Product;

@Repository
public interface ProductRepositary extends JpaRepository<Product, Long>{
	List<Product> findByPriceBetween(double minPrice,double maxPrice);
	List<Product> findByPrice(double price);
	List<Product> findByCategory(String category);
	List<Product> findBySellerId(Long sellerId);
	List<Product> findProductsBySellerId(@Param("sellerId") Long sellerId);
}
