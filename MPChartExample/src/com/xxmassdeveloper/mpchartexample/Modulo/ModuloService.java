package com.xxmassdeveloper.mpchartexample.Modulo;

import com.xxmassdeveloper.mpchartexample.Red.Red;

import java.util.List;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Query;

/**
 * Created by NativoLink on 15/11/16.
 */
public interface ModuloService {
    @GET("/USENSUS/Modulo/getModulo.php")
    void getModulo(@Query("id_modulo") int id_modulo,Callback<Modulo> callback);

    @GET("/USENSUS/Modulo/getModulos.php")
    void getModulos(@Query("id_red") int id_red,Callback<List<Modulo>> callback);
}
