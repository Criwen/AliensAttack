package com.aliensattack.core.ai;

import com.aliensattack.core.ai.ollama.OllamaAIService;
import com.aliensattack.core.model.Alien;
import com.aliensattack.core.model.Position;
import com.aliensattack.core.model.Unit;
import com.aliensattack.field.ITacticalField;
import com.aliensattack.combat.interfaces.ICombatManagerExtended;
import com.aliensattack.core.config.GameConfig;
import lombok.extern.log4j.Log4j2;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

/**
 * EnemyAI that completely relies on Ollama for all decision making
 * Replaces traditional AI logic with intelligent LLM-based analysis
 */
@Log4j2
public class OllamaBasedEnemyAI implements com.aliensattack.core.ai.interfaces.IEnemyAI {
    
    private final OllamaAIService ollamaService;
    private final boolean ollamaEnabled;
    
    private Alien alien;
    private ITacticalField tacticalField;
    private ICombatManagerExtended combatManager;
    private int difficultyLevel;
    
    public OllamaBasedEnemyAI() {
        this.ollamaService = new OllamaAIService();
        this.ollamaEnabled = GameConfig.getBoolean("ollama.ai.enabled", true);
        this.difficultyLevel = GameConfig.getInt("ai.enemy.difficulty.level", 5);
        
        if (ollamaEnabled) {
            log.info("Ollama-based AI initialized");
        } else {
            log.warn("Ollama-based AI initialized but Ollama is disabled");
        }
    }
    
    @Override
    public void initialize(Alien alien) {
        this.alien = alien;
        log.debug("Ollama-based AI initialized for alien: {}", alien.getAlienType());
    }
    
    @Override
    public void initializeWithUnit(Unit unit) {
        if (unit.getUnitType() == com.aliensattack.core.enums.UnitType.ALIEN) {
            log.warn("Ollama-based AI can only work with Alien units directly. Use initialize(Alien alien) instead.");
        } else {
            log.warn("Ollama-based AI can only work with Alien units");
        }
    }
    
    /**
     * Initialize AI directly with an Alien instance
     */
    public void initializeWithAlien(Alien alien) {
        this.alien = alien;
        log.debug("Ollama-based AI initialized for alien: {}", alien.getAlienType());
    }
    
    @Override
    public CompletableFuture<com.aliensattack.core.ai.interfaces.IEnemyAI.AITurnDecision> makeTurnDecision() {
        if (alien == null || !alien.isAlive()) {
            return CompletableFuture.completedFuture(createEmptyDecision());
        }
        
        log.debug("Ollama-based AI making turn decision for alien: {}", alien.getName());
        
        if (!ollamaEnabled || !ollamaService.isAvailable()) {
            log.warn("Ollama not available, using fallback decision");
            return CompletableFuture.completedFuture(createFallbackDecision());
        }
        
        return ollamaService.makeCompleteTurnDecision(alien, tacticalField, combatManager)
                .thenApply(decision -> {
                    log.debug("Ollama decision for {}: {} -> {} at {}", 
                             alien.getName(), decision.getPrimaryAction(), 
                             decision.getSecondaryAction(), decision.getTargetPosition());
                    return decision;
                })
                .exceptionally(throwable -> {
                    log.error("Ollama decision failed for {}: {}", alien.getName(), throwable.getMessage());
                    return createFallbackDecision();
                });
    }
    
    @Override
    public CompletableFuture<Boolean> executeAction() {
        if (alien == null || !alien.isAlive()) {
            return CompletableFuture.completedFuture(false);
        }
        
        log.debug("Ollama-based AI executing action for alien: {}", alien.getName());
        
        if (!ollamaEnabled || !ollamaService.isAvailable()) {
            log.warn("Ollama not available, using fallback action");
            return executeFallbackAction();
        }
        
        // Execute complete turn using Ollama
        return ollamaService.executeCompleteTurn(alien, tacticalField, combatManager)
                .thenApply(success -> {
                    if (success) {
                        log.debug("Ollama complete turn executed successfully for {}", alien.getName());
                        
                        // Check if turn is complete
                        if (ollamaService.isTurnComplete(alien)) {
                            log.info("🎯 {} turn completed - {} action points remaining", 
                                     alien.getName(), ollamaService.getRemainingActionPoints(alien));
                        }
                    } else {
                        log.warn("Ollama complete turn failed for {}", alien.getName());
                    }
                    return success;
                })
                .exceptionally(throwable -> {
                    log.error("Ollama action execution failed for {}: {}", alien.getName(), throwable.getMessage());
                    return executeFallbackAction().join();
                });
    }
    
