package fr.poo;

import fr.poo.data.Position;
import fr.poo.data.terrain.Terrain;
import fr.poo.data.terrain.objects.PathItem;
import fr.poo.data.terrain.objects.Player;
import fr.poo.exceptions.ObjectOutTerrainException;
import fr.poo.exceptions.PathNotFoundException;
import fr.poo.graphics.MainFrame;
import fr.poo.graphs.PathFindingAlgorithm;
import fr.poo.graphs.astar.AStarAlgorithm;
import fr.poo.threads.ThreadManager;

import java.util.List;

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
        Player juliette = new Player("Juliette", new Position(terrain.getWidth() - 5, terrain.getHeight() / 2));

        try {
            terrain.addPlayer(romeo);
            terrain.addPlayer(juliette);
        } catch (ObjectOutTerrainException e) {
            e.printStackTrace();
        }

        MainFrame frame = new MainFrame(terrain);
        frame.open();

        terrain.fillTerrainWithItems(300);
        // display(terrain);

        System.out.println("Bla");

        ThreadManager.getService().submit(() -> {
            PathFindingAlgorithm algorithm = new AStarAlgorithm(terrain);

            try {
                List<Position> path = algorithm.findPathTo(romeo.getAt(), juliette.getAt());

                for (int i = 1; i < path.size() - 1; i++) {
                    terrain.addTerrainObject(new PathItem(path.get(i)).calculateTerrainObjectData());
                }

            } catch (PathNotFoundException | ObjectOutTerrainException e) {
                e.printStackTrace();
            }
        });

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
