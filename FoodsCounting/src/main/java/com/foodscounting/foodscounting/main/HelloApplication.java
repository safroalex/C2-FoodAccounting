package com.foodscounting.foodscounting.main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

/**
 * Основной класс приложения, наследующий от {@link Application}.
 * Этот класс отвечает за запуск графического интерфейса пользователя, установку начальной сцены и отображение главного окна приложения.
 *
 * Методы:
 * - start(Stage): инициализирует и отображает главное окно приложения.
 */
public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/com/foodscounting/foodscounting/view/MainView.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 800, 600);
        stage.setTitle("Главная страница");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
