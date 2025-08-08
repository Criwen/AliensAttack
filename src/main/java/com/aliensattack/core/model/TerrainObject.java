package com.aliensattack.core.model;

import com.aliensattack.core.enums.*;
import lombok.Getter;
import lombok.Setter;

import java.util.*;

/**
 * TerrainObject class representing advanced terrain features in XCOM 2
 */
@Getter
@Setter
public class TerrainObject {
    
    private String terrainId;
    private String terrainName;
    private TerrainType terrainType;
    private Position position;
    private int health;
    private int maxHealth;
    private boolean isDestroyed;
    private boolean isDamaged;
    private boolean isHazardous;
    private boolean isDestructible;
    private boolean isMovable;
    private boolean isInteractable;
    private int movementCost;
    private int damageReduction;
    private int accuracyPenalty;
    private int damageBonus;
    private int defenseBonus;
    private int dodgeBonus;
    private List<StatusEffect> statusEffects;
    private Map<String, Integer> terrainEffects;
    private List<Position> affectedPositions;
    private int radius;
    private boolean isActive;
    private boolean isVisible;
    private boolean isExplosive;
    private boolean isCorrosive;
    private boolean isRadioactive;
    private boolean isElectrified;
    private boolean isFrozen;
    private boolean isBurning;
    private boolean isAcidic;
    private boolean isPoisonous;
    private boolean isToxic;
    private boolean isHazardousEnvironment;
    private boolean isSafeEnvironment;
    private boolean isNeutralEnvironment;
    private boolean isHostileEnvironment;
    private boolean isFriendlyEnvironment;
    private boolean isUnknownEnvironment;
    
    public TerrainObject(String terrainId, String terrainName, TerrainType terrainType, Position position, int health) {
        this.terrainId = terrainId;
        this.terrainName = terrainName;
        this.terrainType = terrainType;
        this.position = position;
        this.health = health;
        this.maxHealth = health;
        this.isDestroyed = false;
        this.isDamaged = false;
        this.isHazardous = false;
        this.isDestructible = true;
        this.isMovable = false;
        this.isInteractable = false;
        this.movementCost = 1;
        this.damageReduction = 0;
        this.accuracyPenalty = 0;
        this.damageBonus = 0;
        this.defenseBonus = 0;
        this.dodgeBonus = 0;
        this.statusEffects = new ArrayList<>();
        this.terrainEffects = new HashMap<>();
        this.affectedPositions = new ArrayList<>();
        this.radius = 1;
        this.isActive = true;
        this.isVisible = true;
        this.isExplosive = false;
        this.isCorrosive = false;
        this.isRadioactive = false;
        this.isElectrified = false;
        this.isFrozen = false;
        this.isBurning = false;
        this.isAcidic = false;
        this.isPoisonous = false;
        this.isToxic = false;
        this.isHazardousEnvironment = false;
        this.isSafeEnvironment = true;
        this.isNeutralEnvironment = false;
        this.isHostileEnvironment = false;
        this.isFriendlyEnvironment = false;
        this.isUnknownEnvironment = false;
        
        initializeTerrainProperties();
    }
    
