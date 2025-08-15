package com.aliensattack.combat;

import com.aliensattack.combat.interfaces.ICombatManagerExtended;
import com.aliensattack.core.interfaces.IUnit;
import com.aliensattack.core.model.Position;
import com.aliensattack.field.ITacticalField;

import lombok.Getter;
import lombok.Setter;
import java.util.List;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;
import java.util.Map;

/**
 * Base combat manager that provides common functionality
 * for all combat manager implementations.
 */
@Getter
@Setter
public abstract class BaseCombatManager implements ICombatManagerExtended {
    
    protected ITacticalField field; // Tactical field
    protected List<IUnit> units;
    protected Map<String, IUnit> initiativeOrder;
    protected boolean combatActive;
    protected int currentTurn;
    protected int maxTurns;
    
    public BaseCombatManager(ITacticalField field) {
        this.field = field;
        this.units = new ArrayList<>();
        this.initiativeOrder = new ConcurrentHashMap<>();
        this.combatActive = false;
        this.currentTurn = 0;
        this.maxTurns = 100; // Default max turns
    }
    
    // ICombatManager interface implementation
    @Override
    public void initialize() {
        combatActive = true;
        currentTurn = 0;
        processInitiative();
    }
    
    @Override
    public void startTurn() {
        if (!combatActive) return;
        
        currentTurn++;
        resetUnitActionPoints();
        processInitiative();
    }
    
    @Override
    public void endTurn() {
        if (!combatActive) return;
        
        processStatusEffects();
        checkCombatEndConditions();
    }
    
    @Override
    public void processInitiative() {
        // Sort units by initiative (higher initiative goes first)
        units.sort((u1, u2) -> Integer.compare(getUnitInitiative(u2), getUnitInitiative(u1)));
        
        // Clear and rebuild initiative order
        initiativeOrder.clear();
        for (IUnit unit : units) {
            initiativeOrder.put(unit.getId(), unit);
        }
    }
    
    @Override
    public List<IUnit> getUnitsInInitiativeOrder() {
        return new ArrayList<>(units);
    }
    
    @Override
    public boolean isCombatFinished() {
        if (!combatActive) return true;
        
        // Check if all units of one side are defeated
        long aliveUnits = units.stream().filter(IUnit::isAlive).count();
        if (aliveUnits <= 1) return true;
        
        // Check turn limit
        if (currentTurn >= maxTurns) return true;
        
        return false;
    }
    
    @Override
    public String getCombatState() {
        return String.format("Turn: %d/%d, Active Units: %d, Combat Active: %s",
                currentTurn, maxTurns, 
                units.stream().filter(IUnit::isAlive).count(),
                combatActive);
    }
    
    @Override
    public List<IUnit> getUnitsAt(Position position) {
        List<IUnit> unitsAtPosition = new ArrayList<>();
        for (IUnit unit : units) {
            if (unit.getPosition().equals(position)) {
                unitsAtPosition.add(unit);
            }
        }
        return unitsAtPosition;
    }
    
    @Override
    public List<IUnit> getUnitsInRange(Position center, int range) {
        List<IUnit> unitsInRange = new ArrayList<>();
        for (IUnit unit : units) {
            if (isUnitInRange(unit, center, range)) {
                unitsInRange.add(unit);
            }
        }
        return unitsInRange;
    }
    
    @Override
    public boolean isValidMovePosition(IUnit unit, Position position) {
        if (position == null || unit == null) return false;
        
        // Check if position is within movement range
        Position currentPos = unit.getPosition();
        int distance = Math.abs(currentPos.getX() - position.getX()) + 
                      Math.abs(currentPos.getY() - position.getY());
        
        return distance <= unit.getMovementRange();
    }
    
    @Override
    public boolean canSeeUnit(IUnit observer, IUnit target) {
        if (observer == null || target == null) return false;
        
        // Simple line of sight check
        Position observerPos = observer.getPosition();
        Position targetPos = target.getPosition();
        
        int distance = Math.abs(observerPos.getX() - targetPos.getX()) + 
                      Math.abs(observerPos.getY() - targetPos.getY());
        
        return distance <= observer.getAttackRange();
    }
    
    @Override
    public ITacticalField getField() {
        return field;
    }
    
    // Protected methods for subclasses to override
    protected abstract int getUnitInitiative(IUnit unit);
    
    protected abstract void resetUnitActionPoints();
    
    protected abstract void processStatusEffects();
    
    protected abstract void checkCombatEndConditions();
    
    // Utility methods
    protected boolean isUnitInRange(IUnit unit, Position center, int range) {
        if (unit == null || center == null) return false;
        
        Position unitPos = unit.getPosition();
        int distance = Math.abs(unitPos.getX() - center.getX()) + 
                      Math.abs(unitPos.getY() - center.getY());
        
        return distance <= range;
    }
    
    public void addUnit(IUnit unit) {
        if (unit != null && !units.contains(unit)) {
            units.add(unit);
        }
    }
    
    public void removeUnit(IUnit unit) {
        units.remove(unit);
        initiativeOrder.remove(unit.getId());
    }
    
    public void setMaxTurns(int maxTurns) {
        this.maxTurns = Math.max(1, maxTurns);
    }
}
