package com.aliensattack.mission;

/**
 * Mission types for different mission objectives
 */
public enum MissionType {
    ELIMINATE_ALL_ENEMIES("Eliminate All Enemies", "Kill all enemy units"),
    REACH_EXTRACTION_POINT("Reach Extraction Point", "Move units to extraction zone"),
    DEFEND_POSITION("Defend Position", "Defend area for X turns"),
    ESCORT_VIP("Escort VIP", "Protect VIP unit"),
    SABOTAGE_TARGET("Sabotage Target", "Destroy specific target"),
    RECONNAISSANCE("Reconnaissance", "Scout specific areas"),
    RESCUE_HOSTAGES("Rescue Hostages", "Free hostage units"),
    SURVIVAL("Survival", "Survive for X turns");
    
    private final String displayName;
    private final String description;
    
    MissionType(String displayName, String description) {
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
