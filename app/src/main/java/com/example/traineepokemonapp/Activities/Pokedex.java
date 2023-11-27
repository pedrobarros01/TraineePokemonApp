package com.example.traineepokemonapp.Activities;

import androidx.annotation.NonNull;
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
import com.example.traineepokemonapp.helper.PokemonHelper;
import com.example.traineepokemonapp.model.Pokemon;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
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
    private String nomeUsuario;
    private List<Integer> pokemonsEquipe ;
    private DatabaseReference reference = FirebaseDatabase.getInstance().getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pokedex);
        Bundle dados = getIntent().getExtras();
        this.nomeUsuario = dados.getString("usuario").toString();
        pokedex = findViewById(R.id.pokedex);
        btnEquipe = findViewById(R.id.buttonEquipe);
        btnSimulacao = findViewById(R.id.buttonSimulacao);
        pokemonsEquipe = new ArrayList<>();
        //CarregarPokedex();
        RecuperarEquipe();
        RecuperaPokemons1G();

        btnSimulacao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), PokeSimulation.class);
                intent.putExtra("usuario", nomeUsuario);
                startActivity(intent);
            }
        });
        btnEquipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), TimeActivity.class);
                intent.putExtra("usuario", nomeUsuario);
                startActivity(intent);
            }
        });

    }
    private void RecuperarEquipe(){
        DatabaseReference time = reference.child("times").child(nomeUsuario);
        time.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot currentPokemon: snapshot.getChildren()){

                    int idPokemon = Integer.parseInt(currentPokemon.getValue().toString());
                    PokemonHelper.PegarPokemon(idPokemon).addOnCompleteListener(new OnCompleteListener<Pokemon>() {
                        @Override
                        public void onComplete(@NonNull Task<Pokemon> task) {
                            if(task.isSuccessful()){
                                Pokemon pokemon = task.getResult();
                                pokemonsEquipe.add(pokemon.getId());
                            }
                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

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
        Collections.sort(pokemons, (p1, p2) -> {
            return p1.getId() - p2.getId();
        });
        AdapterPokedex adapter = new AdapterPokedex(pokemons, this);
        pokedex.setAdapter(adapter);
    }

    @Override
    public void onItemLongClik(Pokemon pokemon) {
        if(pokemonsEquipe.size() < 6){
            pokemonsEquipe.add(pokemon.getId());
            if(pokemonsEquipe.size() == 6){
                DatabaseReference time = reference.child("times").child(nomeUsuario);
                time.setValue(pokemonsEquipe);
            }

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