package org.example.whatsapp.Objects;

import org.example.whatsapp.Controllers.ChatController;
import java.io.ObjectInputStream;

public class ChatHandler implements Runnable{

    ObjectInputStream objectInputStream;
    ChatController controller;

    public ChatHandler(ObjectInputStream objectInputStream, ChatController controller){
        this.objectInputStream = objectInputStream;
        this.controller = controller;

    }

    public void run(){
        try {
            String notificacion = (String) objectInputStream.readObject();

            if (notificacion != null){
                controller.initialize();
            }

        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
