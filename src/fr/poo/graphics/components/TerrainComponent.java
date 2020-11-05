package fr.poo.graphics.components;

import fr.poo.Main;
import fr.poo.data.terrain.Terrain;
import fr.poo.data.terrain.objects.AlternativePathItem;
import fr.poo.data.terrain.objects.PathItem;
import fr.poo.data.terrain.objects.Player;
import fr.poo.data.terrain.objects.TerrainObject;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

import static fr.poo.Main.getPixelSize;

public class TerrainComponent extends JComponent {

    private Terrain terrain;

    public TerrainComponent(Terrain terrain) {
        this.terrain = terrain;
        this.setBackground(Color.DARK_GRAY);
        this.setBorder(new EmptyBorder(10,10,10,10));
        this.setPreferredSize(new Dimension(terrain.getWidth() * getPixelSize(), terrain.getHeight() * getPixelSize()));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        for (int y = 0; y < terrain.getHeight(); y++) {
            for (int x = 0; x < terrain.getWidth(); x++) {
                if (terrain.isEmpty(x, y)) continue;

                TerrainObject object = terrain.getObject(x, y);

                if (object instanceof Player) {
                    g.setColor(Color.GREEN);
                } else if (object instanceof PathItem) {
                    g.setColor(Color.RED);
                } else if(object instanceof AlternativePathItem) {
                    g.setColor(Color.orange);
                } else {
                    g.setColor(Color.BLACK);
                }

                g.fillRect(x * getPixelSize(), y * getPixelSize(), getPixelSize(), getPixelSize());
                g.setColor(Color.WHITE);
                g.drawRect(x * getPixelSize(), y * getPixelSize(), getPixelSize(), getPixelSize());
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
        final int x2 = terrain.getWidth() * getPixelSize();
        final int y2 = terrain.getHeight() * getPixelSize();

        for (int y = 0; y <= terrain.getHeight(); y++) {
            for (int x = 0; x <= terrain.getWidth(); x++) {
                g.drawLine(x * getPixelSize(), 0, x * getPixelSize(), y2);
                g.drawLine(0, y * getPixelSize(), x2, y * getPixelSize());
            }
        }
    }

    public Terrain getTerrain() {
        return terrain;
    }
}
