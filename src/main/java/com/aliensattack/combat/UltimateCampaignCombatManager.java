package com.aliensattack.combat;

import com.aliensattack.core.model.Unit;
import com.aliensattack.core.model.AlienRuler;
import com.aliensattack.core.model.Chosen;
import com.aliensattack.core.model.AlienPod;
import com.aliensattack.core.model.MissionTimer;
import com.aliensattack.core.model.AlienReinforcement;
import com.aliensattack.core.model.Position;
import com.aliensattack.core.model.Mission;
import com.aliensattack.core.enums.*;
import com.aliensattack.field.ITacticalField;
import lombok.Getter;
import lombok.Setter;
import com.aliensattack.core.config.GameConfig;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

/**
 * XCOM 2 Ultimate Combat Manager - Integrates all XCOM 2 mechanics
 * Implements the complete XCOM 2 tactical combat system
 */
@Getter
@Setter
public class UltimateCampaignCombatManager extends UltimateMissionCombatManager {

    private Random random;
    private List<AlienRuler> alienRulers;
    private List<Chosen> chosen;
    private List<AlienPod> alienPods;
    private List<MissionTimer> missionTimers;
    private List<AlienReinforcement> reinforcements;
    private Map<String, Unit> rulerTargets;
    private Map<String, Unit> chosenTargets;
    private Map<String, Position> podPositions;
    private Map<String, Integer> timerValues;
    private Map<String, Integer> reinforcementValues;
    private boolean isConcealmentActive;
    private boolean isAmbushActive;
    private int concealmentBreaks;
    private int ambushCount;
    private List<String> combatEvents;
    private Map<String, Integer> eventHistory;
    private int totalMechanics;
    
    public UltimateCampaignCombatManager(ITacticalField field, Mission mission) {
        super(field, mission);
        this.random = ThreadLocalRandom.current();
        this.alienRulers = new ArrayList<>();
        this.chosen = new ArrayList<>();
        this.alienPods = new ArrayList<>();
        this.missionTimers = new ArrayList<>();
        this.reinforcements = new ArrayList<>();
        this.rulerTargets = new HashMap<>();
        this.chosenTargets = new HashMap<>();
        this.podPositions = new HashMap<>();
        this.timerValues = new HashMap<>();
        this.reinforcementValues = new HashMap<>();
        this.isConcealmentActive = true;
        this.isAmbushActive = false;
        this.concealmentBreaks = 0;
        this.ambushCount = 0;
        this.combatEvents = new ArrayList<>();
        this.eventHistory = new HashMap<>();
        this.totalMechanics = GameConfig.getMaxEvents(); // Use configuration instead of hardcoded 45
        
        logCombatEvent("Ultimate Combat Manager initialized with " + totalMechanics + " mechanics");
    }
    
    // =============================================================================
    // ALIEN RULER SYSTEM
    // =============================================================================
    
    /**
     * Add alien ruler to combat
     */
    public void addAlienRuler(AlienRuler ruler) {
        alienRulers.add(ruler);
        logCombatEvent("Alien Ruler added: " + ruler.getName() + " (" + ruler.getRulerType() + ")");
    }
    
    /**
     * Use ruler ability
     */
    public boolean useRulerAbility(AlienRuler ruler, String abilityName, Unit target) {
        // Convert ability name to reaction type and use the available method
        try {
            RulerReactionType reactionType = RulerReactionType.valueOf(abilityName.toUpperCase());
            if (ruler.reactToPlayerAction(reactionType, target)) {
                logCombatEvent("Ruler ability used: " + abilityName + " by " + ruler.getName());
                return true;
            }
        } catch (IllegalArgumentException e) {
            logCombatEvent("Invalid ruler ability: " + abilityName);
        }
        return false;
    }
    
    /**
     * Process ruler reactions
     */
    public void processRulerReactions() {
        for (AlienRuler ruler : alienRulers) {
            if (ruler.isAlive()) {
                // Check if ruler can react to any available reaction
                for (RulerReactionType reaction : ruler.getAvailableReactions()) {
                    if (ruler.canReact(reaction)) {
                        logCombatEvent("Ruler reaction: " + ruler.getName() + " can react with " + reaction);
                        break;
                    }
                }
            }
        }
    }
    
    // =============================================================================
    // CHOSEN SYSTEM
    // =============================================================================
    
