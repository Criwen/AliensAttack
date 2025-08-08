package com.aliensattack.core.model;

import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.util.*;
import java.time.LocalDateTime;

/**
 * Advanced Alien Research and Technology System
 * Handles alien research progression, technology development, and adaptive threats
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AdvancedAlienResearchTechnologySystem {
    
    // Research Management
    private Map<String, AlienResearch> alienResearch;
    private Map<String, TechnologyProject> technologyProjects;
    private Map<String, ResearchProgress> researchProgress;
    private List<ResearchCategory> researchCategories;
    
    // Technology Tracking
    private Map<String, AlienTechnology> alienTechnologies;
    private Map<String, TechnologyLevel> technologyLevels;
    private Map<String, LocalDateTime> technologyDiscoveryDates;
    private Map<String, Boolean> technologyCountermeasures;
    
    // Adaptive Threats
    private Map<String, AdaptiveThreat> adaptiveThreats;
    private Map<String, ThreatEvolution> threatEvolutions;
    private Map<String, CountermeasureEffectiveness> countermeasureEffectiveness;
    private List<ThreatType> threatTypes;
    
    // Research Facilities
    private List<ResearchFacility> researchFacilities;
    private Map<String, ResearchFacility> assignedFacilities;
    private int totalResearchCapacity;
    private int availableResearchCapacity;
    
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class AlienResearch {
        private String researchId;
        private String name;
        private ResearchCategory category;
        private ResearchType type;
        private int difficulty;
        private int progress;
        private int requiredProgress;
        private LocalDateTime startDate;
        private LocalDateTime expectedCompletionDate;
        private boolean isCompleted;
        private List<ResearchPrerequisite> prerequisites;
        private List<ResearchEffect> effects;
    }
    
    public enum ResearchCategory {
        WEAPON_TECHNOLOGY, DEFENSE_TECHNOLOGY, PSYCHIC_TECHNOLOGY, 
        BIOLOGICAL_TECHNOLOGY, TRANSPORT_TECHNOLOGY, COMMUNICATION_TECHNOLOGY,
        STEALTH_TECHNOLOGY, MEDICAL_TECHNOLOGY, EXPERIMENTAL_TECHNOLOGY
    }
    
    public enum ResearchType {
        BASIC_RESEARCH, ADVANCED_RESEARCH, EXPERIMENTAL_RESEARCH, 
        REVERSE_ENGINEERING, ALIEN_AUTOPSY, TECHNOLOGY_ANALYSIS
    }
    
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ResearchPrerequisite {
        private String prerequisiteId;
        private String researchId;
        private PrerequisiteType type;
        private boolean isMet;
        private String description;
    }
    
    public enum PrerequisiteType {
        RESEARCH_COMPLETED, TECHNOLOGY_LEVEL, RESOURCE_AVAILABLE, 
        FACILITY_ACCESS, EXPERIENCE_GAINED
    }
    
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ResearchEffect {
        private String effectId;
        private EffectType type;
        private int magnitude;
        private String description;
        private boolean isApplied;
    }
    
    public enum EffectType {
        UNLOCK_TECHNOLOGY, IMPROVE_EFFECTIVENESS, REDUCE_COST, 
        INCREASE_CAPACITY, ENABLE_FEATURE, IMPROVE_ACCURACY
    }
    
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class TechnologyProject {
        private String projectId;
        private String name;
        private TechnologyType type;
        private int complexity;
        private int progress;
        private int requiredProgress;
        private LocalDateTime startDate;
        private LocalDateTime expectedCompletionDate;
        private boolean isCompleted;
        private List<String> dependencies;
        private List<TechnologyEffect> effects;
    }
    
    public enum TechnologyType {
        WEAPON_SYSTEM, DEFENSE_SYSTEM, PSYCHIC_AMPLIFIER, MEDICAL_DEVICE,
        STEALTH_SYSTEM, COMMUNICATION_SYSTEM, TRANSPORT_SYSTEM, 
        SENSOR_SYSTEM, EXPERIMENTAL_SYSTEM
    }
    
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class TechnologyEffect {
        private String effectId;
        private TechnologyEffectType type;
        private int magnitude;
        private String description;
        private boolean isActive;
    }
    
    public enum TechnologyEffectType {
        DAMAGE_INCREASE, ACCURACY_IMPROVEMENT, ARMOR_ENHANCEMENT, 
        PSYCHIC_RESISTANCE, HEALING_BONUS, STEALTH_IMPROVEMENT
    }
    
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ResearchProgress {
        private String researchId;
        private int currentProgress;
        private int requiredProgress;
        private double progressPercentage;
        private LocalDateTime lastUpdate;
        private List<String> researchers;
        private ResearchStatus status;
    }
    
    public enum ResearchStatus {
        NOT_STARTED, IN_PROGRESS, PAUSED, COMPLETED, FAILED
    }
    
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class AlienTechnology {
        private String technologyId;
        private String name;
        private TechnologyCategory category;
        private TechnologyLevel level;
        private int power;
        private int complexity;
        private LocalDateTime discoveryDate;
        private boolean isUnderstood;
        private boolean isReplicable;
        private List<TechnologyCapability> capabilities;
    }
    
    public enum TechnologyCategory {
        WEAPON, DEFENSE, PSYCHIC, BIOLOGICAL, TRANSPORT, 
        COMMUNICATION, STEALTH, MEDICAL, EXPERIMENTAL
    }
    
    public enum TechnologyLevel {
        PRIMITIVE(1), BASIC(2), ADVANCED(3), EXPERIMENTAL(4), 
        ALIEN_TECH(5), BEYOND_HUMAN(6);
        
        private final int level;
        
        TechnologyLevel(int level) {
            this.level = level;
        }
        
        public int getLevel() { return level; }
    }
    
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class TechnologyCapability {
        private String capabilityId;
        private String name;
        private CapabilityType type;
        private int power;
        private String description;
        private boolean isActive;
    }
    
    public enum CapabilityType {
        DAMAGE_BONUS, ACCURACY_BONUS, ARMOR_BONUS, PSYCHIC_RESISTANCE,
        HEALING_BONUS, STEALTH_BONUS, MOVEMENT_BONUS, SENSOR_BONUS
    }
    
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class AdaptiveThreat {
        private String threatId;
        private String name;
        private ThreatType type;
        private int power;
        private int adaptability;
        private LocalDateTime firstEncounterDate;
        private int encounterCount;
        private List<ThreatAbility> abilities;
        private ThreatEvolutionState evolutionState;
    }
    
    public enum ThreatType {
        ALIEN_SOLDIER, ALIEN_OFFICER, ALIEN_COMMANDER, ALIEN_SPECIALIST,
        ALIEN_PSYCHIC, ALIEN_MECHANICAL, ALIEN_BIOLOGICAL, ALIEN_EXPERIMENTAL
    }
    
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ThreatAbility {
        private String abilityId;
        private String name;
        private AbilityType type;
        private int power;
        private String description;
        private boolean isActive;
    }
    
    public enum AbilityType {
        WEAPON_ATTACK, PSYCHIC_ATTACK, DEFENSIVE_ABILITY, SUPPORT_ABILITY,
        SPECIAL_ABILITY, ULTIMATE_ABILITY
    }
    
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ThreatEvolution {
        private String threatId;
        private EvolutionStage stage;
        private int evolutionPoints;
        private LocalDateTime lastEvolutionDate;
        private List<String> evolvedAbilities;
        private boolean isEvolving;
    }
    
    public enum EvolutionStage {
        BASIC(1), ENHANCED(2), ADVANCED(3), ELITE(4), LEGENDARY(5);
        
        private final int stage;
        
        EvolutionStage(int stage) {
            this.stage = stage;
        }
        
        public int getStage() { return stage; }
    }
    
    public enum ThreatEvolutionState {
        STABLE, ADAPTING, EVOLVING, MUTATING, PERFECTED
    }
    
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CountermeasureEffectiveness {
        private String threatId;
        private String countermeasureId;
        private double effectiveness;
        private LocalDateTime lastTestDate;
        private boolean isEffective;
        private List<String> weaknesses;
    }
    
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ResearchFacility {
        private String facilityId;
        private String name;
        private FacilityType type;
        private int capacity;
        private int currentWorkload;
        private List<ResearchType> availableResearch;
        private int effectiveness;
        private int cost;
        private int researcherSkill;
    }
    
    public enum FacilityType {
        BASIC_LAB, ADVANCED_LAB, SPECIALIZED_LAB, EXPERIMENTAL_LAB,
        ALIEN_TECHNOLOGY_LAB, PSYCHIC_RESEARCH_LAB, BIOLOGICAL_LAB
    }
    
    // Core Research Management Methods
    
    public boolean startResearch(String researchId, String name, ResearchCategory category, 
                               ResearchType type, int difficulty) {
        if (researchId == null || name == null || category == null || type == null) {
            return false;
        }
        
        AlienResearch research = AlienResearch.builder()
            .researchId(researchId)
            .name(name)
            .category(category)
            .type(type)
            .difficulty(difficulty)
            .progress(0)
            .requiredProgress(calculateRequiredProgress(difficulty, type))
            .startDate(LocalDateTime.now())
            .expectedCompletionDate(LocalDateTime.now().plusDays(calculateResearchTime(difficulty, type)))
            .isCompleted(false)
            .prerequisites(new ArrayList<>())
            .effects(new ArrayList<>())
            .build();
        
        alienResearch.put(researchId, research);
        
        // Initialize progress tracking
        ResearchProgress progress = ResearchProgress.builder()
            .researchId(researchId)
            .currentProgress(0)
            .requiredProgress(research.getRequiredProgress())
            .progressPercentage(0.0)
            .lastUpdate(LocalDateTime.now())
            .researchers(new ArrayList<>())
            .status(ResearchStatus.NOT_STARTED)
            .build();
        
        researchProgress.put(researchId, progress);
        
        return true;
    }
    
    public boolean updateResearchProgress(String researchId, int progressIncrement) {
        AlienResearch research = alienResearch.get(researchId);
        ResearchProgress progress = researchProgress.get(researchId);
        
        if (research == null || progress == null) {
            return false;
        }
        
        // Update progress
        progress.setCurrentProgress(progress.getCurrentProgress() + progressIncrement);
        research.setProgress(progress.getCurrentProgress());
        
        // Calculate percentage
        double percentage = (double) progress.getCurrentProgress() / progress.getRequiredProgress();
        progress.setProgressPercentage(Math.min(1.0, percentage));
        
        // Check for completion
        if (progress.getCurrentProgress() >= progress.getRequiredProgress()) {
            completeResearch(researchId);
        }
        
        progress.setLastUpdate(LocalDateTime.now());
        
        return true;
    }
    
    public boolean completeResearch(String researchId) {
        AlienResearch research = alienResearch.get(researchId);
        if (research == null) {
            return false;
        }
        
        research.setCompleted(true);
        research.setProgress(research.getRequiredProgress());
        
        // Update progress
        ResearchProgress progress = researchProgress.get(researchId);
        if (progress != null) {
            progress.setStatus(ResearchStatus.COMPLETED);
            progress.setProgressPercentage(1.0);
        }
        
        // Apply research effects
        applyResearchEffects(researchId);
        
        // Trigger dependent research
        triggerDependentResearch(researchId);
        
        return true;
    }
    
    public boolean startTechnologyProject(String projectId, String name, TechnologyType type, 
                                       int complexity) {
        if (projectId == null || name == null || type == null) {
            return false;
        }
        
        TechnologyProject project = TechnologyProject.builder()
            .projectId(projectId)
            .name(name)
            .type(type)
            .complexity(complexity)
            .progress(0)
            .requiredProgress(calculateProjectProgress(complexity, type))
            .startDate(LocalDateTime.now())
            .expectedCompletionDate(LocalDateTime.now().plusDays(calculateProjectTime(complexity, type)))
            .isCompleted(false)
            .dependencies(new ArrayList<>())
            .effects(new ArrayList<>())
            .build();
        
        technologyProjects.put(projectId, project);
        
        return true;
    }
    
    public boolean updateProjectProgress(String projectId, int progressIncrement) {
        TechnologyProject project = technologyProjects.get(projectId);
        if (project == null) {
            return false;
        }
        
        project.setProgress(project.getProgress() + progressIncrement);
        
        // Check for completion
        if (project.getProgress() >= project.getRequiredProgress()) {
            completeTechnologyProject(projectId);
        }
        
        return true;
    }
    
    public boolean completeTechnologyProject(String projectId) {
        TechnologyProject project = technologyProjects.get(projectId);
        if (project == null) {
            return false;
        }
        
        project.setCompleted(true);
        project.setProgress(project.getRequiredProgress());
        
        // Apply project effects
        applyProjectEffects(projectId);
        
        return true;
    }
    
    public boolean discoverAlienTechnology(String technologyId, String name, 
                                         TechnologyCategory category, TechnologyLevel level) {
        if (technologyId == null || name == null || category == null || level == null) {
            return false;
        }
        
        AlienTechnology technology = AlienTechnology.builder()
            .technologyId(technologyId)
            .name(name)
            .category(category)
            .level(level)
            .power(calculateTechnologyPower(level, category))
            .complexity(calculateTechnologyComplexity(level, category))
            .discoveryDate(LocalDateTime.now())
            .isUnderstood(false)
            .isReplicable(false)
            .capabilities(new ArrayList<>())
            .build();
        
        alienTechnologies.put(technologyId, technology);
        technologyDiscoveryDates.put(technologyId, LocalDateTime.now());
        
        return true;
    }
    
    public boolean analyzeAlienTechnology(String technologyId) {
        AlienTechnology technology = alienTechnologies.get(technologyId);
        if (technology == null) {
            return false;
        }
        
        // Calculate analysis success chance
        double successChance = calculateAnalysisSuccessChance(technology);
        
        if (Math.random() < successChance) {
            technology.setUnderstood(true);
            
            // Check if technology can be replicated
            if (technology.getLevel().getLevel() <= 4) {
                technology.setReplicable(true);
            }
            
            // Generate capabilities
            generateTechnologyCapabilities(technologyId);
            
            return true;
        }
        
        return false;
    }
    
    public boolean createAdaptiveThreat(String threatId, String name, ThreatType type, 
                                      int power, int adaptability) {
        if (threatId == null || name == null || type == null) {
            return false;
        }
        
        AdaptiveThreat threat = AdaptiveThreat.builder()
            .threatId(threatId)
            .name(name)
            .type(type)
            .power(power)
            .adaptability(adaptability)
            .firstEncounterDate(LocalDateTime.now())
            .encounterCount(0)
            .abilities(new ArrayList<>())
            .evolutionState(ThreatEvolutionState.STABLE)
            .build();
        
        adaptiveThreats.put(threatId, threat);
        
        // Initialize evolution tracking
        ThreatEvolution evolution = ThreatEvolution.builder()
            .threatId(threatId)
            .stage(EvolutionStage.BASIC)
            .evolutionPoints(0)
            .lastEvolutionDate(LocalDateTime.now())
            .evolvedAbilities(new ArrayList<>())
            .isEvolving(false)
            .build();
        
        threatEvolutions.put(threatId, evolution);
        
        return true;
    }
    
    public boolean encounterThreat(String threatId) {
        AdaptiveThreat threat = adaptiveThreats.get(threatId);
        if (threat == null) {
            return false;
        }
        
        threat.setEncounterCount(threat.getEncounterCount() + 1);
        
        // Check for evolution
        checkThreatEvolution(threatId);
        
        // Update countermeasure effectiveness
        updateCountermeasureEffectiveness(threatId);
        
        return true;
    }
    
    public boolean evolveThreat(String threatId) {
        AdaptiveThreat threat = adaptiveThreats.get(threatId);
        ThreatEvolution evolution = threatEvolutions.get(threatId);
        
        if (threat == null || evolution == null) {
            return false;
        }
        
        // Calculate evolution points
        int evolutionPoints = calculateEvolutionPoints(threat);
        evolution.setEvolutionPoints(evolution.getEvolutionPoints() + evolutionPoints);
        
        // Check if evolution is possible
        if (canEvolveThreat(threatId)) {
            performThreatEvolution(threatId);
        }
        
        return true;
    }
    
    public boolean assignResearchFacility(String researchId, String facilityId) {
        ResearchFacility facility = findResearchFacility(facilityId);
        if (facility == null || facility.getCurrentWorkload() >= facility.getCapacity()) {
            return false;
        }
        
        assignedFacilities.put(researchId, facility);
        facility.setCurrentWorkload(facility.getCurrentWorkload() + 1);
        
        return true;
    }
    
    public boolean testCountermeasure(String threatId, String countermeasureId) {
        AdaptiveThreat threat = adaptiveThreats.get(threatId);
        if (threat == null) {
            return false;
        }
        
        // Calculate effectiveness
        double effectiveness = calculateCountermeasureEffectiveness(threat, countermeasureId);
        
        CountermeasureEffectiveness countermeasure = CountermeasureEffectiveness.builder()
            .threatId(threatId)
            .countermeasureId(countermeasureId)
            .effectiveness(effectiveness)
            .lastTestDate(LocalDateTime.now())
            .isEffective(effectiveness >= 0.7)
            .weaknesses(new ArrayList<>())
            .build();
        
        countermeasureEffectiveness.put(threatId + "_" + countermeasureId, countermeasure);
        
        return true;
    }
    
    public boolean isResearchCompleted(String researchId) {
        AlienResearch research = alienResearch.get(researchId);
        return research != null && research.isCompleted();
    }
    
    public boolean isTechnologyUnderstood(String technologyId) {
        AlienTechnology technology = alienTechnologies.get(technologyId);
        return technology != null && technology.isUnderstood();
    }
    
    public boolean isTechnologyReplicable(String technologyId) {
        AlienTechnology technology = alienTechnologies.get(technologyId);
        return technology != null && technology.isReplicable();
    }
    
    public int getThreatPower(String threatId) {
        AdaptiveThreat threat = adaptiveThreats.get(threatId);
        return threat != null ? threat.getPower() : 0;
    }
    
    // Helper Methods
    
    private int calculateRequiredProgress(int difficulty, ResearchType type) {
        int baseProgress = difficulty * 100;
        
        switch (type) {
            case BASIC_RESEARCH:
                return baseProgress;
            case ADVANCED_RESEARCH:
                return baseProgress * 2;
            case EXPERIMENTAL_RESEARCH:
                return baseProgress * 3;
            case REVERSE_ENGINEERING:
                return baseProgress * 2;
            case ALIEN_AUTOPSY:
                return baseProgress * 1;
            case TECHNOLOGY_ANALYSIS:
                return baseProgress * 2;
            default:
                return baseProgress;
        }
    }
    
    private int calculateResearchTime(int difficulty, ResearchType type) {
        int baseTime = difficulty * 7;
        
        switch (type) {
            case BASIC_RESEARCH:
                return baseTime;
            case ADVANCED_RESEARCH:
                return baseTime * 2;
            case EXPERIMENTAL_RESEARCH:
                return baseTime * 3;
            case REVERSE_ENGINEERING:
                return baseTime * 2;
            case ALIEN_AUTOPSY:
                return baseTime * 1;
            case TECHNOLOGY_ANALYSIS:
                return baseTime * 2;
            default:
                return baseTime;
        }
    }
    
    private int calculateProjectProgress(int complexity, TechnologyType type) {
        int baseProgress = complexity * 200;
        
        switch (type) {
            case WEAPON_SYSTEM:
                return baseProgress * 1;
            case DEFENSE_SYSTEM:
                return baseProgress * 1;
            case PSYCHIC_AMPLIFIER:
                return baseProgress * 2;
            case MEDICAL_DEVICE:
                return baseProgress * 1;
            case STEALTH_SYSTEM:
                return baseProgress * 2;
            case COMMUNICATION_SYSTEM:
                return baseProgress * 1;
            case TRANSPORT_SYSTEM:
                return baseProgress * 2;
            case SENSOR_SYSTEM:
                return baseProgress * 1;
            case EXPERIMENTAL_SYSTEM:
                return baseProgress * 3;
            default:
                return baseProgress;
        }
    }
    
    private int calculateProjectTime(int complexity, TechnologyType type) {
        int baseTime = complexity * 14;
        
        switch (type) {
            case WEAPON_SYSTEM:
                return baseTime * 1;
            case DEFENSE_SYSTEM:
                return baseTime * 1;
            case PSYCHIC_AMPLIFIER:
                return baseTime * 2;
            case MEDICAL_DEVICE:
                return baseTime * 1;
            case STEALTH_SYSTEM:
                return baseTime * 2;
            case COMMUNICATION_SYSTEM:
                return baseTime * 1;
            case TRANSPORT_SYSTEM:
                return baseTime * 2;
            case SENSOR_SYSTEM:
                return baseTime * 1;
            case EXPERIMENTAL_SYSTEM:
                return baseTime * 3;
            default:
                return baseTime;
        }
    }
    
    private int calculateTechnologyPower(TechnologyLevel level, TechnologyCategory category) {
        int basePower = level.getLevel() * 10;
        
        switch (category) {
            case WEAPON:
                return basePower * 2;
            case DEFENSE:
                return basePower * 1;
            case PSYCHIC:
                return basePower * 3;
            case BIOLOGICAL:
                return basePower * 2;
            case TRANSPORT:
                return basePower * 1;
            case COMMUNICATION:
                return basePower * 1;
            case STEALTH:
                return basePower * 2;
            case MEDICAL:
                return basePower * 1;
            case EXPERIMENTAL:
                return basePower * 4;
            default:
                return basePower;
        }
    }
    
    private int calculateTechnologyComplexity(TechnologyLevel level, TechnologyCategory category) {
        int baseComplexity = level.getLevel() * 5;
        
        switch (category) {
            case WEAPON:
                return baseComplexity * 1;
            case DEFENSE:
                return baseComplexity * 1;
            case PSYCHIC:
                return baseComplexity * 3;
            case BIOLOGICAL:
                return baseComplexity * 2;
            case TRANSPORT:
                return baseComplexity * 2;
            case COMMUNICATION:
                return baseComplexity * 1;
            case STEALTH:
                return baseComplexity * 2;
            case MEDICAL:
                return baseComplexity * 1;
            case EXPERIMENTAL:
                return baseComplexity * 4;
            default:
                return baseComplexity;
        }
    }
    
    private double calculateAnalysisSuccessChance(AlienTechnology technology) {
        double baseChance = 0.5;
        double levelFactor = 1.0 - (technology.getLevel().getLevel() * 0.1);
        double complexityFactor = 1.0 - (technology.getComplexity() * 0.01);
        
        return Math.max(0.1, baseChance * levelFactor * complexityFactor);
    }
    
    private void generateTechnologyCapabilities(String technologyId) {
        AlienTechnology technology = alienTechnologies.get(technologyId);
        if (technology == null) {
            return;
        }
        
        // Generate capabilities based on technology type and level
        int capabilityCount = technology.getLevel().getLevel();
        
        for (int i = 0; i < capabilityCount; i++) {
            TechnologyCapability capability = TechnologyCapability.builder()
                .capabilityId(UUID.randomUUID().toString())
                .name("Capability " + (i + 1))
                .type(CapabilityType.values()[(int) (Math.random() * CapabilityType.values().length)])
                .power(technology.getPower() / capabilityCount)
                .description("Technology capability")
                .isActive(true)
                .build();
            
            technology.getCapabilities().add(capability);
        }
    }
    
    private void applyResearchEffects(String researchId) {
        AlienResearch research = alienResearch.get(researchId);
        if (research == null) {
            return;
        }
        
        // Apply research effects based on category and type
        // This would unlock new technologies, improve existing ones, etc.
    }
    
    private void triggerDependentResearch(String researchId) {
        // Find and start research that depends on this completed research
        for (AlienResearch research : alienResearch.values()) {
            if (!research.isCompleted()) {
                for (ResearchPrerequisite prerequisite : research.getPrerequisites()) {
                    if (prerequisite.getResearchId().equals(researchId)) {
                        prerequisite.setMet(true);
                        
                        // Check if all prerequisites are met
                        boolean allPrerequisitesMet = research.getPrerequisites().stream()
                            .allMatch(ResearchPrerequisite::isMet);
                        
                        if (allPrerequisitesMet) {
                            ResearchProgress progress = researchProgress.get(research.getResearchId());
                            if (progress != null) {
                                progress.setStatus(ResearchStatus.IN_PROGRESS);
                            }
                        }
                    }
                }
            }
        }
    }
    
    private void applyProjectEffects(String projectId) {
        TechnologyProject project = technologyProjects.get(projectId);
        if (project == null) {
            return;
        }
        
        // Apply project effects based on type
        // This would create new equipment, improve existing systems, etc.
    }
    
    private void checkThreatEvolution(String threatId) {
        AdaptiveThreat threat = adaptiveThreats.get(threatId);
        ThreatEvolution evolution = threatEvolutions.get(threatId);
        
        if (threat == null || evolution == null) {
            return;
        }
        
        // Check if evolution should occur
        if (threat.getEncounterCount() >= 3 && evolution.getEvolutionPoints() >= 10) {
            threat.setEvolutionState(ThreatEvolutionState.EVOLVING);
            // evolution.setIsEvolving(true);
        }
    }
    
    private int calculateEvolutionPoints(AdaptiveThreat threat) {
        return threat.getAdaptability() + (threat.getEncounterCount() / 2);
    }
    
    private boolean canEvolveThreat(String threatId) {
        ThreatEvolution evolution = threatEvolutions.get(threatId);
        if (evolution == null) {
            return false;
        }
        
        return evolution.getEvolutionPoints() >= 10 && evolution.isEvolving();
    }
    
    private void performThreatEvolution(String threatId) {
        AdaptiveThreat threat = adaptiveThreats.get(threatId);
        ThreatEvolution evolution = threatEvolutions.get(threatId);
        
        if (threat == null || evolution == null) {
            return;
        }
        
        // Evolve to next stage
        EvolutionStage currentStage = evolution.getStage();
        EvolutionStage[] stages = EvolutionStage.values();
        
        for (int i = 0; i < stages.length - 1; i++) {
            if (stages[i] == currentStage) {
                evolution.setStage(stages[i + 1]);
                break;
            }
        }
        
        // Increase threat power
        threat.setPower(threat.getPower() * 2);
        
        // Add new abilities
        addEvolvedAbilities(threatId);
        
        // Reset evolution state
        evolution.setEvolutionPoints(0);
        // evolution.setIsEvolving(false);
        evolution.setLastEvolutionDate(LocalDateTime.now());
        
        threat.setEvolutionState(ThreatEvolutionState.STABLE);
    }
    
    private void addEvolvedAbilities(String threatId) {
        AdaptiveThreat threat = adaptiveThreats.get(threatId);
        ThreatEvolution evolution = threatEvolutions.get(threatId);
        
        if (threat == null || evolution == null) {
            return;
        }
        
        // Add evolved abilities based on threat type and evolution stage
        ThreatAbility ability = ThreatAbility.builder()
            .abilityId(UUID.randomUUID().toString())
            .name("Evolved Ability")
            .type(AbilityType.values()[(int) (Math.random() * AbilityType.values().length)])
            .power(threat.getPower() / 2)
            .description("Evolved threat ability")
            .isActive(true)
            .build();
        
        threat.getAbilities().add(ability);
        evolution.getEvolvedAbilities().add(ability.getAbilityId());
    }
    
    private void updateCountermeasureEffectiveness(String threatId) {
        // Update countermeasure effectiveness based on threat evolution
        // This would reduce the effectiveness of existing countermeasures
    }
    
    private double calculateCountermeasureEffectiveness(AdaptiveThreat threat, String countermeasureId) {
        // Calculate how effective a countermeasure is against this threat
        double baseEffectiveness = 0.8;
        double evolutionPenalty = threat.getEncounterCount() * 0.05;
        double adaptabilityPenalty = threat.getAdaptability() * 0.02;
        
        return Math.max(0.1, baseEffectiveness - evolutionPenalty - adaptabilityPenalty);
    }
    
    private ResearchFacility findResearchFacility(String facilityId) {
        return researchFacilities.stream()
            .filter(facility -> facility.getFacilityId().equals(facilityId))
            .findFirst()
            .orElse(null);
    }
    
    // Getters for system state
    public AlienResearch getResearch(String researchId) {
        return alienResearch.get(researchId);
    }
    
    public TechnologyProject getTechnologyProject(String projectId) {
        return technologyProjects.get(projectId);
    }
    
    public AlienTechnology getAlienTechnology(String technologyId) {
        return alienTechnologies.get(technologyId);
    }
    
    public AdaptiveThreat getAdaptiveThreat(String threatId) {
        return adaptiveThreats.get(threatId);
    }
    
    public ResearchProgress getResearchProgress(String researchId) {
        return researchProgress.get(researchId);
    }
    
    public ThreatEvolution getThreatEvolution(String threatId) {
        return threatEvolutions.get(threatId);
    }
    
    public int getAvailableResearchCapacity() {
        return availableResearchCapacity;
    }
    
    public boolean isResearchInProgress(String researchId) {
        ResearchProgress progress = researchProgress.get(researchId);
        return progress != null && progress.getStatus() == ResearchStatus.IN_PROGRESS;
    }
    
    public boolean isThreatEvolving(String threatId) {
        ThreatEvolution evolution = threatEvolutions.get(threatId);
        return evolution != null && evolution.isEvolving();
    }
}
