package com.aliensattack.core.systems;

import com.aliensattack.core.interfaces.IFlankingSystem;
import com.aliensattack.core.model.Unit;
import com.aliensattack.core.model.Position;
import com.aliensattack.core.model.CombatAction;
import lombok.Data;
import lombok.extern.log4j.Log4j2;

import java.util.*;

/**
 * Flanking System - XCOM2 Tactical Combat
 * Manages flanking mechanics, bonuses, and tactical positioning
 */
@Log4j2
public class FlankingSystem implements IFlankingSystem {
    
    private final FlankingCalculator calculator;
    private final FlankingBonusManager bonusManager;
    private final FlankingVisualIndicator visualIndicator;
    
    public FlankingSystem() {
        this.calculator = new FlankingCalculator();
        this.bonusManager = new FlankingBonusManager();
        this.visualIndicator = new FlankingVisualIndicator();
    }
    
    @Override
    public boolean isFlanking(Unit attacker, Unit target) {
        return calculator.isFlanking(attacker.getPosition(), target.getPosition());
    }
    
    @Override
    public double calculateFlankingBonus(Unit attacker, Unit target) {
        if (isFlanking(attacker, target)) {
            return bonusManager.getFlankingBonus(attacker, target);
        }
        return 1.0;
    }
    
    @Override
    public Position findFlankingPosition(Unit attacker, Unit target) {
        List<Position> flankingPositions = getFlankingPositions(target);
        return findBestFlankingPosition(attacker, flankingPositions);
    }
    
    @Override
    public List<Position> getFlankingPositions(Unit target) {
        return calculator.calculateFlankingPositions(target.getPosition());
    }
    
    @Override
    public void applyFlankingEffects(CombatAction action) {
        if (isFlanking(action.getAttacker(), action.getTarget())) {
            bonusManager.applyFlankingBonuses(action);
            visualIndicator.showFlankingEffect(action);
            log.info("Flanking bonuses applied to action: {}", action.getActionId());
        }
    }
    
    private Position findBestFlankingPosition(Unit attacker, List<Position> flankingPositions) {
        if (flankingPositions.isEmpty()) {
            return null;
        }
        
        // Find position with best tactical advantage
        return flankingPositions.stream()
                .filter(pos -> isValidPosition(pos))
                .max(Comparator.comparingDouble(pos -> calculatePositionValue(pos, attacker)))
                .orElse(flankingPositions.get(0));
    }
    
    private boolean isValidPosition(Position position) {
        // Basic position validation
        return position.getX() >= 0 && position.getY() >= 0;
    }
    
    private double calculatePositionValue(Position position, Unit unit) {
        // Calculate tactical value of position
        double value = 0.0;
        
        // Distance from current position
        double distance = calculateDistance(unit.getPosition(), position);
        value += (10.0 - distance) * 0.1; // Closer is better
        
        // Cover value
        if (hasCover(position)) {
            value += 2.0;
        }
        
        // Height advantage
        if (hasHeightAdvantage(position)) {
            value += 1.5;
        }
        
        return value;
    }
    
    private boolean hasCover(Position position) {
        // Simplified cover check
        return Math.random() > 0.5;
    }
    
    private boolean hasHeightAdvantage(Position position) {
        // Simplified height check
        return Math.random() > 0.7;
    }
    
    private double calculateDistance(Position pos1, Position pos2) {
        int dx = pos1.getX() - pos2.getX();
        int dy = pos1.getY() - pos2.getY();
        return Math.sqrt(dx * dx + dy * dy);
    }
    
    // Inner classes for flanking system components
    public static class FlankingCalculator {
        
        public boolean isFlanking(Position attacker, Position target) {
            List<Position> flankingPositions = calculateFlankingPositions(target);
            return flankingPositions.contains(attacker);
        }
        
        public List<Position> calculateFlankingPositions(Position target) {
            List<Position> positions = new ArrayList<>();
            
            // Determine facing direction (simplified)
            Direction facingDirection = Direction.NORTH; // Default facing
            
            // Calculate flanking positions
            for (int distance = 1; distance <= 3; distance++) {
                Position leftFlank = calculateFlankPosition(target, facingDirection, distance, true);
                Position rightFlank = calculateFlankPosition(target, facingDirection, distance, false);
                
                if (isValidPosition(leftFlank)) positions.add(leftFlank);
                if (isValidPosition(rightFlank)) positions.add(rightFlank);
            }
            
            return positions;
        }
        
        private Position calculateFlankPosition(Position target, Direction facing, int distance, boolean isLeft) {
            // Calculate perpendicular offset
            int offsetX = facing.getPerpendicularX() * distance * (isLeft ? -1 : 1);
            int offsetY = facing.getPerpendicularY() * distance * (isLeft ? -1 : 1);
            
            return new Position(target.getX() + offsetX, target.getY() + offsetY);
        }
        
        private boolean isValidPosition(Position position) {
            return position.getX() >= 0 && position.getY() >= 0;
        }
        
        // Direction enum for facing calculations
        public enum Direction {
            NORTH(0, -1, 1, 0),
            EAST(1, 0, 0, 1),
            SOUTH(0, 1, -1, 0),
            WEST(-1, 0, 0, -1);
            
            private final int dx, dy, perpX, perpY;
            
            Direction(int dx, int dy, int perpX, int perpY) {
                this.dx = dx;
                this.dy = dy;
                this.perpX = perpX;
                this.perpY = perpY;
            }
            
            public int getPerpendicularX() { return perpX; }
            public int getPerpendicularY() { return perpY; }
        }
    }
    
    public static class FlankingBonusManager {
        
        public double getFlankingBonus(Unit attacker, Unit target) {
            double baseBonus = 1.3; // 30% base flanking bonus
            
            // Add unit-specific bonuses
            if (attacker.hasFlankingSpecialist()) {
                baseBonus += 0.2; // 20% bonus for flanking specialists
            }
            
            // Add weapon-specific bonuses
            if (attacker.hasFlankingWeapon()) {
                baseBonus += 0.1; // 10% bonus for flanking weapons
            }
            
            return Math.min(baseBonus, 2.0); // Cap at 100% bonus
        }
        
        public void applyFlankingBonuses(CombatAction action) {
            // Apply damage bonus
            action.setDamage((int)(action.getDamage() * 1.3));
            
            // Apply accuracy bonus
            action.setAccuracy(Math.min(100, action.getAccuracy() + 25));
            
            // Apply critical chance bonus
            action.setCriticalChance(Math.min(1.0, action.getCriticalChance() + 0.25));
            
            // Add flanking effect
            action.getEffects().add("FLANKING_BONUS");
        }
    }
    
    public static class FlankingVisualIndicator {
        
        public void showFlankingEffect(CombatAction action) {
            // Show visual indication of flanking
            log.info("Flanking visual effect shown for action: {}", action.getActionId());
        }
        
        public void highlightFlankingPositions(List<Position> positions) {
            // Highlight available flanking positions
            log.info("Highlighting {} flanking positions", positions.size());
        }
    }
}
