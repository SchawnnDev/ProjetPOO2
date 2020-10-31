package fr.poo.threads;

import fr.poo.graphics.components.TerrainComponent;

public class TerrainChangedThread implements Runnable {
    private TerrainComponent component;
    private int currentSize;

    public TerrainChangedThread(TerrainComponent component) {
        this.component = component;
        this.currentSize = getCurrentSize();
    }

    @Override
    public void run() {

        while (true) {

            if (currentSize != getCurrentSize()) {
                this.currentSize = getCurrentSize();
                this.component.repaint();
                System.out.println("Repainting...");
            }

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }

    }

    private int getCurrentSize() {
        return component.getTerrain().getEmptyTilesCount();
    }
}
