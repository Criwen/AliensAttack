# Control System Tests

This directory contains tests for the control system components including brain management and turn-based gameplay.

## Test Structure

### Unit Tests
- **TurnSystemTest.java** - Unit tests for the TurnManager class
  - Tests turn switching logic
  - Tests AI brain activation/deactivation
  - Tests turn statistics and timing
  - Tests validation and error handling

### Integration Tests
- **TurnSystemIntegrationTest.java** - Integration tests for the complete turn system
  - Tests interaction between TurnManager, BrainManager, and AI brains
  - Tests complete turn cycles
  - Tests multiple AI brain coordination
  - Tests system consistency across multiple turns

## Running Tests

### Run All Control Tests
```bash
mvn test -Dtest="com.aliensattack.core.control.*"
```

### Run Specific Test Class
```bash
mvn test -Dtest="TurnSystemTest"
mvn test -Dtest="TurnSystemIntegrationTest"
```

### Run Specific Test Method
```bash
mvn test -Dtest="TurnSystemTest#shouldStartWithPlayerTurn"
```

## Test Coverage

The tests cover:
- ✅ Turn state management
- ✅ Player/Enemy turn switching
- ✅ AI brain lifecycle management
- ✅ Turn statistics and metrics
- ✅ Time limit handling
- ✅ Error conditions and validation
- ✅ Multi-turn cycle consistency
- ✅ Brain manager integration

## Test Data

Tests use:
- Mock GameContext with default values
- Test BrainManager instances
- Sample AI brains with different strategies
- Simulated player actions and unit IDs

## Best Practices

1. **Isolation**: Each test method is independent
2. **Setup**: Common setup in @BeforeEach methods
3. **Assertions**: Clear assertions with descriptive messages
4. **Coverage**: Tests cover both happy path and edge cases
5. **Naming**: Test methods use descriptive names with @DisplayName

## Adding New Tests

When adding new tests:
1. Follow the existing naming convention
2. Use @DisplayName for readable test descriptions
3. Test both positive and negative scenarios
4. Ensure proper cleanup in test methods
5. Add tests to appropriate test class (unit vs integration)