    /**
     * Initialize terrain properties based on type
     */
    private void initializeTerrainProperties() {
        switch (terrainType) {
            case GRASS:
                movementCost = 1;
                isSafeEnvironment = true;
                break;
            case CONCRETE:
                movementCost = 1;
                damageReduction = 5;
                isSafeEnvironment = true;
                break;
            case METAL:
                movementCost = 1;
                damageReduction = 10;
                isDestructible = false;
                isSafeEnvironment = true;
                break;
            case WATER:
                movementCost = 3;
                accuracyPenalty = 10;
                isHazardous = true;
                isHazardousEnvironment = true;
                break;
            case MUD:
                movementCost = 2;
                accuracyPenalty = 5;
                isHazardous = true;
                isHazardousEnvironment = true;
                break;
            case ICE:
                movementCost = 2;
                dodgeBonus = 10;
                isHazardous = true;
                isHazardousEnvironment = true;
                break;
            case SAND:
                movementCost = 2;
                accuracyPenalty = 5;
                isNeutralEnvironment = true;
                break;
            case ROCK:
                movementCost = 2;
                damageReduction = 15;
                isDestructible = false;
                isSafeEnvironment = true;
                break;
            case WOOD:
                movementCost = 1;
                isDestructible = true;
                isSafeEnvironment = true;
                break;
            case GLASS:
                movementCost = 1;
                isDestructible = true;
                isExplosive = true;
                isHazardousEnvironment = true;
                break;
            case RUBBER:
                movementCost = 1;
                damageReduction = 5;
                isSafeEnvironment = true;
                break;
            case PLASTIC:
                movementCost = 1;
                isDestructible = true;
                isSafeEnvironment = true;
                break;
            case FABRIC:
                movementCost = 1;
                isDestructible = true;
                isSafeEnvironment = true;
                break;
            case PAPER:
                movementCost = 1;
                isDestructible = true;
                isSafeEnvironment = true;
                break;
            case LEATHER:
                movementCost = 1;
                isSafeEnvironment = true;
                break;
            case STONE:
                movementCost = 2;
                damageReduction = 20;
                isDestructible = false;
                isSafeEnvironment = true;
                break;
            case BRICK:
                movementCost = 2;
                damageReduction = 15;
                isDestructible = true;
                isSafeEnvironment = true;
                break;
            case TILE:
                movementCost = 1;
                isSafeEnvironment = true;
                break;
            case CARPET:
                movementCost = 1;
                isSafeEnvironment = true;
                break;
            case VINYL:
                movementCost = 1;
                isSafeEnvironment = true;
                break;
            case CERAMIC:
                movementCost = 1;
                damageReduction = 10;
                isDestructible = true;
                isSafeEnvironment = true;
                break;
            case MARBLE:
                movementCost = 1;
                damageReduction = 25;
                isDestructible = false;
                isSafeEnvironment = true;
                break;
            case GRANITE:
                movementCost = 2;
                damageReduction = 30;
                isDestructible = false;
                isSafeEnvironment = true;
                break;
            case LIMESTONE:
                movementCost = 2;
                damageReduction = 20;
                isDestructible = true;
                isSafeEnvironment = true;
                break;
            case SLATE:
                movementCost = 2;
                damageReduction = 25;
                isDestructible = true;
                isSafeEnvironment = true;
                break;
            case QUARTZ:
                movementCost = 1;
                damageReduction = 15;
                isDestructible = true;
                isSafeEnvironment = true;
                break;
            case OBSIDIAN:
                movementCost = 1;
                damageReduction = 40;
                isDestructible = false;
                isSafeEnvironment = true;
                break;
            case CRYSTAL:
                movementCost = 1;
                damageReduction = 20;
                isDestructible = true;
                isSafeEnvironment = true;
                break;
            case MAGMA:
                movementCost = 5;
                isHazardous = true;
                isBurning = true;
                isHazardousEnvironment = true;
                break;
            case ACID:
                movementCost = 4;
                isHazardous = true;
                isAcidic = true;
                isCorrosive = true;
                isHazardousEnvironment = true;
                break;
            case POISON:
                movementCost = 3;
                isHazardous = true;
                isPoisonous = true;
                isToxic = true;
                isHazardousEnvironment = true;
                break;
            case RADIATION:
                movementCost = 3;
                isHazardous = true;
                isRadioactive = true;
                isHazardousEnvironment = true;
                break;
            case ELECTRIC:
                movementCost = 3;
                isHazardous = true;
                isElectrified = true;
                isHazardousEnvironment = true;
                break;
            case FIRE:
                movementCost = 4;
                isHazardous = true;
                isBurning = true;
                isHazardousEnvironment = true;
                break;
            case ICE_FLOOR:
                movementCost = 2;
                isHazardous = true;
                isFrozen = true;
                isHazardousEnvironment = true;
                break;
            case STEAM:
                movementCost = 2;
                accuracyPenalty = 15;
                isHazardous = true;
                isHazardousEnvironment = true;
                break;
            case SMOKE:
                movementCost = 1;
                accuracyPenalty = 20;
                isHazardous = true;
                isHazardousEnvironment = true;
                break;
            case DUST:
                movementCost = 1;
                accuracyPenalty = 5;
                isNeutralEnvironment = true;
                break;
            case FOG:
                movementCost = 1;
                accuracyPenalty = 10;
                isNeutralEnvironment = true;
                break;
            case MIST:
                movementCost = 1;
                accuracyPenalty = 8;
                isNeutralEnvironment = true;
                break;
            case VAPOR:
                movementCost = 1;
                accuracyPenalty = 12;
                isHazardous = true;
                isHazardousEnvironment = true;
                break;
            case PLASMA:
                movementCost = 4;
                isHazardous = true;
                isHazardousEnvironment = true;
                break;
            case VOID:
                movementCost = 5;
                isHazardous = true;
                isHazardousEnvironment = true;
                break;
            case DIMENSIONAL_RIFT:
                movementCost = 5;
                isHazardous = true;
                isHazardousEnvironment = true;
                break;
            case TELEPORTER:
                movementCost = 1;
                isInteractable = true;
                isSafeEnvironment = true;
                break;
            case HOLOGRAM:
                movementCost = 1;
                isDestructible = true;
                isSafeEnvironment = true;
                break;
            case FORCE_FIELD:
                movementCost = 5;
                damageReduction = 50;
                isDestructible = true;
                isSafeEnvironment = true;
                break;
            case SHIELD_WALL:
                movementCost = 5;
                damageReduction = 40;
                isDestructible = true;
                isSafeEnvironment = true;
                break;
            case BARRIER:
                movementCost = 5;
                damageReduction = 30;
                isDestructible = true;
                isSafeEnvironment = true;
                break;
            case WALL:
                movementCost = 10;
                damageReduction = 100;
                isDestructible = false;
                isSafeEnvironment = true;
                break;
            case DOOR:
                movementCost = 2;
                isInteractable = true;
                isSafeEnvironment = true;
                break;
            case WINDOW:
                movementCost = 3;
                isDestructible = true;
                isSafeEnvironment = true;
                break;
            case ROOF:
                movementCost = 2;
                damageReduction = 20;
                isSafeEnvironment = true;
                break;
            case FLOOR:
                movementCost = 1;
                isSafeEnvironment = true;
                break;
            case CEILING:
                movementCost = 10;
                damageReduction = 100;
                isDestructible = false;
                isSafeEnvironment = true;
                break;
            case STAIRS:
                movementCost = 2;
                isSafeEnvironment = true;
                break;
            case RAMP:
                movementCost = 2;
                isSafeEnvironment = true;
                break;
            case ELEVATOR:
                movementCost = 1;
                isInteractable = true;
                isSafeEnvironment = true;
                break;
            case LADDER:
                movementCost = 3;
                isSafeEnvironment = true;
                break;
            case BRIDGE:
                movementCost = 1;
                isSafeEnvironment = true;
                break;
            case TUNNEL:
                movementCost = 1;
                isSafeEnvironment = true;
                break;
            case CORRIDOR:
                movementCost = 1;
                isSafeEnvironment = true;
                break;
            case ROOM:
                movementCost = 1;
                isSafeEnvironment = true;
                break;
            case OUTDOOR:
                movementCost = 1;
                isSafeEnvironment = true;
                break;
            case INDOOR:
                movementCost = 1;
                isSafeEnvironment = true;
                break;
            case UNDERGROUND:
                movementCost = 2;
                isSafeEnvironment = true;
                break;
            case SPACE:
                movementCost = 5;
                isHazardous = true;
                isHazardousEnvironment = true;
                break;
            case AQUATIC:
                movementCost = 3;
                isHazardous = true;
                isHazardousEnvironment = true;
                break;
            case AERIAL:
                movementCost = 4;
                isHazardous = true;
                isHazardousEnvironment = true;
                break;
            case ORBITAL:
                movementCost = 5;
                isHazardous = true;
                isHazardousEnvironment = true;
                break;
            case DIMENSIONAL:
                movementCost = 5;
                isHazardous = true;
                isHazardousEnvironment = true;
                break;
            case TEMPORAL:
                movementCost = 5;
                isHazardous = true;
                isHazardousEnvironment = true;
                break;
            case QUANTUM:
                movementCost = 5;
                isHazardous = true;
                isHazardousEnvironment = true;
                break;
            case GRAVITATIONAL:
                movementCost = 4;
                isHazardous = true;
                isHazardousEnvironment = true;
                break;
            case MAGNETIC:
                movementCost = 3;
                isHazardous = true;
                isHazardousEnvironment = true;
                break;
            case ELECTROMAGNETIC:
                movementCost = 4;
                isHazardous = true;
                isHazardousEnvironment = true;
                break;
            case NUCLEAR:
                movementCost = 5;
                isHazardous = true;
                isRadioactive = true;
                isHazardousEnvironment = true;
                break;
            case BIOLOGICAL:
                movementCost = 3;
                isHazardous = true;
                isToxic = true;
                isHazardousEnvironment = true;
                break;
            case CHEMICAL:
                movementCost = 4;
                isHazardous = true;
                isCorrosive = true;
                isHazardousEnvironment = true;
                break;
            case TOXIC:
                movementCost = 4;
                isHazardous = true;
                isToxic = true;
                isHazardousEnvironment = true;
                break;
            case HAZARDOUS:
                movementCost = 4;
                isHazardous = true;
                isHazardousEnvironment = true;
                break;
            case SAFE:
                movementCost = 1;
                isSafeEnvironment = true;
                break;
            case NEUTRAL:
                movementCost = 1;
                isNeutralEnvironment = true;
                break;
            case HOSTILE:
                movementCost = 3;
                isHazardous = true;
                isHostileEnvironment = true;
                break;
            case FRIENDLY:
                movementCost = 1;
                isFriendlyEnvironment = true;
                break;
            case UNKNOWN:
                movementCost = 2;
                isUnknownEnvironment = true;
                break;
            case URBAN:
                movementCost = 1;
                isSafeEnvironment = true;
                break;
            case FOREST:
                movementCost = 2;
                isSafeEnvironment = true;
                break;
            case MOUNTAIN:
                movementCost = 3;
                isSafeEnvironment = true;
                break;
            case DESERT:
                movementCost = 2;
                isNeutralEnvironment = true;
                break;
            case INDUSTRIAL:
                movementCost = 1;
                isSafeEnvironment = true;
                break;
            case SWAMP:
                movementCost = 3;
                isHazardous = true;
                isHazardousEnvironment = true;
                break;
            case SNOW:
                movementCost = 2;
                isHazardous = true;
                isFrozen = true;
                isHazardousEnvironment = true;
                break;
            case VOLCANIC:
                movementCost = 4;
                isHazardous = true;
                isBurning = true;
                isHazardousEnvironment = true;
                break;
            case COVER:
                movementCost = 1;
                isSafeEnvironment = true;
                break;
            case EXPLOSIVE_BARREL:
                movementCost = 1;
                isExplosive = true;
                isHazardousEnvironment = true;
                break;
            case FUEL_TANK:
                movementCost = 1;
                isExplosive = true;
                isHazardousEnvironment = true;
                break;
            case VEHICLE:
                movementCost = 1;
                isSafeEnvironment = true;
                break;
            case DEBRIS:
                movementCost = 2;
                isSafeEnvironment = true;
                break;
            case RUBBLE:
                movementCost = 2;
                isSafeEnvironment = true;
                break;
            case GROUND:
                movementCost = 1;
                isSafeEnvironment = true;
                break;
        }
        
        // Initialize terrain effects
        terrainEffects.put("movement_cost", movementCost);
        terrainEffects.put("damage_reduction", damageReduction);
        terrainEffects.put("accuracy_penalty", accuracyPenalty);
        terrainEffects.put("damage_bonus", damageBonus);
        terrainEffects.put("defense_bonus", defenseBonus);
        terrainEffects.put("dodge_bonus", dodgeBonus);
        
        // Calculate affected positions
        calculateAffectedPositions();
    }
    
