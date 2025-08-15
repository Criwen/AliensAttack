package com.aliensattack.core.model;

import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.*;

/**
 * Mission Timer System for mission time management.
 * Implements mission deadlines, time pressure, and timer mechanics.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MissionTimerSystem {
    
    private String missionId;
    private Map<String, MissionTimer> activeTimers;
    private Map<String, TimerConsequence> timerConsequences;
    private Map<String, TimerManipulation> activeManipulations;
    private Map<String, TimerPressure> pressureEffects;
    private Map<String, TimerBonus> availableBonuses;
    private List<TimerEvent> timerEvents;
    private Map<String, Integer> timerValues;
    private Map<String, Boolean> timerStates;
    private int missionStartTime;
    private boolean isTimeCritical;
    private int pressureLevel;
    
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MissionTimer {
        private String timerId;
        private String timerName;
        private TimerType timerType;
        private int initialTime;
        private int currentTime;
        private int warningThreshold;
        private int criticalThreshold;
        private Map<String, Integer> timerEffects;
        private boolean isActive;
        private boolean isPaused;
        private String linkedObjective;
        private TimerPriority priority;
        
        public enum TimerType {
            PRIMARY,        // Primary mission timer
            SECONDARY,      // Secondary objective timer
            HIDDEN,         // Hidden timer (unknown to player)
            ESCALATION,     // Enemy escalation timer
            REINFORCEMENT,  // Enemy reinforcement timer
            EXTRACTION,     // Extraction timer
            HACKING,        // Hacking timer
            DEFUSAL,        // Bomb defusal timer
            RESCUE,         // Rescue timer
            EVACUATION      // Evacuation timer
        }
        
        public enum TimerPriority {
            CRITICAL,       // Critical timer - mission failure if expires
            HIGH,           // High priority timer
            MEDIUM,         // Medium priority timer
            LOW,            // Low priority timer
            OPTIONAL        // Optional timer
        }
    }
    
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class TimerConsequence {
        private String consequenceId;
        private String consequenceName;
        private ConsequenceType consequenceType;
        private String timerId;
        private Map<String, Integer> consequenceEffects;
        private boolean isTriggered;
        private int severity;
        private String description;
        
        public enum ConsequenceType {
            MISSION_FAILURE,    // Mission fails immediately
            OBJECTIVE_FAILURE,  // Specific objective fails
            ENEMY_ESCALATION,   // Enemies become stronger
            REINFORCEMENTS,     // Additional enemies arrive
            ENVIRONMENT_DAMAGE, // Environment becomes dangerous
            PLAYER_PENALTY,     // Player units receive penalties
            RESOURCE_LOSS,      // Lose resources or equipment
            REPUTATION_LOSS     // Lose reputation or standing
        }
    }
    
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class TimerManipulation {
        private String manipulationId;
        private String manipulationName;
        private ManipulationType manipulationType;
        private String targetTimerId;
        private int timeModification;
        private Map<String, Integer> manipulationEffects;
        private boolean isActive;
        private int cooldown;
        private String requiredAbility;
        
        public enum ManipulationType {
            EXTEND_TIME,        // Add time to timer
            REDUCE_TIME,        // Reduce time on timer
            PAUSE_TIMER,        // Pause timer temporarily
            RESET_TIMER,        // Reset timer to initial value
            ACCELERATE_TIMER,   // Make timer count down faster
            SLOW_TIMER,         // Make timer count down slower
            TRANSFER_TIME,      // Transfer time between timers
            SPLIT_TIMER         // Split timer into multiple timers
        }
    }
    
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class TimerPressure {
        private String pressureId;
        private String pressureName;
        private PressureType pressureType;
        private String timerId;
        private Map<String, Integer> pressureEffects;
        private int intensity;
        private boolean isActive;
        private int duration;
        
        public enum PressureType {
            ENEMY_ESCALATION,   // Enemies become more aggressive
            ENVIRONMENT_HAZARD,  // Environment becomes more dangerous
            PLAYER_STRESS,       // Player units become stressed
            RESOURCE_DRAIN,      // Resources deplete faster
            MOVEMENT_PENALTY,    // Movement becomes slower
            ACCURACY_PENALTY,    // Accuracy decreases
            DAMAGE_INCREASE,     // Enemy damage increases
            REINFORCEMENT_RATE   // Enemy reinforcement rate increases
        }
    }
    
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class TimerBonus {
        private String bonusId;
        private String bonusName;
        private BonusType bonusType;
        private String timerId;
        private Map<String, Integer> bonusEffects;
        private boolean isAvailable;
        private int requirement;
        private String description;
        
        public enum BonusType {
            TIME_EXTENSION,      // Extend timer duration
            PRESSURE_REDUCTION,  // Reduce pressure effects
            BONUS_REWARDS,       // Additional rewards
            ABILITY_UNLOCK,      // Unlock special abilities
            RESOURCE_BONUS,      // Additional resources
            REPUTATION_BONUS,    // Reputation increase
            EXPERIENCE_BONUS,    // Experience bonus
            EQUIPMENT_BONUS      // Equipment bonus
        }
    }
    
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class TimerEvent {
        private String eventId;
        private String eventName;
        private EventType eventType;
        private String timerId;
        private int triggerTime;
        private Map<String, Integer> eventEffects;
        private boolean isTriggered;
        private String description;
        
        public enum EventType {
            WARNING,             // Timer warning event
            CRITICAL,            // Critical timer event
            EXPIRATION,          // Timer expiration event
            MANIPULATION,        // Timer manipulation event
            PRESSURE_INCREASE,   // Pressure increase event
            BONUS_ACTIVATION,    // Bonus activation event
            CONSEQUENCE_TRIGGER, // Consequence trigger event
            RESET_EVENT          // Timer reset event
        }
    }
    

    
    /**
     * Add mission timer
     */
    public void addTimer(MissionTimer timer) {
        activeTimers.put(timer.getTimerId(), timer);
        timerValues.put(timer.getTimerId(), timer.getCurrentTime());
        timerStates.put(timer.getTimerId(), timer.isActive());
    }
    
    /**
     * Update timer
     */
    public void updateTimer(String timerId, int timeChange) {
        MissionTimer timer = activeTimers.get(timerId);
        if (timer != null && timer.isActive() && !timer.isPaused()) {
            timer.setCurrentTime(Math.max(0, timer.getCurrentTime() + timeChange));
            timerValues.put(timerId, timer.getCurrentTime());
            
            checkTimerEvents(timer);
            checkTimerConsequences(timer);
            updatePressureEffects(timer);
        }
    }
    
    /**
     * Manipulate timer
     */
    public boolean manipulateTimer(TimerManipulation manipulation) {
        if (canPerformManipulation(manipulation)) {
            MissionTimer timer = activeTimers.get(manipulation.getTargetTimerId());
            if (timer != null) {
                applyManipulation(manipulation, timer);
                activeManipulations.put(manipulation.getManipulationId(), manipulation);
                return true;
            }
        }
        return false;
    }
    
    /**
     * Apply pressure effect
     */
    public boolean applyPressureEffect(TimerPressure pressure) {
        if (canApplyPressure(pressure)) {
            pressureEffects.put(pressure.getPressureId(), pressure);
            pressure.setActive(true);
            applyPressureEffects(pressure);
            return true;
        }
        return false;
    }
    
    /**
     * Activate timer bonus
     */
    public boolean activateTimerBonus(String bonusId) {
        TimerBonus bonus = availableBonuses.get(bonusId);
        if (bonus != null && bonus.isAvailable()) {
            applyBonusEffects(bonus);
            bonus.setAvailable(false);
            return true;
        }
        return false;
    }
    
    /**
     * Add timer consequence
     */
    public void addTimerConsequence(TimerConsequence consequence) {
        timerConsequences.put(consequence.getConsequenceId(), consequence);
    }
    
    /**
     * Add timer event
     */
    public void addTimerEvent(TimerEvent event) {
        timerEvents.add(event);
    }
    
    /**
     * Check timer events
     */
    private void checkTimerEvents(MissionTimer timer) {
        for (TimerEvent event : timerEvents) {
            if (event.getTimerId().equals(timer.getTimerId()) && !event.isTriggered()) {
                if (timer.getCurrentTime() <= event.getTriggerTime()) {
                    triggerTimerEvent(event);
                }
            }
        }
    }
    
    /**
     * Check timer consequences
     */
    private void checkTimerConsequences(MissionTimer timer) {
        for (TimerConsequence consequence : timerConsequences.values()) {
            if (consequence.getTimerId().equals(timer.getTimerId()) && !consequence.isTriggered()) {
                if (timer.getCurrentTime() <= 0) {
                    triggerTimerConsequence(consequence);
                }
            }
        }
    }
    
    /**
     * Update pressure effects
     */
    private void updatePressureEffects(MissionTimer timer) {
        for (TimerPressure pressure : pressureEffects.values()) {
            if (pressure.getTimerId().equals(timer.getTimerId()) && pressure.isActive()) {
                updatePressureIntensity(pressure, timer);
            }
        }
    }
    
    /**
     * Check if manipulation can be performed
     */
    private boolean canPerformManipulation(TimerManipulation manipulation) {
        if (manipulation.getRequiredAbility() != null) {
            // Check if unit has required ability
            return true; // Simplified check
        }
        return true;
    }
    
    /**
     * Check if pressure can be applied
     */
    private boolean canApplyPressure(TimerPressure pressure) {
        MissionTimer timer = activeTimers.get(pressure.getTimerId());
        return timer != null && timer.isActive();
    }
    
    /**
     * Apply manipulation to timer
     */
    private void applyManipulation(TimerManipulation manipulation, MissionTimer timer) {
        switch (manipulation.getManipulationType()) {
            case EXTEND_TIME:
                timer.setCurrentTime(timer.getCurrentTime() + manipulation.getTimeModification());
                break;
            case REDUCE_TIME:
                timer.setCurrentTime(Math.max(0, timer.getCurrentTime() - manipulation.getTimeModification()));
                break;
            case PAUSE_TIMER:
                timer.setPaused(true);
                break;
            case RESET_TIMER:
                timer.setCurrentTime(timer.getInitialTime());
                break;
            case ACCELERATE_TIMER:
                // Increase countdown rate
                break;
            case SLOW_TIMER:
                // Decrease countdown rate
                break;
            case TRANSFER_TIME:
                // Transfer time between timers
                break;
            case SPLIT_TIMER:
                // Split timer into multiple timers
                break;
        }
        
        timerValues.put(timer.getTimerId(), timer.getCurrentTime());
    }
    
    /**
     * Apply pressure effects
     */
    private void applyPressureEffects(TimerPressure pressure) {
        for (Map.Entry<String, Integer> effect : pressure.getPressureEffects().entrySet()) {
            applyPressureEffectToMission(effect.getKey(), effect.getValue());
        }
    }
    
    /**
     * Apply bonus effects
     */
    private void applyBonusEffects(TimerBonus bonus) {
        for (Map.Entry<String, Integer> effect : bonus.getBonusEffects().entrySet()) {
            applyBonusEffectToMission(effect.getKey(), effect.getValue());
        }
    }
    
    /**
     * Trigger timer event
     */
    private void triggerTimerEvent(TimerEvent event) {
        event.setTriggered(true);
        
        for (Map.Entry<String, Integer> effect : event.getEventEffects().entrySet()) {
            applyEventEffectToMission(effect.getKey(), effect.getValue());
        }
    }
    
    /**
     * Trigger timer consequence
     */
    private void triggerTimerConsequence(TimerConsequence consequence) {
        consequence.setTriggered(true);
        
        for (Map.Entry<String, Integer> effect : consequence.getConsequenceEffects().entrySet()) {
            applyConsequenceEffectToMission(effect.getKey(), effect.getValue());
        }
    }
    
    /**
     * Update pressure intensity
     */
    private void updatePressureIntensity(TimerPressure pressure, MissionTimer timer) {
        // Increase pressure intensity as time runs out
        double timePercentage = (double) timer.getCurrentTime() / timer.getInitialTime();
        int newIntensity = (int) (pressure.getIntensity() * (1.0 - timePercentage));
        pressure.setIntensity(Math.max(1, newIntensity));
    }
    
    /**
     * Apply pressure effect to mission
     */
    private void applyPressureEffectToMission(String effectType, int effectValue) {
        switch (effectType) {
            case "enemy_escalation":
                pressureLevel += effectValue;
                break;
            case "environment_hazard":
                // Apply environment hazard
                break;
            case "player_stress":
                // Apply stress to player units
                break;
            case "resource_drain":
                // Drain resources
                break;
            case "movement_penalty":
                // Apply movement penalty
                break;
            case "accuracy_penalty":
                // Apply accuracy penalty
                break;
            case "damage_increase":
                // Increase enemy damage
                break;
            case "reinforcement_rate":
                // Increase reinforcement rate
                break;
        }
    }
    
    /**
     * Apply bonus effect to mission
     */
    private void applyBonusEffectToMission(String effectType, int effectValue) {
        switch (effectType) {
            case "time_extension":
                // Extend all active timers by the provided effect value
                for (MissionTimer t : activeTimers.values()) {
                    if (t.isActive()) {
                        t.setCurrentTime(t.getCurrentTime() + effectValue);
                        timerValues.put(t.getTimerId(), t.getCurrentTime());
                    }
                }
                break;
            case "pressure_reduction":
                pressureLevel = Math.max(0, pressureLevel - effectValue);
                break;
            case "bonus_rewards":
                // Add bonus rewards
                break;
            case "ability_unlock":
                // Unlock abilities
                break;
            case "resource_bonus":
                // Add resources
                break;
            case "reputation_bonus":
                // Add reputation
                break;
            case "experience_bonus":
                // Add experience
                break;
            case "equipment_bonus":
                // Add equipment
                break;
        }
    }
    
    /**
     * Apply event effect to mission
     */
    private void applyEventEffectToMission(String effectType, int effectValue) {
        // Apply event effects
        switch (effectType) {
            case "warning":
                // Show warning
                break;
            case "critical":
                isTimeCritical = true;
                break;
            case "expiration":
                // Handle expiration
                break;
        }
    }
    
    /**
     * Apply consequence effect to mission
     */
    private void applyConsequenceEffectToMission(String effectType, int effectValue) {
        switch (effectType) {
            case "mission_failure":
                // Trigger mission failure
                break;
            case "objective_failure":
                // Fail specific objective
                break;
            case "enemy_escalation":
                pressureLevel += effectValue;
                break;
            case "reinforcements":
                // Call reinforcements
                break;
            case "environment_damage":
                // Damage environment
                break;
            case "player_penalty":
                // Apply player penalties
                break;
            case "resource_loss":
                // Lose resources
                break;
            case "reputation_loss":
                // Lose reputation
                break;
        }
    }
    
    /**
     * Get timer by ID
     */
    public MissionTimer getTimer(String timerId) {
        return activeTimers.get(timerId);
    }
    
    /**
     * Get all active timers
     */
    public List<MissionTimer> getActiveTimers() {
        return activeTimers.values().stream()
                .filter(MissionTimer::isActive)
                .collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
    }
    
    /**
     * Get critical timers
     */
    public List<MissionTimer> getCriticalTimers() {
        return activeTimers.values().stream()
                .filter(timer -> timer.isActive() && timer.getCurrentTime() <= timer.getCriticalThreshold())
                .collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
    }
    
    /**
     * Get total time remaining
     */
    public int getTotalTimeRemaining() {
        return activeTimers.values().stream()
                .filter(MissionTimer::isActive)
                .mapToInt(MissionTimer::getCurrentTime)
                .sum();
    }
    
    /**
     * Check if mission is time critical
     */
    public boolean isTimeCritical() {
        return isTimeCritical || getCriticalTimers().size() > 0;
    }
    
    /**
     * Get pressure level
     */
    public int getPressureLevel() {
        return pressureLevel;
    }
    
    /**
     * Get available bonuses
     */
    public List<TimerBonus> getAvailableBonuses() {
        return availableBonuses.values().stream()
                .filter(TimerBonus::isAvailable)
                .collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
    }
    
    /**
     * Get active manipulations
     */
    public List<TimerManipulation> getActiveManipulations() {
        return activeManipulations.values().stream()
                .filter(TimerManipulation::isActive)
                .collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
    }
    
    /**
     * Get active pressure effects
     */
    public List<TimerPressure> getActivePressureEffects() {
        return pressureEffects.values().stream()
                .filter(TimerPressure::isActive)
                .collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
    }
}
