package fr.poo;

import fr.poo.data.Position;
import fr.poo.data.terrain.Terrain;
import fr.poo.data.terrain.objects.Player;
import fr.poo.exceptions.ObjectOutTerrainException;
import fr.poo.graphics.MainFrame;
import fr.poo.graphics.frames.SettingsFrame;

import javax.swing.*;

public class Main {

    private static int terrainWidth = 300;
    private static int terrainHeight = 150;
    private static int pixelSize = 5;

    public static void main(String[] args) {

        try {
            UIManager.setLookAndFeel(
                    UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }

        new SettingsFrame(true);

    }

    public static void display(Terrain terrain) {
        for (int y = 0; y < terrain.getHeight(); y++) {
            for (int x = 0; x < terrain.getWidth(); x++) {

                System.out.print(terrain.isEmpty(x, y) ? "□ " : "⬛ ");
            }

            System.out.print("\n");
        }
    }


    public static int getPixelSize() {
        return pixelSize;
    }

    public static int getTerrainHeight() {
        return terrainHeight;
    }

    public static int getTerrainWidth() {
        return terrainWidth;
    }

    public static void setPixelSize(int pixelSize) {
        Main.pixelSize = pixelSize;
    }

    public static void setTerrainWidth(int terrainWidth) {
        Main.terrainWidth = terrainWidth;
    }

    public static void setTerrainHeight(int terrainHeight) {
        Main.terrainHeight = terrainHeight;
    }
}
