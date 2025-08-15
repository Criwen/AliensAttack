package com.aliensattack.core.systems;

import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.util.*;
import java.util.Date;

/**
 * Advanced Medical System - XCOM 2 Tactical Combat
 * Implements comprehensive medical treatment, healing, stabilization, and medical facilities
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MedicalSystem {
    
    private String medicalSystemId;
    private Map<String, MedicalTreatment> medicalTreatments;
    private Map<String, MedicalFacility> medicalFacilities;
    private Map<String, MedicalEquipment> medicalEquipment;
    private Map<String, MedicalProcedure> medicalProcedures;
    private Map<String, MedicalPriority> medicalPriorities;
    private Map<String, List<String>> patientRecords;
    private Map<String, Map<String, Integer>> treatmentEffects;
    private Map<String, List<String>> activeTreatments;
    private Map<String, Integer> treatmentTimes;
    private Map<String, Boolean> treatmentStates;
    private int totalPatients;
    private int maxMedicalCapacity;
    private boolean isMedicalSystemActive;
    
    /**
     * Medical treatment types
     */
    public enum TreatmentType {
        BASIC_FIRST_AID,        // Basic first aid treatment
        ADVANCED_FIRST_AID,     // Advanced first aid treatment
        SURGERY,                // Surgical procedures
        EMERGENCY_SURGERY,      // Emergency surgical procedures
        PSYCHOLOGICAL_THERAPY,  // Psychological treatment
        PHYSICAL_THERAPY,       // Physical rehabilitation
        MEDICATION,             // Drug treatment
        REST_AND_RECOVERY,      // Rest-based recovery
        EXPERIMENTAL_TREATMENT, // Experimental medical procedures
        PSYCHIC_HEALING,        // Psionic healing abilities
        ALIEN_TECHNOLOGY,       // Alien technology treatment
        EMERGENCY_TREATMENT     // Emergency medical procedures
    }
    
    /**
     * Medical facility types
     */
    public enum FacilityType {
        FIRST_AID_STATION,      // Basic first aid station
        MEDICAL_CLINIC,         // Medical clinic
        HOSPITAL,               // Full hospital
        SURGICAL_CENTER,        // Surgical center
        PSYCHIATRIC_WARD,       // Psychiatric treatment
        REHABILITATION_CENTER,  // Physical rehabilitation
        RESEARCH_LABORATORY,    // Medical research
        EMERGENCY_ROOM,         // Emergency treatment
        INTENSIVE_CARE_UNIT,    // Intensive care
        QUARANTINE_WARD,        // Quarantine treatment
        ALIEN_TECHNOLOGY_LAB,   // Alien technology research
        PSYCHIC_HEALING_CENTER  // Psionic healing center
    }
    
    /**
     * Medical equipment types
     */
    public enum EquipmentType {
        MEDIKIT,                // Basic medical kit
        ADVANCED_MEDIKIT,       // Advanced medical kit
        SURGICAL_TOOLS,         // Surgical instruments
        DIAGNOSTIC_EQUIPMENT,   // Diagnostic tools
        MONITORING_DEVICES,     // Patient monitoring
        LIFE_SUPPORT_SYSTEMS,   // Life support equipment
        HEALING_DEVICES,        // Specialized healing equipment
        PSYCHIC_AMPLIFIERS,     // Psionic enhancement devices
        ALIEN_TECHNOLOGY_DEVICES, // Alien technology devices
        EMERGENCY_EQUIPMENT     // Emergency medical equipment
    }
    
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MedicalTreatment {
        private String treatmentId;
        private String patientId;
        private TreatmentType treatmentType;
        private int effectiveness;
        private int duration;
        private int currentDuration;
        private boolean isActive;
        private String description;
        private Map<String, Integer> effects;
        private List<String> abilities;
        private int cost;
        private String assignedDoctor;
        private String assignedFacility;
        private int treatmentProgress;
        private boolean isCompleted;
        private String completionDate;
        private String treatmentStatus;
        private String treatmentNotes;
        private Map<String, Integer> treatmentBonuses;
        private List<String> treatmentRequirements;
        private int treatmentCost;
        private String treatmentDescription;
        private String manufacturer;
        private String modelNumber;
        private String serialNumber;
        private String purchaseDate;
        private int purchaseCost;
        private String supplier;
        private String location;
        private String status;
    }
    
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MedicalFacility {
        private String facilityId;
        private String facilityName;
        private FacilityType facilityType;
        private int capacity;
        private int currentPatients;
        private int effectiveness;
        private int cost;
        private List<TreatmentType> availableTreatments;
        private String description;
        private Map<String, Integer> effects;
        private List<String> abilities;
        private String assignedDoctor;
        private String assignedTechnician;
        private boolean isOperational;
        private String facilityStatus;
        private int maintenanceCost;
        private int upgradeCost;
        private String facilityQuality;
        private String facilityCondition;
        private String lastMaintenanceDate;
        private String nextMaintenanceDate;
        private String warrantyStatus;
        private boolean isWarrantied;
        private String warrantyExpiryDate;
        private String facilityNotes;
        private Map<String, Integer> facilityStats;
        private List<String> facilityAbilities;
        private String manufacturer;
        private String modelNumber;
        private String serialNumber;
        private String purchaseDate;
        private int purchaseCost;
        private String supplier;
        private String location;
        private String status;
    }
    
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MedicalEquipment {
        private String equipmentId;
        private String equipmentName;
        private EquipmentType equipmentType;
        private int effectiveness;
        private int durability;
        private int currentDurability;
        private boolean isOperational;
        private String description;
        private Map<String, Integer> effects;
        private List<String> abilities;
        private int cost;
        private String assignedDoctor;
        private String assignedFacility;
        private boolean isInUse;
        private String equipmentStatus;
        private int maintenanceCost;
        private int repairCost;
        private String equipmentQuality;
        private String equipmentCondition;
        private String lastMaintenanceDate;
        private String nextMaintenanceDate;
        private String warrantyStatus;
        private boolean isWarrantied;
        private String warrantyExpiryDate;
        private String equipmentNotes;
        private Map<String, Integer> equipmentStats;
        private List<String> equipmentAbilities;
        private String manufacturer;
        private String modelNumber;
        private String serialNumber;
        private String purchaseDate;
        private int purchaseCost;
        private String supplier;
        private String location;
        private String status;
    }
    
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MedicalProcedure {
        private String procedureId;
        private String procedureName;
        private TreatmentType procedureType;
        private int effectiveness;
        private int duration;
        private int currentDuration;
        private boolean isActive;
        private String description;
        private Map<String, Integer> effects;
        private List<String> abilities;
        private int cost;
        private String assignedDoctor;
        private String assignedFacility;
        private int procedureProgress;
        private boolean isCompleted;
        private String completionDate;
        private String procedureStatus;
        private String procedureNotes;
        private Map<String, Integer> procedureBonuses;
        private List<String> procedureRequirements;
        private int procedureCost;
        private String procedureDescription;
        private String manufacturer;
        private String modelNumber;
        private String serialNumber;
        private String purchaseDate;
        private int purchaseCost;
        private String supplier;
        private String location;
        private String status;
    }
    
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MedicalPriority {
        private String priorityId;
        private String patientId;
        private PriorityLevel priorityLevel;
        private String priorityReason;
        private int priorityScore;
        private boolean isUrgent;
        private boolean isCritical;
        private String assignedDoctor;
        private String assignedFacility;
        private int waitTime;
        private String priorityStatus;
        private String priorityNotes;
        private Date priorityDate;
        private String priorityCategory;
        private int priorityDuration;
        private boolean isEscalated;
        private String escalationReason;
        
        public enum PriorityLevel {
            LOW,                // Low priority
            NORMAL,             // Normal priority
            HIGH,               // High priority
            URGENT,             // Urgent priority
            CRITICAL,           // Critical priority
            EMERGENCY,          // Emergency priority
            TRAUMA,             // Trauma priority
            INFECTION,          // Infection priority
            REHABILITATION,     // Rehabilitation priority
            RESEARCH,           // Research priority
            PSIONIC,            // Psionic priority
            ALIEN_TECHNOLOGY    // Alien technology priority
        }
    }
    
    /**
     * Initialize the medical system
     */
    public void initializeSystem() {
        this.medicalSystemId = "MEDICAL_SYSTEM_" + System.currentTimeMillis();
        this.medicalTreatments = new HashMap<>();
        this.medicalFacilities = new HashMap<>();
        this.medicalEquipment = new HashMap<>();
        this.medicalProcedures = new HashMap<>();
        this.medicalPriorities = new HashMap<>();
        this.patientRecords = new HashMap<>();
        this.treatmentEffects = new HashMap<>();
        this.activeTreatments = new HashMap<>();
        this.treatmentTimes = new HashMap<>();
        this.treatmentStates = new HashMap<>();
        this.totalPatients = 0;
        this.maxMedicalCapacity = 50;
        this.isMedicalSystemActive = false;
        
        initializeMedicalFacilities();
        initializeMedicalEquipment();
        initializeMedicalProcedures();
        initializeMedicalPriorities();
    }
    
    /**
     * Initialize medical facilities
     */
    private void initializeMedicalFacilities() {
        // First Aid Station
        MedicalFacility firstAidStation = MedicalFacility.builder()
            .facilityId("FIRST_AID_STATION_001")
            .facilityName("First Aid Station")
            .facilityType(FacilityType.FIRST_AID_STATION)
            .capacity(5)
            .currentPatients(0)
            .effectiveness(60)
            .cost(1000)
            .availableTreatments(Arrays.asList(TreatmentType.BASIC_FIRST_AID, TreatmentType.REST_AND_RECOVERY))
            .description("Basic first aid station for minor injuries")
            .effects(Map.of("healing", 60, "capacity", 5, "cost", 1000))
            .abilities(Arrays.asList("BASIC_HEALING", "REST_RECOVERY", "MINOR_TREATMENT"))
            .assignedDoctor("")
            .assignedTechnician("")
            .isOperational(true)
            .facilityStatus("OPERATIONAL")
            .maintenanceCost(100)
            .upgradeCost(500)
            .facilityQuality("GOOD")
            .facilityCondition("NEW")
            .lastMaintenanceDate("")
            .nextMaintenanceDate("")
            .warrantyStatus("ACTIVE")
            .isWarrantied(true)
            .warrantyExpiryDate("")
            .facilityNotes("Basic first aid station")
            .facilityStats(Map.of("healing", 60, "capacity", 5))
            .facilityAbilities(Arrays.asList("BASIC_HEALING", "REST_RECOVERY"))
            .manufacturer("XCOM_MEDICAL")
            .modelNumber("FACILITY_FIRST_AID_001")
            .serialNumber("SN_FIRST_AID_001")
            .purchaseDate(new Date().toString())
            .purchaseCost(1000)
            .supplier("XCOM_SUPPLY")
            .location("MAIN_BASE")
            .status("ACTIVE")
            .build();
        medicalFacilities.put(firstAidStation.getFacilityId(), firstAidStation);
        
        // Medical Clinic
        MedicalFacility medicalClinic = MedicalFacility.builder()
            .facilityId("MEDICAL_CLINIC_001")
            .facilityName("Medical Clinic")
            .facilityType(FacilityType.MEDICAL_CLINIC)
            .capacity(15)
            .currentPatients(0)
            .effectiveness(75)
            .cost(5000)
            .availableTreatments(Arrays.asList(TreatmentType.BASIC_FIRST_AID, TreatmentType.ADVANCED_FIRST_AID, 
                                             TreatmentType.MEDICATION, TreatmentType.PHYSICAL_THERAPY))
            .description("Full medical clinic for comprehensive treatment")
            .effects(Map.of("healing", 75, "capacity", 15, "cost", 5000))
            .abilities(Arrays.asList("ADVANCED_HEALING", "MEDICATION_TREATMENT", "PHYSICAL_THERAPY"))
            .assignedDoctor("")
            .assignedTechnician("")
            .isOperational(true)
            .facilityStatus("OPERATIONAL")
            .maintenanceCost(250)
            .upgradeCost(1500)
            .facilityQuality("GOOD")
            .facilityCondition("NEW")
            .lastMaintenanceDate("")
            .nextMaintenanceDate("")
            .warrantyStatus("ACTIVE")
            .isWarrantied(true)
            .warrantyExpiryDate("")
            .facilityNotes("Full medical clinic")
            .facilityStats(Map.of("healing", 75, "capacity", 15))
            .facilityAbilities(Arrays.asList("ADVANCED_HEALING", "MEDICATION_TREATMENT"))
            .manufacturer("XCOM_MEDICAL")
            .modelNumber("FACILITY_CLINIC_001")
            .serialNumber("SN_CLINIC_001")
            .purchaseDate(new Date().toString())
            .purchaseCost(5000)
            .supplier("XCOM_SUPPLY")
            .location("MAIN_BASE")
            .status("ACTIVE")
            .build();
        medicalFacilities.put(medicalClinic.getFacilityId(), medicalClinic);
        
        // Hospital
        MedicalFacility hospital = MedicalFacility.builder()
            .facilityId("HOSPITAL_001")
            .facilityName("XCOM Hospital")
            .facilityType(FacilityType.HOSPITAL)
            .capacity(30)
            .currentPatients(0)
            .effectiveness(90)
            .cost(15000)
            .availableTreatments(Arrays.asList(TreatmentType.BASIC_FIRST_AID, TreatmentType.ADVANCED_FIRST_AID,
                                             TreatmentType.SURGERY, TreatmentType.EMERGENCY_SURGERY,
                                             TreatmentType.PSYCHOLOGICAL_THERAPY, TreatmentType.PHYSICAL_THERAPY,
                                             TreatmentType.MEDICATION, TreatmentType.REST_AND_RECOVERY))
            .description("Full hospital with comprehensive medical capabilities")
            .effects(Map.of("healing", 90, "capacity", 30, "cost", 15000))
            .abilities(Arrays.asList("COMPREHENSIVE_HEALING", "SURGICAL_TREATMENT", "EMERGENCY_CARE"))
            .assignedDoctor("")
            .assignedTechnician("")
            .isOperational(true)
            .facilityStatus("OPERATIONAL")
            .maintenanceCost(500)
            .upgradeCost(3000)
            .facilityQuality("EXCELLENT")
            .facilityCondition("NEW")
            .lastMaintenanceDate("")
            .nextMaintenanceDate("")
            .warrantyStatus("ACTIVE")
            .isWarrantied(true)
            .warrantyExpiryDate("")
            .facilityNotes("Full hospital facility")
            .facilityStats(Map.of("healing", 90, "capacity", 30))
            .facilityAbilities(Arrays.asList("COMPREHENSIVE_HEALING", "SURGICAL_TREATMENT"))
            .manufacturer("XCOM_MEDICAL")
            .modelNumber("FACILITY_HOSPITAL_001")
            .serialNumber("SN_HOSPITAL_001")
            .purchaseDate(new Date().toString())
            .purchaseCost(15000)
            .supplier("XCOM_SUPPLY")
            .location("MAIN_BASE")
            .status("ACTIVE")
            .build();
        medicalFacilities.put(hospital.getFacilityId(), hospital);
        
        // Surgical Center
        MedicalFacility surgicalCenter = MedicalFacility.builder()
            .facilityId("SURGICAL_CENTER_001")
            .facilityName("Surgical Center")
            .facilityType(FacilityType.SURGICAL_CENTER)
            .capacity(20)
            .currentPatients(0)
            .effectiveness(95)
            .cost(12000)
            .availableTreatments(Arrays.asList(TreatmentType.SURGERY, TreatmentType.EMERGENCY_SURGERY,
                                             TreatmentType.ADVANCED_FIRST_AID))
            .description("Specialized surgical center for complex procedures")
            .effects(Map.of("surgical", 95, "capacity", 20, "cost", 12000))
            .abilities(Arrays.asList("SURGICAL_TREATMENT", "COMPLEX_PROCEDURES", "SURGICAL_TRAINING"))
            .assignedDoctor("")
            .assignedTechnician("")
            .isOperational(true)
            .facilityStatus("OPERATIONAL")
            .maintenanceCost(400)
            .upgradeCost(2500)
            .facilityQuality("EXCELLENT")
            .facilityCondition("NEW")
            .lastMaintenanceDate("")
            .nextMaintenanceDate("")
            .warrantyStatus("ACTIVE")
            .isWarrantied(true)
            .warrantyExpiryDate("")
            .facilityNotes("Specialized surgical center")
            .facilityStats(Map.of("surgical", 95, "capacity", 20))
            .facilityAbilities(Arrays.asList("SURGICAL_TREATMENT", "COMPLEX_PROCEDURES"))
            .manufacturer("XCOM_MEDICAL")
            .modelNumber("FACILITY_SURGICAL_001")
            .serialNumber("SN_SURGICAL_001")
            .purchaseDate(new Date().toString())
            .purchaseCost(12000)
            .supplier("XCOM_SUPPLY")
            .location("MAIN_BASE")
            .status("ACTIVE")
            .build();
        medicalFacilities.put(surgicalCenter.getFacilityId(), surgicalCenter);
        
        // Psychiatric Ward
        MedicalFacility psychiatricWard = MedicalFacility.builder()
            .facilityId("PSYCHIATRIC_WARD_001")
            .facilityName("Psychiatric Ward")
            .facilityType(FacilityType.PSYCHIATRIC_WARD)
            .capacity(15)
            .currentPatients(0)
            .effectiveness(80)
            .cost(8000)
            .availableTreatments(Arrays.asList(TreatmentType.PSYCHOLOGICAL_THERAPY, TreatmentType.MEDICATION,
                                             TreatmentType.REST_AND_RECOVERY))
            .description("Specialized psychiatric treatment facility")
            .effects(Map.of("mental_health", 80, "capacity", 15, "cost", 8000))
            .abilities(Arrays.asList("PSYCHIATRIC_CARE", "MENTAL_HEALTH_TREATMENT", "PSYCHOLOGICAL_THERAPY"))
            .assignedDoctor("")
            .assignedTechnician("")
            .isOperational(true)
            .facilityStatus("OPERATIONAL")
            .maintenanceCost(300)
            .upgradeCost(2000)
            .facilityQuality("GOOD")
            .facilityCondition("NEW")
            .lastMaintenanceDate("")
            .nextMaintenanceDate("")
            .warrantyStatus("ACTIVE")
            .isWarrantied(true)
            .warrantyExpiryDate("")
            .facilityNotes("Psychiatric treatment facility")
            .facilityStats(Map.of("mental_health", 80, "capacity", 15))
            .facilityAbilities(Arrays.asList("PSYCHIATRIC_CARE", "MENTAL_HEALTH_TREATMENT"))
            .manufacturer("XCOM_MEDICAL")
            .modelNumber("FACILITY_PSYCH_001")
            .serialNumber("SN_PSYCH_001")
            .purchaseDate(new Date().toString())
            .purchaseCost(8000)
            .supplier("XCOM_SUPPLY")
            .location("MAIN_BASE")
            .status("ACTIVE")
            .build();
        medicalFacilities.put(psychiatricWard.getFacilityId(), psychiatricWard);
        
        // Rehabilitation Center
        MedicalFacility rehabilitationCenter = MedicalFacility.builder()
            .facilityId("REHABILITATION_CENTER_001")
            .facilityName("Rehabilitation Center")
            .facilityType(FacilityType.REHABILITATION_CENTER)
            .capacity(25)
            .currentPatients(0)
            .effectiveness(75)
            .cost(10000)
            .availableTreatments(Arrays.asList(TreatmentType.PHYSICAL_THERAPY, TreatmentType.PSYCHOLOGICAL_THERAPY,
                                             TreatmentType.REST_AND_RECOVERY))
            .description("Comprehensive rehabilitation facility for physical and mental recovery")
            .effects(Map.of("rehabilitation", 75, "capacity", 25, "cost", 10000))
            .abilities(Arrays.asList("PHYSICAL_THERAPY", "MENTAL_REHABILITATION", "RECOVERY_PROGRAMS"))
            .assignedDoctor("")
            .assignedTechnician("")
            .isOperational(true)
            .facilityStatus("OPERATIONAL")
            .maintenanceCost(350)
            .upgradeCost(2200)
            .facilityQuality("GOOD")
            .facilityCondition("NEW")
            .lastMaintenanceDate("")
            .nextMaintenanceDate("")
            .warrantyStatus("ACTIVE")
            .isWarrantied(true)
            .warrantyExpiryDate("")
            .facilityNotes("Comprehensive rehabilitation facility")
            .facilityStats(Map.of("rehabilitation", 75, "capacity", 25))
            .facilityAbilities(Arrays.asList("PHYSICAL_THERAPY", "MENTAL_REHABILITATION"))
            .manufacturer("XCOM_MEDICAL")
            .modelNumber("FACILITY_REHAB_001")
            .serialNumber("SN_REHAB_001")
            .purchaseDate(new Date().toString())
            .purchaseCost(10000)
            .supplier("XCOM_SUPPLY")
            .location("MAIN_BASE")
            .status("ACTIVE")
            .build();
        medicalFacilities.put(rehabilitationCenter.getFacilityId(), rehabilitationCenter);
        
        // Research Laboratory
        MedicalFacility researchLaboratory = MedicalFacility.builder()
            .facilityId("RESEARCH_LABORATORY_001")
            .facilityName("Medical Research Laboratory")
            .facilityType(FacilityType.RESEARCH_LABORATORY)
            .capacity(10)
            .currentPatients(0)
            .effectiveness(70)
            .cost(15000)
            .availableTreatments(Arrays.asList(TreatmentType.EXPERIMENTAL_TREATMENT, TreatmentType.ALIEN_TECHNOLOGY))
            .description("Advanced medical research facility for experimental treatments")
            .effects(Map.of("research", 70, "capacity", 10, "cost", 15000))
            .abilities(Arrays.asList("MEDICAL_RESEARCH", "EXPERIMENTAL_TREATMENTS", "TECHNOLOGY_DEVELOPMENT"))
            .assignedDoctor("")
            .assignedTechnician("")
            .isOperational(true)
            .facilityStatus("OPERATIONAL")
            .maintenanceCost(600)
            .upgradeCost(4000)
            .facilityQuality("EXCELLENT")
            .facilityCondition("NEW")
            .lastMaintenanceDate("")
            .nextMaintenanceDate("")
            .warrantyStatus("ACTIVE")
            .isWarrantied(true)
            .warrantyExpiryDate("")
            .facilityNotes("Advanced medical research facility")
            .facilityStats(Map.of("research", 70, "capacity", 10))
            .facilityAbilities(Arrays.asList("MEDICAL_RESEARCH", "EXPERIMENTAL_TREATMENTS"))
            .manufacturer("XCOM_MEDICAL")
            .modelNumber("FACILITY_RESEARCH_001")
            .serialNumber("SN_RESEARCH_001")
            .purchaseDate(new Date().toString())
            .purchaseCost(15000)
            .supplier("XCOM_SUPPLY")
            .location("MAIN_BASE")
            .status("ACTIVE")
            .build();
        medicalFacilities.put(researchLaboratory.getFacilityId(), researchLaboratory);
        
        // Emergency Room
        MedicalFacility emergencyRoom = MedicalFacility.builder()
            .facilityId("EMERGENCY_ROOM_001")
            .facilityName("Emergency Room")
            .facilityType(FacilityType.EMERGENCY_ROOM)
            .capacity(20)
            .currentPatients(0)
            .effectiveness(85)
            .cost(12000)
            .availableTreatments(Arrays.asList(TreatmentType.EMERGENCY_TREATMENT, TreatmentType.EMERGENCY_SURGERY,
                                             TreatmentType.BASIC_FIRST_AID, TreatmentType.ADVANCED_FIRST_AID))
            .description("Emergency medical treatment facility for critical care")
            .effects(Map.of("emergency_care", 85, "capacity", 20, "cost", 12000))
            .abilities(Arrays.asList("EMERGENCY_CARE", "CRITICAL_TREATMENT", "RAPID_RESPONSE"))
            .assignedDoctor("")
            .assignedTechnician("")
            .isOperational(true)
            .facilityStatus("OPERATIONAL")
            .maintenanceCost(450)
            .upgradeCost(2800)
            .facilityQuality("EXCELLENT")
            .facilityCondition("NEW")
            .lastMaintenanceDate("")
            .nextMaintenanceDate("")
            .warrantyStatus("ACTIVE")
            .isWarrantied(true)
            .warrantyExpiryDate("")
            .facilityNotes("Emergency medical treatment facility")
            .facilityStats(Map.of("emergency_care", 85, "capacity", 20))
            .facilityAbilities(Arrays.asList("EMERGENCY_CARE", "CRITICAL_TREATMENT"))
            .manufacturer("XCOM_MEDICAL")
            .modelNumber("FACILITY_EMERGENCY_001")
            .serialNumber("SN_EMERGENCY_001")
            .purchaseDate(new Date().toString())
            .purchaseCost(12000)
            .supplier("XCOM_SUPPLY")
            .location("MAIN_BASE")
            .status("ACTIVE")
            .build();
        medicalFacilities.put(emergencyRoom.getFacilityId(), emergencyRoom);
        
        // Intensive Care Unit
        MedicalFacility intensiveCareUnit = MedicalFacility.builder()
            .facilityId("INTENSIVE_CARE_UNIT_001")
            .facilityName("Intensive Care Unit")
            .facilityType(FacilityType.INTENSIVE_CARE_UNIT)
            .capacity(8)
            .currentPatients(0)
            .effectiveness(95)
            .cost(20000)
            .availableTreatments(Arrays.asList(TreatmentType.EMERGENCY_TREATMENT, TreatmentType.MEDICATION,
                                             TreatmentType.REST_AND_RECOVERY))
            .description("Specialized intensive care facility for critically ill patients")
            .effects(Map.of("intensive_care", 95, "capacity", 8, "cost", 20000))
            .abilities(Arrays.asList("INTENSIVE_CARE", "CRITICAL_MONITORING", "LIFE_SUPPORT"))
            .assignedDoctor("")
            .assignedTechnician("")
            .isOperational(true)
            .facilityStatus("OPERATIONAL")
            .maintenanceCost(800)
            .upgradeCost(5000)
            .facilityQuality("EXCELLENT")
            .facilityCondition("NEW")
            .lastMaintenanceDate("")
            .nextMaintenanceDate("")
            .warrantyStatus("ACTIVE")
            .isWarrantied(true)
            .warrantyExpiryDate("")
            .facilityNotes("Specialized intensive care facility")
            .facilityStats(Map.of("intensive_care", 95, "capacity", 8))
            .facilityAbilities(Arrays.asList("INTENSIVE_CARE", "CRITICAL_MONITORING"))
            .manufacturer("XCOM_MEDICAL")
            .modelNumber("FACILITY_ICU_001")
            .serialNumber("SN_ICU_001")
            .purchaseDate(new Date().toString())
            .purchaseCost(20000)
            .supplier("XCOM_SUPPLY")
            .location("MAIN_BASE")
            .status("ACTIVE")
            .build();
        medicalFacilities.put(intensiveCareUnit.getFacilityId(), intensiveCareUnit);
        
        // Quarantine Ward
        MedicalFacility quarantineWard = MedicalFacility.builder()
            .facilityId("QUARANTINE_WARD_001")
            .facilityName("Quarantine Ward")
            .facilityType(FacilityType.QUARANTINE_WARD)
            .capacity(12)
            .currentPatients(0)
            .effectiveness(60)
            .cost(8000)
            .availableTreatments(Arrays.asList(TreatmentType.MEDICATION, TreatmentType.REST_AND_RECOVERY))
            .description("Isolated quarantine facility for infectious diseases")
            .effects(Map.of("quarantine", 60, "capacity", 12, "cost", 8000))
            .abilities(Arrays.asList("INFECTION_CONTROL", "ISOLATION_TREATMENT", "DISEASE_CONTAINMENT"))
            .assignedDoctor("")
            .assignedTechnician("")
            .isOperational(true)
            .facilityStatus("OPERATIONAL")
            .maintenanceCost(300)
            .upgradeCost(2000)
            .facilityQuality("GOOD")
            .facilityCondition("NEW")
            .lastMaintenanceDate("")
            .nextMaintenanceDate("")
            .warrantyStatus("ACTIVE")
            .isWarrantied(true)
            .warrantyExpiryDate("")
            .facilityNotes("Isolated quarantine facility")
            .facilityStats(Map.of("quarantine", 60, "capacity", 12))
            .facilityAbilities(Arrays.asList("INFECTION_CONTROL", "ISOLATION_TREATMENT"))
            .manufacturer("XCOM_MEDICAL")
            .modelNumber("FACILITY_QUARANTINE_001")
            .serialNumber("SN_QUARANTINE_001")
            .purchaseDate(new Date().toString())
            .purchaseCost(8000)
            .supplier("XCOM_SUPPLY")
            .location("MAIN_BASE")
            .status("ACTIVE")
            .build();
        medicalFacilities.put(quarantineWard.getFacilityId(), quarantineWard);
        
        // Alien Technology Lab
        MedicalFacility alienTechnologyLab = MedicalFacility.builder()
            .facilityId("ALIEN_TECHNOLOGY_LAB_001")
            .facilityName("Alien Technology Laboratory")
            .facilityType(FacilityType.ALIEN_TECHNOLOGY_LAB)
            .capacity(5)
            .currentPatients(0)
            .effectiveness(90)
            .cost(25000)
            .availableTreatments(Arrays.asList(TreatmentType.ALIEN_TECHNOLOGY, TreatmentType.EXPERIMENTAL_TREATMENT))
            .description("Advanced facility for alien technology research and treatment")
            .effects(Map.of("alien_technology", 90, "capacity", 5, "cost", 25000))
            .abilities(Arrays.asList("ALIEN_TECHNOLOGY", "ADVANCED_RESEARCH", "FUTURISTIC_TREATMENT"))
            .assignedDoctor("")
            .assignedTechnician("")
            .isOperational(true)
            .facilityStatus("OPERATIONAL")
            .maintenanceCost(1000)
            .upgradeCost(6000)
            .facilityQuality("EXCELLENT")
            .facilityCondition("NEW")
            .lastMaintenanceDate("")
            .nextMaintenanceDate("")
            .warrantyStatus("ACTIVE")
            .isWarrantied(true)
            .warrantyExpiryDate("")
            .facilityNotes("Advanced alien technology facility")
            .facilityStats(Map.of("alien_technology", 90, "capacity", 5))
            .facilityAbilities(Arrays.asList("ALIEN_TECHNOLOGY", "ADVANCED_RESEARCH"))
            .manufacturer("XCOM_MEDICAL")
            .modelNumber("FACILITY_ALIEN_001")
            .serialNumber("SN_ALIEN_001")
            .purchaseDate(new Date().toString())
            .purchaseCost(25000)
            .supplier("XCOM_SUPPLY")
            .location("MAIN_BASE")
            .status("ACTIVE")
            .build();
        medicalFacilities.put(alienTechnologyLab.getFacilityId(), alienTechnologyLab);
        
        // Psionic Healing Center
        MedicalFacility psionicHealingCenter = MedicalFacility.builder()
            .facilityId("PSYCHIC_HEALING_CENTER_001")
            .facilityName("Psionic Healing Center")
            .facilityType(FacilityType.PSYCHIC_HEALING_CENTER)
            .capacity(10)
            .currentPatients(0)
            .effectiveness(85)
            .cost(18000)
            .availableTreatments(Arrays.asList(TreatmentType.PSYCHIC_HEALING, TreatmentType.PSYCHOLOGICAL_THERAPY))
            .description("Specialized facility for psionic healing and mental restoration")
            .effects(Map.of("psionic_healing", 85, "capacity", 10, "cost", 18000))
            .abilities(Arrays.asList("PSIONIC_HEALING", "MENTAL_RESTORATION", "ENERGY_HEALING"))
            .assignedDoctor("")
            .assignedTechnician("")
            .isOperational(true)
            .facilityStatus("OPERATIONAL")
            .maintenanceCost(700)
            .upgradeCost(4500)
            .facilityQuality("EXCELLENT")
            .facilityCondition("NEW")
            .lastMaintenanceDate("")
            .nextMaintenanceDate("")
            .warrantyStatus("ACTIVE")
            .isWarrantied(true)
            .warrantyExpiryDate("")
            .facilityNotes("Specialized psionic healing facility")
            .facilityStats(Map.of("psionic_healing", 85, "capacity", 10))
            .facilityAbilities(Arrays.asList("PSIONIC_HEALING", "MENTAL_RESTORATION"))
            .manufacturer("XCOM_MEDICAL")
            .modelNumber("FACILITY_PSIONIC_001")
            .serialNumber("SN_PSIONIC_001")
            .purchaseDate(new Date().toString())
            .purchaseCost(18000)
            .supplier("XCOM_SUPPLY")
            .location("MAIN_BASE")
            .status("ACTIVE")
            .build();
        medicalFacilities.put(psionicHealingCenter.getFacilityId(), psionicHealingCenter);
    }
    
    /**
     * Initialize medical equipment
     */
    private void initializeMedicalEquipment() {
        // Basic Medikit
        MedicalEquipment basicMedikit = MedicalEquipment.builder()
            .equipmentId("BASIC_MEDIKIT_001")
            .equipmentName("Basic Medikit")
            .equipmentType(EquipmentType.MEDIKIT)
            .effectiveness(50)
            .durability(100)
            .currentDurability(100)
            .isOperational(true)
            .description("Basic medical kit for field treatment")
            .effects(Map.of("healing", 50, "durability", 100, "portability", 80))
            .abilities(Arrays.asList("BASIC_HEALING", "FIELD_TREATMENT", "PORTABLE"))
            .cost(200)
            .assignedDoctor("")
            .assignedFacility("")
            .isInUse(false)
            .equipmentStatus("AVAILABLE")
            .maintenanceCost(20)
            .repairCost(50)
            .equipmentQuality("GOOD")
            .equipmentCondition("NEW")
            .lastMaintenanceDate("")
            .nextMaintenanceDate("")
            .warrantyStatus("ACTIVE")
            .isWarrantied(true)
            .warrantyExpiryDate("")
            .equipmentNotes("Basic field medical kit")
            .equipmentStats(Map.of("healing", 50, "durability", 100))
            .equipmentAbilities(Arrays.asList("BASIC_HEALING", "FIELD_TREATMENT"))
            .manufacturer("XCOM_MEDICAL")
            .modelNumber("EQUIPMENT_MEDIKIT_001")
            .serialNumber("SN_MEDIKIT_001")
            .purchaseDate(new Date().toString())
            .purchaseCost(200)
            .supplier("XCOM_SUPPLY")
            .location("MEDICAL_ARMORY")
            .status("ACTIVE")
            .build();
        medicalEquipment.put(basicMedikit.getEquipmentId(), basicMedikit);
        
        // Advanced Medikit
        MedicalEquipment advancedMedikit = MedicalEquipment.builder()
            .equipmentId("ADVANCED_MEDIKIT_001")
            .equipmentName("Advanced Medikit")
            .equipmentType(EquipmentType.ADVANCED_MEDIKIT)
            .effectiveness(75)
            .durability(150)
            .currentDurability(150)
            .isOperational(true)
            .description("Advanced medical kit with enhanced capabilities")
            .effects(Map.of("healing", 75, "durability", 150, "portability", 70))
            .abilities(Arrays.asList("ADVANCED_HEALING", "ENHANCED_TREATMENT", "SPECIALIZED_CARE"))
            .cost(500)
            .assignedDoctor("")
            .assignedFacility("")
            .isInUse(false)
            .equipmentStatus("AVAILABLE")
            .maintenanceCost(50)
            .repairCost(100)
            .equipmentQuality("EXCELLENT")
            .equipmentCondition("NEW")
            .lastMaintenanceDate("")
            .nextMaintenanceDate("")
            .warrantyStatus("ACTIVE")
            .isWarrantied(true)
            .warrantyExpiryDate("")
            .equipmentNotes("Advanced field medical kit")
            .equipmentStats(Map.of("healing", 75, "durability", 150))
            .equipmentAbilities(Arrays.asList("ADVANCED_HEALING", "ENHANCED_TREATMENT"))
            .manufacturer("XCOM_MEDICAL")
            .modelNumber("EQUIPMENT_ADV_MEDIKIT_001")
            .serialNumber("SN_ADV_MEDIKIT_001")
            .purchaseDate(new Date().toString())
            .purchaseCost(500)
            .supplier("XCOM_SUPPLY")
            .location("MEDICAL_ARMORY")
            .status("ACTIVE")
            .build();
        medicalEquipment.put(advancedMedikit.getEquipmentId(), advancedMedikit);
        
        // Surgical Tools
        MedicalEquipment surgicalTools = MedicalEquipment.builder()
            .equipmentId("SURGICAL_TOOLS_001")
            .equipmentName("Surgical Tools")
            .equipmentType(EquipmentType.SURGICAL_TOOLS)
            .effectiveness(90)
            .durability(200)
            .currentDurability(200)
            .isOperational(true)
            .description("Professional surgical instruments")
            .effects(Map.of("surgical", 90, "durability", 200, "precision", 95))
            .abilities(Arrays.asList("SURGICAL_PROCEDURES", "HIGH_PRECISION", "PROFESSIONAL_GRADE"))
            .cost(2000)
            .assignedDoctor("")
            .assignedFacility("")
            .isInUse(false)
            .equipmentStatus("AVAILABLE")
            .maintenanceCost(100)
            .repairCost(300)
            .equipmentQuality("EXCELLENT")
            .equipmentCondition("NEW")
            .lastMaintenanceDate("")
            .nextMaintenanceDate("")
            .warrantyStatus("ACTIVE")
            .isWarrantied(true)
            .warrantyExpiryDate("")
            .equipmentNotes("Professional surgical instruments")
            .equipmentStats(Map.of("surgical", 90, "durability", 200))
            .equipmentAbilities(Arrays.asList("SURGICAL_PROCEDURES", "HIGH_PRECISION"))
            .manufacturer("XCOM_MEDICAL")
            .modelNumber("EQUIPMENT_SURGICAL_001")
            .serialNumber("SN_SURGICAL_001")
            .purchaseDate(new Date().toString())
            .purchaseCost(2000)
            .supplier("XCOM_SUPPLY")
            .location("MEDICAL_ARMORY")
            .status("ACTIVE")
            .build();
        medicalEquipment.put(surgicalTools.getEquipmentId(), surgicalTools);
        
        // Diagnostic Equipment
        MedicalEquipment diagnosticEquipment = MedicalEquipment.builder()
            .equipmentId("DIAGNOSTIC_EQUIPMENT_001")
            .equipmentName("Diagnostic Equipment")
            .equipmentType(EquipmentType.DIAGNOSTIC_EQUIPMENT)
            .effectiveness(85)
            .durability(150)
            .currentDurability(150)
            .isOperational(true)
            .description("Advanced diagnostic equipment for medical analysis")
            .effects(Map.of("diagnostic", 85, "durability", 150, "analysis", 90))
            .abilities(Arrays.asList("MEDICAL_ANALYSIS", "DISEASE_DETECTION", "HEALTH_MONITORING"))
            .cost(3000)
            .assignedDoctor("")
            .assignedFacility("")
            .isInUse(false)
            .equipmentStatus("AVAILABLE")
            .maintenanceCost(150)
            .repairCost(400)
            .equipmentQuality("EXCELLENT")
            .equipmentCondition("NEW")
            .lastMaintenanceDate("")
            .nextMaintenanceDate("")
            .warrantyStatus("ACTIVE")
            .isWarrantied(true)
            .warrantyExpiryDate("")
            .equipmentNotes("Advanced diagnostic equipment")
            .equipmentStats(Map.of("diagnostic", 85, "durability", 150))
            .equipmentAbilities(Arrays.asList("MEDICAL_ANALYSIS", "DISEASE_DETECTION"))
            .manufacturer("XCOM_MEDICAL")
            .modelNumber("EQUIPMENT_DIAGNOSTIC_001")
            .serialNumber("SN_DIAGNOSTIC_001")
            .purchaseDate(new Date().toString())
            .purchaseCost(3000)
            .supplier("XCOM_SUPPLY")
            .location("MEDICAL_ARMORY")
            .status("ACTIVE")
            .build();
        medicalEquipment.put(diagnosticEquipment.getEquipmentId(), diagnosticEquipment);
        
        // Monitoring Devices
        MedicalEquipment monitoringDevices = MedicalEquipment.builder()
            .equipmentId("MONITORING_DEVICES_001")
            .equipmentName("Patient Monitoring Devices")
            .equipmentType(EquipmentType.MONITORING_DEVICES)
            .effectiveness(80)
            .durability(120)
            .currentDurability(120)
            .isOperational(true)
            .description("Patient monitoring and vital signs tracking equipment")
            .effects(Map.of("monitoring", 80, "durability", 120, "tracking", 85))
            .abilities(Arrays.asList("VITAL_MONITORING", "HEALTH_TRACKING", "ALERT_SYSTEM"))
            .cost(1500)
            .assignedDoctor("")
            .assignedFacility("")
            .isInUse(false)
            .equipmentStatus("AVAILABLE")
            .maintenanceCost(80)
            .repairCost(200)
            .equipmentQuality("GOOD")
            .equipmentCondition("NEW")
            .lastMaintenanceDate("")
            .nextMaintenanceDate("")
            .warrantyStatus("ACTIVE")
            .isWarrantied(true)
            .warrantyExpiryDate("")
            .equipmentNotes("Patient monitoring equipment")
            .equipmentStats(Map.of("monitoring", 80, "durability", 120))
            .equipmentAbilities(Arrays.asList("VITAL_MONITORING", "HEALTH_TRACKING"))
            .manufacturer("XCOM_MEDICAL")
            .modelNumber("EQUIPMENT_MONITORING_001")
            .serialNumber("SN_MONITORING_001")
            .purchaseDate(new Date().toString())
            .purchaseCost(1500)
            .supplier("XCOM_SUPPLY")
            .location("MEDICAL_ARMORY")
            .status("ACTIVE")
            .build();
        medicalEquipment.put(monitoringDevices.getEquipmentId(), monitoringDevices);
        
        // Life Support Systems
        MedicalEquipment lifeSupportSystems = MedicalEquipment.builder()
            .equipmentId("LIFE_SUPPORT_SYSTEMS_001")
            .equipmentName("Life Support Systems")
            .equipmentType(EquipmentType.LIFE_SUPPORT_SYSTEMS)
            .effectiveness(95)
            .durability(300)
            .currentDurability(300)
            .isOperational(true)
            .description("Critical life support equipment for intensive care")
            .effects(Map.of("life_support", 95, "durability", 300, "critical_care", 100))
            .abilities(Arrays.asList("LIFE_SUPPORT", "CRITICAL_CARE", "EMERGENCY_RESPONSE"))
            .cost(8000)
            .assignedDoctor("")
            .assignedFacility("")
            .isInUse(false)
            .equipmentStatus("AVAILABLE")
            .maintenanceCost(400)
            .repairCost(1000)
            .equipmentQuality("EXCELLENT")
            .equipmentCondition("NEW")
            .lastMaintenanceDate("")
            .nextMaintenanceDate("")
            .warrantyStatus("ACTIVE")
            .isWarrantied(true)
            .warrantyExpiryDate("")
            .equipmentNotes("Critical life support equipment")
            .equipmentStats(Map.of("life_support", 95, "durability", 300))
            .equipmentAbilities(Arrays.asList("LIFE_SUPPORT", "CRITICAL_CARE"))
            .manufacturer("XCOM_MEDICAL")
            .modelNumber("EQUIPMENT_LIFE_SUPPORT_001")
            .serialNumber("SN_LIFE_SUPPORT_001")
            .purchaseDate(new Date().toString())
            .purchaseCost(8000)
            .supplier("XCOM_SUPPLY")
            .location("MEDICAL_ARMORY")
            .status("ACTIVE")
            .build();
        medicalEquipment.put(lifeSupportSystems.getEquipmentId(), lifeSupportSystems);
        
        // Healing Devices
        MedicalEquipment healingDevices = MedicalEquipment.builder()
            .equipmentId("HEALING_DEVICES_001")
            .equipmentName("Specialized Healing Devices")
            .equipmentType(EquipmentType.HEALING_DEVICES)
            .effectiveness(75)
            .durability(180)
            .currentDurability(180)
            .isOperational(true)
            .description("Specialized equipment for accelerated healing")
            .effects(Map.of("healing", 75, "durability", 180, "acceleration", 80))
            .abilities(Arrays.asList("ACCELERATED_HEALING", "TISSUE_REPAIR", "RECOVERY_BOOST"))
            .cost(4000)
            .assignedDoctor("")
            .assignedFacility("")
            .isInUse(false)
            .equipmentStatus("AVAILABLE")
            .maintenanceCost(200)
            .repairCost(500)
            .equipmentQuality("GOOD")
            .equipmentCondition("NEW")
            .lastMaintenanceDate("")
            .nextMaintenanceDate("")
            .warrantyStatus("ACTIVE")
            .isWarrantied(true)
            .warrantyExpiryDate("")
            .equipmentNotes("Specialized healing equipment")
            .equipmentStats(Map.of("healing", 75, "durability", 180))
            .equipmentAbilities(Arrays.asList("ACCELERATED_HEALING", "TISSUE_REPAIR"))
            .manufacturer("XCOM_MEDICAL")
            .modelNumber("EQUIPMENT_HEALING_001")
            .serialNumber("SN_HEALING_001")
            .purchaseDate(new Date().toString())
            .purchaseCost(4000)
            .supplier("XCOM_SUPPLY")
            .location("MEDICAL_ARMORY")
            .status("ACTIVE")
            .build();
        medicalEquipment.put(healingDevices.getEquipmentId(), healingDevices);
        
        // Psionic Amplifiers
        MedicalEquipment psionicAmplifiers = MedicalEquipment.builder()
            .equipmentId("PSYCHIC_AMPLIFIERS_001")
            .equipmentName("Psionic Amplifiers")
            .equipmentType(EquipmentType.PSYCHIC_AMPLIFIERS)
            .effectiveness(85)
            .durability(200)
            .currentDurability(200)
            .isOperational(true)
            .description("Psionic enhancement devices for mental healing")
            .effects(Map.of("psionic_amplification", 85, "durability", 200, "mental_enhancement", 90))
            .abilities(Arrays.asList("PSIONIC_AMPLIFICATION", "MENTAL_ENHANCEMENT", "ENERGY_FOCUS"))
            .cost(6000)
            .assignedDoctor("")
            .assignedFacility("")
            .isInUse(false)
            .equipmentStatus("AVAILABLE")
            .maintenanceCost(300)
            .repairCost(800)
            .equipmentQuality("EXCELLENT")
            .equipmentCondition("NEW")
            .lastMaintenanceDate("")
            .nextMaintenanceDate("")
            .warrantyStatus("ACTIVE")
            .isWarrantied(true)
            .warrantyExpiryDate("")
            .equipmentNotes("Psionic enhancement devices")
            .equipmentStats(Map.of("psionic_amplification", 85, "durability", 200))
            .equipmentAbilities(Arrays.asList("PSIONIC_AMPLIFICATION", "MENTAL_ENHANCEMENT"))
            .manufacturer("XCOM_MEDICAL")
            .modelNumber("EQUIPMENT_PSIONIC_001")
            .serialNumber("SN_PSIONIC_001")
            .purchaseDate(new Date().toString())
            .purchaseCost(6000)
            .supplier("XCOM_SUPPLY")
            .location("MEDICAL_ARMORY")
            .status("ACTIVE")
            .build();
        medicalEquipment.put(psionicAmplifiers.getEquipmentId(), psionicAmplifiers);
        
        // Alien Technology Devices
        MedicalEquipment alienTechnologyDevices = MedicalEquipment.builder()
            .equipmentId("ALIEN_TECHNOLOGY_DEVICES_001")
            .equipmentName("Alien Technology Medical Devices")
            .equipmentType(EquipmentType.ALIEN_TECHNOLOGY_DEVICES)
            .effectiveness(95)
            .durability(250)
            .currentDurability(250)
            .isOperational(true)
            .description("Advanced medical devices using alien technology")
            .effects(Map.of("alien_technology", 95, "durability", 250, "advanced_healing", 100))
            .abilities(Arrays.asList("ALIEN_TECHNOLOGY", "ADVANCED_HEALING", "FUTURISTIC_CARE"))
            .cost(15000)
            .assignedDoctor("")
            .assignedFacility("")
            .isInUse(false)
            .equipmentStatus("AVAILABLE")
            .maintenanceCost(800)
            .repairCost(2000)
            .equipmentQuality("EXCELLENT")
            .equipmentCondition("NEW")
            .lastMaintenanceDate("")
            .nextMaintenanceDate("")
            .warrantyStatus("ACTIVE")
            .isWarrantied(true)
            .warrantyExpiryDate("")
            .equipmentNotes("Advanced alien technology devices")
            .equipmentStats(Map.of("alien_technology", 95, "durability", 250))
            .equipmentAbilities(Arrays.asList("ALIEN_TECHNOLOGY", "ADVANCED_HEALING"))
            .manufacturer("XCOM_MEDICAL")
            .modelNumber("EQUIPMENT_ALIEN_001")
            .serialNumber("SN_ALIEN_001")
            .purchaseDate(new Date().toString())
            .purchaseCost(15000)
            .supplier("XCOM_SUPPLY")
            .location("MEDICAL_ARMORY")
            .status("ACTIVE")
            .build();
        medicalEquipment.put(alienTechnologyDevices.getEquipmentId(), alienTechnologyDevices);
        
        // Emergency Equipment
        MedicalEquipment emergencyEquipment = MedicalEquipment.builder()
            .equipmentId("EMERGENCY_EQUIPMENT_001")
            .equipmentName("Emergency Medical Equipment")
            .equipmentType(EquipmentType.EMERGENCY_EQUIPMENT)
            .effectiveness(90)
            .durability(100)
            .currentDurability(100)
            .isOperational(true)
            .description("Emergency medical equipment for critical situations")
            .effects(Map.of("emergency_care", 90, "durability", 100, "rapid_response", 95))
            .abilities(Arrays.asList("EMERGENCY_CARE", "RAPID_RESPONSE", "CRITICAL_INTERVENTION"))
            .cost(5000)
            .assignedDoctor("")
            .assignedFacility("")
            .isInUse(false)
            .equipmentStatus("AVAILABLE")
            .maintenanceCost(250)
            .repairCost(600)
            .equipmentQuality("EXCELLENT")
            .equipmentCondition("NEW")
            .lastMaintenanceDate("")
            .nextMaintenanceDate("")
            .warrantyStatus("ACTIVE")
            .isWarrantied(true)
            .warrantyExpiryDate("")
            .equipmentNotes("Emergency medical equipment")
            .equipmentStats(Map.of("emergency_care", 90, "durability", 100))
            .equipmentAbilities(Arrays.asList("EMERGENCY_CARE", "RAPID_RESPONSE"))
            .manufacturer("XCOM_MEDICAL")
            .modelNumber("EQUIPMENT_EMERGENCY_001")
            .serialNumber("SN_EMERGENCY_001")
            .purchaseDate(new Date().toString())
            .purchaseCost(5000)
            .supplier("XCOM_SUPPLY")
            .location("MEDICAL_ARMORY")
            .status("ACTIVE")
            .build();
        medicalEquipment.put(emergencyEquipment.getEquipmentId(), emergencyEquipment);
    }
    
    /**
     * Initialize medical procedures
     */
    private void initializeMedicalProcedures() {
        // Basic First Aid Procedure
        MedicalProcedure basicFirstAid = MedicalProcedure.builder()
            .procedureId("BASIC_FIRST_AID_001")
            .procedureName("Basic First Aid")
            .procedureType(TreatmentType.BASIC_FIRST_AID)
            .effectiveness(60)
            .duration(2)
            .currentDuration(0)
            .isActive(false)
            .description("Basic first aid treatment for minor injuries")
            .effects(Map.of("healing", 60, "duration", 2, "cost", 100))
            .abilities(Arrays.asList("BASIC_HEALING", "MINOR_TREATMENT", "QUICK_RECOVERY"))
            .cost(100)
            .assignedDoctor("")
            .assignedFacility("")
            .procedureProgress(0)
            .isCompleted(false)
            .completionDate("")
            .procedureStatus("AVAILABLE")
            .procedureNotes("Basic first aid procedure")
            .procedureBonuses(Map.of("healing", 60, "recovery", 40))
            .procedureRequirements(Arrays.asList("Medikit", "Basic Training"))
            .procedureCost(100)
            .procedureDescription("Basic first aid treatment for minor injuries")
            .manufacturer("XCOM_MEDICAL")
            .modelNumber("PROCEDURE_BASIC_001")
            .serialNumber("SN_PROCEDURE_001")
            .purchaseDate(new Date().toString())
            .purchaseCost(100)
            .supplier("XCOM_SUPPLY")
            .location("MEDICAL_ARMORY")
            .status("ACTIVE")
            .build();
        medicalProcedures.put(basicFirstAid.getProcedureId(), basicFirstAid);
        
        // Advanced First Aid Procedure
        MedicalProcedure advancedFirstAid = MedicalProcedure.builder()
            .procedureId("ADVANCED_FIRST_AID_001")
            .procedureName("Advanced First Aid")
            .procedureType(TreatmentType.ADVANCED_FIRST_AID)
            .effectiveness(80)
            .duration(3)
            .currentDuration(0)
            .isActive(false)
            .description("Advanced first aid treatment for moderate injuries")
            .effects(Map.of("healing", 80, "duration", 3, "cost", 250))
            .abilities(Arrays.asList("ADVANCED_HEALING", "MODERATE_TREATMENT", "ENHANCED_RECOVERY"))
            .cost(250)
            .assignedDoctor("")
            .assignedFacility("")
            .procedureProgress(0)
            .isCompleted(false)
            .completionDate("")
            .procedureStatus("AVAILABLE")
            .procedureNotes("Advanced first aid procedure")
            .procedureBonuses(Map.of("healing", 80, "recovery", 60))
            .procedureRequirements(Arrays.asList("Advanced Medikit", "Advanced Training"))
            .procedureCost(250)
            .procedureDescription("Advanced first aid treatment for moderate injuries")
            .manufacturer("XCOM_MEDICAL")
            .modelNumber("PROCEDURE_ADV_001")
            .serialNumber("SN_PROCEDURE_ADV_001")
            .purchaseDate(new Date().toString())
            .purchaseCost(250)
            .supplier("XCOM_SUPPLY")
            .location("MEDICAL_ARMORY")
            .status("ACTIVE")
            .build();
        medicalProcedures.put(advancedFirstAid.getProcedureId(), advancedFirstAid);
        
        // Surgery Procedure
        MedicalProcedure surgery = MedicalProcedure.builder()
            .procedureId("SURGERY_001")
            .procedureName("Surgery")
            .procedureType(TreatmentType.SURGERY)
            .effectiveness(95)
            .duration(6)
            .currentDuration(0)
            .isActive(false)
            .description("Surgical procedure for serious injuries")
            .effects(Map.of("healing", 95, "duration", 6, "cost", 1000))
            .abilities(Arrays.asList("SURGICAL_TREATMENT", "SERIOUS_INJURY_CARE", "COMPREHENSIVE_HEALING"))
            .cost(1000)
            .assignedDoctor("")
            .assignedFacility("")
            .procedureProgress(0)
            .isCompleted(false)
            .completionDate("")
            .procedureStatus("AVAILABLE")
            .procedureNotes("Surgical procedure")
            .procedureBonuses(Map.of("healing", 95, "recovery", 80))
            .procedureRequirements(Arrays.asList("Surgical Tools", "Surgical Training", "Hospital"))
            .procedureCost(1000)
            .procedureDescription("Surgical procedure for serious injuries")
            .manufacturer("XCOM_MEDICAL")
            .modelNumber("PROCEDURE_SURGERY_001")
            .serialNumber("SN_PROCEDURE_SURGERY_001")
            .purchaseDate(new Date().toString())
            .purchaseCost(1000)
            .supplier("XCOM_SUPPLY")
            .location("MEDICAL_ARMORY")
            .status("ACTIVE")
            .build();
        medicalProcedures.put(surgery.getProcedureId(), surgery);
        
        // Emergency Surgery Procedure
        MedicalProcedure emergencySurgery = MedicalProcedure.builder()
            .procedureId("EMERGENCY_SURGERY_001")
            .procedureName("Emergency Surgery")
            .procedureType(TreatmentType.EMERGENCY_SURGERY)
            .effectiveness(90)
            .duration(4)
            .currentDuration(0)
            .isActive(false)
            .description("Emergency surgical procedure for life-threatening injuries")
            .effects(Map.of("healing", 90, "duration", 4, "cost", 1500))
            .abilities(Arrays.asList("EMERGENCY_TREATMENT", "LIFE_SAVING", "RAPID_INTERVENTION"))
            .cost(1500)
            .assignedDoctor("")
            .assignedFacility("")
            .procedureProgress(0)
            .isCompleted(false)
            .completionDate("")
            .procedureStatus("AVAILABLE")
            .procedureNotes("Emergency surgical procedure")
            .procedureBonuses(Map.of("healing", 90, "recovery", 70))
            .procedureRequirements(Arrays.asList("Surgical Tools", "Emergency Training", "Emergency Room"))
            .procedureCost(1500)
            .procedureDescription("Emergency surgical procedure for life-threatening injuries")
            .manufacturer("XCOM_MEDICAL")
            .modelNumber("PROCEDURE_EMERGENCY_001")
            .serialNumber("SN_PROCEDURE_EMERGENCY_001")
            .purchaseDate(new Date().toString())
            .purchaseCost(1500)
            .supplier("XCOM_SUPPLY")
            .location("MEDICAL_ARMORY")
            .status("ACTIVE")
            .build();
        medicalProcedures.put(emergencySurgery.getProcedureId(), emergencySurgery);
        
        // Psychological Therapy Procedure
        MedicalProcedure psychologicalTherapy = MedicalProcedure.builder()
            .procedureId("PSYCHOLOGICAL_THERAPY_001")
            .procedureName("Psychological Therapy")
            .procedureType(TreatmentType.PSYCHOLOGICAL_THERAPY)
            .effectiveness(75)
            .duration(5)
            .currentDuration(0)
            .isActive(false)
            .description("Psychological therapy for mental health issues")
            .effects(Map.of("mental_health", 75, "duration", 5, "cost", 800))
            .abilities(Arrays.asList("MENTAL_HEALTH_TREATMENT", "PSYCHOLOGICAL_CARE", "STRESS_REDUCTION"))
            .cost(800)
            .assignedDoctor("")
            .assignedFacility("")
            .procedureProgress(0)
            .isCompleted(false)
            .completionDate("")
            .procedureStatus("AVAILABLE")
            .procedureNotes("Psychological therapy procedure")
            .procedureBonuses(Map.of("mental_health", 75, "morale", 60))
            .procedureRequirements(Arrays.asList("Psychologist", "Therapy Training", "Psychiatric Ward"))
            .procedureCost(800)
            .procedureDescription("Psychological therapy for mental health issues")
            .manufacturer("XCOM_MEDICAL")
            .modelNumber("PROCEDURE_PSYCH_001")
            .serialNumber("SN_PROCEDURE_PSYCH_001")
            .purchaseDate(new Date().toString())
            .purchaseCost(800)
            .supplier("XCOM_SUPPLY")
            .location("MEDICAL_ARMORY")
            .status("ACTIVE")
            .build();
        medicalProcedures.put(psychologicalTherapy.getProcedureId(), psychologicalTherapy);
        
        // Physical Therapy Procedure
        MedicalProcedure physicalTherapy = MedicalProcedure.builder()
            .procedureId("PHYSICAL_THERAPY_001")
            .procedureName("Physical Therapy")
            .procedureType(TreatmentType.PHYSICAL_THERAPY)
            .effectiveness(70)
            .duration(7)
            .currentDuration(0)
            .isActive(false)
            .description("Physical therapy for mobility and strength recovery")
            .effects(Map.of("mobility", 70, "strength", 70, "duration", 7, "cost", 600))
            .abilities(Arrays.asList("MOBILITY_RECOVERY", "STRENGTH_TRAINING", "REHABILITATION"))
            .cost(600)
            .assignedDoctor("")
            .assignedFacility("")
            .procedureProgress(0)
            .isCompleted(false)
            .completionDate("")
            .procedureStatus("AVAILABLE")
            .procedureNotes("Physical therapy procedure")
            .procedureBonuses(Map.of("mobility", 70, "strength", 70))
            .procedureRequirements(Arrays.asList("Physical Therapist", "Therapy Equipment", "Rehabilitation Center"))
            .procedureCost(600)
            .procedureDescription("Physical therapy for mobility and strength recovery")
            .manufacturer("XCOM_MEDICAL")
            .modelNumber("PROCEDURE_PHYS_001")
            .serialNumber("SN_PROCEDURE_PHYS_001")
            .purchaseDate(new Date().toString())
            .purchaseCost(600)
            .supplier("XCOM_SUPPLY")
            .location("MEDICAL_ARMORY")
            .status("ACTIVE")
            .build();
        medicalProcedures.put(physicalTherapy.getProcedureId(), physicalTherapy);
        
        // Medication Treatment Procedure
        MedicalProcedure medication = MedicalProcedure.builder()
            .procedureId("MEDICATION_001")
            .procedureName("Medication Treatment")
            .procedureType(TreatmentType.MEDICATION)
            .effectiveness(65)
            .duration(3)
            .currentDuration(0)
            .isActive(false)
            .description("Medication-based treatment for various conditions")
            .effects(Map.of("healing", 65, "duration", 3, "cost", 300))
            .abilities(Arrays.asList("MEDICATION_TREATMENT", "DRUG_THERAPY", "SYMPTOM_RELIEF"))
            .cost(300)
            .assignedDoctor("")
            .assignedFacility("")
            .procedureProgress(0)
            .isCompleted(false)
            .completionDate("")
            .procedureStatus("AVAILABLE")
            .procedureNotes("Medication treatment procedure")
            .procedureBonuses(Map.of("healing", 65, "symptom_relief", 80))
            .procedureRequirements(Arrays.asList("Medication", "Medical Training", "Medical Clinic"))
            .procedureCost(300)
            .procedureDescription("Medication-based treatment for various conditions")
            .manufacturer("XCOM_MEDICAL")
            .modelNumber("PROCEDURE_MED_001")
            .serialNumber("SN_PROCEDURE_MED_001")
            .purchaseDate(new Date().toString())
            .purchaseCost(300)
            .supplier("XCOM_SUPPLY")
            .location("MEDICAL_ARMORY")
            .status("ACTIVE")
            .build();
        medicalProcedures.put(medication.getProcedureId(), medication);
        
        // Rest and Recovery Procedure
        MedicalProcedure restAndRecovery = MedicalProcedure.builder()
            .procedureId("REST_AND_RECOVERY_001")
            .procedureName("Rest and Recovery")
            .procedureType(TreatmentType.REST_AND_RECOVERY)
            .effectiveness(50)
            .duration(10)
            .currentDuration(0)
            .isActive(false)
            .description("Rest-based recovery for natural healing")
            .effects(Map.of("natural_healing", 50, "duration", 10, "cost", 100))
            .abilities(Arrays.asList("NATURAL_HEALING", "REST_RECOVERY", "STRESS_REDUCTION"))
            .cost(100)
            .assignedDoctor("")
            .assignedFacility("")
            .procedureProgress(0)
            .isCompleted(false)
            .completionDate("")
            .procedureStatus("AVAILABLE")
            .procedureNotes("Rest and recovery procedure")
            .procedureBonuses(Map.of("natural_healing", 50, "stress_reduction", 90))
            .procedureRequirements(Arrays.asList("Rest Facility", "Basic Care", "First Aid Station"))
            .procedureCost(100)
            .procedureDescription("Rest-based recovery for natural healing")
            .manufacturer("XCOM_MEDICAL")
            .modelNumber("PROCEDURE_REST_001")
            .serialNumber("SN_PROCEDURE_REST_001")
            .purchaseDate(new Date().toString())
            .purchaseCost(100)
            .supplier("XCOM_SUPPLY")
            .location("MEDICAL_ARMORY")
            .status("ACTIVE")
            .build();
        medicalProcedures.put(restAndRecovery.getProcedureId(), restAndRecovery);
        
        // Experimental Treatment Procedure
        MedicalProcedure experimentalTreatment = MedicalProcedure.builder()
            .procedureId("EXPERIMENTAL_TREATMENT_001")
            .procedureName("Experimental Treatment")
            .procedureType(TreatmentType.EXPERIMENTAL_TREATMENT)
            .effectiveness(85)
            .duration(8)
            .currentDuration(0)
            .isActive(false)
            .description("Experimental medical treatment with high risk and reward")
            .effects(Map.of("healing", 85, "duration", 8, "cost", 2000))
            .abilities(Arrays.asList("EXPERIMENTAL_HEALING", "HIGH_RISK_TREATMENT", "INNOVATIVE_CARE"))
            .cost(2000)
            .assignedDoctor("")
            .assignedFacility("")
            .procedureProgress(0)
            .isCompleted(false)
            .completionDate("")
            .procedureStatus("AVAILABLE")
            .procedureNotes("Experimental treatment procedure")
            .procedureBonuses(Map.of("healing", 85, "innovation", 100))
            .procedureRequirements(Arrays.asList("Research Lab", "Experimental Training", "High Risk Consent"))
            .procedureCost(2000)
            .procedureDescription("Experimental medical treatment with high risk and reward")
            .manufacturer("XCOM_MEDICAL")
            .modelNumber("PROCEDURE_EXP_001")
            .serialNumber("SN_PROCEDURE_EXP_001")
            .purchaseDate(new Date().toString())
            .purchaseCost(2000)
            .supplier("XCOM_SUPPLY")
            .location("MEDICAL_ARMORY")
            .status("ACTIVE")
            .build();
        medicalProcedures.put(experimentalTreatment.getProcedureId(), experimentalTreatment);
        
        // Psionic Healing Procedure
        MedicalProcedure psionicHealing = MedicalProcedure.builder()
            .procedureId("PSYCHIC_HEALING_001")
            .procedureName("Psionic Healing")
            .procedureType(TreatmentType.PSYCHIC_HEALING)
            .effectiveness(80)
            .duration(4)
            .currentDuration(0)
            .isActive(false)
            .description("Psionic healing using psychic abilities")
            .effects(Map.of("psionic_healing", 80, "duration", 4, "cost", 1200))
            .abilities(Arrays.asList("PSIONIC_HEALING", "MENTAL_RESTORATION", "ENERGY_HEALING"))
            .cost(1200)
            .assignedDoctor("")
            .assignedFacility("")
            .procedureProgress(0)
            .isCompleted(false)
            .completionDate("")
            .procedureStatus("AVAILABLE")
            .procedureNotes("Psionic healing procedure")
            .procedureBonuses(Map.of("psionic_healing", 80, "mental_restoration", 90))
            .procedureRequirements(Arrays.asList("Psionic Healer", "Psionic Training", "Psionic Healing Center"))
            .procedureCost(1200)
            .procedureDescription("Psionic healing using psychic abilities")
            .manufacturer("XCOM_MEDICAL")
            .modelNumber("PROCEDURE_PSIONIC_001")
            .serialNumber("SN_PROCEDURE_PSIONIC_001")
            .purchaseDate(new Date().toString())
            .purchaseCost(1200)
            .supplier("XCOM_SUPPLY")
            .location("MEDICAL_ARMORY")
            .status("ACTIVE")
            .build();
        medicalProcedures.put(psionicHealing.getProcedureId(), psionicHealing);
        
        // Alien Technology Treatment Procedure
        MedicalProcedure alienTechnology = MedicalProcedure.builder()
            .procedureId("ALIEN_TECHNOLOGY_001")
            .procedureName("Alien Technology Treatment")
            .procedureType(TreatmentType.ALIEN_TECHNOLOGY)
            .effectiveness(95)
            .duration(5)
            .currentDuration(0)
            .isActive(false)
            .description("Treatment using advanced alien technology")
            .effects(Map.of("alien_healing", 95, "duration", 5, "cost", 3000))
            .abilities(Arrays.asList("ALIEN_TECHNOLOGY", "ADVANCED_HEALING", "FUTURISTIC_CARE"))
            .cost(3000)
            .assignedDoctor("")
            .assignedFacility("")
            .procedureProgress(0)
            .isCompleted(false)
            .completionDate("")
            .procedureStatus("AVAILABLE")
            .procedureNotes("Alien technology treatment procedure")
            .procedureBonuses(Map.of("alien_healing", 95, "technology_boost", 100))
            .procedureRequirements(Arrays.asList("Alien Technology Lab", "Alien Research", "High Security Clearance"))
            .procedureCost(3000)
            .procedureDescription("Treatment using advanced alien technology")
            .manufacturer("XCOM_MEDICAL")
            .modelNumber("PROCEDURE_ALIEN_001")
            .serialNumber("SN_PROCEDURE_ALIEN_001")
            .purchaseDate(new Date().toString())
            .purchaseCost(3000)
            .supplier("XCOM_SUPPLY")
            .location("MEDICAL_ARMORY")
            .status("ACTIVE")
            .build();
        medicalProcedures.put(alienTechnology.getProcedureId(), alienTechnology);
        
        // Emergency Treatment Procedure
        MedicalProcedure emergencyTreatment = MedicalProcedure.builder()
            .procedureId("EMERGENCY_TREATMENT_001")
            .procedureName("Emergency Treatment")
            .procedureType(TreatmentType.EMERGENCY_TREATMENT)
            .effectiveness(70)
            .duration(2)
            .currentDuration(0)
            .isActive(false)
            .description("Emergency medical treatment for critical situations")
            .effects(Map.of("emergency_healing", 70, "duration", 2, "cost", 500))
            .abilities(Arrays.asList("EMERGENCY_CARE", "CRITICAL_TREATMENT", "RAPID_RESPONSE"))
            .cost(500)
            .assignedDoctor("")
            .assignedFacility("")
            .procedureProgress(0)
            .isCompleted(false)
            .completionDate("")
            .procedureStatus("AVAILABLE")
            .procedureNotes("Emergency treatment procedure")
            .procedureBonuses(Map.of("emergency_healing", 70, "rapid_response", 90))
            .procedureRequirements(Arrays.asList("Emergency Equipment", "Emergency Training", "Emergency Room"))
            .procedureCost(500)
            .procedureDescription("Emergency medical treatment for critical situations")
            .manufacturer("XCOM_MEDICAL")
            .modelNumber("PROCEDURE_EMERGENCY_TREAT_001")
            .serialNumber("SN_PROCEDURE_EMERGENCY_TREAT_001")
            .purchaseDate(new Date().toString())
            .purchaseCost(500)
            .supplier("XCOM_SUPPLY")
            .location("MEDICAL_ARMORY")
            .status("ACTIVE")
            .build();
        medicalProcedures.put(emergencyTreatment.getProcedureId(), emergencyTreatment);
    }
    
    /**
     * Initialize medical priorities
     */
    private void initializeMedicalPriorities() {
        // Critical Priority
        MedicalPriority criticalPriority = MedicalPriority.builder()
            .priorityId("CRITICAL_PRIORITY_001")
            .patientId("PATIENT_001")
            .priorityLevel(MedicalPriority.PriorityLevel.CRITICAL)
            .priorityReason("Severe bleeding")
            .priorityScore(100)
            .isUrgent(true)
            .isCritical(true)
            .assignedDoctor("DR_SMITH")
            .assignedFacility("EMERGENCY_ROOM")
            .waitTime(0)
            .priorityStatus("ACTIVE")
            .priorityNotes("Immediate attention required")
            .priorityDate(new Date())
            .priorityCategory("CRITICAL")
            .priorityDuration(0)
            .isEscalated(false)
            .escalationReason("")
            .build();
        medicalPriorities.put(criticalPriority.getPriorityId(), criticalPriority);
        
        // High Priority
        MedicalPriority highPriority = MedicalPriority.builder()
            .priorityId("HIGH_PRIORITY_001")
            .patientId("PATIENT_002")
            .priorityLevel(MedicalPriority.PriorityLevel.HIGH)
            .priorityReason("High fever")
            .priorityScore(80)
            .isUrgent(true)
            .isCritical(false)
            .assignedDoctor("DR_JOHNSON")
            .assignedFacility("MEDICAL_CLINIC")
            .waitTime(1)
            .priorityStatus("ACTIVE")
            .priorityNotes("High priority treatment needed")
            .priorityDate(new Date())
            .priorityCategory("HIGH")
            .priorityDuration(1)
            .isEscalated(false)
            .escalationReason("")
            .build();
        medicalPriorities.put(highPriority.getPriorityId(), highPriority);
        
        // Normal Priority
        MedicalPriority normalPriority = MedicalPriority.builder()
            .priorityId("NORMAL_PRIORITY_001")
            .patientId("PATIENT_003")
            .priorityLevel(MedicalPriority.PriorityLevel.NORMAL)
            .priorityReason("Minor wound")
            .priorityScore(50)
            .isUrgent(false)
            .isCritical(false)
            .assignedDoctor("DR_WILLIAMS")
            .assignedFacility("FIRST_AID_STATION")
            .waitTime(3)
            .priorityStatus("ACTIVE")
            .priorityNotes("Standard priority treatment")
            .priorityDate(new Date())
            .priorityCategory("NORMAL")
            .priorityDuration(3)
            .isEscalated(false)
            .escalationReason("")
            .build();
        medicalPriorities.put(normalPriority.getPriorityId(), normalPriority);
        
        // Low Priority
        MedicalPriority lowPriority = MedicalPriority.builder()
            .priorityId("LOW_PRIORITY_001")
            .patientId("PATIENT_004")
            .priorityLevel(MedicalPriority.PriorityLevel.LOW)
            .priorityReason("Minor bruising")
            .priorityScore(30)
            .isUrgent(false)
            .isCritical(false)
            .assignedDoctor("DR_BROWN")
            .assignedFacility("FIRST_AID_STATION")
            .waitTime(5)
            .priorityStatus("ACTIVE")
            .priorityNotes("Low priority treatment")
            .priorityDate(new Date())
            .priorityCategory("LOW")
            .priorityDuration(5)
            .isEscalated(false)
            .escalationReason("")
            .build();
        medicalPriorities.put(lowPriority.getPriorityId(), lowPriority);
        
        // Emergency Priority
        MedicalPriority emergencyPriority = MedicalPriority.builder()
            .priorityId("EMERGENCY_PRIORITY_001")
            .patientId("PATIENT_005")
            .priorityLevel(MedicalPriority.PriorityLevel.EMERGENCY)
            .priorityReason("Cardiac arrest")
            .priorityScore(120)
            .isUrgent(true)
            .isCritical(true)
            .assignedDoctor("DR_EMERGENCY")
            .assignedFacility("EMERGENCY_ROOM")
            .waitTime(0)
            .priorityStatus("ACTIVE")
            .priorityNotes("Immediate emergency response required")
            .priorityDate(new Date())
            .priorityCategory("EMERGENCY")
            .priorityDuration(0)
            .isEscalated(false)
            .escalationReason("")
            .build();
        medicalPriorities.put(emergencyPriority.getPriorityId(), emergencyPriority);
        
        // Trauma Priority
        MedicalPriority traumaPriority = MedicalPriority.builder()
            .priorityId("TRAUMA_PRIORITY_001")
            .patientId("PATIENT_006")
            .priorityLevel(MedicalPriority.PriorityLevel.TRAUMA)
            .priorityReason("Multiple fractures")
            .priorityScore(110)
            .isUrgent(true)
            .isCritical(true)
            .assignedDoctor("DR_TRAUMA")
            .assignedFacility("SURGICAL_CENTER")
            .waitTime(0)
            .priorityStatus("ACTIVE")
            .priorityNotes("Trauma surgery required")
            .priorityDate(new Date())
            .priorityCategory("TRAUMA")
            .priorityDuration(0)
            .isEscalated(false)
            .escalationReason("")
            .build();
        medicalPriorities.put(traumaPriority.getPriorityId(), traumaPriority);
        
        // Infection Priority
        MedicalPriority infectionPriority = MedicalPriority.builder()
            .priorityId("INFECTION_PRIORITY_001")
            .patientId("PATIENT_007")
            .priorityLevel(MedicalPriority.PriorityLevel.INFECTION)
            .priorityReason("Severe infection")
            .priorityScore(90)
            .isUrgent(true)
            .isCritical(false)
            .assignedDoctor("DR_INFECTIOUS")
            .assignedFacility("QUARANTINE_WARD")
            .waitTime(1)
            .priorityStatus("ACTIVE")
            .priorityNotes("Infection control treatment")
            .priorityDate(new Date())
            .priorityCategory("INFECTION")
            .priorityDuration(1)
            .isEscalated(false)
            .escalationReason("")
            .build();
        medicalPriorities.put(infectionPriority.getPriorityId(), infectionPriority);
        
        // Rehabilitation Priority
        MedicalPriority rehabilitationPriority = MedicalPriority.builder()
            .priorityId("REHABILITATION_PRIORITY_001")
            .patientId("PATIENT_008")
            .priorityLevel(MedicalPriority.PriorityLevel.REHABILITATION)
            .priorityReason("Post-surgery recovery")
            .priorityScore(40)
            .isUrgent(false)
            .isCritical(false)
            .assignedDoctor("DR_REHAB")
            .assignedFacility("REHABILITATION_CENTER")
            .waitTime(4)
            .priorityStatus("ACTIVE")
            .priorityNotes("Rehabilitation program")
            .priorityDate(new Date())
            .priorityCategory("REHABILITATION")
            .priorityDuration(4)
            .isEscalated(false)
            .escalationReason("")
            .build();
        medicalPriorities.put(rehabilitationPriority.getPriorityId(), rehabilitationPriority);
        
        // Research Priority
        MedicalPriority researchPriority = MedicalPriority.builder()
            .priorityId("RESEARCH_PRIORITY_001")
            .patientId("PATIENT_009")
            .priorityLevel(MedicalPriority.PriorityLevel.RESEARCH)
            .priorityReason("Experimental treatment candidate")
            .priorityScore(70)
            .isUrgent(false)
            .isCritical(false)
            .assignedDoctor("DR_RESEARCH")
            .assignedFacility("RESEARCH_LABORATORY")
            .waitTime(2)
            .priorityStatus("ACTIVE")
            .priorityNotes("Research protocol treatment")
            .priorityDate(new Date())
            .priorityCategory("RESEARCH")
            .priorityDuration(2)
            .isEscalated(false)
            .escalationReason("")
            .build();
        medicalPriorities.put(researchPriority.getPriorityId(), researchPriority);
        
        // Psionic Priority
        MedicalPriority psionicPriority = MedicalPriority.builder()
            .priorityId("PSIONIC_PRIORITY_001")
            .patientId("PATIENT_010")
            .priorityLevel(MedicalPriority.PriorityLevel.PSIONIC)
            .priorityReason("Psionic trauma")
            .priorityScore(85)
            .isUrgent(true)
            .isCritical(false)
            .assignedDoctor("DR_PSIONIC")
            .assignedFacility("PSYCHIC_HEALING_CENTER")
            .waitTime(1)
            .priorityStatus("ACTIVE")
            .priorityNotes("Psionic healing treatment")
            .priorityDate(new Date())
            .priorityCategory("PSIONIC")
            .priorityDuration(1)
            .isEscalated(false)
            .escalationReason("")
            .build();
        medicalPriorities.put(psionicPriority.getPriorityId(), psionicPriority);
        
        // Alien Technology Priority
        MedicalPriority alienTechnologyPriority = MedicalPriority.builder()
            .priorityId("ALIEN_TECHNOLOGY_PRIORITY_001")
            .patientId("PATIENT_011")
            .priorityLevel(MedicalPriority.PriorityLevel.ALIEN_TECHNOLOGY)
            .priorityReason("Alien technology treatment")
            .priorityScore(95)
            .isUrgent(true)
            .isCritical(false)
            .assignedDoctor("DR_ALIEN")
            .assignedFacility("ALIEN_TECHNOLOGY_LAB")
            .waitTime(1)
            .priorityStatus("ACTIVE")
            .priorityNotes("Alien technology treatment")
            .priorityDate(new Date())
            .priorityCategory("ALIEN_TECHNOLOGY")
            .priorityDuration(1)
            .isEscalated(false)
            .escalationReason("")
            .build();
        medicalPriorities.put(alienTechnologyPriority.getPriorityId(), alienTechnologyPriority);

        // Urgent Priority (missing to reach 12 types)
        MedicalPriority urgentPriority = MedicalPriority.builder()
            .priorityId("URGENT_PRIORITY_001")
            .patientId("PATIENT_012")
            .priorityLevel(MedicalPriority.PriorityLevel.URGENT)
            .priorityReason("Severe pain")
            .priorityScore(85)
            .isUrgent(true)
            .isCritical(false)
            .assignedDoctor("DR_URGENT")
            .assignedFacility("EMERGENCY_ROOM")
            .waitTime(0)
            .priorityStatus("ACTIVE")
            .priorityNotes("Urgent care required")
            .priorityDate(new Date())
            .priorityCategory("URGENT")
            .priorityDuration(0)
            .isEscalated(false)
            .escalationReason("")
            .build();
        medicalPriorities.put(urgentPriority.getPriorityId(), urgentPriority);
    }
    
    /**
     * Start medical treatment
     */
    public boolean startTreatment(String patientId, String procedureId, String doctorId, String facilityId) {
        MedicalProcedure procedure = medicalProcedures.get(procedureId);
        MedicalFacility facility = medicalFacilities.get(facilityId);
        
        if (procedure == null || facility == null || !facility.isOperational()) {
            return false;
        }
        
        // Check if facility has capacity
        if (facility.getCurrentPatients() >= facility.getCapacity()) {
            return false;
        }
        
        // Create medical treatment
        MedicalTreatment treatment = MedicalTreatment.builder()
            .treatmentId("TREATMENT_" + patientId + "_" + System.currentTimeMillis())
            .patientId(patientId)
            .treatmentType(procedure.getProcedureType())
            .effectiveness(procedure.getEffectiveness())
            .duration(procedure.getDuration())
            .currentDuration(0)
            .isActive(true)
            .description(procedure.getDescription())
            .effects(new HashMap<>(procedure.getEffects()))
            .abilities(new ArrayList<>(procedure.getAbilities()))
            .cost(procedure.getCost())
            .assignedDoctor(doctorId)
            .assignedFacility(facilityId)
            .treatmentProgress(0)
            .isCompleted(false)
            .completionDate("")
            .treatmentStatus("ACTIVE")
            .treatmentNotes("Treatment started")
            .treatmentBonuses(new HashMap<>(procedure.getProcedureBonuses()))
            .treatmentRequirements(new ArrayList<>(procedure.getProcedureRequirements()))
            .treatmentCost(procedure.getProcedureCost())
            .treatmentDescription(procedure.getProcedureDescription())
            .manufacturer("XCOM_MEDICAL")
            .modelNumber("TREATMENT_001")
            .serialNumber("SN_TREATMENT_001")
            .purchaseDate(new Date().toString())
            .purchaseCost(procedure.getCost())
            .supplier("XCOM_SUPPLY")
            .location("MEDICAL_SYSTEM")
            .status("ACTIVE")
            .build();
        
        medicalTreatments.put(treatment.getTreatmentId(), treatment);
        
        // Update facility capacity
        facility.setCurrentPatients(facility.getCurrentPatients() + 1);
        
        // Add to active treatments
        activeTreatments.computeIfAbsent(patientId, k -> new ArrayList<>()).add(treatment.getTreatmentId());
        
        // Update patient records
        patientRecords.computeIfAbsent(patientId, k -> new ArrayList<>()).add(treatment.getTreatmentId());
        
        totalPatients++;
        
        return true;
    }
    
    /**
     * Process medical treatments
     */
    public void processTreatments() {
        for (MedicalTreatment treatment : medicalTreatments.values()) {
            if (treatment.isActive() && !treatment.isCompleted()) {
                processTreatment(treatment);
            }
        }
    }
    
    /**
     * Process specific treatment
     */
    private void processTreatment(MedicalTreatment treatment) {
        treatment.setCurrentDuration(treatment.getCurrentDuration() + 1);
        
        // Calculate treatment progress
        int progress = (treatment.getCurrentDuration() * 100) / treatment.getDuration();
        treatment.setTreatmentProgress(progress);
        
        // Check if treatment is complete
        if (treatment.getCurrentDuration() >= treatment.getDuration()) {
            completeTreatment(treatment);
        } else {
            // Update treatment status
            treatment.setTreatmentStatus("IN_PROGRESS (" + progress + "%)");
        }
    }
    
    /**
     * Complete treatment
     */
    private void completeTreatment(MedicalTreatment treatment) {
        treatment.setActive(false);
        treatment.setCompleted(true);
        treatment.setCompletionDate(new Date().toString());
        treatment.setTreatmentStatus("COMPLETED");
        
        // Update facility capacity
        MedicalFacility facility = medicalFacilities.get(treatment.getAssignedFacility());
        if (facility != null) {
            facility.setCurrentPatients(Math.max(0, facility.getCurrentPatients() - 1));
        }
        
        // Remove from active treatments
        List<String> patientTreatments = activeTreatments.get(treatment.getPatientId());
        if (patientTreatments != null) {
            patientTreatments.remove(treatment.getTreatmentId());
        }
        
        // Update treatment state
        treatmentStates.put(treatment.getTreatmentId(), true);
        
        totalPatients--;
    }
    
    /**
     * Get medical treatment by ID
     */
    public MedicalTreatment getMedicalTreatment(String treatmentId) {
        return medicalTreatments.get(treatmentId);
    }
    
    /**
     * Get medical facility by ID
     */
    public MedicalFacility getMedicalFacility(String facilityId) {
        return medicalFacilities.get(facilityId);
    }
    
    /**
     * Get medical equipment by ID
     */
    public MedicalEquipment getMedicalEquipment(String equipmentId) {
        return medicalEquipment.get(equipmentId);
    }
    
    /**
     * Get medical procedure by ID
     */
    public MedicalProcedure getMedicalProcedure(String procedureId) {
        return medicalProcedures.get(procedureId);
    }
    
    /**
     * Get medical priority by ID
     */
    public MedicalPriority getMedicalPriority(String priorityId) {
        return medicalPriorities.get(priorityId);
    }
    
    /**
     * Get all medical treatments
     */
    public Map<String, MedicalTreatment> getAllMedicalTreatments() {
        return new HashMap<>(medicalTreatments);
    }
    
    /**
     * Get all medical facilities
     */
    public Map<String, MedicalFacility> getAllMedicalFacilities() {
        return new HashMap<>(medicalFacilities);
    }
    
    /**
     * Get all medical equipment
     */
    public Map<String, MedicalEquipment> getAllMedicalEquipment() {
        return new HashMap<>(medicalEquipment);
    }
    
    /**
     * Get all medical procedures
     */
    public Map<String, MedicalProcedure> getAllMedicalProcedures() {
        return new HashMap<>(medicalProcedures);
    }
    
    /**
     * Get all medical priorities
     */
    public Map<String, MedicalPriority> getAllMedicalPriorities() {
        return new HashMap<>(medicalPriorities);
    }
    
    /**
     * Assign doctor to medical facility
     */
    public boolean assignDoctorToFacility(String doctorId, String facilityId) {
        MedicalFacility facility = medicalFacilities.get(facilityId);
        if (facility == null || !facility.isOperational()) {
            return false;
        }
        
        facility.setAssignedDoctor(doctorId);
        return true;
    }
    
    /**
     * Assign technician to medical facility
     */
    public boolean assignTechnicianToFacility(String technicianId, String facilityId) {
        MedicalFacility facility = medicalFacilities.get(facilityId);
        if (facility == null || !facility.isOperational()) {
            return false;
        }
        
        facility.setAssignedTechnician(technicianId);
        return true;
    }
    
    /**
     * Assign equipment to facility
     */
    public boolean assignEquipmentToFacility(String equipmentId, String facilityId) {
        MedicalEquipment equipment = medicalEquipment.get(equipmentId);
        MedicalFacility facility = medicalFacilities.get(facilityId);
        
        if (equipment == null || facility == null || !equipment.isOperational() || !facility.isOperational()) {
            return false;
        }
        
        equipment.setAssignedFacility(facilityId);
        equipment.setAssignedDoctor(facility.getAssignedDoctor());
        return true;
    }
    
    /**
     * Get available treatments for facility
     */
    public List<TreatmentType> getAvailableTreatmentsForFacility(String facilityId) {
        MedicalFacility facility = medicalFacilities.get(facilityId);
        if (facility == null) {
            return new ArrayList<>();
        }
        return new ArrayList<>(facility.getAvailableTreatments());
    }
    
    /**
     * Get facility capacity information
     */
    public Map<String, Object> getFacilityCapacityInfo(String facilityId) {
        MedicalFacility facility = medicalFacilities.get(facilityId);
        if (facility == null) {
            return new HashMap<>();
        }
        
        Map<String, Object> capacityInfo = new HashMap<>();
        capacityInfo.put("totalCapacity", facility.getCapacity());
        capacityInfo.put("currentPatients", facility.getCurrentPatients());
        capacityInfo.put("availableCapacity", facility.getCapacity() - facility.getCurrentPatients());
        capacityInfo.put("utilizationRate", (double) facility.getCurrentPatients() / facility.getCapacity());
        
        return capacityInfo;
    }
    
    /**
     * Get medical system statistics
     */
    public Map<String, Object> getMedicalSystemStatistics() {
        Map<String, Object> stats = new HashMap<>();
        stats.put("totalPatients", totalPatients);
        stats.put("maxMedicalCapacity", maxMedicalCapacity);
        stats.put("availableCapacity", maxMedicalCapacity - totalPatients);
        stats.put("totalFacilities", medicalFacilities.size());
        stats.put("totalEquipment", medicalEquipment.size());
        stats.put("totalProcedures", medicalProcedures.size());
        stats.put("totalPriorities", medicalPriorities.size());
        stats.put("activeTreatments", activeTreatments.size());
        
        // Calculate facility utilization
        double totalFacilityCapacity = medicalFacilities.values().stream()
            .mapToInt(MedicalFacility::getCapacity)
            .sum();
        double totalFacilityPatients = medicalFacilities.values().stream()
            .mapToInt(MedicalFacility::getCurrentPatients)
            .sum();
        stats.put("facilityUtilizationRate", totalFacilityCapacity > 0 ? totalFacilityPatients / totalFacilityCapacity : 0.0);
        
        return stats;
    }
    
    /**
     * Get patients by priority level
     */
    public List<String> getPatientsByPriorityLevel(MedicalPriority.PriorityLevel priorityLevel) {
        return medicalPriorities.values().stream()
            .filter(priority -> priority.getPriorityLevel() == priorityLevel)
            .map(MedicalPriority::getPatientId)
            .collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
    }
    
    /**
     * Get urgent patients
     */
    public List<String> getUrgentPatients() {
        return medicalPriorities.values().stream()
            .filter(MedicalPriority::isUrgent)
            .map(MedicalPriority::getPatientId)
            .collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
    }
    
    /**
     * Get critical patients
     */
    public List<String> getCriticalPatients() {
        return medicalPriorities.values().stream()
            .filter(MedicalPriority::isCritical)
            .map(MedicalPriority::getPatientId)
            .collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
    }
    
    /**
     * Update medical priority
     */
    public boolean updateMedicalPriority(String priorityId, MedicalPriority.PriorityLevel newLevel, int newScore) {
        MedicalPriority priority = medicalPriorities.get(priorityId);
        if (priority == null) {
            return false;
        }
        
        priority.setPriorityLevel(newLevel);
        priority.setPriorityScore(newScore);
        
        // Update urgency and critical status based on new level
        priority.setUrgent(newLevel == MedicalPriority.PriorityLevel.URGENT || 
                          newLevel == MedicalPriority.PriorityLevel.CRITICAL ||
                          newLevel == MedicalPriority.PriorityLevel.EMERGENCY ||
                          newLevel == MedicalPriority.PriorityLevel.TRAUMA ||
                          newLevel == MedicalPriority.PriorityLevel.INFECTION ||
                          newLevel == MedicalPriority.PriorityLevel.PSIONIC ||
                          newLevel == MedicalPriority.PriorityLevel.ALIEN_TECHNOLOGY);
        
        priority.setCritical(newLevel == MedicalPriority.PriorityLevel.CRITICAL ||
                           newLevel == MedicalPriority.PriorityLevel.EMERGENCY ||
                           newLevel == MedicalPriority.PriorityLevel.TRAUMA);
        
        return true;
    }
    
    /**
     * Escalate medical priority
     */
    public boolean escalateMedicalPriority(String priorityId, String escalationReason) {
        MedicalPriority priority = medicalPriorities.get(priorityId);
        if (priority == null) {
            return false;
        }
        
        priority.setEscalated(true);
        priority.setEscalationReason(escalationReason);
        
        // Increase priority score for escalation
        priority.setPriorityScore(priority.getPriorityScore() + 20);
        
        return true;
    }
    
    /**
     * Get treatment recommendations for patient
     */
    public List<MedicalProcedure> getTreatmentRecommendations(String patientId) {
        // This would implement logic to recommend treatments based on patient condition
        // For now, return all available procedures
        return new ArrayList<>(medicalProcedures.values());
    }
    
    /**
     * Check if patient can be treated at facility
     */
    public boolean canPatientBeTreatedAtFacility(String patientId, String facilityId) {
        MedicalFacility facility = medicalFacilities.get(facilityId);
        if (facility == null || !facility.isOperational()) {
            return false;
        }
        
        // Check capacity
        if (facility.getCurrentPatients() >= facility.getCapacity()) {
            return false;
        }
        
        // Check if facility has appropriate treatments for patient's needs
        // This would require more complex logic based on patient condition
        return true;
    }
    
    /**
     * Transfer patient between facilities
     */
    public boolean transferPatient(String patientId, String fromFacilityId, String toFacilityId) {
        MedicalFacility fromFacility = medicalFacilities.get(fromFacilityId);
        MedicalFacility toFacility = medicalFacilities.get(toFacilityId);
        
        if (fromFacility == null || toFacility == null || 
            !fromFacility.isOperational() || !toFacility.isOperational()) {
            return false;
        }
        
        // Check if target facility has capacity
        if (toFacility.getCurrentPatients() >= toFacility.getCapacity()) {
            return false;
        }
        
        // Update facility patient counts
        fromFacility.setCurrentPatients(Math.max(0, fromFacility.getCurrentPatients() - 1));
        toFacility.setCurrentPatients(toFacility.getCurrentPatients() + 1);
        
        // Update patient records
        // This would require more complex logic to update treatment assignments
        
        return true;
    }
    
    /**
     * Get medical facility maintenance schedule
     */
    public Map<String, String> getFacilityMaintenanceSchedule() {
        Map<String, String> maintenanceSchedule = new HashMap<>();
        
        for (MedicalFacility facility : medicalFacilities.values()) {
            if (facility.getNextMaintenanceDate() != null && !facility.getNextMaintenanceDate().isEmpty()) {
                maintenanceSchedule.put(facility.getFacilityId(), facility.getNextMaintenanceDate());
            }
        }
        
        return maintenanceSchedule;
    }
    
    /**
     * Schedule facility maintenance
     */
    public boolean scheduleFacilityMaintenance(String facilityId, String maintenanceDate) {
        MedicalFacility facility = medicalFacilities.get(facilityId);
        if (facility == null) {
            return false;
        }
        
        facility.setNextMaintenanceDate(maintenanceDate);
        return true;
    }
    
    /**
     * Perform facility maintenance
     */
    public boolean performFacilityMaintenance(String facilityId) {
        MedicalFacility facility = medicalFacilities.get(facilityId);
        if (facility == null) {
            return false;
        }
        
        facility.setLastMaintenanceDate(new Date().toString());
        facility.setFacilityCondition("MAINTAINED");
        
        // Reset maintenance costs
        facility.setMaintenanceCost(facility.getMaintenanceCost() / 2);
        
        return true;
    }
    
    /**
     * Get medical equipment status
     */
    public Map<String, String> getMedicalEquipmentStatus() {
        Map<String, String> equipmentStatus = new HashMap<>();
        
        for (MedicalEquipment equipment : medicalEquipment.values()) {
            equipmentStatus.put(equipment.getEquipmentId(), equipment.getEquipmentStatus());
        }
        
        return equipmentStatus;
    }
    
    /**
     * Repair medical equipment
     */
    public boolean repairMedicalEquipment(String equipmentId) {
        MedicalEquipment equipment = medicalEquipment.get(equipmentId);
        if (equipment == null) {
            return false;
        }
        
        equipment.setCurrentDurability(equipment.getDurability());
        equipment.setEquipmentStatus("OPERATIONAL");
        equipment.setEquipmentCondition("REPAIRED");
        
        return true;
    }
    
    /**
     * Get treatment effectiveness statistics
     */
    public Map<String, Double> getTreatmentEffectivenessStatistics() {
        Map<String, Double> effectivenessStats = new HashMap<>();
        
        for (MedicalProcedure procedure : medicalProcedures.values()) {
            effectivenessStats.put(procedure.getProcedureId(), (double) procedure.getEffectiveness());
        }
        
        return effectivenessStats;
    }
    
    /**
     * Activate medical system
     */
    public boolean activateMedicalSystem() {
        this.isMedicalSystemActive = true;
        return true;
    }
    
    /**
     * Deactivate medical system
     */
    public boolean deactivateMedicalSystem() {
        this.isMedicalSystemActive = false;
        return true;
    }
    
    /**
     * Check if medical system is active
     */
    public boolean isMedicalSystemActive() {
        return this.isMedicalSystemActive;
    }
}
