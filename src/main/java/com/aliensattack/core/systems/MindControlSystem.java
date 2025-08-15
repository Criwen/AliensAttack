package com.aliensattack.core.systems;

import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.util.*;
import java.time.LocalDateTime;
// Mind control enums and types
enum ControlType { FULL_CONTROL, PARTIAL_CONTROL, INFLUENCE }
enum ControlledActionType { MOVE, ATTACK, DEFEND, USE_ABILITY }
enum FeedbackType { PSIONIC_FEEDBACK, MENTAL_RESISTANCE }
enum SoundEffectType { PSIONIC_RESONANCE }
enum VisualEffectType { MIND_CONTROL_LINK }

// Mind control data classes
@Data @Builder @NoArgsConstructor @AllArgsConstructor
class ControlledAction {
    private String actionId, actionName, targetType, description;
    private ControlledActionType type;
    private int actionCost, range, cooldown, currentCooldown;
    private List<String> requirements, effects;
    private boolean isAvailable;
}

@Data @Builder @NoArgsConstructor @AllArgsConstructor
class MindControlInstance {
    private String controlId, controllerId, targetId, description;
    private ControlType type;
    private int duration, currentDuration;
    private double controlStrength, breakChance;
    private int resistanceLevel;
    private String resistanceType;
    private List<String> immunities;
    private List<ControlledAction> availableActions, performedActions;
    private boolean isActive;
    private LocalDateTime startTime, endTime;
}

@Data @Builder @NoArgsConstructor @AllArgsConstructor
class MindControlFeedback {
    private String feedbackId, sourceId, targetId, description, triggerCondition;
    private FeedbackType type;
    private int intensity, duration, currentDuration;
    private double triggerChance;
    private List<String> effects;
    private boolean isActive;
    private LocalDateTime startTime;
}

@Data @Builder @NoArgsConstructor @AllArgsConstructor
class MindControlCountermeasure {
    private String countermeasureId, targetId, description;
    private int duration, currentDuration;
    private boolean isActive;
    private LocalDateTime startTime;
}

@Data @Builder @NoArgsConstructor @AllArgsConstructor
class MindControlVisualEffect {
    private String effectId, effectName, targetId, description, color, pattern;
    private VisualEffectType type;
    private int duration, currentDuration, intensity;
    private List<String> visualElements;
    private boolean isActive;
    private LocalDateTime startTime;
}

@Data @Builder @NoArgsConstructor @AllArgsConstructor
class MindControlSoundEffect {
    private String effectId, effectName, targetId, description, frequency, pattern;
    private SoundEffectType type;
    private int duration, currentDuration, volume;
    private List<String> soundElements;
    private boolean isActive;
    private LocalDateTime startTime;
}

