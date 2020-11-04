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

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;

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
        this.objects[position.getX()][position.getY()] = object;
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
        /*
        try {
                    /*Future<TerrainObjectData> data = ThreadManager.getService().submit(generateRandomObstacle(position));
                    addTerrainObject(data.get());
            addTerrainObject();
            break;
        } catch (ObjectOutTerrainException e) {
            //e.printStackTrace();
        }*/
                TerrainObjectData data = generateRandomObstacle2(position);

                if (data != null && checkPositions(data.getPositions()))
                    return data;

            } while (true && trials++ < maxTrialCount);

            return null;
        };
    }

    public List<Future<TerrainObjectData>> generateRandomItems(int nb) throws InterruptedException {
        List<Callable<TerrainObjectData>> callables = new ArrayList<>();

        for (int i = 0; i < nb; i++)
            callables.add(generateRandomItem());

        return ThreadManager.getService().invokeAll(callables);
    }

    public TerrainObjectData generateRandomObstacle2(Position at) {
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
            data = obstacle.calculateRandomTerrainObjectData(random);
        } while (!checkPositions(data.getPositions()) && trials++ < maxTrialCount);

        return data;
    }

    public Callable<TerrainObjectData> generateRandomObstacle(Position at) {
        return () -> {
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
                data = obstacle.calculateRandomTerrainObjectData(random);
            } while (!checkPositions(data.getPositions()) && trials++ < maxTrialCount);

            return data;
        };
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

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    @Override
    public List<String> serialize() {
        List<String> list = new ArrayList<>();
        list.add(getWidth() + " " + getHeight());

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                if (objects[i][j] == null) continue;
                list.add(i + ";" + j + ";" + String.join("|", objects[i][j].calculateTerrainObjectData().serialize()));
            }
        }
        return list;
    }

    @Override
    public Terrain deserialize(List<String> lines) {
        String[] fLine = lines.get(0).split(" ");
        this.width = Integer.parseInt(fLine[0]);
        this.height = Integer.parseInt(fLine[1]);
        createTerrain();

        for (int i = 1; i < lines.size(); i++) {
            String[] split = lines.get(i).split("|");
            objects[Integer.parseInt(split[0])][Integer.parseInt(split[1])] = null;
            //new TerrainObjectData().deserialize(Arrays.asList(split[2].split(" ")));
        }

        return this;
    }
}
