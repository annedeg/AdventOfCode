package helpers;

import java.util.Objects;

public class DirectionalNode extends Node {
    DirectionalNode parent = null;
    Direction direction;

    public DirectionalNode(int x, int y, double g, double h, Direction direction) {
        super(x, y, g, h);
        this.direction = direction;
    }

    public Direction getDirection() {
        return direction;
    }

    public void setParent(DirectionalNode node) {
        parent = node;
    }

    @Override
    public DirectionalNode getParent() {
        return parent;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DirectionalNode that = (DirectionalNode) o;
        return Objects.equals(parent, that.parent) && direction == that.direction;
    }

    @Override
    public int hashCode() {
        return Objects.hash(parent, direction);
    }

    @Override
    public String toString() {
        return "DirectionalNode{" +
                "direction=" + direction +
                ", g=" + g +
                ", h=" + h +
                ", f=" + getF() +
                ", x=" + x +
                ", y=" + y +
                '}';
    }

    public boolean anyParentHasLocation(DirectionalNode directionalNode) {
        DirectionalNode currentNode = this;
        while (directionalNode.getParent() != null) {
            if (directionalNode.getX() == currentNode.getX() && directionalNode.getY() == currentNode.getY()) {
                return true;
            }

            directionalNode = directionalNode.getParent();
        }
        return false;
    }

    public MatrixLocation toMatrixLocation() {
        return new MatrixLocation(this.getX(), this.getY());
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }
}
