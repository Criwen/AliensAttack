package com.aliensattack.combat;

import com.aliensattack.combat.interfaces.ICombatManagerExtended;
import com.aliensattack.core.model.Unit;
import com.aliensattack.core.model.Alien;
import com.aliensattack.core.interfaces.IUnit;
import com.aliensattack.core.ai.EnemyAI;
import com.aliensattack.core.ai.interfaces.IEnemyAI;
import com.aliensattack.core.enums.UnitType;
import com.aliensattack.field.ITacticalField;
import com.aliensattack.core.config.GameConfig;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Turn Manager for tactical combat
 * Manages turn order between soldiers and enemies
 */
@Getter
@Setter
@Log4j2
public class TurnManager {
    
    private ICombatManagerExtended combatManager;
    private ITacticalField tacticalField;
    
    // Turn state
    private int currentTurn;
    private TurnPhase currentPhase;
    private int currentUnitIndex;
    private List<Unit> turnOrder;
    
    // AI management
    private Map<String, IEnemyAI> enemyAIs;
    private boolean aiEnabled;
    
    // Turn phases
    public enum TurnPhase {
        SOLDIER_PHASE("Ход солдат"),
        ENEMY_PHASE("Ход врагов"),
        ENVIRONMENT_PHASE("Фаза окружения"),
        TURN_END("Конец хода");
        
        private final String displayName;
        
        TurnPhase(String displayName) {
            this.displayName = displayName;
        }
        
        public String getDisplayName() {
            return displayName;
        }
    }
    
    public TurnManager(ICombatManagerExtended combatManager, ITacticalField tacticalField) {
        this.combatManager = combatManager;
        this.tacticalField = tacticalField;
        this.enemyAIs = new ConcurrentHashMap<>();
        this.aiEnabled = GameConfig.getBoolean("ai.enemy.enabled", true);
        this.currentTurn = 1;
        this.currentPhase = TurnPhase.SOLDIER_PHASE;
        this.currentUnitIndex = 0;
        this.turnOrder = new ArrayList<>();
        
        initializeTurnManager();
    }
    
    /**
     * Initialize the turn manager
     */
    private void initializeTurnManager() {
        log.info("Initializing Turn Manager");
        
        // Initialize enemy AIs
        if (aiEnabled) {
            initializeEnemyAIs();
        }
        
        // Build initial turn order
        buildTurnOrder();
        
        log.info("Turn Manager initialized. AI enabled: {}", aiEnabled);
    }
    
    /**
     * Initialize AI for all enemy units
     */
    private void initializeEnemyAIs() {
        List<Unit> enemyUnits = tacticalField.getEnemyUnits();
        
        for (Unit unit : enemyUnits) {
            if (unit.getUnitType() == UnitType.ALIEN) {
                try {
                    // Create and initialize AI for this alien
                    EnemyAI ai = new EnemyAI();
                    
                    // Check if unit has required methods for AI initialization
                    if (hasRequiredMethodsForAI(unit)) {
                        // Initialize AI with unit data
                        ai.initializeWithUnit(unit);
                        ai.setTacticalField(tacticalField);
                        ai.setCombatManager(combatManager);
                        
                        // Store AI with unit ID as key
                        enemyAIs.put(unit.getName(), ai);
                        
                        log.debug("AI initialized for alien unit: {} (Type: {})", unit.getName(), unit.getUnitType());
                    } else {
                        log.warn("Unit {} missing required methods for AI initialization", unit.getName());
                    }
                } catch (Exception e) {
                    log.error("Error initializing AI for alien unit {}: {}", unit.getName(), e.getMessage());
                }
            }
        }
        
        log.info("Initialized AI for {} enemy units", enemyAIs.size());
    }
    
    /**
     * Check if unit has required methods for AI initialization
     */
    private boolean hasRequiredMethodsForAI(Unit unit) {
        try {
            // Check if unit has required methods by reflection or method calls
            return unit.isAlive() && 
                   unit.getPosition() != null && 
                   unit.getMovementRange() > 0 &&
                   unit.getAttackRange() > 0;
        } catch (Exception e) {
            log.debug("Unit {} missing required methods: {}", unit.getName(), e.getMessage());
            return false;
        }
    }
    
    /**
     * Build turn order based on initiative
     */
    private void buildTurnOrder() {
        turnOrder.clear();
        
        // Get all units and sort by initiative
        List<Unit> allUnits = tacticalField.getAllUnits();
        allUnits.sort((u1, u2) -> Integer.compare(getUnitInitiative(u2), getUnitInitiative(u1)));
        
        turnOrder.addAll(allUnits);
        currentUnitIndex = 0;
        
        log.debug("Turn order built with {} units", turnOrder.size());
    }
    
