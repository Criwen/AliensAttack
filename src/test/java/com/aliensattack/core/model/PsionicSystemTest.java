package com.aliensattack.core.model;

import com.aliensattack.combat.PsionicAbilityFactory;
import com.aliensattack.core.enums.PsionicType;
import com.aliensattack.core.enums.PsionicSchool;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Comprehensive test for the enhanced psionic system
 */
public class PsionicSystemTest {
    
    private PsionicSystemManager psionicManager;
    private String testUnit1;
    private String testUnit2;
    private String testUnit3;
    
    @BeforeEach
    void setUp() {
        psionicManager = new PsionicSystemManager();
        testUnit1 = "unit_001";
        testUnit2 = "unit_002";
        testUnit3 = "unit_003";
        
        // Register test units
        psionicManager.registerPsionicUnit(testUnit1, 100, 5);
        psionicManager.registerPsionicUnit(testUnit2, 80, 3);
        psionicManager.registerPsionicUnit(testUnit3, 120, 7);
    }
    
    @Test
    void testPsionicSchoolAssignment() {
        // Test setting psionic schools
        assertTrue(psionicManager.setPsionicSchool(testUnit1, PsionicSchool.TELEPATHY));
        assertTrue(psionicManager.setPsionicSchool(testUnit2, PsionicSchool.MIND_CONTROL));
        assertTrue(psionicManager.setPsionicSchool(testUnit3, PsionicSchool.PSYCHIC_WARFARE));
        
        // Verify schools are set correctly
        assertEquals(PsionicSchool.TELEPATHY, psionicManager.getUnitPsiSchool(testUnit1));
        assertEquals(PsionicSchool.MIND_CONTROL, psionicManager.getUnitPsiSchool(testUnit2));
        assertEquals(PsionicSchool.PSYCHIC_WARFARE, psionicManager.getUnitPsiSchool(testUnit3));
    }
    
    @Test
    void testPsionicResistanceSystem() {
        // Test setting psionic resistance
        assertTrue(psionicManager.setPsionicResistance(testUnit1, 25));
        assertTrue(psionicManager.setPsionicResistance(testUnit2, 50));
        assertTrue(psionicManager.setPsionicResistance(testUnit3, 75));
        
        // Verify resistance values
        assertEquals(25, psionicManager.getUnitPsiResistance(testUnit1));
        assertEquals(50, psionicManager.getUnitPsiResistance(testUnit2));
        assertEquals(75, psionicManager.getUnitPsiResistance(testUnit3));
        
        // Test resistance bounds
        assertTrue(psionicManager.setPsionicResistance(testUnit1, -10)); // Should clamp to 0
        assertTrue(psionicManager.setPsionicResistance(testUnit2, 150)); // Should clamp to 100
        assertEquals(0, psionicManager.getUnitPsiResistance(testUnit1));
        assertEquals(100, psionicManager.getUnitPsiResistance(testUnit2));
    }
    
    @Test
    void testPsionicImmunitySystem() {
        // Test adding psionic immunities
        assertTrue(psionicManager.addPsionicImmunity(testUnit1, PsionicType.MIND_CONTROL));
        assertTrue(psionicManager.addPsionicImmunity(testUnit1, PsionicType.PSYCHIC_BLAST));
        assertTrue(psionicManager.addPsionicImmunity(testUnit2, PsionicType.TELEPORT));
        
        // Verify immunities
        assertTrue(psionicManager.isUnitImmuneTo(testUnit1, PsionicType.MIND_CONTROL));
        assertTrue(psionicManager.isUnitImmuneTo(testUnit1, PsionicType.PSYCHIC_BLAST));
        assertTrue(psionicManager.isUnitImmuneTo(testUnit2, PsionicType.TELEPORT));
        assertFalse(psionicManager.isUnitImmuneTo(testUnit1, PsionicType.TELEPORT));
        
        // Test immunity set retrieval
        var immunities = psionicManager.getUnitPsiImmunities(testUnit1);
        assertEquals(2, immunities.size());
        assertTrue(immunities.contains(PsionicType.MIND_CONTROL));
        assertTrue(immunities.contains(PsionicType.PSYCHIC_BLAST));
    }
    
    @Test
    void testPsionicAbilityUnlocking() {
        // Test unlocking abilities
        assertTrue(psionicManager.unlockAbility(testUnit1, PsionicType.MIND_CONTROL));
        assertTrue(psionicManager.unlockAbility(testUnit1, PsionicType.TELEPATHY));
        assertTrue(psionicManager.unlockAbility(testUnit2, PsionicType.PSYCHIC_BLAST));
        
        // Verify abilities are unlocked
        var abilities = psionicManager.getUnitAbilities(testUnit1);
        assertEquals(2, abilities.size());
        
        var abilities2 = psionicManager.getUnitAbilities(testUnit2);
        assertEquals(1, abilities2.size());
    }
    
