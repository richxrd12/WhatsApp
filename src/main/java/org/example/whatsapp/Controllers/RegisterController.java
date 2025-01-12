package org.example.whatsapp.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.example.whatsapp.Objects.Conexion;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class RegisterController {
    @FXML
    private Label errorLabel;

    @FXML
    private Label successLabel;

    @FXML
    private TextField correoTextField;

    @FXML
    private TextField nombreTextField;

    @FXML
    private TextArea estadoTextArea;

    @FXML
    private PasswordField passwordField;

    @FXML
    void onClickLogin(MouseEvent event) {
        final String FXML = "/org/example/whatsapp/LoginView.fxml";

        try {
            FXMLLoader ventanaPrincipal = new FXMLLoader(getClass().getResource(FXML));
            Parent root = ventanaPrincipal.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            Scene scene =  new Scene(root);
            stage.setScene(scene);

            stage.setHeight(750);
            stage.setWidth(1000);

            stage.centerOnScreen();
            stage.show();

        }catch (Exception e){
            System.out.println(e);
        }
    }

    @FXML
    void onClickRegister(ActionEvent event) {
        String nombre = nombreTextField.getText();
        String estado = estadoTextArea.getText();
        String correo = correoTextField.getText();
        String password = passwordField.getText();

        boolean registerSuccess = registerServer(nombre, estado, correo, password);

        if (registerSuccess) {
            succcessRegister();
            goLogin(event);
        }else{
            failedRegister();
        }
    }

    public void goLogin(ActionEvent event){
        final String FXML = "/org/example/whatsapp/LoginView.fxml";

        try {
            FXMLLoader ventanaPrincipal = new FXMLLoader(getClass().getResource(FXML));
            Parent root = ventanaPrincipal.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            Scene scene =  new Scene(root);
            stage.setScene(scene);

            stage.setHeight(750);
            stage.setWidth(1000);

            stage.centerOnScreen();
            stage.show();

        }catch (Exception e){
            System.out.println(e);
        }
    }

    public boolean registerServer(String nombre, String estado, String correo, String password){
        try {
            ObjectInputStream entrada = Conexion.getEntrada();
            ObjectOutputStream salida = Conexion.getSalida();

            //Ponemos los datos en un Map
            Map<String, String> datosRegister = new HashMap<>();

            datosRegister.put("peticion", "register");
            datosRegister.put("nombre", nombre);
            datosRegister.put("estado", estado);
            datosRegister.put("correo", correo);
            datosRegister.put("password", password);

            salida.writeObject(datosRegister);
            salida.flush();

            //Esperamos la respuesta del servidor
            String respuesta = (String) entrada.readObject();

            if (respuesta.equals("true")){
                System.out.println("Se ha registrado correctamente");
                return true;
            } else{
                System.out.println("No se ha podido registrar");
                return false;
            }
        } catch (Exception e){
            System.out.println(e);
        }

        return false;
    }

    public void failedRegister(){
        errorLabel.setVisible(true);
        errorLabel.setStyle("-fx-text-fill: red");
    }

    public void succcessRegister(){
        successLabel.setVisible(true);
        successLabel.setStyle("-fx-text-fill: green");
    }


}
