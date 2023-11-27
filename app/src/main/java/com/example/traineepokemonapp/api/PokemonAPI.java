package com.example.traineepokemonapp.api;

import com.example.traineepokemonapp.model.Pokemon;

import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskCompletionSource;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
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
    private Task<Pokemon> PegarPokemon(int id){
        PokemonAPI pokemonAPI = new PokemonAPI();
        TaskCompletionSource<Pokemon> taskCompletionSource = new TaskCompletionSource<>();
        Call<Pokemon> call = pokemonAPI.RecuperarPokemon(id);
        call.enqueue(new Callback<Pokemon>() {
            @Override
            public void onResponse(Call<Pokemon> call, Response<Pokemon> response) {
                if(response.isSuccessful()){
                    Pokemon poke = response.body();
                    taskCompletionSource.setResult(poke);

                }
            }

            @Override
            public void onFailure(Call<Pokemon> call, Throwable t) {

            }
        });


        return taskCompletionSource.getTask();
    }
}
