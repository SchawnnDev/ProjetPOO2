package fr.poo.exceptions;

public class PathNotFoundException extends Exception {

    public PathNotFoundException()
    {
        super("Aucun chemin n'a été trouvé.");
    }

}
