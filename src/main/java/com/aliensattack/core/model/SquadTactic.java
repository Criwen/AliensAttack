package com.aliensattack.core.model;

import com.aliensattack.core.enums.SquadTacticType;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.ArrayList;

/**
 * Represents squad tactics and cohesion bonuses
 * Enhanced with comprehensive squad coordination mechanics
 */
@Getter
@Setter
public class SquadTactic {
    private String name;
    private SquadTacticType type;
    private int duration;
    private int currentDuration;
    private boolean isActive;
    private int squadSize; // Minimum squad size required
    private List<Unit> squadMembers;
    private int cohesionBonus; // Base cohesion bonus
    private int accuracyBonus; // Accuracy bonus for squad members
    private int damageBonus; // Damage bonus for squad members
    private int defenseBonus; // Defense bonus for squad members
    private int movementBonus; // Movement bonus for squad members
    private int overwatchBonus; // Overwatch bonus for squad members
    private int criticalBonus; // Critical hit bonus for squad members
    private int dodgeBonus; // Dodge bonus for squad members
    private int psiBonus; // Psionic bonus for squad members
    private int hackBonus; // Hacking bonus for squad members
    private boolean providesSquadSight; // Enables squad sight for all members
    private boolean providesConcealment; // Enables concealment for all members
    private boolean providesOverwatchAmbush; // Enables overwatch ambush
    private int chainReactionChance; // Chance for chain reactions
    private int suppressionRadius; // Radius for suppression effects
    private int healingRadius; // Radius for healing effects
    private int supportRange; // Range for support abilities
    
    public SquadTactic(String name, SquadTacticType type, int duration, int squadSize) {
        this.name = name;
        this.type = type;
        this.duration = duration;
        this.currentDuration = duration;
        this.isActive = false;
        this.squadSize = squadSize;
        this.squadMembers = new ArrayList<>();
        this.cohesionBonus = 0;
        this.accuracyBonus = 0;
        this.damageBonus = 0;
        this.defenseBonus = 0;
        this.movementBonus = 0;
        this.overwatchBonus = 0;
        this.criticalBonus = 0;
        this.dodgeBonus = 0;
        this.psiBonus = 0;
        this.hackBonus = 0;
        this.providesSquadSight = false;
        this.providesConcealment = false;
        this.providesOverwatchAmbush = false;
        this.chainReactionChance = 0;
        this.suppressionRadius = 0;
        this.healingRadius = 0;
        this.supportRange = 0;
        
        // Set properties based on tactic type
        setPropertiesByTacticType(type);
    }
    
