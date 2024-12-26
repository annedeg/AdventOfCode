package helpers;

import java.util.Objects;

public class MatrixLocation {
    private char val;
    public int x;
    public int y;
    public int dist;
    public Direction direction;
    public MatrixLocation(int x, int y) {
        this.x = x;
        this.y = y;
        this.direction = Direction.UP;
    }

    public MatrixLocation(int x, int y, Direction direction) {
        this.x = x;
        this.y = y;
        this.direction = direction;
    }

    public MatrixLocation(int x, int y, char val) {
        this.x = x;
        this.y = y;
        this.val = val;
        this.direction = Direction.UP;
    }

    public void setDist(int dist) {
        this.dist = dist;
    }

    public int getDist() {
        return dist;
    }

    public char getVal() {
        return val;
    }

    public int getY() {
        return y;
    }

    public int getX() {
        return x;
    }

    public Direction getDirection() {
        return direction;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MatrixLocation that = (MatrixLocation) o;
        return x == that.x && y == that.y && direction == that.direction;
    }

    public boolean locationEquals(MatrixLocation ml) {
        return x == ml.x && y == ml.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y, direction);
    }

    public int getValue(int[][] matrix) {
        return matrix[this.getY()][this.getX()];
    }

    public char getValue(char[][] matrix) {
        return matrix[this.getY()][this.getX()];
    }

    public Node toNode(int h, int g) {
        return new Node(this.getX(), this.getY(), g, h);
    }

    @Override
    public String toString() {
        return "MatrixLocation{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
