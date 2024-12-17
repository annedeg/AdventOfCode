package year_2024.main;

import helpers.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day16 {
    DirectionalNode startNode;
    Node endNode;

    public void puzzleOne() {
        char[][] map = Helper.toMatrix(2024, 16);
        MatrixLocation start = new MatrixLocation(1, map.length - 2, Direction.RIGHT);
        MatrixLocation end = new MatrixLocation(map[0].length - 2, 1);

        ArrayList<MatrixLocation> history = new ArrayList<>();
//        int shortestPath = getShortestPath(start, end, map, 0, history, false);
        startNode = new DirectionalNode(1, map.length - 1, 0, 0, Direction.RIGHT);
        endNode = new Node(end.getX(), end.getY(), 0, 0);
        DirectionalNode shortestPath = getShortestPath(map, startNode);
        System.out.println(shortestPath.getG());
    }

//    public int getShortestPath(MatrixLocation curr, MatrixLocation end, char[][] map, int val, ArrayList<MatrixLocation> history, boolean hasRotated) {
//        if (end.getX() == curr.getX() && end.getY() == curr.getY()) {
//            return val;
//        }
//
//        if (curr.getValue(map) == '#') {
//            return val;
//        }
//
//        if ((history.contains(curr))) {
//            return val;
//        }
//
//
//        history.add(curr);
//
//        printMap(map, history);
//
//        Direction currDirection = curr.getDirection();
//        ArrayList<MatrixLocation> matrixLocations = Stream.of(currDirection.stepForward(curr), new MatrixLocation(curr.getX(), curr.getY(), currDirection.rotateLeft()), new MatrixLocation(curr.getX(), curr.getY(), currDirection.rotateRight())).collect(Collectors.toCollection(ArrayList::new));
//        ArrayList<Integer> integers = Stream.of(1, 1000, 1000).collect(Collectors.toCollection(ArrayList::new));
//
//        ArrayList<MatrixLocation> historyClone = new ArrayList<>(history);
//        for (int i = 0; i < matrixLocations.size(); i++) {
//            if (hasRotated && i > 1) {
//                continue;
//            }
//            getShortestPath(matrixLocations.get(i), end, map, val += integers.get(i), historyClone, i != 0);
//        }
//
//        return val;
//    }

    private DirectionalNode getShortestPath(char[][] map, DirectionalNode startNode) {
        ArrayList<DirectionalNode> openList = new ArrayList<>();
        ArrayList<DirectionalNode> closedList = new ArrayList<>();
        openList.add(startNode);

        while (!openList.isEmpty()) {
            DirectionalNode q = openList.remove(getLowestNodeInList(openList));
            ArrayList<DirectionalNode> successors = generateSuccessors(q, map);
            for (DirectionalNode successor : successors) {
                if (successor.getX() == endNode.getX() && successor.getY() == endNode.getY()) {
                    return successor;
                }

                int index = inListIndex(openList, successor);
                if (index != -1) {
                    if (openList.get(index).getF() <= successor.getF())
                        continue;
                }

                index = inListIndex(closedList, successor);
                if (index == -1) {
                    openList.add(successor);
                }
            }
            closedList.add(q);
        }

        return null;
    }

    private void printMap(char[][] map, DirectionalNode q) {
        ArrayList<MatrixLocation> matrixLocations = new ArrayList<>();
        while (q.getParent() != null) {
            matrixLocations.add(q.toMatrixLocation());
            q = q.getParent();
        }

        printMap(map, matrixLocations);
    }

    private int inListIndex(ArrayList<DirectionalNode> listItems, DirectionalNode node) {
        int counter = 0;
        for (DirectionalNode item : listItems) {
            if (item.getX() == node.getX() && item.getY() == node.getY() && item.getDirection() == node.getDirection()) {
                return counter;
            }
            counter++;
        }
        return -1;
    }

    private ArrayList<DirectionalNode> generateSuccessors(DirectionalNode q, char[][] map) {
        Direction currDirection = q.getDirection();

        MatrixLocation curLoc = new MatrixLocation(q.getX(), q.getY());
        double currG = generateCurrG(q);

        Direction db = currDirection.rotateLeft().rotateLeft();
        MatrixLocation back = db.stepForward(curLoc);
        DirectionalNode b = new DirectionalNode(back.getX(), back.getY(), currG + 2001, 0, db);

        Direction dl = currDirection.rotateLeft();
        MatrixLocation left = dl.stepForward(curLoc);
        DirectionalNode l = new DirectionalNode(left.getX(), left.getY(), currG + 1001, 0, dl);

        Direction dr = currDirection.rotateRight();
        MatrixLocation right = dr.stepForward(curLoc);
        DirectionalNode r = new DirectionalNode(right.getX(), right.getY(), currG + 1001, 0, dr);

        Direction da = currDirection;
        MatrixLocation ahead = da.stepForward(curLoc);
        DirectionalNode a = new DirectionalNode(ahead.getX(), ahead.getY(), currG + 1, 0, da);

        ArrayList<DirectionalNode> collect = Stream.of(l, r, a)
                .filter(directionalNode -> map[directionalNode.getY()][directionalNode.getX()] != '#')
                .filter(directionalNode -> !q.anyParentHasLocation(directionalNode))
                .collect(Collectors.toCollection(ArrayList::new));
        collect.forEach(directionalNode -> directionalNode.setParent(q));
        return collect;
    }

    private double generateCurrG(Node curr) {
        return curr.getG();
    }

    private double generateH(MatrixLocation matrixLocation) {
        return Math.abs(matrixLocation.getX() - endNode.getX()) + Math.abs(matrixLocation.getY() - endNode.getY());
    }

    private int getLowestNodeInList(ArrayList<DirectionalNode> list) {
        int counter = 0;
        int index = -1;
        double lowestF = Double.MAX_VALUE;
        for (Node listItem : list) {
            if (listItem.getF() < lowestF) {
                lowestF = listItem.getF();
                index = counter;
            }
            counter++;
        }
        return index;
    }

    private int getLowestGNodeInList(ArrayList<DirectionalNode> list) {
        int counter = 0;
        int index = -1;
        double lowestF = Double.MAX_VALUE;
        for (Node listItem : list) {
            if (listItem.getG() < lowestF) {
                lowestF = listItem.getG();
                index = counter;
            }
            counter++;
        }
        return index;
    }


    private static void printMap(char[][] map, ArrayList<MatrixLocation> matrixLocations) {
        ArrayList<SimpleMatrixLocation> simpleMatrixLocations = matrixLocations.stream().map(SimpleMatrixLocation::new).collect(Collectors.toCollection(ArrayList::new));
        for (int y = 0; y < map.length; y++) {
            for (int x = 0; x < map[y].length; x++) {
                if (simpleMatrixLocations.contains(new SimpleMatrixLocation(x, y))) {
                    System.out.print('O');
                } else {
                    System.out.print(map[y][x]);
                }
            }
            System.out.println();
        }
        System.out.println();
    }

    public void puzzleTwo() {
        char[][] map = Helper.toMatrix(2024, 16);
        MatrixLocation start = new MatrixLocation(1, map.length - 2, Direction.RIGHT);
        MatrixLocation end = new MatrixLocation(map[0].length - 2, 1);

        ArrayList<MatrixLocation> history = new ArrayList<>();
//        int shortestPath = getShortestPath(start, end, map, 0, history, false);
        startNode = new DirectionalNode(1, map.length - 2, 0, 0, Direction.RIGHT);
        endNode = new Node(end.getX(), end.getY(), 0, 0);
        ArrayList<DirectionalNode> shortestPaths = getShortestPathP3(map, startNode);

        char[][] mapClone = map.clone();
        for (DirectionalNode directionalNode : shortestPaths) {
            while (directionalNode.getParent() != null) {
                map[directionalNode.getY()][directionalNode.getX()] = 'O';
                directionalNode = directionalNode.getParent();
            }
        }

        int total = 0;
        for (char[] chars : mapClone) {
            for (char ch : chars) {
                if (ch == 'O' || ch == 'S' || ch == 'E') {
                    total += 1;
                }
            }
        }
        System.out.println(total);

    }

    private ArrayList<DirectionalNode> getShortestPathP2(char[][] map, DirectionalNode startNode) {
        ArrayList<DirectionalNode> openList = new ArrayList<>();
        ArrayList<DirectionalNode> closedList = new ArrayList<>();
        openList.add(startNode);

        ArrayList<DirectionalNode> shortest = new ArrayList<>();
        double shorte = -1;

        while (!openList.isEmpty()) {
            DirectionalNode q = openList.remove(getLowestGNodeInList(openList));
            ArrayList<DirectionalNode> successors = generateSuccessors(q, map);
            printMap(map, q);
            for (DirectionalNode successor : successors) {
                if (successor.getX() == endNode.getX() && successor.getY() == endNode.getY() && shorte == -1) {
                    shorte = successor.getG();
                    shortest.add(successor);
                }

                if (shorte != -1 && successor.getX() == endNode.getX() && successor.getY() == endNode.getY() && shorte == successor.getG()) {
                    shortest.add(successor);
                }

                if (successor.getG() > shorte && shorte > -1) {
                    return shortest;
                }

                int index = inListIndex(openList, successor);
                if (index != -1) {
                    if (openList.get(index).getG() < successor.getG()) {
                        System.out.println("skipped");
                        continue;
                    }
                }

                index = inListIndex(closedList, successor);
                if (index == -1) {
                    openList.add(successor);
                }

            }

            closedList.add(q);
        }

        return shortest;
    }

    private ArrayList<DirectionalNode> getShortestPathP3(char[][] map, DirectionalNode startNode) {
        ArrayList<DirectionalNode> unvisited = Helper.allLocations(map).stream()
                .map(location -> new DirectionalNode(location.getX(), location.getY(), Integer.MAX_VALUE, 0, null))
                .collect(Collectors.toCollection(ArrayList::new));

        HashMap<DirectionalNode, Double> costOfNodes = new HashMap<>();

        unvisited.stream()
                .filter(unvisitedNode -> unvisitedNode.getX() == startNode.getX() || unvisitedNode.getY() == startNode.getY())
                .findFirst()
                .ifPresent(node -> {
                    node.setG(0);
                    node.setDirection(Direction.RIGHT);
                });

        Optional<DirectionalNode> lowestGNodeInList = getLowestNodeByGInList(unvisited);
        if (lowestGNodeInList.isEmpty()) {
            //skip to 6
            return null;
        }

        DirectionalNode node = lowestGNodeInList.get();
        ArrayList<DirectionalNode> directionalNodes = generateSuccessors(node, map);
        for (DirectionalNode directionalNode : directionalNodes) {
            if (costOfNodes.containsKey(directionalNode)) {
                if (costOfNodes.get(directionalNode) > directionalNode.getG())
            }
            costOfNodes.put(directionalNode, directionalNode.getG());
        }

    }

    private Optional<DirectionalNode> getLowestNodeByGInList(ArrayList<DirectionalNode> list) {
        int counter = 0;
        int index = -1;
        double lowestF = Double.MAX_VALUE;
        for (Node listItem : list) {
            if (listItem.getG() < lowestF) {
                lowestF = listItem.getG();
                index = counter;
            }
            counter++;
        }

        if (index == -1) {
            return Optional.empty();
        }

        return Optional.ofNullable(list.get(index));
    }

    public static void main(String[] args) {
        Day16 day = new Day16();
//        day.puzzleOne();
        day.puzzleTwo();
    }
}
