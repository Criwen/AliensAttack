# AI System Documentation

## Overview
The AliensAttack project features a sophisticated AI system for enemy units that provides intelligent tactical decision-making, learning capabilities, and performance optimization. The system has been consolidated from the previous `AlienAI` and `EnemyAI` implementations into a single, comprehensive `EnemyAI` class.

## System Architecture

### Core Components
- **EnemyAI**: Main AI implementation with behavior trees and decision-making
- **TurnManager**: Manages turn order and phases (Soldier, Enemy, Environment, Turn End)
- **CoverDetectionSystem**: Advanced terrain analysis and cover detection
- **AIBehaviorTree**: Decision tree structure for AI actions

### Key Features
- **Intelligent Decision Making**: Uses behavior trees and decision trees
- **Learning & Adaptation**: AI learns from successes/failures and adapts tactics
- **Performance Optimization**: Decision caching and calculation time limits
- **Advanced Movement**: A* pathfinding with movement validation
- **Tactical Awareness**: Cover detection, flanking, and positioning analysis

## Advanced Terrain Analysis System

### Overview
The Advanced Terrain Analysis System provides sophisticated terrain analysis capabilities that significantly enhance AI tactical decision-making. It goes beyond basic cover detection to include elevation analysis, terrain object generation, hazard detection, and visibility calculations.

### Core Capabilities

#### 1. Elevation Analysis
- **Dynamic Elevation Generation**: Creates varied terrain with hills, valleys, and slopes
- **Elevation-Based Cover**: Higher positions provide tactical advantages
- **Movement Impact**: Elevation affects movement costs and tactical positioning
- **Configurable Parameters**: Elevation scale, frequency, and bounds are configurable

#### 2. Terrain Object Generation
- **Intelligent Placement**: Places terrain objects based on density and type
- **Object Properties**: Each object has health, destructibility, and hazard properties
- **Cover Contribution**: Objects provide different levels of cover based on type
- **Interaction Capabilities**: Some objects can be interacted with or destroyed

#### 3. Advanced Cover Detection
- **Multi-Factor Analysis**: Considers terrain, units, elevation, and environment
- **Elevation Integration**: Cover value is enhanced by elevation advantages
- **Dynamic Updates**: Cover map updates when terrain changes
- **Quality Assessment**: Provides cover quality scores (0.0 to 1.0)

#### 4. Hazard Detection
- **Environmental Hazards**: Detects fire, acid, poison, radiation, etc.
- **Elevation-Based Hazards**: Identifies flooding in low areas, wind in high areas
- **Terrain Object Hazards**: Analyzes hazardous properties of terrain objects
- **Hazard Interaction**: Some hazards provide cover while others are purely dangerous

#### 5. Visibility Analysis
- **Elevation Impact**: Higher positions generally have better visibility
- **Hazard Modifiers**: Smoke, fog, and other hazards reduce visibility
- **Dynamic Calculation**: Visibility changes based on environmental conditions
- **Tactical Importance**: Visibility affects AI positioning decisions

#### 6. Tactical Position Scoring
- **Comprehensive Evaluation**: Combines cover, elevation, visibility, and hazard factors
- **Weighted Scoring**: Different factors have configurable importance weights
- **Position Comparison**: Allows AI to evaluate multiple positions strategically
- **Performance Optimization**: Uses caching to avoid recalculating scores

### Configuration Parameters

The system is highly configurable through the `ai.properties` file:

```properties
# Elevation System
terrain.elevation.scale=10
terrain.elevation.min=-20
terrain.elevation.max=50
terrain.hill.frequency=0.1
terrain.valley.frequency=0.15

# Terrain Object Generation
terrain.object.density=0.3
terrain.object.default.health=100

# Depression and Slope Detection
terrain.depression.detection.range=3
terrain.depression.threshold=0.6
terrain.slope.detection.range=2
terrain.slope.threshold=5

# Cover System Enhancement
cover.unit.detection.range=3
cover.elevation.bonus=0.02
cover.object.bonus=0.1
cover.elevation.weight=0.3

# Visibility System
visibility.elevation.penalty=0.01
visibility.hazard.modifier.smoke=0.5

# Tactical Analysis Weights
tactical.cover.weight=0.4
tactical.elevation.weight=0.3
tactical.visibility.weight=0.2
tactical.hazard.penalty=0.1
```

### Technical Implementation

#### Data Structures
- **Elevation Map**: `Map<Position, Integer>` storing elevation values
- **Terrain Objects Map**: `Map<Position, List<TerrainObject>>` for object placement
- **Visibility Map**: `Map<Position, Double>` for visibility calculations
- **Hazard Map**: `Map<Position, List<String>>` for hazard identification

#### Analysis Algorithms
- **Elevation Generation**: Uses sine/cosine functions with configurable frequencies
- **Depression Detection**: Analyzes surrounding elevation patterns
- **Slope Detection**: Calculates elevation gradients and thresholds
- **Cover Calculation**: Multi-factor analysis with elevation integration

#### Performance Features
- **Analysis Caching**: Caches analysis results to avoid recalculation
- **Configurable Limits**: Limits analysis depth and calculation time
- **Efficient Updates**: Only updates changed positions when possible
- **Memory Management**: Configurable cache sizes and cleanup

