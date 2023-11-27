package com.example.traineepokemonapp.api;

import com.example.traineepokemonapp.model.Pokemon;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface PokemonService {

    @GET("pokemon/{id}")
    Call<Pokemon> RecuperarPokemonsPokedex(@Path("id") int id);
}
