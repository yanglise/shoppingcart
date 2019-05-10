package com.example.demo.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.demo.controller.CartController.ProductRequest;
import com.example.demo.controller.ProductControllerITest.ProductBuilder;
import com.example.demo.domain.Cart;
import com.example.demo.domain.Product;
import java.io.IOException;
import java.util.List;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

public class CartControllerITest extends AbstractITest {

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
    public void testFindCart_withSession() throws Exception {
        cartRepository.save(new Cart(DEFAULT_SESSION.getId()));
        mockMvc.perform(MockMvcRequestBuilders.get("/cart")
            .contentType(MediaType.APPLICATION_JSON)
            .session(DEFAULT_SESSION))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id", Matchers.is(Matchers.notNullValue())));
    }

    @Test
    public void testFindCart_withoutSession() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/cart")
            .contentType(MediaType.APPLICATION_JSON)
            .session(DEFAULT_SESSION))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id", Matchers.is(Matchers.nullValue())));
    }

    @Test
    public void testUpdateCart_withSession() throws Exception {
        cartRepository.save(new Cart(DEFAULT_SESSION.getId()));
        List<Product> products = productRepository.findAll();
        Product product = products.get(0);

        ProductRequest productRequest = new ProductRequest();
        productRequest.setProductId(product.getId());
        productRequest.setQuantity(3);

        mockMvc.perform(MockMvcRequestBuilders.patch("/cart")
            .contentType(MediaType.APPLICATION_JSON)
            .content(toJson(productRequest))
            .session(DEFAULT_SESSION))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(jsonPath("$.lineItems[0].quantity", Matchers.is(3)))
            .andExpect(jsonPath("$.lineItems[0].subtotal", Matchers.is(3 * product.getPrice())));
    }

    @Test
    public void testUpdateCart_withoutSession() throws Exception {
        List<Product> products = productRepository.findAll();
        Product product = products.get(0);

        ProductRequest productRequest = new ProductRequest();
        productRequest.setProductId(product.getId());
        productRequest.setQuantity(3);

        mockMvc.perform(MockMvcRequestBuilders.patch("/cart")
            .contentType(MediaType.APPLICATION_JSON)
            .content(toJson(productRequest))
            .session(DEFAULT_SESSION))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(jsonPath("$.lineItems[0].quantity", Matchers.is(3)))
            .andExpect(jsonPath("$.lineItems[0].subtotal", Matchers.is(3 * product.getPrice())));
        MatcherAssert.assertThat(cartRepository.findBySessionId(DEFAULT_SESSION.getId()), Matchers.is(Matchers.notNullValue()));
    }

    @Test
    public void testUpdateCart_negativeQuantity() throws Exception {
        List<Product> products = productRepository.findAll();
        Product product = products.get(0);

        ProductRequest productRequest = new ProductRequest();
        productRequest.setProductId(product.getId());
        productRequest.setQuantity(-1);

        mockMvc.perform(MockMvcRequestBuilders.patch("/cart")
            .contentType(MediaType.APPLICATION_JSON)
            .content(toJson(productRequest))
            .session(DEFAULT_SESSION))
            .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    public void testUpdateCart_missingProduct() throws Exception {
        ProductRequest productRequest = new ProductRequest();
        productRequest.setProductId("wrong");
        productRequest.setQuantity(1);

        mockMvc.perform(MockMvcRequestBuilders.patch("/cart")
            .contentType(MediaType.APPLICATION_JSON)
            .content(toJson(productRequest))
            .session(DEFAULT_SESSION))
            .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    public void testUpdateCart_deleteProduct() throws Exception {
        List<Product> products = productRepository.findAll();
        Product product = products.get(0);

        ProductRequest productRequest = new ProductRequest();
        productRequest.setProductId(product.getId());
        productRequest.setQuantity(0);

        mockMvc.perform(MockMvcRequestBuilders.patch("/cart")
            .contentType(MediaType.APPLICATION_JSON)
            .content(toJson(productRequest))
            .session(DEFAULT_SESSION))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.lineItems", Matchers.hasSize(0)));
    }
}
