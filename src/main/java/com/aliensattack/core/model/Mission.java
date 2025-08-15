package com.aliensattack.core.model;

import com.aliensattack.core.enums.MissionType;
import com.aliensattack.core.config.GameConfig;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;
import java.util.List;
import java.util.ArrayList;

/**
 * Represents a mission with objectives and conditions
 */
@Getter
@Setter
@Log4j2
public class Mission {
    private String name;
    private MissionType type;
    private List<String> objectives;
    private List<String> completedObjectives;
    private int turnLimit;
    private int currentTurn;
    private boolean isCompleted;
    private boolean isFailed;
    
    public Mission(String name, MissionType type, int turnLimit) {
        this.name = name;
        this.type = type;
        this.turnLimit = turnLimit > 0 ? turnLimit : getDefaultTurnLimit(type);
        this.currentTurn = 0;
        this.objectives = new ArrayList<>();
        this.completedObjectives = new ArrayList<>();
        this.isCompleted = false;
        this.isFailed = false;
        
        log.info("Mission created: {} (Type: {}, Turn Limit: {})", name, type, this.turnLimit);
        
        // Set default objectives based on mission type
        setDefaultObjectives();
    }
    
    /**
     * Set default objectives based on mission type
     */
    private void setDefaultObjectives() {
        switch (type) {
            case RETALIATION -> objectives.add("Eliminate all enemies");
            case SUPPLY_RAID -> objectives.add("Recover supplies");
            case SABOTAGE -> objectives.add("Sabotage facility");
            case RESCUE -> objectives.add("Extract VIP");
            case EXTRACTION -> objectives.add("Extract all soldiers");
            case DEFENSE -> objectives.add("Defend position");
            case HACKING -> objectives.add("Hack objective");
            case ELIMINATION -> objectives.add("Recover item");
            case RECONNAISSANCE -> objectives.add("Gather intelligence");
            case TERROR -> objectives.add("Defend civilians");
            case COUNCIL -> objectives.add("Complete council objective");
            case GUERRILLA -> objectives.add("Conduct guerrilla operations");
            case ESCORT -> objectives.add("Escort VIP to extraction");
            case ASSASSINATION -> objectives.add("Eliminate target");
            case INFILTRATION -> objectives.add("Infiltrate facility");
            case DESTROY -> objectives.add("Destroy facility");
            case TIMED_ASSAULT -> objectives.add("Complete assault within time limit");
        }
        
        // ToDo: Реализовать полную систему миссий
        // - Mission timer system with pressure mechanics
        // - Mission value calculation and rewards
        // - Mission failure conditions
        // - Mission success conditions
        // - Mission status tracking
        // - Mission planning and preparation
        // - Strategic layer integration
    }
    
    /**
     * Get default turn limit for mission type
     */
    private int getDefaultTurnLimit(MissionType missionType) {
        return GameConfig.getMissionTurnLimit(missionType.name().toLowerCase());
    }
    
    /**
     * Add custom objective
     */
    public void addObjective(String objective) {
        objectives.add(objective);
    }
    
    /**
     * Complete an objective
     */
    public void completeObjective(String objective) {
        if (objectives.contains(objective) && !completedObjectives.contains(objective)) {
            completedObjectives.add(objective);
            log.info("Mission {}: Objective '{}' completed ({}/{})", 
                    name, objective, completedObjectives.size(), objectives.size());
        } else {
            log.warn("Mission {}: Cannot complete objective '{}' (not found or already completed)", 
                    name, objective);
        }
    }
    
    /**
     * Check if all objectives are completed
     */
    public boolean areAllObjectivesCompleted() {
        return completedObjectives.size() >= objectives.size();
    }
    
    /**
     * Advance turn and check time limit
     */
    public void advanceTurn() {
        currentTurn++;
        log.debug("Mission {}: Turn advanced to {}/{}", name, currentTurn, turnLimit);
        
        if (currentTurn > turnLimit && turnLimit > 0) {
            isFailed = true;
            log.warn("Mission {}: Failed due to time limit exceeded (turn: {}, limit: {})", 
                    name, currentTurn, turnLimit);
        }
    }
    
    /**
     * Check if mission is successful
     */
    public boolean isSuccessful() {
        return isCompleted && !isFailed;
    }
    
    /**
     * Get remaining turns
     */
    public int getRemainingTurns() {
        return Math.max(0, turnLimit - currentTurn);
    }
    
    /**
     * Get mission status description
     */
    public String getStatusDescription() {
        if (isFailed) {
            return "Mission Failed - Time limit exceeded";
        } else if (isCompleted) {
            return "Mission Completed - All objectives achieved";
        } else {
            return "Mission in Progress - " + getRemainingTurns() + " turns remaining";
        }
    }
} 