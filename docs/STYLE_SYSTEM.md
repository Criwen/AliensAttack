# AliensAttack Style System

## Overview

The AliensAttack Style System provides a comprehensive, configurable styling solution for all UI components in the game. It follows the Factory pattern and SOLID principles, making it easy to create consistent, themed interfaces while maintaining clean, maintainable code.

## Architecture

### Core Components

1. **IStyle Interface** - Defines the contract for all styles
2. **AbstractStyle** - Base class providing common implementations
3. **StyleManager** - Singleton manager for style switching and caching
4. **StyledComponentFactory** - Factory for creating pre-styled components
5. **StyleConfiguration** - Configuration loader from properties files

### Design Patterns Used

- **Factory Pattern** - For creating styled components
- **Singleton Pattern** - For StyleManager
- **Strategy Pattern** - For different style implementations
- **Template Method Pattern** - In AbstractStyle

## Available Styles

### Sci-Fi Style
- **Theme**: Futuristic, alien invasion
- **Colors**: Cyan, Dark Blue-Gray, Magenta
- **Fonts**: Orbitron, Share Tech Mono
- **Best For**: Main game interface, futuristic feel

## Usage Examples

### Basic Style Application

```java
// Get the style manager
StyleManager styleManager = StyleManager.getInstance();

// Apply current style to a component
JButton button = new JButton("Click me");
styleManager.applyCurrentStyle(button);

// Switch to a different style
styleManager.switchStyle("scifi");

// Apply style to multiple components
styleManager.applyCurrentStyleToAll(panel1, panel2, button1);
```

### Using the Component Factory

```java
// Create a styled component factory
StyledComponentFactory factory = new StyledComponentFactory();

// Create pre-styled components
JButton button = factory.createButton("Action");
JLabel label = factory.createLabel("Information");
JPanel panel = factory.createPanel();
JTextArea textArea = factory.createTextArea("Content", 5, 30);

// Create styled containers
JScrollPane scrollPane = factory.createScrollPane(textArea);
JTabbedPane tabbedPane = factory.createTabbedPane();
```

### Custom Style Implementation

```java
public class CustomStyle extends AbstractStyle {
    
    @Override
    public String getStyleId() {
        return "custom";
    }
    
    @Override
    public String getDisplayName() {
        return "Custom Theme";
    }
    
    @Override
    protected void initializeDefaultColors() {
        primaryColor = new Color(255, 0, 0);      // Red
        secondaryColor = new Color(255, 255, 255); // White
        accentColor = new Color(0, 0, 255);        // Blue
        backgroundColor = new Color(0, 0, 0);      // Black
        textColor = new Color(255, 255, 255);      // White
    }
    
    @Override
    protected void initializeDefaultFonts() {
        primaryFont = new Font("Arial", Font.BOLD, 16);
        secondaryFont = new Font("Courier", Font.PLAIN, 12);
    }
}

// Register the custom style
StyleManager.getInstance().registerStyle(new CustomStyle());
```

## Configuration

### Properties File Structure

The style system reads configuration from `ui.properties`:

```properties
# Active style
ui.style.active=default

# Style performance settings
ui.style.cache.enabled=true
ui.style.cache.size=500

# Custom colors (hex format)
ui.style.custom.primary.color=#4682B4
ui.style.custom.secondary.color=#F0F8FF
ui.style.custom.accent.color=#FF8C00

# Font settings
ui.style.font.primary=Segoe UI
ui.style.font.size.primary=14
```

### Dynamic Configuration

```java
// Load configuration
StyleConfiguration config = StyleConfiguration.getInstance();

// Get configuration values
String activeStyle = config.getActiveStyle();
boolean cachingEnabled = config.isStyleCachingEnabled();
Color customColor = config.getColor("ui.style.custom.primary.color", Color.BLUE);

// Reload configuration
config.reload();
```

## Integration with Existing Code

### Updating GameWindow

To integrate the style system with the existing GameWindow:

```java
public class GameWindow extends JFrame {
    private final StyleManager styleManager;
    private final StyledComponentFactory componentFactory;
    
    public GameWindow() {
        this.styleManager = StyleManager.getInstance();
        this.componentFactory = new StyledComponentFactory();
        
        initializeWindow();
        initializeComponents();
        setupLayout();
        setupEventHandlers();
        initializeGame();
        
        // Apply current style
        styleManager.applyCurrentStyle(this);
    }
    
    private void initializeComponents() {
        // Use factory for styled components
        gameLog = componentFactory.createTextArea("", 10, 40);
        tacticalMapPanel = componentFactory.createPanel();
        unitInfoPanel = componentFactory.createPanel();
        actionPanel = componentFactory.createPanel();
        
        // Apply styles to existing components
        styleManager.applyCurrentStyleToAll(
            tacticalMapPanel, unitInfoPanel, actionPanel
        );
    }
}
```

