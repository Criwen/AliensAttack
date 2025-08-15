package com.aliensattack.core.systems;

import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.*;
import java.time.LocalDateTime;

/**
 * Alien Research Technology System for XCOM 2 Strategic Layer
 * Manages alien research, technology development, and strategic advancement
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AlienResearchTechnologySystem {
    
    private Map<String, ResearchType> researchTypes;
    private Map<String, TechnologyLevel> technologyLevels;
    private List<ResearchProject> researchProjects;
    private List<AlienTechnology> technologies;
    private int researchPoints;
    private int technologyBonus;
    private Random random;
    
    /**
     * Initialize the alien research technology system
     */
    public void initialize() {
        if (researchTypes == null) {
            researchTypes = new HashMap<>();
        }
        if (technologyLevels == null) {
            technologyLevels = new HashMap<>();
        }
        if (researchProjects == null) {
            researchProjects = new ArrayList<>();
        }
        if (technologies == null) {
            technologies = new ArrayList<>();
        }
        if (random == null) {
            random = new Random();
        }
        
        researchPoints = 100;
        technologyBonus = 0;
        
        initializeResearchTypes();
        initializeTechnologyLevels();
    }
    
    /**
     * Initialize research types
     */
    private void initializeResearchTypes() {
        // Add research types
        researchTypes.put("WEAPONS", ResearchType.BASIC_RESEARCH);
        researchTypes.put("ARMOR", ResearchType.BASIC_RESEARCH);
        researchTypes.put("PSIONICS", ResearchType.ADVANCED_RESEARCH);
        researchTypes.put("ALIEN_TECH", ResearchType.EXPERIMENTAL_RESEARCH);
        researchTypes.put("MEDICAL", ResearchType.BASIC_RESEARCH);
    }
    
    /**
     * Initialize technology levels
     */
    private void initializeTechnologyLevels() {
        // Add technology levels
        technologyLevels.put("BASIC", TechnologyLevel.HUMAN_TECH);
        technologyLevels.put("INTERMEDIATE", TechnologyLevel.ALIEN_TECH);
        technologyLevels.put("ADVANCED", TechnologyLevel.BEYOND_HUMAN);
        technologyLevels.put("EXPERIMENTAL", TechnologyLevel.EXPERIMENTAL);
    }
    
    /**
     * Add research project
     */
    public void addResearchProject(ResearchProject project) {
        if (!researchProjects.contains(project)) {
            researchProjects.add(project);
            updateTechnologyBonus();
        }
    }
    
    /**
     * Remove research project
     */
    public void removeResearchProject(ResearchProject project) {
        if (researchProjects.remove(project)) {
            updateTechnologyBonus();
        }
    }
    
    /**
     * Update technology bonus
     */
    private void updateTechnologyBonus() {
        technologyBonus = researchProjects.size() * 2;
    }
    
    /**
     * Get alien research technology status
     */
    public String getAlienResearchTechnologyStatus() {
        StringBuilder status = new StringBuilder();
        status.append("Alien Research Technology Status:\n");
        status.append("- Research Points: ").append(researchPoints).append("\n");
        status.append("- Technology Bonus: ").append(technologyBonus).append("\n");
        status.append("- Research Projects: ").append(researchProjects.size()).append("\n");
        status.append("- Technologies: ").append(technologies.size()).append("\n");
        status.append("- Available Research Types: ").append(researchTypes.size()).append("\n");
        status.append("- Available Technology Levels: ").append(technologyLevels.size()).append("\n");
        
        return status.toString();
    }
    
    // Nested classes for the factory and manager
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class TechnologyProject {
        private String projectId;
        private String name;
        private TechnologyType type;
        private TechnologyCategory category;
        private TechnologyLevel level;
        private int complexity;
        private int progress;
        private ProjectStatus status;
        private String engineerTeam;
    }
    
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ResearchProject {
        private String projectId;
        private String projectName;
        private String projectType;
        private String researchArea;
        private String targetAlienType;
        private int researchCost;
        private int researchTime;
        private int currentProgress;
        private int maxProgress;
        private double successRate;
        private double failureRate;
        private double criticalSuccessRate;
        private double criticalFailureRate;
        private String researchBonus;
        private String researchPenalty;
        private boolean isActive;
        private boolean isCompleted;
        private boolean isFailed;
        private int assignedScientists;
        private int maxScientists;
        private int researchPriority;
    }
    
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class TechnologyNode {
        private String nodeId;
        private String nodeName;
        private String nodeType;
        private String technologyLevel;
        private int researchCost;
        private int technologyCost;
        private List<String> prerequisites;
        private List<String> unlockedTechnologies;
        private int researchProgress;
        private int maxResearchProgress;
        private boolean isResearched;
        private boolean isUnlocked;
        private int researchTime;
        private String technologyBonus;
    }
    
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class AlienTechnology {
        private String technologyId;
        private String name;
        private String technologyName;
        private String technologyType;
        private String technologyLevel;
        private int technologyCost;
        private String technologyBonus;
        private String technologyPenalty;
        private boolean isDiscovered;
        private boolean isImplemented;
        private String discoveryDate;
        private String implementationDate;
        private String technologyDescription;
        private TechnologyCategory category;
        private TechnologyLevel level;
        private int power;
        private int complexity;
        private List<String> unlockRequirements;
        private List<TechnologyEffect> effects;
        private List<TechnologyCapability> capabilities;
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
        private int duration;
    }
    
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class TechnologyCapability {
        private String capabilityId;
        private CapabilityType type;
        private int power;
        private int cooldown;
    }
    
    public enum TechnologyType {
        WEAPON_SYSTEM, DEFENSE_SYSTEM, PSYCHIC_AMPLIFIER, 
        MEDICAL_DEVICE, STEALTH_SYSTEM, EXPERIMENTAL_SYSTEM
    }
    
    public enum TechnologyCategory {
        WEAPON, DEFENSE, PSYCHIC, MEDICAL, STEALTH, EXPERIMENTAL, BIOLOGICAL
    }
    
    public enum TechnologyLevel {
        BASIC, HUMAN_TECH, ALIEN_TECH, BEYOND_HUMAN, EXPERIMENTAL
    }
    
    public enum ProjectStatus {
        PLANNED, IN_PROGRESS, COMPLETED, FAILED
    }
    
    public enum TechnologyEffectType {
        DAMAGE_INCREASE, ACCURACY_BOOST, RANGE_EXTENSION, ARMOR_PENETRATION
    }
    
    public enum CapabilityType {
        ACCURACY_BONUS, DAMAGE_BOOST, RANGE_BONUS, ARMOR_BONUS
    }
    
    public enum ResearchCategory {
        WEAPON_TECHNOLOGY, DEFENSE_TECHNOLOGY, PSYCHIC_TECHNOLOGY, 
        MEDICAL_TECHNOLOGY, STEALTH_TECHNOLOGY
    }
    
    public enum ResearchType {
        BASIC_RESEARCH, ADVANCED_RESEARCH, EXPERIMENTAL_RESEARCH
    }
    
    public enum ResearchStatus {
        NOT_STARTED, IN_PROGRESS, COMPLETED, FAILED, PLANNED
    }
    
    public enum DevelopmentPhase {
        CONCEPT, DESIGN, PROTOTYPE, TESTING, PRODUCTION
    }
    
    public enum ThreatType {
        ALIEN_SOLDIER, ALIEN_OFFICER, ALIEN_COMMANDER, ALIEN_SPECIALIST,
        ALIEN_PSYCHIC, ALIEN_MECHANICAL, ALIEN_BIOLOGICAL, ALIEN_EXPERIMENTAL
    }
    
    public enum ThreatLevel {
        BASIC(1), ENHANCED(2), ADVANCED(3), ELITE(4), LEGENDARY(5);
        
        private final int level;
        
        ThreatLevel(int level) {
            this.level = level;
        }
        
        public int getLevel() {
            return level;
        }
    }
    
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ThreatAssessment {
        private String threatId;
        private ThreatType type;
        private ThreatLevel level;
        private int difficulty;
        private int threatValue;
        private List<String> countermeasures;
        private String description;
        private boolean isActive;
        private LocalDateTime lastUpdated;
    }
    
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class DevelopmentProject {
        private String projectId;
        private String name;
        private TechnologyType type;
        private DevelopmentPhase phase;
        private int progress;
        private ResearchStatus status;
    }
}
