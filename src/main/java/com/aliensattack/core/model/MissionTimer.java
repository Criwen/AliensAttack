package com.aliensattack.core.model;

import com.aliensattack.core.enums.MissionTimerType;
import lombok.Getter;
import lombok.Setter;

import java.util.*;

/**
 * Mission Timer - Turn-based mission timers with consequences
 * Implements XCOM 2 Mission Timer System
 */
@Getter
@Setter
public class MissionTimer {
    
    private String timerId;
    private MissionTimerType timerType;
    private int totalTurns;
    private int remainingTurns;
    private int warningThreshold;
    private int criticalThreshold;
    private boolean isActive;
    private boolean isExpired;
    private boolean isWarning;
    private boolean isCritical;
    private List<String> timerEvents;
    private Map<String, Integer> eventHistory;
    private String timerDescription;
    private int timerValue;
    private boolean hasConsequences;
    private List<String> consequences;
    private boolean isPaused;
    private int pauseTurns;
    private boolean isExtended;
    private int extensionTurns;
    
    public MissionTimer(String timerId, MissionTimerType timerType, int totalTurns, String description) {
        this.timerId = timerId;
        this.timerType = timerType;
        this.totalTurns = totalTurns;
        this.remainingTurns = totalTurns;
        this.warningThreshold = totalTurns / 3;
        this.criticalThreshold = totalTurns / 6;
        this.isActive = true;
        this.isExpired = false;
        this.isWarning = false;
        this.isCritical = false;
        this.timerEvents = new ArrayList<>();
        this.eventHistory = new HashMap<>();
        this.timerDescription = description;
        this.timerValue = 0;
        this.hasConsequences = true;
        this.consequences = new ArrayList<>();
        this.isPaused = false;
        this.pauseTurns = 0;
        this.isExtended = false;
        this.extensionTurns = 0;
        
        initializeTimerConsequences();
    }
    
    private void initializeTimerConsequences() {
        switch (timerType) {
            case EXTRACTION:
                consequences.add("Mission_Failure");
                consequences.add("Unit_Loss");
                consequences.add("Intel_Loss");
                break;
            case HACKING:
                consequences.add("Hack_Failure");
                consequences.add("Security_Alert");
                consequences.add("Reinforcements");
                break;
            case DEFENSE:
                consequences.add("Position_Lost");
                consequences.add("Objective_Failure");
                consequences.add("Enemy_Advance");
                break;
            case SABOTAGE:
                consequences.add("Sabotage_Failure");
                consequences.add("Alarm_Triggered");
                consequences.add("Enemy_Response");
                break;
            case RECONNAISSANCE:
                consequences.add("Intel_Loss");
                consequences.add("Stealth_Break");
                consequences.add("Detection");
                break;
        }
    }
    
    /**
     * Advance timer by one turn
     */
    public void advanceTimer() {
        if (!isActive || isPaused) {
            return;
        }
        
        remainingTurns--;
        timerEvents.add("Timer_Advanced: " + remainingTurns + " turns remaining");
        
        // Check for expiration
        if (remainingTurns <= 0) {
            expireTimer();
            return;
        }
        
        // Check for critical threshold
        if (remainingTurns <= criticalThreshold && !isCritical) {
            setCritical(true);
            timerEvents.add("Timer_Critical: " + remainingTurns + " turns remaining");
        }
        
        // Check for warning threshold
        if (remainingTurns <= warningThreshold && !isWarning) {
            setWarning(true);
            timerEvents.add("Timer_Warning: " + remainingTurns + " turns remaining");
        }
    }
    
    /**
     * Expire timer
     */
    public void expireTimer() {
        isExpired = true;
        isActive = false;
        remainingTurns = 0;
        timerEvents.add("Timer_Expired");
        
        if (hasConsequences) {
            applyConsequences();
        }
    }
    
    /**
     * Apply timer consequences
     */
    private void applyConsequences() {
        for (String consequence : consequences) {
            timerEvents.add("Consequence_Applied: " + consequence);
            eventHistory.put(consequence, eventHistory.getOrDefault(consequence, 0) + 1);
        }
    }
    
