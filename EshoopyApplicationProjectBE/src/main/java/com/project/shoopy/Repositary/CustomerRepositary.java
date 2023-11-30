package com.project.shoopy.Repositary;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.project.shoopy.entity.Customer;

@Repository
public interface CustomerRepositary extends JpaRepository<Customer, Long>{
	Customer  findByEmail(String email);
	boolean existsByEmail(String email);
}