    @Test
    void testPsionicAbilityUsage() {
        // Unlock abilities first
        psionicManager.unlockAbility(testUnit1, PsionicType.PSYCHIC_BLAST);
        psionicManager.unlockAbility(testUnit2, PsionicType.MIND_CONTROL);
        
        // Test ability usage
        assertTrue(psionicManager.usePsionicAbility(testUnit1, testUnit2, PsionicType.PSYCHIC_BLAST));
        assertTrue(psionicManager.usePsionicAbility(testUnit2, testUnit1, PsionicType.MIND_CONTROL));
        
        // Verify psi energy consumption
        assertEquals(96, psionicManager.getUnitPsiEnergy(testUnit1)); // 100 - 4 (blast cost)
        assertEquals(72, psionicManager.getUnitPsiEnergy(testUnit2)); // 80 - 8 (control cost)
    }
    
    @Test
    void testPsionicResistanceMechanics() {
        // Set high resistance on target
        psionicManager.setPsionicResistance(testUnit2, 80);
        psionicManager.unlockAbility(testUnit1, PsionicType.MIND_CONTROL);
        
        // Test that high resistance reduces success chance
        int successChance = psionicManager.calculateSuccessChance(testUnit1, testUnit2, PsionicType.MIND_CONTROL);
        assertEquals(5, successChance); // 85 - 80 = 5% (minimum 10% but resistance is 80)
    }
    
    @Test
    void testPsionicImmunityMechanics() {
        // Make target immune to mind control
        psionicManager.addPsionicImmunity(testUnit2, PsionicType.MIND_CONTROL);
        psionicManager.unlockAbility(testUnit1, PsionicType.MIND_CONTROL);
        
        // Test that immune units cannot be affected
        int successChance = psionicManager.calculateSuccessChance(testUnit1, testUnit2, PsionicType.MIND_CONTROL);
        assertEquals(0, successChance);
        
        // Test that ability usage fails
        assertFalse(psionicManager.usePsionicAbility(testUnit1, testUnit2, PsionicType.MIND_CONTROL));
    }
    
    @Test
    void testPsionicSchoolBonuses() {
        // Set schools and unlock abilities
        psionicManager.setPsionicSchool(testUnit1, PsionicSchool.TELEPATHY);
        psionicManager.setPsionicSchool(testUnit2, PsionicSchool.PSYCHIC_WARFARE);
        psionicManager.unlockAbility(testUnit1, PsionicType.TELEPATHY);
        psionicManager.unlockAbility(testUnit2, PsionicType.PSYCHIC_BLAST);
        
        // Test school bonuses
        var telepathyAbility = psionicManager.getUnitAbilities(testUnit1).get(0);
        var blastAbility = psionicManager.getUnitAbilities(testUnit2).get(0);
        
        // Telepathy school should get +2 regeneration bonus
        psionicManager.processPsiRegeneration();
        assertEquals(107, psionicManager.getUnitPsiEnergy(testUnit1)); // 100 + 5 + 2 (school bonus)
        
        // Psychic Warfare school should get +3 regeneration bonus
        assertEquals(86, psionicManager.getUnitPsiEnergy(testUnit2)); // 80 + 3 + 3 (school bonus)
    }
    
    @Test
    void testPsionicAbilityProperties() {
        // Test enhanced ability properties
        var mindControl = PsionicAbilityFactory.createMindControl();
        var psychicBlast = PsionicAbilityFactory.createPsychicBlast();
        var teleport = PsionicAbilityFactory.createTeleport();
        
        // Test preferred schools
        assertEquals(PsionicSchool.MIND_CONTROL, mindControl.getPreferredSchool());
        assertEquals(PsionicSchool.PSYCHIC_WARFARE, psychicBlast.getPreferredSchool());
        assertEquals(PsionicSchool.TELEPORTATION, teleport.getPreferredSchool());
        
        // Test resistance penetration
        assertEquals(15, mindControl.getResistancePenetration());
        assertEquals(8, psychicBlast.getResistancePenetration());
        assertEquals(25, teleport.getResistancePenetration());
        
        // Test area effects
        assertFalse(mindControl.hasAreaEffect());
        assertTrue(psychicBlast.hasAreaEffect());
        assertEquals(2, psychicBlast.getAreaRadius());
        
        // Test ability tiers
        assertEquals(2, mindControl.getAbilityTier()); // intensity 12 + cost 8 = 20
        assertEquals(1, psychicBlast.getAbilityTier()); // intensity 10 + cost 4 = 14
        assertEquals(3, teleport.getAbilityTier()); // intensity 20 + cost 15 = 35
    }
    
