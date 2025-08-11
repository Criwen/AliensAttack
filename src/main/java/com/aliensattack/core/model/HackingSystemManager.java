package com.aliensattack.core.model;

import lombok.Getter;
import lombok.Setter;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Comprehensive hacking system manager for XCOM 2 tactical combat
 * Manages hacking terminals, technical abilities, and robot control
 */
@Getter
@Setter
public class HackingSystemManager {
    
    // Terminal management
    private Map<String, HackingTerminal> activeTerminals;
    private Map<String, Unit> terminalAssignments;
    private Map<String, Integer> terminalProgress;
    
    // Technical abilities
    private Map<String, SoldierAbility> technicalAbilities;
    private Map<String, List<SoldierAbility>> unitTechnicalAbilities;
    private Map<String, Integer> unitTechnicalSkill;
    
    // Robot control
    private Map<String, Unit> controlledRobots;
    private Map<String, Unit> robotControllers;
    private Map<String, Integer> controlDuration;
    private Map<String, Integer> controlStrength;
    
    // Hacking statistics
    private Map<String, Integer> hackingSuccesses;
    private Map<String, Integer> hackingFailures;
    private Map<String, Integer> technicalPoints;
    
    // System state
    private boolean isHackingEnabled;
    private int globalHackingDifficulty;
    private int globalTechnicalBonus;
    private Random random;
    
    public HackingSystemManager() {
        this.activeTerminals = new ConcurrentHashMap<>();
        this.terminalAssignments = new ConcurrentHashMap<>();
        this.terminalProgress = new ConcurrentHashMap<>();
        
        this.technicalAbilities = new ConcurrentHashMap<>();
        this.unitTechnicalAbilities = new ConcurrentHashMap<>();
        this.unitTechnicalSkill = new ConcurrentHashMap<>();
        
        this.controlledRobots = new ConcurrentHashMap<>();
        this.robotControllers = new ConcurrentHashMap<>();
        this.controlDuration = new ConcurrentHashMap<>();
        this.controlStrength = new ConcurrentHashMap<>();
        
        this.hackingSuccesses = new ConcurrentHashMap<>();
        this.hackingFailures = new ConcurrentHashMap<>();
        this.technicalPoints = new ConcurrentHashMap<>();
        
        this.isHackingEnabled = true;
        this.globalHackingDifficulty = 50;
        this.globalTechnicalBonus = 0;
        this.random = new Random();
        
        initializeTechnicalAbilities();
    }
    
    /**
     * Initialize technical abilities
     */
    private void initializeTechnicalAbilities() {
        // Combat Protocol
        SoldierAbility combatProtocol = new SoldierAbility("Combat Protocol", "Deal damage with gremlin", 1, 3);
        combatProtocol.setAsTechnicalAbility(15);
        technicalAbilities.put("combat_protocol", combatProtocol);
        
        // Aid Protocol
        SoldierAbility aidProtocol = new SoldierAbility("Aid Protocol", "Grant bonus action to ally", 1, 4);
        aidProtocol.setAsTechnicalAbility(10);
        technicalAbilities.put("aid_protocol", aidProtocol);
        
        // Medical Protocol
        SoldierAbility medicalProtocol = new SoldierAbility("Medical Protocol", "Heal ally with gremlin", 1, 5);
        medicalProtocol.setAsTechnicalAbility(20);
        technicalAbilities.put("medical_protocol", medicalProtocol);
        
        // Scanning Protocol
        SoldierAbility scanningProtocol = new SoldierAbility("Scanning Protocol", "Reveal hidden enemies", 1, 2);
        scanningProtocol.setAsTechnicalAbility(5);
        technicalAbilities.put("scanning_protocol", scanningProtocol);
        
        // Sabotage
        SoldierAbility sabotage = new SoldierAbility("Sabotage", "Disable enemy equipment", 2, 4);
        sabotage.setAsTechnicalAbility(25);
        technicalAbilities.put("sabotage", sabotage);
        
        // Override
        SoldierAbility override = new SoldierAbility("Override", "Take control of robotic units", 2, 6);
        override.setAsTechnicalAbility(30);
        technicalAbilities.put("override", override);
        
        // Repair
        SoldierAbility repair = new SoldierAbility("Repair", "Repair damaged equipment", 1, 3);
        repair.setAsTechnicalAbility(15);
        technicalAbilities.put("repair", repair);
        
        // Hack
        SoldierAbility hack = new SoldierAbility("Hack", "Hack enemy systems", 2, 5);
        hack.setAsHackingAbility(40);
        technicalAbilities.put("hack", hack);
    }
    
