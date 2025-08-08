package com.aliensattack.core.model;

import com.aliensattack.core.enums.*;
import lombok.Getter;
import lombok.Setter;

import java.util.*;

/**
 * Chosen class representing persistent enemy adversaries that learn from encounters
 */
@Getter
@Setter
public class Chosen extends Unit {
    
    private String chosenName;
    private ChosenType chosenType;
    private List<ChosenStrength> strengths;
    private List<ChosenWeakness> weaknesses;
    private int learningLevel;
    private List<String> learnedTactics;
    private Map<String, Integer> tacticCounters;
    private Map<String, Integer> tacticEffectiveness;
    private int territoryControl;
    private int intelGathered;
    private boolean isAdapting;
    private boolean isRetreating;
    private boolean isSummoning;
    private boolean isCorrupting;
    private List<Position> territoryPositions;
    private Map<String, Integer> abilityCooldowns;
    private int knowledgeAbsorption;
    private List<String> absorbedKnowledge;
    private Map<String, Integer> knowledgeEffectiveness;
    private Map<ChosenStrength, Integer> strengthLevels;
    
    public Chosen(String name, String chosenName, ChosenType chosenType, int health, int actionPoints, 
                  int movementRange, int attackRange, UnitType unitType) {
        super(name, health, actionPoints, movementRange, attackRange, unitType);
        this.chosenName = chosenName;
        this.chosenType = chosenType;
        this.strengths = new ArrayList<>();
        this.weaknesses = new ArrayList<>();
        this.learningLevel = 1;
        this.learnedTactics = new ArrayList<>();
        this.tacticCounters = new HashMap<>();
        this.tacticEffectiveness = new HashMap<>();
        this.territoryControl = 0;
        this.intelGathered = 0;
        this.isAdapting = false;
        this.isRetreating = false;
        this.isSummoning = false;
        this.isCorrupting = false;
        this.territoryPositions = new ArrayList<>();
        this.abilityCooldowns = new HashMap<>();
        this.knowledgeAbsorption = 0;
        this.absorbedKnowledge = new ArrayList<>();
        this.knowledgeEffectiveness = new HashMap<>();
        this.strengthLevels = new HashMap<>();
        
        initializeChosenAbilities();
    }
    
    /**
     * Initialize chosen-specific abilities based on type
     */
    private void initializeChosenAbilities() {
        switch (chosenType) {
            case ASSASSIN:
                strengths.addAll(Arrays.asList(
                    ChosenStrength.SHADOW_STEP,
                    ChosenStrength.TELEPORT,
                    ChosenStrength.MIND_CONTROL,
                    ChosenStrength.DEATH_MARK,
                    ChosenStrength.KNOWLEDGE_ABSORPTION
                ));
                weaknesses.addAll(Arrays.asList(
                    ChosenWeakness.RANGED_VULNERABILITY,
                    ChosenWeakness.HEIGHT_VULNERABILITY,
                    ChosenWeakness.ISOLATION_VULNERABILITY
                ));
                break;
            case HUNTER:
                strengths.addAll(Arrays.asList(
                    ChosenStrength.TELEPORT,
                    ChosenStrength.SHIELD,
                    ChosenStrength.REGENERATION,
                    ChosenStrength.SUMMON_REINFORCEMENTS,
                    ChosenStrength.KNOWLEDGE_ABSORPTION
                ));
                weaknesses.addAll(Arrays.asList(
                    ChosenWeakness.MELEE_VULNERABILITY,
                    ChosenWeakness.FLANKING_VULNERABILITY,
                    ChosenWeakness.CROWDING_VULNERABILITY
                ));
                break;
            case WARLOCK:
                strengths.addAll(Arrays.asList(
                    ChosenStrength.PSYCHIC_BLAST,
                    ChosenStrength.MIND_CONTROL,
                    ChosenStrength.SHIELD,
                    ChosenStrength.CORRUPTION,
                    ChosenStrength.KNOWLEDGE_ABSORPTION
                ));
                weaknesses.addAll(Arrays.asList(
                    ChosenWeakness.PSYCHIC_VULNERABILITY,
                    ChosenWeakness.ENERGY_VULNERABILITY,
                    ChosenWeakness.SILENCE_VULNERABILITY
                ));
                break;
        }
        
        // Initialize ability cooldowns
        for (ChosenStrength strength : strengths) {
            abilityCooldowns.put(strength.name(), 0);
            strengthLevels.put(strength, 1);
        }
    }
    
