package fr.poo.data.terrain.objects;

public class Player extends TerrainObject {
    private String name;

    public Player(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public TerrainObjectData calculateTerrainObjectData() {
        return null;
    }
}
