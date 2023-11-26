package com.example.traineepokemonapp.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

import com.example.traineepokemonapp.R;
import com.example.traineepokemonapp.api.PokemonAPI;
import com.example.traineepokemonapp.model.Pokemon;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PokemonInfo extends AppCompatActivity {

    private Pokemon pokemon;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pokemon_info);
        Bundle dados = getIntent().getExtras();
        int id = Integer.parseInt(dados.getString("id").toString());
        PokemonAPI pokemonAPI = new PokemonAPI();
        pokemonAPI.RecuperarPokemon(id).enqueue(new Callback<Pokemon>() {
            @Override
            public void onResponse(Call<Pokemon> call, Response<Pokemon> response) {
                pokemon = response.body();
                Toast.makeText(PokemonInfo.this, "Aqui oh: " + pokemon.getName(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<Pokemon> call, Throwable t) {

            }
        });

    }
}