import helpers.Helper;

import java.util.*;

import static java.lang.Math.abs;

public class Day18 extends CodeDay {

    @Override
    public void puzzleOne() {
        var input = Helper.readToStringArrayList("src/input/day18");

        Location currentLocation = new Location(0, 0);

        ArrayList<Trench> trenches = new ArrayList<>();

        for (String line : input) {
            Trench trench = new Trench(line, currentLocation, false);
            trenches.add(trench);
            currentLocation = trench.getEnd();
        }

        int sum1 = 0;
        int sum2 = 0;
        int length = 0;
        for (Trench trench : trenches) {
            sum1 += trench.location.x * trench.getEnd().y;
            sum2 += trench.location.y * trench.getEnd().x;
            length += trench.length;
        }

        int total = abs(sum1-sum2) / 2;
        System.out.println((total + (length / 2) + 1));
    }

    @Override
    public void puzzleTwo() {
        var input = Helper.readToStringArrayList("src/input/day18");

        Location currentLocation = new Location(0, 0);

        ArrayList<Trench> trenches = new ArrayList<>();

        for (String line : input) {
            Trench trench = new Trench(line, currentLocation, true);
            trenches.add(trench);
            currentLocation = trench.getEnd();
        }

        long sum1 = 0;
        long sum2 = 0;
        long length = 0;
        for (Trench trench : trenches) {
            sum1 += (long) trench.location.x * trench.getEnd().y;
            sum2 += (long) trench.location.y * trench.getEnd().x;
            length += trench.length;
        }

        long total = abs(sum1-sum2) / 2;
        System.out.println(total);
        System.out.println((total + (length / 2) + 1));
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

        Trench(String line, Location location, boolean translateHex) {
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

            if (translateHex) {
                this.length = Integer.parseInt(color.replaceAll("[#()]", "").substring(0, 5), 16);
                int dir = (Integer.parseInt(color.replaceAll("[#()]", "").substring(5, 6), 16)) % 4;

                this.direction = switch (dir) {
                    case 0 -> Direction.RIGHT;
                    case 1 -> Direction.DOWN;
                    case 2 -> Direction.LEFT;
                    case 3 -> Direction.UP;
                    default -> throw new IllegalStateException("Unexpected value: " + split[0]);
                };

                System.out.println(length);
                System.out.println(direction.name());
            }
        }

        public Location getEnd() {
            return new Location(location.x + direction.getX() * length, location.y + direction.getY() * length);
        }
    }

    record Location(int x, int y) {
    }
}
