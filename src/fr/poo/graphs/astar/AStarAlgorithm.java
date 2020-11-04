package fr.poo.graphs.astar;

import fr.poo.data.Position;
import fr.poo.data.terrain.Terrain;
import fr.poo.data.terrain.objects.AlternativePathItem;
import fr.poo.exceptions.ObjectOutTerrainException;
import fr.poo.exceptions.PathNotFoundException;
import fr.poo.graphs.Algorithm;
import fr.poo.graphs.NodeItem;
import fr.poo.threads.ThreadManager;

import java.util.*;
import java.util.stream.Collectors;

public class AStarAlgorithm extends Algorithm {

    public AStarAlgorithm(Terrain terrain) {
        super(terrain);
        generateNodes();
    }

    @Override
    public List<Position> findPathTo(Position start, Position end) throws PathNotFoundException {

        NodeItem startNodeItem = getNodeAt(start);
        NodeItem endNodeItem = getNodeAt(end);

        Map<NodeItem, Node> allNodes = new HashMap<>();
        Queue<Node> openSet = new PriorityQueue<>();

        Node startNode = new Node(startNodeItem, null, 0d, startNodeItem.computeDistance(endNodeItem));
        allNodes.put(startNodeItem, startNode);
        openSet.add(startNode);

        while (!openSet.isEmpty()) {
            //System.out.println("Open Set contains: " + openSet.stream().map(Node::getCurrent).collect(Collectors.toSet()));
            Node next = openSet.poll();
           // System.out.println("Looking at node: " + next);
            if (next.getCurrent().equals(endNodeItem)) {
                System.out.println("Found our destination!");

                List<NodeItem> route = new ArrayList<>();
                Node current = next;
                do {
                    route.add(0, current.getCurrent());
                    current = allNodes.get(current.getPrevious());
                } while (current != null);

               // System.out.println("Route: " + route);
                return Utils.toPositionsList(route);
            }

            for (NodeItem neighbour : next.getCurrent().getNeighbors()) {
                double newScore = next.getRouteScore() +next.getCurrent().computeDistance(neighbour);
                Node nextNode = allNodes.getOrDefault(neighbour, new Node(neighbour));
                allNodes.put(neighbour, nextNode);

                if (nextNode.getRouteScore() > newScore) {
/*
                    ThreadManager.getService().submit(() -> {
                        try {
                            getTerrain().addTerrainObject(new AlternativePathItem(nextNode.getCurrent()).calculateTerrainObjectData());
                        } catch (ObjectOutTerrainException e) {
                        }
                    }); */
                    nextNode.setPrevious(next.getCurrent());
                    nextNode.setRouteScore(newScore);
                    nextNode.setEstimatedScore(newScore + neighbour.computeDistance(endNodeItem));
                    openSet.add(nextNode);
                    //System.out.println("Found a better route to node: " + nextNode);
                }
            }

        }

        throw new PathNotFoundException();

    }
}
