package fr.poo.data.terrain;

import fr.poo.data.Position;
import fr.poo.data.terrain.objects.TerrainObject;
import fr.poo.data.terrain.objects.TerrainObjectData;
import fr.poo.data.terrain.objects.obstacles.Obstacle;
import fr.poo.exceptions.ObstacleOutTerrainException;

public class Terrain {
    private int width;
    private int height;
    private TerrainObject[][] objects;

    public Terrain(int width, int height)
    {
        this.width = width;
        this.height = height;
        createTerrain();
    }

    public void createTerrain()
    {
        this.objects = new TerrainObject[width][height]; // donc [x][y]
    }

    public boolean isEmpty(int x, int y)
    {
        return getObject(x, y) == null;
    }

    public TerrainObject getObject(int x, int y)
    {
       return this.objects[x][y];
    }

    public void addObstacle(Obstacle obstacle) throws ObstacleOutTerrainException
    {
        TerrainObjectData data = obstacle.calculateTerrainObjectData();

        if(!checkPositions(data.getPositions()))
            throw new ObstacleOutTerrainException(data.getObstacle());

        for (Position position : data.getPositions()) {
            objects[position.getNormX()][position.getNormY()] = data.getObstacle();
        }

    }

    public boolean checkPosition(Position position)
    {
        return checkPosition(position.getNormX(), position.getNormY());
    }

    public boolean checkPosition(int x, int y)
    {
        return x >= 0 && x < width && y >= 0 && y < height && objects[x][y] == null;
    }

    public boolean checkPositions(Position[] positions)
    {
        for (Position position : positions) {
            if(checkPosition(position.getNormX(), position.getNormY())) continue;
            return false;
        }
        return true;
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
}
