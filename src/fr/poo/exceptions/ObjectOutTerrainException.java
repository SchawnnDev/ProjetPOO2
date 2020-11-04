package fr.poo.exceptions;

import fr.poo.data.Position;

import java.awt.*;

public class ObjectOutTerrainException extends Exception {

    public ObjectOutTerrainException() {
        super("Un objet est sorti du terrain.");
    }

    public ObjectOutTerrainException(Position position) {
        super("L'object (" + position.getX() + ", " + position.getY() + ") est sorti du terrain.");
    }

}
