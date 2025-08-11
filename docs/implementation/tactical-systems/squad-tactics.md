# Squad Tactics System Implementation

## Overview

The Squad Tactics System provides comprehensive squad-level coordination and bonding mechanics for XCOM 2 tactical combat. This system includes squad bonding, advanced tactics, formations, maneuvers, and cohesion bonuses that enhance squad performance and create deeper tactical gameplay.

## Core Components

### 1. SquadCohesionManager

The central manager class that handles all squad-related systems:

- **Squad Registration**: Register squads with members and leaders
- **Cohesion Levels**: 6 levels of squad coordination (None, Basic, Trained, Veteran, Elite, Legendary)
- **Cohesion Bonuses**: 15 types of bonuses (Accuracy, Damage, Defense, Movement, etc.)
- **Bond Management**: Automatic bond creation between squad members
- **Tactic Integration**: Integration with squad tactics and formations

### 2. SquadTacticsFactory

Factory class providing easy creation of tactical components:

- **10 Squad Tactics**: Coordinated Assault, Defensive Formation, Stealth Operation, etc.
- **4 Squad Formations**: Defensive, Offensive, Flanking, Support
- **3 Squad Maneuvers**: Coordinated Attack, Covering Fire, Flanking Maneuver
- **Random Generation**: Methods for creating random tactical components

### 3. AdvancedSquadBonding

Deep bonding system between squad members:

- **6 Bond Levels**: None, Acquaintance, Friend, Close Friend, Partner, Soulmate
- **6 Bond Types**: Combat, Support, Leadership, Specialist, Veteran, Mentor
- **10 Bond Abilities**: Covering Fire, Combat Synergy, Support Healing, etc.
- **Experience System**: Bond experience accumulation and level progression

### 4. AdvancedSquadTacticsSystem

Advanced tactical coordination system:

- **Squad Formations**: 8 formation types with position bonuses
- **Squad Maneuvers**: 8 maneuver types with coordination bonuses
- **Squad Communication**: 6 communication types for tactical coordination
- **Squad Tactics**: 8 tactic types with squad-wide bonuses

## Squad Tactics (10 Types)

### 1. COORDINATED_ASSAULT
- **Effect**: Enhanced coordinated attacks with squad bonuses
- **Duration**: 4 turns
- **Squad Size**: 3+ members
- **Bonuses**: Accuracy +15, Damage +10, Defense +5, Movement +1
- **Special**: Provides Squad Sight and Overwatch Ambush

### 2. DEFENSIVE_FORMATION
- **Effect**: Defensive positioning with high defense bonuses
- **Duration**: 5 turns
- **Squad Size**: 2+ members
- **Bonuses**: Accuracy +10, Damage +5, Defense +20, Overwatch +15
- **Special**: High suppression radius and support range

### 3. STEALTH_OPERATION
- **Effect**: Stealth-focused tactics with concealment
- **Duration**: 3 turns
- **Squad Size**: 2+ members
- **Bonuses**: Accuracy +20, Damage +15, Critical +10, Dodge +10
- **Special**: Provides Concealment and Squad Sight

### 4. PSIONIC_SYNERGY
- **Effect**: Psionic coordination with psi bonuses
- **Duration**: 4 turns
- **Squad Size**: 2+ members
- **Bonuses**: Cohesion +25, Psi +30, Hack +20
- **Special**: Enhanced psionic and hacking abilities

### 5. RAPID_RESPONSE
- **Effect**: Quick response tactics with movement bonuses
- **Duration**: 3 turns
- **Squad Size**: 3+ members
- **Bonuses**: Movement +3, Overwatch +20, Critical +10
- **Special**: Provides Overwatch Ambush

### 6. TECHNICAL_SUPPORT
- **Effect**: Technical support with hacking bonuses
- **Duration**: 4 turns
- **Squad Size**: 2+ members
- **Bonuses**: Defense +15, Hack +40, Psi +10
- **Special**: Enhanced technical abilities

### 7. OVERWATCH_NETWORK
- **Effect**: Coordinated overwatch positions
- **Duration**: 5 turns
- **Squad Size**: 4+ members
- **Bonuses**: Overwatch +30, Accuracy +15, Defense +10
- **Special**: Provides Overwatch Ambush

### 8. CONCEALED_AMBUSH
- **Effect**: Stealth ambush with high damage
- **Duration**: 3 turns
- **Squad Size**: 3+ members
- **Bonuses**: Accuracy +25, Damage +20, Critical +15
- **Special**: Provides Concealment, Squad Sight, and Overwatch Ambush

