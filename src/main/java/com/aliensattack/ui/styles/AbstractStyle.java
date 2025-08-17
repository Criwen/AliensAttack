package com.aliensattack.ui.styles;

import lombok.Getter;
import lombok.Setter;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

/**
 * Abstract base class for UI styles providing common implementations
 * Reduces code duplication and ensures consistency across style implementations
 */
@Getter
@Setter
public abstract class AbstractStyle implements IStyle {
    
    protected Color primaryColor;
    protected Color secondaryColor;
    protected Color accentColor;
    protected Color backgroundColor;
    protected Color textColor;
    protected Font primaryFont;
    protected Font secondaryFont;
    
    protected AbstractStyle() {
        initializeDefaultColors();
        initializeDefaultFonts();
    }
    
    /**
     * Initialize default colors for the style
     */
    protected abstract void initializeDefaultColors();
    
    /**
     * Initialize default fonts for the style
     */
    protected abstract void initializeDefaultFonts();
    
    @Override
    public void applyToWindow(JFrame frame) {
        frame.getContentPane().setBackground(backgroundColor);
        frame.getContentPane().setForeground(textColor);
        
        // Set window icon if available
        try {
            ImageIcon icon = new ImageIcon(getClass().getResource("/assets/icons/window_icon.png"));
            frame.setIconImage(icon.getImage());
        } catch (Exception e) {
            // Icon not available, continue without it
        }
    }
    
    @Override
    public void applyToPanel(JPanel panel) {
        panel.setBackground(backgroundColor);
        panel.setForeground(textColor);
        panel.setBorder(new EmptyBorder(5, 5, 5, 5));
    }
    
    @Override
    public void applyToButton(JButton button) {
        button.setBackground(primaryColor);
        button.setForeground(textColor);
        button.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createRaisedBevelBorder(),
            BorderFactory.createEmptyBorder(8, 16, 8, 16)
        ));
        button.setFont(primaryFont);
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        // Add hover effect
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(accentColor);
            }
            
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(primaryColor);
            }
        });
    }
    
    @Override
    public void applyToLabel(JLabel label) {
        label.setForeground(textColor);
        label.setFont(primaryFont);
        label.setBackground(backgroundColor);
        label.setOpaque(true);
    }
    
    @Override
    public void applyToTextArea(JTextArea textArea) {
        textArea.setBackground(secondaryColor);
        textArea.setForeground(textColor);
        textArea.setFont(secondaryFont);
        textArea.setCaretColor(accentColor);
        textArea.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLoweredBevelBorder(),
            BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));
    }
    
    @Override
    public void applyToTextField(JTextField textField) {
        textField.setBackground(secondaryColor);
        textField.setForeground(textColor);
        textField.setFont(secondaryFont);
        textField.setCaretColor(accentColor);
        textField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLoweredBevelBorder(),
            BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));
    }
    
    @Override
    public void applyToComboBox(JComboBox<?> comboBox) {
        comboBox.setBackground(secondaryColor);
        comboBox.setForeground(textColor);
        comboBox.setFont(secondaryFont);
        comboBox.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLoweredBevelBorder(),
            BorderFactory.createEmptyBorder(3, 5, 3, 5)
        ));
    }
    
    /**
     * Apply style to a JScrollPane
     * @param scrollPane the scroll pane to style
     */
    public void applyToScrollPane(JScrollPane scrollPane) {
        scrollPane.getViewport().setBackground(backgroundColor);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        
        // Style scroll bars
        scrollPane.getVerticalScrollBar().setBackground(secondaryColor);
        scrollPane.getHorizontalScrollBar().setBackground(secondaryColor);
    }
    
    /**
     * Apply style to a JTabbedPane
     * @param tabbedPane the tabbed pane to style
     */
    public void applyToTabbedPane(JTabbedPane tabbedPane) {
        tabbedPane.setBackground(backgroundColor);
        tabbedPane.setForeground(textColor);
        tabbedPane.setFont(primaryFont);
    }
    
    /**
     * Apply style to a JTable
     * @param table the table to style
     */
    public void applyToTable(JTable table) {
        table.setBackground(secondaryColor);
        table.setForeground(textColor);
        table.setFont(secondaryFont);
        table.setGridColor(primaryColor);
        table.setSelectionBackground(accentColor);
        table.setSelectionForeground(textColor);
    }
}

