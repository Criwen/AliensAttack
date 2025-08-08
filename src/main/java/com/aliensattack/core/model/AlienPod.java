package com.aliensattack.core.model;

import com.aliensattack.core.enums.*;
import lombok.Getter;
import lombok.Setter;

import java.util.*;

/**
 * Alien Pod class representing coordinated groups of aliens
 */
@Getter
@Setter
public class AlienPod {
    
    private String podId;
    private PodType podType;
    private List<Unit> podMembers;
    private Position podPosition;
    private boolean isActive;
    private boolean isCoordinating;
    private boolean isReinforcing;
    private int escalationLevel;
    private Map<String, Integer> tacticCounters;
    private List<String> podTactics;
    private Map<String, Integer> tacticEffectiveness;
    private int reinforcementCount;
    private int maxReinforcements;
    private List<Position> reinforcementPositions;
    private Map<String, Integer> coordinationBonuses;
    private boolean isPatrolling;
    private boolean isAmbushing;
    private boolean isPursuing;
    private boolean isDefending;
    private boolean isAggressive;
    private boolean isStealth;
    private boolean isSupport;
    private boolean isArtillery;
    private boolean isMelee;
    private boolean isFlying;
    private boolean isUnderground;
    private boolean isWater;
    private boolean isUrban;
    private boolean isRural;
    private boolean isIndustrial;
    private boolean isLaboratory;
    private boolean isMilitary;
    private boolean isCivilian;
    private boolean isHunter;
    private boolean isScavenger;
    private boolean isConstructor;
    private boolean isInfiltrator;
    private boolean isInterrogator;
    private boolean isExecutioner;
    
    public AlienPod(String podId, PodType podType, Position position) {
        this.podId = podId;
        this.podType = podType;
        this.podMembers = new ArrayList<>();
        this.podPosition = position;
        this.isActive = false;
        this.isCoordinating = false;
        this.isReinforcing = false;
        this.escalationLevel = 1;
        this.tacticCounters = new HashMap<>();
        this.podTactics = new ArrayList<>();
        this.tacticEffectiveness = new HashMap<>();
        this.reinforcementCount = 0;
        this.maxReinforcements = 3;
        this.reinforcementPositions = new ArrayList<>();
        this.coordinationBonuses = new HashMap<>();
        
        initializePodBehavior();
    }
    
