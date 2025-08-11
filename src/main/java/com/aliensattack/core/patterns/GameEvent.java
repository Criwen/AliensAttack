package com.aliensattack.core.patterns;

import com.aliensattack.core.model.Unit;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * Observer pattern for game events
 */
public class GameEvent {
    protected static final Logger log = LogManager.getLogger(GameEvent.class);
    
    public enum EventType {
        UNIT_SELECTED, UNIT_MOVED, UNIT_ATTACKED, GRENADE_THROWN, 
        TURN_STARTED, TURN_ENDED, GAME_STARTED, GAME_ENDED
    }
    
    private EventType type;
    private Unit source;
    private Object data;
    private long timestamp;
    
    public GameEvent(EventType type, Unit source, Object data) {
        this.type = type;
        this.source = source;
        this.data = data;
        this.timestamp = System.currentTimeMillis();
    }
    
    public EventType getType() { return type; }
    public Unit getSource() { return source; }
    public Object getData() { return data; }
    public long getTimestamp() { return timestamp; }
}

/**
 * Event listener interface
 */
interface GameEventListener {
    void onGameEvent(GameEvent event);
}

/**
 * Event manager using Observer pattern
 */
class GameEventManager {
    private static final Logger log = LogManager.getLogger(GameEventManager.class);
    private List<GameEventListener> listeners = new ArrayList<>();
    
    public void addListener(GameEventListener listener) {
        listeners.add(listener);
        log.debug("Added event listener: {}", listener.getClass().getSimpleName());
    }
    
    public void removeListener(GameEventListener listener) {
        listeners.remove(listener);
        log.debug("Removed event listener: {}", listener.getClass().getSimpleName());
    }
    
    public void fireEvent(GameEvent event) {
        log.debug("Firing event: {} from {}", event.getType(), 
                 event.getSource() != null ? event.getSource().getName() : "null");
        
        for (GameEventListener listener : listeners) {
            try {
                listener.onGameEvent(event);
            } catch (Exception e) {
                log.error("Error in event listener: {}", e.getMessage(), e);
            }
        }
    }
    
    public void fireEvent(GameEvent.EventType type, Unit source, Object data) {
        fireEvent(new GameEvent(type, source, data));
    }
}
