package fr.poo.graphs.astar;

import fr.poo.graphs.NodeItem;

import java.util.StringJoiner;

public class Node implements Comparable<Node> {
    private final NodeItem current;
    private NodeItem previous;
    private double routeScore;
    private double estimatedScore;

    public Node(NodeItem current) {
        this(current, null, Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY);
    }

    public Node(NodeItem current, NodeItem previous, double routeScore, double estimatedScore) {
        this.current = current;
        this.previous = previous;
        this.routeScore = routeScore;
        this.estimatedScore = estimatedScore;
    }

    public NodeItem getCurrent() {
        return current;
    }

    public NodeItem getPrevious() {
        return previous;
    }

    public double getRouteScore() {
        return routeScore;
    }

    public double getEstimatedScore() {
        return estimatedScore;
    }

    public void setPrevious(NodeItem previous) {
        this.previous = previous;
    }

    public void setRouteScore(double routeScore) {
        this.routeScore = routeScore;
    }

    public void setEstimatedScore(double estimatedScore) {
        this.estimatedScore = estimatedScore;
    }

    @Override
    public int compareTo(Node other) {
        if (this.estimatedScore > other.estimatedScore) {
            return 1;
        } else if (this.estimatedScore < other.estimatedScore) {
            return -1;
        } else {
            return 0;
        }
    }

    @Override
    public String toString() {
        return "Node{" +
                "current=" + current +
                ", previous=" + previous +
                ", routeScore=" + routeScore +
                ", estimatedScore=" + estimatedScore +
                '}';
    }
}
