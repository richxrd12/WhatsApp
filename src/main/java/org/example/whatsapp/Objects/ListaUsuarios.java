package org.example.whatsapp.Objects;

import java.util.ArrayList;

public class ListaUsuarios {
    private ArrayList<Usuario> usuarios;

    public ListaUsuarios() {
        this.usuarios = new ArrayList<>();
    }

    public ListaUsuarios(ArrayList<Usuario> usuarios) {
        this.usuarios = usuarios;
    }

    public ArrayList<Usuario> getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(ArrayList<Usuario> usuarios) {
        this.usuarios = usuarios;
    }
}