    private void setPropertiesByTacticType(SquadTacticType type) {
        switch (type) {
            case COORDINATED_ASSAULT:
                cohesionBonus = 20;
                accuracyBonus = 15;
                damageBonus = 10;
                defenseBonus = 5;
                movementBonus = 1;
                overwatchBonus = 10;
                criticalBonus = 5;
                dodgeBonus = 5;
                providesSquadSight = true;
                providesOverwatchAmbush = true;
                suppressionRadius = 3;
                supportRange = 5;
                break;
            case DEFENSIVE_FORMATION:
                cohesionBonus = 15;
                accuracyBonus = 10;
                damageBonus = 5;
                defenseBonus = 20;
                movementBonus = 0;
                overwatchBonus = 15;
                criticalBonus = 0;
                dodgeBonus = 15;
                suppressionRadius = 4;
                supportRange = 6;
                break;
            case STEALTH_OPERATION:
                cohesionBonus = 10;
                accuracyBonus = 20;
                damageBonus = 15;
                defenseBonus = 0;
                movementBonus = 2;
                overwatchBonus = 5;
                criticalBonus = 10;
                dodgeBonus = 10;
                providesConcealment = true;
                providesSquadSight = true;
                supportRange = 4;
                break;
            case PSIONIC_SYNERGY:
                cohesionBonus = 25;
                accuracyBonus = 5;
                damageBonus = 5;
                defenseBonus = 10;
                movementBonus = 0;
                overwatchBonus = 5;
                criticalBonus = 5;
                dodgeBonus = 10;
                psiBonus = 30;
                hackBonus = 20;
                supportRange = 8;
                break;
            case RAPID_RESPONSE:
                cohesionBonus = 15;
                accuracyBonus = 10;
                damageBonus = 10;
                defenseBonus = 5;
                movementBonus = 3;
                overwatchBonus = 20;
                criticalBonus = 10;
                dodgeBonus = 5;
                providesOverwatchAmbush = true;
                suppressionRadius = 5;
                supportRange = 7;
                break;
            case TECHNICAL_SUPPORT:
                cohesionBonus = 10;
                accuracyBonus = 5;
                damageBonus = 5;
                defenseBonus = 15;
                movementBonus = 1;
                overwatchBonus = 10;
                criticalBonus = 0;
                dodgeBonus = 10;
                hackBonus = 40;
                psiBonus = 10;
                supportRange = 6;
                break;
            case OVERWATCH_NETWORK:
                cohesionBonus = 20;
                accuracyBonus = 15;
                damageBonus = 5;
                defenseBonus = 10;
                movementBonus = 0;
                overwatchBonus = 30;
                criticalBonus = 5;
                dodgeBonus = 5;
                providesOverwatchAmbush = true;
                suppressionRadius = 6;
                supportRange = 5;
                break;
            case CONCEALED_AMBUSH:
                cohesionBonus = 15;
                accuracyBonus = 25;
                damageBonus = 20;
                defenseBonus = 0;
                movementBonus = 1;
                overwatchBonus = 10;
                criticalBonus = 15;
                dodgeBonus = 10;
                providesConcealment = true;
                providesSquadSight = true;
                providesOverwatchAmbush = true;
                supportRange = 4;
                break;
            case CHAIN_REACTION:
                cohesionBonus = 10;
                accuracyBonus = 10;
                damageBonus = 15;
                defenseBonus = 5;
                movementBonus = 0;
                overwatchBonus = 5;
                criticalBonus = 10;
                dodgeBonus = 5;
                chainReactionChance = 25;
                suppressionRadius = 3;
                supportRange = 5;
                break;
            case HEALING_CIRCLE:
                cohesionBonus = 20;
                accuracyBonus = 5;
                damageBonus = 0;
                defenseBonus = 15;
                movementBonus = 0;
                overwatchBonus = 5;
                criticalBonus = 0;
                dodgeBonus = 10;
                healingRadius = 4;
                supportRange = 6;
                break;
            default:
                cohesionBonus = 0;
                accuracyBonus = 0;
                damageBonus = 0;
                defenseBonus = 0;
                movementBonus = 0;
                overwatchBonus = 0;
                criticalBonus = 0;
                dodgeBonus = 0;
                psiBonus = 0;
                hackBonus = 0;
                providesSquadSight = false;
                providesConcealment = false;
                providesOverwatchAmbush = false;
                chainReactionChance = 0;
                suppressionRadius = 0;
                healingRadius = 0;
                supportRange = 0;
                break;
        }
    }
    
    /**
     * Activate the squad tactic
     */
    public boolean activate(List<Unit> members) {
        if (members.size() < squadSize) {
            return false;
        }
        
        squadMembers = new ArrayList<>(members);
        isActive = true;
        currentDuration = duration;
        
        // Apply tactic bonuses to squad members
        for (Unit member : squadMembers) {
            applyTacticBonuses(member);
        }
        
        return true;
    }
    
    /**
     * Deactivate the squad tactic
     */
    public void deactivate() {
        if (!isActive) {
            return;
        }
        
        // Remove tactic bonuses from squad members
        for (Unit member : squadMembers) {
            removeTacticBonuses(member);
        }
        
        isActive = false;
        squadMembers.clear();
    }
    
