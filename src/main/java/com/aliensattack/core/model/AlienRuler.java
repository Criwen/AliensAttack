package com.aliensattack.core.model;

import com.aliensattack.core.enums.*;
import lombok.Getter;
import lombok.Setter;

import java.util.*;

/**
 * Alien Ruler class representing advanced alien enemies with unique abilities
 */
@Getter
@Setter
public class AlienRuler extends Unit {
    
    private String rulerName;
    private AlienRulerType rulerType;
    private List<RulerReactionType> availableReactions;
    private Map<RulerReactionType, Integer> reactionCooldowns;
    private int healthBars;
    private int currentHealthBar;
    private int regenerationRate;
    private int escalationLevel;
    private boolean isEnraged;
    private boolean isTeleporting;
    private boolean isShielded;
    private boolean isRegenerating;
    private List<Position> teleportTargets;
    private Map<String, Integer> abilityUses;
    private int learningLevel;
    private List<String> learnedTactics;
    private Map<String, Integer> tacticCounters;
    
    public AlienRuler(String name, String rulerName, AlienRulerType rulerType, int health, int actionPoints, 
                      int movementRange, int attackRange, UnitType unitType) {
        super(name, health, actionPoints, movementRange, attackRange, unitType);
        this.rulerName = rulerName;
        this.rulerType = rulerType;
        this.availableReactions = new ArrayList<>();
        this.reactionCooldowns = new HashMap<>();
        this.healthBars = 3;
        this.currentHealthBar = 1;
        this.regenerationRate = 5;
        this.escalationLevel = 1;
        this.isEnraged = false;
        this.isTeleporting = false;
        this.isShielded = false;
        this.isRegenerating = false;
        this.teleportTargets = new ArrayList<>();
        this.abilityUses = new HashMap<>();
        this.learningLevel = 1;
        this.learnedTactics = new ArrayList<>();
        this.tacticCounters = new HashMap<>();
        
        initializeRulerAbilities();
    }
    
    /**
     * Initialize ruler-specific abilities based on type
     */
    private void initializeRulerAbilities() {
        switch (rulerType) {
            case ARCHON_KING:
                availableReactions.addAll(Arrays.asList(
                    RulerReactionType.TELEPORT,
                    RulerReactionType.PSYCHIC_BLAST,
                    RulerReactionType.REGENERATION,
                    RulerReactionType.SHIELD_UP,
                    RulerReactionType.RAGE_MODE
                ));
                break;
            case BERSERKER_QUEEN:
                availableReactions.addAll(Arrays.asList(
                    RulerReactionType.RAGE_MODE,
                    RulerReactionType.ACID_ATTACK,
                    RulerReactionType.REGENERATION,
                    RulerReactionType.SUMMON_REINFORCEMENTS,
                    RulerReactionType.MOVE_INTERRUPT
                ));
                break;
            case VIPER_KING:
                availableReactions.addAll(Arrays.asList(
                    RulerReactionType.POISON_CLOUD,
                    RulerReactionType.MIND_CONTROL,
                    RulerReactionType.REGENERATION,
                    RulerReactionType.TELEPORT,
                    RulerReactionType.ATTACK_INTERRUPT
                ));
                break;
        }
        
        // Initialize cooldowns
        for (RulerReactionType reaction : availableReactions) {
            reactionCooldowns.put(reaction, 0);
        }
    }
    
    /**
     * React to player action with ruler ability
     */
    public boolean reactToPlayerAction(RulerReactionType reactionType, Unit target) {
        if (!availableReactions.contains(reactionType) || reactionCooldowns.get(reactionType) > 0) {
            return false;
        }
        
        switch (reactionType) {
            case TELEPORT:
                return performTeleport();
            case MIND_CONTROL:
                return performMindControl(target);
            case ACID_ATTACK:
                return performAcidAttack(target);
            case PSYCHIC_BLAST:
                return performPsychicBlast();
            case REGENERATION:
                return performRegeneration();
            case SHIELD_UP:
                return performShieldUp();
            case RAGE_MODE:
                return performRageMode();
            case SUMMON_REINFORCEMENTS:
                return performSummonReinforcements();
            case DISABLE_WEAPONS:
                return performDisableWeapons(target);
            case POISON_CLOUD:
                return performPoisonCloud();
            case MIND_SCREAM:
                return performMindScream();
            case DIMENSIONAL_RIFT:
                return performDimensionalRift();
            case TIME_SLOW:
                return performTimeSlow();
            case HEALING_AURA:
                return performHealingAura();
            case ARMOR_BREAK:
                return performArmorBreak(target);
            case WEAPON_DESTROY:
                return performWeaponDestroy(target);
            case MOVE_INTERRUPT:
                return performMoveInterrupt(target);
            case ATTACK_INTERRUPT:
                return performAttackInterrupt(target);
            case ABILITY_BLOCK:
                return performAbilityBlock(target);
            case VISION_BLOCK:
                return performVisionBlock(target);
            default:
                return false;
        }
    }
    
