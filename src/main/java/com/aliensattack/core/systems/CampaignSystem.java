package com.aliensattack.core.systems;

import com.aliensattack.core.interfaces.ICampaignSystem;
import com.aliensattack.core.model.Campaign;
import com.aliensattack.core.model.Region;
import com.aliensattack.core.model.Facility;
import com.aliensattack.core.model.ResearchProject;
import lombok.Data;
import lombok.extern.log4j.Log4j2;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Campaign System - XCOM2 Strategic Layer
 * Manages global campaign, regions, facilities, and strategic decisions
 */
@Log4j2
public class CampaignSystem implements ICampaignSystem {
    
    private final Campaign currentCampaign;
    private final GlobalMapManager globalMap;
    private final ResourceManager resourceManager;
    private final ResearchManager researchManager;
    private final FacilityManager facilityManager;
    private final ThreatManager threatManager;
    
    public CampaignSystem() {
        this.currentCampaign = new Campaign();
        this.globalMap = new GlobalMapManager();
        this.resourceManager = new ResourceManager();
        this.researchManager = new ResearchManager();
        this.facilityManager = new FacilityManager();
        this.threatManager = new ThreatManager();
        
        initializeCampaign();
    }
    
    @Override
    public Campaign getCurrentCampaign() {
        return currentCampaign;
    }
    
    @Override
    public List<Region> getAvailableRegions() {
        return globalMap.getAvailableRegions();
    }
    
    @Override
    public boolean canEstablishContact(Region region) {
        return resourceManager.canAffordContact(region) && 
               !region.isContacted() && 
               globalMap.isRegionAccessible(region);
    }
    
    @Override
    public void establishContact(Region region) {
        if (canEstablishContact(region)) {
            resourceManager.spendContactCost(region);
            region.setContacted(true);
            currentCampaign.addContactedRegion(region);
            
            // Apply region bonuses
            applyRegionBonuses(region);
            
            log.info("Contact established with region: {}", region.getName());
        }
    }
    
    @Override
    public List<ResearchProject> getAvailableResearch() {
        return researchManager.getAvailableProjects();
    }
    
    @Override
    public boolean startResearch(ResearchProject project) {
        if (researchManager.canStartProject(project)) {
            researchManager.startProject(project);
            log.info("Research started: {}", project.getName());
            return true;
        }
        return false;
    }
    
    @Override
    public List<Facility> getAvailableFacilities() {
        return facilityManager.getAvailableFacilities();
    }
    
    @Override
    public boolean constructFacility(Facility facility, Region region) {
        if (facilityManager.canConstruct(facility, region)) {
            facilityManager.constructFacility(facility, region);
            log.info("Facility construction started: {} in {}", facility.getName(), region.getName());
            return true;
        }
        return false;
    }
    
    @Override
    public double getThreatLevel() {
        return threatManager.getCurrentThreatLevel();
    }
    
    @Override
    public void updateCampaign() {
        // Update all campaign systems
        researchManager.updateResearch();
        facilityManager.updateFacilities();
        threatManager.updateThreat();
        resourceManager.updateResources();
        
        // Check campaign conditions
        checkCampaignConditions();
        
        log.info("Campaign updated - Day: {}, Threat: {}", 
                currentCampaign.getCurrentDay(), getThreatLevel());
    }
    
    private void initializeCampaign() {
        currentCampaign.setStartDate(new Date());
        currentCampaign.setCurrentDay(1);
        currentCampaign.setDifficulty(Campaign.Difficulty.NORMAL);
        
        // Initialize starting regions
        globalMap.initializeStartingRegions();
        
        // Initialize starting resources
        resourceManager.initializeStartingResources();
        
        // Initialize starting research
        researchManager.initializeStartingResearch();
        
        log.info("Campaign initialized successfully");
    }
    
    private void applyRegionBonuses(Region region) {
        // Apply region-specific bonuses
        switch (region.getType()) {
            case URBAN -> applyUrbanBonuses(region);
            case RURAL -> applyRuralBonuses(region);
            case INDUSTRIAL -> applyIndustrialBonuses(region);
            case MILITARY -> applyMilitaryBonuses(region);
            case RESEARCH -> applyResearchBonuses(region);
        }
    }
    
