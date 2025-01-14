package org.example.whatsapp.Variables;

public class Variables {
    private static int idCliente;
    private static int idContacto;
    private static String nombreContacto;

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
}
