package com.foodscounting.foodscounting.model.tech;

import java.util.UUID;

public class DishDetails {
    private UUID id;
    private String name;
    private int caloricContent;

    public DishDetails(UUID id, String name, int caloricContent) {
        this.id = id;
        this.name = name;
        this.caloricContent = caloricContent;
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getCaloricContent() {
        return caloricContent;
    }
}
