package com.aliensattack.core.systems;

import lombok.extern.log4j.Log4j2;
import com.aliensattack.core.model.Position;
import java.util.*;

/**
 * Factory for creating squad tactics components
 */
@Log4j2
public class SquadTacticsFactory {
    
    /**
     * Create basic squad formation
     */
    public static SquadTacticsSystem.SquadFormation createBasicFormation() {
        return SquadTacticsSystem.SquadFormation.builder()
            .formationId("basic_formation")
            .description("Basic squad formation")
            .unitPositions(new HashMap<>())
            .coverage(60)
            .mobility(70)
            .build();
    }
    
    /**
     * Create defensive squad formation
     */
    public static SquadTacticsSystem.SquadFormation createDefensiveFormation() {
        Map<String, Position> positions = new HashMap<>();
        positions.put("UNIT_1", new Position(0, 0));
        positions.put("UNIT_2", new Position(1, 0));
        positions.put("UNIT_3", new Position(0, 1));
        
        return SquadTacticsSystem.SquadFormation.builder()
            .formationId("defensive_formation")
            .description("Defensive squad formation")
            .unitPositions(positions)
            .coverage(80)
            .mobility(50)
            .build();
    }
    
    /**
     * Create offensive squad formation
     */
    public static SquadTacticsSystem.SquadFormation createOffensiveFormation() {
        Map<String, Position> positions = new HashMap<>();
        positions.put("UNIT_1", new Position(0, 0));
        positions.put("UNIT_2", new Position(2, 0));
        positions.put("UNIT_3", new Position(1, 1));
        
        return SquadTacticsSystem.SquadFormation.builder()
            .formationId("offensive_formation")
            .description("Offensive squad formation")
            .unitPositions(positions)
            .coverage(50)
            .mobility(90)
            .build();
    }
    
    /**
     * Create flanking squad formation
     */
    public static SquadTacticsSystem.SquadFormation createFlankingFormation() {
        Map<String, Position> positions = new HashMap<>();
        positions.put("UNIT_1", new Position(0, 0));
        positions.put("UNIT_2", new Position(3, 0));
        positions.put("UNIT_3", new Position(1, 2));
        
        return SquadTacticsSystem.SquadFormation.builder()
            .formationId("flanking_formation")
            .description("Flanking squad formation")
            .unitPositions(positions)
            .coverage(70)
            .mobility(80)
            .build();
    }
    
    /**
     * Create support squad formation
     */
    public static SquadTacticsSystem.SquadFormation createSupportFormation() {
        Map<String, Position> positions = new HashMap<>();
        positions.put("UNIT_1", new Position(1, 1));
        positions.put("UNIT_2", new Position(0, 0));
        positions.put("UNIT_3", new Position(2, 0));
        
        return SquadTacticsSystem.SquadFormation.builder()
            .formationId("support_formation")
            .description("Support squad formation")
            .unitPositions(positions)
            .coverage(90)
            .mobility(60)
            .build();
    }
    
    /**
     * Create coordinated attack maneuver
     */
    public static SquadTacticsSystem.SquadManeuver createCoordinatedAttackManeuver() {
        return SquadTacticsSystem.SquadManeuver.builder()
            .maneuverId("coordinated_attack")
            .description("Coordinated attack maneuver")
            .coordinationBonus(25)
            .executionTime(3)
            .build();
    }
    
    /**
     * Create covering fire maneuver
     */
    public static SquadTacticsSystem.SquadManeuver createCoveringFireManeuver() {
        return SquadTacticsSystem.SquadManeuver.builder()
            .maneuverId("covering_fire")
            .description("Covering fire maneuver")
            .coordinationBonus(20)
            .executionTime(2)
            .build();
    }
    
    /**
     * Create flanking maneuver
     */
    public static SquadTacticsSystem.SquadManeuver createFlankingManeuver() {
        return SquadTacticsSystem.SquadManeuver.builder()
            .maneuverId("flanking_maneuver")
            .description("Flanking maneuver")
            .coordinationBonus(30)
            .executionTime(4)
            .build();
    }
    
    /**
     * Create random squad formation
     */
    public static SquadTacticsSystem.SquadFormation createRandomFormation() {
        SquadTacticsSystem.SquadFormation.FormationType[] types = {
            SquadTacticsSystem.SquadFormation.FormationType.BASIC,
            SquadTacticsSystem.SquadFormation.FormationType.DEFENSIVE,
            SquadTacticsSystem.SquadFormation.FormationType.OFFENSIVE,
            SquadTacticsSystem.SquadFormation.FormationType.FLANKING,
            SquadTacticsSystem.SquadFormation.FormationType.SUPPORT
        };
        SquadTacticsSystem.SquadFormation.FormationType type = types[new Random().nextInt(types.length)];
        
        return SquadTacticsSystem.SquadFormation.builder()
            .formationId("random_formation_" + System.currentTimeMillis())
            .description("Random squad formation")
            .formationType(type)
            .unitPositions(new HashMap<>())
            .coverage(60 + new Random().nextInt(30))
            .mobility(60 + new Random().nextInt(30))
            .build();
    }
    
    /**
     * Create random squad maneuver
     */
    public static SquadTacticsSystem.SquadManeuver createRandomManeuver() {
        SquadTacticsSystem.SquadManeuver.ManeuverType[] types = {
            SquadTacticsSystem.SquadManeuver.ManeuverType.COORDINATED_ATTACK,
            SquadTacticsSystem.SquadManeuver.ManeuverType.COVERING_FIRE,
            SquadTacticsSystem.SquadManeuver.ManeuverType.FLANKING_MANEUVER
        };
        SquadTacticsSystem.SquadManeuver.ManeuverType type = types[new Random().nextInt(types.length)];
        
        return SquadTacticsSystem.SquadManeuver.builder()
            .maneuverId("random_maneuver_" + System.currentTimeMillis())
            .description("Random squad maneuver")
            .maneuverType(type)
            .coordinationBonus(15 + new Random().nextInt(20))
            .executionTime(2 + new Random().nextInt(3))
            .build();
    }
}
