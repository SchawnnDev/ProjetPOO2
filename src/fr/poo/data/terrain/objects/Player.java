package fr.poo.data.terrain.objects;

import fr.poo.data.Position;
import fr.poo.data.terrain.objects.obstacles.Obstacle;
import fr.poo.data.terrain.objects.obstacles.Rectangle;

import java.util.List;
import java.util.Random;

public class Player extends TerrainObject {
    private String name;
    private Position at;

    public Player(String name, Position at) {
        this.name = name;
        this.at = at;
    }

    public String getName() {
        return name;
    }

    public Position getAt() {
        return at;
    }

    @Override
    public TerrainObjectData calculateTerrainObjectData() {
        //int width = 5;
        //Rectangle rectangle = new Rectangle(width, width, at.clone().addX(-(width / 2)).addY(-(width / 2)));
      //  return new TerrainObjectData(this, rectangle.calculateTerrainObjectData().getPositions());
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
