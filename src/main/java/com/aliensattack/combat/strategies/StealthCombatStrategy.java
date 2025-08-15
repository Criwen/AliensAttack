package com.aliensattack.combat.strategies;

import com.aliensattack.combat.CombatResult;
import com.aliensattack.core.model.Position;
import com.aliensattack.core.model.Unit;

import java.util.List;

/**
 * Stealth combat strategy: placeholder built on top of tactical defaults
 */
public class StealthCombatStrategy extends TacticalCombatStrategy {
	@Override
	public String getStrategyType() {
		return "STEALTH";
	}
	
	@Override
	public CombatResult executeAttack(Unit attacker, Unit target) {
		return super.executeAttack(attacker, target);
	}
	
	@Override
	public boolean canAttack(Unit attacker, Unit target) {
		return super.canAttack(attacker, target);
	}
	
	@Override
	public List<Unit> getValidTargets(Unit attacker) {
		return super.getValidTargets(attacker);
	}
	
	@Override
	public boolean canMoveTo(Unit unit, Position target) {
		return super.canMoveTo(unit, target);
	}
	
	@Override
	public boolean moveUnit(Unit unit, Position target) {
		return super.moveUnit(unit, target);
	}
}
