package fr.poo.graphs;

import fr.poo.data.Position;
import fr.poo.data.terrain.objects.TerrainObject;

import java.util.ArrayList;
import java.util.List;

public class NodeItem extends Position {
    private TerrainObject object;
    private List<NodeItem> neighbors;

    public NodeItem(int x, int y, TerrainObject object) {
        super(x, y);
        this.object = object;
        this.neighbors = new ArrayList<>();
    }

    public NodeItem(Position position, TerrainObject object) {
        this(position.getX(), position.getY(), object);
    }

    public TerrainObject getObject() {
        return object;
    }

    public List<NodeItem> getNeighbors() {
        return neighbors;
    }

    public boolean hasNeighbors() {
        return neighbors.size() > 0;
    }

    public boolean isNeighbor(NodeItem node) {
        for (NodeItem neighbor : neighbors)
            if (neighbor.equals(node))
                return true;
        return false;
    }

    public NodeItem addNeighbor(NodeItem neighbor) {
        if (!isNeighbor(neighbor))
            neighbors.add(neighbor);
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NodeItem node = (NodeItem) o;
        return getX() == node.getX() && getY() == node.getY();
    }

    @Override
    public String toString() {
        return "(" + getX() + "," + getY() + ")";
    }
}
