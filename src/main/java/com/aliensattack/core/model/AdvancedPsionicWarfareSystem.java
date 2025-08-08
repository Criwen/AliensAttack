package com.aliensattack.core.model;

import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.*;

/**
 * Advanced Psionic Warfare System
 * Implements sophisticated psionic abilities with mind control, psychic attacks, and psionic defense mechanics
 */
@Data
@Builder
@AllArgsConstructor
public class AdvancedPsionicWarfareSystem {
    
    private Map<String, PsionicAbility> psionicAbilities;
    private Map<String, PsionicPower> psionicPowers;
    private Map<String, MindControl> mindControls;
    private Map<String, PsionicDefense> psionicDefenses;
    private Map<String, PsionicAmplification> psionicAmplifications;
    private Map<String, PsionicFeedback> psionicFeedbacks;
    private Map<String, PsionicUnit> psionicUnits;
    private Map<String, PsionicResistance> psionicResistances;
    private Map<String, PsionicTraining> psionicTrainings;
    private Map<String, PsionicEvent> psionicEvents;
    private Map<String, Integer> psionicEnergy;
    private Map<String, Integer> psionicRegeneration;
    private Map<String, Integer> psionicCapacity;
    private Map<String, List<String>> activePsionicEffects;
    private Map<String, List<String>> psionicCooldowns;
    
    /**
     * Default constructor that initializes the psionic warfare system
     */
    public AdvancedPsionicWarfareSystem() {
        // Initialize maps
        psionicAbilities = new HashMap<>();
        psionicPowers = new HashMap<>();
        mindControls = new HashMap<>();
        psionicDefenses = new HashMap<>();
        psionicAmplifications = new HashMap<>();
        psionicFeedbacks = new HashMap<>();
        psionicUnits = new HashMap<>();
        psionicResistances = new HashMap<>();
        psionicTrainings = new HashMap<>();
        psionicEvents = new HashMap<>();
        psionicEnergy = new HashMap<>();
        psionicRegeneration = new HashMap<>();
        psionicCapacity = new HashMap<>();
        activePsionicEffects = new HashMap<>();
        psionicCooldowns = new HashMap<>();
        
        // Initialize the system
        initializeSystem();
    }
    
    /**
     * Psionic ability with power management
     */
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PsionicAbility {
        private String abilityId;
        private String abilityName;
        private String description;
        private int powerCost;
        private int cooldown;
        private int currentCooldown;
        private String abilityType;
        private Map<String, Integer> effects;
        private List<String> requirements;
        private boolean isUnlocked;
        private boolean isActive;
        private String activationCondition;
        private int range;
        private int duration;
        private String targetType;
        private double successRate;
        private String failureEffect;
        private List<String> prerequisites;
        private Map<String, Integer> bonuses;
        private String psionicSchool;
    }
    
    /**
     * Psionic power management
     */
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PsionicPower {
        private String powerId;
        private String powerName;
        private String description;
        private int currentEnergy;
        private int maxEnergy;
        private int regenerationRate;
        private int regenerationDelay;
        private int currentRegenerationDelay;
        private Map<String, Integer> powerBonuses;
        private List<String> activeEffects;
        private boolean isOverloaded;
        private int overloadThreshold;
        private String powerType;
        private Map<String, Integer> efficiency;
        private List<String> powerSources;
        private boolean isStable;
        private int stabilityLevel;
    }
    
    /**
     * Mind control mechanics
     */
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MindControl {
        private String controlId;
        private String controllerId;
        private String targetId;
        private int duration;
        private int currentDuration;
        private double controlStrength;
        private String controlType;
        private Map<String, Integer> controlEffects;
        private List<String> controlledActions;
        private boolean isResisted;
        private int resistanceLevel;
        private String resistanceType;
        private double breakChance;
        private int breakAttempts;
        private String controlMethod;
        private Map<String, Integer> controllerBonuses;
        private List<String> controlLimitations;
        private boolean isPermanent;
        private String permanentCondition;
    }
    
