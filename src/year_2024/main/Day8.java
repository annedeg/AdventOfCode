package year_2024.main;

import year_2024.main.helpers.Helper;
import year_2024.main.helpers.MatrixLocation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day8 {

    public void puzzleOne() {
        char[][] strings = Helper.toMatrix(2024, 8);

        ArrayList<Character> characters = uniqueChars(strings);
        ArrayList<MatrixLocation> matrixLocations = characters.stream()
                .map(ch -> calculateAllPossibleAntiNodes(findLocations(strings, ch)))
                .flatMap(ArrayList::stream)
                .distinct()
                .collect(Collectors.toCollection(ArrayList::new));

        ArrayList<MatrixLocation> legit = legit(matrixLocations, strings);
        System.out.println(legit.size());
    }

    public ArrayList<Character> uniqueChars(char[][] matrix) {
        return Arrays.stream(matrix)
                .flatMap(chars -> new String(chars).chars().mapToObj(i -> (char) i))
                .distinct()
                .filter(character -> character != '.')
                .collect(Collectors.toCollection(ArrayList::new));
    }

    public ArrayList<MatrixLocation> charLocations(char[][] matrix) {
        ArrayList<MatrixLocation> list = new ArrayList<>();
        for (int y = 0; y < matrix.length; y++) {
            for (int x = 0; x < matrix[y].length; x++) {
                if (matrix[y][x] != '.') {
                    list.add(new MatrixLocation(x, y));
                }
            }
        }
        return list;
    }

    public ArrayList<MatrixLocation> findLocations(char[][] matrix, char c) {
        ArrayList<MatrixLocation> list = new ArrayList<>();
        for (int y = 0; y < matrix.length; y++) {
            for (int x = 0; x < matrix[y].length; x++) {
                if (matrix[y][x] == c) {
                    list.add(new MatrixLocation(x, y));
                }
            }
        }
        return list;
    }

    public ArrayList<MatrixLocation> calculateAllPossibleAntiNodes(ArrayList<MatrixLocation> locations) {
        ArrayList<MatrixLocation> antiNodes = new ArrayList<>();
        for (int i = 0; i < locations.size(); i++) {
            for (int j = 0; j < locations.size(); j++) {
                if (i == j) {
                    continue;
                }

                MatrixLocation matrixLocation1 = locations.get(i);
                MatrixLocation matrixLocation2 = locations.get(j);

                antiNodes.addAll(calcAntiNode(matrixLocation1, matrixLocation2));
            }
        }
        return antiNodes;
    }

    public ArrayList<MatrixLocation> calculateAllPossibleAntiNodesPartTwo(ArrayList<MatrixLocation> locations, char[][] matrix) {
        ArrayList<MatrixLocation> antiNodes = new ArrayList<>();
        for (int i = 0; i < locations.size(); i++) {
            for (int j = 0; j < locations.size(); j++) {
                if (i == j) {
                    continue;
                }

                MatrixLocation matrixLocation1 = locations.get(i);
                MatrixLocation matrixLocation2 = locations.get(j);

                antiNodes.addAll(calcAntiNodePartTwo(matrixLocation1, matrixLocation2, matrix));
            }
        }
        return antiNodes;
    }

    public ArrayList<MatrixLocation> calcAntiNode(MatrixLocation l1, MatrixLocation l2) {
        int x1 = l1.getX();
        int y1 = l1.getY();
        int x2 = l2.getX();
        int y2 = l2.getY();

        int i = x1 - x2;
        int j = y1 - y2;

        ArrayList<MatrixLocation> antiNodes = new ArrayList<>();
        antiNodes.add(new MatrixLocation(x1 + i, y1 + j));
        antiNodes.add(new MatrixLocation(x2 - i, y2 - j));
        return antiNodes;
    }


    public ArrayList<MatrixLocation> calcAntiNodePartTwo(MatrixLocation l1, MatrixLocation l2, char[][] matrix) {
        int x1 = l1.getX();
        int y1 = l1.getY();
        int x2 = l2.getX();
        int y2 = l2.getY();

        int i = x1 - x2;
        int j = y1 - y2;

        ArrayList<MatrixLocation> antiNodes = new ArrayList<>();
        boolean p1d = false;
        boolean p2d = false;
        int nx1 = x1 + i;
        int ny1 = y1 + j;
        int nx2 = x2 - i;
        int ny2 = y2 - j;
        while (!(p1d && p2d)) {
            if (!(nx1 >= matrix.length || ny1 >= matrix[0].length || nx1 < 0 || ny1 < 0)) {
                antiNodes.add(new MatrixLocation(nx1, ny1));
            } else {
                p1d = true;
            }
            if (!(nx2 >= matrix.length || ny2 >= matrix[0].length || nx2 < 0 || ny2 < 0)) {
                antiNodes.add(new MatrixLocation(nx2, ny2));
            } else {
                p2d = true;
            }

            nx1 += i;
            ny1 += j;
            nx2 -= i;
            ny2 -= j;
        }
        return antiNodes;
    }

    public ArrayList<MatrixLocation> legit(ArrayList<MatrixLocation> locations, char[][] matrix) {
        int xMax = matrix.length;
        int yMax = matrix[0].length;
        return locations.stream()
                .filter(location -> location.getX() < xMax && location.getY() < yMax && location.getX() >= 0 && location.getY() >= 0)
                .collect(Collectors.toCollection(ArrayList::new));
    }


    public void puzzleTwo() {
        char[][] strings = Helper.toMatrix(2024, 8);
        char[][] map = new char[strings.length][strings[0].length];
        ArrayList<Character> characters = uniqueChars(strings);
        ArrayList<MatrixLocation> matrixLocations = characters.stream()
                .map(ch -> calculateAllPossibleAntiNodesPartTwo(findLocations(strings, ch), strings))
                .flatMap(ArrayList::stream)
                .distinct()
                .collect(Collectors.toCollection(ArrayList::new));

        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[0].length; j++) {
                map[i][j] = '.';
            }
        }
        for (MatrixLocation matrixLocation : matrixLocations) {
            map[matrixLocation.getY()][matrixLocation.getX()] = '#';
        }

        for (MatrixLocation matrixLocation : charLocations(strings)) {
            map[matrixLocation.getY()][matrixLocation.getX()] = '#';
        }

        ArrayList<MatrixLocation> matrixLocationsAll = Stream.of(matrixLocations, charLocations(strings)).flatMap(ArrayList::stream).distinct().collect(Collectors.toCollection(ArrayList::new));
        System.out.println(matrixLocationsAll.size());

//        ArrayList<MatrixLocation> legit = legit(matrixLocations, strings);

    }

    public static void main(String[] args) {
        Day8 day = new Day8();
        day.puzzleOne();
        day.puzzleTwo();
    }
}
