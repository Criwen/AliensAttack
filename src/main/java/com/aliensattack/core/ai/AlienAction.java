package com.aliensattack.core.ai;

import com.aliensattack.core.model.Position;
import com.aliensattack.core.model.Unit;
import lombok.Data;
import lombok.Builder;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a possible action that an Alien unit can perform
 */
@Data
@Builder
public class AlienAction {
    
    // Action identification
    private String actionId;
    private String actionName;
    private String actionType;
    private String description;
    
    // Action requirements
    private double actionPointCost;
    private boolean requiresTarget;
    private boolean requiresPosition;
    private int range;
    
    // Action parameters
    private Unit targetUnit;
    private Position targetPosition;
    private String weaponType;
    private String abilityType;
    
    // Action effectiveness
    private double successChance;
    private int baseDamage;
    private String damageType;
    private List<String> effects;
    
    // Tactical considerations
    private String tacticalAdvantage;
    private String tacticalRisk;
    private int priority;
    
    /**
     * Create a move action
     */
    public static AlienAction createMoveAction(Position targetPosition, double cost) {
        return AlienAction.builder()
                .actionId("MOVE_" + targetPosition.getX() + "_" + targetPosition.getY())
                .actionName("Move")
                .actionType("MOVE")
                .description("Move to position " + targetPosition)
                .actionPointCost(cost)
                .requiresTarget(false)
                .requiresPosition(true)
                .targetPosition(targetPosition)
                .range(0)
                .successChance(1.0)
                .baseDamage(0)
                .damageType("NONE")
                .effects(new ArrayList<>())
                .tacticalAdvantage("Reposition for better tactical advantage")
                .tacticalRisk("May expose unit to enemy fire")
                .priority(3)
                .build();
    }
    
    /**
     * Create an attack action
     */
    public static AlienAction createAttackAction(Unit target, double cost, int damage) {
        return AlienAction.builder()
                .actionId("ATTACK_" + target.getName())
                .actionName("Attack")
                .actionType("ATTACK")
                .description("Attack " + target.getName())
                .actionPointCost(cost)
                .requiresTarget(true)
                .requiresPosition(false)
                .targetUnit(target)
                .range(8)
                .successChance(0.8)
                .baseDamage(damage)
                .damageType("KINETIC")
                .effects(new ArrayList<>())
                .tacticalAdvantage("Eliminate enemy threat")
                .tacticalRisk("May trigger overwatch or retaliation")
                .priority(8)
                .build();
    }
    
    /**
     * Create a defend action
     */
    public static AlienAction createDefendAction(double cost) {
        return AlienAction.builder()
                .actionId("DEFEND")
                .actionName("Defend")
                .actionType("DEFEND")
                .description("Take defensive stance")
                .actionPointCost(cost)
                .requiresTarget(false)
                .requiresPosition(false)
                .range(0)
                .successChance(1.0)
                .baseDamage(0)
                .damageType("NONE")
                .effects(List.of("DEFENSE_BONUS", "OVERWATCH_READY"))
                .tacticalAdvantage("Improved defense and overwatch capability")
                .tacticalRisk("No offensive action this turn")
                .priority(5)
                .build();
    }
    
    /**
     * Create an overwatch action
     */
    public static AlienAction createOverwatchAction(double cost) {
        return AlienAction.builder()
                .actionId("OVERWATCH")
                .actionName("Overwatch")
                .actionType("OVERWATCH")
                .description("Set up overwatch for reaction fire")
                .actionPointCost(cost)
                .requiresTarget(false)
                .requiresPosition(false)
                .range(8)
                .successChance(0.9)
                .baseDamage(0)
                .damageType("REACTION")
                .effects(List.of("OVERWATCH_ACTIVE"))
                .tacticalAdvantage("Can shoot at moving enemies")
                .tacticalRisk("No movement this turn")
                .priority(6)
                .build();
    }
    
    /**
     * Create a special ability action
     */
    public static AlienAction createSpecialAbilityAction(String abilityType, Unit target, double cost) {
        return AlienAction.builder()
                .actionId("SPECIAL_" + abilityType)
                .actionName("Special Ability: " + abilityType)
                .actionType("SPECIAL_ABILITY")
                .description("Use special ability: " + abilityType)
                .actionPointCost(cost)
                .requiresTarget(true)
                .requiresPosition(false)
                .targetUnit(target)
                .range(6)
                .successChance(0.7)
                .baseDamage(0)
                .damageType("SPECIAL")
                .effects(List.of(abilityType.toUpperCase()))
                .tacticalAdvantage("Unique tactical advantage")
                .tacticalRisk("May fail or have unintended consequences")
                .priority(9)
                .build();
    }
    
    /**
     * Create a retreat action
     */
    public static AlienAction createRetreatAction(Position retreatPosition, double cost) {
        return AlienAction.builder()
                .actionId("RETREAT_" + retreatPosition.getX() + "_" + retreatPosition.getY())
                .actionName("Retreat")
                .actionType("RETREAT")
                .description("Retreat to safer position")
                .actionPointCost(cost)
                .requiresTarget(false)
                .requiresPosition(true)
                .targetPosition(retreatPosition)
                .range(0)
                .successChance(1.0)
                .baseDamage(0)
                .damageType("NONE")
                .effects(List.of("RETREAT_BONUS"))
                .tacticalAdvantage("Move to safer position")
                .tacticalRisk("May appear weak to enemies")
                .priority(2)
                .build();
    }
    
    /**
     * Check if this action can be performed by the given unit
     */
    public boolean canBePerformedBy(Unit unit) {
        return unit.getActionPoints() >= actionPointCost && unit.isAlive();
    }
    
    /**
     * Get action summary for display
     */
    public String getActionSummary() {
        StringBuilder summary = new StringBuilder();
        summary.append(actionName).append(" (");
        summary.append(actionPointCost).append(" AP)");
        
        if (targetUnit != null) {
            summary.append(" -> ").append(targetUnit.getName());
        }
        if (targetPosition != null) {
            summary.append(" -> (").append(targetPosition.getX()).append(",").append(targetPosition.getY()).append(")");
        }
        
        summary.append(" - ").append(description);
        return summary.toString();
    }
}