    /**
     * Use chosen strength ability
     */
    public boolean useStrength(ChosenStrength strength, Unit target) {
        if (!strengths.contains(strength) || abilityCooldowns.get(strength.name()) > 0) {
            return false;
        }
        
        switch (strength) {
            case MIND_CONTROL:
                return performMindControl(target);
            case TELEPORT:
                return performTeleport();
            case SHIELD:
                return performShield();
            case REGENERATION:
                return performRegeneration();
            case SUMMON_REINFORCEMENTS:
                return performSummonReinforcements();
            case PSYCHIC_BLAST:
                return performPsychicBlast();
            case ACID_ATTACK:
                return performAcidAttack(target);
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
            case TERROR:
                return performTerror();
            case DOMINATION:
                return performDomination(target);
            case CORRUPTION:
                return performCorruption(target);
            case SHADOW_STEP:
                return performShadowStep();
            case VOID_RIFT:
                return performVoidRift();
            case SOUL_STEAL:
                return performSoulSteal(target);
            case BLOOD_CALL:
                return performBloodCall();
            case DEATH_MARK:
                return performDeathMark(target);
            case ETERNAL_LIFE:
                return performEternalLife();
            case KNOWLEDGE_ABSORPTION:
                return performKnowledgeAbsorption(target);
            default:
                return false;
        }
    }
    
    /**
     * Use chosen ability by name
     */
    public boolean useChosenAbility(String abilityName, Unit target) {
        try {
            ChosenStrength strength = ChosenStrength.valueOf(abilityName.toUpperCase());
            return useStrength(strength, target);
        } catch (IllegalArgumentException e) {
            // Try to find ability by name in learned tactics
            if (learnedTactics.contains(abilityName.toLowerCase())) {
                // Use generic ability
                return true;
            }
            return false;
        }
    }
    
    /**
     * Perform mind control ability
     */
    private boolean performMindControl(Unit target) {
        if (target == null || target.getUnitType() != UnitType.SOLDIER) {
            return false;
        }
        
        // Mind control logic would be implemented here
        abilityCooldowns.put(ChosenStrength.MIND_CONTROL.name(), 5);
        return true;
    }
    
    /**
     * Perform teleport ability
     */
    private boolean performTeleport() {
        // Teleport logic would be implemented here
        abilityCooldowns.put(ChosenStrength.TELEPORT.name(), 3);
        return true;
    }
    
    /**
     * Perform shield ability
     */
    private boolean performShield() {
        // Shield logic would be implemented here
        abilityCooldowns.put(ChosenStrength.SHIELD.name(), 4);
        return true;
    }
    
    /**
     * Perform regeneration ability
     */
    private boolean performRegeneration() {
        if (getCurrentHealth() < getMaxHealth()) {
            setCurrentHealth(Math.min(getMaxHealth(), getCurrentHealth() + 10));
        }
        abilityCooldowns.put(ChosenStrength.REGENERATION.name(), 3);
        return true;
    }
    
    /**
     * Perform summon reinforcements ability
     */
    private boolean performSummonReinforcements() {
        isSummoning = true;
        // Summon reinforcements logic would be implemented here
        abilityCooldowns.put(ChosenStrength.SUMMON_REINFORCEMENTS.name(), 8);
        return true;
    }
    
    /**
     * Perform psychic blast ability
     */
    private boolean performPsychicBlast() {
        // Psychic blast logic would be implemented here
        abilityCooldowns.put(ChosenStrength.PSYCHIC_BLAST.name(), 4);
        return true;
    }
    
    /**
     * Perform acid attack ability
     */
    private boolean performAcidAttack(Unit target) {
        if (target == null) {
            return false;
        }
        
        // Acid attack logic would be implemented here
        abilityCooldowns.put(ChosenStrength.ACID_ATTACK.name(), 2);
        return true;
    }
    
    /**
     * Perform poison cloud ability
     */
    private boolean performPoisonCloud() {
        // Poison cloud logic would be implemented here
        abilityCooldowns.put(ChosenStrength.POISON_CLOUD.name(), 4);
        return true;
    }
    
    /**
     * Perform mind scream ability
     */
    private boolean performMindScream() {
        // Mind scream logic would be implemented here
        abilityCooldowns.put(ChosenStrength.MIND_SCREAM.name(), 5);
        return true;
    }
    
    /**
     * Perform dimensional rift ability
     */
    private boolean performDimensionalRift() {
        // Dimensional rift logic would be implemented here
        abilityCooldowns.put(ChosenStrength.DIMENSIONAL_RIFT.name(), 7);
        return true;
    }
    
    /**
     * Perform time slow ability
     */
    private boolean performTimeSlow() {
        // Time slow logic would be implemented here
        abilityCooldowns.put(ChosenStrength.TIME_SLOW.name(), 6);
        return true;
    }
    
    /**
     * Perform healing aura ability
     */
    private boolean performHealingAura() {
        // Healing aura logic would be implemented here
        abilityCooldowns.put(ChosenStrength.HEALING_AURA.name(), 4);
        return true;
    }
    
