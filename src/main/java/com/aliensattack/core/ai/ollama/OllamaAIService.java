package com.aliensattack.core.ai.ollama;

import com.aliensattack.core.ai.interfaces.IEnemyAI;
import com.aliensattack.core.config.GameConfig;
import com.aliensattack.core.model.Alien;
import com.aliensattack.core.model.Position;
import com.aliensattack.core.model.Unit;
import com.aliensattack.field.ITacticalField;
import com.aliensattack.combat.interfaces.ICombatManagerExtended;
import com.aliensattack.combat.CombatResult;
import lombok.extern.log4j.Log4j2;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * AI service that makes ALL decisions for Alien units using Ollama
 * Completely replaces traditional AI decision making with intelligent LLM-based analysis
 */
@Log4j2
public class OllamaAIService {
    
    private final OllamaApiClient ollamaClient;
    private final Map<String, String> decisionCache;
    private final Map<String, List<String>> learnedPatterns;
    private final Random random;
    
    private final boolean enabled;
    private final String defaultModel;
    private final int maxCacheSize;
    private final int cacheTtlMinutes;
    
    public OllamaAIService() {
        this.ollamaClient = new OllamaApiClient();
        this.decisionCache = new ConcurrentHashMap<>();
        this.learnedPatterns = new ConcurrentHashMap<>();
        this.random = new Random();
        
        this.enabled = GameConfig.getBoolean("ollama.ai.enabled", true);
        this.defaultModel = GameConfig.getString("ollama.default.model", "llama2");
        this.maxCacheSize = GameConfig.getInt("ollama.cache.size", 1000);
        this.cacheTtlMinutes = GameConfig.getInt("ollama.cache.ttl.minutes", 60);
        
        if (enabled) {
            initializeOllama();
        }
    }
    
    /**
     * Initialize Ollama connection and verify availability
     */
    private void initializeOllama() {
        try {
            if (ollamaClient.isAvailable()) {
                log.info("Ollama AI service initialized successfully");
                ollamaClient.getAvailableModels().thenAccept(models -> {
                    log.info("Available Ollama models: {}", models);
                });
            } else {
                log.warn("Ollama is not available, AI service will use fallback behavior");
            }
        } catch (Exception e) {
            log.error("Failed to initialize Ollama AI service: {}", e.getMessage());
        }
    }
    
    /**
     * Check if Ollama AI service is available
     */
    public boolean isAvailable() {
        return enabled && ollamaClient.isAvailable();
    }
    
    /**
     * Execute complete turn using Ollama decisions
     * This method handles the entire turn flow for an Alien
     */
    public CompletableFuture<Boolean> executeCompleteTurn(
            Alien alien, ITacticalField field, ICombatManagerExtended combatManager) {
        
        if (!enabled || !ollamaClient.isAvailable()) {
            return CompletableFuture.completedFuture(executeFallbackTurn(alien, field, combatManager));
        }
        
        return makeCompleteTurnDecision(alien, field, combatManager)
                .thenCompose(decision -> {
                    log.info("🤖 Ollama decision for {}: {} -> {} at {}", 
                             alien.getName(), decision.getPrimaryAction(), 
                             decision.getSecondaryAction(), decision.getTargetPosition());
                    
                    return executeDecisionStepByStep(decision, alien, field, combatManager);
                })
                .exceptionally(throwable -> {
                    log.error("Complete turn execution failed: {}", throwable.getMessage());
                    return executeFallbackTurn(alien, field, combatManager);
                });
    }
    
    /**
     * Make complete turn decision for Alien using Ollama
     */
    public CompletableFuture<IEnemyAI.AITurnDecision> makeCompleteTurnDecision(
            Alien alien, ITacticalField field, ICombatManagerExtended combatManager) {
        
        if (!enabled || !ollamaClient.isAvailable()) {
            return CompletableFuture.completedFuture(createFallbackDecision(alien, field));
        }
        
        try {
            BattlefieldSituation situation = analyzeBattlefieldSituation(alien, field, combatManager);
            String prompt = buildCompleteTurnDecisionPrompt(alien, field, situation);
            
            return ollamaClient.generateCompletion(createOllamaRequest(prompt))
                    .thenApply(response -> parseCompleteTurnDecision(response, alien, field, situation))
                    .thenApply(decision -> {
                        cacheDecision(generateCacheKey(alien, field, situation), decision);
                        return decision;
                    })
                    .exceptionally(throwable -> {
                        log.error("Ollama turn decision failed: {}", throwable.getMessage());
                        return createFallbackDecision(alien, field);
                    });
                    
        } catch (Exception e) {
            log.error("Error in complete turn decision: {}", e.getMessage());
            return CompletableFuture.completedFuture(createFallbackDecision(alien, field));
        }
    }
    
