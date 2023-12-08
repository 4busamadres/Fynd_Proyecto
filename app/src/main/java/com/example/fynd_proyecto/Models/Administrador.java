package com.example.fynd_proyecto.Models;

public class Administrador {
    private String correo;
    private String clave;

    public Administrador(String correo, String contrasenia) {
        this.correo = correo;
        this.clave = contrasenia;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getContrasenia() {
        return clave;
    }

    public void setContrasenia(String contrasenia) {
        this.clave = contrasenia;
    }
}
