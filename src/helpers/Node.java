package helpers;

public class Node {
    double g, h;
    int x, y;
    Node parent = null;
    char val;
    public Node(int x, int y, double g, double h) {
        this.x = x;
        this.y = y;
        this.g = g;
        this.h = h;
    }


    public void setVal(char val) {
        this.val = val;
    }

    public double getG() {
        return g;
    }

    public void setG(double g) {
        this.g = g;
    }

    public double getH() {
        return h;
    }

    public void setH(double h) {
        this.h = h;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public double getF() {
        return this.g + this.h;
    }

    public void setParent(Node node) {
        parent = node;
    }

    public Node getParent() {
        if(parent != null) {
            return this.parent;
        }
        return null;
    }

    @Override
    public String toString() {
        return "Node{" +
                ", x=" + x +
                ", y=" + y +
                ", v=" + val +
                '}';
    }
}
