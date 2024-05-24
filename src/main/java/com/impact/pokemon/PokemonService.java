package com.impact.pokemon;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PokemonService {

    @Autowired
    PokemonData pd;

    public Map<String, Object> attack(String cont1, String cont2){

        Pokemon winner = null;

        Pokemon contender1 = pd.getPokemonByName(cont1).orElse(null);
        Pokemon contender2 = pd.getPokemonByName(cont2).orElse(null);



        Pokemon firstAttacker = determineFirstAttacker(contender1, contender2);
        Pokemon secondAttacker = (firstAttacker == contender1)? contender2 : contender1;

        while (firstAttacker.getHitPoints() > 0 && secondAttacker.getHitPoints() > 0) {
            double damage = calculateDamage(firstAttacker, secondAttacker);
            secondAttacker.takeDamage(damage);

            if(secondAttacker.getHitPoints() <= 0)
                winner = firstAttacker; 

            damage = calculateDamage(secondAttacker, firstAttacker);
            firstAttacker.takeDamage(damage);

            if(firstAttacker.getHitPoints() <= 0)
                winner = secondAttacker;
        }
        return Map.of("winner", winner.getName(), "hitPoints", winner.getHitPoints());
    }

    private double calculateDamage(Pokemon firstAttacker, Pokemon secondAttacker) {
        double effectivenessModifier = getEffectivenessModifier(firstAttacker.getType(), secondAttacker.getType());
        double damage = 50*(firstAttacker.getAttack() / secondAttacker.getDefense()) * effectivenessModifier;
        return damage;
    }

    private double getEffectivenessModifier(String type1, String type2) {
        double effectiveness;
        switch (type1.toLowerCase()) {
            case "fire":
                switch (type2.toLowerCase()) {
                    case "grass":
                        effectiveness = 2.0; 
                        break;
                    case "water":
                        effectiveness = 0.5; 
                        break;
                    default:
                        effectiveness = 1.0;
                        break;
                }
                break;
            case "water":
                switch (type2.toLowerCase()) {
                    case "fire":
                        effectiveness = 2.0; 
                        break;
                    case "electric":
                        effectiveness = 0.5; 
                        break;
                    default:
                        effectiveness = 1.0;
                        break;
                }
                break;
            case "grass":
                switch (type2.toLowerCase()) {
                    case "electric":
                        effectiveness = 2.0; 
                        break;
                    case "fire":
                        effectiveness = 0.5; 
                        break;
                    default:
                        effectiveness = 1.0;
                        break;
                }
                break;
            case "electric":
                switch (type2.toLowerCase()) {
                    case "water":
                        effectiveness = 2.0; 
                        break;
                    case "grass":
                        effectiveness = 0.5; 
                        break;
                    default:
                        effectiveness = 1.0;
                        break;
                }
                break;
            default:
                effectiveness = 1.0;
                break;
        }
    
        return effectiveness;

    }

    private Pokemon determineFirstAttacker(Pokemon contender1, Pokemon contender2) {
        int speed1 = contender1.getSpeed();
        int speed2 = contender2.getSpeed();
        return (speed1 >= speed2)? contender1: contender2;
    }

    
}
