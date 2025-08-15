package com.aliensattack.core.model;

import com.aliensattack.core.enums.StatusEffect;
import java.util.*;

/**
 * Integration class that connects the InjurySystem with the Unit class and combat system
 * Handles applying injury effects to units and managing medical priorities
 */
public class InjurySystemIntegration {
    
    private final InjurySystem injurySystem;
    
    public InjurySystemIntegration() {
        InjurySystem shared = InjurySystem.getInstance();
        if (shared == null) {
            shared = new InjurySystem();
            shared.initialize();
        }
        this.injurySystem = shared;
    }
    
    /**
     * Apply injury effects to a unit's stats
     */
    public void applyInjuryEffectsToUnit(Unit unit, InjurySystem.Injury injury) {
        if (unit == null || injury == null) {
            return;
        }
        
        for (InjurySystem.InjuryEffect effect : injury.getEffects()) {
            applyEffectToUnit(unit, effect);
        }
        
        // Update medical priority based on injury severity
        updateMedicalPriority(unit, injury);
    }
    
    /**
     * Remove injury effects from a unit's stats
     */
    public void removeInjuryEffectsFromUnit(Unit unit, InjurySystem.Injury injury) {
        if (unit == null || injury == null) {
            return;
        }
        
        for (InjurySystem.InjuryEffect effect : injury.getEffects()) {
            removeEffectFromUnit(unit, effect);
        }
        
        // Recalculate medical priority
        recalculateMedicalPriority(unit);
    }
    
    /**
     * Apply a single injury effect to a unit
     */
    private void applyEffectToUnit(Unit unit, InjurySystem.InjuryEffect effect) {
        switch (effect.getType()) {
            case REDUCED_ACCURACY:
                unit.setCriticalChance(Math.max(0, unit.getCriticalChance() - effect.getMagnitude()));
                break;
            case REDUCED_MOBILITY:
                unit.setMovementRange(Math.max(1, unit.getMovementRange() - effect.getMagnitude()));
                break;
            case REDUCED_HEALTH:
                int healthReduction = (int) (unit.getMaxHealth() * (effect.getMagnitude() / 100.0));
                unit.setMaxHealth(Math.max(1, unit.getMaxHealth() - healthReduction));
                unit.setCurrentHealth(Math.min(unit.getCurrentHealth(), unit.getMaxHealth()));
                break;
            case REDUCED_PSYCHIC_RESISTANCE:
                unit.setPsiResistance(Math.max(0, unit.getPsiResistance() - effect.getMagnitude()));
                break;
            case REDUCED_ARMOR:
                if (unit.getArmor() != null) {
                    // Reduce armor effectiveness by damaging it
                    unit.getArmor().takeDamage(effect.getMagnitude());
                }
                break;
            case REDUCED_ACTION_POINTS:
                unit.setActionPoints((int) Math.max(0.5, unit.getActionPoints() - (effect.getMagnitude() / 10.0)));
                break;
            case REDUCED_CRITICAL_CHANCE:
                unit.setCriticalChance(Math.max(0, unit.getCriticalChance() - effect.getMagnitude()));
                break;
            case REDUCED_DODGE_CHANCE:
                // Add dodge chance to unit if not present
                // This would require adding a dodgeChance field to Unit class
                break;
            case INCREASED_FATIGUE:
                // Add fatigue status effect
                addStatusEffect(unit, StatusEffect.WOUNDED, effect.getMagnitude());
                break;
            case REDUCED_MORALE:
                // Add morale status effect
                addStatusEffect(unit, StatusEffect.PANICKED, effect.getMagnitude());
                break;
        }
    }
    