    /**
     * Execute decision step by step with detailed logging
     */
    private CompletableFuture<Boolean> executeDecisionStepByStep(
            IEnemyAI.AITurnDecision decision, Alien alien, ITacticalField field, 
            ICombatManagerExtended combatManager) {
        
        log.info("🎯 Executing Ollama decision for {} step by step", alien.getName());
        log.info("  📍 Primary Action: {}", decision.getPrimaryAction());
        log.info("  📍 Secondary Action: {}", decision.getSecondaryAction());
        log.info("  📍 Target Position: {}", decision.getTargetPosition());
        log.info("  📍 Target Unit: {}", decision.getTargetUnit() != null ? decision.getTargetUnit().getName() : "None");
        log.info("  📍 Reasoning: {}", decision.getReasoning());
        log.info("  📍 Confidence: {:.2f}", decision.getConfidence());
        
        try {
            boolean success = false;
            
            // Execute primary action
            if (decision.getPrimaryAction() != null && !"none".equals(decision.getPrimaryAction())) {
                try {
                    success = executeActionByType(decision.getPrimaryAction(), alien, field, combatManager, 
                                               decision.getTargetPosition(), decision.getTargetUnit()).get();
                    
                    if (success) {
                        log.info("✅ Primary action '{}' executed successfully", decision.getPrimaryAction());
                    } else {
                        log.warn("❌ Primary action '{}' failed", decision.getPrimaryAction());
                    }
                } catch (Exception e) {
                    log.error("Error executing primary action: {}", e.getMessage());
                    success = false;
                }
            }
            
            // Execute secondary action if primary succeeded and we have action points
            if (success && alien.getActionPoints() > 0 && 
                decision.getSecondaryAction() != null && !"none".equals(decision.getSecondaryAction())) {
                
                try {
                    boolean secondarySuccess = executeActionByType(decision.getSecondaryAction(), alien, field, combatManager,
                                                               decision.getTargetPosition(), decision.getTargetUnit()).get();
                    
                    if (secondarySuccess) {
                        log.info("✅ Secondary action '{}' executed successfully", decision.getSecondaryAction());
                    } else {
                        log.warn("❌ Secondary action '{}' failed", decision.getSecondaryAction());
                    }
                    
                    success = success && secondarySuccess;
                } catch (Exception e) {
                    log.error("Error executing secondary action: {}", e.getMessage());
                    success = false;
                }
            }
            
            // Log final turn status
            if (isTurnComplete(alien)) {
                log.info("🏁 {} turn completed - all action points exhausted", alien.getName());
            } else {
                log.info("⏳ {} turn continues - {} action points remaining", 
                         alien.getName(), alien.getActionPoints());
            }
            
            return CompletableFuture.completedFuture(success);
            
        } catch (Exception e) {
            log.error("Error executing decision step by step: {}", e.getMessage());
            return CompletableFuture.completedFuture(false);
        }
    }
    
    /**
     * Execute a specific action based on Ollama decision
     */
    private CompletableFuture<Boolean> executeSpecificAction(
            String action, Alien alien, ITacticalField field, 
            ICombatManagerExtended combatManager, IEnemyAI.AITurnDecision decision) {
        
        log.info("⚡ Executing action '{}' for {} (AP: {})", 
                 action, alien.getName(), alien.getActionPoints());
        
        try {
            switch (action) {
                case "move_to_position":
                    return CompletableFuture.completedFuture(executeMoveAction(alien, field, decision.getTargetPosition()));
                case "attack_target":
                    return CompletableFuture.completedFuture(executeAttackAction(alien, field, combatManager, decision.getTargetUnit()));
                case "use_special_ability":
                    return CompletableFuture.completedFuture(executeSpecialAbilityAction(alien, field, combatManager, decision.getTargetUnit()));
                case "retreat":
                    return CompletableFuture.completedFuture(executeRetreatAction(alien, field));
                case "defend":
                    return CompletableFuture.completedFuture(executeDefendAction(alien));
                case "overwatch":
                    return CompletableFuture.completedFuture(executeOverwatchAction(alien, field));
                case "flank_enemy":
                    return CompletableFuture.completedFuture(executeFlankAction(alien, field, decision.getTargetUnit()));
                case "support_ally":
                    return CompletableFuture.completedFuture(executeSupportAction(alien, field, decision.getTargetUnit()));
                default:
                    log.warn("⚠️ Unknown action '{}' for {}, defaulting to defend", action, alien.getName());
                    return CompletableFuture.completedFuture(executeDefendAction(alien));
            }
        } catch (Exception e) {
            log.error("❌ Error executing action '{}' for {}: {}", action, alien.getName(), e.getMessage());
            return CompletableFuture.completedFuture(false);
        }
    }
    
    /**
     * Check if the Alien's turn is complete (no more action points)
     */
    public boolean isTurnComplete(Alien alien) {
        return alien != null && alien.getActionPoints() <= 0;
    }
    
    /**
     * Get remaining action points for the Alien
     */
    public int getRemainingActionPoints(Alien alien) {
        return alien != null ? (int) alien.getActionPoints() : 0;
    }
    
    /**
     * Execute specific action by type
     */
    private CompletableFuture<Boolean> executeActionByType(String actionType, Alien alien, ITacticalField field,
                                      ICombatManagerExtended combatManager, Position targetPosition, Unit targetUnit) {
        
        try {
            switch (actionType) {
                case "move_to_position":
                    if (targetPosition != null && alien.getActionPoints() >= 1) {
                        return CompletableFuture.completedFuture(executeMoveAction(alien, field, targetPosition));
                    }
                    break;
                    
                case "attack_target":
                    if (targetUnit != null && alien.getActionPoints() >= 1) {
                        return CompletableFuture.completedFuture(executeAttackAction(alien, field, combatManager, targetUnit));
                    }
                    break;
                    
                case "use_special_ability":
                    if (targetUnit != null && alien.getActionPoints() >= 2) {
                        return CompletableFuture.completedFuture(executeSpecialAbilityAction(alien, field, combatManager, targetUnit));
                    }
                    break;
                    
                case "retreat":
                    if (alien.getActionPoints() >= 1) {
                        return CompletableFuture.completedFuture(executeRetreatAction(alien, field));
                    }
                    break;
                    
                case "defend":
                    return CompletableFuture.completedFuture(executeDefendAction(alien));
                    
                case "overwatch":
                    if (alien.getActionPoints() >= 1) {
                        return CompletableFuture.completedFuture(executeOverwatchAction(alien, field));
                    }
                    break;
                    
                case "flank":
                    if (targetUnit != null && alien.getActionPoints() >= 1) {
                        return CompletableFuture.completedFuture(executeFlankAction(alien, field, targetUnit));
                    }
                    break;
                    
                case "support":
                    if (targetUnit != null && alien.getActionPoints() >= 1) {
                        return CompletableFuture.completedFuture(executeSupportAction(alien, field, targetUnit));
                    }
                    break;
                    
                default:
                    log.warn("Unknown action type: {}", actionType);
                    return CompletableFuture.completedFuture(false);
            }
            
            return CompletableFuture.completedFuture(false);
            
        } catch (Exception e) {
            log.error("Error executing action type {}: {}", actionType, e.getMessage());
            return CompletableFuture.completedFuture(false);
        }
    }
    
