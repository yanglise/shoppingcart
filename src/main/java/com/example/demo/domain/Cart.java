package com.example.demo.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import java.util.List;
import java.util.Map;
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

    @NotNull
    @Indexed(unique = true)
    @JsonProperty(access = Access.READ_ONLY)
    private String sessionId;

    @JsonProperty(access = Access.READ_ONLY)
    private Map<String, Integer> products;

    @Transient
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

    public List<LineItem> getLineItems() {
        return lineItems;
    }

    public void setLineItems(List<LineItem> lineItems) {
        this.lineItems = lineItems;
    }
}
