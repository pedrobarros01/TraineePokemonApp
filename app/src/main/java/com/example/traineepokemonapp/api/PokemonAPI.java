package com.example.traineepokemonapp.api;

import com.example.traineepokemonapp.model.Pokemon;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PokemonAPI {
    private Retrofit retrofit;
    public PokemonAPI(){
        retrofit = new Retrofit.Builder()
                .baseUrl("https://pokeapi.co/api/v2/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }
    public Call<Pokemon> RecuperarPokemon(int id){
        PokemonService pokemonService = retrofit.create(PokemonService.class);
        Call<Pokemon> call = pokemonService.RecuperarPokemonsPokedex(id);
        return call;
    }
}
