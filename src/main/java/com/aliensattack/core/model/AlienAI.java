package com.aliensattack.core.model;

import com.aliensattack.core.enums.AlienType;
import com.aliensattack.core.enums.AIBehaviorType;
import lombok.Getter;
import lombok.Setter;

import java.util.*;

/**
 * Alien AI - Sophisticated behavior trees for XCOM 2 aliens
 * Implements XCOM 2 Alien AI Behavior System
 */
@Getter
@Setter
public class AlienAI {
    
    private String aiId;
    private AlienType alienType;
    private AIBehaviorType primaryBehavior;
    private AIBehaviorType secondaryBehavior;
    private Map<String, Integer> behaviorWeights;
    private List<String> availableActions;
    private Map<String, Integer> actionCooldowns;
    private int aggressionLevel;
    private int tacticalAwareness;
    private int groupCoordination;
    private boolean isLeader;
    private List<Unit> squadMembers;
    private Map<String, Object> memory;
    private int decisionPoints;
    private Random random;
    
    public AlienAI(AlienType alienType) {
        this.alienType = alienType;
        this.aiId = "AI_" + alienType.name() + "_" + System.currentTimeMillis();
        this.behaviorWeights = new HashMap<>();
        this.availableActions = new ArrayList<>();
        this.actionCooldowns = new HashMap<>();
        this.squadMembers = new ArrayList<>();
        this.memory = new HashMap<>();
        this.random = new Random();
        
        initializeAI();
    }
    
    private void initializeAI() {
        // Set behavior based on alien type
        switch (alienType) {
            case ADVENT_TROOPER:
                primaryBehavior = AIBehaviorType.AGGRESSIVE;
                secondaryBehavior = AIBehaviorType.SUPPORT;
                aggressionLevel = 70;
                tacticalAwareness = 60;
                groupCoordination = 80;
                break;
            case ADVENT_OFFICER:
                primaryBehavior = AIBehaviorType.TACTICAL;
                secondaryBehavior = AIBehaviorType.LEADERSHIP;
                aggressionLevel = 50;
                tacticalAwareness = 90;
                groupCoordination = 95;
                isLeader = true;
                break;
            case ADVENT_BERSERKER:
                primaryBehavior = AIBehaviorType.BERSERKER;
                secondaryBehavior = AIBehaviorType.CHARGE;
                aggressionLevel = 100;
                tacticalAwareness = 30;
                groupCoordination = 20;
                break;
            case ADVENT_ARCHON:
                primaryBehavior = AIBehaviorType.MOBILITY;
                secondaryBehavior = AIBehaviorType.AIR_SUPERIORITY;
                aggressionLevel = 80;
                tacticalAwareness = 85;
                groupCoordination = 70;
                break;
            default:
                primaryBehavior = AIBehaviorType.STANDARD;
                secondaryBehavior = AIBehaviorType.SUPPORT;
                aggressionLevel = 60;
                tacticalAwareness = 70;
                groupCoordination = 60;
        }
        
        // Initialize behavior weights
        behaviorWeights.put("ATTACK", 40);
        behaviorWeights.put("FLANK", 20);
        behaviorWeights.put("SUPPORT", 15);
        behaviorWeights.put("RETREAT", 10);
        behaviorWeights.put("SPECIAL_ABILITY", 15);
        
        // Initialize available actions
        initializeActions();
    }
    
    private void initializeActions() {
        availableActions.add("MOVE");
        availableActions.add("ATTACK");
        availableActions.add("USE_ABILITY");
        availableActions.add("FLANK");
        availableActions.add("SUPPORT");
        availableActions.add("RETREAT");
        availableActions.add("OVERWATCH");
        availableActions.add("COORDINATE");
        
        // Set cooldowns
        actionCooldowns.put("USE_ABILITY", 3);
        actionCooldowns.put("SPECIAL_ATTACK", 2);
        actionCooldowns.put("COORDINATE", 1);
    }
    
    /**
     * Make AI decision for alien unit
     */
    public AIAction makeDecision(Unit alien, List<Unit> enemies, List<Unit> allies) {
        AIAction action = new AIAction();
        
        // Calculate decision based on behavior and situation
        String decision = calculateDecision(alien, enemies, allies);
        
        switch (decision) {
            case "ATTACK":
                action.setActionType("ATTACK");
                action.setTarget(findBestTarget(alien, enemies));
                action.setPriority(calculateAttackPriority(alien, action.getTarget()));
                break;
            case "FLANK":
                action.setActionType("FLANK");
                action.setTarget(findBestTarget(alien, enemies));
                action.setPosition(findFlankingPosition(alien, action.getTarget()));
                action.setPriority(70);
                break;
            case "SUPPORT":
                action.setActionType("SUPPORT");
                action.setTarget(findBestAlly(alien, allies));
                action.setPriority(60);
                break;
            case "RETREAT":
                action.setActionType("RETREAT");
                action.setPosition(findRetreatPosition(alien, enemies));
                action.setPriority(40);
                break;
            case "USE_ABILITY":
                action.setActionType("USE_ABILITY");
                action.setAbility(selectBestAbility(alien));
                action.setTarget(findBestTarget(alien, enemies));
                action.setPriority(80);
                break;
            default:
                action.setActionType("MOVE");
                action.setPosition(findBestPosition(alien, enemies, allies));
                action.setPriority(50);
        }
        
        return action;
    }
    
