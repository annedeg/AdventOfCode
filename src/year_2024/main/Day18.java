package year_2024.main;

import helpers.Helper;
import helpers.MatrixLocation;
import helpers.Node;
import helpers.SimpleMatrixLocation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

public class Day18 {
    public void puzzleOne() {
        char[][] map = new char[71][71];
        for (char[] chars : map) {
            Arrays.fill(chars, '.');
        }
        ArrayList<String> strings = Helper.readToStringArrayList(2024, 18);
        ArrayList<SimpleMatrixLocation> matrixLocations = strings.stream().map(str -> str.split(",")).map(strarr -> new SimpleMatrixLocation(Integer.parseInt(strarr[0]), Integer.parseInt(strarr[1]))).collect(Collectors.toCollection(ArrayList::new));
        int c = 1;
        int goalBytes = 1024;
        Node startNode = new Node(0, 0, 0, 0);
        Node endNode = new Node(70, 70, 0, 0);
        for (SimpleMatrixLocation matrixLocation : matrixLocations) {
//                map = simSec(map);
                map[matrixLocation.getY()][matrixLocation.getX()] = '#';
                if (c == goalBytes) {
                    Node node = shortestPath(map, startNode, endNode);
                    System.out.println(node.getF());
                    break;
                }
                c++;
        }
        printMap(map);
    }

    private Node shortestPath(char[][] map, Node startNode, Node endNode) {
        ArrayList<Node> openList = new ArrayList<>();
        ArrayList<Node> closedList = new ArrayList<>();

        openList.add(startNode);

        while (!openList.isEmpty()) {
            Node q = openList.remove(getLowestValueIndex(openList));
            for (Node succesor : generateSuccessors(q, map, endNode)) {
                if (succesor.equals(endNode)) {
                    return succesor;
                }

                if (openList.contains(succesor) && openList.get(openList.indexOf(succesor)).getF() <= succesor.getF()) {
                    continue;
                }

                if (!closedList.contains(succesor)) {
                    openList.add(succesor);
                }
            }

            closedList.add(q);
        }

        return null;
    }

    private ArrayList<Node> generateSuccessors(Node q, char[][] map, Node endNode) {
        ArrayList<MatrixLocation> matrixLocations = Helper.surroundingTiles(map, new MatrixLocation(q.getX(), q.getY()), false);
        ArrayList<Node> nodes = matrixLocations.stream()
                .filter(ml -> ml.getValue(map) != '#')
                .map(ml -> new Node(ml.getX(), ml.getY(), q.getG() + 1, getH(ml, endNode)))
                .collect(Collectors.toCollection(ArrayList::new));
        nodes.forEach(ml -> ml.setParent(q));
        return nodes;

    }

    private double getH(MatrixLocation m, Node endNode) {
        return Math.abs(m.getX()-endNode.getX()) + Math.abs(m.getY() - endNode.getY());
    }

    private int getLowestValueIndex(ArrayList<Node> openList) {
        double f = Double.MAX_VALUE;
        int index = -1;

        int c = 0;
        for (Node node : openList) {
            if (node.getF() < f) {
                f = node.getF();
                index = c;
            }
            c++;
        }
        return index;
    }



    private void printMap(char[][] map) {
        for (char[] chars : map) {
            for (char aChar : chars) {
                System.out.print(aChar);
            }
            System.out.println();
        }
        System.out.println();
    }

//    public char[][] simSec(char [][] map) {
//        for (int y = map.length-1; y > 0; y--) {
//            for (int x = 0; x < map[y].length; x++) {
//                if (map[y][x] == '#') {
//                    //move
//                    map[y][x] = '.';
//                    if (!(y+1 >= map.length)) {
//                        map[y+1][x] = '#';
//                    }
//                }
//            }
//        }
//        return map;
//    }

    public void puzzleTwo() {
        char[][] map = new char[71][71];
        for (char[] chars : map) {
            Arrays.fill(chars, '.');
        }
        ArrayList<String> strings = Helper.readToStringArrayList(2024, 18);
        ArrayList<SimpleMatrixLocation> matrixLocations = strings.stream().map(str -> str.split(",")).map(strarr -> new SimpleMatrixLocation(Integer.parseInt(strarr[0]), Integer.parseInt(strarr[1]))).collect(Collectors.toCollection(ArrayList::new));
        int c = 1;
        Node startNode = new Node(0, 0, 0, 0);
        Node endNode = new Node(70, 70, 0, 0);
        for (SimpleMatrixLocation matrixLocation : matrixLocations) {
            map[matrixLocation.getY()][matrixLocation.getX()] = '#';

            if (c > 1024) {
                Node node = shortestPath(map, startNode, endNode);
                if (node == null) {
                    System.out.println(matrixLocations.get(c - 1).getX() + "," + matrixLocations.get(c - 1).getY());
                    break;
                }
            }

            c++;
        }
    }

    public static void main(String[] args) {
        Day18 day = new Day18();
        day.puzzleOne();
        day.puzzleTwo();
    }
}
