package com.foodscounting.foodscounting.model;

import java.util.UUID;

public class ProductItem {
    private UUID id;
    private String name;

    public ProductItem(UUID id, String name) {
        this.id = id;
        this.name = name;
    }

    public UUID getId() {
        return id;
    }

    @Override
    public String toString() {
        return name;
    }
}
