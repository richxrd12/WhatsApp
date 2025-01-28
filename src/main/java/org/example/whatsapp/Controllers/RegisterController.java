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
import javafx.scene.input.KeyEvent;
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
    private TextField usuarioTextField;

    @FXML
    private TextField nombreTextField;

    @FXML
    private TextArea estadoTextArea;

    @FXML
    private PasswordField passwordField;

    /**
     * Cuando se rellenan los campos en la pantalla de Register (si no lo hace tira un error controlado
     * (failedRegsiter()) en una Label invisible que se hace visible cuando ocurre el error), se mandan los parámetros a
     * registerServer, si este nos devuelve true, nos manda al login, si no, nos tira el error de failedRegister().
     */
    @FXML
    void onClickRegister(ActionEvent event) {
        String nombre = nombreTextField.getText();
        String estado = estadoTextArea.getText();
        String usuario = usuarioTextField.getText();
        String password = passwordField.getText();

        if (!nombre.isEmpty() && !estado.isEmpty() && !usuario.isEmpty() && !password.isEmpty()){

            boolean registerSuccess = registerServer(nombre, estado, usuario, password);

            if (registerSuccess) {
                goLogin(event);
            } else{
                failedRegister();
            }

        }else{
            failedRegister();
        }

    }

    /**
     * Este metodo nos manda al servidor una petición de register con el nombre, estado, usuario y password, datos que
     * se obtienen a través de los TextField. Si la respuesta que manda el servidor es "true", es decir, se registró
     * el usuario, nos devuelve true, si no, false.
     */
    public boolean registerServer(String nombre, String estado, String usuario, String password){
        try {
            ObjectInputStream entrada = Conexion.getEntrada();
            ObjectOutputStream salida = Conexion.getSalida();

            //Ponemos los datos en un Map
            Map<String, String> datosRegister = new HashMap<>();

            datosRegister.put("peticion", "register");
            datosRegister.put("nombre", nombre);
            datosRegister.put("estado", estado);
            datosRegister.put("usuario", usuario);
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

    /**
     * Tanto este metodo como onClickLogin nos llevan al login, lo que este se utiliza en el metodo de onClickRegister
     * cuando el usuario se ha registrado correctamente para llevarnos a el login, y el otro cuando pulsamos en la label
     * que dice: "¿Ya tiene cuenta? Inicie sesión"
     */
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
            stage.setTitle("Login");

        }catch (Exception e){
            System.out.println(e);
        }
    }

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



}
