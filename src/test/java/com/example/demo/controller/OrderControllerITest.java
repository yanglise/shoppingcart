package com.example.demo.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.demo.controller.ProductControllerITest.ProductBuilder;
import com.example.demo.domain.Cart;
import com.example.demo.domain.Order;
import com.example.demo.domain.Product;
import java.io.IOException;
import java.util.List;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

public class OrderControllerITest extends AbstractITest {

    @Override
    public void before() throws IOException {
        super.before();

        Product productOne = ProductBuilder.newInstance().withName("One").withDescription("One").withImageUrl("http://example.com").withPrice(100.0).build();
        Product productTwo = ProductBuilder.newInstance().withName("Two").withDescription("Two").withImageUrl("http://example.com").withPrice(200.0).build();
        Product productThree = ProductBuilder.newInstance().withName("Three").withDescription("Three").withImageUrl("http://example.com").withPrice(300.0).build();

        productRepository.save(productOne);
        productRepository.save(productTwo);
        productRepository.save(productThree);
    }

    @Test
    public void testFindAllOrders() throws Exception {
        orderRepository.save(new Order(DEFAULT_SESSION.getId()));
        orderRepository.save(new Order(DEFAULT_SESSION.getId()));

        mockMvc.perform(MockMvcRequestBuilders.get("/order/all")
            .session(DEFAULT_SESSION))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", Matchers.hasSize(2)));
    }

    @Test
    public void testCheckoutOrder() throws Exception {
        List<Product> products = productRepository.findAll();
        Product product = products.get(0);

        Cart cart = new Cart(DEFAULT_SESSION.getId());
        cart.setProduct(product.getId(), 3);
        cartRepository.save(cart);

        mockMvc.perform(MockMvcRequestBuilders.post("/order")
            .session(DEFAULT_SESSION))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.lineItems[0].quantity", Matchers.is(3)))
            .andExpect(jsonPath("$.lineItems[0].subtotal", Matchers.is(3 * product.getPrice())));

        cartRepository.findBySessionId(DEFAULT_SESSION.getId()).ifPresent(updated -> MatcherAssert.assertThat(updated.getProducts().size(), Matchers.is(0)));
    }

}
