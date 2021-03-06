package com.example.root.proyecto_mapa.fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.root.proyecto_mapa.ParqueaderoActivity;
import com.example.root.proyecto_mapa.R;
import com.example.root.proyecto_mapa.adaptadores.AdapterListaParqueaderos;
import com.example.root.proyecto_mapa.clases.DataParqueaderos;
import com.example.root.proyecto_mapa.clases.Parqueadero;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ListaParqueaderosFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ListaParqueaderosFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ListaParqueaderosFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private RecyclerView rv;
    private AdapterListaParqueaderos adapter;

    private OnFragmentInteractionListener mListener;

    public ListaParqueaderosFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ListaParqueaderosFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ListaParqueaderosFragment newInstance(String param1, String param2) {
        ListaParqueaderosFragment fragment = new ListaParqueaderosFragment();
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
        View vista = inflater.inflate(R.layout.fragment_lista_parqueaderos, container, false);

        rv = (RecyclerView) vista.findViewById(R.id.recycler_parqueaderos);
        rv.setLayoutManager( new LinearLayoutManager(getContext()));


        adapter = new AdapterListaParqueaderos(DataParqueaderos.getParqueaderos());
        ClickParqueadero();
        rv.setAdapter(adapter);
        return vista;
    }


    private void ClickParqueadero()
    {
        adapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Parqueadero p = DataParqueaderos.getParqueaderos().get(rv.getChildAdapterPosition(view));
                Intent verParqueadero = new Intent(getContext(), ParqueaderoActivity.class);

                Bundle bundle = new Bundle();
                bundle.putSerializable("parqueadero", p);

                verParqueadero.putExtras(bundle);
                startActivity(verParqueadero);

            }
        });
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

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
