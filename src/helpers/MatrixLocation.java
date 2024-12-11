package helpers;

import java.util.Objects;

public class MatrixLocation {
    public int x;
    public int y;
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

    @Override
    public int hashCode() {
        return Objects.hash(x, y, direction);
    }

    public int getValue(int[][] matrix) {
        return matrix[this.getY()][this.getX()];
    }
}
