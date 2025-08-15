package com.aliensattack.combat;

import com.aliensattack.combat.interfaces.ICombatStrategy;
import com.aliensattack.combat.strategies.TacticalCombatStrategy;
import com.aliensattack.core.model.Unit;
import com.aliensattack.core.model.Position;
import com.aliensattack.core.model.CoverObject;
import com.aliensattack.core.enums.UnitType;
import com.aliensattack.field.ITacticalField;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Tests for the refactored CombatManager
 */
@DisplayName("CombatManager Tests")
class CombatManagerTest {
    
    private CombatManager combatManager;
    private ICombatStrategy mockStrategy;
    private ITacticalField mockField;
    private Unit testUnit1;
    private Unit testUnit2;
    
    @BeforeEach
    void setUp() {
        mockStrategy = new TacticalCombatStrategy();
        // Create a simple mock field for testing
        mockField = new ITacticalField() {
            @Override
            public boolean isValidPosition(Position position) {
                return position.getX() >= 0 && position.getY() >= 0;
            }
            
            @Override
            public boolean isValidPosition(int x, int y) {
                return x >= 0 && y >= 0;
            }
            
            @Override
            public int getWidth() { return 100; }
            
            @Override
            public int getHeight() { return 100; }
            
            @Override
            public void addUnit(Unit unit) {}
            
            @Override
            public void removeUnit(Unit unit) {}
            
            @Override
            public Unit getUnitAt(int x, int y) { return null; }
            
            @Override
            public Unit getUnitAt(Position position) { return null; }
            
            @Override
            public List<Unit> getAllUnits() { return new ArrayList<>(); }
            
            @Override
            public List<Unit> getPlayerUnits() { return new ArrayList<>(); }
            
            @Override
            public List<Unit> getEnemyUnits() { return new ArrayList<>(); }
            
            @Override
            public void addCoverObject(CoverObject cover, int x, int y) {}
            
            @Override
            public void addCoverObject(CoverObject cover, Position position) {}
            
            @Override
            public CoverObject getCoverObject(int x, int y) { return null; }
            
            @Override
            public CoverObject getCoverObject(Position position) { return null; }
            
            @Override
            public boolean isPositionVisible(Position from, Position to, int viewRange) { return true; }
            
            @Override
            public List<Unit> getVisibleUnits(Unit observer) { return new ArrayList<>(); }
            
            @Override
            public List<Unit> getVisibleEnemies(Unit observer) { return new ArrayList<>(); }
            
            @Override
            public List<Position> getVisiblePositions(Unit observer) { return new ArrayList<>(); }
            
            @Override
            public double calculateDistance(Position from, Position to) { return 0.0; }
            
            @Override
            public boolean hasLineOfSight(Position from, Position to) { return true; }
            
            @Override
            public Map<Position, Unit> getUnitPositions() { return new HashMap<>(); }
            
            @Override
            public Map<Position, CoverObject> getCoverPositions() { return new HashMap<>(); }
        };
        combatManager = new CombatManager(mockStrategy, mockField);
        
        // Create test units
        testUnit1 = new Unit("TestSoldier1", 100, 3, 5, 25, UnitType.SOLDIER);
        testUnit2 = new Unit("TestSoldier2", 100, 3, 5, 25, UnitType.SOLDIER);
        
        // Set positions
        testUnit1.setPosition(new Position(0, 0));
        testUnit2.setPosition(new Position(1, 1));
    }
    
    @Test
    @DisplayName("Should initialize with strategy and field")
    void shouldInitializeWithStrategyAndField() {
        assertNotNull(combatManager);
        assertTrue(combatManager.isPlayerTurn());
        assertEquals(1, combatManager.getCurrentTurn());
    }
    
    @Test
    @DisplayName("Should add player units")
    void shouldAddPlayerUnits() {
        combatManager.addPlayerUnit(testUnit1);
        
        assertEquals(1, combatManager.getPlayerUnits().size());
        assertTrue(combatManager.getPlayerUnits().contains(testUnit1));
    }
    
    @Test
    @DisplayName("Should add enemy units")
    void shouldAddEnemyUnits() {
        combatManager.addEnemyUnit(testUnit2);
        
        assertEquals(1, combatManager.getEnemyUnits().size());
        assertTrue(combatManager.getEnemyUnits().contains(testUnit2));
    }
    
    @Test
    @DisplayName("Should manage turn state")
    void shouldManageTurnState() {
        assertTrue(combatManager.isPlayerTurn());
        
        combatManager.endPlayerTurn();
        assertFalse(combatManager.isPlayerTurn());
        assertEquals(2, combatManager.getCurrentTurn());
        
        combatManager.endEnemyTurn();
        assertTrue(combatManager.isPlayerTurn());
    }
    
    @Test
    @DisplayName("Should check game over conditions")
    void shouldCheckGameOverConditions() {
        // Game not over initially
        assertFalse(combatManager.isGameOver());
        assertFalse(combatManager.hasPlayerWon());
        assertFalse(combatManager.hasEnemyWon());
        
        // Add units and simulate death
        combatManager.addPlayerUnit(testUnit1);
        combatManager.addEnemyUnit(testUnit2);
        
        testUnit2.setCurrentHealth(0); // Enemy unit dies
        
        assertTrue(combatManager.hasPlayerWon());
        assertTrue(combatManager.isGameOver());
    }
    
    @Test
    @DisplayName("Should use object pool for positions")
    void shouldUseObjectPoolForPositions() {
        Position pos1 = combatManager.acquirePosition(5, 5);
        Position pos2 = combatManager.acquirePosition(10, 10);
        
        assertNotNull(pos1);
        assertNotNull(pos2);
        assertEquals(5, pos1.getX());
        assertEquals(5, pos1.getY());
        assertEquals(10, pos2.getX());
        assertEquals(10, pos2.getY());
        
        // Return to pool
        combatManager.releasePosition(pos1);
        combatManager.releasePosition(pos2);
        
        String stats = combatManager.getPoolStats();
        assertTrue(stats.contains("PositionPool"));
    }
    
    @Test
    @DisplayName("Should reset unit action points")
    void shouldResetUnitActionPoints() {
        testUnit1.setActionPoints(0);
        assertEquals(0, testUnit1.getActionPoints());
        
        combatManager.resetUnitActionPoints(testUnit1);
        assertEquals(2, testUnit1.getActionPoints());
    }
    
    @Test
    @DisplayName("Should check unit alive status")
    void shouldCheckUnitAliveStatus() {
        assertTrue(combatManager.isUnitAlive(testUnit1));
        
        testUnit1.setCurrentHealth(0);
        assertFalse(combatManager.isUnitAlive(testUnit1));
    }
}