    private void applyUrbanBonuses(Region region) {
        resourceManager.addIncomeBonus("URBAN_INCOME", 25);
        researchManager.addResearchBonus("URBAN_RESEARCH", 15);
    }
    
    private void applyRuralBonuses(Region region) {
        resourceManager.addIncomeBonus("RURAL_INCOME", 15);
        facilityManager.addConstructionBonus("RURAL_CONSTRUCTION", 20);
    }
    
    private void applyIndustrialBonuses(Region region) {
        resourceManager.addIncomeBonus("INDUSTRIAL_INCOME", 35);
        facilityManager.addConstructionBonus("INDUSTRIAL_CONSTRUCTION", 30);
    }
    
    private void applyMilitaryBonuses(Region region) {
        resourceManager.addIncomeBonus("MILITARY_INCOME", 20);
        threatManager.addThreatReduction("MILITARY_THREAT_REDUCTION", 10);
    }
    
    private void applyResearchBonuses(Region region) {
        researchManager.addResearchBonus("RESEARCH_BONUS", 25);
        resourceManager.addIncomeBonus("RESEARCH_INCOME", 10);
    }
    
    private void checkCampaignConditions() {
        // Check for campaign victory/defeat conditions
        if (threatManager.getCurrentThreatLevel() >= 100.0) {
            currentCampaign.setStatus(Campaign.Status.DEFEAT);
            log.warn("Campaign lost - Threat level too high!");
        } else if (currentCampaign.getContactedRegions().size() >= 8) {
            currentCampaign.setStatus(Campaign.Status.VICTORY);
            log.info("Campaign won - All regions contacted!");
        }
    }
    
    // Inner classes for campaign system components
    public static class GlobalMapManager {
        
        private final Map<String, Region> regions;
        private final List<Region> startingRegions;
        
        public GlobalMapManager() {
            this.regions = new ConcurrentHashMap<>();
            this.startingRegions = new ArrayList<>();
        }
        
        public void initializeStartingRegions() {
            // Create starting regions
            createRegion("NORTH_AMERICA", "North America", Region.Type.URBAN, 50);
            createRegion("EUROPE", "Europe", Region.Type.INDUSTRIAL, 45);
            createRegion("ASIA", "Asia", Region.Type.RESEARCH, 40);
            
            // Set starting regions
            startingRegions.add(regions.get("NORTH_AMERICA"));
        }
        
        private void createRegion(String id, String name, Region.Type type, int contactCost) {
            Region region = new Region(id, name, type, contactCost);
            regions.put(id, region);
        }
        
        public List<Region> getAvailableRegions() {
            return new ArrayList<>(regions.values());
        }
        
        public boolean isRegionAccessible(Region region) {
            // Check if region is accessible based on current contacts
            return true; // Simplified for now
        }
    }
    
    public static class ResourceManager {
        
        private final Map<String, Integer> resources;
        private final Map<String, Integer> incomeBonuses;
        
        public ResourceManager() {
            this.resources = new ConcurrentHashMap<>();
            this.incomeBonuses = new ConcurrentHashMap<>();
        }
        
        public void initializeStartingResources() {
            resources.put("SUPPLIES", 200);
            resources.put("INTEL", 100);
            resources.put("ALLOYS", 50);
            resources.put("ELERIUM", 25);
        }
        
        public boolean canAffordContact(Region region) {
            return getResource("SUPPLIES") >= region.getContactCost();
        }
        
        public void spendContactCost(Region region) {
            int currentSupplies = getResource("SUPPLIES");
            resources.put("SUPPLIES", currentSupplies - region.getContactCost());
        }
        
        public int getResource(String resourceType) {
            return resources.getOrDefault(resourceType, 0);
        }
        
        public void addIncomeBonus(String bonusType, int value) {
            incomeBonuses.put(bonusType, value);
        }
        
        public void updateResources() {
            // Apply daily income
            int dailyIncome = calculateDailyIncome();
            resources.put("SUPPLIES", getResource("SUPPLIES") + dailyIncome);
        }
        
        private int calculateDailyIncome() {
            int baseIncome = 20;
            int bonusIncome = incomeBonuses.values().stream().mapToInt(Integer::intValue).sum();
            return baseIncome + bonusIncome;
        }
    }
    
