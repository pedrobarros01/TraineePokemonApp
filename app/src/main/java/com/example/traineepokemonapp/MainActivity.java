package com.example.traineepokemonapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.traineepokemonapp.Activities.CriarContaActivity;
import com.example.traineepokemonapp.Activities.TimeActivity;
import com.example.traineepokemonapp.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    private DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
    private TextView textCriarConta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        reference.child("permissoes").setValue("all");

        textCriarConta = findViewById(R.id.textCriarConta);
        textCriarConta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), TimeActivity.class);
                startActivity(intent);
            }
        });
    }
}