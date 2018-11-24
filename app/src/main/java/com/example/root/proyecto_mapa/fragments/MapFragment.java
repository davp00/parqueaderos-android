package com.example.root.proyecto_mapa.fragments;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.root.proyecto_mapa.ParqueaderoActivity;
import com.example.root.proyecto_mapa.R;
import com.example.root.proyecto_mapa.clases.DataParqueaderos;
import com.example.root.proyecto_mapa.clases.Parqueadero;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MapFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MapFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MapFragment extends Fragment implements OnMapReadyCallback
, GoogleMap.OnMarkerClickListener, GoogleMap.OnInfoWindowClickListener,
        LocationListener{

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    protected GoogleMap mGoogleMap;
    protected MapView mapView;
    protected View mView;
    protected Button btn_encender_gps;
    protected ArrayList<Parqueadero> parqueaderos;

    // PARA LAS LOCACIONES
    protected LocationManager locationManager;
    protected LocationListener locationListener;


    private OnFragmentInteractionListener mListener;

    public MapFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MapFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MapFragment newInstance(String param1, String param2) {
        MapFragment fragment = new MapFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView =  inflater.inflate(R.layout.fragment_map, container, false);


        btn_encender_gps = (Button) mView.findViewById(R.id.btn_encender_gps);




        return mView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mapView = (MapView) mView.findViewById(R.id.map);
        if ( mapView != null)
        {
            mapView.onCreate(null);
            mapView.onResume();
            mapView.getMapAsync(this);
        }
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        MapsInitializer.initialize( getContext() );
        mGoogleMap = googleMap;

        LatLng posInicial = new LatLng(11.2316915, -74.2170733);
        float zoom = 13f;

        mGoogleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        mGoogleMap.setOnInfoWindowClickListener(this);

        parqueaderos = DataParqueaderos.getParqueaderos();

        for (int a = 0 ; a < parqueaderos.size() ; a++)
        {
            Parqueadero park = parqueaderos.get(a);
            if ( park.getPos() != null)
            {
                Marker marker = mGoogleMap.addMarker(new MarkerOptions().position(park.getPos()).title(park.getNombre()));
                park.setTag(String.valueOf(a));
                marker.setTag(park.getTag());
            }
        }

        btn_encender_gps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                on_click_encender_gps();
            }
        });


        mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(posInicial,zoom));
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        Toast.makeText(getContext(), "Qlitos", Toast.LENGTH_SHORT).show();
        return false;
    }

    @Override
    public void onInfoWindowClick(Marker marker) {
        String tk = (String) marker.getTag();

        Parqueadero p = DataParqueaderos.BuscarParqueadero(tk);
        Intent verParqueadero = new Intent(getContext(), ParqueaderoActivity.class);

        Bundle bundle = new Bundle();
        bundle.putSerializable("parqueadero", p);

        verParqueadero.putExtras(bundle);

        startActivity(verParqueadero);
    }

    @Override
    public void onLocationChanged(Location location) {

    }





    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }


    public void on_click_encender_gps()
    {
        this.mGoogleMap.clear();
    }
}
