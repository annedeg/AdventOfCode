package helpers;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

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

    public Node(int x, int y, double g, double h, Node parent) {
        this.x = x;
        this.y = y;
        this.g = g;
        this.h = h;
        this.parent = parent;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Node node = (Node) o;
        return x == node.x && y == node.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    public Node getParent() {
        if(parent != null) {
            return this.parent;
        }
        return null;
    }

    public int amountOfParents() {
        int t = 0;
        Node current = this;
        while (current != null) {
            current = current.getParent();
            t++;
        }
        return t;
    }

    public String toDirectionString() {
        Node current = this;
        Node next = this.parent;
        StringBuilder start = new StringBuilder();
        while (next != null) {
            if (current.getX() > next.getX()) {
                start.append(">");
            }
            if (current.getX() < next.getX()) {
                start.append("<");
            }
            if (current.getY() > next.getY()) {
                start.append("v");
            }
            if (current.getY() < next.getY()) {
                start.append("^");
            }

            current = next;
            next = next.getParent();
        }
        return start.reverse() + "A";
    }

    @Override
    public String toString() {
        return "Node{" +
                ", x=" + x +
                ", y=" + y +
                ", v=" + val +
                '}';
    }


    public Node deepClone() {
        if (this.parent != null) {
            return new Node(this.getX(), this.getY(), this.getG(), this.getH(), this.getParent().deepClone());
        }
        return new Node(this.getX(), this.getY(), this.getG(), this.getH());
    }

    public void copyFrom(Node savedState) {
        this.x = savedState.getX();
        this.y = savedState.getY();
        this.g = savedState.getG();
        this.h = savedState.getH();
        this.parent = savedState.getParent();
    }

    public boolean hasDuplicateParents() {
        ArrayList<Node> all = new ArrayList<>();

        Node curr = this;
        all.add(this);
        while (curr.getParent() != null) {
            all.add(curr.getParent());
            curr = curr.getParent();
        }

        Set<Node> d = new HashSet<>(all);
        return d.size() != all.size();
    }
}
