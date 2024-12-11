package year_2023;

import helpers.Helper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Day13 extends CodeDay {

    @Override
    public void puzzleOne() {
        var input = Helper.readToString("src/input/day13");


        ArrayList<char[][]> charMatrixes;

        charMatrixes = Arrays.stream(input.split("\n\n"))
            .map(this::createMatrix)
            .collect(Collectors.toCollection(ArrayList::new));

        int sum = charMatrixes.stream()
            .map(this::findAmountAboveReflectionLocation)
            .mapToInt(x -> x)
            .sum();

        System.out.println(sum);
    }

    public char[][] createMatrix(String line) {
        String[] lines = line.split("\n");

        char[][] matrix = new char[lines.length][lines[0].length()];
        int c = 0;
        for (String sl : lines) {
            matrix[c] = sl.toCharArray();
            c+=1;
        }

        return matrix;
    }

    public int findAmountAboveReflectionLocation(char[][] matrix) {
        return findAmountAboveReflectionLocation(matrix, -1);
    }
    public int findAmountAboveReflectionLocation(char[][] matrix, int prev) {
        int amount = findReflectionLocation(matrix, prev, true);
        if (amount == -1) {
            amount = findReflectionLocation(rotate(matrix), prev , false);
            return amount;
        }

        return amount;

    }

    public int findReflectionLocation(char[][] matrix, int prev, boolean gr) {
        int l = matrix.length;
        int h = (int) Math.floor(l / 2);

        for (int i = 0; i <= l; i++) {

            int min = ((h + i) + 1) * (gr ? 100 : 1);

            if (confirmLocation(h + i, matrix) && min != prev) {
                return min;
            }

            int max = ((h - i) + 1) * (gr ? 100 : 1);
            if (confirmLocation(h - i, matrix) && max != prev) {
                return max;
            }
        }

        return -1;
    }

    public boolean confirmLocation(int i, char[][] matrix) {
        int r1 = i;
        boolean any = false;
        for (int x = 0; x < matrix.length / 2; x++) {
            if (x > matrix.length) {
                continue;
            }

            if (r1 - x < 0 || r1 + 1 + x > matrix.length-1) {
                continue;
            }

            if (!Arrays.equals(matrix[r1 - x], matrix[r1 + 1 + x])) {
                return false;
            } else {
                any = true;
            }
        }

        return any;
    }

    public char[][] rotate(char[][] matrix){
        int m = matrix.length;
        int n = matrix[0].length;

        char[][] newMatrix = new char[n][m];

        IntStream.range(0, m).forEach(i ->
            IntStream.range(0, n).forEach(j -> {
                // turn matrix 90º clockwise ⟳
                newMatrix[j][m - 1 - i] = matrix[i][j];
            }));
        return newMatrix;
    }


    @Override
    public void puzzleTwo() {
        var input = Helper.readToString("src/input/day13");
        ArrayList<char[][]> charMatrixes;
        int total = 0;
        charMatrixes = Arrays.stream(input.split("\n\n"))
            .map(this::createMatrix)
            .collect(Collectors.toCollection(ArrayList::new));

        int c = 0;
        for (char[][] m : charMatrixes) {
            int amNormal = findAmountAboveReflectionLocation(m);
            ArrayList<char[][]> allAltMatrixes = createAllAltMatrixes(m);
            boolean g = false;
            for (char[][] mm : allAltMatrixes) {
                int amountAboveReflectionLocation = findAmountAboveReflectionLocation(mm, amNormal);
                if (amountAboveReflectionLocation != -1 && amNormal != amountAboveReflectionLocation) {
                    total += amountAboveReflectionLocation;
                    g = true;
                    break;
                }
            }

            if (!g) {
                System.out.println("nah");
            }
        }

        System.out.println(total);

    }

    public ArrayList<char[][]> createAllAltMatrixes(char[][] input) {
        ArrayList<char[][]> all = new ArrayList<>();
        for (int x = 0; x < input.length; x++) {
            for (int y = 0; y < input[0].length; y++) {
                char[][] clone = cl(input);
                clone[x][y] = clone[x][y] == '.' ? '#' : '.';

                all.add(clone);
            }
        }

        return all;
    }

    public char[][] cl (char[][] matrix) {
        char[][] myInt = new char[matrix.length][];
        for(int i = 0; i < matrix.length; i++)
        {
            myInt[i] = new char[matrix[i].length];
            for (int j = 0; j < matrix[i].length; j++)
            {
                myInt[i][j] = matrix[i][j];
            }
        }
        return myInt;
    }
}