package com.aliensattack.core.systems;

import com.aliensattack.core.interfaces.IHeightAdvantageSystem;
import com.aliensattack.core.model.Position;
import com.aliensattack.core.model.Unit;
import com.aliensattack.core.enums.HeightLevel;
import lombok.Data;
import lombok.extern.log4j.Log4j2;

import java.util.*;

/**
 * Height Advantage System - XCOM2 Tactical Combat
 * Manages elevation-based bonuses, penalties, and line of sight calculations
 */
@Log4j2
public class HeightAdvantageSystem implements IHeightAdvantageSystem {
    
    private final HeightManager heightManager;
    private final HeightBonusCalculator bonusCalculator;
    private final ElevationSystem elevationSystem;
    
    public HeightAdvantageSystem() {
        this.heightManager = new HeightManager();
        this.bonusCalculator = new HeightBonusCalculator();
        this.elevationSystem = new ElevationSystem();
    }
    
    @Override
    public HeightLevel getHeightLevel(Position position) {
        return heightManager.getHeightLevel(position);
    }
    
    @Override
    public double calculateHeightBonus(Unit attacker, Unit target) {
        HeightLevel attackerHeight = getHeightLevel(attacker.getPosition());
        HeightLevel targetHeight = getHeightLevel(target.getPosition());
        
        return bonusCalculator.calculateBonus(attackerHeight, targetHeight);
    }
    
    @Override
    public double calculateHeightPenalty(Unit attacker, Unit target) {
        HeightLevel attackerHeight = getHeightLevel(attacker.getPosition());
        HeightLevel targetHeight = getHeightLevel(target.getPosition());
        
        return bonusCalculator.calculatePenalty(attackerHeight, targetHeight);
    }
    
    @Override
    public boolean hasLineOfSight(Position from, Position to) {
        HeightLevel fromHeight = getHeightLevel(from);
        HeightLevel toHeight = getHeightLevel(to);
        
        return elevationSystem.checkLineOfSight(from, to, fromHeight, toHeight);
    }
    
    @Override
    public List<HeightModifier> getHeightModifiers() {
        return heightManager.getAllHeightModifiers();
    }
    
    // Inner classes for height advantage system components
    public static class HeightManager {
        
        private final Map<Position, HeightLevel> heightMap;
        private final List<HeightModifier> heightModifiers;
        
        public HeightManager() {
            this.heightMap = new HashMap<>();
            this.heightModifiers = new ArrayList<>();
            initializeHeightModifiers();
        }
        
        public HeightLevel getHeightLevel(Position position) {
            return heightMap.getOrDefault(position, HeightLevel.GROUND);
        }
        
        public void setHeightLevel(Position position, HeightLevel level) {
            heightMap.put(position, level);
        }
        
        public List<HeightModifier> getAllHeightModifiers() {
            return new ArrayList<>(heightModifiers);
        }
        
        private void initializeHeightModifiers() {
            heightModifiers.add(new HeightModifier(HeightLevel.GROUND, HeightLevel.LOW, 1.0, "No height advantage"));
            heightModifiers.add(new HeightModifier(HeightLevel.GROUND, HeightLevel.MEDIUM, 1.2, "Low height advantage"));
            heightModifiers.add(new HeightModifier(HeightLevel.GROUND, HeightLevel.HIGH, 1.5, "High height advantage"));
            heightModifiers.add(new HeightModifier(HeightLevel.LOW, HeightLevel.GROUND, 0.9, "Height penalty"));
            heightModifiers.add(new HeightModifier(HeightLevel.LOW, HeightLevel.MEDIUM, 1.1, "Minor height advantage"));
            heightModifiers.add(new HeightModifier(HeightLevel.LOW, HeightLevel.HIGH, 1.3, "Medium height advantage"));
            heightModifiers.add(new HeightModifier(HeightLevel.MEDIUM, HeightLevel.GROUND, 0.8, "Significant height penalty"));
            heightModifiers.add(new HeightModifier(HeightLevel.MEDIUM, HeightLevel.LOW, 0.9, "Height penalty"));
            heightModifiers.add(new HeightModifier(HeightLevel.MEDIUM, HeightLevel.HIGH, 1.2, "Minor height advantage"));
            heightModifiers.add(new HeightModifier(HeightLevel.HIGH, HeightLevel.GROUND, 0.7, "Major height penalty"));
            heightModifiers.add(new HeightModifier(HeightLevel.HIGH, HeightLevel.LOW, 0.8, "Significant height penalty"));
            heightModifiers.add(new HeightModifier(HeightLevel.HIGH, HeightLevel.MEDIUM, 0.9, "Height penalty"));
        }
    }
    
    public static class HeightBonusCalculator {
        
        public double calculateBonus(HeightLevel attacker, HeightLevel target) {
            int heightDifference = attacker.ordinal() - target.ordinal();
            
            return switch (heightDifference) {
                case 3 -> 1.5; // High -> Ground
                case 2 -> 1.3; // Medium -> Ground
                case 1 -> 1.1; // Low -> Ground
                case 0 -> 1.0; // Same height
                case -1 -> 0.9; // Ground -> Low
                case -2 -> 0.7; // Ground -> Medium
                case -3 -> 0.5; // Ground -> High
                default -> 1.0;
            };
        }
        
        public double calculatePenalty(HeightLevel attacker, HeightLevel target) {
            return 1.0 / calculateBonus(attacker, target);
        }
        
        public double calculateAccuracyBonus(HeightLevel attacker, HeightLevel target) {
            int heightDifference = attacker.ordinal() - target.ordinal();
            
            if (heightDifference > 0) {
                return heightDifference * 10; // 10% accuracy bonus per level
            } else if (heightDifference < 0) {
                return heightDifference * 15; // 15% accuracy penalty per level
            }
            return 0.0;
        }
        
        public double calculateCriticalBonus(HeightLevel attacker, HeightLevel target) {
            int heightDifference = attacker.ordinal() - target.ordinal();
            
            if (heightDifference > 0) {
                return heightDifference * 0.1; // 10% crit bonus per level
            }
            return 0.0;
        }
    }
    
    public static class ElevationSystem {
        
        public boolean checkLineOfSight(Position from, Position to, HeightLevel fromHeight, HeightLevel toHeight) {
            // Basic line of sight check with height consideration
            if (fromHeight == HeightLevel.GROUND && toHeight == HeightLevel.HIGH) {
                return false; // Can't see high ground from ground level
            }
            
            if (fromHeight == HeightLevel.LOW && toHeight == HeightLevel.HIGH) {
                return Math.random() > 0.3; // 70% chance to see high ground from low
            }
            
            return true; // Default line of sight
        }
        
        public double calculateElevationModifier(Position from, Position to) {
            HeightLevel fromHeight = HeightLevel.GROUND; // Default
            HeightLevel toHeight = HeightLevel.GROUND;   // Default
            
            int heightDifference = fromHeight.ordinal() - toHeight.ordinal();
            
            if (heightDifference > 0) {
                return 1.0 + (heightDifference * 0.2); // 20% bonus per level
            } else if (heightDifference < 0) {
                return 1.0 + (heightDifference * 0.3); // 30% penalty per level
            }
            
            return 1.0; // No height difference
        }
    }
    
    @Data
    public static class HeightModifier {
        private final HeightLevel fromLevel;
        private final HeightLevel toLevel;
        private final double modifier;
        private final String description;
        
        public HeightModifier(HeightLevel fromLevel, HeightLevel toLevel, double modifier, String description) {
            this.fromLevel = fromLevel;
            this.toLevel = toLevel;
            this.modifier = modifier;
            this.description = description;
        }
    }
}
