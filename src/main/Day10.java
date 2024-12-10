package main;

import main.helpers.Helper;
import main.helpers.MatrixLocation;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Day10 {

    public void puzzleOne() {
        int[][] map = Helper.toIntMatrix("C:\\Users\\agraaff\\Dev\\AdventOfCode2022\\src\\main\\input\\10");

        List<MatrixLocation> allStartLocations = Helper.allLocations(map).stream()
            .filter(matrixLocation -> map[matrixLocation.y][matrixLocation.getX()] == 0)
            .toList();

        int sum = allStartLocations.stream()
            .map(startLocation -> pathsFound(map, startLocation, 0, 0, new ArrayList<>(), false))
            .mapToInt(x -> x)
            .sum();

        System.out.println(sum);
    }

    public int pathsFound(int[][] matrix, MatrixLocation current, int value, int totalFound, ArrayList<MatrixLocation> endPoints, boolean part2) {
        if ((!endPoints.contains(current) || part2) && current.getValue(matrix) == 9) {
            endPoints.add(current);
            return ++totalFound;
        }

        ArrayList<MatrixLocation> validNeighbours = Helper.surroundingTiles(matrix, current, false).stream()
            .filter(matrixLocation -> matrix[matrixLocation.getY()][matrixLocation.getX()] == value + 1)
            .collect(Collectors.toCollection(ArrayList::new));

        for (MatrixLocation validNeighbour : validNeighbours) {
            totalFound = pathsFound(matrix, validNeighbour, value + 1, totalFound, endPoints, part2);
        }

        return totalFound;
    }


    public void puzzleTwo() {
        int[][] map = Helper.toIntMatrix("C:\\Users\\agraaff\\Dev\\AdventOfCode2022\\src\\main\\input\\10");

        List<MatrixLocation> allStartLocations = Helper.allLocations(map).stream()
            .filter(matrixLocation -> map[matrixLocation.y][matrixLocation.getX()] == 0)
            .toList();

        int sum = allStartLocations.stream()
            .map(startLocation -> pathsFound(map, startLocation, 0, 0, new ArrayList<>(), true))
            .mapToInt(x -> x)
            .sum();

        System.out.println(sum);
    }


    public static void main(String[] args) {
        Day10 day = new Day10();
        day.puzzleOne();
        day.puzzleTwo();
    }
}
