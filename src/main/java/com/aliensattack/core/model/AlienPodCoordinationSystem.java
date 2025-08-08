package com.aliensattack.core.model;

import com.aliensattack.core.enums.PodType;
import com.aliensattack.core.enums.VisibilityType;
import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.*;

/**
 * Alien Pod Coordination System - XCOM 2 Mechanic
 * Implements the system where alien pods coordinate their actions and activate together
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class AlienPodCoordinationSystem {
    
    private String podId;
    private String podName;
    private PodType podType;
    private List<Unit> podMembers;
    private Map<String, Unit> memberRoles;
    private boolean isActivated;
    private boolean isCoordinated;
    private int coordinationLevel;
    private int maxCoordinationLevel;
    private List<String> podTactics;
    private Map<String, Integer> tacticSuccessCount;
    private Map<String, Integer> tacticFailureCount;
    private Unit podLeader;
    private Map<String, Position> memberPositions;
    private List<String> activeTactics;
    private Map<String, Integer> tacticCooldowns;
    private boolean isReinforcing;
    private int reinforcementProgress;
    private int maxReinforcementTime;
    private List<String> calledReinforcements;
    private Map<String, Integer> reinforcementTimers;
    
    public AlienPodCoordinationSystem(String podId, String podName, PodType podType) {
        this.podId = podId;
        this.podName = podName;
        this.podType = podType;
        this.podMembers = new ArrayList<>();
        this.memberRoles = new HashMap<>();
        this.isActivated = false;
        this.isCoordinated = false;
        this.coordinationLevel = 1;
        this.maxCoordinationLevel = 5;
        this.podTactics = new ArrayList<>();
        this.tacticSuccessCount = new HashMap<>();
        this.tacticFailureCount = new HashMap<>();
        this.podLeader = null;
        this.memberPositions = new HashMap<>();
        this.activeTactics = new ArrayList<>();
        this.tacticCooldowns = new HashMap<>();
        this.isReinforcing = false;
        this.reinforcementProgress = 0;
        this.maxReinforcementTime = 3;
        this.calledReinforcements = new ArrayList<>();
        this.reinforcementTimers = new HashMap<>();
        
        initializePod();
    }
    
    /**
     * Initialize pod with basic tactics and coordination
     */
    private void initializePod() {
        // Add basic pod tactics based on pod type
        switch (podType) {
            case PATROL:
                podTactics.addAll(Arrays.asList("patrol_formation", "alert_response", "standard_engagement"));
                break;
            case GUARD:
                podTactics.addAll(Arrays.asList("defensive_formation", "cover_fire", "position_holding"));
                break;
            case REINFORCEMENT:
                podTactics.addAll(Arrays.asList("rapid_deployment", "overwhelm_attack", "coordinated_assault"));
                break;
            case ELITE:
                podTactics.addAll(Arrays.asList("elite_formation", "superior_tactics", "advanced_coordination"));
                break;
            case BOSS:
                podTactics.addAll(Arrays.asList("boss_abilities", "command_presence", "overwhelming_force"));
                break;
            case AMBUSH:
                podTactics.addAll(Arrays.asList("ambush_setup", "concealed_attack", "surprise_assault"));
                break;
            case PURSUIT:
                podTactics.addAll(Arrays.asList("hunting_tactics", "tracking_abilities", "relentless_pursuit"));
                break;
            case DEFENSIVE:
                podTactics.addAll(Arrays.asList("defensive_formation", "cover_fire", "tactical_retreat"));
                break;
            case AGGRESSIVE:
                podTactics.addAll(Arrays.asList("flanking_maneuver", "overwhelm_attack", "coordinated_assault"));
                break;
            case STEALTH:
                podTactics.addAll(Arrays.asList("ambush_setup", "concealed_attack", "silent_coordination"));
                break;
            case SUPPORT:
                podTactics.addAll(Arrays.asList("healing_support", "buff_ally", "area_control"));
                break;
            case ARTILLERY:
                podTactics.addAll(Arrays.asList("artillery_strike", "ranged_support", "area_bombardment"));
                break;
            case MELEE:
                podTactics.addAll(Arrays.asList("close_combat", "melee_rush", "physical_assault"));
                break;
            case FLYING:
                podTactics.addAll(Arrays.asList("aerial_maneuvers", "air_superiority", "dive_attacks"));
                break;
            case UNDERGROUND:
                podTactics.addAll(Arrays.asList("burrow_attack", "underground_movement", "tunnel_warfare"));
                break;
            case WATER:
                podTactics.addAll(Arrays.asList("aquatic_combat", "water_maneuvers", "submerged_attack"));
                break;
            case URBAN:
                podTactics.addAll(Arrays.asList("urban_warfare", "building_combat", "street_fighting"));
                break;
            case RURAL:
                podTactics.addAll(Arrays.asList("open_field_tactics", "rural_maneuvers", "terrain_advantage"));
                break;
            case INDUSTRIAL:
                podTactics.addAll(Arrays.asList("industrial_combat", "machinery_warfare", "factory_tactics"));
                break;
            case LABORATORY:
                podTactics.addAll(Arrays.asList("research_defense", "experimental_weapons", "lab_warfare"));
                break;
            case MILITARY:
                podTactics.addAll(Arrays.asList("military_discipline", "tactical_superiority", "combat_expertise"));
                break;
            case CIVILIAN:
                podTactics.addAll(Arrays.asList("blend_in", "civilian_disguise", "stealth_operations"));
                break;
            case HUNTER:
                podTactics.addAll(Arrays.asList("tracking_abilities", "hunting_tactics", "prey_pursuit"));
                break;
            case SCAVENGER:
                podTactics.addAll(Arrays.asList("resource_gathering", "scavenging_operations", "material_collection"));
                break;
            case CONSTRUCTOR:
                podTactics.addAll(Arrays.asList("construction_operations", "building_tactics", "structure_warfare"));
                break;
            case INFILTRATOR:
                podTactics.addAll(Arrays.asList("stealth_infiltration", "sneak_operations", "covert_warfare"));
                break;
            case INTERROGATOR:
                podTactics.addAll(Arrays.asList("capture_operations", "interrogation_tactics", "prisoner_handling"));
                break;
            case EXECUTIONER:
                podTactics.addAll(Arrays.asList("elimination_tactics", "execution_operations", "target_elimination"));
                break;
            case SPECIALIST:
                podTactics.addAll(Arrays.asList("specialized_attack", "ability_coordination", "tactical_superiority"));
                break;
        }
        
        // Initialize tactic tracking
        for (String tactic : podTactics) {
            tacticSuccessCount.put(tactic, 0);
            tacticFailureCount.put(tactic, 0);
            tacticCooldowns.put(tactic, 0);
        }
    }
    
    /**
     * Add member to pod
     */
    public void addMember(Unit member, String role) {
        if (!podMembers.contains(member)) {
            podMembers.add(member);
            memberRoles.put(member.getId(), member);
            
            // Set pod leader if not already set
            if (podLeader == null) {
                podLeader = member;
            }
            
            // Update member positions
            memberPositions.put(member.getId(), member.getPosition());
        }
    }
    
    /**
     * Remove member from pod
     */
    public void removeMember(Unit member) {
        podMembers.remove(member);
        memberRoles.remove(member.getId());
        memberPositions.remove(member.getId());
        
        // Reassign leader if current leader is removed
        if (podLeader != null && podLeader.equals(member)) {
            podLeader = podMembers.isEmpty() ? null : podMembers.get(0);
        }
    }
    
    /**
     * Activate pod when one member is spotted
     */
    public void activatePod(Unit spottedMember) {
        if (!isActivated) {
            isActivated = true;
            
            // Activate all pod members
            for (Unit member : podMembers) {
                activateMember(member);
            }
            
            // Choose initial tactic
            chooseInitialTactic();
        }
    }
    
    /**
     * Activate individual member
     */
    private void activateMember(Unit member) {
        // Set member to active state
        member.setVisibility(VisibilityType.CLEAR);
        
        // Apply pod bonuses
        applyPodBonuses(member);
    }
    
    /**
     * Apply pod bonuses to member
     */
    private void applyPodBonuses(Unit member) {
        // Coordination bonus
        if (isCoordinated) {
            member.setCriticalChance(member.getCriticalChance() + coordinationLevel * 5);
            member.setOverwatchChance(member.getOverwatchChance() + coordinationLevel * 3);
        }
        
        // Pod type bonuses
        switch (podType) {
            case AGGRESSIVE:
                member.setAttackDamage(member.getAttackDamage() + 2);
                break;
            case DEFENSIVE:
                // Add defensive bonuses
                break;
            case SUPPORT:
                // Add support bonuses
                break;
            case STEALTH:
                // Add stealth bonuses
                break;
            case SPECIALIST:
                // Add specialist bonuses
                break;
            case PATROL:
                // Add patrol bonuses
                break;
            case GUARD:
                // Add guard bonuses
                break;
            case REINFORCEMENT:
                // Add reinforcement bonuses
                break;
            case ELITE:
                // Add elite bonuses
                break;
            case BOSS:
                // Add boss bonuses
                break;
            case AMBUSH:
                // Add ambush bonuses
                break;
            case PURSUIT:
                // Add pursuit bonuses
                break;
            case ARTILLERY:
                // Add artillery bonuses
                break;
            case MELEE:
                // Add melee bonuses
                break;
            case FLYING:
                // Add flying bonuses
                break;
            case UNDERGROUND:
                // Add underground bonuses
                break;
            case WATER:
                // Add water bonuses
                break;
            case URBAN:
                // Add urban bonuses
                break;
            case RURAL:
                // Add rural bonuses
                break;
            case INDUSTRIAL:
                // Add industrial bonuses
                break;
            case LABORATORY:
                // Add laboratory bonuses
                break;
            case MILITARY:
                // Add military bonuses
                break;
            case CIVILIAN:
                // Add civilian bonuses
                break;
            case HUNTER:
                // Add hunter bonuses
                break;
            case SCAVENGER:
                // Add scavenger bonuses
                break;
            case CONSTRUCTOR:
                // Add constructor bonuses
                break;
            case INFILTRATOR:
                // Add infiltrator bonuses
                break;
            case INTERROGATOR:
                // Add interrogator bonuses
                break;
            case EXECUTIONER:
                // Add executioner bonuses
                break;
        }
    }
    
    /**
     * Choose initial tactic when pod activates
     */
    private void chooseInitialTactic() {
        String bestTactic = getMostSuccessfulTactic();
        if (bestTactic != null) {
            activeTactics.add(bestTactic);
        }
    }
    
    /**
     * Coordinate pod actions
     */
    public void coordinatePodActions() {
        if (isActivated && podMembers.size() >= 2) {
            isCoordinated = true;
            coordinationLevel = Math.min(coordinationLevel + 1, maxCoordinationLevel);
            
            // Execute coordinated actions
            executeCoordinatedActions();
        }
    }
    
    /**
     * Execute coordinated actions
     */
    private void executeCoordinatedActions() {
        for (String tactic : activeTactics) {
            if (canExecuteTactic(tactic)) {
                executeTactic(tactic);
            }
        }
    }
    
    /**
     * Check if tactic can be executed
     */
    private boolean canExecuteTactic(String tactic) {
        return tacticCooldowns.getOrDefault(tactic, 0) <= 0;
    }
    
    /**
     * Execute specific tactic
     */
    private void executeTactic(String tactic) {
        switch (tactic) {
            case "flanking_maneuver":
                executeFlankingManeuver();
                break;
            case "overwhelm_attack":
                executeOverwhelmAttack();
                break;
            case "coordinated_assault":
                executeCoordinatedAssault();
                break;
            case "defensive_formation":
                executeDefensiveFormation();
                break;
            case "cover_fire":
                executeCoverFire();
                break;
            case "tactical_retreat":
                executeTacticalRetreat();
                break;
            case "healing_support":
                executeHealingSupport();
                break;
            case "buff_ally":
                executeBuffAlly();
                break;
            case "area_control":
                executeAreaControl();
                break;
            case "ambush_setup":
                executeAmbushSetup();
                break;
            case "concealed_attack":
                executeConcealedAttack();
                break;
            case "silent_coordination":
                executeSilentCoordination();
                break;
            case "specialized_attack":
                executeSpecializedAttack();
                break;
            case "ability_coordination":
                executeAbilityCoordination();
                break;
            case "tactical_superiority":
                executeTacticalSuperiority();
                break;
        }
        
        // Set cooldown for tactic
        setTacticCooldown(tactic);
    }
    
    /**
     * Execute flanking maneuver
     */
    private void executeFlankingManeuver() {
        // Coordinate movement to flank positions
        for (Unit member : podMembers) {
            Position flankPosition = calculateFlankPosition(member);
            if (flankPosition != null) {
                member.setPosition(flankPosition);
            }
        }
    }
    
    /**
     * Execute overwhelm attack
     */
    private void executeOverwhelmAttack() {
        // All members attack the same target
        Unit target = findPriorityTarget();
        if (target != null) {
            for (Unit member : podMembers) {
                if (member != null && member.isAlive()) {
                    // Overwhelm: all members attack the same target with small coordination bonuses
                    if (member.getWeapon() != null) {
                        int bonusAccuracy = coordinationLevel * 5; // modest hit bonus from coordination
                        int bonusDamage = Math.max(1, podMembers.size() / 2); // scale with pod size
                        int accuracy = member.getWeapon().getAccuracy() + bonusAccuracy;
                        int damage = member.getWeapon().getBaseDamage() + bonusDamage;

                        int roll = (int) (Math.random() * 100) + 1;
                        if (roll <= accuracy) {
                            target.takeDamageWithArmor(damage);
                        }
                    }
                }
            }
        }
    }
    
    /**
     * Execute coordinated assault
     */
    private void executeCoordinatedAssault() {
        // Synchronized attack with timing bonuses
        for (Unit member : podMembers) {
            if (member != null && member.isAlive()) {
                // Coordinated assault: grant small, scaling accuracy and damage bonuses
                int accBonus = 2 + coordinationLevel * 2;
                int dmgBonus = 1 + Math.max(1, podMembers.size() / 3);
                if (member.getWeapon() != null) {
                    member.getWeapon().setAccuracy(member.getWeapon().getAccuracy() + accBonus);
                    member.getWeapon().setBaseDamage(member.getWeapon().getBaseDamage() + dmgBonus);
                }
                member.setInitiative(member.getInitiative() + 1);
                System.out.println(member.getName() + " receives coordinated assault bonuses");
            }
        }
    }
    
    /**
     * Execute defensive formation
     */
    private void executeDefensiveFormation() {
        // Form defensive positions
        for (Unit member : podMembers) {
            Position defensivePosition = calculateDefensivePosition(member);
            if (defensivePosition != null) {
                member.setPosition(defensivePosition);
            }
        }
    }
    
    /**
     * Execute cover fire
     */
    private void executeCoverFire() {
        // Provide covering fire for allies
        for (Unit member : podMembers) {
            member.setOverwatching(true);
        }
    }
    
    /**
     * Execute tactical retreat
     */
    private void executeTacticalRetreat() {
        // Retreat to safer positions
        for (Unit member : podMembers) {
            Position retreatPosition = calculateRetreatPosition(member);
            if (retreatPosition != null) {
                member.setPosition(retreatPosition);
            }
        }
    }
    
    /**
     * Execute healing support
     */
    private void executeHealingSupport() {
        // Heal wounded pod members
        for (Unit member : podMembers) {
            if (member.getCurrentHealth() < member.getMaxHealth()) {
                // Apply healing
            }
        }
    }
    
    /**
     * Execute buff ally
     */
    private void executeBuffAlly() {
        // Apply buffs to pod members
        for (Unit member : podMembers) {
            if (member != null && member.isAlive()) {
                // Simple team buff: slight accuracy/damage boost and minor heal
                if (member.getWeapon() != null) {
                    member.getWeapon().setAccuracy(member.getWeapon().getAccuracy() + 3);
                    member.getWeapon().setBaseDamage(member.getWeapon().getBaseDamage() + 2);
                }
                member.heal(1);
                member.setOverwatchChance(member.getOverwatchChance() + 5);
                System.out.println(member.getName() + " receives support buffs");
            }
        }
    }
    
    /**
     * Execute area control
     */
    private void executeAreaControl() {
        // Control strategic areas
        for (Unit member : podMembers) {
            Position controlPosition = calculateControlPosition(member);
            if (controlPosition != null) {
                member.setPosition(controlPosition);
            }
        }
    }
    
    /**
     * Execute ambush setup
     */
    private void executeAmbushSetup() {
        // Set up ambush positions
        for (Unit member : podMembers) {
            member.setConcealed(true);
        }
    }
    
    /**
     * Execute concealed attack
     */
    private void executeConcealedAttack() {
        // Attack while maintaining concealment
        Unit target = findPriorityTarget();
        if (target == null) {
            return;
        }
        for (Unit member : podMembers) {
            if (member != null && member.isAlive() && member.isConcealed() && member.getWeapon() != null) {
                // Concealed attack: bonus accuracy and crit, do not break concealment here
                int accuracyBonus = 15;
                int critBonus = 25;
                int accuracy = member.getWeapon().getAccuracy() + accuracyBonus;
                int critChance = member.getCriticalChance() + critBonus;
                int damage = member.getWeapon().getBaseDamage();

                int roll = (int) (Math.random() * 100) + 1;
                if (roll <= accuracy) {
                    int critRoll = (int) (Math.random() * 100) + 1;
                    if (critRoll <= critChance) {
                        int mult = Math.max(2, member.getCriticalDamageMultiplier());
                        damage *= mult;
                    }
                    target.takeDamageWithArmor(damage);
                }
            }
        }
    }
    
    /**
     * Execute silent coordination
     */
    private void executeSilentCoordination() {
        // Coordinate without breaking concealment
        for (Unit member : podMembers) {
            if (member != null && member.isAlive() && member.isConcealed()) {
                // Silent coordination: small mobility and accuracy boost while remaining concealed
                member.setMovementRange(member.getMovementRange() + 1);
                if (member.getWeapon() != null) {
                    member.getWeapon().setAccuracy(member.getWeapon().getAccuracy() + 5);
                }
                System.out.println(member.getName() + " is coordinating silently under concealment");
            }
        }
    }
    
    /**
     * Execute specialized attack
     */
    private void executeSpecializedAttack() {
        // Use specialized abilities
        Unit target = findPriorityTarget();
        if (target == null) {
            return;
        }
        for (Unit member : podMembers) {
            if (member != null && member.isAlive()) {
                // Prefer psionic specialization if available
                if (member.getPsionicAbilities() != null && !member.getPsionicAbilities().isEmpty()) {
                    int psiPower = Math.max(0, member.getPsiStrength() - target.getPsiResistance());
                    int damage = Math.max(1, psiPower / 8);
                    target.takeDamage(damage); // mental-type hit, bypass armor
                    System.out.println(member.getName() + " performs a psionic specialized attack for " + damage + " damage");
                    continue;
                }
                // Otherwise, perform a precision boosted weapon attack
                if (member.getWeapon() != null) {
                    int accuracy = member.getWeapon().getAccuracy() + 10;
                    int damage = member.getWeapon().getBaseDamage() + 3;
                    int roll = (int) (Math.random() * 100) + 1;
                    if (roll <= accuracy) {
                        target.takeDamageWithArmor(damage);
                    }
                    System.out.println(member.getName() + " performs a specialized precision attack");
                }
            }
        }
    }
    
    /**
     * Execute ability coordination
     */
    private void executeAbilityCoordination() {
        // Coordinate special abilities
        for (Unit member : podMembers) {
            if (member != null && member.isAlive()) {
                // Minimal coordination: enhance ability users and precision shooters
                if (member.getPsionicAbilities() != null && !member.getPsionicAbilities().isEmpty()) {
                    member.setPsiStrength(member.getPsiStrength() + (2 + coordinationLevel));
                }
                if (member.getWeapon() != null) {
                    member.getWeapon().setAccuracy(member.getWeapon().getAccuracy() + 3 + coordinationLevel);
                }
                member.setInitiative(member.getInitiative() + 1);
                System.out.println(member.getName() + " coordinates abilities with the pod");
            }
        }
    }
    
    /**
     * Execute tactical superiority
     */
    private void executeTacticalSuperiority() {
        // Gain tactical advantages
        for (Unit member : podMembers) {
            if (member != null && member.isAlive()) {
                // Tactical superiority: situational boosts to initiative, accuracy, overwatch, and mobility
                member.setInitiative(member.getInitiative() + 2);
                member.setOverwatchChance(member.getOverwatchChance() + 10);
                member.setMovementRange(member.getMovementRange() + 1);
                if (member.getWeapon() != null) {
                    member.getWeapon().setAccuracy(member.getWeapon().getAccuracy() + 6);
                }
                System.out.println(member.getName() + " gains tactical superiority bonuses");
            }
        }
    }
    
    /**
     * Calculate flank position
     */
    private Position calculateFlankPosition(Unit member) {
        // Calculate optimal flanking position
        return new Position(member.getPosition().getX() + 1, member.getPosition().getY());
    }
    
    /**
     * Find priority target
     */
    private Unit findPriorityTarget() {
        // Find highest priority target
        return null; // Placeholder
    }
    
    /**
     * Calculate defensive position
     */
    private Position calculateDefensivePosition(Unit member) {
        // Calculate optimal defensive position
        return new Position(member.getPosition().getX(), member.getPosition().getY());
    }
    
    /**
     * Calculate retreat position
     */
    private Position calculateRetreatPosition(Unit member) {
        // Calculate safe retreat position
        return new Position(member.getPosition().getX() - 1, member.getPosition().getY());
    }
    
    /**
     * Calculate control position
     */
    private Position calculateControlPosition(Unit member) {
        // Calculate strategic control position
        return new Position(member.getPosition().getX(), member.getPosition().getY());
    }
    
    /**
     * Set tactic cooldown
     */
    private void setTacticCooldown(String tactic) {
        int cooldown = getTacticCooldown(tactic);
        tacticCooldowns.put(tactic, cooldown);
    }
    
    /**
     * Get tactic cooldown
     */
    private int getTacticCooldown(String tactic) {
        switch (tactic) {
            case "flanking_maneuver":
                return 3;
            case "overwhelm_attack":
                return 4;
            case "coordinated_assault":
                return 5;
            case "defensive_formation":
                return 2;
            case "cover_fire":
                return 3;
            case "tactical_retreat":
                return 6;
            case "healing_support":
                return 4;
            case "buff_ally":
                return 3;
            case "area_control":
                return 4;
            case "ambush_setup":
                return 5;
            case "concealed_attack":
                return 3;
            case "silent_coordination":
                return 4;
            case "specialized_attack":
                return 4;
            case "ability_coordination":
                return 5;
            case "tactical_superiority":
                return 6;
            default:
                return 3;
        }
    }
    
    /**
     * Get most successful tactic
     */
    public String getMostSuccessfulTactic() {
        String bestTactic = null;
        double bestSuccessRate = 0.0;
        
        for (String tactic : podTactics) {
            double successRate = getTacticSuccessRate(tactic);
            if (successRate > bestSuccessRate) {
                bestSuccessRate = successRate;
                bestTactic = tactic;
            }
        }
        
        return bestTactic;
    }
    
    /**
     * Get tactic success rate
     */
    public double getTacticSuccessRate(String tactic) {
        int successes = tacticSuccessCount.getOrDefault(tactic, 0);
        int failures = tacticFailureCount.getOrDefault(tactic, 0);
        int total = successes + failures;
        
        return total > 0 ? (double) successes / total : 0.0;
    }
    
    /**
     * Record tactic success
     */
    public void recordTacticSuccess(String tactic) {
        int current = tacticSuccessCount.getOrDefault(tactic, 0);
        tacticSuccessCount.put(tactic, current + 1);
    }
    
    /**
     * Record tactic failure
     */
    public void recordTacticFailure(String tactic) {
        int current = tacticFailureCount.getOrDefault(tactic, 0);
        tacticFailureCount.put(tactic, current + 1);
    }
    
    /**
     * Call reinforcements
     */
    public void callReinforcements() {
        if (!isReinforcing) {
            isReinforcing = true;
            reinforcementProgress = 0;
            
            // Add reinforcement to called list
            String reinforcementId = "REINFORCEMENT_" + System.currentTimeMillis();
            calledReinforcements.add(reinforcementId);
            reinforcementTimers.put(reinforcementId, maxReinforcementTime);
        }
    }
    
    /**
     * Update reinforcement progress
     */
    public void updateReinforcementProgress() {
        if (isReinforcing) {
            reinforcementProgress++;
            
            if (reinforcementProgress >= maxReinforcementTime) {
                completeReinforcement();
            }
        }
        
        // Update reinforcement timers
        for (String reinforcementId : new ArrayList<>(reinforcementTimers.keySet())) {
            int currentTimer = reinforcementTimers.get(reinforcementId);
            if (currentTimer > 0) {
                reinforcementTimers.put(reinforcementId, currentTimer - 1);
            }
        }
    }
    
    /**
     * Complete reinforcement
     */
    private void completeReinforcement() {
        isReinforcing = false;
        reinforcementProgress = 0;
        
        // Add new members to pod
        // This would be implemented based on reinforcement logic
    }
    
    /**
     * Update tactic cooldowns
     */
    public void updateTacticCooldowns() {
        for (String tactic : tacticCooldowns.keySet()) {
            int currentCooldown = tacticCooldowns.get(tactic);
            if (currentCooldown > 0) {
                tacticCooldowns.put(tactic, currentCooldown - 1);
            }
        }
    }
    
    /**
     * Get pod size
     */
    public int getPodSize() {
        return podMembers.size();
    }
    
    /**
     * Check if pod is active
     */
    public boolean isActive() {
        return isActivated;
    }
    
    /**
     * Check if pod is coordinated
     */
    public boolean isCoordinated() {
        return isCoordinated;
    }
    
    /**
     * Get coordination level
     */
    public int getCoordinationLevel() {
        return coordinationLevel;
    }
    
    /**
     * Get pod leader
     */
    public Unit getPodLeader() {
        return podLeader;
    }
    
    /**
     * Get pod members
     */
    public List<Unit> getPodMembers() {
        return new ArrayList<>(podMembers);
    }
    
    /**
     * Get active tactics
     */
    public List<String> getActiveTactics() {
        return new ArrayList<>(activeTactics);
    }
}