    /**
     * Execute fallback turn when Ollama is not available
     */
    private boolean executeFallbackTurn(Alien alien, ITacticalField field, ICombatManagerExtended combatManager) {
        log.warn("Using fallback turn logic for {}", alien.getName());
        
        try {
            // Simple fallback: spend action points and do nothing
            if (alien.getActionPoints() >= 1) {
                alien.spendActionPoints(1);
                log.debug("Fallback turn: spent 1 AP for {}", alien.getName());
                return true;
            }
        } catch (Exception e) {
            log.error("Fallback turn failed: {}", e.getMessage());
        }
        
        return false;
    }
    
    /**
     * Execute overwatch action
     */
    private boolean executeOverwatchAction(Alien alien, ITacticalField field) {
        try {
            if (alien.getActionPoints() >= 1) {
                alien.spendActionPoints(1);
                log.info("✅ {} is on overwatch", alien.getName());
                return true;
            }
            return false;
        } catch (Exception e) {
            log.error("Error executing overwatch action: {}", e.getMessage());
            return false;
        }
    }
    
    /**
     * Execute flank action
     */
    private boolean executeFlankAction(Alien alien, ITacticalField field, Unit target) {
        try {
            if (alien.getActionPoints() >= 1 && target != null) {
                // Find flanking position
                Position flankPosition = findFlankingPosition(alien, target, field);
                if (flankPosition != null) {
                    alien.setPosition(flankPosition);
                    alien.spendActionPoints(1);
                    log.info("✅ {} flanked to {}", alien.getName(), flankPosition);
                    return true;
                }
            }
            return false;
        } catch (Exception e) {
            log.error("Error executing flank action: {}", e.getMessage());
            return false;
        }
    }
    
    /**
     * Execute support action
     */
    private boolean executeSupportAction(Alien alien, ITacticalField field, Unit ally) {
        try {
            if (alien.getActionPoints() >= 1 && ally != null) {
                // Move closer to ally for support
                Position supportPosition = findSupportPosition(alien, ally, field);
                if (supportPosition != null) {
                    alien.setPosition(supportPosition);
                    alien.spendActionPoints(1);
                    log.info("✅ {} moved to support {}", alien.getName(), ally.getName());
                    return true;
                }
            }
            return false;
        } catch (Exception e) {
            log.error("Error executing support action: {}", e.getMessage());
            return false;
        }
    }
    
    /**
     * Calculate best move position using Ollama analysis
     */
    public CompletableFuture<Position> calculateBestMovePosition(
            Alien alien, ITacticalField field, ICombatManagerExtended combatManager) {
        
        return makeCompleteTurnDecision(alien, field, combatManager)
                .thenApply(decision -> decision.getTargetPosition());
    }
    
    /**
     * Find best targets using Ollama analysis
     */
    public CompletableFuture<List<Unit>> findBestTargets(
            Alien alien, ITacticalField field, ICombatManagerExtended combatManager) {
        
        return makeCompleteTurnDecision(alien, field, combatManager)
                .thenApply(decision -> {
                    if (decision.getTargetUnit() != null) {
                        return List.of(decision.getTargetUnit());
                    }
                    return getVisibleEnemies(alien, field);
                });
    }
    
    /**
     * Check if should use special ability using Ollama
     */
    public CompletableFuture<Boolean> shouldUseSpecialAbility(
            Alien alien, ITacticalField field, ICombatManagerExtended combatManager) {
        
        return makeCompleteTurnDecision(alien, field, combatManager)
                .thenApply(decision -> 
                    "use_special_ability".equals(decision.getPrimaryAction()) ||
                    "use_special_ability".equals(decision.getSecondaryAction()));
    }
    
    /**
     * Analyze complete tactical situation on the battlefield
     */
    public CompletableFuture<IEnemyAI.TacticalSituation> analyzeTacticalSituation(
            Alien alien, ITacticalField field, ICombatManagerExtended combatManager) {
        
        if (!enabled || !ollamaClient.isAvailable()) {
            return CompletableFuture.completedFuture(createFallbackTacticalSituation(alien, field));
        }
        
        try {
            BattlefieldSituation situation = analyzeBattlefieldSituation(alien, field, combatManager);
            
            String prompt = buildTacticalAnalysisPrompt(alien, field, situation);
            
            return ollamaClient.generateCompletion(createOllamaRequest(prompt))
                    .thenApply(response -> parseTacticalAnalysis(response, alien, field, situation))
                    .exceptionally(throwable -> {
                        log.error("Tactical analysis failed: {}", throwable.getMessage());
                        return createFallbackTacticalSituation(alien, field);
                    });
                    
        } catch (Exception e) {
            log.error("Error in tactical analysis: {}", e.getMessage());
            return CompletableFuture.completedFuture(createFallbackTacticalSituation(alien, field));
        }
    }
    
    /**
     * Execute action based on Ollama decision (legacy method)
     */
    public CompletableFuture<Boolean> executeAction(
            Alien alien, ITacticalField field, ICombatManagerExtended combatManager) {
        
        return makeCompleteTurnDecision(alien, field, combatManager)
                .thenCompose(decision -> {
                    try {
                        return executeDecisionStepByStep(decision, alien, field, combatManager);
                    } catch (Exception e) {
                        log.error("Error executing action: {}", e.getMessage());
                        return CompletableFuture.completedFuture(false);
                    }
                });
    }
    