    @Override
    public CompletableFuture<Position> calculateBestMovePosition() {
        if (alien == null || tacticalField == null) {
            return CompletableFuture.completedFuture(null);
        }
        
        log.debug("Ollama-based AI calculating best move position for alien: {}", alien.getName());
        
        if (!ollamaEnabled || !ollamaService.isAvailable()) {
            log.warn("Ollama not available, using fallback position calculation");
            return CompletableFuture.completedFuture(calculateFallbackMovePosition());
        }
        
        return ollamaService.calculateBestMovePosition(alien, tacticalField, combatManager)
                .thenApply(position -> {
                    log.debug("Ollama calculated move position for {}: {}", alien.getName(), position);
                    return position;
                })
                .exceptionally(throwable -> {
                    log.error("Ollama position calculation failed for {}: {}", alien.getName(), throwable.getMessage());
                    return calculateFallbackMovePosition();
                });
    }
    
    @Override
    public CompletableFuture<List<Unit>> findBestTargets() {
        if (alien == null || tacticalField == null) {
            return CompletableFuture.completedFuture(new ArrayList<>());
        }
        
        log.debug("Ollama-based AI finding best targets for alien: {}", alien.getName());
        
        if (!ollamaEnabled || !ollamaService.isAvailable()) {
            log.warn("Ollama not available, using fallback target selection");
            return CompletableFuture.completedFuture(findFallbackTargets());
        }
        
        return ollamaService.findBestTargets(alien, tacticalField, combatManager)
                .thenApply(targets -> {
                    log.debug("Ollama found {} targets for {}", targets.size(), alien.getName());
                    return targets;
                })
                .exceptionally(throwable -> {
                    log.error("Ollama target selection failed for {}: {}", alien.getName(), throwable.getMessage());
                    return findFallbackTargets();
                });
    }
    
    @Override
    public CompletableFuture<Boolean> shouldUseSpecialAbility() {
        if (alien == null || alien.getPsionicAbilities().isEmpty()) {
            return CompletableFuture.completedFuture(false);
        }
        
        log.debug("Ollama-based AI checking special ability for alien: {}", alien.getName());
        
        if (!ollamaEnabled || !ollamaService.isAvailable()) {
            log.warn("Ollama not available, using fallback special ability check");
            return CompletableFuture.completedFuture(shouldUseFallbackSpecialAbility());
        }
        
        return ollamaService.shouldUseSpecialAbility(alien, tacticalField, combatManager)
                .thenApply(shouldUse -> {
                    log.debug("Ollama special ability decision for {}: {}", alien.getName(), shouldUse);
                    return shouldUse;
                })
                .exceptionally(throwable -> {
                    log.error("Ollama special ability check failed for {}: {}", alien.getName(), throwable.getMessage());
                    return shouldUseFallbackSpecialAbility();
                });
    }
    
    @Override
    public int getDifficultyLevel() {
        return difficultyLevel;
    }
    
    @Override
    public void setTacticalField(ITacticalField field) {
        this.tacticalField = field;
    }
    
    @Override
    public void setCombatManager(ICombatManagerExtended combatManager) {
        this.combatManager = combatManager;
    }
    
    @Override
    public boolean isOllamaEnabled() {
        return ollamaEnabled && ollamaService.isAvailable();
    }
    
