package fr.poo.exceptions;

public class TrialCountExceededException extends Exception {

    public TrialCountExceededException(int limit) {
        super("Le nombre d'essais a été dépassé. (" + limit + ")");
    }

}
