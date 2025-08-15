package com.aliensattack.core.systems;

import com.aliensattack.core.interfaces.ISuppressionSystem;
import com.aliensattack.core.model.Unit;
import com.aliensattack.core.model.CombatAction;
import com.aliensattack.core.model.Position;
import com.aliensattack.core.enums.ActionType;
import lombok.Data;
import lombok.extern.log4j.Log4j2;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Suppression System - XCOM2 Tactical Combat
 * Manages suppression mechanics, action restrictions, and visual effects
 */
@Log4j2
public class SuppressionSystem implements ISuppressionSystem {
    
    private final Map<String, SuppressionEffect> activeEffects;
    private final SuppressionVisualEffects visualEffects;
    private final SuppressionTactics tactics;
    
    public SuppressionSystem() {
        this.activeEffects = new ConcurrentHashMap<>();
        this.visualEffects = new SuppressionVisualEffects();
        this.tactics = new SuppressionTactics();
    }
    
    @Override
    public void applySuppression(Unit target, Unit suppressor) {
        SuppressionEffect effect = new SuppressionEffect(target, suppressor);
        activeEffects.put(target.getId(), effect);
        
        target.setSuppressed(true);
        visualEffects.applySuppressionEffects(target);
        
        log.info("Suppression applied to {} by {}", target.getName(), suppressor.getName());
    }
    
    @Override
    public boolean isSuppressed(Unit unit) {
        SuppressionEffect effect = activeEffects.get(unit.getId());
        return effect != null && effect.isActive();
    }
    
    @Override
    public List<SuppressionEffect> getSuppressionEffects(Unit unit) {
        SuppressionEffect effect = activeEffects.get(unit.getId());
        return effect != null ? List.of(effect) : List.of();
    }
    
    @Override
    public void removeSuppression(Unit unit) {
        SuppressionEffect effect = activeEffects.remove(unit.getId());
        if (effect != null) {
            unit.setSuppressed(false);
            visualEffects.removeSuppressionEffects(unit);
            log.info("Suppression removed from {}", unit.getName());
        }
    }
    
    @Override
    public double calculateSuppressionChance(Unit attacker, Unit target) {
        return tactics.calculateSuppressionChance(attacker, target);
    }
    
    public void updateSuppression() {
        Iterator<Map.Entry<String, SuppressionEffect>> iterator = activeEffects.entrySet().iterator();
        
        while (iterator.hasNext()) {
            Map.Entry<String, SuppressionEffect> entry = iterator.next();
            SuppressionEffect effect = entry.getValue();
            
            effect.update();
            
            if (!effect.isActive()) {
                removeSuppression(effect.getTarget());
                iterator.remove();
            }
        }
    }
    
    public boolean canPerformAction(Unit unit, ActionType actionType) {
        if (!isSuppressed(unit)) {
            return true;
        }
        
        SuppressionEffect effect = activeEffects.get(unit.getId());
        return effect.canPerformAction(actionType);
    }
    
    // Inner classes for suppression system components
    @Data
    public static class SuppressionEffect {
        private final String id;
        private final Unit target;
        private final Unit suppressor;
        private final long startTime;
        private final int duration;
        private final double strength;
        private boolean active;
        
        public SuppressionEffect(Unit target, Unit suppressor) {
            this.id = UUID.randomUUID().toString();
            this.target = target;
            this.suppressor = suppressor;
            this.startTime = System.currentTimeMillis();
            this.duration = calculateDuration(suppressor);
            this.strength = calculateStrength(suppressor);
            this.active = true;
        }
        
        public void update() {
            if (System.currentTimeMillis() - startTime > duration) {
                active = false;
            }
        }
        
        public double getAccuracyPenalty() {
            return strength * 0.3; // 30% accuracy penalty
        }
        
