package year_2024.main;

import helpers.Helper;
import helpers.MatrixLocation;

import java.util.ArrayList;

public class Day12 {

    public void puzzleOne() {
        char[][] input = Helper.toMatrix(2024, 12);

        int total = calculateChunkz(input).stream()
                .mapToInt(list -> area(list) * perimeter(input, list))
                .sum();

        System.out.println(total);
    }

    public void puzzleTwo() {
        char[][] input = Helper.toMatrix(2024, 12);


        int total = calculateChunkz(input).stream()
                .mapToInt(list -> area(list) * numberOfSides(input, list))
                .sum();

        System.out.println(total);
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

        Helper.surroundingTiles(matrix, location, false)
                .forEach(loc -> findAllChunkz(matrix, toFind, loc, history, values));

        return values;
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
    private ArrayList<ArrayList<MatrixLocation>> calculateChunkz(char[][] input) {
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
        return chunks;
    }

    private int numberOfSides(char[][] input, ArrayList<MatrixLocation> list) {
        return list.stream().mapToInt(loc -> sides(input, loc)).sum();
    }

    private int sides(char[][] input, MatrixLocation location) {
        int sides = 0;

        int y = location.getY();
        int x = location.getX();

        char toFind = input[y][x];

        char u = exToChar(input, y - 1, x);
        char l = exToChar(input, y, x - 1);
        char d = exToChar(input, y + 1, x);
        char r = exToChar(input, y, x + 1);

        char ul = exToChar(input, y-1, x-1);
        char ur = exToChar(input, y-1, x+1);
        char dl = exToChar(input, y+1, x-1);
        char dr = exToChar(input, y+1, x+1);

        if ((u != toFind && l != toFind) || (u == toFind && l == toFind && ul != toFind)) {
            sides+=1;
        }
        if ((u != toFind && r != toFind) || (u == toFind && r == toFind && ur != toFind)) {
            sides+=1;
        }
        if ((d != toFind && l != toFind) || (d == toFind && l == toFind && dl != toFind)) {
            sides+=1;
        }
        if ((d != toFind && r != toFind) || (d == toFind && r == toFind && dr != toFind)) {
            sides+=1;
        }
        return sides;
    }

    private char exToChar(char[][] input, int i, int i1) {
        try {
            return input[i][i1];
        } catch (IndexOutOfBoundsException e) {
            return '.';
        }
    }

    public static void main(String[] args) {
        Day12 day = new Day12();
        day.puzzleOne();
        day.puzzleTwo();
    }
}
