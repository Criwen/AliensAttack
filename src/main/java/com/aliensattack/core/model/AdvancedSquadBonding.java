package com.aliensattack.core.model;

import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.util.*;

/**
 * Advanced Squad Bonding System for XCOM 2 Tactical Combat
 * Provides deeper squad relationship mechanics with bonding levels and special abilities
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AdvancedSquadBonding {
    
    // Bonding Levels
    public enum BondLevel {
        NONE,           // No bonding
        ACQUAINTANCE,   // Basic familiarity
        FRIEND,         // Good relationship
        CLOSE_FRIEND,   // Strong friendship
        PARTNER,        // Combat partnership
        SOULMATE        // Ultimate bonding
    }
    
    // Bonding Types
    public enum BondType {
        COMBAT,         // Combat-focused bonding
        SUPPORT,        // Support-focused bonding
        LEADERSHIP,     // Leadership bonding
        SPECIALIST,     // Specialist bonding
        VETERAN,        // Veteran bonding
        MENTOR          // Mentor-student bonding
    }
    
    // Bonding Abilities
    public enum BondAbility {
        COVERING_FIRE,      // Cover fire for bonded partner
        COMBAT_SYNERGY,     // Enhanced combat when together
        SUPPORT_HEALING,    // Healing abilities for partner
        TACTICAL_AWARENESS, // Shared tactical information
        COORDINATED_ATTACK, // Coordinated attack bonuses
        PROTECTIVE_INSTINCT, // Protective behavior
        SHARED_VISION,      // Shared line of sight
        COMBAT_REFLEXES,    // Enhanced reflexes when partner is in danger
        MENTAL_SYNC,        // Psionic synchronization
        SACRIFICIAL_PROTECTION // Take damage for partner
    }
    
    private String id;
    private Unit unit1;
    private Unit unit2;
    private BondLevel bondLevel;
    private BondType bondType;
    private int bondExperience;
    private List<BondAbility> unlockedAbilities;
    private Map<BondAbility, Integer> abilityCooldowns;
    private boolean isActive;
    private int turnsActive;
    private int maxBondExperience;
    
    public AdvancedSquadBonding(Unit unit1, Unit unit2) {
        this.id = "BOND_" + unit1.getId() + "_" + unit2.getId();
        this.unit1 = unit1;
        this.unit2 = unit2;
        this.bondLevel = BondLevel.NONE;
        this.bondType = determineBondType(unit1, unit2);
        this.bondExperience = 0;
        this.unlockedAbilities = new ArrayList<>();
        this.abilityCooldowns = new HashMap<>();
        this.isActive = false;
        this.turnsActive = 0;
        this.maxBondExperience = 1000;
    }
    
    // Bonding Management Methods
    public void addBondExperience(int experience) {
        bondExperience += experience;
        
        // Check for level up
        checkForLevelUp();
        
        // Unlock new abilities
        unlockAbilities();
    }
    
    public void checkForLevelUp() {
        BondLevel newLevel = calculateBondLevel();
        if (newLevel != bondLevel) {
            bondLevel = newLevel;
            System.out.println("Bond level increased to " + bondLevel + " between " + 
                unit1.getName() + " and " + unit2.getName());
        }
    }
    
    public BondLevel calculateBondLevel() {
        if (bondExperience >= 800) {
            return BondLevel.SOULMATE;
        } else if (bondExperience >= 600) {
            return BondLevel.PARTNER;
        } else if (bondExperience >= 400) {
            return BondLevel.CLOSE_FRIEND;
        } else if (bondExperience >= 200) {
            return BondLevel.FRIEND;
        } else if (bondExperience >= 50) {
            return BondLevel.ACQUAINTANCE;
        } else {
            return BondLevel.NONE;
        }
    }
    
    public BondType determineBondType(Unit unit1, Unit unit2) {
        // Determine bond type based on unit classes and abilities
        if (unit1.getSoldierClass() != null && unit2.getSoldierClass() != null) {
            if (unit1.getSoldierClass().name().contains("LEADER") || 
                unit2.getSoldierClass().name().contains("LEADER")) {
                return BondType.LEADERSHIP;
            }
            
            if (unit1.getSoldierClass().name().contains("SUPPORT") || 
                unit2.getSoldierClass().name().contains("SUPPORT")) {
                return BondType.SUPPORT;
            }
            
            if (unit1.getSoldierClass().name().contains("SPECIALIST") || 
                unit2.getSoldierClass().name().contains("SPECIALIST")) {
                return BondType.SPECIALIST;
            }
        }
        
        // Check for veteran status
        if (unit1.getEvolutionLevel() > 3 || unit2.getEvolutionLevel() > 3) {
            return BondType.VETERAN;
        }
        
        return BondType.COMBAT;
    }
    
    public void unlockAbilities() {
        List<BondAbility> newAbilities = new ArrayList<>();
        
        switch (bondLevel) {
            case NONE:
                // No abilities unlocked at NONE level
                break;
            case ACQUAINTANCE:
                newAbilities.add(BondAbility.COVERING_FIRE);
                break;
            case FRIEND:
                newAbilities.add(BondAbility.COMBAT_SYNERGY);
                newAbilities.add(BondAbility.TACTICAL_AWARENESS);
                break;
            case CLOSE_FRIEND:
                newAbilities.add(BondAbility.SUPPORT_HEALING);
                newAbilities.add(BondAbility.PROTECTIVE_INSTINCT);
                break;
            case PARTNER:
                newAbilities.add(BondAbility.COORDINATED_ATTACK);
                newAbilities.add(BondAbility.SHARED_VISION);
                break;
            case SOULMATE:
                newAbilities.add(BondAbility.COMBAT_REFLEXES);
                newAbilities.add(BondAbility.MENTAL_SYNC);
                newAbilities.add(BondAbility.SACRIFICIAL_PROTECTION);
                break;
        }
        
        for (BondAbility ability : newAbilities) {
            if (!unlockedAbilities.contains(ability)) {
                unlockedAbilities.add(ability);
                System.out.println("Unlocked bond ability: " + ability + " for " + 
                    unit1.getName() + " and " + unit2.getName());
            }
        }
    }
    
    // Bonding Activation Methods
    public void activateBond() {
        if (bondLevel != BondLevel.NONE) {
            isActive = true;
            turnsActive = 0;
            System.out.println("Bond activated between " + unit1.getName() + " and " + unit2.getName());
        }
    }
    
    public void deactivateBond() {
        isActive = false;
        turnsActive = 0;
    }
    
    public void updateBond() {
        if (isActive) {
            turnsActive++;
            
            // Add experience for being active together
            addBondExperience(5);
            
            // Apply bond effects
            applyBondEffects();
        }
    }
    
    public void applyBondEffects() {
        if (!isActive) return;
        
        // Apply passive bond effects
        applyPassiveEffects();
        
        // Check for ability activations
        checkAbilityActivations();
    }
    
    private void applyPassiveEffects() {
        // Apply passive bonuses based on bond level
        int accuracyBonus = getBondLevelBonus();
        int defenseBonus = getBondLevelBonus();
        
        // Apply bonuses to both units
        applyUnitBonuses(unit1, accuracyBonus, defenseBonus);
        applyUnitBonuses(unit2, accuracyBonus, defenseBonus);
    }
    
    private void applyUnitBonuses(Unit unit, int accuracyBonus, int defenseBonus) {
        // Apply accuracy bonus
        int currentCriticalChance = unit.getCriticalChance();
        unit.setCriticalChance(currentCriticalChance + accuracyBonus);
        
        // Apply defense bonus through status effect
        // This would be implemented through the status effect system
    }
    
    private int getBondLevelBonus() {
        switch (bondLevel) {
            case ACQUAINTANCE: return 5;
            case FRIEND: return 10;
            case CLOSE_FRIEND: return 15;
            case PARTNER: return 20;
            case SOULMATE: return 25;
            default: return 0;
        }
    }
    
    private void checkAbilityActivations() {
        for (BondAbility ability : unlockedAbilities) {
            if (canActivateAbility(ability)) {
                activateAbility(ability);
            }
        }
    }
    
    private boolean canActivateAbility(BondAbility ability) {
        // Check if ability is on cooldown
        int cooldown = abilityCooldowns.getOrDefault(ability, 0);
        return cooldown <= 0;
    }
    
    private void activateAbility(BondAbility ability) {
        switch (ability) {
            case COVERING_FIRE:
                activateCoveringFire();
                break;
            case COMBAT_SYNERGY:
                activateCombatSynergy();
                break;
            case SUPPORT_HEALING:
                activateSupportHealing();
                break;
            case TACTICAL_AWARENESS:
                activateTacticalAwareness();
                break;
            case COORDINATED_ATTACK:
                activateCoordinatedAttack();
                break;
            case PROTECTIVE_INSTINCT:
                activateProtectiveInstinct();
                break;
            case SHARED_VISION:
                activateSharedVision();
                break;
            case COMBAT_REFLEXES:
                activateCombatReflexes();
                break;
            case MENTAL_SYNC:
                activateMentalSync();
                break;
            case SACRIFICIAL_PROTECTION:
                activateSacrificialProtection();
                break;
        }
        
        // Set cooldown
        setAbilityCooldown(ability);
    }
    
    private void activateCoveringFire() {
        // Unit1 provides covering fire for Unit2
        System.out.println(unit1.getName() + " provides covering fire for " + unit2.getName());
        // Implementation would add overwatch bonus
    }
    
    private void activateCombatSynergy() {
        // Both units get combat bonuses when near each other
        System.out.println("Combat synergy activated between " + unit1.getName() + " and " + unit2.getName());
        // Implementation would add combat bonuses
    }
    
    private void activateSupportHealing() {
        // One unit can heal the other
        if (unit1.getCurrentHealth() < unit1.getMaxHealth() * 0.5) {
            unit1.heal(20);
            System.out.println(unit2.getName() + " heals " + unit1.getName());
        } else if (unit2.getCurrentHealth() < unit2.getMaxHealth() * 0.5) {
            unit2.heal(20);
            System.out.println(unit1.getName() + " heals " + unit2.getName());
        }
    }
    
    private void activateTacticalAwareness() {
        // Share tactical information
        System.out.println("Tactical awareness shared between " + unit1.getName() + " and " + unit2.getName());
        // Implementation would share vision and tactical data
    }
    
    private void activateCoordinatedAttack() {
        // Coordinated attack bonus
        System.out.println("Coordinated attack between " + unit1.getName() + " and " + unit2.getName());
        // Implementation would provide attack bonuses
    }
    
    private void activateProtectiveInstinct() {
        // Protective behavior
        System.out.println("Protective instinct activated between " + unit1.getName() + " and " + unit2.getName());
        // Implementation would provide defensive bonuses
    }
    
    private void activateSharedVision() {
        // Share line of sight
        System.out.println("Shared vision between " + unit1.getName() + " and " + unit2.getName());
        // Implementation would share vision range
    }
    
    private void activateCombatReflexes() {
        // Enhanced reflexes
        System.out.println("Combat reflexes enhanced for " + unit1.getName() + " and " + unit2.getName());
        // Implementation would provide reaction bonuses
    }
    
    private void activateMentalSync() {
        // Psionic synchronization
        System.out.println("Mental sync between " + unit1.getName() + " and " + unit2.getName());
        // Implementation would enhance psionic abilities
    }
    
    private void activateSacrificialProtection() {
        // Take damage for partner
        System.out.println("Sacrificial protection activated between " + unit1.getName() + " and " + unit2.getName());
        // Implementation would redirect damage
    }
    
    private void setAbilityCooldown(BondAbility ability) {
        int cooldown = getAbilityCooldownValue(ability);
        abilityCooldowns.put(ability, cooldown);
    }
    
    private int getAbilityCooldownValue(BondAbility ability) {
        switch (ability) {
            case COVERING_FIRE: return 2;
            case COMBAT_SYNERGY: return 3;
            case SUPPORT_HEALING: return 4;
            case TACTICAL_AWARENESS: return 1;
            case COORDINATED_ATTACK: return 3;
            case PROTECTIVE_INSTINCT: return 2;
            case SHARED_VISION: return 1;
            case COMBAT_REFLEXES: return 2;
            case MENTAL_SYNC: return 4;
            case SACRIFICIAL_PROTECTION: return 5;
            default: return 3;
        }
    }
    
    public void updateCooldowns() {
        for (BondAbility ability : abilityCooldowns.keySet()) {
            int cooldown = abilityCooldowns.get(ability);
            if (cooldown > 0) {
                abilityCooldowns.put(ability, cooldown - 1);
            }
        }
    }
    
    // Bonding Information Methods
    public String getBondDescription() {
        return String.format("Bond Level: %s, Type: %s, Experience: %d/%d", 
            bondLevel.name(), bondType.name(), bondExperience, maxBondExperience);
    }
    
    public boolean isBondActive() {
        return isActive && bondLevel != BondLevel.NONE;
    }
    
    public int getBondExperiencePercentage() {
        return (bondExperience * 100) / maxBondExperience;
    }
    
    public List<BondAbility> getAvailableAbilities() {
        return new ArrayList<>(unlockedAbilities);
    }
    
    public boolean hasAbility(BondAbility ability) {
        return unlockedAbilities.contains(ability);
    }
    
    public int getAbilityCooldown(BondAbility ability) {
        return abilityCooldowns.getOrDefault(ability, 0);
    }
    
    public boolean isUnitInBond(Unit unit) {
        return unit.equals(unit1) || unit.equals(unit2);
    }
    
    public Unit getBondPartner(Unit unit) {
        if (unit.equals(unit1)) {
            return unit2;
        } else if (unit.equals(unit2)) {
            return unit1;
        }
        return null;
    }
}
