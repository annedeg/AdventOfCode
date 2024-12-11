package year_2023.main;

import helpers.*;

import java.util.ArrayList;
import java.util.Objects;
import java.util.stream.Collectors;

public class Day10 extends CodeDay {
    char[][] matrix;
    ArrayList<ArrayList<Location>> allPaths = new ArrayList<>();
    Location currentLocation = null;

    @Override
    public void puzzleOne() {
        var input = Helper.readToStringArrayList(2023, 10);
        matrix = createMatrix(input);

        Location start = findStart();
        ArrayList<Location> history = new ArrayList<>();
        ArrayList<Location> paths = findPath(start, start, history);
        System.out.println(paths.size() / 2);
    }

    public ArrayList<Location> findPath(Location currentLocation, Location start, ArrayList<Location> history) {

        if (history.contains(currentLocation) || currentLocation.getChar() == '.') {
            return history;
        }

        history.add(currentLocation);

        ArrayList<Location> nextLocations = findNextLocations(currentLocation);

        for (Location location : nextLocations) {
            findPath(location, start, history);
        }

        return history;
    }

    public ArrayList<Location> findNextLocations(Location currentLocation) {
        int x = currentLocation.x;
        int y = currentLocation.y;

        ArrayList<Location> locations = new ArrayList<>();
        switch (matrix[x][y]) {
            case '|' -> {
                locations.add(new Location(x + 1, y));
                locations.add(new Location(x - 1, y));
            }
            case '-' -> {
                locations.add(new Location(x, y - 1));
                locations.add(new Location(x, y + 1));
            }
            case 'L' -> {
                locations.add(new Location(x, y + 1));
                locations.add(new Location(x - 1, y));
            }
            case 'J' -> {
                locations.add(new Location(x, y - 1));
                locations.add(new Location(x - 1, y));
            }
            case '7' -> {
                locations.add(new Location(x, y - 1));
                locations.add(new Location(x + 1, y));
            }
            case 'F' -> {
                locations.add(new Location(x, y + 1));
                locations.add(new Location(x + 1, y));
            }
            case 'S' -> {
                if (x + 1 < matrix.length && (matrix[x + 1][y] != '|' || matrix[x + 1][y] != '7' || matrix[x + 1][y] != 'F')) {
                    locations.add(new Location(x + 1, y));
                }
                if (x > 0 && (matrix[x - 1][y] != '|' || matrix[x - 1][y] != 'L' || matrix[x - 1][y] != 'J')) {
                    locations.add(new Location(x - 1, y));
                }
                if (y + 1 < matrix.length && (matrix[x][y + 1] != '-' || matrix[x][y + 1] != '7' || matrix[x][y + 1] != 'J')) {
                    locations.add(new Location(x, y + 1));
                }
                if (y > 0 && (matrix[x][y - 1] != '-' || matrix[x][y - 1] != 'F' || matrix[x][y - 1] != 'L')) {
                    locations.add(new Location(x, y - 1));
                }
            }
        }

        ArrayList<Location> newLocations = locations.stream().filter(loc -> loc.getChar() != '.').collect(Collectors.toCollection(ArrayList::new));
        return newLocations;
    }

    @Override
    public void puzzleTwo() {
        var input = Helper.readToStringArrayList(2023, 10);
        matrix = createMatrix(input);

        Location start = findStart();
        ArrayList<Location> history = new ArrayList<>();
        ArrayList<Location> path = findPath(start, start, history);

        isInLoop(3, 14, history);
        int amount = 0;
        for (int x = 0; x < matrix.length; x++) {
            for (int y = 0; y < matrix[0].length; y++) {
                if (isInLoop(x, y, path)) {
                    System.out.println(new Location(x, y));
                    amount += 1;
                }
            }
        }
        System.out.println(amount);
    }

    public boolean isInLoop(int nx, int ny, ArrayList<Location> loop) {
        boolean xCorrect = false;
        boolean yCorrect = false;
        int aantalX = 0;
        int aantalY = 0;

        boolean openx = false;
        boolean openy = false;


        Location toCheck = new Location(nx, ny);

        if (loop.contains(toCheck) || nx == 0 || ny == 0 || nx == matrix.length-1 || ny == matrix[0].length-1) {
            return false;
        }

        int direction = 0;
        for (int x = 0; x < matrix.length; x++) {
            Location location = new Location(x, ny);
            Location next = new Location(x+1, ny);

            if (openx && location.equals(toCheck)) {
                xCorrect = true;
                break;
            } else if (location.equals(toCheck)) {
                break;
            }

            if (loop.contains(location) && (next.isPossible())) {
                if (location.getChar() == 'F' || location.getChar() == 'J') {
                    direction -= 1;
                }

                if (location.getChar() == '7' || location.getChar() == 'L') {
                    direction += 1;
                }

                if (direction == 2 || direction == -2) {
                    openx = !openx;
                    direction = 0;
                    continue;
                }

                if (location.getChar() == '-' && direction == 0) {
                    openx = !openx;
                }
            }
        }

        direction = 0;

        for (int y = 0; y < matrix[0].length; y++) {
            Location location = new Location(nx, y);
            Location next = new Location(nx, y + 1);

            if (openy && location.equals(toCheck)) {
                yCorrect = true;
                break;
            } else if (location.equals(toCheck)) {
                break;
            }

            if (loop.contains(location) && (next.isPossible())) {
                if (location.getChar() == 'F' || location.getChar() == 'J') {
                    direction -= 1;
                }

                if (location.getChar() == '7' || location.getChar() == 'L') {
                    direction += 1;
                }

                if (direction == 2 || direction == -2) {
                    openy = !openy;
                    direction = 0;
                    continue;
                }

                if (location.getChar() == '|' && direction == 0) {
                    openy = !openy;
                }
            }
        }


        return (xCorrect && yCorrect);
    }

    public char[][] createMatrix(ArrayList<String> input) {
        char[][] matrix = new char[input.size()][input.get(0).length()];

        int x = 0;
        for (String inputLine : input) {
            int y = 0;
            for (char ch : inputLine.toCharArray()) {
                matrix[x][y] = ch;
                y++;
            }
            x++;
        }

        return matrix;
    }

    public Location findStart() {
        for (int x = 0; x < matrix.length; x++) {
            for (int y = 0; y < matrix[0].length; y++) {
                if (matrix[x][y] == 'S') {
                    return new Location(x, y);
                }
            }
        }
        return null;
    }

    public class Location {

        private final int x;
        private final int y;

        Location(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public boolean isPossible() {
            return this.x >= 0 && this.y >= 0 && this.x <= matrix.length && this.y <= matrix[0].length;
        }

        public char getChar() {
            return matrix[x][y];
        }

        @Override
        public String toString() {
            return getChar() + " (" + x + "," + y + ")";
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Location location = (Location) o;
            return x == location.x && y == location.y;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y);
        }
    }
}

