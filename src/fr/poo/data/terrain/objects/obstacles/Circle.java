package fr.poo.data.terrain.objects.obstacles;

import fr.poo.data.Position;
import fr.poo.data.terrain.objects.TerrainObjectData;

import java.util.ArrayList;
import java.util.List;

public class Circle extends Obstacle {
    private int radius;

    public Circle(int radius, Position at) {
        super(at);
        this.radius = radius;
    }

    @Override
    public TerrainObjectData calculateTerrainObjectData() {

        List<Position> positions = new ArrayList<>();

        for (int y = -radius; y <= radius; y++)
            for (int x = -radius; x <= radius; x++)
                if (x * x + y * y <= radius * radius)
                    positions.add(new Position(getAt().getX() + x, getAt().getY() + y));

        return new TerrainObjectData(this, positions.toArray(new Position[positions.size()]));
    }
}