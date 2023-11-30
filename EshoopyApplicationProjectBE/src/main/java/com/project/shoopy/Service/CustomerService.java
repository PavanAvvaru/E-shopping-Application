package com.project.shoopy.Service;

import org.springframework.stereotype.Service;

import com.project.shoopy.Dto.CustomerRequest;
import com.project.shoopy.entity.Customer;


@Service
public interface CustomerService {
	 Customer findByEmailAddress(String userEmail);
	 String savecustomer(CustomerRequest customer);
	 String Login(String email,String pass);
}
