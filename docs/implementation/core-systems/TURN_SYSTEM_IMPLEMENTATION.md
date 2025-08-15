# Turn System Implementation

## Overview

The Turn System in AliensAttack manages the flow of gameplay between player and enemy turns, automatically activating and deactivating AI brains based on whose turn it is.

## Key Components

### 1. TurnManager
- **Purpose**: Central controller for turn-based gameplay
- **Responsibilities**: 
  - Switch between player and enemy turns
  - Activate/deactivate enemy AI brains
  - Track turn statistics and timing
  - Manage turn limits and forced endings

### 2. BrainManager Integration
- **AI Brain Activation**: Automatically assigns AI brains to enemy units during enemy turns
- **AI Brain Deactivation**: Releases AI control when switching back to player turn
- **Priority Management**: Executes AI brains in priority order during enemy turns

### 3. Turn Flow

#### Player Turn
```
🎯 Player Turn Starts
├── Human brains are active
├── Player controls units manually
├── Actions are recorded
└── Player ends turn manually
```

#### Enemy Turn
```
👾 Enemy Turn Starts
├── AI brains are automatically activated
├── AI brains take control of enemy units
├── AI makes tactical decisions
├── AI executes actions automatically
└── Enemy turn ends when all actions complete
```

## Implementation Details

### Turn State Management
```java
public enum TurnPhase {
    PLAYER_TURN,    // Player's turn
    ENEMY_TURN,     // Enemy AI turn
    INTERRUPT,      // Interrupt phase (reactions, overwatch)
    TRANSITION      // Transition between turns
}
```

### AI Brain Activation
```java
private void activateEnemyAI() {
    List<IBrain> enemyBrains = brainManager.getBrainsByType(IBrain.BrainType.AI);
    
    for (IBrain brain : enemyBrains) {
        // Find available enemy units for this brain
        var availableUnit = findAvailableEnemyUnit(brain);
        if (availableUnit.isPresent()) {
            boolean assigned = brainManager.assignBrainToUnit(
                brain.getBrainId(), availableUnit.get().getId());
            if (assigned) {
                log.info("🎯 Activated enemy AI brain {} for unit {}", 
                    brain.getBrainId(), availableUnit.get().getId());
            }
        }
    }
}
```

### Enemy Turn Execution
```java
public void executeEnemyTurn() {
    // Get all active enemy AI brains
    List<IBrain> enemyBrains = getActiveEnemyBrains();
    
    // Execute each enemy brain in priority order
    for (IBrain brain : enemyBrains) {
        if (!brain.isReady()) continue;
        
        try {
            // Update brain with current game context
            brain.update(gameContext);
            
            // Select and execute action
            var action = brain.selectAction(gameContext);
            if (action.isPresent()) {
                boolean success = brain.executeAction(action.get());
                if (success) {
                    enemyActionsThisTurn++;
                    recordUnitAction(brain.getControlledUnit().getId());
                }
            }
        } catch (Exception e) {
            log.error("Error executing enemy brain {}", brain.getBrainId(), e);
        }
    }
    
    // Check if enemy turn should end
    if (shouldEndEnemyTurn()) {
        endEnemyTurn();
    }
}
```

## AI Decision Making During Enemy Turns

### 1. Tactical Analysis
- **Environment Assessment**: AI analyzes cover, hazards, and terrain
- **Threat Evaluation**: Identifies player unit positions and vulnerabilities
- **Resource Management**: Considers action points, health, and ammunition

### 2. Strategy Selection
- **Aggressive**: Focus on attacking and advancing
- **Defensive**: Focus on positioning and cover
- **Survival**: Focus on staying alive when health is low
- **Exploration**: Focus on discovering new areas
- **Conservative**: Focus on resource conservation

### 3. Action Execution
- **Priority-Based**: Actions are selected based on current strategy
- **Context-Aware**: Decisions consider current game state
- **Learning**: AI adapts based on previous success/failure

## Turn Timing and Limits

### Configurable Time Limits
```java
// Set turn time limit (default: 30 seconds)
turnManager.setTurnTimeLimit(45000); // 45 seconds

// Enable/disable time limits
turnManager.setTurnTimeLimitEnabled(true);
```

### Automatic Turn Ending
- **Time Exceeded**: Force end turn when time limit reached
- **Actions Complete**: End turn when all units have no action points
- **No Actions Possible**: End turn when no valid actions available

## Usage Example

### Basic Turn Flow
```java
// Initialize systems
GameContext gameContext = GameContext.createDefault();
BrainManager brainManager = new BrainManager();
TurnManager turnManager = new TurnManager(brainManager, gameContext);

// Start game
turnManager.startNewTurn();

// Player performs actions...
turnManager.recordPlayerAction("SOLDIER_1");

// End player turn - automatically starts enemy turn
turnManager.endPlayerTurn();

// Execute enemy AI turn
turnManager.executeEnemyTurn();

// End enemy turn - automatically starts player turn
turnManager.endEnemyTurn();

// Start next turn
turnManager.startNewTurn();
```

### Advanced Configuration
```java
// Create specialized AI brains
AIBrain tacticalEnemy = BrainFactory.createTacticalAIBrain();
AIBrain aggressiveEnemy = BrainFactory.createAggressiveAIBrain();
AIBrain defensiveEnemy = BrainFactory.createDefensiveAIBrain();

// Register with brain manager
brainManager.registerBrain(tacticalEnemy);
brainManager.registerBrain(aggressiveEnemy);
brainManager.registerBrain(defensiveEnemy);

// AI brains will automatically activate during enemy turns
```

## Benefits

### 1. Automatic Management
- **No Manual Switching**: Turns automatically switch between player and enemy
- **AI Brain Lifecycle**: AI brains are automatically activated/deactivated
- **Seamless Transitions**: Smooth handoff between turn phases

### 2. Tactical AI
- **Intelligent Decisions**: AI makes context-aware tactical choices
- **Strategy Adaptation**: AI adapts strategy based on situation
- **Coordinated Actions**: Multiple AI brains can coordinate actions

### 3. Performance Tracking
- **Turn Statistics**: Track actions per turn and performance metrics
- **Timing Control**: Manage turn duration and prevent stalling
- **Debug Information**: Comprehensive logging for troubleshooting

## Future Enhancements

### 1. Advanced AI Coordination
- **Squad Tactics**: AI brains coordinate for flanking maneuvers
- **Support Actions**: AI provides covering fire and support
- **Retreat Logic**: AI decides when to fall back

### 2. Dynamic Difficulty
- **Adaptive AI**: AI difficulty adjusts based on player performance
- **Learning Systems**: AI learns from player tactics
- **Personality Traits**: Different AI personalities for variety

### 3. Interrupt System
- **Overwatch Reactions**: Units can react during enemy turns
- **Opportunity Attacks**: Special actions when conditions are met
- **Chain Reactions**: Actions trigger additional responses

## Conclusion

The Turn System provides a robust foundation for turn-based tactical combat, automatically managing the complex interactions between player and enemy AI brains. The system ensures smooth gameplay flow while providing intelligent AI opponents that make tactical decisions based on the current game state.
