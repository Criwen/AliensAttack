package com.aliensattack.ui.panels;

import com.aliensattack.core.model.Position;
import com.aliensattack.core.model.Unit;
import com.aliensattack.core.enums.UnitType;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.function.BiConsumer;

public class TacticalMapView {
    private final JPanel panel;
    private BiConsumer<Integer, Integer> onTileClick;
    private final int width;
    private final int height;

    public TacticalMapView(JPanel panel, int width, int height) {
        this.panel = panel;
        this.width = width;
        this.height = height;
    }

    public void setOnTileClick(BiConsumer<Integer, Integer> onTileClick) {
        this.onTileClick = onTileClick;
    }

    public void render(List<Position> highlightedPositions, boolean isHighlightingMovePositions,
                       boolean isWaitingForGrenadeTarget, Position grenadePreviewCenter, int grenadePreviewRadius,
                       List<Unit> units) {
        panel.removeAll();
        panel.setLayout(new GridLayout(height, width, 2, 2));
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                JButton button = createTileButton(x, y, highlightedPositions, isHighlightingMovePositions,
                        isWaitingForGrenadeTarget, grenadePreviewCenter, grenadePreviewRadius, units);
                panel.add(button);
            }
        }
        panel.revalidate();
        panel.repaint();
    }

    private JButton createTileButton(int x, int y, List<Position> highlightedPositions, boolean isHighlightingMovePositions,
                                     boolean isWaitingForGrenadeTarget, Position grenadePreviewCenter, int grenadePreviewRadius,
                                     List<Unit> units) {
        JButton button = new JButton();
        button.setPreferredSize(new Dimension(50, 50));
        button.setFont(new Font("Arial", Font.BOLD, 10));

        Unit unitAtPosition = units.stream()
                .filter(u -> u.getPosition() != null && u.getPosition().getX() == x && u.getPosition().getY() == y)
                .findFirst().orElse(null);

        if (unitAtPosition != null) {
            if (unitAtPosition.getUnitType() == UnitType.SOLDIER) {
                button.setBackground(Color.BLUE);
                button.setText(unitAtPosition.getName().substring(0, 1));
            } else {
                button.setBackground(Color.RED);
                button.setText("A");
            }
            button.setForeground(Color.WHITE);
        } else {
            Position currentPos = new Position(x, y);
            if (isHighlightingMovePositions && highlightedPositions.contains(currentPos)) {
                button.setBackground(Color.GREEN);
                button.setText("M");
                button.setForeground(Color.WHITE);
            } else if (isWaitingForGrenadeTarget && highlightedPositions.contains(currentPos)) {
                button.setBackground(new Color(255, 100, 100));
                button.setText("");
                button.setForeground(Color.WHITE);
                button.setBorder(BorderFactory.createLineBorder(Color.RED, 2));
            } else if (isWaitingForGrenadeTarget && grenadePreviewCenter != null) {
                int dx = Math.abs(grenadePreviewCenter.getX() - x);
                int dy = Math.abs(grenadePreviewCenter.getY() - y);
                int manhattan = dx + dy;
                if (manhattan <= grenadePreviewRadius) {
                    button.setBackground(new Color(255, 150, 150));
                    button.setText("A");
                    button.setForeground(Color.WHITE);
                } else {
                    button.setBackground(Color.LIGHT_GRAY);
                    button.setText("");
                }
            } else {
                button.setBackground(Color.LIGHT_GRAY);
                button.setText("");
            }
        }

        button.addActionListener(e -> { if (onTileClick != null) onTileClick.accept(x, y); });

        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                // Rendering of preview is controlled by caller
            }
        });

        return button;
    }
}


