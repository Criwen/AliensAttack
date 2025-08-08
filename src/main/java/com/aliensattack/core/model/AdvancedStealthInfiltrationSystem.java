package com.aliensattack.core.model;

import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.*;

/**
 * Advanced Stealth and Infiltration System
 * Implements complex stealth mechanics with detection ranges, noise levels, and infiltration bonuses
 */
@Data
@Builder
@AllArgsConstructor
public class AdvancedStealthInfiltrationSystem {
    
    private Map<String, StealthUnit> stealthUnits;
    private Map<String, DetectionZone> detectionZones;
    private Map<String, NoiseLevel> noiseLevels;
    private Map<String, StealthEquipment> stealthEquipment;
    private Map<String, InfiltrationBonus> infiltrationBonuses;
    private Map<String, StealthBreach> stealthBreaches;
    private Map<String, StealthEvent> stealthEvents;
    private Map<String, Integer> detectionRanges;
    private Map<String, Integer> noiseValues;
    private Map<String, Boolean> concealmentStatus;
    private Map<String, List<String>> activeStealthEffects;
    private Map<String, List<String>> detectedUnits;
    private Map<String, Integer> infiltrationProgress;
    private Map<String, Integer> stealthBonuses;
    
    public AdvancedStealthInfiltrationSystem() {
        initializeSystem();
    }
    

    
    /**
     * Stealth unit with concealment abilities
     */
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class StealthUnit {
        private String unitId;
        private String unitName;
        private String unitType;
        private boolean isConcealed;
        private int stealthLevel;
        private int maxStealthLevel;
        private Map<String, Integer> stealthStats;
        private List<String> stealthAbilities;
        private String stealthEquipment;
        private int detectionRange;
        private int noiseLevel;
        private boolean isDetected;
        private String detectionStatus;
        private Map<String, Integer> stealthBonuses;
        private List<String> activeEffects;
        private boolean isTrained;
        private String trainingLevel;
        private Map<String, Integer> bonuses;
        private List<String> specializations;
        private int infiltrationProgress;
        private boolean isInfiltrationActive;
    }
    
    /**
     * Detection zone for enemy units
     */
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class DetectionZone {
        private String zoneId;
        private String detectorId;
        private int detectionRange;
        private int detectionStrength;
        private String detectionType;
        private Map<String, Integer> detectionBonuses;
        private List<String> detectedUnits;
        private boolean isActive;
        private int duration;
        private int currentDuration;
        private String activationCondition;
        private double successRate;
        private String failureEffect;
        private Map<String, Integer> zoneEffects;
        private List<String> zoneAbilities;
        private String zoneMethod;
        private int energyCost;
        private boolean isAutomatic;
        private String triggerCondition;
        private Map<String, Integer> rangeModifiers;
        private List<String> detectionFilters;
    }
    
    /**
     * Noise level management
     */
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class NoiseLevel {
        private String noiseId;
        private String sourceId;
        private int noiseValue;
        private String noiseType;
        private int duration;
        private int currentDuration;
        private int detectionRange;
        private Map<String, Integer> noiseEffects;
        private List<String> affectedUnits;
        private boolean isActive;
        private String noiseSource;
        private Map<String, Integer> rangeModifiers;
        private List<String> noiseFilters;
        private boolean isSuppressible;
        private String suppressionMethod;
        private int suppressionCost;
        private double suppressionChance;
    }
    
    /**
     * Stealth equipment for units
     */
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class StealthEquipment {
        private String equipmentId;
        private String equipmentName;
        private String description;
        private String equipmentType;
        private Map<String, Integer> stealthBonuses;
        private List<String> equipmentAbilities;
        private int noiseReduction;
        private int detectionReduction;
        private boolean isEquipped;
        private String slot;
        private List<String> requirements;
        private Map<String, Integer> classBonuses;
        private int durability;
        private int maxDurability;
        private String equipmentQuality;
        private Map<String, Integer> efficiency;
        private boolean isMaintained;
        private int maintenanceLevel;
    }
    
