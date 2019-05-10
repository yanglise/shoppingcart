package com.example.demo.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.demo.domain.Product;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

public class ProductControllerITest extends AbstractITest {

    @Test
    public void testFindAllProducts() throws Exception {
        Product productOne = ProductBuilder.newInstance().withName("One").withDescription("One").withImageUrl("http://example.com").withPrice(100.0).build();
        Product productTwo = ProductBuilder.newInstance().withName("Two").withDescription("Two").withImageUrl("http://example.com").withPrice(200.0).build();
        Product productThree = ProductBuilder.newInstance().withName("Three").withDescription("Three").withImageUrl("http://example.com").withPrice(300.0).build();

        productRepository.save(productOne);
        productRepository.save(productTwo);
        productRepository.save(productThree);

        mockMvc.perform(MockMvcRequestBuilders.get("/product/all")
            .session(DEFAULT_SESSION))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", Matchers.is(Matchers.hasSize(3))));
    }

    static class ProductBuilder {

        private String id;

        private String name;

        private String description;

        private String imageUrl;

        private Double price;

        private ProductBuilder() {

        }

        public static ProductBuilder newInstance() {
            return new ProductBuilder();
        }

        public ProductBuilder withId(String id) {
            this.id = id;
            return this;
        }

        public ProductBuilder withName(String name) {
            this.name = name;
            return this;
        }

        public ProductBuilder withDescription(String description) {
            this.description = description;
            return this;
        }

        public ProductBuilder withImageUrl(String imageUrl) {
            this.imageUrl = imageUrl;
            return this;
        }

        public ProductBuilder withPrice(Double price) {
            this.price = price;
            return this;
        }

        public Product build() {
            Product product = new Product();
            product.setId(id);
            product.setName(name);
            product.setDescription(description);
            product.setImageUrl(imageUrl);
            product.setPrice(price);
            return product;
        }
    }
}
