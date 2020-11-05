package fr.poo.data.terrain.objects.obstacles;

import fr.poo.data.Position;
import fr.poo.data.terrain.objects.TerrainObjectData;
import fr.poo.utils.Randoms;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Circle extends Obstacle {
    private int radius;

    public Circle() {
    }

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
        radius = Randoms.randomRangeInt((int) ((double) 1 / 3 * radius), radius);
        return calculateTerrainObjectData();
    }

    @Override
    public List<String> serialize() {
        List<String> lines = super.serialize();
        lines.add(String.valueOf(radius));
        return lines;
    }

    @Override
    public Obstacle deserialize(List<String> lines) {
        Obstacle obstacle = super.deserialize(lines);
        ((Circle)obstacle).radius = Integer.parseInt(lines.get(1));
        System.out.println("Circle à " + getAt() + "> radius: " + radius);
        return obstacle;
    }

    @Override
    public Obstacle clone() {
        return new Circle(this.radius, getAt());
    }

}
