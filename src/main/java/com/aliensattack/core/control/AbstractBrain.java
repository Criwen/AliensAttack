package com.aliensattack.core.control;

import com.aliensattack.core.interfaces.IBrain;
import com.aliensattack.core.interfaces.IUnit;
import com.aliensattack.actions.interfaces.IAction;
import com.aliensattack.core.model.GameContext;
import lombok.Data;
import lombok.extern.log4j.Log4j2;

import java.util.List;
import java.util.Optional;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Abstract base class for brain implementations
 * Provides common functionality and state management
 */
@Data
@Log4j2
public abstract class AbstractBrain implements IBrain {
    
    // Brain identification
    private final String brainId;
    private final BrainType brainType;
    private final int priority;
    
    // Control state
    private IUnit controlledUnit;
    private BrainState currentState;
    private boolean isActive;
    
    // Performance tracking
    private final BrainMetricsImpl metrics;
    private long lastDecisionTime;
    private long lastActionTime;
    
    // Action management
    private List<IAction> availableActions;
    private Optional<IAction> selectedAction;
    
    protected AbstractBrain(String brainId, BrainType brainType, int priority) {
        this.brainId = brainId;
        this.brainType = brainType;
        this.priority = priority;
        this.currentState = BrainState.IDLE;
        this.isActive = false;
        this.metrics = new BrainMetricsImpl();
        this.availableActions = new ArrayList<>();
        this.selectedAction = Optional.empty();
    }
    
    @Override
    public boolean canControl(IUnit unit) {
        if (unit == null) {
            return false;
        }
        
        // Check if unit is alive and can be controlled
        if (!unit.isAlive()) {
            log.debug("Brain {} cannot control dead unit {}", brainId, unit.getId());
            return false;
        }
        
        // Check if unit has action points
        if (!unit.canTakeActions()) {
            log.debug("Brain {} cannot control unit {} without action points", brainId, unit.getId());
            return false;
        }
        
        return true;
    }
    
    @Override
    public void takeControl(IUnit unit) {
        if (!canControl(unit)) {
            log.warn("Brain {} cannot take control of unit {}", brainId, unit.getId());
            return;
        }
        
        if (isControlling()) {
            releaseControl();
        }
        
        this.controlledUnit = unit;
        this.isActive = true;
        this.currentState = BrainState.THINKING;
        
        log.info("Brain {} took control of unit {}", brainId, unit.getId());
        
        // Notify the unit that it's being controlled
        onTakeControl(unit);
    }
    
    @Override
    public void releaseControl() {
        if (controlledUnit != null) {
            log.info("Brain {} released control of unit {}", brainId, controlledUnit.getId());
            onReleaseControl(controlledUnit);
        }
        
        this.controlledUnit = null;
        this.isActive = false;
        this.currentState = BrainState.IDLE;
        this.selectedAction = Optional.empty();
        this.availableActions.clear();
    }
    
    @Override
    public boolean isControlling() {
        return controlledUnit != null && isActive;
    }
    
    @Override
    public List<IAction> getAvailableActions() {
        if (!isControlling()) {
            return new ArrayList<>();
        }
        
        if (availableActions.isEmpty()) {
            availableActions = calculateAvailableActions();
        }
        
        return new ArrayList<>(availableActions);
    }
    
    @Override
    public Optional<IAction> selectAction(GameContext context) {
        if (!isControlling() || !isReady()) {
            return Optional.empty();
        }
        
        long startTime = System.currentTimeMillis();
        this.currentState = BrainState.DECIDING;
        
        try {
            // Let subclasses implement their decision logic
            Optional<IAction> action = decideAction(context);
            
            if (action.isPresent()) {
                this.selectedAction = action;
                this.currentState = BrainState.THINKING;
                metrics.recordDecision(System.currentTimeMillis() - startTime);
                log.debug("Brain {} selected action: {}", brainId, action.get().getActionType());
            }
            
            return action;
        } catch (Exception e) {
            log.error("Brain {} error during action selection", brainId, e);
            this.currentState = BrainState.ERROR;
            metrics.recordError();
            return Optional.empty();
        }
    }
    
