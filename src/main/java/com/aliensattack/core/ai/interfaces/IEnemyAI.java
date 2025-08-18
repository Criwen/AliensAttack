package com.aliensattack.core.ai.interfaces;

import com.aliensattack.core.model.Alien;
import com.aliensattack.core.model.Position;
import com.aliensattack.field.ITacticalField;
import com.aliensattack.combat.interfaces.ICombatManagerExtended;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.Map;

/**
 * Interface for enemy AI behavior in tactical combat
 * Now supports asynchronous Ollama-based decision making
 */
public interface IEnemyAI {
    
    /**
     * Initialize AI for a specific alien
     */
    void initialize(Alien alien);
    
    /**
     * Initialize AI with any unit type (for compatibility)
     */
    void initializeWithUnit(com.aliensattack.core.model.Unit unit);
    
    /**
     * Make AI decision for the current turn using Ollama
     * Returns a CompletableFuture with the decision result
     */
    CompletableFuture<AITurnDecision> makeTurnDecision();
    
    /**
     * Execute AI action based on Ollama decision
     * Returns true if action was executed successfully
     */
    CompletableFuture<Boolean> executeAction();
    
    /**
     * Calculate best move position using Ollama analysis
     */
    CompletableFuture<Position> calculateBestMovePosition();
    
    /**
     * Find best target for attack using Ollama analysis
     */
    CompletableFuture<List<com.aliensattack.core.model.Unit>> findBestTargets();
    
    /**
     * Check if AI should use special ability using Ollama
     */
    CompletableFuture<Boolean> shouldUseSpecialAbility();
    
    /**
     * Get AI difficulty level
     */
    int getDifficultyLevel();
    
    /**
     * Set tactical field reference
     */
    void setTacticalField(ITacticalField field);
    
    /**
     * Set combat manager reference
     */
    void setCombatManager(ICombatManagerExtended combatManager);
    
    /**
     * Check if Ollama AI is available and enabled
     */
    boolean isOllamaEnabled();
    
    /**
     * Get the current tactical situation analysis
     */
    CompletableFuture<TacticalSituation> analyzeTacticalSituation();
    
    /**
     * Data class for AI turn decisions
     */
    class AITurnDecision {
        private final String primaryAction;
        private final String secondaryAction;
        private final Position targetPosition;
        private final com.aliensattack.core.model.Unit targetUnit;
        private final String reasoning;
        private final double confidence;
        
        public AITurnDecision(String primaryAction, String secondaryAction, 
                             Position targetPosition, com.aliensattack.core.model.Unit targetUnit,
                             String reasoning, double confidence) {
            this.primaryAction = primaryAction;
            this.secondaryAction = secondaryAction;
            this.targetPosition = targetPosition;
            this.targetUnit = targetUnit;
            this.reasoning = reasoning;
            this.confidence = confidence;
        }
        
        // Getters
        public String getPrimaryAction() { return primaryAction; }
        public String getSecondaryAction() { return secondaryAction; }
        public Position getTargetPosition() { return targetPosition; }
        public com.aliensattack.core.model.Unit getTargetUnit() { return targetUnit; }
        public String getReasoning() { return reasoning; }
        public double getConfidence() { return confidence; }
    }
    
    /**
     * Data class for tactical situation analysis
     */
    class TacticalSituation {
        private final List<Position> availableMovePositions;
        private final List<com.aliensattack.core.model.Unit> visibleEnemies;
        private final List<com.aliensattack.core.model.Unit> visibleAllies;
        private final Map<Position, Double> positionScores;
        private final Map<com.aliensattack.core.model.Unit, Double> threatLevels;
        private final String tacticalAdvice;
        
        public TacticalSituation(List<Position> availableMovePositions,
                               List<com.aliensattack.core.model.Unit> visibleEnemies,
                               List<com.aliensattack.core.model.Unit> visibleAllies,
                               Map<Position, Double> positionScores,
                               Map<com.aliensattack.core.model.Unit, Double> threatLevels,
                               String tacticalAdvice) {
            this.availableMovePositions = availableMovePositions;
            this.visibleEnemies = visibleEnemies;
            this.visibleAllies = visibleAllies;
            this.positionScores = positionScores;
            this.threatLevels = threatLevels;
            this.tacticalAdvice = tacticalAdvice;
        }
        
        // Getters
        public List<Position> getAvailableMovePositions() { return availableMovePositions; }
        public List<com.aliensattack.core.model.Unit> getVisibleEnemies() { return visibleEnemies; }
        public List<com.aliensattack.core.model.Unit> getVisibleAllies() { return visibleAllies; }
        public Map<Position, Double> getPositionScores() { return positionScores; }
        public Map<com.aliensattack.core.model.Unit, Double> getThreatLevels() { return threatLevels; }
        public String getTacticalAdvice() { return tacticalAdvice; }
    }
}
