package com.aliensattack.core.systems;

import com.aliensattack.core.enums.RulerReactionType;
import com.aliensattack.core.model.Unit;
import com.aliensattack.core.model.Position;
import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.*;

/**
 * Alien Ruler Reaction System - XCOM 2 Mechanic
 * Implements the system where alien rulers react to player actions with special abilities
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class AlienRulerReactionSystem {
    
    private String rulerId;
    private String rulerName;
    private RulerReactionType primaryReactionType;
    private List<RulerReactionType> availableReactions;
    private Map<String, Integer> reactionCooldowns;
    private Map<String, Integer> reactionSuccessCount;
    private Map<String, Integer> reactionFailureCount;
    private boolean isEnraged;
    private int enrageLevel;
    private int maxEnrageLevel;
    private List<String> learnedPlayerTactics;
    private Map<String, Integer> tacticCounter;
    private boolean isAdapting;
    private int adaptationLevel;
    
    public AlienRulerReactionSystem(String rulerId, String rulerName) {
        this.rulerId = rulerId;
        this.rulerName = rulerName;
        this.availableReactions = new ArrayList<>();
        this.reactionCooldowns = new HashMap<>();
        this.reactionSuccessCount = new HashMap<>();
        this.reactionFailureCount = new HashMap<>();
        this.learnedPlayerTactics = new ArrayList<>();
        this.tacticCounter = new HashMap<>();
        this.isEnraged = false;
        this.enrageLevel = 0;
        this.maxEnrageLevel = 5;
        this.isAdapting = false;
        this.adaptationLevel = 0;
        
        initializeReactions();
    }
    
    /**
     * Initialize available ruler reactions
     */
    private void initializeReactions() {
        availableReactions.addAll(Arrays.asList(
            RulerReactionType.TELEPORT,
            RulerReactionType.MIND_CONTROL,
            RulerReactionType.ACID_ATTACK,
            RulerReactionType.PSYCHIC_BLAST,
            RulerReactionType.REGENERATION,
            RulerReactionType.SHIELD_UP,
            RulerReactionType.SUMMON_REINFORCEMENTS,
            RulerReactionType.RAGE_MODE,
            RulerReactionType.DISABLE_WEAPONS,
            RulerReactionType.POISON_CLOUD
        ));
        
        // Set initial cooldowns
        for (RulerReactionType reaction : availableReactions) {
            reactionCooldowns.put(reaction.name(), 0);
            reactionSuccessCount.put(reaction.name(), 0);
            reactionFailureCount.put(reaction.name(), 0);
        }
    }
    
    /**
     * React to player action with appropriate ruler ability
     */
    public RulerReactionType reactToPlayerAction(String playerAction, Unit targetUnit) {
        if (isReactionAvailable()) {
            RulerReactionType chosenReaction = chooseOptimalReaction(playerAction, targetUnit);
            if (chosenReaction != null) {
                executeReaction(chosenReaction, targetUnit);
                return chosenReaction;
            }
        }
        return null;
    }
    
    /**
     * Check if any reaction is available
     */
    public boolean isReactionAvailable() {
        for (RulerReactionType reaction : availableReactions) {
            if (reactionCooldowns.get(reaction.name()) <= 0) {
                return true;
            }
        }
        return false;
    }
    
    /**
     * Choose optimal reaction based on player action and target
     */
    public RulerReactionType chooseOptimalReaction(String playerAction, Unit targetUnit) {
        List<RulerReactionType> availableReactions = getAvailableReactions();
        
        if (availableReactions.isEmpty()) {
            return null;
        }
        
        // Choose reaction based on player action type
        switch (playerAction.toLowerCase()) {
            case "attack":
                return chooseAttackReaction(targetUnit);
            case "move":
                return chooseMovementReaction(targetUnit);
            case "overwatch":
                return chooseOverwatchReaction(targetUnit);
            case "grenade":
                return chooseGrenadeReaction(targetUnit);
            case "psionic":
                return choosePsionicReaction(targetUnit);
            default:
                return availableReactions.get(new Random().nextInt(availableReactions.size()));
        }
    }
    
    /**
     * Choose reaction for attack actions
     */
    private RulerReactionType chooseAttackReaction(Unit targetUnit) {
        if (isEnraged && enrageLevel >= 3) {
            return RulerReactionType.RAGE_MODE;
        } else if (targetUnit.getCurrentHealth() < 50) {
            return RulerReactionType.MIND_CONTROL;
        } else {
            return RulerReactionType.SHIELD_UP;
        }
    }
    
    /**
     * Choose reaction for movement actions
     */
    private RulerReactionType chooseMovementReaction(Unit targetUnit) {
        if (targetUnit.getPosition().getDistanceTo(getRulerPosition()) > 5) {
            return RulerReactionType.TELEPORT;
        } else {
            return RulerReactionType.ACID_ATTACK;
        }
    }
    
    /**
     * Choose reaction for overwatch actions
     */
    private RulerReactionType chooseOverwatchReaction(Unit targetUnit) {
        return RulerReactionType.PSYCHIC_BLAST;
    }
    
    /**
     * Choose reaction for grenade actions
     */
    private RulerReactionType chooseGrenadeReaction(Unit targetUnit) {
        return RulerReactionType.REGENERATION;
    }
    
    /**
     * Choose reaction for psionic actions
     */
    private RulerReactionType choosePsionicReaction(Unit targetUnit) {
        return RulerReactionType.MIND_SCREAM;
    }
    
    /**
     * Get available reactions (not on cooldown)
     */
    public List<RulerReactionType> getAvailableReactions() {
        List<RulerReactionType> available = new ArrayList<>();
        for (RulerReactionType reaction : availableReactions) {
            if (reactionCooldowns.get(reaction.name()) <= 0) {
                available.add(reaction);
            }
        }
        return available;
    }
    
    /**
     * Execute the chosen reaction
     */
    public void executeReaction(RulerReactionType reaction, Unit targetUnit) {
        // Set cooldown for the reaction
        int cooldown = getReactionCooldown(reaction);
        reactionCooldowns.put(reaction.name(), cooldown);
        
        // Apply reaction effects
        applyReactionEffects(reaction, targetUnit);
        
        // Learn from this reaction
        learnFromReaction(reaction, targetUnit);
        
        // Update enrage level
        updateEnrageLevel(reaction);
    }
    
    /**
     * Get cooldown for specific reaction
     */
    private int getReactionCooldown(RulerReactionType reaction) {
        switch (reaction) {
            case TELEPORT:
                return 3;
            case MIND_CONTROL:
                return 5;
            case ACID_ATTACK:
                return 2;
            case PSYCHIC_BLAST:
                return 4;
            case REGENERATION:
                return 6;
            case SHIELD_UP:
                return 2;
            case SUMMON_REINFORCEMENTS:
                return 8;
            case RAGE_MODE:
                return 4;
            case DISABLE_WEAPONS:
                return 3;
            case POISON_CLOUD:
                return 5;
            default:
                return 3;
        }
    }
    
    /**
     * Apply effects of the reaction
     */
    private void applyReactionEffects(RulerReactionType reaction, Unit targetUnit) {
        switch (reaction) {
            case TELEPORT:
                // Teleport to new position
                break;
            case MIND_CONTROL:
                // Take control of target unit
                break;
            case ACID_ATTACK:
                // Deal acid damage over time
                break;
            case PSYCHIC_BLAST:
                // Deal psychic damage in area
                break;
            case REGENERATION:
                // Regenerate health
                break;
            case SHIELD_UP:
                // Increase defense
                break;
            case SUMMON_REINFORCEMENTS:
                // Call additional aliens
                break;
            case RAGE_MODE:
                // Increase damage and attack speed
                break;
            case DISABLE_WEAPONS:
                // Disable player weapons temporarily
                break;
            case POISON_CLOUD:
                // Create poisonous area effect
                break;
            case MIND_SCREAM:
                // Stun and disorient player units
                break;
            case DIMENSIONAL_RIFT:
                // Create dangerous dimensional portals
                break;
            case TIME_SLOW:
                // Slow down player turn time
                break;
            case HEALING_AURA:
                // Heal nearby alien units
                break;
            case ARMOR_BREAK:
                // Reduce player armor effectiveness
                break;
            case WEAPON_DESTROY:
                // Destroy player weapons
                break;
            case MOVE_INTERRUPT:
                // Interrupt player movement
                break;
            case ATTACK_INTERRUPT:
                // Interrupt player attacks
                break;
            case ABILITY_BLOCK:
                // Block player abilities
                break;
            case VISION_BLOCK:
                // Block player vision temporarily
                break;
        }
    }
    
    /**
     * Learn from the reaction outcome
     */
    private void learnFromReaction(RulerReactionType reaction, Unit targetUnit) {
        String tactic = reaction.name() + "_vs_" + targetUnit.getUnitType();
        tacticCounter.put(tactic, tacticCounter.getOrDefault(tactic, 0) + 1);
        
        if (!learnedPlayerTactics.contains(tactic)) {
            learnedPlayerTactics.add(tactic);
        }
    }
    
    /**
     * Update enrage level based on reaction
     */
    private void updateEnrageLevel(RulerReactionType reaction) {
        if (reaction == RulerReactionType.RAGE_MODE) {
            enrageLevel = Math.min(enrageLevel + 1, maxEnrageLevel);
            isEnraged = enrageLevel >= 2;
        } else if (reaction == RulerReactionType.TELEPORT) {
            enrageLevel = Math.max(enrageLevel - 1, 0);
            isEnraged = enrageLevel >= 2;
        }
    }
    
    /**
     * Adapt to player tactics
     */
    public void adaptToPlayerTactics(String playerTactic) {
        if (!learnedPlayerTactics.contains(playerTactic)) {
            learnedPlayerTactics.add(playerTactic);
        }
        
        tacticCounter.put(playerTactic, tacticCounter.getOrDefault(playerTactic, 0) + 1);
        
        if (tacticCounter.get(playerTactic) >= 3) {
            isAdapting = true;
            adaptationLevel++;
        }
    }
    
    /**
     * Get ruler position (placeholder)
     */
    private Position getRulerPosition() {
        // This would be implemented based on actual ruler position
        return new Position(0, 0);
    }
    
    /**
     * Update reaction cooldowns
     */
    public void updateCooldowns() {
        for (RulerReactionType reaction : availableReactions) {
            int currentCooldown = reactionCooldowns.get(reaction.name());
            if (currentCooldown > 0) {
                reactionCooldowns.put(reaction.name(), currentCooldown - 1);
            }
        }
    }
    
    /**
     * Get reaction success rate
     */
    public double getReactionSuccessRate(RulerReactionType reaction) {
        int successes = reactionSuccessCount.getOrDefault(reaction.name(), 0);
        int failures = reactionFailureCount.getOrDefault(reaction.name(), 0);
        int total = successes + failures;
        
        return total > 0 ? (double) successes / total : 0.5;
    }
    
    /**
     * Record reaction success
     */
    public void recordReactionSuccess(RulerReactionType reaction) {
        int current = reactionSuccessCount.getOrDefault(reaction.name(), 0);
        reactionSuccessCount.put(reaction.name(), current + 1);
    }
    
    /**
     * Record reaction failure
     */
    public void recordReactionFailure(RulerReactionType reaction) {
        int current = reactionFailureCount.getOrDefault(reaction.name(), 0);
        reactionFailureCount.put(reaction.name(), current + 1);
    }
    
    /**
     * Get adaptation level
     */
    public int getAdaptationLevel() {
        return adaptationLevel;
    }
    
    /**
     * Check if ruler is adapting
     */
    public boolean isAdapting() {
        return isAdapting;
    }
    
    /**
     * Get enrage level
     */
    public int getEnrageLevel() {
        return enrageLevel;
    }
    
    /**
     * Check if ruler is enraged
     */
    public boolean isEnraged() {
        return isEnraged;
    }
}
