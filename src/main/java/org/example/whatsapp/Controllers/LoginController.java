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
import javafx.scene.input.KeyEvent;
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
    private TextField usuarioTextField;

    @FXML
    private PasswordField passField;

    private int idCliente;

    /**
     * Si hace click en login, cogerá los parámetros de los textField de usuario y contraseña y los mandará por
     * loginServidor()
     */
    @FXML
    public void onClickLogin(ActionEvent event) {
        String usuario = usuarioTextField.getText();
        String password = passField.getText();

        boolean loginSucces = loginServidor(usuario, password);

        if (loginSucces){
            goContactos(event);
        }else{
            failedLogin();
        }
    }

    /**
     * Método para hacer login.
     * Hace una petición al servidor con el código login, el usuario y la contraseña (ambas pasadas por parámetro) y
     * depende del id que reciba, hace login o no. Si es diferente a 0 el resultado, hará login porque significa que ha
     * encontrado un usuario. De ser correcto, se guardará en la clase estática Variables el idCliente.
     */
    public boolean loginServidor(String usuario, String pass){
        try {
            // Conectar solo si no está conectado
            ObjectInputStream entrada = Conexion.getEntrada();
            ObjectOutputStream salida = Conexion.getSalida();

            // Crear datos para login
            Map<String, String> datosLogin = new HashMap<>();
            datosLogin.put("peticion", "login");
            datosLogin.put("usuario", usuario);
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

            stage.setTitle("Register");

            stage.centerOnScreen();
            stage.show();

        }catch (Exception e){
            System.out.println(e);
        }
    }

    void goContactos(ActionEvent event) {
        final String FXML = "/org/example/whatsapp/ContactosView.fxml";

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(FXML));
            Parent root = loader.load();

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));

            ContactosController controller = loader.getController();
            controller.setStage(stage);

            stage.setHeight(855);
            stage.setWidth(500);

            stage.setTitle("Contactos");

            stage.centerOnScreen();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
