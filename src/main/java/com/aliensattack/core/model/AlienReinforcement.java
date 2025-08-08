package com.aliensattack.core.model;

import com.aliensattack.core.enums.AlienType;
import com.aliensattack.core.enums.ReinforcementType;
import lombok.Getter;
import lombok.Setter;

import java.util.*;

/**
 * Alien Reinforcement - Dynamic enemy reinforcements
 * Implements XCOM 2 Alien Reinforcement System
 */
@Getter
@Setter
public class AlienReinforcement {
    
    private String reinforcementId;
    private ReinforcementType reinforcementType;
    private List<Unit> reinforcementUnits;
    private Position spawnPosition;
    private int spawnDelay;
    private int currentDelay;
    private boolean isSpawning;
    private boolean isActive;
    private boolean isCompleted;
    private int reinforcementValue;
    private List<String> reinforcementEvents;
    private Map<String, Integer> eventHistory;
    private boolean isEscalating;
    private int escalationLevel;
    private int maxEscalationLevel;
    private boolean isAdaptive;
    private List<AlienType> adaptiveTypes;
    private boolean isPersistent;
    private int persistenceTurns;
    private boolean isRepeating;
    private int repeatCount;
    private int maxRepeatCount;
    
    public AlienReinforcement(String reinforcementId, ReinforcementType reinforcementType, Position spawnPosition) {
        this.reinforcementId = reinforcementId;
        this.reinforcementType = reinforcementType;
        this.reinforcementUnits = new ArrayList<>();
        this.spawnPosition = spawnPosition;
        this.spawnDelay = 3;
        this.currentDelay = spawnDelay;
        this.isSpawning = false;
        this.isActive = true;
        this.isCompleted = false;
        this.reinforcementValue = 0;
        this.reinforcementEvents = new ArrayList<>();
        this.eventHistory = new HashMap<>();
        this.isEscalating = false;
        this.escalationLevel = 1;
        this.maxEscalationLevel = 5;
        this.isAdaptive = false;
        this.adaptiveTypes = new ArrayList<>();
        this.isPersistent = false;
        this.persistenceTurns = 0;
        this.isRepeating = false;
        this.repeatCount = 0;
        this.maxRepeatCount = 3;
        
        initializeReinforcement();
    }
    
    private void initializeReinforcement() {
        switch (reinforcementType) {
            case STANDARD:
                spawnDelay = 3;
                reinforcementValue = 50;
                break;
            case ESCALATING:
                spawnDelay = 2;
                reinforcementValue = 75;
                isEscalating = true;
                break;
            case ADAPTIVE:
                spawnDelay = 4;
                reinforcementValue = 100;
                isAdaptive = true;
                adaptiveTypes.add(AlienType.ADVENT_TROOPER);
                adaptiveTypes.add(AlienType.ADVENT_OFFICER);
                break;
            case PERSISTENT:
                spawnDelay = 1;
                reinforcementValue = 150;
                isPersistent = true;
                persistenceTurns = 10;
                break;
            case REPEATING:
                spawnDelay = 5;
                reinforcementValue = 200;
                isRepeating = true;
                maxRepeatCount = 3;
                break;
        }
        
        reinforcementEvents.add("Reinforcement_Initialized: " + reinforcementType);
    }
    
    /**
     * Add reinforcement unit
     */
    public void addReinforcementUnit(Unit unit) {
        reinforcementUnits.add(unit);
        reinforcementEvents.add("Unit_Added: " + unit.getName());
    }
    
    /**
     * Start spawning process
     */
    public void startSpawning() {
        if (isSpawning || isCompleted) {
            return;
        }
        
        isSpawning = true;
        currentDelay = spawnDelay;
        reinforcementEvents.add("Spawning_Started");
    }
    
    /**
     * Process reinforcement turn
     */
    public void processReinforcementTurn() {
        if (!isActive || isCompleted) {
            return;
        }
        
        if (isSpawning) {
            currentDelay--;
            reinforcementEvents.add("Spawning_Progress: " + currentDelay + " turns remaining");
            
            if (currentDelay <= 0) {
                spawnReinforcements();
            }
        }
        
        // Process persistence
        if (isPersistent && persistenceTurns > 0) {
            persistenceTurns--;
            if (persistenceTurns <= 0) {
                isPersistent = false;
                reinforcementEvents.add("Persistence_Ended");
            }
        }
        
        // Process escalation
        if (isEscalating && escalationLevel < maxEscalationLevel) {
            escalationLevel++;
            reinforcementEvents.add("Escalation_Level: " + escalationLevel);
        }
    }
    