    /**
     * Psionic defense system
     */
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PsionicDefense {
        private String defenseId;
        private String defenderId;
        private String defenseType;
        private int defenseStrength;
        private Map<String, Integer> resistanceBonuses;
        private List<String> protectedAbilities;
        private boolean isActive;
        private int duration;
        private int currentDuration;
        private String activationCondition;
        private double successRate;
        private String failureEffect;
        private Map<String, Integer> defenseBonuses;
        private List<String> defenseAbilities;
        private String defenseMethod;
        private int energyCost;
        private boolean isAutomatic;
        private String triggerCondition;
    }
    
    /**
     * Psionic amplification equipment
     */
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PsionicAmplification {
        private String amplificationId;
        private String amplificationName;
        private String description;
        private String equipmentType;
        private Map<String, Integer> amplificationBonuses;
        private List<String> amplifiedAbilities;
        private int energyBonus;
        private int regenerationBonus;
        private int capacityBonus;
        private boolean isEquipped;
        private String slot;
        private List<String> requirements;
        private Map<String, Integer> classBonuses;
        private int durability;
        private int maxDurability;
        private String amplificationType;
        private Map<String, Integer> efficiency;
        private boolean isStable;
        private int stabilityLevel;
    }
    
    /**
     * Psionic feedback damage
     */
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PsionicFeedback {
        private String feedbackId;
        private String sourceId;
        private String targetId;
        private int damageAmount;
        private String damageType;
        private String triggerCondition;
        private double triggerChance;
        private Map<String, Integer> feedbackEffects;
        private List<String> affectedAbilities;
        private boolean isActive;
        private int duration;
        private int currentDuration;
        private String feedbackType;
        private Map<String, Integer> resistance;
        private double resistanceChance;
        private String feedbackMethod;
        private int energyCost;
        private boolean isAutomatic;
    }
    
    /**
     * Psionic unit with abilities
     */
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PsionicUnit {
        private String unitId;
        private String unitName;
        private String unitType;
        private int psionicLevel;
        private int maxPsionicLevel;
        private List<String> psionicAbilities;
        private Map<String, Integer> psionicStats;
        private boolean isPsionic;
        private String psionicSchool;
        private int energyCapacity;
        private int currentEnergy;
        private int regenerationRate;
        private Map<String, Integer> resistance;
        private List<String> activeEffects;
        private boolean isTrained;
        private String trainingLevel;
        private Map<String, Integer> bonuses;
        private List<String> specializations;
    }
    
    /**
     * Psionic resistance mechanics
     */
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PsionicResistance {
        private String resistanceId;
        private String unitId;
        private String resistanceType;
        private int resistanceLevel;
        private Map<String, Integer> resistanceBonuses;
        private List<String> protectedAbilities;
        private boolean isActive;
        private int duration;
        private int currentDuration;
        private String activationCondition;
        private double successRate;
        private String failureEffect;
        private Map<String, Integer> resistanceEffects;
        private List<String> resistanceAbilities;
        private String resistanceMethod;
        private int energyCost;
        private boolean isAutomatic;
        private String triggerCondition;
        private double breakChance;
        private int breakAttempts;
    }
    
    /**
     * Psionic training system
     */
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PsionicTraining {
        private String trainingId;
        private String unitId;
        private String trainingType;
        private int trainingLevel;
        private int maxTrainingLevel;
        private int experience;
        private int experienceToNext;
        private List<String> unlockedAbilities;
        private List<String> activeAbilities;
        private Map<String, Integer> trainingBonuses;
        private String trainingSchool;
        private boolean isTrained;
        private String trainingStatus;
        private Map<String, Integer> statBonuses;
        private List<String> completedTraining;
        private int totalTraining;
        private double successRate;
        private String specializationPath;
        private boolean isSpecialized;
    }
    
    /**
     * Psionic event tracking
     */
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PsionicEvent {
        private String eventId;
        private String eventType;
        private String sourceId;
        private String targetId;
        private int timestamp;
        private Map<String, Integer> eventEffects;
        private List<String> affectedUnits;
        private boolean isSuccessful;
        private String result;
        private int energyCost;
        private int energyGained;
        private String eventDescription;
        private Map<String, Integer> bonuses;
        private List<String> consequences;
        private String eventLocation;
        private int duration;
        private boolean isPermanent;
    }
    
