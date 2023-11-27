package com.example.traineepokemonapp.Activities;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.traineepokemonapp.R;
import com.example.traineepokemonapp.api.PokemonAPI;
import com.example.traineepokemonapp.model.Pokemon;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TimeActivity extends AppCompatActivity {

    private ImageView[] pokemonSlots;
    private DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
    private Button btnPokedex;
    private String nomeUsuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time);
        Bundle dados = getIntent().getExtras();
        nomeUsuario = dados.getString("usuario");
        pokemonSlots = new ImageView[]{
                findViewById(R.id.slot1),
                findViewById(R.id.slot2),
                findViewById(R.id.slot3),
                findViewById(R.id.slot4),
                findViewById(R.id.slot5),
                findViewById(R.id.slot6)
        };
        pokemonSlots[0].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reference.child("times").child(nomeUsuario).child("0").removeValue();
                pokemonSlots[0].setImageResource(R.drawable.ic_launcher_background);
            }
        });
        pokemonSlots[1].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reference.child("times").child(nomeUsuario).child("1").removeValue();
                pokemonSlots[1].setImageResource(R.drawable.ic_launcher_background);
            }
        });
        pokemonSlots[2].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reference.child("times").child(nomeUsuario).child("2").removeValue();
                pokemonSlots[2].setImageResource(R.drawable.ic_launcher_background);
            }
        });
        pokemonSlots[3].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reference.child("times").child(nomeUsuario).child("3").removeValue();
                pokemonSlots[3].setImageResource(R.drawable.ic_launcher_background);
            }
        });
        pokemonSlots[4].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reference.child("times").child(nomeUsuario).child("4").removeValue();
                pokemonSlots[4].setImageResource(R.drawable.ic_launcher_background);
            }
        });
        pokemonSlots[5].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reference.child("times").child(nomeUsuario).child("5").removeValue();
                pokemonSlots[5].setImageResource(R.drawable.ic_launcher_background);
            }
        });
        btnPokedex = findViewById(R.id.btnPokedex);
        btnPokedex.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Pokedex.class);
                intent.putExtra("usuario", nomeUsuario);
                startActivity(intent);
            }
        });
        firebaseTime();
    }

    private void firebaseTime(){
        DatabaseReference noFilho = reference.child("times").child(nomeUsuario);
        noFilho.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot currentPokemon:snapshot.getChildren()){
                    int id = Integer.parseInt(currentPokemon.getValue().toString());
                    pokemonIdSprite(id, Integer.parseInt(currentPokemon.getKey().toString()));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void pokemonIdSprite(int PokemonID, int cont){
        PokemonAPI pokeAPI = new PokemonAPI();
        Call <Pokemon> call = pokeAPI.RecuperarPokemon(PokemonID);
        call.enqueue(new Callback<Pokemon>() {
            @Override
            public void onResponse(Call<Pokemon> call, Response<Pokemon> response) {
                Pokemon poke = response.body();
                int id = poke.getId();
                String sprite = poke.getSprites().getFront_default();

                Picasso.get().load(sprite).into(pokemonSlots[cont]);
            }

            @Override
            public void onFailure(Call<Pokemon> call, Throwable t) {

            }


        });

    }



}