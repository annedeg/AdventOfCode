package year_2022.main;

import helpers.*;

import java.util.ArrayList;
import java.util.List;

public class Day12 extends CodeDay {

    @Override
    public void puzzleOne() {
        var input = Helper.readToStringArrayList(2022, 12);

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

        for(var z : getValid(new ArrayList<>(List.of('S')), map)) {
            Node path = controller.getShortestPath(z);
            ArrayList<Node> paths = new ArrayList<>();
            paths.add(path);
            int amountCounted = 0;
            while ((path = path.getParent()) != null) {
                paths.add(path);
            }

            for (int i = paths.size()-1;i>=0;i--) {
                System.out.println(paths.get(i));
            }
            System.out.println(paths.size()-1);
        }
    }

    public ArrayList<Node> getValid(ArrayList<Character> seek, char[][] input) {
        ArrayList<Node> valid = new ArrayList<>();

        int x = 0;
        for (var row : input) {
            int y = 0;
            for (var value : row) {
                if (seek.contains(value))
                    valid.add(new Node(x, y, 0, 0));
                y++;
            }
            x++;
        }

        return valid;
    }

    @Override
    public void puzzleTwo() {
        var input = Helper.readToStringArrayList(2022, 12);

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

        int shortest = Integer.MAX_VALUE;

        for(var z : getValid(new ArrayList<>(List.of('a','S')), map)) {
            Node path = controller.getShortestPath(z);
            if (path == null)
                continue;
            ArrayList<Node> paths = new ArrayList<>();
            paths.add(path);

            while ((path = path.getParent()) != null) {
                paths.add(path);
            }

            if (shortest > paths.size()-1) {
                shortest = paths.size()-1;
            }
        }

        System.out.println(shortest);
    }
}
