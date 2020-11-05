package fr.poo.data.terrain.objects.obstacles;

import fr.poo.data.Position;
import fr.poo.data.terrain.objects.TerrainObjectData;
import fr.poo.utils.Randoms;

import java.util.List;
import java.util.Random;

public class Rectangle extends Obstacle {
    private int width;
    private int height;

    public Rectangle() {

    }

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
        width = Randoms.randomRangeInt(1 / 3 * width, width);
        height = Randoms.randomRangeInt(1 / 3 * height, height);
        return calculateTerrainObjectData();
    }

    @Override
    public List<String> serialize() {
        List<String> lines = super.serialize();
        lines.add(String.valueOf(width));
        lines.add(String.valueOf(height));
        return lines;
    }

    @Override
    public Obstacle deserialize(List<String> lines) {
        Obstacle obstacle = super.deserialize(lines);
        ((Rectangle)obstacle).width = Integer.parseInt(lines.get(1));
        ((Rectangle)obstacle).height = Integer.parseInt(lines.get(2));
        System.out.println("Rectangle à " + getAt() + "> width: " + width + " | height: " + height);
        return obstacle;
    }

    @Override
    public Obstacle clone() {
        return new Rectangle(this.width, this.height, getAt());
    }
}
