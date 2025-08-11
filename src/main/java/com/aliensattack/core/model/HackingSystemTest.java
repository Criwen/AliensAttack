package com.aliensattack.core.model;

import java.util.*;

/**
 * Test class for the hacking system
 * Verifies functionality of HackingSystemManager and HackingSystemFactory
 */
public class HackingSystemTest {
    
    public static void main(String[] args) {
        System.out.println("=== HACKING SYSTEM TEST ===");
        
        // Test HackingSystemManager
        testHackingSystemManager();
        
        // Test HackingSystemFactory
        testHackingSystemFactory();
        
        // Test integration
        testIntegration();
        
        System.out.println("=== ALL TESTS COMPLETED ===");
    }
    
    /**
     * Test HackingSystemManager functionality
     */
    private static void testHackingSystemManager() {
        System.out.println("\n--- Testing HackingSystemManager ---");
        
        HackingSystemManager manager = new HackingSystemManager();
        
        // Test unit registration
        System.out.println("Testing unit registration...");
        boolean registered = manager.registerHackingUnit("UNIT_001", 40);
        System.out.println("Unit registration: " + registered);
        
        // Test ability unlocking
        System.out.println("Testing ability unlocking...");
        boolean unlocked = manager.unlockTechnicalAbility("UNIT_001", "combat_protocol");
        System.out.println("Ability unlock: " + unlocked);
        
        // Test technical ability usage
        System.out.println("Testing technical ability usage...");
        boolean used = manager.useTechnicalAbility("UNIT_001", "combat_protocol", "TARGET_001");
        System.out.println("Ability usage: " + used);
        
        // Test terminal management
        System.out.println("Testing terminal management...");
        HackingTerminal terminal = HackingSystemFactory.createPrimaryTerminal("Test Terminal", new Position(5, 5));
        boolean added = manager.addHackingTerminal("TERMINAL_001", terminal);
        System.out.println("Terminal added: " + added);
        
        // Test terminal hacking
        System.out.println("Testing terminal hacking...");
        boolean hacked = manager.attemptTerminalHack("UNIT_001", "TERMINAL_001", "Disable Security");
        System.out.println("Terminal hacked: " + hacked);
        
        // Test robot control
        System.out.println("Testing robot control...");
        boolean controlled = manager.controlRobot("UNIT_001", "ROBOT_001", 50);
        System.out.println("Robot controlled: " + controlled);
        
        // Test statistics
        System.out.println("Testing statistics...");
        Map<String, Integer> stats = manager.getUnitHackingStats("UNIT_001");
        System.out.println("Unit stats: " + stats);
        
        // Test system status
        System.out.println("Testing system status...");
        String status = manager.getSystemStatus();
        System.out.println("System status:\n" + status);
    }
    
