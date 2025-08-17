package com.aliensattack.core.ai;

import com.aliensattack.core.model.Position;
import com.aliensattack.core.model.Unit;
import com.aliensattack.field.ITacticalField;
import com.aliensattack.core.enums.UnitType;
import com.aliensattack.core.config.GameConfig;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Squad Coordination System for AI tactical positioning
 * Implements multi-unit AI tactics and coordination, allowing enemy units
 * to work together as cohesive squads rather than individual units
 */
@Getter
@Setter
@Log4j2
public class SquadCoordinationSystem {
    
    private ITacticalField tacticalField;
    private CoverDetectionSystem coverSystem;
    private Map<String, Squad> squads;
    private Map<String, String> unitToSquadMap;
    private Map<String, SquadTactic> activeTactics;
    
    // Squad coordination caches
    private Map<String, SquadCoordinationData> coordinationCache;
    private long lastCoordinationUpdate;
    private static final long COORDINATION_CACHE_DURATION = 3000; // 3 seconds
    
    public SquadCoordinationSystem(ITacticalField tacticalField, CoverDetectionSystem coverSystem) {
        this.tacticalField = tacticalField;
        this.coverSystem = coverSystem;
        this.squads = new ConcurrentHashMap<>();
        this.unitToSquadMap = new ConcurrentHashMap<>();
        this.activeTactics = new ConcurrentHashMap<>();
        this.coordinationCache = new ConcurrentHashMap<>();
        this.lastCoordinationUpdate = System.currentTimeMillis();
        
        initializeSquads();
    }
    
    /**
     * Initialize squads from existing units
     */
    private void initializeSquads() {
        if (tacticalField == null) {
            return;
        }
        
        List<Unit> enemyUnits = tacticalField.getEnemyUnits();
        if (enemyUnits.isEmpty()) {
            log.info("No enemy units found for squad initialization");
            return;
        }
        
        // Group units into squads based on proximity and type
        List<Squad> detectedSquads = detectSquads(enemyUnits);
        
        for (Squad squad : detectedSquads) {
            squads.put(squad.getSquadId(), squad);
            
            // Map units to squad
            for (Unit unit : squad.getMembers()) {
                unitToSquadMap.put(unit.getName(), squad.getSquadId());
            }
            
            // Initialize squad tactics
            initializeSquadTactics(squad);
        }
        
        log.info("Initialized {} squads with {} total units", squads.size(), enemyUnits.size());
    }
    
    /**
     * Detect existing squads from units
     */
    private List<Squad> detectSquads(List<Unit> enemyUnits) {
        List<Squad> detectedSquads = new ArrayList<>();
        Set<Unit> assignedUnits = new HashSet<>();
        
        for (Unit unit : enemyUnits) {
            if (assignedUnits.contains(unit)) {
                continue;
            }
            
            // Find nearby units to form a squad
            List<Unit> nearbyUnits = findNearbyUnits(unit, GameConfig.getSquadFormationRange());
            if (nearbyUnits.size() >= GameConfig.getSquadMinSize()) {
                Squad squad = createSquad(nearbyUnits);
                detectedSquads.add(squad);
                assignedUnits.addAll(nearbyUnits);
            }
        }
        
        return detectedSquads;
    }
    
    /**
     * Find units near a given unit
     */
    private List<Unit> findNearbyUnits(Unit centerUnit, int range) {
        List<Unit> nearbyUnits = new ArrayList<>();
        Position centerPos = centerUnit.getPosition();
        
        if (centerPos == null) {
            return nearbyUnits;
        }
        
        for (Unit unit : tacticalField.getEnemyUnits()) {
            if (unit == centerUnit) {
                nearbyUnits.add(unit);
                continue;
            }
            
            Position unitPos = unit.getPosition();
            if (unitPos != null) {
                double distance = calculateDistance(centerPos, unitPos);
                if (distance <= range) {
                    nearbyUnits.add(unit);
                }
            }
        }
        
        return nearbyUnits;
    }
    
