package com.aliensattack.core.systems;

import com.aliensattack.core.interfaces.ISuppressionSystem;
import com.aliensattack.core.model.Unit;
import com.aliensattack.core.config.GameConfig;
import lombok.extern.log4j.Log4j2;

import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Advanced implementation of suppression system with enhanced features
 * Provides sophisticated suppression mechanics and effects
 */
@Log4j2
public class AdvancedSuppressionSystem implements ISuppressionSystem {
    
    private final Map<Unit, List<SuppressionSystem.SuppressionEffect>> suppressionEffects;
    private final Map<Unit, Integer> suppressionDuration;
    
    public AdvancedSuppressionSystem() {
        this.suppressionEffects = new HashMap<>();
        this.suppressionDuration = new HashMap<>();
        log.info("Advanced suppression system initialized");
    }
    
    @Override
    public void applySuppression(Unit target, Unit suppressor) {
        log.info("Applying advanced suppression to {} from {}", target.getName(), suppressor.getName());
        
        // Calculate suppression chance
        double chance = calculateSuppressionChance(suppressor, target);
        
        if (Math.random() < chance) {
            // Create suppression effects using existing system
            SuppressionSystem baseSystem = new SuppressionSystem();
            baseSystem.applySuppression(target, suppressor);
            
            // Track duration for advanced features
            suppressionDuration.put(target, GameConfig.getInt("combat.suppression.duration", 2));
            
            log.info("Advanced suppression applied to {}", target.getName());
        } else {
            log.info("Suppression failed on {}", target.getName());
        }
    }
    
    @Override
    public boolean isSuppressed(Unit unit) {
        SuppressionSystem baseSystem = new SuppressionSystem();
        return baseSystem.isSuppressed(unit);
    }
    
    @Override
    public List<SuppressionSystem.SuppressionEffect> getSuppressionEffects(Unit unit) {
        SuppressionSystem baseSystem = new SuppressionSystem();
        return baseSystem.getSuppressionEffects(unit);
    }
    
    @Override
    public void removeSuppression(Unit unit) {
        SuppressionSystem baseSystem = new SuppressionSystem();
        baseSystem.removeSuppression(unit);
        suppressionDuration.remove(unit);
        log.info("Advanced suppression removed from {}", unit.getName());
    }
    
    @Override
    public double calculateSuppressionChance(Unit attacker, Unit target) {
        double baseChance = 0.7; // 70% base chance
        
        // Modify based on attacker's suppression skill
        double attackerBonus = attacker.getSuppressionSkill() / 100.0;
        
        // Modify based on distance
        double distanceModifier = calculateDistanceModifier(attacker, target);
        
        double finalChance = baseChance * (1 + attackerBonus) * distanceModifier;
        
        return Math.max(0.1, Math.min(0.95, finalChance));
    }
    
    /**
     * Calculate distance modifier for suppression
     */
    private double calculateDistanceModifier(Unit attacker, Unit target) {
        // Simple distance calculation
        int dx = Math.abs(attacker.getPosition().getX() - target.getPosition().getX());
        int dy = Math.abs(attacker.getPosition().getY() - target.getPosition().getY());
        double distance = Math.sqrt(dx * dx + dy * dy);
        
        if (distance <= 3) return 1.2;      // Close range bonus
        if (distance <= 6) return 1.0;      // Medium range
        if (distance <= 10) return 0.8;     // Long range penalty
        return 0.6;                         // Very long range penalty
    }
    
    /**
     * Update suppression effects (called each turn)
     */
    public void updateSuppressionEffects() {
        suppressionDuration.entrySet().removeIf(entry -> {
            Unit unit = entry.getKey();
            int duration = entry.getValue();
            
            if (duration <= 1) {
                log.info("Advanced suppression expired for {}", unit.getName());
                return true;
            } else {
                entry.setValue(duration - 1);
                return false;
            }
        });
    }
    
    /**
     * Attempt to break suppression through willpower
     */
    public boolean attemptSuppressionBreak(Unit unit) {
        if (!isSuppressed(unit)) {
            return false;
        }
        
        double breakChance = GameConfig.getDouble("combat.suppression.break.chance", 0.3);
        
        if (Math.random() < breakChance) {
            removeSuppression(unit);
            log.info("{} broke free from advanced suppression", unit.getName());
            return true;
        }
        
        return false;
    }
}
