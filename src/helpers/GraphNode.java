package helpers;

import java.util.*;

public class GraphNode {
    String values = "0123456789abcdefghijk";
    private static final HashMap<MatrixLocation, HashMap<GraphNode, Integer>> nodesCreated = new HashMap<>();
    private final char[][] map;
    private List<GraphNode> shortestPath = new LinkedList<>();
    private final MatrixLocation matrixLocation;
    private int distance = Integer.MAX_VALUE;

    Map<GraphNode, Integer> adjacentNodes;

    public GraphNode(char[][] map, MatrixLocation neighbour) {
        this.matrixLocation = neighbour;
        this.map = map;
    }

    public Map<GraphNode, Integer> getAdjacentNodes() {
        if (adjacentNodes != null) {
            return adjacentNodes;
        }

        setNeighbours();
        return adjacentNodes;
    }

    public void addDestination(GraphNode dest, int dist) {
        adjacentNodes.put(dest, dist);
    }

    public List<GraphNode> getShortestPath() {
        return shortestPath;
    }

    public void setShortestPath(List<GraphNode> shortestPath) {
        this.shortestPath = shortestPath;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    private void setNeighbours() {
        if (nodesCreated.containsKey(this.matrixLocation)) {
            adjacentNodes = nodesCreated.get(this.matrixLocation);
            return;
        }

        HashMap<GraphNode, Integer> neighbours = new HashMap<>();
        nodesCreated.put(this.matrixLocation, neighbours);

        Helper.surroundingTiles(map, matrixLocation,false).stream()
                .filter(neighbour -> neighbour.getValue(map) != '#')
                .filter(neighbour -> {
                    String neighbourValue = String.valueOf(neighbour.getValue(map));
                    String currentValue = String.valueOf(matrixLocation.getValue(map));

                    if (values.contains(neighbourValue) && !values.contains(currentValue)) {
                        if (values.indexOf(neighbourValue) > 1) {
                            return false;
                        }
                    }

                    if (values.contains(neighbourValue) && values.contains(currentValue)) {
                        return values.indexOf(neighbourValue) > values.indexOf(currentValue);
                    }
                    return true;
                })
                .filter(neighbour -> !neighbour.locationEquals(this.matrixLocation))
                .map(neighbour -> new GraphNode(map, neighbour))
                .forEach(gn -> neighbours.put(gn,1));

        adjacentNodes = neighbours;
        nodesCreated.replace(this.matrixLocation, neighbours);
    }

    public MatrixLocation getMatrixLocation() {
        return matrixLocation;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GraphNode graphNode = (GraphNode) o;
        return distance == graphNode.distance && Objects.equals(matrixLocation, graphNode.matrixLocation);
    }

    @Override
    public int hashCode() {
        return Objects.hash(matrixLocation, distance);
    }

    @Override
    public String toString() {
        return "GraphNode{"
                +"distance=" + distance +
                '}';
    }

    public char[][] getMap() {
        return map;
    }

    public static void clearSavedNodes() {
        GraphNode.nodesCreated.clear();
    }
}
