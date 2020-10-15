package fr.poo.data.terrain.objects.obstacles;

import fr.poo.data.Position;
import fr.poo.data.terrain.objects.TerrainObjectData;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class Triangle extends Obstacle {

    private int height;

    public Triangle(int height, Position at)
    {
        super(at);
        this.height = height;
    }

    @Override
    public TerrainObjectData calculateTerrainObjectData() {
        List<Position> positions = new ArrayList<>();
        int width = 0;

        for (int i = height - 1; i >= 0; i--) {

            Position mid = new Position(getAt().getX(), getAt().getY() + i);
            positions.add(mid);

            for (int j = 1; j <= width; j++) {
                positions.add(mid.clone().addX(j));
                positions.add(mid.clone().addX(-j));
            }

            width++;
        }

        return new TerrainObjectData(this, positions);
    }

}
