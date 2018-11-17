package com.example.root.proyecto_mapa.clases;

import java.io.Serializable;

public class Parqueadero implements Serializable {
    private String nombre;
    private String direccion;
    private String hora_inicio = "";
    private String hora_fin = "";

    public Parqueadero()
    {
        nombre      = "";
        direccion   = "";
        hora_inicio = "";
        hora_fin    = "";
    }

    public Parqueadero(String nombre)
    {
        this.nombre = nombre;
        direccion   = "";
    }

    public Parqueadero(String nombre, String direccion, String hora_inicio, String hora_fin) {
        this.nombre = nombre;
        this.direccion = direccion;
        this.hora_inicio = hora_inicio;
        this.hora_fin = hora_fin;
    }

    public Parqueadero(String nombre, String direccion) {
        this.nombre = nombre;
        this.direccion = direccion;
    }

    public String horario()
    {
        if ( hora_inicio.equals(""))
            return "HORARIO: 24 Horas";
        return "HORARIO: "+hora_inicio + " hasta " + hora_fin + ".";
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDireccion() {
        return "DIRECCION: " + direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getHora_inicio() {
        return hora_inicio;
    }

    public void setHora_inicio(String hora_inicio) {
        this.hora_inicio = hora_inicio;
    }

    public String getHora_fin() {
        return hora_fin;
    }

    public void setHora_fin(String hora_fin) {
        this.hora_fin = hora_fin;
    }
}
