package com.foodscounting.foodscounting.model.tech;

import java.util.UUID;

/**
 * Расширенный класс модели для блюда, включающий уникальный идентификатор, название и калорийность.
 * Используется для представления подробной информации о блюде, включая его идентификацию в базе данных.
 *
 * Атрибуты:
 * - id: Уникальный идентификатор блюда.
 * - name: Название блюда.
 * - caloricContent: Калорийность блюда.
 *
 * Методы:
 * - getId(): Возвращает идентификатор блюда.
 * - getName(): Возвращает название блюда.
 * - getCaloricContent(): Возвращает калорийность блюда.
 */
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