    /**
     * Cleanup resources and shutdown the service
     */
    public void cleanup() {
        try {
            log.info("Cleaning up Ollama AI Service...");
            
            // Close Ollama client
            if (ollamaClient != null) {
                ollamaClient.close();
            }
            
            // Clear decision cache
            if (decisionCache != null) {
                decisionCache.clear();
            }
            
            // Clear learned patterns
            if (learnedPatterns != null) {
                learnedPatterns.clear();
            }
            
            // Note: random is final, cannot be nullified
            
            log.info("Ollama AI Service cleanup completed");
            
        } catch (Exception e) {
            log.error("Error during Ollama AI Service cleanup", e);
        }
    }
    
    /**
     * Data class for battlefield situation analysis
     */
    private static class BattlefieldSituation {
        Position alienPosition;
        List<Unit> visibleEnemies;
        List<Unit> visibleAllies;
        List<Position> availableMovePositions;
        Map<Position, Double> positionScores;
        Map<Unit, Double> threatLevels;
        ITacticalField field;
        ICombatManagerExtended combatManager;
        
        // Additional fields for analysis
        int alienHealth;
        int alienMaxHealth;
        double alienActionPoints;
        String alienType;
        List<Position> coverPositions;
        List<Position> hazardPositions;
        double fieldWidth;
        double fieldHeight;
        
        // Default constructor
        BattlefieldSituation() {
            this.visibleEnemies = new ArrayList<>();
            this.visibleAllies = new ArrayList<>();
            this.availableMovePositions = new ArrayList<>();
            this.positionScores = new HashMap<>();
            this.threatLevels = new HashMap<>();
            this.coverPositions = new ArrayList<>();
            this.hazardPositions = new ArrayList<>();
        }
        
        // Full constructor
        BattlefieldSituation(Position alienPosition, List<Unit> visibleEnemies, 
                           List<Unit> visibleAllies, List<Position> availableMovePositions,
                           Map<Position, Double> positionScores, Map<Unit, Double> threatLevels,
                           ITacticalField field, ICombatManagerExtended combatManager) {
            this.alienPosition = alienPosition;
            this.visibleEnemies = visibleEnemies;
            this.visibleAllies = visibleAllies;
            this.availableMovePositions = availableMovePositions;
            this.positionScores = positionScores;
            this.threatLevels = threatLevels;
            this.field = field;
            this.combatManager = combatManager;
            this.coverPositions = new ArrayList<>();
            this.hazardPositions = new ArrayList<>();
        }
    }

    /**
     * Create fallback decision when Ollama is not available
     */
    private IEnemyAI.AITurnDecision createFallbackDecision(Alien alien, ITacticalField field) {
        if (alien == null) {
            return new IEnemyAI.AITurnDecision("none", "none", null, null, "No alien available", 0.0);
        }
        
        // Simple fallback logic
        String primaryAction = "defend";
        Position targetPosition = alien.getPosition();
        Unit targetUnit = null;
        String reasoning = "Fallback decision - Ollama unavailable";
        double confidence = 0.3;
        
        // Check if we can attack
        List<Unit> visibleEnemies = getVisibleEnemies(alien, field);
        if (!visibleEnemies.isEmpty() && alien.getActionPoints() >= 1) {
            Unit nearestEnemy = findNearestEnemy(alien, visibleEnemies);
            if (isInAttackRange(alien, nearestEnemy)) {
                primaryAction = "attack_target";
                targetUnit = nearestEnemy;
                reasoning = "Fallback: Attack nearest visible enemy";
                confidence = 0.5;
            } else if (alien.getActionPoints() >= 1) {
                primaryAction = "move_to_position";
                targetPosition = findNearestValidPosition(alien, nearestEnemy.getPosition(), field);
                reasoning = "Fallback: Move towards nearest enemy";
                confidence = 0.4;
            }
        }
        
        return new IEnemyAI.AITurnDecision(primaryAction, "none", targetPosition, targetUnit, reasoning, confidence);
    }
    
    /**
     * Create fallback tactical situation when Ollama is not available
     */
    private IEnemyAI.TacticalSituation createFallbackTacticalSituation(Alien alien, ITacticalField field) {
        if (alien == null || field == null) {
            return new IEnemyAI.TacticalSituation(
                new ArrayList<>(), new ArrayList<>(), new ArrayList<>(),
                new HashMap<>(), new HashMap<>(), "No tactical data available"
            );
        }
        
        List<Position> availablePositions = getValidMovePositions(alien, field);
        List<Unit> visibleEnemies = getVisibleEnemies(alien, field);
        List<Unit> visibleAllies = getVisibleAllies(alien, field);
        
        Map<Position, Double> positionScores = new HashMap<>();
        for (Position pos : availablePositions) {
            positionScores.put(pos, calculateFallbackPositionScore(pos, alien, field));
        }
        
        Map<Unit, Double> threatLevels = new HashMap<>();
        for (Unit enemy : visibleEnemies) {
            threatLevels.put(enemy, calculateFallbackThreatLevel(enemy, alien));
        }
        
        return new IEnemyAI.TacticalSituation(
            availablePositions, visibleEnemies, visibleAllies,
            positionScores, threatLevels, "Fallback tactical analysis"
        );
    }
    
