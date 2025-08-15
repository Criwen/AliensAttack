package com.aliensattack.ui.panels;

import com.aliensattack.actions.ActionManager;
import com.aliensattack.actions.ActionType;
import com.aliensattack.core.model.Unit;

import javax.swing.*;
import java.awt.*;
import java.util.function.Consumer;

public class ActionPanelView {
    private final JPanel panel;
    private final ActionManager actionManager; // reserved for future enabling/disabling logic
    private Consumer<ActionType> onActionSelected;

    public ActionPanelView(JPanel panel, ActionManager actionManager) {
        this.panel = panel;
        this.actionManager = actionManager;
    }

    public void setOnActionSelected(Consumer<ActionType> onActionSelected) {
        this.onActionSelected = onActionSelected;
    }

    public void render(Unit selectedUnit) {
        panel.removeAll();
        panel.setLayout(new GridLayout(0, 2, 5, 5));
        for (ActionType type : ActionType.values()) {
            JButton button = new JButton(type.getDisplayName());
            button.addActionListener(e -> {
                if (onActionSelected != null) {
                    onActionSelected.accept(type);
                }
            });
            button.setEnabled(selectedUnit != null && selectedUnit.getActionPoints() >= type.getActionPointCost());
            panel.add(button);
        }
        panel.revalidate();
        panel.repaint();
    }
}


