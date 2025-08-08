package com.aliensattack.core.model;

import com.aliensattack.core.enums.AlienType;
import lombok.Getter;
import lombok.Setter;

import java.util.*;

/**
 * Alien Evolution - Progressive alien difficulty scaling
 * Implements XCOM 2 Alien Evolution System
 */
@Getter
@Setter
public class AlienEvolution {
    
    private String evolutionId;
    private Map<AlienType, Integer> alienLevels;
    private Map<AlienType, List<String>> alienAbilities;
    private Map<AlienType, Map<String, Integer>> alienStats;
    private Map<String, Integer> evolutionProgress;
    private List<String> evolutionEvents;
    private int totalEvolutionLevel;
    private int missionsCompleted;
    private Random random;
    
    public AlienEvolution() {
        this.evolutionId = "ALIEN_EVOLUTION_" + System.currentTimeMillis();
        this.alienLevels = new HashMap<>();
        this.alienAbilities = new HashMap<>();
        this.alienStats = new HashMap<>();
        this.evolutionProgress = new HashMap<>();
        this.evolutionEvents = new ArrayList<>();
        this.totalEvolutionLevel = 1;
        this.missionsCompleted = 0;
        this.random = new Random();
        
        initializeAlienEvolution();
    }
    
    private void initializeAlienEvolution() {
        // Initialize alien levels
        for (AlienType alienType : AlienType.values()) {
            alienLevels.put(alienType, 1);
            alienAbilities.put(alienType, new ArrayList<>());
            alienStats.put(alienType, new HashMap<>());
        }
        
        // Initialize evolution progress
        evolutionProgress.put("ADVENT_TROOPER", 0);
        evolutionProgress.put("ADVENT_OFFICER", 0);
        evolutionProgress.put("ADVENT_STUN_LANCER", 0);
        evolutionProgress.put("ADVENT_MEC", 0);
        evolutionProgress.put("ADVENT_PRIEST", 0);
        evolutionProgress.put("ADVENT_BERSERKER", 0);
        evolutionProgress.put("ADVENT_ARCHON", 0);
        evolutionProgress.put("ADVENT_VIPER", 0);
        evolutionProgress.put("ADVENT_MUTON", 0);
        evolutionProgress.put("ADVENT_SECTOPOD", 0);
        evolutionProgress.put("ADVENT_ANDROMEDON", 0);
        evolutionProgress.put("ADVENT_CODEX", 0);
        evolutionProgress.put("ADVENT_GATEKEEPER", 0);
        evolutionProgress.put("ADVENT_AVATAR", 0);
        
        // Initialize basic abilities
        initializeBasicAbilities();
    }
    
    private void initializeBasicAbilities() {
        // ADVENT Trooper abilities
        alienAbilities.get(AlienType.ADVENT_TROOPER).add("STANDARD_ATTACK");
        alienAbilities.get(AlienType.ADVENT_TROOPER).add("OVERWATCH");
        
        // ADVENT Officer abilities
        alienAbilities.get(AlienType.ADVENT_OFFICER).add("MARK_TARGET");
        alienAbilities.get(AlienType.ADVENT_OFFICER).add("COORDINATE");
        
        // ADVENT Berserker abilities
        alienAbilities.get(AlienType.ADVENT_BERSERKER).add("RAGE");
        alienAbilities.get(AlienType.ADVENT_BERSERKER).add("CHARGE");
        
        // ADVENT Archon abilities
        alienAbilities.get(AlienType.ADVENT_ARCHON).add("BLAZING_PINIONS");
        alienAbilities.get(AlienType.ADVENT_ARCHON).add("AIR_SUPERIORITY");
    }
    
    /**
     * Complete mission and trigger evolution
     */
    public void completeMission(boolean playerVictory) {
        missionsCompleted++;
        
        if (playerVictory) {
            // Player victory triggers faster evolution
            triggerEvolution(2);
        } else {
            // Player defeat triggers slower evolution
            triggerEvolution(1);
        }
        
        logEvolutionEvent("Mission " + missionsCompleted + " completed. Player victory: " + playerVictory);
    }
    