    /**
     * Calculate positions affected by this terrain
     */
    private void calculateAffectedPositions() {
        affectedPositions.clear();
        
        for (int dx = -radius; dx <= radius; dx++) {
            for (int dy = -radius; dy <= radius; dy++) {
                Position affectedPos = new Position(
                    position.getX() + dx,
                    position.getY() + dy,
                    position.getHeight()
                );
                affectedPositions.add(affectedPos);
            }
        }
    }
    
    /**
     * Take damage from attacks or explosions
     */
    public boolean takeDamage(int damage) {
        if (!isDestructible || isDestroyed) {
            return false;
        }
        
        health -= damage;
        
        if (health <= 0) {
            isDestroyed = true;
            isActive = false;
            return true;
        } else if (health < maxHealth * 0.5) {
            isDamaged = true;
        }
        
        return false;
    }
    
    /**
     * Repair terrain object
     */
    public boolean repair(int repairAmount) {
        if (isDestroyed) {
            return false;
        }
        
        health = Math.min(maxHealth, health + repairAmount);
        
        if (health >= maxHealth * 0.5) {
            isDamaged = false;
        }
        
        return true;
    }
    
    /**
     * Destroy terrain object
     */
    public boolean destroy() {
        if (!isDestructible || isDestroyed) {
            return false;
        }
        
        isDestroyed = true;
        isActive = false;
        return true;
    }
    
