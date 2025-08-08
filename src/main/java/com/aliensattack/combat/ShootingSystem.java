package com.aliensattack.combat;

import com.aliensattack.core.model.*;
import com.aliensattack.core.enums.*;
import com.aliensattack.field.ITacticalField;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.ArrayList;
import java.util.Random;

/**
 * Manages the shooting system with weapon selection, target visibility,
 * and hit chance calculation
 */
@Getter
@Setter
public class ShootingSystem {
    
    private ITacticalField tacticalField;
    private ICombatManager combatManager;
    private Random random;
    
    // Shooting state
    private Unit selectedShooter;
    private Weapon selectedWeapon;
    private Unit selectedTarget;
    private boolean weaponSelected;
    private boolean targetSelected;
    
    public ShootingSystem(ITacticalField tacticalField, ICombatManager combatManager) {
        this.tacticalField = tacticalField;
        this.combatManager = combatManager;
        this.random = new Random();
        resetShootingState();
    }
    
    /**
     * Reset shooting state
     */
    public void resetShootingState() {
        this.selectedShooter = null;
        this.selectedWeapon = null;
        this.selectedTarget = null;
        this.weaponSelected = false;
        this.targetSelected = false;
    }
    
    /**
     * Select a shooter unit
     */
    public boolean selectShooter(Unit shooter) {
        if (shooter == null || !shooter.isAlive() || !shooter.canTakeActions()) {
            return false;
        }
        
        this.selectedShooter = shooter;
        resetWeaponAndTarget();
        return true;
    }
    
    /**
     * Select a weapon for the shooter
     */
    public boolean selectWeapon(Weapon weapon) {
        if (selectedShooter == null || weapon == null) {
            return false;
        }
        
        if (!weapon.hasAmmo()) {
            return false;
        }
        
        this.selectedWeapon = weapon;
        this.weaponSelected = true;
        this.targetSelected = false;
        return true;
    }
    
    /**
     * Get available weapons for the selected shooter
     */
    public List<Weapon> getAvailableWeapons() {
        List<Weapon> weapons = new ArrayList<>();
        
        if (selectedShooter != null) {
            // Add primary weapon
            if (selectedShooter.getWeapon() != null && selectedShooter.getWeapon().hasAmmo()) {
                weapons.add(selectedShooter.getWeapon());
            }
            
            // Add secondary weapons (pistol, grenades, etc.)
            // This could be expanded based on unit inventory
        }
        
        return weapons;
    }
    
    /**
     * Get visible enemies for the selected shooter
     */
    public List<Unit> getVisibleEnemies() {
        List<Unit> visibleEnemies = new ArrayList<>();
        
        if (selectedShooter == null) {
            return visibleEnemies;
        }
        
        Position shooterPos = selectedShooter.getPosition();
        int viewRange = selectedShooter.getViewRange();
        
        // Check all units in the field
        for (int x = 0; x < tacticalField.getWidth(); x++) {
            for (int y = 0; y < tacticalField.getHeight(); y++) {
                Unit unit = tacticalField.getUnitAt(x, y);
                if (unit != null && unit != selectedShooter && unit.isAlive()) {
                    // Check if enemy
                    if (isEnemy(selectedShooter, unit)) {
                        // Check if in view range
                        if (isInRange(shooterPos, unit.getPosition(), viewRange)) {
                            // Check line of sight
                            if (hasLineOfSight(shooterPos, unit.getPosition())) {
                                visibleEnemies.add(unit);
                            }
                        }
                    }
                }
            }
        }
        
        return visibleEnemies;
    }
    
    /**
     * Check if there are any visible enemies
     */
    public boolean hasVisibleEnemies() {
        return !getVisibleEnemies().isEmpty();
    }
    
    /**
     * Select a target for shooting
     */
    public boolean selectTarget(Unit target) {
        if (selectedShooter == null || target == null) {
            return false;
        }
        
        List<Unit> visibleEnemies = getVisibleEnemies();
        if (!visibleEnemies.contains(target)) {
            return false;
        }
        
        this.selectedTarget = target;
        this.targetSelected = true;
        return true;
    }
    
    /**
     * Calculate hit chance for the current setup
     */
    public int calculateHitChance() {
        if (selectedShooter == null || selectedWeapon == null || selectedTarget == null) {
            return 0;
        }
        
        int baseAccuracy = selectedWeapon.getAccuracy();
        int distance = calculateDistance(selectedShooter.getPosition(), selectedTarget.getPosition());
        
        // Distance penalty
        int distancePenalty = Math.max(0, (distance - selectedWeapon.getRange()) * 5);
        
        // Cover bonus for target
        int coverBonus = calculateCoverBonus(selectedTarget);
        
        // Height advantage
        int heightBonus = calculateHeightBonus(selectedShooter, selectedTarget);
        
        // Final hit chance
        int hitChance = baseAccuracy - distancePenalty - coverBonus + heightBonus;
        
        return Math.max(0, Math.min(100, hitChance));
    }
    
