package fr.poo.utils;

public class Randoms {

    public static int randomRangeInt(int min, int max) {
        return (int) (Math.random() * ((max - min) + 1)) + min;
    }

}