    /**
     * Analyze battlefield situation for Ollama
     */
    private BattlefieldSituation analyzeBattlefieldSituation(Alien alien, ITacticalField field, 
                                                           ICombatManagerExtended combatManager) {
        BattlefieldSituation situation = new BattlefieldSituation();
        
        try {
            // Get alien information
            situation.alienHealth = alien.getCurrentHealth();
            situation.alienMaxHealth = alien.getMaxHealth();
            situation.alienActionPoints = alien.getActionPoints();
            situation.alienPosition = alien.getPosition();
            situation.alienType = alien.getAlienType().name();
            
            // Get visible enemies and allies
            situation.visibleEnemies = getVisibleEnemies(alien, field);
            situation.visibleAllies = getVisibleAllies(alien, field);
            
            // Get available move positions
            situation.availableMovePositions = getValidMovePositions(alien, field);
            
            // Get cover information
            situation.coverPositions = getCoverPositions(alien, field);
            
            // Get environmental hazards
            situation.hazardPositions = getHazardPositions(field);
            
            // Get tactical field dimensions
            situation.fieldWidth = field.getWidth();
            situation.fieldHeight = field.getHeight();
            
        } catch (Exception e) {
            log.error("Error analyzing battlefield situation: {}", e.getMessage());
        }
        
        return situation;
    }
    
    /**
     * Build prompt for complete turn decision
     */
    private String buildCompleteTurnDecisionPrompt(Alien alien, ITacticalField field, BattlefieldSituation situation) {
        StringBuilder prompt = new StringBuilder();
        
        prompt.append("You are an AI controlling an Alien unit in a tactical combat game. ");
        prompt.append("Analyze the battlefield situation and decide the best actions for this turn.\n\n");
        
        prompt.append("ALIEN INFORMATION:\n");
        prompt.append("- Type: ").append(situation.alienType).append("\n");
        prompt.append("- Health: ").append(situation.alienHealth).append("/").append(situation.alienMaxHealth).append("\n");
        prompt.append("- Action Points: ").append(situation.alienActionPoints).append("\n");
        prompt.append("- Position: ").append(situation.alienPosition).append("\n\n");
        
        prompt.append("BATTLEFIELD SITUATION:\n");
        prompt.append("- Field Size: ").append(situation.fieldWidth).append("x").append(situation.fieldHeight).append("\n");
        prompt.append("- Visible Enemies: ").append(situation.visibleEnemies.size()).append("\n");
        prompt.append("- Visible Allies: ").append(situation.visibleAllies.size()).append("\n");
        prompt.append("- Available Move Positions: ").append(situation.availableMovePositions.size()).append("\n");
        prompt.append("- Cover Positions: ").append(situation.coverPositions.size()).append("\n");
        prompt.append("- Hazard Positions: ").append(situation.hazardPositions.size()).append("\n\n");
        
        prompt.append("DECISION REQUIREMENTS:\n");
        prompt.append("Choose the best primary and secondary actions from:\n");
        prompt.append("- move_to_position: Move to a strategic position\n");
        prompt.append("- attack_target: Attack an enemy unit\n");
        prompt.append("- use_special_ability: Use psionic or special ability\n");
        prompt.append("- retreat: Move away from danger\n");
        prompt.append("- defend: Stay in place and defend\n");
        prompt.append("- none: No action\n\n");
        
        prompt.append("RESPONSE FORMAT:\n");
        prompt.append("{\n");
        prompt.append("  \"primary_action\": \"action_type\",\n");
        prompt.append("  \"secondary_action\": \"action_type\",\n");
        prompt.append("  \"target_position\": {\"x\": X, \"y\": Y, \"height\": Z},\n");
        prompt.append("  \"target_unit\": \"unit_name_or_null\",\n");
        prompt.append("  \"reasoning\": \"explanation of decision\",\n");
        prompt.append("  \"confidence\": 0.0-1.0\n");
        prompt.append("}\n\n");
        
        prompt.append("Make a tactical decision based on the current situation.");
        
        return prompt.toString();
    }
    
    /**
     * Build prompt for tactical analysis
     */
    private String buildTacticalAnalysisPrompt(Alien alien, ITacticalField field, BattlefieldSituation situation) {
        StringBuilder prompt = new StringBuilder();
        
        prompt.append("Analyze the tactical situation for an Alien unit in combat.\n\n");
        prompt.append("Provide tactical advice including:\n");
        prompt.append("- Best move positions with scores\n");
        prompt.append("- Threat assessment of visible enemies\n");
        prompt.append("- Strategic recommendations\n");
        prompt.append("- Cover and positioning advice\n\n");
        
        prompt.append("RESPONSE FORMAT:\n");
        prompt.append("{\n");
        prompt.append("  \"tactical_advice\": \"detailed tactical analysis\",\n");
        prompt.append("  \"position_scores\": {\"x,y\": score, ...},\n");
        prompt.append("  \"threat_levels\": {\"unit_name\": threat_score, ...}\n");
        prompt.append("}\n");
        
        return prompt.toString();
    }
    
    /**
     * Create Ollama request with prompt
     */
    private OllamaRequest createOllamaRequest(String prompt) {
        OllamaOptions options = OllamaOptions.builder()
                .temperature(0.7)
                .topP(0.9)
                .numPredict(1000)
                .build();
        
        OllamaRequest request = OllamaRequest.builder()
                .model(defaultModel)
                .prompt(prompt)
                .options(options)
                .stream(false)
                .build();
        
        return request;
    }
    
    /**
     * Parse complete turn decision from Ollama response
     */
    private IEnemyAI.AITurnDecision parseCompleteTurnDecision(String response, Alien alien, 
                                                             ITacticalField field, BattlefieldSituation situation) {
        try {
            // Simple parsing - in production, use proper JSON parsing
            if (response.contains("move_to_position")) {
                Position targetPos = findBestMovePosition(alien, field);
                return new IEnemyAI.AITurnDecision("move_to_position", "none", targetPos, null, 
                                                 "Ollama decided to move", 0.8);
            } else if (response.contains("attack_target")) {
                List<Unit> enemies = getVisibleEnemies(alien, field);
                Unit target = !enemies.isEmpty() ? enemies.get(0) : null;
                return new IEnemyAI.AITurnDecision("attack_target", "none", alien.getPosition(), target, 
                                                 "Ollama decided to attack", 0.8);
            } else if (response.contains("defend")) {
                return new IEnemyAI.AITurnDecision("defend", "none", alien.getPosition(), null, 
                                                 "Ollama decided to defend", 0.8);
            } else {
                return new IEnemyAI.AITurnDecision("defend", "none", alien.getPosition(), null, 
                                                 "Ollama response: " + response, 0.6);
            }
        } catch (Exception e) {
            log.error("Error parsing Ollama response: {}", e.getMessage());
            return createFallbackDecision(alien, field);
        }
    }
    
