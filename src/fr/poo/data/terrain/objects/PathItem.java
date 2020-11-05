package fr.poo.data.terrain.objects;

import fr.poo.data.Position;
import fr.poo.data.terrain.objects.obstacles.Obstacle;

import java.util.List;
import java.util.Random;

public class PathItem extends TerrainObject {
    private Position at;

    public PathItem(Position at) {
        this.at = at;
    }

    @Override
    public TerrainObjectData calculateTerrainObjectData() {
        return new TerrainObjectData(this, new Position[]{at});
    }

    @Override
    public TerrainObjectData calculateRandomTerrainObjectData(Random random) {
        return null;
    }

    @Override
    public List<String> serialize() {
        return null;
    }

    @Override
    public Obstacle deserialize(List<String> lines) {
        return null;
    }
}