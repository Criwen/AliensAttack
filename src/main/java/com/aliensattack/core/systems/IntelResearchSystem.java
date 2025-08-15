package com.aliensattack.core.systems;

import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import com.aliensattack.core.model.Position;

import java.util.*;

/**
 * Intel Research System for intelligence gathering.
 * Implements intel collection, analysis, and research mechanics.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class IntelResearchSystem {
    
    private String intelId;
    private Map<String, IntelData> gatheredIntel;
    private Map<String, IntelAnalysis> intelAnalysis;
    private Map<String, ResearchProject> researchProjects;
    private Map<String, IntelBenefit> intelBenefits;
    private Map<String, IntelSharing> intelSharing;
    private Map<String, IntelConsequence> intelConsequences;
    private List<IntelEvent> intelEvents;
    private Map<String, Integer> intelValues;
    private Map<String, Boolean> intelStates;
    private int totalIntelPoints;
    private int maxIntelCapacity;
    private boolean isIntelActive;
    private int researchProgress;
    
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class IntelData {
        private String intelId;
        private String intelName;
        private IntelType intelType;
        private String source;
        private Position location;
        private Map<String, Integer> intelValues;
        private int quality;
        private boolean isAnalyzed;
        private String description;
        private int collectionTime;
        
        public enum IntelType {
            ENEMY_MOVEMENT,     // Enemy movement patterns
            ENEMY_ABILITIES,    // Enemy abilities and capabilities
            ENEMY_STRENGTHS,    // Enemy strengths and weaknesses
            ENEMY_TACTICS,      // Enemy tactical approaches
            ENVIRONMENT_DATA,   // Environmental information
            MISSION_OBJECTIVES, // Mission objective details
            RESOURCE_LOCATIONS, // Resource and supply locations
            SECURITY_SYSTEMS,   // Security system information
            ALIEN_TECHNOLOGY,   // Alien technology data
            STRATEGIC_TARGETS   // Strategic target information
        }
    }
    
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class IntelAnalysis {
        private String analysisId;
        private String intelId;
        private AnalysisType analysisType;
        private Map<String, Integer> analysisResults;
        private int analysisQuality;
        private boolean isComplete;
        private String analyst;
        private int analysisTime;
        private String conclusion;
        
        public enum AnalysisType {
            PATTERN_ANALYSIS,   // Pattern recognition analysis
            THREAT_ASSESSMENT,  // Threat level assessment
            CAPABILITY_ANALYSIS, // Capability analysis
            VULNERABILITY_STUDY, // Vulnerability study
            TACTICAL_REVIEW,    // Tactical review
            STRATEGIC_EVALUATION, // Strategic evaluation
            TECHNOLOGY_STUDY,   // Technology study
            BEHAVIOR_ANALYSIS   // Behavior analysis
        }
    }
    
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ResearchProject {
        private String projectId;
        private String projectName;
        private ResearchType researchType;
        private Map<String, Integer> researchRequirements;
        private Map<String, Integer> researchBenefits;
        private int researchProgress;
        private int maxProgress;
        private boolean isComplete;
        private String researcher;
        private int researchTime;
        
        public enum ResearchType {
            WEAPON_RESEARCH,    // Weapon technology research
            ARMOR_RESEARCH,     // Armor technology research
            ALIEN_BIOLOGY,      // Alien biology research
            ALIEN_TECHNOLOGY,   // Alien technology research
            PSIONIC_RESEARCH,   // Psionic research
            MEDICAL_RESEARCH,   // Medical research
            TACTICAL_RESEARCH,  // Tactical research
            STRATEGIC_RESEARCH  // Strategic research
        }
    }
    
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class IntelBenefit {
        private String benefitId;
        private String benefitName;
        private BenefitType benefitType;
        private String intelId;
        private Map<String, Integer> benefitEffects;
        private boolean isActive;
        private int duration;
        private String description;
        
        public enum BenefitType {
            COMBAT_BONUS,       // Combat performance bonus
            ACCURACY_BONUS,     // Accuracy improvement
            DAMAGE_BONUS,       // Damage increase
            DEFENSE_BONUS,      // Defense improvement
            MOVEMENT_BONUS,     // Movement enhancement
            ABILITY_UNLOCK,     // Ability unlocking
            RESISTANCE_GAIN,    // Resistance gain
            VULNERABILITY_EXPLOIT // Vulnerability exploitation
        }
    }
    
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class IntelSharing {
        private String sharingId;
        private String intelId;
        private SharingType sharingType;
        private String recipient;
        private Map<String, Integer> sharingEffects;
        private boolean isShared;
        private int sharingTime;
        private String description;
        
        public enum SharingType {
            SQUAD_SHARING,      // Share with squad members
            BASE_SHARING,       // Share with base personnel
            ALLY_SHARING,       // Share with allies
            COMMAND_SHARING,    // Share with command
            PUBLIC_SHARING,     // Public sharing
            SECURE_SHARING,     // Secure sharing
            ENCRYPTED_SHARING,  // Encrypted sharing
            RESTRICTED_SHARING  // Restricted sharing
        }
    }
    
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class IntelConsequence {
        private String consequenceId;
        private String consequenceName;
        private ConsequenceType consequenceType;
        private String intelId;
        private Map<String, Integer> consequenceEffects;
        private boolean isTriggered;
        private int severity;
        private String description;
        
        public enum ConsequenceType {
            INTEL_LOSS,         // Loss of intel
            SECURITY_BREACH,    // Security breach
            ENEMY_AWARENESS,    // Enemy becomes aware
            REPUTATION_LOSS,    // Reputation loss
            RESOURCE_LOSS,      // Resource loss
            ABILITY_LOSS,       // Ability loss
            ACCESS_DENIAL,      // Access denial
            INTELLIGENCE_LEAK   // Intelligence leak
        }
    }
    
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class IntelEvent {
        private String eventId;
        private String eventName;
        private EventType eventType;
        private String intelId;
        private Map<String, Integer> eventEffects;
        private boolean isTriggered;
        private String description;
        
        public enum EventType {
            INTEL_GATHERED,     // Intel gathered event
            INTEL_ANALYZED,     // Intel analyzed event
            RESEARCH_COMPLETE,  // Research complete event
            BENEFIT_ACTIVATED,  // Benefit activated event
            SHARING_COMPLETE,   // Sharing complete event
            CONSEQUENCE_TRIGGER, // Consequence trigger event
            INTEL_EXPIRED,      // Intel expired event
            RESEARCH_BREAKTHROUGH // Research breakthrough event
        }
    }
    

    
    /**
     * Gather intel
     */
    public boolean gatherIntel(IntelData intelData) {
        if (canGatherIntel(intelData)) {
            gatheredIntel.put(intelData.getIntelId(), intelData);
            intelValues.put(intelData.getIntelId(), intelData.getQuality());
            intelStates.put(intelData.getIntelId(), false); // Not analyzed yet
            
            totalIntelPoints += intelData.getQuality();
            return true;
        }
        return false;
    }
    
    /**
     * Analyze intel
     */
    public boolean analyzeIntel(String intelId, IntelAnalysis analysis) {
        IntelData intelData = gatheredIntel.get(intelId);
        if (intelData != null && canAnalyzeIntel(intelData, analysis)) {
            intelAnalysis.put(analysis.getAnalysisId(), analysis);
            analysis.setComplete(true);
            intelData.setAnalyzed(true);
            intelStates.put(intelId, true);
            
            applyAnalysisResults(analysis);
            return true;
        }
        return false;
    }
    
    /**
     * Start research project
     */
    public boolean startResearchProject(ResearchProject project) {
        if (canStartResearch(project)) {
            researchProjects.put(project.getProjectId(), project);
            return true;
        }
        return false;
    }
    
    /**
     * Progress research
     */
    public boolean progressResearch(String projectId, int progress) {
        ResearchProject project = researchProjects.get(projectId);
        if (project != null && !project.isComplete()) {
            project.setResearchProgress(project.getResearchProgress() + progress);
            
            if (project.getResearchProgress() >= project.getMaxProgress()) {
                completeResearch(project);
            }
            
            return true;
        }
        return false;
    }
    
    /**
     * Activate intel benefit
     */
    public boolean activateIntelBenefit(String benefitId) {
        IntelBenefit benefit = intelBenefits.get(benefitId);
        if (benefit != null && !benefit.isActive()) {
            benefit.setActive(true);
            applyBenefitEffects(benefit);
            return true;
        }
        return false;
    }
    
    /**
     * Share intel
     */
    public boolean shareIntel(IntelSharing sharing) {
        if (canShareIntel(sharing)) {
            intelSharing.put(sharing.getSharingId(), sharing);
            sharing.setShared(true);
            applySharingEffects(sharing);
            return true;
        }
        return false;
    }
    
    /**
     * Add intel consequence
     */
    public void addIntelConsequence(IntelConsequence consequence) {
        intelConsequences.put(consequence.getConsequenceId(), consequence);
    }
    
    /**
     * Add intel event
     */
    public void addIntelEvent(IntelEvent event) {
        intelEvents.add(event);
    }
    
    /**
     * Check if can gather intel
     */
    private boolean canGatherIntel(IntelData intelData) {
        return totalIntelPoints + intelData.getQuality() <= maxIntelCapacity;
    }
    
    /**
     * Check if can analyze intel
     */
    private boolean canAnalyzeIntel(IntelData intelData, IntelAnalysis analysis) {
        return !intelData.isAnalyzed() && analysis.getAnalysisQuality() > 0;
    }
    
    /**
     * Check if can start research
     */
    private boolean canStartResearch(ResearchProject project) {
        // Check if requirements are met
        for (Map.Entry<String, Integer> requirement : project.getResearchRequirements().entrySet()) {
            if (!checkResearchRequirement(requirement.getKey(), requirement.getValue())) {
                return false;
            }
        }
        return true;
    }
    
    /**
     * Check if can share intel
     */
    private boolean canShareIntel(IntelSharing sharing) {
        IntelData intelData = gatheredIntel.get(sharing.getIntelId());
        return intelData != null && intelData.isAnalyzed();
    }
    
    /**
     * Check research requirement
     */
    private boolean checkResearchRequirement(String requirement, int value) {
        switch (requirement) {
            case "intel_points":
                return totalIntelPoints >= value;
            case "analyzed_intel":
                return (int) gatheredIntel.values().stream()
                        .filter(IntelData::isAnalyzed)
                        .count() >= value;
            case "research_progress":
                return researchProgress >= value;
            default:
                return true;
        }
    }
    
    /**
     * Apply analysis results
     */
    private void applyAnalysisResults(IntelAnalysis analysis) {
        for (Map.Entry<String, Integer> result : analysis.getAnalysisResults().entrySet()) {
            applyAnalysisResultToSystem(result.getKey(), result.getValue());
        }
    }
    
    /**
     * Complete research
     */
    private void completeResearch(ResearchProject project) {
        project.setComplete(true);
        researchProgress += 100;
        
        for (Map.Entry<String, Integer> benefit : project.getResearchBenefits().entrySet()) {
            applyResearchBenefitToSystem(benefit.getKey(), benefit.getValue());
        }
    }
    
    /**
     * Apply benefit effects
     */
    private void applyBenefitEffects(IntelBenefit benefit) {
        for (Map.Entry<String, Integer> effect : benefit.getBenefitEffects().entrySet()) {
            applyBenefitEffectToSystem(effect.getKey(), effect.getValue());
        }
    }
    
    /**
     * Apply sharing effects
     */
    private void applySharingEffects(IntelSharing sharing) {
        for (Map.Entry<String, Integer> effect : sharing.getSharingEffects().entrySet()) {
            applySharingEffectToSystem(effect.getKey(), effect.getValue());
        }
    }
    
    /**
     * Apply analysis result to system
     */
    private void applyAnalysisResultToSystem(String resultType, int resultValue) {
        switch (resultType) {
            case "pattern_recognized":
                // Pattern recognition bonus
                break;
            case "threat_assessed":
                // Threat assessment bonus
                break;
            case "capability_analyzed":
                // Capability analysis bonus
                break;
            case "vulnerability_identified":
                // Vulnerability identification bonus
                break;
            case "tactical_insight":
                // Tactical insight bonus
                break;
            case "strategic_knowledge":
                // Strategic knowledge bonus
                break;
            case "technology_understood":
                // Technology understanding bonus
                break;
            case "behavior_analyzed":
                // Behavior analysis bonus
                break;
        }
    }
    
    /**
     * Apply research benefit to system
     */
    private void applyResearchBenefitToSystem(String benefitType, int benefitValue) {
        switch (benefitType) {
            case "weapon_improvement":
                // Weapon technology improvement
                break;
            case "armor_improvement":
                // Armor technology improvement
                break;
            case "alien_knowledge":
                // Alien knowledge gain
                break;
            case "technology_advancement":
                // Technology advancement
                break;
            case "psionic_development":
                // Psionic development
                break;
            case "medical_advancement":
                // Medical advancement
                break;
            case "tactical_improvement":
                // Tactical improvement
                break;
            case "strategic_advantage":
                // Strategic advantage
                break;
        }
    }
    
    /**
     * Apply benefit effect to system
     */
    private void applyBenefitEffectToSystem(String effectType, int effectValue) {
        switch (effectType) {
            case "combat_bonus":
                // Combat performance bonus
                break;
            case "accuracy_bonus":
                // Accuracy improvement
                break;
            case "damage_bonus":
                // Damage increase
                break;
            case "defense_bonus":
                // Defense improvement
                break;
            case "movement_bonus":
                // Movement enhancement
                break;
            case "ability_unlock":
                // Ability unlocking
                break;
            case "resistance_gain":
                // Resistance gain
                break;
            case "vulnerability_exploit":
                // Vulnerability exploitation
                break;
        }
    }
    
    /**
     * Apply sharing effect to system
     */
    private void applySharingEffectToSystem(String effectType, int effectValue) {
        switch (effectType) {
            case "squad_coordination":
                // Squad coordination bonus
                break;
            case "base_knowledge":
                // Base knowledge sharing
                break;
            case "ally_support":
                // Ally support bonus
                break;
            case "command_awareness":
                // Command awareness
                break;
            case "public_knowledge":
                // Public knowledge
                break;
            case "secure_communication":
                // Secure communication
                break;
            case "encrypted_data":
                // Encrypted data sharing
                break;
            case "restricted_access":
                // Restricted access sharing
                break;
        }
    }
    
    /**
     * Get gathered intel
     */
    public List<IntelData> getGatheredIntel() {
        return new ArrayList<>(gatheredIntel.values());
    }
    
    /**
     * Get analyzed intel
     */
    public List<IntelData> getAnalyzedIntel() {
        return gatheredIntel.values().stream()
                .filter(IntelData::isAnalyzed)
                .collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
    }
    
    /**
     * Get active research projects
     */
    public List<ResearchProject> getActiveResearchProjects() {
        return researchProjects.values().stream()
                .filter(project -> !project.isComplete())
                .collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
    }
    
    /**
     * Get completed research projects
     */
    public List<ResearchProject> getCompletedResearchProjects() {
        return researchProjects.values().stream()
                .filter(ResearchProject::isComplete)
                .collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
    }
    
    /**
     * Get active intel benefits
     */
    public List<IntelBenefit> getActiveIntelBenefits() {
        return intelBenefits.values().stream()
                .filter(IntelBenefit::isActive)
                .collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
    }
    
    /**
     * Get shared intel
     */
    public List<IntelSharing> getSharedIntel() {
        return intelSharing.values().stream()
                .filter(IntelSharing::isShared)
                .collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
    }
    
    /**
     * Get total intel points
     */
    public int getTotalIntelPoints() {
        return totalIntelPoints;
    }
    
    /**
     * Get intel capacity percentage
     */
    public double getIntelCapacityPercentage() {
        return (double) totalIntelPoints / maxIntelCapacity * 100.0;
    }
    
    /**
     * Get research progress
     */
    public int getResearchProgress() {
        return researchProgress;
    }
    
    /**
     * Check if intel is active
     */
    public boolean isIntelActive() {
        return isIntelActive;
    }
    
    /**
     * Get intel by ID
     */
    public IntelData getIntel(String intelId) {
        return gatheredIntel.get(intelId);
    }
    
    /**
     * Get research project by ID
     */
    public ResearchProject getResearchProject(String projectId) {
        return researchProjects.get(projectId);
    }
}
