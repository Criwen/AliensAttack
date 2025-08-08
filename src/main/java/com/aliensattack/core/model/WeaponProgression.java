package com.aliensattack.core.model;

import com.aliensattack.core.enums.WeaponType;
import lombok.Getter;
import lombok.Setter;

import java.util.*;

/**
 * Weapon Progression - Research and weapon upgrades
 * Implements XCOM 2 Weapon Progression System
 */
@Getter
@Setter
public class WeaponProgression {
    
    private String progressionId;
    private Map<String, Weapon> availableWeapons;
    private Map<String, Weapon> researchedWeapons;
    private Map<String, Integer> researchProgress;
    private Map<String, List<String>> weaponUpgrades;
    private Map<String, Integer> upgradeCosts;
    private List<String> researchQueue;
    private int researchPoints;
    private int totalResearchCompleted;
    private Random random;
    
    public WeaponProgression() {
        this.progressionId = "WEAPON_PROG_" + System.currentTimeMillis();
        this.availableWeapons = new HashMap<>();
        this.researchedWeapons = new HashMap<>();
        this.researchProgress = new HashMap<>();
        this.weaponUpgrades = new HashMap<>();
        this.upgradeCosts = new HashMap<>();
        this.researchQueue = new ArrayList<>();
        this.researchPoints = 0;
        this.totalResearchCompleted = 0;
        this.random = new Random();
        
        initializeWeaponProgression();
    }
    
    private void initializeWeaponProgression() {
        // Initialize basic weapons
        initializeBasicWeapons();
        
        // Initialize research requirements
        initializeResearchRequirements();
        
        // Initialize upgrade paths
        initializeUpgradePaths();
    }
    
    private void initializeBasicWeapons() {
        // Basic weapons available from start
        Weapon assaultRifle = new Weapon("Assault Rifle", WeaponType.RIFLE, 25, 35, 10, 85, 8);
        Weapon pistol = new Weapon("Pistol", WeaponType.PISTOL, 15, 25, 5, 75, 6);
        Weapon shotgun = new Weapon("Shotgun", WeaponType.SHOTGUN, 30, 45, 15, 70, 4);
        
        availableWeapons.put("ASSAULT_RIFLE", assaultRifle);
        availableWeapons.put("PISTOL", pistol);
        availableWeapons.put("SHOTGUN", shotgun);
        
        // Mark as researched
        researchedWeapons.put("ASSAULT_RIFLE", assaultRifle);
        researchedWeapons.put("PISTOL", pistol);
        researchedWeapons.put("SHOTGUN", shotgun);
    }
    
    private void initializeResearchRequirements() {
        // Research requirements for advanced weapons
        researchProgress.put("MAG_WEAPONS", 0);
        researchProgress.put("PLASMA_WEAPONS", 0);
        researchProgress.put("BEAM_WEAPONS", 0);
        researchProgress.put("MAGNETIC_WEAPONS", 0);
        researchProgress.put("POWERED_WEAPONS", 0);
        researchProgress.put("EXPERIMENTAL_WEAPONS", 0);
        
        // Research costs
        upgradeCosts.put("MAG_WEAPONS", 50);
        upgradeCosts.put("PLASMA_WEAPONS", 100);
        upgradeCosts.put("BEAM_WEAPONS", 150);
        upgradeCosts.put("MAGNETIC_WEAPONS", 75);
        upgradeCosts.put("POWERED_WEAPONS", 125);
        upgradeCosts.put("EXPERIMENTAL_WEAPONS", 200);
    }
    
