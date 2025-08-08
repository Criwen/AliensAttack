package com.aliensattack.combat;

import com.aliensattack.core.model.*;
import com.aliensattack.core.enums.*;
import com.aliensattack.field.ITacticalField;
import lombok.Getter;
import lombok.Setter;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Alien Ruler Combat Manager - Handles advanced alien ruler mechanics
 */
@Getter
@Setter
public class AlienRulerCombatManager extends XCOM2UltimateCombatManager {
    
    private Random random;
    private List<AlienRuler> activeRulers;
    private Map<String, AlienRuler> rulerRegistry;
    private Map<String, List<RulerReactionType>> rulerReactionHistory;
    private Map<String, Integer> rulerEscalationLevels;
    private Map<String, Position> rulerLastPositions;
    private Map<String, Integer> rulerHealthBars;
    private Map<String, Boolean> rulerEnragedStates;
    private Map<String, Boolean> rulerTeleportingStates;
    private Map<String, Boolean> rulerShieldedStates;
    private Map<String, Boolean> rulerRegeneratingStates;
    private List<String> combatEvents;
    private Map<String, Integer> eventHistory;
    private Map<String, List<Unit>> rulerTargetList;
    
    public AlienRulerCombatManager(ITacticalField field, Mission mission) {
        super(field, mission);
        this.random = ThreadLocalRandom.current();
        this.activeRulers = new ArrayList<>();
        this.rulerRegistry = new HashMap<>();
        this.rulerReactionHistory = new HashMap<>();
        this.rulerEscalationLevels = new HashMap<>();
        this.rulerLastPositions = new HashMap<>();
        this.rulerHealthBars = new HashMap<>();
        this.rulerEnragedStates = new HashMap<>();
        this.rulerTeleportingStates = new HashMap<>();
        this.rulerShieldedStates = new HashMap<>();
        this.rulerRegeneratingStates = new HashMap<>();
        this.combatEvents = new ArrayList<>();
        this.eventHistory = new HashMap<>();
        this.rulerTargetList = new HashMap<>();
    }
    
    /**
     * Add alien ruler to combat
     */
    public void addAlienRuler(AlienRuler ruler) {
        if (!activeRulers.contains(ruler)) {
            activeRulers.add(ruler);
            rulerRegistry.put(ruler.getRulerName(), ruler);
            rulerReactionHistory.put(ruler.getRulerName(), new ArrayList<>());
            rulerEscalationLevels.put(ruler.getRulerName(), 1);
            rulerLastPositions.put(ruler.getRulerName(), ruler.getPosition());
            rulerTargetList.put(ruler.getRulerName(), new ArrayList<>());
            rulerHealthBars.put(ruler.getRulerName(), ruler.getHealthBars());
            rulerEnragedStates.put(ruler.getRulerName(), false);
            rulerTeleportingStates.put(ruler.getRulerName(), false);
            rulerShieldedStates.put(ruler.getRulerName(), false);
            rulerRegeneratingStates.put(ruler.getRulerName(), false);
            
            logCombatEvent("Alien Ruler " + ruler.getRulerName() + " entered combat");
        }
    }
    
    /**
     * Remove alien ruler from combat
     */
    public void removeAlienRuler(AlienRuler ruler) {
        activeRulers.remove(ruler);
        rulerRegistry.remove(ruler.getRulerName());
        rulerReactionHistory.remove(ruler.getRulerName());
        rulerEscalationLevels.remove(ruler.getRulerName());
        rulerLastPositions.remove(ruler.getRulerName());
        rulerHealthBars.remove(ruler.getRulerName());
        rulerEnragedStates.remove(ruler.getRulerName());
        rulerTeleportingStates.remove(ruler.getRulerName());
        rulerShieldedStates.remove(ruler.getRulerName());
        rulerRegeneratingStates.remove(ruler.getRulerName());
        
        logCombatEvent("Alien Ruler " + ruler.getRulerName() + " left combat");
    }
    
