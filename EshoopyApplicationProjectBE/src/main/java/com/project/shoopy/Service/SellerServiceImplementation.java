package com.project.shoopy.Service;


import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.shoopy.Repositary.ProductRepositary;
import com.project.shoopy.Repositary.SellerRepositary;
import com.project.shoopy.entity.Product;
import com.project.shoopy.entity.Seller;

@Service
public class SellerServiceImplementation implements SellerService{
	@Autowired
private SellerRepositary sellerRepositary;
	@Autowired
    private ProductRepositary productRepositary;
	@Override
	public Seller findByEmailAddress(String email) {
		// TODO Auto-generated method stub
		return sellerRepositary.findByEmail(email);
	}

@Override
public String saveSeller(Seller seller) {
	// TODO Auto-generated method stub
	if(sellerRepositary.existsByEmail(seller.getEmail()))
	{
		return "entered email already registred";
	}
	else
	{
	Seller ss=new Seller();
	ss.setEmail(seller.getEmail());
	ss.setMobileNumber(seller.getMobileNumber());
	ss.setName(seller.getName());
	ss.setPassword(seller.getPassword());
	sellerRepositary.save(ss);
    return "Seller saved successfully";
	}
}

@Override
public String Login(String email,String pass) {
	// TODO Auto-generated method stub
	Seller seller = sellerRepositary.findByEmail(email);
    if (seller != null && seller.getPassword().equals(pass)) {
        return "Login successful";
    } else {
        return "Invalid email or password";
    }
}
@Override
public String deleteSeller(Long sellerId) {
    java.util.Optional<Seller> optionalSeller = sellerRepositary.findById(sellerId);
    if (optionalSeller.isPresent()) {
        sellerRepositary.deleteById(sellerId);
        return "Seller deleted successfully";
    } else {
        return "Seller not found";
    }
}
@Override
public String updateSeller(Long sellerId, Seller updatedSeller) {
   Optional<Seller> optionalSeller = sellerRepositary.findById(sellerId);
    if (optionalSeller.isPresent()) {
        Seller existingSeller = optionalSeller.get();
        // Update the fields of existingSeller with the values from updatedSeller
        existingSeller.setName(updatedSeller.getName());
        existingSeller.setEmail(updatedSeller.getEmail());
        existingSeller.setMobileNumber(updatedSeller.getMobileNumber());
        existingSeller.setPassword(updatedSeller.getPassword());
        sellerRepositary.save(existingSeller);
        return "Seller updated successfully";
    } else {
        return "Seller not found";
    }
}

@Override
public String addProductForSeller(Long sellerId, Product product) {
    Optional<Seller> optionalSeller = sellerRepositary.findById(sellerId);
    if (optionalSeller.isPresent()) {
        Seller seller = optionalSeller.get();
        product.setSeller(seller);
        seller.addProduct(product);
        // Save both the seller and the product
        sellerRepositary.save(seller);
        productRepositary.save(product);
        return "Product added by the seller successfully";
    } else {
        return "Seller not found";
    }
}

@Override
public String updateProductForSeller(Long sellerId, Long productId, Product updatedProduct) 
{
    Optional<Seller> optionalSeller = sellerRepositary.findById(sellerId);
    if(optionalSeller.isPresent()) 
    {
        Seller seller = optionalSeller.get();
        Iterator<Product> presentp=seller.getProducts().iterator();
        while (presentp.hasNext()) {
            Product p=presentp.next();
            if (p.getId()==productId) {
            	p.setName(updatedProduct.getName());
            	p.setPrice(updatedProduct.getPrice());
            	p.setCategory(updatedProduct.getCategory());
            	p.setDescription(updatedProduct.getDescription());
            	
            	// Save both the seller and the updated product
                sellerRepositary.save(seller);
                productRepositary.save(p);
                return "Product updated successfully";
            }
            else {
                return "Product not found for the seller";
            }
        } 
    }
	return "Seller not found";
}
@Override
public String deleteProductForSeller(Long sellerId, Long productId) {
    Optional<Seller> optionalSeller = sellerRepositary.findById(sellerId);
    if (optionalSeller.isPresent()) {
        Seller seller = optionalSeller.get();
        // Using an iterator to find and remove the product
        Iterator<Product> iterator = seller.getProducts().iterator();
        while (iterator.hasNext()) {
            Product product = iterator.next();
            if (product.getId()==productId) {
                // Remove the product from the seller's products list
                iterator.remove();
                break; // Assuming each product has a unique ID, exit the loop after removal
            }
        }
        // Delete the product from the product table
        productRepositary.deleteById(productId);
        // Save the updated seller
        sellerRepositary.save(seller);

        return "Product deleted successfully";
    } else {
        return "Seller not found";
    }
}
@Override
public List<Product> getAllProductsForSeller(Long sellerId) {
    Optional<Seller> optionalSeller = sellerRepositary.findById(sellerId);
    return optionalSeller.map(Seller::getProducts).orElseGet(Collections::emptyList);
}
}