    /**
     * Initialize the psionic warfare system
     */
    private void initializeSystem() {
        // Initialize psionic abilities
        initializePsionicAbilities();
        
        // Initialize psionic powers
        initializePsionicPowers();
        
        // Initialize mind control mechanics
        initializeMindControlMechanics();
        
        // Initialize psionic defenses
        initializePsionicDefenses();
        
        // Initialize psionic amplification
        initializePsionicAmplification();
        
        // Initialize psionic feedback
        initializePsionicFeedback();
    }
    
    /**
     * Initialize psionic abilities
     */
    private void initializePsionicAbilities() {
        // Mind Control ability
        PsionicAbility mindControl = PsionicAbility.builder()
            .abilityId("mind_control")
            .abilityName("Mind Control")
            .description("Take control of an enemy unit temporarily")
            .powerCost(50)
            .cooldown(5)
            .currentCooldown(0)
            .abilityType("Active")
            .effects(Map.of("Control", 100, "Duration", 3))
            .requirements(Arrays.asList("psionic_level_3"))
            .isUnlocked(false)
            .isActive(false)
            .activationCondition("Enemy Unit in Range")
            .range(8)
            .duration(3)
            .targetType("Enemy")
            .successRate(0.7)
            .failureEffect("Psionic Feedback")
            .prerequisites(Arrays.asList("psionic_training"))
            .bonuses(Map.of("ControlStrength", 20, "Duration", 1))
            .psionicSchool("Domination")
            .build();
        
        psionicAbilities.put("mind_control", mindControl);
        
        // Psychic Blast ability
        PsionicAbility psychicBlast = PsionicAbility.builder()
            .abilityId("psychic_blast")
            .abilityName("Psychic Blast")
            .description("Deal psychic damage to enemies")
            .powerCost(30)
            .cooldown(3)
            .currentCooldown(0)
            .abilityType("Active")
            .effects(Map.of("Damage", 25, "Stun", 50))
            .requirements(Arrays.asList("psionic_level_2"))
            .isUnlocked(false)
            .isActive(false)
            .activationCondition("Enemy in Range")
            .range(6)
            .duration(1)
            .targetType("Enemy")
            .successRate(0.8)
            .failureEffect("Energy Loss")
            .prerequisites(Arrays.asList("psionic_training"))
            .bonuses(Map.of("Damage", 10, "Range", 2))
            .psionicSchool("Destruction")
            .build();
        
        psionicAbilities.put("psychic_blast", psychicBlast);
        
        // Psionic Shield ability
        PsionicAbility psionicShield = PsionicAbility.builder()
            .abilityId("psionic_shield")
            .abilityName("Psionic Shield")
            .description("Create a protective psionic barrier")
            .powerCost(25)
            .cooldown(4)
            .currentCooldown(0)
            .abilityType("Active")
            .effects(Map.of("Defense", 30, "Resistance", 50))
            .requirements(Arrays.asList("psionic_level_2"))
            .isUnlocked(false)
            .isActive(false)
            .activationCondition("Self or Ally")
            .range(0)
            .duration(3)
            .targetType("Self")
            .successRate(0.9)
            .failureEffect("Shield Failure")
            .prerequisites(Arrays.asList("psionic_training"))
            .bonuses(Map.of("Defense", 15, "Duration", 1))
            .psionicSchool("Protection")
            .build();
        
        psionicAbilities.put("psionic_shield", psionicShield);
    }
    
