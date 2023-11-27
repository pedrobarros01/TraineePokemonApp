package com.example.traineepokemonapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.traineepokemonapp.Activities.PokemonInfo;
import com.example.traineepokemonapp.R;
import com.example.traineepokemonapp.helper.PokemonHelper;
import com.example.traineepokemonapp.model.Pokemon;
import com.squareup.picasso.Picasso;

import java.util.List;

public class AdapterPokedex extends RecyclerView.Adapter<AdapterPokedex.MyViewHolder> {

    public interface PokemonEventsInterface {
        void onItemLongClik(Pokemon pokemon);

        void onItemClick(int id);
    }
    private PokemonEventsInterface listener;
    private List<Pokemon> listPokemons;
    private Context context;
    public AdapterPokedex(List<Pokemon> listPokemons, PokemonEventsInterface listener) {
        this.listPokemons = listPokemons;
        this.listener = listener;
    }

    @NonNull
    @Override
    public AdapterPokedex.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View ItemLista = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_pokedex, parent, false);
        return new MyViewHolder(ItemLista);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterPokedex.MyViewHolder holder, int position) {
        Pokemon pokemon = listPokemons.get(position);
        holder.ColocarImagem(pokemon.getSprites().getFront_default());
        holder.namePoke.setText(pokemon.getName());
        holder.imgPokedexPokemon.setVisibility(View.VISIBLE);
        if(pokemon.getTypes().size() == 1){
            holder.imgTipo1.setVisibility(View.VISIBLE);
            holder.imgTipo2.setVisibility(View.INVISIBLE);
            String type = pokemon.getTypes().get(0).getType().getName();
            int drawableTypeId = PokemonHelper.SaberTipo(type);
            holder.imgTipo1.setImageResource(drawableTypeId);
        }
        if(pokemon.getTypes().size() == 2){
            holder.imgTipo1.setVisibility(View.VISIBLE);
            holder.imgTipo2.setVisibility(View.VISIBLE);
            String type1 = pokemon.getTypes().get(0).getType().getName();
            String type2 = pokemon.getTypes().get(1).getType().getName();
            int drawableType1Id = PokemonHelper.SaberTipo(type1);
            int drawableType2Id = PokemonHelper.SaberTipo(type2);
            holder.imgTipo1.setImageResource(drawableType1Id);
            holder.imgTipo2.setImageResource(drawableType2Id);
        }
        holder.rowList.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                listener.onItemLongClik(pokemon);
                return true;
            }
        });
        holder.rowList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClick(pokemon.getId());
            }
        });



    }

    @Override
    public int getItemCount() {
        return listPokemons.size();
    }
    public class MyViewHolder extends  RecyclerView.ViewHolder{
        private ImageView imgPokedexPokemon, imgTipo1, imgTipo2;
        private TextView namePoke;
        private LinearLayout rowList;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imgPokedexPokemon = itemView.findViewById(R.id.imagePokedexPokemon);
            imgTipo1 = itemView.findViewById(R.id.imageTipo1);
            imgTipo2 = itemView.findViewById(R.id.imageTipo2);
            namePoke = itemView.findViewById(R.id.namePokemon);
            rowList = itemView.findViewById(R.id.rowItem);


        }
        private void ColocarImagem(String url){
            Picasso.get().load(url).into(imgPokedexPokemon);

        }
    }
}