    /**
     * Parse tactical analysis from Ollama response
     */
    private IEnemyAI.TacticalSituation parseTacticalAnalysis(String response, Alien alien, 
                                                            ITacticalField field, BattlefieldSituation situation) {
        try {
            // Simple parsing - in production, use proper JSON parsing
            String tacticalAdvice = "Ollama analysis: " + response;
            
            List<Position> availablePositions = getValidMovePositions(alien, field);
            List<Unit> visibleEnemies = getVisibleEnemies(alien, field);
            List<Unit> visibleAllies = getVisibleAllies(alien, field);
            
            Map<Position, Double> positionScores = new HashMap<>();
            for (Position pos : availablePositions) {
                positionScores.put(pos, calculateFallbackPositionScore(pos, alien, field));
            }
            
            Map<Unit, Double> threatLevels = new HashMap<>();
            for (Unit enemy : visibleEnemies) {
                threatLevels.put(enemy, calculateFallbackThreatLevel(enemy, alien));
            }
            
            return new IEnemyAI.TacticalSituation(
                availablePositions, visibleEnemies, visibleAllies,
                positionScores, threatLevels, tacticalAdvice
            );
            
        } catch (Exception e) {
            log.error("Error parsing tactical analysis: {}", e.getMessage());
            return createFallbackTacticalSituation(alien, field);
        }
    }
    
    /**
     * Execute move action
     */
    private boolean executeMoveAction(Alien alien, ITacticalField field, Position targetPosition) {
        try {
            if (targetPosition != null && alien.getActionPoints() >= 1) {
                // Check if position is valid and reachable
                if (isValidMovePosition(alien, targetPosition, field)) {
                    alien.setPosition(targetPosition);
                    alien.spendActionPoints(1);
                    log.info("✅ {} moved to {}", alien.getName(), targetPosition);
                    return true;
                }
            }
            return false;
        } catch (Exception e) {
            log.error("Error executing move action: {}", e.getMessage());
            return false;
        }
    }
    
    /**
     * Execute attack action
     */
    private boolean executeAttackAction(Alien alien, ITacticalField field, 
                                      ICombatManagerExtended combatManager, Unit target) {
        try {
            if (target != null && alien.getActionPoints() >= 1 && target.isAlive()) {
                // Check if target is in range
                if (isInAttackRange(alien, target)) {
                    // Perform attack through combat manager
                    boolean success = true; // Simplified for now
                    if (success) {
                        alien.spendActionPoints(1);
                        log.info("✅ {} attacked {}", alien.getName(), target.getName());
                        return true;
                    }
                }
            }
            return false;
        } catch (Exception e) {
            log.error("Error executing attack action: {}", e.getMessage());
            return false;
        }
    }
    
    /**
     * Execute special ability action
     */
    private boolean executeSpecialAbilityAction(Alien alien, ITacticalField field,
                                              ICombatManagerExtended combatManager, Unit target) {
        try {
            if (target != null && alien.getActionPoints() >= 2 && !alien.getPsionicAbilities().isEmpty()) {
                // Use first available psionic ability
                String ability = alien.getPsionicAbilities().get(0).toString();
                boolean success = true; // Simplified for now
                if (success) {
                    alien.spendActionPoints(2);
                    log.info("✅ {} used {} on {}", alien.getName(), ability, target.getName());
                    return true;
                }
            }
            return false;
        } catch (Exception e) {
            log.error("Error executing special ability action: {}", e.getMessage());
            return false;
        }
    }
    
    /**
     * Execute retreat action
     */
    private boolean executeRetreatAction(Alien alien, ITacticalField field) {
        try {
            if (alien.getActionPoints() >= 1) {
                // Find safe position away from enemies
                Position safePosition = findSafeRetreatPosition(alien, field);
                if (safePosition != null) {
                    alien.setPosition(safePosition);
                    alien.spendActionPoints(1);
                    log.info("✅ {} retreated to {}", alien.getName(), safePosition);
                    return true;
                }
            }
            return false;
        } catch (Exception e) {
            log.error("Error executing retreat action: {}", e.getMessage());
            return false;
        }
    }
    
    /**
     * Execute defend action
     */
    private boolean executeDefendAction(Alien alien) {
        try {
            // Defend action doesn't cost action points, just increases defense
            log.info("✅ {} is defending", alien.getName());
            return true;
        } catch (Exception e) {
            log.error("Error executing defend action: {}", e.getMessage());
            return false;
        }
    }
    
    /**
     * Check if move position is valid
     */
    private boolean isValidMovePosition(Alien alien, Position position, ITacticalField field) {
        if (position == null || field == null) return false;
        
        // Check bounds
        if (position.getX() < 0 || position.getX() >= field.getWidth() ||
            position.getY() < 0 || position.getY() >= field.getHeight()) {
            return false;
        }
        
        // Check if position is occupied
        if (field.getUnitAt(position) != null) {
            return false;
        }
        
        // Check if position is reachable within movement range
        Position currentPos = alien.getPosition();
        int distance = Math.abs(position.getX() - currentPos.getX()) + 
                      Math.abs(position.getY() - currentPos.getY());
        
        return distance <= alien.getMovementRange();
    }
    
