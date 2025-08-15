package com.aliensattack.core.events;

import lombok.extern.log4j.Log4j2;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Consumer;

/**
 * Event bus for handling combat events
 */
@Log4j2
public class EventBus {
    
    private static EventBus instance;
    private final List<Consumer<CombatEvent>> eventHandlers = new CopyOnWriteArrayList<>();
    
    private EventBus() {}
    
    public static EventBus getInstance() {
        if (instance == null) {
            instance = new EventBus();
        }
        return instance;
    }
    
    /**
     * Subscribe to combat events
     */
    public void subscribe(Consumer<CombatEvent> handler) {
        eventHandlers.add(handler);
        log.debug("Event handler subscribed, total handlers: {}", eventHandlers.size());
    }
    
    /**
     * Unsubscribe from combat events
     */
    public void unsubscribe(Consumer<CombatEvent> handler) {
        eventHandlers.remove(handler);
        log.debug("Event handler unsubscribed, total handlers: {}", eventHandlers.size());
    }
    
    /**
     * Publish combat event to all subscribers
     */
    public void publish(CombatEvent event) {
        log.debug("Publishing event: {} from {} to {}", event.getEventType(), event.getSourceUnitId(), event.getTargetUnitId());
        
        for (Consumer<CombatEvent> handler : eventHandlers) {
            try {
                handler.accept(event);
            } catch (Exception e) {
                log.error("Error in event handler: {}", e.getMessage(), e);
            }
        }
    }
    
    /**
     * Get current subscriber count
     */
    public int getSubscriberCount() {
        return eventHandlers.size();
    }
}