    /**
     * Initialize pod behavior based on type
     */
    private void initializePodBehavior() {
        switch (podType) {
            case PATROL:
                isPatrolling = true;
                podTactics.addAll(Arrays.asList("patrol", "investigate", "report"));
                break;
            case GUARD:
                isDefending = true;
                podTactics.addAll(Arrays.asList("defend", "alert", "reinforce"));
                break;
            case REINFORCEMENT:
                isReinforcing = true;
                podTactics.addAll(Arrays.asList("reinforce", "support", "cover"));
                break;
            case ELITE:
                isAggressive = true;
                podTactics.addAll(Arrays.asList("assault", "flank", "overwhelm"));
                break;
            case BOSS:
                isAggressive = true;
                podTactics.addAll(Arrays.asList("command", "coordinate", "dominate"));
                break;
            case AMBUSH:
                isAmbushing = true;
                podTactics.addAll(Arrays.asList("hide", "wait", "strike"));
                break;
            case PURSUIT:
                isPursuing = true;
                podTactics.addAll(Arrays.asList("track", "chase", "corner"));
                break;
            case DEFENSIVE:
                isDefending = true;
                podTactics.addAll(Arrays.asList("hold", "protect", "retreat"));
                break;
            case AGGRESSIVE:
                isAggressive = true;
                podTactics.addAll(Arrays.asList("attack", "charge", "overrun"));
                break;
            case STEALTH:
                isStealth = true;
                podTactics.addAll(Arrays.asList("sneak", "infiltrate", "assassinate"));
                break;
            case SUPPORT:
                isSupport = true;
                podTactics.addAll(Arrays.asList("heal", "buff", "shield"));
                break;
            case ARTILLERY:
                isArtillery = true;
                podTactics.addAll(Arrays.asList("bombard", "suppress", "snipe"));
                break;
            case MELEE:
                isMelee = true;
                podTactics.addAll(Arrays.asList("charge", "grapple", "maul"));
                break;
            case FLYING:
                isFlying = true;
                podTactics.addAll(Arrays.asList("aerial", "dive", "strafe"));
                break;
            case UNDERGROUND:
                isUnderground = true;
                podTactics.addAll(Arrays.asList("burrow", "tunnel", "emerge"));
                break;
            case WATER:
                isWater = true;
                podTactics.addAll(Arrays.asList("swim", "dive", "surface"));
                break;
            case URBAN:
                isUrban = true;
                podTactics.addAll(Arrays.asList("urban", "building", "street"));
                break;
            case RURAL:
                isRural = true;
                podTactics.addAll(Arrays.asList("rural", "field", "forest"));
                break;
            case INDUSTRIAL:
                isIndustrial = true;
                podTactics.addAll(Arrays.asList("industrial", "factory", "machinery"));
                break;
            case LABORATORY:
                isLaboratory = true;
                podTactics.addAll(Arrays.asList("research", "experiment", "analyze"));
                break;
            case MILITARY:
                isMilitary = true;
                podTactics.addAll(Arrays.asList("military", "tactical", "strategic"));
                break;
            case CIVILIAN:
                isCivilian = true;
                podTactics.addAll(Arrays.asList("blend", "infiltrate", "sabotage"));
                break;
            case HUNTER:
                isHunter = true;
                podTactics.addAll(Arrays.asList("hunt", "track", "kill"));
                break;
            case SCAVENGER:
                isScavenger = true;
                podTactics.addAll(Arrays.asList("scavenge", "collect", "salvage"));
                break;
            case CONSTRUCTOR:
                isConstructor = true;
                podTactics.addAll(Arrays.asList("build", "construct", "fortify"));
                break;
            case INFILTRATOR:
                isInfiltrator = true;
                podTactics.addAll(Arrays.asList("infiltrate", "sneak", "sabotage"));
                break;
            case INTERROGATOR:
                isInterrogator = true;
                podTactics.addAll(Arrays.asList("capture", "interrogate", "extract"));
                break;
            case EXECUTIONER:
                isExecutioner = true;
                podTactics.addAll(Arrays.asList("execute", "eliminate", "terminate"));
                break;
            case SPECIALIST:
                isSupport = true;
                podTactics.addAll(Arrays.asList("specialize", "analyze", "adapt"));
                break;
        }
        
        // Initialize tactic effectiveness
        for (String tactic : podTactics) {
            tacticEffectiveness.put(tactic, 50); // Start with 50% effectiveness
        }
        
        // Initialize coordination bonuses
        coordinationBonuses.put("accuracy", 10);
        coordinationBonuses.put("damage", 5);
        coordinationBonuses.put("movement", 2);
        coordinationBonuses.put("defense", 15);
    }
    
    /**
     * Add member to pod
     */
    public void addMember(Unit member) {
        if (!podMembers.contains(member)) {
            podMembers.add(member);
            updatePodPosition();
        }
    }
    
    /**
     * Remove member from pod
     */
    public void removeMember(Unit member) {
        podMembers.remove(member);
        updatePodPosition();
    }
    
    /**
     * Update pod position based on member positions
     */
    private void updatePodPosition() {
        if (podMembers.isEmpty()) {
            return;
        }
        
        int totalX = 0;
        int totalY = 0;
        int totalZ = 0;
        
        for (Unit member : podMembers) {
            Position pos = member.getPosition();
            totalX += pos.getX();
            totalY += pos.getY();
            totalZ += pos.getHeight();
        }
        
        int avgX = totalX / podMembers.size();
        int avgY = totalY / podMembers.size();
        int avgZ = totalZ / podMembers.size();
        
        podPosition = new Position(avgX, avgY, avgZ);
    }
    
    /**
     * Activate pod
     */
    public void activate() {
        isActive = true;
        isCoordinating = true;
        
        // Apply coordination bonuses to all members
        for (Unit member : podMembers) {
            applyCoordinationBonuses(member);
        }
    }
    
    /**
     * Deactivate pod
     */
    public void deactivate() {
        isActive = false;
        isCoordinating = false;
        
        // Remove coordination bonuses from all members
        for (Unit member : podMembers) {
            removeCoordinationBonuses(member);
        }
    }
    