    /**
     * Find safe retreat position
     */
    private Position findSafeRetreatPosition(Alien alien, ITacticalField field) {
        List<Position> validPositions = getValidMovePositions(alien, field);
        if (validPositions.isEmpty()) return null;
        
        // Find position furthest from enemies
        List<Unit> enemies = getVisibleEnemies(alien, field);
        if (enemies.isEmpty()) {
            return validPositions.get(0); // Return first valid position if no enemies
        }
        
        Position bestPosition = null;
        double maxDistance = 0;
        
        for (Position pos : validPositions) {
            double minEnemyDistance = Double.MAX_VALUE;
            for (Unit enemy : enemies) {
                double distance = calculateDistance(pos, enemy.getPosition());
                minEnemyDistance = Math.min(minEnemyDistance, distance);
            }
            
            if (minEnemyDistance > maxDistance) {
                maxDistance = minEnemyDistance;
                bestPosition = pos;
            }
        }
        
        return bestPosition;
    }
    
    /**
     * Generate cache key for decisions
     */
    private String generateCacheKey(Alien alien, ITacticalField field, BattlefieldSituation situation) {
        if (alien == null || field == null) return "unknown";
        
        StringBuilder key = new StringBuilder();
        key.append(alien.getName()).append("_");
        key.append(alien.getPosition()).append("_");
        key.append(situation.visibleEnemies.size()).append("_");
        key.append(situation.visibleAllies.size()).append("_");
        key.append(alien.getActionPoints());
        
        return key.toString();
    }
    
    /**
     * Cache decision for future use
     */
    private void cacheDecision(String key, IEnemyAI.AITurnDecision decision) {
        if (decisionCache.size() >= maxCacheSize) {
            // Remove oldest entries
            String oldestKey = decisionCache.keySet().iterator().next();
            decisionCache.remove(oldestKey);
        }
        
        // Store decision as JSON string (simplified)
        String decisionJson = String.format("{\"action\":\"%s\",\"confidence\":%.2f}", 
                                          decision.getPrimaryAction(), decision.getConfidence());
        decisionCache.put(key, decisionJson);
    }
    
    /**
     * Get visible enemies for alien
     */
    private List<Unit> getVisibleEnemies(Alien alien, ITacticalField field) {
        if (alien == null || field == null) return new ArrayList<>();
        
        return field.getAllUnits().stream()
                .filter(unit -> unit.getUnitType() == com.aliensattack.core.enums.UnitType.SOLDIER)
                .filter(Unit::isAlive)
                .filter(unit -> isInRange(alien.getPosition(), unit.getPosition(), alien.getAttackRange()))
                .collect(Collectors.toList());
    }
    
    /**
     * Get visible allies for alien
     */
    private List<Unit> getVisibleAllies(Alien alien, ITacticalField field) {
        if (alien == null || field == null) return new ArrayList<>();
        
        return field.getAllUnits().stream()
                .filter(unit -> unit.getUnitType() == com.aliensattack.core.enums.UnitType.ALIEN)
                .filter(Unit::isAlive)
                .filter(unit -> !unit.equals(alien))
                .filter(unit -> isInRange(alien.getPosition(), unit.getPosition(), alien.getAttackRange()))
                .collect(Collectors.toList());
    }
    
    /**
     * Get valid move positions for alien
     */
    private List<Position> getValidMovePositions(Alien alien, ITacticalField field) {
        if (alien == null || field == null) return new ArrayList<>();
        
        List<Position> positions = new ArrayList<>();
        Position currentPos = alien.getPosition();
        int maxRange = alien.getMovementRange();
        
        for (int x = Math.max(0, currentPos.getX() - maxRange); 
             x <= Math.min(field.getWidth() - 1, currentPos.getX() + maxRange); x++) {
            for (int y = Math.max(0, currentPos.getY() - maxRange); 
                 y <= Math.min(field.getHeight() - 1, currentPos.getY() + maxRange); y++) {
                
                Position pos = new Position(x, y);
                int distance = Math.abs(x - currentPos.getX()) + Math.abs(y - currentPos.getY());
                
                if (distance <= maxRange && distance > 0 && isValidMovePosition(alien, pos, field)) {
                    positions.add(pos);
                }
            }
        }
        
        return positions;
    }
    
    /**
     * Get cover positions
     */
    private List<Position> getCoverPositions(Alien alien, ITacticalField field) {
        if (alien == null || field == null) return new ArrayList<>();
        
        List<Position> coverPositions = new ArrayList<>();
        List<Position> validPositions = getValidMovePositions(alien, field);
        
        for (Position pos : validPositions) {
            if (field.getCoverObject(pos) != null) {
                coverPositions.add(pos);
            }
        }
        
        return coverPositions;
    }
    
    /**
     * Get hazard positions
     */
    private List<Position> getHazardPositions(ITacticalField field) {
        if (field == null) return new ArrayList<>();
        
        // This would need to be implemented based on your hazard system
        // For now, return empty list
        return new ArrayList<>();
    }
    
    /**
     * Find best move position (fallback)
     */
    private Position findBestMovePosition(Alien alien, ITacticalField field) {
        List<Position> validPositions = getValidMovePositions(alien, field);
        if (validPositions.isEmpty()) return alien.getPosition();
        
        // Return first valid position
        return validPositions.get(0);
    }
    
    /**
     * Find nearest enemy
     */
    private Unit findNearestEnemy(Alien alien, List<Unit> enemies) {
        if (enemies.isEmpty()) return null;
        
        return enemies.stream()
                .min(Comparator.comparingDouble(enemy -> 
                    calculateDistance(alien.getPosition(), enemy.getPosition())))
                .orElse(null);
    }
    
    /**
     * Find nearest valid position to target
     */
    private Position findNearestValidPosition(Alien alien, Position targetPos, ITacticalField field) {
        List<Position> validPositions = getValidMovePositions(alien, field);
        if (validPositions.isEmpty()) return null;
        
        return validPositions.stream()
                .min(Comparator.comparingDouble(pos -> 
                    calculateDistance(pos, targetPos)))
                .orElse(null);
    }
    
