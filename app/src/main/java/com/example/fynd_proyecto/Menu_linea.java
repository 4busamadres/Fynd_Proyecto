package com.example.fynd_proyecto;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Menu_linea extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_linea);
    }
    public void volver_menu(View v) {
        Intent volver_menu = new Intent(Menu_linea.this, Menu.class);
        startActivity(volver_menu);
    }
    public void abrir_paradas(View v){
        Intent abrir_paradas = new Intent(Menu_linea.this, Menu_parada.class);
        startActivity(abrir_paradas);
    }

}