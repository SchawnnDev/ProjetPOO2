package fr.poo.data.terrain.objects;

import fr.poo.data.Position;
import fr.poo.data.terrain.objects.obstacles.Obstacle;

import java.util.List;

public class TerrainObjectData {
    private Obstacle obstacle;
    private Position[] positions;

    public TerrainObjectData(Obstacle obstacle, Position[] positions) {
        this.obstacle = obstacle;
        this.positions = positions;
    }

    public TerrainObjectData(Obstacle obstacle, List<Position> positions)
    {
        this(obstacle, positions.toArray(new Position[positions.size()]));
    }

    public Position[] getPositions() {
        return positions;
    }

    public Obstacle getObstacle() {
        return obstacle;
    }
}
