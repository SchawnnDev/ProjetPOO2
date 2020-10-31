package fr.poo;

import fr.poo.data.Position;
import fr.poo.data.terrain.Terrain;
import fr.poo.data.terrain.objects.Player;
import fr.poo.exceptions.ObjectOutTerrainException;
import fr.poo.exceptions.PositionWrongException;
import fr.poo.graphics.MainFrame;

public class Main {

    public static void main(String[] args) {
        //       MainFrame frame = new MainFrame();
        //     frame.open();
        Terrain terrain = new Terrain(300, 150);
 /*
        try {

            terrain.addObstacle(new Circle(5, new Position(10, 10)));
            terrain.addObstacle(new Rectangle(7,5, new Position(20,10)));
            terrain.addObstacle(new Rectangle(10, 10, new Position(30,10)));
            terrain.addObstacle(new Rectangle(100,100, new Position(50,20)));
            terrain.addObstacle(new Triangle(7, new Position(30, 2)));
        } catch (ObstacleOutTerrainException e) {
            e.printStackTrace();
            return;
        } */

        Player romeo = new Player("Roméo", new Position(5, terrain.getHeight() / 2));
        Player juliette = new Player("Juliette", new Position(terrain.getWidth()-5, terrain.getHeight() / 2));

        try {
            terrain.addPlayer(romeo);
            terrain.addPlayer(juliette);
        } catch (ObjectOutTerrainException e) {
            e.printStackTrace();
        }

        terrain.fillTerrainWithItems(50);
        // display(terrain);

        MainFrame frame = new MainFrame(terrain);
        frame.open();
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