    /**
     * Trigger alien evolution
     */
    private void triggerEvolution(int evolutionPoints) {
        totalEvolutionLevel += evolutionPoints;
        
        // Evolve random alien types
        List<AlienType> alienTypes = Arrays.asList(AlienType.values());
        int aliensToEvolve = Math.min(evolutionPoints, alienTypes.size());
        
        for (int i = 0; i < aliensToEvolve; i++) {
            AlienType alienType = alienTypes.get(random.nextInt(alienTypes.size()));
            evolveAlien(alienType);
        }
        
        logEvolutionEvent("Evolution triggered. Total level: " + totalEvolutionLevel);
    }
    
    /**
     * Evolve specific alien type
     */
    private void evolveAlien(AlienType alienType) {
        int currentLevel = alienLevels.get(alienType);
        int newLevel = currentLevel + 1;
        alienLevels.put(alienType, newLevel);
        
        // Add new abilities based on level
        addAbilitiesForLevel(alienType, newLevel);
        
        // Increase stats based on level
        increaseStatsForLevel(alienType, newLevel);
        
        logEvolutionEvent("Alien " + alienType + " evolved to level " + newLevel);
    }
    
    /**
     * Add abilities for alien level
     */
    private void addAbilitiesForLevel(AlienType alienType, int level) {
        List<String> abilities = alienAbilities.get(alienType);
        
        switch (alienType) {
            case ADVENT_TROOPER:
                if (level == 2) abilities.add("SUPPRESSION");
                if (level == 3) abilities.add("FLANKING_BONUS");
                if (level == 4) abilities.add("COVERING_FIRE");
                break;
            case ADVENT_OFFICER:
                if (level == 2) abilities.add("INSPIRE");
                if (level == 3) abilities.add("TACTICAL_SENSE");
                if (level == 4) abilities.add("LEADERSHIP_AURA");
                break;
            case ADVENT_BERSERKER:
                if (level == 2) abilities.add("RAGE_MODE");
                if (level == 3) abilities.add("BERSERKER_RUSH");
                if (level == 4) abilities.add("UNSTOPPABLE");
                break;
            case ADVENT_ARCHON:
                if (level == 2) abilities.add("PINION_BARRAGE");
                if (level == 3) abilities.add("AERIAL_DOMINANCE");
                if (level == 4) abilities.add("SKY_FALL");
                break;
            case ADVENT_VIPER:
                if (level == 2) abilities.add("CONSTRICT");
                if (level == 3) abilities.add("POISON_SPIT");
                if (level == 4) abilities.add("VENOM_BLAST");
                break;
            case ADVENT_MUTON:
                if (level == 2) abilities.add("MELEE_ATTACK");
                if (level == 3) abilities.add("COUNTER_ATTACK");
                if (level == 4) abilities.add("BERSERKER_RAGE");
                break;
            default:
                // Generic evolution for other alien types
                if (level == 2) abilities.add("ENHANCED_ATTACK");
                if (level == 3) abilities.add("TACTICAL_ADVANTAGE");
                if (level == 4) abilities.add("ELITE_STATUS");
                break;
        }
    }
    
