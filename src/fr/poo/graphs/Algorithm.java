package fr.poo.graphs;

import fr.poo.data.Position;
import fr.poo.data.terrain.Terrain;
import fr.poo.data.terrain.objects.TerrainObject;
import fr.poo.data.terrain.objects.obstacles.Obstacle;

import java.util.ArrayList;
import java.util.List;

public abstract class Algorithm implements PathFindingAlgorithm {

    private Terrain terrain;
    private List<NodeItem> nodes;

    public Algorithm(Terrain terrain) {
        this.terrain = terrain;
        this.nodes = new ArrayList<>();
    }

    public void generateNodes() {
        if (terrain == null) return;

        // generating nodes

        for (int x = 0; x < terrain.getWidth(); x++) {
            for (int y = 0; y < terrain.getHeight(); y++) {
                TerrainObject object = terrain.getObject(x, y);

                // on ajoute pas l'obstacle car on veut les éviter.
                if (object instanceof Obstacle)
                    continue;

                nodes.add(new NodeItem(x, y, object));
            }
        }

        // searching neighbors

        for (int x = 0; x < terrain.getWidth(); x++) {
            for (int y = 0; y < terrain.getHeight(); y++) {
                NodeItem node = getNodeAt(x, y);

                if (node == null) continue;

                NodeItem foundNode;

                // voisin gauche
                if (x - 1 >= 0) {
                    if ((foundNode = getNodeAt(x - 1, y)) != null)
                        node.addNeighbor(foundNode);
                }


                // voisin droit
                if (x + 1 < terrain.getWidth()) {
                    if ((foundNode = getNodeAt(x + 1, y)) != null)
                        node.addNeighbor(foundNode);
                }

                // voisin du bas
                if (y - 1 >= 0) {
                    if ((foundNode = getNodeAt(x, y - 1)) != null)
                        node.addNeighbor(foundNode);
                }

                // voisin du bas
                if (y + 1 < terrain.getHeight()) {
                    if ((foundNode = getNodeAt(x, y + 1)) != null)
                        node.addNeighbor(foundNode);
                }

            }
        }

    }

    public NodeItem getNodeAt(Position position) {
        return getNodeAt(position.getX(), position.getY());
    }

    public NodeItem getNodeAt(int x, int y) {
        for (NodeItem node : nodes) {
            if (node.getX() == x && node.getY() == y)
                return node;
        }
        return null;
    }

    public Terrain getTerrain() {
        return terrain;
    }

    public List<NodeItem> getNodes() {
        return nodes;
    }
}