    private void initializeUpgradePaths() {
        // Assault Rifle upgrades
        weaponUpgrades.put("ASSAULT_RIFLE", Arrays.asList("MAG_ASSAULT_RIFLE", "PLASMA_ASSAULT_RIFLE"));
        
        // Pistol upgrades
        weaponUpgrades.put("PISTOL", Arrays.asList("MAG_PISTOL", "PLASMA_PISTOL"));
        
        // Shotgun upgrades
        weaponUpgrades.put("SHOTGUN", Arrays.asList("MAG_SHOTGUN", "PLASMA_SHOTGUN"));
        
        // Sniper Rifle upgrades
        weaponUpgrades.put("SNIPER_RIFLE", Arrays.asList("MAG_SNIPER", "PLASMA_SNIPER"));
        
        // Heavy Weapon upgrades
        weaponUpgrades.put("HEAVY_WEAPON", Arrays.asList("MAG_HEAVY", "PLASMA_HEAVY"));
    }
    
    /**
     * Start research on weapon technology
     */
    public boolean startResearch(String researchType) {
        if (researchProgress.containsKey(researchType) && researchProgress.get(researchType) == 0) {
            researchQueue.add(researchType);
            logProgressionEvent("Started research on " + researchType);
            return true;
        }
        return false;
    }
    
    /**
     * Add research points
     */
    public void addResearchPoints(int points) {
        researchPoints += points;
        processResearchQueue();
    }
    
    /**
     * Process research queue
     */
    private void processResearchQueue() {
        if (researchQueue.isEmpty() || researchPoints <= 0) {
            return;
        }
        
        String currentResearch = researchQueue.get(0);
        int cost = upgradeCosts.getOrDefault(currentResearch, 50);
        
        if (researchPoints >= cost) {
            // Complete research
            researchPoints -= cost;
            researchProgress.put(currentResearch, 100);
            researchQueue.remove(0);
            totalResearchCompleted++;
            
            // Unlock new weapons
            unlockWeaponsForResearch(currentResearch);
            
            logProgressionEvent("Completed research on " + currentResearch);
        } else {
            // Partial progress
            int progress = Math.min(researchPoints, cost);
            researchPoints -= progress;
            int currentProgress = researchProgress.getOrDefault(currentResearch, 0);
            researchProgress.put(currentResearch, currentProgress + progress);
            
            logProgressionEvent("Research progress on " + currentResearch + ": " + 
                              (currentProgress + progress) + "/" + cost);
        }
    }
    
    /**
     * Unlock weapons for completed research
     */
    private void unlockWeaponsForResearch(String researchType) {
        switch (researchType) {
            case "MAG_WEAPONS":
                unlockMagneticWeapons();
                break;
            case "PLASMA_WEAPONS":
                unlockPlasmaWeapons();
                break;
            case "BEAM_WEAPONS":
                unlockBeamWeapons();
                break;
            case "MAGNETIC_WEAPONS":
                unlockMagneticWeapons();
                break;
            case "POWERED_WEAPONS":
                unlockPoweredWeapons();
                break;
            case "EXPERIMENTAL_WEAPONS":
                unlockExperimentalWeapons();
                break;
        }
    }
    
    private void unlockMagneticWeapons() {
        Weapon magAssaultRifle = new Weapon("Magnetic Assault Rifle", WeaponType.RIFLE, 35, 50, 15, 90, 8);
        Weapon magPistol = new Weapon("Magnetic Pistol", WeaponType.PISTOL, 25, 35, 8, 85, 6);
        Weapon magShotgun = new Weapon("Magnetic Shotgun", WeaponType.SHOTGUN, 40, 55, 20, 80, 4);
        
        availableWeapons.put("MAG_ASSAULT_RIFLE", magAssaultRifle);
        availableWeapons.put("MAG_PISTOL", magPistol);
        availableWeapons.put("MAG_SHOTGUN", magShotgun);
        
        logProgressionEvent("Unlocked Magnetic Weapons");
    }
    
    private void unlockPlasmaWeapons() {
        Weapon plasmaAssaultRifle = new Weapon("Plasma Assault Rifle", WeaponType.RIFLE, 45, 65, 20, 95, 8);
        Weapon plasmaPistol = new Weapon("Plasma Pistol", WeaponType.PISTOL, 35, 50, 12, 90, 6);
        Weapon plasmaShotgun = new Weapon("Plasma Shotgun", WeaponType.SHOTGUN, 50, 70, 25, 85, 4);
        
        availableWeapons.put("PLASMA_ASSAULT_RIFLE", plasmaAssaultRifle);
        availableWeapons.put("PLASMA_PISTOL", plasmaPistol);
        availableWeapons.put("PLASMA_SHOTGUN", plasmaShotgun);
        
        logProgressionEvent("Unlocked Plasma Weapons");
    }
    