    /**
     * Create a new squad from units
     */
    private Squad createSquad(List<Unit> members) {
        String squadId = "squad_" + System.currentTimeMillis() + "_" + members.size();
        SquadType squadType = determineSquadType(members);
        
        Squad squad = new Squad(squadId, squadType, members);
        squad.setFormation(calculateOptimalFormation(squad));
        squad.setTacticalRole(determineTacticalRole(squad));
        
        return squad;
    }
    
    /**
     * Determine squad type based on unit composition
     */
    private SquadType determineSquadType(List<Unit> members) {
        // TODO: Implement sophisticated squad type determination
        // - Analyze unit types and capabilities
        // - Consider unit equipment and specializations
        // - Apply mission-specific requirements
        
        if (members.size() >= GameConfig.getSquadLargeSize()) {
            return SquadType.ASSAULT;
        } else if (members.size() >= GameConfig.getSquadMediumSize()) {
            return SquadType.TACTICAL;
        } else {
            return SquadType.SCOUT;
        }
    }
    
    /**
     * Calculate optimal formation for squad
     */
    private SquadFormation calculateOptimalFormation(Squad squad) {
        // TODO: Implement sophisticated formation calculation
        // - Consider terrain and cover availability
        // - Apply squad type-specific formations
        // - Optimize for tactical objectives
        
        SquadType squadType = squad.getSquadType();
        switch (squadType) {
            case ASSAULT:
                return SquadFormation.WEDGE;
            case TACTICAL:
                return SquadFormation.DIAMOND;
            case SCOUT:
                return SquadFormation.LINE;
            case SUPPORT:
                return SquadFormation.CIRCLE;
            default:
                return SquadFormation.LINE;
        }
    }
    
    /**
     * Determine tactical role for squad
     */
    private TacticalRole determineTacticalRole(Squad squad) {
        // TODO: Implement sophisticated tactical role determination
        // - Analyze squad capabilities and composition
        // - Consider mission objectives and current situation
        // - Apply adaptive role assignment
        
        SquadType squadType = squad.getSquadType();
        switch (squadType) {
            case ASSAULT:
                return TacticalRole.ENGAGEMENT;
            case TACTICAL:
                return TacticalRole.FLEXIBLE;
            case SCOUT:
                return TacticalRole.RECONNAISSANCE;
            case SUPPORT:
                return TacticalRole.SUPPORT;
            default:
                return TacticalRole.FLEXIBLE;
        }
    }
    
    /**
     * Initialize tactics for a squad
     */
    private void initializeSquadTactics(Squad squad) {
        SquadType squadType = squad.getSquadType();
        TacticalRole tacticalRole = squad.getTacticalRole();
        
        // Create squad-specific tactics
        List<SquadTactic> squadTactics = new ArrayList<>();
        
        switch (squadType) {
            case ASSAULT:
                squadTactics.add(new SquadTactic("FLANKING_ATTACK", 0.8, TacticalPriority.HIGH));
                squadTactics.add(new SquadTactic("COORDINATED_CHARGE", 0.7, TacticalPriority.HIGH));
                squadTactics.add(new SquadTactic("SUPPRESSING_FIRE", 0.6, TacticalPriority.MEDIUM));
                break;
            case TACTICAL:
                squadTactics.add(new SquadTactic("COVER_AND_MOVE", 0.8, TacticalPriority.HIGH));
                squadTactics.add(new SquadTactic("PINCH_MANEUVER", 0.7, TacticalPriority.HIGH));
                squadTactics.add(new SquadTactic("TACTICAL_WITHDRAWAL", 0.5, TacticalPriority.MEDIUM));
                break;
            case SCOUT:
                squadTactics.add(new SquadTactic("STEALTH_RECON", 0.9, TacticalPriority.HIGH));
                squadTactics.add(new SquadTactic("MARKING_TARGETS", 0.8, TacticalPriority.HIGH));
                squadTactics.add(new SquadTactic("RAPID_REPOSITIONING", 0.7, TacticalPriority.MEDIUM));
                break;
            case SUPPORT:
                squadTactics.add(new SquadTactic("COVERING_FIRE", 0.8, TacticalPriority.HIGH));
                squadTactics.add(new SquadTactic("MEDICAL_SUPPORT", 0.7, TacticalPriority.MEDIUM));
                squadTactics.add(new SquadTactic("SUPPLY_DISTRIBUTION", 0.6, TacticalPriority.LOW));
                break;
        }
        
        squad.setAvailableTactics(squadTactics);
    }
    