    /**
     * Remove a single injury effect from a unit
     */
    private void removeEffectFromUnit(Unit unit, InjurySystem.InjuryEffect effect) {
        switch (effect.getType()) {
            case REDUCED_ACCURACY:
                unit.setCriticalChance(unit.getCriticalChance() + effect.getMagnitude());
                break;
            case REDUCED_MOBILITY:
                unit.setMovementRange(unit.getMovementRange() + effect.getMagnitude());
                break;
            case REDUCED_HEALTH:
                int healthRestoration = (int) (unit.getMaxHealth() * (effect.getMagnitude() / 100.0));
                unit.setMaxHealth(unit.getMaxHealth() + healthRestoration);
                break;
            case REDUCED_PSYCHIC_RESISTANCE:
                unit.setPsiResistance(unit.getPsiResistance() + effect.getMagnitude());
                break;
            case REDUCED_ARMOR:
                if (unit.getArmor() != null) {
                    // Restore armor effectiveness by repairing it
                    unit.getArmor().repair(effect.getMagnitude());
                }
                break;
            case REDUCED_ACTION_POINTS:
                unit.setActionPoints((int) (unit.getActionPoints() + (effect.getMagnitude() / 10.0)));
                break;
            case REDUCED_CRITICAL_CHANCE:
                unit.setCriticalChance(unit.getCriticalChance() + effect.getMagnitude());
                break;
            case REDUCED_DODGE_CHANCE:
                // Remove dodge chance reduction
                break;
            case INCREASED_FATIGUE:
                // Remove fatigue status effect
                removeStatusEffect(unit, StatusEffect.WOUNDED);
                break;
            case REDUCED_MORALE:
                // Remove morale status effect
                removeStatusEffect(unit, StatusEffect.PANICKED);
                break;
        }
    }
    
    /**
     * Add a status effect to a unit
     */
    private void addStatusEffect(Unit unit, StatusEffect effect, int magnitude) {
        StatusEffectData effectData = new StatusEffectData(effect, 3, magnitude);
        unit.addStatusEffect(effectData);
    }
    
    /**
     * Remove a status effect from a unit
     */
    private void removeStatusEffect(Unit unit, StatusEffect effect) {
        unit.getStatusEffects().removeIf(effectData -> effectData.getEffect() == effect);
    }
    
    /**
     * Update medical priority based on injury severity
     */
    private void updateMedicalPriority(Unit unit, InjurySystem.Injury injury) {
        int basePriority = unit.getMedicalPriority();
        int severityBonus = injury.getSeverity().getSeverityLevel() * 10;
        
        // Additional priority for critical injuries
        if (injury.getSeverity() == InjurySystem.InjurySeverity.CRITICAL) {
            severityBonus += 20;
        }
        
        // Additional priority for fatal injuries
        if (injury.getSeverity() == InjurySystem.InjurySeverity.FATAL) {
            severityBonus += 50;
        }
        
        unit.setMedicalPriority(basePriority + severityBonus);
    }
    
    /**
     * Recalculate medical priority based on all active injuries
     */
    private void recalculateMedicalPriority(Unit unit) {
        List<InjurySystem.Injury> activeInjuries = injurySystem.getActiveInjuries(unit.getId());
        
        int totalPriority = 0;
        for (InjurySystem.Injury injury : activeInjuries) {
            int severityBonus = injury.getSeverity().getSeverityLevel() * 10;
            
            if (injury.getSeverity() == InjurySystem.InjurySeverity.CRITICAL) {
                severityBonus += 20;
            }
            
            if (injury.getSeverity() == InjurySystem.InjurySeverity.FATAL) {
                severityBonus += 50;
            }
            
            totalPriority += severityBonus;
        }
        
        unit.setMedicalPriority(totalPriority);
    }
    
    /**
     * Inflict injury on a unit during combat
     */
    public boolean inflictCombatInjury(Unit unit, InjurySystem.InjuryType injuryType, 
                                      InjurySystem.InjurySeverity severity, 
                                      InjurySystem.InjurySource source) {
        if (unit == null) {
            return false;
        }
        
        // Inflict injury through the injury system
        boolean success = injurySystem.inflictInjury(unit.getId(), injuryType, severity, source);
        
        if (success) {
            // Get the created injury and apply effects
            List<InjurySystem.Injury> injuries = injurySystem.getSoldierInjuries(unit.getId());
            if (!injuries.isEmpty()) {
                InjurySystem.Injury latestInjury = injuries.get(injuries.size() - 1);
                applyInjuryEffectsToUnit(unit, latestInjury);
            }
        }
        
        return success;
    }
    
    /**
     * Heal an injury on a unit
     */
    public boolean healUnitInjury(Unit unit, String injuryId) {
        if (unit == null) {
            return false;
        }
        
        // Find the injury to get its effects before removal
        List<InjurySystem.Injury> injuries = injurySystem.getSoldierInjuries(unit.getId());
        InjurySystem.Injury injuryToRemove = null;
        
        for (InjurySystem.Injury injury : injuries) {
            if (injury.getInjuryId().equals(injuryId)) {
                injuryToRemove = injury;
                break;
            }
        }
        
        if (injuryToRemove != null) {
            // Remove effects before healing
            removeInjuryEffectsFromUnit(unit, injuryToRemove);
            
            // Heal the injury
            return injurySystem.healInjury(unit.getId(), injuryId);
        }
        
        return false;
    }
    
