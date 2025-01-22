package org.example.whatsapp.Objects;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Conexion {

    private static Socket socket;
    private static Socket socketEscucha;
    private static ObjectOutputStream salida;
    private static ObjectInputStream entrada;
    private static ObjectOutputStream salidaEscucha;
    private static ObjectInputStream entradaEscucha;

    // Obtener la conexión global
    public static Socket getSocket() {
        return socket;
    }

    public static synchronized ObjectOutputStream getSalida() throws IOException {
        if (socket == null || socket.isClosed()) {
            throw new IOException("El socket está cerrado.");
        }
        return salida;
    }

    public static synchronized ObjectInputStream getEntrada() throws IOException {
        if (socket == null || socket.isClosed()) {
            throw new IOException("El socket está cerrado.");
        }
        return entrada;
    }

    public static synchronized ObjectOutputStream getSalidaEscucha() throws IOException {
        if (socketEscucha == null || socketEscucha.isClosed()) {
            throw new IOException("El socket está cerrado.");
        }
        return salidaEscucha;
    }

    public static synchronized ObjectInputStream getEntradaEscucha() throws IOException {
        if (socketEscucha == null || socketEscucha.isClosed()) {
            throw new IOException("El socket está cerrado.");
        }
        return entradaEscucha;
    }


    public static void establecerConexion(String host, int puerto) throws IOException {
        socket = new Socket(host, puerto);
        salida = new ObjectOutputStream(socket.getOutputStream());
        entrada = new ObjectInputStream(socket.getInputStream());
    }

    public static void establecerConexionEscucha(String host, int puerto) throws IOException {
        socketEscucha = new Socket(host, puerto);
        salidaEscucha = new ObjectOutputStream(socketEscucha.getOutputStream());
        entradaEscucha = new ObjectInputStream(socketEscucha.getInputStream());
    }

    public static void cerrarConexion() throws IOException {
        if (socket != null) {
            socket.close();
            salida.close();
            entrada.close();
        }
    }

    public static void cerrarConexionEscucha() throws IOException {
        if (socketEscucha != null){
            socketEscucha.close();
            salidaEscucha.close();
            entradaEscucha.close();
        }
    }

}

