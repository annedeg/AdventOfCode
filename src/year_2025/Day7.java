package year_2025;

import helpers.Helper;
import helpers.MatrixLocation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Day7 {
    public void puzzleOne() {
        char[][] matrix = Helper.toMatrix(2025, 7);

        char[][] matrixCopy = Helper.deepCopy(matrix);

        ArrayList<MatrixLocation> currentBeams = new ArrayList<>();
        MatrixLocation startingLocation = Helper.findFirstCharInMap(matrix, 'S');

        MatrixLocation firstBeam = new MatrixLocation(startingLocation.getX(), startingLocation.getY() + 1);
        matrixCopy[firstBeam.getY()][firstBeam.getX()] = '|';
        currentBeams.add(firstBeam);

        int splitAmount = 0;

        for (int row = 1; row < matrix.length-1; row++) {
            ArrayList<MatrixLocation> newBeams = new ArrayList<>();
            for (MatrixLocation beam : currentBeams) {
                List<MatrixLocation> beams = moveBeamDown(matrix, beam);

                if (beams.size() == 2) {
                    // splitted
                    splitAmount++;
                }
                newBeams.addAll(beams);
            }

            currentBeams = newBeams.stream().distinct().collect(Collectors.toCollection(ArrayList::new));

            for (MatrixLocation beam : currentBeams) {
                matrixCopy[beam.getY()][beam.getX()] = '|';
            }
        }

        Helper.printMap(matrixCopy);
        System.out.println("1: " + splitAmount);
    }

    private List<MatrixLocation> moveBeamDown(char[][] matrix, MatrixLocation beam) {
        char valueBelow = matrix[beam.getY() + 1][beam.getX()];

        ArrayList<MatrixLocation> matrixLocations = new ArrayList<>();
        if (valueBelow == '^') {
            //split

            //left
            if (beam.getX()-1 >= 0) {
                matrixLocations.add(new MatrixLocation(beam.getX()-1, beam.getY()+1));
            }

            //right
            if (beam.getX()+1 < matrix[0].length) {
                matrixLocations.add(new MatrixLocation(beam.getX() + 1, beam.getY()+1));
            }
        } else {
            matrixLocations.add(new MatrixLocation(beam.getX(), beam.getY()+1));
        }

        return matrixLocations;
    }

    public void puzzleTwo() {
        char[][] matrix = Helper.toMatrix(2025, 7);
        long[][] matrixValues = new long[matrix.length][matrix[0].length];

        MatrixLocation startingLocation = Helper.findFirstCharInMap(matrix, 'S');
        matrixValues[startingLocation.getY()][startingLocation.getX()] = 1;

        for (int y = 1; y < matrix.length; y++) {
            for (int x = 0; x < matrix[0].length; x++) {
                if (matrix[y][x] == '^' && matrixValues[y-1][x] != 0) {
                    long val = matrixValues[y-1][x] != 0 ? matrixValues[y-1][x] : 1;

                    if (x > 0) {
                        matrixValues[y][x-1] += val;
                    }

                    if (x < matrix[0].length -1) {
                        matrixValues[y][x+1] += val;
                    }
                }

                if (matrix[y][x] == '.') {
                    matrixValues[y][x] += matrixValues[y-1][x];
                }
            }
        }

        System.out.println("2: " + Arrays.stream(matrixValues[matrixValues.length - 1]).sum());
    }

    public static void main(String[] args) {
        Day7 day = new Day7();
        day.puzzleOne();
        day.puzzleTwo();
    }
}
