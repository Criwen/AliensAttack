package com.aliensattack.core.model;

import java.util.*;

/**
 * Factory class for creating squad tactics, formations, and bonding components
 */
public class SquadTacticsFactory {
    
    private static final Random random = new Random();
    
    /**
     * Create a coordinated assault tactic
     */
    public static SquadTactic createCoordinatedAssaultTactic() {
        return new SquadTactic("Coordinated Assault", 
            com.aliensattack.core.enums.SquadTacticType.COORDINATED_ASSAULT, 4, 3);
    }
    
    /**
     * Create a defensive formation tactic
     */
    public static SquadTactic createDefensiveFormationTactic() {
        return new SquadTactic("Defensive Formation", 
            com.aliensattack.core.enums.SquadTacticType.DEFENSIVE_FORMATION, 5, 2);
    }
    
    /**
     * Create a stealth operation tactic
     */
    public static SquadTactic createStealthOperationTactic() {
        return new SquadTactic("Stealth Operation", 
            com.aliensattack.core.enums.SquadTacticType.STEALTH_OPERATION, 3, 2);
    }
    
    /**
     * Create a psionic synergy tactic
     */
    public static SquadTactic createPsionicSynergyTactic() {
        return new SquadTactic("Psionic Synergy", 
            com.aliensattack.core.enums.SquadTacticType.PSIONIC_SYNERGY, 4, 2);
    }
    
    /**
     * Create a rapid response tactic
     */
    public static SquadTactic createRapidResponseTactic() {
        return new SquadTactic("Rapid Response", 
            com.aliensattack.core.enums.SquadTacticType.RAPID_RESPONSE, 3, 3);
    }
    
    /**
     * Create a technical support tactic
     */
    public static SquadTactic createTechnicalSupportTactic() {
        return new SquadTactic("Technical Support", 
            com.aliensattack.core.enums.SquadTacticType.TECHNICAL_SUPPORT, 4, 2);
    }
    
    /**
     * Create an overwatch network tactic
     */
    public static SquadTactic createOverwatchNetworkTactic() {
        return new SquadTactic("Overwatch Network", 
            com.aliensattack.core.enums.SquadTacticType.OVERWATCH_NETWORK, 5, 4);
    }
    
    /**
     * Create a concealed ambush tactic
     */
    public static SquadTactic createConcealedAmbushTactic() {
        return new SquadTactic("Concealed Ambush", 
            com.aliensattack.core.enums.SquadTacticType.CONCEALED_AMBUSH, 3, 3);
    }
    
    /**
     * Create a chain reaction tactic
     */
    public static SquadTactic createChainReactionTactic() {
        return new SquadTactic("Chain Reaction", 
            com.aliensattack.core.enums.SquadTacticType.CHAIN_REACTION, 4, 3);
    }
    
    /**
     * Create a healing circle tactic
     */
    public static SquadTactic createHealingCircleTactic() {
        return new SquadTactic("Healing Circle", 
            com.aliensattack.core.enums.SquadTacticType.HEALING_CIRCLE, 4, 3);
    }
    
    /**
     * Create a defensive formation
     */
    public static AdvancedSquadTacticsSystem.SquadFormation createDefensiveFormation() {
        Map<String, Integer> positionBonuses = new HashMap<>();
        positionBonuses.put("defense", 15);
        positionBonuses.put("accuracy", 5);
        positionBonuses.put("overwatch", 10);
        
        List<String> requiredPositions = Arrays.asList("front", "back", "flank");
        
        return AdvancedSquadTacticsSystem.SquadFormation.builder()
            .formationId("DEFENSIVE_FORMATION")
            .formationName("Defensive Formation")
            .formationType(AdvancedSquadTacticsSystem.SquadFormation.FormationType.DEFENSIVE)
            .formationEffects(positionBonuses)
            .formationRequirements(requiredPositions)
            .formationCost(2)
            .isActive(true)
            .build();
    }
    
