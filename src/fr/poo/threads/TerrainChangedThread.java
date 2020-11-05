package fr.poo.threads;

import fr.poo.graphics.components.TerrainComponent;

public class TerrainChangedThread implements Runnable {
    private TerrainComponent component;
    private int currentSize;
    private boolean running;

    public TerrainChangedThread(TerrainComponent component) {
        this.component = component;
        this.currentSize = getCurrentSize();
        this.running = true;
    }

    @Override
    public void run() {

        while (running) {

            if (currentSize != getCurrentSize()) {
                this.currentSize = getCurrentSize();
                this.component.repaint();
                System.out.println("Repainting...");
            }

        }

    }

    private int getCurrentSize() {
        return component.getTerrain().getEmptyTilesCount();
    }

    public void close() {
        this.running = false;
    }
}
