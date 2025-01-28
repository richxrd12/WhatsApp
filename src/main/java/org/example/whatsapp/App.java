package org.example.whatsapp;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.example.whatsapp.Objects.Conexion;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class App extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        final String HOST = "localhost";
        final int PORT = 5000;
        final int PORT_ESCUCHA = 5001;

        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("LoginView.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1000, 750);

        try{
            /**
             * Se establece la conexión con el servidor, se obtiene su InputStream (para recibir datos), recibe el
             * código que manda el servidor cada vez que se establece una conexión, lo muestra por consola y
             * se establece una conexión secundaria que estará en escucha.
             */
            Conexion.establecerConexion(HOST, PORT);
            ObjectInputStream inputStream = Conexion.getEntrada();

            String codigo = (String) inputStream.readObject();
            System.out.println(codigo);

            Conexion.establecerConexionEscucha(HOST, PORT_ESCUCHA);

        }catch (Exception e){
            System.out.println("Hubo un error estableciendo la conexion");
        }

        stage.setTitle("Login");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}