    /**
     * Test HackingSystemFactory functionality
     */
    private static void testHackingSystemFactory() {
        System.out.println("\n--- Testing HackingSystemFactory ---");
        
        // Test terminal creation
        System.out.println("Testing terminal creation...");
        HackingTerminal primaryTerminal = HackingSystemFactory.createPrimaryTerminal("Primary", new Position(1, 1));
        HackingTerminal secondaryTerminal = HackingSystemFactory.createSecondaryTerminal("Secondary", new Position(2, 2));
        HackingTerminal optionalTerminal = HackingSystemFactory.createOptionalTerminal("Optional", new Position(3, 3));
        HackingTerminal timeTerminal = HackingSystemFactory.createTimeSensitiveTerminal("Time", new Position(4, 4), 5);
        HackingTerminal securityTerminal = HackingSystemFactory.createHighSecurityTerminal("Security", new Position(5, 5));
        HackingTerminal damagedTerminal = HackingSystemFactory.createDamagedTerminal("Damaged", new Position(6, 6));
        HackingTerminal randomTerminal = HackingSystemFactory.createRandomTerminal("Random", new Position(7, 7));
        
        System.out.println("Primary terminal: " + primaryTerminal.getName() + " - " + primaryTerminal.getTerminalStatus());
        System.out.println("Secondary terminal: " + secondaryTerminal.getName() + " - " + secondaryTerminal.getTerminalStatus());
        System.out.println("Optional terminal: " + optionalTerminal.getName() + " - " + optionalTerminal.getTerminalStatus());
        System.out.println("Time terminal: " + timeTerminal.getName() + " - " + timeTerminal.getTerminalStatus());
        System.out.println("Security terminal: " + securityTerminal.getName() + " - " + securityTerminal.getTerminalStatus());
        System.out.println("Damaged terminal: " + damagedTerminal.getName() + " - " + damagedTerminal.getTerminalStatus());
        System.out.println("Random terminal: " + randomTerminal.getName() + " - " + randomTerminal.getTerminalStatus());
        
        // Test ability creation
        System.out.println("\nTesting ability creation...");
        SoldierAbility combatProtocol = HackingSystemFactory.createCombatProtocol();
        SoldierAbility aidProtocol = HackingSystemFactory.createAidProtocol();
        SoldierAbility medicalProtocol = HackingSystemFactory.createMedicalProtocol();
        SoldierAbility scanningProtocol = HackingSystemFactory.createScanningProtocol();
        SoldierAbility sabotage = HackingSystemFactory.createSabotage();
        SoldierAbility override = HackingSystemFactory.createOverride();
        SoldierAbility repair = HackingSystemFactory.createRepair();
        SoldierAbility hack = HackingSystemFactory.createHack();
        
        System.out.println("Combat Protocol: " + combatProtocol.getName() + " - " + combatProtocol.getDescription());
        System.out.println("Aid Protocol: " + aidProtocol.getName() + " - " + aidProtocol.getDescription());
        System.out.println("Medical Protocol: " + medicalProtocol.getName() + " - " + medicalProtocol.getDescription());
        System.out.println("Scanning Protocol: " + scanningProtocol.getName() + " - " + scanningProtocol.getDescription());
        System.out.println("Sabotage: " + sabotage.getName() + " - " + sabotage.getDescription());
        System.out.println("Override: " + override.getName() + " - " + override.getDescription());
        System.out.println("Repair: " + repair.getName() + " - " + repair.getDescription());
        System.out.println("Hack: " + hack.getName() + " - " + hack.getDescription());
        
        // Test robot creation
        System.out.println("\nTesting robot creation...");
        Unit standardRobot = HackingSystemFactory.createRoboticUnit("Standard Robot", new Position(8, 8));
        Unit heavyRobot = HackingSystemFactory.createHeavyRoboticUnit("Heavy Robot", new Position(9, 9));
        Unit lightRobot = HackingSystemFactory.createLightRoboticUnit("Light Robot", new Position(10, 10));
        Unit randomRobot = HackingSystemFactory.createRandomRoboticUnit("Random Robot", new Position(11, 11));
        
        System.out.println("Standard Robot: " + standardRobot.getName() + " - Health: " + standardRobot.getMaxHealth());
        System.out.println("Heavy Robot: " + heavyRobot.getName() + " - Health: " + heavyRobot.getMaxHealth());
        System.out.println("Light Robot: " + lightRobot.getName() + " - Health: " + lightRobot.getMaxHealth());
        System.out.println("Random Robot: " + randomRobot.getName() + " - Health: " + randomRobot.getMaxHealth());
        
        // Test specialist creation
        System.out.println("\nTesting specialist creation...");
        Unit technicalSpecialist = HackingSystemFactory.createTechnicalSpecialist("Technical", new Position(12, 12));
        Unit hackingSpecialist = HackingSystemFactory.createHackingSpecialist("Hacking", new Position(13, 13));
        Unit robotControlSpecialist = HackingSystemFactory.createRobotControlSpecialist("Robot Control", new Position(14, 14));
        Unit randomSpecialist = HackingSystemFactory.createRandomSpecialist("Random Specialist", new Position(15, 15));
        
        System.out.println("Technical Specialist: " + technicalSpecialist.getName() + " - Health: " + technicalSpecialist.getMaxHealth());
        System.out.println("Hacking Specialist: " + hackingSpecialist.getName() + " - Health: " + hackingSpecialist.getMaxHealth());
        System.out.println("Robot Control Specialist: " + robotControlSpecialist.getName() + " - Health: " + robotControlSpecialist.getMaxHealth());
        System.out.println("Random Specialist: " + randomSpecialist.getName() + " - Health: " + randomSpecialist.getMaxHealth());
        
        // Test mission creation
        System.out.println("\nTesting mission creation...");
        Map<String, Object> mission = HackingSystemFactory.createHackingMission("Test Mission", 3, 2, 2);
        
        @SuppressWarnings("unchecked")
        List<HackingTerminal> terminals = (List<HackingTerminal>) mission.get("terminals");
        @SuppressWarnings("unchecked")
        List<Unit> robots = (List<Unit>) mission.get("robots");
        @SuppressWarnings("unchecked")
        List<Unit> specialists = (List<Unit>) mission.get("specialists");
        @SuppressWarnings("unchecked")
        List<SoldierAbility> abilities = (List<SoldierAbility>) mission.get("abilities");
        
        System.out.println("Mission created with:");
        System.out.println("- Terminals: " + terminals.size());
        System.out.println("- Robots: " + robots.size());
        System.out.println("- Specialists: " + specialists.size());
        System.out.println("- Abilities: " + abilities.size());
        
        // Test available types
        System.out.println("\nTesting available types...");
        List<String> terminalTypes = HackingSystemFactory.getAllTerminalTypes();
        List<String> robotTypes = HackingSystemFactory.getAllRobotTypes();
        List<String> specialistTypes = HackingSystemFactory.getAllSpecialistTypes();
        List<SoldierAbility> allAbilities = HackingSystemFactory.getAllTechnicalAbilities();
        
        System.out.println("Terminal types: " + terminalTypes);
        System.out.println("Robot types: " + robotTypes);
        System.out.println("Specialist types: " + specialistTypes);
        System.out.println("Technical abilities: " + allAbilities.size() + " total");
    }
    