### Style Switching in Game

```java
// Add style switching to game menu
private void createStyleMenu() {
    JMenu styleMenu = componentFactory.createMenu("Styles");
    
    for (IStyle style : styleManager.getAvailableStyles()) {
        JMenuItem item = new JMenuItem(style.getDisplayName());
        item.addActionListener(e -> {
            styleManager.switchStyle(style.getStyleId());
            refreshGameInterface();
        });
        styleMenu.add(item);
    }
    
    return styleMenu;
}

private void refreshGameInterface() {
    // Reapply styles to all components
    styleManager.applyCurrentStyleToAll(
        this, tacticalMapPanel, unitInfoPanel, actionPanel,
        gameLog, shootingPanel, missionPreparationPanel
    );
    
    // Refresh the display
    revalidate();
    repaint();
}
```

## Performance Considerations

### Caching

- Styles are cached by the StyleManager
- Component styling is applied efficiently
- Batch operations supported for multiple components

### Memory Management

- Styles are lightweight and reusable
- No unnecessary object creation
- Efficient color and font management

### Best Practices

```java
// Good: Apply styles once during initialization
public void initializeComponents() {
    button = new JButton("Click");
    styleManager.applyCurrentStyle(button);
}

// Better: Use factory for pre-styled components
public void initializeComponents() {
    button = componentFactory.createButton("Click");
}

// Avoid: Applying styles repeatedly
public void updateButton() {
    // Don't do this - styles are already applied
    styleManager.applyCurrentStyle(button);
}
```

## Testing

### Unit Tests

```java
@Test
public void testStyleSwitching() {
    StyleManager manager = StyleManager.getInstance();
    
    // Test style switching
    assertTrue(manager.switchStyle("scifi"));
    assertEquals("scifi", manager.getCurrentStyle().getStyleId());
    
    // Test invalid style
    assertFalse(manager.switchStyle("invalid"));
}

@Test
public void testComponentStyling() {
    StyledComponentFactory factory = new StyledComponentFactory();
    JButton button = factory.createButton("Test");
    
    // Verify button has been styled
    assertNotNull(button.getBackground());
    assertNotNull(button.getForeground());
    assertNotNull(button.getFont());
}
```

### Integration Tests

```java
@Test
public void testStyleIntegration() {
    // Test style integration with actual game components
    StyleManager manager = StyleManager.getInstance();
    StyledComponentFactory factory = new StyledComponentFactory();
    
    // Create and verify styled components
    JFrame frame = factory.createFrame("Test Frame");
    assertNotNull(frame);
    
    // Verify style application
    // ... test implementation
}
```

## Troubleshooting

### Common Issues

1. **Styles not applying**
   - Check if StyleManager is properly initialized
   - Verify component types are supported
   - Ensure styles are registered

2. **Performance issues**
   - Enable style caching
   - Use batch operations for multiple components
   - Avoid repeated style applications

3. **Configuration not loading**
   - Verify properties file location
   - Check file permissions
   - Use StyleConfiguration.reload()

### Debug Mode

```java
// Enable debug logging
System.setProperty("log4j.logger.com.aliensattack.ui.styles", "DEBUG");

// Get style statistics
Map<String, Object> stats = StyleManager.getInstance().getStatistics();
System.out.println("Style stats: " + stats);
```

## Future Enhancements

### Planned Features

1. **CSS-like styling** - More flexible style definitions
2. **Animation support** - Smooth transitions between styles
3. **Theme inheritance** - Base themes with customizations
4. **Dynamic color schemes** - Time-based or event-based changes
5. **Accessibility themes** - High contrast, large text options

### Extension Points

The system is designed for easy extension:

- Implement `IStyle` for new themes
- Extend `AbstractStyle` for common functionality
- Add new component types to `StyledComponentFactory`
- Create custom configuration loaders

## Conclusion

The AliensAttack Style System provides a robust, flexible foundation for UI styling. It follows established design patterns and best practices, making it easy to maintain and extend. By using the provided factories and managers, developers can create consistently styled interfaces with minimal effort while maintaining the ability to customize and extend the system as needed.

For questions or contributions, refer to the main project documentation or contact the development team.
