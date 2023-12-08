package com.example.fynd_proyecto;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    private EditText txtCorreo;
    private EditText txtContrasena;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Inicializa Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        // Obtén referencias a los EditTexts
        txtCorreo = findViewById(R.id.txt_inicioSesion_correo);
        txtContrasena = findViewById(R.id.txt_inicioSesion_contrasena);
    }

    // Método para el botón "Ingresar"
    public void ingresar(View view) {
        // Obtiene el correo y la contraseña ingresados por el usuario
        String correo = txtCorreo.getText().toString();
        String contrasena = txtContrasena.getText().toString();

        // Validar el inicio de sesión
        if (validarInicioSesion(correo, contrasena)) {
            // La validación se realiza en el método validarInicioSesion
            // No es necesario realizar ninguna acción aquí
        }
    }

    // Método de validación de inicio de sesión con Firebase
    private boolean validarInicioSesion(String correo, String contrasena) {
        // Verificar si los campos están vacíos
        if (TextUtils.isEmpty(correo) || TextUtils.isEmpty(contrasena)) {
            Toast.makeText(this, "Completa todos los campos.", Toast.LENGTH_SHORT).show();
            return false;
        }

        // Iniciar sesión con Firebase
        mAuth.signInWithEmailAndPassword(correo, contrasena)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Inicio de sesión exitoso
                            Toast.makeText(MainActivity.this, "Inicio de sesión exitoso", Toast.LENGTH_SHORT).show();
                            Intent abrirMenu = new Intent(MainActivity.this, Menu.class);
                            startActivity(abrirMenu);
                        } else {
                            // Si el inicio de sesión falla, muestra un mensaje al usuario.
                            Toast.makeText(MainActivity.this, "Inicio de sesión fallido. Verifica tu correo y contraseña.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

        // No bloquees la ejecución aquí, ya que la autenticación es asíncrona
        return true;
    }
}
