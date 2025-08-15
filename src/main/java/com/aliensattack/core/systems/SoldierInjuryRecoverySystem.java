package com.aliensattack.core.systems;

import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.*;
import java.util.Date;

/**
 * Soldier Injury Recovery System for medical management.
 * Implements injury tracking, recovery mechanics, and medical treatment.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SoldierInjuryRecoverySystem {
    
    private String injurySystemId;
    private Map<String, SoldierInjury> soldierInjuries;
    private Map<String, InjuryType> injuryTypes;
    private Map<String, RecoveryPlan> recoveryPlans;
    private Map<String, MedicalFacility> medicalFacilities;
    private Map<String, MedicalPriority> medicalPriorities;
    private Map<String, RehabilitationProgram> rehabilitationPrograms;
    private Map<String, List<String>> injuryHistory;
    private Map<String, Map<String, Integer>> injurySeverities;
    private Map<String, List<String>> activeInjuries;
    private Map<String, Integer> recoveryTimes;
    private Map<String, Boolean> recoveryStates;
    private int totalInjuredSoldiers;
    private int maxMedicalCapacity;
    private boolean isRecoveryActive;
    
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SoldierInjury {
        private String injuryId;
        private String soldierId;
        private InjuryType injuryType;
        private int severity;
        private int maxSeverity;
        private int recoveryTime;
        private int currentRecoveryTime;
        private boolean isHealing;
        private boolean isCritical;
        private boolean isPermanent;
        private String injuryLocation;
        private String injuryDescription;
        private Map<String, Integer> injuryEffects;
        private List<String> injuryComplications;
        private String medicalPriority;
        private String assignedFacility;
        private String assignedDoctor;
        private boolean isStabilized;
        private boolean isInSurgery;
        private boolean isInRehabilitation;
        private String recoveryStatus;
        private int medicalCost;
        private String insuranceStatus;
        private boolean isDischarged;
        private String dischargeDate;
        private String returnToDutyDate;
        private String permanentDisability;
        private String medicalNotes;
    }
    
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class InjuryType {
        private String injuryTypeId;
        private String injuryTypeName;
        private InjuryCategory injuryCategory;
        private int baseRecoveryTime;
        private int baseMedicalCost;
        private Map<String, Integer> injuryEffects;
        private List<String> possibleComplications;
        private String treatmentMethod;
        private int treatmentSuccessRate;
        private String preventionMethod;
        private boolean isPreventable;
        private boolean isTreatable;
        private boolean isCurable;
        private String description;
        private int severityLevel;
        private String riskFactors;
        private String symptoms;
        private String diagnosis;
        private String prognosis;
        
        public enum InjuryCategory {
            BULLET_WOUND,      // Gunshot wounds
            BURN_INJURY,       // Burn injuries
            FRACTURE,          // Bone fractures
            CONCUSSION,        // Head injuries
            POISONING,         // Chemical poisoning
            RADIATION,         // Radiation exposure
            PSYCHIC_TRAUMA,    // Mental trauma
            DISEASE,           // Infectious diseases
            AMPUTATION,        // Limb loss
            INTERNAL_DAMAGE,   // Internal organ damage
            SHOCK,             // Shock and trauma
            INFECTION,         // Wound infections
            PARALYSIS,         // Nerve damage
            BLINDNESS,         // Eye injuries
            DEAFNESS          // Hearing damage
        }
    }
    
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class RecoveryPlan {
        private String recoveryPlanId;
        private String soldierId;
        private String injuryId;
        private RecoveryPhase currentPhase;
        private List<RecoveryPhase> phases;
        private int totalRecoveryTime;
        private int currentRecoveryTime;
        private boolean isActive;
        private String assignedDoctor;
        private String assignedFacility;
        private Map<String, Integer> recoveryBonuses;
        private List<String> recoveryActivities;
        private String recoveryStatus;
        private int recoveryProgress;
        private boolean isCompleted;
        private String completionDate;
        private String notes;
        
        public enum RecoveryPhase {
            STABILIZATION,     // Initial stabilization
            SURGERY,           // Surgical procedures
            RECOVERY,          // Post-surgery recovery
            REHABILITATION,    // Physical therapy
            THERAPY,           // Mental health therapy
            TRAINING,          // Return to training
            EVALUATION,        // Medical evaluation
            CLEARANCE          // Return to duty clearance
        }
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
        private int currentOccupancy;
        private List<String> availableDoctors;
        private List<String> availableEquipment;
        private Map<String, Integer> facilityBonuses;
        private List<String> facilityServices;
        private boolean isOperational;
        private int facilityLevel;
        private int facilityQuality;
        private String location;
        private int operatingCost;
        private int maintenanceCost;
        private String facilityStatus;
        private List<String> patients;
        private Map<String, Integer> treatmentSuccessRates;
        private List<String> specializations;
        private String facilityDescription;
        
        public enum FacilityType {
            EMERGENCY_ROOM,    // Emergency treatment
            SURGERY_CENTER,    // Surgical procedures
            INTENSIVE_CARE,    // Critical care
            REHABILITATION,    // Physical therapy
            PSYCHIATRIC_WARD,  // Mental health
            RESEARCH_LAB,      // Medical research
            QUARANTINE_WARD,   // Infectious diseases
            BURNS_UNIT,        // Burn treatment
            ORTHOPEDICS,       // Bone and joint
            NEUROLOGY,         // Brain and nerve
            CARDIOLOGY,        // Heart treatment
            ONCOLOGY,          // Cancer treatment
            PEDIATRICS,        // Child care
            GERIATRICS,        // Elderly care
            TRAUMA_CENTER      // Trauma treatment
        }
    }
    
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MedicalPriority {
        private String priorityId;
        private String soldierId;
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
            CRITICAL,      // Immediate attention required
            URGENT,        // High priority
            HIGH,          // Above normal priority
            NORMAL,        // Standard priority
            LOW,           // Below normal priority
            ROUTINE,       // Non-urgent
            ELECTIVE,      // Optional treatment
            MAINTENANCE    // Preventive care
        }
    }
    
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class RehabilitationProgram {
        private String programId;
        private String soldierId;
        private ProgramType programType;
        private int programDuration;
        private int currentDuration;
        private List<String> programActivities;
        private Map<String, Integer> programGoals;
        private List<String> programMilestones;
        private boolean isActive;
        private String assignedTherapist;
        private String assignedFacility;
        private int programProgress;
        private boolean isCompleted;
        private String completionDate;
        private String programStatus;
        private String programNotes;
        private Map<String, Integer> programBonuses;
        private List<String> programRequirements;
        private int programCost;
        private String programDescription;
        
        public enum ProgramType {
            PHYSICAL_THERAPY,    // Physical rehabilitation
            OCCUPATIONAL_THERAPY, // Daily living skills
            SPEECH_THERAPY,      // Communication skills
            PSYCHOLOGICAL_THERAPY, // Mental health
            COGNITIVE_THERAPY,   // Brain function
            VOCATIONAL_THERAPY,  // Work skills
            RECREATIONAL_THERAPY, // Leisure activities
            RESPIRATORY_THERAPY, // Breathing therapy
            CARDIOVASCULAR_THERAPY, // Heart health
            NEUROLOGICAL_THERAPY, // Nerve function
            ORTHOPEDIC_THERAPY,  // Bone and joint
            BURN_THERAPY,        // Burn recovery
            AMPUTEE_THERAPY,     // Limb loss adaptation
            SPINAL_THERAPY,      // Spinal cord injury
            PEDIATRIC_THERAPY    // Child therapy
        }
    }
    

    
    /**
     * Initialize the injury system
     */
    public void initializeSystem() {
        this.injurySystemId = "INJURY_SYSTEM_" + System.currentTimeMillis();
        this.soldierInjuries = new HashMap<>();
        this.injuryTypes = new HashMap<>();
        this.recoveryPlans = new HashMap<>();
        this.medicalFacilities = new HashMap<>();
        this.medicalPriorities = new HashMap<>();
        this.rehabilitationPrograms = new HashMap<>();
        this.injuryHistory = new HashMap<>();
        this.injurySeverities = new HashMap<>();
        this.activeInjuries = new HashMap<>();
        this.recoveryTimes = new HashMap<>();
        this.recoveryStates = new HashMap<>();
        this.totalInjuredSoldiers = 0;
        this.maxMedicalCapacity = 20;
        this.isRecoveryActive = false;
        
        initializeInjuryTypes();
        initializeMedicalFacilities();
        initializeMedicalPriorities();
        initializeRehabilitationPrograms();
    }
    
    /**
     * Initialize injury types
     */
    private void initializeInjuryTypes() {
        // Bullet wound
        InjuryType bulletWound = InjuryType.builder()
            .injuryTypeId("BULLET_WOUND")
            .injuryTypeName("Bullet Wound")
            .injuryCategory(InjuryType.InjuryCategory.BULLET_WOUND)
            .baseRecoveryTime(14)
            .baseMedicalCost(5000)
            .injuryEffects(Map.of("damage", 20, "bleeding", 10, "infection_risk", 15))
            .possibleComplications(Arrays.asList("INFECTION", "NERVE_DAMAGE", "SCAR_TISSUE"))
            .treatmentMethod("SURGERY")
            .treatmentSuccessRate(85)
            .preventionMethod("BODY_ARMOR")
            .isPreventable(true)
            .isTreatable(true)
            .isCurable(true)
            .description("Gunshot wound requiring immediate medical attention")
            .severityLevel(3)
            .riskFactors("Combat exposure, lack of armor")
            .symptoms("Pain, bleeding, tissue damage")
            .diagnosis("Physical examination, X-ray")
            .prognosis("Good with proper treatment")
            .build();
        
        injuryTypes.put("BULLET_WOUND", bulletWound);
        
        // Burn injury
        InjuryType burnInjury = InjuryType.builder()
            .injuryTypeId("BURN_INJURY")
            .injuryTypeName("Burn Injury")
            .injuryCategory(InjuryType.InjuryCategory.BURN_INJURY)
            .baseRecoveryTime(21)
            .baseMedicalCost(8000)
            .injuryEffects(Map.of("damage", 25, "infection_risk", 20, "scarring", 30))
            .possibleComplications(Arrays.asList("INFECTION", "SCARRING", "CONTRACTURES"))
            .treatmentMethod("SKIN_GRAFT")
            .treatmentSuccessRate(75)
            .preventionMethod("FIRE_PROTECTION")
            .isPreventable(true)
            .isTreatable(true)
            .isCurable(true)
            .description("Thermal injury requiring specialized burn treatment")
            .severityLevel(4)
            .riskFactors("Fire exposure, explosion proximity")
            .symptoms("Pain, blistering, tissue damage")
            .diagnosis("Visual examination, depth assessment")
            .prognosis("Variable based on burn depth")
            .build();
        
        injuryTypes.put("BURN_INJURY", burnInjury);
        
        // Fracture
        InjuryType fracture = InjuryType.builder()
            .injuryTypeId("FRACTURE")
            .injuryTypeName("Bone Fracture")
            .injuryCategory(InjuryType.InjuryCategory.FRACTURE)
            .baseRecoveryTime(42)
            .baseMedicalCost(3000)
            .injuryEffects(Map.of("mobility", -50, "pain", 15, "healing_time", 30))
            .possibleComplications(Arrays.asList("MALUNION", "NONUNION", "INFECTION"))
            .treatmentMethod("CASTING_SURGERY")
            .treatmentSuccessRate(90)
            .preventionMethod("PROTECTIVE_GEAR")
            .isPreventable(true)
            .isTreatable(true)
            .isCurable(true)
            .description("Broken bone requiring immobilization and healing")
            .severityLevel(2)
            .riskFactors("Falls, impacts, overuse")
            .symptoms("Pain, swelling, deformity")
            .diagnosis("X-ray, physical examination")
            .prognosis("Excellent with proper treatment")
            .build();
        
        injuryTypes.put("FRACTURE", fracture);
    }
    
    /**
     * Initialize medical facilities
     */
    private void initializeMedicalFacilities() {
        // Emergency Room
        MedicalFacility emergencyRoom = MedicalFacility.builder()
            .facilityId("EMERGENCY_ROOM")
            .facilityName("Emergency Room")
            .facilityType(MedicalFacility.FacilityType.EMERGENCY_ROOM)
            .capacity(20)
            .currentOccupancy(0)
            .availableDoctors(Arrays.asList("DR_SMITH", "DR_JOHNSON", "DR_WILLIAMS"))
            .availableEquipment(Arrays.asList("DEFIBRILLATOR", "VENTILATOR", "MONITOR"))
            .facilityBonuses(Map.of("emergency_treatment", 25, "stabilization", 30))
            .facilityServices(Arrays.asList("TRAUMA_CARE", "STABILIZATION", "EMERGENCY_SURGERY"))
            .isOperational(true)
            .facilityLevel(3)
            .facilityQuality(85)
            .location("MAIN_BASE")
            .operatingCost(5000)
            .maintenanceCost(1000)
            .facilityStatus("OPERATIONAL")
            .patients(new ArrayList<>())
            .treatmentSuccessRates(Map.of("STABILIZATION", 95, "EMERGENCY_SURGERY", 80))
            .specializations(Arrays.asList("TRAUMA", "EMERGENCY_MEDICINE", "CRITICAL_CARE"))
            .facilityDescription("Primary emergency treatment facility")
            .build();
        
        medicalFacilities.put("EMERGENCY_ROOM", emergencyRoom);
        
        // Surgery Center
        MedicalFacility surgeryCenter = MedicalFacility.builder()
            .facilityId("SURGERY_CENTER")
            .facilityName("Surgery Center")
            .facilityType(MedicalFacility.FacilityType.SURGERY_CENTER)
            .capacity(10)
            .currentOccupancy(0)
            .availableDoctors(Arrays.asList("DR_BROWN", "DR_DAVIS", "DR_MILLER"))
            .availableEquipment(Arrays.asList("SURGICAL_TABLE", "ANESTHESIA_MACHINE", "SURGICAL_TOOLS"))
            .facilityBonuses(Map.of("surgical_procedures", 30, "precision", 25))
            .facilityServices(Arrays.asList("GENERAL_SURGERY", "ORTHOPEDIC_SURGERY", "PLASTIC_SURGERY"))
            .isOperational(true)
            .facilityLevel(4)
            .facilityQuality(90)
            .location("MAIN_BASE")
            .operatingCost(8000)
            .maintenanceCost(2000)
            .facilityStatus("OPERATIONAL")
            .patients(new ArrayList<>())
            .treatmentSuccessRates(Map.of("GENERAL_SURGERY", 85, "ORTHOPEDIC_SURGERY", 90))
            .specializations(Arrays.asList("GENERAL_SURGERY", "ORTHOPEDICS", "PLASTIC_SURGERY"))
            .facilityDescription("Advanced surgical facility")
            .build();
        
        medicalFacilities.put("SURGERY_CENTER", surgeryCenter);
    }
    
    /**
     * Initialize medical priorities
     */
    private void initializeMedicalPriorities() {
        // Example priorities
        MedicalPriority criticalPriority = MedicalPriority.builder()
            .priorityId("CRITICAL_PRIORITY_1")
            .soldierId("SOLDIER_1")
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

        MedicalPriority urgentPriority = MedicalPriority.builder()
            .priorityId("URGENT_PRIORITY_1")
            .soldierId("SOLDIER_2")
            .priorityLevel(MedicalPriority.PriorityLevel.URGENT)
            .priorityReason("High fever")
            .priorityScore(70)
            .isUrgent(true)
            .isCritical(false)
            .assignedDoctor("DR_JOHNSON")
            .assignedFacility("EMERGENCY_ROOM")
            .waitTime(0)
            .priorityStatus("ACTIVE")
            .priorityNotes("High priority")
            .priorityDate(new Date())
            .priorityCategory("URGENT")
            .priorityDuration(0)
            .isEscalated(false)
            .escalationReason("")
            .build();
        medicalPriorities.put(urgentPriority.getPriorityId(), urgentPriority);

        MedicalPriority normalPriority = MedicalPriority.builder()
            .priorityId("NORMAL_PRIORITY_1")
            .soldierId("SOLDIER_3")
            .priorityLevel(MedicalPriority.PriorityLevel.NORMAL)
            .priorityReason("Minor wound")
            .priorityScore(30)
            .isUrgent(false)
            .isCritical(false)
            .assignedDoctor("DR_WILLIAMS")
            .assignedFacility("SURGERY_CENTER")
            .waitTime(0)
            .priorityStatus("ACTIVE")
            .priorityNotes("Standard priority")
            .priorityDate(new Date())
            .priorityCategory("NORMAL")
            .priorityDuration(0)
            .isEscalated(false)
            .escalationReason("")
            .build();
        medicalPriorities.put(normalPriority.getPriorityId(), normalPriority);
    }

    /**
     * Initialize rehabilitation programs
     */
    private void initializeRehabilitationPrograms() {
        // Example programs
        RehabilitationProgram physicalTherapy = RehabilitationProgram.builder()
            .programId("PHYSICAL_THERAPY_1")
            .soldierId("SOLDIER_1")
            .programType(RehabilitationProgram.ProgramType.PHYSICAL_THERAPY)
            .programDuration(30)
            .currentDuration(0)
            .programActivities(Arrays.asList("Walking", "Stretching", "Light exercises"))
            .programGoals(Map.of("mobility", 100, "strength", 50))
            .programMilestones(Arrays.asList("Day 1: Walk 50m", "Day 5: Walk 100m", "Day 10: Walk 200m"))
            .isActive(true)
            .assignedTherapist("DR_SMITH")
            .assignedFacility("SURGERY_CENTER")
            .programProgress(0)
            .isCompleted(false)
            .completionDate("")
            .programStatus("ACTIVE")
            .programNotes("Initial physical therapy")
            .programBonuses(Map.of("mobility_bonus", 10, "strength_bonus", 5))
            .programRequirements(Arrays.asList("Follow doctor's instructions", "Attend sessions"))
            .programCost(1000)
            .programDescription("Physical therapy to regain mobility and strength")
            .build();
        rehabilitationPrograms.put(physicalTherapy.getProgramId(), physicalTherapy);

        RehabilitationProgram occupationalTherapy = RehabilitationProgram.builder()
            .programId("OCCUPATIONAL_THERAPY_1")
            .soldierId("SOLDIER_2")
            .programType(RehabilitationProgram.ProgramType.OCCUPATIONAL_THERAPY)
            .programDuration(60)
            .currentDuration(0)
            .programActivities(Arrays.asList("Cooking", "Cleaning", "Basic tasks"))
            .programGoals(Map.of("independence", 100, "confidence", 50))
            .programMilestones(Arrays.asList("Day 1: Cook simple meal", "Day 10: Cook complex meal", "Day 30: Manage household"))
            .isActive(true)
            .assignedTherapist("DR_JOHNSON")
            .assignedFacility("SURGERY_CENTER")
            .programProgress(0)
            .isCompleted(false)
            .completionDate("")
            .programStatus("ACTIVE")
            .programNotes("Occupational therapy to regain daily living skills")
            .programBonuses(Map.of("independence_bonus", 10, "confidence_bonus", 5))
            .programRequirements(Arrays.asList("Attend sessions", "Practice daily"))
            .programCost(2000)
            .programDescription("Occupational therapy to regain daily living skills")
            .build();
        rehabilitationPrograms.put(occupationalTherapy.getProgramId(), occupationalTherapy);
    }
    
    /**
     * Register soldier injury
     */
    public boolean registerInjury(String soldierId, String injuryTypeId, int severity, String location) {
        InjuryType injuryType = injuryTypes.get(injuryTypeId);
        if (injuryType == null) {
            return false;
        }
        
        String injuryId = "INJURY_" + soldierId + "_" + System.currentTimeMillis();
        
        SoldierInjury injury = SoldierInjury.builder()
            .injuryId(injuryId)
            .soldierId(soldierId)
            .injuryType(injuryType)
            .severity(severity)
            .maxSeverity(10)
            .recoveryTime(injuryType.getBaseRecoveryTime())
            .currentRecoveryTime(0)
            .isHealing(false)
            .isCritical(severity >= 8)
            .isPermanent(severity >= 10)
            .injuryLocation(location)
            .injuryDescription(injuryType.getDescription())
            .injuryEffects(new HashMap<>(injuryType.getInjuryEffects()))
            .injuryComplications(new ArrayList<>(injuryType.getPossibleComplications()))
            .medicalPriority(severity >= 8 ? "CRITICAL" : severity >= 5 ? "URGENT" : "NORMAL")
            .assignedFacility("")
            .assignedDoctor("")
            .isStabilized(false)
            .isInSurgery(false)
            .isInRehabilitation(false)
            .recoveryStatus("REGISTERED")
            .medicalCost(injuryType.getBaseMedicalCost())
            .insuranceStatus("COVERED")
            .isDischarged(false)
            .dischargeDate("")
            .returnToDutyDate("")
            .permanentDisability("")
            .medicalNotes("")
            .build();
        
        soldierInjuries.put(injuryId, injury);
        injuryHistory.put(soldierId, injuryHistory.getOrDefault(soldierId, new ArrayList<>()));
        injuryHistory.get(soldierId).add(injuryId);
        injurySeverities.put(soldierId, injurySeverities.getOrDefault(soldierId, new HashMap<>()));
        injurySeverities.get(soldierId).put(injuryTypeId, severity);
        activeInjuries.put(soldierId, activeInjuries.getOrDefault(soldierId, new ArrayList<>()));
        activeInjuries.get(soldierId).add(injuryId);
        recoveryTimes.put(injuryId, injuryType.getBaseRecoveryTime());
        recoveryStates.put(injuryId, false);
        
        totalInjuredSoldiers++;
        
        // Create medical priority
        createMedicalPriority(soldierId, injury);
        
        return true;
    }
    
    /**
     * Create medical priority for injury
     */
    private void createMedicalPriority(String soldierId, SoldierInjury injury) {
        String priorityId = "PRIORITY_" + soldierId + "_" + System.currentTimeMillis();
        
        MedicalPriority.PriorityLevel priorityLevel;
        if (injury.isCritical()) {
            priorityLevel = MedicalPriority.PriorityLevel.CRITICAL;
        } else if (injury.getSeverity() >= 5) {
            priorityLevel = MedicalPriority.PriorityLevel.URGENT;
        } else {
            priorityLevel = MedicalPriority.PriorityLevel.NORMAL;
        }
        
        MedicalPriority priority = MedicalPriority.builder()
            .priorityId(priorityId)
            .soldierId(soldierId)
            .priorityLevel(priorityLevel)
            .priorityReason(injury.getInjuryType().getInjuryTypeName())
            .priorityScore(injury.getSeverity() * 10)
            .isUrgent(injury.getSeverity() >= 5)
            .isCritical(injury.isCritical())
            .assignedDoctor("")
            .assignedFacility("")
            .waitTime(0)
            .priorityStatus("PENDING")
            .priorityNotes("")
            .priorityDate(new Date())
            .priorityCategory(injury.getInjuryType().getInjuryCategory().name())
            .priorityDuration(0)
            .isEscalated(false)
            .escalationReason("")
            .build();
        
        medicalPriorities.put(priorityId, priority);
    }
    
    /**
     * Start recovery process
     */
    public boolean startRecovery(String injuryId, String facilityId, String doctorId) {
        SoldierInjury injury = soldierInjuries.get(injuryId);
        if (injury == null) {
            return false;
        }
        
        injury.setAssignedFacility(facilityId);
        injury.setAssignedDoctor(doctorId);
        injury.setHealing(true);
        injury.setRecoveryStatus("IN_TREATMENT");
        
        // Create recovery plan
        RecoveryPlan recoveryPlan = RecoveryPlan.builder()
            .recoveryPlanId("RECOVERY_" + injuryId)
            .soldierId(injury.getSoldierId())
            .injuryId(injuryId)
            .currentPhase(RecoveryPlan.RecoveryPhase.STABILIZATION)
            .phases(Arrays.asList(RecoveryPlan.RecoveryPhase.values()))
            .totalRecoveryTime(injury.getRecoveryTime())
            .currentRecoveryTime(0)
            .isActive(true)
            .assignedDoctor(doctorId)
            .assignedFacility(facilityId)
            .recoveryBonuses(new HashMap<>())
            .recoveryActivities(new ArrayList<>())
            .recoveryStatus("ACTIVE")
            .recoveryProgress(0)
            .isCompleted(false)
            .completionDate("")
            .notes("")
            .build();
        
        recoveryPlans.put(recoveryPlan.getRecoveryPlanId(), recoveryPlan);
        
        return true;
    }
    
    /**
     * Process recovery for all injuries
     */
    public void processRecovery() {
        for (SoldierInjury injury : soldierInjuries.values()) {
            if (injury.isHealing() && !injury.isPermanent()) {
                processInjuryRecovery(injury);
            }
        }
    }
    
    /**
     * Process recovery for specific injury
     */
    private void processInjuryRecovery(SoldierInjury injury) {
        injury.setCurrentRecoveryTime(injury.getCurrentRecoveryTime() + 1);
        
        // Check if recovery is complete
        if (injury.getCurrentRecoveryTime() >= injury.getRecoveryTime()) {
            completeRecovery(injury);
        } else {
            // Update recovery status
            int progress = (injury.getCurrentRecoveryTime() * 100) / injury.getRecoveryTime();
            injury.setRecoveryStatus("RECOVERING (" + progress + "%)");
        }
    }
    
    /**
     * Complete recovery for injury
     */
    private void completeRecovery(SoldierInjury injury) {
        injury.setHealing(false);
        injury.setRecoveryStatus("COMPLETED");
        injury.setReturnToDutyDate(new Date().toString());
        
        // Remove from active injuries
        List<String> soldierInjuries = activeInjuries.get(injury.getSoldierId());
        if (soldierInjuries != null) {
            soldierInjuries.remove(injury.getInjuryId());
        }
        
        // Update recovery state
        recoveryStates.put(injury.getInjuryId(), true);
        
        totalInjuredSoldiers--;
    }
    
    /**
     * Get injury by ID
     */
    public SoldierInjury getInjury(String injuryId) {
        return soldierInjuries.get(injuryId);
    }
    
    /**
     * Get active injuries for soldier
     */
    public List<String> getActiveInjuries(String soldierId) {
        return activeInjuries.getOrDefault(soldierId, new ArrayList<>());
    }
    
    /**
     * Get injury history for soldier
     */
    public List<String> getInjuryHistory(String soldierId) {
        return injuryHistory.getOrDefault(soldierId, new ArrayList<>());
    }
    
    /**
     * Get recovery time for injury
     */
    public int getRecoveryTime(String injuryId) {
        return recoveryTimes.getOrDefault(injuryId, 0);
    }
    
    /**
     * Check if injury is recovered
     */
    public boolean isInjuryRecovered(String injuryId) {
        return recoveryStates.getOrDefault(injuryId, false);
    }
    
    /**
     * Get total injured soldiers
     */
    public int getTotalInjuredSoldiers() {
        return totalInjuredSoldiers;
    }
    
    /**
     * Get medical facility by ID
     */
    public MedicalFacility getMedicalFacility(String facilityId) {
        return medicalFacilities.get(facilityId);
    }
    
    /**
     * Get medical priority by ID
     */
    public MedicalPriority getMedicalPriority(String priorityId) {
        return medicalPriorities.get(priorityId);
    }
    
    /**
     * Get recovery plan by ID
     */
    public RecoveryPlan getRecoveryPlan(String planId) {
        return recoveryPlans.get(planId);
    }
}
