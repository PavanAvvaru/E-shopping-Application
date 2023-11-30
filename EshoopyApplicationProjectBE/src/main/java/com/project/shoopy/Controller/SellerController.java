package com.project.shoopy.Controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.project.shoopy.Repositary.SellerRepositary;
import com.project.shoopy.Service.OtpService;
import com.project.shoopy.Service.SellerService;
import com.project.shoopy.entity.OtpVerificationRequest;
import com.project.shoopy.entity.Product;
import com.project.shoopy.entity.Seller;

@RestController
@RequestMapping("/seller")
public class SellerController {
	@Autowired
	private OtpService otpService;
    private Map<String, Boolean> otpVerificationMap = new HashMap<>();
    @Autowired
	private com.project.shoopy.Service.EmailService emailService;
	@Autowired
	private SellerService sellerser;
	@Autowired
	private SellerRepositary sellerRepositary;

    @PostMapping("/newaccount/sendotp")
	public ResponseEntity<String> addNewAccount(@RequestBody Seller seller)
	{
    	String userEmail = seller.getEmail();
        Seller c=sellerser.findByEmailAddress(userEmail);
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
	@PostMapping("/newaccount/verifyotp")
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
	@PostMapping("/saveseller")
    public ResponseEntity<String> register(@RequestBody Seller customer) {
       return new ResponseEntity<String>(sellerser.saveSeller(customer),HttpStatus.ACCEPTED);
    }
   @PostMapping("/login")
   public ResponseEntity<String> loginAccount(@RequestBody Map<String, String> request)
   {
	   String email=request.get("email");
	   String password=request.get("password");
	return new ResponseEntity<String>(sellerser.Login(email, password),HttpStatus.ACCEPTED);
   }
   @PostMapping("/applicant/forgotpassword")
   public ResponseEntity<String> resetpassword(@RequestBody Map<String, String> request)
   {
	   String userEmail = request.get("email");
       Seller applicant =sellerser.findByEmailAddress(userEmail);
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
	   Seller c1=sellerser.findByEmailAddress(email);
	   if(c1==null)
	   {
		   return ResponseEntity.badRequest().body("entered email id not present");
	   }
	   if(!(newpassword.equals(conformnewpassword)))
	   {
		   return ResponseEntity.badRequest().body("paswords mismatches");
	   }
	   c1.setPassword(conformnewpassword);
	   sellerRepositary.save(c1);
	return ResponseEntity.ok("password reset successfull.");
	   
   }
   @DeleteMapping("sellerdel/{sellerId}")
   public ResponseEntity<String> deleteSeller(@PathVariable Long sellerId) {
       String result = sellerser.deleteSeller(sellerId);
       return ResponseEntity.ok(result);
   }
   @PutMapping("upseller/{sellerId}")
   public ResponseEntity<String> updateSeller(@PathVariable Long sellerId, @RequestBody Seller updatedSeller) {
       String result = sellerser.updateSeller(sellerId, updatedSeller);
       return ResponseEntity.ok(result);
   }
   @PostMapping("/{sellerId}/products")
   public ResponseEntity<String> addProductForSeller(@PathVariable Long sellerId, @RequestBody Product product) {
       String result = sellerser.addProductForSeller(sellerId, product);
       return ResponseEntity.status(HttpStatus.CREATED).body(result);
   }
   @PutMapping("/{sellerId}/products/{productId}")
   public ResponseEntity<String> updateProductForSeller(
           @PathVariable Long sellerId,
           @PathVariable Long productId,
           @RequestBody Product updatedProduct) {
       String result = sellerser.updateProductForSeller(sellerId, productId, updatedProduct);
       return ResponseEntity.ok(result);
   }
   @DeleteMapping("/{sellerId}/products/{productId}")
   public ResponseEntity<String> deleteProductForSeller(@PathVariable Long sellerId, @PathVariable Long productId) {
       String result = sellerser.deleteProductForSeller(sellerId, productId);
       return ResponseEntity.ok(result);
   }
}