    /**
     * Get squad for a unit
     */
    public Squad getSquadForUnit(Unit unit) {
        String squadId = unitToSquadMap.get(unit.getName());
        if (squadId != null) {
            return squads.get(squadId);
        }
        return null;
    }
    
    /**
     * Get squad for an alien (compatibility method)
     */
    public Squad getSquadForUnit(com.aliensattack.core.model.Alien alien) {
        String squadId = unitToSquadMap.get(alien.getName());
        if (squadId != null) {
            return squads.get(squadId);
        }
        return null;
    }
    
    /**
     * Get squad for any unit type using IUnit interface
     */
    public Squad getSquadForUnit(com.aliensattack.core.interfaces.IUnit unit) {
        String squadId = unitToSquadMap.get(unit.getName());
        if (squadId != null) {
            return squads.get(squadId);
        }
        return null;
    }
    
    /**
     * Get all squads
     */
    public Collection<Squad> getAllSquads() {
        return squads.values();
    }
    
    /**
     * Get squad by ID
     */
    public Squad getSquad(String squadId) {
        return squads.get(squadId);
    }
    
    /**
     * Add unit to squad
     */
    public boolean addUnitToSquad(Unit unit, String squadId) {
        Squad squad = squads.get(squadId);
        if (squad == null) {
            return false;
        }
        
        if (squad.addMember(unit)) {
            unitToSquadMap.put(unit.getName(), squadId);
            updateSquadTactics(squad);
            return true;
        }
        
        return false;
    }
    
    /**
     * Remove unit from squad
     */
    public boolean removeUnitFromSquad(Unit unit) {
        String squadId = unitToSquadMap.remove(unit.getName());
        if (squadId != null) {
            Squad squad = squads.get(squadId);
            if (squad != null) {
                squad.removeMember(unit);
                
                // Check if squad should be disbanded
                if (squad.getMemberCount() < GameConfig.getSquadMinSize()) {
                    disbandSquad(squadId);
                } else {
                    updateSquadTactics(squad);
                }
                
                return true;
            }
        }
        
        return false;
    }
    
    /**
     * Disband a squad
     */
    private void disbandSquad(String squadId) {
        Squad squad = squads.remove(squadId);
        if (squad != null) {
            // Remove squad mapping for all units
            for (Unit unit : squad.getMembers()) {
                unitToSquadMap.remove(unit.getName());
            }
            
            // Remove active tactics
            activeTactics.remove(squadId);
            
            log.info("Squad {} disbanded due to insufficient members", squadId);
        }
    }
    
    /**
     * Update squad tactics after composition change
     */
    private void updateSquadTactics(Squad squad) {
        // Recalculate formation and role
        squad.setFormation(calculateOptimalFormation(squad));
        squad.setTacticalRole(determineTacticalRole(squad));
        
        // Update available tactics
        initializeSquadTactics(squad);
        
        log.debug("Updated tactics for squad {}: {} members, {} formation, {} role", 
                 squad.getSquadId(), squad.getMemberCount(), squad.getFormation(), squad.getTacticalRole());
    }
    
    /**
     * Get coordination data for a squad
     */
    public SquadCoordinationData getCoordinationData(String squadId) {
        Squad squad = squads.get(squadId);
        if (squad == null) {
            return null;
        }
        
        // Check cache first
        SquadCoordinationData cached = coordinationCache.get(squadId);
        if (cached != null && !isCacheExpired()) {
            return cached;
        }
        
        // Calculate new coordination data
        SquadCoordinationData coordinationData = calculateCoordinationData(squad);
        coordinationCache.put(squadId, coordinationData);
        
        return coordinationData;
    }
    
