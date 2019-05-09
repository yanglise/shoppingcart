package com.example.demo.domain;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * Record keeper for product along with its quantity and subtotals
 */
public class LineItem {

    @NotNull
    private Product product;

    @NotNull
    @Min(1)
    private Integer quantity = 0;

    @NotNull
    @Min(0)
    private Double subtotal = 0D;

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(Double subtotal) {
        this.subtotal = subtotal;
    }
}
