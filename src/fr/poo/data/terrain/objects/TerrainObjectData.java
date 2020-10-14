package fr.poo.data.terrain.objects;

import fr.poo.data.Position;
import fr.poo.data.terrain.objects.obstacles.Obstacle;

public class TerrainObjectData {
    private Obstacle obstacle;
    private Position[] positions;

    public TerrainObjectData(Obstacle obstacle, Position[] positions) {
        this.obstacle = obstacle;
        this.positions = positions;
    }

    public Position[] getPositions() {
        return positions;
    }

    public Obstacle getObstacle() {
        return obstacle;
    }
}