    @Override
    public CompletableFuture<com.aliensattack.core.ai.interfaces.IEnemyAI.TacticalSituation> analyzeTacticalSituation() {
        if (alien == null || tacticalField == null) {
            return CompletableFuture.completedFuture(createEmptyTacticalSituation());
        }
        
        log.debug("Ollama-based AI analyzing tactical situation for alien: {}", alien.getName());
        
        if (!ollamaEnabled || !ollamaService.isAvailable()) {
            log.warn("Ollama not available, using fallback tactical analysis");
            return CompletableFuture.completedFuture(createFallbackTacticalSituation());
        }
        
        return ollamaService.analyzeTacticalSituation(alien, tacticalField, combatManager)
                .thenApply(situation -> {
                    log.debug("Ollama tactical analysis completed for {}", alien.getName());
                    return situation;
                })
                .exceptionally(throwable -> {
                    log.error("Ollama tactical analysis failed for {}: {}", alien.getName(), throwable.getMessage());
                    return createFallbackTacticalSituation();
                });
    }
    
    // Fallback methods when Ollama is not available
    
    private com.aliensattack.core.ai.interfaces.IEnemyAI.AITurnDecision createEmptyDecision() {
        return new com.aliensattack.core.ai.interfaces.IEnemyAI.AITurnDecision(
            "none", "none", null, null, "No alien available", 0.0
        );
    }
    
    private com.aliensattack.core.ai.interfaces.IEnemyAI.AITurnDecision createFallbackDecision() {
        if (alien == null) return createEmptyDecision();
        
        // Simple fallback logic
        String primaryAction = "defend";
        Position targetPosition = alien.getPosition();
        Unit targetUnit = null;
        String reasoning = "Fallback decision - Ollama unavailable";
        double confidence = 0.3;
        
        // Check if we can attack
        List<Unit> visibleEnemies = getVisibleEnemies();
        if (!visibleEnemies.isEmpty() && alien.getActionPoints() >= 1) {
            Unit nearestEnemy = findNearestEnemy(visibleEnemies);
            if (isInAttackRange(nearestEnemy)) {
                primaryAction = "attack_target";
                targetUnit = nearestEnemy;
                reasoning = "Fallback: Attack nearest visible enemy";
                confidence = 0.5;
            } else if (alien.getActionPoints() >= 1) {
                primaryAction = "move_to_position";
                targetPosition = findNearestValidPosition(nearestEnemy.getPosition());
                reasoning = "Fallback: Move towards nearest enemy";
                confidence = 0.4;
            }
        }
        
        return new com.aliensattack.core.ai.interfaces.IEnemyAI.AITurnDecision(
            primaryAction, "none", targetPosition, targetUnit, reasoning, confidence
        );
    }
    
    private CompletableFuture<Boolean> executeFallbackAction() {
        if (alien == null || !alien.isAlive()) {
            return CompletableFuture.completedFuture(false);
        }
        
        // Simple fallback action execution
        try {
            if (alien.getActionPoints() >= 1) {
                // Just spend action points and do nothing
                alien.spendActionPoints(1);
                log.debug("Fallback action executed for {}: spent 1 AP", alien.getName());
                return CompletableFuture.completedFuture(true);
            }
        } catch (Exception e) {
            log.error("Fallback action failed for {}: {}", alien.getName(), e.getMessage());
        }
        
        return CompletableFuture.completedFuture(false);
    }
    
    private Position calculateFallbackMovePosition() {
        if (alien == null || tacticalField == null) return null;
        
        Position currentPos = alien.getPosition();
        if (currentPos == null) return null;
        
        // Find any valid move position
        List<Position> validPositions = getValidMovePositions();
        if (validPositions.isEmpty()) {
            return currentPos; // Stay in place
        }
        
        // Try to move towards enemies if any are visible
        List<Unit> visibleEnemies = getVisibleEnemies();
        if (!visibleEnemies.isEmpty()) {
            Unit nearestEnemy = findNearestEnemy(visibleEnemies);
            Position nearestValid = findNearestValidPosition(nearestEnemy.getPosition());
            if (nearestValid != null) {
                return nearestValid;
            }
        }
        
        // Return random valid position
        return validPositions.get(new Random().nextInt(validPositions.size()));
    }
    
