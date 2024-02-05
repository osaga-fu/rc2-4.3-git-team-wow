package net.toyland.store.controllers;

import java.math.BigDecimal;

public class ToyResponse {

    private Long id;
    private String name;
    private String brand;
    private BigDecimal price;

    public ToyResponse(Long id, String name, String brand, BigDecimal price) {
        this.id = id;
        this.name = name;
        this.brand = brand;
        this.price = price;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getBrand() {
        return brand;
    }

    public BigDecimal getPrice() {
        return price;
    }

}
