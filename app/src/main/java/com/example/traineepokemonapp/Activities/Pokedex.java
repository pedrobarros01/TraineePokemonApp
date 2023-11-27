package com.example.traineepokemonapp.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.traineepokemonapp.R;
import com.example.traineepokemonapp.adapter.AdapterPokedex;
import com.example.traineepokemonapp.api.PokemonAPI;
import com.example.traineepokemonapp.api.PokemonService;
import com.example.traineepokemonapp.model.Pokemon;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Pokedex extends AppCompatActivity implements AdapterPokedex.PokemonEventsInterface{
    private List<Pokemon> listPokemons;
    private RecyclerView pokedex;
    private Button btnEquipe, btnSimulacao;

    private List<Integer> pokemonsEquipe ;
    private DatabaseReference reference = FirebaseDatabase.getInstance().getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pokedex);
        pokedex = findViewById(R.id.pokedex);
        btnEquipe = findViewById(R.id.buttonEquipe);
        btnSimulacao = findViewById(R.id.buttonSimulacao);
        pokemonsEquipe = new ArrayList<>();
        //CarregarPokedex();
        RecuperaPokemons1G();

        btnSimulacao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), PokeSimulation.class);
                startActivity(intent);
            }
        });

    }
    private void RecuperaPokemons1G(){
        List<Pokemon> pokemons = new ArrayList<>();
        int totalPokemons = 151;
        PokemonAPI pokemonAPI = new PokemonAPI();
        for (int i = 1; i <= totalPokemons; i++) {
            Call<Pokemon> call = pokemonAPI.RecuperarPokemon(i);
            call.enqueue(new Callback<Pokemon>() {
                @Override
                public void onResponse(Call<Pokemon> call, Response<Pokemon> response) {
                    if(response.isSuccessful()){
                        Pokemon poke = response.body();
                        pokemons.add(poke);
                        Log.i("POKEMONS", poke.getName());
                        if(pokemons.size() == 151){
                            CarregarPokedex(pokemons);
                        }
                    }

                }

                @Override
                public void onFailure(Call<Pokemon> call, Throwable t) {
                    Log.i("POKEMONS", "Não deu");
                }
            });
        }
    }
    private void CarregarPokedex(List<Pokemon> pokemons){
        //this.listPokemons = RecuperaPokemons1G();
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        pokedex.setLayoutManager(layoutManager);
        pokedex.addItemDecoration(new DividerItemDecoration(
                getApplicationContext(),
                LinearLayout.VERTICAL
        ));
        pokedex.setHasFixedSize(true);

        AdapterPokedex adapter = new AdapterPokedex(pokemons, this);
        pokedex.setAdapter(adapter);
    }

    @Override
    public void onItemLongClik(Pokemon pokemon) {
        if(pokemonsEquipe.size() < 6){
            pokemonsEquipe.add(pokemon.getId());
            DatabaseReference time = reference.child("times").child("nomeUsuario");
            time.setValue(pokemonsEquipe);
            Toast.makeText(this, "Pokemon Cadastrado na equipe", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this, "Equipe Cheia!!", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onItemClick(int id) {
        Intent intent = new Intent(this, PokemonInfo.class);
        intent.putExtra("id", String.valueOf(id));
        startActivity(intent);
    }

}