    /**
     * Perform teleport reaction
     */
    private boolean performTeleport() {
        if (teleportTargets.isEmpty()) {
            return false;
        }
        
        Position targetPos = teleportTargets.get(0);
        setPosition(targetPos);
        isTeleporting = true;
        reactionCooldowns.put(RulerReactionType.TELEPORT, 3);
        return true;
    }
    
    /**
     * Perform mind control reaction
     */
    private boolean performMindControl(Unit target) {
        if (target == null || target.getUnitType() != UnitType.SOLDIER) {
            return false;
        }
        
        // Mind control logic would be implemented here
        reactionCooldowns.put(RulerReactionType.MIND_CONTROL, 5);
        return true;
    }
    
    /**
     * Perform acid attack reaction
     */
    private boolean performAcidAttack(Unit target) {
        if (target == null) {
            return false;
        }
        
        // Acid attack logic would be implemented here
        reactionCooldowns.put(RulerReactionType.ACID_ATTACK, 2);
        return true;
    }
    
    /**
     * Perform psychic blast reaction
     */
    private boolean performPsychicBlast() {
        // Psychic blast logic would be implemented here
        reactionCooldowns.put(RulerReactionType.PSYCHIC_BLAST, 4);
        return true;
    }
    
    /**
     * Perform regeneration reaction
     */
    private boolean performRegeneration() {
        if (getCurrentHealth() < getMaxHealth()) {
            setCurrentHealth(Math.min(getMaxHealth(), getCurrentHealth() + regenerationRate));
            isRegenerating = true;
        }
        reactionCooldowns.put(RulerReactionType.REGENERATION, 3);
        return true;
    }
    
    /**
     * Perform shield up reaction
     */
    private boolean performShieldUp() {
        isShielded = true;
        reactionCooldowns.put(RulerReactionType.SHIELD_UP, 4);
        return true;
    }
    
    /**
     * Perform rage mode reaction
     */
    private boolean performRageMode() {
        isEnraged = true;
        // Increase damage and movement
        reactionCooldowns.put(RulerReactionType.RAGE_MODE, 6);
        return true;
    }
    
    /**
     * Perform summon reinforcements reaction
     */
    private boolean performSummonReinforcements() {
        // Summon reinforcements logic would be implemented here
        reactionCooldowns.put(RulerReactionType.SUMMON_REINFORCEMENTS, 8);
        return true;
    }
    
    /**
     * Perform disable weapons reaction
     */
    private boolean performDisableWeapons(Unit target) {
        if (target == null) {
            return false;
        }
        
        // Disable weapons logic would be implemented here
        reactionCooldowns.put(RulerReactionType.DISABLE_WEAPONS, 3);
        return true;
    }
    
    /**
     * Perform poison cloud reaction
     */
    private boolean performPoisonCloud() {
        // Poison cloud logic would be implemented here
        reactionCooldowns.put(RulerReactionType.POISON_CLOUD, 4);
        return true;
    }
    
    /**
     * Perform mind scream reaction
     */
    private boolean performMindScream() {
        // Mind scream logic would be implemented here
        reactionCooldowns.put(RulerReactionType.MIND_SCREAM, 5);
        return true;
    }
    
    /**
     * Perform dimensional rift reaction
     */
    private boolean performDimensionalRift() {
        // Dimensional rift logic would be implemented here
        reactionCooldowns.put(RulerReactionType.DIMENSIONAL_RIFT, 7);
        return true;
    }
    
    /**
     * Perform time slow reaction
     */
    private boolean performTimeSlow() {
        // Time slow logic would be implemented here
        reactionCooldowns.put(RulerReactionType.TIME_SLOW, 6);
        return true;
    }
    
    /**
     * Perform healing aura reaction
     */
    private boolean performHealingAura() {
        // Healing aura logic would be implemented here
        reactionCooldowns.put(RulerReactionType.HEALING_AURA, 4);
        return true;
    }
    
    /**
     * Perform armor break reaction
     */
    private boolean performArmorBreak(Unit target) {
        if (target == null) {
            return false;
        }
        
        // Armor break logic would be implemented here
        reactionCooldowns.put(RulerReactionType.ARMOR_BREAK, 3);
        return true;
    }
    
