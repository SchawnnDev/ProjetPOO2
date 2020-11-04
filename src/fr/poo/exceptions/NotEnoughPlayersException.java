package fr.poo.exceptions;

public class NotEnoughPlayersException extends Exception {

    public NotEnoughPlayersException()
    {
        super("Veuillez placer deux joueurs: point de départ et point d'arrivée.");
    }

}
