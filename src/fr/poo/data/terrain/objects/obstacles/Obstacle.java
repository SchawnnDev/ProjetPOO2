package fr.poo.data.terrain.objects.obstacles;

import fr.poo.data.Position;
import fr.poo.data.terrain.objects.TerrainObject;

import java.util.ArrayList;
import java.util.List;

public abstract class Obstacle extends TerrainObject {
    private Position at;

    public Obstacle() {
    }

    public Obstacle(Position at) {
        this.at = at;
    }

    public Position getAt() {
        return at;
    }

    public void setAt(Position at) {
        this.at = at;
    }

    @Override
    public List<String> serialize() {
        List<String> lines = new ArrayList<>();
        lines.add(getClass().getCanonicalName());
        lines.add(getAt().serialize().get(0));
        return lines;
    }

    @Override
    public Obstacle deserialize(List<String> lines) {
        this.setAt(new Position().deserialize(lines));
        return this;
    }

    public abstract Obstacle clone();

}
