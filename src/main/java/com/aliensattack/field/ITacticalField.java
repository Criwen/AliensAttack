package com.aliensattack.field;

import com.aliensattack.core.model.Position;
import com.aliensattack.core.model.Unit;
import com.aliensattack.core.model.CoverObject;

import java.util.List;
import java.util.Map;

/**
 * Интерфейс для тактического поля боя
 * Объединяет функционал базовой и оптимизированной версий
 */
public interface ITacticalField {
    
    // Основные операции поля
    int getWidth();
    int getHeight();
    boolean isValidPosition(int x, int y);
    boolean isValidPosition(Position position);
    
    // Управление юнитами
    void addUnit(Unit unit);
    void removeUnit(Unit unit);
    Unit getUnitAt(int x, int y);
    Unit getUnitAt(Position position);
    List<Unit> getAllUnits();
    List<Unit> getPlayerUnits();
    List<Unit> getEnemyUnits();
    
    // Управление укрытиями
    void addCoverObject(CoverObject cover, int x, int y);
    void addCoverObject(CoverObject cover, Position position);
    CoverObject getCoverObject(int x, int y);
    CoverObject getCoverObject(Position position);
    
    // Система видимости (из оптимизированной версии)
    boolean isPositionVisible(Position from, Position to, int viewRange);
    List<Unit> getVisibleUnits(Unit observer);
    List<Unit> getVisibleEnemies(Unit observer);
    List<Position> getVisiblePositions(Unit observer);
    
    // Утилиты
    double calculateDistance(Position from, Position to);
    boolean hasLineOfSight(Position from, Position to);
    
    // Получение данных поля
    Map<Position, Unit> getUnitPositions();
    Map<Position, CoverObject> getCoverPositions();
} 