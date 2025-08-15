package com.aliensattack.core.model;

import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.*;

/**
 * Squad Tactics System for coordinated unit actions.
 * Implements formation tactics, coordinated attacks, and squad maneuvers.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SquadTacticsSystem {
    
    private String tacticsId;
    private Map<String, Squad> squads;
    private Map<String, SquadTactic> squadTactics;
    private Map<String, SquadBond> squadBonds;
    private Map<String, SquadCoordination> squadCoordinations;
    private Map<String, SquadMorale> squadMorales;
    private Map<String, SquadExperience> squadExperiences;
    private Map<String, List<String>> squadMembers;
    private Map<String, Map<String, Integer>> squadBonuses;
    private Map<String, List<String>> activeTactics;
    private Map<String, Integer> squadLevels;
    private Map<String, Boolean> squadStates;
    private int totalSquads;
    private int maxSquadSize;
    private boolean isTacticsActive;
    
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SquadFormation {
        private String formationId;
        private String formationName;
        private FormationType formationType;
        private Map<String, Integer> formationEffects;
        private List<String> formationRequirements;
        private int formationCost;
        private boolean isActive;
        private String description;
        private int cooldown;
        private int currentCooldown;
        private String activationCondition;
        private double successRate;
        private String failureEffect;
        private Map<String, Integer> formationBonuses;
        private List<String> formationAbilities;
        private String formationMethod;
        private int energyCost;
        private boolean isAutomatic;
        private String triggerCondition;
        private Map<String, Integer> damageModifiers;
        private List<String> resistanceTypes;
        private boolean isPermanent;
        private String permanentCondition;
        
        // Additional fields for formation positioning and stats
        private Map<String, Position> unitPositions;
        private int coverage;
        private int mobility;
        
        public enum FormationType {
            DEFENSIVE,      // Defensive formation
            OFFENSIVE,      // Offensive formation
            FLANKING,       // Flanking formation
            SUPPORT,        // Support formation
            COVER,          // Cover formation
            OVERWATCH,      // Overwatch formation
            MEDICAL,        // Medical formation
            TECHNICAL,      // Technical formation
            BASIC           // Basic formation
        }
    }
    
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SquadManeuver {
        private String maneuverId;
        private String maneuverName;
        private ManeuverType maneuverType;
        private Map<String, Integer> maneuverEffects;
        private List<String> maneuverRequirements;
        private int maneuverCost;
        private boolean isActive;
        private String description;
        private int cooldown;
        private int currentCooldown;
        private String activationCondition;
        private double successRate;
        private String failureEffect;
        private Map<String, Integer> maneuverBonuses;
        private List<String> maneuverAbilities;
        private String maneuverMethod;
        private int energyCost;
        private boolean isAutomatic;
        private String triggerCondition;
        private Map<String, Integer> damageModifiers;
        private List<String> resistanceTypes;
        private boolean isPermanent;
        private String permanentCondition;
        
        // Additional fields for maneuver stats
        private int coordinationBonus;
        private int executionTime;
        
        public enum ManeuverType {
            COORDINATED_ATTACK,    // Coordinated attack maneuver
            COVERING_FIRE,         // Covering fire maneuver
            FLANKING_MANEUVER,     // Flanking maneuver
            SUPPRESSION_FIRE,      // Suppression fire maneuver
            OVERWATCH_AMBUSH,      // Overwatch ambush maneuver
            TACTICAL_WITHDRAWAL,   // Tactical withdrawal maneuver
            MEDICAL_EVACUATION,    // Medical evacuation maneuver
            TECHNICAL_SPECIALIST   // Technical specialist maneuver
        }
    }
    
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Squad {
        private String squadId;
        private String squadName;
        private List<String> members;
        private SquadTacticType primaryTactic;
        private SquadTacticType secondaryTactic;
        private int squadLevel;
        private int experience;
        private int morale;
        private int cohesion;
        private boolean isActive;
        private String leaderId;
        private Map<String, Integer> squadBonuses;
        private List<String> squadAbilities;
        private int maxSize;
        private int currentSize;
        
        public enum SquadTacticType {
            OVERWATCH_AMBUSH,    // Overwatch and ambush tactics
            FLANKING_MANEUVER,   // Flanking and positioning
            SUPPRESSION_COVER,   // Suppression and cover tactics
            PSYCHIC_COORDINATION, // Psychic coordination
            STEALTH_INFILTRATION, // Stealth and infiltration
            HEAVY_ASSAULT,       // Heavy weapons assault
            MEDICAL_SUPPORT,     // Medical and support tactics
            TECHNICAL_SPECIALIST  // Technical specialist tactics
        }
    }
    
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SquadTactic {
        private String tacticId;
        private String tacticName;
        private Squad.SquadTacticType tacticType;
        private Map<String, Integer> tacticEffects;
        private List<String> tacticRequirements;
        private int tacticCost;
        private boolean isActive;
        private String description;
        private int cooldown;
        private int currentCooldown;
        private String activationCondition;
        private double successRate;
        private String failureEffect;
        private Map<String, Integer> tacticBonuses;
        private List<String> tacticAbilities;
        private String tacticMethod;
        private int energyCost;
        private boolean isAutomatic;
        private String triggerCondition;
        private Map<String, Integer> damageModifiers;
        private List<String> resistanceTypes;
        private boolean isPermanent;
        private String permanentCondition;
    }
    
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SquadBond {
        private String bondId;
        private String unit1Id;
        private String unit2Id;
        private int bondLevel;
        private int bondExperience;
        private Map<String, Integer> bondBonuses;
        private List<String> bondAbilities;
        private boolean isActive;
        private String bondType;
        private int bondDuration;
        private int currentDuration;
        private String activationCondition;
        private double successRate;
        private String failureEffect;
        private Map<String, Integer> bondEffects;
        private List<String> bondRequirements;
        private int bondCost;
        private boolean isPermanent;
        private String permanentCondition;
    }
    
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SquadCoordination {
        private String coordinationId;
        private String coordinationName;
        private String squadId;
        private CoordinationType coordinationType;
        private Map<String, Integer> coordinationEffects;
        private List<String> participatingUnits;
        private boolean isActive;
        private int duration;
        private int currentDuration;
        private String activationCondition;
        private double successRate;
        private String failureEffect;
        private Map<String, Integer> coordinationBonuses;
        private List<String> coordinationAbilities;
        private String coordinationMethod;
        private int energyCost;
        private boolean isAutomatic;
        private String triggerCondition;
        private Map<String, Integer> damageModifiers;
        private List<String> resistanceTypes;
        private boolean isPermanent;
        private String permanentCondition;
        private int coordinationCost;
        
        public enum CoordinationType {
            COMBINED_ATTACK,     // Combined attack coordination
            DEFENSIVE_FORMATION,  // Defensive formation coordination
            SUPPORT_ACTION,       // Support action coordination
            TACTICAL_MOVEMENT,    // Tactical movement coordination
            COVER_COORDINATION,   // Cover coordination
            OVERWATCH_NETWORK,    // Overwatch network coordination
            MEDICAL_COORDINATION, // Medical coordination
            TECHNICAL_COORDINATION, // Technical coordination
            BASIC_COORDINATION,   // Basic coordination
            ADVANCED_COORDINATION, // Advanced coordination
            ELITE_COORDINATION,   // Elite coordination
            LEGENDARY_COORDINATION // Legendary coordination
        }
    }
    
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SquadMorale {
        private String moraleId;
        private String moraleName;
        private String squadId;
        private MoraleType moraleType;
        private int moraleLevel;
        private int maxMoraleLevel;
        private Map<String, Integer> moraleEffects;
        private List<String> moraleEvents;
        private boolean isActive;
        private int duration;
        private int currentDuration;
        private String activationCondition;
        private double successRate;
        private String failureEffect;
        private Map<String, Integer> moraleBonuses;
        private List<String> moraleAbilities;
        private String moraleMethod;
        private int energyCost;
        private boolean isAutomatic;
        private String triggerCondition;
        private Map<String, Integer> damageModifiers;
        private List<String> resistanceTypes;
        private boolean isPermanent;
        private String permanentCondition;
        private int moraleCost;
        
        public enum MoraleType {
            LOW_MORALE,      // Low morale state
            NORMAL_MORALE,   // Normal morale state
            HIGH_MORALE,     // High morale state
            ELITE_MORALE,    // Elite morale state
            LEGENDARY_MORALE // Legendary morale state
        }
    }
    
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SquadExperience {
        private String experienceId;
        private String squadId;
        private int experienceLevel;
        private int maxExperienceLevel;
        private int experiencePoints;
        private int experienceToNext;
        private Map<String, Integer> experienceEffects;
        private List<String> experienceEvents;
        private boolean isActive;
        private int duration;
        private int currentDuration;
        private String activationCondition;
        private double successRate;
        private String failureEffect;
        private Map<String, Integer> experienceBonuses;
        private List<String> experienceAbilities;
        private String experienceMethod;
        private int energyCost;
        private boolean isAutomatic;
        private String triggerCondition;
        private Map<String, Integer> damageModifiers;
        private List<String> resistanceTypes;
        private boolean isPermanent;
        private String permanentCondition;
    }
    

    
    /**
     * Initialize the squad tactics system
     */
    public void initializeSystem() {
        this.tacticsId = "SQUAD_TACTICS_" + System.currentTimeMillis();
        this.squads = new HashMap<>();
        this.squadTactics = new HashMap<>();
        this.squadBonds = new HashMap<>();
        this.squadCoordinations = new HashMap<>();
        this.squadMorales = new HashMap<>();
        this.squadExperiences = new HashMap<>();
        this.squadMembers = new HashMap<>();
        this.squadBonuses = new HashMap<>();
        this.activeTactics = new HashMap<>();
        this.squadLevels = new HashMap<>();
        this.squadStates = new HashMap<>();
        this.totalSquads = 0;
        this.maxSquadSize = 6;
        this.isTacticsActive = false;
        
        initializeSquadTactics();
        initializeSquadBonds();
        initializeSquadCoordinations();
        initializeSquadMorales();
        initializeSquadExperiences();
    }
    
    /**
     * Initialize squad tactics
     */
    private void initializeSquadTactics() {
        // Overwatch Ambush Tactic
        SquadTactic overwatchAmbush = SquadTactic.builder()
            .tacticId("OVERWATCH_AMBUSH_TACTIC")
            .tacticName("Overwatch Ambush")
            .tacticType(Squad.SquadTacticType.OVERWATCH_AMBUSH)
            .tacticEffects(Map.of("overwatch_bonus", 20, "ambush_damage", 15, "concealment_bonus", 10))
            .tacticRequirements(Arrays.asList("Squad size 3+", "Concealment available"))
            .tacticCost(2)
            .isActive(false)
            .description("Coordinated overwatch positions with ambush bonuses")
            .cooldown(3)
            .currentCooldown(0)
            .activationCondition("Squad has action points")
            .successRate(0.85)
            .failureEffect("Reduced overwatch effectiveness")
            .tacticBonuses(Map.of("overwatch", 20, "damage", 15, "concealment", 10))
            .tacticAbilities(Arrays.asList("OVERWATCH_AMBUSH", "CONCEALMENT_BONUS", "COORDINATED_ATTACK"))
            .tacticMethod("Manual activation")
            .energyCost(0)
            .isAutomatic(false)
            .triggerCondition("Squad action")
            .damageModifiers(Map.of("ambush", 15, "overwatch", 10))
            .resistanceTypes(Arrays.asList("Stealth", "Coordination"))
            .isPermanent(false)
            .permanentCondition("")
            .build();
        squadTactics.put(overwatchAmbush.getTacticId(), overwatchAmbush);
        
        // Flanking Maneuver Tactic
        SquadTactic flankingManeuver = SquadTactic.builder()
            .tacticId("FLANKING_MANEUVERS_TACTIC")
            .tacticName("Flanking Maneuvers")
            .tacticType(Squad.SquadTacticType.FLANKING_MANEUVER)
            .tacticEffects(Map.of("flanking_bonus", 25, "movement_bonus", 10, "critical_chance", 15))
            .tacticRequirements(Arrays.asList("Squad size 2+", "Positioning possible"))
            .tacticCost(1)
            .isActive(false)
            .description("Coordinated flanking with movement and critical bonuses")
            .cooldown(2)
            .currentCooldown(0)
            .activationCondition("Squad has movement points")
            .successRate(0.9)
            .failureEffect("Reduced flanking effectiveness")
            .tacticBonuses(Map.of("flanking", 25, "movement", 10, "critical", 15))
            .tacticAbilities(Arrays.asList("FLANKING_BONUS", "MOVEMENT_BONUS", "CRITICAL_CHANCE"))
            .tacticMethod("Automatic")
            .energyCost(0)
            .isAutomatic(true)
            .triggerCondition("Squad movement")
            .damageModifiers(Map.of("flanking", 25, "critical", 15))
            .resistanceTypes(Arrays.asList("Positioning", "Coordination"))
            .isPermanent(false)
            .permanentCondition("")
            .build();
        squadTactics.put(flankingManeuver.getTacticId(), flankingManeuver);
        
        // Suppression Cover Tactic
        SquadTactic suppressionCover = SquadTactic.builder()
            .tacticId("SUPPRESSION_COVER_TACTIC")
            .tacticName("Suppression Cover")
            .tacticType(Squad.SquadTacticType.SUPPRESSION_COVER)
            .tacticEffects(Map.of("suppression_bonus", 15, "cover_bonus", 20, "defense_bonus", 10))
            .tacticRequirements(Arrays.asList("Squad size 2+", "Cover available"))
            .tacticCost(1)
            .isActive(false)
            .description("Coordinated suppression with cover bonuses")
            .cooldown(2)
            .currentCooldown(0)
            .activationCondition("Squad has action points")
            .successRate(0.8)
            .failureEffect("Reduced suppression effectiveness")
            .tacticBonuses(Map.of("suppression", 15, "cover", 20, "defense", 10))
            .tacticAbilities(Arrays.asList("SUPPRESSION_BONUS", "COVER_BONUS", "DEFENSE_BONUS"))
            .tacticMethod("Automatic")
            .energyCost(0)
            .isAutomatic(true)
            .triggerCondition("Squad action")
            .damageModifiers(Map.of("suppression", 15, "cover", 10))
            .resistanceTypes(Arrays.asList("Cover", "Coordination"))
            .isPermanent(false)
            .permanentCondition("")
            .build();
        squadTactics.put(suppressionCover.getTacticId(), suppressionCover);
        
        // Psionic Coordination Tactic
        SquadTactic psionicCoordination = SquadTactic.builder()
            .tacticId("PSYCHIC_COORDINATION_TACTIC")
            .tacticName("Psionic Coordination")
            .tacticType(Squad.SquadTacticType.PSYCHIC_COORDINATION)
            .tacticEffects(Map.of("psychic_bonus", 30, "coordination_bonus", 15, "psi_energy", 20))
            .tacticRequirements(Arrays.asList("Squad size 2+", "Psionic units"))
            .tacticCost(2)
            .isActive(false)
            .description("Coordinated psionic abilities with synergy bonuses")
            .cooldown(4)
            .currentCooldown(0)
            .activationCondition("Squad has psi energy")
            .successRate(0.75)
            .failureEffect("Reduced psionic effectiveness")
            .tacticBonuses(Map.of("psychic", 30, "coordination", 15, "psi", 20))
            .tacticAbilities(Arrays.asList("PSYCHIC_BONUS", "COORDINATION_BONUS", "PSI_ENERGY"))
            .tacticMethod("Manual activation")
            .energyCost(20)
            .isAutomatic(false)
            .triggerCondition("Squad psionic action")
            .damageModifiers(Map.of("psychic", 30, "psi", 20))
            .resistanceTypes(Arrays.asList("Psionic", "Coordination"))
            .isPermanent(false)
            .permanentCondition("")
            .build();
        squadTactics.put(psionicCoordination.getTacticId(), psionicCoordination);
        
        // Stealth Infiltration Tactic
        SquadTactic stealthInfiltration = SquadTactic.builder()
            .tacticId("STEALTH_INFILTRATION_TACTIC")
            .tacticName("Stealth Infiltration")
            .tacticType(Squad.SquadTacticType.STEALTH_INFILTRATION)
            .tacticEffects(Map.of("stealth_bonus", 25, "infiltration_bonus", 20, "concealment_bonus", 15))
            .tacticRequirements(Arrays.asList("Squad size 2+", "Stealth capable"))
            .tacticCost(2)
            .isActive(false)
            .description("Coordinated stealth with infiltration bonuses")
            .cooldown(3)
            .currentCooldown(0)
            .activationCondition("Squad has stealth capability")
            .successRate(0.8)
            .failureEffect("Reduced stealth effectiveness")
            .tacticBonuses(Map.of("stealth", 25, "infiltration", 20, "concealment", 15))
            .tacticAbilities(Arrays.asList("STEALTH_BONUS", "INFILTRATION_BONUS", "CONCEALMENT_BONUS"))
            .tacticMethod("Manual activation")
            .energyCost(0)
            .isAutomatic(false)
            .triggerCondition("Squad stealth action")
            .damageModifiers(Map.of("stealth", 25, "infiltration", 20))
            .resistanceTypes(Arrays.asList("Stealth", "Coordination"))
            .isPermanent(false)
            .permanentCondition("")
            .build();
        squadTactics.put(stealthInfiltration.getTacticId(), stealthInfiltration);
        
        // Heavy Assault Tactic
        SquadTactic heavyAssault = SquadTactic.builder()
            .tacticId("HEAVY_ASSAULT_TACTIC")
            .tacticName("Heavy Assault")
            .tacticType(Squad.SquadTacticType.HEAVY_ASSAULT)
            .tacticEffects(Map.of("heavy_damage", 30, "armor_penetration", 15, "suppression_bonus", 20))
            .tacticRequirements(Arrays.asList("Squad size 3+", "Heavy weapons"))
            .tacticCost(3)
            .isActive(false)
            .description("Coordinated heavy assault with armor penetration")
            .cooldown(4)
            .currentCooldown(0)
            .activationCondition("Squad has heavy weapons")
            .successRate(0.85)
            .failureEffect("Reduced assault effectiveness")
            .tacticBonuses(Map.of("heavy_damage", 30, "armor_penetration", 15, "suppression", 20))
            .tacticAbilities(Arrays.asList("HEAVY_DAMAGE", "ARMOR_PENETRATION", "SUPPRESSION_BONUS"))
            .tacticMethod("Manual activation")
            .energyCost(0)
            .isAutomatic(false)
            .triggerCondition("Squad assault action")
            .damageModifiers(Map.of("heavy", 30, "armor_penetration", 15))
            .resistanceTypes(Arrays.asList("Heavy Weapons", "Coordination"))
            .isPermanent(false)
            .permanentCondition("")
            .build();
        squadTactics.put(heavyAssault.getTacticId(), heavyAssault);
        
        // Medical Support Tactic
        SquadTactic medicalSupport = SquadTactic.builder()
            .tacticId("MEDICAL_SUPPORT_TACTIC")
            .tacticName("Medical Support")
            .tacticType(Squad.SquadTacticType.MEDICAL_SUPPORT)
            .tacticEffects(Map.of("healing_bonus", 25, "support_bonus", 20, "stabilization_bonus", 15))
            .tacticRequirements(Arrays.asList("Squad size 2+", "Medical units"))
            .tacticCost(2)
            .isActive(false)
            .description("Coordinated medical support with healing bonuses")
            .cooldown(3)
            .currentCooldown(0)
            .activationCondition("Squad has medical capability")
            .successRate(0.9)
            .failureEffect("Reduced medical effectiveness")
            .tacticBonuses(Map.of("healing", 25, "support", 20, "stabilization", 15))
            .tacticAbilities(Arrays.asList("HEALING_BONUS", "SUPPORT_BONUS", "STABILIZATION_BONUS"))
            .tacticMethod("Automatic")
            .energyCost(0)
            .isAutomatic(true)
            .triggerCondition("Squad medical action")
            .damageModifiers(Map.of("healing", 25, "support", 20))
            .resistanceTypes(Arrays.asList("Medical", "Coordination"))
            .isPermanent(false)
            .permanentCondition("")
            .build();
        squadTactics.put(medicalSupport.getTacticId(), medicalSupport);
        
        // Technical Specialist Tactic
        SquadTactic technicalSpecialist = SquadTactic.builder()
            .tacticId("TECHNICAL_SPECIALIST_TACTIC")
            .tacticName("Technical Specialist")
            .tacticType(Squad.SquadTacticType.TECHNICAL_SPECIALIST)
            .tacticEffects(Map.of("technical_bonus", 30, "hacking_bonus", 25, "robot_control", 20))
            .tacticRequirements(Arrays.asList("Squad size 2+", "Technical units"))
            .tacticCost(2)
            .isActive(false)
            .description("Coordinated technical abilities with hacking bonuses")
            .cooldown(3)
            .currentCooldown(0)
            .activationCondition("Squad has technical capability")
            .successRate(0.8)
            .failureEffect("Reduced technical effectiveness")
            .tacticBonuses(Map.of("technical", 30, "hacking", 25, "robot_control", 20))
            .tacticAbilities(Arrays.asList("TECHNICAL_BONUS", "HACKING_BONUS", "ROBOT_CONTROL"))
            .tacticMethod("Manual activation")
            .energyCost(15)
            .isAutomatic(false)
            .triggerCondition("Squad technical action")
            .damageModifiers(Map.of("technical", 30, "hacking", 25))
            .resistanceTypes(Arrays.asList("Technical", "Coordination"))
            .isPermanent(false)
            .permanentCondition("")
            .build();
        squadTactics.put(technicalSpecialist.getTacticId(), technicalSpecialist);
    }
    
    /**
     * Initialize squad bonds
     */
    private void initializeSquadBonds() {
        // Combat Bond
        SquadBond combatBond = SquadBond.builder()
            .bondId("COMBAT_BOND")
            .unit1Id("")
            .unit2Id("")
            .bondLevel(1)
            .bondExperience(0)
            .bondBonuses(Map.of("combat_synergy", 15, "coordination_bonus", 10))
            .bondAbilities(Arrays.asList("COMBAT_SYNERGY", "COORDINATION_BONUS"))
            .isActive(false)
            .bondType("Combat")
            .bondDuration(0)
            .currentDuration(0)
            .activationCondition("Units fight together")
            .successRate(0.9)
            .failureEffect("No bond effect")
            .bondEffects(Map.of("combat", 15, "coordination", 10))
            .bondRequirements(Arrays.asList("Combat experience", "Proximity"))
            .bondCost(0)
            .isPermanent(true)
            .permanentCondition("Units remain together")
            .build();
        squadBonds.put(combatBond.getBondId(), combatBond);
        
        // Support Bond
        SquadBond supportBond = SquadBond.builder()
            .bondId("SUPPORT_BOND")
            .unit1Id("")
            .unit2Id("")
            .bondLevel(1)
            .bondExperience(0)
            .bondBonuses(Map.of("support_bonus", 20, "healing_bonus", 15))
            .bondAbilities(Arrays.asList("SUPPORT_BONUS", "HEALING_BONUS"))
            .isActive(false)
            .bondType("Support")
            .bondDuration(0)
            .currentDuration(0)
            .activationCondition("Units support each other")
            .successRate(0.85)
            .failureEffect("Reduced support effectiveness")
            .bondEffects(Map.of("support", 20, "healing", 15))
            .bondRequirements(Arrays.asList("Support actions", "Proximity"))
            .bondCost(0)
            .isPermanent(true)
            .permanentCondition("Units remain together")
            .build();
        squadBonds.put(supportBond.getBondId(), supportBond);
        
        // Leadership Bond
        SquadBond leadershipBond = SquadBond.builder()
            .bondId("LEADERSHIP_BOND")
            .unit1Id("")
            .unit2Id("")
            .bondLevel(1)
            .bondExperience(0)
            .bondBonuses(Map.of("leadership_bonus", 25, "morale_bonus", 20))
            .bondAbilities(Arrays.asList("LEADERSHIP_BONUS", "MORALE_BONUS"))
            .isActive(false)
            .bondType("Leadership")
            .bondDuration(0)
            .currentDuration(0)
            .activationCondition("Leader-follower relationship")
            .successRate(0.9)
            .failureEffect("Reduced leadership effectiveness")
            .bondEffects(Map.of("leadership", 25, "morale", 20))
            .bondRequirements(Arrays.asList("Leadership role", "Follower role"))
            .bondCost(0)
            .isPermanent(true)
            .permanentCondition("Units remain together")
            .build();
        squadBonds.put(leadershipBond.getBondId(), leadershipBond);
        
        // Specialist Bond
        SquadBond specialistBond = SquadBond.builder()
            .bondId("SPECIALIST_BOND")
            .unit1Id("")
            .unit2Id("")
            .bondLevel(1)
            .bondExperience(0)
            .bondBonuses(Map.of("specialist_bonus", 20, "skill_bonus", 15))
            .bondAbilities(Arrays.asList("SPECIALIST_BONUS", "SKILL_BONUS"))
            .isActive(false)
            .bondType("Specialist")
            .bondDuration(0)
            .currentDuration(0)
            .activationCondition("Specialist collaboration")
            .successRate(0.8)
            .failureEffect("Reduced specialist effectiveness")
            .bondEffects(Map.of("specialist", 20, "skill", 15))
            .bondRequirements(Arrays.asList("Specialist skills", "Collaboration"))
            .bondCost(0)
            .isPermanent(true)
            .permanentCondition("Units remain together")
            .build();
        squadBonds.put(specialistBond.getBondId(), specialistBond);
        
        // Veteran Bond
        SquadBond veteranBond = SquadBond.builder()
            .bondId("VETERAN_BOND")
            .unit1Id("")
            .unit2Id("")
            .bondLevel(1)
            .bondExperience(0)
            .bondBonuses(Map.of("veteran_bonus", 30, "experience_bonus", 25))
            .bondAbilities(Arrays.asList("VETERAN_BONUS", "EXPERIENCE_BONUS"))
            .isActive(false)
            .bondType("Veteran")
            .bondDuration(0)
            .currentDuration(0)
            .activationCondition("Veteran units together")
            .successRate(0.95)
            .failureEffect("Reduced veteran effectiveness")
            .bondEffects(Map.of("veteran", 30, "experience", 25))
            .bondRequirements(Arrays.asList("Veteran status", "Combat experience"))
            .bondCost(0)
            .isPermanent(true)
            .permanentCondition("Units remain together")
            .build();
        squadBonds.put(veteranBond.getBondId(), veteranBond);
        
        // Mentor Bond
        SquadBond mentorBond = SquadBond.builder()
            .bondId("MENTOR_BOND")
            .unit1Id("")
            .unit2Id("")
            .bondLevel(1)
            .bondExperience(0)
            .bondBonuses(Map.of("mentor_bonus", 25, "learning_bonus", 20))
            .bondAbilities(Arrays.asList("MENTOR_BONUS", "LEARNING_BONUS"))
            .isActive(false)
            .bondType("Mentor")
            .bondDuration(0)
            .currentDuration(0)
            .activationCondition("Mentor-student relationship")
            .successRate(0.85)
            .failureEffect("Reduced mentor effectiveness")
            .bondEffects(Map.of("mentor", 25, "learning", 20))
            .bondRequirements(Arrays.asList("Mentor experience", "Student status"))
            .bondCost(0)
            .isPermanent(true)
            .permanentCondition("Units remain together")
            .build();
        squadBonds.put(mentorBond.getBondId(), mentorBond);
    }
    
    /**
     * Initialize squad coordinations
     */
    private void initializeSquadCoordinations() {
        // Basic Coordination
        SquadCoordination basicCoordination = SquadCoordination.builder()
            .coordinationId("BASIC_COORDINATION")
            .coordinationName("Basic Coordination")
            .coordinationType(SquadCoordination.CoordinationType.BASIC_COORDINATION)
            .coordinationEffects(Map.of("coordination_bonus", 10, "movement_bonus", 5))
            .coordinationAbilities(Arrays.asList("BASIC_COORDINATION", "MOVEMENT_BONUS"))
            .isActive(false)
            .coordinationCost(0)
            .build();
        squadCoordinations.put(basicCoordination.getCoordinationId(), basicCoordination);
        
        // Advanced Coordination
        SquadCoordination advancedCoordination = SquadCoordination.builder()
            .coordinationId("ADVANCED_COORDINATION")
            .coordinationName("Advanced Coordination")
            .coordinationType(SquadCoordination.CoordinationType.ADVANCED_COORDINATION)
            .coordinationEffects(Map.of("coordination_bonus", 20, "tactical_bonus", 15))
            .coordinationAbilities(Arrays.asList("ADVANCED_COORDINATION", "TACTICAL_BONUS"))
            .isActive(false)
            .coordinationCost(1)
            .build();
        squadCoordinations.put(advancedCoordination.getCoordinationId(), advancedCoordination);
        
        // Elite Coordination
        SquadCoordination eliteCoordination = SquadCoordination.builder()
            .coordinationId("ELITE_COORDINATION")
            .coordinationName("Elite Coordination")
            .coordinationType(SquadCoordination.CoordinationType.ELITE_COORDINATION)
            .coordinationEffects(Map.of("coordination_bonus", 30, "elite_bonus", 25))
            .coordinationAbilities(Arrays.asList("ELITE_COORDINATION", "ELITE_BONUS"))
            .isActive(false)
            .coordinationCost(2)
            .build();
        squadCoordinations.put(eliteCoordination.getCoordinationId(), eliteCoordination);
        
        // Legendary Coordination
        SquadCoordination legendaryCoordination = SquadCoordination.builder()
            .coordinationId("LEGENDARY_COORDINATION")
            .coordinationName("Legendary Coordination")
            .coordinationType(SquadCoordination.CoordinationType.LEGENDARY_COORDINATION)
            .coordinationEffects(Map.of("coordination_bonus", 40, "legendary_bonus", 35))
            .coordinationAbilities(Arrays.asList("LEGENDARY_COORDINATION", "LEGENDARY_BONUS"))
            .isActive(false)
            .coordinationCost(3)
            .build();
        squadCoordinations.put(legendaryCoordination.getCoordinationId(), legendaryCoordination);
    }
    
    /**
     * Initialize squad morales
     */
    private void initializeSquadMorales() {
        // Low Morale
        SquadMorale lowMorale = SquadMorale.builder()
            .moraleId("LOW_MORALE")
            .moraleName("Low Morale")
            .moraleType(SquadMorale.MoraleType.LOW_MORALE)
            .moraleEffects(Map.of("morale_penalty", -20, "accuracy_penalty", -15))
            .moraleAbilities(Arrays.asList("MORALE_PENALTY", "ACCURACY_PENALTY"))
            .isActive(false)
            .moraleCost(0)
            .build();
        squadMorales.put(lowMorale.getMoraleId(), lowMorale);
        
        // Normal Morale
        SquadMorale normalMorale = SquadMorale.builder()
            .moraleId("NORMAL_MORALE")
            .moraleName("Normal Morale")
            .moraleType(SquadMorale.MoraleType.NORMAL_MORALE)
            .moraleEffects(Map.of("morale_bonus", 0, "accuracy_bonus", 0))
            .moraleAbilities(Arrays.asList("NORMAL_MORALE", "STANDARD_PERFORMANCE"))
            .isActive(false)
            .moraleCost(0)
            .build();
        squadMorales.put(normalMorale.getMoraleId(), normalMorale);
        
        // High Morale
        SquadMorale highMorale = SquadMorale.builder()
            .moraleId("HIGH_MORALE")
            .moraleName("High Morale")
            .moraleType(SquadMorale.MoraleType.HIGH_MORALE)
            .moraleEffects(Map.of("morale_bonus", 20, "accuracy_bonus", 15))
            .moraleAbilities(Arrays.asList("MORALE_BONUS", "ACCURACY_BONUS"))
            .isActive(false)
            .moraleCost(0)
            .build();
        squadMorales.put(highMorale.getMoraleId(), highMorale);
        
        // Elite Morale
        SquadMorale eliteMorale = SquadMorale.builder()
            .moraleId("ELITE_MORALE")
            .moraleName("Elite Morale")
            .moraleType(SquadMorale.MoraleType.ELITE_MORALE)
            .moraleEffects(Map.of("morale_bonus", 30, "accuracy_bonus", 25))
            .moraleAbilities(Arrays.asList("ELITE_MORALE", "ELITE_ACCURACY"))
            .isActive(false)
            .moraleCost(0)
            .build();
        squadMorales.put(eliteMorale.getMoraleId(), eliteMorale);
        
        // Legendary Morale
        SquadMorale legendaryMorale = SquadMorale.builder()
            .moraleId("LEGENDARY_MORALE")
            .moraleName("Legendary Morale")
            .moraleType(SquadMorale.MoraleType.LEGENDARY_MORALE)
            .moraleEffects(Map.of("morale_bonus", 40, "accuracy_bonus", 35))
            .moraleAbilities(Arrays.asList("LEGENDARY_MORALE", "LEGENDARY_ACCURACY"))
            .isActive(false)
            .moraleCost(0)
            .build();
        squadMorales.put(legendaryMorale.getMoraleId(), legendaryMorale);
    }
    
    /**
     * Initialize squad experiences
     */
    private void initializeSquadExperiences() {
        // Rookie Experience
        SquadExperience rookieExperience = SquadExperience.builder()
            .experienceId("ROOKIE_EXPERIENCE")
            .squadId("")
            .experienceLevel(1)
            .maxExperienceLevel(10)
            .experiencePoints(0)
            .experienceToNext(100)
            .experienceEffects(Map.of("basic_skills", 10, "learning_rate", 15))
            .experienceEvents(new ArrayList<>())
            .isActive(false)
            .duration(0)
            .currentDuration(0)
            .activationCondition("Rookie status")
            .successRate(1.0)
            .failureEffect("No effect")
            .experienceBonuses(Map.of("basic", 10, "learning", 15))
            .experienceAbilities(Arrays.asList("BASIC_SKILLS", "LEARNING_RATE"))
            .experienceMethod("Automatic")
            .energyCost(0)
            .isAutomatic(true)
            .triggerCondition("Rookie status")
            .damageModifiers(new HashMap<>())
            .resistanceTypes(new ArrayList<>())
            .isPermanent(false)
            .permanentCondition("")
            .build();
        squadExperiences.put(rookieExperience.getExperienceId(), rookieExperience);
        
        // Veteran Experience
        SquadExperience veteranExperience = SquadExperience.builder()
            .experienceId("VETERAN_EXPERIENCE")
            .squadId("")
            .experienceLevel(5)
            .maxExperienceLevel(10)
            .experiencePoints(500)
            .experienceToNext(1000)
            .experienceEffects(Map.of("veteran_skills", 25, "tactical_bonus", 20))
            .experienceEvents(new ArrayList<>())
            .isActive(false)
            .duration(0)
            .currentDuration(0)
            .activationCondition("Veteran status")
            .successRate(1.0)
            .failureEffect("No effect")
            .experienceBonuses(Map.of("veteran", 25, "tactical", 20))
            .experienceAbilities(Arrays.asList("VETERAN_SKILLS", "TACTICAL_BONUS"))
            .experienceMethod("Automatic")
            .energyCost(0)
            .isAutomatic(true)
            .triggerCondition("Veteran status")
            .damageModifiers(new HashMap<>())
            .resistanceTypes(new ArrayList<>())
            .isPermanent(false)
            .permanentCondition("")
            .build();
        squadExperiences.put(veteranExperience.getExperienceId(), veteranExperience);
        
        // Elite Experience
        SquadExperience eliteExperience = SquadExperience.builder()
            .experienceId("ELITE_EXPERIENCE")
            .squadId("")
            .experienceLevel(8)
            .maxExperienceLevel(10)
            .experiencePoints(800)
            .experienceToNext(1000)
            .experienceEffects(Map.of("elite_skills", 35, "mastery_bonus", 30))
            .experienceEvents(new ArrayList<>())
            .isActive(false)
            .duration(0)
            .currentDuration(0)
            .activationCondition("Elite status")
            .successRate(1.0)
            .failureEffect("No effect")
            .experienceBonuses(Map.of("elite", 35, "mastery", 30))
            .experienceAbilities(Arrays.asList("ELITE_SKILLS", "MASTERY_BONUS"))
            .experienceMethod("Automatic")
            .energyCost(0)
            .isAutomatic(true)
            .triggerCondition("Elite status")
            .damageModifiers(new HashMap<>())
            .resistanceTypes(new ArrayList<>())
            .isPermanent(false)
            .permanentCondition("")
            .build();
        squadExperiences.put(eliteExperience.getExperienceId(), eliteExperience);
        
        // Legendary Experience
        SquadExperience legendaryExperience = SquadExperience.builder()
            .experienceId("LEGENDARY_EXPERIENCE")
            .squadId("")
            .experienceLevel(10)
            .maxExperienceLevel(10)
            .experiencePoints(1000)
            .experienceToNext(0)
            .experienceEffects(Map.of("legendary_skills", 50, "ultimate_bonus", 45))
            .experienceEvents(new ArrayList<>())
            .isActive(false)
            .duration(0)
            .currentDuration(0)
            .activationCondition("Legendary status")
            .successRate(1.0)
            .failureEffect("No effect")
            .experienceBonuses(Map.of("legendary", 50, "ultimate", 45))
            .experienceAbilities(Arrays.asList("LEGENDARY_SKILLS", "ULTIMATE_BONUS"))
            .experienceMethod("Automatic")
            .energyCost(0)
            .isAutomatic(true)
            .triggerCondition("Legendary status")
            .damageModifiers(new HashMap<>())
            .resistanceTypes(new ArrayList<>())
            .isPermanent(false)
            .permanentCondition("")
            .build();
        squadExperiences.put(legendaryExperience.getExperienceId(), legendaryExperience);
    }
    
    /**
     * Create a new squad
     */
    public boolean createSquad(String squadId, String squadName, String leaderId) {
        if (squads.containsKey(squadId)) {
            return false; // Squad already exists
        }
        
        Squad squad = Squad.builder()
            .squadId(squadId)
            .squadName(squadName)
            .members(new ArrayList<>())
            .primaryTactic(Squad.SquadTacticType.OVERWATCH_AMBUSH)
            .secondaryTactic(Squad.SquadTacticType.FLANKING_MANEUVER)
            .squadLevel(1)
            .experience(0)
            .morale(100)
            .cohesion(50)
            .isActive(true)
            .leaderId(leaderId)
            .squadBonuses(new HashMap<>())
            .squadAbilities(new ArrayList<>())
            .maxSize(maxSquadSize)
            .currentSize(0)
            .build();
        
        squads.put(squadId, squad);
        squadMembers.put(squadId, new ArrayList<>());
        squadBonuses.put(squadId, new HashMap<>());
        squadLevels.put(squadId, 1);
        squadStates.put(squadId, true);
        totalSquads++;
        
        return true;
    }
    
    /**
     * Add member to squad
     */
    public boolean addSquadMember(String squadId, String unitId) {
        Squad squad = squads.get(squadId);
        if (squad == null || squad.getCurrentSize() >= squad.getMaxSize()) {
            return false;
        }
        
        squad.getMembers().add(unitId);
        squad.setCurrentSize(squad.getCurrentSize() + 1);
        squadMembers.get(squadId).add(unitId);
        
        // Update squad cohesion
        updateSquadCohesion(squadId);
        
        return true;
    }
    
    /**
     * Remove member from squad
     */
    public boolean removeSquadMember(String squadId, String unitId) {
        Squad squad = squads.get(squadId);
        if (squad == null) {
            return false;
        }
        
        boolean removed = squad.getMembers().remove(unitId);
        if (removed) {
            squad.setCurrentSize(squad.getCurrentSize() - 1);
            squadMembers.get(squadId).remove(unitId);
            
            // Update squad cohesion
            updateSquadCohesion(squadId);
        }
        
        return removed;
    }
    
    /**
     * Create squad bond between two units
     */
    public boolean createSquadBond(String unit1Id, String unit2Id, String bondType) {
        String bondId = "BOND_" + unit1Id + "_" + unit2Id;
        
        if (squadBonds.containsKey(bondId)) {
            return false; // Bond already exists
        }
        
        SquadBond bond = SquadBond.builder()
            .bondId(bondId)
            .unit1Id(unit1Id)
            .unit2Id(unit2Id)
            .bondLevel(1)
            .bondExperience(0)
            .bondBonuses(new HashMap<>())
            .bondAbilities(new ArrayList<>())
            .isActive(true)
            .bondType(bondType)
            .bondDuration(0)
            .currentDuration(0)
            .activationCondition("Both units alive")
            .successRate(0.9)
            .failureEffect("No bond effect")
            .bondEffects(new HashMap<>())
            .bondRequirements(new ArrayList<>())
            .bondCost(0)
            .isPermanent(true)
            .permanentCondition("Both units alive")
            .build();
        
        squadBonds.put(bondId, bond);
        
        // Apply bond bonuses
        applyBondBonuses(bond);
        
        return true;
    }
    
    /**
     * Apply squad tactic
     */
    public boolean applySquadTactic(String squadId, Squad.SquadTacticType tacticType) {
        Squad squad = squads.get(squadId);
        if (squad == null) {
            return false;
        }
        
        // Create tactic
        SquadTactic tactic = SquadTactic.builder()
            .tacticId("TACTIC_" + squadId + "_" + tacticType.name())
            .tacticName(tacticType.name())
            .tacticType(tacticType)
            .tacticEffects(new HashMap<>())
            .tacticRequirements(new ArrayList<>())
            .tacticCost(1)
            .isActive(true)
            .description("Squad tactic: " + tacticType.name())
            .cooldown(3)
            .currentCooldown(0)
            .activationCondition("Squad has action points")
            .successRate(0.8)
            .failureEffect("No tactic effect")
            .tacticBonuses(new HashMap<>())
            .tacticAbilities(new ArrayList<>())
            .tacticMethod("Automatic")
            .energyCost(0)
            .isAutomatic(true)
            .triggerCondition("Squad action")
            .damageModifiers(new HashMap<>())
            .resistanceTypes(new ArrayList<>())
            .isPermanent(false)
            .permanentCondition("")
            .build();
        
        // Apply tactic effects based on type
        applyTacticEffects(squad, tactic);
        
        squadTactics.put(tactic.getTacticId(), tactic);
        activeTactics.put(squadId, new ArrayList<>());
        activeTactics.get(squadId).add(tactic.getTacticId());
        
        return true;
    }
    
    /**
     * Update squad cohesion
     */
    private void updateSquadCohesion(String squadId) {
        Squad squad = squads.get(squadId);
        if (squad == null) {
            return;
        }
        
        int memberCount = squad.getMembers().size();
        if (memberCount < 2) {
            squad.setCohesion(0);
            return;
        }
        
        // Calculate cohesion based on member count and bonds
        int baseCohesion = Math.min(100, memberCount * 20);
        int bondBonus = calculateBondBonus(squadId);
        int finalCohesion = Math.min(100, baseCohesion + bondBonus);
        
        squad.setCohesion(finalCohesion);
    }
    
    /**
     * Calculate bond bonus for squad
     */
    private int calculateBondBonus(String squadId) {
        List<String> members = squadMembers.get(squadId);
        if (members == null) {
            return 0;
        }
        
        int bondBonus = 0;
        for (SquadBond bond : squadBonds.values()) {
            if (members.contains(bond.getUnit1Id()) && members.contains(bond.getUnit2Id())) {
                bondBonus += bond.getBondLevel() * 5;
            }
        }
        
        return bondBonus;
    }
    
    /**
     * Apply bond bonuses
     */
    private void applyBondBonuses(SquadBond bond) {
        // Apply basic bond bonuses
        bond.getBondBonuses().put("accuracy", 5);
        bond.getBondBonuses().put("defense", 3);
        bond.getBondBonuses().put("morale", 10);
        
        // Add bond abilities
        bond.getBondAbilities().add("COORDINATED_ATTACK");
        bond.getBondAbilities().add("COVER_FIRE");
        bond.getBondAbilities().add("MORALE_BOOST");
    }
    
    /**
     * Apply tactic effects
     */
    private void applyTacticEffects(Squad squad, SquadTactic tactic) {
        switch (tactic.getTacticType()) {
            case OVERWATCH_AMBUSH:
                tactic.getTacticEffects().put("overwatch_bonus", 20);
                tactic.getTacticEffects().put("ambush_damage", 15);
                break;
            case FLANKING_MANEUVER:
                tactic.getTacticEffects().put("flanking_bonus", 25);
                tactic.getTacticEffects().put("movement_bonus", 10);
                break;
            case SUPPRESSION_COVER:
                tactic.getTacticEffects().put("suppression_bonus", 15);
                tactic.getTacticEffects().put("cover_bonus", 20);
                break;
            case PSYCHIC_COORDINATION:
                tactic.getTacticEffects().put("psychic_bonus", 30);
                tactic.getTacticEffects().put("coordination_bonus", 15);
                break;
            case STEALTH_INFILTRATION:
                tactic.getTacticEffects().put("stealth_bonus", 25);
                tactic.getTacticEffects().put("infiltration_bonus", 20);
                break;
            case HEAVY_ASSAULT:
                tactic.getTacticEffects().put("heavy_damage", 30);
                tactic.getTacticEffects().put("armor_penetration", 15);
                break;
            case MEDICAL_SUPPORT:
                tactic.getTacticEffects().put("healing_bonus", 25);
                tactic.getTacticEffects().put("support_bonus", 20);
                break;
            case TECHNICAL_SPECIALIST:
                tactic.getTacticEffects().put("technical_bonus", 30);
                tactic.getTacticEffects().put("hacking_bonus", 25);
                break;
        }
    }
    
    /**
     * Get squad by ID
     */
    public Squad getSquad(String squadId) {
        return squads.get(squadId);
    }
    
    /**
     * Get squad members
     */
    public List<String> getSquadMembers(String squadId) {
        return squadMembers.getOrDefault(squadId, new ArrayList<>());
    }
    
    /**
     * Get squad bonuses
     */
    public Map<String, Integer> getSquadBonuses(String squadId) {
        return squadBonuses.getOrDefault(squadId, new HashMap<>());
    }
    
    /**
     * Get active tactics for squad
     */
    public List<String> getActiveTactics(String squadId) {
        return activeTactics.getOrDefault(squadId, new ArrayList<>());
    }
    
    /**
     * Get squad level
     */
    public int getSquadLevel(String squadId) {
        return squadLevels.getOrDefault(squadId, 1);
    }
    
    /**
     * Check if squad is active
     */
    public boolean isSquadActive(String squadId) {
        return squadStates.getOrDefault(squadId, false);
    }
    
    /**
     * Get total squads
     */
    public int getTotalSquads() {
        return totalSquads;
    }
    
    /**
     * Create offensive formation
     */
    public SquadFormation createOffensiveFormation() {
        return SquadFormation.builder()
            .formationId("OFFENSIVE_" + System.currentTimeMillis())
            .formationName("Offensive Formation")
            .formationType(SquadFormation.FormationType.OFFENSIVE)
            .formationEffects(new HashMap<>())
            .formationRequirements(new ArrayList<>())
            .formationCost(0)
            .isActive(true)
            .description("Aggressive offensive formation")
            .cooldown(0)
            .currentCooldown(0)
            .activationCondition("Always")
            .successRate(1.0)
            .failureEffect("None")
            .formationBonuses(new HashMap<>())
            .formationAbilities(new ArrayList<>())
            .formationMethod("Manual")
            .energyCost(0)
            .isAutomatic(false)
            .triggerCondition("Manual")
            .damageModifiers(new HashMap<>())
            .resistanceTypes(new ArrayList<>())
            .isPermanent(false)
            .permanentCondition("None")
            .build();
    }
    
    /**
     * Create defensive formation
     */
    public SquadFormation createDefensiveFormation() {
        return SquadFormation.builder()
            .formationId("DEFENSIVE_" + System.currentTimeMillis())
            .formationName("Defensive Formation")
            .formationType(SquadFormation.FormationType.DEFENSIVE)
            .formationEffects(new HashMap<>())
            .formationRequirements(new ArrayList<>())
            .formationCost(0)
            .isActive(true)
            .description("Defensive formation for protection")
            .cooldown(0)
            .currentCooldown(0)
            .activationCondition("Always")
            .successRate(1.0)
            .failureEffect("None")
            .formationBonuses(new HashMap<>())
            .formationAbilities(new ArrayList<>())
            .formationMethod("Manual")
            .energyCost(0)
            .isAutomatic(false)
            .triggerCondition("Manual")
            .damageModifiers(new HashMap<>())
            .resistanceTypes(new ArrayList<>())
            .isPermanent(false)
            .permanentCondition("None")
            .build();
    }
    
    /**
     * Create flanking formation
     */
    public SquadFormation createFlankingFormation() {
        return SquadFormation.builder()
            .formationId("FLANKING_" + System.currentTimeMillis())
            .formationName("Flanking Formation")
            .formationType(SquadFormation.FormationType.FLANKING)
            .formationEffects(new HashMap<>())
            .formationRequirements(new ArrayList<>())
            .formationCost(0)
            .isActive(true)
            .description("Flanking formation for tactical advantage")
            .cooldown(0)
            .currentCooldown(0)
            .activationCondition("Always")
            .successRate(1.0)
            .failureEffect("None")
            .formationBonuses(new HashMap<>())
            .formationAbilities(new ArrayList<>())
            .formationMethod("Manual")
            .energyCost(0)
            .isAutomatic(false)
            .triggerCondition("Manual")
            .damageModifiers(new HashMap<>())
            .resistanceTypes(new ArrayList<>())
            .isPermanent(false)
            .permanentCondition("None")
            .build();
    }
    
    /**
     * Create support formation
     */
    public SquadFormation createSupportFormation() {
        return SquadFormation.builder()
            .formationId("SUPPORT_" + System.currentTimeMillis())
            .formationName("Support Formation")
            .formationType(SquadFormation.FormationType.SUPPORT)
            .formationEffects(new HashMap<>())
            .formationRequirements(new ArrayList<>())
            .formationCost(0)
            .isActive(true)
            .description("Support formation for assistance")
            .cooldown(0)
            .currentCooldown(0)
            .activationCondition("Always")
            .successRate(1.0)
            .failureEffect("None")
            .formationBonuses(new HashMap<>())
            .formationAbilities(new ArrayList<>())
            .formationMethod("Manual")
            .energyCost(0)
            .isAutomatic(false)
            .triggerCondition("Manual")
            .damageModifiers(new HashMap<>())
            .resistanceTypes(new ArrayList<>())
            .isPermanent(false)
            .permanentCondition("None")
            .build();
    }
    
    /**
     * Create balanced formation
     */
    public SquadFormation createBalancedFormation() {
        return SquadFormation.builder()
            .formationId("BALANCED_" + System.currentTimeMillis())
            .formationName("Balanced Formation")
            .formationType(SquadFormation.FormationType.DEFENSIVE)
            .formationEffects(new HashMap<>())
            .formationRequirements(new ArrayList<>())
            .formationCost(0)
            .isActive(true)
            .description("Balanced formation for versatility")
            .cooldown(0)
            .currentCooldown(0)
            .activationCondition("Always")
            .successRate(1.0)
            .failureEffect("None")
            .formationBonuses(new HashMap<>())
            .formationAbilities(new ArrayList<>())
            .formationMethod("Manual")
            .energyCost(0)
            .isAutomatic(false)
            .triggerCondition("Manual")
            .damageModifiers(new HashMap<>())
            .resistanceTypes(new ArrayList<>())
            .isPermanent(false)
            .permanentCondition("None")
            .build();
    }
}
