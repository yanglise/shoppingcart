package com.example.demo.controller;

import com.example.demo.domain.Cart;
import com.example.demo.domain.LineItem;
import com.example.demo.domain.Order;
import com.example.demo.repository.CartRepository;
import com.example.demo.repository.OrderRepository;
import com.example.demo.repository.ProductRepository;
import java.util.Collections;
import java.util.List;
import javax.servlet.http.HttpSession;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/order")
public class OrderController {

    private final OrderRepository orderRepository;

    private final CartRepository cartRepository;

    private final ProductRepository productRepository;

    public OrderController(OrderRepository orderRepository, CartRepository cartRepository,
        ProductRepository productRepository) {
        this.orderRepository = orderRepository;
        this.cartRepository = cartRepository;
        this.productRepository = productRepository;
    }

    @GetMapping("/all")
    public ResponseEntity<?> findAllBySession(HttpSession httpSession) {
        List<Order> orders = orderRepository.findAllBySessionIdOrderByCreatedDesc(httpSession.getId());
        return ResponseEntity.ok(orders);
    }

    @PostMapping
    public ResponseEntity<?> checkout(HttpSession httpSession) {
        Cart cart = cartRepository.findBySessionId(httpSession.getId()).orElseThrow(() -> new ResourceNotFoundException("Cart not found"));

        Order order = new Order(httpSession.getId());

        List<LineItem> lineItems = order.getLineItems();

        // Update order line items for everything in the cart
        if (cart.getProducts() != null) {
            cart.getProducts().forEach((productId, quantity) -> productRepository.findById(productId).ifPresent(product -> {
                LineItem lineItem = new LineItem();
                lineItem.setProduct(product);
                lineItem.setQuantity(quantity);
                lineItem.setSubtotal(product.getPrice() * quantity);
                lineItems.add(lineItem);
            }));
        }

        // Update the total price of a order
        order.setTotalPrice(lineItems.stream().map(LineItem::getSubtotal).reduce(0D, Double::sum));
        order = orderRepository.save(order);

        // Empty the cart
        cart.setProducts(Collections.emptyMap());
        cartRepository.save(cart);

        return ResponseEntity.status(HttpStatus.CREATED).body(order);
    }
}
