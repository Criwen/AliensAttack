package com.aliensattack.core.patterns;

import com.aliensattack.core.model.Unit;
import lombok.Data;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.ArrayList;

/**
 * GameState class to manage current game state
 */
@Data
public class GameState {
    private static final Logger log = LogManager.getLogger(GameState.class);
    
    private int currentTurn;
    private List<Unit> units;
    private Unit selectedUnit;
    private boolean isGameActive;
    private String currentPhase;
    
    public GameState() {
        this.currentTurn = 1;
        this.units = new ArrayList<>();
        this.selectedUnit = null;
        this.isGameActive = true;
        this.currentPhase = "INITIALIZATION";
        log.debug("GameState initialized");
    }
    
    public void nextTurn() {
        currentTurn++;
        log.info("Turn {} started", currentTurn);
    }
    
    public void selectUnit(Unit unit) {
        this.selectedUnit = unit;
        if (unit != null) {
            log.debug("Unit selected: {}", unit.getName());
        }
    }
    
    public void addUnit(Unit unit) {
        units.add(unit);
        log.debug("Unit added: {}", unit.getName());
    }
    
    public void setPhase(String phase) {
        this.currentPhase = phase;
        log.debug("Game phase changed to: {}", phase);
    }
}
