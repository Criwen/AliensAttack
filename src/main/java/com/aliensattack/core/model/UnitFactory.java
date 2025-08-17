package com.aliensattack.core.model;

import com.aliensattack.core.enums.UnitType;
import com.aliensattack.core.enums.SoldierClass;
import com.aliensattack.core.enums.AlienType;
import com.aliensattack.core.config.GameConfig;
import com.aliensattack.core.model.components.CombatStats;
import com.aliensattack.core.model.components.EquipmentManager;
import com.aliensattack.core.model.components.StatusEffectManager;
import com.aliensattack.core.model.components.MedicalStatus;
import lombok.extern.log4j.Log4j2;

/**
 * Factory for creating different types of units
 * Provides standardized unit creation with proper initialization
 */
@Log4j2
public class UnitFactory {
    
    /**
     * Create a basic unit with specified parameters
     */
    public static Unit createUnit(UnitType unitType, String name, int maxHealth, 
                                int movementRange, int attackRange, int attackDamage) {
        Unit unit = new Unit(name, maxHealth, movementRange, attackRange, attackDamage, unitType);
        
        // Set default unit properties
        unit.setActionPoints(GameConfig.getDefaultActionPoints());
        unit.setOverwatchChance(GameConfig.getDefaultOverwatchChance());
        unit.setCriticalChance(GameConfig.getDefaultCriticalChance());
        
        log.debug("Created unit: {} with {} action points", name, unit.getActionPoints());
        return unit;
    }
    
    /**
     * Create a soldier with default stats
     */
    public static Soldier createSoldier(String name, int maxHealth, int movementRange, 
                                      int attackRange, int attackDamage) {
        Soldier soldier = new Soldier(name, maxHealth, movementRange, attackRange, attackDamage);
        
        // Set default soldier properties
        soldier.setActionPoints(GameConfig.getDefaultActionPoints());
        soldier.setOverwatchChance(GameConfig.getDefaultOverwatchChance());
        soldier.setCriticalChance(GameConfig.getDefaultCriticalChance());
        
        log.debug("Created soldier: {} with {} action points", name, soldier.getActionPoints());
        return soldier;
    }
    
    /**
     * Create an alien with default stats
     */
    public static Alien createAlien(String name, int maxHealth, int movementRange, 
                                  int attackRange, int attackDamage) {
        Alien alien = new Alien(name, maxHealth, movementRange, attackRange, attackDamage, AlienType.ADVENT_TROOPER);
        
        // Set default alien properties
        alien.setActionPoints(GameConfig.getDefaultActionPoints());
        alien.setEvolutionLevel(1);
        
        log.debug("Created alien: {} with evolution level {}", name, alien.getEvolutionLevel());
        return alien;
    }
    
    /**
     * Create a refactored unit with component-based architecture
     */
    public static RefactoredUnit createRefactoredUnit(String name, int maxHealth, 
                                                    int movementRange, int attackRange, int attackDamage) {
        RefactoredUnit unit = new RefactoredUnit(name, maxHealth, movementRange, attackRange, attackDamage, UnitType.SOLDIER);
        
        // Initialize components
        unit.setCombatStats(new CombatStats(maxHealth, maxHealth, movementRange, attackRange, attackDamage, 8, 0, 10, 5, 1, 10));
        unit.setEquipment(new EquipmentManager());
        unit.setStatusEffects(new StatusEffectManager());
        unit.setMedicalStatus(new MedicalStatus());
        
        log.debug("Created refactored unit: {} with component architecture", name);
        return unit;
    }
    
    /**
     * Create a unit with specific soldier class
     */
    public static Soldier createSoldierWithClass(String name, SoldierClass soldierClass) {
        int baseHealth = 100;
        int baseMovement = 3;
        int baseAttackRange = 8;
        int baseAttackDamage = 15;
        
        // Apply class-specific modifiers
        switch (soldierClass) {
            case HEAVY:
                baseHealth += 20;
                baseAttackDamage += 5;
                baseMovement -= 1;
                break;
            case SNIPER:
                baseAttackRange += 4;
                baseAttackDamage += 3;
                baseMovement -= 1;
                break;
            case ASSAULT:
                baseMovement += 1;
                baseAttackDamage += 2;
                break;
            case SUPPORT:
                baseHealth += 10;
                baseMovement += 1;
                break;
        }
        
        Soldier soldier = createSoldier(name, baseHealth, baseMovement, baseAttackRange, baseAttackDamage);
        soldier.setSoldierClass(soldierClass);
        
        log.info("Created {} soldier: {} with class-specific stats", soldierClass, name);
        return soldier;
    }
    
    /**
     * Create a unit with random stats for testing
     */
    public static Unit createRandomUnit(String name, UnitType unitType) {
        int maxHealth = 80 + (int)(Math.random() * 40);  // 80-120
        int movementRange = 2 + (int)(Math.random() * 3); // 2-5
        int attackRange = 6 + (int)(Math.random() * 6);   // 6-12
        int attackDamage = 10 + (int)(Math.random() * 15); // 10-25
        
        return createUnit(unitType, name, maxHealth, movementRange, attackRange, attackDamage);
    }
}
