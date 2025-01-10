package org.example.whatsapp.Controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class TarjetaContactoController {

    @FXML
    private ImageView imageView;

    @FXML
    private Label nombreLabel;

    public void setContacto(String nombre, String estado, String fotoUrl) {
        this.nombreLabel.setText(nombre);
        this.imageView.setImage(new Image("file:/C:/Users/2DAM/Desktop/hasbulla.jpg"));

    }
}
