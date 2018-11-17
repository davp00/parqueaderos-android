package com.example.root.proyecto_mapa.clases;

import java.util.ArrayList;

public class DataParqueaderos {
    private ArrayList<Parqueadero> parqueaderos = null;
    private static DataParqueaderos data = null;

    private DataParqueaderos()
    {
        parqueaderos = new ArrayList<>();

        parqueaderos.add(new Parqueadero("PARQUEADERO DE DAVP"));
        parqueaderos.add(new Parqueadero("Parqueadero la 20"));
        parqueaderos.add(new Parqueadero("Parqueadero central la 14"));
        parqueaderos.add(new Parqueadero("Parqueadero la 22"));
    }

    public static ArrayList<Parqueadero> getParqueaderos()
    {
        if (data == null)
        {
            data = new DataParqueaderos();
        }

        return data.parqueaderos;
    }
}
