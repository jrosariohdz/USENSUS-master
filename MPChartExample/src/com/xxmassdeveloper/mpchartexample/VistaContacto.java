package com.xxmassdeveloper.mpchartexample;


import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.listener.ChartTouchListener;
import com.github.mikephil.charting.listener.OnChartGestureListener;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.xxmassdeveloper.mpchartexample.Modulo.ModuloService;
import com.xxmassdeveloper.mpchartexample.Red.Red;
import com.xxmassdeveloper.mpchartexample.Red.RedService;
import com.xxmassdeveloper.mpchartexample.Sensor.Sensor;
import com.xxmassdeveloper.mpchartexample.Sensor.SensorService;
import com.xxmassdeveloper.mpchartexample.custom.MyMarkerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.security.auth.callback.Callback;

import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by NativoLink on 1/3/16.
 */
public class VistaContacto extends Fragment implements SeekBar.OnSeekBarChangeListener,
        OnChartGestureListener, OnChartValueSelectedListener {

    private OnFragmentInteractionListener mListener;

    private ArrayList<ILineDataSet> lineDataSets1;
    private ArrayList<ILineDataSet> lineDataSets2;
    private ArrayList<ILineDataSet> lineDataSets3;
    private LineChart mChart;
    private Spinner spinner;

    public VistaContacto() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.activity_linechart, container, false);

        //<editor-fold desc="Setting Chart">
        mChart = (LineChart) view.findViewById(R.id.chart1);
        mChart.setOnChartGestureListener(this);
        mChart.setOnChartValueSelectedListener(this);
        mChart.setDrawGridBackground(false);

        MyMarkerView mv = new MyMarkerView(getContext(), R.layout.custom_marker_view);
        mv.setChartView(mChart); // For bounds control
        mChart.setMarker(mv); // Set the marker to the chart


        YAxis leftAxis = mChart.getAxisLeft();
        leftAxis.removeAllLimitLines(); // reset all limit lines to avoid overlapping lines
        leftAxis.setAxisMinimum(0f);
        leftAxis.setAxisMaximum(100f);
        leftAxis.enableGridDashedLine(10f, 10f, 0f);
        leftAxis.setDrawZeroLine(false);

        leftAxis.setDrawLimitLinesBehindData(true);

        mChart.getAxisRight().setEnabled(false);
        mChart.animateX(2500);

        mChart.getXAxis().setAxisMaximum(13);
        //</editor-fold>

        lineDataSets1 = getRandomDataSets();
        lineDataSets2 = getRandomDataSets();
        lineDataSets3 = getRandomDataSets();

        mChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {
                if (!mChart.isFullyZoomedOut()){
                    String point = "X: " + String.valueOf(e.getX()) + "\nY: " + String.valueOf(e.getY());
                    Toast.makeText(getContext(), point, Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onNothingSelected() {

            }
        });

        //mChart.setVisibleXRangeMaximum(65f);

        final ArrayList<String> items =  new ArrayList<>();
        items.add("RED local");

        //===================> CONSULTAR REDES <===================
        RestAdapter restadpter = new RestAdapter.Builder().setEndpoint("http://10.0.0.9/").build();
        RedService servicio = restadpter.create(RedService.class);
        servicio.getRedes(new retrofit.Callback<List<Red>>() {
            @Override
            public void success(List<Red> reds, Response response) {
                for( int i = 0 ; i < reds.size() ; i++ ){
                    String red = reds.get( i ).getNombre()+" "+reds.get( i ).getId_red()+" Servidor";
                    items.add(red);
                    Log.d("REDES",reds.get( i ).getNombre());
                }
            }

            @Override
            public void failure(RetrofitError error) {
                Log.d("REDES",""+error);
                Toast.makeText(getContext(), error+" ERROR-RED", Toast.LENGTH_LONG).show();
            }
        });
        //===================> CONSULTAR REDES  <================| END |===

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, items);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner = (Spinner)view.findViewById(R.id.spinner);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0){
                    mChart.setData(new LineData(lineDataSets1));
                    mChart.notifyDataSetChanged();
                    mChart.invalidate();
                } else if (position == 1){
                    mChart.setData(new LineData(lineDataSets2));
                    mChart.notifyDataSetChanged();
                    mChart.invalidate();
                } else if (position == 2){
                    mChart.setData(new LineData(lineDataSets3));
                    mChart.notifyDataSetChanged();
                    mChart.invalidate();
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        ImageButton humedad = (ImageButton) view.findViewById(R.id.btnHumedad);
        humedad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                lineDataSets1 = getRandomDataSets()
                mChart.setData(new LineData(lineDataSets1.get(0)));
                mChart.notifyDataSetChanged();
                mChart.invalidate();
            }
        });

        ImageButton tempBT = (ImageButton)view.findViewById(R.id.btnTemperatura);
        tempBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mChart.setData(new LineData(lineDataSets1.get(1)));
                mChart.notifyDataSetChanged();
                mChart.invalidate();
            }
        });

        ImageButton sonidoIB = (ImageButton)view.findViewById(R.id.btnSonido);
        sonidoIB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mChart.setData(new LineData(lineDataSets1.get(2)));
                mChart.notifyDataSetChanged();
                mChart.invalidate();
            }
        });

        ImageButton CO2IB = (ImageButton)view.findViewById(R.id.btnMonoxido);
        CO2IB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mChart.setData(new LineData(lineDataSets1.get(3)));
                mChart.notifyDataSetChanged();
                mChart.invalidate();
            }
        });
        return view; 

    }

    private ArrayList<ILineDataSet> getRandomDataSets(){

        final ArrayList<Entry> yAxes1 = new ArrayList<>();
        final ArrayList<Entry> yAxes2 = new ArrayList<>();
        final ArrayList<Entry> yAxes3 = new ArrayList<>();
        final ArrayList<Entry> yAxes4 = new ArrayList<>();
        final Random random = new Random();

        // ===================> CONSULTAR SENSORES <===================
        RestAdapter restadpter = new RestAdapter.Builder().setEndpoint("http://10.0.0.9/").build();
        SensorService servicio = restadpter.create(SensorService.class);

        servicio.getSensores(new retrofit.Callback<List<Sensor>>() {
            @Override
            public void success(List<Sensor> sensors, Response response) {
                for( int i = 0 ; i < sensors.size() ; i++ ){         // RECORREMOS EL LISTADO
                    float temperatura = sensors.get(i).getTemperatura(); // ESTA VARIABLE RECIBE DE DB
                    float humedad = sensors.get(i).getHumedad(); // ESTA VARIABLE RECIBE DE DB
                    float uv = sensors.get(i).getRayos_uv(); // ESTA VARIABLE RECIBE DE DB
                    float co2 = sensors.get(i).getMonoxido_carbono(); // ESTA VARIABLE RECIBE DE DB
                    yAxes2.add(new Entry(i, temperatura));
                    yAxes1.add(new Entry(i, humedad));
                    yAxes3.add(new Entry(i, uv));
                    yAxes4.add(new Entry(i, co2));
                    Log.v("SENSORES","LLENANDO DATOS ");
                }
                Toast.makeText(getContext(), "LLENANDO DATOS CON SENSORES", Toast.LENGTH_LONG).show();
            }

            @Override
            public void failure(RetrofitError error) {
                Log.d("SENSORES",""+error);
                Toast.makeText(getContext(), error+" ERROR-SENSOR", Toast.LENGTH_LONG).show();
            }
        });
//        ===================> CONSULTAR SENSORES  <================| END |===

        for (int i=0;i<2;i++){
            yAxes1.add(new Entry(i, random.nextFloat()));
            yAxes2.add(new Entry(i, random.nextFloat()));
            yAxes3.add(new Entry(i, random.nextFloat()));
            yAxes4.add(new Entry(i, random.nextFloat()));
        }

        LineDataSet lineDataSet1 = new LineDataSet(yAxes1, "Humedad");
        lineDataSet1.setColor(Color.BLUE);
        lineDataSet1.setDrawCircles(false);

        LineDataSet lineDataSet2 = new LineDataSet(yAxes2, "Temperatura");
        lineDataSet2.setColor(Color.RED);
        lineDataSet2.setDrawCircles(false);

        LineDataSet lineDataSet3 = new LineDataSet(yAxes3, "Rayos UV");
        lineDataSet3.setColor(Color.MAGENTA);
        lineDataSet3.setDrawCircles(false);

        LineDataSet lineDataSet4 = new LineDataSet(yAxes3, "Monóxido de Carbono");
        lineDataSet4.setColor(Color.GREEN);
        lineDataSet4.setDrawCircles(false);

        ArrayList<ILineDataSet> lineDataSets = new ArrayList<>();
        lineDataSets.add(lineDataSet1);
        lineDataSets.add(lineDataSet2);
        lineDataSets.add(lineDataSet3);
        lineDataSets.add(lineDataSet4);
        return lineDataSets;
    }

    //<editor-fold desc="Auto Generated">
    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onChartGestureStart(MotionEvent me, ChartTouchListener.ChartGesture lastPerformedGesture) {

    }

    @Override
    public void onChartGestureEnd(MotionEvent me, ChartTouchListener.ChartGesture lastPerformedGesture) {

    }

    @Override
    public void onChartLongPressed(MotionEvent me) {

    }

    @Override
    public void onChartDoubleTapped(MotionEvent me) {

    }

    @Override
    public void onChartSingleTapped(MotionEvent me) {

    }

    @Override
    public void onChartFling(MotionEvent me1, MotionEvent me2, float velocityX, float velocityY) {

    }

    @Override
    public void onChartScale(MotionEvent me, float scaleX, float scaleY) {

    }

    @Override
    public void onChartTranslate(MotionEvent me, float dX, float dY) {

    }

    @Override
    public void onValueSelected(Entry e, Highlight h) {

    }

    @Override
    public void onNothingSelected() {

    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }
    //</editor-fold>

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

}