    /**
     * Check if unit is in attack range
     */
    private boolean isInAttackRange(Alien alien, Unit target) {
        if (alien == null || target == null) return false;
        
        Position alienPos = alien.getPosition();
        Position targetPos = target.getPosition();
        
        if (alienPos == null || targetPos == null) return false;
        
        double distance = calculateDistance(alienPos, targetPos);
        return distance <= alien.getAttackRange();
    }
    
    /**
     * Check if position is in range
     */
    private boolean isInRange(Position pos1, Position pos2, int range) {
        return calculateDistance(pos1, pos2) <= range;
    }
    
    /**
     * Calculate distance between positions
     */
    private double calculateDistance(Position pos1, Position pos2) {
        int dx = pos1.getX() - pos2.getX();
        int dy = pos1.getY() - pos2.getY();
        return Math.sqrt(dx * dx + dy * dy);
    }
    
    /**
     * Calculate fallback position score
     */
    private double calculateFallbackPositionScore(Position position, Alien alien, ITacticalField field) {
        double score = 0.0;
        
        // Distance to enemies (closer is better for aggressive aliens)
        List<Unit> enemies = getVisibleEnemies(alien, field);
        for (Unit enemy : enemies) {
            double distance = calculateDistance(position, enemy.getPosition());
            score += Math.max(0, 10 - distance);
        }
        
        // Cover bonus
        if (field.getCoverObject(position) != null) {
            score += 5.0;
        }
        
        // Height advantage
        if (position.getHeight() > 0) {
            score += position.getHeight() * 2.0;
        }
        
        return score;
    }
    
    /**
     * Calculate fallback threat level
     */
    private double calculateFallbackThreatLevel(Unit enemy, Alien alien) {
        double threat = 0.0;
        
        // Health-based threat (lower health = higher threat)
        double healthPercentage = (double) enemy.getCurrentHealth() / enemy.getMaxHealth();
        threat += (1.0 - healthPercentage) * 5.0;
        
        // Distance-based threat
        double distance = calculateDistance(alien.getPosition(), enemy.getPosition());
        threat += Math.max(0, 10 - distance);
        
        // Weapon-based threat
        if (enemy.getWeapon() != null) {
            threat += 5.0; // Default weapon threat
        }
        
        return threat;
    }
    
    /**
     * Find flanking position
     */
    private Position findFlankingPosition(Alien alien, Unit target, ITacticalField field) {
        List<Position> validPositions = getValidMovePositions(alien, field);
        if (validPositions.isEmpty()) return null;
        
        Position targetPos = target.getPosition();
        Position bestPosition = null;
        double bestScore = -1;
        
        for (Position pos : validPositions) {
            double score = calculateFlankingScore(pos, targetPos, alien, field);
            if (score > bestScore) {
                bestScore = score;
                bestPosition = pos;
            }
        }
        
        return bestPosition;
    }
    
    /**
     * Find support position
     */
    private Position findSupportPosition(Alien alien, Unit ally, ITacticalField field) {
        List<Position> validPositions = getValidMovePositions(alien, field);
        if (validPositions.isEmpty()) return null;
        
        Position allyPos = ally.getPosition();
        Position bestPosition = null;
        double bestScore = -1;
        
        for (Position pos : validPositions) {
            double score = calculateSupportScore(pos, allyPos, alien, field);
            if (score > bestScore) {
                bestScore = score;
                bestPosition = pos;
            }
        }
        
        return bestPosition;
    }
    
    /**
     * Calculate flanking score
     */
    private double calculateFlankingScore(Position position, Position targetPos, Alien alien, ITacticalField field) {
        double score = 0.0;
        
        // Distance to target (closer is better)
        double distance = calculateDistance(position, targetPos);
        score += Math.max(0, 10 - distance);
        
        // Cover bonus
        if (field.getCoverObject(position) != null) {
            score += 5.0;
        }
        
        // Height advantage
        if (position.getHeight() > 0) {
            score += position.getHeight() * 2.0;
        }
        
        return score;
    }
    
    /**
     * Calculate support score
     */
    private double calculateSupportScore(Position position, Position allyPos, Alien alien, ITacticalField field) {
        double score = 0.0;
        
        // Distance to ally (closer is better for support)
        double distance = calculateDistance(position, allyPos);
        score += Math.max(0, 8 - distance);
        
        // Cover bonus
        if (field.getCoverObject(position) != null) {
            score += 3.0;
        }
        
        // Strategic position
        if (position.getHeight() > 0) {
            score += position.getHeight();
        }
        
        return score;
    }
    
    /**
     * Get decision from Ollama for a given prompt
     */
    public String getOllamaDecision(String prompt) {
        try {
            if (!enabled || !ollamaClient.isAvailable()) {
                log.warn("Ollama not available, returning null");
                return null;
            }
            
            log.info("🤖 Sending prompt to Ollama for decision making");
            
            // Create request for decision
            OllamaRequest request = OllamaRequest.builder()
                .model("llama2")
                .prompt(prompt)
                .stream(false)
                .options(OllamaOptions.builder()
                    .numPredict(500)
                    .temperature(0.7)
                    .topP(0.9)
                    .build())
                .build();
            
            // Get response from Ollama
            String response = ollamaClient.generateCompletion(request).get();
            
            if (response != null && !response.trim().isEmpty()) {
                log.info("✅ Received decision from Ollama: {}", response.substring(0, Math.min(100, response.length())));
                return response;
            } else {
                log.warn("⚠️ Ollama returned empty response");
                return null;
            }
            
        } catch (Exception e) {
            log.error("❌ Error getting decision from Ollama: {}", e.getMessage());
            return null;
        }
    }
    
}