    @Test
    void testPsionicEffectCalculations() {
        // Test effect calculations with different schools
        var mindControl = PsionicAbilityFactory.createMindControl();
        
        // Base effect without school
        int baseEffect = mindControl.getPsionicEffect();
        assertEquals(24, baseEffect); // intensity 12 * 2 = 24
        
        // Effect with matching school
        int schoolEffect = mindControl.getPsionicEffect(PsionicSchool.MIND_CONTROL);
        assertEquals(30, schoolEffect); // base 24 + school bonus 6 (intensity/2)
        
        // Effect with complementary school
        int complementaryEffect = mindControl.getPsionicEffect(PsionicSchool.TELEPATHY);
        assertEquals(27, complementaryEffect); // base 24 + complementary bonus 3 (intensity/4)
        
        // Effect with non-complementary school
        int nonComplementaryEffect = mindControl.getPsionicEffect(PsionicSchool.PSYCHIC_WARFARE);
        assertEquals(24, nonComplementaryEffect); // base 24 + no bonus
    }
    
    @Test
    void testPsionicRangeModifiers() {
        // Test range modifiers with different schools
        var telepathy = PsionicAbilityFactory.createTelepathy();
        
        // Base range modifier
        int baseRange = telepathy.getRangeModifier();
        assertEquals(2, baseRange); // range 1 * 2 = 2
        
        // Range with telepathy school bonus
        int schoolRange = telepathy.getRangeModifier(PsionicSchool.TELEPATHY);
        assertEquals(4, schoolRange); // base 2 + school bonus 2 = 4
        
        // Range with other school
        int otherRange = telepathy.getRangeModifier(PsionicSchool.MIND_CONTROL);
        assertEquals(2, otherRange); // base 2 + no bonus
    }
    
    @Test
    void testPsionicEnergyManagement() {
        // Test psi energy regeneration with school bonuses
        psionicManager.setPsionicSchool(testUnit1, PsionicSchool.TELEPATHY);
        psionicManager.setPsionicSchool(testUnit2, PsionicSchool.PSYCHIC_WARFARE);
        
        // Process regeneration
        psionicManager.processPsiRegeneration();
        
        // Telepathy school: 100 + 5 + 2 = 107
        assertEquals(107, psionicManager.getUnitPsiEnergy(testUnit1));
        
        // Psychic Warfare school: 80 + 3 + 3 = 86
        assertEquals(86, psionicManager.getUnitPsiEnergy(testUnit2));
        
        // No school: 120 + 7 + 0 = 127
        assertEquals(127, psionicManager.getUnitPsiEnergy(testUnit3));
    }
    
    @Test
    void testStatusEffectProcessing() {
        // Test status effect processing
        psionicManager.unlockAbility(testUnit1, PsionicType.PSYCHIC_BARRIER);
        psionicManager.usePsionicAbility(testUnit1, testUnit2, PsionicType.PSYCHIC_BARRIER);
        
        // Verify effects are applied
        var effects = psionicManager.getUnitActiveEffects(testUnit2);
        assertEquals(2, effects.size()); // PROTECTED and PSYCHIC_SHIELD
        
        // Process status effects
        psionicManager.processStatusEffects();
        
        // Effects should still be active (duration > 1)
        effects = psionicManager.getUnitActiveEffects(testUnit2);
        assertEquals(2, effects.size());
    }
    
    @Test
    void testCustomPsionicAbilityCreation() {
        // Test custom ability creation
        var customAbility = PsionicAbilityFactory.createCustomPsionicAbility(
            "Custom Mind Control", 
            PsionicType.MIND_CONTROL, 
            15, 5, 6, 4, 20, 
            PsionicSchool.MIND_CONTROL, 
            25, false, 0
        );
        
        assertEquals("Custom Mind Control", customAbility.getName());
        assertEquals(PsionicType.MIND_CONTROL, customAbility.getPsionicType());
        assertEquals(15, customAbility.getPsiCost());
        assertEquals(5, customAbility.getRange());
        assertEquals(6, customAbility.getCooldown());
        assertEquals(4, customAbility.getDuration());
        assertEquals(20, customAbility.getIntensity());
        assertEquals(PsionicSchool.MIND_CONTROL, customAbility.getPreferredSchool());
        assertEquals(25, customAbility.getResistancePenetration());
        assertFalse(customAbility.hasAreaEffect());
        assertEquals(0, customAbility.getAreaRadius());
    }
}