    /**
     * Add chosen to combat
     */
    public void addChosen(Chosen chosen) {
        this.chosen.add(chosen);
        logCombatEvent("Chosen added: " + chosen.getName() + " (" + chosen.getChosenType() + ")");
    }
    
    /**
     * Use chosen ability
     */
    public boolean useChosenAbility(Chosen chosen, String abilityName, Unit target) {
        if (chosen.useChosenAbility(abilityName, target)) {
            logCombatEvent("Chosen ability used: " + abilityName + " by " + chosen.getName());
            return true;
        }
        return false;
    }
    
    /**
     * Process chosen knowledge gain
     */
    public void processChosenKnowledge(String soldierName, int knowledgeAmount) {
        for (Chosen chosen : this.chosen) {
            chosen.gainKnowledge(soldierName, knowledgeAmount);
        }
        logCombatEvent("Chosen knowledge gained: " + soldierName + " (+" + knowledgeAmount + ")");
    }
    
    // =============================================================================
    // ALIEN POD SYSTEM
    // =============================================================================
    
    /**
     * Add alien pod to combat
     */
    public void addAlienPod(AlienPod pod) {
        alienPods.add(pod);
        podPositions.put(pod.getPodId(), pod.getPodPosition());
        logCombatEvent("Alien Pod added: " + pod.getPodId() + " (" + pod.getPodType() + ")");
    }
    
    /**
     * Check pod activation
     */
    public void checkPodActivation(Position playerPosition) {
        for (AlienPod pod : alienPods) {
            if (pod.shouldActivate(playerPosition)) {
                pod.activatePod();
                logCombatEvent("Pod activated: " + pod.getPodId());
            }
        }
    }
    
    /**
     * Use pod ability
     */
    public boolean usePodAbility(AlienPod pod, String abilityName, List<Unit> targets) {
        try {
            pod.usePodAbility(abilityName, targets);
            logCombatEvent("Pod ability used: " + abilityName + " by " + pod.getPodId());
            return true;
        } catch (Exception e) {
            logCombatEvent("Pod ability failed: " + abilityName + " by " + pod.getPodId());
            return false;
        }
    }
    
    // =============================================================================
    // MISSION TIMER SYSTEM
    // =============================================================================
    
    /**
     * Add mission timer
     */
    public void addMissionTimer(MissionTimer timer) {
        missionTimers.add(timer);
        timerValues.put(timer.getTimerId(), timer.getTimerValue());
        logCombatEvent("Mission Timer added: " + timer.getTimerId() + " (" + timer.getTimerType() + ")");
    }
    
    /**
     * Advance all timers
     */
    public void advanceAllTimers() {
        for (MissionTimer timer : missionTimers) {
            timer.advanceTimer();
            if (timer.isExpired()) {
                logCombatEvent("Timer expired: " + timer.getTimerId());
            }
        }
    }
    
    /**
     * Extend timer
     */
    public boolean extendTimer(String timerId, int turns) {
        for (MissionTimer timer : missionTimers) {
            if (timer.getTimerId().equals(timerId)) {
                return timer.extendTimer(turns);
            }
        }
        return false;
    }
    
    // =============================================================================
    // REINFORCEMENT SYSTEM
    // =============================================================================
    
    /**
     * Add reinforcement
     */
    public void addReinforcement(AlienReinforcement reinforcement) {
        reinforcements.add(reinforcement);
        reinforcementValues.put(reinforcement.getReinforcementId(), reinforcement.getReinforcementValue());
        logCombatEvent("Reinforcement added: " + reinforcement.getReinforcementId());
    }
    
    /**
     * Process reinforcements
     */
    public void processReinforcements() {
        for (AlienReinforcement reinforcement : reinforcements) {
            reinforcement.processReinforcementTurn();
            if (reinforcement.isReadyToSpawn()) {
                logCombatEvent("Reinforcement ready to spawn: " + reinforcement.getReinforcementId());
            }
        }
    }
    
    // =============================================================================
    // CONCEALMENT AND AMBUSH SYSTEM
    // =============================================================================
    
    /**
     * Break concealment
     */
    public void breakConcealment() {
        if (isConcealmentActive) {
            isConcealmentActive = false;
            concealmentBreaks++;
            logCombatEvent("Concealment broken");
        }
    }
    
