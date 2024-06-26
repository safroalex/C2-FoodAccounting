package com.foodscounting.foodscounting.model.warehouse;

import java.util.UUID;

/**
 * Класс представляет собой товарную позицию.
 * Хранит уникальный идентификатор и название товара.
 */
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
