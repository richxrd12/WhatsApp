package org.example.whatsapp.Controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class ChatRecibidoController {

    @FXML
    private Label fechaLabel;

    @FXML
    private Label mensajeLabel;

    @FXML
    private Label nombreLabel;

    public void setText(String nombre, String mensaje, String fecha){
        nombreLabel.setText(nombre);
        fechaLabel.setText(fecha);
        mensajeLabel.setText(mensaje);
    }
}
