package org.example.whatsapp.Controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.example.whatsapp.Variables.Variables;

public class TarjetaContactoController {

    @FXML
    private Label estadoLabel;

    @FXML
    private ImageView imageView;

    @FXML
    private Label nombreLabel;

    private int id;

    public void setContacto(int id, String nombre, String estado, String fotoUrl) {
        this.id = id;
        this.nombreLabel.setText(nombre);
        this.estadoLabel.setText(estado);

        String imagePath = "C:\\Users\\richa\\Desktop\\Clases\\DAD\\WhatsApp\\src\\main\\resources\\org\\example\\whatsapp\\img\\hasbulla.jpg";
        this.imageView.setImage(new Image("file:///" + imagePath.replace("\\", "/")));

    }

    @FXML
    void onClickGoChat(MouseEvent event) {
        final String FXML = "/org/example/whatsapp/ChatView.fxml";

        try {
            Variables.setIdContacto(getId()); // Seteamos primero el IdContacto
            Variables.setNombreContacto(nombreLabel.getText());

            FXMLLoader loader = new FXMLLoader(getClass().getResource(FXML));
            Parent root = loader.load();

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));

            ChatController controller = loader.getController();
            controller.setNombreLabel(nombreLabel.getText());

            stage.setHeight(870);
            stage.setWidth(610);
            stage.centerOnScreen();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    public int getId(){
        return id;
    }
}




