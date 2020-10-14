package fr.poo;

import fr.poo.data.Position;
import fr.poo.data.terrain.Terrain;
import fr.poo.data.terrain.objects.obstacles.Circle;
import fr.poo.data.terrain.objects.obstacles.Rectangle;
import fr.poo.exceptions.ObstacleOutTerrainException;


public class Main {

    public static void main(String[] args) {
        //       MainFrame frame = new MainFrame();
        //     frame.open();
        Terrain terrain = new Terrain(50, 20);
        try {
            terrain.addObstacle(new Circle(5, new Position(10, 10)));
            terrain.addObstacle(new Rectangle(7,5, new Position(20,10)));
            terrain.addObstacle(new Rectangle(10, 10, new Position(30,10)));
            terrain.addObstacle(new Rectangle(1,1, new Position(50,20)));
        } catch (ObstacleOutTerrainException e) {
            e.printStackTrace();
            return;
        }
        display(terrain);
    }

    public static void display(Terrain terrain) {
        for (int y = 0; y < terrain.getHeight(); y++) {
            for (int x = 0; x < terrain.getWidth(); x++) {

                System.out.print(terrain.isEmpty(x, y) ? "□ " : "⬛ ");
            }

            System.out.print("\n");
        }
    }

}
