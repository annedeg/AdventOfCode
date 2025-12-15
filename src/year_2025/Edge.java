package year_2025;

import helpers.MatrixLocation;

import java.util.Objects;

public class Edge {
    MatrixLocation node1;
    MatrixLocation node2;

    public Edge(MatrixLocation node1, MatrixLocation node2) {
        this.node1 = node1;
        this.node2 = node2;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Edge edge = (Edge) o;
        return Objects.equals(node1, edge.node1) && Objects.equals(node2, edge.node2);
    }

    @Override
    public int hashCode() {
        return Objects.hash(node1, node2);
    }
}
