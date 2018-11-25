package com.example.root.proyecto_mapa.fragments;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationListener;

import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
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
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.ArrayList;


public class MapFragment extends Fragment implements OnMapReadyCallback
        , GoogleMap.OnMarkerClickListener, GoogleMap.OnInfoWindowClickListener ,
        LocationListener, ActivityCompat.OnRequestPermissionsResultCallback{

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";



    protected GoogleMap mGoogleMap;
    protected MapView mapView;
    protected View mView;
    protected Button btn_accion;
    protected ArrayList<Parqueadero> parqueaderos;

    // PARA LAS LOCACIONES
    protected LatLng pos;
    protected LocationManager locationManager;
    protected LocationRequest request;

    private FusedLocationProviderClient mFusedLocationClient;

    protected double radio = 1000;
    protected boolean sw_gps = false;


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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_map, container, false);
        btn_accion = (Button) mView.findViewById(R.id.btn_encender_gps);
        return mView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mapView = (MapView) mView.findViewById(R.id.map);
        if (mapView != null) {
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
        MapsInitializer.initialize(getContext());
        mGoogleMap = googleMap;

        LatLng posInicial = new LatLng(11.2316915, -74.2170733);
        float zoom = 13f;

        mGoogleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        mGoogleMap.setOnInfoWindowClickListener(this);

        parqueaderos = DataParqueaderos.getParqueaderos();

        agregarMarcadores();

        btn_accion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ( ! sw_gps )
                    on_click_encender_gps();
                else
                    agregarMarcadores();

                sw_gps = ! sw_gps;
            }
        });


        mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(posInicial, zoom));
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        Toast.makeText(getContext(), "", Toast.LENGTH_SHORT).show();
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


    public void on_click_encender_gps() {
        this.mGoogleMap.clear();
        btn_accion.setText("Mostrar todos los parqueaderos");

        activar();

        Circle circle = mGoogleMap.addCircle(new CircleOptions().center(this.pos)
                        .radius(radio).strokeColor(Color.GRAY).fillColor(Color.TRANSPARENT));

        for (int a = 0; a < parqueaderos.size(); a++)
        {
            Parqueadero park = parqueaderos.get(a);
            if (Parqueadero.EnCirculo(circle, park.getPos()))
            {
                Marker marker = mGoogleMap.addMarker(new MarkerOptions().position(park.getPos()).title(park.getNombre()));
                park.setTag(String.valueOf(a));
                marker.setTag(park.getTag());
            }
        }

    }

    @SuppressLint("MissingPermission")
    private void agregarMarcadores()
    {
        this.btn_accion.setText("ENCENDER GPS");


        this.mGoogleMap.clear();
        for (int a = 0; a < parqueaderos.size(); a++) {
            Parqueadero park = parqueaderos.get(a);
            if (park.getPos() != null) {
                Marker marker = mGoogleMap.addMarker(new MarkerOptions().position(park.getPos()).title(park.getNombre()));
                park.setTag(String.valueOf(a));
                marker.setTag(park.getTag());
            }
        }
    }



    private void activar()
    {
        ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                123
        );
        if (ActivityCompat.checkSelfPermission(getActivity(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(getContext(), "First enable LOCATION ACCESS in settings.", Toast.LENGTH_LONG).show();
            return;
        }
        locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

        if ( location != null)
        {
            this.pos = new LatLng(location.getLatitude(), location.getLongitude());
        }else
        {
            this.pos = new LatLng(11.2414655,-74.2126184);
        }
    }
}
