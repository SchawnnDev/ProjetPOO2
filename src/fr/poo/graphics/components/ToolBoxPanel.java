package fr.poo.graphics.components;

import fr.poo.data.terrain.Terrain;
import fr.poo.data.terrain.objects.TerrainObjectData;
import fr.poo.exceptions.NotEnoughPlayersException;
import fr.poo.exceptions.ObjectOutTerrainException;
import fr.poo.exceptions.PathNotFoundException;
import fr.poo.exceptions.UiException;
import fr.poo.graphics.MainFrame;
import fr.poo.graphs.astar.AStarAlgorithm;
import fr.poo.threads.ThreadManager;

import javax.swing.*;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class ToolBoxPanel extends JPanel {
    private MainFrame instance;
    private JButton startBtn;
    private JButton generateBtn;
    private JButton clearBtn;

    public ToolBoxPanel(MainFrame instance) {
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.instance = instance;

        // instances
        startBtn = new JButton("Lancer");
        startBtn.addActionListener(e -> {

            ThreadManager.getService().submit(() -> {
                try {
                    getTerrain().executeAlgorithm(new AStarAlgorithm(getTerrain()));
                } catch (NotEnoughPlayersException | ObjectOutTerrainException | PathNotFoundException ex) {
                    ex.printStackTrace();
                }
            });
        });

        // ...
        generateBtn = new JButton("Générer");
        generateBtn.addActionListener(e -> ThreadManager.getService().submit(() -> {
            try {
                List<Future<TerrainObjectData>> futures = getTerrain().generateRandomItems(300);
                for (Future<TerrainObjectData> future : futures) {
                    TerrainObjectData data = future.get();
                    if (data == null)
                        continue;

                    try {
                        getTerrain().addTerrainObject(data);
                    } catch (ObjectOutTerrainException ex) {
                    }

                }
            } catch (InterruptedException | ExecutionException ex) {
                new UiException(ex, instance);
            }
        }));

        clearBtn = new JButton("Clear");
        clearBtn.addActionListener(e -> ThreadManager.getService().submit(() -> {
            getTerrain().clear();
            JOptionPane.showMessageDialog(instance, "La grille a bien été clear.");
        }));



        this.add(generateBtn);
        this.add(startBtn);
        this.add(clearBtn);
    }

    private Terrain getTerrain() {
        return instance.getTerrainComponent().getTerrain();
    }

}
