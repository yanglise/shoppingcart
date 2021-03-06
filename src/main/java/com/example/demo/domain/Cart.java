package com.example.demo.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Shopping cart representation for mongo collection
 *
 * Each shopping cart will be uniquely identified by the session id
 */
@Document(collection = "cart")
public class Cart {

    @Id
    private String id;

    @JsonIgnore
    @NotNull
    @Indexed(unique = true)
    private String sessionId;

    @JsonIgnore
    private Map<String, Integer> products;

    @Transient
    @Valid
    private List<LineItem> lineItems;

    public Cart(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public Map<String, Integer> getProducts() {
        return products;
    }

    public void setProducts(Map<String, Integer> products) {
        this.products = products;
    }

    public void setProduct(String productId, Integer quantity) {
        if (products == null) {
            products = new LinkedHashMap<>();
        }
        products.put(productId, quantity);
    }

    public List<LineItem> getLineItems() {
        return lineItems;
    }

    public void setLineItems(List<LineItem> lineItems) {
        this.lineItems = lineItems;
    }
}
