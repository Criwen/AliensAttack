package com.aliensattack.field;

import com.aliensattack.core.model.Position;
import com.aliensattack.core.model.Unit;
import com.aliensattack.core.model.Tile;
import com.aliensattack.core.model.CoverObject;
import com.aliensattack.core.enums.CoverType;
import com.aliensattack.core.enums.UnitType;

import lombok.Getter;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Optimized tactical field with enhanced performance and memory efficiency
 * Uses concurrent collections and optimized algorithms for better scalability
 * Оптимизированная реализация тактического поля
 */
@Getter
public class OptimizedTacticalField implements ITacticalField {
    private final int width;
    private final int height;
    private final Tile[][] grid;
    private final Map<Integer, Unit> unitMap; // Position hash -> Unit
    private final Map<Integer, CoverObject> coverMap; // Position hash -> CoverObject
    private final Set<Integer> occupiedPositions; // Fast lookup for occupied tiles
    
    public OptimizedTacticalField(int width, int height) {
        this.width = width;
        this.height = height;
        this.grid = new Tile[width][height];
        this.unitMap = new ConcurrentHashMap<>();
        this.coverMap = new ConcurrentHashMap<>();
        this.occupiedPositions = ConcurrentHashMap.newKeySet();
        
        initializeGrid();
    }
    
