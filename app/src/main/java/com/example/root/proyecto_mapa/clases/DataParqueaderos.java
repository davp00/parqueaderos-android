package com.example.root.proyecto_mapa.clases;

import java.util.ArrayList;

public class DataParqueaderos {
    private ArrayList<Parqueadero> parqueaderos = null;
    private static DataParqueaderos data = null;

    private DataParqueaderos()
    {
        parqueaderos = new ArrayList<>();

        parqueaderos.add(new Parqueadero("PARQUEADERO DE DAVP","a 23-133, Cra 2 #23-39"));
        parqueaderos.add(new Parqueadero("Parqueadero la 20","Cl. 20 #2-36"));
        parqueaderos.add(new Parqueadero("Parqueadero central la 14","Cl. 14 Santa Marta, Magdalena"));
        parqueaderos.add(new Parqueadero("Parqueadero la 22", "Calle 22 cerca a la marina","6:00 am", "8:00 pm"));
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