    /**
     * Initialize psionic powers
     */
    private void initializePsionicPowers() {
        // Basic psionic power
        PsionicPower basicPower = PsionicPower.builder()
            .powerId("basic_psionic")
            .powerName("Basic Psionic Power")
            .description("Basic psionic energy management")
            .currentEnergy(100)
            .maxEnergy(100)
            .regenerationRate(5)
            .regenerationDelay(2)
            .currentRegenerationDelay(0)
            .powerBonuses(Map.of("Efficiency", 10, "Stability", 20))
            .activeEffects(new ArrayList<>())
            .isOverloaded(false)
            .overloadThreshold(90)
            .powerType("Basic")
            .efficiency(Map.of("Energy", 80, "Regeneration", 70))
            .powerSources(Arrays.asList("Natural"))
            .isStable(true)
            .stabilityLevel(80)
            .build();
        
        psionicPowers.put("basic_psionic", basicPower);
        
        // Advanced psionic power
        PsionicPower advancedPower = PsionicPower.builder()
            .powerId("advanced_psionic")
            .powerName("Advanced Psionic Power")
            .description("Advanced psionic energy management")
            .currentEnergy(150)
            .maxEnergy(150)
            .regenerationRate(8)
            .regenerationDelay(1)
            .currentRegenerationDelay(0)
            .powerBonuses(Map.of("Efficiency", 20, "Stability", 40))
            .activeEffects(new ArrayList<>())
            .isOverloaded(false)
            .overloadThreshold(95)
            .powerType("Advanced")
            .efficiency(Map.of("Energy", 90, "Regeneration", 85))
            .powerSources(Arrays.asList("Natural", "Amplified"))
            .isStable(true)
            .stabilityLevel(90)
            .build();
        
        psionicPowers.put("advanced_psionic", advancedPower);
    }
    
    /**
     * Initialize mind control mechanics
     */
    private void initializeMindControlMechanics() {
        // Basic mind control
        MindControl basicControl = MindControl.builder()
            .controlId("basic_mind_control")
            .controllerId("")
            .targetId("")
            .duration(3)
            .currentDuration(0)
            .controlStrength(0.7)
            .controlType("Temporary")
            .controlEffects(Map.of("Control", 70, "Duration", 3))
            .controlledActions(Arrays.asList("Move", "Attack", "Use Ability"))
            .isResisted(false)
            .resistanceLevel(0)
            .resistanceType("None")
            .breakChance(0.3)
            .breakAttempts(0)
            .controlMethod("Direct")
            .controllerBonuses(Map.of("ControlStrength", 10, "Duration", 1))
            .controlLimitations(Arrays.asList("Cannot use special abilities", "Reduced damage"))
            .isPermanent(false)
            .permanentCondition("")
            .build();
        
        mindControls.put("basic_mind_control", basicControl);
        
        // Advanced mind control
        MindControl advancedControl = MindControl.builder()
            .controlId("advanced_mind_control")
            .controllerId("")
            .targetId("")
            .duration(5)
            .currentDuration(0)
            .controlStrength(0.9)
            .controlType("Enhanced")
            .controlEffects(Map.of("Control", 90, "Duration", 5))
            .controlledActions(Arrays.asList("Move", "Attack", "Use Ability", "Special Abilities"))
            .isResisted(false)
            .resistanceLevel(0)
            .resistanceType("None")
            .breakChance(0.1)
            .breakAttempts(0)
            .controlMethod("Advanced")
            .controllerBonuses(Map.of("ControlStrength", 20, "Duration", 2))
            .controlLimitations(Arrays.asList("Cannot use ultimate abilities"))
            .isPermanent(false)
            .permanentCondition("")
            .build();
        
        mindControls.put("advanced_mind_control", advancedControl);
    }
    