    /**
     * Apply coordination bonuses to unit
     */
    private void applyCoordinationBonuses(Unit unit) {
        // Apply accuracy bonus
        int currentAccuracy = unit.getWeapon() != null ? unit.getWeapon().getAccuracy() : 0;
        if (unit.getWeapon() != null) {
            unit.getWeapon().setAccuracy(currentAccuracy + coordinationBonuses.get("accuracy"));
        }
        
        // Apply damage bonus
        int currentDamage = unit.getWeapon() != null ? unit.getWeapon().getBaseDamage() : 0;
        if (unit.getWeapon() != null) {
            unit.getWeapon().setBaseDamage(currentDamage + coordinationBonuses.get("damage"));
        }
        
        // Apply movement bonus
        unit.setMovementRange(unit.getMovementRange() + coordinationBonuses.get("movement"));
        
        // Apply defense bonus
        // Defense bonus would be applied through armor or other defensive mechanisms
    }
    
    /**
     * Remove coordination bonuses from unit
     */
    private void removeCoordinationBonuses(Unit unit) {
        // Remove accuracy bonus
        int currentAccuracy = unit.getWeapon() != null ? unit.getWeapon().getAccuracy() : 0;
        if (unit.getWeapon() != null) {
            unit.getWeapon().setAccuracy(currentAccuracy - coordinationBonuses.get("accuracy"));
        }
        
        // Remove damage bonus
        int currentDamage = unit.getWeapon() != null ? unit.getWeapon().getBaseDamage() : 0;
        if (unit.getWeapon() != null) {
            unit.getWeapon().setBaseDamage(currentDamage - coordinationBonuses.get("damage"));
        }
        
        // Remove movement bonus
        unit.setMovementRange(unit.getMovementRange() - coordinationBonuses.get("movement"));
        
        // Remove defense bonus
        // Defense bonus removal would be implemented here
    }
    
    /**
     * Coordinate pod actions
     */
    public void coordinateActions() {
        if (!isCoordinating || podMembers.isEmpty()) {
            return;
        }
        
        // Coordinate based on pod type
        switch (podType) {
            case PATROL:
                coordinatePatrol();
                break;
            case GUARD:
                coordinateGuard();
                break;
            case REINFORCEMENT:
                coordinateReinforcement();
                break;
            case ELITE:
                coordinateElite();
                break;
            case BOSS:
                coordinateBoss();
                break;
            case AMBUSH:
                coordinateAmbush();
                break;
            case PURSUIT:
                coordinatePursuit();
                break;
            case DEFENSIVE:
                coordinateDefensive();
                break;
            case AGGRESSIVE:
                coordinateAggressive();
                break;
            case STEALTH:
                coordinateStealth();
                break;
            case SUPPORT:
                coordinateSupport();
                break;
            case ARTILLERY:
                coordinateArtillery();
                break;
            case MELEE:
                coordinateMelee();
                break;
            case FLYING:
                coordinateFlying();
                break;
            case UNDERGROUND:
                coordinateUnderground();
                break;
            case WATER:
                coordinateWater();
                break;
            case URBAN:
                coordinateUrban();
                break;
            case RURAL:
                coordinateRural();
                break;
            case INDUSTRIAL:
                coordinateIndustrial();
                break;
            case LABORATORY:
                coordinateLaboratory();
                break;
            case MILITARY:
                coordinateMilitary();
                break;
            case CIVILIAN:
                coordinateCivilian();
                break;
            case HUNTER:
                coordinateHunter();
                break;
            case SCAVENGER:
                coordinateScavenger();
                break;
            case CONSTRUCTOR:
                coordinateConstructor();
                break;
            case INFILTRATOR:
                coordinateInfiltrator();
                break;
            case INTERROGATOR:
                coordinateInterrogator();
                break;
            case EXECUTIONER:
                coordinateExecutioner();
                break;
            case SPECIALIST:
                coordinateSpecialist();
                break;
        }
    }
    
    /**
     * Coordinate patrol actions
     */
    private void coordinatePatrol() {
        // Patrol coordination logic
        for (Unit member : podMembers) {
            if (member != null && member.isAlive()) {
                // Patrol behavior: move in formation, scan area, report findings
                if (member.getActionPoints() > 0) {
                    member.setMovementRange(member.getMovementRange() + 1); // Enhanced movement for patrol
                    System.out.println(member.getName() + " is patrolling the area");
                }
            }
        }
    }
    