    /**
     * Calculate coordination data for a squad
     */
    private SquadCoordinationData calculateCoordinationData(Squad squad) {
        SquadCoordinationData data = new SquadCoordinationData();
        
        // Calculate squad center and bounding box
        Position center = calculateSquadCenter(squad);
        data.setSquadCenter(center);
        
        // Calculate optimal positions for each member
        Map<Unit, Position> optimalPositions = calculateOptimalPositions(squad);
        data.setOptimalPositions(optimalPositions);
        
        // Calculate tactical advantages
        double tacticalAdvantage = calculateTacticalAdvantage(squad);
        data.setTacticalAdvantage(tacticalAdvantage);
        
        // Calculate coordination score
        double coordinationScore = calculateCoordinationScore(squad);
        data.setCoordinationScore(coordinationScore);
        
        // Identify best tactical options
        List<SquadTactic> bestTactics = identifyBestTactics(squad);
        data.setBestTactics(bestTactics);
        
        return data;
    }
    
    /**
     * Calculate squad center position
     */
    private Position calculateSquadCenter(Squad squad) {
        if (squad.getMembers().isEmpty()) {
            return new Position(0, 0);
        }
        
        int totalX = 0;
        int totalY = 0;
        int count = 0;
        
        for (Unit unit : squad.getMembers()) {
            Position pos = unit.getPosition();
            if (pos != null) {
                totalX += pos.getX();
                totalY += pos.getY();
                count++;
            }
        }
        
        if (count > 0) {
            return new Position(totalX / count, totalY / count);
        }
        
        return new Position(0, 0);
    }
    
    /**
     * Calculate optimal positions for squad members
     */
    private Map<Unit, Position> calculateOptimalPositions(Squad squad) {
        Map<Unit, Position> optimalPositions = new HashMap<>();
        Position squadCenter = calculateSquadCenter(squad);
        SquadFormation formation = squad.getFormation();
        
        // TODO: Implement sophisticated position calculation
        // - Apply formation-specific positioning
        // - Consider terrain and cover
        // - Optimize for tactical objectives
        
        List<Unit> members = new ArrayList<>(squad.getMembers());
        for (int i = 0; i < members.size(); i++) {
            Unit unit = members.get(i);
            Position optimalPos = calculateFormationPosition(squadCenter, formation, i, members.size());
            optimalPositions.put(unit, optimalPos);
        }
        
        return optimalPositions;
    }
    
    /**
     * Calculate position based on formation
     */
    private Position calculateFormationPosition(Position center, SquadFormation formation, int memberIndex, int totalMembers) {
        int spacing = GameConfig.getSquadFormationSpacing();
        
        switch (formation) {
            case LINE:
                return new Position(center.getX() + (memberIndex - totalMembers/2) * spacing, center.getY());
            case DIAMOND:
                return calculateDiamondPosition(center, memberIndex, totalMembers, spacing);
            case WEDGE:
                return calculateWedgePosition(center, memberIndex, totalMembers, spacing);
            case CIRCLE:
                return calculateCirclePosition(center, memberIndex, totalMembers, spacing);
            default:
                return center;
        }
    }
    
    /**
     * Calculate diamond formation position
     */
    private Position calculateDiamondPosition(Position center, int memberIndex, int totalMembers, int spacing) {
        if (memberIndex == 0) {
            return center; // Leader at center
        }
        
        int ring = (memberIndex - 1) / 4 + 1;
        int positionInRing = (memberIndex - 1) % 4;
        
        int x = center.getX();
        int y = center.getY();
        
        switch (positionInRing) {
            case 0: return new Position(x, y - ring * spacing); // North
            case 1: return new Position(x + ring * spacing, y); // East
            case 2: return new Position(x, y + ring * spacing); // South
            case 3: return new Position(x - ring * spacing, y); // West
            default: return center;
        }
    }
    
    /**
     * Calculate wedge formation position
     */
    private Position calculateWedgePosition(Position center, int memberIndex, int totalMembers, int spacing) {
        if (memberIndex == 0) {
            return center; // Leader at tip
        }
        
        int row = (int) Math.sqrt(memberIndex);
        int col = memberIndex - row * row;
        
        int x = center.getX() + col * spacing;
        int y = center.getY() + row * spacing;
        
        return new Position(x, y);
    }
    
