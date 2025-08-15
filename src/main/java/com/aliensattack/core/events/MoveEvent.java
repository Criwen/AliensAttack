package com.aliensattack.core.events;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Event representing a unit movement
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class MoveEvent extends CombatEvent {
	private int fromX;
	private int fromY;
	private int toX;
	private int toY;
	
	public MoveEvent(String sourceUnitId, int fromX, int fromY, int toX, int toY) {
		super(sourceUnitId, null, EventType.MOVE);
		this.fromX = fromX;
		this.fromY = fromY;
		this.toX = toX;
		this.toY = toY;
	}
}
