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
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class ToolBoxPanel extends JPanel {
    private MainFrame instance;
    private JButton startBtn;
    private JButton generateBtn;
    private JButton clearBtn;

    private boolean loading = false;

    public ToolBoxPanel(MainFrame instance) {
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.setBorder(new EmptyBorder(10, 10, 10, 10));
        this.instance = instance;

        // instances
        startBtn = new JButton("Lancer");
        startBtn.addActionListener(e -> ThreadManager.getService().submit(() -> {
            switchLoading();
            try {
                getTerrain().executeAlgorithm(new AStarAlgorithm(getTerrain()));
            } catch (NotEnoughPlayersException | ObjectOutTerrainException | PathNotFoundException ex) {
                new UiException(ex, this);
                ex.printStackTrace();
            } finally {
                switchLoading();
            }
        }));

        // ...
        generateBtn = new JButton("Générer");
        generateBtn.addActionListener(e -> ThreadManager.getService().submit(() -> {

            switchLoading();

            try {
                List<Future<TerrainObjectData>> futures = getTerrain().generateRandomItems(300);
                for (Future<TerrainObjectData> future : futures) {
                    TerrainObjectData data = future.get(2, TimeUnit.SECONDS);
                    System.out.println("Is done ? : " + future.isDone());
                    /*if (data == null)
                        continue;

                    try {
                        getTerrain().addTerrainObject(data);
                    } catch (ObjectOutTerrainException ex) {
                    }*/

                }
            } catch (InterruptedException | ExecutionException | TimeoutException ex) {
                new UiException(ex, instance);
                ex.printStackTrace();
            } finally {
                switchLoading();
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

    public void switchLoading() {
        this.loading = !this.loading;
        instance.setCursor(this.loading ? Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR) : Cursor.getDefaultCursor());

        for (JButton btn : getButtons())
            btn.setEnabled(!this.loading);
    }

    private JButton[] getButtons() {
        return new JButton[]{
                generateBtn,
                startBtn,
                clearBtn
        };
    }

    public boolean isLoading() {
        return loading;
    }
}