    /**
     * Infiltration bonus system
     */
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class InfiltrationBonus {
        private String bonusId;
        private String bonusName;
        private String description;
        private String bonusType;
        private Map<String, Integer> bonusEffects;
        private List<String> affectedUnits;
        private boolean isActive;
        private int duration;
        private int currentDuration;
        private String activationCondition;
        private double successRate;
        private String failureEffect;
        private Map<String, Integer> bonusBonuses;
        private List<String> bonusAbilities;
        private String bonusMethod;
        private int energyCost;
        private boolean isAutomatic;
        private String triggerCondition;
        private int progressRequired;
        private int currentProgress;
    }
    
    /**
     * Stealth breach mechanics
     */
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class StealthBreach {
        private String breachId;
        private String breacherId;
        private String targetId;
        private String breachType;
        private int breachStrength;
        private Map<String, Integer> breachEffects;
        private List<String> breachAbilities;
        private boolean isSuccessful;
        private int duration;
        private int currentDuration;
        private String breachMethod;
        private Map<String, Integer> breachBonuses;
        private List<String> breachLimitations;
        private boolean isPermanent;
        private String permanentCondition;
        private double successRate;
        private String failureEffect;
        private int energyCost;
        private boolean isAutomatic;
        private String triggerCondition;
    }
    
    /**
     * Stealth event tracking
     */
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class StealthEvent {
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
        private String stealthStatus;
        private int detectionLevel;
    }
    
    /**
     * Initialize the stealth and infiltration system
     */
    private void initializeSystem() {
        // Initialize stealth equipment
        initializeStealthEquipment();
        
        // Initialize infiltration bonuses
        initializeInfiltrationBonuses();
        
        // Initialize stealth breaches
        initializeStealthBreaches();
        
        // Initialize noise levels
        initializeNoiseLevels();
        
        // Initialize detection zones
        initializeDetectionZones();
    }
    
    /**
     * Initialize stealth equipment
     */
    private void initializeStealthEquipment() {
        // Basic stealth suit
        StealthEquipment basicSuit = StealthEquipment.builder()
            .equipmentId("basic_stealth_suit")
            .equipmentName("Basic Stealth Suit")
            .description("Basic stealth equipment for concealment")
            .equipmentType("Armor")
            .stealthBonuses(Map.of("Concealment", 20, "Noise Reduction", 15))
            .equipmentAbilities(Arrays.asList("Conceal", "Silent Movement"))
            .noiseReduction(15)
            .detectionReduction(10)
            .isEquipped(false)
            .slot("Armor")
            .requirements(Arrays.asList("stealth_training"))
            .classBonuses(Map.of("Ranger", 10))
            .durability(100)
            .maxDurability(100)
            .equipmentQuality("Basic")
            .efficiency(Map.of("Stealth", 70, "Noise", 80))
            .isMaintained(true)
            .maintenanceLevel(100)
            .build();
        
        stealthEquipment.put("basic_stealth_suit", basicSuit);
        
        // Advanced stealth suit
        StealthEquipment advancedSuit = StealthEquipment.builder()
            .equipmentId("advanced_stealth_suit")
            .equipmentName("Advanced Stealth Suit")
            .description("Advanced stealth equipment with enhanced concealment")
            .equipmentType("Armor")
            .stealthBonuses(Map.of("Concealment", 40, "Noise Reduction", 30))
            .equipmentAbilities(Arrays.asList("Conceal", "Silent Movement", "Thermal Masking"))
            .noiseReduction(30)
            .detectionReduction(25)
            .isEquipped(false)
            .slot("Armor")
            .requirements(Arrays.asList("advanced_stealth_training"))
            .classBonuses(Map.of("Ranger", 20))
            .durability(150)
            .maxDurability(150)
            .equipmentQuality("Advanced")
            .efficiency(Map.of("Stealth", 90, "Noise", 90))
            .isMaintained(true)
            .maintenanceLevel(100)
            .build();
        
        stealthEquipment.put("advanced_stealth_suit", advancedSuit);
        
        // Stealth gremlin
        StealthEquipment stealthGremlin = StealthEquipment.builder()
            .equipmentId("stealth_gremlin")
            .equipmentName("Stealth Gremlin")
            .description("Stealth-capable technical drone")
            .equipmentType("Drone")
            .stealthBonuses(Map.of("Concealment", 25, "Technical Stealth", 20))
            .equipmentAbilities(Arrays.asList("Silent Hacking", "Stealth Scanning"))
            .noiseReduction(20)
            .detectionReduction(15)
            .isEquipped(false)
            .slot("Secondary")
            .requirements(Arrays.asList("specialist"))
            .classBonuses(Map.of("Specialist", 15))
            .durability(100)
            .maxDurability(100)
            .equipmentQuality("Specialized")
            .efficiency(Map.of("Stealth", 80, "Technical", 85))
            .isMaintained(true)
            .maintenanceLevel(100)
            .build();
        
        stealthEquipment.put("stealth_gremlin", stealthGremlin);
    }
    
