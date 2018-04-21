package com.xxmassdeveloper.mpchartexample.Cliente;


import retrofit.Callback;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.Headers;
import retrofit.http.POST;

/**
 * Created by NativoLink on 15/12/15.
 */
public interface ClienteService {
//    @Headers("Cache-Control: max-age=1")
//    @GET("/WebSites/Meexin/Cliente/ListCliente.php")
//    void getClientes(Callback<List<Cliente>> callback);
//    void getFeed(@Path("user") String user,Callback<Cliente> response);

    @FormUrlEncoded
    @POST("/USENSUS/Login/login.php")
    public void postLogin(@Field("username") String username, @Field("password") String password, Callback<Cliente> callback);

    @Headers("Cache-Control: max-age=1")
    @FormUrlEncoded
    @POST("/ActualizarCuenta.php")
    public void ActualizarCuenta(@Field("id_cliente") int id_cliente,
                                 @Field("clave_nueva") String clave_nueva,
                                 @Field("clave_vieja") String clave_vieja, Callback<String> callback);

}