    private void unlockBeamWeapons() {
        Weapon beamRifle = new Weapon("Beam Rifle", WeaponType.SNIPER_RIFLE, 60, 80, 25, 95, 10);
        Weapon beamPistol = new Weapon("Beam Pistol", WeaponType.PISTOL, 40, 55, 15, 90, 6);
        
        availableWeapons.put("BEAM_RIFLE", beamRifle);
        availableWeapons.put("BEAM_PISTOL", beamPistol);
        
        logProgressionEvent("Unlocked Beam Weapons");
    }
    
    private void unlockPoweredWeapons() {
        Weapon poweredRifle = new Weapon("Powered Rifle", WeaponType.RIFLE, 55, 75, 22, 90, 8);
        Weapon poweredShotgun = new Weapon("Powered Shotgun", WeaponType.SHOTGUN, 60, 80, 28, 85, 4);
        
        availableWeapons.put("POWERED_RIFLE", poweredRifle);
        availableWeapons.put("POWERED_SHOTGUN", poweredShotgun);
        
        logProgressionEvent("Unlocked Powered Weapons");
    }
    
    private void unlockExperimentalWeapons() {
        Weapon experimentalRifle = new Weapon("Experimental Rifle", WeaponType.RIFLE, 70, 90, 30, 95, 8);
        Weapon experimentalPistol = new Weapon("Experimental Pistol", WeaponType.PISTOL, 50, 65, 18, 95, 6);
        
        availableWeapons.put("EXPERIMENTAL_RIFLE", experimentalRifle);
        availableWeapons.put("EXPERIMENTAL_PISTOL", experimentalPistol);
        
        logProgressionEvent("Unlocked Experimental Weapons");
    }
    
    /**
     * Get weapon for unit
     */
    public Weapon getWeaponForUnit(Unit unit, String weaponType) {
        if (availableWeapons.containsKey(weaponType)) {
            return availableWeapons.get(weaponType);
        }
        
        // Fallback to basic weapon
        return availableWeapons.get("ASSAULT_RIFLE");
    }
    
    /**
     * Upgrade unit's weapon
     */
    public boolean upgradeUnitWeapon(Unit unit, String newWeaponType) {
        Weapon newWeapon = availableWeapons.get(newWeaponType);
        if (newWeapon != null) {
            unit.setWeapon(newWeapon);
            logProgressionEvent("Upgraded " + unit.getName() + " to " + newWeaponType);
            return true;
        }
        return false;
    }
    
    /**
     * Get available upgrades for weapon
     */
    public List<String> getAvailableUpgrades(String weaponType) {
        List<String> upgrades = weaponUpgrades.get(weaponType);
        if (upgrades == null) {
            return new ArrayList<>();
        }
        
        // Filter to only available upgrades
        return upgrades.stream()
                .filter(upgrade -> availableWeapons.containsKey(upgrade))
                .toList();
    }
    
    /**
     * Get research progress
     */
    public int getResearchProgress(String researchType) {
        return researchProgress.getOrDefault(researchType, 0);
    }
    
    /**
     * Check if research is completed
     */
    public boolean isResearchCompleted(String researchType) {
        return researchProgress.getOrDefault(researchType, 0) >= 100;
    }
    
    /**
     * Get progression statistics
     */
    public String getProgressionStatistics() {
        return String.format("Weapon Progression: %d weapons available, %d research completed, %d points remaining",
                availableWeapons.size(), totalResearchCompleted, researchPoints);
    }
    
    private void logProgressionEvent(String event) {
        System.out.println("Weapon Progression: " + event);
    }
}