    /**
     * Initialize psionic defenses
     */
    private void initializePsionicDefenses() {
        // Basic psionic defense
        PsionicDefense basicDefense = PsionicDefense.builder()
            .defenseId("basic_psionic_defense")
            .defenderId("")
            .defenseType("Resistance")
            .defenseStrength(30)
            .resistanceBonuses(Map.of("Psionic", 30, "Mind Control", 20))
            .protectedAbilities(Arrays.asList("Mind Control", "Psychic Blast"))
            .isActive(false)
            .duration(3)
            .currentDuration(0)
            .activationCondition("Psionic Attack")
            .successRate(0.7)
            .failureEffect("Partial Resistance")
            .defenseBonuses(Map.of("Resistance", 30, "Duration", 3))
            .defenseAbilities(Arrays.asList("Resist", "Block"))
            .defenseMethod("Passive")
            .energyCost(0)
            .isAutomatic(true)
            .triggerCondition("Psionic Attack")
            .build();
        
        psionicDefenses.put("basic_psionic_defense", basicDefense);
        
        // Advanced psionic defense
        PsionicDefense advancedDefense = PsionicDefense.builder()
            .defenseId("advanced_psionic_defense")
            .defenderId("")
            .defenseType("Immunity")
            .defenseStrength(80)
            .resistanceBonuses(Map.of("Psionic", 80, "Mind Control", 70))
            .protectedAbilities(Arrays.asList("Mind Control", "Psychic Blast", "Psionic Shield"))
            .isActive(false)
            .duration(5)
            .currentDuration(0)
            .activationCondition("Psionic Attack")
            .successRate(0.9)
            .failureEffect("Reduced Damage")
            .defenseBonuses(Map.of("Resistance", 80, "Duration", 5))
            .defenseAbilities(Arrays.asList("Resist", "Block", "Reflect"))
            .defenseMethod("Active")
            .energyCost(20)
            .isAutomatic(false)
            .triggerCondition("Manual Activation")
            .build();
        
        psionicDefenses.put("advanced_psionic_defense", advancedDefense);
    }
    
    /**
     * Initialize psionic amplification
     */
    private void initializePsionicAmplification() {
        // Basic psionic amplifier
        PsionicAmplification basicAmplifier = PsionicAmplification.builder()
            .amplificationId("basic_amplifier")
            .amplificationName("Basic Psionic Amplifier")
            .description("Basic psionic power amplification")
            .equipmentType("Amplifier")
            .amplificationBonuses(Map.of("Power", 20, "Range", 10))
            .amplifiedAbilities(Arrays.asList("Mind Control", "Psychic Blast"))
            .energyBonus(25)
            .regenerationBonus(2)
            .capacityBonus(50)
            .isEquipped(false)
            .slot("Psionic")
            .requirements(Arrays.asList("psionic_level_1"))
            .classBonuses(Map.of("Psionic", 15))
            .durability(100)
            .maxDurability(100)
            .amplificationType("Basic")
            .efficiency(Map.of("Power", 70, "Energy", 80))
            .isStable(true)
            .stabilityLevel(75)
            .build();
        
        psionicAmplifications.put("basic_amplifier", basicAmplifier);
        
        // Advanced psionic amplifier
        PsionicAmplification advancedAmplifier = PsionicAmplification.builder()
            .amplificationId("advanced_amplifier")
            .amplificationName("Advanced Psionic Amplifier")
            .description("Advanced psionic power amplification")
            .equipmentType("Amplifier")
            .amplificationBonuses(Map.of("Power", 40, "Range", 20))
            .amplifiedAbilities(Arrays.asList("Mind Control", "Psychic Blast", "Psionic Shield"))
            .energyBonus(50)
            .regenerationBonus(5)
            .capacityBonus(100)
            .isEquipped(false)
            .slot("Psionic")
            .requirements(Arrays.asList("psionic_level_3"))
            .classBonuses(Map.of("Psionic", 30))
            .durability(150)
            .maxDurability(150)
            .amplificationType("Advanced")
            .efficiency(Map.of("Power", 90, "Energy", 90))
            .isStable(true)
            .stabilityLevel(90)
            .build();
        
        psionicAmplifications.put("advanced_amplifier", advancedAmplifier);
    }
    