    /**
     * Initialize infiltration bonuses
     */
    private void initializeInfiltrationBonuses() {
        // Silent infiltration bonus
        InfiltrationBonus silentInfiltration = InfiltrationBonus.builder()
            .bonusId("silent_infiltration")
            .bonusName("Silent Infiltration")
            .description("Bonus for completing missions without detection")
            .bonusType("Stealth")
            .bonusEffects(Map.of("Experience", 50, "Reputation", 25))
            .affectedUnits(new ArrayList<>())
            .isActive(false)
            .duration(-1)
            .currentDuration(0)
            .activationCondition("Mission completed undetected")
            .successRate(1.0)
            .failureEffect("No bonus")
            .bonusBonuses(Map.of("Stealth", 10, "Experience", 25))
            .bonusAbilities(Arrays.asList("Silent Movement", "Enhanced Concealment"))
            .bonusMethod("Passive")
            .energyCost(0)
            .isAutomatic(true)
            .triggerCondition("Mission Success")
            .progressRequired(100)
            .currentProgress(0)
            .build();
        
        infiltrationBonuses.put("silent_infiltration", silentInfiltration);
        
        // Perfect infiltration bonus
        InfiltrationBonus perfectInfiltration = InfiltrationBonus.builder()
            .bonusId("perfect_infiltration")
            .bonusName("Perfect Infiltration")
            .description("Bonus for completing missions without any detection")
            .bonusType("Stealth")
            .bonusEffects(Map.of("Experience", 100, "Reputation", 50, "Equipment", 25))
            .affectedUnits(new ArrayList<>())
            .isActive(false)
            .duration(-1)
            .currentDuration(0)
            .activationCondition("Mission completed without any detection")
            .successRate(1.0)
            .failureEffect("No bonus")
            .bonusBonuses(Map.of("Stealth", 20, "Experience", 50, "Reputation", 25))
            .bonusAbilities(Arrays.asList("Silent Movement", "Enhanced Concealment", "Perfect Stealth"))
            .bonusMethod("Passive")
            .energyCost(0)
            .isAutomatic(true)
            .triggerCondition("Perfect Mission Success")
            .progressRequired(100)
            .currentProgress(0)
            .build();
        
        infiltrationBonuses.put("perfect_infiltration", perfectInfiltration);
    }
    
