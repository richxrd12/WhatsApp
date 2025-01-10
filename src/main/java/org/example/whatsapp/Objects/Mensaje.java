package org.example.whatsapp.Objects;

public class Mensaje {

    private int id;
    private Usuario remitente;
    private Usuario destinatario;
    private String mensaje;
    private String fecha;

    public Mensaje(int id, Usuario remitente, Usuario destinatario, String mensaje, String fecha) {
        this.id = id;
        this.remitente = remitente;
        this.destinatario = destinatario;
        this.mensaje = mensaje;
        this.fecha = fecha;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Usuario getRemitente() {
        return remitente;
    }

    public void setRemitente(Usuario remitente) {
        this.remitente = remitente;
    }

    public Usuario getDestinatario() {
        return destinatario;
    }

    public void setDestinatario(Usuario destinatario) {
        this.destinatario = destinatario;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

}