        public boolean canPerformAction(ActionType actionType) {
            return switch (actionType) {
                case MOVE -> strength < 0.7; // Weak restrictions on movement
                case ATTACK -> strength < 0.5; // Strong restrictions on attack
                case SPECIAL_ABILITY -> strength < 0.3; // Very strong restrictions on abilities
                case OVERWATCH -> strength < 0.6; // Moderate restrictions on overwatch
                case RELOAD -> strength < 0.8; // Weak restrictions on reload
                case HEAL -> strength < 0.4; // Strong restrictions on healing
                case GRENADE -> strength < 0.2; // Very strong restrictions on grenades
                case DEFEND -> strength < 0.9; // Minimal restrictions on defend
                case DASH -> strength < 0.1; // Almost no dash allowed
                case HACK -> strength < 0.3; // Strong restrictions on hacking
                default -> true;
            };
        }
        
        private int calculateDuration(Unit suppressor) {
            // Base duration based on suppressor's suppression skill
            int baseDuration = 15000; // 15 seconds base
            int skillBonus = suppressor.getSuppressionSkill() * 1000; // 1 second per skill level
            return baseDuration + skillBonus;
        }
        
        private double calculateStrength(Unit suppressor) {
            // Base strength based on suppressor's weapon and skill
            double baseStrength = 0.5;
            double weaponBonus = suppressor.getSuppressionWeaponBonus();
            double skillBonus = suppressor.getSuppressionSkill() * 0.1;
            return Math.min(1.0, baseStrength + weaponBonus + skillBonus);
        }
    }
    
    public static class SuppressionVisualEffects {
        
        public void applySuppressionEffects(Unit unit) {
            // Apply visual suppression effects
            log.info("Applying suppression visual effects to {}", unit.getName());
        }
        
        public void removeSuppressionEffects(Unit unit) {
            // Remove visual suppression effects
            log.info("Removing suppression visual effects from {}", unit.getName());
        }
        
        public void showSuppressionIndicator(Unit unit) {
            // Show suppression indicator
            log.info("Showing suppression indicator for {}", unit.getName());
        }
    }
    
    public static class SuppressionTactics {
        
        public double calculateSuppressionChance(Unit attacker, Unit target) {
            double baseChance = 0.6; // 60% base suppression chance
            
            // Weapon type modifier
            if (attacker.hasSuppressionWeapon()) {
                baseChance += 0.2; // 20% bonus for suppression weapons
            }
            
            // Distance modifier
            double distance = calculateDistance(attacker.getPosition(), target.getPosition());
            if (distance <= 3) {
                baseChance += 0.1; // 10% bonus for close range
            } else if (distance > 8) {
                baseChance -= 0.2; // 20% penalty for long range
            }
            
            // Cover modifier
            if (target.hasCover()) {
                baseChance -= 0.15; // 15% penalty if target has cover
            }
            
            // Skill modifier
            baseChance += attacker.getSuppressionSkill() * 0.05; // 5% per skill level
            
            return Math.max(0.1, Math.min(0.95, baseChance)); // Clamp between 10% and 95%
        }
        
        private double calculateDistance(Position pos1, Position pos2) {
            int dx = pos1.getX() - pos2.getX();
            int dy = pos1.getY() - pos2.getY();
            return Math.sqrt(dx * dx + dy * dy);
        }
        
        public List<Position> getOptimalSuppressionPositions(Unit suppressor, Unit target) {
            List<Position> positions = new ArrayList<>();
            
            // Calculate positions that provide good suppression angles
            Position targetPos = target.getPosition();
            
            for (int x = -2; x <= 2; x++) {
                for (int y = -2; y <= 2; y++) {
                    if (x == 0 && y == 0) continue; // Skip target position
                    
                    Position pos = new Position(targetPos.getX() + x, targetPos.getY() + y);
                    if (isValidSuppressionPosition(pos, suppressor, target)) {
                        positions.add(pos);
                    }
                }
            }
            
            return positions;
        }
        
        private boolean isValidSuppressionPosition(Position position, Unit suppressor, Unit target) {
            // Basic validation for suppression position
            return position.getX() >= 0 && position.getY() >= 0;
        }
    }
}
