package year_2025;

import helpers.Helper;
import helpers.MatrixLocation;

import java.util.ArrayList;
import java.util.Arrays;

public class Day4 {
    public void puzzleOne() {
        char[][] chars = Arrays.stream(Helper.readToString(2025, 4).split("\n")).map(String::toCharArray).toArray(char[][]::new);

        int total = 0;
        for (int i = 0; i < chars.length; i++) {
            for (int j = 0; j < chars[0].length; j++) {
                ArrayList<MatrixLocation> matrixLocations = Helper.surroundingTiles(chars, new MatrixLocation(i, j), true);
                if (chars[i][j] == '@' && matrixLocations.stream()
                        .map(matrixLocation -> chars[matrixLocation.getX()][matrixLocation.getY()])
                        .filter(character -> character.equals('@'))
                        .count() < 4) {
                    total++;
                }
            }
        }

        System.out.println("1: " + total);
    }

    public void puzzleTwo() {

        int total = 0;
        char[][] chars = Arrays.stream(Helper.readToString(2025, 4).split("\n")).map(String::toCharArray).toArray(char[][]::new);
        boolean done = false;
        while (!done) {
            ArrayList<MatrixLocation> toDelete = new ArrayList<>();
            for (int i = 0; i < chars.length; i++) {
                for (int j = 0; j < chars[0].length; j++) {
                    ArrayList<MatrixLocation> matrixLocations = Helper.surroundingTiles(chars, new MatrixLocation(i, j), true);
                    if (chars[i][j] == '@' && matrixLocations.stream()
                            .map(matrixLocation -> chars[matrixLocation.getX()][matrixLocation.getY()])
                            .filter(character -> character.equals('@'))
                            .count() < 4) {
                        toDelete.add(new MatrixLocation(i,j));
                    }
                }
            }

            if (toDelete.isEmpty()) {
                done = true;
            }

            toDelete.forEach(ml -> chars[ml.getX()][ml.getY()] = '-');
            total += toDelete.size();
        }

        System.out.println("2: " + total);
    }

    public static void main(String[] args) {
        Day4 day = new Day4();
        day.puzzleOne();
        day.puzzleTwo();
    }
}
