package com.foodscounting.foodscounting.model.tech;

/**
 * Класс модели, представляющий блюдо с его названием и калорийностью.
 * Используется для управления информацией о блюдах в приложении.
 *
 * Атрибуты:
 * - name: Название блюда.
 * - caloricContent: Калорийность блюда.
 *
 * Методы:
 * - getName(): Возвращает название блюда.
 * - getCaloricContent(): Возвращает калорийность блюда.
 */
public class Dish {
    private String name;
    private int caloricContent;
    private String dayOfWeek;

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
    // getters and setters
    public String getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(String dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }
}
