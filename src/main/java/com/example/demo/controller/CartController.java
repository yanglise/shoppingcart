package com.example.demo.controller;

import com.example.demo.domain.Cart;
import com.example.demo.domain.LineItem;
import com.example.demo.repository.CartRepository;
import com.example.demo.repository.ProductRepository;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import javax.servlet.http.HttpSession;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cart")
public class CartController {

    private final CartRepository cartRepository;

    private final ProductRepository productRepository;

    public CartController(CartRepository cartRepository, ProductRepository productRepository) {
        this.cartRepository = cartRepository;
        this.productRepository = productRepository;
    }

    /**
     * Find cart by the http session ID or create a new one if does not exist
     *
     * @param httpSession the http session
     * @return response that wraps the {@link Cart} object
     */
    @GetMapping
    public ResponseEntity<?> findBySession(HttpSession httpSession) {
        Cart cart = findBySessionId(httpSession.getId());
        refreshLineItems(cart);
        return ResponseEntity.ok(cart);
    }

    /**
     * Update product quantity by ID and quantity
     *
     * @param productId the product ID
     * @param quantity the quantity to be updated
     * @param httpSession the http session
     * @return response that wraps the updated {@link Cart} object
     */
    @PatchMapping
    public ResponseEntity<?> updateProduct(@RequestParam String productId, @RequestParam(defaultValue = "0") Integer quantity, HttpSession httpSession) {
        Cart cart = findBySessionId(httpSession.getId());

        // Make sure product exists with the given ID, throw exception if not found
        productRepository.findById(productId).orElseThrow(() -> new ResourceNotFoundException(String.format("Product not found with ID '%s'", productId)));

        // Remove the stored product ID if quantity becomes 0 and less, otherwise update the product with quantity
        if (quantity <= 0) {
            Optional.ofNullable(cart.getProducts()).ifPresent(products -> products.remove(productId));
        } else {
            cart.setProduct(productId, quantity);
        }

        refreshLineItems(cart);
        cart = cartRepository.save(cart);

        return ResponseEntity.ok(cart);
    }

    /**
     * Delete product by ID
     *
     * @param productId the productID
     * @param httpSession the http session
     * @return response that wraps the updated {@link Cart} object
     */
    @DeleteMapping
    public ResponseEntity<?> deleteProduct(@RequestParam String productId, HttpSession httpSession) {
        Cart cart = cartRepository.findBySessionId(httpSession.getId())
            .orElseThrow(() -> new ResourceNotFoundException("Cart not found with current session"));
        Optional.ofNullable(cart.getProducts()).ifPresent(products -> products.remove(productId));

        refreshLineItems(cart);
        cart = cartRepository.save(cart);

        return ResponseEntity.ok(cart);
    }

    private Cart findBySessionId(String sessionId) {
        return cartRepository.findBySessionId(sessionId).orElseGet(() -> new Cart(sessionId));
    }

    private void refreshLineItems(Cart cart) {
        if (cart.getProducts() == null) {
            cart.setLineItems(Collections.emptyList());
            return;
        }

        // For each stored product ID, calculate the subtotal and add it to the list
        List<LineItem> lineItems = new LinkedList<>();
        cart.getProducts().forEach((productId, quantity) -> productRepository.findById(productId).ifPresent(product -> {
            LineItem lineItem = new LineItem();
            lineItem.setProduct(product);
            lineItem.setQuantity(quantity);
            lineItem.setSubtotal(product.getPrice() * quantity);
            lineItems.add(lineItem);
        }));

        // Update the line items within the cart
        cart.setLineItems(lineItems);
    }
}
