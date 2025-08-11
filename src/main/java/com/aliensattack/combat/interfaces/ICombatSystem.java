package com.aliensattack.combat.interfaces;

import com.aliensattack.core.interfaces.IUnit;
import com.aliensattack.core.model.Position;
import com.aliensattack.combat.CombatResult;
import com.aliensattack.core.enums.CoverType;
import com.aliensattack.core.enums.TerrainType;

import java.util.List;

/**
 * Core interface for the combat system
 * Defines all combat-related operations and calculations
 */
public interface ICombatSystem {
    
    // Combat Resolution
    CombatResult resolveCombat(IUnit attacker, IUnit target, Position targetPosition);
    CombatResult resolveRangedAttack(IUnit attacker, IUnit target, Position targetPosition);
    CombatResult resolveMeleeAttack(IUnit attacker, IUnit target, Position targetPosition);
    CombatResult resolveOverwatchShot(IUnit attacker, IUnit target, Position targetPosition);
    
    // Accuracy and Hit Calculation
    int calculateHitChance(IUnit attacker, IUnit target, Position targetPosition);
    int calculateCriticalChance(IUnit attacker, IUnit target, Position targetPosition);
    boolean isHit(int hitChance);
    boolean isCritical(int criticalChance);
    
    // Damage Calculation
    int calculateDamage(IUnit attacker, IUnit target, boolean isCritical);
    int calculateDamageWithCover(IUnit attacker, IUnit target, Position targetPosition, CoverType cover);
    int calculateDamageWithTerrain(IUnit attacker, IUnit target, Position targetPosition, TerrainType terrain);
    
    // Cover and Terrain
    CoverType getCoverAtPosition(Position position);
    TerrainType getTerrainAtPosition(Position position);
    int getCoverBonus(CoverType cover);
    int getTerrainBonus(TerrainType terrain);
    
    // Range and Line of Sight
    boolean isInRange(IUnit attacker, IUnit target, Position targetPosition);
    boolean hasLineOfSight(IUnit attacker, Position targetPosition);
    boolean isFlanking(IUnit attacker, IUnit target, Position targetPosition);
    int calculateDistance(Position pos1, Position pos2);
    
    // Height and Elevation
    int getHeightDifference(IUnit attacker, IUnit target);
    int getHeightBonus(int heightDifference);
    boolean hasHeightAdvantage(IUnit attacker, IUnit target);
    
    // Status Effects
    void applyStatusEffect(IUnit target, String effectType, int duration, int intensity);
    void removeStatusEffect(IUnit target, String effectType);
    boolean hasStatusEffect(IUnit target, String effectType);
    
    // Suppression and Overwatch
    void applySuppression(IUnit target, int duration);
    void removeSuppression(IUnit target);
    boolean canTriggerOverwatch(IUnit attacker, IUnit target, Position targetPosition);
    
    // Squad Tactics
    int getSquadSightBonus(IUnit attacker, List<IUnit> allies);
    boolean canUseSquadSight(IUnit attacker, IUnit target, List<IUnit> allies);
    int getSquadCohesionBonus(IUnit unit, List<IUnit> nearbyAllies);
    
    // Psionic Combat
    int calculatePsionicHitChance(IUnit attacker, IUnit target);
    int calculatePsionicDamage(IUnit attacker, IUnit target);
    boolean canUsePsionicAbility(IUnit attacker);
    void applyPsionicEffect(IUnit target, String effectType, int duration);
    
    // Environmental Effects
    void applyEnvironmentalHazard(IUnit target, Position position, String hazardType);
    int getEnvironmentalDamage(String hazardType, Position position);
    boolean isPositionHazardous(Position position);
    
    // Combat State Management
    void startCombat();
    void endCombat();
    boolean isCombatActive();
    void processCombatTurn();
    
    // Unit State Validation
    boolean canUnitAttack(IUnit unit);
    boolean canUnitMove(IUnit unit);
    boolean canUnitUseAbility(IUnit unit, String abilityName);
    boolean isUnitValidForCombat(IUnit unit);
    
    // Combat Statistics
    int getTotalDamageDealt();
    int getTotalDamageTaken();
    int getTotalHits();
    int getTotalMisses();
    int getTotalCriticalHits();
    void resetCombatStatistics();
    
    // Advanced Combat Features
    boolean canPerformReactionShot(IUnit unit);
    void triggerReactionShot(IUnit unit, IUnit target);
    boolean canPerformCounterAttack(IUnit unit);
    void performCounterAttack(IUnit unit, IUnit attacker);
    
    // Combat AI
    void processAICombatTurn(List<IUnit> aiUnits, List<IUnit> playerUnits);
    Position calculateOptimalPosition(IUnit unit, List<IUnit> enemies, List<IUnit> allies);
    IUnit selectBestTarget(IUnit attacker, List<IUnit> potentialTargets);
    
    // Mission-Specific Combat
    void setMissionType(String missionType);
    String getMissionType();
    void applyMissionModifiers();
    boolean isMissionObjectiveComplete();
    void checkMissionFailureConditions();
}
