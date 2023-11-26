package com.example.traineepokemonapp.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.traineepokemonapp.R;
import com.example.traineepokemonapp.adapter.AdapterPokedex;
import com.example.traineepokemonapp.api.PokemonAPI;
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
    private List<Pokemon> listPokemons;
    private RecyclerView pokedex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pokedex);
        pokedex = findViewById(R.id.pokedex);

        //CarregarPokedex();
        RecuperaPokemons1G();

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

        AdapterPokedex adapter = new AdapterPokedex(pokemons);
        pokedex.setAdapter(adapter);
    }
}