package com.aliensattack.core.patterns;

import com.aliensattack.combat.ICombatManager;
import com.aliensattack.combat.*;
import com.aliensattack.field.ITacticalField;
import com.aliensattack.field.OptimizedTacticalField;
import com.aliensattack.ui.GameWindow;
import com.aliensattack.actions.ActionManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.concurrent.atomic.AtomicReference;

/**
 * Singleton GameEngine using Facade pattern to manage all game systems
 */
public class GameEngine {
    private static final Logger log = LogManager.getLogger(GameEngine.class);
    private static final AtomicReference<GameEngine> INSTANCE = new AtomicReference<>();
    
    private OptimizedTacticalField tacticalField;
    private ICombatManager combatManager;
    private ActionManager actionManager;
    private GameWindow gameWindow;
    private GameState gameState;
    
    private GameEngine() {
        initializeSystems();
    }
    
    public static GameEngine getInstance() {
        GameEngine instance = INSTANCE.get();
        if (instance == null) {
            synchronized (GameEngine.class) {
                instance = INSTANCE.get();
                if (instance == null) {
                    instance = new GameEngine();
                    INSTANCE.set(instance);
                    log.info("GameEngine singleton created");
                }
            }
        }
        return instance;
    }
    
    private void initializeSystems() {
        log.debug("Initializing game systems");
        
        // Initialize tactical field
        tacticalField = new OptimizedTacticalField(10, 10);
        
        // Initialize combat manager using Strategy pattern
        combatManager = new OptimizedCombatManager(tacticalField);
        
        // Initialize action manager
        actionManager = new ActionManager(tacticalField, (OptimizedCombatManager) combatManager);
        
        // Initialize game state
        gameState = new GameState();
        
        log.info("Game systems initialized successfully");
    }
    
    public void startGame() {
        log.info("Starting game");
        gameWindow = new GameWindow();
        gameWindow.showWindow();
    }
    
    public void shutdown() {
        log.info("Shutting down game engine");
        if (gameWindow != null) {
            gameWindow.dispose();
        }
    }
    
    // Facade methods for simplified access
    public ITacticalField getTacticalField() {
        return tacticalField;
    }
    
    public ICombatManager getCombatManager() {
        return combatManager;
    }
    
    public ActionManager getActionManager() {
        return actionManager;
    }
    
    public GameState getGameState() {
        return gameState;
    }
}
