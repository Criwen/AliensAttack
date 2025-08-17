package com.aliensattack.core.ai;

import com.aliensattack.core.model.Position;
import com.aliensattack.core.model.Unit;
import com.aliensattack.core.model.TerrainObject;
import com.aliensattack.field.ITacticalField;
import com.aliensattack.core.enums.CoverType;
import com.aliensattack.core.enums.TerrainType;
import com.aliensattack.core.config.GameConfig;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;

import java.util.*;

/**
 * Advanced Cover Detection System for AI tactical positioning
 * Implements sophisticated terrain analysis, elevation calculations, 
 * line of sight analysis, and environmental hazard detection
 */
@Getter
@Setter
@Log4j2
public class CoverDetectionSystem {
    
    private ITacticalField tacticalField;
    private Map<Position, CoverType> coverMap;
    private Map<Position, Double> coverValues;
    private Map<Position, Integer> elevationMap;
    private Map<Position, List<TerrainObject>> terrainObjectsMap;
    private Map<Position, Double> visibilityMap;
    private Map<Position, List<String>> hazardMap;
    
    // Advanced analysis caches
    private Map<String, Double> analysisCache;
    private long lastAnalysisTime;
    private static final long ANALYSIS_CACHE_DURATION = 5000; // 5 seconds
    
    public CoverDetectionSystem(ITacticalField tacticalField) {
        this.tacticalField = tacticalField;
        this.coverMap = new HashMap<>();
        this.coverValues = new HashMap<>();
        this.elevationMap = new HashMap<>();
        this.terrainObjectsMap = new HashMap<>();
        this.visibilityMap = new HashMap<>();
        this.hazardMap = new HashMap<>();
        this.analysisCache = new HashMap<>();
        this.lastAnalysisTime = System.currentTimeMillis();
        
        initializeAdvancedCoverMap();
    }
    
