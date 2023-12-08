package com.example.fynd_proyecto;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

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
    private ArrayList<Parada> listaParada;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_parada);

        FirebaseApp.initializeApp(this);
        db = FirebaseFirestore.getInstance();

        lv_listParada = findViewById(R.id.lv_paradas);
        listaParada = new ArrayList<>();

        cargarParadas();

        EditText txt_parada_destino = findViewById(R.id.txt_parada_Destino);
        Button btn_buscar_parada = findViewById(R.id.btn_parada_buscar);

        btn_buscar_parada.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buscarParadas(txt_parada_destino.getText().toString());
            }
        });
    }

    public void cargarParadas() {
        ArrayList<String> listaNomParada = new ArrayList<>();

        db.collection("Parada").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        String nomParada = document.getData().get("nomParada").toString();

                        Parada pa = new Parada(nomParada);
                        listaParada.add(pa);
                        listaNomParada.add(nomParada);
                    }
                    ArrayAdapter<String> adapter = new ArrayAdapter<>(Menu_parada.this, android.R.layout.simple_list_item_1, listaNomParada);
                    lv_listParada.setAdapter(adapter);
                } else {
                    mostrarAlerta("Error", "No se puede cargar la lista de paradas");
                }
            }
        });
    }

    private void buscarParadas(String textoBusqueda) {
        ArrayList<Parada> paradasEncontradas = new ArrayList<>();
        ArrayList<String> nombresEncontrados = new ArrayList<>();

        for (Parada parada : listaParada) {
            if (parada.getNombre().toLowerCase().contains(textoBusqueda.toLowerCase())) {
                paradasEncontradas.add(parada);
                nombresEncontrados.add(parada.getNombre());
            }
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(Menu_parada.this, android.R.layout.simple_list_item_1, nombresEncontrados);
        lv_listParada.setAdapter(adapter);

        // Mostrar mensaje en Toast dependiendo de si se encontraron resultados
        if (paradasEncontradas.isEmpty()) {
            showToast("No se encontraron paradas con ese nombre.");
        } else {
            showToast("Se encontraron paradas con ese nombre.");
        }
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    public void mostrarAlerta(String titulo, String mensaje) {
        AlertDialog.Builder alerta = new AlertDialog.Builder(this);
        alerta.setTitle(titulo);
        alerta.setMessage(mensaje);
        alerta.setPositiveButton("Aceptar", null);
        AlertDialog dialogo = alerta.create();
        dialogo.show();
    }

    public void volver_menu(View v) {
        Intent volver_menu = new Intent(Menu_parada.this, Menu.class);
        startActivity(volver_menu);
    }

    public void abrir_lineas(View v) {
        Intent abrir_lineas = new Intent(Menu_parada.this, Menu_linea.class);
        startActivity(abrir_lineas);
    }
}