    /**
     * Register a unit for hacking system
     */
    public boolean registerHackingUnit(String unitId, int technicalSkill) {
        if (unitTechnicalSkill.containsKey(unitId)) {
            return false; // Already registered
        }
        
        unitTechnicalSkill.put(unitId, technicalSkill);
        unitTechnicalAbilities.put(unitId, new ArrayList<>());
        hackingSuccesses.put(unitId, 0);
        hackingFailures.put(unitId, 0);
        technicalPoints.put(unitId, 0);
        
        return true;
    }
    
    /**
     * Unlock technical ability for unit
     */
    public boolean unlockTechnicalAbility(String unitId, String abilityId) {
        SoldierAbility ability = technicalAbilities.get(abilityId);
        if (ability == null) {
            return false;
        }
        
        List<SoldierAbility> unitAbilities = unitTechnicalAbilities.get(unitId);
        if (unitAbilities == null) {
            return false;
        }
        
        if (unitAbilities.contains(ability)) {
            return false; // Already unlocked
        }
        
        unitAbilities.add(ability);
        return true;
    }
    
    /**
     * Use technical ability
     */
    public boolean useTechnicalAbility(String unitId, String abilityId, String targetId) {
        SoldierAbility ability = technicalAbilities.get(abilityId);
        if (ability == null || !ability.canUse()) {
            return false;
        }
        
        List<SoldierAbility> unitAbilities = unitTechnicalAbilities.get(unitId);
        if (unitAbilities == null || !unitAbilities.contains(ability)) {
            return false;
        }
        
        // Use ability
        ability.useAbility();
        
        // Apply technical effect
        if (ability.isTechnicalAbility()) {
            applyTechnicalEffect(unitId, targetId, ability);
        }
        
        if (ability.isHackingAbility()) {
            applyHackingEffect(unitId, targetId, ability);
        }
        
        return true;
    }
    
    /**
     * Apply technical effect
     */
    private void applyTechnicalEffect(String unitId, String targetId, SoldierAbility ability) {
        int technicalPower = ability.getTechnicalEffectPower();
        int unitSkill = unitTechnicalSkill.getOrDefault(unitId, 0);
        int totalEffect = technicalPower + unitSkill + globalTechnicalBonus;
        
        // Record technical points
        technicalPoints.put(unitId, technicalPoints.getOrDefault(unitId, 0) + totalEffect);
        
        // Apply effect based on ability type
        switch (ability.getName()) {
            case "Combat Protocol" -> applyCombatProtocol(unitId, targetId, totalEffect);
            case "Aid Protocol" -> applyAidProtocol(unitId, targetId, totalEffect);
            case "Medical Protocol" -> applyMedicalProtocol(unitId, targetId, totalEffect);
            case "Scanning Protocol" -> applyScanningProtocol(unitId, targetId, totalEffect);
            case "Sabotage" -> applySabotage(unitId, targetId, totalEffect);
            case "Override" -> applyOverride(unitId, targetId, totalEffect);
            case "Repair" -> applyRepair(unitId, targetId, totalEffect);
        }
    }
    
    /**
     * Apply hacking effect
     */
    private void applyHackingEffect(String unitId, String targetId, SoldierAbility ability) {
        int hackingPower = ability.getHackingSuccessChance();
        int unitSkill = unitTechnicalSkill.getOrDefault(unitId, 0);
        int totalPower = hackingPower + unitSkill;
        
        // Attempt hack
        int roll = random.nextInt(100);
        if (roll < totalPower) {
            // Hack successful
            hackingSuccesses.put(unitId, hackingSuccesses.getOrDefault(unitId, 0) + 1);
            applyHackingSuccess(unitId, targetId, totalPower);
        } else {
            // Hack failed
            hackingFailures.put(unitId, hackingFailures.getOrDefault(unitId, 0) + 1);
            applyHackingFailure(unitId, targetId, totalPower);
        }
    }
    
