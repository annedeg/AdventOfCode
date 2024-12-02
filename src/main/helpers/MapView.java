package main.helpers;

public class MapView extends View {
    private AIController controller;
    public MapView() {
    }

    public void draw(int[][] map) {

    }

    public void notifyController(String type, double xLocation, double yLocation) {
        controller.notify(type, xLocation, yLocation);
    }

    @Override
    public void setController(Object controller) {
        this.controller = (AIController) controller;
    }
}