    /**
     * Perform armor break ability
     */
    private boolean performArmorBreak(Unit target) {
        if (target == null) {
            return false;
        }
        
        // Armor break logic would be implemented here
        abilityCooldowns.put(ChosenStrength.ARMOR_BREAK.name(), 3);
        return true;
    }
    
    /**
     * Perform weapon destroy ability
     */
    private boolean performWeaponDestroy(Unit target) {
        if (target == null) {
            return false;
        }
        
        // Weapon destroy logic would be implemented here
        abilityCooldowns.put(ChosenStrength.WEAPON_DESTROY.name(), 5);
        return true;
    }
    
    /**
     * Perform move interrupt ability
     */
    private boolean performMoveInterrupt(Unit target) {
        if (target == null) {
            return false;
        }
        
        // Move interrupt logic would be implemented here
        abilityCooldowns.put(ChosenStrength.MOVE_INTERRUPT.name(), 2);
        return true;
    }
    
    /**
     * Perform attack interrupt ability
     */
    private boolean performAttackInterrupt(Unit target) {
        if (target == null) {
            return false;
        }
        
        // Attack interrupt logic would be implemented here
        abilityCooldowns.put(ChosenStrength.ATTACK_INTERRUPT.name(), 2);
        return true;
    }
    
    /**
     * Perform ability block ability
     */
    private boolean performAbilityBlock(Unit target) {
        if (target == null) {
            return false;
        }
        
        // Ability block logic would be implemented here
        abilityCooldowns.put(ChosenStrength.ABILITY_BLOCK.name(), 4);
        return true;
    }
    
    /**
     * Perform vision block ability
     */
    private boolean performVisionBlock(Unit target) {
        if (target == null) {
            return false;
        }
        
        // Vision block logic would be implemented here
        abilityCooldowns.put(ChosenStrength.VISION_BLOCK.name(), 3);
        return true;
    }
    
    /**
     * Perform terror ability
     */
    private boolean performTerror() {
        // Terror logic would be implemented here
        abilityCooldowns.put(ChosenStrength.TERROR.name(), 6);
        return true;
    }
    
    /**
     * Perform domination ability
     */
    private boolean performDomination(Unit target) {
        if (target == null) {
            return false;
        }
        
        // Domination logic would be implemented here
        abilityCooldowns.put(ChosenStrength.DOMINATION.name(), 7);
        return true;
    }
    
    /**
     * Perform corruption ability
     */
    private boolean performCorruption(Unit target) {
        if (target == null) {
            return false;
        }
        
        isCorrupting = true;
        // Corruption logic would be implemented here
        abilityCooldowns.put(ChosenStrength.CORRUPTION.name(), 5);
        return true;
    }
    
    /**
     * Perform shadow step ability
     */
    private boolean performShadowStep() {
        // Shadow step logic would be implemented here
        abilityCooldowns.put(ChosenStrength.SHADOW_STEP.name(), 3);
        return true;
    }
    
    /**
     * Perform void rift ability
     */
    private boolean performVoidRift() {
        // Void rift logic would be implemented here
        abilityCooldowns.put(ChosenStrength.VOID_RIFT.name(), 6);
        return true;
    }
    
    /**
     * Perform soul steal ability
     */
    private boolean performSoulSteal(Unit target) {
        if (target == null) {
            return false;
        }
        
        // Soul steal logic would be implemented here
        abilityCooldowns.put(ChosenStrength.SOUL_STEAL.name(), 8);
        return true;
    }
    
    /**
     * Perform blood call ability
     */
    private boolean performBloodCall() {
        // Blood call logic would be implemented here
        abilityCooldowns.put(ChosenStrength.BLOOD_CALL.name(), 5);
        return true;
    }
    
    /**
     * Perform death mark ability
     */
    private boolean performDeathMark(Unit target) {
        if (target == null) {
            return false;
        }
        
        // Death mark logic would be implemented here
        abilityCooldowns.put(ChosenStrength.DEATH_MARK.name(), 4);
        return true;
    }
    
    /**
     * Perform eternal life ability
     */
    private boolean performEternalLife() {
        // Eternal life logic would be implemented here
        abilityCooldowns.put(ChosenStrength.ETERNAL_LIFE.name(), 10);
        return true;
    }
    
    /**
     * Perform knowledge absorption ability
     */
    private boolean performKnowledgeAbsorption(Unit target) {
        if (target == null) {
            return false;
        }
        
        // Knowledge absorption logic would be implemented here
        knowledgeAbsorption++;
        abilityCooldowns.put(ChosenStrength.KNOWLEDGE_ABSORPTION.name(), 3);
        return true;
    }
    
