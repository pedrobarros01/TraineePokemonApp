package com.example.traineepokemonapp.helper;

import android.graphics.drawable.Drawable;

import com.example.traineepokemonapp.R;
import com.example.traineepokemonapp.api.PokemonAPI;
import com.example.traineepokemonapp.model.Pokemon;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskCompletionSource;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class   PokemonHelper {
    public static int SaberTipo(String Tipo){
        switch (Tipo){
            case "fire":
                return R.drawable.fire;
            case "water":
                return R.drawable.water;
            case "grass":
                return R.drawable.grass;
            case "bug":
                return  R.drawable.bug;
            case "dark":
                return  R.drawable.dark;
            case  "dragon":
                return R.drawable.dragon;
            case "electric":
                return R.drawable.eletric;
            case "fairy":
                return R.drawable.fairy;
            case "fighting":
                return R.drawable.fighting;
            case "flying":
                return R.drawable.flying;
            case "ghost":
                return R.drawable.ghost;
            case "ground":
                return R.drawable.ground;
            case "ice":
                return R.drawable.ice;
            case "normal":
                return R.drawable.normal;
            case "poison":
                return R.drawable.poison;
            case "psychic":
                return R.drawable.psychic;
            case "rock":
                return R.drawable.rock;
            default:
                return R.drawable.steel;
        }
    }
    public static Task<Pokemon> PegarPokemon(int id){
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
