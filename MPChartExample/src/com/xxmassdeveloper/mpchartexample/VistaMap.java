package com.xxmassdeveloper.mpchartexample;

import android.annotation.TargetApi;
import android.app.Fragment;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;


import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.xxmassdeveloper.mpchartexample.Modulo.AdapterModulos;
import com.xxmassdeveloper.mpchartexample.Modulo.Modulo;
import com.xxmassdeveloper.mpchartexample.Modulo.ModuloService;

import java.util.List;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class VistaMap extends Fragment implements OnMapReadyCallback {

    private GoogleMap mMap;
    public VistaMap() {

    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v =  inflater.inflate(R.layout.activity_maps, container, false);

        //        ============| LISTADO DE PEDIDOS REALIZADOS |==============
        final ListView lvresult;
        lvresult = (ListView)v.findViewById(R.id.lvModulos);


        RestAdapter restadpter = new RestAdapter.Builder().setEndpoint("http://linksdominicana.com").build();
        ModuloService servicio = restadpter.create(ModuloService.class);

        servicio.getModulos(1, new Callback<List<Modulo>>() {
            @Override
            public void success(List<Modulo> modulos, Response response) {
                ListAdapter listAdapter = null;
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                    listAdapter = new AdapterModulos(getContext(), modulos);
                }
                lvresult.setAdapter(listAdapter);
            }

            @TargetApi(Build.VERSION_CODES.M)
            @Override
            public void failure(RetrofitError error) {
                Log.e("Error", error.getMessage());
                Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
        return v;
    }

    @TargetApi(Build.VERSION_CODES.M)
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        MapFragment fragment = (MapFragment)getChildFragmentManager().findFragmentById(R.id.map);
        fragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(19.444026, -70.685414);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));

        mMap.addMarker(new MarkerOptions()
                .position(new LatLng(19.441578, -70.684308))
                .title("F*ck Society"));
    }
}
