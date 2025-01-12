package org.example.whatsapp.Controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class TarjetaContactoController {

    @FXML
    private Label estadoLabel;

    @FXML
    private ImageView imageView;

    @FXML
    private Label nombreLabel;

    public void setContacto(String nombre, String estado, String fotoUrl) {
        this.nombreLabel.setText(nombre);
        this.estadoLabel.setText(estado);

        String imagePath = "C:\\Users\\richa\\Desktop\\Clases\\DAD\\WhatsApp\\src\\main\\resources\\org\\example\\whatsapp\\img\\hasbulla.jpg";
        this.imageView.setImage(new Image("file:///" + imagePath.replace("\\", "/")));

    }
}




