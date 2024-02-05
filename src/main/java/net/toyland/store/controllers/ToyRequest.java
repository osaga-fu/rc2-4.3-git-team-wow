package net.toyland.store.controllers;

import java.math.BigDecimal;

public class ToyRequest {
    private String name;
    private String brand;
    private BigDecimal price;

    public String getName() {
        return this.name;
    }

    public String getBrand() {
        return this.brand;
    }

    public BigDecimal getPrice() {
        return this.price;
    }

}
