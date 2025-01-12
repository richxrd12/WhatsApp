package org.example.whatsapp.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;

public class ChatController {

    @FXML
    private ListView<?> chatListView;

    @FXML
    private TextField chatTextField;

    @FXML
    private Label nombreLabel;

    @FXML
    private ImageView usuarioImage;

    private int contactId;

    //Aquí poner el método que pide los mensajes al servidor
    @FXML
    public void initialize(){

    }

    @FXML
    void onClickEnviarMensaje(ActionEvent event) {

    }

    void setNombreLabel(String nombre){
        nombreLabel.setText(nombre + " " + contactId);
    }

    void setContactId(int contactId){
        this.contactId = contactId;
    }

}
