package com.example.fynd_proyecto;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Menu_parada extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_parada);
    }
    public void volver_menu(View v){
        Intent volver_menu=new Intent(Menu_parada.this, Menu.class);
        startActivity(volver_menu);
    }
    public void abrir_lineas(View v){
        Intent abrir_lineas=new Intent(Menu_parada.this, Menu_linea.class);
        startActivity(abrir_lineas);
    }

}