    /**
     * Interact with terrain object
     */
    public boolean interact(Unit interactor) {
        if (!isInteractable || !isActive) {
            return false;
        }
        
        switch (terrainType) {
            case TELEPORTER:
                return performTeleport(interactor);
            case DOOR:
                return performDoorInteraction(interactor);
            case ELEVATOR:
                return performElevatorInteraction(interactor);
            default:
                return false;
        }
    }
    
    /**
     * Perform teleport interaction
     */
    private boolean performTeleport(Unit interactor) {
        // Teleport logic would be implemented here
        return true;
    }
    
    /**
     * Perform door interaction
     */
    private boolean performDoorInteraction(Unit interactor) {
        // Door interaction logic would be implemented here
        return true;
    }
    
    /**
     * Perform elevator interaction
     */
    private boolean performElevatorInteraction(Unit interactor) {
        // Elevator interaction logic would be implemented here
        return true;
    }
    
    /**
     * Apply terrain effects to unit
     */
    public void applyEffectsToUnit(Unit unit) {
        if (!isActive || unit == null) {
            return;
        }
        
        // Apply movement cost
        unit.setMovementRange(unit.getMovementRange() - movementCost);
        
        // Apply damage reduction
        if (unit.getArmor() != null) {
            unit.getArmor().setDamageReduction(unit.getArmor().getDamageReduction() + damageReduction);
        }
        
        // Apply accuracy penalty
        if (unit.getWeapon() != null) {
            unit.getWeapon().setAccuracy(unit.getWeapon().getAccuracy() - accuracyPenalty);
        }
        
        // Apply damage bonus
        if (unit.getWeapon() != null) {
            unit.getWeapon().setBaseDamage(unit.getWeapon().getBaseDamage() + damageBonus);
        }
        
        // Apply defense bonus
        // Defense bonus would be applied through armor or other mechanisms
        
        // Apply dodge bonus
        // Dodge bonus would be applied through unit stats
        
        // Apply status effects
        if (isHazardous) {
            applyHazardousEffects(unit);
        }
    }
    
