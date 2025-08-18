package com.aliensattack.core.ai;

import com.aliensattack.core.model.Position;
import com.aliensattack.core.model.Unit;
import lombok.Data;
import lombok.Builder;

import java.util.List;
import java.util.ArrayList;

/**
 * Represents the current battlefield situation for AI decision making
 */
@Data
@Builder
public class BattlefieldSituation {
    
    // Unit information
    private String unitName;
    private int unitHealth;
    private int unitMaxHealth;
    private double unitActionPoints;
    private Position unitPosition;
    private String unitType;
    
    // Field information
    private int fieldWidth;
    private int fieldHeight;
    
    // Enemy information
    private List<EnemyInfo> visibleEnemies;
    private List<EnemyInfo> nearbyEnemies;
    
    // Ally information
    private List<AllyInfo> visibleAllies;
    private List<AllyInfo> nearbyAllies;
    
    // Position information
    private List<Position> availableMovePositions;
    private List<Position> coverPositions;
    private List<Position> hazardPositions;
    private List<Position> highGroundPositions;
    
    // Tactical information
    private boolean isUnderFire;
    private boolean isFlanked;
    private boolean hasCover;
    private int distanceToNearestEnemy;
    private String currentThreatLevel;
    
    // Weather and environmental effects
    private String weatherCondition;
    private List<String> activeHazards;
    
    @Data
    @Builder
    public static class EnemyInfo {
        private String name;
        private Position position;
        private int health;
        private int maxHealth;
        private String weaponType;
        private int threatLevel;
        private double distance;
        private boolean hasCover;
        private boolean isOverwatching;
    }
    
    @Data
    @Builder
    public static class AllyInfo {
        private String name;
        private Position position;
        private int health;
        private int maxHealth;
        private boolean needsSupport;
        private double distance;
    }
    
    /**
     * Create a battlefield situation for a specific unit
     */
    public static BattlefieldSituation createForUnit(Unit unit, List<Unit> allUnits, 
                                                   int fieldWidth, int fieldHeight) {
        return BattlefieldSituation.builder()
                .unitName(unit.getName())
                .unitHealth(unit.getCurrentHealth())
                .unitMaxHealth(unit.getMaxHealth())
                .unitActionPoints(unit.getActionPoints())
                .unitPosition(unit.getPosition())
                .unitType(unit.getUnitType().toString())
                .fieldWidth(fieldWidth)
                .fieldHeight(fieldHeight)
                .visibleEnemies(new ArrayList<>())
                .nearbyEnemies(new ArrayList<>())
                .visibleAllies(new ArrayList<>())
                .nearbyAllies(new ArrayList<>())
                .availableMovePositions(new ArrayList<>())
                .coverPositions(new ArrayList<>())
                .hazardPositions(new ArrayList<>())
                .highGroundPositions(new ArrayList<>())
                .isUnderFire(false)
                .isFlanked(false)
                .hasCover(false)
                .distanceToNearestEnemy(0)
                .currentThreatLevel("LOW")
                .weatherCondition("CLEAR")
                .activeHazards(new ArrayList<>())
                .build();
    }
}
