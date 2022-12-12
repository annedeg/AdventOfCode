import helpers.*;

public class Day12 extends CodeDay {

    @Override
    public void puzzleOne() {
        var input = Helper.readToStringArrayList("src/input/day12");

        char[][] map = new char[input.size()][input.get(0).length()];
        int x = 0;
        for (var row : input) {
            int y = 0;
            for (var item : row.toCharArray()) {
                map[x][y] = item;
                y += 1;
            }
            x += 1;
        }

        for (var row:map) {
            for (var item : row) {
                System.out.print(item);
            }
            System.out.println("");
        }


        MapView view = new MapView();
        MapModel model = new MapModel(view, map);
        AStar controller = new AStar(model, view);
        view.setController(controller);
        controller.start();

        Node path = controller.getShortestPath();

        int amountCounted = 0;
        while ((path = path.getParent()) != null) {
            System.out.println(path.getX() + " " + path.getY());
        }

        System.out.println(amountCounted);

    }


    @Override
    public void puzzleTwo() {

    }
}
