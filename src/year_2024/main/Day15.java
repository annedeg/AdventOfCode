package year_2024.main;

import helpers.Helper;
import helpers.MatrixLocation;

import java.util.*;
import java.util.stream.Collectors;

public class Day15 {
    public void puzzleOne() {
        String[] s = Helper.readToString(2024, 15).split("\n\n");
        String tmap = s[0];
        String moves = s[1];
        moves = moves.replaceAll("\n", "");
        char[][] map = Helper.toMatrix(Arrays.stream(tmap.split("\n")).collect(Collectors.toCollection(ArrayList::new)));
        map = doMoves(map, moves, false, false);
        long total = calcCoordinates(map, 'O');
        System.out.println(total);
    }

    public void puzzleTwo() {
        String[] s = Helper.readToString(2024, 15).split("\n\n");
        String tmap = s[0];
        String moves = s[1];
        moves = moves.replaceAll("\n", "");
        char[][] map = createPartTwoMap(tmap);

        map = doMoves(map, moves, false, true);
        long coordinates = calcCoordinates(map, '[');
        System.out.println(coordinates);
    }


    private char[][] doMoves(char[][] map, String moves, boolean printSteps, boolean partTwo) {
        if (printSteps) {
            System.out.println("Inital state:");
            printMap(map);
        }

        for (char ch : moves.toCharArray()) {
            Day4.Direction direction = switch (ch) {
                case '>' -> Day4.Direction.RIGHT;
                case '^' -> Day4.Direction.UP;
                case 'v' -> Day4.Direction.DOWN;
                case '<' -> Day4.Direction.LEFT;
                default -> throw new IllegalStateException("Unexpected value: " + ch);
            };

            if (printSteps) {
                System.out.println("Move " + ch + ":");
            }

            if (partTwo) {
                map = doMovev2(map, direction);
            } else {
                map = doMove(map, direction);
            }

            if (printSteps) {
                printMap(map);
            }
        }
        return map;
    }

    private static long calcCoordinates(char[][] map, char charToFind) {
        long total = 0;
        for (int y = 0; y < map.length; y++) {
            for (int x = 0; x < map[y].length; x++) {
                if (map[y][x] == charToFind) {
                    total += (100L * y) + x;
                }
            }
        }
        return total;
    }

    private static void printMap(char[][] map) {
        for (char[] chars : map) {
            for (char aChar : chars) {
                System.out.print(aChar);
            }
            System.out.println();
        }
        System.out.println();
    }

    public Optional<MatrixLocation> getRobotLocation(char[][] map) {
        for (int y = 0; y < map.length; y++) {
            for (int x = 0; x < map[y].length; x++) {
                if (map[y][x] == '@') {
                    return Optional.of(new MatrixLocation(x, y, '@'));
                }
            }
        }
        return Optional.empty();
    }

    public char[][] doMove(char[][] map, Day4.Direction direction) {
        char[][] mapClone = map.clone();
        Optional<MatrixLocation> optional = getRobotLocation(map);

        if (optional.isEmpty()) {
            return map;
        }

        MatrixLocation robotLocation = optional.get();

        int xc = 0;
        int yc = 0;
        switch (direction) {
            case RIGHT -> xc = 1;
            case LEFT -> xc = -1;
            case UP -> yc = -1;
            case DOWN -> yc = 1;
        }

        int x = robotLocation.getX();
        int y = robotLocation.getY();


        HashMap<MatrixLocation, MatrixLocation> changes = new HashMap<>();

        while (true) {
            int nx = x + xc;
            int ny = y + yc;

            if (map[ny][nx] == 'O') {
                changes.put(new MatrixLocation(x, y), new MatrixLocation(nx, ny));
                x = nx;
                y = ny;
                continue;
                //oei, we moeten nog meer verplaatsen
            } else if (map[ny][nx] == '#') {
                return mapClone;
                //move kan niet, niks aanpassen
            }

            changes.put(new MatrixLocation(x, y), new MatrixLocation(nx, ny));
            ArrayList<MatrixLocation> matrixLocations = new ArrayList<>(changes.keySet());

            Collection<MatrixLocation> destList = changes.values();
            matrixLocations.forEach(from -> {
                MatrixLocation to = changes.get(from);
                if (!destList.contains(from)) {
                    map[from.y][from.x] = '.';
                }
                map[to.y][to.x] = (robotLocation.equals(from)) ? '@' : 'O';
            });
            return map;
        }
    }


    private static char[][] createPartTwoMap(String tmap) {
        char[][] oldMap = Helper.toMatrix(Arrays.stream(tmap.split("\n")).collect(Collectors.toCollection(ArrayList::new)));
        char[][] map = new char[oldMap.length][oldMap[0].length * 2];

        for (int y = 0; y < map.length; y++) {
            int xc = 0;
            for (int x = 0; x < map.length; x++) {
                char c = oldMap[y][x];

                switch (c) {
                    case '#' -> {
                        map[y][x + xc] = '#';
                        xc++;
                        map[y][x + xc] = '#';
                    }
                    case 'O' -> {
                        map[y][x + xc] = '[';
                        xc++;
                        map[y][x + xc] = ']';
                    }

                    case '.' -> {
                        map[y][x + xc] = '.';
                        xc++;
                        map[y][x + xc] = '.';
                    }

                    case '@' -> {
                        map[y][x + xc] = '@';
                        xc++;
                        map[y][x + xc] = '.';
                    }
                }
            }
        }
        return map;
    }