    /**
     * Process ruler reactions to player actions
     */
    public List<CombatResult> processRulerReactions(Unit playerUnit, String action) {
        List<CombatResult> results = new ArrayList<>();
        
        for (AlienRuler ruler : activeRulers) {
            if (ruler.isAlive() && ruler.canTakeActions()) {
                // Check if ruler should react to this action
                RulerReactionType reaction = determineRulerReaction(ruler, playerUnit, action);
                if (reaction != null && ruler.canReact(reaction)) {
                    CombatResult result = executeRulerReaction(ruler, playerUnit, reaction);
                    if (result != null) {
                        results.add(result);
                    }
                }
            }
        }
        
        return results;
    }
    
    /**
     * Determine what reaction a ruler should have to a player action
     */
    private RulerReactionType determineRulerReaction(AlienRuler ruler, Unit playerUnit, String action) {
        List<RulerReactionType> availableReactions = ruler.getAvailableReactions();
        
        // Simple reaction logic - can be enhanced with more sophisticated AI
        switch (action.toLowerCase()) {
            case "attack":
                if (availableReactions.contains(RulerReactionType.TELEPORT)) {
                    return RulerReactionType.TELEPORT;
                } else if (availableReactions.contains(RulerReactionType.SHIELD_UP)) {
                    return RulerReactionType.SHIELD_UP;
                }
                break;
            case "move":
                if (availableReactions.contains(RulerReactionType.MOVE_INTERRUPT)) {
                    return RulerReactionType.MOVE_INTERRUPT;
                }
                break;
            case "ability":
                if (availableReactions.contains(RulerReactionType.ABILITY_BLOCK)) {
                    return RulerReactionType.ABILITY_BLOCK;
                }
                break;
            case "heal":
                if (availableReactions.contains(RulerReactionType.ATTACK_INTERRUPT)) {
                    return RulerReactionType.ATTACK_INTERRUPT;
                }
                break;
        }
        
        // Random reaction if no specific reaction is triggered
        if (!availableReactions.isEmpty()) {
            return availableReactions.get(random.nextInt(availableReactions.size()));
        }
        
        return null;
    }
    
    /**
     * Execute a ruler reaction
     */
    private CombatResult executeRulerReaction(AlienRuler ruler, Unit target, RulerReactionType reaction) {
        boolean success = ruler.reactToPlayerAction(reaction, target);
        
        if (success) {
            // Update ruler state
            updateRulerState(ruler, reaction);
            
            // Log the reaction
            logCombatEvent("Ruler " + ruler.getRulerName() + " used " + reaction);
            
            // Create combat result
            return new CombatResult(true, 0, "Ruler " + ruler.getRulerName() + " reacted with " + reaction);
        }
        
        return new CombatResult(false, 0, "Ruler reaction failed");
    }
    
    /**
     * Update ruler state after reaction
     */
    private void updateRulerState(AlienRuler ruler, RulerReactionType reaction) {
        String rulerName = ruler.getRulerName();
        
        // Update reaction history
        List<RulerReactionType> history = rulerReactionHistory.get(rulerName);
        if (history != null) {
            history.add(reaction);
        }
        
        // Update ruler states based on reaction
        switch (reaction) {
            case TELEPORT:
                rulerTeleportingStates.put(rulerName, true);
                rulerLastPositions.put(rulerName, ruler.getPosition());
                break;
            case MIND_CONTROL:
                // Mind control effects handled elsewhere
                break;
            case ACID_ATTACK:
                // Acid attack effects handled elsewhere
                break;
            case PSYCHIC_BLAST:
                // Psychic blast effects handled elsewhere
                break;
            case REGENERATION:
                rulerRegeneratingStates.put(rulerName, true);
                break;
            case SHIELD_UP:
                rulerShieldedStates.put(rulerName, true);
                break;
            case RAGE_MODE:
                rulerEnragedStates.put(rulerName, true);
                break;
            case SUMMON_REINFORCEMENTS:
                // Summon effects handled elsewhere
                break;
            case DISABLE_WEAPONS:
                // Disable effects handled elsewhere
                break;
            case POISON_CLOUD:
                // Poison effects handled elsewhere
                break;
            case MIND_SCREAM:
                // Mind scream effects handled elsewhere
                break;
            case DIMENSIONAL_RIFT:
                // Dimensional rift effects handled elsewhere
                break;
            case TIME_SLOW:
                // Time slow effects handled elsewhere
                break;
            case HEALING_AURA:
                // Healing aura effects handled elsewhere
                break;
            case ARMOR_BREAK:
                // Armor break effects handled elsewhere
                break;
            case WEAPON_DESTROY:
                // Weapon destroy effects handled elsewhere
                break;
            case MOVE_INTERRUPT:
                // Move interrupt effects handled elsewhere
                break;
            case ATTACK_INTERRUPT:
                // Attack interrupt effects handled elsewhere
                break;
            case ABILITY_BLOCK:
                // Ability block effects handled elsewhere
                break;
            case VISION_BLOCK:
                // Vision block effects handled elsewhere
                break;
        }
    }
    
