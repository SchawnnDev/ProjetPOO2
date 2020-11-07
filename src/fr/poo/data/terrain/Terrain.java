package fr.poo.data.terrain;

import fr.poo.data.Position;
import fr.poo.data.terrain.objects.PathItem;
import fr.poo.data.terrain.objects.Player;
import fr.poo.data.terrain.objects.TerrainObject;
import fr.poo.data.terrain.objects.TerrainObjectData;
import fr.poo.data.terrain.objects.obstacles.Circle;
import fr.poo.data.terrain.objects.obstacles.Obstacle;
import fr.poo.data.terrain.objects.obstacles.Rectangle;
import fr.poo.data.terrain.objects.obstacles.Triangle;
import fr.poo.exceptions.NotEnoughPlayersException;
import fr.poo.exceptions.ObjectOutTerrainException;
import fr.poo.exceptions.ObstacleOutTerrainException;
import fr.poo.exceptions.PathNotFoundException;
import fr.poo.graphs.PathFindingAlgorithm;
import fr.poo.io.ISerializable;
import fr.poo.threads.ThreadManager;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

public class Terrain implements ISerializable<Terrain> {
    private final Random random = new Random();
    private final int maxTrialCount = 40;
    private int width;
    private int height;
    private TerrainObject[][] objects;

    public Terrain(int width, int height) {
        this.width = width;
        this.height = height;
        createTerrain();
    }

    public Terrain() {
    }

    public void createTerrain() {
        this.objects = new TerrainObject[width][height]; // donc [x][y]
    }

    public boolean isEmpty(int x, int y) {
        return getObject(x, y) == null;
    }

    public TerrainObject getObject(int x, int y) {
        return this.objects[x][y];
    }

    public void setObject(Position position, TerrainObject object) {
        setObject(position.getX(), position.getY(), object);
    }

    public void setObject(int x, int y, TerrainObject object) {
        this.objects[x][y] = object;
    }

    public void addObstacle(Obstacle obstacle) throws ObstacleOutTerrainException {
        TerrainObjectData data = obstacle.calculateTerrainObjectData();

        try {
            addTerrainObject(data);
        } catch (ObjectOutTerrainException e) {
            throw new ObstacleOutTerrainException(obstacle);
        }
    }

    public void addTerrainObject(TerrainObjectData data) throws ObjectOutTerrainException {

        for (Position position : data.getPositions()) {
            if (checkPosition(position.getX(), position.getY())) continue;
            throw new ObjectOutTerrainException(position);
        }

        for (Position position : data.getPositions())
            objects[position.getX()][position.getY()] = data.getTerrainObject();
    }

    public void addPlayer(Player player) throws ObjectOutTerrainException {
        addTerrainObject(player.calculateTerrainObjectData());
    }

    public boolean checkPosition(Position position) {
        return position != null && checkPosition(position.getX(), position.getY());
    }

    public boolean checkPosition(int x, int y) {
        return x >= 0 && x < width && y >= 0 && y < height && objects[x][y] == null;
    }

    public boolean checkPositions(Position[] positions) {
        for (Position position : positions) {
            if (checkPosition(position.getX(), position.getY())) continue;
            return false;
        }
        return true;
    }

    public List<Player> getPlayers() {
        List<Player> players = new ArrayList<>();
        for (int x = 0; x < getWidth(); x++) {
            for (int y = 0; y < getHeight(); y++) {
                TerrainObject object = objects[x][y];
                if (object == null || !(object instanceof Player) || players.contains(object))
                    continue;
                players.add((Player) object);

            }
        }
        return players;
    }

    public void executeAlgorithm(PathFindingAlgorithm algorithm) throws NotEnoughPlayersException, PathNotFoundException, ObjectOutTerrainException {
        List<Player> players = getPlayers();

        if (players.size() < 2)
            throw new NotEnoughPlayersException();

        List<Position> path = algorithm.findPathTo(players.get(0).getAt(), players.get(1).getAt());

        for (int i = 1; i < path.size() - 1; i++)
            addTerrainObject(new PathItem(path.get(i)).calculateTerrainObjectData());

    }

