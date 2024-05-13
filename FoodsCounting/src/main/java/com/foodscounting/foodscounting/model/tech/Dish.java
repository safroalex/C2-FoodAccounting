package com.foodscounting.foodscounting.model.tech;

public class Dish {
    private String name;
    private int caloricContent;

    public Dish(String name, int caloricContent) {
        this.name = name;
        this.caloricContent = caloricContent;
    }

    public String getName() {
        return name;
    }

    public int getCaloricContent() {
        return caloricContent;
    }
}
