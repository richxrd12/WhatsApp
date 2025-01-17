package org.example.whatsapp;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.example.whatsapp.Objects.Conexion;

import java.io.IOException;

public class App extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        final String HOST = "10.11.1.201";
        final int PORT = 5000;

        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("LoginView.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1000, 750);

        Conexion.establecerConexion(HOST, PORT);

        stage.setTitle("Login");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}