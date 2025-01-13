package org.example.whatsapp.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import org.example.whatsapp.Objects.Conexion;
import org.example.whatsapp.Variables.Variables;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Map;

public class ChatController {

    @FXML
    private ListView<?> chatListView;

    @FXML
    private TextField chatTextField;

    @FXML
    private Label nombreLabel;

    @FXML
    private ImageView usuarioImage;

    private int idCliente;

    private int contactId;

    //Aquí poner el método que pide los mensajes al servidor
    @FXML
    public void initialize(){
        try{
            pedirMensajes();
        }catch (Exception e){
            System.out.println(e);
        }
    }

    @FXML
    void onClickEnviarMensaje(ActionEvent event) {

    }

    public void pedirMensajes() throws IOException {
        ObjectInputStream entrada = Conexion.getEntrada();
        ObjectOutputStream salida = Conexion.getSalida();

        //Ponemos los datos en un Map
        Map<String, String> datosMensajes = new HashMap<>();
        datosMensajes.put("peticion", "peticion-mensajes");
        datosMensajes.put("idCliente", String.valueOf(Variables.getIdCliente()));
        datosMensajes.put("idContacto", String.valueOf(Variables.getIdContacto()));

        salida.writeObject(datosMensajes);
    }

    void setNombreLabel(String nombre){
        nombreLabel.setText(nombre);
    }
}
