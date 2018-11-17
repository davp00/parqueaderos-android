package com.example.root.proyecto_mapa;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.root.proyecto_mapa.clases.Parqueadero;

public class ParqueaderoActivity extends AppCompatActivity {

    ImageView   parker_imagen;
    TextView    parker_nombre;
    TextView    parker_direccion;
    TextView    parker_horario;
    Parqueadero parqueadero;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parqueadero);

        parker_nombre       = (TextView) findViewById(R.id.parker_nombre);
        parker_direccion    = (TextView) findViewById(R.id.parker_direccion);
        parker_horario      = (TextView) findViewById(R.id.parker_horario);

        parker_imagen       = (ImageView) findViewById(R.id.parker_imagen);

        Bundle parker_enviado = getIntent().getExtras();

        if ( parker_enviado != null)
        {
            parqueadero = (Parqueadero) parker_enviado.getSerializable("parqueadero");

            parker_nombre.setText( parqueadero.getNombre().toUpperCase() );
            parker_direccion.setText(parqueadero.getDireccion());
            parker_horario.setText(parqueadero.horario());
        }else
            finish();
    }
}
