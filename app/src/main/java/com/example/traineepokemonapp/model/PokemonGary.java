package com.example.traineepokemonapp.model;

import java.util.ArrayList;
import java.util.List;

public class PokemonGary {
    public Pokemon getPokemon() {
        return pokemon;
    }

    public void setPokemon(Pokemon pokemon) {
        this.pokemon = pokemon;
    }

    private Pokemon pokemon;

    public List<Fraco> getFracos() {
        return fracos;
    }

    public void setFracos(Fraco fraco) {
        this.fracos.add(fraco);
    }

    private List<Fraco> fracos;

    public PokemonGary() {
        this.fracos = new ArrayList<>();
    }
}
