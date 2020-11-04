package fr.poo.graphics;

import fr.poo.data.terrain.Terrain;
import fr.poo.graphics.components.TerrainComponent;
import fr.poo.graphics.components.ToolBoxPanel;
import fr.poo.threads.TerrainChangedThread;
import fr.poo.threads.ThreadManager;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class MainFrame extends JFrame {

    private final Terrain terrain;
    private JPanel contentPane;
    private TerrainComponent terrainComponent;
    private ToolBoxPanel toolBoxComponent;
    private TerrainChangedThread terrainChangedThread;

    public MainFrame(Terrain terrain) {
        super("Projet POO2");

        this.terrain = terrain;
        this.terrainComponent = new TerrainComponent(terrain);
        this.toolBoxComponent = new ToolBoxPanel(this);
        this.contentPane = new JPanel();

        // init frame
        // this.setSize(terrain.getWidth() * pixelWidth, terrain.getHeight() * pixelHeight);

        this.setDefaultCloseOperation(EXIT_ON_CLOSE);

        //set panel
        this.contentPane.setLayout(new BorderLayout());
        this.contentPane.setBorder(new EmptyBorder(0, 0, 0, 0));
        this.setContentPane(contentPane);
        //this.setResizable(false);
        //this.contentPane.setBounds(0, 0,pixelWidth,pixelHeight);

        contentPane.add(terrainComponent, BorderLayout.EAST);
        contentPane.add(toolBoxComponent, BorderLayout.WEST);

        this.terrainChangedThread = new TerrainChangedThread(terrainComponent);
        ThreadManager.getService().submit(this.terrainChangedThread);

        this.pack();
        this.setLocationRelativeTo(null);

    }

    public void open() {
        setVisible(true);
    }

    public TerrainComponent getTerrainComponent() {
        return terrainComponent;
    }
}
