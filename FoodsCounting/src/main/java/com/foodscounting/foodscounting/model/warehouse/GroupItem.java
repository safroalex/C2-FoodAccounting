package com.foodscounting.foodscounting.model.warehouse;

import java.util.UUID;

/**
 * Класс представляет собой элемент группы товаров.
 * Хранит информацию об идентификаторе и названии группы.
 */
public class GroupItem {
    private UUID id;
    private String name;

    public GroupItem(UUID id, String name) {
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
