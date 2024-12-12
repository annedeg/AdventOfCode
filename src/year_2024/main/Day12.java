package year_2024.main;

import helpers.Helper;
import helpers.MatrixLocation;

import javax.management.MBeanAttributeInfo;
import java.lang.reflect.Array;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day12 {

    public void puzzleOne() {
        char[][] input = Helper.toMatrix(2024, 12);
        ArrayList<MatrixLocation> visitedOrIncluded = new ArrayList<>();
        ArrayList<ArrayList<MatrixLocation>> chunks = new ArrayList<>();
        ArrayList<MatrixLocation> allLocations = Helper.allLocations(input);
        for (MatrixLocation currentLocation : allLocations) {
            if (visitedOrIncluded.contains(currentLocation)) {
                continue;
            }

            char currChar = input[currentLocation.getY()][currentLocation.getX()];
            ArrayList<MatrixLocation> matrixLocations = findAllChunkz(input, currChar, currentLocation, new ArrayList<>(), new ArrayList<>());
            chunks.add(matrixLocations);
            visitedOrIncluded.addAll(matrixLocations);
        }

        int total = chunks.stream()
            .mapToInt(list -> area(list) * perimeter(input, list))
            .sum();

        System.out.println(total);
    }

    private int area(ArrayList<MatrixLocation> list) {
        return list.size();
    }

    private int perimeter(char[][] matrix, ArrayList<MatrixLocation> list) {
        int total = 0;
        for (MatrixLocation matrixLocation : list) {
            ArrayList<MatrixLocation> surroundingTiles = Helper.surroundingTiles(matrix, matrixLocation, false);
            int amountOfNeigbours = surroundingTiles.stream().mapToInt(loc -> list.contains(loc) ? 1 : 0).sum();
            total += 4 - amountOfNeigbours;
        }
        return total;
    }

    private ArrayList<MatrixLocation> findAllChunkz(char[][] matrix, char toFind, MatrixLocation location, ArrayList<MatrixLocation> history, ArrayList<MatrixLocation> values) {
        if (history.contains(location)) {
            return values;
        }

        history.add(location);

        if (location.getValue(matrix) != toFind) {
            return values;
        }

        values.add(location);

        for (MatrixLocation loc : Helper.surroundingTiles(matrix, location, false)) {
            findAllChunkz(matrix, toFind, loc, history, values);
        }

        return values;
    }

    public void puzzleTwo() {
        char[][] input = Helper.toMatrix(2024, 12);
        ArrayList<MatrixLocation> visitedOrIncluded = new ArrayList<>();
        ArrayList<ArrayList<MatrixLocation>> chunks = new ArrayList<>();
        ArrayList<MatrixLocation> allLocations = Helper.allLocations(input);
        for (MatrixLocation currentLocation : allLocations) {
            if (visitedOrIncluded.contains(currentLocation)) {
                continue;
            }

            char currChar = input[currentLocation.getY()][currentLocation.getX()];
            ArrayList<MatrixLocation> matrixLocations = findAllChunkz(input, currChar, currentLocation, new ArrayList<>(), new ArrayList<>());
            chunks.add(matrixLocations);
            visitedOrIncluded.addAll(matrixLocations);
        }

        int total = chunks.stream()
            .mapToInt(list -> area(list) * numberOfSides(input, list))
            .sum();

        System.out.println(total);
    }

    private int numberOfSides(char[][] input, ArrayList<MatrixLocation> list) {
        int total = 0;
        for (MatrixLocation matrixLocation : list) {
            total += sides(input, matrixLocation, list);
        }

        System.out.println(list);
        System.out.println(total);
        System.out.println();
        return total;
    }

    private int sides(char[][] input, MatrixLocation location, ArrayList<MatrixLocation> valid) {
        int y = location.getY();
        int x = location.getX();

        char[][] clone = input.clone();
        int sides = 0;


        StringBuilder b = new StringBuilder();
        for (int s = 0; s < 4; s++) {
            for (int yi = y; yi < y + 1; yi++) {
                for (int xi = x; xi < x + 1; xi++) {
                    if (valid.contains(new MatrixLocation(yi, xi))) {
                        b.append("1");
                    } else {
                        b.append("0");
                    }
                }
            }

            int index = Integer.parseInt(b.toString(), 2);

            if (!Stream.of(0, 3, 5, 10, 12, 15).toList().contains(index)) {
                if (index == 6 || index == 9) {
                    sides += 1;
                }
                sides += 1;
            }
        }

        return sides;
    }

    public static void main(String[] args) {
        Day12 day = new Day12();
        day.puzzleOne();
        day.puzzleTwo();
    }
}
