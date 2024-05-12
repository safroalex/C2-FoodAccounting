package com.foodscounting.foodscounting.main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        // Обновляем путь к FXML файлу на MainView.fxml
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/com/foodscounting/foodscounting/view/MainView.fxml"));
        // Можно изменить размер окна, чтобы лучше соответствовать вашему интерфейсу
        Scene scene = new Scene(fxmlLoader.load(), 800, 600); // Увеличил размер окна
        stage.setTitle("Main View"); // Обновляем заголовок окна
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
