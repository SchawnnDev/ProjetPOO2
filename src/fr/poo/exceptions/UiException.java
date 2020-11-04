package fr.poo.exceptions;

import javax.swing.*;
import java.awt.*;

public class UiException extends Exception {

    public UiException(String message) {
        super(message);
    }

    public UiException(Exception ex, Component component) {
        this(ex.getMessage(), component);
    }

    public UiException(String message, Component component) {
        JOptionPane.showMessageDialog(component, message, "Une erreur est survenue", JOptionPane.ERROR_MESSAGE);
    }

}