    private String calculateDecision(Unit alien, List<Unit> enemies, List<Unit> allies) {
        int healthPercent = (alien.getHealth() * 100) / alien.getMaxHealth();
        int enemyCount = enemies.size();
        int allyCount = allies.size();
        
        // High aggression behavior
        if (aggressionLevel > 80 && healthPercent > 30) {
            return "ATTACK";
        }
        
        // Low health retreat
        if (healthPercent < 25) {
            return "RETREAT";
        }
        
        // Support behavior
        if (allyCount > enemyCount && isLeader) {
            return "SUPPORT";
        }
        
        // Flanking opportunity
        if (canFlank(alien, enemies)) {
            return "FLANK";
        }
        
        // Use special abilities
        if (hasAvailableAbility(alien) && random.nextInt(100) < 30) {
            return "USE_ABILITY";
        }
        
        // Default to attack
        return "ATTACK";
    }
    
    private Unit findBestTarget(Unit alien, List<Unit> enemies) {
        if (enemies.isEmpty()) return null;
        
        return enemies.stream()
                .filter(enemy -> enemy.getHealth() > 0)
                .min(Comparator.comparingInt(enemy -> 
                    alien.getPosition().getDistanceTo(enemy.getPosition())))
                .orElse(enemies.get(0));
    }
    
    private Unit findBestAlly(Unit alien, List<Unit> allies) {
        if (allies.isEmpty()) return null;
        
        return allies.stream()
                .filter(ally -> ally.getHealth() < ally.getMaxHealth())
                .min(Comparator.comparingInt(ally -> ally.getHealth()))
                .orElse(allies.get(0));
    }
    
    private Position findFlankingPosition(Unit alien, Unit target) {
        // Simple flanking position calculation
        Position targetPos = target.getPosition();
        Position alienPos = alien.getPosition();
        
        int dx = targetPos.getX() - alienPos.getX();
        int dy = targetPos.getY() - alienPos.getY();
        
        return new Position(targetPos.getX() + dy, targetPos.getY() - dx);
    }
    
    private Position findRetreatPosition(Unit alien, List<Unit> enemies) {
        // Find position away from enemies
        Position currentPos = alien.getPosition();
        Position retreatPos = new Position(currentPos.getX(), currentPos.getY());
        
        // Simple retreat logic - move away from nearest enemy
        if (!enemies.isEmpty()) {
            Unit nearestEnemy = findBestTarget(alien, enemies);
            Position enemyPos = nearestEnemy.getPosition();
            
            int dx = currentPos.getX() - enemyPos.getX();
            int dy = currentPos.getY() - enemyPos.getY();
            
            retreatPos.setX(currentPos.getX() + Integer.signum(dx));
            retreatPos.setY(currentPos.getY() + Integer.signum(dy));
        }
        
        return retreatPos;
    }
    
    private Position findBestPosition(Unit alien, List<Unit> enemies, List<Unit> allies) {
        // Find position that provides good cover and tactical advantage
        Position currentPos = alien.getPosition();
        return new Position(currentPos.getX() + random.nextInt(3) - 1, 
                          currentPos.getY() + random.nextInt(3) - 1);
    }
    
    private String selectBestAbility(Unit alien) {
        // Select best ability based on alien type and situation
        switch (alienType) {
            case ADVENT_OFFICER:
                return "MARK_TARGET";
            case ADVENT_BERSERKER:
                return "RAGE";
            case ADVENT_ARCHON:
                return "BLAZING_PINIONS";
            default:
                return "STANDARD_ATTACK";
        }
    }
    
    private boolean canFlank(Unit alien, List<Unit> enemies) {
        // Check if flanking is possible
        return enemies.stream().anyMatch(enemy -> 
            alien.getPosition().getDistanceTo(enemy.getPosition()) <= 3);
    }
    
    private boolean hasAvailableAbility(Unit alien) {
        // Check if alien has available abilities
        return !actionCooldowns.isEmpty();
    }
    
    private int calculateAttackPriority(Unit alien, Unit target) {
        if (target == null) return 0;
        
        int distance = alien.getPosition().getDistanceTo(target.getPosition());
        int targetHealth = target.getHealth();
        
        // Higher priority for closer, weaker targets
        return 100 - distance * 10 - targetHealth / 10;
    }
    
    /**
     * Update AI memory with combat events
     */
    public void updateMemory(String event, Object data) {
        memory.put(event, data);
    }
    
    /**
     * Get AI behavior description
     */
    public String getBehaviorDescription() {
        return String.format("AI %s: %s/%s (Aggression: %d, Awareness: %d, Coordination: %d)",
                aiId, primaryBehavior, secondaryBehavior, aggressionLevel, tacticalAwareness, groupCoordination);
    }
}