    /**
     * Create an offensive formation
     */
    public static AdvancedSquadTacticsSystem.SquadFormation createOffensiveFormation() {
        Map<String, Integer> positionBonuses = new HashMap<>();
        positionBonuses.put("damage", 15);
        positionBonuses.put("accuracy", 10);
        positionBonuses.put("critical", 5);
        
        List<String> requiredPositions = Arrays.asList("assault", "support", "flank");
        
                return AdvancedSquadTacticsSystem.SquadFormation.builder()
            .formationId("OFFENSIVE_FORMATION")
            .formationName("Offensive Formation")
            .formationType(AdvancedSquadTacticsSystem.SquadFormation.FormationType.OFFENSIVE)
            .formationEffects(positionBonuses)
            .formationRequirements(requiredPositions)
            .formationCost(3)
            .isActive(true)
            .build();
    }

    /**
     * Create a flanking formation
     */
    public static AdvancedSquadTacticsSystem.SquadFormation createFlankingFormation() {
        Map<String, Integer> positionBonuses = new HashMap<>();
        positionBonuses.put("critical", 20);
        positionBonuses.put("damage", 10);
        positionBonuses.put("movement", 2);
        
        List<String> requiredPositions = Arrays.asList("left_flank", "right_flank", "center");
        
        return AdvancedSquadTacticsSystem.SquadFormation.builder()
            .formationId("FLANKING_FORMATION")
            .formationName("Flanking Formation")
            .formationType(AdvancedSquadTacticsSystem.SquadFormation.FormationType.FLANKING)
            .formationEffects(positionBonuses)
            .formationRequirements(requiredPositions)
            .formationCost(3)
            .isActive(true)
            .build();
    }

    /**
     * Create a support formation
     */
    public static AdvancedSquadTacticsSystem.SquadFormation createSupportFormation() {
        Map<String, Integer> positionBonuses = new HashMap<>();
        positionBonuses.put("healing", 20);
        positionBonuses.put("support_range", 3);
        positionBonuses.put("defense", 10);
        
        List<String> requiredPositions = Arrays.asList("medic", "support", "guard");
        
        return AdvancedSquadTacticsSystem.SquadFormation.builder()
            .formationId("SUPPORT_FORMATION")
            .formationName("Support Formation")
            .formationType(AdvancedSquadTacticsSystem.SquadFormation.FormationType.SUPPORT)
            .formationEffects(positionBonuses)
            .formationRequirements(requiredPositions)
            .formationCost(2)
            .isActive(true)
            .build();
    }

    /**
     * Create a coordinated attack maneuver
     */
    public static AdvancedSquadTacticsSystem.SquadManeuver createCoordinatedAttackManeuver() {
        Map<String, Integer> maneuverBonuses = new HashMap<>();
        maneuverBonuses.put("damage", 20);
        maneuverBonuses.put("accuracy", 15);
        maneuverBonuses.put("critical", 10);
        
        List<String> requiredUnits = Arrays.asList("attacker1", "attacker2", "support");
        
        return AdvancedSquadTacticsSystem.SquadManeuver.builder()
            .maneuverId("COORDINATED_ATTACK")
            .maneuverName("Coordinated Attack")
            .maneuverType(AdvancedSquadTacticsSystem.SquadManeuver.ManeuverType.COORDINATED_ATTACK)
            .maneuverEffects(maneuverBonuses)
            .maneuverRequirements(requiredUnits)
            .maneuverCost(3)
            .isActive(true)
            .build();
    }

