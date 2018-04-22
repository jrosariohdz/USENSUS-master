package com.xxmassdeveloper.mpchartexample;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.NavigationView;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.widget.TextView;

import com.xxmassdeveloper.mpchartexample.Login.Login;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);// SE ASOCIA ESTE ARCHIVO JAVA A UNA INTERFAZ GRAFICA

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Context context =getApplicationContext(); // ES EL ESTADO DE LA APLICACION ( VARIABLES QUE ESTA MANEJANDO ACTUALMENTE )

        // VARIABLE PARA SELECIONAR LOS COMPONENTES GRAFICOS DE XML PARA MANEJARLOS CON JAVA
        TextView nav_h_nombre = (TextView)findViewById(R.id.nav_header_nombre);
        TextView nav_h_correo = (TextView)findViewById(R.id.nav_header_correo);





//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "algo", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
        getSupportActionBar().setTitle("Graphic");// CAMBIAR EL TITULO DE LA BARRA SUPERIOR
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout); // BARRA SUPERIO DE LA APLICACION
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        drawer.setBackgroundColor(Color.parseColor("#77FFFFFF"));
        toggle.syncState();// SE AGREGA LA OPCION PARA UN MENU DESPLEGABLE

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        Fragment vista = new VistaContacto(); // INICIA LA VISTA DE LOS CURSOS AL INICIAR SESION EL ESTUDIANTE
        Bundle bundle = new Bundle(); // VAERIABLE PARA SOCIAR PARAMETROS A LAS VISTA (( DISTINTO DE LAS ACTIVIDADES ))

        //PARAMS RECIBIDOS POR LOGIN
        String id_cliente = getIntent().getStringExtra("id_cliente");
        String nombre = getIntent().getStringExtra("nombre");
        String apellido = getIntent().getStringExtra("apellido");
        String correo = getIntent().getStringExtra("correo");
        String id_usuario = getIntent().getStringExtra("id_usuario");
        String tipo_usuario = getIntent().getStringExtra("tipo_usuario");

        //PARAMS PARA ENVIAR A FRAGMENTS
        bundle.putString("id_cliente", id_cliente);
        bundle.putString("nombre", nombre);
        bundle.putString("apellido", apellido);
        bundle.putString("correo", correo);
        bundle.putString("tipo_usuario", tipo_usuario);
        vista.setArguments(bundle);

        nav_h_nombre.setText(nombre);
        nav_h_correo.setText(correo);

        FragmentTransaction transaction= getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.f_main, vista);
        transaction.addToBackStack(null);
        transaction.commit();





    }

    // DETERMINA CUANDO EL MENU ESTA VISIBLE O OCULTO
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return false;
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.main, menu);
//        return true;
//    }

//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }

    //CONTROLADOR DE MENU LATERAL
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here
        int id = item.getItemId();

        //OPCIONES DEL MENU LATERAL ,esta sustituyen el fragmento principal
        Fragment vista = null;
        boolean trans = false;
        if (id == R.id.nav_cursos) {
            FragmentManager fm = getFragmentManager();

            fm.beginTransaction()
                    .replace(R.id.f_main, new VistaMap())
                    .addToBackStack(null)
                    .commit();
            item.setCheckable(true);
//            trans= true;
            overridePendingTransition(R.anim.move_right_in_activity, R.anim.move_left_out_activity);
        } else if (id == R.id.nav_settings) {
            vista = new VistaContacto();
            trans= true;
            overridePendingTransition(R.anim.move_right_in_activity, R.anim.move_left_out_activity);
        }else if (id == R.id.nav_report) {
            vista = new VistaReporte();
            trans= true;
            overridePendingTransition(R.anim.move_right_in_activity, R.anim.move_left_out_activity);
        }else if (id == R.id.nav_exit) {
            Intent exit = new Intent( MainActivity.this , Login.class);
            startActivity(exit);
//            vista = new VistaContacto();
//            trans= true;
//            overridePendingTransition(R.anim.move_right_in_activity, R.anim.move_left_out_activity);
        }


        if(trans){
//            getSupportFragmentManager().beginTransaction().replace(R.id.f_main,vista).commit();
//            getSupportFragmentManager().beginTransaction().remove(getSupportFragmentManager().findFragmentById(R.id.f_main));
            Bundle bundle = new Bundle();
            //PARAMS RECIBIDOS POR LOGIN
            String id_cliente = getIntent().getStringExtra("id_cliente");
            String nombre = getIntent().getStringExtra("nombre");
            String apellido = getIntent().getStringExtra("apellido");
            String correo = getIntent().getStringExtra("correo");
            String id_usuario = getIntent().getStringExtra("id_usuario");
            String tipo_usuario = getIntent().getStringExtra("tipo_usuario");


            //PARAMS PARA ENVIAR A FRAGMENTS
            bundle.putString("id_cliente", id_cliente);
            bundle.putString("nombre", nombre);
            bundle.putString("apellido", apellido);
            bundle.putString("correo", correo);
            bundle.putString("tipo_usuario", tipo_usuario);
            vista.setArguments(bundle);

            FragmentTransaction transaction= getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.f_main, vista);
            transaction.addToBackStack(null);
            transaction.detach(vista);
            transaction.attach(vista);
            transaction.commit();
            item.setCheckable(true);
            getSupportActionBar().setTitle(item.getTitle());
        }
        // COMPONENTE QUE MANEJA EL MENU LATERAL
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


}
