package com.aliensattack.core.model;

import java.util.*;

/**
 * Test class for Squad Tactics System
 * Tests squad bonding, tactics, and cohesion bonuses
 */
public class SquadTacticsTest {
    
    public static void main(String[] args) {
        System.out.println("=== Squad Tactics System Test ===");
        
        // Test Squad Cohesion Manager
        testSquadCohesionManager();
        
        // Test Squad Tactics Factory
        testSquadTacticsFactory();
        
        // Test Advanced Squad Bonding
        testAdvancedSquadBonding();
        
        // Test Advanced Squad Tactics System
        testAdvancedSquadTacticsSystem();
        
        // Test Integration
        testIntegration();
        
        System.out.println("=== All tests completed ===");
    }
    
    /**
     * Test Squad Cohesion Manager
     */
    private static void testSquadCohesionManager() {
        System.out.println("\n--- Testing Squad Cohesion Manager ---");
        
        SquadCohesionManager manager = new SquadCohesionManager();
        
        // Create test units
        Unit unit1 = createTestUnit("Soldier1", "Assault");
        Unit unit2 = createTestUnit("Soldier2", "Support");
        Unit unit3 = createTestUnit("Soldier3", "Sniper");
        Unit unit4 = createTestUnit("Soldier4", "Heavy");
        
        List<Unit> squadMembers = Arrays.asList(unit1, unit2, unit3, unit4);
        
        // Register squad
        boolean registered = manager.registerSquad("Squad_Alpha", squadMembers, unit1);
        System.out.println("Squad registered: " + registered);
        
        // Add experience
        manager.addSquadExperience("Squad_Alpha", 150);
        System.out.println("Squad experience: " + manager.getSquadExperience("Squad_Alpha"));
        System.out.println("Squad cohesion level: " + manager.getSquadCohesionLevel("Squad_Alpha"));
        
        // Test cohesion bonuses
        int accuracyBonus = manager.getCohesionBonus("Squad_Alpha", unit1, 
            SquadCohesionManager.CohesionBonusType.ACCURACY_BONUS);
        System.out.println("Accuracy bonus for " + unit1.getName() + ": " + accuracyBonus);
        
        // Test total cohesion bonus
        int totalBonus = manager.getTotalCohesionBonus("Squad_Alpha", unit1);
        System.out.println("Total cohesion bonus for " + unit1.getName() + ": " + totalBonus);
        
        // Test squad bonds
        List<AdvancedSquadBonding> bonds = manager.getSquadBonds("Squad_Alpha");
        System.out.println("Number of squad bonds: " + bonds.size());
        
        // Process squad systems
        manager.processSquadSystems();
        System.out.println("Squad systems processed");
    }
    
    /**
     * Test Squad Tactics Factory
     */
    private static void testSquadTacticsFactory() {
        System.out.println("\n--- Testing Squad Tactics Factory ---");
        
        // Test tactic creation
        SquadTactic coordinatedAssault = SquadTacticsFactory.createCoordinatedAssaultTactic();
        System.out.println("Created tactic: " + coordinatedAssault.getName());
        System.out.println("Tactic type: " + coordinatedAssault.getType());
        System.out.println("Duration: " + coordinatedAssault.getDuration());
        System.out.println("Squad size: " + coordinatedAssault.getSquadSize());
        
        SquadTactic defensiveFormation = SquadTacticsFactory.createDefensiveFormationTactic();
        System.out.println("Created tactic: " + defensiveFormation.getName());
        
        SquadTactic stealthOperation = SquadTacticsFactory.createStealthOperationTactic();
        System.out.println("Created tactic: " + stealthOperation.getName());
        
        // Test formation creation
        AdvancedSquadTacticsSystem.SquadFormation defensiveFormation2 = 
            SquadTacticsFactory.createDefensiveFormation();
        System.out.println("Created formation: " + defensiveFormation2.getFormationName());
        
        // Test maneuver creation
        AdvancedSquadTacticsSystem.SquadManeuver coordinatedAttack = 
            SquadTacticsFactory.createCoordinatedAttackManeuver();
        System.out.println("Created maneuver: " + coordinatedAttack.getManeuverName());
        
        // Test random creation
        SquadTactic randomTactic = SquadTacticsFactory.createRandomSquadTactic();
        System.out.println("Created random tactic: " + randomTactic.getName());
        
        // Test available types
        List<String> tacticTypes = SquadTacticsFactory.getAllTacticTypes();
        System.out.println("Available tactic types: " + tacticTypes.size());
        
        List<String> formationTypes = SquadTacticsFactory.getAllFormationTypes();
        System.out.println("Available formation types: " + formationTypes.size());
        
        List<String> maneuverTypes = SquadTacticsFactory.getAllManeuverTypes();
        System.out.println("Available maneuver types: " + maneuverTypes.size());
    }
    
