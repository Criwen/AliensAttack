package com.aliensattack.core.model;

import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.util.*;
import java.time.LocalDateTime;

/**
 * Mission Pressure System for mission difficulty scaling.
 * Implements pressure mechanics, difficulty progression, and adaptive challenges.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MissionPressureSystem {
    
    private String missionId;
    private MissionPressureLevel currentPressureLevel;
    private Map<String, PressureModifier> activeModifiers;
    private List<PressureEvent> pressureEvents;
    private Map<String, Integer> pressureValues;
    private Map<String, Boolean> pressureStates;
    private int missionStartTime;
    private int currentTurn;
    private boolean isPressureActive;
    private int pressureThreshold;
    private int currentPressure;
    private List<PressureConsequence> activeConsequences;
    
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MissionPressureLevel {
        private String levelId;
        private String levelName;
        private int pressureValue;
        private List<String> modifiers;
        private List<String> consequences;
        private boolean isEscalating;
        private int escalationRate;
        private int maxPressure;
        private String description;
    }
    
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PressureModifier {
        private String modifierId;
        private String modifierName;
        private ModifierType type;
        private int value;
        private int duration;
        private int currentDuration;
        private String source;
        private boolean isActive;
        private List<String> affectedSystems;
        private String description;
    }
    
    public enum ModifierType {
        ENEMY_STRENGTH,      // Increase enemy power
        ENEMY_NUMBERS,       // Increase enemy count
        TIME_PRESSURE,       // Reduce available time
        ENVIRONMENTAL_HAZARD, // Add environmental dangers
        PSIONIC_INTERFERENCE, // Reduce psionic effectiveness
        EQUIPMENT_FAILURE,   // Increase equipment problems
        MORALE_PENALTY,      // Reduce squad morale
        VISION_RESTRICTION,  // Limit visibility
        MOVEMENT_PENALTY,    // Reduce movement options
        COVER_DESTRUCTION    // Increase cover destruction
    }
    
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PressureEvent {
        private String eventId;
        private String eventName;
        private EventType type;
        private int turn;
        private String description;
        private List<String> consequences;
        private boolean isTriggered;
        private int triggerThreshold;
        private String triggerCondition;
    }
    
    public enum EventType {
        ENEMY_REINFORCEMENT,    // Additional enemies arrive
        ENVIRONMENTAL_CRISIS,   // Environmental hazard appears
        EQUIPMENT_MALFUNCTION,  // Equipment fails
        PSIONIC_STORM,          // Psionic interference
        TIME_CRUNCH,            // Time pressure increases
        MORALE_CRISIS,          // Squad morale drops
        COVER_COLLAPSE,         // Cover structures fail
        WEATHER_ESCALATION,     // Weather worsens
        ALIEN_ESCAPE,           // Aliens try to escape
        OBJECTIVE_CRISIS        // Objective becomes harder
    }
    
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PressureConsequence {
        private String consequenceId;
        private String consequenceName;
        private ConsequenceType type;
        private int severity;
        private int duration;
        private int currentDuration;
        private String description;
        private List<String> affectedUnits;
        private boolean isActive;
        private String source;
    }
    
    public enum ConsequenceType {
        ENEMY_STRENGTH_BOOST,   // Enemies become stronger
        ENEMY_NUMBER_INCREASE,  // More enemies spawn
        TIME_REDUCTION,          // Less time available
        ENVIRONMENTAL_DAMAGE,   // Environmental hazards
        PSIONIC_DISRUPTION,     // Psionic abilities weakened
        EQUIPMENT_DEGRADATION,  // Equipment problems
        MORALE_LOSS,            // Squad morale drops
        VISION_REDUCTION,       // Visibility reduced
        MOVEMENT_RESTRICTION,   // Movement limited
        COVER_DESTRUCTION       // Cover destroyed
    }
    
    /**
     * Initialize mission pressure system
     */
    public boolean initializeSystem(String missionId, int basePressure) {
        this.missionId = missionId;
        this.currentPressure = basePressure;
        this.pressureThreshold = basePressure * 2;
        this.isPressureActive = true;
        this.currentTurn = 0;
        this.missionStartTime = 0;
        
        this.activeModifiers = new HashMap<>();
        this.pressureEvents = new ArrayList<>();
        this.pressureValues = new HashMap<>();
        this.pressureStates = new HashMap<>();
        this.activeConsequences = new ArrayList<>();
        
        // Initialize base pressure level
        initializeBasePressureLevel();
        
        return true;
    }
    
    /**
     * Initialize base pressure level
     */
    private void initializeBasePressureLevel() {
        MissionPressureLevel baseLevel = MissionPressureLevel.builder()
            .levelId("BASE")
            .levelName("Base Pressure")
            .pressureValue(currentPressure)
            .modifiers(new ArrayList<>())
            .consequences(new ArrayList<>())
            .isEscalating(false)
            .escalationRate(1)
            .maxPressure(pressureThreshold)
            .description("Base mission pressure level")
            .build();
        
        this.currentPressureLevel = baseLevel;
    }
    
    /**
     * Advance mission turn and update pressure
     */
    public boolean advanceTurn() {
        currentTurn++;
        
        // Update pressure based on current situation
        updatePressure();
        
        // Check for pressure events
        checkPressureEvents();
        
        // Apply pressure consequences
        applyPressureConsequences();
        
        // Update modifiers
        updateModifiers();
        
        return true;
    }
    
    /**
     * Update mission pressure
     */
    private void updatePressure() {
        // Base pressure increase per turn
        int baseIncrease = 1;
        
        // Add pressure from active modifiers
        for (PressureModifier modifier : activeModifiers.values()) {
            if (modifier.isActive()) {
                baseIncrease += modifier.getValue();
            }
        }
        
        // Add pressure from active consequences
        for (PressureConsequence consequence : activeConsequences) {
            if (consequence.isActive()) {
                baseIncrease += consequence.getSeverity();
            }
        }
        
        currentPressure += baseIncrease;
        
        // Check if pressure level should escalate
        if (currentPressure >= pressureThreshold && !currentPressureLevel.isEscalating()) {
            escalatePressure();
        }
    }
    
    /**
     * Escalate mission pressure
     */
    private void escalatePressure() {
        currentPressureLevel.setEscalating(true);
        
        // Create escalation consequence
        PressureConsequence escalation = PressureConsequence.builder()
            .consequenceId("ESCALATION_" + currentTurn)
            .consequenceName("Mission Escalation")
            .type(ConsequenceType.ENEMY_STRENGTH_BOOST)
            .severity(5)
            .duration(3)
            .currentDuration(3)
            .description("Mission pressure has escalated - enemies become stronger")
            .affectedUnits(new ArrayList<>())
            .isActive(true)
            .source("Pressure System")
            .build();
        
        activeConsequences.add(escalation);
        
        // Increase pressure threshold
        pressureThreshold = (int) (pressureThreshold * 1.5);
    }
    
    /**
     * Check for pressure events
     */
    private void checkPressureEvents() {
        for (PressureEvent event : pressureEvents) {
            if (!event.isTriggered() && shouldTriggerEvent(event)) {
                triggerPressureEvent(event);
            }
        }
    }
    
    /**
     * Check if event should be triggered
     */
    private boolean shouldTriggerEvent(PressureEvent event) {
        return currentPressure >= event.getTriggerThreshold();
    }
    
    /**
     * Trigger pressure event
     */
    private void triggerPressureEvent(PressureEvent event) {
        event.setTriggered(true);
        
        // Create consequence for the event
        PressureConsequence eventConsequence = PressureConsequence.builder()
            .consequenceId("EVENT_" + event.getEventId())
            .consequenceName(event.getEventName())
            .type(mapEventTypeToConsequence(event.getType()))
            .severity(3)
            .duration(2)
            .currentDuration(2)
            .description(event.getDescription())
            .affectedUnits(new ArrayList<>())
            .isActive(true)
            .source("Pressure Event: " + event.getEventName())
            .build();
        
        activeConsequences.add(eventConsequence);
    }
    
    /**
     * Map event type to consequence type
     */
    private ConsequenceType mapEventTypeToConsequence(EventType eventType) {
        return switch (eventType) {
            case ENEMY_REINFORCEMENT -> ConsequenceType.ENEMY_NUMBER_INCREASE;
            case ENVIRONMENTAL_CRISIS -> ConsequenceType.ENVIRONMENTAL_DAMAGE;
            case EQUIPMENT_MALFUNCTION -> ConsequenceType.EQUIPMENT_DEGRADATION;
            case PSIONIC_STORM -> ConsequenceType.PSIONIC_DISRUPTION;
            case TIME_CRUNCH -> ConsequenceType.TIME_REDUCTION;
            case MORALE_CRISIS -> ConsequenceType.MORALE_LOSS;
            case COVER_COLLAPSE -> ConsequenceType.COVER_DESTRUCTION;
            case WEATHER_ESCALATION -> ConsequenceType.ENVIRONMENTAL_DAMAGE;
            case ALIEN_ESCAPE -> ConsequenceType.ENEMY_STRENGTH_BOOST;
            case OBJECTIVE_CRISIS -> ConsequenceType.TIME_REDUCTION;
        };
    }
    
    /**
     * Apply pressure consequences
     */
    private void applyPressureConsequences() {
        Iterator<PressureConsequence> iterator = activeConsequences.iterator();
        
        while (iterator.hasNext()) {
            PressureConsequence consequence = iterator.next();
            
            if (consequence.isActive()) {
                // Apply consequence effects
                applyConsequenceEffects(consequence);
                
                // Update duration
                consequence.setCurrentDuration(consequence.getCurrentDuration() - 1);
                
                // Remove expired consequences
                if (consequence.getCurrentDuration() <= 0) {
                    iterator.remove();
                }
            }
        }
    }
    
    /**
     * Apply consequence effects
     */
    private void applyConsequenceEffects(PressureConsequence consequence) {
        switch (consequence.getType()) {
            case ENEMY_STRENGTH_BOOST:
                // Increase enemy strength
                break;
            case ENEMY_NUMBER_INCREASE:
                // Spawn additional enemies
                break;
            case TIME_REDUCTION:
                // Reduce available time
                break;
            case ENVIRONMENTAL_DAMAGE:
                // Create environmental hazards
                break;
            case PSIONIC_DISRUPTION:
                // Reduce psionic effectiveness
                break;
            case EQUIPMENT_DEGRADATION:
                // Cause equipment problems
                break;
            case MORALE_LOSS:
                // Reduce squad morale
                break;
            case VISION_REDUCTION:
                // Limit visibility
                break;
            case MOVEMENT_RESTRICTION:
                // Restrict movement
                break;
            case COVER_DESTRUCTION:
                // Destroy cover
                break;
        }
    }
    
    /**
     * Update active modifiers
     */
    private void updateModifiers() {
        Iterator<Map.Entry<String, PressureModifier>> iterator = activeModifiers.entrySet().iterator();
        
        while (iterator.hasNext()) {
            Map.Entry<String, PressureModifier> entry = iterator.next();
            PressureModifier modifier = entry.getValue();
            
            if (modifier.isActive()) {
                // Update duration
                modifier.setCurrentDuration(modifier.getCurrentDuration() - 1);
                
                // Remove expired modifiers
                if (modifier.getCurrentDuration() <= 0) {
                    iterator.remove();
                }
            }
        }
    }
    
    /**
     * Add pressure modifier
     */
    public boolean addPressureModifier(PressureModifier modifier) {
        if (activeModifiers.containsKey(modifier.getModifierId())) {
            return false;
        }
        
        modifier.setActive(true);
        modifier.setCurrentDuration(modifier.getDuration());
        activeModifiers.put(modifier.getModifierId(), modifier);
        
        return true;
    }
    
    /**
     * Add pressure event
     */
    public boolean addPressureEvent(PressureEvent event) {
        if (pressureEvents.stream().anyMatch(e -> e.getEventId().equals(event.getEventId()))) {
            return false;
        }
        
        pressureEvents.add(event);
        return true;
    }
    
    /**
     * Get current pressure level
     */
    public int getCurrentPressure() {
        return currentPressure;
    }
    
    /**
     * Get pressure threshold
     */
    public int getPressureThreshold() {
        return pressureThreshold;
    }
    
    /**
     * Check if pressure is critical
     */
    public boolean isPressureCritical() {
        return currentPressure >= pressureThreshold;
    }
    
    /**
     * Get pressure percentage
     */
    public double getPressurePercentage() {
        return (double) currentPressure / pressureThreshold * 100.0;
    }
    
    /**
     * Get active consequences count
     */
    public int getActiveConsequencesCount() {
        return (int) activeConsequences.stream().filter(PressureConsequence::isActive).count();
    }
    
    /**
     * Get active modifiers count
     */
    public int getActiveModifiersCount() {
        return (int) activeModifiers.values().stream().filter(PressureModifier::isActive).count();
    }
}