    /**
     * Coordinate guard actions
     */
    private void coordinateGuard() {
        // Guard coordination logic
        for (Unit member : podMembers) {
            if (member != null && member.isAlive()) {
                // Guard behavior: stay in position, overwatch, alert nearby pods
                member.setOverwatching(true);
                member.setOverwatchChance(75); // High overwatch chance for guards
                
                // Alert nearby pods if enemy detected
                if (member.getViewRange() > 0) {
                    System.out.println(member.getName() + " is on guard duty");
                }
            }
        }
    }
    
    /**
     * Coordinate reinforcement actions
     */
    private void coordinateReinforcement() {
        // Reinforcement coordination logic
        if (reinforcementCount < maxReinforcements) {
            // Call for reinforcements
            isReinforcing = true;
        }
    }
    
    /**
     * Coordinate elite actions
     */
    private void coordinateElite() {
        // Elite coordination logic
        for (Unit member : podMembers) {
            if (member != null && member.isAlive()) {
                // Elite behavior: enhanced combat abilities, tactical positioning
                if (member.getWeapon() != null) {
                    member.getWeapon().setAccuracy(member.getWeapon().getAccuracy() + 10);
                    member.getWeapon().setBaseDamage(member.getWeapon().getBaseDamage() + 5);
                }
                System.out.println(member.getName() + " elite unit is ready for combat");
            }
        }
    }
    
    /**
     * Coordinate boss actions
     */
    private void coordinateBoss() {
        // Boss coordination logic
        for (Unit member : podMembers) {
            if (member != null && member.isAlive()) {
                // Boss behavior: command other units, use special abilities
                member.setInitiative(member.getInitiative() + 20); // Boss acts first
                if (member.getWeapon() != null) {
                    member.getWeapon().setBaseDamage(member.getWeapon().getBaseDamage() + 10);
                }
                System.out.println(member.getName() + " boss unit is commanding the pod");
            }
        }
    }
    
    /**
     * Coordinate ambush actions
     */
    private void coordinateAmbush() {
        // Ambush coordination logic
        for (Unit member : podMembers) {
            if (member != null && member.isAlive()) {
                // Ambush behavior: conceal, wait for targets, surprise attack
                member.conceal();
                member.setCriticalChance(member.getCriticalChance() + 25); // Bonus crit for ambush
                System.out.println(member.getName() + " is setting up an ambush");
            }
        }
    }
    
    /**
     * Coordinate pursuit actions
     */
    private void coordinatePursuit() {
        // Pursuit coordination logic
        for (Unit member : podMembers) {
            if (member != null && member.isAlive()) {
                // Pursuit behavior: increase mobility and reduce accuracy penalty while moving
                member.setMovementRange(member.getMovementRange() + 2);
                if (member.getWeapon() != null) {
                    member.getWeapon().setAccuracy(member.getWeapon().getAccuracy() + 5);
                }
                System.out.println(member.getName() + " is pursuing targets");
            }
        }
    }
    
    /**
     * Coordinate defensive actions
     */
    private void coordinateDefensive() {
        // Defensive coordination logic
        for (Unit member : podMembers) {
            if (member != null && member.isAlive()) {
                // Defensive behavior: adopt overwatch and increase initiative slightly
                member.setOverwatching(true);
                member.setOverwatchChance(65);
                member.setInitiative(member.getInitiative() + 2);
                System.out.println(member.getName() + " is holding a defensive position");
            }
        }
    }
    
    /**
     * Coordinate aggressive actions
     */
    private void coordinateAggressive() {
        // Aggressive coordination logic
        for (Unit member : podMembers) {
            if (member != null && member.isAlive()) {
                // Aggressive behavior: trade accuracy for damage and push forward
                if (member.getWeapon() != null) {
                    member.getWeapon().setBaseDamage(member.getWeapon().getBaseDamage() + 4);
                    member.getWeapon().setAccuracy(member.getWeapon().getAccuracy() + 5);
                }
                member.setMovementRange(member.getMovementRange() + 1);
                System.out.println(member.getName() + " is advancing aggressively");
            }
        }
    }
    