### Integration with AI System

The Advanced Terrain Analysis System integrates seamlessly with the EnemyAI:

1. **Cover Detection**: AI uses advanced cover analysis for positioning
2. **Elevation Awareness**: AI considers elevation advantages in movement
3. **Hazard Avoidance**: AI avoids dangerous terrain when possible
4. **Tactical Scoring**: AI uses position scoring for strategic decisions
5. **Performance Monitoring**: System provides performance statistics

### Usage Examples

#### Basic Terrain Analysis
```java
CoverDetectionSystem coverSystem = new CoverDetectionSystem(tacticalField);

// Get elevation at position
int elevation = coverSystem.getElevationAt(position);

// Get cover information
CoverType cover = coverSystem.getCoverAt(position);
double coverValue = coverSystem.getCoverValueAt(position);

// Check for hazards
List<String> hazards = coverSystem.getHazardsAt(position);
```

#### Advanced Tactical Analysis
```java
// Get comprehensive tactical analysis
Map<String, Object> analysis = coverSystem.getTacticalAnalysis(position);
double tacticalScore = (Double) analysis.get("tacticalScore");

// Find best cover positions with elevation consideration
List<Position> bestPositions = coverSystem.getBestCoverPositionsWithElevation(center, range);

// Check elevation advantage
boolean hasAdvantage = coverSystem.hasElevationAdvantage(position, targetPosition);
```

#### Performance Monitoring
```java
// Get system performance statistics
Map<String, Object> stats = coverSystem.getPerformanceStats();
int coverMapSize = (Integer) stats.get("coverMapSize");
long cacheAge = (Long) stats.get("cacheAge");
```

### Benefits

1. **Enhanced AI Intelligence**: AI makes better tactical decisions with comprehensive terrain understanding
2. **Realistic Terrain**: Dynamic elevation and object generation creates varied, interesting battlefields
3. **Strategic Depth**: Multiple factors create complex tactical decisions
4. **Performance**: Efficient algorithms and caching ensure good performance
5. **Configurability**: All parameters can be tuned for different gameplay experiences
6. **Extensibility**: System is designed to easily add new terrain types and analysis methods

## AI Behavior System

### Behavior Trees
The AI uses behavior trees for decision-making, allowing for complex, hierarchical decision processes.

### Decision Making
- **Attack Decisions**: Evaluates targets, cover, and tactical advantages
- **Movement Decisions**: Uses pathfinding to find optimal positions
- **Special Ability Usage**: Considers energy, cooldowns, and tactical value
- **Defensive Actions**: Evaluates threats and chooses appropriate responses

### Learning and Adaptation
- **Success Memory**: Remembers successful tactics and their outcomes
- **Failure Analysis**: Learns from failed attempts and adjusts strategies
- **Pattern Recognition**: Identifies recurring tactical situations
- **Adaptive Behavior**: Gradually improves performance over time

## Turn Management

### Turn Phases
1. **Soldier Phase**: Human players control their units
2. **Enemy Phase**: AI-controlled enemies take their turns
3. **Environment Phase**: Environmental effects and hazards are processed
4. **Turn End**: Status effects, healing, and turn completion

### Unit Initiative
- Units are ordered by initiative value
- Higher initiative units act first
- Aliens receive initiative bonuses

## Performance Optimization

### Decision Caching
- Caches AI decisions to avoid recalculation
- Configurable cache size and duration
- Performance monitoring and statistics

### Calculation Limits
- Maximum calculation time per decision
- Pathfinding iteration limits
- Memory usage optimization

## Configuration

### AI Properties
All AI behavior is configurable through `ai.properties`:
- Difficulty levels
- Learning rates
- Performance limits
- Behavior probabilities

### Integration
The AI system integrates with:
- Combat management
- Turn management
- Terrain analysis
- Unit management

## Testing and Validation

### Test Classes
- `AITest`: Basic AI functionality testing
- `AdvancedTerrainTest`: Advanced terrain analysis testing

### Validation
- AI decision validation
- Performance testing
- Integration testing
- Cover detection accuracy

## Future Enhancements

### Planned Features
- **Squad Coordination**: Multi-unit AI tactics
- **Environmental Effects**: Weather and hazard integration
- **Machine Learning**: Advanced pattern recognition
- **Performance Profiling**: Detailed performance analysis tools

### Advanced Terrain Features
- **Dynamic Terrain**: Terrain that changes during missions
- **Weather Effects**: Rain, snow, and other weather impacts
- **Destructible Environment**: Buildings and terrain that can be destroyed
- **Interactive Objects**: Doors, switches, and other interactive elements

## Conclusion

The Advanced Terrain Analysis System represents a significant enhancement to the AI system, providing sophisticated terrain understanding that enables more intelligent and realistic AI behavior. Combined with the existing AI learning, adaptation, and performance optimization features, it creates a comprehensive and powerful AI system that enhances the tactical depth and enjoyment of the game.

The system is designed to be both powerful and performant, with extensive configuration options that allow for fine-tuning the gameplay experience. Its modular architecture makes it easy to extend with new terrain types, analysis methods, and integration points.
