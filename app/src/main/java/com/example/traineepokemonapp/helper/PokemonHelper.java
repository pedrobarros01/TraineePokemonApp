package com.example.traineepokemonapp.helper;

import android.graphics.drawable.Drawable;

import com.example.traineepokemonapp.R;

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
}
