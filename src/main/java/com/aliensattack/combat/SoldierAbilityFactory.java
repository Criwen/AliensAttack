package com.aliensattack.combat;

import com.aliensattack.core.model.SoldierAbility;
import com.aliensattack.core.enums.SoldierClass;

/**
 * Factory for creating soldier abilities
 */
public class SoldierAbilityFactory {
    
    /**
     * Creates Ranger abilities
     */
    public static SoldierAbility createBladestorm() {
        return new SoldierAbility("Bladestorm", "Free melee attack when enemy moves adjacent", 0, 0);
    }
    
    public static SoldierAbility createPhantom() {
        return new SoldierAbility("Phantom", "Remain concealed after squad is revealed", 0, 0);
    }
    
    /**
     * Creates Sharpshooter abilities
     */
    public static SoldierAbility createSquadsight() {
        return new SoldierAbility("Squadsight", "Can shoot at enemies visible to allies", 0, 0);
    }
    
    public static SoldierAbility createDeadeye() {
        return new SoldierAbility("Deadeye", "Increased accuracy, reduced damage", 2, 1);
    }
    
    /**
     * Creates Grenadier abilities
     */
    public static SoldierAbility createHeavyOrdnance() {
        return new SoldierAbility("Heavy Ordnance", "Carry additional grenade", 0, 0);
    }
    
    public static SoldierAbility createVolatileMix() {
        return new SoldierAbility("Volatile Mix", "Increased grenade damage", 0, 0);
    }
    
    /**
     * Creates Specialist abilities
     */
    public static SoldierAbility createCombatProtocol() {
        return new SoldierAbility("Combat Protocol", "Guaranteed damage to robotic enemies", 3, 1);
    }
    
    public static SoldierAbility createMedicalProtocol() {
        return new SoldierAbility("Medical Protocol", "Heal allies at range", 2, 1);
    }
    
    /**
     * Creates Heavy abilities
     */
    public static SoldierAbility createShredder() {
        return new SoldierAbility("Shredder", "Remove enemy armor", 2, 1);
    }
    
    public static SoldierAbility createHoloTargeting() {
        return new SoldierAbility("Holo-Targeting", "Mark enemies for increased accuracy", 1, 1);
    }
    
    /**
     * Creates Medic abilities
     */
    public static SoldierAbility createFieldMedic() {
        return new SoldierAbility("Field Medic", "Heal multiple allies", 2, 1);
    }
    
    public static SoldierAbility createCombatStims() {
        return new SoldierAbility("Combat Stims", "Temporary combat bonuses", 3, 1);
    }
    
    /**
     * Creates abilities based on soldier class
     */
    public static SoldierAbility createClassAbility(SoldierClass soldierClass, String abilityName) {
        return switch (soldierClass) {
            case RANGER -> switch (abilityName.toLowerCase()) {
                case "bladestorm" -> createBladestorm();
                case "phantom" -> createPhantom();
                default -> null;
            };
            case SHARPSHOOTER -> switch (abilityName.toLowerCase()) {
                case "squadsight" -> createSquadsight();
                case "deadeye" -> createDeadeye();
                default -> null;
            };
            case GRENADIER -> switch (abilityName.toLowerCase()) {
                case "heavyordnance" -> createHeavyOrdnance();
                case "volatilemix" -> createVolatileMix();
                default -> null;
            };
            case SPECIALIST -> switch (abilityName.toLowerCase()) {
                case "combatprotocol" -> createCombatProtocol();
                case "medicalprotocol" -> createMedicalProtocol();
                default -> null;
            };
            case HEAVY -> switch (abilityName.toLowerCase()) {
                case "shredder" -> createShredder();
                case "holotargeting" -> createHoloTargeting();
                default -> null;
            };
            case MEDIC -> switch (abilityName.toLowerCase()) {
                case "fieldmedic" -> createFieldMedic();
                case "combatstims" -> createCombatStims();
                default -> null;
            };
            case TECHNICIAN -> null; // No abilities defined yet
            case ENGINEER -> null;   // No abilities defined yet
            case ROOKIE -> null;     // No abilities defined yet
            case SOLDIER -> null;    // No abilities defined yet
            case PSI_OPERATIVE -> null; // No abilities defined yet
            case TECHNICAL -> null;  // No abilities defined yet
            case SCOUT -> null;      // No abilities defined yet
            case SNIPER -> null;     // No abilities defined yet
            case ASSAULT -> null;    // No abilities defined yet
            case SUPPORT -> null;    // No abilities defined yet
            case COMMANDO -> null;   // No abilities defined yet
        };
    }
} 