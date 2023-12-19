package com.upm.es.grupo9;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    EditText TextUsuario;
    EditText TextContra;
    Button buttonLogin;
    Button buttonRegister;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextUsuario=findViewById(R.id.TextUsuario);
        TextContra=findViewById(R.id.TextContra);
        buttonLogin=findViewById(R.id.buttonLogin);
        buttonRegister=findViewById(R.id.buttonRegister);
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validarUsuario();
            }
        });

    }

    private void validarUsuario(){
        Intent intent= new Intent(getApplicationContext(),MercadoActivity.class);
        startActivity(intent);
    }
}