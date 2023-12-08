package com.example.fynd_proyecto;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.fynd_proyecto.Models.Linea;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class Menu_linea extends AppCompatActivity {
    private FirebaseFirestore db;
    private ListView lv_listar;
    private ArrayList<Linea> lista_linea;
    private EditText txt_lineas_buscar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_linea);

        FirebaseApp.initializeApp(this);
        db = FirebaseFirestore.getInstance();

        lv_listar = findViewById(R.id.lv_listar_lineas);
        txt_lineas_buscar = findViewById(R.id.txt_lineas_buscar);
        lista_linea = new ArrayList<>();

        cargar_lineas();

        Button btn_buscar = findViewById(R.id.btn_buscar_linea);
        btn_buscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buscarLineas();
            }
        });

        // Agregar un listener al EditText para realizar búsqueda en tiempo real
        txt_lineas_buscar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // No se necesita implementar en este caso
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // No se necesita implementar en este caso
            }

            @Override
            public void afterTextChanged(Editable s) {
                // Realizar búsqueda en tiempo real mientras se escribe
                buscarLineas();
            }
        });
    }

    public void cargar_lineas() {
        ArrayList<String> lista_nombre_lineas = new ArrayList<>();

        db.collection("Linea").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        String nombre_linea = document.getData().get("Nombre_linea").toString();
                        String horario = document.getData().get("Horario").toString();

                        Linea LI = new Linea(nombre_linea, horario);
                        lista_linea.add(LI);

                        lista_nombre_lineas.add(nombre_linea);
                    }

                    ArrayAdapter<String> adapter = new ArrayAdapter<>(Menu_linea.this, android.R.layout.simple_list_item_1, lista_nombre_lineas);
                    lv_listar.setAdapter(adapter);

                    // Agregar el clic de los elementos de la lista
                    lv_listar.setOnItemClickListener((parent, view, position, id) -> {
                        mostrarOpciones(position);
                    });

                } else {
                    mostrarAlerta("Error", "No se pudieron cargar las líneas.");
                }
            }
        });
    }

    private void mostrarOpciones(int position) {
        Linea lineaSeleccionada = lista_linea.get(position);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Opciones")
                .setItems(new CharSequence[]{"Ver Horario", "Cerrar"}, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                // Ver Horario
                                verHorario(lineaSeleccionada);
                                break;
                            case 1:
                                // Cerrar
                                dialog.dismiss();
                                break;
                        }
                    }
                });

        builder.create().show();
    }

    private void verHorario(Linea linea) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Horario de la línea");

        // Aquí agregas el contenido del cuadro de diálogo
        String mensaje = "El horario de la línea es:\n" + linea.getHorario();
        builder.setMessage(mensaje);

        // Botón para cerrar el cuadro de diálogo
        builder.setPositiveButton("Cerrar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss(); // Cierra el cuadro de diálogo
            }
        });

        builder.create().show();
    }

    private void buscarLineas() {
        String textoBusqueda = txt_lineas_buscar.getText().toString().toLowerCase();

        ArrayList<Linea> lineasEncontradas = new ArrayList<>();
        ArrayList<String> nombresEncontrados = new ArrayList<>();

        for (Linea linea : lista_linea) {
            if (linea.getNombre_linea().toLowerCase().contains(textoBusqueda)) {
                lineasEncontradas.add(linea);
                nombresEncontrados.add(linea.getNombre_linea());
            }
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(Menu_linea.this, android.R.layout.simple_list_item_1, nombresEncontrados);
        lv_listar.setAdapter(adapter);

        lv_listar.setOnItemClickListener((parent, view, position, id) -> {
            mostrarOpciones(position);
        });

        // Mostrar mensaje en Toast dependiendo de si se encontraron resultados
        if (lineasEncontradas.isEmpty()) {
            showToast("No se encontraron líneas con ese nombre.");
        } else {
            showToast("Se encontraron líneas con ese nombre.");
        }
    }

    // Método para mostrar un Toast
    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }


    public void volver_menu(View v) {
        Intent volver_menu = new Intent(Menu_linea.this, Menu.class);
        startActivity(volver_menu);
    }

    public void abrir_paradas(View v) {
        Intent abrir_paradas = new Intent(Menu_linea.this, Menu_parada.class);
        startActivity(abrir_paradas);
    }

    public void mostrarAlerta(String titulo, String mensaje) {
        AlertDialog.Builder alerta = new AlertDialog.Builder(this);
        alerta.setTitle(titulo);
        alerta.setMessage(mensaje);
        alerta.setPositiveButton("Aceptar", null);
        AlertDialog dialogo = alerta.create();
        dialogo.show();
    }
}
