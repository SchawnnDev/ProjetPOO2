package fr.poo.graphics.components;

import fr.poo.data.terrain.Terrain;
import fr.poo.exceptions.UiException;
import fr.poo.graphics.MainFrame;
import fr.poo.io.FileManager;
import fr.poo.threads.ThreadManager;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;

public class MenuBar extends JMenuBar {

    public MenuBar(MainFrame instance) {

        JMenu fileMenu = new JMenu("File");
        fileMenu.setMnemonic('F');

        JMenuItem loadObstaclesMenuItem = new JMenuItem("Charger les obstacles");
        JMenuItem saveObstaclesMenuItem = new JMenuItem("Sauvegarder les obstacles");

        loadObstaclesMenuItem.addActionListener(e -> ThreadManager.getService().submit(() -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Séléctionner un fichier a charger");
            int returnVal = fileChooser.showOpenDialog(instance);

            if (returnVal == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();
                if (file == null) return;
                //This is where a real application would open the file.
                try {
                    Terrain terrain = FileManager.load(file.getAbsolutePath(), new Terrain());
                    Terrain.copy(terrain, instance.getTerrainComponent().getTerrain());
                } catch (IOException ex) {
                    new UiException(ex, instance);
                    ex.printStackTrace();
                }
            }
        }));

        saveObstaclesMenuItem.addActionListener(e -> ThreadManager.getService().submit(() -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Specify a file to save");

            int userSelection = fileChooser.showSaveDialog(instance);

            if (userSelection == JFileChooser.APPROVE_OPTION) {
                File fileToSave = fileChooser.getSelectedFile();
                try {
                    FileManager.save(fileToSave.getAbsolutePath(), instance.getTerrainComponent().getTerrain());
                } catch (IOException ex) {
                    new UiException(ex, instance);
                    ex.printStackTrace();
                }
                //System.out.println("Save as file: " + fileToSave.getAbsolutePath());
            }
        }));

        JMenuItem exitMenuItem = new JMenuItem("Quitter");

        exitMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, KeyEvent.CTRL_DOWN_MASK));
        exitMenuItem.addActionListener(e -> {
            instance.close();
        });

        fileMenu.add(loadObstaclesMenuItem);
        fileMenu.add(saveObstaclesMenuItem);
        fileMenu.add(new JSeparator());
        fileMenu.add(exitMenuItem);

        this.add(fileMenu);
    }

}
