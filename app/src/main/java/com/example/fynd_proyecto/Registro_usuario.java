package com.example.fynd_proyecto;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Registro_usuario extends AppCompatActivity {

    private FirebaseFirestore db;
    EditText txt_nombre, txt_apellido, txt_correo, txt_clave, sp_genero, txt_nacimiento;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_usuario);

        db = FirebaseFirestore.getInstance();
        txt_nombre=findViewById(R.id.txt_registro_nombre);
        txt_apellido=findViewById(R.id.txt_registro_apellido);
        txt_correo=findViewById(R.id.txt_registro_correo);
        txt_clave=findViewById(R.id.txt_registro_clave);
        txt_nacimiento=findViewById(R.id.txt_registro_fecha);
    }
    public void mostrarAlerta(String titulo,String mensaje){
        AlertDialog.Builder alerta=new AlertDialog.Builder(this);
        alerta.setTitle(titulo);
        alerta.setMessage(mensaje);
        alerta.setPositiveButton("Aceptar",null);
        AlertDialog dialogo=alerta.create();
        dialogo.show();
    }
    public void  gestionarRegistro(View view){
        String nombre= txt_nombre.getText().toString();
        String apellido= txt_apellido.getText().toString();
        String correo= txt_correo.getText().toString();
        String clave= txt_clave.getText().toString();
        String nacimiento= txt_nacimiento.getText().toString();

        Map<String,Object> MapguardarRegistro = new HashMap<>();
        MapguardarRegistro.put("Nombre",nombre);
        MapguardarRegistro.put("Apellido",apellido);
        MapguardarRegistro.put("Correo",correo);
        MapguardarRegistro.put("Clave",clave);
        MapguardarRegistro.put("Fecha Nacimiento",nacimiento);

        db.collection("Usuario").add(MapguardarRegistro).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {

                mostrarAlerta("Correcto","Registro ingresado");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

                mostrarAlerta("Error","No se ingreso: "+e.getMessage());
            }
        });
    }
    public void volver_menu(View v) {
        Intent volver_menu = new Intent(Registro_usuario.this, MainActivity.class);
        startActivity(volver_menu);
    }
}