    /**
     * Coordinate stealth actions
     */
    private void coordinateStealth() {
        // Stealth coordination logic
        for (Unit member : podMembers) {
            if (member != null && member.isAlive()) {
                // Stealth behavior: conceal and prepare ambush
                member.conceal();
                member.setCriticalChance(member.getCriticalChance() + 5);
                System.out.println(member.getName() + " is moving stealthily");
            }
        }
    }
    
    /**
     * Coordinate support actions
     */
    private void coordinateSupport() {
        // Support coordination logic
        for (Unit member : podMembers) {
            if (member != null && member.isAlive()) {
                // Support behavior: minor healing and movement aid
                member.heal(2);
                member.setMovementRange(member.getMovementRange() + 1);
                System.out.println(member.getName() + " is supporting allies");
            }
        }
    }
    
    /**
     * Coordinate artillery actions
     */
    private void coordinateArtillery() {
        // Artillery coordination logic
        for (Unit member : podMembers) {
            if (member != null && member.isAlive()) {
                // Artillery behavior: boost ranged lethality, limit mobility
                if (member.getWeapon() != null) {
                    member.getWeapon().setAccuracy(member.getWeapon().getAccuracy() + 8);
                    member.getWeapon().setBaseDamage(member.getWeapon().getBaseDamage() + 6);
                }
                member.setMovementRange(Math.max(1, member.getMovementRange() - 1));
                System.out.println(member.getName() + " is providing artillery fire");
            }
        }
    }
    
    /**
     * Coordinate melee actions
     */
    private void coordinateMelee() {
        // Melee coordination logic
        for (Unit member : podMembers) {
            if (member != null && member.isAlive()) {
                // Melee behavior: increase mobility and close-range lethality
                member.setMovementRange(member.getMovementRange() + 2);
                if (member.getWeapon() != null) {
                    member.getWeapon().setBaseDamage(member.getWeapon().getBaseDamage() + 3);
                }
                System.out.println(member.getName() + " is charging for melee");
            }
        }
    }
    
    /**
     * Coordinate flying actions
     */
    private void coordinateFlying() {
        // Flying coordination logic
        for (Unit member : podMembers) {
            if (member != null && member.isAlive()) {
                // Flying behavior: improved mobility and accuracy due to vantage
                member.setMovementRange(member.getMovementRange() + 1);
                if (member.getWeapon() != null) {
                    member.getWeapon().setAccuracy(member.getWeapon().getAccuracy() + 5);
                }
                System.out.println(member.getName() + " is attacking from the air");
            }
        }
    }
    
    /**
     * Coordinate underground actions
     */
    private void coordinateUnderground() {
        // Underground coordination logic
        for (Unit member : podMembers) {
            if (member != null && member.isAlive()) {
                // Underground behavior: conceal approach and resilience to flanking
                member.conceal();
                member.setFlanked(false);
                System.out.println(member.getName() + " is tunneling for a hidden approach");
            }
        }
    }
    
    /**
     * Coordinate water actions
     */
    private void coordinateWater() {
        // Water coordination logic
        for (Unit member : podMembers) {
            if (member != null && member.isAlive()) {
                // Water behavior: reduced mobility; steadier aim
                member.setMovementRange(Math.max(1, member.getMovementRange() - 1));
                if (member.getWeapon() != null) {
                    member.getWeapon().setAccuracy(member.getWeapon().getAccuracy() + 3);
                }
                System.out.println(member.getName() + " is advancing through water");
            }
        }
    }
    
    /**
     * Coordinate urban actions
     */
    private void coordinateUrban() {
        // Urban coordination logic
        for (Unit member : podMembers) {
            if (member != null && member.isAlive()) {
                // Urban behavior: overwatch lanes and improved accuracy
                member.setOverwatching(true);
                member.setOverwatchChance(55);
                if (member.getWeapon() != null) {
                    member.getWeapon().setAccuracy(member.getWeapon().getAccuracy() + 4);
                }
                System.out.println(member.getName() + " is locking down urban lanes");
            }
        }
    }
    
