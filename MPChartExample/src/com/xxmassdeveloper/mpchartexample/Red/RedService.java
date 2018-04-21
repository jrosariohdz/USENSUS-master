package com.xxmassdeveloper.mpchartexample.Red;


import com.xxmassdeveloper.mpchartexample.Modulo.Modulo;

import java.util.List;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Query;

/**
 * Created by NativoLink on 15/11/16.
 */
public interface RedService {

    @GET("/USENSUS/Red/getRed.php")
    void getRede(@Query("id_red") int id_red,Callback<Modulo> callback);

    @GET("/USENSUS/Red/getRedes.php")
    void getRedes(Callback<List<Red>> callback);

}
