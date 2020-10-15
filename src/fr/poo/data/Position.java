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

    public Position clone()
    {
        return new Position(this.x, this.y);
    }

    public Position addX(int x)
    {
        this.x += x;
        return this;
    }

    public Position addY(int y)
    {
        this.y += y;
        return this;
    }

}