    /**
     * Execute the shot
     */
    public ShootingResult executeShot() {
        if (!canExecuteShot()) {
            return new ShootingResult(false, 0, "Cannot execute shot");
        }
        
        // Проверяем, достаточно ли очков действия
        double weaponCost = selectedWeapon.getActionPointCost();
        if (selectedWeapon.isPistol()) {
            // Для пистолета нужны минимум 0.5 AP
            if (selectedShooter.getActionPoints() < weaponCost) {
                return new ShootingResult(false, 0, "Недостаточно очков действия для атаки. Нужно: " + weaponCost + " AP");
            }
        } else {
            // Для основного оружия нужен минимум 1 AP
            if (selectedShooter.getActionPoints() < 1.0) {
                return new ShootingResult(false, 0, "Недостаточно очков действия для атаки. Нужно минимум 1 AP");
            }
        }
        
        int hitChance = calculateHitChance();
        int roll = random.nextInt(100) + 1;
        boolean hit = roll <= hitChance;
        
        ShootingResult result;
        if (hit) {
            // Calculate damage
            int damage = calculateDamage();
            boolean killed = selectedTarget.takeDamage(damage);
            
            // Use ammo
            selectedWeapon.useAmmo();
            
            // Spend action points based on weapon type
            if (selectedWeapon.isPistol()) {
                selectedShooter.spendActionPoints(weaponCost);
                result = new ShootingResult(true, damage, killed ? "Target eliminated! (AP spent: " + weaponCost + ")" : "Target hit! (AP spent: " + weaponCost + ")");
            } else {
                // Для основного оружия тратим все очки действия
                double allAP = selectedShooter.getActionPoints();
                selectedShooter.spendActionPoints(allAP);
                result = new ShootingResult(true, damage, killed ? "Target eliminated! (AP spent: ALL)" : "Target hit! (AP spent: ALL)");
            }
        } else {
            // Use ammo even on miss
            selectedWeapon.useAmmo();
            
            // Spend action points based on weapon type
            if (selectedWeapon.isPistol()) {
                selectedShooter.spendActionPoints(weaponCost);
                result = new ShootingResult(false, 0, "Shot missed! (AP spent: " + weaponCost + ")");
            } else {
                // Для основного оружия тратим все очки действия
                double allAP = selectedShooter.getActionPoints();
                selectedShooter.spendActionPoints(allAP);
                result = new ShootingResult(false, 0, "Shot missed! (AP spent: ALL)");
            }
        }
        
        // Reset shooting state
        resetShootingState();
        
        return result;
    }
    
    /**
     * Check if shot can be executed
     */
    public boolean canExecuteShot() {
        if (selectedShooter == null || selectedWeapon == null || selectedTarget == null) {
            return false;
        }
        
        if (!selectedWeapon.hasAmmo() || !selectedShooter.canTakeActions() || !hasVisibleEnemies()) {
            return false;
        }
        
        // Проверяем очки действия в зависимости от типа оружия
        if (selectedWeapon.isPistol()) {
            return selectedShooter.getActionPoints() >= 0.5;
        } else {
            return selectedShooter.getActionPoints() >= 1.0;
        }
    }
    
    /**
     * Get shooting status message
     */
    public String getShootingStatus() {
        if (selectedShooter == null) {
            return "Выберите солдата для стрельбы";
        }
        
        if (!hasVisibleEnemies()) {
            return "Врагов не найдено в зоне видимости";
        }
        
        if (selectedWeapon == null) {
            return "Выберите оружие для стрельбы";
        }
        
        if (selectedTarget == null) {
            return "Выберите цель для стрельбы";
        }
        
        int hitChance = calculateHitChance();
        return String.format("Шанс попадания: %d%%", hitChance);
    }
    
    // Helper methods
    
    private void resetWeaponAndTarget() {
        this.selectedWeapon = null;
        this.selectedTarget = null;
        this.weaponSelected = false;
        this.targetSelected = false;
    }
    
    private boolean isEnemy(Unit unit1, Unit unit2) {
        return unit1.getUnitType() != unit2.getUnitType();
    }
    
    private boolean isInRange(Position pos1, Position pos2, int range) {
        int distance = calculateDistance(pos1, pos2);
        return distance <= range;
    }
    
    private int calculateDistance(Position pos1, Position pos2) {
        return Math.abs(pos1.getX() - pos2.getX()) + Math.abs(pos1.getY() - pos2.getY());
    }
    
    private boolean hasLineOfSight(Position from, Position to) {
        // Simple line of sight check - could be enhanced with cover detection
        return true; // For now, assume clear line of sight
    }
    
    private int calculateCoverBonus(Unit target) {
        Position targetPos = target.getPosition();
        CoverObject coverObject = tacticalField.getCoverObject(targetPos.getX(), targetPos.getY());
        
        if (coverObject == null) {
            return 0;
        }
        
        CoverType coverType = coverObject.getCoverType();
        return switch (coverType) {
            case FULL_COVER -> 40;
            case HALF_COVER -> 20;
            case LOW_COVER -> 10;
            default -> 0;
        };
    }
    
    private int calculateHeightBonus(Unit shooter, Unit target) {
        int heightDiff = shooter.getHeight() - target.getHeight();
        return Math.max(0, heightDiff * 10);
    }
    
    private int calculateDamage() {
        int baseDamage = selectedWeapon.getBaseDamage();
        
        // Check for critical hit
        int criticalChance = selectedWeapon.getCriticalChance();
        int roll = random.nextInt(100) + 1;
        
        if (roll <= criticalChance) {
            return baseDamage * selectedWeapon.getCriticalDamage() / 100;
        }
        
        return baseDamage;
    }
    
    /**
     * Result of a shooting action
     */
    public static class ShootingResult {
        private final boolean hit;
        private final int damage;
        private final String message;
        
        public ShootingResult(boolean hit, int damage, String message) {
            this.hit = hit;
            this.damage = damage;
            this.message = message;
        }
        
        public boolean isHit() { return hit; }
        public int getDamage() { return damage; }
        public String getMessage() { return message; }
    }
} 