package org.example.whatsapp.Variables;

import javafx.fxml.FXMLLoader;

public class Variables {
    private static int idCliente;
    private static int idContacto;
    private static String nombreContacto;
    private static FXMLLoader contactosLoader;
    private static FXMLLoader chatLoader;

    public static int getIdCliente() {
        return idCliente;
    }

    public static void setIdCliente(int idCliente) {
        Variables.idCliente = idCliente;
    }

    public static int getIdContacto() {
        return idContacto;
    }

    public static void setIdContacto(int idContacto) {
        Variables.idContacto = idContacto;
    }

    public static String getNombreContacto() {
        return nombreContacto;
    }

    public static void setNombreContacto(String nombreContacto) {
        Variables.nombreContacto = nombreContacto;
    }

    public static FXMLLoader getContactosLoader() {
        return contactosLoader;
    }

    public static void setContactosLoader(FXMLLoader contactosLoader) {
        Variables.contactosLoader = contactosLoader;
    }

    public static FXMLLoader getChatLoader() {
        return chatLoader;
    }

    public static void setChatLoader(FXMLLoader chatLoader) {
        Variables.chatLoader = chatLoader;
    }
}

