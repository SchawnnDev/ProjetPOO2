package fr.poo.data.terrain.objects.obstacles;

import fr.poo.data.Position;
import fr.poo.data.terrain.objects.TerrainObjectData;
import fr.poo.utils.Randoms;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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

    @Override
    public TerrainObjectData calculateRandomTerrainObjectData(Random random) {
        int tempRadius = radius;
        radius =  Randoms.randomRangeInt((int)((double)1/3 * radius), radius);
        TerrainObjectData data = calculateTerrainObjectData();
        radius = tempRadius;
        return data;
    }
}