    /**
     * Extend timer
     */
    public boolean extendTimer(int turns) {
        if (isExpired) {
            return false;
        }
        
        remainingTurns += turns;
        extensionTurns += turns;
        isExtended = true;
        timerEvents.add("Timer_Extended: +" + turns + " turns");
        return true;
    }
    
    /**
     * Pause timer
     */
    public void pauseTimer(int turns) {
        isPaused = true;
        pauseTurns = turns;
        timerEvents.add("Timer_Paused: " + turns + " turns");
    }
    
    /**
     * Resume timer
     */
    public void resumeTimer() {
        isPaused = false;
        pauseTurns = 0;
        timerEvents.add("Timer_Resumed");
    }
    
    /**
     * Reset timer
     */
    public void resetTimer() {
        remainingTurns = totalTurns;
        isActive = true;
        isExpired = false;
        isWarning = false;
        isCritical = false;
        isPaused = false;
        pauseTurns = 0;
        isExtended = false;
        extensionTurns = 0;
        timerEvents.add("Timer_Reset");
    }
    
    /**
     * Add time bonus
     */
    public void addTimeBonus(int turns) {
        if (!isExpired) {
            remainingTurns += turns;
            timerEvents.add("Time_Bonus: +" + turns + " turns");
        }
    }
    
    /**
     * Add time penalty
     */
    public void addTimePenalty(int turns) {
        if (!isExpired) {
            remainingTurns = Math.max(remainingTurns - turns, 0);
            timerEvents.add("Time_Penalty: -" + turns + " turns");
            
            if (remainingTurns <= 0) {
                expireTimer();
            }
        }
    }
    
    /**
     * Get timer progress percentage
     */
    public double getProgressPercentage() {
        if (totalTurns <= 0) {
            return 0.0;
        }
        
        double elapsed = totalTurns - remainingTurns;
        return (elapsed / totalTurns) * 100.0;
    }
    
    /**
     * Get timer status
     */
    public String getTimerStatus() {
        StringBuilder status = new StringBuilder();
        status.append("Timer ID: ").append(timerId).append("\n");
        status.append("Timer Type: ").append(timerType).append("\n");
        status.append("Description: ").append(timerDescription).append("\n");
        status.append("Remaining Turns: ").append(remainingTurns).append("/").append(totalTurns).append("\n");
        status.append("Progress: ").append(String.format("%.1f", getProgressPercentage())).append("%\n");
        status.append("Active: ").append(isActive).append("\n");
        status.append("Expired: ").append(isExpired).append("\n");
        status.append("Warning: ").append(isWarning).append("\n");
        status.append("Critical: ").append(isCritical).append("\n");
        status.append("Paused: ").append(isPaused).append(" (").append(pauseTurns).append(" turns)\n");
        status.append("Extended: ").append(isExtended).append(" (+").append(extensionTurns).append(" turns)\n");
        status.append("Has Consequences: ").append(hasConsequences).append("\n");
        
        if (!consequences.isEmpty()) {
            status.append("Consequences: ");
            for (String consequence : consequences) {
                status.append(consequence).append(", ");
            }
            status.append("\n");
        }
        
        return status.toString();
    }
    
    /**
     * Get timer events
     */
    public List<String> getRecentEvents(int count) {
        int startIndex = Math.max(0, timerEvents.size() - count);
        return timerEvents.subList(startIndex, timerEvents.size());
    }
    
    /**
     * Check if timer is in warning state
     */
    public boolean isInWarning() {
        return remainingTurns <= warningThreshold && remainingTurns > criticalThreshold;
    }
    
    /**
     * Check if timer is in critical state
     */
    public boolean isInCritical() {
        return remainingTurns <= criticalThreshold && remainingTurns > 0;
    }
    
    /**
     * Check if timer is expired
     */
    public boolean isExpired() {
        return remainingTurns <= 0;
    }
    
    /**
     * Get timer urgency level
     */
    public int getUrgencyLevel() {
        if (isExpired) {
            return 4; // Critical
        } else if (isInCritical()) {
            return 3; // Critical
        } else if (isInWarning()) {
            return 2; // Warning
        } else {
            return 1; // Normal
        }
    }
}
