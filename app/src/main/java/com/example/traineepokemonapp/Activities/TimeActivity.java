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
import com.example.traineepokemonapp.api.PokemonService;
import com.example.traineepokemonapp.model.Pokemon;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TimeActivity extends AppCompatActivity {

    public ImageView[] pokemonSlots;
    private int PokemonID;
    private int cont = 0;
    private DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
    private Button btnPokedex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.time);

        pokemonSlots = new ImageView[]{
                findViewById(R.id.slot1),
                findViewById(R.id.slot2),
                findViewById(R.id.slot3),
                findViewById(R.id.slot4),
                findViewById(R.id.slot5),
                findViewById(R.id.slot6)
        };

        btnPokedex.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Pokedex.class);
                startActivity(intent);
            }
        });
    }

    private void firebaseTime(){
        DatabaseReference noFilho = reference.child("time");
        noFilho.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot currentPokemon:snapshot.getChildren()){
                    int id = Integer.parseInt(currentPokemon.child("id").getValue().toString());
                    pokemonIdSprite(id, cont);
                    cont++;
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

                Picasso.get().load("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/132.png").into(pokemonSlots[cont]);
            }

            @Override
            public void onFailure(Call<Pokemon> call, Throwable t) {

            }


        });

    }



}