package com.example.traineepokemonapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.traineepokemonapp.Activities.CriarContaActivity;
import com.example.traineepokemonapp.Activities.TimeActivity;
import com.example.traineepokemonapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskCompletionSource;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    private DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
    private TextView textCriarConta;
    private EditText login;
    private Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        reference.child("permissoes").setValue("all");

        login = findViewById(R.id.editTextUsername);
        btnLogin = findViewById(R.id.btnLogin);
        textCriarConta = findViewById(R.id.textCriarConta);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String loginstr = login.getText().toString();
                verificarUsuario(loginstr).addOnCompleteListener(new OnCompleteListener<Boolean>() {
                    @Override
                    public void onComplete(@NonNull Task<Boolean> task) {
                        boolean check = task.getResult();
                        if(check){
                            Intent intent = new Intent(getApplicationContext(), TimeActivity.class);
                            startActivity(intent);
                        }
                        else{
                            Toast.makeText(getApplicationContext(), "Usuário não existe", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
        textCriarConta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), CriarContaActivity.class);
                startActivity(intent);
            }
        });
    }

    public Task<Boolean> verificarUsuario(String loginText){
        DatabaseReference noUsuario = reference.child("login");
        TaskCompletionSource <Boolean> exists = new TaskCompletionSource<>();
        noUsuario.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot CurrentUsuario: snapshot.getChildren()) {
                    String login = CurrentUsuario.getKey().toString();
                    if (login == loginText){
                        exists.setResult(true);
                        return;
                    }
                    exists.setResult(false);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                exists.setResult(false);
            }
        });
        return exists.getTask();
    }
}