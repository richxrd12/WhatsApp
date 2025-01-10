package org.example.whatsapp.Objects;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Conexion {

    private static Socket socket;
    private static ObjectOutputStream salida;
    private static ObjectInputStream entrada;

    // Obtener la conexión global
    public static Socket getSocket() {
        return socket;
    }

    public static ObjectOutputStream getSalida() {
        return salida;
    }

    public static ObjectInputStream getEntrada() {
        return entrada;
    }

    public static void establecerConexion(String host, int puerto) throws IOException {
        socket = new Socket(host, puerto);
        salida = new ObjectOutputStream(socket.getOutputStream());
        entrada = new ObjectInputStream(socket.getInputStream());
    }

    public static void cerrarConexion() throws IOException {
        if (socket != null) {
            socket.close();
            salida.close();
            entrada.close();
        }
    }
}

