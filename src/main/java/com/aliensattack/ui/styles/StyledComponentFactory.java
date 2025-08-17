package com.aliensattack.ui.styles;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.swing.*;
import java.awt.*;

/**
 * Factory class for creating pre-styled UI components
 * Ensures consistent styling across the application
 */
public class StyledComponentFactory {
    
    private static final Logger log = LogManager.getLogger(StyledComponentFactory.class);
    
    private final StyleManager styleManager;
    
    public StyledComponentFactory() {
        this.styleManager = StyleManager.getInstance();
    }
    
    /**
     * Create a styled button
     * @param text button text
     * @return styled button
     */
    public JButton createButton(String text) {
        JButton button = new JButton(text);
        styleManager.applyCurrentStyle(button);
        return button;
    }
    
    /**
     * Create a styled button with action
     * @param text button text
     * @param action action to perform
     * @return styled button
     */
    public JButton createButton(String text, Action action) {
        JButton button = new JButton(action);
        button.setText(text);
        styleManager.applyCurrentStyle(button);
        return button;
    }
    
    /**
     * Create a styled label
     * @param text label text
     * @return styled label
     */
    public JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        styleManager.applyCurrentStyle(label);
        return label;
    }
    
    /**
     * Create a styled label with icon
     * @param text label text
     * @param icon label icon
     * @return styled label
     */
    public JLabel createLabel(String text, Icon icon) {
        JLabel label = new JLabel(text, icon, SwingConstants.LEFT);
        styleManager.applyCurrentStyle(label);
        return label;
    }
    
    /**
     * Create a styled text area
     * @param text initial text
     * @param rows number of rows
     * @param cols number of columns
     * @return styled text area
     */
    public JTextArea createTextArea(String text, int rows, int cols) {
        JTextArea textArea = new JTextArea(text, rows, cols);
        styleManager.applyCurrentStyle(textArea);
        return textArea;
    }
    
    /**
     * Create a styled text field
     * @param text initial text
     * @param cols number of columns
     * @return styled text field
     */
    public JTextField createTextField(String text, int cols) {
        JTextField textField = new JTextField(text, cols);
        styleManager.applyCurrentStyle(textField);
        return textField;
    }
    
    /**
     * Create a styled panel
     * @param layout layout manager
     * @return styled panel
     */
    public JPanel createPanel(LayoutManager layout) {
        JPanel panel = new JPanel(layout);
        styleManager.applyCurrentStyle(panel);
        return panel;
    }
    
    /**
     * Create a styled panel with default layout
     * @return styled panel
     */
    public JPanel createPanel() {
        return createPanel(new FlowLayout());
    }
    
    /**
     * Create a styled combo box
     * @param items combo box items
     * @return styled combo box
     */
    public JComboBox<String> createComboBox(String... items) {
        JComboBox<String> comboBox = new JComboBox<>(items);
        styleManager.applyCurrentStyle(comboBox);
        return comboBox;
    }
    
    /**
     * Create a styled scroll pane
     * @param view view component
     * @return styled scroll pane
     */
    public JScrollPane createScrollPane(Component view) {
        JScrollPane scrollPane = new JScrollPane(view);
        if (styleManager.getCurrentStyle() instanceof AbstractStyle) {
            ((AbstractStyle) styleManager.getCurrentStyle()).applyToScrollPane(scrollPane);
        }
        return scrollPane;
    }
    
    /**
     * Create a styled tabbed pane
     * @return styled tabbed pane
     */
    public JTabbedPane createTabbedPane() {
        JTabbedPane tabbedPane = new JTabbedPane();
        if (styleManager.getCurrentStyle() instanceof AbstractStyle) {
            ((AbstractStyle) styleManager.getCurrentStyle()).applyToTabbedPane(tabbedPane);
        }
        return tabbedPane;
    }
    
    /**
     * Create a styled table
     * @param data table data
     * @param columnNames column names
     * @return styled table
     */
    public JTable createTable(Object[][] data, Object[] columnNames) {
        JTable table = new JTable(data, columnNames);
        if (styleManager.getCurrentStyle() instanceof AbstractStyle) {
            ((AbstractStyle) styleManager.getCurrentStyle()).applyToTable(table);
        }
        return table;
    }
    
    /**
     * Create a styled frame
     * @param title frame title
     * @return styled frame
     */
    public JFrame createFrame(String title) {
        JFrame frame = new JFrame(title);
        styleManager.applyCurrentStyle(frame);
        return frame;
    }
    
    /**
     * Create a styled dialog
     * @param owner owner frame
     * @param title dialog title
     * @param modal modal flag
     * @return styled dialog
     */
    public JDialog createDialog(Frame owner, String title, boolean modal) {
        JDialog dialog = new JDialog(owner, title, modal);
        dialog.getContentPane().setBackground(styleManager.getCurrentStyle().getBackgroundColor());
        dialog.getContentPane().setForeground(styleManager.getCurrentStyle().getTextColor());
        return dialog;
    }
    
    /**
     * Create a styled menu bar
     * @return styled menu bar
     */
    public JMenuBar createMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        menuBar.setBackground(styleManager.getCurrentStyle().getPrimaryColor());
        menuBar.setForeground(styleManager.getCurrentStyle().getTextColor());
        return menuBar;
    }
    
    /**
     * Create a styled menu
     * @param text menu text
     * @return styled menu
     */
    public JMenu createMenu(String text) {
        JMenu menu = new JMenu(text);
        menu.setBackground(styleManager.getCurrentStyle().getPrimaryColor());
        menu.setForeground(styleManager.getCurrentStyle().getTextColor());
        menu.setFont(styleManager.getCurrentStyle().getPrimaryFont());
        return menu;
    }
}
