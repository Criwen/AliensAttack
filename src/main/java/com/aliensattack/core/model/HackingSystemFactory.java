package com.aliensattack.core.model;

import com.aliensattack.core.enums.UnitType;
import lombok.Getter;
import lombok.Setter;

import java.util.*;

/**
 * Factory class for creating hacking system components
 * Provides methods to create terminals, abilities, and robot control systems
 */
@Getter
@Setter
public class HackingSystemFactory {
    
    private static final Random random = new Random();
    
    /**
     * Create a primary hacking terminal
     */
    public static HackingTerminal createPrimaryTerminal(String name, Position position) {
        return new HackingTerminal(name, "Primary mission objective terminal", position, 100, 60);
    }
    
    /**
     * Create a secondary hacking terminal
     */
    public static HackingTerminal createSecondaryTerminal(String name, Position position) {
        return new HackingTerminal(name, "Secondary objective terminal", position, 80, 40);
    }
    
    /**
     * Create an optional hacking terminal
     */
    public static HackingTerminal createOptionalTerminal(String name, Position position) {
        return new HackingTerminal(name, "Optional bonus terminal", position, 60, 30);
    }
    
    /**
     * Create a time-sensitive hacking terminal
     */
    public static HackingTerminal createTimeSensitiveTerminal(String name, Position position, int timeLimit) {
        HackingTerminal terminal = new HackingTerminal(name, "Time-sensitive terminal", position, 90, 50);
        terminal.setTimeSensitive(true);
        terminal.setTimeLimit(timeLimit);
        return terminal;
    }
    
    /**
     * Create a high-security hacking terminal
     */
    public static HackingTerminal createHighSecurityTerminal(String name, Position position) {
        HackingTerminal terminal = new HackingTerminal(name, "High-security terminal", position, 120, 80);
        terminal.setSecure(true);
        terminal.setSecurityLevel(3);
        terminal.setGuarded(true);
        terminal.setGuardLevel(2);
        return terminal;
    }
    
    /**
     * Create a damaged hacking terminal
     */
    public static HackingTerminal createDamagedTerminal(String name, Position position) {
        HackingTerminal terminal = new HackingTerminal(name, "Damaged terminal", position, 50, 20);
        terminal.setDamaged(true);
        terminal.setDamageLevel(2);
        return terminal;
    }
    
    /**
     * Create a random hacking terminal
     */
    public static HackingTerminal createRandomTerminal(String name, Position position) {
        int type = random.nextInt(6);
        
        return switch (type) {
            case 0 -> createPrimaryTerminal(name, position);
            case 1 -> createSecondaryTerminal(name, position);
            case 2 -> createOptionalTerminal(name, position);
            case 3 -> createTimeSensitiveTerminal(name, position, 5);
            case 4 -> createHighSecurityTerminal(name, position);
            case 5 -> createDamagedTerminal(name, position);
            default -> createSecondaryTerminal(name, position);
        };
    }
    
    /**
     * Create Combat Protocol ability
     */
    public static SoldierAbility createCombatProtocol() {
        SoldierAbility ability = new SoldierAbility("Combat Protocol", "Deal damage with gremlin", 1, 3);
        ability.setAsTechnicalAbility(15);
        return ability;
    }
    
    /**
     * Create Aid Protocol ability
     */
    public static SoldierAbility createAidProtocol() {
        SoldierAbility ability = new SoldierAbility("Aid Protocol", "Grant bonus action to ally", 1, 4);
        ability.setAsTechnicalAbility(10);
        return ability;
    }
    
    /**
     * Create Medical Protocol ability
     */
    public static SoldierAbility createMedicalProtocol() {
        SoldierAbility ability = new SoldierAbility("Medical Protocol", "Heal ally with gremlin", 1, 5);
        ability.setAsTechnicalAbility(20);
        return ability;
    }
    
    /**
     * Create Scanning Protocol ability
     */
    public static SoldierAbility createScanningProtocol() {
        SoldierAbility ability = new SoldierAbility("Scanning Protocol", "Reveal hidden enemies", 1, 2);
        ability.setAsTechnicalAbility(5);
        return ability;
    }
    
    /**
     * Create Sabotage ability
     */
    public static SoldierAbility createSabotage() {
        SoldierAbility ability = new SoldierAbility("Sabotage", "Disable enemy equipment", 2, 4);
        ability.setAsTechnicalAbility(25);
        return ability;
    }
    
    /**
     * Create Override ability
     */
    public static SoldierAbility createOverride() {
        SoldierAbility ability = new SoldierAbility("Override", "Take control of robotic units", 2, 6);
        ability.setAsTechnicalAbility(30);
        return ability;
    }
    
    /**
     * Create Repair ability
     */
    public static SoldierAbility createRepair() {
        SoldierAbility ability = new SoldierAbility("Repair", "Repair damaged equipment", 1, 3);
        ability.setAsTechnicalAbility(15);
        return ability;
    }
    
    /**
     * Create Hack ability
     */
    public static SoldierAbility createHack() {
        SoldierAbility ability = new SoldierAbility("Hack", "Hack enemy systems", 2, 5);
        ability.setAsHackingAbility(40);
        return ability;
    }
    
    /**
     * Create a random technical ability
     */
    public static SoldierAbility createRandomTechnicalAbility() {
        int type = random.nextInt(8);
        
        return switch (type) {
            case 0 -> createCombatProtocol();
            case 1 -> createAidProtocol();
            case 2 -> createMedicalProtocol();
            case 3 -> createScanningProtocol();
            case 4 -> createSabotage();
            case 5 -> createOverride();
            case 6 -> createRepair();
            case 7 -> createHack();
            default -> createCombatProtocol();
        };
    }
    
