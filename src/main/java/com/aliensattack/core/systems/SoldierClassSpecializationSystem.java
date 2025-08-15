package com.aliensattack.core.systems;

import com.aliensattack.core.enums.SoldierClass;
import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.*;

/**
 * Soldier Class Specialization System for class progression.
 * Implements soldier classes, specializations, and progression mechanics.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SoldierClassSpecializationSystem {
    
    private Map<String, SoldierClassData> soldierClasses;
    private Map<String, SkillTree> skillTrees;
    private Map<String, ClassAbility> classAbilities;
    private Map<String, ClassSynergy> classSynergies;
    private Map<String, ClassEquipment> classEquipment;
    private Map<String, Integer> classExperience;
    private Map<String, Integer> classLevels;
    private Map<String, List<String>> unlockedAbilities;
    private Map<String, List<String>> activeAbilities;
    private Map<String, ClassProgression> classProgressions;
    

    

    
    /**
     * Soldier Class Data with progression information
     */
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SoldierClassData {
        private String classId;
        private SoldierClass soldierClass;
        private String className;
        private String description;
        private int baseHealth;
        private int baseArmor;
        private int baseMobility;
        private int baseAim;
        private int baseWill;
        private List<String> primaryAbilities;
        private List<String> secondaryAbilities;
        private String primaryWeaponType;
        private String secondaryWeaponType;
        private String armorType;
        private List<String> equipmentBonuses;
        private Map<String, Integer> statBonuses;
        private List<String> classSynergies;
        private int experienceToNextLevel;
        private int maxLevel;
        private boolean isSpecialized;
        private String specializationPath;
    }
    
    /**
     * Skill Tree with multiple branches
     */
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SkillTree {
        private String treeId;
        private String treeName;
        private String classId;
        private List<SkillBranch> branches;
        private int maxPoints;
        private int currentPoints;
        private Map<String, Integer> branchProgress;
        private boolean isUnlocked;
        private String unlockRequirement;
        private List<String> prerequisites;
        private Map<String, Integer> skillCosts;
        private List<String> unlockedSkills;
        private List<String> activeSkills;
    }
    
    /**
     * Skill Branch within a skill tree
     */
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SkillBranch {
        private String branchId;
        private String branchName;
        private String description;
        private List<SkillNode> nodes;
        private int maxLevel;
        private int currentLevel;
        private String branchType;
        private List<String> requirements;
        private Map<String, Integer> bonuses;
        private boolean isActive;
        private String activationCondition;
    }
    
    /**
     * Individual skill node
     */
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SkillNode {
        private String nodeId;
        private String nodeName;
        private String description;
        private int level;
        private int maxLevel;
        private int cost;
        private String effect;
        private List<String> prerequisites;
        private boolean isUnlocked;
        private boolean isActive;
        private Map<String, Integer> bonuses;
        private String activationType;
    }
    
    /**
     * Class-specific ability
     */
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ClassAbility {
        private String abilityId;
        private String abilityName;
        private String description;
        private String classId;
        private int cooldown;
        private int currentCooldown;
        private int actionPointCost;
        private String abilityType;
        private Map<String, Integer> effects;
        private List<String> requirements;
        private boolean isUnlocked;
        private boolean isActive;
        private String activationCondition;
        private int range;
        private int duration;
        private String targetType;
    }
    
    /**
     * Class synergy bonuses
     */
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ClassSynergy {
        private String synergyId;
        private String synergyName;
        private String description;
        private List<String> requiredClasses;
        private Map<String, Integer> bonuses;
        private String activationCondition;
        private boolean isActive;
        private int duration;
        private String synergyType;
        private List<String> affectedAbilities;
        private Map<String, Integer> statBonuses;
    }
    
    /**
     * Class-specific equipment
     */
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ClassEquipment {
        private String equipmentId;
        private String equipmentName;
        private String classId;
        private String equipmentType;
        private Map<String, Integer> bonuses;
        private List<String> abilities;
        private String description;
        private int durability;
        private int maxDurability;
        private boolean isEquipped;
        private String slot;
        private List<String> requirements;
        private Map<String, Integer> classBonuses;
    }
    
    /**
     * Class progression tracking
     */
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ClassProgression {
        private String soldierId;
        private String classId;
        private int level;
        private int experience;
        private int experienceToNext;
        private List<String> unlockedAbilities;
        private List<String> activeAbilities;
        private Map<String, Integer> skillPoints;
        private String specializationPath;
        private boolean isSpecialized;
        private Map<String, Integer> statBonuses;
        private List<String> completedMissions;
        private int totalMissions;
        private double successRate;
    }
    
    /**
     * Initialize the soldier class specialization system
     */
    private void initializeSystem() {
        // Initialize soldier classes
        initializeSoldierClasses();
        
        // Initialize skill trees
        initializeSkillTrees();
        
        // Initialize class abilities
        initializeClassAbilities();
        
        // Initialize class synergies
        initializeClassSynergies();
        
        // Initialize class equipment
        initializeClassEquipment();
    }
    
    /**
     * Initialize soldier classes with their base stats and abilities
     */
    private void initializeSoldierClasses() {
        // Ranger class
        SoldierClassData ranger = SoldierClassData.builder()
            .classId("ranger")
            .soldierClass(SoldierClass.RANGER)
            .className("Ranger")
            .description("Close-quarters specialist with stealth abilities")
            .baseHealth(5)
            .baseArmor(1)
            .baseMobility(15)
            .baseAim(70)
            .baseWill(50)
            .primaryAbilities(Arrays.asList("Phantom", "Bladestorm", "Shadowstep"))
            .secondaryAbilities(Arrays.asList("Conceal", "Run and Gun", "Implacable"))
            .primaryWeaponType("Shotgun")
            .secondaryWeaponType("Sword")
            .armorType("Light")
            .equipmentBonuses(Arrays.asList("Stealth", "Close Combat"))
            .statBonuses(Map.of("Mobility", 5, "CloseCombat", 10))
            .classSynergies(Arrays.asList("Specialist", "Grenadier"))
            .experienceToNextLevel(100)
            .maxLevel(7)
            .isSpecialized(false)
            .specializationPath("")
            .build();
        
        soldierClasses.put("ranger", ranger);
        
        // Specialist class
        SoldierClassData specialist = SoldierClassData.builder()
            .classId("specialist")
            .soldierClass(SoldierClass.SPECIALIST)
            .className("Specialist")
            .description("Technical expert with hacking and medical abilities")
            .baseHealth(4)
            .baseArmor(1)
            .baseMobility(12)
            .baseAim(75)
            .baseWill(60)
            .primaryAbilities(Arrays.asList("Medical Protocol", "Combat Protocol", "Haywire Protocol"))
            .secondaryAbilities(Arrays.asList("Field Medic", "Revival Protocol", "Scanning Protocol"))
            .primaryWeaponType("Assault Rifle")
            .secondaryWeaponType("Gremlin")
            .armorType("Light")
            .equipmentBonuses(Arrays.asList("Hacking", "Medical"))
            .statBonuses(Map.of("Hacking", 15, "Medical", 10))
            .classSynergies(Arrays.asList("Ranger", "Grenadier"))
            .experienceToNextLevel(100)
            .maxLevel(7)
            .isSpecialized(false)
            .specializationPath("")
            .build();
        
        soldierClasses.put("specialist", specialist);
        
        // Grenadier class
        SoldierClassData grenadier = SoldierClassData.builder()
            .classId("grenadier")
            .soldierClass(SoldierClass.GRENADIER)
            .className("Grenadier")
            .description("Heavy weapons expert with explosive abilities")
            .baseHealth(6)
            .baseArmor(2)
            .baseMobility(10)
            .baseAim(65)
            .baseWill(45)
            .primaryAbilities(Arrays.asList("Suppression", "Demolition", "Volatile Mix"))
            .secondaryAbilities(Arrays.asList("Heavy Ordnance", "Saturation Fire", "Chain Shot"))
            .primaryWeaponType("Heavy Cannon")
            .secondaryWeaponType("Grenade Launcher")
            .armorType("Heavy")
            .equipmentBonuses(Arrays.asList("Explosives", "Heavy Weapons"))
            .statBonuses(Map.of("Explosives", 15, "HeavyWeapons", 10))
            .classSynergies(Arrays.asList("Ranger", "Specialist"))
            .experienceToNextLevel(100)
            .maxLevel(7)
            .isSpecialized(false)
            .specializationPath("")
            .build();
        
        soldierClasses.put("grenadier", grenadier);
        
        // Sharpshooter class
        SoldierClassData sharpshooter = SoldierClassData.builder()
            .classId("sharpshooter")
            .soldierClass(SoldierClass.SHARPSHOOTER)
            .className("Sharpshooter")
            .description("Long-range specialist with precision abilities")
            .baseHealth(4)
            .baseArmor(1)
            .baseMobility(12)
            .baseAim(85)
            .baseWill(55)
            .primaryAbilities(Arrays.asList("Squadsight", "Deadeye", "Kill Zone"))
            .secondaryAbilities(Arrays.asList("Long Watch", "Steady Hands", "Faceoff"))
            .primaryWeaponType("Sniper Rifle")
            .secondaryWeaponType("Pistol")
            .armorType("Light")
            .equipmentBonuses(Arrays.asList("Precision", "Long Range"))
            .statBonuses(Map.of("Aim", 10, "LongRange", 15))
            .classSynergies(Arrays.asList("Ranger", "Specialist"))
            .experienceToNextLevel(100)
            .maxLevel(7)
            .isSpecialized(false)
            .specializationPath("")
            .build();
        
        soldierClasses.put("sharpshooter", sharpshooter);
    }
    
    /**
     * Initialize skill trees for each class
     */
    private void initializeSkillTrees() {
        // Ranger skill tree
        SkillTree rangerTree = SkillTree.builder()
            .treeId("ranger_tree")
            .treeName("Ranger Skill Tree")
            .classId("ranger")
            .branches(createRangerBranches())
            .maxPoints(20)
            .currentPoints(0)
            .branchProgress(new HashMap<>())
            .isUnlocked(true)
            .unlockRequirement("")
            .prerequisites(new ArrayList<>())
            .skillCosts(Map.of("Phantom", 3, "Bladestorm", 5, "Shadowstep", 4))
            .unlockedSkills(new ArrayList<>())
            .activeSkills(new ArrayList<>())
            .build();
        
        skillTrees.put("ranger_tree", rangerTree);
        
        // Specialist skill tree
        SkillTree specialistTree = SkillTree.builder()
            .treeId("specialist_tree")
            .treeName("Specialist Skill Tree")
            .classId("specialist")
            .branches(createSpecialistBranches())
            .maxPoints(20)
            .currentPoints(0)
            .branchProgress(new HashMap<>())
            .isUnlocked(true)
            .unlockRequirement("")
            .prerequisites(new ArrayList<>())
            .skillCosts(Map.of("Medical Protocol", 3, "Combat Protocol", 4, "Haywire Protocol", 5))
            .unlockedSkills(new ArrayList<>())
            .activeSkills(new ArrayList<>())
            .build();
        
        skillTrees.put("specialist_tree", specialistTree);
    }
    
    /**
     * Create ranger skill branches
     */
    private List<SkillBranch> createRangerBranches() {
        List<SkillBranch> branches = new ArrayList<>();
        
        // Stealth branch
        SkillBranch stealthBranch = SkillBranch.builder()
            .branchId("stealth_branch")
            .branchName("Stealth")
            .description("Stealth and concealment abilities")
            .nodes(createStealthNodes())
            .maxLevel(5)
            .currentLevel(0)
            .branchType("Stealth")
            .requirements(new ArrayList<>())
            .bonuses(Map.of("Stealth", 10, "Concealment", 15))
            .isActive(false)
            .activationCondition("")
            .build();
        
        branches.add(stealthBranch);
        
        // Combat branch
        SkillBranch combatBranch = SkillBranch.builder()
            .branchId("combat_branch")
            .branchName("Combat")
            .description("Close combat and melee abilities")
            .nodes(createCombatNodes())
            .maxLevel(5)
            .currentLevel(0)
            .branchType("Combat")
            .requirements(new ArrayList<>())
            .bonuses(Map.of("CloseCombat", 15, "MeleeDamage", 20))
            .isActive(false)
            .activationCondition("")
            .build();
        
        branches.add(combatBranch);
        
        return branches;
    }
    
    /**
     * Create specialist skill branches
     */
    private List<SkillBranch> createSpecialistBranches() {
        List<SkillBranch> branches = new ArrayList<>();
        
        // Medical branch
        SkillBranch medicalBranch = SkillBranch.builder()
            .branchId("medical_branch")
            .branchName("Medical")
            .description("Medical and healing abilities")
            .nodes(createMedicalNodes())
            .maxLevel(5)
            .currentLevel(0)
            .branchType("Medical")
            .requirements(new ArrayList<>())
            .bonuses(Map.of("Medical", 15, "Healing", 20))
            .isActive(false)
            .activationCondition("")
            .build();
        
        branches.add(medicalBranch);
        
        // Technical branch
        SkillBranch technicalBranch = SkillBranch.builder()
            .branchId("technical_branch")
            .branchName("Technical")
            .description("Hacking and technical abilities")
            .nodes(createTechnicalNodes())
            .maxLevel(5)
            .currentLevel(0)
            .branchType("Technical")
            .requirements(new ArrayList<>())
            .bonuses(Map.of("Hacking", 15, "Technical", 20))
            .isActive(false)
            .activationCondition("")
            .build();
        
        branches.add(technicalBranch);
        
        return branches;
    }
    
    /**
     * Create stealth skill nodes
     */
    private List<SkillNode> createStealthNodes() {
        List<SkillNode> nodes = new ArrayList<>();
        
        SkillNode phantom = SkillNode.builder()
            .nodeId("phantom")
            .nodeName("Phantom")
            .description("Remain concealed after squad is revealed")
            .level(1)
            .maxLevel(1)
            .cost(3)
            .effect("Concealment")
            .prerequisites(new ArrayList<>())
            .isUnlocked(false)
            .isActive(false)
            .bonuses(Map.of("Concealment", 100))
            .activationType("Passive")
            .build();
        
        nodes.add(phantom);
        
        SkillNode shadowstep = SkillNode.builder()
            .nodeId("shadowstep")
            .nodeName("Shadowstep")
            .description("Move without breaking concealment")
            .level(1)
            .maxLevel(1)
            .cost(4)
            .effect("Stealth Movement")
            .prerequisites(Arrays.asList("phantom"))
            .isUnlocked(false)
            .isActive(false)
            .bonuses(Map.of("StealthMovement", 50))
            .activationType("Active")
            .build();
        
        nodes.add(shadowstep);
        
        return nodes;
    }
    
    /**
     * Create combat skill nodes
     */
    private List<SkillNode> createCombatNodes() {
        List<SkillNode> nodes = new ArrayList<>();
        
        SkillNode bladestorm = SkillNode.builder()
            .nodeId("bladestorm")
            .nodeName("Bladestorm")
            .description("Automatic melee attacks when enemies move nearby")
            .level(1)
            .maxLevel(1)
            .cost(5)
            .effect("Reactive Melee")
            .prerequisites(new ArrayList<>())
            .isUnlocked(false)
            .isActive(false)
            .bonuses(Map.of("ReactiveMelee", 100, "MeleeDamage", 50))
            .activationType("Reactive")
            .build();
        
        nodes.add(bladestorm);
        
        return nodes;
    }
    
    /**
     * Create medical skill nodes
     */
    private List<SkillNode> createMedicalNodes() {
        List<SkillNode> nodes = new ArrayList<>();
        
        SkillNode medicalProtocol = SkillNode.builder()
            .nodeId("medical_protocol")
            .nodeName("Medical Protocol")
            .description("Heal allies with gremlin")
            .level(1)
            .maxLevel(1)
            .cost(3)
            .effect("Healing")
            .prerequisites(new ArrayList<>())
            .isUnlocked(false)
            .isActive(false)
            .bonuses(Map.of("Healing", 20, "MedicalRange", 10))
            .activationType("Active")
            .build();
        
        nodes.add(medicalProtocol);
        
        return nodes;
    }
    
    /**
     * Create technical skill nodes
     */
    private List<SkillNode> createTechnicalNodes() {
        List<SkillNode> nodes = new ArrayList<>();
        
        SkillNode combatProtocol = SkillNode.builder()
            .nodeId("combat_protocol")
            .nodeName("Combat Protocol")
            .description("Deal damage with gremlin")
            .level(1)
            .maxLevel(1)
            .cost(4)
            .effect("Damage")
            .prerequisites(new ArrayList<>())
            .isUnlocked(false)
            .isActive(false)
            .bonuses(Map.of("Damage", 15, "TechnicalRange", 10))
            .activationType("Active")
            .build();
        
        nodes.add(combatProtocol);
        
        return nodes;
    }
    
    /**
     * Initialize class abilities
     */
    private void initializeClassAbilities() {
        // Ranger abilities
        ClassAbility phantom = ClassAbility.builder()
            .abilityId("phantom")
            .abilityName("Phantom")
            .description("Remain concealed after squad is revealed")
            .classId("ranger")
            .cooldown(0)
            .currentCooldown(0)
            .actionPointCost(0)
            .abilityType("Passive")
            .effects(Map.of("Concealment", 100))
            .requirements(new ArrayList<>())
            .isUnlocked(false)
            .isActive(false)
            .activationCondition("Squad Revealed")
            .range(0)
            .duration(-1)
            .targetType("Self")
            .build();
        
        classAbilities.put("phantom", phantom);
        
        // Specialist abilities
        ClassAbility medicalProtocol = ClassAbility.builder()
            .abilityId("medical_protocol")
            .abilityName("Medical Protocol")
            .description("Heal allies with gremlin")
            .classId("specialist")
            .cooldown(3)
            .currentCooldown(0)
            .actionPointCost(1)
            .abilityType("Active")
            .effects(Map.of("Healing", 20))
            .requirements(new ArrayList<>())
            .isUnlocked(false)
            .isActive(false)
            .activationCondition("Ally Damaged")
            .range(10)
            .duration(1)
            .targetType("Ally")
            .build();
        
        classAbilities.put("medical_protocol", medicalProtocol);
    }
    
    /**
     * Initialize class synergies
     */
    private void initializeClassSynergies() {
        // Ranger-Specialist synergy
        ClassSynergy rangerSpecialist = ClassSynergy.builder()
            .synergyId("ranger_specialist")
            .synergyName("Ranger-Specialist Synergy")
            .description("Rangers and Specialists work better together")
            .requiredClasses(Arrays.asList("ranger", "specialist"))
            .bonuses(Map.of("Aim", 10, "Will", 5))
            .activationCondition("Both classes in squad")
            .isActive(false)
            .duration(-1)
            .synergyType("Combat")
            .affectedAbilities(Arrays.asList("Phantom", "Medical Protocol"))
            .statBonuses(Map.of("Aim", 10, "Will", 5))
            .build();
        
        classSynergies.put("ranger_specialist", rangerSpecialist);
    }
    
    /**
     * Initialize class equipment
     */
    private void initializeClassEquipment() {
        // Ranger equipment
        ClassEquipment rangerSword = ClassEquipment.builder()
            .equipmentId("ranger_sword")
            .equipmentName("Ranger Sword")
            .classId("ranger")
            .equipmentType("Weapon")
            .bonuses(Map.of("MeleeDamage", 20, "CloseCombat", 15))
            .abilities(Arrays.asList("Bladestorm"))
            .description("Specialized melee weapon for rangers")
            .durability(100)
            .maxDurability(100)
            .isEquipped(false)
            .slot("Secondary")
            .requirements(Arrays.asList("ranger"))
            .classBonuses(Map.of("MeleeDamage", 25))
            .build();
        
        classEquipment.put("ranger_sword", rangerSword);
        
        // Specialist equipment
        ClassEquipment gremlin = ClassEquipment.builder()
            .equipmentId("gremlin")
            .equipmentName("Gremlin")
            .classId("specialist")
            .equipmentType("Drone")
            .bonuses(Map.of("Hacking", 15, "Medical", 10))
            .abilities(Arrays.asList("Medical Protocol", "Combat Protocol"))
            .description("Technical drone for specialists")
            .durability(100)
            .maxDurability(100)
            .isEquipped(false)
            .slot("Secondary")
            .requirements(Arrays.asList("specialist"))
            .classBonuses(Map.of("Hacking", 20, "Medical", 15))
            .build();
        
        classEquipment.put("gremlin", gremlin);
    }
    
    /**
     * Add soldier to class specialization system
     */
    public boolean addSoldierToClass(String soldierId, String classId) {
        if (!soldierClasses.containsKey(classId)) {
            return false;
        }
        
        ClassProgression progression = ClassProgression.builder()
            .soldierId(soldierId)
            .classId(classId)
            .level(1)
            .experience(0)
            .experienceToNext(100)
            .unlockedAbilities(new ArrayList<>())
            .activeAbilities(new ArrayList<>())
            .skillPoints(new HashMap<>())
            .specializationPath("")
            .isSpecialized(false)
            .statBonuses(new HashMap<>())
            .completedMissions(new ArrayList<>())
            .totalMissions(0)
            .successRate(0.0)
            .build();
        
        classProgressions.put(soldierId, progression);
        return true;
    }
    
    /**
     * Unlock ability for soldier
     */
    public boolean unlockAbility(String soldierId, String abilityId) {
        ClassProgression progression = classProgressions.get(soldierId);
        if (progression == null) {
            return false;
        }
        
        ClassAbility ability = classAbilities.get(abilityId);
        if (ability == null) {
            return false;
        }
        
        if (!ability.getClassId().equals(progression.getClassId())) {
            return false;
        }
        
        progression.getUnlockedAbilities().add(abilityId);
        return true;
    }
    
    /**
     * Activate ability for soldier
     */
    public boolean activateAbility(String soldierId, String abilityId) {
        ClassProgression progression = classProgressions.get(soldierId);
        if (progression == null) {
            return false;
        }
        
        if (!progression.getUnlockedAbilities().contains(abilityId)) {
            return false;
        }
        
        progression.getActiveAbilities().add(abilityId);
        return true;
    }
    
    /**
     * Add experience to soldier class
     */
    public boolean addClassExperience(String soldierId, int experience) {
        ClassProgression progression = classProgressions.get(soldierId);
        if (progression == null) {
            return false;
        }
        
        progression.setExperience(progression.getExperience() + experience);
        
        // Check for level up
        while (progression.getExperience() >= progression.getExperienceToNext()) {
            levelUpSoldier(soldierId);
        }
        
        return true;
    }
    
    /**
     * Level up soldier
     */
    private void levelUpSoldier(String soldierId) {
        ClassProgression progression = classProgressions.get(soldierId);
        if (progression == null) {
            return;
        }
        
        progression.setLevel(progression.getLevel() + 1);
        progression.setExperience(progression.getExperience() - progression.getExperienceToNext());
        progression.setExperienceToNext(progression.getExperienceToNext() * 2);
        
        // Add skill points
        Map<String, Integer> skillPoints = progression.getSkillPoints();
        skillPoints.put("available", skillPoints.getOrDefault("available", 0) + 2);
    }
    
    /**
     * Get soldier class data
     */
    public SoldierClassData getSoldierClassData(String classId) {
        return soldierClasses.get(classId);
    }
    
    /**
     * Get soldier progression
     */
    public ClassProgression getSoldierProgression(String soldierId) {
        return classProgressions.get(soldierId);
    }
    
    /**
     * Get available abilities for soldier
     */
    public List<ClassAbility> getAvailableAbilities(String soldierId) {
        ClassProgression progression = classProgressions.get(soldierId);
        if (progression == null) {
            return new ArrayList<>();
        }
        
        return classAbilities.values().stream()
            .filter(ability -> ability.getClassId().equals(progression.getClassId()))
            .filter(ability -> progression.getUnlockedAbilities().contains(ability.getAbilityId()))
            .collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
    }
    
    /**
     * Get active abilities for soldier
     */
    public List<ClassAbility> getActiveAbilities(String soldierId) {
        ClassProgression progression = classProgressions.get(soldierId);
        if (progression == null) {
            return new ArrayList<>();
        }
        
        return classAbilities.values().stream()
            .filter(ability -> ability.getClassId().equals(progression.getClassId()))
            .filter(ability -> progression.getActiveAbilities().contains(ability.getAbilityId()))
            .collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
    }
    
    /**
     * Get class synergies for squad
     */
    public List<ClassSynergy> getSquadSynergies(List<String> soldierIds) {
        List<ClassSynergy> activeSynergies = new ArrayList<>();
        
        for (ClassSynergy synergy : classSynergies.values()) {
            boolean hasAllRequired = synergy.getRequiredClasses().stream()
                .allMatch(requiredClass -> soldierIds.stream()
                    .anyMatch(soldierId -> {
                        ClassProgression progression = classProgressions.get(soldierId);
                        return progression != null && progression.getClassId().equals(requiredClass);
                    }));
            
            if (hasAllRequired) {
                activeSynergies.add(synergy);
            }
        }
        
        return activeSynergies;
    }
    
    /**
     * Get class equipment for soldier
     */
    public List<ClassEquipment> getClassEquipment(String soldierId) {
        ClassProgression progression = classProgressions.get(soldierId);
        if (progression == null) {
            return new ArrayList<>();
        }
        
        return classEquipment.values().stream()
            .filter(equipment -> equipment.getClassId().equals(progression.getClassId()))
            .collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
    }
    
    /**
     * Get skill tree for soldier
     */
    public SkillTree getSoldierSkillTree(String soldierId) {
        ClassProgression progression = classProgressions.get(soldierId);
        if (progression == null) {
            return null;
        }
        
        return skillTrees.values().stream()
            .filter(tree -> tree.getClassId().equals(progression.getClassId()))
            .findFirst()
            .orElse(null);
    }
    
    /**
     * Unlock skill node
     */
    public boolean unlockSkillNode(String soldierId, String nodeId) {
        ClassProgression progression = classProgressions.get(soldierId);
        if (progression == null) {
            return false;
        }
        
        SkillTree skillTree = getSoldierSkillTree(soldierId);
        if (skillTree == null) {
            return false;
        }
        
        // Find the skill node
        for (SkillBranch branch : skillTree.getBranches()) {
            for (SkillNode node : branch.getNodes()) {
                if (node.getNodeId().equals(nodeId)) {
                    // Check if soldier has enough skill points
                    int availablePoints = progression.getSkillPoints().getOrDefault("available", 0);
                    if (availablePoints >= node.getCost()) {
                        node.setUnlocked(true);
                        progression.getSkillPoints().put("available", availablePoints - node.getCost());
                        skillTree.getUnlockedSkills().add(nodeId);
                        return true;
                    }
                }
            }
        }
        
        return false;
    }
    
    /**
     * Get total class experience
     */
    public int getTotalClassExperience() {
        return classExperience.values().stream().mapToInt(Integer::intValue).sum();
    }
    
    /**
     * Get total class levels
     */
    public int getTotalClassLevels() {
        return classLevels.values().stream().mapToInt(Integer::intValue).sum();
    }
    
    /**
     * Get unlocked abilities count
     */
    public int getUnlockedAbilitiesCount() {
        return unlockedAbilities.values().stream().mapToInt(List::size).sum();
    }
    
    /**
     * Get active abilities count
     */
    public int getActiveAbilitiesCount() {
        return activeAbilities.values().stream().mapToInt(List::size).sum();
    }
    
    /**
     * Get class progression count
     */
    public int getClassProgressionCount() {
        return classProgressions.size();
    }
    
    /**
     * Get skill tree count
     */
    public int getSkillTreeCount() {
        return skillTrees.size();
    }
    
    /**
     * Get class synergy count
     */
    public int getClassSynergyCount() {
        return classSynergies.size();
    }
    
    /**
     * Get class equipment count
     */
    public int getClassEquipmentCount() {
        return classEquipment.size();
    }
}