    /**
     * Spawn reinforcements
     */
    private void spawnReinforcements() {
        isSpawning = false;
        isCompleted = true;
        reinforcementEvents.add("Reinforcements_Spawned");
        
        // Calculate reinforcement value
        for (Unit unit : reinforcementUnits) {
            reinforcementValue += unit.getHealth() * 2;
        }
        
        // Process adaptive behavior
        if (isAdaptive) {
            adaptReinforcements();
        }
        
        // Process repeating behavior
        if (isRepeating && repeatCount < maxRepeatCount) {
            repeatCount++;
            resetForRepeat();
        }
    }
    
    /**
     * Adapt reinforcements based on situation
     */
    private void adaptReinforcements() {
        // Add adaptive units based on current situation
        reinforcementEvents.add("Reinforcements_Adapted");
        
        // This would integrate with the tactical situation
        // For now, just add a basic adaptive unit
        if (adaptiveTypes.contains(AlienType.ADVENT_TROOPER)) {
            Unit adaptiveUnit = new Unit("Adaptive Trooper", 80, 3, 4, 2, com.aliensattack.core.enums.UnitType.ALIEN);
            reinforcementUnits.add(adaptiveUnit);
        }
    }
    
    /**
     * Reset for repeat
     */
    private void resetForRepeat() {
        isCompleted = false;
        isSpawning = false;
        currentDelay = spawnDelay;
        reinforcementEvents.add("Reinforcement_Repeating: " + repeatCount + "/" + maxRepeatCount);
    }
    
    /**
     * Escalate reinforcement
     */
    public void escalateReinforcement() {
        if (isEscalating && escalationLevel < maxEscalationLevel) {
            escalationLevel++;
            spawnDelay = Math.max(spawnDelay - 1, 1);
            reinforcementValue += 25;
            reinforcementEvents.add("Reinforcement_Escalated: Level " + escalationLevel);
        }
    }
    
    /**
     * Adapt reinforcement type
     */
    public void adaptReinforcementType(AlienType newType) {
        if (isAdaptive) {
            adaptiveTypes.add(newType);
            reinforcementEvents.add("Reinforcement_Adapted_Type: " + newType);
        }
    }
    
    /**
     * Extend persistence
     */
    public void extendPersistence(int turns) {
        if (isPersistent) {
            persistenceTurns += turns;
            reinforcementEvents.add("Persistence_Extended: +" + turns + " turns");
        }
    }
    
    /**
     * Get reinforcement status
     */
    public String getReinforcementStatus() {
        StringBuilder status = new StringBuilder();
        status.append("Reinforcement ID: ").append(reinforcementId).append("\n");
        status.append("Reinforcement Type: ").append(reinforcementType).append("\n");
        status.append("Spawn Position: ").append(spawnPosition).append("\n");
        status.append("Units: ").append(reinforcementUnits.size()).append("\n");
        status.append("Spawning: ").append(isSpawning).append(" (").append(currentDelay).append(" turns)\n");
        status.append("Active: ").append(isActive).append("\n");
        status.append("Completed: ").append(isCompleted).append("\n");
        status.append("Reinforcement Value: ").append(reinforcementValue).append("\n");
        status.append("Escalating: ").append(isEscalating).append(" (Level ").append(escalationLevel).append(")\n");
        status.append("Adaptive: ").append(isAdaptive).append("\n");
        status.append("Persistent: ").append(isPersistent).append(" (").append(persistenceTurns).append(" turns)\n");
        status.append("Repeating: ").append(isRepeating).append(" (").append(repeatCount).append("/").append(maxRepeatCount).append(")\n");
        
        if (!adaptiveTypes.isEmpty()) {
            status.append("Adaptive Types: ");
            for (AlienType type : adaptiveTypes) {
                status.append(type).append(", ");
            }
            status.append("\n");
        }
        
        return status.toString();
    }
    
    /**
     * Get reinforcement events
     */
    public List<String> getRecentEvents(int count) {
        int startIndex = Math.max(0, reinforcementEvents.size() - count);
        return reinforcementEvents.subList(startIndex, reinforcementEvents.size());
    }
    
    /**
     * Check if reinforcement is ready to spawn
     */
    public boolean isReadyToSpawn() {
        return isSpawning && currentDelay <= 0;
    }
    
    /**
     * Check if reinforcement is active
     */
    public boolean isActive() {
        return isActive && !isCompleted;
    }
    
    /**
     * Get reinforcement urgency level
     */
    public int getUrgencyLevel() {
        if (isSpawning && currentDelay <= 1) {
            return 4; // Critical
        } else if (isSpawning && currentDelay <= 2) {
            return 3; // High
        } else if (isSpawning) {
            return 2; // Medium
        } else {
            return 1; // Low
        }
    }
}