    /**
     * Initialize psionic feedback
     */
    private void initializePsionicFeedback() {
        // Basic psionic feedback
        PsionicFeedback basicFeedback = PsionicFeedback.builder()
            .feedbackId("basic_feedback")
            .sourceId("")
            .targetId("")
            .damageAmount(10)
            .damageType("Psionic")
            .triggerCondition("Failed Psionic Ability")
            .triggerChance(0.3)
            .feedbackEffects(Map.of("Damage", 10, "Stun", 20))
            .affectedAbilities(Arrays.asList("Mind Control", "Psychic Blast"))
            .isActive(false)
            .duration(2)
            .currentDuration(0)
            .feedbackType("Damage")
            .resistance(Map.of("Psionic", 20))
            .resistanceChance(0.5)
            .feedbackMethod("Automatic")
            .energyCost(0)
            .isAutomatic(true)
            .build();
        
        psionicFeedbacks.put("basic_feedback", basicFeedback);
        
        // Advanced psionic feedback
        PsionicFeedback advancedFeedback = PsionicFeedback.builder()
            .feedbackId("advanced_feedback")
            .sourceId("")
            .targetId("")
            .damageAmount(20)
            .damageType("Psionic")
            .triggerCondition("Failed Advanced Psionic Ability")
            .triggerChance(0.5)
            .feedbackEffects(Map.of("Damage", 20, "Stun", 40))
            .affectedAbilities(Arrays.asList("Mind Control", "Psychic Blast", "Psionic Shield"))
            .isActive(false)
            .duration(3)
            .currentDuration(0)
            .feedbackType("Enhanced Damage")
            .resistance(Map.of("Psionic", 40))
            .resistanceChance(0.7)
            .feedbackMethod("Automatic")
            .energyCost(0)
            .isAutomatic(true)
            .build();
        
        psionicFeedbacks.put("advanced_feedback", advancedFeedback);
    }
    
    /**
     * Add psionic unit to system
     */
    public boolean addPsionicUnit(String unitId, String unitName, String unitType) {
        PsionicUnit unit = PsionicUnit.builder()
            .unitId(unitId)
            .unitName(unitName)
            .unitType(unitType)
            .psionicLevel(1)
            .maxPsionicLevel(5)
            .psionicAbilities(new ArrayList<>())
            .psionicStats(Map.of("Energy", 100, "Regeneration", 5, "Capacity", 100))
            .isPsionic(true)
            .psionicSchool("Basic")
            .energyCapacity(100)
            .currentEnergy(100)
            .regenerationRate(5)
            .resistance(Map.of("Psionic", 20, "Mind Control", 10))
            .activeEffects(new ArrayList<>())
            .isTrained(false)
            .trainingLevel("Novice")
            .bonuses(new HashMap<>())
            .specializations(new ArrayList<>())
            .build();
        
        psionicUnits.put(unitId, unit);
        psionicEnergy.put(unitId, 100);
        psionicRegeneration.put(unitId, 5);
        psionicCapacity.put(unitId, 100);
        activePsionicEffects.put(unitId, new ArrayList<>());
        psionicCooldowns.put(unitId, new ArrayList<>());
        
        return true;
    }
    
    /**
     * Use psionic ability
     */
    public boolean usePsionicAbility(String unitId, String abilityId, String targetId) {
        PsionicUnit unit = psionicUnits.get(unitId);
        PsionicAbility ability = psionicAbilities.get(abilityId);
        
        if (unit == null || ability == null) {
            return false;
        }
        
        // Check if ability is unlocked
        if (!unit.getPsionicAbilities().contains(abilityId)) {
            return false;
        }
        
        // Check energy cost
        if (unit.getCurrentEnergy() < ability.getPowerCost()) {
            return false;
        }
        
        // Check cooldown
        if (ability.getCurrentCooldown() > 0) {
            return false;
        }
        
        // Use ability
        unit.setCurrentEnergy(unit.getCurrentEnergy() - ability.getPowerCost());
        ability.setCurrentCooldown(ability.getCooldown());
        
        // Add to active effects
        List<String> activeEffects = activePsionicEffects.get(unitId);
        activeEffects.add(abilityId);
        
        return true;
    }
    
    /**
     * Attempt mind control
     */
    public boolean attemptMindControl(String controllerId, String targetId, String controlType) {
        MindControl control = mindControls.get(controlType);
        if (control == null) {
            return false;
        }
        
        PsionicUnit controller = psionicUnits.get(controllerId);
        if (controller == null) {
            return false;
        }
        
        // Check if controller has mind control ability
        if (!controller.getPsionicAbilities().contains("mind_control")) {
            return false;
        }
        
        // Set up mind control
        control.setControllerId(controllerId);
        control.setTargetId(targetId);
        control.setCurrentDuration(control.getDuration());
        
        return true;
    }
    