    /**
     * Initialize advanced cover map with elevation and terrain analysis
     */
    private void initializeAdvancedCoverMap() {
        if (tacticalField == null) {
            return;
        }
        
        int width = tacticalField.getWidth();
        int height = tacticalField.getHeight();
        
        log.info("Initializing advanced cover map for {}x{} field", width, height);
        
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                Position pos = new Position(x, y);
                
                // Analyze elevation
                int elevation = analyzeElevation(pos);
                elevationMap.put(pos, elevation);
                
                // Analyze terrain objects
                List<TerrainObject> terrainObjects = analyzeTerrainObjects(pos);
                terrainObjectsMap.put(pos, terrainObjects);
                
                // Analyze cover with elevation consideration
                CoverType coverType = analyzeAdvancedCoverAtPosition(pos);
                coverMap.put(pos, coverType);
                coverValues.put(pos, calculateAdvancedCoverValue(pos, coverType));
                
                // Analyze visibility
                double visibility = analyzeVisibility(pos);
                visibilityMap.put(pos, visibility);
                
                // Analyze hazards
                List<String> hazards = analyzeHazards(pos);
                hazardMap.put(pos, hazards);
            }
        }
        
        log.info("Advanced cover map initialized with elevation, terrain, and hazard analysis");
    }
    
    /**
     * Analyze elevation at a specific position
     */
    private int analyzeElevation(Position position) {
        // TODO: Implement sophisticated elevation generation
        // - Load elevation patterns from configuration
        // - Generate natural elevation variations
        // - Consider building heights and underground levels
        // - Implement elevation-based movement penalties
        
        // For now, generate varied elevation based on position
        int baseElevation = 0;
        
        // Generate hills and valleys
        double hillFrequency = GameConfig.getTerrainHillFrequency();
        double valleyFrequency = GameConfig.getTerrainValleyFrequency();
        
        double hillValue = Math.sin(position.getX() * hillFrequency) * 
                          Math.cos(position.getY() * hillFrequency);
        double valleyValue = Math.sin(position.getX() * valleyFrequency) * 
                           Math.cos(position.getY() * valleyFrequency);
        
        int elevation = (int) ((hillValue + valleyValue) * GameConfig.getTerrainElevationScale());
        
        // Ensure elevation is within bounds
        elevation = Math.max(GameConfig.getTerrainElevationMin(), 
                           Math.min(GameConfig.getTerrainElevationMax(), elevation));
        
        return elevation;
    }
    
    /**
     * Analyze terrain objects at a specific position
     */
    private List<TerrainObject> analyzeTerrainObjects(Position position) {
        List<TerrainObject> objects = new ArrayList<>();
        
        // TODO: Implement terrain object generation system
        // - Load terrain object templates from configuration
        // - Generate natural terrain features
        // - Place interactive objects strategically
        // - Consider mission-specific terrain requirements
        
        // For now, generate basic terrain objects based on position
        Random random = new Random(position.hashCode());
        double objectChance = random.nextDouble();
        
        if (objectChance < GameConfig.getTerrainObjectDensity()) {
            TerrainType terrainType = selectTerrainType(position, random);
            TerrainObject object = createTerrainObject(position, terrainType);
            if (object != null) {
                objects.add(object);
            }
        }
        
        return objects;
    }
    
    /**
     * Select appropriate terrain type based on position and context
     */
    private TerrainType selectTerrainType(Position position, Random random) {
        // TODO: Implement intelligent terrain type selection
        // - Consider surrounding terrain types
        // - Apply biome rules and transitions
        // - Consider mission objectives and tactical requirements
        // - Implement terrain type compatibility checks
        
        TerrainType[] commonTypes = {
            TerrainType.GRASS, TerrainType.CONCRETE, TerrainType.ROCK,
            TerrainType.WOOD, TerrainType.RUBBLE, TerrainType.COVER
        };
        
        return commonTypes[random.nextInt(commonTypes.length)];
    }
    
    /**
     * Create a terrain object with appropriate properties
     */
    private TerrainObject createTerrainObject(Position position, TerrainType terrainType) {
        try {
            // TODO: Implement terrain object factory system
            // - Load object properties from configuration
            // - Apply terrain type-specific modifiers
            // - Consider object health and destructibility
            // - Implement object interaction capabilities
            
            return new TerrainObject(
                "terrain_" + position.getX() + "_" + position.getY(),
                terrainType.name().toLowerCase(),
                terrainType,
                position,
                GameConfig.getTerrainObjectDefaultHealth()
            );
        } catch (Exception e) {
            log.warn("Failed to create terrain object at {}: {}", position, e.getMessage());
            return null;
        }
    }
    
    /**
     * Analyze advanced cover at a specific position with elevation consideration
     */
    private CoverType analyzeAdvancedCoverAtPosition(Position position) {
        // Check for terrain-based cover with elevation
        CoverType terrainCover = checkAdvancedTerrainCover(position);
        if (terrainCover != CoverType.NONE) {
            return terrainCover;
        }
        
        // Check for unit-based cover with elevation
        CoverType unitCover = checkAdvancedUnitCover(position);
        if (unitCover != CoverType.NONE) {
            return unitCover;
        }
        
        // Check for environmental cover with elevation
        CoverType environmentalCover = checkAdvancedEnvironmentalCover(position);
        if (environmentalCover != CoverType.NONE) {
            return environmentalCover;
        }
        
        // Check for elevation-based cover
        CoverType elevationCover = checkElevationCover(position);
        if (elevationCover != CoverType.NONE) {
            return elevationCover;
        }
        
        return CoverType.NONE;
    }
    
    /**
     * Check for advanced terrain-based cover with elevation consideration
     */
    private CoverType checkAdvancedTerrainCover(Position position) {
        List<TerrainObject> objects = terrainObjectsMap.get(position);
        if (objects == null || objects.isEmpty()) {
            return CoverType.NONE;
        }
        
        CoverType bestCover = CoverType.NONE;
        double bestCoverValue = 0.0;
        
        for (TerrainObject object : objects) {
            if (object == null || object.isDestroyed()) {
                continue;
            }
            
            CoverType objectCover = calculateTerrainObjectCover(object, position);
            double coverValue = calculateCoverValue(objectCover);
            
            if (coverValue > bestCoverValue) {
                bestCover = objectCover;
                bestCoverValue = coverValue;
            }
        }
        
        return bestCover;
    }
    
    /**
     * Calculate cover provided by a terrain object
     */
    private CoverType calculateTerrainObjectCover(TerrainObject object, Position position) {
        if (object == null || object.isDestroyed()) {
            return CoverType.NONE;
        }
        
        // TODO: Implement sophisticated terrain object cover calculation
        // - Consider object height and density
        // - Apply material-specific cover properties
        // - Consider object damage state
        // - Implement directional cover analysis
        
        // Basic cover calculation based on terrain type
        switch (object.getTerrainType()) {
            case WALL:
            case BARRIER:
            case FORCE_FIELD:
                return CoverType.FULL_COVER;
            case RUBBLE:
            case COVER:
            case VEHICLE:
                return CoverType.HALF_COVER;
            case GRASS:
            case WATER:
            case MUD:
                return CoverType.LOW_COVER;
            default:
                return CoverType.NONE;
        }
    }
    
    /**
     * Check for advanced unit-based cover with elevation consideration
     */
    private CoverType checkAdvancedUnitCover(Position position) {
        List<Unit> nearbyUnits = getUnitsInRange(position, GameConfig.getCoverUnitDetectionRange());
        
        CoverType bestCover = CoverType.NONE;
        double bestCoverValue = 0.0;
        
        for (Unit unit : nearbyUnits) {
            if (unit.isAlive() && unit.getPosition() != null) {
                CoverType unitCover = calculateUnitCover(position, unit);
                double coverValue = calculateCoverValue(unitCover);
                
                if (coverValue > bestCoverValue) {
                    bestCover = unitCover;
                    bestCoverValue = coverValue;
                }
            }
        }
        
        return bestCover;
    }
    
    /**
     * Calculate cover provided by a unit
     */
    private CoverType calculateUnitCover(Position position, Unit unit) {
        double distance = calculateDistance(position, unit.getPosition());
        int unitElevation = elevationMap.getOrDefault(unit.getPosition(), 0);
        int positionElevation = elevationMap.getOrDefault(position, 0);
        
        // TODO: Implement sophisticated unit cover calculation
        // - Consider unit size and armor type
        // - Apply unit stance and orientation
        // - Consider unit health and status effects
        // - Implement directional cover analysis
        
        if (distance <= GameConfig.getCoverUnitMaxDistance()) {
            // Check if unit is higher than position (provides better cover)
            if (unitElevation > positionElevation) {
                return CoverType.HALF_COVER;
            } else if (distance <= GameConfig.getCoverUnitCloseDistance()) {
                return CoverType.LOW_COVER;
            }
        }
        
        return CoverType.NONE;
    }
    
    /**
     * Check for advanced environmental cover with elevation consideration
     */
    private CoverType checkAdvancedEnvironmentalCover(Position position) {
        // TODO: Implement sophisticated environmental cover analysis
        // - Consider weather conditions
        // - Analyze atmospheric effects
        // - Consider time of day and lighting
        // - Implement dynamic environmental changes
        
        List<String> hazards = hazardMap.get(position);
        if (hazards != null && !hazards.isEmpty()) {
            // Some hazards provide cover (smoke, fog)
            if (hazards.contains("SMOKE") || hazards.contains("FOG")) {
                return CoverType.LOW_COVER;
            }
        }
        
        return CoverType.NONE;
    }
    
    /**
     * Check for elevation-based cover
     */
    private CoverType checkElevationCover(Position position) {
        int currentElevation = elevationMap.getOrDefault(position, 0);
        
        // Check if position is in a depression (provides cover)
        if (isInDepression(position, currentElevation)) {
            return CoverType.HALF_COVER;
        }
        
        // Check if position is on a slope (partial cover)
        if (isOnSlope(position, currentElevation)) {
            return CoverType.LOW_COVER;
        }
        
        return CoverType.NONE;
    }
    
    /**
     * Check if position is in a depression
     */
    private boolean isInDepression(Position position, int currentElevation) {
        // TODO: Implement sophisticated depression detection
        // - Analyze surrounding elevation patterns
        // - Consider depression size and depth
        // - Apply terrain-specific depression rules
        // - Implement depression quality assessment
        
        int range = GameConfig.getTerrainDepressionDetectionRange();
        int higherNeighbors = 0;
        int totalNeighbors = 0;
        
        for (int dx = -range; dx <= range; dx++) {
            for (int dy = -range; dy <= range; dy++) {
                if (dx == 0 && dy == 0) continue;
                
                Position neighbor = new Position(
                    position.getX() + dx,
                    position.getY() + dy
                );
                
                if (tacticalField.isValidPosition(neighbor.getX(), neighbor.getY())) {
                    int neighborElevation = elevationMap.getOrDefault(neighbor, 0);
                    if (neighborElevation > currentElevation) {
                        higherNeighbors++;
                    }
                    totalNeighbors++;
                }
            }
        }
        
        double depressionThreshold = GameConfig.getTerrainDepressionThreshold();
        return totalNeighbors > 0 && 
               (double) higherNeighbors / totalNeighbors >= depressionThreshold;
    }
    
    /**
     * Check if position is on a slope
     */
    private boolean isOnSlope(Position position, int currentElevation) {
        // TODO: Implement sophisticated slope detection
        // - Calculate slope gradient and direction
        // - Consider slope steepness thresholds
        // - Apply terrain-specific slope rules
        // - Implement slope-based movement penalties
        
        int range = GameConfig.getTerrainSlopeDetectionRange();
        double maxElevationDiff = 0;
        
        for (int dx = -range; dx <= range; dx++) {
            for (int dy = -range; dy <= range; dy++) {
                if (dx == 0 && dy == 0) continue;
                
                Position neighbor = new Position(
                    position.getX() + dx,
                    position.getY() + dy
                );
                
                if (tacticalField.isValidPosition(neighbor.getX(), neighbor.getY())) {
                    int neighborElevation = elevationMap.getOrDefault(neighbor, 0);
                    double elevationDiff = Math.abs(neighborElevation - currentElevation);
                    maxElevationDiff = Math.max(maxElevationDiff, elevationDiff);
                }
            }
        }
        
        double slopeThreshold = GameConfig.getTerrainSlopeThreshold();
        return maxElevationDiff >= slopeThreshold;
    }
    
    /**
     * Analyze visibility at a specific position
     */
    private double analyzeVisibility(Position position) {
        // TODO: Implement sophisticated visibility analysis
        // - Consider elevation differences
        // - Analyze line of sight to key positions
        // - Consider environmental factors
        // - Implement dynamic visibility changes
        
        double baseVisibility = 1.0;
        
        // Reduce visibility based on elevation
        int elevation = elevationMap.getOrDefault(position, 0);
        double elevationModifier = 1.0 - (Math.abs(elevation) * GameConfig.getVisibilityElevationPenalty());
        baseVisibility *= Math.max(0.1, elevationModifier);
        
        // Reduce visibility based on hazards
        List<String> hazards = hazardMap.get(position);
        if (hazards != null) {
            for (String hazard : hazards) {
                baseVisibility *= GameConfig.getVisibilityHazardModifier(hazard);
            }
        }
        
        return Math.max(0.0, Math.min(1.0, baseVisibility));
    }
    
    /**
     * Analyze hazards at a specific position
     */
    private List<String> analyzeHazards(Position position) {
        List<String> hazards = new ArrayList<>();
        
        // TODO: Implement sophisticated hazard analysis
        // - Consider terrain type hazards
        // - Analyze environmental conditions
        // - Consider unit proximity hazards
        // - Implement hazard interaction chains
        
        // Check terrain objects for hazards
        List<TerrainObject> objects = terrainObjectsMap.get(position);
        if (objects != null) {
            for (TerrainObject object : objects) {
                if (object != null && object.isHazardous()) {
                    addHazardsFromObject(object, hazards);
                }
            }
        }
        
        // Check elevation-based hazards
        int elevation = elevationMap.getOrDefault(position, 0);
        if (elevation < GameConfig.getTerrainHazardLowElevation()) {
            hazards.add("FLOODING");
        }
        if (elevation > GameConfig.getTerrainHazardHighElevation()) {
            hazards.add("WIND");
        }
        
        return hazards;
    }
    
    /**
     * Add hazards from a terrain object
     */
    private void addHazardsFromObject(TerrainObject object, List<String> hazards) {
        if (object.isBurning()) hazards.add("FIRE");
        if (object.isAcidic()) hazards.add("ACID");
        if (object.isPoisonous()) hazards.add("POISON");
        if (object.isRadioactive()) hazards.add("RADIATION");
        if (object.isElectrified()) hazards.add("ELECTRIC");
        if (object.isFrozen()) hazards.add("ICE");
        if (object.isCorrosive()) hazards.add("CORROSION");
        if (object.isToxic()) hazards.add("TOXIC");
        if (object.isExplosive()) hazards.add("EXPLOSIVE");
    }
    
    /**
     * Calculate advanced cover value with multiple factors
     */
    private double calculateAdvancedCoverValue(Position position, CoverType coverType) {
        double baseValue = calculateCoverValue(coverType);
        
        // Apply elevation modifier
        int elevation = elevationMap.getOrDefault(position, 0);
        double elevationModifier = 1.0 + (elevation * GameConfig.getCoverElevationBonus());
        baseValue *= elevationModifier;
        
        // Apply terrain object modifier
        List<TerrainObject> objects = terrainObjectsMap.get(position);
        if (objects != null && !objects.isEmpty()) {
            double objectModifier = 1.0 + (objects.size() * GameConfig.getCoverObjectBonus());
            baseValue *= objectModifier;
        }
        
        // Apply hazard modifier (some hazards provide cover)
        List<String> hazards = hazardMap.get(position);
        if (hazards != null && !hazards.isEmpty()) {
            double hazardModifier = 1.0 + (hazards.size() * GameConfig.getCoverHazardBonus());
            baseValue *= hazardModifier;
        }
        
        return Math.max(0.0, Math.min(1.0, baseValue));
    }
    
    /**
     * Calculate cover value (0.0 to 1.0)
     */
    private double calculateCoverValue(CoverType coverType) {
        return switch (coverType) {
            case FULL_COVER -> 1.0;
            case HALF_COVER -> 0.5;
            case LOW_COVER -> 0.25;
            case NONE -> 0.0;
            default -> 0.0;
        };
    }
    
    /**
     * Get elevation at position
     */
    public int getElevationAt(Position position) {
        return elevationMap.getOrDefault(position, 0);
    }
    
    /**
     * Get terrain objects at position
     */
    public List<TerrainObject> getTerrainObjectsAt(Position position) {
        return terrainObjectsMap.getOrDefault(position, new ArrayList<>());
    }
    
    /**
     * Get visibility at position
     */
    public double getVisibilityAt(Position position) {
        return visibilityMap.getOrDefault(position, 1.0);
    }
    
    /**
     * Get hazards at position
     */
    public List<String> getHazardsAt(Position position) {
        return hazardMap.getOrDefault(position, new ArrayList<>());
    }
    
    /**
     * Check if position has elevation advantage
     */
    public boolean hasElevationAdvantage(Position position, Position targetPosition) {
        int positionElevation = getElevationAt(position);
        int targetElevation = getElevationAt(targetPosition);
        return positionElevation > targetElevation;
    }
    
    /**
     * Get best cover positions with elevation consideration
     */
    public List<Position> getBestCoverPositionsWithElevation(Position center, int range) {
        List<Position> coverPositions = new ArrayList<>();
        
        for (int x = Math.max(0, center.getX() - range); 
             x <= Math.min(tacticalField.getWidth() - 1, center.getX() + range); x++) {
            for (int y = Math.max(0, center.getY() - range); 
                 y <= Math.min(tacticalField.getHeight() - 1, center.getY() + range); y++) {
                
                Position pos = new Position(x, y);
                double distance = calculateDistance(center, pos);
                
                if (distance <= range) {
                    double coverValue = getCoverValueAt(pos);
                    int elevation = getElevationAt(pos);
                    
                    // Prioritize positions with good cover and elevation advantage
                    if (coverValue > GameConfig.getCoverMinimumValue() || 
                        elevation > getElevationAt(center)) {
                        coverPositions.add(pos);
                    }
                }
            }
        }
        
        // Sort by combined cover and elevation score
        coverPositions.sort((p1, p2) -> {
            double score1 = getCoverValueAt(p1) + (getElevationAt(p1) * GameConfig.getCoverElevationWeight());
            double score2 = getCoverValueAt(p2) + (getElevationAt(p2) * GameConfig.getCoverElevationWeight());
            return Double.compare(score2, score1);
        });
        
        return coverPositions;
    }
    
    /**
     * Get tactical analysis for a position
     */
    public Map<String, Object> getTacticalAnalysis(Position position) {
        Map<String, Object> analysis = new HashMap<>();
        
        analysis.put("cover", getCoverAt(position));
        analysis.put("coverValue", getCoverValueAt(position));
        analysis.put("elevation", getElevationAt(position));
        analysis.put("visibility", getVisibilityAt(position));
        analysis.put("hazards", getHazardsAt(position));
        analysis.put("terrainObjects", getTerrainObjectsAt(position));
        
        // Calculate tactical score
        double tacticalScore = calculateTacticalScore(position);
        analysis.put("tacticalScore", tacticalScore);
        
        return analysis;
    }
    
    /**
     * Calculate tactical score for a position
     */
    private double calculateTacticalScore(Position position) {
        double score = 0.0;
        
        // Cover score
        score += getCoverValueAt(position) * GameConfig.getTacticalCoverWeight();
        
        // Elevation score
        int elevation = getElevationAt(position);
        score += elevation * GameConfig.getTacticalElevationWeight();
        
        // Visibility score
        score += getVisibilityAt(position) * GameConfig.getTacticalVisibilityWeight();
        
        // Hazard penalty
        List<String> hazards = getHazardsAt(position);
        score -= hazards.size() * GameConfig.getTacticalHazardPenalty();
        
        return Math.max(0.0, score);
    }
    
    /**
     * Get cover type at position
     */
    public CoverType getCoverAt(Position position) {
        return coverMap.getOrDefault(position, CoverType.NONE);
    }
    
    /**
     * Get cover value at position (0.0 to 1.0)
     */
    public double getCoverValueAt(Position position) {
        return coverValues.getOrDefault(position, 0.0);
    }
    
    /**
     * Check if position has any cover
     */
    public boolean hasCover(Position position) {
        return getCoverAt(position) != CoverType.NONE;
    }
    
    /**
     * Check if position has good cover (HALF_COVER or FULL_COVER)
     */
    public boolean hasGoodCover(Position position) {
        CoverType cover = getCoverAt(position);
        return cover == CoverType.HALF_COVER || cover == CoverType.FULL_COVER;
    }
    
    /**
     * Get best cover positions within range
     */
    public List<Position> getBestCoverPositions(Position center, int range) {
        return getBestCoverPositionsWithElevation(center, range);
    }
    
    /**
     * Get units within range of a position
     */
    private List<Unit> getUnitsInRange(Position center, int range) {
        List<Unit> unitsInRange = new ArrayList<>();
        
        if (tacticalField == null) {
            return unitsInRange;
        }
        
        List<Unit> allUnits = tacticalField.getAllUnits();
        
        for (Unit unit : allUnits) {
            if (unit.getPosition() != null) {
                double distance = calculateDistance(center, unit.getPosition());
                if (distance <= range) {
                    unitsInRange.add(unit);
                }
            }
        }
        
        return unitsInRange;
    }
    
    /**
     * Calculate distance between two positions
     */
    private double calculateDistance(Position pos1, Position pos2) {
        int dx = pos1.getX() - pos2.getX();
        int dy = pos1.getY() - pos2.getY();
        return Math.sqrt(dx * dx + dy * dy);
    }
    
    /**
     * Update cover map for a specific position
     */
    public void updateCoverAt(Position position) {
        CoverType newCover = analyzeAdvancedCoverAtPosition(position);
        coverMap.put(position, newCover);
        coverValues.put(position, calculateAdvancedCoverValue(position, newCover));
        
        // Update related maps
        updateElevationAt(position);
        updateTerrainObjectsAt(position);
        updateVisibilityAt(position);
        updateHazardsAt(position);
        
        log.debug("Updated advanced cover at {}: {} (value: {})", position, newCover, getCoverValueAt(position));
    }
    
    /**
     * Update elevation at position
     */
    private void updateElevationAt(Position position) {
        int newElevation = analyzeElevation(position);
        elevationMap.put(position, newElevation);
    }
    
    /**
     * Update terrain objects at position
     */
    private void updateTerrainObjectsAt(Position position) {
        List<TerrainObject> newObjects = analyzeTerrainObjects(position);
        terrainObjectsMap.put(position, newObjects);
    }
    
    /**
     * Update visibility at position
     */
    private void updateVisibilityAt(Position position) {
        double newVisibility = analyzeVisibility(position);
        visibilityMap.put(position, newVisibility);
    }
    
    /**
     * Update hazards at position
     */
    private void updateHazardsAt(Position position) {
        List<String> newHazards = analyzeHazards(position);
        hazardMap.put(position, newHazards);
    }
    
    /**
     * Refresh entire cover map
     */
    public void refreshCoverMap() {
        coverMap.clear();
        coverValues.clear();
        elevationMap.clear();
        terrainObjectsMap.clear();
        visibilityMap.clear();
        hazardMap.clear();
        analysisCache.clear();
        
        initializeAdvancedCoverMap();
        lastAnalysisTime = System.currentTimeMillis();
        log.info("Advanced cover map refreshed with all analysis data");
    }
    
    /**
     * Get system performance statistics
     */
    public Map<String, Object> getPerformanceStats() {
        Map<String, Object> stats = new HashMap<>();
        
        stats.put("coverMapSize", coverMap.size());
        stats.put("elevationMapSize", elevationMap.size());
        stats.put("terrainObjectsMapSize", terrainObjectsMap.size());
        stats.put("visibilityMapSize", visibilityMap.size());
        stats.put("hazardMapSize", hazardMap.size());
        stats.put("analysisCacheSize", analysisCache.size());
        stats.put("lastAnalysisTime", lastAnalysisTime);
        stats.put("cacheAge", System.currentTimeMillis() - lastAnalysisTime);
        
        return stats;
    }
}