    /**
     * Initialize stealth breaches
     */
    private void initializeStealthBreaches() {
        // Silent breach
        StealthBreach silentBreach = StealthBreach.builder()
            .breachId("silent_breach")
            .breacherId("")
            .targetId("")
            .breachType("Silent")
            .breachStrength(30)
            .breachEffects(Map.of("Stealth", 30, "Noise", 10))
            .breachAbilities(Arrays.asList("Silent Entry", "Concealed Movement"))
            .isSuccessful(false)
            .duration(3)
            .currentDuration(0)
            .breachMethod("Silent")
            .breachBonuses(Map.of("Stealth", 15, "Noise", 5))
            .breachLimitations(Arrays.asList("Cannot use loud weapons", "Reduced movement speed"))
            .isPermanent(false)
            .permanentCondition("")
            .successRate(0.8)
            .failureEffect("Detection")
            .energyCost(10)
            .isAutomatic(false)
            .triggerCondition("Manual Activation")
            .build();
        
        stealthBreaches.put("silent_breach", silentBreach);
        
        // Technical breach
        StealthBreach technicalBreach = StealthBreach.builder()
            .breachId("technical_breach")
            .breacherId("")
            .targetId("")
            .breachType("Technical")
            .breachStrength(50)
            .breachEffects(Map.of("Stealth", 50, "Technical", 40))
            .breachAbilities(Arrays.asList("Silent Hacking", "Technical Entry"))
            .isSuccessful(false)
            .duration(5)
            .currentDuration(0)
            .breachMethod("Technical")
            .breachBonuses(Map.of("Stealth", 25, "Technical", 20))
            .breachLimitations(Arrays.asList("Requires technical equipment", "Longer setup time"))
            .isPermanent(false)
            .permanentCondition("")
            .successRate(0.9)
            .failureEffect("Technical Failure")
            .energyCost(20)
            .isAutomatic(false)
            .triggerCondition("Manual Activation")
            .build();
        
        stealthBreaches.put("technical_breach", technicalBreach);
    }
    
    /**
     * Initialize noise levels
     */
    private void initializeNoiseLevels() {
        // Silent movement
        NoiseLevel silentMovement = NoiseLevel.builder()
            .noiseId("silent_movement")
            .sourceId("")
            .noiseValue(5)
            .noiseType("Movement")
            .duration(1)
            .currentDuration(0)
            .detectionRange(2)
            .noiseEffects(Map.of("Detection", 5, "Range", 2))
            .affectedUnits(new ArrayList<>())
            .isActive(false)
            .noiseSource("Movement")
            .rangeModifiers(Map.of("Stealth", -2, "Equipment", -1))
            .noiseFilters(Arrays.asList("Stealth Equipment"))
            .isSuppressible(true)
            .suppressionMethod("Stealth Equipment")
            .suppressionCost(5)
            .suppressionChance(0.8)
            .build();
        
        noiseLevels.put("silent_movement", silentMovement);
        
        // Combat noise
        NoiseLevel combatNoise = NoiseLevel.builder()
            .noiseId("combat_noise")
            .sourceId("")
            .noiseValue(50)
            .noiseType("Combat")
            .duration(3)
            .currentDuration(0)
            .detectionRange(10)
            .noiseEffects(Map.of("Detection", 50, "Range", 10))
            .affectedUnits(new ArrayList<>())
            .isActive(false)
            .noiseSource("Combat")
            .rangeModifiers(Map.of("Stealth", -5, "Equipment", -3))
            .noiseFilters(Arrays.asList("Stealth Equipment", "Suppressors"))
            .isSuppressible(true)
            .suppressionMethod("Suppressors")
            .suppressionCost(15)
            .suppressionChance(0.6)
            .build();
        
        noiseLevels.put("combat_noise", combatNoise);
        
        // Explosive noise
        NoiseLevel explosiveNoise = NoiseLevel.builder()
            .noiseId("explosive_noise")
            .sourceId("")
            .noiseValue(100)
            .noiseType("Explosive")
            .duration(5)
            .currentDuration(0)
            .detectionRange(15)
            .noiseEffects(Map.of("Detection", 100, "Range", 15))
            .affectedUnits(new ArrayList<>())
            .isActive(false)
            .noiseSource("Explosives")
            .rangeModifiers(Map.of("Stealth", -10, "Equipment", -5))
            .noiseFilters(Arrays.asList("Stealth Equipment"))
            .isSuppressible(false)
            .suppressionMethod("None")
            .suppressionCost(0)
            .suppressionChance(0.0)
            .build();
        
        noiseLevels.put("explosive_noise", explosiveNoise);
    }
    
