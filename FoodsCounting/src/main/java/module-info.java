module com.foodscounting.foodscounting {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.foodscounting.foodscounting to javafx.fxml;
    exports com.foodscounting.foodscounting;
}