package com.project.shoopy.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
public class Product {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	 private Long id;
	@NotNull(message = "product name should be not null")
	@Size(min = 3,message = "name should ontains minimum 3 characters")
	    private String name;
	@NotNull(message = "product name should be not null")
	@Size(min = 3,message = "catagory type should ontains minimum 3 characters")
	    private String category;
	@NotNull(message = "product name should be not null")
	@Size(min = 5,message = "discription should contains minimum 5 characters")
	    private String description;
	@NotNull(message = "product name should be not null")
	@Min(value = 1)
	    private double price;
	
	@ManyToOne
    @JoinColumn(name = "seller_id")
    private Seller seller;
	
		public Product() {
			super();
			// TODO Auto-generated constructor stub
		}
		public Product(String name, String category, String description, double price) {
			super();
			this.name = name;
			this.category = category;
			this.description = description;
			this.price = price;
		}
		public long getId() {
			return id;
		}
		public void setId(Long id) {
			this.id = id;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getCategory() {
			return category;
		}
		public void setCategory(String category) {
			this.category = category;
		}
		public String getDescription() {
			return description;
		}
		public void setDescription(String description) {
			this.description = description;
		}
		public double getPrice() {
			return price;
		}
		public void setPrice(double price) {
			this.price = price;
		}
		@Override
		public String toString() {
			return "Product details \n id=" + id + "\n name=" + name + "\n category=" + category + "\n description=" + description
					+ "\n price=" + price + " ";
		}

		public Seller getSeller() {
		return seller;
	}
	public void setSeller(Seller seller) {
		this.seller = seller;
	}
}
