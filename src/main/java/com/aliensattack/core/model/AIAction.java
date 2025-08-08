package com.aliensattack.core.model;

import lombok.Getter;
import lombok.Setter;

/**
 * AI Action - Represents AI decision and action
 * Implements XCOM 2 AI Action System
 */
@Getter
@Setter
public class AIAction {
    
    private String actionType;
    private Unit target;
    private Position position;
    private String ability;
    private int priority;
    private String description;
    private boolean isExecuted;
    
    public AIAction() {
        this.actionType = "MOVE";
        this.priority = 50;
        this.isExecuted = false;
    }
    
    public AIAction(String actionType, Unit target, int priority) {
        this.actionType = actionType;
        this.target = target;
        this.priority = priority;
        this.isExecuted = false;
    }
    
    /**
     * Execute the AI action
     */
    public boolean execute() {
        if (isExecuted) {
            return false;
        }
        
        // Mark as executed
        isExecuted = true;
        
        // Generate description
        generateDescription();
        
        return true;
    }
    
    private void generateDescription() {
        switch (actionType) {
            case "ATTACK":
                description = "AI attacks " + (target != null ? target.getName() : "target");
                break;
            case "FLANK":
                description = "AI flanks " + (target != null ? target.getName() : "target");
                break;
            case "SUPPORT":
                description = "AI supports " + (target != null ? target.getName() : "ally");
                break;
            case "RETREAT":
                description = "AI retreats to " + (position != null ? position.toString() : "safe position");
                break;
            case "USE_ABILITY":
                description = "AI uses " + ability + " on " + (target != null ? target.getName() : "target");
                break;
            case "MOVE":
                description = "AI moves to " + (position != null ? position.toString() : "position");
                break;
            default:
                description = "AI performs " + actionType;
        }
    }
    
    @Override
    public String toString() {
        return String.format("AIAction{type='%s', priority=%d, executed=%s, description='%s'}", 
                actionType, priority, isExecuted, description);
    }
}


