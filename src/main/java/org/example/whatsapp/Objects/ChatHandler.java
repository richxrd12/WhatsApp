package org.example.whatsapp.Objects;

import javafx.application.Platform;
import org.example.whatsapp.Controllers.ChatController;
import java.io.ObjectInputStream;

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
            while (true) {
                synchronized (objectInputStream) {
                    Object respuesta = objectInputStream.readObject();
                    if (respuesta instanceof String) {
                        String mensajeRecibido = (String) respuesta;
                        if (mensajeRecibido.equals("mensaje-recibido")) {
                            Platform.runLater(() -> {
                                try {
                                    controller.initialize(); // Actualiza la interfaz
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            });
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
