package com.aliensattack.core.model;

import java.util.*;

/**
 * Factory for creating strategic layer components
 */
public class StrategicLayerFactory {
    
    /**
     * Create a strategic project
     */
    public static String createStrategicProject(String projectId, String projectName, 
                                              String projectType, 
                                              int projectCost, int projectDuration) {
        return "Strategic Project: " + projectName + " (" + projectType + ")";
    }
    
    /**
     * Create a research project
     */
    public static String createResearchProject(String projectId, String projectName, 
                                             int projectCost, int projectDuration) {
        return createStrategicProject(projectId, projectName, "RESEARCH", projectCost, projectDuration);
    }
    
    /**
     * Create a development project
     */
    public static String createDevelopmentProject(String projectId, String projectName, 
                                                int projectCost, int projectDuration) {
        return createStrategicProject(projectId, projectName, "DEVELOPMENT", projectCost, projectDuration);
    }
    
    /**
     * Create a global threat
     */
    public static String createGlobalThreat(String threatId, String threatName, 
                                           String threatType, int threatLevel, int threatDuration) {
        return "Global Threat: " + threatName + " (" + threatType + ")";
    }
    
    /**
     * Create strategic statistics
     */
    public static String createStrategicStatistics(String statisticsId, String statisticsName, 
                                                 String statisticsType, double statisticsValue) {
        return "Strategic Statistics: " + statisticsName + " (" + statisticsType + ")";
    }
    
    /**
     * Create strategic feedback
     */
    public static String createStrategicFeedback(String feedbackId, String feedbackType, 
                                                String feedbackMessage, int feedbackValue) {
        return "Strategic Feedback: " + feedbackType + " - " + feedbackMessage;
    }
    
    /**
     * Create strategic resource
     */
    public static String createStrategicResource(String resourceId, String resourceName, 
                                                String resourceType, int resourceAmount) {
        return "Strategic Resource: " + resourceName + " (" + resourceType + ")";
    }
    
    /**
     * Create strategic decision
     */
    public static String createStrategicDecision(String decisionId, String decisionType, 
                                                 String decisionDescription, int decisionImpact) {
        return "Strategic Decision: " + decisionType + " - " + decisionDescription;
    }
    
    /**
     * Create manufacturing facility
     */
    public static String createManufacturingFacility(String facilityId, String facilityName, 
                                                     String facilityType, int productionCapacity) {
        return "Manufacturing Facility: " + facilityName + " (" + facilityType + ")";
    }
    
    /**
     * Create intel gathering
     */
    public static String createIntelGathering(String intelId, String intelType, 
                                              String intelSource, int intelValue) {
        return "Intel Gathering: " + intelType + " from " + intelSource;
    }
}