    /**
     * Apply medical treatment to a unit
     */
    public boolean applyMedicalTreatmentToUnit(Unit unit, InjurySystem.TreatmentType treatmentType) {
        if (unit == null) {
            return false;
        }
        
        boolean success = injurySystem.applyMedicalTreatment(unit.getId(), treatmentType);
        
        if (success) {
            // Apply treatment benefits to unit stats
            applyTreatmentBenefitsToUnit(unit, treatmentType);
        }
        
        return success;
    }
    
    /**
     * Apply treatment benefits to unit stats
     */
    private void applyTreatmentBenefitsToUnit(Unit unit, InjurySystem.TreatmentType treatmentType) {
        switch (treatmentType) {
            case BASIC_FIRST_AID:
                unit.heal(10);
                break;
            case SURGERY:
                unit.heal(30);
                break;
            case PSYCHOLOGICAL_THERAPY:
                // Remove negative morale effects
                removeStatusEffect(unit, StatusEffect.PANICKED);
                break;
            case PHYSICAL_THERAPY:
                // Restore mobility
                unit.setMovementRange(unit.getMovementRange() + 1);
                break;
            case MEDICATION:
                unit.heal(15);
                break;
            case REST_AND_RECOVERY:
                unit.heal(5);
                break;
            case EXPERIMENTAL_TREATMENT:
                unit.heal(50);
                break;
            case PSYCHIC_HEALING:
                unit.heal(25);
                unit.setPsiResistance(unit.getPsiResistance() + 10);
                break;
            case ALIEN_TECHNOLOGY:
                unit.heal(100);
                break;
            case EMERGENCY_TREATMENT:
                unit.heal(20);
                break;
        }
    }
    
    /**
     * Check if a unit can participate in combat based on injuries
     */
    public boolean canUnitParticipateInCombat(Unit unit) {
        if (unit == null) {
            return false;
        }
        
        return injurySystem.canReturnToCombat(unit.getId());
    }
    
    /**
     * Get units that need medical attention, sorted by priority
     */
    public List<Unit> getUnitsNeedingMedicalAttention(List<Unit> allUnits) {
        return allUnits.stream()
            .filter(unit -> unit.isAlive() && injurySystem.hasActiveInjuries(unit.getId()))
            .sorted((u1, u2) -> Integer.compare(u2.getMedicalPriority(), u1.getMedicalPriority()))
            .toList();
    }
    
    /**
     * Update recovery progress for all units
     */
    public boolean updateAllUnitsRecoveryProgress() {
        return injurySystem.updateRecoveryProgress();
    }
    
    /**
     * Get injury system instance
     */
    public InjurySystem getInjurySystem() {
        return injurySystem;
    }
    
    /**
     * Get recovery status for a unit
     */
    public InjurySystem.RecoveryStatus getUnitRecoveryStatus(Unit unit) {
        if (unit == null) {
            return null;
        }
        
        return injurySystem.getRecoveryStatus(unit.getId());
    }
    
    /**
     * Get active injuries for a unit
     */
    public List<InjurySystem.Injury> getUnitActiveInjuries(Unit unit) {
        if (unit == null) {
            return new ArrayList<>();
        }
        
        return injurySystem.getActiveInjuries(unit.getId());
    }
    
    /**
     * Check if unit has permanent injuries
     */
    public boolean hasUnitPermanentInjuries(Unit unit) {
        if (unit == null) {
            return false;
        }
        
        return injurySystem.hasPermanentInjuries(unit.getId());
    }
    
    /**
     * Get recovery days remaining for a unit
     */
    public int getUnitRecoveryDaysRemaining(Unit unit) {
        if (unit == null) {
            return 0;
        }
        
        return injurySystem.getRecoveryDaysRemaining(unit.getId());
    }
    
    /**
     * Get recovery progress for a unit
     */
    public double getUnitRecoveryProgress(Unit unit) {
        if (unit == null) {
            return 1.0;
        }
        
        return injurySystem.getRecoveryProgress(unit.getId());
    }
}