    public char[][] doMovev2(char[][] map, Day4.Direction direction) {
        char[][] mapClone = map.clone();
        Optional<MatrixLocation> optional = getRobotLocation(map);

        if (optional.isEmpty()) {
            return map;
        }

        MatrixLocation robotLocation = optional.get();

        int xc = 0;
        int yc = 0;
        switch (direction) {
            case RIGHT -> xc = 1;
            case LEFT -> xc = -1;
            case UP -> yc = -1;
            case DOWN -> yc = 1;
        }

        int x = robotLocation.getX();
        int y = robotLocation.getY();

        HashMap<MatrixLocation, MatrixLocation> changes = new HashMap<>();

        while (true) {
            int nx = x + xc;
            int ny = y + yc;

            if ((map[ny][nx] == '[' || map[ny][nx] == ']') && (direction == Day4.Direction.UP || direction == Day4.Direction.DOWN)) {
                // this sucks :(
                return handleDoubleBlocks(map, direction, nx, ny, mapClone, y, x);
            } else if ((map[ny][nx] == '[' || map[ny][nx] == ']') && direction != Day4.Direction.UP) {
                changes.put(new MatrixLocation(x, y, map[y][x]), new MatrixLocation(nx, ny, map[ny][nx]));
                x = nx;
                y = ny;
                continue;
            } else if (map[ny][nx] == '#') {
                return mapClone;
                //move kan niet, niks aanpassen
            }
            changes.put(new MatrixLocation(x, y, map[y][x]), new MatrixLocation(nx, ny, map[ny][nx]));
            ArrayList<MatrixLocation> matrixLocations = new ArrayList<>(changes.keySet());
            ArrayList<MatrixLocation> hasHadNewInput = new ArrayList<>();
            for (MatrixLocation from : matrixLocations) {
                MatrixLocation to = changes.get(from);

                if (!hasHadNewInput.contains(from)) {
                    map[from.y][from.x] = '.';
                }
                map[to.y][to.x] = from.getVal();

                hasHadNewInput.add(to);
            }

            return map;
        }
    }

    private char[][] handleDoubleBlocks(char[][] map, Day4.Direction direction, int nx, int ny, char[][] mapClone, int y, int x) {
        ArrayList<MatrixLocation> matrixLocations = detectAllLocationsConnecting(map, direction, new MatrixLocation(nx, ny), new ArrayList<>());
        matrixLocations = matrixLocations.stream().distinct().collect(Collectors.toCollection(ArrayList::new));
        if (matrixLocations.contains(new MatrixLocation(-1, -1))) {
            // move kan niet, niks aanpassen (add flag)
            return mapClone;
        }

        matrixLocations = matrixLocations.stream()
                .sorted((ml1, ml2) -> {
                    if (direction != Day4.Direction.UP) {
                        return ml1.getY() - ml2.getX();
                    } else {
                        return ml2.getY() - ml1.getX();
                    }
                })
                .collect(Collectors.toCollection(ArrayList::new));


        HashMap<MatrixLocation, MatrixLocation> fromTo = new HashMap<>();
        for (MatrixLocation matrixLocation : matrixLocations) {
            MatrixLocation to = new MatrixLocation(matrixLocation.getX(), matrixLocation.getY() + ((direction == Day4.Direction.UP) ? -1 : 1), matrixLocation.getVal());
            fromTo.put(matrixLocation, to);
        }

        Collection<MatrixLocation> destList = fromTo.values();
        for (MatrixLocation from : matrixLocations) {
            MatrixLocation to = fromTo.get(from);

            if (!destList.contains(from)) {
                map[from.y][from.x] = '.';
            }


            map[to.y][to.x] = to.getVal();
        }

        map[y][x] = '.';
        map[ny][nx] = '@';
        return map;
    }

    public ArrayList<MatrixLocation> detectAllLocationsConnecting(char[][] map, Day4.Direction direction, MatrixLocation startLocation, ArrayList<MatrixLocation> locations) {
        //check if connected (paths cross)

        int x = startLocation.getX();
        int y = startLocation.getY();
        char c = map[y][x];


        if (c == '.' || c == '@') {
            return locations;
        }

        if (c == '#') {
            //not possible break everything! (add flag)
            locations.add(new MatrixLocation(-1, -1));
            return locations;
        }

        ArrayList<MatrixLocation> newLocations = new ArrayList<>();

        if (c == '[') {
            newLocations.add(new MatrixLocation(x, y, c));
            newLocations.add(new MatrixLocation(x + 1, y, ']'));
        }

        if (c == ']') {
            newLocations.add(new MatrixLocation(x, y, c));
            newLocations.add(new MatrixLocation(x - 1, y, '['));
        }

        ArrayList<MatrixLocation> locationsToSearchFor = newLocations.stream().map(nl -> {
            locations.add(nl);
            return new MatrixLocation(nl.getX(), nl.getY() + (direction == Day4.Direction.UP ? -1 : 1));
        }).collect(Collectors.toCollection(ArrayList::new));

        locationsToSearchFor.forEach(ltsf -> detectAllLocationsConnecting(map, direction, ltsf, locations));
        return locations;
    }

    public static void main(String[] args) {
        Day15 day = new Day15();
        day.puzzleOne();
        day.puzzleTwo();
    }
}
