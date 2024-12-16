package helpers;

import java.util.Objects;

public class SimpleMatrixLocation {
    public int x;
    public int y;

    public SimpleMatrixLocation(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public SimpleMatrixLocation(MatrixLocation matrixLocation) {
        this.x = matrixLocation.getX();
        this.y = matrixLocation.getY();
    }

    public int getY() {
        return y;
    }

    public int getX() {
        return x;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SimpleMatrixLocation that = (SimpleMatrixLocation) o;
        return x == that.x && y == that.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}
