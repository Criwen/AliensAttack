package com.aliensattack.core.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import com.aliensattack.core.enums.SquadTacticType;
import com.aliensattack.core.model.SquadBonding.BondAbility;
import com.aliensattack.core.model.SquadTacticsSystem.SquadFormation;

import java.util.List;

/**
 * Test class for Squad Tactics System
 */
public class SquadTacticsTest {
    
    private SquadTacticsSystem tacticsSystem;
    private SquadBonding squadBonding;
    private Unit unit1;
    private Unit unit2;
    
    @BeforeEach
    void setUp() {
        tacticsSystem = new SquadTacticsSystem();
        
        // Create test units
        unit1 = new Unit("UNIT_001", "Test Unit 1", com.aliensattack.core.enums.UnitType.SOLDIER, "SQUAD_ALPHA");
        unit2 = new Unit("UNIT_002", "Test Unit 2", com.aliensattack.core.enums.UnitType.SOLDIER, "SQUAD_ALPHA");
        
        squadBonding = new SquadBonding(unit1, unit2);
    }
    
    @Test
    void testSquadFormationCreation() {
        // Test offensive formation
        SquadFormation offensiveFormation = tacticsSystem.createOffensiveFormation();
        assertNotNull(offensiveFormation);
        assertEquals("OFFENSIVE", offensiveFormation.getFormationType());
        
        // Test defensive formation
        SquadFormation defensiveFormation = tacticsSystem.createDefensiveFormation();
        assertNotNull(defensiveFormation);
        assertEquals("DEFENSIVE", defensiveFormation.getFormationType());
        
        // Test flanking formation
        SquadFormation flankingFormation = tacticsSystem.createFlankingFormation();
        assertNotNull(flankingFormation);
        assertEquals("FLANKING", flankingFormation.getFormationType());
        
        // Test support formation
        SquadFormation supportFormation = tacticsSystem.createSupportFormation();
        assertNotNull(supportFormation);
        assertEquals("SUPPORT", supportFormation.getFormationType());
        
        // Test balanced formation
        SquadFormation balancedFormation = tacticsSystem.createBalancedFormation();
        assertNotNull(balancedFormation);
        assertEquals("BALANCED", balancedFormation.getFormationType());
    }
    
    @Test
    void testSquadBonding() {
        // Test bond creation
        assertNotNull(squadBonding);
        assertEquals(unit1, squadBonding.getUnit1());
        assertEquals(unit2, squadBonding.getUnit2());
        
        // Test bond abilities
        List<BondAbility> abilities = squadBonding.getAvailableAbilities();
        assertNotNull(abilities);
        assertFalse(abilities.isEmpty());
        
        // Test bond strength
        int bondStrength = squadBonding.getBondStrength();
        assertTrue(bondStrength > 0);
    }
    
    @Test
    void testSquadTacticsIntegration() {
        // Test tactics system with bonding
        SquadFormation defensiveFormation = tacticsSystem.createDefensiveFormation();
        assertNotNull(defensiveFormation);
        
        // Test formation bonuses
        int formationBonus = defensiveFormation.getFormationBonus();
        assertTrue(formationBonus >= 0);
        
        // Test formation penalties
        int formationPenalty = defensiveFormation.getFormationPenalty();
        assertTrue(formationPenalty >= 0);
    }
    
    @Test
    void testSquadCohesionManager() {
        SquadCohesionManager manager = new SquadCohesionManager();
        manager.initialize();
        
        // Test bond creation
        boolean bondCreated = manager.createSquadBond(unit1, unit2);
        assertTrue(bondCreated);
        
        // Test getting squad bonds
        List<SquadBonding> bonds = manager.getSquadBonds("SQUAD_ALPHA");
        assertNotNull(bonds);
        assertFalse(bonds.isEmpty());
        
        // Test formation retrieval
        SquadFormation formation = manager.getSquadFormation("SQUAD_ALPHA", SquadTacticType.DEFENSIVE);
        assertNotNull(formation);
    }
    
    @Test
    void testSquadBondingFactory() {
        SquadBondingFactory factory = new SquadBondingFactory();
        
        // Test bond creation with factory
        SquadBonding factoryBond = factory.createSquadBond(unit1, unit2, "STRONG");
        assertNotNull(factoryBond);
        assertEquals("STRONG", factoryBond.getBondType());
        
        // Test different bond types
        SquadBonding weakBond = factory.createSquadBond(unit1, unit2, "WEAK");
        assertNotNull(weakBond);
        assertEquals("WEAK", weakBond.getBondType());
        
        SquadBonding moderateBond = factory.createSquadBond(unit1, unit2, "MODERATE");
        assertNotNull(moderateBond);
        assertEquals("MODERATE", moderateBond.getBondType());
    }
    
    @Test
    void testSquadTacticsFactory() {
        SquadTacticsFactory factory = new SquadTacticsFactory();
        
        // Test formation creation with factory
        SquadFormation offensiveFormation = factory.createOffensiveFormation();
        assertNotNull(offensiveFormation);
        assertEquals("OFFENSIVE", offensiveFormation.getFormationType());
        
        SquadFormation defensiveFormation = factory.createDefensiveFormation();
        assertNotNull(defensiveFormation);
        assertEquals("DEFENSIVE", defensiveFormation.getFormationType());
        
        SquadFormation flankingFormation = factory.createFlankingFormation();
        assertNotNull(flankingFormation);
        assertEquals("FLANKING", flankingFormation.getFormationType());
        
        SquadFormation supportFormation = factory.createSupportFormation();
        assertNotNull(supportFormation);
        assertEquals("SUPPORT", supportFormation.getFormationType());
        
        SquadFormation balancedFormation = factory.createBalancedFormation();
        assertNotNull(balancedFormation);
        assertEquals("BALANCED", balancedFormation.getFormationType());
    }
}
