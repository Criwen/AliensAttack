package com.aliensattack.combat;

import com.aliensattack.core.model.Position;
import com.aliensattack.core.model.Unit;
import com.aliensattack.core.model.Explosive;
import com.aliensattack.core.enums.CoverType;
import com.aliensattack.field.TacticalField;
import com.aliensattack.field.ITacticalField;
import com.aliensattack.core.interfaces.IUnit;

import lombok.Getter;
import lombok.extern.log4j.Log4j2;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Default combat manager with performance-focused data structures and caching
 * Uses concurrent collections and efficient algorithms for scalability
 */
@Log4j2
@Getter
public class DefaultCombatManager extends BaseCombatManager implements ICombatManager {
    private final TacticalField field;
    private final Map<String, Unit> playerUnits;
    private final Map<String, Unit> enemyUnits;
    private final Map<String, CombatResult> resultCache;
    private boolean isPlayerTurn;
    private final Random random;
    
    public DefaultCombatManager(TacticalField field) {
        super(field);
        this.field = field;
        this.playerUnits = new ConcurrentHashMap<>();
        this.enemyUnits = new ConcurrentHashMap<>();
        this.resultCache = new ConcurrentHashMap<>();
        this.isPlayerTurn = true;
        this.random = new Random();
        org.apache.logging.log4j.LogManager.getLogger(DefaultCombatManager.class)
            .info("DefaultCombatManager initialized with field: {}x{}", field.getWidth(), field.getHeight());
    }
    

    
    public boolean moveUnitQuick(Unit unit, int newX, int newY) {
        if (!unit.canMove()) return false;
        
        Position currentPos = unit.getPosition();
        int distance = Math.abs(currentPos.getX() - newX) + Math.abs(currentPos.getY() - newY);
        
        if (distance > unit.getMovementRange()) return false;
        
        if (field.moveUnit(unit, newX, newY)) {
            unit.spendActionPoint();
            return true;
        }
        
        return false;
    }
    
    public CombatResult attackCore(Unit attacker, Unit target) {
        log.info("Attack initiated: {} -> {} (distance: {})", 
                attacker.getName(), target.getName(), 
                Math.abs(attacker.getPosition().getX() - target.getPosition().getX()) + 
                Math.abs(attacker.getPosition().getY() - target.getPosition().getY()));
        
        // Check cache first
        String cacheKey = attacker.getName() + "_" + target.getName() + "_" + 
                         attacker.getPosition() + "_" + target.getPosition();
        
        CombatResult cached = resultCache.get(cacheKey);
        if (cached != null) {
            log.debug("Using cached combat result for attack: {} -> {}", attacker.getName(), target.getName());
            return cached;
        }
        
        if (!attacker.canAttack()) {
            log.warn("Attack failed: {} cannot attack (no action points)", attacker.getName());
            return new CombatResult(false, 0, "No action points remaining");
        }
        
        Position attackerPos = attacker.getPosition();
        Position targetPos = target.getPosition();
        
        int distance = Math.abs(attackerPos.getX() - targetPos.getX()) + 
                      Math.abs(attackerPos.getY() - targetPos.getY());
        
        if (distance > attacker.getAttackRange()) {
            log.warn("Attack failed: {} target {} out of range (distance: {}, max: {})", 
                    attacker.getName(), target.getName(), distance, attacker.getAttackRange());
            return new CombatResult(false, 0, "Target out of range");
        }
        
        // Calculate hit chance with optimized cover calculation
        int baseHitChance = 80;
        CoverType targetCover = field.getCoverTypeAt(targetPos.getX(), targetPos.getY());
        int coverBonus = getCoverBonus(targetCover);
        int finalHitChance = baseHitChance - coverBonus;
        
        log.debug("Attack calculation: base={}, cover={}, final={}", baseHitChance, coverBonus, finalHitChance);
        
        // Roll for hit
        int roll = random.nextInt(100) + 1;
        boolean hit = roll <= finalHitChance;
        
        CombatResult result;
        if (hit) {
            int damage = attacker.getAttackDamage();
            boolean killed = target.takeDamage(damage);
            attacker.spendActionPoint();
            
            log.info("Attack successful: {} hit {} for {} damage (killed: {})", 
                    attacker.getName(), target.getName(), damage, killed);
            result = new CombatResult(true, damage, killed ? "Target killed!" : "Target hit!");
        } else {
            log.info("Attack missed: {} missed {} (roll: {}, needed: {})", 
                    attacker.getName(), target.getName(), roll, finalHitChance);
            result = new CombatResult(false, 0, "Attack missed!");
        }
        
        // Cache the result
        resultCache.put(cacheKey, result);
        return result;
    }
    
