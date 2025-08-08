package com.aliensattack.core.model;

import com.aliensattack.core.enums.MissionType;
import com.aliensattack.core.config.GameConfig;
import lombok.Getter;
import lombok.Setter;
import java.util.List;
import java.util.ArrayList;

/**
 * Represents a mission with objectives and conditions
 */
@Getter
@Setter
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
        
        // Set default objectives based on mission type
        setDefaultObjectives();
    }
    
    /**
     * Set default objectives based on mission type
     */
    private void setDefaultObjectives() {
        switch (type) {
            case ELIMINATION -> objectives.add("Eliminate all enemy units");
            case EXTRACTION -> objectives.add("Extract VIP to designated area");
            case DEFENSE -> objectives.add("Defend position for " + turnLimit + " turns");
            case SABOTAGE -> objectives.add("Destroy target objective");
            case RECONNAISSANCE -> objectives.add("Scout and gather intelligence");
            case ESCORT -> objectives.add("Escort VIP to extraction point");
            case TIMED_ASSAULT -> objectives.add("Complete objective within " + turnLimit + " turns");
            case ASSASSINATION -> objectives.add("Eliminate specific target");
            case HACKING -> objectives.add("Hack terminals or systems");
            case DESTROY -> objectives.add("Destroy alien facilities");
            case RESCUE -> objectives.add("Rescue captured soldiers");
            case INFILTRATION -> objectives.add("Stealth infiltration mission");
            case RETALIATION -> objectives.add("Retaliation strike mission");
            case GUERRILLA -> objectives.add("Guerrilla warfare mission");
            case SUPPLY_RAID -> objectives.add("Raid supply convoy");
            case TERROR -> objectives.add("Defend against terror attack");
            case COUNCIL -> objectives.add("Complete council mission");
        }
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
        if (currentTurn > turnLimit && turnLimit > 0) {
            isFailed = true;
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