    /**
     * Create a covering fire maneuver
     */
    public static AdvancedSquadTacticsSystem.SquadManeuver createCoveringFireManeuver() {
        Map<String, Integer> maneuverBonuses = new HashMap<>();
        maneuverBonuses.put("overwatch", 25);
        maneuverBonuses.put("defense", 10);
        maneuverBonuses.put("movement", 1);
        
        List<String> requiredUnits = Arrays.asList("cover", "mover");
        
        return AdvancedSquadTacticsSystem.SquadManeuver.builder()
            .maneuverId("COVERING_FIRE")
            .maneuverName("Covering Fire")
            .maneuverType(AdvancedSquadTacticsSystem.SquadManeuver.ManeuverType.COVERING_FIRE)
            .maneuverEffects(maneuverBonuses)
            .maneuverRequirements(requiredUnits)
            .maneuverCost(2)
            .isActive(true)
            .build();
    }

    /**
     * Create a flanking maneuver
     */
    public static AdvancedSquadTacticsSystem.SquadManeuver createFlankingManeuver() {
        Map<String, Integer> maneuverBonuses = new HashMap<>();
        maneuverBonuses.put("critical", 30);
        maneuverBonuses.put("damage", 15);
        maneuverBonuses.put("accuracy", 10);
        
        List<String> requiredUnits = Arrays.asList("flanker1", "flanker2", "distraction");
        
        return AdvancedSquadTacticsSystem.SquadManeuver.builder()
            .maneuverId("FLANKING_MANEUVER")
            .maneuverName("Flanking Maneuver")
            .maneuverType(AdvancedSquadTacticsSystem.SquadManeuver.ManeuverType.FLANKING_MANEUVER)
            .maneuverEffects(maneuverBonuses)
            .maneuverRequirements(requiredUnits)
            .maneuverCost(3)
            .isActive(true)
            .build();
    }
    
    /**
     * Create a random squad tactic
     */
    public static SquadTactic createRandomSquadTactic() {
        int tacticType = random.nextInt(10);
        
        switch (tacticType) {
            case 0: return createCoordinatedAssaultTactic();
            case 1: return createDefensiveFormationTactic();
            case 2: return createStealthOperationTactic();
            case 3: return createPsionicSynergyTactic();
            case 4: return createRapidResponseTactic();
            case 5: return createTechnicalSupportTactic();
            case 6: return createOverwatchNetworkTactic();
            case 7: return createConcealedAmbushTactic();
            case 8: return createChainReactionTactic();
            case 9: return createHealingCircleTactic();
            default: return createCoordinatedAssaultTactic();
        }
    }
    
    /**
     * Create a random formation
     */
    public static AdvancedSquadTacticsSystem.SquadFormation createRandomFormation() {
        int formationType = random.nextInt(4);
        
        switch (formationType) {
            case 0: return createDefensiveFormation();
            case 1: return createOffensiveFormation();
            case 2: return createFlankingFormation();
            case 3: return createSupportFormation();
            default: return createDefensiveFormation();
        }
    }
    
    /**
     * Create a random maneuver
     */
    public static AdvancedSquadTacticsSystem.SquadManeuver createRandomManeuver() {
        int maneuverType = random.nextInt(3);
        
        switch (maneuverType) {
            case 0: return createCoordinatedAttackManeuver();
            case 1: return createCoveringFireManeuver();
            case 2: return createFlankingManeuver();
            default: return createCoordinatedAttackManeuver();
        }
    }
    
    /**
     * Get all available tactic types
     */
    public static List<String> getAllTacticTypes() {
        return Arrays.asList(
            "Coordinated Assault",
            "Defensive Formation", 
            "Stealth Operation",
            "Psionic Synergy",
            "Rapid Response",
            "Technical Support",
            "Overwatch Network",
            "Concealed Ambush",
            "Chain Reaction",
            "Healing Circle"
        );
    }
    
    /**
     * Get all available formation types
     */
    public static List<String> getAllFormationTypes() {
        return Arrays.asList(
            "Defensive Formation",
            "Offensive Formation",
            "Flanking Formation", 
            "Support Formation"
        );
    }
    
    /**
     * Get all available maneuver types
     */
    public static List<String> getAllManeuverTypes() {
        return Arrays.asList(
            "Coordinated Attack",
            "Covering Fire",
            "Flanking Maneuver"
        );
    }
}
