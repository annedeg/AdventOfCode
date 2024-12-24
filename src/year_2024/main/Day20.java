package year_2024.main;

import helpers.Graph;
import helpers.GraphNode;
import helpers.Helper;
import helpers.MatrixLocation;

import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import static helpers.Helper.printMap;

public class Day20 {
    String values = "0123456789abcdefghijk";

    public void puzzleOne() {
        char[][] matrix = Helper.toMatrix(2024, 20);
        MatrixLocation start = Helper.findFirstCharInMap(matrix, 'S');
        MatrixLocation end = Helper.findFirstCharInMap(matrix, 'E');
        GraphNode graphNode = new GraphNode(matrix, start);
        Graph graph = shortestPath(graphNode);
        Optional<GraphNode> e = graph.getNodes().stream()
                .filter(gn -> gn.getMatrixLocation().locationEquals(end))
                .findFirst();

        if (!e.isPresent()) {
            System.out.println("geen oplossing :(");
        }

        int defaultShortest = e.get().getDistance();
        System.out.println("normaal kortste: " + defaultShortest);


        ArrayList<MatrixLocation> cheatOptions = Helper.allLocations(matrix).stream()
                .filter(matrixLocation -> matrixLocation.getValue(matrix) == '#')
                .collect(Collectors.toCollection(ArrayList::new));

        AtomicInteger c = new AtomicInteger();
        for (int i = 0; i < cheatOptions.size(); i++) {
            MatrixLocation cheat = cheatOptions.get(i);
            char[][] matrixC = Helper.deepCopy(matrix);
            matrixC[cheat.getY()][cheat.getX()] = '.';
            GraphNode.clearSavedNodes();
            graphNode = new GraphNode(matrixC, start);

            int amountValid = graphNode.getAdjacentNodes().size();

            graph = shortestPath(graphNode);
            List<GraphNode> e1 = graph.getNodes().stream().filter(gn -> gn.getMatrixLocation().locationEquals(end)).sorted(Comparator.comparing(GraphNode::getDistance)).toList();
            e1.stream().findFirst().ifPresent(node -> {
                        int diff = defaultShortest - node.getDistance();
                        if (diff >= 100) c.addAndGet(amountValid);
                    }
            );
            System.out.println(i + "/" + cheatOptions.size());
        }
        System.out.println(c.get());
    }

    public Graph shortestPath(GraphNode start) {
        Graph graph = new Graph();
        start.setDistance(0);

        Set<MatrixLocation> settled = new HashSet<>();
        Set<GraphNode> settledFinal = new HashSet<>();
        Set<GraphNode> unsettled = new HashSet<>();

        unsettled.add(start);

        while (!unsettled.isEmpty()) {
            GraphNode curr = getLowestDistanceNode(unsettled);
            unsettled.remove(curr);

            for (Map.Entry<GraphNode, Integer> successorPair : curr.getAdjacentNodes().entrySet()) {
                GraphNode successor = successorPair.getKey();
                int sucDis = successorPair.getValue();

                if (!settled.contains(successor.getMatrixLocation())) {
                    calcMinDist(successor, sucDis, curr);
                    unsettled.add(successor);
                }
            }

            settled.add(curr.getMatrixLocation());
            settledFinal.add(curr);
        }
        graph.setNodes(settledFinal);
        return graph;
    }

    private <T> void testTime(Callable<T> ttFunction, String name, long min) {
        long l = System.currentTimeMillis();
        try {
            ttFunction.call();
        } catch (Exception e) {

        }

        long r = System.currentTimeMillis() - l;
        if (r >= min) {
            System.out.println(r + "ms | " + name);
        }
    }

    private void calcMinDist(GraphNode successor, int sucDis, GraphNode current) {
        int distance = current.getDistance();
        if (distance + sucDis < successor.getDistance()) {
            successor.setDistance(distance + sucDis);
        }
    }

    private GraphNode getLowestDistanceNode(Set<GraphNode> unsettled) {
        GraphNode lowest = null;
        int low = Integer.MAX_VALUE;

        for (GraphNode node : unsettled) {
            if (node.getDistance() < low) {
                low = node.getDistance();
                lowest = node;
            }
        }

        return lowest;
    }

