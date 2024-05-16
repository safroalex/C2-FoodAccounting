package com.foodscounting.foodscounting.model.tech;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Date;
import java.util.UUID;

/**
 * Класс модели для раскладки, включающий идентификатор, даты начала и окончания, статус и список блюд.
 * Используется для управления информацией о раскладках в приложении, включая их статус и ассоциированные блюда.
 *
 * Атрибуты:
 * - id: Уникальный идентификатор раскладки.
 * - dateBegin: Дата начала раскладки.
 * - dateEnd: Дата окончания раскладки.
 * - status: Статус раскладки (например, 'New', 'Approved').
 * - dishes: Список блюд, включенных в раскладку.
 *
 * Методы:
 * - getId(): Возвращает идентификатор раскладки.
 * - getDateBegin(): Возвращает дату начала раскладки.
 * - getDateEnd(): Возвращает дату окончания раскладки.
 * - getStatus(): Возвращает статус раскладки.
 * - getDishes(): Возвращает список блюд раскладки.
 */
public class Layout {
    private UUID id;
    private Date dateBegin;
    private Date dateEnd;
    private String status;
    private ObservableList<Dish> dishes;

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
        this.dishes = FXCollections.observableArrayList();
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
}