    /**
     * Activate psionic defense
     */
    public boolean activatePsionicDefense(String unitId, String defenseType) {
        PsionicDefense defense = psionicDefenses.get(defenseType);
        if (defense == null) {
            return false;
        }
        
        PsionicUnit unit = psionicUnits.get(unitId);
        if (unit == null) {
            return false;
        }
        
        // Check energy cost
        if (unit.getCurrentEnergy() < defense.getEnergyCost()) {
            return false;
        }
        
        // Activate defense
        defense.setDefenderId(unitId);
        defense.setActive(true);
        defense.setCurrentDuration(defense.getDuration());
        
        // Spend energy
        unit.setCurrentEnergy(unit.getCurrentEnergy() - defense.getEnergyCost());
        
        return true;
    }
    
    /**
     * Equip psionic amplification
     */
    public boolean equipPsionicAmplification(String unitId, String amplificationId) {
        PsionicAmplification amplification = psionicAmplifications.get(amplificationId);
        if (amplification == null) {
            return false;
        }
        
        PsionicUnit unit = psionicUnits.get(unitId);
        if (unit == null) {
            return false;
        }
        
        // Check requirements
        if (!unit.getPsionicAbilities().containsAll(amplification.getRequirements())) {
            return false;
        }
        
        // Equip amplification
        amplification.setEquipped(true);
        
        // Apply bonuses
        Map<String, Integer> bonuses = unit.getBonuses();
        bonuses.putAll(amplification.getAmplificationBonuses());
        
        // Increase energy capacity
        unit.setEnergyCapacity(unit.getEnergyCapacity() + amplification.getCapacityBonus());
        unit.setCurrentEnergy(unit.getCurrentEnergy() + amplification.getEnergyBonus());
        
        return true;
    }
    
    /**
     * Process psionic feedback
     */
    public boolean processPsionicFeedback(String unitId, String feedbackType) {
        PsionicFeedback feedback = psionicFeedbacks.get(feedbackType);
        if (feedback == null) {
            return false;
        }
        
        PsionicUnit unit = psionicUnits.get(unitId);
        if (unit == null) {
            return false;
        }
        
        // Check trigger chance
        if (Math.random() > feedback.getTriggerChance()) {
            return false;
        }
        
        // Apply feedback damage
        // DONE: Calculate effective damage using unit resistance; integrate with core Unit damage system when available
        int baseDamage = feedback.getDamageAmount();
        int resistance = 0;
        Map<String, Integer> unitResistance = unit.getResistance();
        if (unitResistance != null) {
            resistance = unitResistance.getOrDefault(
                feedback.getDamageType(),
                unitResistance.getOrDefault("Psionic", 0)
            );
        }
        int effectiveDamage = Math.max(0, baseDamage - resistance);
        
        // Record effective damage for downstream systems; when integrated, apply to core Unit health
        List<String> effectsForUnit = activePsionicEffects.get(unitId);
        if (effectsForUnit != null) {
            effectsForUnit.add("psionic_feedback_damage:" + effectiveDamage);
        }
        
        // Add to active effects
        List<String> activeEffects = activePsionicEffects.get(unitId);
        activeEffects.add(feedbackType);
        
        return true;
    }
    