    /**
     * Apply combat protocol effect
     */
    private void applyCombatProtocol(String unitId, String targetId, int effect) {
        // Deal damage to target
        // This would integrate with combat system
        System.out.println("Combat Protocol applied: " + effect + " damage to " + targetId);
    }
    
    /**
     * Apply aid protocol effect
     */
    private void applyAidProtocol(String unitId, String targetId, int effect) {
        // Grant bonus action to target
        System.out.println("Aid Protocol applied: " + effect + " bonus action to " + targetId);
    }
    
    /**
     * Apply medical protocol effect
     */
    private void applyMedicalProtocol(String unitId, String targetId, int effect) {
        // Heal target
        System.out.println("Medical Protocol applied: " + effect + " healing to " + targetId);
    }
    
    /**
     * Apply scanning protocol effect
     */
    private void applyScanningProtocol(String unitId, String targetId, int effect) {
        // Reveal hidden enemies
        System.out.println("Scanning Protocol applied: " + effect + " detection range");
    }
    
    /**
     * Apply sabotage effect
     */
    private void applySabotage(String unitId, String targetId, int effect) {
        // Disable enemy equipment
        System.out.println("Sabotage applied: " + effect + " equipment damage to " + targetId);
    }
    
    /**
     * Apply override effect
     */
    private void applyOverride(String unitId, String targetId, int effect) {
        // Take control of robotic unit
        System.out.println("Override applied: " + effect + " control strength to " + targetId);
    }
    
    /**
     * Apply repair effect
     */
    private void applyRepair(String unitId, String targetId, int effect) {
        // Repair damaged equipment
        System.out.println("Repair applied: " + effect + " repair points to " + targetId);
    }
    
    /**
     * Apply hacking success
     */
    private void applyHackingSuccess(String unitId, String targetId, int power) {
        System.out.println("Hacking successful: " + power + " power applied to " + targetId);
    }
    
    /**
     * Apply hacking failure
     */
    private void applyHackingFailure(String unitId, String targetId, int power) {
        System.out.println("Hacking failed: " + power + " power attempted on " + targetId);
    }
    
    /**
     * Add hacking terminal
     */
    public boolean addHackingTerminal(String terminalId, HackingTerminal terminal) {
        if (activeTerminals.containsKey(terminalId)) {
            return false;
        }
        
        activeTerminals.put(terminalId, terminal);
        terminalProgress.put(terminalId, 0);
        return true;
    }
    
    /**
     * Assign unit to terminal
     */
    public boolean assignUnitToTerminal(String unitId, String terminalId) {
        if (!activeTerminals.containsKey(terminalId)) {
            return false;
        }
        
        terminalAssignments.put(terminalId, null); // Placeholder for Unit object
        return true;
    }
    
    /**
     * Attempt to hack terminal
     */
    public boolean attemptTerminalHack(String unitId, String terminalId, String hackOption) {
        HackingTerminal terminal = activeTerminals.get(terminalId);
        if (terminal == null || !terminal.isHackable()) {
            return false;
        }
        
        int unitSkill = unitTechnicalSkill.getOrDefault(unitId, 0);
        int successChance = terminal.getHackingSuccessChance() + unitSkill + globalTechnicalBonus;
        
        if (random.nextInt(100) < successChance) {
            // Hack successful
            terminal.hack(hackOption);
            terminalProgress.put(terminalId, terminalProgress.getOrDefault(terminalId, 0) + terminal.getHackingReward());
            hackingSuccesses.put(unitId, hackingSuccesses.getOrDefault(unitId, 0) + 1);
            return true;
        } else {
            // Hack failed
            terminalProgress.put(terminalId, terminalProgress.getOrDefault(terminalId, 0) + terminal.getHackingPenalty());
            hackingFailures.put(unitId, hackingFailures.getOrDefault(unitId, 0) + 1);
            return false;
        }
    }
    