    /**
     * Calculate circle formation position
     */
    private Position calculateCirclePosition(Position center, int memberIndex, int totalMembers, int spacing) {
        if (totalMembers <= 1) {
            return center;
        }
        
        double angle = (2 * Math.PI * memberIndex) / totalMembers;
        int x = center.getX() + (int) (Math.cos(angle) * spacing);
        int y = center.getY() + (int) (Math.sin(angle) * spacing);
        
        return new Position(x, y);
    }
    
    /**
     * Calculate tactical advantage for squad
     */
    private double calculateTacticalAdvantage(Squad squad) {
        double advantage = 0.0;
        
        // TODO: Implement sophisticated tactical advantage calculation
        // - Consider terrain and cover
        // - Analyze enemy positions and threats
        // - Evaluate squad capabilities
        
        // Basic advantage based on member count and type
        advantage += squad.getMemberCount() * GameConfig.getSquadMemberAdvantage();
        
        // Formation advantage
        advantage += getFormationAdvantage(squad.getFormation());
        
        // Terrain advantage
        advantage += calculateTerrainAdvantage(squad);
        
        return Math.max(0.0, advantage);
    }
    
    /**
     * Get formation advantage
     */
    private double getFormationAdvantage(SquadFormation formation) {
        switch (formation) {
            case WEDGE: return GameConfig.getFormationWedgeAdvantage();
            case DIAMOND: return GameConfig.getFormationDiamondAdvantage();
            case LINE: return GameConfig.getFormationLineAdvantage();
            case CIRCLE: return GameConfig.getFormationCircleAdvantage();
            default: return 0.0;
        }
    }
    
    /**
     * Calculate terrain advantage for squad
     */
    private double calculateTerrainAdvantage(Squad squad) {
        double advantage = 0.0;
        
        for (Unit unit : squad.getMembers()) {
            Position pos = unit.getPosition();
            if (pos != null) {
                // Cover advantage
                double coverValue = coverSystem.getCoverValueAt(pos);
                advantage += coverValue * GameConfig.getSquadCoverAdvantage();
                
                // Elevation advantage
                int elevation = coverSystem.getElevationAt(pos);
                advantage += elevation * GameConfig.getSquadElevationAdvantage();
            }
        }
        
        return advantage / squad.getMemberCount();
    }
    
    /**
     * Calculate coordination score for squad
     */
    private double calculateCoordinationScore(Squad squad) {
        double score = 0.0;
        
        // TODO: Implement sophisticated coordination scoring
        // - Analyze unit positioning and formation
        // - Consider communication and support capabilities
        // - Evaluate tactical cohesion
        
        // Base coordination score
        score += GameConfig.getSquadBaseCoordinationScore();
        
        // Formation adherence
        score += calculateFormationAdherence(squad);
        
        // Unit spacing
        score += calculateUnitSpacing(squad);
        
        // Tactical role alignment
        score += calculateRoleAlignment(squad);
        
        return Math.max(0.0, Math.min(1.0, score));
    }
    
    /**
     * Calculate formation adherence
     */
    private double calculateFormationAdherence(Squad squad) {
        // TODO: Implement formation adherence calculation
        // - Compare actual positions to ideal formation
        // - Consider terrain constraints
        // - Apply formation-specific rules
        
        return 0.7; // Placeholder
    }
    
    /**
     * Calculate unit spacing
     */
    private double calculateUnitSpacing(Squad squad) {
        // TODO: Implement unit spacing calculation
        // - Analyze distances between units
        // - Consider optimal spacing for tactics
        // - Apply squad type-specific rules
        
        return 0.8; // Placeholder
    }
    
    /**
     * Calculate role alignment
     */
    private double calculateRoleAlignment(Squad squad) {
        // TODO: Implement role alignment calculation
        // - Analyze unit capabilities vs. tactical role
        // - Consider equipment and specializations
        // - Apply role-specific requirements
        
        return 0.9; // Placeholder
    }
    