    /**
     * Initialize detection zones
     */
    private void initializeDetectionZones() {
        // Basic detection zone
        DetectionZone basicZone = DetectionZone.builder()
            .zoneId("basic_detection")
            .detectorId("")
            .detectionRange(8)
            .detectionStrength(30)
            .detectionType("Visual")
            .detectionBonuses(Map.of("Visual", 30, "Range", 8))
            .detectedUnits(new ArrayList<>())
            .isActive(false)
            .duration(-1)
            .currentDuration(0)
            .activationCondition("Unit in range")
            .successRate(0.7)
            .failureEffect("No detection")
            .zoneEffects(Map.of("Detection", 30, "Range", 8))
            .zoneAbilities(Arrays.asList("Visual Detection", "Range Detection"))
            .zoneMethod("Passive")
            .energyCost(0)
            .isAutomatic(true)
            .triggerCondition("Unit in range")
            .rangeModifiers(Map.of("Stealth", -3, "Concealment", -5))
            .detectionFilters(Arrays.asList("Stealth", "Concealment"))
            .build();
        
        detectionZones.put("basic_detection", basicZone);
        
        // Advanced detection zone
        DetectionZone advancedZone = DetectionZone.builder()
            .zoneId("advanced_detection")
            .detectorId("")
            .detectionRange(12)
            .detectionStrength(60)
            .detectionType("Multi-Spectral")
            .detectionBonuses(Map.of("Visual", 60, "Thermal", 40, "Range", 12))
            .detectedUnits(new ArrayList<>())
            .isActive(false)
            .duration(-1)
            .currentDuration(0)
            .activationCondition("Unit in range")
            .successRate(0.9)
            .failureEffect("Reduced detection")
            .zoneEffects(Map.of("Detection", 60, "Range", 12))
            .zoneAbilities(Arrays.asList("Visual Detection", "Thermal Detection", "Range Detection"))
            .zoneMethod("Active")
            .energyCost(10)
            .isAutomatic(false)
            .triggerCondition("Manual activation")
            .rangeModifiers(Map.of("Stealth", -6, "Concealment", -8))
            .detectionFilters(Arrays.asList("Stealth", "Concealment", "Thermal Masking"))
            .build();
        
        detectionZones.put("advanced_detection", advancedZone);
    }
    
    /**
     * Add stealth unit to system
     */
    public boolean addStealthUnit(String unitId, String unitName, String unitType) {
        StealthUnit unit = StealthUnit.builder()
            .unitId(unitId)
            .unitName(unitName)
            .unitType(unitType)
            .isConcealed(true)
            .stealthLevel(1)
            .maxStealthLevel(5)
            .stealthStats(Map.of("Concealment", 20, "Noise Reduction", 10, "Detection Range", 8))
            .stealthAbilities(new ArrayList<>())
            .stealthEquipment("")
            .detectionRange(8)
            .noiseLevel(10)
            .isDetected(false)
            .detectionStatus("Concealed")
            .stealthBonuses(new HashMap<>())
            .activeEffects(new ArrayList<>())
            .isTrained(false)
            .trainingLevel("Novice")
            .bonuses(new HashMap<>())
            .specializations(new ArrayList<>())
            .infiltrationProgress(0)
            .isInfiltrationActive(false)
            .build();
        
        stealthUnits.put(unitId, unit);
        detectionRanges.put(unitId, 8);
        noiseValues.put(unitId, 10);
        concealmentStatus.put(unitId, true);
        activeStealthEffects.put(unitId, new ArrayList<>());
        detectedUnits.put(unitId, new ArrayList<>());
        infiltrationProgress.put(unitId, 0);
        stealthBonuses.put(unitId, 0);
        
        return true;
    }
    
    /**
     * Equip stealth equipment
     */
    public boolean equipStealthEquipment(String unitId, String equipmentId) {
        StealthUnit unit = stealthUnits.get(unitId);
        StealthEquipment equipment = stealthEquipment.get(equipmentId);
        
        if (unit == null || equipment == null) {
            return false;
        }
        
        // Check requirements
        if (!unit.getStealthAbilities().containsAll(equipment.getRequirements())) {
            return false;
        }
        
        // Equip equipment
        equipment.setEquipped(true);
        unit.setStealthEquipment(equipmentId);
        
        // Apply bonuses
        Map<String, Integer> bonuses = unit.getStealthBonuses();
        bonuses.putAll(equipment.getStealthBonuses());
        
        // Reduce noise level
        unit.setNoiseLevel(Math.max(0, unit.getNoiseLevel() - equipment.getNoiseReduction()));
        noiseValues.put(unitId, unit.getNoiseLevel());
        
        // Reduce detection range
        unit.setDetectionRange(Math.max(2, unit.getDetectionRange() - equipment.getDetectionReduction()));
        detectionRanges.put(unitId, unit.getDetectionRange());
        
        return true;
    }
    
