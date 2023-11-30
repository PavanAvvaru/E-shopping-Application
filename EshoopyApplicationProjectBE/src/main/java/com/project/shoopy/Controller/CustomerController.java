package com.project.shoopy.Controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.project.shoopy.Dto.CustomerRequest;
import com.project.shoopy.Repositary.CustomerRepositary;
import com.project.shoopy.Service.CustomerService;
import com.project.shoopy.Service.OtpService;
import com.project.shoopy.entity.Customer;
import com.project.shoopy.entity.OtpVerificationRequest;

import jakarta.validation.Valid;

@RestController
public class CustomerController {
	@Autowired
	private OtpService otpService;
	private Map<String, Boolean> otpVerificationMap = new HashMap<>();
	@Autowired
	private com.project.shoopy.Service.EmailService emailService;
	@Autowired
	private CustomerService custser;
	@Autowired
	private CustomerRepositary customerRepositary;

	@PostMapping("/newaccount/sendotp")
	public ResponseEntity<String> addNewAccount(@RequestBody @Valid CustomerRequest customer)
	{
		String userEmail = customer.getEmail();
	        Customer c=custser.findByEmailAddress(userEmail);
	        if (c== null) {     
	            String otp = otpService.generateOtp(userEmail);
	         	            emailService.sendOtpEmail(userEmail, otp);
	 	            otpVerificationMap.put(userEmail, true);
	 	            return ResponseEntity.ok("OTP sent to your email.");
	        }
	        else {
	        	 return ResponseEntity.badRequest().body("Email is already  registered.");
	        }
	}
	@PostMapping("/customer/verifyotp")
	public ResponseEntity<String> verifyOtpSended(@RequestBody OtpVerificationRequest request)
	{
		String otp=request.getOtp();
        String email=request.getEmail();
        System.out.println(otp+email);

        if (otpService.validateOtp(email, otp)) {
            return ResponseEntity.ok("OTP verified successfully");
        } else {
            return ResponseEntity.badRequest().body("Incorrect OTP.");
        }
	}
	@PostMapping("/saveApplicant")
    public ResponseEntity<String> register(@RequestBody CustomerRequest customer) {
       return new ResponseEntity<String>(custser.savecustomer(customer),HttpStatus.ACCEPTED);
    }
   @PostMapping("/login")
   public ResponseEntity<String> loginAccount(@RequestBody Map<String, String> request)
   {
	   String email=request.get("email");
	   String password=request.get("password");
	return new ResponseEntity<String>(custser.Login(email, password),HttpStatus.ACCEPTED);
   }
   @PostMapping("/applicant/forgotpassword")
   public ResponseEntity<String> resetpassword(@RequestBody Map<String, String> request)
   {
	   String userEmail = request.get("email");
       Customer applicant =custser.findByEmailAddress(userEmail);
        if (applicant != null) {     
            String otp = otpService.generateOtp(userEmail);
         	            emailService.sendOtpEmail(userEmail, otp);
 	            otpVerificationMap.put(userEmail, true);
 	            return ResponseEntity.ok("OTP sent to your email.");
        }
        else {
        	 return ResponseEntity.badRequest().body("Email is not exist");
        }
   }
   @PostMapping("/resetpassword/{email}")
   public ResponseEntity<String> passwordresetting(@RequestBody Map<String, String> request,@PathVariable String email)
   {
	   String newpassword=request.get("newpassword");
	   String conformnewpassword=request.get("conformnewpassword");
	   Customer c1=custser.findByEmailAddress(email);
	   if(c1==null)
	   {
		   return ResponseEntity.badRequest().body("entered email id not present");
	   }
	   if(!(newpassword.equals(conformnewpassword)))
	   {
		   return ResponseEntity.badRequest().body("paswords mismatches");
	   }
	   c1.setPassword(conformnewpassword);
	   customerRepositary.save(c1);
	return ResponseEntity.ok("password reset successfull.");  
   }
}
