package com.aliensattack.mission;

import com.aliensattack.core.model.Position;
import lombok.Getter;
import lombok.Setter;
import com.aliensattack.core.config.GameConfig;
import com.aliensattack.core.model.MissionTimer;

import java.util.List;
import java.util.ArrayList;

/**
 * Represents a mission with objectives and completion criteria
 */
@Getter
@Setter
public class Mission {
    
    private String id;
    private String name;
    private String description;
    private MissionType type;
    private List<MissionObjective> objectives;
    private int maxTurns;
    private List<MissionTimer> timers;
    
    // Mission-specific positions
    private List<Position> extractionPoints;
    private List<Position> vipPositions;
    private List<Position> sabotageTargets;
    private List<Position> reconnaissanceAreas;
    private List<Position> hostagePositions;
    
    public Mission(String name, MissionType type, String description) {
        this.name = name;
        this.type = type;
        this.description = description;
        this.objectives = new ArrayList<>();
        this.timers = new ArrayList<>();
        this.maxTurns = GameConfig.getDefaultMissionTurns(); // Use configuration instead of hardcoded 30
        
        // TODO: Implement comprehensive mission initialization system
        // - Load mission configuration from properties
        // - Initialize mission-specific mechanics
        // - Set up victory/defeat conditions
        // - Initialize mission timers
        // - Set up mission rewards and penalties
    }
    
    /**
     * Add an objective to the mission
     */
    public void addObjective(MissionObjective objective) {
        objectives.add(objective);
    }
    
    /**
     * Get objective by ID
     */
    public MissionObjective getObjectiveById(String objectiveId) {
        return objectives.stream()
            .filter(obj -> obj.getId().equals(objectiveId))
            .findFirst()
            .orElse(null);
    }
    
    /**
     * Add extraction point
     */
    public void addExtractionPoint(Position position) {
        extractionPoints.add(position);
    }
    
    /**
     * Add VIP position
     */
    public void addVipPosition(Position position) {
        vipPositions.add(position);
    }
    
    /**
     * Add sabotage target
     */
    public void addSabotageTarget(Position position) {
        sabotageTargets.add(position);
    }
    
    /**
     * Add reconnaissance area
     */
    public void addReconnaissanceArea(Position position) {
        reconnaissanceAreas.add(position);
    }
    
    /**
     * Add hostage position
     */
    public void addHostagePosition(Position position) {
        hostagePositions.add(position);
    }
    
    /**
     * Check if mission is completed
     */
    public boolean isCompleted() {
        return objectives.stream().allMatch(MissionObjective::isCompleted);
    }
    
    /**
     * Get mission progress percentage
     */
    public double getProgressPercentage() {
        if (objectives.isEmpty()) return 100.0;
        
        long completedCount = objectives.stream()
            .filter(MissionObjective::isCompleted)
            .count();
        
        return (double) completedCount / objectives.size() * 100.0;
    }
}
