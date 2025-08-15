package com.aliensattack.core.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for MedicalSystem
 */
@DisplayName("Medical System Tests")
public class MedicalSystemTest {
    
    private MedicalSystem medicalSystem;
    
    @BeforeEach
    void setUp() {
        medicalSystem = new MedicalSystem();
        medicalSystem.initializeSystem();
    }
    
    @Test
    @DisplayName("Test Medical System Initialization")
    void testMedicalSystemInitialization() {
        assertNotNull(medicalSystem);
        assertTrue(medicalSystem.getAllMedicalFacilities().size() > 0);
        assertTrue(medicalSystem.getAllMedicalEquipment().size() > 0);
        assertTrue(medicalSystem.getAllMedicalProcedures().size() > 0);
        assertTrue(medicalSystem.getAllMedicalPriorities().size() > 0);
    }
    
    @Test
    @DisplayName("Test Medical Procedures")
    void testMedicalProcedures() {
        Map<String, MedicalSystem.MedicalProcedure> procedures = medicalSystem.getAllMedicalProcedures();
        assertNotNull(procedures);
        assertTrue(procedures.size() > 0);
        
        // Test specific procedure
        MedicalSystem.MedicalProcedure procedure = procedures.get("BASIC_FIRST_AID_001");
        assertNotNull(procedure);
        assertEquals("Basic First Aid", procedure.getProcedureName());
        assertEquals(MedicalSystem.TreatmentType.BASIC_FIRST_AID, procedure.getTreatmentType());
        assertTrue(procedure.getEffectiveness() > 0);
    }
    
    @Test
    @DisplayName("Test Medical Facilities")
    void testMedicalFacilities() {
        Map<String, MedicalSystem.MedicalFacility> facilities = medicalSystem.getAllMedicalFacilities();
        assertNotNull(facilities);
        assertTrue(facilities.size() > 0);
        
        // Test specific facility
        MedicalSystem.MedicalFacility facility = facilities.get("FACILITY_001");
        assertNotNull(facility);
        assertEquals("Medical Clinic Alpha", facility.getFacilityName());
        assertEquals(MedicalSystem.FacilityType.MEDICAL_CLINIC, facility.getFacilityType());
        assertTrue(facility.getCapacity() > 0);
    }
    
    @Test
    @DisplayName("Test Medical Equipment")
    void testMedicalEquipment() {
        Map<String, MedicalSystem.MedicalEquipment> equipment = medicalSystem.getAllMedicalEquipment();
        assertNotNull(equipment);
        assertTrue(equipment.size() > 0);
        
        // Test specific equipment
        MedicalSystem.MedicalEquipment medikit = equipment.get("MEDIKIT_001");
        assertNotNull(medikit);
        assertEquals("Standard Medikit", medikit.getEquipmentName());
        assertEquals(MedicalSystem.EquipmentType.MEDIKIT, medikit.getEquipmentType());
        assertTrue(medikit.getEffectiveness() > 0);
        
        // Test advanced equipment
        assertTrue(equipment.containsKey("ADVANCED_MEDIKIT_001"));
    }
    
    @Test
    @DisplayName("Test Medical Priorities")
    void testMedicalPriorities() {
        Map<String, MedicalSystem.MedicalPriority> priorities = medicalSystem.getAllMedicalPriorities();
        assertNotNull(priorities);
        assertTrue(priorities.size() > 0);
        
        // Test specific priority
        MedicalSystem.MedicalPriority priority = priorities.get("PRIORITY_001");
        assertNotNull(priority);
        assertEquals("Critical Patient", priority.getPriorityName());
        assertEquals(MedicalSystem.MedicalPriority.PriorityLevel.CRITICAL, priority.getPriorityLevel());
        assertTrue(priority.getResponseTime() > 0);
    }
    
    @Test
    @DisplayName("Test Treatment Management")
    void testTreatmentManagement() {
        String patientId = "PATIENT_001";
        String procedureId = "BASIC_FIRST_AID_001";
        String doctorId = "DR_TEST";
        String facilityId = "FACILITY_001";
        
        boolean result = medicalSystem.startTreatment(patientId, procedureId, doctorId, facilityId);
        assertTrue(result);
        assertTrue(medicalSystem.getAllMedicalTreatments().size() > 0);
    }
    