### 9. CHAIN_REACTION
- **Effect**: Chain reaction tactics with explosive bonuses
- **Duration**: 4 turns
- **Squad Size**: 3+ members
- **Bonuses**: Damage +15, Critical +10, Chain Reaction +25%
- **Special**: Enhanced chain reaction chances

### 10. HEALING_CIRCLE
- **Effect**: Coordinated healing and support
- **Duration**: 4 turns
- **Squad Size**: 3+ members
- **Bonuses**: Cohesion +20, Defense +15, Healing Radius +4
- **Special**: Enhanced healing and support abilities

## Squad Formations (4 Types)

### 1. DEFENSIVE_FORMATION
- **Effect**: Defensive positioning with cover bonuses
- **Bonuses**: Defense +15, Accuracy +5, Overwatch +10
- **Positions**: Front, Back, Flank
- **Cost**: 2 action points

### 2. OFFENSIVE_FORMATION
- **Effect**: Offensive positioning with attack bonuses
- **Bonuses**: Damage +15, Accuracy +10, Critical +5
- **Positions**: Assault, Support, Flank
- **Cost**: 3 action points

### 3. FLANKING_FORMATION
- **Effect**: Flanking positioning for side attacks
- **Bonuses**: Critical +20, Damage +10, Movement +2
- **Positions**: Left Flank, Right Flank, Center
- **Cost**: 3 action points

### 4. SUPPORT_FORMATION
- **Effect**: Support positioning for healing/buffing
- **Bonuses**: Healing +20, Support Range +3, Defense +10
- **Positions**: Medic, Support, Guard
- **Cost**: 2 action points

## Squad Maneuvers (3 Types)

### 1. COORDINATED_ATTACK
- **Effect**: Multiple units attack same target
- **Bonuses**: Damage +20, Accuracy +15, Critical +10
- **Units**: Attacker1, Attacker2, Support
- **Cost**: 3 action points
- **Cooldown**: 2 turns

### 2. COVERING_FIRE
- **Effect**: One unit covers another's movement
- **Bonuses**: Overwatch +25, Defense +10, Movement +1
- **Units**: Cover, Mover
- **Cost**: 2 action points
- **Cooldown**: 1 turn

### 3. FLANKING_MANEUVER
- **Effect**: Coordinated flanking attack
- **Bonuses**: Critical +30, Damage +15, Accuracy +10
- **Units**: Flanker1, Flanker2, Distraction
- **Cost**: 3 action points
- **Cooldown**: 3 turns

## Bond Abilities (10 Types)

### 1. COVERING_FIRE
- **Effect**: Cover fire for bonded partner
- **Unlock**: Acquaintance level
- **Cooldown**: 2 turns

### 2. COMBAT_SYNERGY
- **Effect**: Enhanced combat when together
- **Unlock**: Friend level
- **Cooldown**: 3 turns

### 3. SUPPORT_HEALING
- **Effect**: Healing abilities for partner
- **Unlock**: Close Friend level
- **Cooldown**: 4 turns

### 4. TACTICAL_AWARENESS
- **Effect**: Shared tactical information
- **Unlock**: Friend level
- **Cooldown**: 1 turn

### 5. COORDINATED_ATTACK
- **Effect**: Coordinated attack bonuses
- **Unlock**: Partner level
- **Cooldown**: 3 turns

### 6. PROTECTIVE_INSTINCT
- **Effect**: Protective behavior
- **Unlock**: Close Friend level
- **Cooldown**: 2 turns

### 7. SHARED_VISION
- **Effect**: Shared line of sight
- **Unlock**: Partner level
- **Cooldown**: 1 turn

### 8. COMBAT_REFLEXES
- **Effect**: Enhanced reflexes when partner is in danger
- **Unlock**: Soulmate level
- **Cooldown**: 2 turns

### 9. MENTAL_SYNC
- **Effect**: Psionic synchronization
- **Unlock**: Soulmate level
- **Cooldown**: 4 turns

### 10. SACRIFICIAL_PROTECTION
- **Effect**: Take damage for partner
- **Unlock**: Soulmate level
- **Cooldown**: 5 turns

## Cohesion Levels (6 Levels)

### 1. NONE
- **Experience**: 0-99
- **Bonuses**: None

### 2. BASIC
- **Experience**: 100-249
- **Bonuses**: Accuracy +5, Defense +3

### 3. TRAINED
- **Experience**: 250-499
- **Bonuses**: Accuracy +10, Defense +5, Movement +1

