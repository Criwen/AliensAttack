package com.aliensattack.combat;

import com.aliensattack.core.config.GameConfig;
import com.aliensattack.core.interfaces.IUnit;
import com.aliensattack.core.model.Position;
import com.aliensattack.core.model.Unit;
import com.aliensattack.core.enums.CoverType;
import com.aliensattack.combat.CombatResult;
import com.aliensattack.field.ITacticalField;

/**
 * Tactical combat manager with flanking, cover, and tactical positioning
 */
public class TacticalCombatManager extends BaseCombatManager {
    public TacticalCombatManager(ITacticalField field) {
        super(field);
    }

    @Override
    protected int getUnitInitiative(IUnit unit) {
        int baseInitiative = GameConfig.getInt("unit.initiative.base", 50);
        int unitTypeBonus = getUnitTypeInitiativeBonus(unit);
        return baseInitiative + unitTypeBonus;
    }

    @Override
    protected void resetUnitActionPoints() {
        for (IUnit unit : units) {
            if (unit.isAlive()) {
                unit.resetActionPoints();
            }
        }
    }

    @Override
    protected void processStatusEffects() {
        for (IUnit unit : units) {
            if (unit.isAlive()) {
                unit.processStatusEffects();
            }
        }
    }

    @Override
    protected void checkCombatEndConditions() {
        if (isCombatFinished()) {
            combatActive = false;
        }
    }

    private int getUnitTypeInitiativeBonus(IUnit unit) {
        return switch (unit.getUnitType().name()) {
            case "SOLDIER" -> 10;
            case "ALIEN" -> 5;
            case "ALIEN_RULER" -> 20;
            case "CHOSEN" -> 15;
            default -> 0;
        };
    }

    public boolean isFlanking(IUnit attacker, IUnit target) {
        if (attacker == null || target == null) return false;
        Position attackerPos = attacker.getPosition();
        Position targetPos = target.getPosition();
        int dx = Math.abs(attackerPos.getX() - targetPos.getX());
        int dy = Math.abs(attackerPos.getY() - targetPos.getY());
        return dx > 0 || dy > 0;
    }

    public int getFlankingBonusDamage(int baseDamage) {
        return (int) (baseDamage * 0.2);
    }

    public void processOverwatchReactions(IUnit movingUnit, Position newPosition) {
        java.util.List<IUnit> overwatchingUnits = units.stream()
            .filter(IUnit::isOverwatching)
            .filter(unit -> canSeeUnit(unit, movingUnit))
            .toList();

        for (IUnit overwatcher : overwatchingUnits) {
            if (Math.random() * 100 < overwatcher.getOverwatchChance()) {
                triggerOverwatchShot(overwatcher, movingUnit);
            }
        }
    }

    private void triggerOverwatchShot(IUnit overwatcher, IUnit target) {
        int damage = overwatcher.getAttackDamage();
        boolean hit = target.takeDamage(damage);
        overwatcher.setOverwatching(false);
        System.out.printf("%s overwatch shot at %s: %s (damage: %d)%n",
            overwatcher.getName(), target.getName(), hit ? "HIT" : "MISS", damage);
    }
    
    // Implement abstract methods from ICombatManagerExtended
    @Override
    public boolean performTacticalMove(Unit unit, int newX, int newY) {
        if (unit.canMove() && isValidMovePosition(unit, new Position(newX, newY))) {
            unit.setPosition(new Position(newX, newY));
            return true;
        }
        return false;
    }
    
    @Override
    public CombatResult executeCoordinatedAttack(Unit[] attackers, Unit target) {
        int totalDamage = 0;
        int hits = 0;
        
        for (Unit attacker : attackers) {
            if (attacker.canAttack()) {
                // Simple attack simulation
                totalDamage += 10; // Base damage
                hits++;
            }
        }
        
        return new CombatResult(hits > 0, totalDamage, 
            String.format("Coordinated attack: %d hits, %d total damage", hits, totalDamage));
    }
    
    @Override
    public double calculateAdvancedCoverBonus(Unit unit) {
        // Simple cover bonus calculation
        return 0.5; // Default cover bonus
    }
    
    @Override
    public void processEnvironmentalEffects() {
        // Process environmental effects on all units
        for (IUnit unit : units) {
            if (unit.isAlive()) {
                // Apply environmental effects
            }
        }
    }
    
    @Override
    public ITacticalField getTacticalField() {
        return (ITacticalField) getField();
    }
}