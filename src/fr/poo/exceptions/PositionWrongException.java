package fr.poo.exceptions;

import fr.poo.data.Position;
import fr.poo.data.terrain.objects.obstacles.Obstacle;

public class PositionWrongException extends Exception {

    public PositionWrongException(Position position)
    {
        super("La position ne tient pas dans le terrain (" + position.getX() + "," + position.getY() + ")");
    }

}