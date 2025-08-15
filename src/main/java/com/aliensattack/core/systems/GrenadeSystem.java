package com.aliensattack.core.systems;

import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import com.aliensattack.core.enums.StatusEffect;
import java.util.*;

/**
 * Grenade System for explosive mechanics.
 * Implements grenade types, explosion effects, and area damage.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GrenadeSystem {
    
    private String grenadeSystemId;
    private Map<String, Grenade> grenades;
    private Map<String, GrenadeType> grenadeTypes;
    private Map<String, Explosive> explosives;
    private Map<String, GrenadeLauncher> grenadeLaunchers;
    private Map<String, ChainReaction> chainReactions;
    private Map<String, List<String>> grenadeInventory;
    private Map<String, Map<String, Integer>> grenadeEffects;
    private Map<String, List<String>> activeGrenades;
    private Map<String, Integer> grenadeCounts;
    private Map<String, Boolean> grenadeStates;
    private int totalGrenades;
    private int maxGrenadeCapacity;
    private boolean isGrenadeSystemActive;
    
    /**
     * Grenade types with different effects
     */
    public enum GrenadeType {
        FRAG_GRENADE,       // Standard fragmentation grenade
        ACID_GRENADE,       // Acid damage and armor degradation
        FIRE_GRENADE,       // Fire damage and burning effects
        POISON_GRENADE,     // Poison damage and status effects
        SMOKE_GRENADE,      // Smoke screen and concealment
        FLASHBANG_GRENADE,  // Stun and disorientation
        PLASMA_GRENADE,     // High damage with psionic interference
        FREEZE_GRENADE,     // Freezing damage and movement penalties
        ELECTRICAL_GRENADE, // Electrical damage and stunning
        RADIATION_GRENADE,  // Radiation damage and mutation risk
        GRAVITY_GRENADE,    // Gravity manipulation effects
        TIME_GRENADE        // Time distortion effects
    }
    
    /**
     * Explosive types for placement
     */
    public enum ExplosiveType {
        C4_EXPLOSIVE,       // Remote detonated explosive
        LANDMINE,           // Pressure activated explosive
        PROXIMITY_MINE,     // Proximity activated explosive
        TIMED_EXPLOSIVE,    // Time delayed explosive
        TRIGGER_EXPLOSIVE,  // Trigger activated explosive
        SEQUENCE_EXPLOSIVE, // Sequential detonation explosive
        CHAIN_EXPLOSIVE,    // Chain reaction explosive
        IMPACT_EXPLOSIVE    // Impact activated explosive
    }
    
    /**
     * Chain reaction types
     */
    public enum ChainReactionType {
        EXPLOSIVE_CHAIN,    // Explosive chain reaction
        FIRE_SPREAD,        // Fire spreading reaction
        ELECTRICAL_ARC,     // Electrical arcing reaction
        CHEMICAL_REACTION,  // Chemical reaction chain
        RADIATION_SPREAD,   // Radiation spreading reaction
        PLASMA_CASCADE,     // Plasma cascade reaction
        GRAVITY_WAVE,       // Gravity wave reaction
        TIME_DISTORTION     // Time distortion reaction
    }
    
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Grenade {
        private String grenadeId;
        private String grenadeName;
        private GrenadeType grenadeType;
        private int damage;
        private int radius;
        private int range;
        private int accuracy;
        private int criticalChance;
        private double criticalMultiplier;
        private int cooldown;
        private int currentCooldown;
        private boolean isAvailable;
        private String description;
        private Map<String, Integer> effects;
        private List<String> abilities;
        private int cost;
        private String rarity;
        private boolean isUpgraded;
        private int upgradeLevel;
        private Map<String, Integer> upgradeBonuses;
        private List<String> upgradeAbilities;
        private String manufacturer;
        private String modelNumber;
        private String serialNumber;
        private String purchaseDate;
        private int purchaseCost;
        private String supplier;
        private String location;
        private String status;
    }
    
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Explosive {
        private String explosiveId;
        private String explosiveName;
        private ExplosiveType explosiveType;
        private int damage;
        private int radius;
        private int triggerRange;
        private int detonationDelay;
        private boolean isArmed;
        private boolean isDetonated;
        private String triggerCondition;
        private String placementLocation;
        private String assignedUnit;
        private String assignedFacility;
        private String assignedTechnician;
        private boolean isUnderMaintenance;
        private boolean isUnderRepair;
        private String explosiveStatus;
        private int maintenanceCost;
        private int repairCost;
        private String explosiveQuality;
        private String explosiveCondition;
        private String lastMaintenanceDate;
        private String nextMaintenanceDate;
        private String warrantyStatus;
        private boolean isWarrantied;
        private String warrantyExpiryDate;
        private String explosiveNotes;
        private Map<String, Integer> explosiveStats;
        private List<String> explosiveAbilities;
        private String manufacturer;
        private String modelNumber;
        private String serialNumber;
        private String purchaseDate;
        private int purchaseCost;
        private String supplier;
        private String location;
        private String status;
    }
    
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class GrenadeLauncher {
        private String launcherId;
        private String launcherName;
        private int damage;
        private int range;
        private int accuracy;
        private int magazineSize;
        private int currentAmmo;
        private int reloadTime;
        private int currentReloadTime;
        private boolean isReloading;
        private String description;
        private Map<String, Integer> effects;
        private List<String> abilities;
        private int cost;
        private String rarity;
        private boolean isUpgraded;
        private int upgradeLevel;
        private Map<String, Integer> upgradeBonuses;
        private List<String> upgradeAbilities;
        private String manufacturer;
        private String modelNumber;
        private String serialNumber;
        private String purchaseDate;
        private int purchaseCost;
        private String supplier;
        private String location;
        private String status;
    }
    
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ChainReaction {
        private String reactionId;
        private String reactionName;
        private ChainReactionType reactionType;
        private int damage;
        private int radius;
        private int chainLength;
        private int currentChainLength;
        private boolean isActive;
        private String description;
        private Map<String, Integer> effects;
        private List<String> abilities;
        private int cost;
        private String rarity;
        private boolean isUpgraded;
        private int upgradeLevel;
        private Map<String, Integer> upgradeBonuses;
        private List<String> upgradeAbilities;
        private String manufacturer;
        private String modelNumber;
        private String serialNumber;
        private String purchaseDate;
        private int purchaseCost;
        private String supplier;
        private String location;
        private String status;
    }
    
    /**
     * Initialize the grenade system
     */
    public void initializeSystem() {
        this.grenadeSystemId = "GRENADE_SYSTEM_" + System.currentTimeMillis();
        this.grenades = new HashMap<>();
        this.grenadeTypes = new HashMap<>();
        this.explosives = new HashMap<>();
        this.grenadeLaunchers = new HashMap<>();
        this.chainReactions = new HashMap<>();
        this.grenadeInventory = new HashMap<>();
        this.grenadeEffects = new HashMap<>();
        this.activeGrenades = new HashMap<>();
        this.grenadeCounts = new HashMap<>();
        this.grenadeStates = new HashMap<>();
        this.totalGrenades = 0;
        this.maxGrenadeCapacity = 100;
        this.isGrenadeSystemActive = false;
        
        initializeGrenadeTypes();
        initializeExplosives();
        initializeGrenadeLaunchers();
        initializeChainReactions();
    }
    
    /**
     * Initialize grenade types
     */
    private void initializeGrenadeTypes() {
        // Frag Grenade
        Grenade fragGrenade = Grenade.builder()
            .grenadeId("FRAG_GRENADE_001")
            .grenadeName("Fragmentation Grenade")
            .grenadeType(GrenadeType.FRAG_GRENADE)
            .damage(35)
            .radius(3)
            .range(15)
            .accuracy(85)
            .criticalChance(10)
            .criticalMultiplier(2)
            .cooldown(2)
            .currentCooldown(0)
            .isAvailable(true)
            .description("Standard fragmentation grenade with high damage and radius")
            .effects(Map.of("damage", 35, "radius", 3, "accuracy", 85))
            .abilities(Arrays.asList("FRAGMENTATION_DAMAGE", "AREA_EFFECT", "HIGH_DAMAGE"))
            .cost(500)
            .rarity("COMMON")
            .isUpgraded(false)
            .upgradeLevel(1)
            .upgradeBonuses(new HashMap<>())
            .upgradeAbilities(new ArrayList<>())
            .manufacturer("XCOM_WEAPONS")
            .modelNumber("GRENADE_FRAG_001")
            .serialNumber("SN_FRAG_001")
            .purchaseDate(new Date().toString())
            .purchaseCost(500)
            .supplier("XCOM_SUPPLY")
            .location("WEAPON_ARMORY")
            .status("ACTIVE")
            .build();
        grenades.put(fragGrenade.getGrenadeId(), fragGrenade);
        
        // Acid Grenade
        Grenade acidGrenade = Grenade.builder()
            .grenadeId("ACID_GRENADE_001")
            .grenadeName("Acid Grenade")
            .grenadeType(GrenadeType.ACID_GRENADE)
            .damage(25)
            .radius(2)
            .range(12)
            .accuracy(80)
            .criticalChance(15)
            .criticalMultiplier(1.5)
            .cooldown(3)
            .currentCooldown(0)
            .isAvailable(true)
            .description("Acid grenade that deals damage over time and degrades armor")
            .effects(Map.of("damage", 25, "radius", 2, "armor_degradation", 20))
            .abilities(Arrays.asList("ACID_DAMAGE", "ARMOR_DEGRADATION", "DAMAGE_OVER_TIME"))
            .cost(750)
            .rarity("UNCOMMON")
            .isUpgraded(false)
            .upgradeLevel(1)
            .upgradeBonuses(new HashMap<>())
            .upgradeAbilities(new ArrayList<>())
            .manufacturer("XCOM_WEAPONS")
            .modelNumber("GRENADE_ACID_001")
            .serialNumber("SN_ACID_001")
            .purchaseDate(new Date().toString())
            .purchaseCost(750)
            .supplier("XCOM_SUPPLY")
            .location("WEAPON_ARMORY")
            .status("ACTIVE")
            .build();
        grenades.put(acidGrenade.getGrenadeId(), acidGrenade);
        
        // Fire Grenade
        Grenade fireGrenade = Grenade.builder()
            .grenadeId("FIRE_GRENADE_001")
            .grenadeName("Fire Grenade")
            .grenadeType(GrenadeType.FIRE_GRENADE)
            .damage(30)
            .radius(2)
            .range(10)
            .accuracy(75)
            .criticalChance(20)
            .criticalMultiplier(1.8)
            .cooldown(4)
            .currentCooldown(0)
            .isAvailable(true)
            .description("Fire grenade that creates burning areas and deals fire damage")
            .effects(Map.of("damage", 30, "radius", 2, "burning", 25))
            .abilities(Arrays.asList("FIRE_DAMAGE", "BURNING_EFFECT", "AREA_CONTROL"))
            .cost(800)
            .rarity("UNCOMMON")
            .isUpgraded(false)
            .upgradeLevel(1)
            .upgradeBonuses(new HashMap<>())
            .upgradeAbilities(new ArrayList<>())
            .manufacturer("XCOM_WEAPONS")
            .modelNumber("GRENADE_FIRE_001")
            .serialNumber("SN_FIRE_001")
            .purchaseDate(new Date().toString())
            .purchaseCost(800)
            .supplier("XCOM_SUPPLY")
            .location("WEAPON_ARMORY")
            .status("ACTIVE")
            .build();
        grenades.put(fireGrenade.getGrenadeId(), fireGrenade);
        
        // Smoke Grenade
        Grenade smokeGrenade = Grenade.builder()
            .grenadeId("SMOKE_GRENADE_001")
            .grenadeName("Smoke Grenade")
            .grenadeType(GrenadeType.SMOKE_GRENADE)
            .damage(0)
            .radius(4)
            .range(8)
            .accuracy(90)
            .criticalChance(0)
            .criticalMultiplier(1)
            .cooldown(1)
            .currentCooldown(0)
            .isAvailable(true)
            .description("Smoke grenade that provides concealment and reduces visibility")
            .effects(Map.of("concealment", 30, "visibility_reduction", 40, "stealth_bonus", 25))
            .abilities(Arrays.asList("SMOKE_SCREEN", "CONCEALMENT", "STEALTH_BONUS"))
            .cost(300)
            .rarity("COMMON")
            .isUpgraded(false)
            .upgradeLevel(1)
            .upgradeBonuses(new HashMap<>())
            .upgradeAbilities(new ArrayList<>())
            .manufacturer("XCOM_WEAPONS")
            .modelNumber("GRENADE_SMOKE_001")
            .serialNumber("SN_SMOKE_001")
            .purchaseDate(new Date().toString())
            .purchaseCost(300)
            .supplier("XCOM_SUPPLY")
            .location("WEAPON_ARMORY")
            .status("ACTIVE")
            .build();
        grenades.put(smokeGrenade.getGrenadeId(), smokeGrenade);
        
        // Flashbang Grenade
        Grenade flashbangGrenade = Grenade.builder()
            .grenadeId("FLASHBANG_GRENADE_001")
            .grenadeName("Flashbang Grenade")
            .grenadeType(GrenadeType.FLASHBANG_GRENADE)
            .damage(5)
            .radius(3)
            .range(10)
            .accuracy(85)
            .criticalChance(0)
            .criticalMultiplier(1)
            .cooldown(2)
            .currentCooldown(0)
            .isAvailable(true)
            .description("Flashbang grenade that stuns and disorients enemies")
            .effects(Map.of("damage", 5, "radius", 3, "stun", 20))
            .abilities(Arrays.asList("STUN_EFFECT", "DISORIENTATION", "LOW_DAMAGE"))
            .cost(400)
            .rarity("COMMON")
            .isUpgraded(false)
            .upgradeLevel(1)
            .upgradeBonuses(new HashMap<>())
            .upgradeAbilities(new ArrayList<>())
            .manufacturer("XCOM_WEAPONS")
            .modelNumber("GRENADE_FLASHBANG_001")
            .serialNumber("SN_FLASHBANG_001")
            .purchaseDate(new Date().toString())
            .purchaseCost(400)
            .supplier("XCOM_SUPPLY")
            .location("WEAPON_ARMORY")
            .status("ACTIVE")
            .build();
        grenades.put(flashbangGrenade.getGrenadeId(), flashbangGrenade);
    }
    
    /**
     * Initialize explosives
     */
    private void initializeExplosives() {
        // C4 Explosive
        Explosive c4Explosive = Explosive.builder()
            .explosiveId("C4_EXPLOSIVE_001")
            .explosiveName("C4 Explosive")
            .explosiveType(ExplosiveType.C4_EXPLOSIVE)
            .damage(100)
            .radius(5)
            .triggerRange(0)
            .detonationDelay(0)
            .isArmed(false)
            .isDetonated(false)
            .triggerCondition("REMOTE")
            .placementLocation("")
            .assignedUnit("")
            .assignedFacility("")
            .assignedTechnician("")
            .isUnderMaintenance(false)
            .isUnderRepair(false)
            .explosiveStatus("INACTIVE")
            .maintenanceCost(200)
            .repairCost(500)
            .explosiveQuality("EXCELLENT")
            .explosiveCondition("NEW")
            .lastMaintenanceDate("")
            .nextMaintenanceDate("")
            .warrantyStatus("ACTIVE")
            .isWarrantied(true)
            .warrantyExpiryDate("")
            .explosiveNotes("High-powered remote detonated explosive")
            .explosiveStats(Map.of("damage", 100, "radius", 5, "trigger_range", 0))
            .explosiveAbilities(Arrays.asList("REMOTE_DETONATION", "HIGH_DAMAGE", "LARGE_RADIUS"))
            .manufacturer("XCOM_WEAPONS")
            .modelNumber("EXPLOSIVE_C4_001")
            .serialNumber("SN_C4_001")
            .purchaseDate(new Date().toString())
            .purchaseCost(2000)
            .supplier("XCOM_SUPPLY")
            .location("EXPLOSIVE_ARMORY")
            .status("ACTIVE")
            .build();
        explosives.put(c4Explosive.getExplosiveId(), c4Explosive);
        
        // Landmine
        Explosive landmine = Explosive.builder()
            .explosiveId("LANDMINE_001")
            .explosiveName("Landmine")
            .explosiveType(ExplosiveType.LANDMINE)
            .damage(75)
            .radius(3)
            .triggerRange(2)
            .detonationDelay(0)
            .isArmed(false)
            .isDetonated(false)
            .triggerCondition("PRESSURE")
            .placementLocation("")
            .assignedUnit("")
            .assignedFacility("")
            .assignedTechnician("")
            .isUnderMaintenance(false)
            .isUnderRepair(false)
            .explosiveStatus("INACTIVE")
            .maintenanceCost(150)
            .repairCost(400)
            .explosiveQuality("EXCELLENT")
            .explosiveCondition("NEW")
            .lastMaintenanceDate("")
            .nextMaintenanceDate("")
            .warrantyStatus("ACTIVE")
            .isWarrantied(true)
            .warrantyExpiryDate("")
            .explosiveNotes("Pressure activated explosive for area denial")
            .explosiveStats(Map.of("damage", 75, "radius", 3, "trigger_range", 2))
            .explosiveAbilities(Arrays.asList("PRESSURE_TRIGGER", "AREA_DENIAL", "HIDDEN_PLACEMENT"))
            .manufacturer("XCOM_WEAPONS")
            .modelNumber("EXPLOSIVE_LANDMINE_001")
            .serialNumber("SN_LANDMINE_001")
            .purchaseDate(new Date().toString())
            .purchaseCost(1500)
            .supplier("XCOM_SUPPLY")
            .location("EXPLOSIVE_ARMORY")
            .status("ACTIVE")
            .build();
        explosives.put(landmine.getExplosiveId(), landmine);
    }
    
    /**
     * Initialize grenade launchers
     */
    private void initializeGrenadeLaunchers() {
        // Standard Grenade Launcher
        GrenadeLauncher standardLauncher = GrenadeLauncher.builder()
            .launcherId("GRENADE_LAUNCHER_001")
            .launcherName("Standard Grenade Launcher")
            .damage(0)
            .range(20)
            .accuracy(80)
            .magazineSize(6)
            .currentAmmo(6)
            .reloadTime(3)
            .currentReloadTime(0)
            .isReloading(false)
            .description("Standard grenade launcher with 6-round magazine")
            .effects(Map.of("range", 20, "accuracy", 80, "magazine_size", 6))
            .abilities(Arrays.asList("GRENADE_LAUNCH", "MULTI_SHOT", "RAPID_RELOAD"))
            .cost(1500)
            .rarity("COMMON")
            .isUpgraded(false)
            .upgradeLevel(1)
            .upgradeBonuses(new HashMap<>())
            .upgradeAbilities(new ArrayList<>())
            .manufacturer("XCOM_WEAPONS")
            .modelNumber("LAUNCHER_GRENADE_001")
            .serialNumber("SN_LAUNCHER_001")
            .purchaseDate(new Date().toString())
            .purchaseCost(1500)
            .supplier("XCOM_SUPPLY")
            .location("WEAPON_ARMORY")
            .status("ACTIVE")
            .build();
        grenadeLaunchers.put(standardLauncher.getLauncherId(), standardLauncher);
        
        // Heavy Grenade Launcher
        GrenadeLauncher heavyLauncher = GrenadeLauncher.builder()
            .launcherId("HEAVY_GRENADE_LAUNCHER_001")
            .launcherName("Heavy Grenade Launcher")
            .damage(0)
            .range(25)
            .accuracy(75)
            .magazineSize(4)
            .currentAmmo(4)
            .reloadTime(4)
            .currentReloadTime(0)
            .isReloading(false)
            .description("Heavy grenade launcher with increased range and damage")
            .effects(Map.of("range", 25, "accuracy", 75, "magazine_size", 4))
            .abilities(Arrays.asList("HEAVY_GRENADE_LAUNCH", "INCREASED_RANGE", "HIGH_DAMAGE"))
            .cost(2500)
            .rarity("UNCOMMON")
            .isUpgraded(false)
            .upgradeLevel(1)
            .upgradeBonuses(new HashMap<>())
            .upgradeAbilities(new ArrayList<>())
            .manufacturer("XCOM_WEAPONS")
            .modelNumber("LAUNCHER_HEAVY_001")
            .serialNumber("SN_HEAVY_LAUNCHER_001")
            .purchaseDate(new Date().toString())
            .purchaseCost(2500)
            .supplier("XCOM_SUPPLY")
            .location("WEAPON_ARMORY")
            .status("ACTIVE")
            .build();
        grenadeLaunchers.put(heavyLauncher.getLauncherId(), heavyLauncher);
    }
    
    /**
     * Initialize chain reactions
     */
    private void initializeChainReactions() {
        // Explosive Chain Reaction
        ChainReaction explosiveChain = ChainReaction.builder()
            .reactionId("EXPLOSIVE_CHAIN_001")
            .reactionName("Explosive Chain Reaction")
            .reactionType(ChainReactionType.EXPLOSIVE_CHAIN)
            .damage(50)
            .radius(4)
            .chainLength(3)
            .currentChainLength(0)
            .isActive(false)
            .description("Explosive chain reaction that spreads damage")
            .effects(Map.of("damage", 50, "radius", 4, "chain_length", 3))
            .abilities(Arrays.asList("EXPLOSIVE_CHAIN", "DAMAGE_SPREAD", "AREA_DAMAGE"))
            .cost(1000)
            .rarity("RARE")
            .isUpgraded(false)
            .upgradeLevel(1)
            .upgradeBonuses(new HashMap<>())
            .upgradeAbilities(new ArrayList<>())
            .manufacturer("XCOM_WEAPONS")
            .modelNumber("CHAIN_EXPLOSIVE_001")
            .serialNumber("SN_CHAIN_EXPLOSIVE_001")
            .purchaseDate(new Date().toString())
            .purchaseCost(1000)
            .supplier("XCOM_SUPPLY")
            .location("EXPLOSIVE_ARMORY")
            .status("ACTIVE")
            .build();
        chainReactions.put(explosiveChain.getReactionId(), explosiveChain);
        
        // Fire Spread Reaction
        ChainReaction fireSpread = ChainReaction.builder()
            .reactionId("FIRE_SPREAD_001")
            .reactionName("Fire Spread Reaction")
            .reactionType(ChainReactionType.FIRE_SPREAD)
            .damage(30)
            .radius(3)
            .chainLength(4)
            .currentChainLength(0)
            .isActive(false)
            .description("Fire spread reaction that creates burning areas")
            .effects(Map.of("damage", 30, "radius", 3, "chain_length", 4))
            .abilities(Arrays.asList("FIRE_SPREAD", "BURNING_AREA", "AREA_CONTROL"))
            .cost(800)
            .rarity("UNCOMMON")
            .isUpgraded(false)
            .upgradeLevel(1)
            .upgradeBonuses(new HashMap<>())
            .upgradeAbilities(new ArrayList<>())
            .manufacturer("XCOM_WEAPONS")
            .modelNumber("CHAIN_FIRE_001")
            .serialNumber("SN_CHAIN_FIRE_001")
            .purchaseDate(new Date().toString())
            .purchaseCost(800)
            .supplier("XCOM_SUPPLY")
            .location("EXPLOSIVE_ARMORY")
            .status("ACTIVE")
            .build();
        chainReactions.put(fireSpread.getReactionId(), fireSpread);
    }
    
    /**
     * Throw grenade
     */
    public boolean throwGrenade(String grenadeId, String unitId, int targetX, int targetY) {
        Grenade grenade = grenades.get(grenadeId);
        if (grenade == null || !grenade.isAvailable()) {
            return false;
        }
        
        // Calculate area of effect damage
        calculateAreaOfEffectDamage(grenade, targetX, targetY);
        
        // Apply environmental destruction
        applyEnvironmentalDestruction(grenade, targetX, targetY);
        
        // Check for chain reactions
        checkChainReactions(grenade, targetX, targetY);
        
        // Update grenade state
        grenade.setCurrentCooldown(grenade.getCooldown());
        grenade.setAvailable(false);
        
        // Add to active grenades
        activeGrenades.computeIfAbsent(unitId, k -> new ArrayList<>()).add(grenadeId);
        
        return true;
    }
    
    /**
     * Place explosive
     */
    public boolean placeExplosive(String explosiveId, String unitId, int targetX, int targetY) {
        Explosive explosive = explosives.get(explosiveId);
        if (explosive == null || explosive.isArmed()) {
            return false;
        }
        
        explosive.setPlacementLocation(targetX + "," + targetY);
        explosive.setAssignedUnit(unitId);
        explosive.setArmed(true);
        explosive.setExplosiveStatus("ARMED");
        
        return true;
    }
    
    /**
     * Detonate explosive
     */
    public boolean detonateExplosive(String explosiveId) {
        Explosive explosive = explosives.get(explosiveId);
        if (explosive == null || !explosive.isArmed() || explosive.isDetonated()) {
            return false;
        }
        
        // Calculate damage and radius
        calculateExplosiveDamage(explosive);
        
        // Apply environmental destruction
        applyEnvironmentalDestruction(explosive);
        
        // Check for chain reactions
        checkChainReactions(explosive);
        
        // Update explosive state
        explosive.setDetonated(true);
        explosive.setExplosiveStatus("DETONATED");
        
        return true;
    }
    
    /**
     * Calculate area of effect damage
     */
    private void calculateAreaOfEffectDamage(Grenade grenade, int targetX, int targetY) {
        int damage = grenade.getDamage();
        int radius = grenade.getRadius();
        
        // Calculate damage falloff based on distance from center
        for (int x = targetX - radius; x <= targetX + radius; x++) {
            for (int y = targetY - radius; y <= targetY + radius; y++) {
                double distance = Math.sqrt((x - targetX) * (x - targetX) + (y - targetY) * (y - targetY));
                if (distance <= radius) {
                    double damageMultiplier = 1.0 - (distance / radius) * 0.5; // 50% damage falloff at edge
                    int finalDamage = (int) (damage * damageMultiplier);
                    
                    // Apply damage to units in range
                    applyDamageToUnitsInRange(x, y, finalDamage, grenade);
                }
            }
        }
    }
    
    /**
     * Apply environmental destruction
     */
    private void applyEnvironmentalDestruction(Grenade grenade, int targetX, int targetY) {
        int radius = grenade.getRadius();
        
        // Destroy or damage environmental objects in radius
        for (int x = targetX - radius; x <= targetX + radius; x++) {
            for (int y = targetY - radius; y <= targetY + radius; y++) {
                double distance = Math.sqrt((x - targetX) * (x - targetX) + (y - targetY) * (y - targetY));
                if (distance <= radius) {
                    // Apply destruction to terrain and objects
                    destroyTerrainAtLocation(x, y, grenade);
                    destroyObjectsAtLocation(x, y, grenade);
                }
            }
        }
    }
    
    /**
     * Check for chain reactions
     */
    private void checkChainReactions(Grenade grenade, int targetX, int targetY) {
        // Check if grenade can trigger chain reactions
        if (grenade.getGrenadeType() == GrenadeType.FIRE_GRENADE) {
            triggerFireSpread(targetX, targetY);
        } else if (grenade.getGrenadeType() == GrenadeType.ACID_GRENADE) {
            triggerChemicalReaction(targetX, targetY);
        } else if (grenade.getGrenadeType() == GrenadeType.ELECTRICAL_GRENADE) {
            triggerElectricalArc(targetX, targetY);
        }
    }
    
    /**
     * Trigger fire spread
     */
    private void triggerFireSpread(int startX, int startY) {
        ChainReaction fireSpread = chainReactions.get("FIRE_SPREAD_001");
        if (fireSpread != null && !fireSpread.isActive()) {
            fireSpread.setActive(true);
            fireSpread.setCurrentChainLength(1);
            
            // Spread fire to adjacent tiles
            spreadFireToAdjacentTiles(startX, startY, fireSpread);
        }
    }
    
    /**
     * Spread fire to adjacent tiles
     */
    private void spreadFireToAdjacentTiles(int centerX, int centerY, ChainReaction fireSpread) {
        int radius = fireSpread.getRadius();
        int chainLength = fireSpread.getChainLength();
        
        for (int x = centerX - radius; x <= centerX + radius; x++) {
            for (int y = centerY - radius; y <= centerY + radius; y++) {
                if (x == centerX && y == centerY) continue; // Skip center
                
                double distance = Math.sqrt((x - centerX) * (x - centerX) + (y - centerY) * (y - centerY));
                if (distance <= radius && fireSpread.getCurrentChainLength() < chainLength) {
                    // Create fire hazard at location
                    createFireHazard(x, y, fireSpread);
                    fireSpread.setCurrentChainLength(fireSpread.getCurrentChainLength() + 1);
                }
            }
        }
    }
    
    /**
     * Create fire hazard
     */
    private void createFireHazard(int x, int y, ChainReaction fireSpread) {
        // Create environmental hazard at location
        // This would integrate with the EnvironmentalHazardsManager
        System.out.println("Fire hazard created at (" + x + ", " + y + ")");
    }
    
    /**
     * Apply damage to units in range
     */
    private void applyDamageToUnitsInRange(int x, int y, int damage, Grenade grenade) {
        // This would integrate with the combat system to apply damage to units
        System.out.println("Applying " + damage + " damage at (" + x + ", " + y + ") from " + grenade.getGrenadeName());
    }
    
    /**
     * Destroy terrain at location
     */
    private void destroyTerrainAtLocation(int x, int y, Grenade grenade) {
        // This would integrate with the terrain system to destroy terrain
        System.out.println("Destroying terrain at (" + x + ", " + y + ") with " + grenade.getGrenadeName());
    }
    
    /**
     * Destroy objects at location
     */
    private void destroyObjectsAtLocation(int x, int y, Grenade grenade) {
        // This would integrate with the object system to destroy objects
        System.out.println("Destroying objects at (" + x + ", " + y + ") with " + grenade.getGrenadeName());
    }
    
    /**
     * Calculate explosive damage
     */
    private void calculateExplosiveDamage(Explosive explosive) {
        // Similar to grenade damage calculation but for explosives
        System.out.println("Calculating damage for " + explosive.getExplosiveName());
    }
    
    /**
     * Apply environmental destruction for explosive
     */
    private void applyEnvironmentalDestruction(Explosive explosive) {
        // Similar to grenade environmental destruction but for explosives
        System.out.println("Applying environmental destruction for " + explosive.getExplosiveName());
    }
    
    /**
     * Check chain reactions for explosive
     */
    private void checkChainReactions(Explosive explosive) {
        // Similar to grenade chain reactions but for explosives
        System.out.println("Checking chain reactions for " + explosive.getExplosiveName());
    }
    
    /**
     * Trigger chemical reaction
     */
    private void triggerChemicalReaction(int x, int y) {
        System.out.println("Chemical reaction triggered at (" + x + ", " + y + ")");
    }
    
    /**
     * Trigger electrical arc
     */
    private void triggerElectricalArc(int x, int y) {
        System.out.println("Electrical arc triggered at (" + x + ", " + y + ")");
    }
    
    /**
     * Get grenade by ID
     */
    public Grenade getGrenade(String grenadeId) {
        return grenades.get(grenadeId);
    }
    
    /**
     * Get explosive by ID
     */
    public Explosive getExplosive(String explosiveId) {
        return explosives.get(explosiveId);
    }
    
    /**
     * Get grenade launcher by ID
     */
    public GrenadeLauncher getGrenadeLauncher(String launcherId) {
        return grenadeLaunchers.get(launcherId);
    }
    
    /**
     * Get chain reaction by ID
     */
    public ChainReaction getChainReaction(String reactionId) {
        return chainReactions.get(reactionId);
    }
    
    /**
     * Get all grenades
     */
    public Map<String, Grenade> getAllGrenades() {
        return new HashMap<>(grenades);
    }
    
    /**
     * Get all explosives
     */
    public Map<String, Explosive> getAllExplosives() {
        return new HashMap<>(explosives);
    }
    
    /**
     * Get all grenade launchers
     */
    public Map<String, GrenadeLauncher> getAllGrenadeLaunchers() {
        return new HashMap<>(grenadeLaunchers);
    }
    
    /**
     * Get all chain reactions
     */
    public Map<String, ChainReaction> getAllChainReactions() {
        return new HashMap<>(chainReactions);
    }
}
