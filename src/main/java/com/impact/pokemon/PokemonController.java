package com.impact.pokemon;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
public class PokemonController {

    private static final Logger logger = LoggerFactory.getLogger(PokemonController.class);

    @Resource
    private PokemonData data;

    @Autowired
    private PokemonService ps;

    @GetMapping("/pokemonNames")
    public List<String> getPokemonNames() throws IOException {
        List<String> pokemonNames = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new FileReader(ResourceUtils.getFile("classpath:data/pokemon.csv")));

        reader.readLine();
        String line;
        while ((line = reader.readLine()) != null) {
            String[] parts = line.split(",");
            pokemonNames.add(parts[1]); 
        }
        reader.close();
        return pokemonNames;
    }

    @GetMapping("attack")
    public Map<String, Object> attack(String pokemonA, String pokemonB) throws IOException {
        logger.info("Requested pokemonA: {}, pokemonB: {}", pokemonA, pokemonB);
        

        return ps.attack(pokemonA, pokemonB);
    }
}
