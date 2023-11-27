package com.example.traineepokemonapp.model;

import java.util.List;

public class Pokemon {

    private int id;
    private String name;
    private List<Stats> stats;
    private int height;
    private int weight;
    private Sprites sprites;
    private List<Types> types;
    public String getName() {
        return name;
    }
    public int getId() {
        return id;
    }
    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }
    public int getWeight() {
        return weight;
    }

    public List<Stats> getStats() {
        return stats;
    }

    public void setStats(List<Stats> stats) {
        this.stats = stats;
    }
    public void setWeight(int weight) {
        this.weight = weight;
    }
    public void setId(int id) {
        this.id = id;
    }
    public void setName(String name) {
        this.name = name;
    }

    public Sprites getSprites() {
        return sprites;
    }

    public void setSprites(Sprites sprites) {
        this.sprites = sprites;
    }

    public List<Types> getTypes() {
        return types;
    }

    public void setTypes(List<Types> types) {
        this.types = types;
    }



}
