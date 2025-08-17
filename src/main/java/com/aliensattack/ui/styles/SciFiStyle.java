package com.aliensattack.ui.styles;

import java.awt.*;
import javax.swing.*;

/**
 * Professional Sci-Fi style implementation with excellent readability and futuristic aesthetics
 * Designed for optimal user experience with high contrast and modern visual effects
 */
public class SciFiStyle extends AbstractStyle {
    
    // Professional color palette optimized for readability
    private final Color NEON_CYAN = new Color(0, 255, 255);
    private final Color DEEP_SPACE = new Color(15, 15, 25);
    private final Color COSMIC_BLUE = new Color(25, 35, 55);
    private final Color ELECTRIC_PURPLE = new Color(138, 43, 226);
    private final Color PLASMA_ORANGE = new Color(255, 69, 0);
    private final Color QUANTUM_WHITE = new Color(248, 248, 255);
    private final Color VOID_BLACK = new Color(8, 8, 12);
    private final Color ACCENT_BLUE = new Color(30, 144, 255);
    
    @Override
    public String getStyleId() {
        return "scifi";
    }
    
    @Override
    public String getDisplayName() {
        return "Professional Sci-Fi";
    }
    
    @Override
    protected void initializeDefaultColors() {
        primaryColor = NEON_CYAN;
        secondaryColor = COSMIC_BLUE;
        accentColor = ELECTRIC_PURPLE;
        backgroundColor = DEEP_SPACE;
        textColor = QUANTUM_WHITE;
    }
    
    @Override
    protected void initializeDefaultFonts() {
        // Use professional fonts with fallbacks
        try {
            primaryFont = new Font("Segoe UI", Font.BOLD, 14);
        } catch (Exception e) {
            primaryFont = new Font("Arial", Font.BOLD, 14);
        }
        
        try {
            secondaryFont = new Font("Consolas", Font.PLAIN, 12);
        } catch (Exception e) {
            secondaryFont = new Font("Monospaced", Font.PLAIN, 12);
        }
    }
    
    @Override
    public void applyToButton(JButton button) {
        super.applyToButton(button);
        
        // Professional button styling with excellent contrast
        button.setBackground(COSMIC_BLUE);
        button.setForeground(QUANTUM_WHITE);
        button.setFont(primaryFont);
        button.setFocusPainted(false);
        
        // Create professional border with subtle glow effect
        button.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(ACCENT_BLUE, 1), // Subtle outer accent
                BorderFactory.createLineBorder(NEON_CYAN, 2)    // Main border
            ),
            BorderFactory.createEmptyBorder(8, 16, 8, 16)
        ));
        
        // Enhanced hover effects with smooth transitions
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(ACCENT_BLUE);
                button.setForeground(VOID_BLACK);
                button.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(NEON_CYAN, 2), // Enhanced glow
                        BorderFactory.createLineBorder(PLASMA_ORANGE, 2) // Accent border
                    ),
                    BorderFactory.createEmptyBorder(8, 16, 8, 16)
                ));
            }
            
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(COSMIC_BLUE);
                button.setForeground(QUANTUM_WHITE);
                button.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(ACCENT_BLUE, 1),
                        BorderFactory.createLineBorder(NEON_CYAN, 2)
                    ),
                    BorderFactory.createEmptyBorder(8, 16, 8, 16)
                ));
            }
        });
    }
    
    @Override
    public void applyToPanel(JPanel panel) {
        super.applyToPanel(panel);
        
        // Professional panel styling with subtle borders
        panel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(ACCENT_BLUE, 1), // Subtle outer accent
                BorderFactory.createLineBorder(NEON_CYAN, 1)    // Main border
            ),
            BorderFactory.createEmptyBorder(6, 6, 6, 6)
        ));
        
        // Set panel background with professional feel
        panel.setBackground(COSMIC_BLUE);
        panel.setOpaque(true);
    }
    
    @Override
    public void applyToLabel(JLabel label) {
        super.applyToLabel(label);
        label.setForeground(QUANTUM_WHITE);
        label.setFont(primaryFont);
        label.setBackground(COSMIC_BLUE);
        label.setOpaque(true);
        
        // Add subtle border for labels
        label.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(NEON_CYAN, 1),
            BorderFactory.createEmptyBorder(2, 4, 2, 4)
        ));
    }
    
    @Override
    public void applyToTextArea(JTextArea textArea) {
        super.applyToTextArea(textArea);
        textArea.setForeground(QUANTUM_WHITE);
        textArea.setBackground(VOID_BLACK);
        textArea.setCaretColor(NEON_CYAN);
        textArea.setFont(secondaryFont);
        textArea.setSelectionColor(ACCENT_BLUE);
        textArea.setSelectedTextColor(QUANTUM_WHITE);
        
        // Professional border styling
        textArea.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(ACCENT_BLUE, 1),
                BorderFactory.createLineBorder(NEON_CYAN, 1)
            ),
            BorderFactory.createEmptyBorder(6, 6, 6, 6)
        ));
    }
    
    @Override
    public void applyToTextField(JTextField textField) {
        super.applyToTextField(textField);
        textField.setForeground(QUANTUM_WHITE);
        textField.setBackground(VOID_BLACK);
        textField.setCaretColor(NEON_CYAN);
        textField.setFont(secondaryFont);
        textField.setSelectionColor(ACCENT_BLUE);
        textField.setSelectedTextColor(QUANTUM_WHITE);
        
        // Professional border styling
        textField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(ACCENT_BLUE, 1),
                BorderFactory.createLineBorder(NEON_CYAN, 1)
            ),
            BorderFactory.createEmptyBorder(4, 6, 4, 6)
        ));
    }
    
    @Override
    public void applyToComboBox(JComboBox<?> comboBox) {
        super.applyToComboBox(comboBox);
        comboBox.setForeground(QUANTUM_WHITE);
        comboBox.setBackground(VOID_BLACK);
        comboBox.setFont(secondaryFont);
        
        // Professional border styling
        comboBox.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(ACCENT_BLUE, 1),
                BorderFactory.createLineBorder(NEON_CYAN, 1)
            ),
            BorderFactory.createEmptyBorder(3, 6, 3, 6)
        ));
    }
    
    @Override
    public void applyToScrollPane(JScrollPane scrollPane) {
        super.applyToScrollPane(scrollPane);
        
        // Professional border styling
        scrollPane.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(ACCENT_BLUE, 1),
                BorderFactory.createLineBorder(NEON_CYAN, 1)
            ),
            BorderFactory.createEmptyBorder(2, 2, 2, 2)
        ));
        
        // Style the viewport
        scrollPane.getViewport().setBackground(VOID_BLACK);
        
        // Style scrollbars professionally
        try {
            scrollPane.getVerticalScrollBar().setBackground(COSMIC_BLUE);
            scrollPane.getHorizontalScrollBar().setBackground(COSMIC_BLUE);
        } catch (Exception e) {
            // Ignore if scrollbars are not accessible
        }
    }
    
    @Override
    public void applyToWindow(JFrame window) {
        super.applyToWindow(window);
        
        // Professional window styling
        window.getRootPane().setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(ACCENT_BLUE, 2),
            BorderFactory.createLineBorder(NEON_CYAN, 1)
        ));
        
        // Set window background
        window.getContentPane().setBackground(DEEP_SPACE);
    }
}