    public static class ResearchManager {
        
        private final List<ResearchProject> availableProjects;
        private final List<ResearchProject> activeProjects;
        private final Map<String, Integer> researchBonuses;
        
        public ResearchManager() {
            this.availableProjects = new ArrayList<>();
            this.activeProjects = new ArrayList<>();
            this.researchBonuses = new ConcurrentHashMap<>();
        }
        
        public void initializeStartingResearch() {
            // Add starting research projects
            availableProjects.add(new ResearchProject("BASIC_WEAPONS", "Basic Weapons", 5, 100));
            availableProjects.add(new ResearchProject("BASIC_ARMOR", "Basic Armor", 5, 100));
            availableProjects.add(new ResearchProject("ALIEN_BIOLOGY", "Alien Biology", 7, 150));
        }
        
        public List<ResearchProject> getAvailableProjects() {
            return new ArrayList<>(availableProjects);
        }
        
        public boolean canStartProject(ResearchProject project) {
            return availableProjects.contains(project) && 
                   !activeProjects.contains(project);
        }
        
        public void startProject(ResearchProject project) {
            if (canStartProject(project)) {
                availableProjects.remove(project);
                activeProjects.add(project);
                project.setStartDay(getCurrentDay());
            }
        }
        
        public void addResearchBonus(String bonusType, int value) {
            researchBonuses.put(bonusType, value);
        }
        
        public void updateResearch() {
            Iterator<ResearchProject> iterator = activeProjects.iterator();
            
            while (iterator.hasNext()) {
                ResearchProject project = iterator.next();
                project.incrementProgress();
                
                if (project.isComplete()) {
                    completeResearch(project);
                    iterator.remove();
                }
            }
        }
        
        private void completeResearch(ResearchProject project) {
            log.info("Research completed: {}", project.getName());
            // Apply research effects
        }
        
        private int getCurrentDay() {
            return 1; // Simplified for now
        }
    }
    
    public static class FacilityManager {
        
        private final List<Facility> availableFacilities;
        private final List<Facility> constructedFacilities;
        private final Map<String, Integer> constructionBonuses;
        
        public FacilityManager() {
            this.availableFacilities = new ArrayList<>();
            this.constructedFacilities = new ArrayList<>();
            this.constructionBonuses = new ConcurrentHashMap<>();
        }
        
        public void initializeStartingFacilities() {
            // Add available facilities
            availableFacilities.add(new Facility("COMMAND_CENTER", "Command Center", 100, 10));
            availableFacilities.add(new Facility("WORKSHOP", "Workshop", 80, 8));
            availableFacilities.add(new Facility("LABORATORY", "Laboratory", 120, 12));
        }
        
        public List<Facility> getAvailableFacilities() {
            return new ArrayList<>(availableFacilities);
        }
        
        public boolean canConstruct(Facility facility, Region region) {
            return availableFacilities.contains(facility) && 
                   region.canSupportFacility(facility);
        }
        
        public void constructFacility(Facility facility, Region region) {
            if (canConstruct(facility, region)) {
                availableFacilities.remove(facility);
                constructedFacilities.add(facility);
                region.addFacility(facility);
            }
        }
        
        public void addConstructionBonus(String bonusType, int value) {
            constructionBonuses.put(bonusType, value);
        }
        
        public void updateFacilities() {
            // Update facility effects and maintenance
            for (Facility facility : constructedFacilities) {
                facility.update();
            }
        }
    }
    
    public static class ThreatManager {
        
        private double currentThreatLevel;
        private final Map<String, Integer> threatReductions;
        
        public ThreatManager() {
            this.currentThreatLevel = 0.0;
            this.threatReductions = new ConcurrentHashMap<>();
        }
        
        public double getCurrentThreatLevel() {
            return currentThreatLevel;
        }
        
        public void addThreatReduction(String reductionType, int value) {
            threatReductions.put(reductionType, value);
        }
        
        public void updateThreat() {
            // Increase threat over time
            currentThreatLevel += 2.0;
            
            // Apply threat reductions
            double totalReduction = threatReductions.values().stream()
                    .mapToInt(Integer::intValue)
                    .sum();
            
            currentThreatLevel = Math.max(0.0, currentThreatLevel - totalReduction);
        }
    }
}
