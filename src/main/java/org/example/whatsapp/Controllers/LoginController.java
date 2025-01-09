package org.example.whatsapp.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import org.example.whatsapp.Models.LoginModel;

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
    void onClickBorrar(ActionEvent event) {
        textFieldUsuario.setText("");
        textFieldPass.setText("");
    }

    @FXML
    public void onClickLogin(ActionEvent event) {
        String correo = textFieldUsuario.getText();
        String password = textFieldPass.getText();

        LoginModel model = new LoginModel();

        boolean loginSucces = model.comprobarLogin(correo, password);

        if (loginSucces){
            goHome(event);
        }else{
            failedLogin();
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

            stage.setHeight(800);
            stage.setWidth(1213);

            stage.centerOnScreen();
            stage.show();

        }catch (Exception e){
            System.out.println(e);
        }
    }

    public void failedLogin(){
        errorLabel.setText("Correo o contraseña incorrecto, inténtelo de nuevo.");
        errorLabel.setStyle("-fx-text-fill: red");
    }


}