    private int getCoverBonus(CoverType coverType) {
        return switch (coverType) {
            case FULL_COVER -> 40;
            case HALF_COVER -> 20;
            case LOW_COVER -> 10;
            default -> 0;
        };
    }
    
    public void endTurnCore() {
        // Reset action points for all units efficiently
        playerUnits.values().stream()
            .filter(Unit::isAlive)
            .forEach(Unit::resetActionPoints);
            
        enemyUnits.values().stream()
            .filter(Unit::isAlive)
            .forEach(Unit::resetActionPoints);
        
        isPlayerTurn = !isPlayerTurn;
        resultCache.clear(); // Clear cache on turn end
    }
    
    public boolean isCombatOver() {
        boolean playerAlive = playerUnits.values().stream().anyMatch(Unit::isAlive);
        boolean enemyAlive = enemyUnits.values().stream().anyMatch(Unit::isAlive);
        
        return !playerAlive || !enemyAlive;
    }
    
    public String getWinner() {
        boolean playerAlive = playerUnits.values().stream().anyMatch(Unit::isAlive);
        boolean enemyAlive = enemyUnits.values().stream().anyMatch(Unit::isAlive);
        
        if (!playerAlive) return "Enemy";
        if (!enemyAlive) return "Player";
        return null;
    }
    
    public List<Unit> getPlayerUnits() {
        return new ArrayList<>(playerUnits.values());
    }
    
    public List<Unit> getEnemyUnits() {
        return new ArrayList<>(enemyUnits.values());
    }
    
    public List<Unit> getAllUnits() {
        List<Unit> allUnits = new ArrayList<>();
        allUnits.addAll(playerUnits.values());
        allUnits.addAll(enemyUnits.values());
        return allUnits;
    }
    
    public CombatResult performAttack(Unit attacker, Unit target) {
        return attackCore(attacker, target);
    }
    
    public boolean canAttack(Unit attacker, Unit target) {
        if (!attacker.isAlive() || !target.isAlive()) return false;
        
        Position attackerPos = attacker.getPosition();
        Position targetPos = target.getPosition();
        int distance = Math.abs(attackerPos.getX() - targetPos.getX()) + 
                      Math.abs(attackerPos.getY() - targetPos.getY());
        
        return distance <= attacker.getAttackRange();
    }
    
    public List<Unit> getValidTargets(Unit attacker) {
        List<Unit> validTargets = new ArrayList<>();
        for (Unit target : enemyUnits.values()) {
            if (canAttack(attacker, target)) {
                validTargets.add(target);
            }
        }
        return validTargets;
    }
    
    public boolean canMoveTo(Unit unit, Position target) {
        if (!unit.isAlive()) return false;
        
        Position currentPos = unit.getPosition();
        int distance = Math.abs(currentPos.getX() - target.getX()) + 
                      Math.abs(currentPos.getY() - target.getY());
        
        return distance <= unit.getMovementRange() && field.isValidPosition(target.getX(), target.getY());
    }
    
    public boolean moveUnit(Unit unit, Position target) {
        if (canMoveTo(unit, target)) {
            return field.moveUnit(unit, target.getX(), target.getY());
        }
        return false;
    }
    
    public List<Position> getValidMovePositions(Unit unit) {
        return field.getValidMoves(unit, unit.getMovementRange());
    }
    
    public boolean isGameOver() {
        return hasPlayerWon() || hasEnemyWon();
    }
    
