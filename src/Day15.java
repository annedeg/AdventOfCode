import helpers.Helper;
import helpers.Position;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public class Day15 extends CodeDay {

    @Override
    public void puzzleOne() {
        var input = Helper.readToStringArrayList("src/input/day15");

        var getRow = 10;

        var allSenBec = new HashMap<List<Position>, Integer>();

        for (var line : input) {
            var sensor = (line.split("Sensor at ")[1].split(":")[0]).split(", ");
            var beacon = line.split("is at ")[1].split(", ");
            var sp = new Position(Integer.parseInt(sensor[0].split("=")[1]), Integer.parseInt(sensor[1].split("=")[1]));
            var bp = new Position(Integer.parseInt(beacon[0].split("=")[1]), Integer.parseInt(beacon[1].split("=")[1]));


            allSenBec.put(List.of(sp, bp), getDistance(sp, bp));
        }
        int c = 0;
        for (int x = -1_000_000; x < 1_000_000; x++) {
            for (var senbec : allSenBec.keySet()) {
                var distanceNeeded = allSenBec.get(senbec);
                var distance = getDistance(new Position(x, getRow), senbec.get(0));
                if (distance <= distanceNeeded &&
                        (senbec.get(1).x != x && senbec.get(1).y != getRow)
//                        (senbec.get(1).x != x && senbec.get(1).y != getRow)
                ) {
                    c += 1;
                }
            }
        }

        System.out.println(c);
    }

    public void countRow(byte[][] map, int row, int lowX, int lowY, int higX, int higY) {
        row += 10000;
        int count = 0;
        for (int y = lowY; y < higY; y++) {
            for (int x = lowX; x < higX; x++) {
                if (y == row && map[x][y] == 3) count++;
            }
        }

        System.out.println(count);
    }

    public void fillAround(byte[][] map, Position pos, int distance) {
        for (int x = pos.x - distance; x <= pos.x + distance; x++) {
            for (int y = pos.y - distance; y <= pos.y + distance; y++) {
                try {
                    if (getDistance(new Position(x, y), pos) <= distance && map[x][y] == 0) {
                        map[x][y] = 3;
                    }
                } catch (ArrayIndexOutOfBoundsException e) {
                }
            }
        }
    }

    public int getDistance(Position pos1, Position pos2) {
        return Math.abs(pos2.x - pos1.x) + Math.abs(pos2.y - pos1.y);
    }

    enum Spot {
        B, S, L;

        public static String toStr(int f) {
            if (f == 1) {
                return "B";
            }

            if (f == 2) {
                return "S";
            }

            if (f == 3) {
                return "L";
            }
            return " .";
        }

        public byte ordinalAdded() {
            return (byte) (this.ordinal() + 1);
        }
    }

    public void printMap(byte[][] map, int lowX, int lowY, int higX, int higY) {
        for (int y = lowY; y < higY; y++) {
            for (int x = lowX; x < higX; x++) {
                System.out.print(Spot.toStr(map[x][y]));
            }
            System.out.println();
        }
    }

    @Override
    public void puzzleTwo() {

    }
}
