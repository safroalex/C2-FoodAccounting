package com.foodscounting.foodscounting.model.warehouse;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import java.time.LocalDate;

public class ProductRecord {
    private final IntegerProperty number;
    private final ObjectProperty<LocalDate> date;

    public ProductRecord(int number, LocalDate date) {
        this.number = new SimpleIntegerProperty(number);
        this.date = new SimpleObjectProperty<>(date);
    }

    // В классе ProductRecord
    public LocalDate getDate() {
        return date.get();
    }


    public IntegerProperty numberProperty() {
        return number;
    }

    public ObjectProperty<LocalDate> dateProperty() {
        return date;
    }
}