    public boolean hasPlayerWon() {
        return enemyUnits.values().stream().noneMatch(Unit::isAlive);
    }
    
    public boolean hasEnemyWon() {
        return playerUnits.values().stream().noneMatch(Unit::isAlive);
    }
    
    public boolean allPlayerUnitsOutOfActionPoints() {
        return playerUnits.values().stream()
            .filter(Unit::isAlive)
            .allMatch(unit -> unit.getActionPoints() <= 0);
    }
    
    public Map<String, Unit> getPlayerUnitsMap() {
        return new HashMap<>(playerUnits);
    }
    
    public Map<String, Unit> getEnemyUnitsMap() {
        return new HashMap<>(enemyUnits);
    }
    
    // Legacy convenience getters retained via interface-standard methods
    
    public boolean isUnitAlive(Unit unit) {
        return unit.isAlive();
    }
    
    public void resetUnitActionPoints(Unit unit) {
        unit.setActionPoints(2);
    }
    
    public void startTurn() {
        isPlayerTurn = true;
        for (Unit unit : playerUnits.values()) {
            unit.setActionPoints(2);
        }
    }
    
    public void endTurn() {
        isPlayerTurn = false;
        // AI turn logic would go here
    }
    
    public boolean isPlayerTurn() {
        return isPlayerTurn;
    }
    
    public int getCurrentTurn() {
        return 1; // Simplified for now
    }
    
    public void addPlayerUnit(Unit unit) {
        playerUnits.put(unit.getName(), unit);
    }
    
    public void addEnemyUnit(Unit unit) {
        enemyUnits.put(unit.getName(), unit);
    }
    
    public void removeUnit(Unit unit) {
        playerUnits.remove(unit.getName());
        enemyUnits.remove(unit.getName());
    }
    

    
    public void clearCache() {
        resultCache.clear();
    }
    
    public int getCacheSize() {
        return resultCache.size();
    }
    
    public void removeDeadUnits() {
        playerUnits.entrySet().removeIf(entry -> !entry.getValue().isAlive());
        enemyUnits.entrySet().removeIf(entry -> !entry.getValue().isAlive());
    }
    
    // Additional utility methods (not part of ICombatManager interface)
    public void initialize() {
        // Initialize the combat system
        isPlayerTurn = true;
        clearCache();
    }
    
    public void processInitiative() {
        // Process unit initiative order (not implemented in this optimized version)
    }
    
    public List<IUnit> getUnitsInInitiativeOrder() {
        List<IUnit> allUnits = new ArrayList<>();
        allUnits.addAll(playerUnits.values());
        allUnits.addAll(enemyUnits.values());
        return allUnits;
    }
    
    public boolean isCombatFinished() {
        return isCombatOver();
    }
    
    public String getCombatState() {
        return String.format("Player Turn: %s, Player Units: %d, Enemy Units: %d", 
                           isPlayerTurn, playerUnits.size(), enemyUnits.size());
    }
    
    public List<IUnit> getUnitsAt(Position position) {
        List<IUnit> unitsAtPosition = new ArrayList<>();
        for (Unit unit : playerUnits.values()) {
            if (unit.getPosition().equals(position)) {
                unitsAtPosition.add(unit);
            }
        }
        for (Unit unit : enemyUnits.values()) {
            if (unit.getPosition().equals(position)) {
                unitsAtPosition.add(unit);
            }
        }
        return unitsAtPosition;
    }
    
    public List<IUnit> getUnitsInRange(Position center, int range) {
        List<IUnit> unitsInRange = new ArrayList<>();
        for (Unit unit : playerUnits.values()) {
            if (center.getDistanceTo(unit.getPosition()) <= range) {
                unitsInRange.add(unit);
            }
        }
        for (Unit unit : enemyUnits.values()) {
            if (center.getDistanceTo(unit.getPosition()) <= range) {
                unitsInRange.add(unit);
            }
        }
        return unitsInRange;
    }
    