    public Callable<TerrainObjectData> generateRandomItem() {
        return () -> {
            int trials = 0;

            do {
                int x = random.nextInt(getWidth());
                int y = random.nextInt(getHeight());
                Position position = new Position(x, y);
                TerrainObjectData data = generateRandomObstacle(position);

                if (data != null && checkPositions(data.getPositions()))
                    return data;

            } while (trials++ < maxTrialCount);

            return null;
        };
    }

    public List<Future<TerrainObjectData>> generateRandomItems(int nb) throws InterruptedException {
        List<Callable<TerrainObjectData>> callables = new ArrayList<>();

        for (int i = 0; i < nb; i++)
            callables.add(generateRandomItem());

        return ThreadManager.getService().invokeAll(callables);
    }

    public TerrainObjectData generateRandomObstacle(Position at) {
        Obstacle obstacle = null;
        final int height = getHeight();

        switch (random.nextInt(3)) {
            case 0:
                obstacle = new Circle(height, at);
                break;
            case 1:
                obstacle = new Rectangle(20, height, at);
                break;
            case 2:
                obstacle = new Triangle(height, at);
                break;
            default:
                break;
        }
        TerrainObjectData data;
        int trials = 0;

        do {
            data = obstacle.clone().calculateRandomTerrainObjectData(random);
        } while (!checkPositions(data.getPositions()) && trials++ < maxTrialCount);

        return data;
    }

    public int getEmptyTilesCount() {
        int count = 0;
        for (int x = 0; x < getWidth(); x++) {
            for (int y = 0; y < getHeight(); y++) {
                if (objects[x][y] != null)
                    continue;
                count++;
            }
        }
        return count;
    }

    public void clear() {
        for (int x = 0; x < getWidth(); x++) {
            for (int y = 0; y < getHeight(); y++) {
                if (objects[x][y] instanceof Player)
                    continue;
                objects[x][y] = null;
            }
        }
    }

    public TerrainObject[][] getObjects() {
        return objects;
    }

    public List<Obstacle> getObstacles() {
        List<Obstacle> obstacles = new ArrayList<>();
        for (int x = 0; x < getWidth(); x++) {
            for (int y = 0; y < getHeight(); y++) {
                TerrainObject object = objects[x][y];
                if (object == null || !(objects[x][y] instanceof Obstacle) || obstacles.contains(object))
                    continue;
                obstacles.add((Obstacle) object);
            }
        }
        return obstacles;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    @Override
    public List<String> serialize() {
        List<String> list = new ArrayList<>();
        list.add(String.valueOf(getWidth()));
        list.add(String.valueOf(getHeight()));

        for (Obstacle obstacle : getObstacles())
            list.add(String.join(",", obstacle.serialize()));

        return list;
    }

    @Override
    public Terrain deserialize(List<String> lines) {
        this.width = Integer.parseInt(lines.get(0));
        this.height = Integer.parseInt(lines.get(1));
        createTerrain();

        for (int i = 2; i < lines.size(); i++) {
            List<String> split = Arrays.asList(lines.get(i).split(","));

            // System.out.println("Trying to get class " + split.get(0) + " in " + lines.get(i));

            try {
                Class<?> cl = Class.forName(split.get(0));

                Obstacle obstacle = (Obstacle) cl.getConstructor().newInstance();
                obstacle.deserialize(split.stream().skip(1).collect(Collectors.toList()));
                System.out.println("Loaded obstacle " + obstacle.getAt().getX() + " >> " + obstacle.getAt().getY());

                addObstacle(obstacle);

            } catch (ClassNotFoundException | NoSuchMethodException | IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (ObstacleOutTerrainException e) {
                e.printStackTrace();
                break;
            }

        }

        System.out.println("Terminé");

        return this;
    }

    public static void copy(Terrain t1, Terrain t2) {
        for (int x = 0; x < t1.getWidth(); x++) {
            for (int y = 0; y < t1.getHeight(); y++) {
                TerrainObject object = t1.getObject(x, y);
                if (object == null || !(object instanceof Obstacle))
                    continue;
                t2.setObject(x, y, object);
            }
        }
    }
}