    /**
     * Get unit initiative value
     */
    private int getUnitInitiative(Unit unit) {
        // Base initiative from unit stats
        int baseInitiative = unit.getInitiative();
        
        // Bonus for soldiers
        if (unit.getUnitType() == UnitType.SOLDIER) {
            baseInitiative += GameConfig.getInt("combat.soldier.initiative.bonus", 5);
        }
        
        // Bonus for aliens based on type
        if (unit.getUnitType() == UnitType.ALIEN) {
            // Add default bonus for aliens
            baseInitiative += 1;
        }
        
        return baseInitiative;
    }
    
    /**
     * Start a new turn
     */
    public void startNewTurn() {
        currentTurn++;
        currentPhase = TurnPhase.SOLDIER_PHASE;
        currentUnitIndex = 0;
        
        log.info("=== НАЧАЛО ХОДА {} ===", currentTurn);
        
        // Reset action points for all units
        resetAllUnitActionPoints();
        
        // Build new turn order
        buildTurnOrder();
        
        // Start soldier phase
        startSoldierPhase();
    }
    
    /**
     * Start soldier phase
     */
    public void startSoldierPhase() {
        currentPhase = TurnPhase.SOLDIER_PHASE;
        log.info("🎯 Фаза солдат - ход {}", currentTurn);
        
        // Find first soldier in turn order
        currentUnitIndex = findFirstSoldierIndex();
        
        if (currentUnitIndex >= 0) {
            Unit currentUnit = turnOrder.get(currentUnitIndex);
            log.info("Солдат {} готов к действию", currentUnit.getName());
        } else {
            log.warn("Нет солдат для хода!");
            endSoldierPhase();
        }
    }
    
    /**
     * End soldier phase and start enemy phase
     */
    public void endSoldierPhase() {
        log.info("🏁 Фаза солдат завершена");
        
        if (aiEnabled) {
            startEnemyPhase();
        } else {
            endTurn();
        }
    }
    
    /**
     * Start enemy phase
     */
    public void startEnemyPhase() {
        currentPhase = TurnPhase.ENEMY_PHASE;
        log.info("👾 Фаза врагов - ход {}", currentTurn);
        
        // Execute AI turns for all enemies
        executeEnemyTurns();
        
        // End enemy phase
        endEnemyPhase();
    }
    
    /**
     * Execute AI turns for all enemies
     */
    private void executeEnemyTurns() {
        List<Unit> enemyUnits = tacticalField.getEnemyUnits();
        
        if (enemyUnits.isEmpty()) {
            log.info("Нет врагов для хода");
            return;
        }
        
        log.info("Выполнение ходов {} врагов", enemyUnits.size());
        
        for (Unit enemyUnit : enemyUnits) {
            if (enemyUnit.isAlive() && enemyUnit.getActionPoints() > 0) {
                executeEnemyTurn(enemyUnit);
            }
        }
    }
    
    /**
     * Execute AI turn for a single enemy
     */
    private void executeEnemyTurn(Unit enemyUnit) {
        if (enemyUnit.getUnitType() != UnitType.ALIEN) {
            return;
        }
        
        try {
            // Get AI for this enemy unit
            IEnemyAI ai = enemyAIs.get(enemyUnit.getName());
            
            if (ai != null) {
                log.debug("Executing AI turn for enemy: {}", enemyUnit.getName());
                
                // Make AI decision
                ai.makeTurnDecision();
                
                // Execute AI action
                boolean actionExecuted = ai.executeAction();
                
                if (actionExecuted) {
                    log.debug("AI action executed successfully for enemy: {}", enemyUnit.getName());
                } else {
                    log.debug("AI action failed for enemy: {}", enemyUnit.getName());
                }
            } else {
                log.warn("No AI found for enemy unit: {}", enemyUnit.getName());
            }
        } catch (Exception e) {
            log.error("Error executing AI turn for enemy {}: {}", enemyUnit.getName(), e.getMessage());
        }
    }
    
    /**
     * End enemy phase
     */
    private void endEnemyPhase() {
        log.info("🏁 Фаза врагов завершена");
        endTurn();
    }
    
    /**
     * End current turn
     */
    public void endTurn() {
        currentPhase = TurnPhase.TURN_END;
        log.info("🏁 Ход {} завершен", currentTurn);
        
        // Process end of turn effects
        processEndOfTurnEffects();
        
        // Check if combat should continue
        if (combatManager.isCombatFinished()) {
            log.info("Бой завершен!");
            return;
        }
        
        // Start next turn
        startNewTurn();
    }
    
    /**
     * Process end of turn effects
     */
    private void processEndOfTurnEffects() {
        // Process status effects
        processStatusEffects();
        
        // Process environmental effects
        processEnvironmentalEffects();
        
        // Update AI state
        updateAIState();
    }
    
    /**
     * Process status effects for all units
     */
    private void processStatusEffects() {
        List<Unit> allUnits = tacticalField.getAllUnits();
        
        for (Unit unit : allUnits) {
            if (unit.isAlive()) {
                // TODO: Implement status effect processing
                // For now, just log
                log.debug("Processing status effects for unit: {}", unit.getName());
            }
        }
    }
    