    /**
     * Identify best tactics for squad
     */
    private List<SquadTactic> identifyBestTactics(Squad squad) {
        List<SquadTactic> bestTactics = new ArrayList<>();
        List<SquadTactic> availableTactics = squad.getAvailableTactics();
        
        // TODO: Implement sophisticated tactic selection
        // - Consider current tactical situation
        // - Analyze enemy positions and threats
        // - Apply squad capabilities and formation
        
        // Sort tactics by priority and success rate
        availableTactics.sort((t1, t2) -> {
            int priorityCompare = t2.getPriority().compareTo(t1.getPriority());
            if (priorityCompare != 0) {
                return priorityCompare;
            }
            return Double.compare(t2.getSuccessRate(), t1.getSuccessRate());
        });
        
        // Return top tactics
        int maxTactics = Math.min(GameConfig.getSquadMaxTactics(), availableTactics.size());
        for (int i = 0; i < maxTactics; i++) {
            bestTactics.add(availableTactics.get(i));
        }
        
        return bestTactics;
    }
    
    /**
     * Execute squad tactic
     */
    public boolean executeSquadTactic(String squadId, String tacticName) {
        Squad squad = squads.get(squadId);
        if (squad == null) {
            return false;
        }
        
        SquadTactic tactic = findTactic(squad, tacticName);
        if (tactic == null) {
            return false;
        }
        
        // TODO: Implement tactic execution
        // - Coordinate unit movements
        // - Apply tactical bonuses
        // - Update squad state
        
        activeTactics.put(squadId, tactic);
        log.info("Executing tactic {} for squad {}", tacticName, squadId);
        
        return true;
    }
    
    /**
     * Find tactic by name
     */
    private SquadTactic findTactic(Squad squad, String tacticName) {
        return squad.getAvailableTactics().stream()
                .filter(t -> t.getName().equals(tacticName))
                .findFirst()
                .orElse(null);
    }
    
    /**
     * Get active tactic for squad
     */
    public SquadTactic getActiveTactic(String squadId) {
        return activeTactics.get(squadId);
    }
    
    /**
     * Update squad coordination
     */
    public void updateCoordination() {
        long currentTime = System.currentTimeMillis();
        
        // Check if update is needed
        if (currentTime - lastCoordinationUpdate < GameConfig.getSquadUpdateInterval()) {
            return;
        }
        
        // Update all squads
        for (Squad squad : squads.values()) {
            updateSquadCoordination(squad);
        }
        
        // Clear expired cache
        clearExpiredCache();
        
        lastCoordinationUpdate = currentTime;
        log.debug("Updated coordination for {} squads", squads.size());
    }
    
    /**
     * Update coordination for a specific squad
     */
    private void updateSquadCoordination(Squad squad) {
        // Update formation if needed
        if (shouldUpdateFormation(squad)) {
            SquadFormation newFormation = calculateOptimalFormation(squad);
            squad.setFormation(newFormation);
        }
        
        // Update tactical role if needed
        if (shouldUpdateTacticalRole(squad)) {
            TacticalRole newRole = determineTacticalRole(squad);
            squad.setTacticalRole(newRole);
            initializeSquadTactics(squad);
        }
        
        // Update coordination data
        SquadCoordinationData coordinationData = calculateCoordinationData(squad);
        coordinationCache.put(squad.getSquadId(), coordinationData);
    }
    
    /**
     * Check if formation should be updated
     */
    private boolean shouldUpdateFormation(Squad squad) {
        // TODO: Implement formation update logic
        // - Consider tactical situation changes
        // - Analyze terrain changes
        // - Apply squad state changes
        
        return false; // Placeholder
    }
    
    /**
     * Check if tactical role should be updated
     */
    private boolean shouldUpdateTacticalRole(Squad squad) {
        // TODO: Implement tactical role update logic
        // - Consider mission objective changes
        // - Analyze enemy threat changes
        // - Apply squad capability changes
        
        return false; // Placeholder
    }
    
