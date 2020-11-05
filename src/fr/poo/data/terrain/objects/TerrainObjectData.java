package fr.poo.data.terrain.objects;

import fr.poo.data.Position;
import java.util.List;

public class TerrainObjectData {
    private TerrainObject terrainObject;
    private Position[] positions;

    public TerrainObjectData(TerrainObject terrainObject, Position[] positions) {
        this.terrainObject = terrainObject;
        this.positions = positions;
    }

    public TerrainObjectData(TerrainObject terrainObject, List<Position> positions) {
        this(terrainObject, positions.toArray(new Position[positions.size()]));
    }

    public TerrainObjectData() {

    }

    public Position[] getPositions() {
        return positions;
    }

    public TerrainObject getTerrainObject() {
        return terrainObject;
    }

}