    private List<Unit> findFallbackTargets() {
        if (alien == null || tacticalField == null) {
            return new ArrayList<>();
        }
        
        List<Unit> targets = getVisibleEnemies();
        if (targets.isEmpty()) {
            return new ArrayList<>();
        }
        
        // Sort by distance and health
        targets.sort((t1, t2) -> {
            double dist1 = calculateDistance(alien.getPosition(), t1.getPosition());
            double dist2 = calculateDistance(alien.getPosition(), t2.getPosition());
            
            if (Math.abs(dist1 - dist2) < 0.1) {
                // If distances are similar, prefer weaker targets
                return Integer.compare(t1.getCurrentHealth(), t2.getCurrentHealth());
            }
            
            return Double.compare(dist1, dist2);
        });
        
        return targets;
    }
    
    private boolean shouldUseFallbackSpecialAbility() {
        if (alien == null || alien.getPsionicAbilities().isEmpty()) {
            return false;
        }
        
        // Check if we have enough action points and energy
        if (alien.getActionPoints() < 2) {
            return false;
        }
        
        // Check if there are good targets
        List<Unit> targets = getVisibleEnemies();
        if (targets.isEmpty()) {
            return false;
        }
        
        // Random chance based on difficulty
        double chance = 0.1 + (difficultyLevel * 0.05);
        return new Random().nextDouble() < chance;
    }
    
    private com.aliensattack.core.ai.interfaces.IEnemyAI.TacticalSituation createEmptyTacticalSituation() {
        return new com.aliensattack.core.ai.interfaces.IEnemyAI.TacticalSituation(
            new ArrayList<>(), new ArrayList<>(), new ArrayList<>(),
            new HashMap<>(), new HashMap<>(), "No tactical data available"
        );
    }
    
    private com.aliensattack.core.ai.interfaces.IEnemyAI.TacticalSituation createFallbackTacticalSituation() {
        if (alien == null || tacticalField == null) {
            return createEmptyTacticalSituation();
        }
        
        List<Position> availablePositions = getValidMovePositions();
        List<Unit> visibleEnemies = getVisibleEnemies();
        List<Unit> visibleAllies = getVisibleAllies();
        
        Map<Position, Double> positionScores = new HashMap<>();
        for (Position pos : availablePositions) {
            positionScores.put(pos, calculateFallbackPositionScore(pos));
        }
        
        Map<Unit, Double> threatLevels = new HashMap<>();
        for (Unit enemy : visibleEnemies) {
            threatLevels.put(enemy, calculateFallbackThreatLevel(enemy));
        }
        
        return new com.aliensattack.core.ai.interfaces.IEnemyAI.TacticalSituation(
            availablePositions, visibleEnemies, visibleAllies,
            positionScores, threatLevels, "Fallback tactical analysis"
        );
    }
    
    // Helper methods for fallback logic
    
    private List<Unit> getVisibleEnemies() {
        if (tacticalField == null) return new ArrayList<>();
        
        return tacticalField.getAllUnits().stream()
                .filter(unit -> unit.getUnitType() == com.aliensattack.core.enums.UnitType.SOLDIER)
                .filter(Unit::isAlive)
                .filter(unit -> isInRange(alien.getPosition(), unit.getPosition(), alien.getAttackRange()))
                .collect(Collectors.toList());
    }
    
    private List<Unit> getVisibleAllies() {
        if (tacticalField == null) return new ArrayList<>();
        
        return tacticalField.getAllUnits().stream()
                .filter(unit -> unit.getUnitType() == com.aliensattack.core.enums.UnitType.ALIEN)
                .filter(Unit::isAlive)
                .filter(unit -> !unit.equals(alien))
                .filter(unit -> isInRange(alien.getPosition(), unit.getPosition(), alien.getAttackRange()))
                .collect(Collectors.toList());
    }
    