### 4. VETERAN
- **Experience**: 500-749
- **Bonuses**: Accuracy +15, Defense +8, Movement +2, Overwatch +10

### 5. ELITE
- **Experience**: 750-999
- **Bonuses**: Accuracy +20, Defense +12, Movement +3, Overwatch +15, Critical +10

### 6. LEGENDARY
- **Experience**: 1000+
- **Bonuses**: Accuracy +25, Defense +15, Movement +4, Overwatch +20, Critical +15, Squad Sight +2

## Integration with Combat System

### Unit Integration
- **SquadCohesionManager.getCohesionBonus()** - Get cohesion bonuses for units
- **SquadCohesionManager.getTotalCohesionBonus()** - Get total cohesion bonus
- **AdvancedSquadBonding.applyBondEffects()** - Apply bond effects to units

### Combat Manager Integration
- **ComprehensiveXCOM2CombatManager.activateSquadTactic()** - Activate squad tactics
- **ComprehensiveXCOM2CombatManager.getSquadCohesionBonus()** - Get cohesion bonuses
- **ComprehensiveXCOM2CombatManager.processSquadTactics()** - Process tactic durations

### Status Effect Integration
- **Bond-based status effects** for enhanced abilities
- **Tactic-based bonuses** applied through status system
- **Cohesion-based modifiers** for squad performance

## Factory Pattern Implementation

### SquadTacticsFactory Methods

#### Tactic Creation
```java
createCoordinatedAssaultTactic()
createDefensiveFormationTactic()
createStealthOperationTactic()
createPsionicSynergyTactic()
createRapidResponseTactic()
createTechnicalSupportTactic()
createOverwatchNetworkTactic()
createConcealedAmbushTactic()
createChainReactionTactic()
createHealingCircleTactic()
```

#### Formation Creation
```java
createDefensiveFormation()
createOffensiveFormation()
createFlankingFormation()
createSupportFormation()
```

#### Maneuver Creation
```java
createCoordinatedAttackManeuver()
createCoveringFireManeuver()
createFlankingManeuver()
```

## Testing and Verification

### SquadTacticsTest Class
- **Squad Cohesion Manager Tests**: Registration, experience, bonuses
- **Squad Tactics Factory Tests**: Tactic, formation, maneuver creation
- **Advanced Squad Bonding Tests**: Bond creation, experience, abilities
- **Advanced Squad Tactics System Tests**: Formations, coordination, synergy
- **Integration Tests**: Complete system integration

### Test Coverage
- **Unit Registration**: Squad member registration and management
- **Experience System**: Cohesion experience accumulation and leveling
- **Bonus Calculation**: Cohesion, bond, and tactic bonus calculations
- **Tactic Activation**: Squad tactic activation and deactivation
- **Bond Management**: Bond creation, experience, and ability unlocking
- **Formation Management**: Formation setting and bonus application
- **Maneuver Execution**: Maneuver execution and cooldown management

## Performance Considerations

### Memory Management
- **Efficient bond storage** with HashMap for quick lookups
- **Tactic caching** to avoid repeated calculations
- **Bonus aggregation** to minimize redundant calculations

### Processing Optimization
- **Batch processing** of squad systems
- **Lazy evaluation** of cohesion bonuses
- **Cached calculations** for frequently accessed data

### Scalability
- **Modular design** allows easy addition of new tactics
- **Factory pattern** simplifies component creation
- **Manager pattern** centralizes squad operations

## Future Enhancements

### Planned Features
- **Dynamic Tactics**: Tactics that evolve based on squad performance
- **Squad Specializations**: Specialized squad types with unique bonuses
- **Advanced Bonding**: More complex bonding mechanics and abilities
- **Tactical AI**: AI-driven squad coordination and tactic selection
- **Mission-Specific Tactics**: Tactics tailored to specific mission types

### Integration Opportunities
- **Psionic System**: Enhanced psionic coordination through bonds
- **Environmental System**: Tactics that interact with environmental hazards
- **Hacking System**: Technical coordination through squad tactics
- **Mission System**: Mission-specific squad requirements and bonuses

## Conclusion

The Squad Tactics System provides a comprehensive framework for squad-level coordination and bonding in XCOM 2 tactical combat. With 10 squad tactics, 4 formations, 3 maneuvers, 10 bond abilities, and 6 cohesion levels, the system offers deep tactical gameplay while maintaining clean architecture and optimal performance.

The system successfully integrates with existing combat mechanics while providing extensible architecture for future enhancements. The factory pattern simplifies component creation, while the manager pattern centralizes squad operations for optimal performance and maintainability.
