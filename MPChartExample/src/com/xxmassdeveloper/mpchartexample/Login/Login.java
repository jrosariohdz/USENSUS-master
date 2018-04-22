package com.xxmassdeveloper.mpchartexample.Login;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.xxmassdeveloper.mpchartexample.Cliente.Cliente;
import com.xxmassdeveloper.mpchartexample.Cliente.ClienteService;
import com.xxmassdeveloper.mpchartexample.Global;
import com.xxmassdeveloper.mpchartexample.MainActivity;
import com.xxmassdeveloper.mpchartexample.R;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by NativoLink on 11/2/16.
 */
public class Login extends AppCompatActivity {

    EditText user;
    EditText pass;
    Button iniciar;
    String name_user;
    String pass_user;

    Global servidor = new Global();

    @Override
    public void onBackPressed() {
        super.onBackPressed();

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return false;
    }

    public boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        final ProgressBar pb = (ProgressBar)findViewById(R.id.pbHeaderProgress);
        pb.setVisibility(View.INVISIBLE);


        // SE ELECCIONAN LOS COMPONENTE GRAFICOS DE XML PARA PODER TOMAR EL VALOR INGRESADO POR EL USUARIO
        user = (EditText)findViewById(R.id.etUserLogin);
        pass = (EditText)findViewById(R.id.etPassLogin);
        iniciar = (Button)findViewById(R.id.btnLogin);
        iniciar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //SE DETERMINA SI EL TELEFONO ESTA CONECTADO A INTERNET ANTES DE INTENTAR HACER LA PETICION
                if (isOnline() == false) {
                    Toast.makeText(Login.this, "Sin conexion", Toast.LENGTH_LONG).show();
                }else {

                    //SI SE TIENE INTERNET SE CONFIRMA QUE LOS CAMPOS REQUERIDOS ESTEN LLENOS PARA HACER LA PETICION
                    name_user = user.getText().toString();  //PASAMOS EL TIPO DE DATO DEL COMPONENTE GRAFICO A UN TIPO DE DATO CONOCIDO (STRING,ETC..)
                    pass_user = pass.getText().toString();
                    if ((!name_user.equals("") && !pass_user.equals("")) && (!name_user.equals(" ") && !pass_user.equals(" ")))//COMPROBAMOS QUE NINGUNO ESTA VACIO
                    {

                        pb.setVisibility(View.VISIBLE);//HACEMOS VISIBLE EL PROGRESSBAR PARA INDICAR QUE SE ESTA CARGANDO

                        //SE SOLICITA UN ADATER EL CUAL ES EL ENCARGADO DE LA CONEXION DESDE LA APLICACION
                        RestAdapter restadpter = servidor.getRestadpter();
                        ClienteService servicio = restadpter.create(ClienteService.class);//SE INICIA UN NUEVO SERVICIO EN ESTE CASO DE TIPO CLIENTE


                       //LE INDICAMOS AL SERVICIO QUE FUNCION NECESITAMOS SEGUN LA PETICION QUE NECESITEMOS
                        servicio.postLogin(name_user, pass_user, new Callback<Cliente>() {
                        @Override
                        public void success(Cliente cliente, Response response) {
                            Log.v("USENSUS","=====| USENSUS |==========>"+response.getStatus()+"<--------");
                            if (cliente.isLogin().equals("true")) { //COMPROBAMOS QUE EL SERVICO NOS RETORNA QUE SI EXITE ESTE CLIENTE
//                                Toast.makeText(Login.this, cliente.getApellido(), Toast.LENGTH_LONG).show();


                                //CLASIFICAMOS  EL USUARIO RETORNADO PARA PODER DETERMINAR CUAL INTERFAZ MOSTRAR
                                if (cliente.getUsertype() == 1) {

//                                Toast.makeText(Login.this, cliente.getApellido() + " ", Toast.LENGTH_LONG).show();
                                    Intent intent2 = new Intent(Login.this, MainActivity.class);
                                    String idCliente = String.valueOf(cliente.getId_cliente());
                                    intent2.putExtra("id_cliente", idCliente);
                                    intent2.putExtra("nombre", cliente.getNombre());
                                    String tel = String.valueOf(cliente.getTelefono());
                                    intent2.putExtra("telefono", tel);
                                    intent2.putExtra("correo", cliente.getCorreo());
                                    intent2.putExtra("apellido", cliente.getApellido());
                                    intent2.putExtra("tipo_usuario", "1");
                                    pb.setVisibility(View.GONE);
                                    startActivity(intent2);
                                    Log.d("===============>",idCliente+"<--------");
                                    overridePendingTransition(R.anim.move_right_in_activity, R.anim.move_left_out_activity);
                                }
                                if (cliente.getUsertype() == 2) {
                                    Intent intent = new Intent(Login.this, MainActivity.class);
//                                Toast.makeText(Login.this, cliente.getId_cliente() + " ", Toast.LENGTH_LONG).show();
                                    String idCliente = String.valueOf(cliente.getId_cliente());
                                    intent.putExtra("id_maestro", idCliente);
                                    intent.putExtra("id_cliente", idCliente);
                                    intent.putExtra("nombre", cliente.getNombre());
                                    intent.putExtra("correo", cliente.getCorreo());
                                    intent.putExtra("apellido", cliente.getApellido());
                                    intent.putExtra("tipo_usuario", "3");
                                    pb.setVisibility(View.GONE);
                                    Log.d("===============>", idCliente + "<--------");
                                    startActivity(intent);
                                    overridePendingTransition(R.anim.move_right_in_activity, R.anim.move_left_out_activity);
                                }
                                Toast.makeText(Login.this, "Bienvenid@ "+cliente.getNombre(), Toast.LENGTH_LONG).show();

                            }else{
                                pb.setVisibility(View.INVISIBLE);   //OCULTAMOS EL PROGRESSBAR CUANDO TERMINA LA PETICION
                                Toast.makeText(Login.this, "Acceso No valido", Toast.LENGTH_LONG).show();
                            }

                        }

                        @Override
                        public void failure(RetrofitError error) {
                            Toast.makeText(Login.this, "Error en Servidor"+error, Toast.LENGTH_LONG).show();
                            pb.setVisibility(View.INVISIBLE);
                        }
                    });
                }else{
                        Toast.makeText(Login.this, "Campos vacíos", Toast.LENGTH_LONG).show();
                        pb.setVisibility(View.INVISIBLE);
                    }
                }
            }
        });


    }
}
