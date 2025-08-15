package com.aliensattack.core.model;

import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.Date;
import java.util.List;
import java.util.ArrayList;

/**
 * Campaign Model - XCOM2 Strategic Layer
 * Represents the overall campaign state and progress
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Campaign {
    
    private String campaignId;
    private String name;
    private Date startDate;
    private int currentDay;
    private Difficulty difficulty;
    private Status status;
    private List<Region> contactedRegions;
    private List<Region> availableRegions;
    private double threatLevel;
    private int totalMissions;
    private int successfulMissions;
    private int failedMissions;
    

    
    public void addContactedRegion(Region region) {
        if (!contactedRegions.contains(region)) {
            contactedRegions.add(region);
        }
    }
    
    public void removeContactedRegion(Region region) {
        contactedRegions.remove(region);
    }
    
    public boolean isRegionContacted(Region region) {
        return contactedRegions.contains(region);
    }
    
    public int getContactedRegionCount() {
        return contactedRegions.size();
    }
    
    public double getSuccessRate() {
        if (totalMissions == 0) return 0.0;
        return (double) successfulMissions / totalMissions * 100.0;
    }
    
    public boolean isVictory() {
        return status == Status.VICTORY;
    }
    
    public boolean isDefeat() {
        return status == Status.DEFEAT;
    }
    
    public boolean isInProgress() {
        return status == Status.IN_PROGRESS;
    }
    
    public void advanceDay() {
        currentDay++;
        // Update campaign state
    }
    
    public void addMissionResult(boolean success) {
        totalMissions++;
        if (success) {
            successfulMissions++;
        } else {
            failedMissions++;
        }
    }
    
    /**
     * Campaign difficulty levels
     */
    public enum Difficulty {
        ROOKIE("Rookie", 0.8, "Easier combat, more resources"),
        NORMAL("Normal", 1.0, "Standard XCOM2 experience"),
        VETERAN("Veteran", 1.2, "Harder combat, fewer resources"),
        COMMANDER("Commander", 1.5, "Very challenging gameplay"),
        LEGEND("Legend", 2.0, "Extreme difficulty, ironman mode");
        
        private final String displayName;
        private final double multiplier;
        private final String description;
        
        Difficulty(String displayName, double multiplier, String description) {
            this.displayName = displayName;
            this.multiplier = multiplier;
            this.description = description;
        }
        
        public String getDisplayName() {
            return displayName;
        }
        
        public double getMultiplier() {
            return multiplier;
        }
        
        public String getDescription() {
            return description;
        }
    }
    
    /**
     * Campaign status
     */
    public enum Status {
        IN_PROGRESS("In Progress", "Campaign is ongoing"),
        VICTORY("Victory", "Campaign won successfully"),
        DEFEAT("Defeat", "Campaign lost"),
        PAUSED("Paused", "Campaign temporarily paused");
        
        private final String displayName;
        private final String description;
        
        Status(String displayName, String description) {
            this.displayName = displayName;
            this.description = description;
        }
        
        public String getDisplayName() {
            return displayName;
        }
        
        public String getDescription() {
            return description;
        }
    }
}
