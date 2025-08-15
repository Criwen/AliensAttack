package com.aliensattack.core.systems;

import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.Date;

/**
 * Event class for mission completion
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MissionCompletionEvent {
    private String eventId;
    private String missionId;
    private String missionName;
    private String missionType;
    private boolean isSuccess;
    private int completionTurns;
    private Date completionDate;
    private String completionReason;
    private int experienceGained;
    private int resourcesGained;
    private String[] rewards;
    private String[] penalties;
    
    // Additional fields for mission completion
    private MissionFailureSuccessConditionsSystem.MissionResult result;
    private String type;
}
