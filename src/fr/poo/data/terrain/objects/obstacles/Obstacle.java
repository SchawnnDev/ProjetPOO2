package fr.poo.data.terrain.objects.obstacles;

import fr.poo.data.Position;
import fr.poo.data.terrain.objects.TerrainObject;

public abstract class Obstacle extends TerrainObject {
    private Position at;

    public Obstacle(Position at) {
        this.at = at;
    }

    public Position getAt() {
        return at;
    }
}
