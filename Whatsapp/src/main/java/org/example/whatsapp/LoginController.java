package org.example.whatsapp;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class LoginController {

    private final String user = "bet";
    private final String pass = "1234";
    private final String url = "";
    Connection conexionLogin = null;

    @FXML
    private ImageView imagenLogo;

    @FXML
    private AnchorPane errorLabel;

    @FXML
    private Button buttonBorrar;

    @FXML
    private Button buttonIniciar;

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
    void onClickIniciar(ActionEvent event) {
        final String FXML = "/org/example/whatsapp/contactos.fxml";

        try {
            FXMLLoader ventanaPrincipal = new FXMLLoader(getClass().getResource(FXML));
            Parent root = ventanaPrincipal.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            Scene scene =  new Scene(root);
            stage.setHeight(800);
            stage.setWidth(1213);
            stage.setScene(scene);
            stage.centerOnScreen();
            stage.show();
        }catch (Exception e){
            System.out.println(e);
        }
    }

    void checkLogin(ActionEvent event) {
        String usuario = textFieldUsuario.getText();
        String password = textFieldPass.getText();

        boolean existe = verificarLogin(usuario, password);

        if (existe){
            onClickIniciar(event);
        }else{
            loggedFail();
        }
    }

    void loggedFail(){
        errorLabel.setStyle("-fx-text-fill: red");
    }





    /** SE ESTABLECE LA CONECCION **/

    public void startConnection(){
        try{
            conexionLogin = DriverManager.getConnection(url, user, pass);
        }catch (Exception e){
            System.out.println(e);
        }
    }

    public void endConnection(){
        try{
            conexionLogin.close();
            conexionLogin = null;
        } catch (Exception e){
            System.out.println(e);
        }
    }

    public boolean verificarLogin(String user, String pass){
        final String consulta = """
                select nombre, apellido from alumnos
                    where nombre = ? and apellido = ?; 
               
                """;
        PreparedStatement preparedStatement = null;
        ResultSet resultados = null;

        try{
            startConnection();

            preparedStatement = conexionLogin.prepareStatement(consulta);

            preparedStatement.setString(1, user);
            preparedStatement.setString(2, pass);

            resultados = preparedStatement.executeQuery();

            if (resultados.next()){
                return true;
            }
        } catch (Exception e){
            System.out.println(e);
        } finally {
            endConnection();
        }
        return false;
    }




}