    /**
     * Test Advanced Squad Bonding
     */
    private static void testAdvancedSquadBonding() {
        System.out.println("\n--- Testing Advanced Squad Bonding ---");
        
        // Create test units
        Unit unit1 = createTestUnit("Bonded1", "Assault");
        Unit unit2 = createTestUnit("Bonded2", "Support");
        
        // Create bond
        AdvancedSquadBonding bond = new AdvancedSquadBonding(unit1, unit2);
        System.out.println("Created bond between " + unit1.getName() + " and " + unit2.getName());
        System.out.println("Initial bond level: " + bond.getBondLevel());
        
        // Add experience
        bond.addBondExperience(100);
        System.out.println("Bond experience: " + bond.getBondExperience());
        System.out.println("Bond level: " + bond.getBondLevel());
        
        // Activate bond
        bond.activateBond();
        System.out.println("Bond activated: " + bond.isBondActive());
        
        // Update bond
        bond.updateBond();
        System.out.println("Bond updated, experience: " + bond.getBondExperience());
        
        // Test bond abilities
        List<AdvancedSquadBonding.BondAbility> abilities = bond.getAvailableAbilities();
        System.out.println("Available bond abilities: " + abilities.size());
        
        // Test bond description
        String description = bond.getBondDescription();
        System.out.println("Bond description: " + description);
        
        // Test bond partner
        Unit partner = bond.getBondPartner(unit1);
        System.out.println("Bond partner for " + unit1.getName() + ": " + partner.getName());
    }
    
    /**
     * Test Advanced Squad Tactics System
     */
    private static void testAdvancedSquadTacticsSystem() {
        System.out.println("\n--- Testing Advanced Squad Tactics System ---");
        
        // Create test units
        Unit unit1 = createTestUnit("Tactics1", "Assault");
        Unit unit2 = createTestUnit("Tactics2", "Support");
        Unit unit3 = createTestUnit("Tactics3", "Sniper");
        
        List<Unit> squadMembers = Arrays.asList(unit1, unit2, unit3);
        
        // Create tactics system
        AdvancedSquadTacticsSystem tacticsSystem = AdvancedSquadTacticsSystem.builder()
            .tacticsId("Tactics_Squad")
            .squads(new HashMap<>())
            .squadTactics(new HashMap<>())
            .squadBonds(new HashMap<>())
            .squadCoordinations(new HashMap<>())
            .squadMorales(new HashMap<>())
            .squadExperiences(new HashMap<>())
            .squadMembers(new HashMap<>())
            .squadBonuses(new HashMap<>())
            .activeTactics(new HashMap<>())
            .squadLevels(new HashMap<>())
            .squadStates(new HashMap<>())
            .totalSquads(0)
            .maxSquadSize(6)
            .isTacticsActive(false)
            .build();
        
        System.out.println("Created tactics system with ID: " + tacticsSystem.getTacticsId());
        
        // Create a squad
        boolean squadCreated = tacticsSystem.createSquad("Tactics_Squad", "Tactics Squad", unit1.getName());
        System.out.println("Squad created: " + squadCreated);
        
        // Add squad members
        for (Unit member : squadMembers) {
            tacticsSystem.addSquadMember("Tactics_Squad", member.getName());
        }
        System.out.println("Added " + squadMembers.size() + " squad members");
        
        // Test squad information
        List<String> members = tacticsSystem.getSquadMembers("Tactics_Squad");
        System.out.println("Squad members: " + members.size());
        
        // Test squad bonuses
        Map<String, Integer> bonuses = tacticsSystem.getSquadBonuses("Tactics_Squad");
        System.out.println("Squad bonuses: " + bonuses.size());
        
        // Test squad level
        int level = tacticsSystem.getSquadLevel("Tactics_Squad");
        System.out.println("Squad level: " + level);
        
        // Test squad state
        boolean isActive = tacticsSystem.isSquadActive("Tactics_Squad");
        System.out.println("Squad active: " + isActive);
    }
    