    /**
     * Apply tactic bonuses to a unit
     */
    private void applyTacticBonuses(Unit unit) {
        // Apply stat bonuses
        unit.setInitiative(unit.getInitiative() + cohesionBonus);
        
        // Apply weapon bonuses if unit has a weapon
        if (unit.getWeapon() != null) {
            unit.getWeapon().setAccuracy(unit.getWeapon().getAccuracy() + accuracyBonus);
            unit.getWeapon().setBaseDamage(unit.getWeapon().getBaseDamage() + damageBonus);
        }
        
        // Apply movement bonus
        unit.setMovementRange(unit.getMovementRange() + movementBonus);
        
        // Apply overwatch bonus
        unit.setOverwatchChance(unit.getOverwatchChance() + overwatchBonus);
        
        // Apply critical bonus
        unit.setCriticalChance(unit.getCriticalChance() + criticalBonus);
        
        // Apply psionic bonus
        unit.setPsiStrength(unit.getPsiStrength() + psiBonus);
        
        // Apply concealment if tactic provides it
        if (providesConcealment && unit.canConceal()) {
            unit.conceal();
        }
    }
    
    /**
     * Remove tactic bonuses from a unit
     */
    private void removeTacticBonuses(Unit unit) {
        // Remove stat bonuses
        unit.setInitiative(unit.getInitiative() - cohesionBonus);
        
        // Remove weapon bonuses if unit has a weapon
        if (unit.getWeapon() != null) {
            unit.getWeapon().setAccuracy(unit.getWeapon().getAccuracy() - accuracyBonus);
            unit.getWeapon().setBaseDamage(unit.getWeapon().getBaseDamage() - damageBonus);
        }
        
        // Remove movement bonus
        unit.setMovementRange(unit.getMovementRange() - movementBonus);
        
        // Remove overwatch bonus
        unit.setOverwatchChance(unit.getOverwatchChance() - overwatchBonus);
        
        // Remove critical bonus
        unit.setCriticalChance(unit.getCriticalChance() - criticalBonus);
        
        // Remove psionic bonus
        unit.setPsiStrength(unit.getPsiStrength() - psiBonus);
        
        // Remove concealment if tactic provided it
        if (providesConcealment && unit.isConcealed()) {
            unit.reveal();
        }
    }
    
    /**
     * Process tactic duration
     */
    public void processDuration() {
        if (!isActive) {
            return;
        }
        
        currentDuration--;
        if (currentDuration <= 0) {
            deactivate();
        }
    }
    
    /**
     * Check if tactic is active
     */
    public boolean isActive() {
        return isActive && currentDuration > 0;
    }
    
    /**
     * Get remaining duration
     */
    public int getRemainingDuration() {
        return currentDuration;
    }
    
    /**
     * Check if unit is in squad
     */
    public boolean isSquadMember(Unit unit) {
        return squadMembers.contains(unit);
    }
    
    /**
     * Get squad size
     */
    public int getSquadSize() {
        return squadMembers.size();
    }
    
    /**
     * Check if squad is at full capacity
     */
    public boolean isSquadFull() {
        return squadMembers.size() >= squadSize;
    }
    
    /**
     * Add member to squad
     */
    public boolean addSquadMember(Unit unit) {
        if (isSquadFull() || isSquadMember(unit)) {
            return false;
        }
        
        squadMembers.add(unit);
        if (isActive) {
            applyTacticBonuses(unit);
        }
        return true;
    }
    
    /**
     * Remove member from squad
     */
    public boolean removeSquadMember(Unit unit) {
        if (!isSquadMember(unit)) {
            return false;
        }
        
        squadMembers.remove(unit);
        if (isActive) {
            removeTacticBonuses(unit);
        }
        return true;
    }
    
    /**
     * Get total cohesion bonus
     */
    public int getTotalCohesionBonus() {
        if (!isActive) {
            return 0;
        }
        
        // Bonus increases with squad size
        return cohesionBonus * squadMembers.size();
    }
    
    /**
     * Get squad sight range bonus
     */
    public int getSquadSightRangeBonus() {
        if (providesSquadSight && isActive) {
            return 3; // +3 range for squad sight
        }
        return 0;
    }
    
    /**
     * Check if tactic provides concealment
     */
    public boolean providesConcealment() {
        return providesConcealment && isActive;
    }
    
