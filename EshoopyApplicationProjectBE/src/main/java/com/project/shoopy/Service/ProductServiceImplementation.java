package com.project.shoopy.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.shoopy.Repositary.ProductRepositary;
import com.project.shoopy.entity.Product;

@Service
public class ProductServiceImplementation implements ProductService{
	@Autowired
	private ProductRepositary prorepo;
	
	@Override
	public Product addproduct(Product product) {
		// TODO Auto-generated method stub
		return prorepo.save(product);
	}

	@Override
	public List<Product> getAll() {
		// TODO Auto-generated method stub
		return prorepo.findAll();
	}

	@Override
	public List<Product> getProductbyCategory(String category) {
		// TODO Auto-generated method stub
		return prorepo.findByCategory(category);
	}

	@Override
	public List<Product> getproductpricerange(double minPrice, double maxPrice) {
		// TODO Auto-generated method stub
		return prorepo.findByPriceBetween(minPrice, maxPrice);
	}

	@Override
	public String getproductbyid(Long id) {
		// TODO Auto-generated method stub
		Optional<Product> p=prorepo.findById(id);
		if(p.isPresent())
		{
			p.get();
			return p.toString();
		}else
		{
			return "product not found";
		}
	}
	@Override
	public String deltebyid(Long id) {
		if(prorepo.existsById(id)) {
			prorepo.deleteById(id);
			return "successfully deleted your product";
			
		}else {
			return "given product id is not found check once and try again";
		}
	}

	@Override
	public String updateproduct(Product product, double price) {
		Product p=prorepo.findByPrice(price).get(0);
		p.setName(product.getName());
		p.setCategory(product.getCategory());
		p.setPrice(product.getPrice());
		p.setDescription(product.getDescription());
		return "the product is updated by price and the updated details are : \n"+ prorepo.save(p).toString();
	}

	@Override
	public String updateproduct1(Product product, String category) {
		Product pd=prorepo.findByCategory(category).get(0);
		pd.setName(product.getName());
		pd.setPrice(product.getPrice());
		pd.setDescription(product.getDescription());
		pd.setCategory(product.getCategory());
		return "the product is updated by category and the updated details are : \n"+ prorepo.save(pd).toString();
	}
}