    /**
     * Perform weapon destroy reaction
     */
    private boolean performWeaponDestroy(Unit target) {
        if (target == null) {
            return false;
        }
        
        // Weapon destroy logic would be implemented here
        reactionCooldowns.put(RulerReactionType.WEAPON_DESTROY, 5);
        return true;
    }
    
    /**
     * Perform move interrupt reaction
     */
    private boolean performMoveInterrupt(Unit target) {
        if (target == null) {
            return false;
        }
        
        // Move interrupt logic would be implemented here
        reactionCooldowns.put(RulerReactionType.MOVE_INTERRUPT, 2);
        return true;
    }
    
    /**
     * Perform attack interrupt reaction
     */
    private boolean performAttackInterrupt(Unit target) {
        if (target == null) {
            return false;
        }
        
        // Attack interrupt logic would be implemented here
        reactionCooldowns.put(RulerReactionType.ATTACK_INTERRUPT, 2);
        return true;
    }
    
    /**
     * Perform ability block reaction
     */
    private boolean performAbilityBlock(Unit target) {
        if (target == null) {
            return false;
        }
        
        // Ability block logic would be implemented here
        reactionCooldowns.put(RulerReactionType.ABILITY_BLOCK, 4);
        return true;
    }
    
    /**
     * Perform vision block reaction
     */
    private boolean performVisionBlock(Unit target) {
        if (target == null) {
            return false;
        }
        
        // Vision block logic would be implemented here
        reactionCooldowns.put(RulerReactionType.VISION_BLOCK, 3);
        return true;
    }
    
    /**
     * Learn from player tactics
     */
    public void learnFromTactic(String tactic) {
        if (!learnedTactics.contains(tactic)) {
            learnedTactics.add(tactic);
            tacticCounters.put(tactic, 1);
        } else {
            tacticCounters.put(tactic, tacticCounters.get(tactic) + 1);
        }
        
        // Increase learning level based on tactics learned
        if (learnedTactics.size() >= learningLevel * 5) {
            learningLevel++;
        }
    }
    
    /**
     * Escalate ruler abilities
     */
    public void escalate() {
        escalationLevel++;
        regenerationRate += 2;
        
        // Unlock new reactions based on escalation level
        if (escalationLevel >= 3 && !availableReactions.contains(RulerReactionType.DIMENSIONAL_RIFT)) {
            availableReactions.add(RulerReactionType.DIMENSIONAL_RIFT);
        }
        
        if (escalationLevel >= 5 && !availableReactions.contains(RulerReactionType.TIME_SLOW)) {
            availableReactions.add(RulerReactionType.TIME_SLOW);
        }
    }
    
    /**
     * Take damage and handle health bars
     */
    @Override
    public boolean takeDamage(int damage) {
        boolean killed = super.takeDamage(damage);
        
        // Check if health bar is depleted
        if (getCurrentHealth() <= 0 && currentHealthBar < healthBars) {
            currentHealthBar++;
            setCurrentHealth(getMaxHealth()); // Refill health for next bar
            killed = false; // Not killed since we have more health bars
        }
        
        return killed;
    }
    
    /**
     * Update cooldowns
     */
    public void updateCooldowns() {
        for (RulerReactionType reaction : reactionCooldowns.keySet()) {
            if (reactionCooldowns.get(reaction) > 0) {
                reactionCooldowns.put(reaction, reactionCooldowns.get(reaction) - 1);
            }
        }
    }
    
    /**
     * Check if ruler can react
     */
    public boolean canReact(RulerReactionType reactionType) {
        return availableReactions.contains(reactionType) && reactionCooldowns.get(reactionType) <= 0;
    }
    
    /**
     * Get available reactions
     */
    public List<RulerReactionType> getAvailableReactions() {
        return new ArrayList<>(availableReactions);
    }
    
    /**
     * Get reaction cooldowns
     */
    public Map<RulerReactionType, Integer> getReactionCooldowns() {
        return new HashMap<>(reactionCooldowns);
    }
    
    /**
     * Add teleport target
     */
    public void addTeleportTarget(Position position) {
        teleportTargets.add(position);
    }
    
    /**
     * Clear teleport targets
     */
    public void clearTeleportTargets() {
        teleportTargets.clear();
    }
    
    /**
     * Get ruler status
     */
    public String getRulerStatus() {
        return String.format("Ruler: %s, Health: %d/%d, Bars: %d/%d, Level: %d", 
                           rulerName, getCurrentHealth(), getMaxHealth(), currentHealthBar, healthBars, escalationLevel);
    }
}
