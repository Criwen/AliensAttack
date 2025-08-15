package com.aliensattack.combat.strategies;

import com.aliensattack.combat.interfaces.ICombatStrategy;
import com.aliensattack.core.model.Unit;
import com.aliensattack.core.model.Position;
import com.aliensattack.combat.CombatResult;
import lombok.extern.log4j.Log4j2;

import java.util.List;

/**
 * Tactical combat strategy for standard combat operations
 */
@Log4j2
public class TacticalCombatStrategy implements ICombatStrategy {
	
	@Override
	public CombatResult executeAttack(Unit attacker, Unit target) {
		if (!canAttack(attacker, target)) {
			return new CombatResult(false, 0, "Cannot attack target");
		}
		
		// Calculate damage considering cover, armor, and status effects
		int baseDamage = attacker.getAttackDamage();
		int finalDamage = calculateFinalDamage(baseDamage, attacker, target);
		
		// Apply damage
		target.setCurrentHealth(Math.max(0, target.getCurrentHealth() - finalDamage));
		
		// Consume action points via unit API
		attacker.spendActionPoint();
		
		log.info("Attack executed: {} -> {} (Damage: {})",
				attacker.getName(), target.getName(), finalDamage);
		
		return new CombatResult(true, finalDamage, "Attack successful");
	}
	
	@Override
	public boolean canAttack(Unit attacker, Unit target) {
		if (attacker == null || target == null) return false;
		if (attacker.getCurrentHealth() <= 0) return false;
		if (target.getCurrentHealth() <= 0) return false;
		if (attacker.getActionPoints() < 1.0) return false;
		
		// Check range
		double distance = calculateDistance(attacker.getPosition(), target.getPosition());
		return distance <= attacker.getAttackRange();
	}
	
	@Override
	public List<Unit> getValidTargets(Unit attacker) {
		// This would be implemented based on game context
		// For now, return empty list
		return List.of();
	}
	
	@Override
	public boolean canMoveTo(Unit unit, Position target) {
		if (unit == null || target == null) return false;
		if (unit.getCurrentHealth() <= 0) return false;
		if (unit.getActionPoints() < 0.5) return false;
		
		double distance = calculateDistance(unit.getPosition(), target);
		return distance <= unit.getMovementRange();
	}
	
	@Override
	public boolean moveUnit(Unit unit, Position target) {
		if (!canMoveTo(unit, target)) {
			return false;
		}
		
		unit.setPosition(target);
		unit.spendActionPoints(0.5);
		
		log.info("Unit {} moved to position {}", unit.getName(), target);
		return true;
	}
	
	@Override
	public List<Position> getValidMovePositions(Unit unit) {
		// This would calculate valid positions based on terrain and obstacles
		// For now, return empty list
		return List.of();
	}
	
	@Override
	public String getStrategyType() {
		return "TACTICAL";
	}
	
	private int calculateFinalDamage(int baseDamage, Unit attacker, Unit target) {
		int damage = baseDamage;
		
		// Apply critical hit
		if (Math.random() * 100 < attacker.getCriticalChance()) {
			damage = (int) (damage * attacker.getCriticalDamageMultiplier());
		}
		
		// Apply armor reduction
		if (target.getArmor() != null) {
			damage = Math.max(1, damage - target.getArmor().getDamageReduction());
		}
		
		return damage;
	}
	
	private double calculateDistance(Position pos1, Position pos2) {
		if (pos1 == null || pos2 == null) return Double.MAX_VALUE;
		
		int dx = pos1.getX() - pos2.getX();
		int dy = pos1.getY() - pos2.getY();
		int dh = pos1.getHeight() - pos2.getHeight();
		
		return Math.sqrt(dx * dx + dy * dy + dh * dh);
	}
}
