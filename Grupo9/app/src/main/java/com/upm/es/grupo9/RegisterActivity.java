package com.upm.es.grupo9;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.upm.es.grupo9.OnRegistrationListener;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class RegisterActivity extends AppCompatActivity implements OnRegistrationListener {

    EditText tNombre, tUsuario, tPassword, tEdad;
    Button btn_registrar;

    @Override
    public void onRegistrationSuccess() {
        // La registración fue exitosa, redirigir a MainActivity
        Toast.makeText(RegisterActivity.this, "Usuario creado exitosamente", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onRegistrationError(String errorMessage) {
        // Manejar el error de registración según sea necesario
        Toast.makeText(RegisterActivity.this, errorMessage, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        tNombre = findViewById(R.id.TextNombre);
        tUsuario = findViewById(R.id.TextUsuario);
        tPassword = findViewById(R.id.TextContra);
        tEdad = findViewById(R.id.textEdad);
        btn_registrar = findViewById(R.id.buttonRegister);


        btn_registrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String name = tNombre.getText().toString();
                final String username = tUsuario.getText().toString();
                final String password = tPassword.getText().toString();
                final String edadString = tEdad.getText().toString();




                // Validaciones
                if (name.isEmpty() || username.isEmpty() || password.isEmpty() || edadString.isEmpty()) {
                    // Algún campo está vacío
                    Toast.makeText(RegisterActivity.this, "Por favor, complete todos los campos", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Verificar si la edad ingresada es un número válido
                try {
                    int edad = Integer.parseInt(edadString);
                    if (edad <= 0) {
                        // Edad no válida
                        Toast.makeText(RegisterActivity.this, "Por favor, ingrese una edad válida", Toast.LENGTH_SHORT).show();
                        return;
                    }
                } catch (NumberFormatException e) {
                    // Error al convertir la edad a entero
                    Toast.makeText(RegisterActivity.this, "Error al procesar la edad", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Realizar la solicitud de registro
                RegisterRequest registerRequest = new RegisterRequest(RegisterActivity.this);
                registerRequest.execute("http://192.168.1.104/Register.php", name, username, edadString, password);

            }
        });
    }
}