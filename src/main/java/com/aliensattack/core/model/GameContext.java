package com.aliensattack.core.model;

import com.aliensattack.core.interfaces.IUnit;
import com.aliensattack.field.ITacticalField;
import com.aliensattack.combat.ICombatManager;
import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;

/**
 * Game context information passed to brain systems for decision making
 * Contains all relevant information about the current game state
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GameContext {
    
    // Current game state
    private int currentTurn;
    private int currentPhase;
    private GamePhase gamePhase;
    private boolean isPlayerTurn;
    
    // Field information
    private ITacticalField tacticalField;
    private ICombatManager combatManager;
    
    // Unit information
    private IUnit activeUnit;
    private List<IUnit> playerUnits;
    private List<IUnit> enemyUnits;
    private List<IUnit> neutralUnits;
    
    // Combat information
    private List<IUnit> visibleEnemies;
    private List<IUnit> unitsInRange;
    private Map<String, Integer> threatLevels;
    private Map<String, Integer> coverValues;
    
    // Environmental information
    private Map<String, Integer> hazardLevels;
    private WeatherType currentWeather;
    private int visibility;
    private int timeOfDay;
    
    // Mission information
    private String missionType;
    private int missionTimer;
    private List<String> objectives;
    private Map<String, Boolean> objectiveStatus;
    
    // Strategic information
    private int playerResources;
    private int enemyResources;
    private List<String> availableAbilities;
    private Map<String, Integer> cooldowns;
    
    // AI specific information
    private Map<String, Double> unitPriorities;
    private Map<String, String> lastKnownPositions;
    private List<String> tacticalAdvantages;
    private Map<String, Integer> unitExperience;
    
    // Human player preferences
    private Map<String, Integer> playerPreferences;
    private List<String> favoriteTactics;
    private Map<String, Boolean> autoActions;
    
    // Performance metrics
    private long turnStartTime;
    private int actionsThisTurn;
    private double averageActionTime;
    
    /**
     * Game phases enumeration
     */
    public enum GamePhase {
        PREPARATION,    // Before combat starts
        DEPLOYMENT,     // Unit deployment
        PLAYER_TURN,    // Player's turn
        ENEMY_TURN,     // Enemy turn
        INTERRUPT,      // Interrupt phase
        VICTORY,        // Victory achieved
        DEFEAT,         // Defeat occurred
        MISSION_COMPLETE // Mission finished
    }
    
    /**
     * Weather types enumeration
     */
    public enum WeatherType {
        CLEAR,          // Clear weather
        RAIN,           // Rainy weather
        FOG,            // Foggy conditions
        STORM,          // Stormy weather
        SNOW,           // Snowy conditions
        ACID_RAIN,      // Acid rain (alien world)
        PLASMA_STORM,   // Plasma storm
        RADIATION       // High radiation
    }
    
    /**
     * Create a default game context
     */
    public static GameContext createDefault() {
        return GameContext.builder()
                .currentTurn(1)
                .currentPhase(1)
                .gamePhase(GamePhase.PREPARATION)
                .isPlayerTurn(true)
                .threatLevels(new HashMap<>())
                .coverValues(new HashMap<>())
                .hazardLevels(new HashMap<>())
                .currentWeather(WeatherType.CLEAR)
                .visibility(100)
                .timeOfDay(12)
                .objectives(new ArrayList<>())
                .objectiveStatus(new HashMap<>())
                .availableAbilities(new ArrayList<>())
                .cooldowns(new HashMap<>())
                .unitPriorities(new HashMap<>())
                .lastKnownPositions(new HashMap<>())
                .tacticalAdvantages(new ArrayList<>())
                .unitExperience(new HashMap<>())
                .playerPreferences(new HashMap<>())
                .favoriteTactics(new ArrayList<>())
                .autoActions(new HashMap<>())
                .turnStartTime(System.currentTimeMillis())
                .actionsThisTurn(0)
                .averageActionTime(0.0)
                .build();
    }
    
    /**
     * Update context for a new turn
     */
    public void updateForNewTurn() {
        this.currentTurn++;
        this.actionsThisTurn = 0;
        this.turnStartTime = System.currentTimeMillis();
        this.cooldowns.replaceAll((k, v) -> Math.max(0, v - 1));
    }
    
    /**
     * Check if a unit is visible to the active unit
     */
    public boolean isUnitVisible(IUnit unit) {
        return visibleEnemies != null && visibleEnemies.contains(unit);
    }
    
    /**
     * Get the threat level for a specific unit
     */
    public int getThreatLevel(String unitId) {
        return threatLevels.getOrDefault(unitId, 0);
    }
    
    /**
     * Get the cover value for a specific position
     */
    public int getCoverValue(String positionKey) {
        return coverValues.getOrDefault(positionKey, 0);
    }
    
    /**
     * Check if an ability is available (not on cooldown)
     */
    public boolean isAbilityAvailable(String abilityId) {
        return cooldowns.getOrDefault(abilityId, 0) <= 0;
    }
    
    /**
     * Get the priority for a specific unit
     */
    public double getUnitPriority(String unitId) {
        return unitPriorities.getOrDefault(unitId, 1.0);
    }
    
    /**
     * Check if auto-action is enabled for a specific action type
     */
    public boolean isAutoActionEnabled(String actionType) {
        return autoActions.getOrDefault(actionType, false);
    }
    
    /**
     * Get visible enemies for the current unit
     */
    public List<IUnit> getVisibleEnemies() {
        return visibleEnemies != null ? new ArrayList<>(visibleEnemies) : new ArrayList<>();
    }
    
    /**
     * Get units in range of the active unit
     */
    public List<IUnit> getUnitsInRange() {
        return unitsInRange != null ? new ArrayList<>(unitsInRange) : new ArrayList<>();
    }
    
    /**
     * Get the current weather conditions
     */
    public WeatherType getCurrentWeather() {
        return currentWeather != null ? currentWeather : WeatherType.CLEAR;
    }
    
    /**
     * Get threat levels for all units
     */
    public Map<String, Integer> getThreatLevels() {
        return threatLevels != null ? new HashMap<>(threatLevels) : new HashMap<>();
    }
    
    /**
     * Get cover values for all positions
     */
    public Map<String, Integer> getCoverValues() {
        return coverValues != null ? new HashMap<>(coverValues) : new HashMap<>();
    }
    
    /**
     * Get hazard levels for all positions
     */
    public Map<String, Integer> getHazardLevels() {
        return hazardLevels != null ? new HashMap<>(hazardLevels) : new HashMap<>();
    }
    
    /**
     * Get the current visibility level
     */
    public int getVisibility() {
        return visibility;
    }
    
    /**
     * Get the current time of day
     */
    public int getTimeOfDay() {
        return timeOfDay;
    }
    
    /**
     * Get the current mission type
     */
    public String getMissionType() {
        return missionType != null ? missionType : "UNKNOWN";
    }
    
    /**
     * Get the current mission timer
     */
    public int getMissionTimer() {
        return missionTimer;
    }
    
    /**
     * Get the current objectives
     */
    public List<String> getObjectives() {
        return objectives != null ? new ArrayList<>(objectives) : new ArrayList<>();
    }
    
    /**
     * Get the status of objectives
     */
    public Map<String, Boolean> getObjectiveStatus() {
        return objectiveStatus != null ? new HashMap<>(objectiveStatus) : new HashMap<>();
    }
    
    /**
     * Get available abilities
     */
    public List<String> getAvailableAbilities() {
        return availableAbilities != null ? new ArrayList<>(availableAbilities) : new ArrayList<>();
    }
    
    /**
     * Get ability cooldowns
     */
    public Map<String, Integer> getCooldowns() {
        return cooldowns != null ? new HashMap<>(cooldowns) : new HashMap<>();
    }
    
    /**
     * Get unit priorities
     */
    public Map<String, Double> getUnitPriorities() {
        return unitPriorities != null ? new HashMap<>(unitPriorities) : new HashMap<>();
    }
    
    /**
     * Get last known positions
     */
    public Map<String, String> getLastKnownPositions() {
        return lastKnownPositions != null ? new HashMap<>(lastKnownPositions) : new HashMap<>();
    }
    
    /**
     * Get tactical advantages
     */
    public List<String> getTacticalAdvantages() {
        return tacticalAdvantages != null ? new ArrayList<>(tacticalAdvantages) : new ArrayList<>();
    }
    
    /**
     * Get unit experience levels
     */
    public Map<String, Integer> getUnitExperience() {
        return unitExperience != null ? new HashMap<>(unitExperience) : new HashMap<>();
    }
    
    /**
     * Get player preferences
     */
    public Map<String, Integer> getPlayerPreferences() {
        return playerPreferences != null ? new HashMap<>(playerPreferences) : new HashMap<>();
    }
    
    /**
     * Get favorite tactics
     */
    public List<String> getFavoriteTactics() {
        return favoriteTactics != null ? new ArrayList<>(favoriteTactics) : new ArrayList<>();
    }
    
    /**
     * Get auto action settings
     */
    public Map<String, Boolean> getAutoActions() {
        return autoActions != null ? new HashMap<>(autoActions) : new HashMap<>();
    }
    
    /**
     * Check if it's currently the player's turn
     */
    public boolean isPlayerTurn() {
        return isPlayerTurn;
    }
    
    /**
     * Get the current turn number
     */
    public int getCurrentTurn() {
        return currentTurn;
    }
    
    /**
     * Get the current game phase
     */
    public GamePhase getGamePhase() {
        return gamePhase != null ? gamePhase : GamePhase.PREPARATION;
    }
    
    /**
     * Get the active unit
     */
    public IUnit getActiveUnit() {
        return activeUnit;
    }
    
    /**
     * Get all player units
     */
    public List<IUnit> getPlayerUnits() {
        return playerUnits != null ? new ArrayList<>(playerUnits) : new ArrayList<>();
    }
    
    /**
     * Get all enemy units
     */
    public List<IUnit> getEnemyUnits() {
        return enemyUnits != null ? new ArrayList<>(enemyUnits) : new ArrayList<>();
    }
    
    /**
     * Get all neutral units
     */
    public List<IUnit> getNeutralUnits() {
        return neutralUnits != null ? new ArrayList<>(neutralUnits) : new ArrayList<>();
    }
    
    /**
     * Get the tactical field
     */
    public ITacticalField getTacticalField() {
        return tacticalField;
    }
    
    /**
     * Get the combat manager
     */
    public ICombatManager getCombatManager() {
        return combatManager;
    }
}
