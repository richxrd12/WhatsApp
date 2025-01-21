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
            ObjectInputStream entrada = Conexion.getEntrada();
            while (true) {
                Object respuesta = entrada.readObject();
                if (respuesta instanceof String) {
                    String mensajeRecibido = (String) respuesta;
                    if (mensajeRecibido.equals("mensaje-recibido")) {
                        Platform.runLater(() -> {
                            try {
                                System.out.println("Hola");
                                controller.initialize();
                            } catch (Exception e) {
                                System.out.println(e);
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
