package org.example.whatsapp.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import org.example.whatsapp.Objects.Conexion;
import org.example.whatsapp.Variables.Variables;

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
    private TextField correoTextField;

    @FXML
    private PasswordField passField;

    private int idCliente;

    @FXML
    public void onClickLogin(ActionEvent event) {
        String correo = correoTextField.getText();
        String password = passField.getText();

        boolean loginSucces = loginServidor(correo, password);

        if (loginSucces){
            goContactos(event);
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

            stage.setHeight(750);
            stage.setWidth(1000);

            stage.centerOnScreen();
            stage.show();

        }catch (Exception e){
            System.out.println(e);
        }
    }

    void goContactos(ActionEvent event) {
        final String FXML = "/org/example/whatsapp/ContactosView.fxml";

        try {
            FXMLLoader loader;
            Parent root;

            if (Variables.getContactosLoader() == null) {
                loader = new FXMLLoader(getClass().getResource(FXML));
                root = loader.load();
                Variables.setContactosLoader(loader);
            } else {
                loader = Variables.getContactosLoader();
                root = loader.getRoot();

                if (root.getScene() != null) {
                    root.getScene().setRoot(new StackPane());
                }
            }

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));

            ContactosController controller = loader.getController();
            controller.setStage(stage);

            stage.setHeight(855);
            stage.setWidth(500);
            stage.centerOnScreen();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean loginServidor(String correo, String pass){
        try {
            // Conectar solo si no está conectado
            ObjectInputStream entrada = Conexion.getEntrada();
            ObjectOutputStream salida = Conexion.getSalida();

            // Crear datos para login
            Map<String, String> datosLogin = new HashMap<>();
            datosLogin.put("peticion", "login");
            datosLogin.put("correo", correo);
            datosLogin.put("password", pass);

            salida.writeObject(datosLogin);
            salida.flush();

            // Leer respuesta
            String idString = (String) entrada.readObject();
            int id = Integer.parseInt(idString);

            if (id != 0) {
                System.out.println("Se ha logeado correctamente");
                Variables.setIdCliente(id);
                return true;
            } else {
                System.out.println("No se ha podido logear");
                return false;
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return false;
    }


    public void failedLogin(){
        errorLabel.setVisible(true);
        errorLabel.setText("Correo o contraseña incorrecto, inténtelo de nuevo.");
        errorLabel.setStyle("-fx-text-fill: red");
    }

}
