package com.betamobilemonkey.preciodeleuro.Activities;

import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.BaseAdapter;

import com.betamobilemonkey.preciodeleuro.Entidades.Cotizacion;
import com.betamobilemonkey.preciodeleuro.Fragments.CalculadoraFragment;
import com.betamobilemonkey.preciodeleuro.Fragments.ContactoFragment;
import com.betamobilemonkey.preciodeleuro.Fragments.CotizacionesFragment;
import com.betamobilemonkey.preciodeleuro.R;

import java.math.BigDecimal;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setToolbar();

        // Recupero componentes drawer_layout y navview del xml activitymain
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) findViewById(R.id.navview);

        setFragmentByDefault();

        // Creo Evento para capturar cuando se abre menu o se cierra
        drawerLayout.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(@NonNull View view, float v) {
            }
            @Override
            public void onDrawerOpened(@NonNull View view) {
            }
            @Override
            public void onDrawerClosed(@NonNull View view) {
            }
            @Override
            public void onDrawerStateChanged(int i) {
            }
        });

        // Capturo click en item del menu con este evento, y agrego logica segun que click es
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                boolean fragmentTransaction = false;
                Fragment fragment = null;

                switch (menuItem.getItemId()){

                    case R.id.menu_opcion_1:
                        fragment = new CotizacionesFragment();
                        fragmentTransaction = true;
                        break;

                    case R.id.menu_opcion_2:
                        fragment = new CalculadoraFragment();
                        fragmentTransaction = true;
                        break;

                    case R.id.menu_contacto:
                        fragment = new ContactoFragment();
                        fragmentTransaction = true;
                        break;
                }

                // Si tiene item seleccionado
                if (fragmentTransaction){

                    setFragmentByDefault();
                    //Trae manejador de Fragments e inicia transaccion y pasa fragmento a fragment del activitymain xml
                    getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, fragment).commit();
                    //Esto se hace para que se vea activo el item seleccionado (con gris)
                    menuItem.setChecked(true);
                    //A la barra le paso el nombre del item
                    getSupportActionBar().setTitle(menuItem.getTitle());
                    //Cierra menu lateral
                    drawerLayout.closeDrawers();
                }

                return true;
            }
        });

    }

    //Busco barra de aplicacion y la seteo
    private void setToolbar(){
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //Agrego Icono de menu (inyeccion nativa, no se infla)
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_home);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    // Fragmento que aparece por default al iniciar app
    private void setFragmentByDefault(){
        getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, new CotizacionesFragment()).commit();

        MenuItem item = navigationView.getMenu().getItem(0);
        // Dejo item chequeado para cuando abra el menu
        item.setChecked(true);
        getSupportActionBar().setTitle(item.getTitle());
    }


    // Al clickear cualquier item de pantalla
    @Override
    public boolean onOptionsItemSelected(MenuItem item){

        switch(item.getItemId()){
            case android.R.id.home:
                //Abre menu lateral
                drawerLayout.openDrawer(GravityCompat.START);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
