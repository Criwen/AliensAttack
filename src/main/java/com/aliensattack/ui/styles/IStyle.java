package com.aliensattack.ui.styles;

import javax.swing.*;
import java.awt.*;

/**
 * Base interface for UI styles in AliensAttack
 * Defines the contract for applying styles to UI components
 */
public interface IStyle {
    
    /**
     * Get the unique identifier for this style
     * @return style identifier
     */
    String getStyleId();
    
    /**
     * Get the display name for this style
     * @return human-readable style name
     */
    String getDisplayName();
    
    /**
     * Apply style to a JFrame window
     * @param frame the window to style
     */
    void applyToWindow(JFrame frame);
    
    /**
     * Apply style to a JPanel
     * @param panel the panel to style
     */
    void applyToPanel(JPanel panel);
    
    /**
     * Apply style to a JButton
     * @param button the button to style
     */
    void applyToButton(JButton button);
    
    /**
     * Apply style to a JLabel
     * @param label the label to style
     */
    void applyToLabel(JLabel label);
    
    /**
     * Apply style to a JTextArea
     * @param textArea the text area to style
     */
    void applyToTextArea(JTextArea textArea);
    
    /**
     * Apply style to a JTextField
     * @param textField the text field to style
     */
    void applyToTextField(JTextField textField);
    
    /**
     * Apply style to a JComboBox
     * @param comboBox the combo box to style
     */
    void applyToComboBox(JComboBox<?> comboBox);
    
    /**
     * Get the primary color for this style
     * @return primary color
     */
    Color getPrimaryColor();
    
    /**
     * Get the secondary color for this style
     * @return secondary color
     */
    Color getSecondaryColor();
    
    /**
     * Get the accent color for this style
     * @return accent color
     */
    Color getAccentColor();
    
    /**
     * Get the background color for this style
     * @return background color
     */
    Color getBackgroundColor();
    
    /**
     * Get the text color for this style
     * @return text color
     */
    Color getTextColor();
    
    /**
     * Get the font for this style
     * @return primary font
     */
    Font getPrimaryFont();
    
    /**
     * Get the secondary font for this style
     * @return secondary font
     */
    Font getSecondaryFont();
}

