import helpers.Helper;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.lang.Math.abs;

public class Day18 extends CodeDay {

    @Override
    public void puzzleOne() {
        var input = Helper.readToStringArrayList("src/input/day18");

        int maxX = 0;
        int maxY = 0;

        int minX = 0;
        int minY = 0;


        Location currentLocation = new Location(0, 0);

        ArrayList<Trench> trenches = new ArrayList<>();

        for (String line : input) {
            Trench trench = new Trench(line, currentLocation);
            trenches.add(trench);
            currentLocation = trench.getEnd();

            if (currentLocation.x > maxX) {
                maxX = currentLocation.x;
            }

            if (currentLocation.x < minX) {
                minX = currentLocation.x;
            }

            if (currentLocation.y > maxY) {
                maxY = currentLocation.y;
            }

            if (currentLocation.y < minY) {
                minY = currentLocation.y;
            }
        }

        int xmod = abs(minX);
        int ymod = abs(minY);
        char[][] matrix = new char[maxX + xmod+1][maxY + ymod+1];
        currentLocation = new Location(0,0);
        for (Trench trench : trenches) {
            int startX = Math.min(currentLocation.x, trench.getEnd().x)+xmod;
            int endX = Math.max(currentLocation.x, trench.getEnd().x)+xmod;

            int startY = Math.min(currentLocation.y, trench.getEnd().y)+ymod;
            int endY = Math.max(currentLocation.y, trench.getEnd().y)+ymod;

            for (int x = startX; x <= endX; x++) {
                for (int y = startY; y <= endY; y++) {
                    matrix[x][y] = '#';
                }
            }

            currentLocation = trench.getEnd();
        }

        for (int x = 0; x < matrix.length; x++) {
            for (int y = 0; y < matrix[0].length; y++) {
                if (checkRow(matrix, new Location(x,y), trenches)) {
                    matrix[x][y] = '/';
                }
            }
        }

        long count = Arrays.stream(matrix)
                .map(String::new)
                .map(str -> Arrays.stream(str.split("")).collect(Collectors.toCollection(ArrayList::new)))
                .flatMap(ArrayList::stream)
                .filter(str -> str.equals("#") || str.equals("/"))
                .count();
        System.out.println(count);
    }

    public boolean checkRow(char[][] matrix, Location location, ArrayList<Trench> trenches) {
        boolean in = false;
        for (int yc = 0; yc < matrix[0].length; yc++) {
            if (yc+1 < matrix[0].length && matrix[location.x][yc] == '#' && matrix[location.x][yc + 1] == '#') {
                continue;
            }

            if (matrix[location.x][yc] == '#') {
                in = !in;
                continue;
            }

            if (location.y == yc && matrix[location.x][location.y] == '\u0000') {
                return in;
            }
        }

        return false;
    }

    private boolean inStartOrEndTrench(ArrayList<Trench> trenches, Location location) {
        return trenches.stream()
                .anyMatch(trench -> trench.inStartEndTrench(location));
    }

    public boolean inAnyTrench(ArrayList<Trench> trenches, Location location) {
        return trenches.stream()
                .anyMatch(trench -> trench.inTrench(location));
    }

    @Override
    public void puzzleTwo() {
        var input = Helper.readToStringArrayList("src/input/day17");

    }

    enum Direction {
        UP(-1, 0), RIGHT(0, 1), DOWN(1, 0), LEFT(0, -1);

        int x;
        int y;

        Direction(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }
    }

    class Trench {
        Location location;
        Direction direction;
        String color;
        int length;

        Trench(String line, Location location) {
            String[] split = line.split(" ");
            direction = switch (split[0]) {
                case "U" -> Direction.UP;
                case "R" -> Direction.RIGHT;
                case "D" -> Direction.DOWN;
                case "L" -> Direction.LEFT;
                default -> throw new IllegalStateException("Unexpected value: " + split[0]);
            };

            this.location = location;
            this.length = Integer.parseInt(split[1]);

            color = split[2];
        }

        public Location getEnd() {
            return new Location(location.x + direction.getX() * length, location.y + direction.getY() * length);
        }

        public boolean inTrench(Location location) {
            return this.location.x >= location.x && location.x <= getEnd().x && this.location.y >= location.y && location.y <= getEnd().y;
        }

        public boolean inStartEndTrench(Location location) {
            return location.x == this.location.x && location.y == this.location.y || location.x == getEnd().x && location.y == getEnd().y;
        }
    }

    record Location(int x, int y) {
    }
}