    @Test
    @DisplayName("Test Medical System Statistics")
    void testMedicalSystemStatistics() {
        Map<String, Object> stats = medicalSystem.getMedicalSystemStatistics();
        assertNotNull(stats);
        assertTrue(stats.size() > 0);
        
        // Test specific statistics
        assertTrue(stats.containsKey("totalPatients"));
        assertTrue(stats.containsKey("totalTreatments"));
        assertTrue(stats.containsKey("facilityUtilization"));
        assertTrue(stats.containsKey("treatmentSuccessRate"));
    }
    
    @Test
    @DisplayName("Test Medical Priority Management")
    void testMedicalPriorityManagement() {
        String priorityId = "PRIORITY_001";
        
        boolean result = medicalSystem.updateMedicalPriority(
            priorityId, 
            MedicalSystem.MedicalPriority.PriorityLevel.HIGH,
            "Updated priority level"
        );
        assertTrue(result);
        
        result = medicalSystem.escalateMedicalPriority(priorityId, "Patient condition worsened");
        assertTrue(result);
    }
    
    @Test
    @DisplayName("Test Facility Management")
    void testFacilityManagement() {
        String facilityId = "FACILITY_001";
        
        Map<String, Object> capacityInfo = medicalSystem.getFacilityCapacityInfo(facilityId);
        assertNotNull(capacityInfo);
        assertTrue(capacityInfo.containsKey("currentCapacity"));
        assertTrue(capacityInfo.containsKey("maxCapacity"));
        assertTrue(capacityInfo.containsKey("utilizationRate"));
    }
    
    @Test
    @DisplayName("Test Equipment Assignment")
    void testEquipmentAssignment() {
        String equipmentId = "MEDIKIT_001";
        String facilityId = "FACILITY_001";
        
        boolean result = medicalSystem.assignEquipmentToFacility(equipmentId, facilityId);
        assertTrue(result);
    }
    
    @Test
    @DisplayName("Test Doctor Assignment")
    void testDoctorAssignment() {
        String doctorId = "DR_TEST";
        String facilityId = "FACILITY_001";
        
        boolean result = medicalSystem.assignDoctorToFacility(doctorId, facilityId);
        assertTrue(result);
    }
    
    @Test
    @DisplayName("Test Medical System Activation")
    void testMedicalSystemActivation() {
        assertFalse(medicalSystem.isMedicalSystemActive());
        
        boolean result = medicalSystem.activateMedicalSystem();
        assertTrue(result);
        assertTrue(medicalSystem.isMedicalSystemActive());
        
        result = medicalSystem.deactivateMedicalSystem();
        assertTrue(result);
        assertFalse(medicalSystem.isMedicalSystemActive());
    }
    
    @Test
    @DisplayName("Test Treatment Recommendations")
    void testTreatmentRecommendations() {
        String patientId = "PATIENT_001";
        
        List<MedicalSystem.MedicalProcedure> recommendations =
            medicalSystem.getTreatmentRecommendations(patientId);
        assertNotNull(recommendations);
        assertTrue(recommendations.size() > 0);
        
        // Test recommendation properties
        MedicalSystem.MedicalProcedure recommendation = recommendations.get(0);
        assertNotNull(recommendation);
        assertTrue(recommendation.getEffectiveness() > 0);
    }
    
    @Test
    @DisplayName("Test Patient Transfer")
    void testPatientTransfer() {
        String patientId = "PATIENT_001";
        String fromFacilityId = "FACILITY_001";
        String toFacilityId = "FACILITY_002";
        
        // Start treatment first
        medicalSystem.startTreatment(patientId, "BASIC_FIRST_AID_001", "DR_TEST", fromFacilityId);
        
        boolean result = medicalSystem.transferPatient(patientId, fromFacilityId, toFacilityId);
        assertTrue(result);
    }
    
    @Test
    @DisplayName("Test Facility Maintenance")
    void testFacilityMaintenance() {
        String facilityId = "FACILITY_001";
        
        boolean result = medicalSystem.scheduleFacilityMaintenance(facilityId, "2024-01-15");
        assertTrue(result);
        
        result = medicalSystem.performFacilityMaintenance(facilityId);
        assertTrue(result);
    }
    
    @Test
    @DisplayName("Test Equipment Repair")
    void testEquipmentRepair() {
        String equipmentId = "MEDIKIT_001";
        
        boolean result = medicalSystem.repairMedicalEquipment(equipmentId);
        assertTrue(result);
    }
    
    @Test
    @DisplayName("Test Treatment Effectiveness Statistics")
    void testTreatmentEffectivenessStatistics() {
        Map<String, Double> effectivenessStats = medicalSystem.getTreatmentEffectivenessStatistics();
        assertNotNull(effectivenessStats);
        assertTrue(effectivenessStats.size() > 0);
    }
}
