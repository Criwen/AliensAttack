package com.aliensattack.mission;

import lombok.Getter;
import lombok.Setter;

/**
 * Represents an individual mission objective
 */
@Getter
@Setter
public class MissionObjective {
    
    private String id;
    private String name;
    private String description;
    private int requiredProgress;
    private int currentProgress;
    private boolean completed;
    
    public MissionObjective(String id, String name, String description, int requiredProgress) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.requiredProgress = requiredProgress;
        this.currentProgress = 0;
        this.completed = false;
    }
    
    /**
     * Update objective progress
     */
    public void updateProgress(int progress) {
        this.currentProgress = Math.max(0, Math.min(progress, requiredProgress));
        
        if (currentProgress >= requiredProgress && !completed) {
            this.completed = true;
        }
    }
    
    /**
     * Add progress to current objective
     */
    public void addProgress(int progress) {
        updateProgress(currentProgress + progress);
    }
    
    /**
     * Get progress percentage
     */
    public double getProgressPercentage() {
        if (requiredProgress == 0) return 100.0;
        return (double) currentProgress / requiredProgress * 100.0;
    }
    
    /**
     * Check if objective is completed
     */
    public boolean isCompleted() {
        return completed;
    }
    
    /**
     * Reset objective progress
     */
    public void reset() {
        this.currentProgress = 0;
        this.completed = false;
    }
}
