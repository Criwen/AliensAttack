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
        
        // ToDo: Реализовать полную систему телепортации
        // - Teleport target selection
        // - Teleport range limitations
        // - Teleport cooldown mechanics
        // - Teleport visual effects
        // - Teleport sound effects
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
        
        // ToDo: Реализовать полную систему контроля разума
        // - Mind control mechanics with resistance
        // - Controlled unit actions
        // - Mind control duration and break chances
        // - Psionic feedback system
        // - Mind control countermeasures
    }
    
    /**
     * Perform acid attack reaction
     */
    private boolean performAcidAttack(Unit target) {
        if (target == null) {
            return false;
        }
        
        // Apply acid burn status effect
        StatusEffectData acidEffect = new StatusEffectData(StatusEffect.ACID_BURN, 3, 5);
        target.addStatusEffect(acidEffect);
        
        // Deal immediate acid damage
        target.takeDamage(8);
        
        reactionCooldowns.put(RulerReactionType.ACID_ATTACK, 2);
        return true;
    }
    
    /**
     * Perform psychic blast reaction
     */
    private boolean performPsychicBlast() {
        // Apply psychic damage to all nearby units
        // This would typically affect all units within range
        // For now, we'll simulate the effect
        
        // Increase ruler's psychic power temporarily
        setPsiStrength(getPsiStrength() + 5);
        
        // Apply psychic damage status effect to self (represents power usage)
        StatusEffectData psychicEffect = new StatusEffectData(StatusEffect.DOMINATED, 1, 3);
        addStatusEffect(psychicEffect);
        
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
        // Create reinforcement positions around the ruler
        List<Position> reinforcementPositions = new ArrayList<>();
        Position currentPos = getPosition();
        
        // Add positions in a 3x3 grid around the ruler
        for (int x = -1; x <= 1; x++) {
            for (int y = -1; y <= 1; y++) {
                if (x != 0 || y != 0) { // Skip the ruler's position
                    Position newPos = new Position(currentPos.getX() + x, currentPos.getY() + y);
                    reinforcementPositions.add(newPos);
                }
            }
        }
        
        // Store reinforcement positions for later use
        this.teleportTargets.addAll(reinforcementPositions);
        
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
        
        // Apply weapon malfunction status effect
        StatusEffectData weaponMalfunction = new StatusEffectData(StatusEffect.WEAPON_MALFUNCTION, 2, 50);
        target.addStatusEffect(weaponMalfunction);
        
        // Reduce target's accuracy temporarily
        target.setAccuracy(Math.max(0, target.getAccuracy() - 30));
        
        reactionCooldowns.put(RulerReactionType.DISABLE_WEAPONS, 3);
        return true;
    }
    
    /**
     * Perform poison cloud reaction
     */
    private boolean performPoisonCloud() {
        // Apply poison status effect to all nearby units
        // This would typically affect all units within range
        // For now, we'll simulate the effect by applying to self
        
        // Apply poison status effect
        StatusEffectData poisonEffect = new StatusEffectData(StatusEffect.POISONED, 3, 4);
        addStatusEffect(poisonEffect);
        
        // Create poison cloud area effect (simulated)
        // In a real implementation, this would affect all units in range
        
        reactionCooldowns.put(RulerReactionType.POISON_CLOUD, 4);
        return true;
    }
    
    /**
     * Perform mind scream reaction
     */
    private boolean performMindScream() {
        // Apply panic status effect to all nearby units
        // This would typically affect all units within range
        // For now, we'll simulate the effect
        
        // Apply panic status effect to self (simulating area effect)
        StatusEffectData panicEffect = new StatusEffectData(StatusEffect.PANICKED, 2, 75);
        addStatusEffect(panicEffect);
        
        // Increase psychic power temporarily
        setPsiStrength(getPsiStrength() + 3);
        
        reactionCooldowns.put(RulerReactionType.MIND_SCREAM, 5);
        return true;
    }
    
    /**
     * Perform dimensional rift reaction
     */
    private boolean performDimensionalRift() {
        // Create dimensional rift effect - teleport to random position
        Position currentPos = getPosition();
        int newX = currentPos.getX() + (new Random().nextInt(6) - 3); // -3 to +3 range
        int newY = currentPos.getY() + (new Random().nextInt(6) - 3);
        Position newPos = new Position(newX, newY);
        
        setPosition(newPos);
        isTeleporting = true;
        
        // Apply dimensional instability status effect
        StatusEffectData riftEffect = new StatusEffectData(StatusEffect.STUNNED, 1, 1);
        addStatusEffect(riftEffect);
        
        reactionCooldowns.put(RulerReactionType.DIMENSIONAL_RIFT, 7);
        return true;
    }
    
    /**
     * Perform time slow reaction
     */
    private boolean performTimeSlow() {
        // Apply time slow effect to all nearby units
        // This would typically affect all units within range
        // For now, we'll simulate the effect
        
        // Apply time slow status effect to self (simulating area effect)
        StatusEffectData timeSlowEffect = new StatusEffectData(StatusEffect.FROZEN, 2, 50);
        addStatusEffect(timeSlowEffect);
        
        // Increase action points temporarily (time manipulation)
        setActionPoints((int)(getActionPoints() + 1));
        
        reactionCooldowns.put(RulerReactionType.TIME_SLOW, 6);
        return true;
    }
    
    /**
     * Perform healing aura reaction
     */
    private boolean performHealingAura() {
        // Apply healing status effect to self and nearby units
        // This would typically affect all units within range
        // For now, we'll simulate the effect
        
        // Apply healing status effect
        StatusEffectData healingEffect = new StatusEffectData(StatusEffect.HEALING, 2, 10);
        addStatusEffect(healingEffect);
        
        // Restore health
        setCurrentHealth(Math.min(getMaxHealth(), getCurrentHealth() + 15));
        
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
        
        // Apply armor degradation status effect
        StatusEffectData armorBreakEffect = new StatusEffectData(StatusEffect.ARMOR_DEGRADATION, 3, 30);
        target.addStatusEffect(armorBreakEffect);
        
        // Reduce target's defense temporarily
        target.setDefense(Math.max(0, target.getDefense() - 20));
        
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
        
        // Apply weapon malfunction status effect with higher intensity
        StatusEffectData weaponDestroyEffect = new StatusEffectData(StatusEffect.WEAPON_MALFUNCTION, 4, 80);
        target.addStatusEffect(weaponDestroyEffect);
        
        // Completely disable target's weapon temporarily
        target.setAccuracy(0);
        
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
        
        // Apply stunned status effect to prevent movement
        StatusEffectData moveInterruptEffect = new StatusEffectData(StatusEffect.STUNNED, 1, 1);
        target.addStatusEffect(moveInterruptEffect);
        
        // Reduce target's action points
        target.setActionPoints(Math.max(0, (int)(target.getActionPoints() - 1.0)));
        
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
        
        // Apply suppressed status effect to prevent attacks
        StatusEffectData attackInterruptEffect = new StatusEffectData(StatusEffect.SUPPRESSED, 2, 1);
        target.addStatusEffect(attackInterruptEffect);
        
        // Reduce target's accuracy temporarily
        target.setAccuracy(Math.max(0, target.getAccuracy() - 40));
        
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
        
        // Apply ability block status effect
        StatusEffectData abilityBlockEffect = new StatusEffectData(StatusEffect.STUNNED, 2, 1);
        target.addStatusEffect(abilityBlockEffect);
        
        // Disable target's abilities temporarily
        // This would typically disable all abilities for the duration
        
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
        
        // Apply vision block status effect
        StatusEffectData visionBlockEffect = new StatusEffectData(StatusEffect.FLASHBANGED, 2, 1);
        target.addStatusEffect(visionBlockEffect);
        
        // Reduce target's view range temporarily
        target.setViewRange(Math.max(1, target.getViewRange() - 3));
        
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