/**
 * Extended Mind Control System - XCOM 2 Tactical Combat
 * Implements comprehensive mind control mechanics with controlled unit actions,
 * psionic feedback, countermeasures, and visual/sound effects
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MindControlSystem {
    
    private String systemId;
    private Map<String, MindControlInstance> activeControls;
    private List<MindControlFeedback> feedbackEvents;
    private Map<String, MindControlCountermeasure> activeCountermeasures;
    private List<MindControlVisualEffect> visualEffects;
    private List<MindControlSoundEffect> soundEffects;
    private Map<String, Integer> controlStatistics;
    private boolean isSystemActive;
    
    // Nested types moved to package com.aliensattack.core.model.mindcontrol
    
    /**
     * Initialize mind control system
     */
    public boolean initializeSystem(String systemId) {
        this.systemId = systemId;
        this.isSystemActive = true;
        
        this.activeControls = new HashMap<>();
        this.feedbackEvents = new ArrayList<>();
        this.activeCountermeasures = new HashMap<>();
        this.visualEffects = new ArrayList<>();
        this.soundEffects = new ArrayList<>();
        this.controlStatistics = new HashMap<>();
        
        // Initialize statistics
        initializeStatistics();
        
        return true;
    }
    
    /**
     * Initialize control statistics
     */
    private void initializeStatistics() {
        controlStatistics.put("total_controls", 0);
        controlStatistics.put("successful_controls", 0);
        controlStatistics.put("failed_controls", 0);
        controlStatistics.put("broken_controls", 0);
        controlStatistics.put("feedback_events", 0);
        controlStatistics.put("countermeasures_used", 0);
    }
    
    /**
     * Attempt mind control
     */
    public boolean attemptMindControl(String controllerId, String targetId, ControlType type, int duration) {
        // Check if target is already controlled
        if (isTargetControlled(targetId)) {
            return false;
        }
        
        // Check if target has countermeasures
        if (hasActiveCountermeasures(targetId)) {
            return false;
        }
        
        // Create mind control instance
        MindControlInstance control = MindControlInstance.builder()
            .controlId("MC_" + System.currentTimeMillis())
            .controllerId(controllerId)
            .targetId(targetId)
            .type(type)
            .duration(duration)
            .currentDuration(duration)
            .controlStrength(calculateControlStrength(controllerId, targetId))
            .isActive(true)
            .startTime(LocalDateTime.now())
            .availableActions(generateAvailableActions(type))
            .performedActions(new ArrayList<>())
            .breakChance(calculateBreakChance(targetId))
            .resistanceLevel(getTargetResistance(targetId))
            .resistanceType(getTargetResistanceType(targetId))
            .immunities(getTargetImmunities(targetId))
            .description("Mind control: " + type.name())
            .build();
        
        activeControls.put(control.getControlId(), control);
        
        // Update statistics
        controlStatistics.put("total_controls", controlStatistics.get("total_controls") + 1);
        controlStatistics.put("successful_controls", controlStatistics.get("successful_controls") + 1);
        
        // Create visual and sound effects
        createControlEffects(control);
        
        return true;
    }
    
    /**
     * Check if target is already controlled
     */
    private boolean isTargetControlled(String targetId) {
        return activeControls.values().stream()
            .anyMatch(control -> control.getTargetId().equals(targetId) && control.isActive());
    }
    
    /**
     * Check if target has active countermeasures
     */
    private boolean hasActiveCountermeasures(String targetId) {
        return activeCountermeasures.values().stream()
            .anyMatch(countermeasure -> countermeasure.getTargetId().equals(targetId) && countermeasure.isActive());
    }
    
    /**
     * Calculate control strength
     */
    private double calculateControlStrength(String controllerId, String targetId) {
        // Base control strength calculation
        double baseStrength = 50.0;
        
        // Add controller bonuses
        // Add target penalties
        
        return Math.min(100.0, Math.max(0.0, baseStrength));
    }
    
    /**
     * Calculate break chance
     */
    private double calculateBreakChance(String targetId) {
        // Base break chance calculation
        double baseChance = 0.1;
        
        // Add resistance bonuses
        // Add immunity effects
        
        return Math.min(1.0, Math.max(0.0, baseChance));
    }
    
    /**
     * Get target resistance level
     */
    private int getTargetResistance(String targetId) {
        // Get target's psionic resistance
        return 25; // Default value
    }
    
    /**
     * Get target resistance type
     */
    private String getTargetResistanceType(String targetId) {
        // Get target's resistance type
        return "PSIONIC"; // Default value
    }
    
    /**
     * Get target immunities
     */
    private List<String> getTargetImmunities(String targetId) {
        // Get target's immunities
        return new ArrayList<>();
    }
    
    /**
     * Generate available actions for control type
     */
    private List<ControlledAction> generateAvailableActions(ControlType type) {
        List<ControlledAction> actions = new ArrayList<>();
        
        switch (type) {
            case FULL_CONTROL:
                actions.add(createAction("MOVE", "Move", ControlledActionType.MOVE, 1, 5, "Position", "Move to position"));
                actions.add(createAction("ATTACK", "Attack", ControlledActionType.ATTACK, 2, 8, "Enemy", "Attack enemy"));
                actions.add(createAction("DEFEND", "Defend", ControlledActionType.DEFEND, 1, 0, "Self", "Defend position"));
                actions.add(createAction("USE_ABILITY", "Use Ability", ControlledActionType.USE_ABILITY, 3, 6, "Target", "Use special ability"));
                break;
            case PARTIAL_CONTROL:
                actions.add(createAction("MOVE", "Move", ControlledActionType.MOVE, 2, 3, "Position", "Move to position"));
                actions.add(createAction("DEFEND", "Defend", ControlledActionType.DEFEND, 1, 0, "Self", "Defend position"));
                break;
            case INFLUENCE:
                actions.add(createAction("MOVE", "Influenced Move", ControlledActionType.MOVE, 3, 2, "Position", "Influenced movement"));
                break;
            default:
                // Default actions
                break;
        }
        
        return actions;
    }
    
    /**
     * Create controlled action
     */
    private ControlledAction createAction(String actionId, String actionName, ControlledActionType type, 
                                        int actionCost, int range, String targetType, String description) {
        return ControlledAction.builder()
            .actionId(actionId)
            .actionName(actionName)
            .type(type)
            .actionCost(actionCost)
            .range(range)
            .targetType(targetType)
            .requirements(new ArrayList<>())
            .isAvailable(true)
            .description(description)
            .effects(new ArrayList<>())
            .cooldown(0)
            .currentCooldown(0)
            .build();
    }
    
    /**
     * Create control effects
     */
    private void createControlEffects(MindControlInstance control) {
        // Create visual effect
        MindControlVisualEffect visualEffect = MindControlVisualEffect.builder()
            .effectId("VE_" + control.getControlId())
            .effectName("Mind Control Aura")
            .type(VisualEffectType.MIND_CONTROL_LINK)
            .targetId(control.getTargetId())
            .duration(control.getDuration())
            .currentDuration(control.getDuration())
            .isActive(true)
            .startTime(LocalDateTime.now())
            .visualElements(Arrays.asList("Psionic aura", "Control link", "Mental domination"))
            .description("Visual effect for mind control")
            .intensity(50)
            .color("Purple")
            .pattern("Pulsing")
            .build();
        
        visualEffects.add(visualEffect);
        
        // Create sound effect
        MindControlSoundEffect soundEffect = MindControlSoundEffect.builder()
            .effectId("SE_" + control.getControlId())
            .effectName("Mind Control Resonance")
            .type(SoundEffectType.PSIONIC_RESONANCE)
            .targetId(control.getTargetId())
            .duration(control.getDuration())
            .currentDuration(control.getDuration())
            .isActive(true)
            .startTime(LocalDateTime.now())
            .soundElements(Arrays.asList("Psionic hum", "Mental resonance", "Control echo"))
            .description("Sound effect for mind control")
            .volume(60)
            .frequency("Medium")
            .pattern("Continuous")
            .build();
        
        soundEffects.add(soundEffect);
    }
    
    /**
     * Execute controlled action
     */
    public boolean executeControlledAction(String controlId, String actionId, String targetId) {
        MindControlInstance control = activeControls.get(controlId);
        if (control == null || !control.isActive()) {
            return false;
        }
        
        ControlledAction action = findAvailableAction(control, actionId);
        if (action == null || !action.isAvailable()) {
            return false;
        }
        
        // Execute action
        boolean success = performAction(action, targetId);
        
        if (success) {
            // Add to performed actions
            control.getPerformedActions().add(action);
            
            // Check for feedback
            checkForFeedback(control);
            
            // Update action cooldown
            action.setCurrentCooldown(action.getCooldown());
        }
        
        return success;
    }
    
    /**
     * Find available action
     */
    private ControlledAction findAvailableAction(MindControlInstance control, String actionId) {
        return control.getAvailableActions().stream()
            .filter(action -> action.getActionId().equals(actionId) && action.isAvailable())
            .findFirst()
            .orElse(null);
    }
    
    /**
     * Perform controlled action
     */
    private boolean performAction(ControlledAction action, String targetId) {
        // Action execution logic would be implemented here
        // This would integrate with the combat system
        
        switch (action.getType()) {
            case MOVE:
                // Execute move action
                return true;
            case ATTACK:
                // Execute attack action
                return true;
            case DEFEND:
                // Execute defend action
                return true;
            case USE_ABILITY:
                // Execute ability action
                return true;
            default:
                return false;
        }
    }
    
    /**
     * Check for feedback events
     */
    private void checkForFeedback(MindControlInstance control) {
        // Check if feedback should trigger
        if (Math.random() < control.getBreakChance()) {
            createFeedbackEvent(control, FeedbackType.PSIONIC_FEEDBACK);
        }
        
        // Check for resistance-based feedback
        if (control.getResistanceLevel() > 50) {
            createFeedbackEvent(control, FeedbackType.MENTAL_RESISTANCE);
        }
    }
    
    /**
     * Create feedback event
     */
    private void createFeedbackEvent(MindControlInstance control, FeedbackType type) {
        MindControlFeedback feedback = MindControlFeedback.builder()
            .feedbackId("FB_" + System.currentTimeMillis())
            .sourceId(control.getTargetId())
            .targetId(control.getControllerId())
            .type(type)
            .intensity(calculateFeedbackIntensity(control))
            .duration(3)
            .currentDuration(3)
            .startTime(LocalDateTime.now())
            .effects(Arrays.asList("Mental damage", "Psionic interference"))
            .isActive(true)
            .description("Feedback event: " + type.name())
            .triggerChance(0.3)
            .triggerCondition("Mind control resistance")
            .build();
        
        feedbackEvents.add(feedback);
        
        // Update statistics
        controlStatistics.put("feedback_events", controlStatistics.get("feedback_events") + 1);
    }
    
    /**
     * Calculate feedback intensity
     */
    private int calculateFeedbackIntensity(MindControlInstance control) {
        // Base intensity calculation
        int baseIntensity = 20;
        
        // Add resistance bonuses
        baseIntensity += control.getResistanceLevel() / 10;
        
        return Math.min(100, Math.max(10, baseIntensity));
    }
    
    /**
     * Add countermeasure
     */
    public boolean addCountermeasure(MindControlCountermeasure countermeasure) {
        if (activeCountermeasures.containsKey(countermeasure.getCountermeasureId())) {
            return false;
        }
        
        countermeasure.setActive(true);
        countermeasure.setCurrentDuration(countermeasure.getDuration());
        countermeasure.setStartTime(LocalDateTime.now());
        activeCountermeasures.put(countermeasure.getCountermeasureId(), countermeasure);
        
        // Update statistics
        controlStatistics.put("countermeasures_used", controlStatistics.get("countermeasures_used") + 1);
        
        return true;
    }
    
    /**
     * Break mind control
     */
    public boolean breakMindControl(String controlId) {
        MindControlInstance control = activeControls.get(controlId);
        if (control == null || !control.isActive()) {
            return false;
        }
        
        control.setActive(false);
        control.setEndTime(LocalDateTime.now());
        
        // Remove visual and sound effects
        removeControlEffects(control);
        
        // Update statistics
        controlStatistics.put("broken_controls", controlStatistics.get("broken_controls") + 1);
        
        return true;
    }
    
    /**
     * Remove control effects
     */
    private void removeControlEffects(MindControlInstance control) {
        // Remove visual effects
        visualEffects.removeIf(effect -> effect.getEffectId().equals("VE_" + control.getControlId()));
        
        // Remove sound effects
        soundEffects.removeIf(effect -> effect.getEffectId().equals("SE_" + control.getControlId()));
    }
    
    /**
     * Update system
     */
    public boolean updateSystem() {
        // Update active controls
        updateActiveControls();
        
        // Update feedback events
        updateFeedbackEvents();
        
        // Update countermeasures
        updateCountermeasures();
        
        // Update visual effects
        updateVisualEffects();
        
        // Update sound effects
        updateSoundEffects();
        
        return true;
    }
    
    /**
     * Update active controls
     */
    private void updateActiveControls() {
        Iterator<Map.Entry<String, MindControlInstance>> iterator = activeControls.entrySet().iterator();
        
        while (iterator.hasNext()) {
            Map.Entry<String, MindControlInstance> entry = iterator.next();
            MindControlInstance control = entry.getValue();
            
            if (control.isActive()) {
                // Update duration
                control.setCurrentDuration(control.getCurrentDuration() - 1);
                
                // Check if control should end
                if (control.getCurrentDuration() <= 0) {
                    control.setActive(false);
                    control.setEndTime(LocalDateTime.now());
                    removeControlEffects(control);
                }
            }
        }
    }
    
    /**
     * Update feedback events
     */
    private void updateFeedbackEvents() {
        Iterator<MindControlFeedback> iterator = feedbackEvents.iterator();
        
        while (iterator.hasNext()) {
            MindControlFeedback feedback = iterator.next();
            
            if (feedback.isActive()) {
                // Update duration
                feedback.setCurrentDuration(feedback.getCurrentDuration() - 1);
                
                // Remove expired feedback
                if (feedback.getCurrentDuration() <= 0) {
                    iterator.remove();
                }
            }
        }
    }
    
    /**
     * Update countermeasures
     */
    private void updateCountermeasures() {
        Iterator<Map.Entry<String, MindControlCountermeasure>> iterator = activeCountermeasures.entrySet().iterator();
        
        while (iterator.hasNext()) {
            Map.Entry<String, MindControlCountermeasure> entry = iterator.next();
            MindControlCountermeasure countermeasure = entry.getValue();
            
            if (countermeasure.isActive()) {
                // Update duration
                countermeasure.setCurrentDuration(countermeasure.getCurrentDuration() - 1);
                
                // Remove expired countermeasures
                if (countermeasure.getCurrentDuration() <= 0) {
                    iterator.remove();
                }
            }
        }
    }
    
    /**
     * Update visual effects
     */
    private void updateVisualEffects() {
        Iterator<MindControlVisualEffect> iterator = visualEffects.iterator();
        
        while (iterator.hasNext()) {
            MindControlVisualEffect effect = iterator.next();
            
            if (effect.isActive()) {
                // Update duration
                effect.setCurrentDuration(effect.getCurrentDuration() - 1);
                
                // Remove expired effects
                if (effect.getCurrentDuration() <= 0) {
                    iterator.remove();
                }
            }
        }
    }
    
    /**
     * Update sound effects
     */
    private void updateSoundEffects() {
        Iterator<MindControlSoundEffect> iterator = soundEffects.iterator();
        
        while (iterator.hasNext()) {
            MindControlSoundEffect effect = iterator.next();
            
            if (effect.isActive()) {
                // Update duration
                effect.setCurrentDuration(effect.getCurrentDuration() - 1);
                
                // Remove expired effects
                if (effect.getCurrentDuration() <= 0) {
                    iterator.remove();
                }
            }
        }
    }
    
    /**
     * Get active controls count
     */
    public int getActiveControlsCount() {
        return (int) activeControls.values().stream().filter(MindControlInstance::isActive).count();
    }
    
    /**
     * Get active feedback count
     */
    public int getActiveFeedbackCount() {
        return (int) feedbackEvents.stream().filter(MindControlFeedback::isActive).count();
    }
    
    /**
     * Get active countermeasures count
     */
    public int getActiveCountermeasuresCount() {
        return (int) activeCountermeasures.values().stream().filter(MindControlCountermeasure::isActive).count();
    }
    
    /**
     * Get active visual effects count
     */
    public int getActiveVisualEffectsCount() {
        return (int) visualEffects.stream().filter(MindControlVisualEffect::isActive).count();
    }
    
    /**
     * Get active sound effects count
     */
    public int getActiveSoundEffectsCount() {
        return (int) soundEffects.stream().filter(MindControlSoundEffect::isActive).count();
    }
}
