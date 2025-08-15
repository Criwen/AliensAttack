package com.aliensattack.combat;

import com.aliensattack.core.model.Unit;
import com.aliensattack.core.model.Explosive;
import com.aliensattack.core.model.SquadTactic;
import com.aliensattack.core.model.PsionicAbility;
import com.aliensattack.core.model.Position;
import com.aliensattack.core.model.Mission;
import com.aliensattack.combat.CombatResult;
import com.aliensattack.core.enums.*;
import com.aliensattack.field.TacticalField;

import lombok.Getter;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Ultimate combat manager with all XCOM 2 mechanics:
 * - Psionic system and mental abilities
 * - Suppression and covering fire
 * - Explosives and traps
 * - Squad tactics and coordination
 * - Evolution and mutation system
 */
@Getter
public class UltimateCombatManager extends ComprehensiveCombatManager {
    private final Map<String, Explosive> activeExplosives;
    private final Map<String, SquadTactic> activeSquadTactics;
    private final Random random;
    
    public UltimateCombatManager(TacticalField field, Mission mission) {
        super(field, mission);
        this.activeExplosives = new ConcurrentHashMap<>();
        this.activeSquadTactics = new ConcurrentHashMap<>();
        this.random = new Random();
    }
    
    /**
     * Use psionic ability
     */
    public CombatResult usePsionicAbility(Unit caster, Unit target, PsionicType psionicType) {
        if (!caster.canTakeActions() || caster.getActionPoints() < 1) {
            return new CombatResult(false, 0, "Cannot use psionic ability");
        }
        
        // Find the psionic ability
        PsionicAbility ability = caster.getPsionicAbilities().stream()
            .filter(a -> a.getType() == psionicType && a.canUse())
            .findFirst()
            .orElse(null);
            
        if (ability == null) {
            return new CombatResult(false, 0, "Psionic ability not available");
        }
        
        // Check range
        Position casterPos = caster.getPosition();
        Position targetPos = target.getPosition();
        int distance = Math.abs(casterPos.getX() - targetPos.getX()) + 
                      Math.abs(casterPos.getY() - targetPos.getY());
        
        if (distance > ability.getRange()) {
            return new CombatResult(false, 0, "Target out of range");
        }
        
        // Apply psionic effect based on type
        CombatResult result = applyPsionicEffect(caster, target, psionicType);
        
        if (result.isSuccess()) {
            ability.use();
            caster.spendActionPoint();
        }
        
        return result;
    }
    
