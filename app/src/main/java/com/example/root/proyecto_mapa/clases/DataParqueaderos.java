package com.example.root.proyecto_mapa.clases;

import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;

public class DataParqueaderos {
    private ArrayList<Parqueadero> parqueaderos = null;
    private static DataParqueaderos data = null;

    private DataParqueaderos()
    {
        parqueaderos = new ArrayList<>();

        Parqueadero park = new Parqueadero("PARQUEADERO DE DAVP","a 23-133, Cra 2 #23-39");
        park.setPos(11.2314439, -74.2131814);

        Parqueadero park2 = new Parqueadero("Parqueadero de Motos Colonial","Cl. 19 #4 - 44");
        park2.setPos(11.2414655,-74.2126184);

        Parqueadero park3 = new Parqueadero("Parqueadero central la 14","Cl. 14 Santa Marta, Magdalena");
        park3.setPos(11.2443837,-74.2100939);

        Parqueadero park4 = new Parqueadero("Parqueadero de bicicletas", "Cl. 16 #16-30");
        park4.setPos(11.2437862,-74.2115373);

        Parqueadero park5 = new Parqueadero("Parqueadero CC Buenavista", "Calle 29A");
        park5.setPos(11.2312596,-74.1864803);

        parqueaderos.add(park);
        parqueaderos.add(park2);
        parqueaderos.add(park3);
        parqueaderos.add(park4);
        parqueaderos.add(park5);
    }

    public static ArrayList<Parqueadero> getParqueaderos()
    {
        if (data == null)
        {
            data = new DataParqueaderos();
        }

        return data.parqueaderos;
    }

    public static Parqueadero BuscarParqueadero(String tag)
    {
        for (Parqueadero park: getParqueaderos())
        {
            if ( park.getTag().equals(tag))
                return park;
        }
        return null;
    }

}
