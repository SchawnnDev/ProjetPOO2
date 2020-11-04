package fr.poo.graphs.astar;

import fr.poo.data.Position;
import fr.poo.graphs.NodeItem;

import java.util.ArrayList;
import java.util.List;

public class Utils {

    public static List<Position> toPositionsList(List<NodeItem> nodes)
    {
        List<Position> result = new ArrayList<>();
        for (NodeItem item : nodes)
            result.add(item);
        return result;
    }

}
