package com.aliensattack.core.data;

/**
 * Detection Level Enum - XCOM2 Stealth System
 * Represents different levels of detection capability
 */
public enum DetectionLevel {
    MASTER(0.1, "Master detection skills"),
    EXPERT(0.2, "Expert detection skills"),
    TRAINED(0.4, "Trained detection skills"),
    BASIC(0.6, "Basic detection skills"),
    NONE(0.8, "No detection skills");
    
    private final double detectionChance;
    private final String description;
    
    DetectionLevel(double detectionChance, String description) {
        this.detectionChance = detectionChance;
        this.description = description;
    }
    
    public double getDetectionChance() {
        return detectionChance;
    }
    
    public String getDescription() {
        return description;
    }
}
