package com.project.shoopy.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.project.shoopy.Dto.CustomerRequest;
import com.project.shoopy.Repositary.CustomerRepositary;
import com.project.shoopy.entity.Customer;

@Service
public class CustomerServiceImplementation implements CustomerService{
	@Autowired
	private CustomerRepositary custrepo;
	@Override
	public Customer findByEmailAddress(String userEmail) {
		return custrepo.findByEmail(userEmail);
	}
	@Override
	public String savecustomer(CustomerRequest customer) {
		Customer c=new Customer();
		c.setName(customer.getName());
		c.setEmail(customer.getEmail());
		c.setMobilenumber(customer.getMobilenumber());
		c.setPassword(customer.getPassword());
		custrepo.save(c);
		return "successfully created account";
	}
	@Override
	public String Login(String email, String pass) {
		if(custrepo.existsByEmail(email))
		{
			Customer c=custrepo.findByEmail(email);
			String p=c.getPassword();
			if(p.equalsIgnoreCase(pass))
			{
				return "successfully login";
			}
			return "invalid username or password";
		}
		return "invalid email ";
	}
}
