package com.example.traineepokemonapp;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class CriarContaActivity extends AppCompatActivity {

    private EditText editTextUsername;
    private EditText editTextPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.criar_conta);

        editTextUsername = findViewById(R.id.editCriarUser);
        editTextPassword = findViewById(R.id.editCriarSenha);

        Button btnCriarConta = findViewById(R.id.btnCriar);
        btnCriarConta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nomeUsuario = editTextUsername.getText().toString();
                String senha = editTextPassword.getText().toString();

                CriarConta criarConta = new CriarConta(nomeUsuario, senha);
            }
        });
    }
}

