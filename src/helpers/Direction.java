package helpers;

public enum Direction {
    UP, RIGHT, DOWN, LEFT;

    public Direction getNext(Direction direction) {
        switch (direction) {
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
            default -> throw new IllegalStateException("Unexpected value: " + direction);
        }
    }
}
