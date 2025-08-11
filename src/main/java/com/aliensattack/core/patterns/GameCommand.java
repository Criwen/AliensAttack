package com.aliensattack.core.patterns;

import com.aliensattack.core.model.Unit;
import com.aliensattack.core.model.Position;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Command pattern for game actions
 */
public abstract class GameCommand {
    protected static final Logger log = LogManager.getLogger(GameCommand.class);
    protected Unit executor;
    protected boolean isExecuted;
    
    public GameCommand(Unit executor) {
        this.executor = executor;
        this.isExecuted = false;
    }
    
    public abstract boolean canExecute();
    public abstract void execute();
    public abstract void undo();
    
    public boolean isExecuted() {
        return isExecuted;
    }
    
    public Unit getExecutor() {
        return executor;
    }
}

/**
 * Move command implementation
 */
class MoveCommand extends GameCommand {
    private Position fromPosition;
    private Position toPosition;
    
    public MoveCommand(Unit executor, Position toPosition) {
        super(executor);
        this.fromPosition = executor.getPosition();
        this.toPosition = toPosition;
    }
    
    @Override
    public boolean canExecute() {
        return executor != null && executor.canMove() && 
               executor.getActionPoints() >= 1 && toPosition != null;
    }
    
    @Override
    public void execute() {
        if (canExecute()) {
            log.debug("Executing move command for {} from {} to {}", 
                     executor.getName(), fromPosition, toPosition);
            executor.setPosition(toPosition);
            executor.spendActionPoint();
            isExecuted = true;
            log.info("{} moved to {}", executor.getName(), toPosition);
        } else {
            log.warn("Cannot execute move command for {}", executor.getName());
        }
    }
    
    @Override
    public void undo() {
        if (isExecuted) {
            log.debug("Undoing move command for {}", executor.getName());
            executor.setPosition(fromPosition);
            // Note: Unit doesn't have addActionPoint method, would need to be implemented
            isExecuted = false;
        }
    }
}

/**
 * Attack command implementation
 */
class AttackCommand extends GameCommand {
    private Unit target;
    
    public AttackCommand(Unit executor, Unit target) {
        super(executor);
        this.target = target;
    }
    
    @Override
    public boolean canExecute() {
        return executor != null && target != null && 
               executor.canPerformAttack() && executor.getActionPoints() >= 1;
    }
    
    @Override
    public void execute() {
        if (canExecute()) {
            log.debug("Executing attack command: {} attacks {}", 
                     executor.getName(), target.getName());
            // Attack logic would be implemented here
            executor.spendActionPoint();
            isExecuted = true;
            log.info("{} attacked {}", executor.getName(), target.getName());
        } else {
            log.warn("Cannot execute attack command");
        }
    }
    
    @Override
    public void undo() {
        if (isExecuted) {
            log.debug("Undoing attack command for {}", executor.getName());
            // Note: Unit doesn't have addActionPoint method, would need to be implemented
            isExecuted = false;
        }
    }
}