    /**
     * Control robotic unit
     */
    public boolean controlRobot(String controllerId, String robotId, int controlStrength) {
        // This would integrate with Unit system
        controlledRobots.put(robotId, null); // Placeholder for Unit object
        robotControllers.put(robotId, null); // Placeholder for Unit object
        controlDuration.put(robotId, 3); // 3 turns control
        this.controlStrength.put(robotId, controlStrength);
        
        System.out.println("Robot " + robotId + " controlled by " + controllerId + " with strength " + controlStrength);
        return true;
    }
    
    /**
     * Release robot control
     */
    public boolean releaseRobotControl(String robotId) {
        if (!controlledRobots.containsKey(robotId)) {
            return false;
        }
        
        controlledRobots.remove(robotId);
        robotControllers.remove(robotId);
        controlDuration.remove(robotId);
        controlStrength.remove(robotId);
        
        System.out.println("Robot " + robotId + " control released");
        return true;
    }
    
    /**
     * Process robot control duration
     */
    public void processRobotControl() {
        List<String> expiredRobots = new ArrayList<>();
        
        for (Map.Entry<String, Integer> entry : controlDuration.entrySet()) {
            String robotId = entry.getKey();
            int duration = entry.getValue();
            
            duration--;
            if (duration <= 0) {
                // Control expired
                expiredRobots.add(robotId);
            } else {
                controlDuration.put(robotId, duration);
            }
        }
        
        // Release expired robots
        for (String robotId : expiredRobots) {
            releaseRobotControl(robotId);
        }
    }
    
    /**
     * Process terminal time limits
     */
    public void processTerminals() {
        for (HackingTerminal terminal : activeTerminals.values()) {
            terminal.processTime();
        }
    }
    
    /**
     * Get unit technical skill
     */
    public int getUnitTechnicalSkill(String unitId) {
        return unitTechnicalSkill.getOrDefault(unitId, 0);
    }
    
    /**
     * Get unit hacking statistics
     */
    public Map<String, Integer> getUnitHackingStats(String unitId) {
        Map<String, Integer> stats = new HashMap<>();
        stats.put("successes", hackingSuccesses.getOrDefault(unitId, 0));
        stats.put("failures", hackingFailures.getOrDefault(unitId, 0));
        stats.put("technicalPoints", technicalPoints.getOrDefault(unitId, 0));
        return stats;
    }
    
    /**
     * Get controlled robots
     */
    public Set<String> getControlledRobots() {
        return controlledRobots.keySet();
    }
    
    /**
     * Get active terminals
     */
    public Set<String> getActiveTerminals() {
        return activeTerminals.keySet();
    }
    
    /**
     * Check if unit can hack
     */
    public boolean canUnitHack(String unitId) {
        return unitTechnicalSkill.containsKey(unitId) && unitTechnicalSkill.get(unitId) > 0;
    }
    
    /**
     * Check if unit can control robots
     */
    public boolean canUnitControlRobots(String unitId) {
        List<SoldierAbility> abilities = unitTechnicalAbilities.get(unitId);
        if (abilities == null) {
            return false;
        }
        
        return abilities.stream().anyMatch(ability -> "Override".equals(ability.getName()));
    }
    
    /**
     * Get available technical abilities for unit
     */
    public List<SoldierAbility> getUnitTechnicalAbilities(String unitId) {
        return unitTechnicalAbilities.getOrDefault(unitId, new ArrayList<>());
    }
    
    /**
     * Get system status
     */
    public String getSystemStatus() {
        StringBuilder status = new StringBuilder();
        status.append("Hacking System Status:\n");
        status.append("Active Terminals: ").append(activeTerminals.size()).append("\n");
        status.append("Controlled Robots: ").append(controlledRobots.size()).append("\n");
        status.append("Registered Units: ").append(unitTechnicalSkill.size()).append("\n");
        status.append("Global Difficulty: ").append(globalHackingDifficulty).append("\n");
        status.append("Global Technical Bonus: ").append(globalTechnicalBonus).append("\n");
        return status.toString();
    }
}
