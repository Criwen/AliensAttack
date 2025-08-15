package com.aliensattack.core.events;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Base class for all combat events
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public abstract class CombatEvent {
    private String eventId;
    private LocalDateTime timestamp;
    private String sourceUnitId;
    private String targetUnitId;
    private EventType eventType;
    
    public CombatEvent(String sourceUnitId, String targetUnitId, EventType eventType) {
        this.eventId = "EVENT_" + System.currentTimeMillis();
        this.timestamp = LocalDateTime.now();
        this.sourceUnitId = sourceUnitId;
        this.targetUnitId = targetUnitId;
        this.eventType = eventType;
    }
    
    public enum EventType {
        ATTACK,
        MOVE,
        DAMAGE,
        HEAL,
        STATUS_EFFECT,
        PSIONIC_ABILITY,
        OVERWATCH,
        SUPPRESSION
    }
}
