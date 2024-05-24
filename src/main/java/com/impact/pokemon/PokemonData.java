package com.impact.pokemon;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * !! Feel free to change everything about this !!
 * This could be a class to hold all the Pokemon objects loaded from CSV,
 * but there are many ways to do it.
 */
@Component
public class PokemonData {
    private final List<Pokemon> pokemonList;

    PokemonData() throws IOException {
        this.pokemonList = loadPokemonData();
    }


    private List<Pokemon> loadPokemonData() throws IOException {
        List<Pokemon> pokemonList = new ArrayList<>();
        File file = new ClassPathResource("data/pokemon.csv").getFile();
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            br.readLine();
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                int id = Integer.parseInt(data[0]);
                String name = data[1];
                String type = data[2];
                int total = Integer.parseInt(data[3]);
                int hp = Integer.parseInt(data[4]);
                int attack = Integer.parseInt(data[5]);
                int defense = Integer.parseInt(data[6]);
                int spAttack = Integer.parseInt(data[7]);
                int spDefense = Integer.parseInt(data[8]);
                int speed = Integer.parseInt(data[9]);
                int generation = Integer.parseInt(data[10]);
                boolean legendary = Boolean.parseBoolean(data[11]);
                Pokemon pokemon = new Pokemon(id, name, type, total, hp, attack, defense,
                        spAttack, spDefense, speed, generation, legendary);
                pokemonList.add(pokemon);
            }
        }
        return pokemonList;
    }

    public List<Pokemon> getPokemonList() {
        return pokemonList;
    }

    public Optional<Pokemon> getPokemonByName(String pokemonName){
        String searchName = pokemonName.toLowerCase();
        return this.getPokemonList().stream()
                .filter(pokemon -> pokemon.getName().toLowerCase().contains(searchName))
                .findFirst();
    }

}
