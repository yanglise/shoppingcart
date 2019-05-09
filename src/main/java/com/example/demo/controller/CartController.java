package com.example.demo.controller;

import com.example.demo.domain.Cart;
import com.example.demo.repository.CartRepository;
import javax.servlet.http.HttpSession;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cart")
public class CartController {

    private final CartRepository cartRepository;

    public CartController(CartRepository cartRepository) {
        this.cartRepository = cartRepository;
    }

    /**
     * Find cart by the http session ID or create a new one if does not exist
     *
     * @param httpSession the http session
     * @return response that wraps the {@link Cart} object
     */
    @GetMapping
    public ResponseEntity<?> findBySession(HttpSession httpSession) {
        Cart cart = cartRepository.findBySessionId(httpSession.getId()).orElseGet(() -> new Cart(httpSession.getId()));
        return ResponseEntity.ok(cart);
    }
}
