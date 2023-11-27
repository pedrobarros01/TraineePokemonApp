package com.example.traineepokemonapp.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.traineepokemonapp.R;
import com.example.traineepokemonapp.api.PokemonAPI;
import com.example.traineepokemonapp.helper.PokemonHelper;
import com.example.traineepokemonapp.model.Pokemon;
import com.example.traineepokemonapp.model.Stats;
import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PokemonInfo extends AppCompatActivity {

    private Pokemon pokemon;
    private ImageView imgPokemon, imgTipo1, imgTipo2;
    private TextView nomePokemon, altura, peso, hp, attack, defense, spAttack, spDefense, speed;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pokemon_info);
        imgPokemon = findViewById(R.id.pokemon);
        imgTipo1 = findViewById(R.id.tipo1);
        imgTipo2 = findViewById(R.id.tipo2);
        nomePokemon = findViewById(R.id.nomePoke);
        altura = findViewById(R.id.altura);
        peso = findViewById(R.id.peso);
        hp = findViewById(R.id.hp);
        attack = findViewById(R.id.attack);
        defense = findViewById(R.id.defense);
        spAttack = findViewById(R.id.specialAttack);
        spDefense = findViewById(R.id.specialDefense);
        speed = findViewById(R.id.speed);
        Bundle dados = getIntent().getExtras();
        int id = Integer.parseInt(dados.getString("id").toString());
        PokemonAPI pokemonAPI = new PokemonAPI();
        pokemonAPI.RecuperarPokemon(id).enqueue(new Callback<Pokemon>() {
            @Override
            public void onResponse(Call<Pokemon> call, Response<Pokemon> response) {
                pokemon = response.body();
                nomePokemon.setText(pokemon.getName());
                String alt = "Altura: " + (pokemon.getHeight() / 10) + " Metros";
                String pes = "Peso: " + (pokemon.getWeight() / 10) + " Kilogramas";
                altura.setText(alt);
                peso.setText(pes);
                for(Stats stats: pokemon.getStats()){
                    switch (stats.getStat().getName()){
                        case "hp":
                            String vida = "Hp: " + stats.getBase_stat();
                            hp.setText(vida);
                            break;
                        case "attack":
                            String atk = "Attack: " + stats.getBase_stat();
                            attack.setText(atk);
                            break;
                        case "defense":
                            String dfs = "Defense: " + stats.getBase_stat();
                            defense.setText(dfs);
                            break;
                        case "special-attack":
                            String spatk = "Sp.Attack: " + stats.getBase_stat();
                            spAttack.setText(spatk);
                            break;
                        case "special-defense":
                            String spdef = "Sp.Defense: " + stats.getBase_stat();
                            spDefense.setText(spdef);
                            break;
                        case "speed":
                            String spd = "Speed: " + stats.getBase_stat();
                            speed.setText(spd);
                            break;
                    }


                }
                if(pokemon.getTypes().size() == 1){
                    int ptipo = PokemonHelper.SaberTipo(pokemon.getTypes().get(0).getType().getName());
                    imgTipo1.setVisibility(View.VISIBLE);
                    imgTipo2.setVisibility(View.INVISIBLE);
                    imgTipo1.setImageResource(ptipo);
                }
                if(pokemon.getTypes().size() == 2){
                    int ptipo = PokemonHelper.SaberTipo(pokemon.getTypes().get(0).getType().getName());
                    int stipo = PokemonHelper.SaberTipo(pokemon.getTypes().get(1).getType().getName());
                    imgTipo1.setVisibility(View.VISIBLE);
                    imgTipo2.setVisibility(View.VISIBLE);
                    imgTipo1.setImageResource(ptipo);
                    imgTipo2.setImageResource(stipo);
                }
                Picasso.get().load(pokemon.getSprites().getFront_default()).into(imgPokemon);
                Toast.makeText(PokemonInfo.this, "Aqui oh: " + pokemon.getName(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<Pokemon> call, Throwable t) {

            }
        });

    }
}