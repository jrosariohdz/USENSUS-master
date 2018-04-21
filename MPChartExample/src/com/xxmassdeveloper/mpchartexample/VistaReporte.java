package com.xxmassdeveloper.mpchartexample;





import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import com.github.mikephil.charting.data.LineData;
import com.xxmassdeveloper.mpchartexample.Red.Red;
import com.xxmassdeveloper.mpchartexample.Red.RedService;
import com.xxmassdeveloper.mpchartexample.Sensor.ReportProHoy;
import com.xxmassdeveloper.mpchartexample.Sensor.SensorService;


import java.util.ArrayList;
import java.util.List;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class VistaReporte extends Fragment implements android.app.DatePickerDialog.OnDateSetListener{

    private RadioButton porDia, porSemana, porFecha;
    private Spinner spinner;
    private TextView pro_temp,pro_humedad,pro_sonido,pro_rayos,pro_monoxido;

    public VistaReporte() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.reporte, container, false);
//        Toast.makeText(getContext(), "Dia", Toast.LENGTH_SHORT).show();



        final ArrayList<String> items =  new ArrayList<>();
        items.add("RED local");

        //===================> CONSULTAR REDES <===================
        RestAdapter restadpter = new RestAdapter.Builder().setEndpoint("http://linksdominicana.com/").build();
        RedService servicio = restadpter.create(RedService.class);
        servicio.getRedes(new retrofit.Callback<List<Red>>() {
            @Override
            public void success(List<Red> reds, Response response) {
                for (int i = 0; i < reds.size(); i++) {
                    String red = reds.get(i).getNombre() + " " + reds.get(i).getId_red() + " Servidor";
                    items.add(red);
                    Log.d("REDES", reds.get(i).getNombre());
                }
            }

            @Override
            public void failure(RetrofitError error) {
                Log.d("REDES", "" + error);
                Toast.makeText(getContext(), error + " ERROR-RED", Toast.LENGTH_LONG).show();
            }
        });
        //===================> CONSULTAR REDES  <================| END |===


        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, items);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner = (Spinner)view.findViewById(R.id.spinner2);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    Reportar(1);
                } else if (position == 1) {
                    Reportar(2);
                } else if (position == 2) {
                    Reportar(3);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        pro_temp= (TextView)view.findViewById(R.id.tvPTemperatura);
        pro_humedad= (TextView)view.findViewById(R.id.tvPHumedad);
        pro_sonido= (TextView)view.findViewById(R.id.tvPSonido);
        pro_rayos= (TextView)view.findViewById(R.id.tvPRayosUV);
        pro_monoxido= (TextView)view.findViewById(R.id.tvPMonoxido);

        porDia = (RadioButton)view.findViewById(R.id.por_dia);
        porSemana = (RadioButton)view.findViewById(R.id.por_semana);
        porFecha = (RadioButton)view.findViewById(R.id.por_fecha);

        porDia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Reportar(1);
            }
        });

        porSemana.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(getContext(), "Semana", Toast.LENGTH_SHORT).show();
            }
        });





        return view;
    }
    private void Reportar(int id_modulo){
//            ===================> CONSULTAR REPORTE PROMEDIO HOY <===================
        RestAdapter restadpter = new RestAdapter.Builder().setEndpoint("http://linksdominicana.com/").build();
        SensorService servicio = restadpter.create(SensorService.class);

        servicio.getReporteSensorHoy(id_modulo,new Callback<ReportProHoy>() {
            @Override
            public void success(ReportProHoy reportProHoy, Response response) {
                pro_temp.setText(reportProHoy.getPromedio_temperatura()+"");
                pro_humedad.setText(reportProHoy.getPromedio_humedad()+"");
                pro_sonido.setText(reportProHoy.getPromedio_sonido()+"");
                pro_rayos.setText(reportProHoy.getPromedio_rayos_uv()+"");
                pro_monoxido.setText(reportProHoy.getPromedio_monoxido_carbono()+"");
            }

            @Override
            public void failure(RetrofitError error) {

            }
        });

//      ===================> CONSULTAR REPORTE PROMEDIO HOY <=========| END |===
    }


    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

    }
}
