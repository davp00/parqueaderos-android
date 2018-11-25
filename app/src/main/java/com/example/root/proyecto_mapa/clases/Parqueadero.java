package com.example.root.proyecto_mapa.clases;

import android.location.Location;

import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.LatLng;

import java.io.Serializable;

public class Parqueadero implements Serializable {
    private String nombre;
    private String direccion;
    private String hora_inicio = "";
    private String hora_fin = "";
    private transient LatLng pos = null;
    private String tag;

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

    public LatLng getPos() {
        return pos;
    }

    public void setPos(double v1 , double v2) {
        this.pos = new LatLng(v1,v2);
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }



    public static boolean EnCirculo(Circle circle, LatLng point)
    {
        double r = circle.getRadius();
        LatLng center = circle.getCenter();
        double cX = center.latitude;
        double cY = center.longitude;
        double pX = point.latitude;
        double pY = point.longitude;

        float[] results = new float[1];

        Location.distanceBetween(cX, cY, pX, pY, results);

        return results[0] < r;
    }
}
