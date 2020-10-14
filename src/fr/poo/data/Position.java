package fr.poo.data;

public class Position {
    private int x;
    private int y;

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getNormX() { return x - 1; }

    public int getNormY() { return y - 1; }

}