    /**
     * Train psionic unit
     */
    public boolean trainPsionicUnit(String unitId, String trainingType) {
        PsionicUnit unit = psionicUnits.get(unitId);
        if (unit == null) {
            return false;
        }
        
        PsionicTraining training = psionicTrainings.get(unitId);
        if (training == null) {
            training = PsionicTraining.builder()
                .trainingId(unitId + "_training")
                .unitId(unitId)
                .trainingType(trainingType)
                .trainingLevel(1)
                .maxTrainingLevel(5)
                .experience(0)
                .experienceToNext(100)
                .unlockedAbilities(new ArrayList<>())
                .activeAbilities(new ArrayList<>())
                .trainingBonuses(new HashMap<>())
                .trainingSchool("Basic")
                .isTrained(false)
                .trainingStatus("In Progress")
                .statBonuses(new HashMap<>())
                .completedTraining(new ArrayList<>())
                .totalTraining(0)
                .successRate(0.0)
                .specializationPath("")
                .isSpecialized(false)
                .build();
            
            psionicTrainings.put(unitId, training);
        }
        
        // Add experience
        training.setExperience(training.getExperience() + 25);
        
        // Check for level up
        if (training.getExperience() >= training.getExperienceToNext()) {
            training.setTrainingLevel(training.getTrainingLevel() + 1);
            training.setExperience(training.getExperience() - training.getExperienceToNext());
            training.setExperienceToNext(training.getExperienceToNext() * 2);
            
            // Unlock new abilities
            if (training.getTrainingLevel() == 2) {
                unit.getPsionicAbilities().add("psychic_blast");
            } else if (training.getTrainingLevel() == 3) {
                unit.getPsionicAbilities().add("mind_control");
            } else if (training.getTrainingLevel() == 4) {
                unit.getPsionicAbilities().add("psionic_shield");
            }
        }
        
        return true;
    }
    
    /**
     * Get psionic unit
     */
    public PsionicUnit getPsionicUnit(String unitId) {
        return psionicUnits.get(unitId);
    }
    
    /**
     * Get psionic ability
     */
    public PsionicAbility getPsionicAbility(String abilityId) {
        return psionicAbilities.get(abilityId);
    }
    
    /**
     * Get active psionic effects for unit
     */
    public List<String> getActivePsionicEffects(String unitId) {
        return activePsionicEffects.getOrDefault(unitId, new ArrayList<>());
    }
    
    /**
     * Get psionic energy for unit
     */
    public int getPsionicEnergy(String unitId) {
        return psionicEnergy.getOrDefault(unitId, 0);
    }
    
    /**
     * Get psionic regeneration for unit
     */
    public int getPsionicRegeneration(String unitId) {
        return psionicRegeneration.getOrDefault(unitId, 0);
    }
    
    /**
     * Get psionic capacity for unit
     */
    public int getPsionicCapacity(String unitId) {
        return psionicCapacity.getOrDefault(unitId, 0);
    }
    
    /**
     * Get total psionic energy
     */
    public int getTotalPsionicEnergy() {
        return psionicEnergy.values().stream().mapToInt(Integer::intValue).sum();
    }
    
    /**
     * Get total psionic regeneration
     */
    public int getTotalPsionicRegeneration() {
        return psionicRegeneration.values().stream().mapToInt(Integer::intValue).sum();
    }
    
    /**
     * Get total psionic capacity
     */
    public int getTotalPsionicCapacity() {
        return psionicCapacity.values().stream().mapToInt(Integer::intValue).sum();
    }
    
    /**
     * Get psionic unit count
     */
    public int getPsionicUnitCount() {
        return psionicUnits.size();
    }
    
    /**
     * Get psionic ability count
     */
    public int getPsionicAbilityCount() {
        return psionicAbilities.size();
    }
    
    /**
     * Get mind control count
     */
    public int getMindControlCount() {
        return mindControls.size();
    }
    
    /**
     * Get psionic defense count
     */
    public int getPsionicDefenseCount() {
        return psionicDefenses.size();
    }
    
    /**
     * Get psionic amplification count
     */
    public int getPsionicAmplificationCount() {
        return psionicAmplifications.size();
    }
    
    /**
     * Get psionic feedback count
     */
    public int getPsionicFeedbackCount() {
        return psionicFeedbacks.size();
    }
    
    /**
     * Get psionic training count
     */
    public int getPsionicTrainingCount() {
        return psionicTrainings.size();
    }
    
    /**
     * Get psionic event count
     */
    public int getPsionicEventCount() {
        return psionicEvents.size();
    }
}

