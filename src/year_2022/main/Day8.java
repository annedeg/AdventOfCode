package year_2022.main;

import helpers.CodeDay;
import helpers.Helper;

import java.util.ArrayList;
import java.util.Arrays;

public class Day8 extends CodeDay {
    @Override
    public void puzzleOne() {
        var input = Helper.readToStringArrayList(2022, 8);
        int[][] map = toMap(input);
        int total = 0;
        for (int x = 0; x < map.length; x++) {
            for (int y = 0; y < map[x].length; y++) {
                if (y == 0 || x == 0 || y == map.length - 1 || x == map[x].length - 1) {
                    total += 1;
                    continue;
                }
                System.out.println(x + " " + y + " value: " + map[x][y]);
                if (checkRow(x, y, Direction.LEFT, map) || checkRow(x, y, Direction.RIGHT, map) ||
                        checkRow(x, y, Direction.UP, map) || checkRow(x, y, Direction.DOWN, map)) {
                    total += 1;
                }
            }
        }
        System.out.println(total);
    }

    enum Direction {UP, DOWN, LEFT, RIGHT}

    public boolean checkRow(int x, int y, Direction direction, int[][] map) {
        var val = map[x][y];
        switch (direction) {
            case UP -> {
                for (int yy = y - 1; yy >= 0; yy--) {
                    if (((map[x][yy]) >= val)) {
                        return false;
                    }
                }
                System.out.println("trueup");
                System.out.println("value: " + map[x][y]);
                System.out.println();
                return true;
            }
            case DOWN -> {
                for (int yy = y + 1; yy <= map.length - 1; yy++) {
                    if (((map[x][yy]) >= val)) {
                        return false;
                    }
                }
                System.out.println("truedown");
                System.out.println("value: " + map[x][y]);
                System.out.println();
                return true;
            }
            case LEFT -> {
                for (int xx = x - 1; xx >= 0; xx--) {
                    if (((map[xx][y]) >= val)) {
                        return false;
                    }
                }
                System.out.println("trueleft");
                System.out.println("value: " + map[x][y]);
                System.out.println();
                return true;
            }
            case RIGHT -> {
                for (int xx = x + 1; xx <= map.length - 1; xx++) {
                    if (((map[xx][y]) >= val)) {
                        return false;
                    }
                }
                System.out.println("trueright");
                System.out.println("value: " + map[x][y]);
                System.out.println();
                return true;
            }
        }
        return false;
    }


    public int[][] toMap(ArrayList<String> input) {

        int[][] map = new int[input.size()][input.size()];

        var xc = 0;
        for (var row : input) {
            var yc = 0;
            for (char cr : row.toCharArray()) {
                map[yc][xc] = Integer.parseInt(String.valueOf(cr));
                yc++;
            }
            xc++;
        }

        return map;
    }

    @Override
    public void puzzleTwo() {
        var input = Helper.readToStringArrayList(2022, 8);
        int[][] map = toMap(input);
        int total = 0;
        for (int x = 0; x < map.length; x++) {
            for (int y = 0; y < map[x].length; y++) {
                System.out.println(x + " " + y + " value: " + checkRowValue(x,y,map));
                if (checkRowValue(x, y, map) > total) {
                    total = checkRowValue(x, y, map);
                }
            }
        }
        System.out.println(total);
    }

    public int checkRowValue(int x, int y, int[][] map) {
        var dist = 0;
        var tempdist = 0;
        var values = new int[4];
        var val = map[x][y];

        for (int yy = y - 1; yy >= 0; yy--) {
            if (((map[x][yy]) >= val)) {
                tempdist+=1;
                break;
            }
            tempdist += 1;
        }
        values[0] = tempdist;
        tempdist = 0;

        for (int yy = y + 1; yy <= map.length - 1; yy++) {
            if (((map[x][yy]) >= val)) {
                tempdist+=1;
                break;
            }
            tempdist += 1;
        }

        values[1] = tempdist;
        tempdist = 0;

        for (int xx = x - 1; xx >= 0; xx--) {
            if (((map[xx][y]) >= val)) {
                tempdist+=1;
                break;
            }
            tempdist += 1;
        }

        values[2] = tempdist;
        tempdist = 0;

        for (int xx = x + 1; xx <= map.length - 1; xx++) {
            if (((map[xx][y]) >= val)) {
                tempdist+=1;
                break;
            }
            tempdist += 1;
        }

        values[3] = tempdist;


//        System.out.println(Arrays.stream(values).reduce(1, (a,b) -> a*b));
        return Arrays.stream(values).reduce(1, (a,b) -> a*b);
    }
}