    /**
     * Clear expired cache entries
     */
    private void clearExpiredCache() {
        if (isCacheExpired()) {
            coordinationCache.clear();
            log.debug("Cleared expired coordination cache");
        }
    }
    
    /**
     * Check if cache is expired
     */
    private boolean isCacheExpired() {
        return System.currentTimeMillis() - lastCoordinationUpdate > COORDINATION_CACHE_DURATION;
    }
    
    /**
     * Get system performance statistics
     */
    public Map<String, Object> getPerformanceStats() {
        Map<String, Object> stats = new HashMap<>();
        
        stats.put("totalSquads", squads.size());
        stats.put("totalUnits", unitToSquadMap.size());
        stats.put("activeTactics", activeTactics.size());
        stats.put("coordinationCacheSize", coordinationCache.size());
        stats.put("lastCoordinationUpdate", lastCoordinationUpdate);
        stats.put("cacheAge", System.currentTimeMillis() - lastCoordinationUpdate);
        
        return stats;
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
     * Squad inner class
     */
    @Getter
    @Setter
    public static class Squad {
        private String squadId;
        private SquadType squadType;
        private TacticalRole tacticalRole;
        private SquadFormation formation;
        private List<Unit> members;
        private List<SquadTactic> availableTactics;
        private Position objective;
        private double morale;
        
        public Squad(String squadId, SquadType squadType, List<Unit> members) {
            this.squadId = squadId;
            this.squadType = squadType;
            this.members = new ArrayList<>(members);
            this.availableTactics = new ArrayList<>();
            this.morale = GameConfig.getSquadBaseMorale();
        }
        
        public int getMemberCount() {
            return members.size();
        }
        
        public boolean addMember(Unit unit) {
            if (members.size() >= GameConfig.getSquadMaxSize()) {
                return false;
            }
            return members.add(unit);
        }
        
        public boolean removeMember(Unit unit) {
            return members.remove(unit);
        }
    }
    
    /**
     * Squad tactic inner class
     */
    @Getter
    @Setter
    public static class SquadTactic {
        private String name;
        private double successRate;
        private TacticalPriority priority;
        private int cooldown;
        private long lastUsed;
        
        public SquadTactic(String name, double successRate, TacticalPriority priority) {
            this.name = name;
            this.successRate = successRate;
            this.priority = priority;
            this.cooldown = GameConfig.getTacticDefaultCooldown();
            this.lastUsed = 0;
        }
        
        public boolean canUse() {
            return System.currentTimeMillis() - lastUsed >= cooldown * 1000L;
        }
        
        public void use() {
            this.lastUsed = System.currentTimeMillis();
        }
    }
    
    /**
     * Squad coordination data inner class
     */
    @Getter
    @Setter
    public static class SquadCoordinationData {
        private Position squadCenter;
        private Map<Unit, Position> optimalPositions;
        private double tacticalAdvantage;
        private double coordinationScore;
        private List<SquadTactic> bestTactics;
        private long timestamp;
        
        public SquadCoordinationData() {
            this.timestamp = System.currentTimeMillis();
        }
    }
    
    /**
     * Squad type enum
     */
    public enum SquadType {
        ASSAULT,    // Heavy combat focused
        TACTICAL,   // Balanced capabilities
        SCOUT,      // Reconnaissance and mobility
        SUPPORT     // Medical and supply
    }
    
    /**
     * Tactical role enum
     */
    public enum TacticalRole {
        ENGAGEMENT,     // Direct combat
        FLEXIBLE,       // Adaptive tactics
        RECONNAISSANCE, // Information gathering
        SUPPORT         // Supporting other units
    }
    
    /**
     * Squad formation enum
     */
    public enum SquadFormation {
        LINE,       // Horizontal line
        DIAMOND,    // Diamond pattern
        WEDGE,      // V-formation
        CIRCLE      // Circular defense
    }
    
    /**
     * Tactical priority enum
     */
    public enum TacticalPriority {
        HIGH,       // Critical importance
        MEDIUM,     // Important
        LOW         // Low priority
    }
}