    /**
     * Activate ambush
     */
    public void activateAmbush() {
        if (!isAmbushActive && !isConcealmentActive) {
            isAmbushActive = true;
            ambushCount++;
            logCombatEvent("Ambush activated");
        }
    }
    
    /**
     * Deactivate ambush
     */
    public void deactivateAmbush() {
        if (isAmbushActive) {
            isAmbushActive = false;
            logCombatEvent("Ambush deactivated");
        }
    }
    
    // =============================================================================
    // COMBAT PROCESSING
    // =============================================================================
    
    /**
     * Process combat turn
     */
    @Override
    public void endTurn() {
        super.endTurn();
        
        // Process all XCOM 2 systems
        processRulerReactions();
        processReinforcements();
        advanceAllTimers();
        
        // Process pod turns
        for (AlienPod pod : alienPods) {
            pod.processPodTurn();
        }
        
        // Process ruler turns
        for (AlienRuler ruler : alienRulers) {
            ruler.updateCooldowns();
        }
        
        // Process chosen turns
        for (Chosen chosen : this.chosen) {
            chosen.processTurn();
        }
        
        logCombatEvent("Combat turn processed");
    }
    
    /**
     * Log combat event
     */
    public void logCombatEvent(String event) {
        combatEvents.add(event);
        eventHistory.put(event, eventHistory.getOrDefault(event, 0) + 1);
    }
    
    /**
     * Get combat summary
     */
    public String getCombatSummary() {
        StringBuilder summary = new StringBuilder();
        summary.append("=== ULTIMATE COMBAT SUMMARY ===\n");
        summary.append("Total Mechanics Implemented: ").append(totalMechanics).append("\n");
        summary.append("Alien Rulers: ").append(alienRulers.size()).append("\n");
        summary.append("Chosen: ").append(chosen.size()).append("\n");
        summary.append("Alien Pods: ").append(alienPods.size()).append("\n");
        summary.append("Mission Timers: ").append(missionTimers.size()).append("\n");
        summary.append("Reinforcements: ").append(reinforcements.size()).append("\n");
        summary.append("Concealment Active: ").append(isConcealmentActive).append("\n");
        summary.append("Ambush Active: ").append(isAmbushActive).append("\n");
        summary.append("Concealment Breaks: ").append(concealmentBreaks).append("\n");
        summary.append("Ambush Count: ").append(ambushCount).append("\n");
        summary.append("Combat Events: ").append(combatEvents.size()).append("\n");
        summary.append("========================================\n");
        
        return summary.toString();
    }
    
    /**
     * Get recent combat events
     */
    public List<String> getRecentCombatEvents(int count) {
        int startIndex = Math.max(0, combatEvents.size() - count);
        return combatEvents.subList(startIndex, combatEvents.size());
    }
    
    /**
     * Check if combat is complete
     */
    public boolean isCombatComplete() {
        // Check if all enemies are defeated
        boolean allRulersDefeated = alienRulers.stream().noneMatch(Unit::isAlive);
        boolean allChosenDefeated = chosen.stream().noneMatch(Unit::isAlive);
        boolean allPodsDefeated = alienPods.stream().allMatch(AlienPod::isDefeated);
        
        return allRulersDefeated && allChosenDefeated && allPodsDefeated;
    }
    
    /**
     * Get combat status
     */
    public String getCombatStatus() {
        StringBuilder status = new StringBuilder();
        status.append("=== COMBAT STATUS ===\n");
        status.append("Concealment: ").append(isConcealmentActive ? "Active" : "Broken").append("\n");
        status.append("Ambush: ").append(isAmbushActive ? "Active" : "Inactive").append("\n");
        status.append("Active Rulers: ").append(alienRulers.stream().filter(Unit::isAlive).count()).append("\n");
        status.append("Active Chosen: ").append(chosen.stream().filter(Unit::isAlive).count()).append("\n");
        status.append("Active Pods: ").append(alienPods.stream().filter(pod -> !pod.isDefeated()).count()).append("\n");
        status.append("Active Timers: ").append(missionTimers.stream().filter(MissionTimer::isActive).count()).append("\n");
        status.append("Active Reinforcements: ").append(reinforcements.stream().filter(AlienReinforcement::isActive).count()).append("\n");
        status.append("===================\n");
        
        return status.toString();
    }
}
