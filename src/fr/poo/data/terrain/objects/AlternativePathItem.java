package fr.poo.data.terrain.objects;

import fr.poo.data.Position;

import java.util.Random;

public class AlternativePathItem extends TerrainObject {
    private Position at;

    public AlternativePathItem(Position at) {
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
}