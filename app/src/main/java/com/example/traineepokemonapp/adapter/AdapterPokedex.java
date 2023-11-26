package com.example.traineepokemonapp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.traineepokemonapp.R;
import com.example.traineepokemonapp.model.Pokemon;
import com.squareup.picasso.Picasso;

import java.util.List;

public class AdapterPokedex extends RecyclerView.Adapter<AdapterPokedex.MyViewHolder> {



    private List<Pokemon> listPokemons;
    public AdapterPokedex(List<Pokemon> listPokemons) {
        this.listPokemons = listPokemons;
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
        holder.imgTipo1.setVisibility(View.VISIBLE);
        holder.imgTipo2.setVisibility(View.VISIBLE);



    }

    @Override
    public int getItemCount() {
        return listPokemons.size();
    }
    public class MyViewHolder extends  RecyclerView.ViewHolder{
        private ImageView imgPokedexPokemon, imgTipo1, imgTipo2;
        private TextView namePoke;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imgPokedexPokemon = itemView.findViewById(R.id.imagePokedexPokemon);
            imgTipo1 = itemView.findViewById(R.id.imageTipo1);
            imgTipo2 = itemView.findViewById(R.id.imageTipo2);
            namePoke = itemView.findViewById(R.id.namePokemon);
        }
        private void ColocarImagem(String url){
            Picasso.get().load(url).into(imgPokedexPokemon);

        }
    }
}
