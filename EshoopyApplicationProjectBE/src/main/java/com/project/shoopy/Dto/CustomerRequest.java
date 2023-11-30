package com.project.shoopy.Dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class CustomerRequest {
	@NotNull(message = "name should be not null")
	private String name;
	@Email(message = "email must ends with @mail.com")
	private String email;
	@Pattern(regexp = "[6-9]{1}[0-9]{9}")
	private String mobilenumber;
	@Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$", message = "Password must meet the specified criteria")
    @Size(min = 8, message = "Password must be at least 8 characters long")
	private String password;
	public CustomerRequest() {
		super();
		// TODO Auto-generated constructor stub
	}
	public CustomerRequest(@NotNull(message = "name should be not null") String name,
			@Email(message = "email must ends with @mail.com") String email,
			@Pattern(regexp = "[6-9]{1}[0-9]{9}") String mobilenumber,
			@Pattern(regexp = "[A-Z]{1}[a-z]{1}[0-9]{1}") @Size(min = 8) String password) {
		super();
		this.name = name;
		this.email = email;
		this.mobilenumber = mobilenumber;
		this.password = password;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getMobilenumber() {
		return mobilenumber;
	}
	public void setMobilenumber(String mobilenumber) {
		this.mobilenumber = mobilenumber;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
}
