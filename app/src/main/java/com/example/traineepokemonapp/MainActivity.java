package com.example.traineepokemonapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.traineepokemonapp.Activities.Pokedex;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {
    private Button btnTeste;
    private DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnTeste = findViewById(R.id.buttonTeste);
        btnTeste.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reference.child("permissoes").setValue("123");
                Intent intent = new Intent(getApplicationContext(), Pokedex.class);
                startActivity(intent);
            }
        });

    }
}