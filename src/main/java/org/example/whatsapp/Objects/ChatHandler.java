package org.example.whatsapp.Objects;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.example.whatsapp.Controllers.ChatController;
import java.io.ObjectInputStream;
import java.util.ArrayList;

public class ChatHandler implements Runnable{

    ObjectInputStream objectInputStream;
    ChatController controller;

    public ChatHandler(ObjectInputStream objectInputStream, ChatController controller){
        this.objectInputStream = objectInputStream;
        this.controller = controller;

    }

    @Override
    public void run() {
        try {
            ObjectInputStream entrada = Conexion.getEntrada();
            while (true) {
                Object respuesta = entrada.readObject();
                if (respuesta instanceof String) {
                    String mensajeRecibido = (String) respuesta;
                    if (mensajeRecibido.equals("mensaje-recibido")) {
                        Platform.runLater(() -> {
                            try {
                                ArrayList<Mensaje> nuevosMensajes = controller.pedirMensajes();
                                ObservableList<Mensaje> mensajes = FXCollections.observableArrayList(nuevosMensajes);
                                mensajes.setAll(nuevosMensajes);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        });
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
