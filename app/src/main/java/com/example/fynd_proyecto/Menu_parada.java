package com.example.fynd_proyecto;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.fynd_proyecto.Models.Parada;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;


public class Menu_parada extends AppCompatActivity {
    private FirebaseFirestore db;
    private ListView lv_listParada;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FirebaseApp.initializeApp(this);
        db=FirebaseFirestore.getInstance();

        lv_listParada=findViewById(R.id.lv_paradas);
        cargarParadas();
    }
    public void cargarParadas()
    {
        ArrayList<Parada>listaParada=new ArrayList<>();
        ArrayList<String>listaNomParada=new ArrayList<>();
        db.collection("Parada").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful())
                {
                    for (QueryDocumentSnapshot document : task.getResult())
                    {
                        String NomParada = document.getData().get("nomParada").toString();

                        Parada pa=new Parada(NomParada);
                        listaParada.add(pa);
                        listaNomParada.add(NomParada);
                    }
                    ArrayAdapter<String>adapter=new ArrayAdapter<>(Menu_parada.this, android.R.layout.simple_list_item_1,listaNomParada);
                    lv_listParada.setAdapter(adapter);
                }else {
                    mostrarAlerta("Error","No se puede cargar la lista de paradas");
                }

            }
        });
    }
    public void mostrarAlerta(String titulo, String mensaje) {
        AlertDialog.Builder alerta = new AlertDialog.Builder(this);
        alerta.setTitle(titulo);
        alerta.setMessage(mensaje);
        alerta.setPositiveButton("Aceptar", null);
        AlertDialog dialogo = alerta.create();
        dialogo.show();
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