    /**
     * Learn from player tactics
     */
    public void learnFromTactic(String tactic) {
        if (!learnedTactics.contains(tactic)) {
            learnedTactics.add(tactic);
            tacticCounters.put(tactic, 1);
            tacticEffectiveness.put(tactic, 50); // Start with 50% effectiveness
        } else {
            tacticCounters.put(tactic, tacticCounters.get(tactic) + 1);
            // Increase effectiveness with more encounters
            int currentEffectiveness = tacticEffectiveness.get(tactic);
            tacticEffectiveness.put(tactic, Math.min(100, currentEffectiveness + 10));
        }
        
        // Increase learning level based on tactics learned
        if (learnedTactics.size() >= learningLevel * 3) {
            learningLevel++;
        }
    }
    
    /**
     * Adapt to player tactics
     */
    public void adapt() {
        isAdapting = true;
        
        // Adapt based on learned tactics
        for (String tactic : learnedTactics) {
            int effectiveness = tacticEffectiveness.get(tactic);
            if (effectiveness > 70) {
                // Develop counter-tactics
                String counterTactic = "counter_" + tactic;
                if (!learnedTactics.contains(counterTactic)) {
                    learnedTactics.add(counterTactic);
                    tacticCounters.put(counterTactic, 1);
                    tacticEffectiveness.put(counterTactic, 60);
                }
            }
        }
    }
    
    /**
     * Gather intel from player operations
     */
    public void gatherIntel(int amount) {
        intelGathered += amount;
        
        // Use intel to improve abilities
        if (intelGathered >= 100) {
            intelGathered -= 100;
            // Improve a random strength
            if (!strengths.isEmpty()) {
                // DONE: Increment strength level and apply a small contextual stat bonus
                Random rnd = new Random();
                ChosenStrength improved = strengths.get(rnd.nextInt(strengths.size()));
                int newLevel = strengthLevels.getOrDefault(improved, 1) + 1;
                strengthLevels.put(improved, newLevel);

                switch (improved) {
                    case PSYCHIC_BLAST, MIND_CONTROL, MIND_SCREAM, DOMINATION, VOID_RIFT ->
                        setPsiStrength(getPsiStrength() + 2);
                    case SHIELD, REGENERATION -> {
                        setMaxHealth(getMaxHealth() + 2);
                        setCurrentHealth(Math.min(getMaxHealth(), getCurrentHealth() + 2));
                    }
                    case SUMMON_REINFORCEMENTS -> setInitiative(getInitiative() + 1);
                    default -> setCriticalChance(getCriticalChance() + 1);
                }
            }
        }
    }
    
    /**
     * Control territory
     */
    public void controlTerritory(Position position) {
        if (!territoryPositions.contains(position)) {
            territoryPositions.add(position);
            territoryControl++;
        }
    }
    
    /**
     * Retreat when outmatched
     */
    public void retreat() {
        isRetreating = true;
        // Retreat logic would be implemented here
    }
    
    /**
     * Update cooldowns
     */
    public void updateCooldowns() {
        for (String ability : abilityCooldowns.keySet()) {
            if (abilityCooldowns.get(ability) > 0) {
                abilityCooldowns.put(ability, abilityCooldowns.get(ability) - 1);
            }
        }
    }
    
    /**
     * Process chosen turn
     */
    public void processTurn() {
        updateCooldowns();
        
        // Process regeneration if active
        if (getCurrentHealth() < getMaxHealth()) {
            setCurrentHealth(Math.min(getMaxHealth(), getCurrentHealth() + 2));
        }
        
        // Process adaptation if active
        if (isAdapting) {
            // Adaptation logic would be implemented here
        }
        
        // Process corruption if active
        if (isCorrupting) {
            // Corruption logic would be implemented here
        }
    }
    
    /**
     * Check if chosen can use ability
     */
    public boolean canUseAbility(ChosenStrength strength) {
        return strengths.contains(strength) && abilityCooldowns.get(strength.name()) <= 0;
    }
    
    /**
     * Get available strengths
     */
    public List<ChosenStrength> getAvailableStrengths() {
        return new ArrayList<>(strengths);
    }
    
    /**
     * Get weaknesses
     */
    public List<ChosenWeakness> getWeaknesses() {
        return new ArrayList<>(weaknesses);
    }
    
    /**
     * Gain knowledge from soldier encounters
     */
    public void gainKnowledge(String soldierName, int knowledgeAmount) {
        knowledgeAbsorption += knowledgeAmount;
        absorbedKnowledge.add(soldierName);
        
        // Increase learning level based on knowledge gained
        if (knowledgeAbsorption >= learningLevel * 10) {
            learningLevel++;
        }
        
        // Store knowledge effectiveness
        knowledgeEffectiveness.put(soldierName, knowledgeAmount);
    }
    
    /**
     * Get chosen status
     */
    public String getChosenStatus() {
        return String.format("Chosen: %s, Health: %d/%d, Level: %d, Intel: %d, Territory: %d", 
                           chosenName, getCurrentHealth(), getMaxHealth(), learningLevel, intelGathered, territoryControl);
    }
}
