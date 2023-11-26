package com.example.traineepokemonapp.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.traineepokemonapp.MainActivity;
import com.example.traineepokemonapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskCompletionSource;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CriarContaActivity extends AppCompatActivity {

    private EditText editTextUsername;
    private DatabaseReference reference = FirebaseDatabase.getInstance().getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.criar_conta);

        editTextUsername = findViewById(R.id.editCriarUser);
        Button btnCriarConta = findViewById(R.id.btnCriar);
        btnCriarConta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nomeUsuario = editTextUsername.getText().toString();
                criarConta(nomeUsuario).addOnCompleteListener(new OnCompleteListener<Boolean>() {
                    @Override
                    public void onComplete(@NonNull Task<Boolean> task) {
                        boolean check = task.getResult();
                        if(check){
                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                            startActivity(intent);
                        }
                        else{
                            Toast.makeText(getApplicationContext(), "Não foi possível criar o usuáro", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }

    public Task<Boolean> criarConta(String nomeUsuario){
        DatabaseReference noUsuario = reference.child("login");
        TaskCompletionSource<Boolean> created = new TaskCompletionSource<>();
        noUsuario.child(nomeUsuario).setValue(nomeUsuario);
        return created.getTask();
    }
}

