package com.aliensattack.core.interfaces;

import com.aliensattack.core.model.Campaign;
import com.aliensattack.core.model.Region;
import com.aliensattack.core.model.Facility;
import com.aliensattack.core.model.ResearchProject;

import java.util.List;

/**
 * Interface for Campaign System - XCOM2 Strategic Layer
 * Defines contract for campaign management and strategic decisions
 */
public interface ICampaignSystem {
    
    /**
     * Get current campaign
     * @return Current campaign instance
     */
    Campaign getCurrentCampaign();
    
    /**
     * Get available regions for contact
     * @return List of available regions
     */
    List<Region> getAvailableRegions();
    
    /**
     * Check if contact can be established with region
     * @param region Region to check
     * @return true if contact can be established
     */
    boolean canEstablishContact(Region region);
    
    /**
     * Establish contact with a region
     * @param region Region to establish contact with
     */
    void establishContact(Region region);
    
    /**
     * Get available research projects
     * @return List of available research projects
     */
    List<ResearchProject> getAvailableResearch();
    
    /**
     * Start a research project
     * @param project Research project to start
     * @return true if research started successfully
     */
    boolean startResearch(ResearchProject project);
    
    /**
     * Get available facilities for construction
     * @return List of available facilities
     */
    List<Facility> getAvailableFacilities();
    
    /**
     * Construct a facility in a region
     * @param facility Facility to construct
     * @param region Region to construct in
     * @return true if construction started successfully
     */
    boolean constructFacility(Facility facility, Region region);
    
    /**
     * Get current threat level
     * @return Current threat level (0.0 to 100.0)
     */
    double getThreatLevel();
    
    /**
     * Update campaign state
     * Called daily to update all systems
     */
    void updateCampaign();
}
