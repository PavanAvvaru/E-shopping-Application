package com.project.shoopy.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.project.shoopy.Repositary.CartItemRepositary;
import com.project.shoopy.Repositary.CartRepositary;
import com.project.shoopy.Repositary.ProductRepositary;
import com.project.shoopy.entity.Cart;
import com.project.shoopy.entity.CartItem;
import com.project.shoopy.entity.Product;
@Service
public class CartServiceImplementation implements CartService{
	private final CartRepositary cartRepositary ;
    private final CartItemRepositary cartItemRepository;
    private final ProductRepositary productRepository ;
    
    @Autowired
    public CartServiceImplementation(CartRepositary cartRepositary, CartItemRepositary cartItemRepository,
			ProductRepositary productRepository) {
		this.cartRepositary = cartRepositary;
		this.cartItemRepository = cartItemRepository;
		this.productRepository = productRepository;
	}
    public boolean isProductInCart(Long productId) {
        return cartItemRepository.existsByProductId(productId);
    }
 // Implement methods to add products to the cart, update quantities.
    public String addProductToCart(Long productId, int quantity) {
        Product product = productRepository.findById(productId).orElse(null);

        if (product != null) {
            Optional<CartItem> optionalCartItem = cartItemRepository.findByProductId(productId);

            if (optionalCartItem.isPresent()) {
                // Product already exists in the cart, update quantity
                CartItem cartItem = optionalCartItem.get();
                cartItem.setQuantity(quantity);
                cartItemRepository.save(cartItem);
                return "Successfully updated product quantity in cart";
            } else {
                // Product not in the cart, create a new Cart and add the product
                Cart cart = new Cart();
                CartItem cartItem = new CartItem();
                cartItem.setProduct(product);
                cartItem.setQuantity(quantity);
                cartItem.setCart(cart); // Associate the cart with the cart item
                cartItemRepository.save(cartItem);
                return "Successfully added new product to cart";
            }
        } else {
            return "Product not found";
        }
    }
    @Override
    public String removeProductFromCartItem(Long cartItemId) {
        if (cartItemRepository.existsById(cartItemId)) {
            cartItemRepository.deleteById(cartItemId);
            return "Successfully removed product from cart item";
        } else {
            return "Product not found in cart item";
        }
    }
	@Override
	public List<CartItem> getAllproductsincart() {
		// TODO Auto-generated method stub
		return cartItemRepository.findAll();
	}
	
}