    /**
     * Process ruler turn
     */
    public void processRulerTurn() {
        for (AlienRuler ruler : activeRulers) {
            if (ruler.isAlive()) {
                // Update ruler cooldowns
                ruler.updateCooldowns();
                
                // Process ruler regeneration
                if (rulerRegeneratingStates.get(ruler.getRulerName())) {
                    processRulerRegeneration(ruler);
                }
                
                // Process ruler escalation
                processRulerEscalation(ruler);
                
                // Process ruler learning
                processRulerLearning(ruler);
            }
        }
    }
    
    /**
     * Process ruler regeneration
     */
    private void processRulerRegeneration(AlienRuler ruler) {
        String rulerName = ruler.getRulerName();
        
        if (ruler.getCurrentHealth() < ruler.getMaxHealth()) {
            int regenerationAmount = ruler.getRegenerationRate();
            ruler.setCurrentHealth(Math.min(ruler.getMaxHealth(), ruler.getCurrentHealth() + regenerationAmount));
            
            logCombatEvent("Ruler " + rulerName + " regenerated " + regenerationAmount + " health");
        }
        
        // Reset regeneration state
        rulerRegeneratingStates.put(rulerName, false);
    }
    
    /**
     * Process ruler escalation
     */
    private void processRulerEscalation(AlienRuler ruler) {
        String rulerName = ruler.getRulerName();
        int currentEscalation = rulerEscalationLevels.get(rulerName);
        
        // Escalate based on damage taken or turns passed
        if (shouldEscalate(ruler)) {
            ruler.escalate();
            rulerEscalationLevels.put(rulerName, currentEscalation + 1);
            
            logCombatEvent("Ruler " + rulerName + " escalated to level " + (currentEscalation + 1));
        }
    }
    
    /**
     * Check if ruler should escalate
     */
    private boolean shouldEscalate(AlienRuler ruler) {
        // Simple escalation logic - can be enhanced
        int healthPercentage = (ruler.getCurrentHealth() * 100) / ruler.getMaxHealth();
        return healthPercentage < 50; // Escalate when below 50% health
    }
    
    /**
     * Process ruler learning
     */
    private void processRulerLearning(AlienRuler ruler) {
        // Rulers learn from player tactics
        // This can be enhanced with more sophisticated learning algorithms
        String rulerName = ruler.getRulerName();
        
        // Learn from recent player actions
        List<Unit> nearbyPlayers = getNearbyPlayerUnits(ruler.getPosition(), 5);
        for (Unit player : nearbyPlayers) {
            // Analyze player tactics and learn
            String tactic = analyzePlayerTactic(player);
            if (tactic != null) {
                ruler.learnFromTactic(tactic);
                logCombatEvent("Ruler " + rulerName + " learned tactic: " + tactic);
            }
        }
    }
    
