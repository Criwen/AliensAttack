package com.aliensattack.combat;

import com.aliensattack.core.model.Position;
import com.aliensattack.core.model.Unit;
import com.aliensattack.field.ITacticalField;

import java.util.List;
import java.util.Map;

/**
 * Интерфейс для менеджера боевых действий
 * Объединяет функционал базовой и оптимизированной версий
 */
public interface ICombatManager {
    
    // Основные операции боя
    void startTurn();
    void endTurn();
    boolean isPlayerTurn();
    int getCurrentTurn();
    
    // Управление юнитами
    void addPlayerUnit(Unit unit);
    void addEnemyUnit(Unit unit);
    void removeUnit(Unit unit);
    List<Unit> getPlayerUnits();
    List<Unit> getEnemyUnits();
    List<Unit> getAllUnits();
    
    // Боевые действия
    CombatResult performAttack(Unit attacker, Unit target);
    boolean canAttack(Unit attacker, Unit target);
    List<Unit> getValidTargets(Unit attacker);
    
    // Движение
    boolean canMoveTo(Unit unit, Position target);
    boolean moveUnit(Unit unit, Position target);
    List<Position> getValidMovePositions(Unit unit);
    
    // Проверки состояния
    boolean isGameOver();
    boolean hasPlayerWon();
    boolean hasEnemyWon();
    boolean allPlayerUnitsOutOfActionPoints();
    
    // Получение данных
    ITacticalField getField();
    Map<String, Unit> getPlayerUnitsMap();
    Map<String, Unit> getEnemyUnitsMap();
    
    // Оптимизированные методы (из OptimizedCombatManager)
    List<Unit> getPlayerUnitsOptimized();
    List<Unit> getEnemyUnitsOptimized();
    boolean isUnitAlive(Unit unit);
    void resetUnitActionPoints(Unit unit);
} 