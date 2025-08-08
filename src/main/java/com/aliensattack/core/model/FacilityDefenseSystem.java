package com.aliensattack.core.model;

import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.util.*;

/**
 * Facility Defense System for XCOM 2 Tactical Combat
 * Provides base defense scenarios with defensive positions and security systems
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FacilityDefenseSystem {
    
    // Facility Types
    public enum FacilityType {
        COMMAND_CENTER,     // Main command center
        RESEARCH_LAB,       // Research laboratory
        ENGINEERING_BAY,    // Engineering facility
        MEDICAL_WING,       // Medical facility
        ARMORY,            // Weapons storage
        PSIONIC_CHAMBER,    // Psionic training facility
        HANGAR,            // Aircraft hangar
        POWER_PLANT,       // Power generation facility
        COMMUNICATIONS,     // Communications center
        STORAGE_FACILITY   // General storage facility
    }
    
    // Defense Positions
    public enum DefensePosition {
        ENTRANCE,          // Main entrance
        ROOFTOP,           // Rooftop position
        BALCONY,           // Balcony position
        WINDOW,            // Window position
        STAIRS,            // Stairwell position
        ELEVATOR,          // Elevator position
        CORRIDOR,          // Corridor position
        ROOM,              // Room position
        UNDERGROUND,       // Underground position
        EXTERIOR           // Exterior position
    }
    
    // Security Systems
    public enum SecuritySystem {
        SURVEILLANCE_CAMERAS,  // Security cameras
        MOTION_SENSORS,        // Motion detection
        ALARM_SYSTEM,          // Alarm system
        LOCKDOWN_SYSTEM,       // Lockdown capability
        TURRET_SYSTEM,         // Automated turrets
        SHIELD_GENERATOR,      // Shield protection
        FORCE_FIELD,           // Force field barrier
        TRAP_SYSTEM,           // Trap system
        DECOY_SYSTEM,          // Decoy system
        REINFORCEMENT_SYSTEM   // Reinforcement system
    }
    
    // Defense Status
    public enum DefenseStatus {
        SECURE,             // Facility is secure
        COMPROMISED,        // Facility is compromised
        UNDER_ATTACK,       // Facility is under attack
        PARTIALLY_DAMAGED,  // Facility is partially damaged
        HEAVILY_DAMAGED,    // Facility is heavily damaged
        DESTROYED,          // Facility is destroyed
        EVACUATED,          // Facility is evacuated
        LOCKED_DOWN,        // Facility is locked down
        REINFORCED,         // Facility is reinforced
        VULNERABLE          // Facility is vulnerable
    }
    
    private String id;
    private String facilityName;
    private FacilityType facilityType;
    private DefenseStatus defenseStatus;
    private List<DefensePosition> defensePositions;
    private List<SecuritySystem> securitySystems;
    private List<Unit> defendingUnits;
    private List<Unit> attackingUnits;
    private int facilityHealth;
    private int maxFacilityHealth;
    private int securityLevel;
    private int defenseRating;
    private boolean isLockedDown;
    private boolean isReinforced;
    private int turnsUnderAttack;
    private Map<DefensePosition, Unit> positionAssignments;
    
    public FacilityDefenseSystem(String facilityName, FacilityType facilityType) {
        this.id = "FACILITY_" + facilityName + "_" + System.currentTimeMillis();
        this.facilityName = facilityName;
        this.facilityType = facilityType;
        this.defenseStatus = DefenseStatus.SECURE;
        this.defensePositions = new ArrayList<>();
        this.securitySystems = new ArrayList<>();
        this.defendingUnits = new ArrayList<>();
        this.attackingUnits = new ArrayList<>();
        this.maxFacilityHealth = calculateMaxHealth();
        this.facilityHealth = maxFacilityHealth;
        this.securityLevel = calculateSecurityLevel();
        this.defenseRating = calculateDefenseRating();
        this.isLockedDown = false;
        this.isReinforced = false;
        this.turnsUnderAttack = 0;
        this.positionAssignments = new HashMap<>();
        
        // Initialize facility-specific defenses
        initializeFacilityDefenses();
    }
    
    // Facility Management Methods
    public void initializeFacilityDefenses() {
        // Add standard defense positions
        defensePositions.add(DefensePosition.ENTRANCE);
        defensePositions.add(DefensePosition.ROOFTOP);
        defensePositions.add(DefensePosition.CORRIDOR);
        
        // Add facility-specific positions
        switch (facilityType) {
            case COMMAND_CENTER:
                defensePositions.add(DefensePosition.ROOM);
                securitySystems.add(SecuritySystem.SURVEILLANCE_CAMERAS);
                securitySystems.add(SecuritySystem.ALARM_SYSTEM);
                break;
            case RESEARCH_LAB:
                defensePositions.add(DefensePosition.ROOM);
                securitySystems.add(SecuritySystem.MOTION_SENSORS);
                securitySystems.add(SecuritySystem.LOCKDOWN_SYSTEM);
                break;
            case ENGINEERING_BAY:
                defensePositions.add(DefensePosition.UNDERGROUND);
                securitySystems.add(SecuritySystem.TURRET_SYSTEM);
                break;
            case MEDICAL_WING:
                defensePositions.add(DefensePosition.ROOM);
                securitySystems.add(SecuritySystem.SHIELD_GENERATOR);
                break;
            case ARMORY:
                defensePositions.add(DefensePosition.UNDERGROUND);
                securitySystems.add(SecuritySystem.FORCE_FIELD);
                securitySystems.add(SecuritySystem.TRAP_SYSTEM);
                break;
            case PSIONIC_CHAMBER:
                defensePositions.add(DefensePosition.ROOM);
                securitySystems.add(SecuritySystem.DECOY_SYSTEM);
                break;
            case HANGAR:
                defensePositions.add(DefensePosition.EXTERIOR);
                securitySystems.add(SecuritySystem.TURRET_SYSTEM);
                break;
            case POWER_PLANT:
                defensePositions.add(DefensePosition.UNDERGROUND);
                securitySystems.add(SecuritySystem.SHIELD_GENERATOR);
                break;
            case COMMUNICATIONS:
                defensePositions.add(DefensePosition.ROOFTOP);
                securitySystems.add(SecuritySystem.SURVEILLANCE_CAMERAS);
                break;
            case STORAGE_FACILITY:
                defensePositions.add(DefensePosition.UNDERGROUND);
                securitySystems.add(SecuritySystem.MOTION_SENSORS);
                break;
        }
    }
    
    private int calculateMaxHealth() {
        switch (facilityType) {
            case COMMAND_CENTER: return 1000;
            case RESEARCH_LAB: return 800;
            case ENGINEERING_BAY: return 900;
            case MEDICAL_WING: return 700;
            case ARMORY: return 1200;
            case PSIONIC_CHAMBER: return 600;
            case HANGAR: return 1100;
            case POWER_PLANT: return 1500;
            case COMMUNICATIONS: return 500;
            case STORAGE_FACILITY: return 800;
            default: return 800;
        }
    }
    
    private int calculateSecurityLevel() {
        int baseLevel = 10;
        
        // Add security systems
        baseLevel += securitySystems.size() * 5;
        
        // Add defending units
        baseLevel += defendingUnits.size() * 3;
        
        // Facility-specific bonuses
        switch (facilityType) {
            case COMMAND_CENTER:
                baseLevel += 20;
                break;
            case ARMORY:
                baseLevel += 15;
                break;
            case POWER_PLANT:
                baseLevel += 10;
                break;
            case RESEARCH_LAB:
                baseLevel += 8;
                break;
            case ENGINEERING_BAY:
                baseLevel += 12;
                break;
            case MEDICAL_WING:
                baseLevel += 5;
                break;
            case PSIONIC_CHAMBER:
                baseLevel += 8;
                break;
            case HANGAR:
                baseLevel += 15;
                break;
            case COMMUNICATIONS:
                baseLevel += 10;
                break;
            case STORAGE_FACILITY:
                baseLevel += 6;
                break;
        }
        
        return baseLevel;
    }
    
    private int calculateDefenseRating() {
        int baseRating = 50;
        
        // Add defense positions
        baseRating += defensePositions.size() * 10;
        
        // Add security systems
        baseRating += securitySystems.size() * 15;
        
        // Add defending units
        baseRating += defendingUnits.size() * 20;
        
        // Facility-specific bonuses
        switch (facilityType) {
            case COMMAND_CENTER:
                baseRating += 30;
                break;
            case RESEARCH_LAB:
                baseRating += 15;
                break;
            case ENGINEERING_BAY:
                baseRating += 20;
                break;
            case MEDICAL_WING:
                baseRating += 10;
                break;
            case ARMORY:
                baseRating += 25;
                break;
            case PSIONIC_CHAMBER:
                baseRating += 15;
                break;
            case HANGAR:
                baseRating += 20;
                break;
            case POWER_PLANT:
                baseRating += 20;
                break;
            case COMMUNICATIONS:
                baseRating += 10;
                break;
            case STORAGE_FACILITY:
                baseRating += 15;
                break;
        }
        
        return baseRating;
    }
    
    // Defense Management Methods
    public void addDefendingUnit(Unit unit) {
        if (!defendingUnits.contains(unit)) {
            defendingUnits.add(unit);
            updateDefenseRating();
            System.out.println(unit.getName() + " assigned to defend " + facilityName);
        }
    }
    
    public void removeDefendingUnit(Unit unit) {
        defendingUnits.remove(unit);
        updateDefenseRating();
        
        // Remove from position assignments
        positionAssignments.entrySet().removeIf(entry -> entry.getValue().equals(unit));
    }
    
    public void assignUnitToPosition(Unit unit, DefensePosition position) {
        if (defendingUnits.contains(unit) && defensePositions.contains(position)) {
            positionAssignments.put(position, unit);
            System.out.println(unit.getName() + " assigned to " + position + " position at " + facilityName);
        }
    }
    
    public void addAttackingUnit(Unit unit) {
        if (!attackingUnits.contains(unit)) {
            attackingUnits.add(unit);
            updateDefenseStatus();
            System.out.println(unit.getName() + " attacking " + facilityName);
        }
    }
    
    public void removeAttackingUnit(Unit unit) {
        attackingUnits.remove(unit);
        updateDefenseStatus();
    }
    
    public void takeDamage(int damage) {
        facilityHealth -= damage;
        
        if (facilityHealth <= 0) {
            facilityHealth = 0;
            defenseStatus = DefenseStatus.DESTROYED;
            System.out.println(facilityName + " has been destroyed!");
        } else if (facilityHealth < maxFacilityHealth * 0.2) {
            defenseStatus = DefenseStatus.HEAVILY_DAMAGED;
        } else if (facilityHealth < maxFacilityHealth * 0.5) {
            defenseStatus = DefenseStatus.PARTIALLY_DAMAGED;
        }
        
        updateDefenseRating();
    }
    
    public void repair(int amount) {
        facilityHealth = Math.min(maxFacilityHealth, facilityHealth + amount);
        updateDefenseStatus();
        updateDefenseRating();
    }
    
    public void activateSecuritySystem(SecuritySystem system) {
        if (securitySystems.contains(system)) {
            System.out.println("Activating " + system + " at " + facilityName);
            
            switch (system) {
                case SURVEILLANCE_CAMERAS:
                    activateSurveillance();
                    break;
                case MOTION_SENSORS:
                    activateMotionSensors();
                    break;
                case ALARM_SYSTEM:
                    activateAlarm();
                    break;
                case LOCKDOWN_SYSTEM:
                    activateLockdown();
                    break;
                case TURRET_SYSTEM:
                    activateTurrets();
                    break;
                case SHIELD_GENERATOR:
                    activateShield();
                    break;
                case FORCE_FIELD:
                    activateForceField();
                    break;
                case TRAP_SYSTEM:
                    activateTraps();
                    break;
                case DECOY_SYSTEM:
                    activateDecoys();
                    break;
                case REINFORCEMENT_SYSTEM:
                    callReinforcements();
                    break;
            }
        }
    }
    
    private void activateSurveillance() {
        System.out.println("Surveillance cameras activated at " + facilityName);
        // Implementation would add surveillance monitoring
    }
    
    private void activateMotionSensors() {
        System.out.println("Motion sensors activated at " + facilityName);
        // Implementation would add motion detection
    }
    
    private void activateAlarm() {
        System.out.println("Alarm system activated at " + facilityName);
        // Implementation would add alarm alerts
    }
    
    private void activateLockdown() {
        isLockedDown = true;
        defenseStatus = DefenseStatus.LOCKED_DOWN;
        System.out.println(facilityName + " is now locked down");
    }
    
    private void activateTurrets() {
        System.out.println("Turrets activated at " + facilityName);
        // Implementation would add turret attacks
    }
    
    private void activateShield() {
        System.out.println("Shield generator activated at " + facilityName);
        // Implementation would add shield protection
    }
    
    private void activateForceField() {
        System.out.println("Force field activated at " + facilityName);
        // Implementation would add force field barrier
    }
    
    private void activateTraps() {
        System.out.println("Trap system activated at " + facilityName);
        // Implementation would add trap effects
    }
    
    private void activateDecoys() {
        System.out.println("Decoy system activated at " + facilityName);
        // Implementation would add decoy effects
    }
    
    private void callReinforcements() {
        System.out.println("Reinforcements called for " + facilityName);
        // Implementation would add reinforcement units
    }
    
    public void updateDefenseStatus() {
        if (attackingUnits.isEmpty()) {
            if (defenseStatus == DefenseStatus.UNDER_ATTACK) {
                defenseStatus = DefenseStatus.SECURE;
                turnsUnderAttack = 0;
            }
        } else {
            if (defenseStatus != DefenseStatus.UNDER_ATTACK) {
                defenseStatus = DefenseStatus.UNDER_ATTACK;
                turnsUnderAttack = 0;
            }
            turnsUnderAttack++;
        }
        
        // Check for compromise
        if (turnsUnderAttack > 5 && !isReinforced) {
            defenseStatus = DefenseStatus.COMPROMISED;
        }
    }
    
    public void updateDefenseRating() {
        defenseRating = calculateDefenseRating();
        securityLevel = calculateSecurityLevel();
    }
    
    public void reinforce() {
        isReinforced = true;
        defenseStatus = DefenseStatus.REINFORCED;
        System.out.println(facilityName + " has been reinforced");
    }
    
    public void evacuate() {
        defenseStatus = DefenseStatus.EVACUATED;
        defendingUnits.clear();
        positionAssignments.clear();
        System.out.println(facilityName + " has been evacuated");
    }
    
    // Defense Information Methods
    public String getDefenseDescription() {
        return String.format("Facility: %s (%s), Status: %s, Health: %d/%d, Defense Rating: %d", 
            facilityName, facilityType.name(), defenseStatus.name(), facilityHealth, maxFacilityHealth, defenseRating);
    }
    
    public boolean isUnderAttack() {
        return defenseStatus == DefenseStatus.UNDER_ATTACK;
    }
    
    public boolean isCompromised() {
        return defenseStatus == DefenseStatus.COMPROMISED;
    }
    
    public boolean isDestroyed() {
        return defenseStatus == DefenseStatus.DESTROYED;
    }
    
    public boolean isLockedDown() {
        return isLockedDown;
    }
    
    public boolean isReinforced() {
        return isReinforced;
    }
    
    public int getHealthPercentage() {
        return (facilityHealth * 100) / maxFacilityHealth;
    }
    
    public List<DefensePosition> getAvailablePositions() {
        return new ArrayList<>(defensePositions);
    }
    
    public List<SecuritySystem> getAvailableSecuritySystems() {
        return new ArrayList<>(securitySystems);
    }
    
    public List<Unit> getDefendingUnits() {
        return new ArrayList<>(defendingUnits);
    }
    
    public List<Unit> getAttackingUnits() {
        return new ArrayList<>(attackingUnits);
    }
    
    public Unit getUnitAtPosition(DefensePosition position) {
        return positionAssignments.get(position);
    }
    
    public DefensePosition getUnitPosition(Unit unit) {
        for (Map.Entry<DefensePosition, Unit> entry : positionAssignments.entrySet()) {
            if (entry.getValue().equals(unit)) {
                return entry.getKey();
            }
        }
        return null;
    }
    
    public int getDefenseRating() {
        return defenseRating;
    }
    
    public int getSecurityLevel() {
        return securityLevel;
    }
    
    public int getTurnsUnderAttack() {
        return turnsUnderAttack;
    }
}
