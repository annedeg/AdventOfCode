package helpers;

public enum Direction {
    UP, RIGHT, DOWN, LEFT;

    public Direction rotateRight() {
        switch (this) {
            case UP -> {
                return RIGHT;
            }
            case RIGHT -> {
                return DOWN;
            }
            case DOWN -> {
                return LEFT;
            }
            case LEFT -> {
                return UP;
            }
        }
        return null;
    }

    public Direction rotateLeft() {
        switch (this) {
            case UP -> {
                return LEFT;
            }
            case RIGHT -> {
                return UP;
            }
            case DOWN -> {
                return RIGHT;
            }
            case LEFT -> {
                return DOWN;
            }
        }
        return null;
    }

    public MatrixLocation stepForward(MatrixLocation matrixLocation) {
        MatrixLocation nml = new MatrixLocation(matrixLocation.getX(), matrixLocation.getY());
        switch (this) {
            case UP -> nml.y--;
            case RIGHT -> nml.x++;
            case DOWN -> nml.y++;
            case LEFT -> nml.x--;
        }
        return nml;
    }

    public MatrixLocation stepForward(DirectionalNode directionalNode) {
        MatrixLocation nml = new MatrixLocation(directionalNode.getX(), directionalNode.getY(), directionalNode.getDirection());
        switch (this) {
            case UP -> nml.y--;
            case RIGHT -> nml.x++;
            case DOWN -> nml.y++;
            case LEFT -> nml.x--;
        }
        return nml;
    }
}
