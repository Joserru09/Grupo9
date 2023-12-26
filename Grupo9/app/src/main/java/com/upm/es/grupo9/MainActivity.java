package com.upm.es.grupo9;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements OnLoginListener{
    EditText TextUsuario;
    EditText TextContra;
    Button buttonLogin;
    Button buttonRegister;

    @Override
    public void onLoginSuccess(String username, String name, int edad) {
        // El inicio de sesión fue exitoso, redirigir a MercadoActivity
        Toast.makeText(MainActivity.this, "Bienvenido", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(MainActivity.this, MercadoActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onLoginError(String errorMessage) {
        // Manejar el error de inicio de sesión según sea necesario
        Toast.makeText(MainActivity.this, errorMessage, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextUsuario = findViewById(R.id.TextNombre);
        TextContra = findViewById(R.id.TextContra);
        buttonLogin = findViewById(R.id.buttonLogin);
        buttonRegister = findViewById(R.id.buttonRegister);
        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = TextUsuario.getText().toString().trim();
                String password = TextContra.getText().toString().trim();

                // Validar que se ingresaron datos
                if (username.isEmpty() || password.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Por favor, complete todos los campos", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Realizar la solicitud de inicio de sesión
                LoginRequest loginRequest = new LoginRequest(MainActivity.this);
                loginRequest.execute("http://192.168.1.104/Login.php", username, password);
            }
        });
    }
}