    /**
     * Process environmental effects
     */
    private void processEnvironmentalEffects() {
        // TODO: Implement environmental effects
        log.debug("Processing environmental effects");
    }
    
    /**
     * Update AI state for all enemies
     */
    private void updateAIState() {
        if (!aiEnabled) {
            return;
        }
        
        for (IEnemyAI ai : enemyAIs.values()) {
            // AI state is updated during their turns
            // This is just for end-of-turn updates
        }
    }
    
    /**
     * Reset action points for all units
     */
    private void resetAllUnitActionPoints() {
        List<Unit> allUnits = tacticalField.getAllUnits();
        
        for (Unit unit : allUnits) {
            if (unit.isAlive()) {
                unit.setActionPoints(GameConfig.getInt("unit.default.action.points", 2));
                log.debug("Reset action points for unit: {} -> {}", unit.getName(), unit.getActionPoints());
            }
        }
    }
    
    /**
     * Find first soldier index in turn order
     */
    private int findFirstSoldierIndex() {
        for (int i = 0; i < turnOrder.size(); i++) {
            Unit unit = turnOrder.get(i);
            if (unit.getUnitType() == UnitType.SOLDIER && unit.isAlive()) {
                return i;
            }
        }
        return -1;
    }
    
    /**
     * Get current turn information
     */
    public String getTurnInfo() {
        return String.format("Ход %d - %s", currentTurn, currentPhase.getDisplayName());
    }
    
    /**
     * Get current unit (if any)
     */
    public Unit getCurrentUnit() {
        if (currentUnitIndex >= 0 && currentUnitIndex < turnOrder.size()) {
            return turnOrder.get(currentUnitIndex);
        }
        return null;
    }
    
    /**
     * Move to next unit in turn order
     */
    public Unit getNextUnit() {
        if (turnOrder.isEmpty()) {
            return null;
        }
        
        currentUnitIndex = (currentUnitIndex + 1) % turnOrder.size();
        Unit nextUnit = turnOrder.get(currentUnitIndex);
        
        // Skip dead units
        while (!nextUnit.isAlive() && currentUnitIndex < turnOrder.size() - 1) {
            currentUnitIndex++;
            nextUnit = turnOrder.get(currentUnitIndex);
        }
        
        return nextUnit;
    }
    
    /**
     * Check if it's currently a soldier's turn
     */
    public boolean isSoldierTurn() {
        return currentPhase == TurnPhase.SOLDIER_PHASE;
    }
    
    /**
     * Check if it's currently an enemy's turn
     */
    public boolean isEnemyTurn() {
        return currentPhase == TurnPhase.ENEMY_PHASE;
    }
    
    /**
     * Add new unit to turn order
     */
    public void addUnit(Unit unit) {
        if (!turnOrder.contains(unit)) {
            turnOrder.add(unit);
            
            // Sort by initiative
            turnOrder.sort((u1, u2) -> Integer.compare(getUnitInitiative(u2), getUnitInitiative(u1)));
            
            // Initialize AI if it's an enemy
            if (aiEnabled && unit.getUnitType() == UnitType.ALIEN) {
                try {
                    // Create and initialize AI for this alien
                    EnemyAI ai = new EnemyAI();
                    
                    // Check if unit has required methods for AI initialization
                    if (hasRequiredMethodsForAI(unit)) {
                        // Initialize AI with unit data
                        ai.initializeWithUnit(unit);
                        ai.setTacticalField(tacticalField);
                        ai.setCombatManager(combatManager);
                        
                        // Store AI with unit ID as key
                        enemyAIs.put(unit.getName(), ai);
                        
                        log.debug("AI initialized for new alien unit: {} (Type: {})", unit.getName(), unit.getUnitType());
                    } else {
                        log.warn("New unit {} missing required methods for AI initialization", unit.getName());
                    }
                } catch (Exception e) {
                    log.error("Error initializing AI for new alien unit {}: {}", unit.getName(), e.getMessage());
                }
            }
            
            log.debug("Added unit to turn order: {}", unit.getName());
        }
    }
    
    /**
     * Remove unit from turn order
     */
    public void removeUnit(Unit unit) {
        turnOrder.remove(unit);
        
        if (unit.getUnitType() == UnitType.ALIEN) {
            // Remove AI for this alien unit
            IEnemyAI ai = enemyAIs.remove(unit.getName());
            if (ai != null) {
                log.debug("AI removed for alien unit: {}", unit.getName());
            }
        }
        
        log.debug("Removed unit from turn order: {}", unit.getName());
    }
    
    /**
     * Get turn order as list
     */
    public List<Unit> getTurnOrder() {
        return new ArrayList<>(turnOrder);
    }
    
    /**
     * Get current phase
     */
    public TurnPhase getCurrentPhase() {
        return currentPhase;
    }
    
    /**
     * Get current turn number
     */
    public int getCurrentTurn() {
        return currentTurn;
    }
}
