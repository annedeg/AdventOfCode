import helpers.Helper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Collectors;

public class Day9 extends CodeDay {
    @Override
    public void puzzleOne() {
        var input = Helper.readToStringArrayList("src/input/daynine.txt");

        var h = new Position(0, 0);
        var t = new Position(0, 0);
        var allLocations = new ArrayList<Position>();
        allLocations.add(t);
        for (var c : input) {
            var spl = c.split(" ");
            var res = stepHead(spl[0], Integer.parseInt(spl[1]), h, t, allLocations);
            h = (Position) res[0];
            t = (Position) res[1];
            allLocations = (ArrayList<Position>) res[2];
            allLocations.add(t);
        }

//        allLocations.forEach(System.out::println);

        var res = allLocations.stream()
                .distinct().toList();
//        System.out.println(res);
        System.out.println(res.size());
    }

    public Object[] stepHead(String direction, int amount, Position h, Position t, ArrayList<Position> positions) {
        for (int i = 0; i < amount; i++) {

            var prevh = h;

            h = switch (direction) {
                case "R" -> new Position(h.x + 1, h.y);
                case "L" -> new Position(h.x - 1, h.y);
                case "U" -> new Position(h.x, h.y - 1);
                case "D" -> new Position(h.x, h.y + 1);
                default -> null;
            };

            t = stepTail(h, prevh, t);
            System.out.println(h);
            System.out.println(t + "\n");

            positions.add(t);
        }
        return new Object[]{h, t, positions};
    }

    public Object[] stepHeadTwo(String direction, int amount, Position h, ArrayList<Position> t, ArrayList<Position> positions) {
        for (int i = 0; i < amount; i++) {
            var prevh = h;
            h = switch (direction) {
                case "R" -> new Position(h.x + 1, h.y);
                case "L" -> new Position(h.x - 1, h.y);
                case "U" -> new Position(h.x, h.y - 1);
                case "D" -> new Position(h.x, h.y + 1);
                default -> null;
            };

            t = setAllTails(h, prevh, t);

//            printboard(t,h);
            positions.add(t.get(8));
        }

        return new Object[]{h, t, positions};
    }

    private void printboard(ArrayList<Position> t, Position h) {
        int map[][] = new int[10][10];

        int c = 9;
        for (var ti : t) {
            map[ti.x+3][ti.y+5] = c;
            c-=1;
        }
        map[h.x+3][h.y+5] = -1;

        for (int x = 0; x < map.length; x++) {
            var amounttoprint = 0;
            var str = new StringBuilder();
            for (int[] ints : map) {
                if (ints[x] != 0) {
                    amounttoprint += 1;
                    if (ints[x] == -1) {
                        str.append("H");
                    } else {
                        str.append(ints[x]);
                    }
                } else {
                    str.append(".");
                }
            }
//            if (amounttoprint > 0) {
                System.out.println(str);
//            }

        }
        System.out.println();
        System.out.println();
    }


    public Position stepTail(Position h, Position prevh, Position t) {
        int yc = (h.y - t.y);
        int xc = (h.x - t.x);

        if (Math.abs(yc) > 1 || Math.abs(xc) > 1) {
            return prevh;
        }

        return t;
    }

    public ArrayList<Position> setAllTails(Position h, Position prevh, ArrayList<Position> t) {
        for (int i = 0; i <= t.size()-1; i++) {
            int yc = (h.y - t.get(i).y);
            int xc = (h.x - t.get(i).x);
            if (Math.abs(yc) > 1 || Math.abs(xc) > 1) {
                if (xc == 0 && yc > 1) {
                    t.set(i, new Position(t.get(i).x, t.get(i).y + 1));
                }
                if (xc == 0 && yc < -1) {
                    t.set(i, new Position(t.get(i).x, t.get(i).y - 1));
                }
                if (yc == 0 && xc > 1) {
                    t.set(i, new Position(t.get(i).x + 1, t.get(i).y));
                }
                if (yc == 0 && xc < -1) {
                    t.set(i, new Position(t.get(i).x - 1, t.get(i).y));
                }

                if (Math.abs(xc) == 1 && yc > 1) {
                    t.set(i, new Position(h.x, t.get(i).y + 1));
                }
                if (Math.abs(xc) == 1 && yc < -1) {
                    t.set(i, new Position(h.x, t.get(i).y - 1));
                }
                if (Math.abs(yc) == 1 && xc > 1) {
                    t.set(i, new Position(t.get(i).x + 1, h.y));
                }
                if (Math.abs(yc) == 1 && xc < -1) {
                    t.set(i, new Position(t.get(i).x - 1, h.y));
                }
                if (yc == 2 && xc == 2) {
                    t.set(i, new Position(t.get(i).x - 1, t.get(i).x - 1));
                }

                if (yc == -2 && xc == -2) {
                    t.set(i, new Position(t.get(i).x + 1, t.get(i).y + 1));
                }

                if (yc == -2 && xc == 2) {
                    t.set(i, new Position(t.get(i).x + 1, t.get(i).y - 1));
                }

                if (yc == 2 && xc == -2) {
                    t.set(i, new Position(t.get(i).x - 1, t.get(i).y + 1));
                }
            }
            h = t.get(i);
        }

        return t;
    }

    @Override
    public void puzzleTwo() {
        var input = Helper.readToStringArrayList("src/input/daynine.txt");

        var h = new Position(0, 0);
        var t = new ArrayList<Position>();

        for (int i = 0; i < 9; i++) {
            t.add(new Position(0, 0));
        }

        var allLocations = new ArrayList<Position>();
        allLocations.add(t.get(8));
        for (var c : input) {
            var spl = c.split(" ");
            var res = stepHeadTwo(spl[0], Integer.parseInt(spl[1]), h, t, allLocations);
            h = (Position) res[0];
            t = (ArrayList<Position>) res[1];
            allLocations = (ArrayList<Position>) res[2];
            allLocations.add(t.get(8));
        }

//        allLocations.forEach(System.out::println);

        var res = allLocations.stream()
                .distinct().toList();
//        System.out.println(res);
        System.out.println(res.size());
    }
}

class Position {
    final int x;
    final int y;

    Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Position position)) return false;
        return x == position.x && y == position.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    @Override
    public String toString() {
        return "Position{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
