package fr.poo.data.terrain.objects;

import java.util.Random;

public abstract class TerrainObject {

    public TerrainObject() {

    }

    public abstract TerrainObjectData calculateTerrainObjectData();

    public abstract TerrainObjectData calculateRandomTerrainObjectData(Random random);

}
