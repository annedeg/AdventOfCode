package main.helpers;


import java.util.ArrayList;

public class AStar extends AIController {
    private char[][] graph;
    private MapModel model;
    private MapView view;
    private Node startNode;
    private Node endNode;

    public AStar(MapModel model, MapView view) {
        this.model = model;
        this.view = view;
        this.graph = model.getMap();
        for (int x = 0; x < this.graph.length; x++) {
            for (int y = 0; y < this.graph[x].length; y++) {
                if (this.graph[x][y] == MapSettings.START) {
                    startNode = new Node(x, y, 0, 0);
                }
                if (this.graph[x][y] == MapSettings.END) {
                    endNode = new Node(x, y, 0, 0);
                }
            }
        }
    }

    @Override
    public void start() {
//        Node result = getShortestPath();
    }

    @Override
    public Node getShortestPath(Node startNode) {
        this.startNode = startNode;
        ArrayList<Node> openList = new ArrayList<Node>();
        ArrayList<Node> closedList = new ArrayList<Node>();
        openList.add(this.startNode);

        while (openList.size() != 0) {
            Node q = openList.remove(getLowestNodeInList(openList));
            ArrayList<Node> successors = generateSuccessors(q);
            for (Node successor:successors) {
                if(this.graph[successor.getX()][successor.getY()] == MapSettings.END) {
                    return successor;
                }
                int index = inListIndex(openList, successor);
                if(index != -1) {
                    if(openList.get(index).getF() <= successor.getF())
                        continue;
                }

                index = inListIndex(closedList, successor);
                if(index == -1) {
                    openList.add(successor);
                }
            }
            closedList.add(q);
        }

        return null;
    }

    @Override
    public void notify(String type, double xLocation, double yLocation) {

    }

    private int getLowestNodeInList(ArrayList<Node> list) {
        int counter = 0;
        int index = -1;
        double lowestF = Double.MAX_VALUE;
        for(Node listItem:list) {
            if(listItem.getF() < lowestF) {
                lowestF = listItem.getF();
                index = counter;
            }
            counter++;
        }
        return index;
    }

    private ArrayList<Node> generateSuccessors(Node middleNode) {
        ArrayList<Node> successors = new ArrayList<>();
        int counter = 0;

        for(int x = (middleNode.getX() -1); x < middleNode.getX()+2; x++) {
            for(int y = (middleNode.getY() -1); y < middleNode.getY()+2; y++) {
                if(isUsable(x,y) && counter != 4 && counter != 0 && counter != 2 && counter != 6 && counter != 8 && notHigh(x,y,middleNode)) {
                    if (x == middleNode.getX() && y == middleNode.getY())
                        continue;
                    double h = generateH(x, y);
                    double g = generateCurrG(middleNode) + 1;
                    Node node = new Node(x,y,g,h);
                    node.setVal(this.graph[x][y]);
                    node.setParent(middleNode);
                    successors.add(node);
                }
                counter++;
            }
        }
        return successors;
    }

    private boolean notHigh(int x,int y, Node middleNode) {
        var middelVal = this.graph[middleNode.getX()][middleNode.getY()];
        var newVal = this.graph[x][y];
        if (middelVal == 'S') {
            middelVal = 'a'-1;
        } else if (middelVal == 'E') {
            middelVal = 'z'+1;
        }

        if (newVal == 'S') {
            newVal = 'a'-1;
        } else if (newVal == 'E') {
            newVal = 'z'+1;
        }

        var steepness = newVal - middelVal;

        return (steepness) == 1 || (steepness) == 0 || (steepness) < 0;
    }

    private double generateH(int currX, int currY) {
        return Math.abs(currX-this.endNode.getX()) + Math.abs(currY-this.endNode.getY());
    }

    private double generateCurrG(Node curr) {
        double total = 0;
        while (curr.getParent() != null) {
            total += curr.getG();
            curr = curr.getParent();
        }
        return total;
    }

    private boolean isUsable(int x, int y) {
        return x >= 0 && y >= 0 && x < this.graph.length && y < this.graph[x].length && this.graph[x][y] != MapSettings.START;
    }

    private int inListIndex(ArrayList<Node> listItems, Node node) {
        int counter = 0;
        for(Node item:listItems) {
            if(item.getX() == node.getX() && item.getY() == node.getY()) {
                return counter;
            }
            counter++;
        }
        return -1;
    }
}