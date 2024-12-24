package helpers;

import java.util.HashSet;
import java.util.Set;

public class Graph {
    private Set<GraphNode> nodes = new HashSet<>();

    void addNode(GraphNode node) {
        nodes.add(node);
    }

    public Set<GraphNode> getNodes() {
        return nodes;
    }

    public void setNodes(Set<GraphNode> nodes) {
        this.nodes = nodes;
    }
}