    @Override
    public boolean executeAction(IAction action) {
        if (!isControlling() || action == null) {
            return false;
        }
        
        if (!availableActions.contains(action)) {
            log.warn("Brain {} cannot execute unavailable action: {}", brainId, action.getActionType());
            return false;
        }
        
        long startTime = System.currentTimeMillis();
        this.currentState = BrainState.EXECUTING;
        
        try {
            action.execute();
            
            // Check if action was successful after execution
            boolean success = action.isSuccessful();
            
            if (success) {
                metrics.recordAction(System.currentTimeMillis() - startTime);
                log.info("Brain {} successfully executed action: {}", brainId, action.getActionType());
                
                // Update available actions after execution
                availableActions.remove(action);
                selectedAction = Optional.empty();
                
                // Notify subclasses of successful execution
                onActionExecuted(action, true);
            } else {
                log.warn("Brain {} failed to execute action: {}", brainId, action.getActionType());
                onActionExecuted(action, false);
            }
            
            this.currentState = BrainState.THINKING;
            return success;
            
        } catch (Exception e) {
            log.error("Brain {} error during action execution", brainId, e);
            this.currentState = BrainState.ERROR;
            metrics.recordError();
            onActionExecuted(action, false);
            return false;
        }
    }
    
    @Override
    public String getBrainId() {
        return brainId;
    }
    
    @Override
    public boolean isReady() {
        return isControlling() && 
               currentState != BrainState.ERROR && 
               controlledUnit.canTakeActions();
    }
    
    @Override
    public void update(GameContext context) {
        if (!isControlling()) {
            return;
        }
        
        // Update available actions
        this.availableActions = calculateAvailableActions();
        
        // Let subclasses implement their update logic
        onUpdate(context);
    }
    
    @Override
    public void resetForNewTurn() {
        this.currentState = BrainState.THINKING;
        this.selectedAction = Optional.empty();
        this.availableActions.clear();
        
        if (controlledUnit != null) {
            this.availableActions = calculateAvailableActions();
        }
        
        onNewTurn();
    }
    
    @Override
    public BrainMetrics getMetrics() {
        return metrics;
    }
    
    // Abstract methods that subclasses must implement
    protected abstract Optional<IAction> decideAction(GameContext context);
    protected abstract List<IAction> calculateAvailableActions();
    protected abstract void onUpdate(GameContext context);
    protected abstract void onNewTurn();
    
    // Optional hook methods that subclasses can override
    protected void onTakeControl(IUnit unit) {
        // Default implementation does nothing
    }
    
    protected void onReleaseControl(IUnit unit) {
        // Default implementation does nothing
    }
    
    protected void onActionExecuted(IAction action, boolean success) {
        // Default implementation does nothing
    }
    
    // Helper methods
    protected boolean hasActionPoints(double requiredPoints) {
        return controlledUnit != null && controlledUnit.getActionPoints() >= requiredPoints;
    }
    
    protected boolean isUnitInRange(IUnit target, int range) {
        if (controlledUnit == null || target == null) {
            return false;
        }
        
        // Simple distance calculation - can be enhanced with tactical field
        int dx = Math.abs(controlledUnit.getPosition().getX() - target.getPosition().getX());
        int dy = Math.abs(controlledUnit.getPosition().getY() - target.getPosition().getY());
        return Math.max(dx, dy) <= range;
    }
    
    // Brain metrics implementation
    private static class BrainMetricsImpl implements BrainMetrics {
        private final AtomicInteger decisionsMade = new AtomicInteger(0);
        private final AtomicInteger actionsExecuted = new AtomicInteger(0);
        private final AtomicInteger errors = new AtomicInteger(0);
        private long totalDecisionTime = 0;
        private long totalActionTime = 0;
        
        public void recordDecision(long decisionTime) {
            decisionsMade.incrementAndGet();
            totalDecisionTime += decisionTime;
        }
        
        public void recordAction(long actionTime) {
            actionsExecuted.incrementAndGet();
            totalActionTime += actionTime;
        }
        
        public void recordError() {
            errors.incrementAndGet();
        }
        
        @Override
        public int getDecisionsMade() {
            return decisionsMade.get();
        }
        
        @Override
        public int getActionsExecuted() {
            return actionsExecuted.get();
        }
        
        @Override
        public double getAverageDecisionTime() {
            int decisions = decisionsMade.get();
            return decisions > 0 ? (double) totalDecisionTime / decisions : 0.0;
        }
        
        @Override
        public double getSuccessRate() {
            int total = decisionsMade.get();
            int errors = this.errors.get();
            return total > 0 ? (double) (total - errors) / total : 0.0;
        }
        
        @Override
        public int getErrors() {
            return errors.get();
        }
    }
}
