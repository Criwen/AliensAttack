package com.aliensattack.core.model.mindcontrol;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

import static com.aliensattack.core.model.mindcontrol.MindControlTypes.*;

public final class MindControlModels {
    private MindControlModels() { }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MindControlInstance {
        private String controlId;
        private String controllerId;
        private String targetId;
        private ControlType type;
        private int duration;
        private int currentDuration;
        private double controlStrength;
        private boolean isActive;
        private LocalDateTime startTime;
        private LocalDateTime endTime;
        private List<ControlledAction> availableActions;
        private List<ControlledAction> performedActions;
        private double breakChance;
        private int resistanceLevel;
        private String resistanceType;
        private List<String> immunities;
        private String description;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ControlledAction {
        private String actionId;
        private String actionName;
        private ControlledActionType type;
        private int actionCost;
        private int range;
        private String targetType;
        private List<String> requirements;
        private boolean isAvailable;
        private String description;
        private List<String> effects;
        private int cooldown;
        private int currentCooldown;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MindControlFeedback {
        private String feedbackId;
        private String sourceId;
        private String targetId;
        private FeedbackType type;
        private int intensity;
        private int duration;
        private int currentDuration;
        private LocalDateTime startTime;
        private List<String> effects;
        private boolean isActive;
        private String description;
        private double triggerChance;
        private String triggerCondition;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MindControlCountermeasure {
        private String countermeasureId;
        private String countermeasureName;
        private CountermeasureType type;
        private int effectiveness;
        private int duration;
        private int currentDuration;
        private String targetId;
        private boolean isActive;
        private LocalDateTime startTime;
        private List<String> effects;
        private String description;
        private int energyCost;
        private String activationCondition;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MindControlVisualEffect {
        private String effectId;
        private String effectName;
        private VisualEffectType type;
        private String targetId;
        private int duration;
        private int currentDuration;
        private boolean isActive;
        private LocalDateTime startTime;
        private List<String> visualElements;
        private String description;
        private int intensity;
        private String color;
        private String pattern;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MindControlSoundEffect {
        private String effectId;
        private String effectName;
        private SoundEffectType type;
        private String targetId;
        private int duration;
        private int currentDuration;
        private boolean isActive;
        private LocalDateTime startTime;
        private List<String> soundElements;
        private String description;
        private int volume;
        private String frequency;
        private String pattern;
    }
}


