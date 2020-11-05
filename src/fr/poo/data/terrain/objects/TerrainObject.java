package fr.poo.data.terrain.objects;

import fr.poo.data.terrain.objects.obstacles.Obstacle;
import fr.poo.io.ISerializable;

import java.util.Random;

public abstract class TerrainObject implements ISerializable<Obstacle> {

    public TerrainObject() {

    }

    public abstract TerrainObjectData calculateTerrainObjectData();

    public abstract TerrainObjectData calculateRandomTerrainObjectData(Random random);

}
