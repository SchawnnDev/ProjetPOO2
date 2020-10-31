package fr.poo.exceptions;

import fr.poo.data.terrain.objects.TerrainObject;
import fr.poo.data.terrain.objects.obstacles.Obstacle;

public class ObjectOutTerrainException extends Exception {

    public ObjectOutTerrainException()
    {
        super("Un objet est sorti du terrain.");
    }

}