    private void initializeGrid() {
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                grid[x][y] = new Tile(x, y);
            }
        }
    }
    
    private int positionHash(int x, int y) {
        int hash = x * 10000 + y; // Efficient hash for 2D coordinates
        System.out.println("DEBUG: positionHash(" + x + ", " + y + ") = " + hash);
        return hash;
    }
    
    public void addUnit(Unit unit, int x, int y) {
        System.out.println("DEBUG: Adding unit " + unit.getName() + " at (" + x + ", " + y + ")");
        
        if (!isValidPosition(x, y)) {
            System.out.println("DEBUG: Invalid position (" + x + ", " + y + ")");
            return;
        }
        
        if (isOccupied(x, y)) {
            System.out.println("DEBUG: Position (" + x + ", " + y + ") is occupied");
            return;
        }
        
        int hash = positionHash(x, y);
        unit.setPosition(x, y);
        unitMap.put(hash, unit);
        occupiedPositions.add(hash);
        grid[x][y].setUnit(unit);
        
        System.out.println("DEBUG: Unit " + unit.getName() + " added successfully at (" + x + ", " + y + ")");
    }
    
    public void addCoverObject(CoverObject cover, int x, int y) {
        if (!isValidPosition(x, y)) {
            return;
        }
        
        int hash = positionHash(x, y);
        cover.setPosition(x, y);
        coverMap.put(hash, cover);
        grid[x][y].setCoverObject(cover);
    }
    
    public boolean isValidPosition(int x, int y) {
        return x >= 0 && x < width && y >= 0 && y < height;
    }
    
    public boolean isOccupied(int x, int y) {
        return occupiedPositions.contains(positionHash(x, y));
    }
    
    public List<Position> getValidMovesOptimized(Unit unit, int movementRange) {
        List<Position> validMoves = new ArrayList<>();
        Position currentPos = unit.getPosition();
        
        // Use diamond pattern for more efficient range calculation
        for (int dx = -movementRange; dx <= movementRange; dx++) {
            for (int dy = -movementRange; dy <= movementRange; dy++) {
                if (Math.abs(dx) + Math.abs(dy) <= movementRange) {
                    int newX = currentPos.getX() + dx;
                    int newY = currentPos.getY() + dy;
                    
                    if (isValidPosition(newX, newY) && !isOccupied(newX, newY)) {
                        validMoves.add(new Position(newX, newY));
                    }
                }
            }
        }
        
        return validMoves;
    }
    
    public boolean moveUnitOptimized(Unit unit, int newX, int newY) {
        System.out.println("DEBUG: moveUnitOptimized called for " + unit.getName() + " to (" + newX + ", " + newY + ")");
        
        if (!isValidPosition(newX, newY)) {
            System.out.println("DEBUG: Invalid position (" + newX + ", " + newY + ")");
            return false;
        }
        
        Position oldPos = unit.getPosition();
        System.out.println("DEBUG: Old position: " + oldPos);
        
        // Проверяем, что новая позиция не занята другим юнитом
        Unit unitAtNewPos = getUnitAt(newX, newY);
        if (unitAtNewPos != null && unitAtNewPos != unit) {
            System.out.println("DEBUG: Position (" + newX + ", " + newY + ") is occupied by " + unitAtNewPos.getName());
            return false;
        }
        
        int oldHash = positionHash(oldPos.getX(), oldPos.getY());
        int newHash = positionHash(newX, newY);
        
        // Update maps atomically
        unitMap.remove(oldHash);
        unitMap.put(newHash, unit);
        occupiedPositions.remove(oldHash);
        occupiedPositions.add(newHash);
        
        // Update grid
        grid[oldPos.getX()][oldPos.getY()].setUnit(null);
        unit.setPosition(newX, newY);
        grid[newX][newY].setUnit(unit);
        
        System.out.println("DEBUG: Unit moved successfully to (" + newX + ", " + newY + ")");
        return true;
    }
    
    public List<Unit> getUnitsInRangeOptimized(Position position, int range) {
        List<Unit> unitsInRange = new ArrayList<>();
        
        // Use diamond pattern for efficient range checking
        for (int dx = -range; dx <= range; dx++) {
            for (int dy = -range; dy <= range; dy++) {
                if (Math.abs(dx) + Math.abs(dy) <= range) {
                    int x = position.getX() + dx;
                    int y = position.getY() + dy;
                    
                    if (isValidPosition(x, y)) {
                        Unit unit = unitMap.get(positionHash(x, y));
                        if (unit != null) {
                            unitsInRange.add(unit);
                        }
                    }
                }
            }
        }
        
        return unitsInRange;
    }
    
    public CoverType getCoverTypeAt(int x, int y) {
        if (!isValidPosition(x, y)) return CoverType.NONE;
        
        CoverObject cover = coverMap.get(positionHash(x, y));
        return cover != null ? cover.getCoverType() : CoverType.NONE;
    }
    
    public Tile getTile(int x, int y) {
        if (!isValidPosition(x, y)) return null;
        return grid[x][y];
    }
    
    public Unit getUnitAt(int x, int y) {
        int hash = positionHash(x, y);
        Unit unit = unitMap.get(hash);
        System.out.println("DEBUG: getUnitAt(" + x + ", " + y + ") hash=" + hash + " -> " + (unit != null ? unit.getName() : "null"));
        return unit;
    }
    
    public CoverObject getCoverAt(int x, int y) {
        return coverMap.get(positionHash(x, y));
    }
    
    public List<Unit> getAllUnits() {
        List<Unit> units = new ArrayList<>(unitMap.values());
        System.out.println("DEBUG: getAllUnits() returned " + units.size() + " units");
        for (Unit unit : units) {
            System.out.println("DEBUG: - " + unit.getName() + " at " + unit.getPosition());
        }
        return units;
    }
    
    public List<CoverObject> getAllCoverObjects() {
        return new ArrayList<>(coverMap.values());
    }
    
    public void clearField() {
        unitMap.clear();
        coverMap.clear();
        occupiedPositions.clear();
        
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                grid[x][y].setUnit(null);
                grid[x][y].setCoverObject(null);
            }
        }
    }
    
    public int getUnitCount() {
        return unitMap.size();
    }
    
    public int getCoverObjectCount() {
        return coverMap.size();
    }
    
    public boolean hasUnits() {
        return !unitMap.isEmpty();
    }
    
    public boolean hasCoverObjects() {
        return !coverMap.isEmpty();
    }
    
    /**
     * Проверяет видимость между двумя позициями
     * @param from начальная позиция
     * @param to конечная позиция
     * @param viewRange максимальный диапазон видимости
     * @return true если позиция видима
     */
    public boolean isPositionVisible(Position from, Position to, int viewRange) {
        if (from == null || to == null) return false;
        
        // Проверяем расстояние
        double distance = calculateDistance(from, to);
        if (distance > viewRange) return false;
        
        // Проверяем препятствия (простая линия видимости)
        return hasLineOfSight(from, to);
    }
    
    /**
     * Возвращает всех видимых юнитов для данного юнита
     */
    public List<Unit> getVisibleUnits(Unit observer) {
        if (observer == null || observer.getPosition() == null) {
            return new ArrayList<>();
        }
        
        List<Unit> visibleUnits = new ArrayList<>();
        Position observerPos = observer.getPosition();
        
        for (Unit unit : getAllUnits()) {
            if (unit != observer && unit.isAlive() && unit.getPosition() != null) {
                if (isPositionVisible(observerPos, unit.getPosition(), observer.getViewRange())) {
                    visibleUnits.add(unit);
                }
            }
        }
        
        return visibleUnits;
    }
    
    /**
     * Возвращает все видимые позиции для данного юнита
     */
    public List<Position> getVisiblePositions(Unit observer) {
        if (observer == null || observer.getPosition() == null) {
            return new ArrayList<>();
        }
        
        List<Position> visiblePositions = new ArrayList<>();
        Position observerPos = observer.getPosition();
        int viewRange = observer.getViewRange();
        
        // Проверяем все позиции в квадрате видимости
        int startX = Math.max(0, observerPos.getX() - viewRange);
        int endX = Math.min(width - 1, observerPos.getX() + viewRange);
        int startY = Math.max(0, observerPos.getY() - viewRange);
        int endY = Math.min(height - 1, observerPos.getY() + viewRange);
        
        for (int x = startX; x <= endX; x++) {
            for (int y = startY; y <= endY; y++) {
                Position pos = new Position(x, y);
                if (isPositionVisible(observerPos, pos, viewRange)) {
                    visiblePositions.add(pos);
                }
            }
        }
        
        return visiblePositions;
    }
    

    
    /**
     * Проверяет прямую линию видимости между двумя позициями
     * Использует алгоритм Bresenham для проверки препятствий
     */
    @Override
    public boolean hasLineOfSight(Position from, Position to) {
        int x0 = from.getX();
        int y0 = from.getY();
        int x1 = to.getX();
        int y1 = to.getY();
        
        int dx = Math.abs(x1 - x0);
        int dy = Math.abs(y1 - y0);
        int x = x0;
        int y = y0;
        
        int x_inc = (x1 > x0) ? 1 : -1;
        int y_inc = (y1 > y0) ? 1 : -1;
        int error = dx - dy;
        
        dx *= 2;
        dy *= 2;
        
        for (int i = 0; i <= Math.max(Math.abs(x1 - x0), Math.abs(y1 - y0)); i++) {
            // Проверяем препятствия на текущей позиции (кроме начальной и конечной)
            if (!(x == x0 && y == y0) && !(x == x1 && y == y1)) {
                if (isValidPosition(x, y)) {
                    Tile tile = grid[x][y];
                    CoverObject cover = tile.getCoverObject();
                    if (cover != null && cover.getCoverType() == CoverType.FULL_COVER) {
                        return false; // Полное укрытие блокирует видимость
                    }
                }
            }
            
            if (x == x1 && y == y1) break;
            
            if (error > 0) {
                x += x_inc;
                error -= dy;
            } else {
                y += y_inc;
                error += dx;
            }
        }
        
        return true; // Линия видимости свободна
    }
    
    /**
     * Возвращает видимых врагов для данного юнита
     */
    public List<Unit> getVisibleEnemies(Unit observer) {
        return getVisibleUnits(observer).stream()
                .filter(unit -> isEnemy(observer, unit))
                .toList();
    }
    
    /**
     * Проверяет являются ли два юнита врагами
     */
    private boolean isEnemy(Unit unit1, Unit unit2) {
        if (unit1.getUnitType() == UnitType.SOLDIER && unit2.getUnitType() == UnitType.ALIEN) return true;
        if (unit1.getUnitType() == UnitType.ALIEN && unit2.getUnitType() == UnitType.SOLDIER) return true;
        return false;
    }
    
    // Реализация методов интерфейса ITacticalField
    
    @Override
    public boolean isValidPosition(Position position) {
        return isValidPosition(position.getX(), position.getY());
    }
    
    @Override
    public void addUnit(Unit unit) {
        if (unit.getPosition() != null) {
            System.out.println("DEBUG: Adding unit " + unit.getName() + " at position " + unit.getPosition());
            addUnit(unit, unit.getPosition().getX(), unit.getPosition().getY());
        } else {
            System.out.println("DEBUG: Unit " + unit.getName() + " has no position!");
        }
    }
    
    @Override
    public void removeUnit(Unit unit) {
        if (unit.getPosition() != null) {
            int hash = positionHash(unit.getPosition().getX(), unit.getPosition().getY());
            unitMap.remove(hash);
            occupiedPositions.remove(hash);
            grid[unit.getPosition().getX()][unit.getPosition().getY()].setUnit(null);
        }
    }
    
    @Override
    public Unit getUnitAt(Position position) {
        return getUnitAt(position.getX(), position.getY());
    }
    
    @Override
    public List<Unit> getPlayerUnits() {
        return getAllUnits().stream()
            .filter(unit -> unit.getUnitType() == UnitType.SOLDIER)
            .collect(java.util.stream.Collectors.toList());
    }
    
    @Override
    public List<Unit> getEnemyUnits() {
        return getAllUnits().stream()
            .filter(unit -> unit.getUnitType() == UnitType.ALIEN)
            .collect(java.util.stream.Collectors.toList());
    }
    
    @Override
    public void addCoverObject(CoverObject cover, Position position) {
        addCoverObject(cover, position.getX(), position.getY());
    }
    
    @Override
    public CoverObject getCoverObject(int x, int y) {
        if (isValidPosition(x, y)) {
            return coverMap.get(positionHash(x, y));
        }
        return null;
    }
    
    @Override
    public CoverObject getCoverObject(Position position) {
        return getCoverObject(position.getX(), position.getY());
    }
    
    @Override
    public double calculateDistance(Position from, Position to) {
        return Math.sqrt(Math.pow(from.getX() - to.getX(), 2) + Math.pow(from.getY() - to.getY(), 2));
    }
    
    @Override
    public Map<Position, Unit> getUnitPositions() {
        Map<Position, Unit> positions = new HashMap<>();
        for (Unit unit : getAllUnits()) {
            if (unit.getPosition() != null) {
                positions.put(unit.getPosition(), unit);
            }
        }
        return positions;
    }
    
    @Override
    public Map<Position, CoverObject> getCoverPositions() {
        Map<Position, CoverObject> positions = new HashMap<>();
        for (CoverObject cover : getAllCoverObjects()) {
            if (cover.getPosition() != null) {
                positions.put(cover.getPosition(), cover);
            }
        }
        return positions;
    }
    
    // Additional getter methods for compatibility
    public int getWidth() {
        return width;
    }
    
    public int getHeight() {
        return height;
    }
} 