    /**
     * Test Integration
     */
    private static void testIntegration() {
        System.out.println("\n--- Testing Integration ---");
        
        // Create comprehensive test
        SquadCohesionManager manager = new SquadCohesionManager();
        
        // Create test units
        Unit unit1 = createTestUnit("Integration1", "Assault");
        Unit unit2 = createTestUnit("Integration2", "Support");
        Unit unit3 = createTestUnit("Integration3", "Sniper");
        
        List<Unit> squadMembers = Arrays.asList(unit1, unit2, unit3);
        
        // Register squad
        manager.registerSquad("Integration_Squad", squadMembers, unit1);
        
        // Create and activate tactic
        SquadTactic tactic = SquadTacticsFactory.createCoordinatedAssaultTactic();
        boolean tacticActivated = manager.activateSquadTactic("Integration_Squad", tactic);
        System.out.println("Tactic activated: " + tacticActivated);
        
        // Add experience
        manager.addSquadExperience("Integration_Squad", 300);
        
        // Test integrated bonuses
        int accuracyBonus = manager.getCohesionBonus("Integration_Squad", unit1, 
            SquadCohesionManager.CohesionBonusType.ACCURACY_BONUS);
        int damageBonus = manager.getCohesionBonus("Integration_Squad", unit1, 
            SquadCohesionManager.CohesionBonusType.DAMAGE_BONUS);
        int defenseBonus = manager.getCohesionBonus("Integration_Squad", unit1, 
            SquadCohesionManager.CohesionBonusType.DEFENSE_BONUS);
        
        System.out.println("Integrated bonuses for " + unit1.getName() + ":");
        System.out.println("  Accuracy: " + accuracyBonus);
        System.out.println("  Damage: " + damageBonus);
        System.out.println("  Defense: " + defenseBonus);
        
        // Process systems
        manager.processSquadSystems();
        
        // Test squad information
        SquadCohesionManager.CohesionLevel level = manager.getSquadCohesionLevel("Integration_Squad");
        int experience = manager.getSquadExperience("Integration_Squad");
        Unit leader = manager.getSquadLeader("Integration_Squad");
        List<Unit> members = manager.getSquadMembers("Integration_Squad");
        
        System.out.println("Squad information:");
        System.out.println("  Cohesion level: " + level);
        System.out.println("  Experience: " + experience);
        System.out.println("  Leader: " + leader.getName());
        System.out.println("  Members: " + members.size());
        
        // Test bond integration
        List<AdvancedSquadBonding> bonds = manager.getSquadBonds("Integration_Squad");
        System.out.println("Active bonds: " + bonds.size());
        
        for (AdvancedSquadBonding bond : bonds) {
            System.out.println("  Bond: " + bond.getUnit1().getName() + " <-> " + bond.getUnit2().getName());
            System.out.println("    Level: " + bond.getBondLevel());
            System.out.println("    Active: " + bond.isBondActive());
        }
    }
    
    /**
     * Create a test unit
     */
    private static Unit createTestUnit(String name, String role) {
        Unit unit = new Unit(name, 100, 3, 5, 25, com.aliensattack.core.enums.UnitType.SOLDIER);
        unit.setAccuracy(75);
        unit.setDefense(10);
        unit.setCriticalChance(10);
        unit.setDodgeChance(5);
        unit.setOverwatchChance(15);
        unit.setPsiStrength(20);
        unit.setPosition(new Position(0, 0, 0));
        return unit;
    }
}
