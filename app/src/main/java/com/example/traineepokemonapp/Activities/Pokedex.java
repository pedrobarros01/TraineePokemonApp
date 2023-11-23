package com.example.traineepokemonapp.Activities;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.traineepokemonapp.R;
import com.example.traineepokemonapp.api.PokemonService;
import com.example.traineepokemonapp.model.Pokemon;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Pokedex extends AppCompatActivity {

    private Retrofit retrofit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pokedex);
        retrofit = new Retrofit.Builder()
                .baseUrl("https://pokeapi.co/api/v2/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RecuperaPokemons1G();

    }
    private void RecuperaPokemons1G(){
        List<Pokemon> pokemons = new ArrayList<>();
        int totalPokemons = 151;
        PokemonService pokemonService = retrofit.create(PokemonService.class);
        for (int i = 1; i <= totalPokemons; i++) {
            Call<Pokemon> call = pokemonService.RecuperarPokemonsPokedex(i);
            call.enqueue(new Callback<Pokemon>() {
                @Override
                public void onResponse(Call<Pokemon> call, Response<Pokemon> response) {
                    if(response.isSuccessful()){
                        Pokemon poke = response.body();
                        pokemons.add(poke);
                        Log.i("POKEMONS", poke.getName());
                    }

                }

                @Override
                public void onFailure(Call<Pokemon> call, Throwable t) {
                    Log.i("POKEMONS", "Não deu");
                }
            });
        }
    }
}