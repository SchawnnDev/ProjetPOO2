package fr.poo.graphics.components;

import fr.poo.data.terrain.Terrain;
import fr.poo.data.terrain.objects.Player;

import javax.swing.*;
import java.awt.*;

import static fr.poo.graphics.MainFrame.pixelHeight;
import static fr.poo.graphics.MainFrame.pixelWidth;

public class TerrainComponent extends JComponent {

    private Terrain terrain;

    public TerrainComponent(Terrain terrain) {
        this.terrain = terrain;
        this.setBackground(Color.DARK_GRAY);
        this.setPreferredSize(new Dimension(terrain.getWidth() * pixelWidth, terrain.getHeight() * pixelHeight));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        for (int y = 0; y < terrain.getHeight(); y++) {
            for (int x = 0; x < terrain.getWidth(); x++) {
                if (terrain.isEmpty(x, y)) continue;

                if (terrain.getObject(x, y) instanceof Player)
                    g.setColor(Color.GREEN);
                else
                    g.setColor(Color.BLACK);

                g.fillRect(x * pixelWidth, y * pixelHeight, pixelWidth, pixelHeight);
                g.setColor(Color.WHITE);
                g.drawRect(x * pixelWidth, y * pixelHeight, pixelWidth, pixelHeight);
            }
        }

        drawGrid(g, Color.GRAY);

    }

    @Override
    public void repaint() {
        super.repaint();
    }

    private void drawGrid(Graphics g, Color color) {
        g.setColor(color);
        final int x2 = terrain.getWidth() * pixelWidth;
        final int y2 = terrain.getHeight() * pixelHeight;

        for (int y = 0; y <= terrain.getHeight(); y++) {
            for (int x = 0; x <= terrain.getWidth(); x++) {
                g.drawLine(x * pixelWidth, 0, x * pixelWidth, y2);
                g.drawLine(0, y * pixelHeight, x2, y * pixelHeight);
            }
        }
    }

    public Terrain getTerrain() {
        return terrain;
    }
}