    /**
     * Create noise
     */
    public boolean createNoise(String unitId, String noiseType, int noiseValue) {
        StealthUnit unit = stealthUnits.get(unitId);
        if (unit == null) {
            return false;
        }
        
        // Create noise level
        NoiseLevel noise = NoiseLevel.builder()
            .noiseId(unitId + "_" + noiseType)
            .sourceId(unitId)
            .noiseValue(noiseValue)
            .noiseType(noiseType)
            .duration(3)
            .currentDuration(0)
            .detectionRange(noiseValue / 5)
            .noiseEffects(Map.of("Detection", noiseValue, "Range", noiseValue / 5))
            .affectedUnits(Arrays.asList(unitId))
            .isActive(true)
            .noiseSource(noiseType)
            .rangeModifiers(new HashMap<>())
            .noiseFilters(new ArrayList<>())
            .isSuppressible(true)
            .suppressionMethod("Stealth Equipment")
            .suppressionCost(noiseValue / 2)
            .suppressionChance(0.7)
            .build();
        
        noiseLevels.put(noise.getNoiseId(), noise);
        
        // Update unit noise level
        unit.setNoiseLevel(unit.getNoiseLevel() + noiseValue);
        noiseValues.put(unitId, unit.getNoiseLevel());
        
        // Check for detection
        checkDetection(unitId);
        
        return true;
    }
    
    /**
     * Check for detection
     */
    private void checkDetection(String unitId) {
        StealthUnit unit = stealthUnits.get(unitId);
        if (unit == null) {
            return;
        }
        
        // Calculate detection chance based on noise level and stealth bonuses
        int baseDetectionChance = unit.getNoiseLevel() * 2;
        int stealthBonus = unit.getStealthBonuses().getOrDefault("Concealment", 0);
        int finalDetectionChance = Math.max(0, baseDetectionChance - stealthBonus);
        
        // Check if unit is detected
        if (Math.random() * 100 < finalDetectionChance) {
            unit.setDetected(true);
            unit.setDetectionStatus("Detected");
            concealmentStatus.put(unitId, false);
            
            // Add to detected units list
            List<String> detected = detectedUnits.get(unitId);
            detected.add(unitId);
        }
    }
    
    /**
     * Attempt stealth breach
     */
    public boolean attemptStealthBreach(String unitId, String targetId, String breachType) {
        StealthBreach breach = stealthBreaches.get(breachType);
        if (breach == null) {
            return false;
        }
        
        StealthUnit unit = stealthUnits.get(unitId);
        if (unit == null) {
            return false;
        }
        
        // Check if unit has stealth abilities
        if (!unit.getStealthAbilities().contains("stealth_breach")) {
            return false;
        }
        
        // Attempt breach
        breach.setBreacherId(unitId);
        breach.setTargetId(targetId);
        breach.setCurrentDuration(breach.getDuration());
        
        // Check success
        if (Math.random() < breach.getSuccessRate()) {
            breach.setSuccessful(true);
            return true;
        } else {
            breach.setSuccessful(false);
            // Apply failure effect
            unit.setDetected(true);
            unit.setDetectionStatus("Breach Failed");
            concealmentStatus.put(unitId, false);
            return false;
        }
    }
    
