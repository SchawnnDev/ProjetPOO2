package fr.poo.graphics.components;

import fr.poo.graphics.MainFrame;

import javax.swing.*;
import java.awt.*;

public class ToolBoxPanel extends JPanel {

    private JButton startBtn;

    private JSpinner pixelSizeSpinner;

    public ToolBoxPanel(MainFrame instance) {
        this.setLayout(new FlowLayout());

        // instances
        startBtn = new JButton("Click");
        startBtn.addActionListener(e -> {
            System.out.println("Recharging");
            instance.getTerrainComponent().getTerrain().clear();
            instance.getTerrainComponent().getTerrain().fillTerrainWithItems(50);
        });

        pixelSizeSpinner = new JSpinner(new SpinnerNumberModel(MainFrame.pixelHeight, 1, 30, 1));
        pixelSizeSpinner.addChangeListener(e -> {
            MainFrame.pixelHeight = (Integer) pixelSizeSpinner.getValue();
            MainFrame.pixelWidth = (Integer) pixelSizeSpinner.getValue();
            SwingUtilities.invokeLater(() -> instance.getTerrainComponent().repaint());
        });

        this.add(startBtn);
        this.add(pixelSizeSpinner);
    }

}
