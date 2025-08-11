package com.aliensattack.core.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for InjurySystem functionality
 */
public class InjurySystemTest {
    
    private InjurySystem injurySystem;
    private InjurySystemIntegration integration;
    private Unit testUnit;
    
    @BeforeEach
    void setUp() {
        injurySystem = new InjurySystem();
        injurySystem.initialize();
        integration = new InjurySystemIntegration();
        
        // Create a test unit
        testUnit = new Unit("Test Soldier", 100, 3, 2, 25, com.aliensattack.core.enums.UnitType.SOLDIER);
    }
    
    @Test
    void testInflictInjury() {
        // Test inflicting a gunshot wound
        boolean success = injurySystem.inflictInjury(
            testUnit.getId(), 
            InjurySystem.InjuryType.GUNSHOT_WOUND, 
            InjurySystem.InjurySeverity.MODERATE, 
            InjurySystem.InjurySource.COMBAT_DAMAGE
        );
        
        assertTrue(success);
        assertTrue(injurySystem.hasActiveInjuries(testUnit.getId()));
        
        // Check that injury was created
        var injuries = injurySystem.getSoldierInjuries(testUnit.getId());
        assertEquals(1, injuries.size());
        
        var injury = injuries.get(0);
        assertEquals(InjurySystem.InjuryType.GUNSHOT_WOUND, injury.getType());
        assertEquals(InjurySystem.InjurySeverity.MODERATE, injury.getSeverity());
        assertEquals(InjurySystem.InjurySource.COMBAT_DAMAGE, injury.getSource());
        assertFalse(injury.isPermanent());
    }
    
    @Test
    void testInjuryEffects() {
        // Inflict injury through integration
        boolean success = integration.inflictCombatInjury(
            testUnit,
            InjurySystem.InjuryType.FRACTURE,
            InjurySystem.InjurySeverity.SEVERE,
            InjurySystem.InjurySource.COMBAT_DAMAGE
        );
        
        assertTrue(success);
        
        // Check that unit stats were affected
        assertTrue(testUnit.getMovementRange() < 3); // Reduced mobility
        assertTrue(testUnit.getActionPoints() < 2.0); // Reduced action points
        assertTrue(testUnit.getMedicalPriority() > 0); // Medical priority set
    }
    
    @Test
    void testMedicalTreatment() {
        // Inflict injury first
        integration.inflictCombatInjury(
            testUnit,
            InjurySystem.InjuryType.BURN_INJURY,
            InjurySystem.InjurySeverity.MODERATE,
            InjurySystem.InjurySource.ENVIRONMENTAL_HAZARD
        );
        
        int initialHealth = testUnit.getCurrentHealth();
        
        // Apply medical treatment
        boolean success = integration.applyMedicalTreatmentToUnit(
            testUnit, 
            InjurySystem.TreatmentType.BASIC_FIRST_AID
        );
        
        assertTrue(success);
        
        // Check that health was restored
        assertTrue(testUnit.getCurrentHealth() > initialHealth);
    }
    
    @Test
    void testRecoveryProgress() {
        // Inflict injury
        injurySystem.inflictInjury(
            testUnit.getId(),
            InjurySystem.InjuryType.CONCUSSION,
            InjurySystem.InjurySeverity.LIGHT,
            InjurySystem.InjurySource.COMBAT_DAMAGE
        );
        
        // Check recovery status
        var recoveryStatus = injurySystem.getRecoveryStatus(testUnit.getId());
        assertNotNull(recoveryStatus);
        assertEquals(InjurySystem.RecoveryState.LIGHT_DUTY, recoveryStatus.getState());
        
        // Check recovery progress
        double progress = injurySystem.getRecoveryProgress(testUnit.getId());
        assertTrue(progress >= 0.0 && progress <= 1.0);
    }
    
