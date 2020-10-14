package fr.poo.exceptions;

import fr.poo.data.terrain.objects.obstacles.Obstacle;

public class ObstacleOutTerrainException extends Exception {

    public ObstacleOutTerrainException(Obstacle obstacle)
    {
        super("L'obstacle est sorti du terrain (" + obstacle.getAt().getX() + "," + obstacle.getAt().getY() + ")");
    }

}
