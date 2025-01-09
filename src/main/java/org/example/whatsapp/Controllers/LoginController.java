package org.example.whatsapp.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class LoginController {

    @FXML
    private ImageView imagenLogo;

    @FXML
    private Label errorLabel;

    @FXML
    private TextField textFieldPass;

    @FXML
    private TextField textFieldUsuario;

    @FXML
    public void onClickLogin(ActionEvent event) {
        String correo = textFieldUsuario.getText();
        String password = textFieldPass.getText();

        boolean loginSucces = loginServidor(correo, password);

        if (loginSucces){
            goHome(event);
        }else{
            failedLogin();
        }
    }

    @FXML
    public void onClickRegister(MouseEvent event) {
        final String FXML = "/org/example/whatsapp/RegisterView.fxml";

        try {
            FXMLLoader ventanaPrincipal = new FXMLLoader(getClass().getResource(FXML));
            Parent root = ventanaPrincipal.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            Scene scene =  new Scene(root);
            stage.setScene(scene);

            stage.setHeight(500);
            stage.setWidth(500);

            stage.centerOnScreen();
            stage.show();

        }catch (Exception e){
            System.out.println(e);
        }
    }

    public void goHome(ActionEvent event) {
        final String FXML = "/org/example/whatsapp/ContactosView.fxml";

        try {
            FXMLLoader ventanaPrincipal = new FXMLLoader(getClass().getResource(FXML));
            Parent root = ventanaPrincipal.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            Scene scene =  new Scene(root);
            stage.setScene(scene);

            stage.setHeight(500);
            stage.setWidth(500);

            stage.centerOnScreen();
            stage.show();

        }catch (Exception e){
            System.out.println(e);
        }
    }

    public void failedLogin(){
        errorLabel.setVisible(true);
        errorLabel.setText("Correo o contraseña incorrecto, inténtelo de nuevo.");
        errorLabel.setStyle("-fx-text-fill: red");
    }

    public boolean loginServidor(String correo, String pass){
        final String HOST = "localhost";
        final int PORT = 5000;

        try (Socket socket = new Socket(HOST, PORT)){
            //Ponemos los datos en un Map
            Map<String, String> datosLogin = new HashMap<>();
            datosLogin.put("correo", correo);
            datosLogin.put("password", pass);

            ObjectOutputStream salida = new ObjectOutputStream(socket.getOutputStream());
            salida.writeObject(datosLogin);
            salida.flush();

            //Esperamos la respuesta del servidor
            ObjectInputStream entrada = new ObjectInputStream(socket.getInputStream());
            String respuesta = (String) entrada.readObject();

            if (respuesta.equals("true")){
                System.out.println("Se ha logeado correctamente");
                return true;
            } else{
                System.out.println("No se ha podido logear");
                return false;
            }
        } catch (Exception e){
            System.out.println(e);
        }

        return false;
    }


}
