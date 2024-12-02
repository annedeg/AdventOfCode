package main.helpers;

public abstract class AIController extends Controller {
    abstract public Node getShortestPath(Node startNode);
    abstract public void notify(String type, double xLocation, double yLocation);
}
