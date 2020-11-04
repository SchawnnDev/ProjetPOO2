package fr.poo.data.terrain.objects.obstacles;

import fr.poo.data.Position;
import fr.poo.data.terrain.objects.TerrainObjectData;
import fr.poo.utils.Randoms;

import java.util.Random;

public class Rectangle extends Obstacle {
    private int width;
    private int height;

    public Rectangle(int width, int height, Position at) {
        super(at);
        this.width = width;
        this.height = height;
    }

    @Override
    public TerrainObjectData calculateTerrainObjectData() {
        Position[] positions = new Position[width * height];
        int i = 0;

        for (int x = getAt().getX(); x < getAt().getX() + width; x++)
            for (int y = getAt().getY(); y < getAt().getY() + height; y++)
                positions[i++] = new Position(x, y);

        return new TerrainObjectData(this, positions);
    }

    @Override
    public TerrainObjectData calculateRandomTerrainObjectData(Random random) {
        int tempWidth = width;
        int tempHeight = height;
        width = Randoms.randomRangeInt(1/3 * width, width);
        height = Randoms.randomRangeInt(1/3 * height, height);
        TerrainObjectData data = calculateTerrainObjectData();
        width = tempWidth;
        height = tempHeight;
        return data;
    }

}
