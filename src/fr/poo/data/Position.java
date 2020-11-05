package fr.poo.data;

import fr.poo.io.ISerializable;

import java.util.Arrays;
import java.util.List;

public class Position implements ISerializable<Position> {
    private int x;
    private int y;

    public Position() {
        this(0, 0);
    }

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

    public Position clone() {
        return new Position(this.x, this.y);
    }

    public Position addX(int x) {
        this.x += x;
        return this;
    }

    public Position addY(int y) {
        this.y += y;
        return this;
    }

    public double computeDistance(Position to) {
        return Math.sqrt(Math.pow(to.getX() - getX(), 2) + Math.pow(to.getY() - getY(), 2));
    }

    @Override
    public String toString() {
        return String.format("[%d, %d]", this.x, this.y);
    }

    @Override
    public List<String> serialize() {
        StringBuilder builder = new StringBuilder();
        builder.append(this.x);
        builder.append(' ');
        builder.append(this.y);
        return Arrays.asList(builder.toString());
    }

    @Override
    public Position deserialize(List<String> s) {
        String[] split = s.get(0).split(" ");
        if (split.length != 2) return this;
        this.x = Integer.parseInt(split[0]);
        this.y = Integer.parseInt(split[1]);
        System.out.println(String.format("position(%d,%d)", x, y));
        return this;
    }
}