    /**
     * Coordinate rural actions
     */
    private void coordinateRural() {
        // Rural coordination logic
        for (Unit member : podMembers) {
            if (member != null && member.isAlive()) {
                // Rural behavior: better maneuvering
                member.setMovementRange(member.getMovementRange() + 1);
                System.out.println(member.getName() + " is maneuvering in open terrain");
            }
        }
    }
    
    /**
     * Coordinate industrial actions
     */
    private void coordinateIndustrial() {
        // Industrial coordination logic
        for (Unit member : podMembers) {
            if (member != null && member.isAlive()) {
                // Industrial behavior: careful aim through cluttered cover
                if (member.getWeapon() != null) {
                    member.getWeapon().setAccuracy(member.getWeapon().getAccuracy() + 3);
                }
                System.out.println(member.getName() + " is advancing through industrial cover");
            }
        }
    }
    
    /**
     * Coordinate laboratory actions
     */
    private void coordinateLaboratory() {
        // Laboratory coordination logic
        for (Unit member : podMembers) {
            if (member != null && member.isAlive()) {
                // Laboratory behavior: psionic amplification
                member.setPsiStrength(member.getPsiStrength() + 3);
                System.out.println(member.getName() + " is empowered by lab anomalies");
            }
        }
    }
    
    /**
     * Coordinate military actions
     */
    private void coordinateMilitary() {
        // Military coordination logic
        for (Unit member : podMembers) {
            if (member != null && member.isAlive()) {
                // Military behavior: disciplined overwatch and accuracy
                member.setOverwatching(true);
                member.setOverwatchChance(70);
                if (member.getWeapon() != null) {
                    member.getWeapon().setAccuracy(member.getWeapon().getAccuracy() + 5);
                }
                System.out.println(member.getName() + " is executing military drills");
            }
        }
    }
    
    /**
     * Coordinate civilian actions
     */
    private void coordinateCivilian() {
        // Civilian coordination logic
        for (Unit member : podMembers) {
            if (member != null && member.isAlive()) {
                // Civilian behavior: conceal and withdraw
                member.conceal();
                member.setMovementRange(member.getMovementRange() + 1);
                System.out.println(member.getName() + " is avoiding engagement");
            }
        }
    }
    
    /**
     * Coordinate hunter actions
     */
    private void coordinateHunter() {
        // Hunter coordination logic
        for (Unit member : podMembers) {
            if (member != null && member.isAlive()) {
                // Hunter behavior: stalk and strike with precision
                if (member.getWeapon() != null) {
                    member.getWeapon().setAccuracy(member.getWeapon().getAccuracy() + 6);
                    member.getWeapon().setBaseDamage(member.getWeapon().getBaseDamage() + 2);
                }
                System.out.println(member.getName() + " is hunting targets");
            }
        }
    }
    
    /**
     * Coordinate scavenger actions
     */
    private void coordinateScavenger() {
        // Scavenger coordination logic
        for (Unit member : podMembers) {
            if (member != null && member.isAlive()) {
                // Scavenger behavior: mobility and minor self-repair
                member.setMovementRange(member.getMovementRange() + 1);
                member.heal(1);
                System.out.println(member.getName() + " is scavenging and patching up");
            }
        }
    }
    
    /**
     * Coordinate constructor actions
     */
    private void coordinateConstructor() {
        // Constructor coordination logic
        for (Unit member : podMembers) {
            if (member != null && member.isAlive()) {
                // Constructor behavior: improve initiative for tactical setup
                member.setInitiative(member.getInitiative() + 2);
                System.out.println(member.getName() + " is constructing tactical cover");
            }
        }
    }
    
    /**
     * Coordinate infiltrator actions
     */
    private void coordinateInfiltrator() {
        // Infiltrator coordination logic
        for (Unit member : podMembers) {
            if (member != null && member.isAlive()) {
                // Infiltrator behavior: conceal, improve mobility and precision for stealth strikes
                member.conceal();
                member.setMovementRange(member.getMovementRange() + 1);
                member.setCriticalChance(member.getCriticalChance() + 10);
                System.out.println(member.getName() + " is infiltrating under concealment");
            }
        }
    }
    
