package com.example.root.proyecto_mapa.adaptadores;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.root.proyecto_mapa.R;
import com.example.root.proyecto_mapa.clases.Parqueadero;

import java.util.ArrayList;

public class AdapterListaParqueaderos extends RecyclerView.Adapter<AdapterListaParqueaderos.ParqueaderoViewHolder>
implements View.OnClickListener{

    private ArrayList<Parqueadero> parqueaderos;
    private View.OnClickListener listener;


    public AdapterListaParqueaderos(ArrayList<Parqueadero>parqueaderos)
    {
        this.parqueaderos = parqueaderos;
    }

    @NonNull
    @Override
    public ParqueaderoViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View vista = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.vista_parqueadero, viewGroup, false);
        vista.setOnClickListener(this);
        ParqueaderoViewHolder park = new ParqueaderoViewHolder(vista);
        return park;
    }

    @Override
    public void onBindViewHolder(@NonNull ParqueaderoViewHolder holder, int i) {
        Parqueadero parqueadero = this.parqueaderos.get(i);
        holder.txt_nombre.setText(parqueadero.getNombre());
        holder.txt_direccion.setText(parqueadero.getDireccion());
    }

    @Override
    public int getItemCount() {
        return this.parqueaderos.size();
    }


    public void setOnClickListener(View.OnClickListener listener)
    {
        this.listener = listener;
    }

    @Override
    public void onClick(View v) {
        if ( listener != null)
        {
            listener.onClick(v);
        }
    }

    public class ParqueaderoViewHolder extends RecyclerView.ViewHolder {
        TextView txt_nombre;
        TextView txt_direccion;

        public ParqueaderoViewHolder(@NonNull View vista) {
            super(vista);
            txt_nombre = (TextView) vista.findViewById(R.id.txt_nombre);
            txt_direccion = (TextView) vista.findViewById(R.id.txt_direccion);
        }
    }
}
