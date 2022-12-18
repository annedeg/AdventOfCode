import helpers.Helper;

import java.util.Arrays;

public class Day14 extends CodeDay {
    @Override
    public void puzzleOne() {
        var input = Helper.readToStringArrayList("src/input/day14");
        var startx = 500;
        var starty = 0;

        int lowX = Integer.MAX_VALUE;
        int lowY = starty;
        int higX = startx;
        int higY = Integer.MIN_VALUE;

        for (var row : input) {
            var cols = row.split(" -> ");
            for (var col : cols) {
                var xy = col.split(",");
                var x = Integer.parseInt(xy[0]);
                var y = Integer.parseInt(xy[1]);
                if (x > higX) higX = x;
                if (y > higY) higY = y;
                if (x < lowX) lowX = x;
                if (y < lowY) lowY = y;
            }
        }

        int[][] filledMap = new int[higX+1][higY+1];
        int oldX = -1;
        int oldY = -1;

        for (var row : input) {
            var cols = row.split(" -> ");
            for (var col : cols) {
                var xy = col.split(",");
                var x = Integer.parseInt(xy[0]);
                var y = Integer.parseInt(xy[1]);
                filledMap[x][y] = Place.ROCK.num;
                if (oldX != -1) filledMap = fillLines(filledMap, oldX, oldY, x, y);
                oldX = x;
                oldY = y;
            }
            oldX = -1;
            oldY = -1;
        }
        printMap(filledMap, lowX, lowY, higX, higY);
        var old = -1;
        var o = -1;
        var c = 0;
        while (true) {
            filledMap[startx][starty] = Place.SAND.num;
            try {
                o = tick(filledMap, startx, starty, 0);
            } catch (ArrayIndexOutOfBoundsException e) {
                break;
            }
            if (o == old && o == 0) break;
            c+=1;
            old = o;

        }
        printMap(filledMap, lowX, lowY, higX, higY);
        System.out.println(c);
    }

    public int[][] fillLines(int[][] map, int x, int y, int x2, int y2) {
        if (Math.abs(x - x2) == 0) {
            int z = y2;
            if (y > y2) {y2 = y; y = z;}
            for (;y<=y2;y++) map[x][y] = Place.ROCK.num;
        } else {
            int z = x2;
            if (x > x2) {x2 = x; x = z;}
            for (;x<=x2;x++) map[x][y] = Place.ROCK.num;
        }
        return map;
    }

    private int tick(int[][] map, int startx, int starty, int c) throws ArrayIndexOutOfBoundsException {
        if (map[startx][starty+1] == Place.AIR.num) {
            map[startx][starty+1] = Place.SAND.num;
            map[startx][starty] = Place.AIR.num;
            c+=1;
            tick(map, startx, starty + 1, c);
        } else if (map[startx-1][starty+1] == Place.AIR.num) {
            map[startx-1][starty+1] = Place.SAND.num;
            map[startx][starty] = Place.AIR.num;
            c+=1;
            tick(map, startx - 1, starty + 1, c);
        } else if (map[startx+1][starty+1] == Place.AIR.num) {
            map[startx+1][starty+1] = Place.SAND.num;
            map[startx][starty] = Place.AIR.num;
            c+=1;
            tick(map, startx + 1, starty + 1, c);
        }

        return c;
    }

    public void printMap(int[][] map, int lowX, int lowY, int higX, int higY) {
        for (int y = lowY; y < higY; y++) {
            for (int x = lowX; x < higX; x++) {
                System.out.print(Place.getC(map[x][y]));
            }
            System.out.println();
        }
    }

    public int countMap(int[][] map, int lowX, int lowY, int higX, int higY) {
        var c = 0;
        for (int y = lowY; y <= higY; y++)
            for (int x = lowX; x <= higX; x++)
                if (map[x][y] == Place.SAND.num) c+=1;
        return c;
    }

    enum Place{
        ROCK(1, "#"), AIR(0, "."), SAND(2, "O");

        final int num;
        final String c;
        Place(int num, String c) {
            this.num = num;
            this.c = c;
        }

        public static String getC(int num) {
            return switch (num) {
                case 1 -> "#";
                case 2 -> "O";
                default -> ".";
            };
        }
    }

    @Override
    public void puzzleTwo() {
        var input = Helper.readToStringArrayList("src/input/day14");
        var startx = 500;
        var starty = 0;

        int lowX = Integer.MAX_VALUE;
        int lowY = starty;
        int higX = startx;
        int higY = Integer.MIN_VALUE;

        for (var row : input) {
            var cols = row.split(" -> ");
            for (var col : cols) {
                var xy = col.split(",");
                var x = Integer.parseInt(xy[0]);
                var y = Integer.parseInt(xy[1]);
                if (x > higX) higX = x;
                if (y > higY) higY = y;
                if (x < lowX) lowX = x;
                if (y < lowY) lowY = y;
            }
        }

        higY += 3;
        lowX -= (higY*2);
        higX += (higY*2);

        int[][] filledMap = new int[higX][higY];
        int oldX = -1;
        int oldY = -1;

        for (var row : input) {
            var cols = row.split(" -> ");
            for (var col : cols) {
                var xy = col.split(",");
                var x = Integer.parseInt(xy[0]);
                var y = Integer.parseInt(xy[1]);
                filledMap[x][y] = Place.ROCK.num;
                if (oldX != -1) filledMap = fillLines(filledMap, oldX, oldY, x, y);
                oldX = x;
                oldY = y;
            }
            oldX = -1;
            oldY = -1;
        }

        for (int x = lowX;x < higX; x++) {
            filledMap[x][higY-1] = Place.ROCK.num;
        }

        printMap(filledMap, lowX, lowY, higX, higY);
        var old = -1;
        var o = -1;
        var c = 0;
        while (true) {
            filledMap[startx][starty] = Place.SAND.num;
            try {
                o = tick(filledMap, startx, starty, 0);
            } catch (ArrayIndexOutOfBoundsException e) {
                break;
            }
            if (o == old && o == 0) break;
            c+=1;
            old = o;

        }
        printMap(filledMap, lowX, lowY, higX, higY);
        System.out.println(c);
    }


}
