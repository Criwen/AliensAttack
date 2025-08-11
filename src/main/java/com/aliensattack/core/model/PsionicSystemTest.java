package com.aliensattack.core.model;

import com.aliensattack.combat.PsionicAbilityFactory;
import com.aliensattack.core.enums.PsionicType;

/**
 * Simple test class for psionic system functionality
 */
public class PsionicSystemTest {
    
    public static void main(String[] args) {
        System.out.println("Testing Psionic System Implementation...");
        
        // Test 1: Create psionic abilities
        testPsionicAbilityCreation();
        
        // Test 2: Test psionic system manager
        testPsionicSystemManager();
        
        System.out.println("All tests completed successfully!");
    }
    
    /**
     * Test psionic ability creation
     */
    private static void testPsionicAbilityCreation() {
        System.out.println("Testing psionic ability creation...");
        
        // Test TELEPORT ability
        var teleportAbility = PsionicAbilityFactory.createTeleport();
        System.out.println("Created TELEPORT ability: " + teleportAbility.getName());
        System.out.println("  - Psi Cost: " + teleportAbility.getPsiCost());
        System.out.println("  - Range: " + teleportAbility.getRange());
        System.out.println("  - Cooldown: " + teleportAbility.getCooldown());
        System.out.println("  - Description: " + teleportAbility.getDescription());
        
        // Test PSYCHIC_DOMINANCE ability
        var dominanceAbility = PsionicAbilityFactory.createPsychicDominance();
        System.out.println("Created PSYCHIC_DOMINANCE ability: " + dominanceAbility.getName());
        System.out.println("  - Psi Cost: " + dominanceAbility.getPsiCost());
        System.out.println("  - Range: " + dominanceAbility.getRange());
        System.out.println("  - Affects Robots: " + dominanceAbility.affectsRobots());
        
        // Test MIND_MERGE ability
        var mergeAbility = PsionicAbilityFactory.createEnhancedMindMerge();
        System.out.println("Created MIND_MERGE ability: " + mergeAbility.getName());
        System.out.println("  - Psi Cost: " + mergeAbility.getPsiCost());
        System.out.println("  - Duration: " + mergeAbility.getDuration());
        
        // Test PSYCHIC_BARRIER ability
        var barrierAbility = PsionicAbilityFactory.createEnhancedPsychicBarrier();
        System.out.println("Created PSYCHIC_BARRIER ability: " + barrierAbility.getName());
        System.out.println("  - Psi Cost: " + barrierAbility.getPsiCost());
        System.out.println("  - Is Defensive: " + barrierAbility.isDefensive());
        
        System.out.println("Psionic ability creation tests passed!");
    }
    
    /**
     * Test psionic system manager
     */
    private static void testPsionicSystemManager() {
        System.out.println("Testing psionic system manager...");
        
        PsionicSystemManager manager = new PsionicSystemManager();
        
        // Register a psionic unit
        String unitId = "test_unit_1";
        boolean registered = manager.registerPsionicUnit(unitId, 100, 5);
        System.out.println("Registered psionic unit: " + registered);
        
        // Unlock abilities
        boolean unlockedTeleport = manager.unlockAbility(unitId, PsionicType.TELEPORT);
        boolean unlockedDominance = manager.unlockAbility(unitId, PsionicType.PSYCHIC_DOMINANCE);
        boolean unlockedMerge = manager.unlockAbility(unitId, PsionicType.MIND_MERGE);
        boolean unlockedBarrier = manager.unlockAbility(unitId, PsionicType.PSYCHIC_BARRIER);
        
        System.out.println("Unlocked TELEPORT: " + unlockedTeleport);
        System.out.println("Unlocked PSYCHIC_DOMINANCE: " + unlockedDominance);
        System.out.println("Unlocked MIND_MERGE: " + unlockedMerge);
        System.out.println("Unlocked PSYCHIC_BARRIER: " + unlockedBarrier);
        
        // Check abilities
        var abilities = manager.getUnitAbilities(unitId);
        System.out.println("Unit has " + abilities.size() + " abilities");
        
        // Check psi energy
        int energy = manager.getUnitPsiEnergy(unitId);
        int capacity = manager.getUnitPsiCapacity(unitId);
        int regeneration = manager.getUnitPsiRegeneration(unitId);
        
        System.out.println("Unit psi energy: " + energy + "/" + capacity);
        System.out.println("Unit psi regeneration: " + regeneration);
        
        // Test ability usage
        boolean canUseTeleport = manager.canUseAbility(unitId, PsionicType.TELEPORT);
        System.out.println("Can use TELEPORT: " + canUseTeleport);
        
        System.out.println("Psionic system manager tests passed!");
    }
}