    /**
     * Increase stats for alien level
     */
    private void increaseStatsForLevel(AlienType alienType, int level) {
        Map<String, Integer> stats = alienStats.get(alienType);
        
        // Base stat increases
        stats.put("health", stats.getOrDefault("health", 100) + (level * 10));
        stats.put("damage", stats.getOrDefault("damage", 20) + (level * 5));
        stats.put("accuracy", stats.getOrDefault("accuracy", 70) + (level * 2));
        stats.put("defense", stats.getOrDefault("defense", 10) + (level * 3));
        stats.put("movement", stats.getOrDefault("movement", 3) + (level / 2));
        
        // Special stat increases for specific aliens
        switch (alienType) {
            case ADVENT_BERSERKER:
                stats.put("rage_bonus", stats.getOrDefault("rage_bonus", 0) + (level * 5));
                break;
            case ADVENT_ARCHON:
                stats.put("flight_range", stats.getOrDefault("flight_range", 0) + (level * 2));
                break;
            case ADVENT_VIPER:
                stats.put("poison_damage", stats.getOrDefault("poison_damage", 0) + (level * 3));
                break;
            case ADVENT_MUTON:
                stats.put("melee_damage", stats.getOrDefault("melee_damage", 0) + (level * 8));
                break;
            case ADVENT_TROOPER:
                stats.put("tactical_bonus", stats.getOrDefault("tactical_bonus", 0) + (level * 2));
                break;
            case ADVENT_OFFICER:
                stats.put("leadership_bonus", stats.getOrDefault("leadership_bonus", 0) + (level * 3));
                break;
            case ADVENT_STUN_LANCER:
                stats.put("stun_damage", stats.getOrDefault("stun_damage", 0) + (level * 4));
                break;
            case ADVENT_MEC:
                stats.put("armor_bonus", stats.getOrDefault("armor_bonus", 0) + (level * 6));
                break;
            case ADVENT_PRIEST:
                stats.put("psionic_power", stats.getOrDefault("psionic_power", 0) + (level * 4));
                break;
            case ADVENT_SECTOPOD:
                stats.put("heavy_damage", stats.getOrDefault("heavy_damage", 0) + (level * 7));
                break;
            case ADVENT_ANDROMEDON:
                stats.put("acid_damage", stats.getOrDefault("acid_damage", 0) + (level * 5));
                break;
            case ADVENT_CODEX:
                stats.put("psionic_clone", stats.getOrDefault("psionic_clone", 0) + (level * 3));
                break;
            case ADVENT_GATEKEEPER:
                stats.put("gate_power", stats.getOrDefault("gate_power", 0) + (level * 6));
                break;
            case ADVENT_AVATAR:
                stats.put("avatar_power", stats.getOrDefault("avatar_power", 0) + (level * 8));
                break;
        }
    }
    
    /**
     * Get evolved alien unit
     */
    public Unit getEvolvedAlien(AlienType alienType) {
        int level = alienLevels.get(alienType);
        Map<String, Integer> stats = alienStats.get(alienType);
        
        // Create evolved alien with enhanced stats
        Unit evolvedAlien = new Unit(
            alienType.name() + " (Level " + level + ")",
            stats.getOrDefault("health", 100 + (level * 10)),
            stats.getOrDefault("movement", 3 + (level / 2)),
            8, // attack range
            stats.getOrDefault("damage", 20 + (level * 5)),
            com.aliensattack.core.enums.UnitType.ALIEN
        );
        
        // Add evolved abilities
        List<String> abilities = alienAbilities.get(alienType);
        for (String ability : abilities) {
            // Add ability to unit (simplified)
            evolvedAlien.addAbility(new SoldierAbility(ability, "Evolved alien ability", 1, 2));
        }
        
        return evolvedAlien;
    }
    
    /**
     * Get evolution statistics
     */
    public String getEvolutionStatistics() {
        StringBuilder stats = new StringBuilder();
        stats.append("Alien Evolution - Total Level: ").append(totalEvolutionLevel);
        stats.append(", Missions: ").append(missionsCompleted);
        stats.append("\nAlien Levels:\n");
        
        for (Map.Entry<AlienType, Integer> entry : alienLevels.entrySet()) {
            stats.append("  ").append(entry.getKey()).append(": Level ").append(entry.getValue()).append("\n");
        }
        
        return stats.toString();
    }
    
    /**
     * Get evolution events
     */
    public List<String> getEvolutionEvents() {
        return new ArrayList<>(evolutionEvents);
    }
    
    /**
     * Check if alien type has evolved
     */
    public boolean hasEvolved(AlienType alienType) {
        return alienLevels.get(alienType) > 1;
    }
    
    /**
     * Get alien evolution level
     */
    public int getAlienLevel(AlienType alienType) {
        return alienLevels.get(alienType);
    }
    
    /**
     * Get alien abilities
     */
    public List<String> getAlienAbilities(AlienType alienType) {
        return new ArrayList<>(alienAbilities.get(alienType));
    }
    
    /**
     * Get alien stats
     */
    public Map<String, Integer> getAlienStats(AlienType alienType) {
        return new HashMap<>(alienStats.get(alienType));
    }
    
    private void logEvolutionEvent(String event) {
        evolutionEvents.add(System.currentTimeMillis() + ": " + event);
        System.out.println("Alien Evolution: " + event);
    }
}
