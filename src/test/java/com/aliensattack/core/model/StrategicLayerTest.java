package com.aliensattack.core.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import com.aliensattack.core.enums.StrategicProjectType;
import com.aliensattack.core.model.StrategicLayerIntegrationSystem.StrategicProject;
import com.aliensattack.core.model.StrategicLayerIntegrationSystem.GlobalThreat;
import com.aliensattack.core.model.StrategicLayerIntegrationSystem.StrategicStatistics;

import java.util.List;
import java.util.Arrays;
import java.util.Map;

/**
 * Test class for Strategic Layer Integration System
 */
public class StrategicLayerTest {
    
    private StrategicLayerIntegrationSystem strategicSystem;
    private StrategicLayerManager strategicManager;
    private StrategicLayerFactory strategicFactory;
    
    @BeforeEach
    void setUp() {
        strategicSystem = new StrategicLayerIntegrationSystem();
        strategicManager = new StrategicLayerManager();
        strategicFactory = new StrategicLayerFactory();
        
        strategicManager.initialize();
    }
    
    @Test
    void testStrategicProjectCreation() {
        // Test project creation
        StrategicProject project = StrategicProject.builder()
                .projectId("TEST_PROJECT")
                .projectName("Test Strategic Project")
                .projectType(StrategicProjectType.RESEARCH)
                .projectCost(1000)
                .projectDuration(5)
                .currentProgress(0)
                .maxProgress(100)
                .successRate(0.8)
                .failureRate(0.2)
                .projectBonus("Strategic advantage")
                .projectPenalty("Resource cost")
                .isActive(false)
                .isCompleted(false)
                .isFailed(false)
                .assignedResources(0)
                .maxResources(10)
                .projectPriority(1)
                .build();
        
        assertNotNull(project);
        assertEquals("TEST_PROJECT", project.getProjectId());
        assertEquals("Test Strategic Project", project.getProjectName());
        assertEquals(StrategicProjectType.RESEARCH, project.getProjectType());
    }
    
    @Test
    void testGlobalThreatCreation() {
        // Test threat creation
        GlobalThreat threat = GlobalThreat.builder()
                .threatId("TEST_THREAT")
                .threatName("Test Global Threat")
                .threatType("ALIEN_INVASION")
                .threatLevel(75)
                .threatDuration(10)
                .currentDuration(0)
                .threatEffects(Map.of("panic", 25, "resource_loss", 15))
                .affectedRegions(Arrays.asList("North America", "Europe"))
                .isActive(true)
                .isResolved(false)
                .threatDescription("Test alien invasion threat")
                .build();
        
        assertNotNull(threat);
        assertEquals("TEST_THREAT", threat.getThreatId());
        assertEquals("Test Global Threat", threat.getThreatName());
        assertEquals(75, threat.getThreatLevel());
    }
    
    @Test
    void testStrategicStatisticsCreation() {
        // Test statistics creation
        StrategicStatistics stats = StrategicStatistics.builder()
                .statisticsId("TEST_STATS")
                .statisticsName("Test Strategic Statistics")
                .statisticsType("PERFORMANCE")
                .statisticsValue(85.5)
                .statisticsUnit("PERCENTAGE")
                .statisticsPeriod("MONTHLY")
                .currentValue(80.0)
                .targetValue(90.0)
                .isPositive(true)
                .isTrendingUp(true)
                .statisticsDescription("Test strategic performance metrics")
                .build();
        
        assertNotNull(stats);
        assertEquals("TEST_STATS", stats.getStatisticsId());
        assertEquals("Test Strategic Statistics", stats.getStatisticsName());
        assertEquals(85.5, stats.getStatisticsValue());
    }
    
    @Test
    void testStrategicLayerManager() {
        // Test manager initialization
        assertNotNull(strategicManager);
        
        // Test project management
        StrategicProject project = strategicFactory.createResearchProject("MANAGER_TEST", "Manager Test Project", 500, 3);
        assertNotNull(project);
        
        // Test threat management
        GlobalThreat threat = strategicFactory.createGlobalThreat("MANAGER_THREAT", "Manager Test Threat", "TEST", 50, 5);
        assertNotNull(threat);
        
        // Test statistics management
        StrategicStatistics stats = strategicFactory.createStrategicStatistics("MANAGER_STATS", "Manager Test Stats", "TEST", 75.0);
        assertNotNull(stats);
    }
    
    @Test
    void testStrategicLayerFactory() {
        // Test factory project creation
        StrategicProject researchProject = strategicFactory.createResearchProject("FACTORY_RESEARCH", "Factory Research Project", 750, 4);
        assertNotNull(researchProject);
        assertEquals("FACTORY_RESEARCH", researchProject.getProjectId());
        
        // Test factory threat creation
        GlobalThreat invasionThreat = strategicFactory.createGlobalThreat("FACTORY_INVASION", "Factory Invasion Threat", "INVASION", 80, 8);
        assertNotNull(invasionThreat);
        assertEquals("FACTORY_INVASION", invasionThreat.getThreatId());
        
        // Test factory statistics creation
        StrategicStatistics performanceStats = strategicFactory.createStrategicStatistics("FACTORY_PERFORMANCE", "Factory Performance Stats", "PERFORMANCE", 88.5);
        assertNotNull(performanceStats);
        assertEquals("FACTORY_PERFORMANCE", performanceStats.getStatisticsId());
    }
    
    @Test
    void testStrategicLayerIntegration() {
        // Test system integration
        assertNotNull(strategicSystem);
        
        // Test project integration
        StrategicProject integratedProject = strategicSystem.createStrategicProject("INTEGRATED_PROJECT", "Integrated Test Project", StrategicProjectType.DEVELOPMENT, 1200, 6);
        assertNotNull(integratedProject);
        
        // Test threat integration
        GlobalThreat integratedThreat = strategicSystem.createGlobalThreat("INTEGRATED_THREAT", "Integrated Test Threat", "INTEGRATED", 65, 7);
        assertNotNull(integratedThreat);
        
        // Test statistics integration
        StrategicStatistics integratedStats = strategicSystem.createStrategicStatistics("INTEGRATED_STATS", "Integrated Test Stats", "INTEGRATED", 92.0);
        assertNotNull(integratedStats);
    }
}
