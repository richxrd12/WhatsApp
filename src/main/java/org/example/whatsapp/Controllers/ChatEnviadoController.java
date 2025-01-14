package org.example.whatsapp.Controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class ChatEnviadoController {

    @FXML
    private Label fechaLabel;

    @FXML
    private Label mensajeLabel;

    public void setText(String mensaje, String fecha){
        fechaLabel.setText(fecha);
        mensajeLabel.setText(mensaje);
    }
}
