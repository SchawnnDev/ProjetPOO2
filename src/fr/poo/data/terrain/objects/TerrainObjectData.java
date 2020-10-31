package fr.poo.data.terrain.objects;

import fr.poo.data.Position;
import fr.poo.io.ISerializable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TerrainObjectData implements ISerializable<TerrainObjectData> {
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

    @Override
    public List<String> serialize() {
        List<String> str = new ArrayList<>();
       // str.add(terrainObject.getAt().serialize().get(0));
        for (Position position : getPositions())
            str.add(position.serialize().get(0));
        return str;
    }

    @Override
    public TerrainObjectData deserialize(List<String> s) {
       // this.terrainObject.setAt(new Position().deserialize(Arrays.asList(s.get(0))));
        if (s.size() - 1 < 0) return this;
        Position[] positions = new Position[s.size() - 1];
        for (int i = 1; i < s.size(); i++)
            positions[i] = new Position().deserialize(Arrays.asList(s.get(i)));
        this.positions = positions;
        return this;
    }
}
