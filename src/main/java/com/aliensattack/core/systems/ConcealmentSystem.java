package com.aliensattack.core.systems;

import com.aliensattack.core.interfaces.IConcealmentSystem;
import com.aliensattack.core.model.Unit;
import com.aliensattack.core.model.Position;
import com.aliensattack.core.model.CombatAction;
import com.aliensattack.core.enums.ConcealmentStatus;
import com.aliensattack.core.enums.ConcealmentBreakReason;
import lombok.Data;
import lombok.extern.log4j.Log4j2;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Comprehensive Concealment System for XCOM2 Tactical Combat
 * Manages stealth mechanics, detection, and concealment break handling
 */
@Log4j2
public class ConcealmentSystem implements IConcealmentSystem {
    
    private final Map<String, ConcealmentEffect> activeEffects;
    private final DetectionSystem detectionSystem;
    private final ConcealmentBreakHandler breakHandler;
    private final StealthCombatBonus stealthBonus;
    
    public ConcealmentSystem() {
        this.activeEffects = new ConcurrentHashMap<>();
        this.detectionSystem = new DetectionSystem();
        this.breakHandler = new ConcealmentBreakHandler();
        this.stealthBonus = new StealthCombatBonus();
    }
    
    @Override
    public boolean isConcealed(Unit unit) {
        ConcealmentEffect effect = activeEffects.get(unit.getId());
        return effect != null && effect.getStatus() == ConcealmentStatus.CONCEALED;
    }
    
    @Override
    public void breakConcealment(Unit unit, ConcealmentBreakReason reason) {
        ConcealmentEffect effect = activeEffects.get(unit.getId());
        if (effect != null) {
            breakHandler.handleConcealmentBreak(effect, reason);
            activeEffects.remove(unit.getId());
            log.info("Concealment broken for {}: {}", unit.getName(), reason);
        }
    }
    
    @Override
    public double calculateDetectionChance(Unit unit, Unit enemy) {
        return detectionSystem.calculateDetectionChance(unit, enemy);
    }
    
    @Override
    public void applyConcealmentBonus(CombatAction action) {
        stealthBonus.applyBonus(action);
    }
    
    @Override
    public List<ConcealmentEffect> getActiveEffects() {
        return new ArrayList<>(activeEffects.values());
    }
    
    public void updateConcealment(Unit unit, Position newPosition) {
        if (isConcealed(unit)) {
            double detectionChance = detectionSystem.calculateDetectionChance(unit, newPosition);
            if (detectionChance > getDetectionThreshold()) {
                breakConcealment(unit, ConcealmentBreakReason.DETECTED);
            }
        }
    }
    
    public void establishConcealment(Unit unit) {
        ConcealmentEffect effect = new ConcealmentEffect(unit);
        activeEffects.put(unit.getId(), effect);
        unit.setConcealmentStatus(ConcealmentStatus.CONCEALED);
        log.info("Concealment established for {}", unit.getName());
    }
    
    private double getDetectionThreshold() {
        return 0.8; // 80% detection threshold
    }
    
    // Inner classes for concealment system components
    @Data
    public static class ConcealmentEffect {
        private final String id;
        private final Unit unit;
        private final long startTime;
        private final int duration;
        private ConcealmentStatus status;
        private double strength;
        
        public ConcealmentEffect(Unit unit) {
            this.id = UUID.randomUUID().toString();
            this.unit = unit;
            this.startTime = System.currentTimeMillis();
            this.duration = 30000; // 30 seconds default duration
            this.status = ConcealmentStatus.CONCEALED;
            this.strength = 1.0; // Default strength
        }
        
        public boolean isExpired() {
            return System.currentTimeMillis() - startTime > duration;
        }
    }
    
    public static class DetectionSystem {
        
        public double calculateDetectionChance(Unit unit, Unit enemy) {
            double baseChance = getBaseDetectionChance(enemy);
            double coverModifier = getCoverModifier(unit.getPosition());
            double distanceModifier = getDistanceModifier(unit.getPosition(), enemy.getPosition());
            double lightingModifier = getLightingModifier(unit.getPosition());
            
            return baseChance * coverModifier * distanceModifier * lightingModifier;
        }
        
        public double calculateDetectionChance(Unit unit, Position position) {
            double baseChance = 0.5; // Default detection chance for position-based detection
            double coverModifier = getCoverModifier(position);
            double distanceModifier = getDistanceModifier(unit.getPosition(), position);
            double lightingModifier = getLightingModifier(position);
            
            return baseChance * coverModifier * distanceModifier * lightingModifier;
        }
        
        private double getBaseDetectionChance(Unit enemy) {
            return 0.5; // Default detection chance
        }
        
        private double getCoverModifier(Position position) {
            // Simplified cover calculation
            return 0.7; // 30% reduction due to cover
        }
        
        private double getDistanceModifier(Position unitPos, Position enemyPos) {
            double distance = calculateDistance(unitPos, enemyPos);
            if (distance <= 3) return 1.0;      // Close range
            if (distance <= 6) return 0.8;      // Medium range
            if (distance <= 10) return 0.6;     // Long range
            return 0.4;                         // Very long range
        }
        
        private double getLightingModifier(Position position) {
            // Simplified lighting calculation
            return 0.8; // 20% reduction due to lighting
        }
        
        private double calculateDistance(Position pos1, Position pos2) {
            int dx = pos1.getX() - pos2.getX();
            int dy = pos1.getY() - pos2.getY();
            return Math.sqrt(dx * dx + dy * dy);
        }
    }
    
    public static class ConcealmentBreakHandler {
        
        public void handleConcealmentBreak(ConcealmentEffect effect, ConcealmentBreakReason reason) {
            effect.getUnit().setConcealmentStatus(ConcealmentStatus.BROKEN);
            
            switch (reason) {
                case ATTACK -> handleAttackBreak(effect);
                case DETECTED -> handleDetectionBreak(effect);
                case MOVEMENT -> handleMovementBreak(effect);
                case ABILITY_USE -> handleAbilityBreak(effect);
                default -> handleGenericBreak(effect);
            }
        }
        
        private void handleAttackBreak(ConcealmentEffect effect) {
            // Apply penalties for breaking concealment with attack
            effect.getUnit().addStatusEffect("CONCEALMENT_BREAK_PENALTY", 3);
        }
        
        private void handleDetectionBreak(ConcealmentEffect effect) {
            // Handle detection by enemy
            effect.getUnit().addStatusEffect("DETECTED", 2);
        }
        
        private void handleMovementBreak(ConcealmentEffect effect) {
            // Handle movement-based concealment break
            effect.getUnit().addStatusEffect("MOVEMENT_PENALTY", 1);
        }
        
        private void handleAbilityBreak(ConcealmentEffect effect) {
            // Handle ability use concealment break
            effect.getUnit().addStatusEffect("ABILITY_COOLDOWN", 2);
        }
        
        private void handleGenericBreak(ConcealmentEffect effect) {
            // Generic concealment break handling
            effect.getUnit().addStatusEffect("CONCEALMENT_LOSS", 1);
        }
    }
    
    public static class StealthCombatBonus {
        
        public void applyBonus(CombatAction action) {
            if (action.getAttacker().getConcealmentStatus() == ConcealmentStatus.CONCEALED) {
                action.setDamage((int)(action.getDamage() * 1.5)); // 50% damage bonus
                action.setAccuracy(action.getAccuracy() + 25);     // 25% accuracy bonus
                action.setCriticalChance(action.getCriticalChance() + 0.3); // 30% crit bonus
            }
        }
    }
}