    /**
     * Activate infiltration bonus
     */
    public boolean activateInfiltrationBonus(String unitId, String bonusId) {
        InfiltrationBonus bonus = infiltrationBonuses.get(bonusId);
        if (bonus == null) {
            return false;
        }
        
        StealthUnit unit = stealthUnits.get(unitId);
        if (unit == null) {
            return false;
        }
        
        // Check if unit meets requirements
        if (unit.getInfiltrationProgress() < bonus.getProgressRequired()) {
            return false;
        }
        
        // Activate bonus
        bonus.setActive(true);
        bonus.setCurrentDuration(bonus.getDuration());
        bonus.getAffectedUnits().add(unitId);
        
        // Apply bonus effects
        Map<String, Integer> bonuses = unit.getBonuses();
        bonuses.putAll(bonus.getBonusEffects());
        
        return true;
    }
    
    /**
     * Update infiltration progress
     */
    public boolean updateInfiltrationProgress(String unitId, int progress) {
        StealthUnit unit = stealthUnits.get(unitId);
        if (unit == null) {
            return false;
        }
        
        unit.setInfiltrationProgress(unit.getInfiltrationProgress() + progress);
        infiltrationProgress.put(unitId, unit.getInfiltrationProgress());
        
        // Check for infiltration bonuses
        for (InfiltrationBonus bonus : infiltrationBonuses.values()) {
            if (unit.getInfiltrationProgress() >= bonus.getProgressRequired() && !bonus.isActive()) {
                activateInfiltrationBonus(unitId, bonus.getBonusId());
            }
        }
        
        return true;
    }
    
    /**
     * Get stealth unit
     */
    public StealthUnit getStealthUnit(String unitId) {
        return stealthUnits.get(unitId);
    }
    
    /**
     * Get detection range for unit
     */
    public int getDetectionRange(String unitId) {
        return detectionRanges.getOrDefault(unitId, 8);
    }
    
    /**
     * Get noise level for unit
     */
    public int getNoiseLevel(String unitId) {
        return noiseValues.getOrDefault(unitId, 10);
    }
    
    /**
     * Get concealment status for unit
     */
    public boolean getConcealmentStatus(String unitId) {
        return concealmentStatus.getOrDefault(unitId, true);
    }
    
    /**
     * Get active stealth effects for unit
     */
    public List<String> getActiveStealthEffects(String unitId) {
        return activeStealthEffects.getOrDefault(unitId, new ArrayList<>());
    }
    
    /**
     * Get detected units for unit
     */
    public List<String> getDetectedUnits(String unitId) {
        return detectedUnits.getOrDefault(unitId, new ArrayList<>());
    }
    
    /**
     * Get infiltration progress for unit
     */
    public int getInfiltrationProgress(String unitId) {
        return infiltrationProgress.getOrDefault(unitId, 0);
    }
    
    /**
     * Get stealth bonus for unit
     */
    public int getStealthBonus(String unitId) {
        return stealthBonuses.getOrDefault(unitId, 0);
    }
    
    /**
     * Get total detection ranges
     */
    public int getTotalDetectionRanges() {
        return detectionRanges.values().stream().mapToInt(Integer::intValue).sum();
    }
    
    /**
     * Get total noise values
     */
    public int getTotalNoiseValues() {
        return noiseValues.values().stream().mapToInt(Integer::intValue).sum();
    }
    
    /**
     * Get total concealment status
     */
    public long getTotalConcealmentStatus() {
        return concealmentStatus.values().stream().filter(status -> status).count();
    }
    
    /**
     * Get stealth unit count
     */
    public int getStealthUnitCount() {
        return stealthUnits.size();
    }
    
    /**
     * Get detection zone count
     */
    public int getDetectionZoneCount() {
        return detectionZones.size();
    }
    
    /**
     * Get noise level count
     */
    public int getNoiseLevelCount() {
        return noiseLevels.size();
    }
    
    /**
     * Get stealth equipment count
     */
    public int getStealthEquipmentCount() {
        return stealthEquipment.size();
    }
    
    /**
     * Get infiltration bonus count
     */
    public int getInfiltrationBonusCount() {
        return infiltrationBonuses.size();
    }
    
    /**
     * Get stealth breach count
     */
    public int getStealthBreachCount() {
        return stealthBreaches.size();
    }
    
    /**
     * Get stealth event count
     */
    public int getStealthEventCount() {
        return stealthEvents.size();
    }
}

