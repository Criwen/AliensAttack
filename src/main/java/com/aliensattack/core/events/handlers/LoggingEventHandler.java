package com.aliensattack.core.events.handlers;

import com.aliensattack.core.events.CombatEvent;
import com.aliensattack.core.events.AttackEvent;
import com.aliensattack.core.events.MoveEvent;
import com.aliensattack.core.events.EventBus;
import lombok.extern.log4j.Log4j2;

/**
 * Simple logging handler for combat events
 */
@Log4j2
public class LoggingEventHandler {
	public static void register() {
		EventBus.getInstance().subscribe(LoggingEventHandler::onEvent);
		log.info("LoggingEventHandler subscribed to EventBus");
	}
	
	private static void onEvent(CombatEvent event) {
		switch (event.getEventType()) {
			case ATTACK -> logAttack((AttackEvent) event);
			case MOVE -> logMove((MoveEvent) event);
			default -> log.info("Event: {} from {} to {}", event.getEventType(), event.getSourceUnitId(), event.getTargetUnitId());
		}
	}
	
	private static void logAttack(AttackEvent e) {
		log.info("ATTACK: {} -> {} dmg={} crit={} hit={} weapon={}", e.getSourceUnitId(), e.getTargetUnitId(), e.getDamage(), e.isCritical(), e.isHit(), e.getWeaponType());
	}
	
	private static void logMove(MoveEvent e) {
		log.info("MOVE: {} {}:{} -> {}:{}", e.getSourceUnitId(), e.getFromX(), e.getFromY(), e.getToX(), e.getToY());
	}
}