    /**
     * Apply hazardous effects to unit
     */
    private void applyHazardousEffects(Unit unit) {
        if (isBurning) {
            // Apply burning effect
        }
        if (isAcidic) {
            // Apply acid effect
        }
        if (isPoisonous) {
            // Apply poison effect
        }
        if (isRadioactive) {
            // Apply radiation effect
        }
        if (isElectrified) {
            // Apply electric effect
        }
        if (isFrozen) {
            // Apply freeze effect
        }
        if (isCorrosive) {
            // Apply corrosion effect
        }
        if (isToxic) {
            // Apply toxic effect
        }
    }
    
    /**
     * Remove terrain effects from unit
     */
    public void removeEffectsFromUnit(Unit unit) {
        if (unit == null) {
            return;
        }
        
        // Remove movement cost
        unit.setMovementRange(unit.getMovementRange() + movementCost);
        
        // Remove damage reduction
        if (unit.getArmor() != null) {
            unit.getArmor().setDamageReduction(unit.getArmor().getDamageReduction() - damageReduction);
        }
        
        // Remove accuracy penalty
        if (unit.getWeapon() != null) {
            unit.getWeapon().setAccuracy(unit.getWeapon().getAccuracy() + accuracyPenalty);
        }
        
        // Remove damage bonus
        if (unit.getWeapon() != null) {
            unit.getWeapon().setBaseDamage(unit.getWeapon().getBaseDamage() - damageBonus);
        }
        
        // Remove status effects
        if (isHazardous) {
            removeHazardousEffects(unit);
        }
    }
    
    /**
     * Remove hazardous effects from unit
     */
    private void removeHazardousEffects(Unit unit) {
        // Remove hazardous effects logic would be implemented here
    }
    
    /**
     * Check if terrain affects position
     */
    public boolean affectsPosition(Position position) {
        return affectedPositions.contains(position);
    }
    
    /**
     * Get terrain status
     */
    public String getTerrainStatus() {
        return String.format("Terrain: %s, Type: %s, Health: %d/%d, Active: %s", 
                           terrainName, terrainType, health, maxHealth, isActive);
    }
    
    /**
     * Get terrain description
     */
    public String getTerrainDescription() {
        StringBuilder desc = new StringBuilder();
        desc.append(terrainName).append(" (").append(terrainType).append(")");
        
        if (isDestroyed) desc.append(" [Destroyed]");
        if (isDamaged) desc.append(" [Damaged]");
        if (isHazardous) desc.append(" [Hazardous]");
        if (isDestructible) desc.append(" [Destructible]");
        if (isMovable) desc.append(" [Movable]");
        if (isInteractable) desc.append(" [Interactable]");
        if (isExplosive) desc.append(" [Explosive]");
        if (isCorrosive) desc.append(" [Corrosive]");
        if (isRadioactive) desc.append(" [Radioactive]");
        if (isElectrified) desc.append(" [Electrified]");
        if (isFrozen) desc.append(" [Frozen]");
        if (isBurning) desc.append(" [Burning]");
        if (isAcidic) desc.append(" [Acidic]");
        if (isPoisonous) desc.append(" [Poisonous]");
        if (isToxic) desc.append(" [Toxic]");
        
        return desc.toString();
    }
}