    /**
     * Create a robotic unit for control
     */
    public static Unit createRoboticUnit(String name, Position position) {
        Unit robot = new Unit(name, 80, 3, 4, 25, UnitType.ROBOTIC);
        robot.setPosition(position);
        return robot;
    }
    
    /**
     * Create a heavy robotic unit
     */
    public static Unit createHeavyRoboticUnit(String name, Position position) {
        Unit robot = new Unit(name, 120, 2, 5, 35, UnitType.ROBOTIC);
        robot.setPosition(position);
        return robot;
    }
    
    /**
     * Create a light robotic unit
     */
    public static Unit createLightRoboticUnit(String name, Position position) {
        Unit robot = new Unit(name, 60, 4, 3, 20, UnitType.ROBOTIC);
        robot.setPosition(position);
        return robot;
    }
    
    /**
     * Create a random robotic unit
     */
    public static Unit createRandomRoboticUnit(String name, Position position) {
        int type = random.nextInt(3);
        
        return switch (type) {
            case 0 -> createRoboticUnit(name, position);
            case 1 -> createHeavyRoboticUnit(name, position);
            case 2 -> createLightRoboticUnit(name, position);
            default -> createRoboticUnit(name, position);
        };
    }
    
    /**
     * Create a technical specialist unit
     */
    public static Unit createTechnicalSpecialist(String name, Position position) {
        Unit specialist = new Unit(name, 70, 3, 3, 20, UnitType.SOLDIER);
        specialist.setPosition(position);
        return specialist;
    }
    
    /**
     * Create a hacking specialist unit
     */
    public static Unit createHackingSpecialist(String name, Position position) {
        Unit specialist = new Unit(name, 65, 3, 3, 18, UnitType.SOLDIER);
        specialist.setPosition(position);
        return specialist;
    }
    
    /**
     * Create a robot control specialist unit
     */
    public static Unit createRobotControlSpecialist(String name, Position position) {
        Unit specialist = new Unit(name, 75, 3, 3, 22, UnitType.SOLDIER);
        specialist.setPosition(position);
        return specialist;
    }
    
    /**
     * Create a random specialist unit
     */
    public static Unit createRandomSpecialist(String name, Position position) {
        int type = random.nextInt(3);
        
        return switch (type) {
            case 0 -> createTechnicalSpecialist(name, position);
            case 1 -> createHackingSpecialist(name, position);
            case 2 -> createRobotControlSpecialist(name, position);
            default -> createTechnicalSpecialist(name, position);
        };
    }
    
    /**
     * Create a complete hacking mission setup
     */
    public static Map<String, Object> createHackingMission(String missionName, int terminalCount, int robotCount, int specialistCount) {
        Map<String, Object> mission = new HashMap<>();
        
        // Create terminals
        List<HackingTerminal> terminals = new ArrayList<>();
        for (int i = 0; i < terminalCount; i++) {
            Position pos = new Position(random.nextInt(10), random.nextInt(10));
            HackingTerminal terminal = createRandomTerminal("Terminal_" + (i + 1), pos);
            terminals.add(terminal);
        }
        mission.put("terminals", terminals);
        
        // Create robots
        List<Unit> robots = new ArrayList<>();
        for (int i = 0; i < robotCount; i++) {
            Position pos = new Position(random.nextInt(10), random.nextInt(10));
            Unit robot = createRandomRoboticUnit("Robot_" + (i + 1), pos);
            robots.add(robot);
        }
        mission.put("robots", robots);
        
        // Create specialists
        List<Unit> specialists = new ArrayList<>();
        for (int i = 0; i < specialistCount; i++) {
            Position pos = new Position(random.nextInt(10), random.nextInt(10));
            Unit specialist = createRandomSpecialist("Specialist_" + (i + 1), pos);
            specialists.add(specialist);
        }
        mission.put("specialists", specialists);
        
        // Create technical abilities
        List<SoldierAbility> abilities = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            abilities.add(createRandomTechnicalAbility());
        }
        mission.put("abilities", abilities);
        
        return mission;
    }
    
    /**
     * Get all available technical abilities
     */
    public static List<SoldierAbility> getAllTechnicalAbilities() {
        List<SoldierAbility> abilities = new ArrayList<>();
        abilities.add(createCombatProtocol());
        abilities.add(createAidProtocol());
        abilities.add(createMedicalProtocol());
        abilities.add(createScanningProtocol());
        abilities.add(createSabotage());
        abilities.add(createOverride());
        abilities.add(createRepair());
        abilities.add(createHack());
        return abilities;
    }
    
    /**
     * Get all available terminal types
     */
    public static List<String> getAllTerminalTypes() {
        return Arrays.asList(
            "Primary Terminal",
            "Secondary Terminal", 
            "Optional Terminal",
            "Time-Sensitive Terminal",
            "High-Security Terminal",
            "Damaged Terminal"
        );
    }
    
    /**
     * Get all available robot types
     */
    public static List<String> getAllRobotTypes() {
        return Arrays.asList(
            "Standard Robot",
            "Heavy Robot",
            "Light Robot"
        );
    }
    
    /**
     * Get all available specialist types
     */
    public static List<String> getAllSpecialistTypes() {
        return Arrays.asList(
            "Technical Specialist",
            "Hacking Specialist", 
            "Robot Control Specialist"
        );
    }
}