    /**
     * Apply psionic effect
     */
    private CombatResult applyPsionicEffect(Unit caster, Unit target, PsionicType psionicType) {
        int psiPower = caster.getPsiStrength();
        int psiResistance = target.getPsiResistance();
        int effectivePower = Math.max(0, psiPower - psiResistance);
        
        return switch (psionicType) {
            case MIND_CONTROL -> {
                if (effectivePower > 50) {
                    // Mind control successful
                    yield new CombatResult(true, 0, "Mind control successful!");
                } else {
                    yield new CombatResult(false, 0, "Mind control failed - target resisted");
                }
            }
            case MIND_SCORCH -> {
                int damage = effectivePower / 10;
                boolean killed = target.takeDamage(damage);
                yield new CombatResult(true, damage, killed ? "Target killed by mental attack!" : "Mental damage dealt!");
            }
            case PSYCHIC_BLAST -> {
                int damage = effectivePower / 8;
                boolean killed = target.takeDamage(damage);
                yield new CombatResult(true, damage, killed ? "Target killed by psychic blast!" : "Psychic blast damage dealt!");
            }
            case TELEPORT -> {
                // Teleport caster to target position
                Position targetPos = target.getPosition();
                caster.setPosition(targetPos);
                yield new CombatResult(true, 0, "Unit teleported to target position!");
            }
            case PSYCHIC_DOMINANCE -> {
                if (target.getUnitType() == UnitType.ROBOTIC && effectivePower > 40) {
                    yield new CombatResult(true, 0, "Robotic unit controlled!");
                } else {
                    yield new CombatResult(false, 0, "Target is not robotic or resisted control");
                }
            }
            case MIND_MERGE -> {
                // Share consciousness between caster and target
                yield new CombatResult(true, 0, "Minds merged - consciousness shared!");
            }
            case PSYCHIC_BARRIER -> {
                // Create protective barrier
                yield new CombatResult(true, effectivePower, "Psychic barrier created!");
            }
            case PSYCHIC_SHIELD -> {
                // Apply psychic shield
                yield new CombatResult(true, effectivePower, "Psychic shield applied!");
            }
            case MIND_SHIELD -> {
                // Apply mind shield
                yield new CombatResult(true, effectivePower, "Mind shield applied!");
            }
            case DOMINATION -> {
                if (effectivePower > 60) {
                    yield new CombatResult(true, 0, "Target dominated!");
                } else {
                    yield new CombatResult(false, 0, "Domination resisted");
                }
            }
            case TELEPATHY -> {
                yield new CombatResult(true, 0, "Enemy information revealed!");
            }
            case PSYCHIC_SCREAM -> {
                int stunDamage = effectivePower / 12;
                yield new CombatResult(true, stunDamage, "Target stunned by psychic scream!");
            }
            default -> new CombatResult(false, 0, "Unknown psionic ability");
        };
    }
    
    /**
     * Apply suppression to target
     */
    public boolean applySuppression(Unit suppressor, Unit target, int turns) {
        if (!suppressor.canTakeActions() || suppressor.getActionPoints() < 1) {
            return false;
        }
        
        if (target.isSuppressed()) {
            return false; // Already suppressed
        }
        
        target.applySuppression(turns);
        suppressor.spendActionPoint();
        
        return true;
    }
    
    /**
     * Place explosive
     */
    public boolean placeExplosive(Unit unit, Explosive explosive, int x, int y) {
        if (!unit.canTakeActions() || unit.getActionPoints() < 1) {
            return false;
        }
        
        if (!unit.getExplosives().contains(explosive)) {
            return false; // Unit doesn't have this explosive
        }
        
        // Place explosive on the field
        String key = x + "_" + y;
        activeExplosives.put(key, explosive);
        explosive.arm();
        
        unit.removeExplosive(explosive);
        unit.spendActionPoint();
        
        return true;
    }
    
    /**
     * Detonate remote explosive
     */
    public List<CombatResult> detonateExplosive(int x, int y) {
        String key = x + "_" + y;
        Explosive explosive = activeExplosives.get(key);
        
        if (explosive == null || !explosive.isRemoteTriggered()) {
            return List.of(new CombatResult(false, 0, "No remote explosive at location"));
        }
        
        List<CombatResult> results = new ArrayList<>();
        Position explosionPos = new Position(x, y);
        
        // Find all units in explosion radius
        List<Unit> unitsInRadius = ((TacticalField)getField()).getUnitsInRange(explosionPos, explosive.getRadius());
        
        for (Unit unit : unitsInRadius) {
            if (unit.isAlive()) {
                int damage = explosive.getDamage();
                boolean killed = unit.takeDamage(damage);
                
                String message = killed ? "Unit killed by explosion!" : "Unit damaged by explosion!";
                results.add(new CombatResult(true, damage, message));
            }
        }
        
        // Remove explosive from field
        activeExplosives.remove(key);
        explosive.detonate();
        
        return results;
    }
    
    /**
     * Activate squad tactic
     */
    public boolean activateSquadTactic(SquadTactic tactic, List<Unit> participants) {
        if (participants.size() < 2) {
            return false; // Need at least 2 units for squad tactic
        }
        
        // Check if all participants can take actions
        for (Unit unit : participants) {
            if (!unit.canTakeActions() || unit.getActionPoints() < tactic.getActionPointCost()) {
                return false;
            }
        }
        
        // Activate tactic
        tactic.activate();
        for (Unit unit : participants) {
            unit.setActiveSquadTactic(tactic);
            unit.spendActionPoint();
            tactic.addParticipant(unit.getName());
        }
        
        activeSquadTactics.put(tactic.getName(), tactic);
        
        return true;
    }
    