    /**
     * Get nearby player units
     */
    private List<Unit> getNearbyPlayerUnits(Position position, int range) {
        List<Unit> nearbyUnits = new ArrayList<>();
        
        for (Unit unit : getAllUnits()) {
            if (unit.getUnitType() == UnitType.SOLDIER && unit.isAlive()) {
                int distance = position.getDistanceTo(unit.getPosition());
                if (distance <= range) {
                    nearbyUnits.add(unit);
                }
            }
        }
        
        return nearbyUnits;
    }
    
    /**
     * Analyze player tactic
     */
    private String analyzePlayerTactic(Unit player) {
        // Simple tactic analysis - can be enhanced
        if (player.getWeapon() != null) {
            if (player.getWeapon().getRange() > 5) {
                return "long_range_combat";
            } else if (player.getWeapon().getBaseDamage() > 15) {
                return "high_damage_combat";
            } else {
                return "standard_combat";
            }
        }
        return null;
    }
    
    /**
     * Get ruler status
     */
    public String getRulerStatus(AlienRuler ruler) {
        String rulerName = ruler.getRulerName();
        StringBuilder status = new StringBuilder();
        
        status.append("Ruler: ").append(rulerName).append("\n");
        status.append("Health: ").append(ruler.getCurrentHealth()).append("/").append(ruler.getMaxHealth()).append("\n");
        status.append("Health Bars: ").append(ruler.getCurrentHealthBar()).append("/").append(ruler.getHealthBars()).append("\n");
        status.append("Escalation Level: ").append(rulerEscalationLevels.get(rulerName)).append("\n");
        status.append("Enraged: ").append(rulerEnragedStates.get(rulerName)).append("\n");
        status.append("Shielded: ").append(rulerShieldedStates.get(rulerName)).append("\n");
        status.append("Regenerating: ").append(rulerRegeneratingStates.get(rulerName)).append("\n");
        status.append("Teleporting: ").append(rulerTeleportingStates.get(rulerName)).append("\n");
        
        return status.toString();
    }
    
    /**
     * Get all ruler statuses
     */
    public String getAllRulerStatuses() {
        StringBuilder statuses = new StringBuilder();
        statuses.append("=== ALIEN RULER STATUS ===\n");
        
        for (AlienRuler ruler : activeRulers) {
            statuses.append(getRulerStatus(ruler)).append("\n");
        }
        
        return statuses.toString();
    }
    
    /**
     * Log combat event
     */
    public void logCombatEvent(String event) {
        combatEvents.add(event);
        eventHistory.put(event, eventHistory.getOrDefault(event, 0) + 1);
    }
    
    /**
     * Get recent combat events
     */
    public List<String> getRecentCombatEvents() {
        int maxEvents = 10;
        int startIndex = Math.max(0, combatEvents.size() - maxEvents);
        return combatEvents.subList(startIndex, combatEvents.size());
    }
    
    /**
     * Get active rulers
     */
    public List<AlienRuler> getActiveRulers() {
        return new ArrayList<>(activeRulers);
    }
    
    /**
     * Check if any rulers are active
     */
    public boolean hasActiveRulers() {
        return !activeRulers.isEmpty();
    }
    
    /**
     * Get ruler by name
     */
    public AlienRuler getRulerByName(String rulerName) {
        return rulerRegistry.get(rulerName);
    }
    
    /**
     * Get ruler escalation level
     */
    public int getRulerEscalationLevel(String rulerName) {
        return rulerEscalationLevels.getOrDefault(rulerName, 1);
    }
    
    /**
     * Check if ruler is enraged
     */
    public boolean isRulerEnraged(String rulerName) {
        return rulerEnragedStates.getOrDefault(rulerName, false);
    }
    
    /**
     * Check if ruler is shielded
     */
    public boolean isRulerShielded(String rulerName) {
        return rulerShieldedStates.getOrDefault(rulerName, false);
    }
    
    /**
     * Check if ruler is regenerating
     */
    public boolean isRulerRegenerating(String rulerName) {
        return rulerRegeneratingStates.getOrDefault(rulerName, false);
    }
    
    /**
     * Check if ruler is teleporting
     */
    public boolean isRulerTeleporting(String rulerName) {
        return rulerTeleportingStates.getOrDefault(rulerName, false);
    }
}
