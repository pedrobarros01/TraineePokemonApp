package com.example.traineepokemonapp.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.traineepokemonapp.R;
import com.example.traineepokemonapp.helper.PokemonHelper;
import com.example.traineepokemonapp.model.PokemonGary;
import com.squareup.picasso.Picasso;

import java.util.List;

public class AdapterChampion extends RecyclerView.Adapter<AdapterChampion.MyViewHolder> {


public  interface ChampionEvent{
    void OnItemClick(PokemonGary pokemon);
}

    private ChampionEvent listener;
    private List<PokemonGary> pokemonGaryList;
    public AdapterChampion(List<PokemonGary> pokemonGaryList, ChampionEvent listener) {
        this.pokemonGaryList = pokemonGaryList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View ItemLista = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_champion, parent, false);
        return new MyViewHolder(ItemLista);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterChampion.MyViewHolder holder, int position) {
        PokemonGary garyPoke = pokemonGaryList.get(position);
        holder.CarregarImagemPokemon(garyPoke.getPokemon().getSprites().getFront_default());
        holder.pokeGary.setVisibility(View.VISIBLE);
        holder.nomePokeGary.setText(garyPoke.getPokemon().getName());
        if(garyPoke.getPokemon().getTypes().size() == 1){
            holder.tipo1.setVisibility(View.VISIBLE);
            holder.tipo2.setVisibility(View.INVISIBLE);
            String type = garyPoke.getPokemon().getTypes().get(0).getType().getName();
            int ptype = PokemonHelper.SaberTipo(type);
            //Log.i("GARY", String.valueOf(ptype));
            holder.tipo1.setImageResource(ptype);
        }
        if(garyPoke.getPokemon().getTypes().size() == 2){
            holder.tipo1.setVisibility(View.VISIBLE);
            holder.tipo2.setVisibility(View.VISIBLE);
            String type1 = garyPoke.getPokemon().getTypes().get(0).getType().getName();
            String type2 = garyPoke.getPokemon().getTypes().get(1).getType().getName();
            //Log.i("GARY", type1);
            //Log.i("GARY", type2);
            int ptype = PokemonHelper.SaberTipo(type1);
            //Log.i("GARY", String.valueOf(ptype));
            int stype = PokemonHelper.SaberTipo(type2);
            //Log.i("GARY", String.valueOf(stype));
            holder.tipo1.setImageResource(ptype);
            holder.tipo2.setImageResource(stype);
        }
        holder.linhaPokeGary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.OnItemClick(garyPoke);
            }
        });




    }

    @Override
    public int getItemCount() {
        return pokemonGaryList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        private TextView nomePokeGary;
        private ImageView pokeGary, tipo1, tipo2;
        private LinearLayout linhaPokeGary;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            nomePokeGary = itemView.findViewById(R.id.nomeGaryPokemon);
            pokeGary = itemView.findViewById(R.id.pokeGary);
            tipo1 = itemView.findViewById(R.id.imageView5);
            tipo2 = itemView.findViewById(R.id.imageView6);
            linhaPokeGary = itemView.findViewById(R.id.linhaPokeGary);
        }

        private void CarregarImagemPokemon(String sprite){
            Picasso.get().load(sprite).into(pokeGary);

        }
    }
}
