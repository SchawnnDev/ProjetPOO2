package fr.poo.graphs;

import fr.poo.data.Position;
import fr.poo.exceptions.PathNotFoundException;

import java.util.List;

public interface PathFindingAlgorithm {

    List<Position> findPathTo(Position start, Position end) throws PathNotFoundException;

}
