package com.foodscounting.foodscounting.model.warehouse;

import java.time.LocalDate;

/**
 * Класс описывает детальную информацию о товаре на складе.
 * Включает группу товара, название, количество, единицу измерения, срок годности и примечание.
 */
public class ProductDetail {
    private String group;
    private String name;
    private Integer quantity;
    private String unit;
    private LocalDate expiryDate;
    private String remark;

    public ProductDetail(String group, String name, Integer quantity, String unit, LocalDate expiryDate, String remark) {
        this.group = group;
        this.name = name;
        this.quantity = quantity;
        this.unit = unit;
        this.expiryDate = expiryDate;
        this.remark = remark;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public LocalDate getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(LocalDate expiryDate) {
        this.expiryDate = expiryDate;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