    public boolean isValidMovePosition(IUnit unit, Position position) {
        if (unit instanceof Unit) {
            Unit u = (Unit) unit;
            return canMoveTo(u, position);
        }
        return false;
    }
    
    public boolean canSeeUnit(IUnit observer, IUnit target) {
        if (observer instanceof Unit && target instanceof Unit) {
            Unit obs = (Unit) observer;
            Unit tgt = (Unit) target;
            return obs.getPosition().getDistanceTo(tgt.getPosition()) <= obs.getAttackRange();
        }
        return false;
    }
    
    @Override
    public ITacticalField getField() {
        return field;
    }
    
    @Override
    public ITacticalField getTacticalField() {
        return field;
    }

    @Override
    public boolean performTacticalMove(Unit unit, int newX, int newY) {
        return moveUnitQuick(unit, newX, newY);
    }

    @Override
    public CombatResult executeCoordinatedAttack(Unit[] attackers, Unit target) {
        // Simple coordinated attack implementation
        CombatResult result = new CombatResult(false, 0, "No attack performed");
        for (Unit attacker : attackers) {
            CombatResult singleResult = attackCore(attacker, target);
            // Note: CombatResult.addResult() method needs to be implemented
            // For now, just return the first result
            if (singleResult != null) {
                result = singleResult;
                break;
            }
        }
        return result;
    }

    @Override
    public double calculateAdvancedCoverBonus(Unit unit) {
        // Use existing cover bonus calculation
        return getCoverBonus(CoverType.FULL);
    }

    @Override
    public void processEnvironmentalEffects() {
        // Process environmental effects on all units
        for (Unit unit : getAllUnits()) {
            if (unit.getPosition() != null) {
                // Apply environmental effects based on position
                applyEnvironmentalEffects(unit);
            }
        }
    }

    private void applyEnvironmentalEffects(Unit unit) {
        // Apply environmental effects to unit
        // This is a placeholder implementation
        log.debug("Applying environmental effects to unit: {}", unit.getId());
    }
    
    /**
     * Throw a grenade at a target position
     */
    public List<CombatResult> throwGrenade(Unit thrower, Explosive explosive, Position targetPos) {
        List<CombatResult> results = new ArrayList<>();
        
        if (!thrower.canAttack()) {
            results.add(new CombatResult(false, 0, "No action points remaining"));
            return results;
        }
        
        // Check if target position is in range (use Manhattan distance for consistency)
        Position throwerPos = thrower.getPosition();
        int distance = Math.abs(throwerPos.getX() - targetPos.getX()) + Math.abs(throwerPos.getY() - targetPos.getY());
        if (distance > explosive.getRadius()) {
            results.add(new CombatResult(false, 0, "Target out of range"));
            return results;
        }
        
        // Simulate grenade explosion
        int damage = explosive.getDamage();
        boolean hit = random.nextInt(100) < 80; // 80% hit chance for grenades
        
        if (hit) {
            thrower.spendActionPoint();
            results.add(new CombatResult(true, damage, "Grenade hit target!"));
        } else {
            results.add(new CombatResult(false, 0, "Grenade missed!"));
        }
        
        return results;
    }
    
    // Implementation of abstract methods from BaseCombatManager
    @Override
    protected int getUnitInitiative(IUnit unit) {
        if (unit instanceof Unit) {
            Unit u = (Unit) unit;
            return u.getInitiative() + random.nextInt(10); // Add some randomness
        }
        return 0;
    }
    
    @Override
    protected void resetUnitActionPoints() {
        for (Unit unit : getAllUnits()) {
            unit.setActionPoints(2); // Reset to default action points
        }
    }
    
    @Override
    protected void processStatusEffects() {
        // Process status effects on all units
        for (Unit unit : getAllUnits()) {
            if (unit.hasStatusEffects()) {
                // Process each status effect
                unit.processStatusEffects();
            }
        }
    }
    
    @Override
    protected void checkCombatEndConditions() {
        // Check if combat should end
        if (hasPlayerWon() || hasEnemyWon()) {
            combatActive = false;
        }
    }
} 
