package com.example.fynd_proyecto.Models;

public class Linea {

    private String nombre_linea;
    private String horario;

    public Linea(String nombre_linea, String horario) {

        this.nombre_linea = nombre_linea;
        this.horario = horario;
    }



    public String getNombre_linea() {
        return nombre_linea;
    }

    public void setNombre_linea(String nombre_linea) {
        this.nombre_linea = nombre_linea;
    }

    public String getHorario() {
        return horario;
    }

    public void setHorario(String horario) {
        this.horario = horario;
    }
    public String info_linea() {
        return "*** Información de la línea ***\n" +
                "\t Nombre de la línea: " + this.getNombre_linea() + "\n" +
                "\t Horario: " + this.getHorario() + "\n" +
                "\t *** Fin información de la línea ***";
    }
}