    public void puzzleTwo() {
        char[][] matrix = Helper.toMatrix(2024, 20);
        MatrixLocation start = Helper.findFirstCharInMap(matrix, 'S');
        MatrixLocation end = Helper.findFirstCharInMap(matrix, 'E');
        GraphNode graphNode = new GraphNode(matrix, start);
        Graph graph = shortestPath(graphNode);
        Optional<GraphNode> e = graph.getNodes().stream()
                .filter(gn -> gn.getMatrixLocation().locationEquals(end))
                .findFirst();

        if (!e.isPresent()) {
            System.out.println("geen oplossing :(");
        }

        int defaultShortest = e.get().getDistance();
        System.out.println("normaal kortste: " + defaultShortest);


        HashMap<MatrixLocation, ArrayList<MatrixLocation>> allCheatOptions = new HashMap<>();

        ArrayList<MatrixLocation> cheatOptions = Helper.allLocations(matrix).stream()
                .filter(matrixLocation -> matrixLocation.getValue(matrix) == '#')
                .collect(Collectors.toCollection(ArrayList::new));

        ArrayList<MatrixLocation> validResults = Helper.allLocations(matrix).stream()
                .filter(matrixLocation -> matrixLocation.getValue(matrix) != '#')
                .collect(Collectors.toCollection(ArrayList::new));

//        ArrayList<MatrixLocation> cheatOptions = Stream.of(
//                new MatrixLocation(1, 3)
//        ).collect(Collectors.toCollection(ArrayList::new));
//        ArrayList<MatrixLocation> validResults = Stream.of(
//                new MatrixLocation(5, 7)
//        ).collect(Collectors.toCollection(ArrayList::new));

        HashMap<Integer, Boolean> map = new HashMap<>();

        for (MatrixLocation l1 : cheatOptions) {
            for (MatrixLocation l2 : validResults) {
                if (length(l1,l2) > 0 && length(l1, l2) < 20) {
                    if (!allCheatOptions.containsKey(l1)) {
                        allCheatOptions.put(l1, new ArrayList<>());
                    }

                    allCheatOptions.get(l1).add(l2);
                }
            }
        }

        HashMap<Integer, Integer> am = new HashMap<>();

        AtomicInteger c = new AtomicInteger();
        int i = 0;
        for (Map.Entry<MatrixLocation, ArrayList<MatrixLocation>> entry : allCheatOptions.entrySet()) {
            MatrixLocation cs = entry.getKey();
            for (MatrixLocation ce : allCheatOptions.get(cs)) {
                GraphNode.clearSavedNodes();

                char[][] matrixC = Helper.deepCopy(matrix);

                int y1 = cs.getY();
                int y2 = ce.getY();
                int x1 = cs.getX();
                int x2 = ce.getX();
                int sy = Math.min(y1, y2);
                int sx = Math.min(x1, x2);
                int b = 0;
                int y;

                if (y1 == sy) {
                    for (y = y1; y < y2; y++) {
                        matrixC[y][cs.getX()] = values.charAt(b);
                        b++;
                    }
                } else {
                    for (y = y1; y > y2; y--) {
                        matrixC[y][cs.getX()] = values.charAt(b);
                        b++;
                    }
                }

                if (x1 == sx) {
                    for (int x = x1; x < x2; x++) {
                        matrixC[y][x] = values.charAt(b);
                        b++;
                    }
                } else {
                    for (int x = x1; x > x2; x--) {
                        matrixC[y][x] = values.charAt(b);
                        b++;
                    }
                }

//                printMap(matrixC);
                graphNode = new GraphNode(matrixC, start);
                graph = shortestPath(graphNode);
                List<GraphNode> e1 = graph.getNodes().stream().filter(gn -> gn.getMatrixLocation().locationEquals(cs)).sorted(Comparator.comparing(GraphNode::getDistance)).toList();

                if (e1.isEmpty()) {
                    continue;
                }
                GraphNode n = e1.get(e1.size() - 1);
                int fp = n.getDistance();
                graphNode = new GraphNode(matrixC, ce);
                graph = shortestPath(graphNode);
                e1 = graph.getNodes().stream().filter(gn -> gn.getMatrixLocation().locationEquals(end)).sorted(Comparator.comparing(GraphNode::getDistance)).toList();
                if (e1.isEmpty()) {
                    continue;
                }
                n = e1.get(e1.size() - 1);
                int sp = n.getDistance();

                int diff = defaultShortest - (fp + sp + (length(cs, ce)));

                if (diff == 76) {
                    System.out.println(cs);
                    System.out.println(ce);
                    System.out.println(diff);
                    printMap(matrixC);
                }
                if (am.containsKey(diff)) {
                    am.put(diff, am.get(diff) + 1);
                } else {
                    am.put(diff, 1);
                }
                if (diff >= 50) {
                    c.addAndGet(1);
                }
            }
//            i++;
//            System.out.println(i + "/" + allCheatOptions.size());
        }
        System.out.println(c.get());
    }

    public int length(MatrixLocation a, MatrixLocation b) {
        return Math.abs(a.getX() - b.getX()) + Math.abs(a.getY() - b.getY());
    }

    public static void main(String[] args) {
        Day20 day = new Day20();
//        day.puzzleOne();
        day.puzzleTwo();
    }
}