    /**
     * Check if tactic provides overwatch ambush
     */
    public boolean providesOverwatchAmbush() {
        return providesOverwatchAmbush && isActive;
    }
    
    /**
     * Get chain reaction chance
     */
    public int getChainReactionChance() {
        if (isActive) {
            return chainReactionChance;
        }
        return 0;
    }
    
    /**
     * Get suppression radius
     */
    public int getSuppressionRadius() {
        if (isActive) {
            return suppressionRadius;
        }
        return 0;
    }
    
    /**
     * Get healing radius
     */
    public int getHealingRadius() {
        if (isActive) {
            return healingRadius;
        }
        return 0;
    }
    
    /**
     * Get support range
     */
    public int getSupportRange() {
        if (isActive) {
            return supportRange;
        }
        return 0;
    }
    
    /**
     * Get tactic description
     */
    public String getDescription() {
        StringBuilder desc = new StringBuilder();
        desc.append(name).append(" (").append(type).append(")\n");
        desc.append("Duration: ").append(duration).append(" turns\n");
        desc.append("Squad Size: ").append(squadSize).append(" members\n");
        
        if (cohesionBonus > 0) desc.append("Cohesion: +").append(cohesionBonus).append("\n");
        if (accuracyBonus > 0) desc.append("Accuracy: +").append(accuracyBonus).append("\n");
        if (damageBonus > 0) desc.append("Damage: +").append(damageBonus).append("\n");
        if (defenseBonus > 0) desc.append("Defense: +").append(defenseBonus).append("\n");
        if (movementBonus > 0) desc.append("Movement: +").append(movementBonus).append("\n");
        if (overwatchBonus > 0) desc.append("Overwatch: +").append(overwatchBonus).append("\n");
        if (criticalBonus > 0) desc.append("Critical: +").append(criticalBonus).append("\n");
        if (dodgeBonus > 0) desc.append("Dodge: +").append(dodgeBonus).append("\n");
        if (psiBonus > 0) desc.append("Psionic: +").append(psiBonus).append("\n");
        if (hackBonus > 0) desc.append("Hacking: +").append(hackBonus).append("\n");
        
        if (providesSquadSight) desc.append("Provides Squad Sight\n");
        if (providesConcealment) desc.append("Provides Concealment\n");
        if (providesOverwatchAmbush) desc.append("Provides Overwatch Ambush\n");
        if (chainReactionChance > 0) desc.append("Chain Reaction: ").append(chainReactionChance).append("%\n");
        if (suppressionRadius > 0) desc.append("Suppression Radius: ").append(suppressionRadius).append("\n");
        if (healingRadius > 0) desc.append("Healing Radius: ").append(healingRadius).append("\n");
        if (supportRange > 0) desc.append("Support Range: ").append(supportRange).append("\n");
        
        return desc.toString();
    }
    
    // Additional methods for compatibility
    public boolean canActivate() {
        return !isActive && squadMembers.size() >= squadSize;
    }
    
    public void activate() {
        if (canActivate()) {
            activate(squadMembers);
        }
    }
    
    public int getRange() {
        return supportRange;
    }
    
    public int getTotalBonus(String bonusType) {
        return switch (bonusType.toLowerCase()) {
            case "accuracy" -> accuracyBonus;
            case "damage" -> damageBonus;
            case "defense" -> defenseBonus;
            case "movement" -> movementBonus;
            case "overwatch" -> overwatchBonus;
            case "critical" -> criticalBonus;
            case "dodge" -> dodgeBonus;
            case "psi" -> psiBonus;
            case "hack" -> hackBonus;
            default -> 0;
        };
    }
    
    public void processCooldown() {
        if (isActive) {
            processDuration();
        }
    }
    
    public int getActionPointCost() {
        return 1; // Default action point cost
    }
    
    public void addParticipant(String participant) {
        // This method is called but not implemented in the original design
        // In a real implementation, this would track participants
    }
    
    public void decrementDuration() {
        if (isActive && currentDuration > 0) {
            currentDuration--;
            if (currentDuration <= 0) {
                deactivate();
            }
        }
    }
} 