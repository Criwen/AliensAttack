package com.aliensattack.core.events;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Event representing an attack action
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class AttackEvent extends CombatEvent {
    private int damage;
    private boolean critical;
    private boolean hit;
    private String weaponType;
    
    public AttackEvent(String sourceUnitId, String targetUnitId, int damage, boolean critical, boolean hit, String weaponType) {
        super(sourceUnitId, targetUnitId, EventType.ATTACK);
        this.damage = damage;
        this.critical = critical;
        this.hit = hit;
        this.weaponType = weaponType;
    }
}