    /**
     * Test integration between components
     */
    private static void testIntegration() {
        System.out.println("\n--- Testing Integration ---");
        
        HackingSystemManager manager = new HackingSystemManager();
        
        // Create a complete hacking scenario
        System.out.println("Creating hacking scenario...");
        
        // Register specialists
        manager.registerHackingUnit("SPECIALIST_001", 50);
        manager.registerHackingUnit("SPECIALIST_002", 40);
        manager.registerHackingUnit("SPECIALIST_003", 60);
        
        // Unlock abilities
        manager.unlockTechnicalAbility("SPECIALIST_001", "combat_protocol");
        manager.unlockTechnicalAbility("SPECIALIST_001", "aid_protocol");
        manager.unlockTechnicalAbility("SPECIALIST_002", "medical_protocol");
        manager.unlockTechnicalAbility("SPECIALIST_002", "scanning_protocol");
        manager.unlockTechnicalAbility("SPECIALIST_003", "override");
        manager.unlockTechnicalAbility("SPECIALIST_003", "hack");
        
        // Create terminals
        HackingTerminal primaryTerminal = HackingSystemFactory.createPrimaryTerminal("Primary Objective", new Position(5, 5));
        HackingTerminal secondaryTerminal = HackingSystemFactory.createSecondaryTerminal("Secondary Objective", new Position(8, 8));
        HackingTerminal optionalTerminal = HackingSystemFactory.createOptionalTerminal("Bonus Objective", new Position(3, 3));
        
        manager.addHackingTerminal("PRIMARY_001", primaryTerminal);
        manager.addHackingTerminal("SECONDARY_001", secondaryTerminal);
        manager.addHackingTerminal("OPTIONAL_001", optionalTerminal);
        
        // Create robots
        Unit robot1 = HackingSystemFactory.createRoboticUnit("Security Bot 1", new Position(6, 6));
        Unit robot2 = HackingSystemFactory.createHeavyRoboticUnit("Heavy Bot 1", new Position(7, 7));
        
        // Use robots in control attempts
        System.out.println("Robot 1: " + robot1.getName() + " at " + robot1.getPosition());
        System.out.println("Robot 2: " + robot2.getName() + " at " + robot2.getPosition());
        
        // Simulate hacking attempts
        System.out.println("Simulating hacking attempts...");
        
        // Attempt to hack primary terminal
        boolean primaryHacked = manager.attemptTerminalHack("SPECIALIST_001", "PRIMARY_001", "Disable Security");
        System.out.println("Primary terminal hacked: " + primaryHacked);
        
        // Attempt to hack secondary terminal
        boolean secondaryHacked = manager.attemptTerminalHack("SPECIALIST_002", "SECONDARY_001", "Extract Data");
        System.out.println("Secondary terminal hacked: " + secondaryHacked);
        
        // Attempt to hack optional terminal
        boolean optionalHacked = manager.attemptTerminalHack("SPECIALIST_003", "OPTIONAL_001", "Activate Systems");
        System.out.println("Optional terminal hacked: " + optionalHacked);
        
        // Attempt to control robots
        boolean robot1Controlled = manager.controlRobot("SPECIALIST_003", "ROBOT_001", 60);
        System.out.println("Robot 1 controlled: " + robot1Controlled);
        
        boolean robot2Controlled = manager.controlRobot("SPECIALIST_003", "ROBOT_002", 70);
        System.out.println("Robot 2 controlled: " + robot2Controlled);
        
        // Use technical abilities
        System.out.println("Using technical abilities...");
        
        manager.useTechnicalAbility("SPECIALIST_001", "combat_protocol", "ENEMY_001");
        manager.useTechnicalAbility("SPECIALIST_001", "aid_protocol", "ALLY_001");
        manager.useTechnicalAbility("SPECIALIST_002", "medical_protocol", "ALLY_002");
        manager.useTechnicalAbility("SPECIALIST_002", "scanning_protocol", "AREA_001");
        
        // Process systems
        System.out.println("Processing systems...");
        manager.processRobotControl();
        manager.processTerminals();
        
        // Get final statistics
        System.out.println("Final statistics:");
        Map<String, Integer> stats1 = manager.getUnitHackingStats("SPECIALIST_001");
        Map<String, Integer> stats2 = manager.getUnitHackingStats("SPECIALIST_002");
        Map<String, Integer> stats3 = manager.getUnitHackingStats("SPECIALIST_003");
        
        System.out.println("Specialist 1 stats: " + stats1);
        System.out.println("Specialist 2 stats: " + stats2);
        System.out.println("Specialist 3 stats: " + stats3);
        
        System.out.println("Controlled robots: " + manager.getControlledRobots());
        System.out.println("Active terminals: " + manager.getActiveTerminals());
        
        // Final system status
        System.out.println("Final system status:\n" + manager.getSystemStatus());
    }
}
