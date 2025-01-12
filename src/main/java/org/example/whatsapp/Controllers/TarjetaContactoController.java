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

public class TarjetaContactoController {

    @FXML
    private Label estadoLabel;

    @FXML
    private ImageView imageView;

    @FXML
    private Label nombreLabel;

    private int id;

    private int idCliente;

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
            FXMLLoader ventanaPrincipal = new FXMLLoader(getClass().getResource(FXML));
            Parent root = ventanaPrincipal.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            Scene scene =  new Scene(root);
            stage.setScene(scene);

            ChatController controller = ventanaPrincipal.getController();
            controller.setNombreLabel(nombreLabel.getText());
            controller.setContactId(id);
            controller.setIdCliente(getIdCliente());

            stage.setHeight(840);
            stage.setWidth(610);

            stage.centerOnScreen();
            stage.show();

        }catch (Exception e){
            System.out.println(e);
        }
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }
}




