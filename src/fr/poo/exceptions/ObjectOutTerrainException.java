package fr.poo.exceptions;

import fr.poo.data.Position;
import fr.poo.data.terrain.objects.TerrainObject;
import fr.poo.data.terrain.objects.obstacles.Obstacle;

public class ObjectOutTerrainException extends Exception {

    public ObjectOutTerrainException()
    {
        super("Un objet est sorti du terrain.");
    }

    public ObjectOutTerrainException(Position position)
    {
        super("L'object (" + position.getX() + ", " + position.getY() + ") est sorti du terrain.");
    }

}
