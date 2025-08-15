package com.aliensattack.core.model;

import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.util.*;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

/**
 * Comprehensive Injury System for AliensAttack
 * Handles soldier injuries, recovery, medical treatment, and permanent consequences
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InjurySystem {
    
    // Singleton support to ensure shared state across integrations and tests
    private static InjurySystem instance;
    
    public static synchronized InjurySystem getInstance() {
        return instance;
    }
    
    // Core injury management
    private Map<String, List<Injury>> soldierInjuries;
    private Map<String, RecoveryStatus> recoveryStatuses;
    private Map<String, MedicalTreatment> activeTreatments;
    private Map<String, Boolean> permanentInjuries;
    
    // Medical facilities and capacity
    private List<MedicalFacility> medicalFacilities;
    private Map<String, MedicalFacility> assignedFacilities;
    private int totalMedicalCapacity;
    private int availableMedicalCapacity;
    
    // Recovery tracking
    private Map<String, LocalDateTime> injuryDates;
    private Map<String, LocalDateTime> expectedRecoveryDates;
    private Map<String, Integer> recoveryProgress;
    
    // Initialize the system with default values
    public void initialize() {
        this.soldierInjuries = new HashMap<>();
        this.recoveryStatuses = new HashMap<>();
        this.activeTreatments = new HashMap<>();
        this.permanentInjuries = new HashMap<>();
        this.medicalFacilities = new ArrayList<>();
        this.assignedFacilities = new HashMap<>();
        this.injuryDates = new HashMap<>();
        this.expectedRecoveryDates = new HashMap<>();
        this.recoveryProgress = new HashMap<>();
        this.totalMedicalCapacity = 10;
        this.availableMedicalCapacity = 10;
        
        initializeMedicalFacilities();
        // Register this initialized instance as the shared singleton
        instance = this;
    }
    
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Injury {
        private String injuryId;
        private String soldierId;
        private InjuryType type;
        private InjurySeverity severity;
        private LocalDateTime injuryDate;
        private LocalDateTime expectedRecoveryDate;
        private int recoveryDays;
        private boolean isPermanent;
        private List<InjuryEffect> effects;
        private String description;
        private InjurySource source;
        private boolean isTreated;
    }
    
    public enum InjuryType {
        GUNSHOT_WOUND, EXPLOSION_BLAST, BURN_INJURY, FRACTURE, CONCUSSION, 
        PSYCHOLOGICAL_TRAUMA, POISONING, RADIATION_EXPOSURE, ACID_BURN, 
        ELECTRICAL_SHOCK, FALL_DAMAGE, MELEE_WOUND, DISLOCATION, 
        INTERNAL_BLEEDING, INFECTION
    }
    
    public enum InjurySeverity {
        LIGHT(1, 3, 0.05), MODERATE(2, 7, 0.15), SEVERE(3, 14, 0.30), 
        CRITICAL(4, 30, 0.50), FATAL(5, 0, 1.0);
        
        private final int severityLevel;
        private final int baseRecoveryDays;
        private final double permanentChance;
        
        InjurySeverity(int severityLevel, int baseRecoveryDays, double permanentChance) {
            this.severityLevel = severityLevel;
            this.baseRecoveryDays = baseRecoveryDays;
            this.permanentChance = permanentChance;
        }
        
        public int getSeverityLevel() { return severityLevel; }
        public int getBaseRecoveryDays() { return baseRecoveryDays; }
        public double getPermanentChance() { return permanentChance; }
    }
    
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class InjuryEffect {
        private String effectId;
        private EffectType type;
        private int magnitude;
        private boolean isPermanent;
        private String description;
    }
    
    public enum EffectType {
        REDUCED_ACCURACY, REDUCED_MOBILITY, REDUCED_HEALTH, REDUCED_PSYCHIC_RESISTANCE,
        REDUCED_ARMOR, REDUCED_ACTION_POINTS, REDUCED_CRITICAL_CHANCE, 
        REDUCED_DODGE_CHANCE, INCREASED_FATIGUE, REDUCED_MORALE
    }
    
    public enum InjurySource {
        COMBAT_DAMAGE, ENVIRONMENTAL_HAZARD, FRIENDLY_FIRE, ACCIDENT, 
        PSYCHIC_ATTACK, ALIEN_ABILITY, EQUIPMENT_FAILURE, FALL_DAMAGE
    }
    
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class RecoveryStatus {
        private String soldierId;
        private RecoveryState state;
        private LocalDateTime startDate;
        private LocalDateTime expectedEndDate;
        private int daysRemaining;
        private double recoveryProgress;
        private List<Injury> activeInjuries;
        private MedicalTreatment currentTreatment;
    }
    
    public enum RecoveryState {
        ACTIVE_COMBAT, LIGHT_DUTY, MEDICAL_LEAVE, CRITICAL_CARE, 
        PERMANENT_DISABILITY, FULLY_RECOVERED
    }
    
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MedicalTreatment {
        private String treatmentId;
        private String soldierId;
        private TreatmentType type;
        private LocalDateTime startDate;
        private LocalDateTime endDate;
        private int effectiveness;
        private int cost;
        private boolean isActive;
        private List<String> sideEffects;
    }
    
    public enum TreatmentType {
        BASIC_FIRST_AID, SURGERY, PSYCHOLOGICAL_THERAPY, PHYSICAL_THERAPY,
        MEDICATION, REST_AND_RECOVERY, EXPERIMENTAL_TREATMENT, 
        PSYCHIC_HEALING, ALIEN_TECHNOLOGY, EMERGENCY_TREATMENT
    }
    
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MedicalFacility {
        private String facilityId;
        private String name;
        private int capacity;
        private int currentPatients;
        private int effectiveness;
        private int cost;
        private List<TreatmentType> availableTreatments;
    }
    
    // Core injury system methods
    
    public boolean inflictInjury(String soldierId, InjuryType injuryType, 
                                InjurySeverity severity, InjurySource source) {
        if (soldierId == null || injuryType == null || severity == null) {
            return false;
        }
        
        // Create injury
        Injury injury = createInjury(soldierId, injuryType, severity, source);
        
        // Add to soldier's injury list
        soldierInjuries.computeIfAbsent(soldierId, k -> new ArrayList<>()).add(injury);
        
        // Update recovery status
        updateRecoveryStatus(soldierId);
        
        // Apply injury effects to soldier
        applyInjuryEffects(soldierId, injury);
        
        // Check for permanent injury
        checkPermanentInjury(soldierId, injury);
        
        return true;
    }
    
    public boolean healInjury(String soldierId, String injuryId) {
        List<Injury> injuries = soldierInjuries.get(soldierId);
        if (injuries == null) {
            return false;
        }
        
        for (Injury injury : injuries) {
            if (injury.getInjuryId().equals(injuryId) && !injury.isPermanent()) {
                injuries.remove(injury);
                updateRecoveryStatus(soldierId);
                removeInjuryEffects(soldierId, injury);
                return true;
            }
        }
        
        return false;
    }
    
    public boolean applyMedicalTreatment(String soldierId, TreatmentType treatmentType) {
        if (soldierId == null || treatmentType == null) {
            return false;
        }
        
        // Check if soldier has injuries
        List<Injury> injuries = soldierInjuries.get(soldierId);
        if (injuries == null || injuries.isEmpty()) {
            return false;
        }
        
        // Create treatment
        MedicalTreatment treatment = createTreatment(soldierId, treatmentType);
        
        // Apply treatment effects
        applyTreatmentEffects(soldierId, treatment);
        
        // Update recovery status
        updateRecoveryStatus(soldierId);
        
        return true;
    }
    
    public boolean assignMedicalFacility(String soldierId, String facilityId) {
        MedicalFacility facility = findMedicalFacility(facilityId);
        if (facility == null || facility.getCurrentPatients() >= facility.getCapacity()) {
            return false;
        }
        
        assignedFacilities.put(soldierId, facility);
        facility.setCurrentPatients(facility.getCurrentPatients() + 1);
        
        // Apply facility benefits
        applyFacilityBenefits(soldierId, facility);
        
        return true;
    }
    
    public boolean updateRecoveryProgress() {
        LocalDateTime now = LocalDateTime.now();
        boolean anyUpdates = false;
        
        for (Map.Entry<String, RecoveryStatus> entry : recoveryStatuses.entrySet()) {
            String soldierId = entry.getKey();
            RecoveryStatus status = entry.getValue();
            
            if (status.getState() != RecoveryState.ACTIVE_COMBAT && 
                status.getState() != RecoveryState.PERMANENT_DISABILITY) {
                
                // Calculate recovery progress
                double progress = calculateRecoveryProgress(soldierId, now);
                status.setRecoveryProgress(progress);
                
                // Update days remaining
                int daysRemaining = calculateDaysRemaining(soldierId, now);
                status.setDaysRemaining(daysRemaining);
                
                // Check if fully recovered
                if (progress >= 1.0) {
                    completeRecovery(soldierId);
                }
                
                anyUpdates = true;
            }
        }
        
        return anyUpdates;
    }
    
    public boolean canReturnToCombat(String soldierId) {
        RecoveryStatus status = recoveryStatuses.get(soldierId);
        if (status == null) {
            return true; // No injuries, can fight
        }
        
        return status.getState() == RecoveryState.ACTIVE_COMBAT || 
               status.getState() == RecoveryState.LIGHT_DUTY;
    }
    
    public boolean hasPermanentInjuries(String soldierId) {
        return permanentInjuries.getOrDefault(soldierId, false);
    }
    
    public List<Injury> getActiveInjuries(String soldierId) {
        List<Injury> injuries = soldierInjuries.get(soldierId);
        if (injuries == null) {
            return new ArrayList<>();
        }
        
        return injuries.stream()
            .filter(injury -> !injury.isPermanent())
            .collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
    }
    
    public int getRecoveryDaysRemaining(String soldierId) {
        RecoveryStatus status = recoveryStatuses.get(soldierId);
        return status != null ? status.getDaysRemaining() : 0;
    }
    
    public double getRecoveryProgress(String soldierId) {
        RecoveryStatus status = recoveryStatuses.get(soldierId);
        return status != null ? status.getRecoveryProgress() : 1.0;
    }
    
    public boolean hasActiveInjuries(String soldierId) {
        List<Injury> injuries = getActiveInjuries(soldierId);
        return !injuries.isEmpty();
    }
    
    // Helper methods
    
    private Injury createInjury(String soldierId, InjuryType injuryType, 
                               InjurySeverity severity, InjurySource source) {
        String injuryId = UUID.randomUUID().toString();
        LocalDateTime injuryDate = LocalDateTime.now();
        int recoveryDays = calculateRecoveryDays(severity, injuryType);
        LocalDateTime expectedRecoveryDate = injuryDate.plusDays(recoveryDays);
        
        List<InjuryEffect> effects = generateInjuryEffects(injuryType, severity);
        
        return Injury.builder()
            .injuryId(injuryId)
            .soldierId(soldierId)
            .type(injuryType)
            .severity(severity)
            .injuryDate(injuryDate)
            .expectedRecoveryDate(expectedRecoveryDate)
            .recoveryDays(recoveryDays)
            .isPermanent(false)
            .effects(effects)
            .description(generateInjuryDescription(injuryType, severity))
            .source(source)
            .isTreated(false)
            .build();
    }
    
    private int calculateRecoveryDays(InjurySeverity severity, InjuryType injuryType) {
        int baseDays = severity.getBaseRecoveryDays();
        
        // Modify based on injury type
        switch (injuryType) {
            case PSYCHOLOGICAL_TRAUMA:
                return baseDays * 2; // Psychological injuries take longer
            case RADIATION_EXPOSURE:
                return baseDays * 3; // Radiation exposure is very serious
            case INFECTION:
                return baseDays + 7; // Infections extend recovery time
            default:
                return baseDays;
        }
    }
    
    private List<InjuryEffect> generateInjuryEffects(InjuryType injuryType, InjurySeverity severity) {
        List<InjuryEffect> effects = new ArrayList<>();
        
        switch (injuryType) {
            case GUNSHOT_WOUND:
                effects.add(createEffect(EffectType.REDUCED_HEALTH, severity.getSeverityLevel() * 10));
                effects.add(createEffect(EffectType.REDUCED_MOBILITY, severity.getSeverityLevel() * 5));
                break;
            case EXPLOSION_BLAST:
                effects.add(createEffect(EffectType.REDUCED_HEALTH, severity.getSeverityLevel() * 15));
                effects.add(createEffect(EffectType.REDUCED_ACCURACY, severity.getSeverityLevel() * 8));
                break;
            case BURN_INJURY:
                effects.add(createEffect(EffectType.REDUCED_HEALTH, severity.getSeverityLevel() * 12));
                effects.add(createEffect(EffectType.INCREASED_FATIGUE, severity.getSeverityLevel() * 3));
                break;
            case FRACTURE:
                effects.add(createEffect(EffectType.REDUCED_MOBILITY, severity.getSeverityLevel() * 15));
                effects.add(createEffect(EffectType.REDUCED_ACTION_POINTS, severity.getSeverityLevel() * 2));
                break;
            case CONCUSSION:
                effects.add(createEffect(EffectType.REDUCED_ACCURACY, severity.getSeverityLevel() * 10));
                effects.add(createEffect(EffectType.REDUCED_CRITICAL_CHANCE, severity.getSeverityLevel() * 5));
                break;
            case PSYCHOLOGICAL_TRAUMA:
                effects.add(createEffect(EffectType.REDUCED_MORALE, severity.getSeverityLevel() * 20));
                effects.add(createEffect(EffectType.REDUCED_PSYCHIC_RESISTANCE, severity.getSeverityLevel() * 15));
                break;
            case POISONING:
                effects.add(createEffect(EffectType.REDUCED_HEALTH, severity.getSeverityLevel() * 8));
                effects.add(createEffect(EffectType.INCREASED_FATIGUE, severity.getSeverityLevel() * 5));
                break;
            case RADIATION_EXPOSURE:
                effects.add(createEffect(EffectType.REDUCED_HEALTH, severity.getSeverityLevel() * 20));
                effects.add(createEffect(EffectType.REDUCED_PSYCHIC_RESISTANCE, severity.getSeverityLevel() * 10));
                break;
            case ACID_BURN:
                effects.add(createEffect(EffectType.REDUCED_HEALTH, severity.getSeverityLevel() * 18));
                effects.add(createEffect(EffectType.REDUCED_ARMOR, severity.getSeverityLevel() * 10));
                break;
            case ELECTRICAL_SHOCK:
                effects.add(createEffect(EffectType.REDUCED_ACTION_POINTS, severity.getSeverityLevel() * 3));
                effects.add(createEffect(EffectType.REDUCED_ACCURACY, severity.getSeverityLevel() * 8));
                break;
            case FALL_DAMAGE:
                effects.add(createEffect(EffectType.REDUCED_MOBILITY, severity.getSeverityLevel() * 12));
                effects.add(createEffect(EffectType.REDUCED_HEALTH, severity.getSeverityLevel() * 10));
                break;
            case MELEE_WOUND:
                effects.add(createEffect(EffectType.REDUCED_HEALTH, severity.getSeverityLevel() * 8));
                effects.add(createEffect(EffectType.REDUCED_ACTION_POINTS, severity.getSeverityLevel() * 1));
                break;
            case DISLOCATION:
                effects.add(createEffect(EffectType.REDUCED_MOBILITY, severity.getSeverityLevel() * 10));
                effects.add(createEffect(EffectType.REDUCED_ACTION_POINTS, severity.getSeverityLevel() * 2));
                break;
            case INTERNAL_BLEEDING:
                effects.add(createEffect(EffectType.REDUCED_HEALTH, severity.getSeverityLevel() * 25));
                effects.add(createEffect(EffectType.INCREASED_FATIGUE, severity.getSeverityLevel() * 8));
                break;
            case INFECTION:
                effects.add(createEffect(EffectType.REDUCED_HEALTH, severity.getSeverityLevel() * 6));
                effects.add(createEffect(EffectType.INCREASED_FATIGUE, severity.getSeverityLevel() * 4));
                break;
        }
        
        return effects;
    }
    
    private InjuryEffect createEffect(EffectType type, int magnitude) {
        return InjuryEffect.builder()
            .effectId(UUID.randomUUID().toString())
            .type(type)
            .magnitude(magnitude)
            .isPermanent(false)
            .description("Injury effect: " + type.name())
            .build();
    }
    
    private String generateInjuryDescription(InjuryType injuryType, InjurySeverity severity) {
        return severity.name() + " " + injuryType.name() + " injury";
    }
    
    private void updateRecoveryStatus(String soldierId) {
        List<Injury> injuries = soldierInjuries.get(soldierId);
        if (injuries == null || injuries.isEmpty()) {
            recoveryStatuses.remove(soldierId);
            return;
        }
        
        RecoveryStatus status = recoveryStatuses.computeIfAbsent(soldierId, 
            k -> RecoveryStatus.builder()
                .soldierId(soldierId)
                .state(RecoveryState.MEDICAL_LEAVE)
                .startDate(LocalDateTime.now())
                .activeInjuries(new ArrayList<>())
                .build());
        
        // Update active injuries
        status.setActiveInjuries(getActiveInjuries(soldierId));
        
        // Determine recovery state
        RecoveryState newState = determineRecoveryState(soldierId, injuries);
        status.setState(newState);
        
        // Update expected end date
        LocalDateTime latestRecoveryDate = injuries.stream()
            .map(Injury::getExpectedRecoveryDate)
            .max(LocalDateTime::compareTo)
            .orElse(LocalDateTime.now());
        status.setExpectedEndDate(latestRecoveryDate);
    }
    
    private RecoveryState determineRecoveryState(String soldierId, List<Injury> injuries) {
        boolean hasPermanent = injuries.stream().anyMatch(Injury::isPermanent);
        if (hasPermanent) {
            return RecoveryState.PERMANENT_DISABILITY;
        }
        
        boolean hasSevere = injuries.stream()
            .anyMatch(injury -> injury.getSeverity() == InjurySeverity.SEVERE || 
                               injury.getSeverity() == InjurySeverity.CRITICAL);
        if (hasSevere) {
            return RecoveryState.CRITICAL_CARE;
        }
        
        boolean hasModerate = injuries.stream()
            .anyMatch(injury -> injury.getSeverity() == InjurySeverity.MODERATE);
        if (hasModerate) {
            return RecoveryState.MEDICAL_LEAVE;
        }
        
        return RecoveryState.LIGHT_DUTY;
    }
    
    private void applyInjuryEffects(String soldierId, Injury injury) {
        // Apply injury effects to the soldier's stats
        // This would integrate with the Unit class to modify stats
    }
    
    private void removeInjuryEffects(String soldierId, Injury injury) {
        // Remove injury effects from the soldier's stats
        // This would integrate with the Unit class to restore stats
    }
    
    private void checkPermanentInjury(String soldierId, Injury injury) {
        double permanentChance = injury.getSeverity().getPermanentChance();
        if (Math.random() < permanentChance) {
            injury.setPermanent(true);
            permanentInjuries.put(soldierId, true);
        }
    }
    
    private MedicalTreatment createTreatment(String soldierId, TreatmentType treatmentType) {
        String treatmentId = UUID.randomUUID().toString();
        LocalDateTime startDate = LocalDateTime.now();
        LocalDateTime endDate = startDate.plusDays(calculateTreatmentDuration(treatmentType));
        
        return MedicalTreatment.builder()
            .treatmentId(treatmentId)
            .soldierId(soldierId)
            .type(treatmentType)
            .startDate(startDate)
            .endDate(endDate)
            .effectiveness(calculateTreatmentEffectiveness(treatmentType))
            .cost(calculateTreatmentCost(treatmentType))
            .isActive(true)
            .sideEffects(new ArrayList<>())
            .build();
    }
    
    private int calculateTreatmentDuration(TreatmentType treatmentType) {
        switch (treatmentType) {
            case BASIC_FIRST_AID: return 1;
            case SURGERY: return 7;
            case PSYCHOLOGICAL_THERAPY: return 14;
            case PHYSICAL_THERAPY: return 10;
            case MEDICATION: return 5;
            case REST_AND_RECOVERY: return 3;
            case EXPERIMENTAL_TREATMENT: return 21;
            case PSYCHIC_HEALING: return 2;
            case ALIEN_TECHNOLOGY: return 1;
            case EMERGENCY_TREATMENT: return 1;
            default: return 5;
        }
    }
    
    private int calculateTreatmentEffectiveness(TreatmentType treatmentType) {
        switch (treatmentType) {
            case BASIC_FIRST_AID: return 30;
            case SURGERY: return 80;
            case PSYCHOLOGICAL_THERAPY: return 60;
            case PHYSICAL_THERAPY: return 70;
            case MEDICATION: return 50;
            case REST_AND_RECOVERY: return 40;
            case EXPERIMENTAL_TREATMENT: return 90;
            case PSYCHIC_HEALING: return 75;
            case ALIEN_TECHNOLOGY: return 95;
            case EMERGENCY_TREATMENT: return 60;
            default: return 50;
        }
    }
    
    private int calculateTreatmentCost(TreatmentType treatmentType) {
        switch (treatmentType) {
            case BASIC_FIRST_AID: return 10;
            case SURGERY: return 100;
            case PSYCHOLOGICAL_THERAPY: return 50;
            case PHYSICAL_THERAPY: return 75;
            case MEDICATION: return 25;
            case REST_AND_RECOVERY: return 15;
            case EXPERIMENTAL_TREATMENT: return 200;
            case PSYCHIC_HEALING: return 150;
            case ALIEN_TECHNOLOGY: return 300;
            case EMERGENCY_TREATMENT: return 80;
            default: return 50;
        }
    }
    
    private void applyTreatmentEffects(String soldierId, MedicalTreatment treatment) {
        // Apply treatment effects to speed up recovery
        // This would modify recovery progress
    }
    
    private MedicalFacility findMedicalFacility(String facilityId) {
        return medicalFacilities.stream()
            .filter(facility -> facility.getFacilityId().equals(facilityId))
            .findFirst()
            .orElse(null);
    }
    
    private void applyFacilityBenefits(String soldierId, MedicalFacility facility) {
        // Apply facility benefits to recovery
        // This would improve recovery rates
    }
    
    private double calculateRecoveryProgress(String soldierId, LocalDateTime now) {
        RecoveryStatus status = recoveryStatuses.get(soldierId);
        if (status == null || status.getExpectedEndDate() == null) {
            return 1.0;
        }
        
        long totalDays = ChronoUnit.DAYS.between(status.getStartDate(), status.getExpectedEndDate());
        long elapsedDays = ChronoUnit.DAYS.between(status.getStartDate(), now);
        
        if (totalDays <= 0) {
            return 1.0;
        }
        
        return Math.min(1.0, (double) elapsedDays / totalDays);
    }
    
    private int calculateDaysRemaining(String soldierId, LocalDateTime now) {
        RecoveryStatus status = recoveryStatuses.get(soldierId);
        if (status == null || status.getExpectedEndDate() == null) {
            return 0;
        }
        
        return (int) ChronoUnit.DAYS.between(now, status.getExpectedEndDate());
    }
    
    private void completeRecovery(String soldierId) {
        RecoveryStatus status = recoveryStatuses.get(soldierId);
        if (status != null) {
            status.setState(RecoveryState.FULLY_RECOVERED);
            status.setRecoveryProgress(1.0);
            status.setDaysRemaining(0);
        }
        
        // Remove all non-permanent injuries
        List<Injury> injuries = soldierInjuries.get(soldierId);
        if (injuries != null) {
            injuries.removeIf(injury -> !injury.isPermanent());
        }
    }
    
    private void initializeMedicalFacilities() {
        medicalFacilities.add(MedicalFacility.builder()
            .facilityId("BASIC_MEDICAL")
            .name("Basic Medical Bay")
            .capacity(5)
            .currentPatients(0)
            .effectiveness(50)
            .cost(100)
            .availableTreatments(Arrays.asList(TreatmentType.BASIC_FIRST_AID, TreatmentType.MEDICATION))
            .build());
        
        medicalFacilities.add(MedicalFacility.builder()
            .facilityId("ADVANCED_MEDICAL")
            .name("Advanced Medical Center")
            .capacity(10)
            .currentPatients(0)
            .effectiveness(80)
            .cost(500)
            .availableTreatments(Arrays.asList(TreatmentType.SURGERY, TreatmentType.PHYSICAL_THERAPY, 
                                             TreatmentType.PSYCHOLOGICAL_THERAPY))
            .build());
        
        medicalFacilities.add(MedicalFacility.builder()
            .facilityId("EXPERIMENTAL_MEDICAL")
            .name("Experimental Medical Lab")
            .capacity(3)
            .currentPatients(0)
            .effectiveness(95)
            .cost(1000)
            .availableTreatments(Arrays.asList(TreatmentType.EXPERIMENTAL_TREATMENT, 
                                             TreatmentType.ALIEN_TECHNOLOGY, TreatmentType.PSYCHIC_HEALING))
            .build());
    }
    
    // Getters for system state
    public List<Injury> getSoldierInjuries(String soldierId) {
        return soldierInjuries.getOrDefault(soldierId, new ArrayList<>());
    }
    
    public RecoveryStatus getRecoveryStatus(String soldierId) {
        return recoveryStatuses.get(soldierId);
    }
    
    public List<MedicalTreatment> getActiveTreatments(String soldierId) {
        return activeTreatments.values().stream()
            .filter(treatment -> treatment.getSoldierId().equals(soldierId) && treatment.isActive())
            .collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
    }
    
    public int getAvailableMedicalCapacity() {
        return availableMedicalCapacity;
    }
    
    public List<MedicalFacility> getMedicalFacilities() {
        return new ArrayList<>(medicalFacilities);
    }
}
