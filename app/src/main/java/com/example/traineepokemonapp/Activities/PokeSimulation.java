package com.example.traineepokemonapp.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.traineepokemonapp.R;
import com.example.traineepokemonapp.adapter.AdapterChampion;
import com.example.traineepokemonapp.api.PokemonAPI;
import com.example.traineepokemonapp.model.Fraco;
import com.example.traineepokemonapp.model.Pokemon;
import com.example.traineepokemonapp.model.PokemonGary;
import com.example.traineepokemonapp.model.Type;
import com.example.traineepokemonapp.model.Types;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskCompletionSource;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PokeSimulation extends AppCompatActivity implements AdapterChampion.ChampionEvent {

    private List<PokemonGary> pokemonsGary;
    private List<Pokemon> pokemonsFortesContra;
    private List<Pokemon> pokemonsUser;
    private DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
    private ImageView imgPoke1, imgPoke2, imgPoke3;
    private RecyclerView listaGary;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_poke_simulation);
        imgPoke1 = findViewById(R.id.pokeUser1);
        imgPoke2 = findViewById(R.id.pokeUser2);
        imgPoke3 = findViewById(R.id.pokeUser3);
        listaGary = findViewById(R.id.listaGary);
        pokemonsFortesContra = new ArrayList<>();
        pokemonsUser = new ArrayList<>();
        pokemonsGary = new ArrayList<>();
        PegarPokemonsUser();
        PegarPokemonsGary();



    }

    private void  PegarPokemonsUser(){
        DatabaseReference timeRef = reference.child("times").child("nomeUsuario");
        timeRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot poketime: snapshot.getChildren()){
                    int idPokemon = Integer.parseInt(poketime.getValue().toString());

                    PegarPokemon(idPokemon).addOnCompleteListener(new OnCompleteListener<Pokemon>() {
                        @Override
                        public void onComplete(@NonNull Task<Pokemon> task) {
                            if(task.isSuccessful()){
                                Pokemon poke = task.getResult();
                                Log.i("GARY", poke.getName());
                                pokemonsUser.add(poke);
                                if(pokemonsUser.size() == 6){
                                    Toast.makeText(getApplicationContext(), "Equipe de user carregada", Toast.LENGTH_SHORT).show();
                                }
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
    private void PegarPokemonsGary(){
        DatabaseReference garyRef = reference.child("gary");

        garyRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot currentPokemonGary: snapshot.getChildren()){

                    int idPokemon = Integer.parseInt(currentPokemonGary.child("pokemon").getValue().toString());

                    PegarPokemon(idPokemon).addOnCompleteListener(new OnCompleteListener<Pokemon>() {
                        @Override
                        public void onComplete(@NonNull Task<Pokemon> task) {
                            if(task.isSuccessful()){
                                Pokemon poke = task.getResult();
                                PokemonGary pokemonGary = new PokemonGary();
                                pokemonGary.setPokemon(poke);
                                DataSnapshot fracoRef = currentPokemonGary.child("fraco");
                                for(DataSnapshot fracosnap: fracoRef.getChildren()){
                                    Fraco fraco = new Fraco();
                                    String tipo = fracosnap.child("tipo").getValue().toString();
                                    fraco.setTipo(tipo);
                                    pokemonGary.setFracos(fraco);
                                }
                                pokemonsGary.add(pokemonGary);
                                if(pokemonsGary.size() == 6){
                                    CarregarListaGary(pokemonsGary);
                                }
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
    private void CarregarListaGary(List<PokemonGary> list){

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        listaGary.setLayoutManager(layoutManager);
        listaGary.addItemDecoration(new DividerItemDecoration(
                getApplicationContext(),
                LinearLayout.VERTICAL
        ));
        listaGary.setHasFixedSize(true);
        AdapterChampion adapter = new AdapterChampion(list, this);
        listaGary.setAdapter(adapter);
    }

    private boolean repeteNaLista(String nome){
        for(Pokemon pokemon: pokemonsFortesContra){
            if(pokemon.getName().equals(nome)){
                return true;
            }
        }
        return false;
    }
    @Override
    public void OnItemClick(PokemonGary pokemon) {
        Toast.makeText(this, "aq oh", Toast.LENGTH_SHORT).show();
        pokemonsFortesContra.clear();
        imgPoke1.setImageResource(R.drawable.ic_launcher_background);
        imgPoke2.setImageResource(R.drawable.ic_launcher_background);
        imgPoke3.setImageResource(R.drawable.ic_launcher_background);
        for(Fraco fraco: pokemon.getFracos()){
            for(Pokemon pokemonUser: pokemonsUser){
                for(Types tipo: pokemonUser.getTypes()){
                    if(tipo.getType().getName().equals(fraco.getTipo())
                            && !repeteNaLista(pokemonUser.getName())
                            && pokemonsFortesContra.size() <= 3
                    )
                    {
                        pokemonsFortesContra.add(pokemonUser);
                    }
                }
            }
        }
        for (int i = 0; i < pokemonsFortesContra.size(); i++) {
            String sprite = pokemonsFortesContra.get(i).getSprites().getFront_default();
            if(i == 0){
                Picasso.get().load(sprite).into(imgPoke1);
            }
            if(i == 1){
                Picasso.get().load(sprite).into(imgPoke2);
            }
            if(i == 3){
                Picasso.get().load(sprite).into(imgPoke3);
            }
        }
    }
}