    private List<Position> getValidMovePositions() {
        if (alien == null || tacticalField == null) return new ArrayList<>();
        
        List<Position> positions = new ArrayList<>();
        Position currentPos = alien.getPosition();
        int maxRange = alien.getMovementRange();
        
        for (int x = Math.max(0, currentPos.getX() - maxRange); 
             x <= Math.min(tacticalField.getWidth() - 1, currentPos.getX() + maxRange); x++) {
            for (int y = Math.max(0, currentPos.getY() - maxRange); 
                 y <= Math.min(tacticalField.getHeight() - 1, currentPos.getY() + maxRange); y++) {
                
                Position pos = new Position(x, y);
                int distance = Math.abs(x - currentPos.getX()) + Math.abs(y - currentPos.getY());
                
                if (distance <= maxRange && distance > 0 && isValidPosition(pos)) {
                    positions.add(pos);
                }
            }
        }
        
        return positions;
    }
    
    private boolean isValidPosition(Position position) {
        if (tacticalField == null) return false;
        
        if (position.getX() < 0 || position.getX() >= tacticalField.getWidth() ||
            position.getY() < 0 || position.getY() >= tacticalField.getHeight()) {
            return false;
        }
        
        return tacticalField.getUnitAt(position) == null;
    }
    
    private boolean isInRange(Position pos1, Position pos2, int range) {
        return calculateDistance(pos1, pos2) <= range;
    }
    
    private double calculateDistance(Position pos1, Position pos2) {
        int dx = pos1.getX() - pos2.getX();
        int dy = pos1.getY() - pos2.getY();
        return Math.sqrt(dx * dx + dy * dy);
    }
    
    private boolean isInAttackRange(Unit target) {
        if (alien == null || target == null) return false;
        
        Position alienPos = alien.getPosition();
        Position targetPos = target.getPosition();
        
        if (alienPos == null || targetPos == null) return false;
        
        double distance = calculateDistance(alienPos, targetPos);
        return distance <= alien.getAttackRange();
    }
    
    private Unit findNearestEnemy(List<Unit> enemies) {
        if (enemies.isEmpty()) return null;
        
        return enemies.stream()
                .min(Comparator.comparingDouble(enemy -> 
                    calculateDistance(alien.getPosition(), enemy.getPosition())))
                .orElse(null);
    }
    
    private Position findNearestValidPosition(Position targetPos) {
        List<Position> validPositions = getValidMovePositions();
        if (validPositions.isEmpty()) return null;
        
        return validPositions.stream()
                .min(Comparator.comparingDouble(pos -> 
                    calculateDistance(pos, targetPos)))
                .orElse(null);
    }
    
    private double calculateFallbackPositionScore(Position position) {
        double score = 0.0;
        
        // Distance to enemies (closer is better for aggressive aliens)
        List<Unit> enemies = getVisibleEnemies();
        for (Unit enemy : enemies) {
            double distance = calculateDistance(position, enemy.getPosition());
            score += Math.max(0, 10 - distance);
        }
        
        // Cover bonus
        if (tacticalField != null && tacticalField.getCoverObject(position) != null) {
            score += 5.0;
        }
        
        // Height advantage
        if (position.getHeight() > 0) {
            score += position.getHeight() * 2.0;
        }
        
        return score;
    }
    
    private double calculateFallbackThreatLevel(Unit enemy) {
        double threat = 0.0;
        
        // Health-based threat (lower health = higher threat)
        double healthPercentage = (double) enemy.getCurrentHealth() / enemy.getMaxHealth();
        threat += (1.0 - healthPercentage) * 5.0;
        
        // Distance-based threat
        double distance = calculateDistance(alien.getPosition(), enemy.getPosition());
        threat += Math.max(0, 10 - distance);
        
        // Weapon-based threat
        if (enemy.getWeapon() != null) {
            // Use a default damage value if getDamage() is not available
            threat += 5.0; // Default weapon threat
        }
        
        return threat;
    }
    
    /**
     * Cleanup resources
     */
    public void cleanup() {
        if (ollamaService != null) {
            ollamaService.cleanup();
        }
    }
}