    @Test
    void testPermanentInjury() {
        // Inflict a critical injury that has a chance of being permanent
        injurySystem.inflictInjury(
            testUnit.getId(),
            InjurySystem.InjuryType.RADIATION_EXPOSURE,
            InjurySystem.InjurySeverity.CRITICAL,
            InjurySystem.InjurySource.ENVIRONMENTAL_HAZARD
        );
        
        // Check if permanent injury occurred (this is random, so we just check the system works)
        var injuries = injurySystem.getSoldierInjuries(testUnit.getId());
        assertFalse(injuries.isEmpty());
        
        // Check recovery state
        var recoveryStatus = injurySystem.getRecoveryStatus(testUnit.getId());
        assertNotNull(recoveryStatus);
        assertTrue(recoveryStatus.getState() == InjurySystem.RecoveryState.CRITICAL_CARE || 
                  recoveryStatus.getState() == InjurySystem.RecoveryState.PERMANENT_DISABILITY);
    }
    
    @Test
    void testHealInjury() {
        // Inflict injury
        injurySystem.inflictInjury(
            testUnit.getId(),
            InjurySystem.InjuryType.GUNSHOT_WOUND,
            InjurySystem.InjurySeverity.LIGHT,
            InjurySystem.InjurySource.COMBAT_DAMAGE
        );
        
        var injuries = injurySystem.getSoldierInjuries(testUnit.getId());
        assertFalse(injuries.isEmpty());
        
        String injuryId = injuries.get(0).getInjuryId();
        
        // Heal the injury
        boolean success = integration.healUnitInjury(testUnit, injuryId);
        assertTrue(success);
        
        // Check that injury was removed
        injuries = injurySystem.getSoldierInjuries(testUnit.getId());
        assertTrue(injuries.isEmpty());
    }
    
    @Test
    void testMedicalFacilities() {
        var facilities = injurySystem.getMedicalFacilities();
        assertFalse(facilities.isEmpty());
        
        // Test assigning a facility
        String facilityId = facilities.get(0).getFacilityId();
        boolean success = injurySystem.assignMedicalFacility(testUnit.getId(), facilityId);
        assertTrue(success);
    }
    
    @Test
    void testCombatParticipation() {
        // Initially, unit can participate in combat
        assertTrue(integration.canUnitParticipateInCombat(testUnit));
        
        // Inflict severe injury
        integration.inflictCombatInjury(
            testUnit,
            InjurySystem.InjuryType.FRACTURE,
            InjurySystem.InjurySeverity.SEVERE,
            InjurySystem.InjurySource.COMBAT_DAMAGE
        );
        
        // Check if unit can still participate (depends on recovery state)
        var recoveryStatus = integration.getUnitRecoveryStatus(testUnit);
        assertNotNull(recoveryStatus);
    }
    
    @Test
    void testMultipleInjuries() {
        // Inflict multiple injuries
        integration.inflictCombatInjury(
            testUnit,
            InjurySystem.InjuryType.GUNSHOT_WOUND,
            InjurySystem.InjurySeverity.LIGHT,
            InjurySystem.InjurySource.COMBAT_DAMAGE
        );
        
        integration.inflictCombatInjury(
            testUnit,
            InjurySystem.InjuryType.BURN_INJURY,
            InjurySystem.InjurySeverity.MODERATE,
            InjurySystem.InjurySource.ENVIRONMENTAL_HAZARD
        );
        
        var injuries = integration.getUnitActiveInjuries(testUnit);
        assertEquals(2, injuries.size());
        
        // Check medical priority (should be higher with multiple injuries)
        assertTrue(testUnit.getMedicalPriority() > 0);
    }
    
    @Test
    void testRecoveryDaysRemaining() {
        // Inflict injury
        injurySystem.inflictInjury(
            testUnit.getId(),
            InjurySystem.InjuryType.PSYCHOLOGICAL_TRAUMA,
            InjurySystem.InjurySeverity.MODERATE,
            InjurySystem.InjurySource.PSYCHIC_ATTACK
        );
        
        int daysRemaining = injurySystem.getRecoveryDaysRemaining(testUnit.getId());
        assertTrue(daysRemaining > 0);
        
        // Psychological trauma should take longer to recover
        assertTrue(daysRemaining >= 7); // Base recovery days for moderate injury
    }
}