    /**
     * Coordinate interrogator actions
     */
    private void coordinateInterrogator() {
        // Interrogator coordination logic
        for (Unit member : podMembers) {
            if (member != null && member.isAlive()) {
                // Interrogator behavior: pressure enemies via overwatch posture and psionic focus
                member.setOverwatching(true);
                member.setOverwatchChance(60);
                member.setPsiStrength(member.getPsiStrength() + 5);
                System.out.println(member.getName() + " is conducting interrogation operations");
            }
        }
    }
    
    /**
     * Coordinate executioner actions
     */
    private void coordinateExecutioner() {
        // Executioner coordination logic
        for (Unit member : podMembers) {
            if (member != null && member.isAlive()) {
                // Executioner behavior: high lethality burst
                if (member.getWeapon() != null) {
                    member.getWeapon().setBaseDamage(member.getWeapon().getBaseDamage() + 8);
                }
                member.setCriticalChance(member.getCriticalChance() + 15);
                System.out.println(member.getName() + " is executing high-value targets");
            }
        }
    }
    
    /**
     * Coordinate specialist actions
     */
    private void coordinateSpecialist() {
        // Specialist coordination logic
        for (Unit member : podMembers) {
            if (member != null && member.isAlive()) {
                // Specialist behavior: precise fire and flexible movement
                if (member.getWeapon() != null) {
                    member.getWeapon().setAccuracy(member.getWeapon().getAccuracy() + 5);
                }
                member.setMovementRange(member.getMovementRange() + 1);
                System.out.println(member.getName() + " is deploying specialist tactics");
            }
        }
    }
    
    /**
     * Escalate pod behavior
     */
    public void escalate() {
        escalationLevel++;
        
        // Improve tactic effectiveness
        for (String tactic : podTactics) {
            int currentEffectiveness = tacticEffectiveness.get(tactic);
            tacticEffectiveness.put(tactic, Math.min(100, currentEffectiveness + 10));
        }
        
        // Increase coordination bonuses
        for (String bonus : coordinationBonuses.keySet()) {
            int currentBonus = coordinationBonuses.get(bonus);
            coordinationBonuses.put(bonus, currentBonus + 2);
        }
    }
    
    /**
     * Learn from encounters
     */
    public void learnFromEncounter(String tactic) {
        if (!podTactics.contains(tactic)) {
            podTactics.add(tactic);
            tacticEffectiveness.put(tactic, 50);
        } else {
            int currentEffectiveness = tacticEffectiveness.get(tactic);
            tacticEffectiveness.put(tactic, Math.min(100, currentEffectiveness + 5));
        }
        
        tacticCounters.put(tactic, tacticCounters.getOrDefault(tactic, 0) + 1);
    }
    
    /**
     * Call for reinforcements
     */
    public boolean callReinforcements() {
        if (reinforcementCount >= maxReinforcements) {
            return false;
        }
        
        reinforcementCount++;
        isReinforcing = true;
        return true;
    }
    
    /**
     * Get pod status
     */
    public String getPodStatus() {
        return String.format("Pod: %s, Type: %s, Members: %d, Active: %s, Level: %d", 
                           podId, podType, podMembers.size(), isActive, escalationLevel);
    }
    
    /**
     * Get pod members
     */
    public List<Unit> getPodMembers() {
        return new ArrayList<>(podMembers);
    }
    
    /**
     * Check if pod is empty
     */
    public boolean isEmpty() {
        return podMembers.isEmpty();
    }
    
    /**
     * Get pod size
     */
    public int getPodSize() {
        return podMembers.size();
    }
    
    // Additional methods for compatibility
    public boolean shouldActivate(Position position) {
        // Check if pod should activate based on position
        if (isActive) {
            return false;
        }
        
        // Check if any member is close to the position
        for (Unit member : podMembers) {
            if (member.getPosition().getDistance2D(position) <= 3) {
                return true;
            }
        }
        return false;
    }
    
    public void activatePod() {
        activate();
    }
    
    public void usePodAbility(String abilityName, List<Unit> targets) {
        // This method is called but not implemented in the original design
        // In a real implementation, this would use pod abilities
    }
    
    public void processPodTurn() {
        if (isActive) {
            coordinateActions();
        }
    }
    
    public boolean isDefeated() {
        // Check if all pod members are defeated
        for (Unit member : podMembers) {
            if (member.isAlive()) {
                return false;
            }
        }
        return true;
    }
}
