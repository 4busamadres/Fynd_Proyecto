package com.example.fynd_proyecto;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class editar_usuario extends AppCompatActivity {

    private EditText txtEditarNombre, txtEditarApellido, txtEditarCorreo, txtEditarClave, txtEditarFecha;
    private Button btnEditarGuardar;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_usuario);

        // Inicializar Firebase Firestore
        db = FirebaseFirestore.getInstance();

        // Vincular elementos de la interfaz de usuario
        txtEditarNombre = findViewById(R.id.txt_editar_nombre);
        txtEditarApellido = findViewById(R.id.txt_editar_apellido);
        txtEditarCorreo = findViewById(R.id.txt_editar_correo);
        txtEditarClave = findViewById(R.id.txt_editar_clave);
        txtEditarFecha = findViewById(R.id.txt_editar_fecha);
        btnEditarGuardar = findViewById(R.id.btn_registro_guardar);

        // Establecer OnClickListener para el botón de edición
        btnEditarGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Obtener los nuevos valores editados
                String nuevoNombre = txtEditarNombre.getText().toString();
                String nuevoApellido = txtEditarApellido.getText().toString();
                String nuevoCorreo = txtEditarCorreo.getText().toString();
                String nuevaClave = txtEditarClave.getText().toString();
                String nuevaFecha = txtEditarFecha.getText().toString();

                // Crear un mapa con los nuevos valores
                Map<String, Object> actualizacion = new HashMap<>();
                actualizacion.put("nombre", nuevoNombre);
                actualizacion.put("apellido", nuevoApellido);
                actualizacion.put("correo", nuevoCorreo);
                actualizacion.put("clave", nuevaClave);
                actualizacion.put("fecha", nuevaFecha);

                // Actualizar en la base de datos utilizando el correo como ID del documento
                db.collection("Usuario").document(nuevoCorreo).set(actualizacion)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    // Éxito al actualizar
                                    Toast.makeText(editar_usuario.this, "Usuario editado con éxito", Toast.LENGTH_SHORT).show();
                                } else {
                                    // Error al actualizar
                                    Toast.makeText(editar_usuario.this, "Error al editar usuario", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });
    }
}
