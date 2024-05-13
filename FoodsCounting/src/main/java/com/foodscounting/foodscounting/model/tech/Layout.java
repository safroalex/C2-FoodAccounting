package com.foodscounting.foodscounting.model.tech;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Date;
import java.util.UUID;

public class Layout {
    private UUID id;
    private Date dateBegin;
    private Date dateEnd;
    private String status;
    private ObservableList<Dish> dishes; // Список блюд

    public Layout(UUID id, Date dateBegin, Date dateEnd, String status, ObservableList<Dish> dishes) {
        this.id = id;
        this.dateBegin = dateBegin;
        this.dateEnd = dateEnd;
        this.status = status;
        this.dishes = dishes;
    }

    public Layout(UUID id, Date dateBegin, Date dateEnd, String status) {
        this.id = id;
        this.dateBegin = dateBegin;
        this.dateEnd = dateEnd;
        this.status = status;
        this.dishes = FXCollections.observableArrayList(); // Инициализация пустого списка
    }

    public UUID getId() {
        return id;
    }

    public Date getDateBegin() {
        return dateBegin;
    }

    public Date getDateEnd() {
        return dateEnd;
    }

    public String getStatus() {
        return status;
    }

    public ObservableList<Dish> getDishes() {
        return dishes;
    }

    // Дополнительные методы
}