    /**
     * Evolve unit
     */
    public boolean evolveUnit(Unit unit, String mutation) {
        if (unit.getEvolutionLevel() >= 5) {
            return false; // Max evolution level reached
        }
        
        unit.setEvolutionLevel(unit.getEvolutionLevel() + 1);
        unit.addMutation(mutation);
        
        // Apply evolution bonuses
        switch (mutation.toLowerCase()) {
            case "enhanced_strength" -> unit.setAttackDamage(unit.getAttackDamage() + 2);
            case "enhanced_speed" -> unit.setMovementRange(unit.getMovementRange() + 1);
            case "enhanced_accuracy" -> {
                if (unit.getWeapon() != null) {
                    unit.getWeapon().setAccuracy(unit.getWeapon().getAccuracy() + 5);
                }
            }
            case "enhanced_defense" -> unit.setMaxHealth(unit.getMaxHealth() + 5);
            case "psionic_awakening" -> unit.setPsiStrength(unit.getPsiStrength() + 10);
        }
        
        return true;
    }
    
    /**
     * Process proximity mines
     */
    public void processProximityMines() {
        for (Map.Entry<String, Explosive> entry : activeExplosives.entrySet()) {
            Explosive explosive = entry.getValue();
            
            if (explosive.isProximityTriggered() && explosive.isArmed()) {
                String[] coords = entry.getKey().split("_");
                int x = Integer.parseInt(coords[0]);
                int y = Integer.parseInt(coords[1]);
                Position minePos = new Position(x, y);
                
                // Check if any unit is adjacent to mine
                List<Unit> nearbyUnits = ((TacticalField)getField()).getUnitsInRange(minePos, 1);
                
                if (!nearbyUnits.isEmpty()) {
                    // Trigger explosion
                    detonateExplosive(x, y);
                }
            }
        }
    }
    
    /**
     * Process timed explosives
     */
    public void processTimedExplosives() {
        for (Map.Entry<String, Explosive> entry : activeExplosives.entrySet()) {
            Explosive explosive = entry.getValue();
            explosive.decrementTimer();
            
            if (explosive.isReadyToExplode()) {
                String[] coords = entry.getKey().split("_");
                int x = Integer.parseInt(coords[0]);
                int y = Integer.parseInt(coords[1]);
                detonateExplosive(x, y);
            }
        }
    }
    
    /**
     * Process squad tactics
     */
    public void processSquadTactics() {
        for (SquadTactic tactic : activeSquadTactics.values()) {
            tactic.decrementDuration();
            
            if (!tactic.isActive()) {
                // Remove tactic from all participating units
                for (Unit unit : getAllUnits()) {
                    if (unit.getActiveSquadTactic() == tactic) {
                        unit.setActiveSquadTactic(null);
                    }
                }
            }
        }
        
        // Remove inactive tactics
        activeSquadTactics.entrySet().removeIf(entry -> !entry.getValue().isActive());
    }
    
    /**
     * Process suppression
     */
    public void processSuppression() {
        for (Unit unit : getAllUnits()) {
            if (unit.isSuppressed()) {
                unit.setSuppressionTurns(unit.getSuppressionTurns() - 1);
                
                if (unit.getSuppressionTurns() <= 0) {
                    unit.removeSuppression();
                }
            }
        }
    }
    
    public void endTurnRound() {
        // Process explosives
        processProximityMines();
        processTimedExplosives();
        
        // Process squad tactics
        processSquadTactics();
        
        // Process suppression
        processSuppression();
        
        super.endTurnCore();
    }
} 