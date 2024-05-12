package com.foodscounting.foodscounting.model.warehouse;

import java.util.UUID;

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
