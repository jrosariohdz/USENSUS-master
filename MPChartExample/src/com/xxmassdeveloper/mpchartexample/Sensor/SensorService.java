package com.xxmassdeveloper.mpchartexample.Sensor;


import java.util.List;

import retrofit.Callback;
import retrofit.http.Field;
import retrofit.http.GET;
import retrofit.http.Query;

/**
 * Created by NativoLink on 16/11/16.
 */
public interface SensorService {
    @GET("/USENSUS/Sensor/getSensor.php")
    void getSensor(@Query("id_red") int id_red, Callback<Sensor> callback);

    @GET("/USENSUS/Sensor/getSensores.php")
    void getSensores(Callback<List<Sensor>> callback);

    @GET("/USENSUS/Sensor/getReporteSensores.php")
    void getReporteSensorHoy(@Query("id_modulo") int id_modulo,Callback<ReportProHoy> callback);
}
