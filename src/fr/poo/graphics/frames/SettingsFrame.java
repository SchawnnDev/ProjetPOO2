package fr.poo.graphics.frames;

import fr.poo.Main;
import fr.poo.data.Position;
import fr.poo.data.terrain.Terrain;
import fr.poo.data.terrain.objects.Player;
import fr.poo.exceptions.ObjectOutTerrainException;
import fr.poo.exceptions.UiException;
import fr.poo.graphics.MainFrame;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class SettingsFrame extends JFrame {
    private JSpinner pixelSizeSpinner;
    private JSpinner terrainWidthSpinner;
    private JSpinner terrainHeightSpinner;

    private JButton saveButton;
    private JButton cancelButton;

    public SettingsFrame(boolean firstStart) {
        JPanel spinnerPanel = new JPanel();
        JPanel buttonsPanel = new JPanel();

        spinnerPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        spinnerPanel.setLayout(new GridLayout(3, 2));

        JLabel pixelSizeLabel = new JLabel("Pixel size");
        JLabel terrainWidthLabel = new JLabel("Terrain width (pixels)");
        JLabel terrainHeightLabel = new JLabel("Terrain height (pixels)");

        // setting up spinners
        pixelSizeSpinner = new JSpinner(new SpinnerNumberModel(Main.getPixelSize(), 1, 50, 1));
        terrainWidthSpinner = new JSpinner(new SpinnerNumberModel(Main.getTerrainWidth(), 10, 2000, 1));
        terrainHeightSpinner = new JSpinner(new SpinnerNumberModel(Main.getTerrainHeight(), 10, 1000, 1));

        // setting up buttons
        saveButton = new JButton(firstStart ? "Lancer" : "Sauvegarder");
        saveButton.addActionListener(e -> {

            try {
                Main.setPixelSize((Integer) pixelSizeSpinner.getValue());
                Main.setTerrainWidth((Integer) terrainWidthSpinner.getValue());
                Main.setTerrainHeight((Integer) terrainHeightSpinner.getValue());
                setVisible(false);

                if(firstStart)
                {
                    SwingUtilities.invokeLater(() -> open());
                } else {
                    JOptionPane.showMessageDialog(this, String.format("Les modifs ont bien été enregistrées (%d) (%d) (%d)", Main.getPixelSize(), Main.getTerrainWidth(), Main.getTerrainHeight()));
                }

            } catch (Exception ex)
            {
                new UiException(ex, this);
            }

        });

        cancelButton = new JButton("Annuler");
        cancelButton.addActionListener(e -> setVisible(false));

        buttonsPanel.add(saveButton);
        buttonsPanel.add(cancelButton);

        // Spinner panel
        spinnerPanel.add(pixelSizeLabel);
        spinnerPanel.add(pixelSizeSpinner);

        spinnerPanel.add(terrainWidthLabel);
        spinnerPanel.add(terrainWidthSpinner);

        spinnerPanel.add(terrainHeightLabel);
        spinnerPanel.add(terrainHeightSpinner);

        setTitle("Settings");
        setLayout(new BorderLayout());

        add(spinnerPanel, BorderLayout.CENTER);
        add(buttonsPanel, BorderLayout.SOUTH);

        setDefaultCloseOperation( firstStart ? EXIT_ON_CLOSE : DISPOSE_ON_CLOSE);
        setSize(300, 200);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void open()
    {
        Terrain terrain = new Terrain(300, 150);

        Player romeo = new Player("Roméo", new Position(5, terrain.getHeight() / 2));
        Player juliette = new Player("Juliette", new Position(terrain.getWidth() - 5, terrain.getHeight() / 2));

        try {
            terrain.addPlayer(romeo);
            terrain.addPlayer(juliette);
        } catch (ObjectOutTerrainException e) {
            e.printStackTrace();
        }

        MainFrame frame = new MainFrame(terrain);
        frame.open();
    }

}
