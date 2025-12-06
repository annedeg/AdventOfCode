package year_2025;

import helpers.Helper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

public class Day6 {
    public void puzzleOne() {
        String[] split = Helper.readToString(2025, 6).split("\n");

        ArrayList<Character> operators = new ArrayList<>();
        int[][] integers = new int[split[0].strip().split("( +)").length][split.length-1];

        for (int i = 0; i < split.length; i++) {
            String row = split[i].strip();

            String[] rowItems = row.split("( +)");

            if ((i+1) == split.length) {
                operators.addAll(Arrays.stream(rowItems).map(str -> str.toCharArray()[0]).toList());
            } else {
                ArrayList<Integer> rowIntegers = Arrays.stream(rowItems).map(Integer::parseInt).collect(Collectors.toCollection(ArrayList::new));
                for (int j = 0; j < rowIntegers.size(); j++) {
                    integers[j][i] = rowIntegers.get(j);
                }
            }
        }

        long total = getTotal(integers, operators);

        System.out.println("1: " + total);
    }

    private static long getTotal(int[][] integers, ArrayList<Character> operators) {
        long total = 0;
        for (int i = 0; i < integers.length; i++) {
            char operator = operators.get(i);
            long subTotal = operator == '*' ? 1 : 0;
            for (int j = 0; j < integers[i].length; j++) {
                if (operator == '*') {
                    subTotal *= integers[i][j];
                    continue;
                }

                subTotal += integers[i][j];
            }
            total += subTotal;
        }
        return total;
    }

    private static long getTotal(ArrayList<ArrayList<Integer>> integers, ArrayList<Character> operators) {
        long total = 0;
        for (int i = 0; i < integers.size(); i++) {
            char operator = operators.get(i);
            long subTotal = operator == '*' ? 1 : 0;
            for (int j = 0; j < integers.get(i).size(); j++) {
                if (operator == '*') {
                    subTotal *= integers.get(i).get(j);
                    continue;
                }

                subTotal += integers.get(i).get(j);
            }
            total += subTotal;
        }
        return total;
    }


    public void puzzleTwo() {
        char[][] matrix = Helper.toMatrix(2025, 6);

        ArrayList<ArrayList<Integer>> integers = new ArrayList<>();
        ArrayList<Character> operators = new ArrayList<>();

        boolean isEmptyColumn = false;

        ArrayList<Integer> values = new ArrayList<>();
        char operator = ' ';
        for (int x = matrix[0].length-1; x >= -1; x--) {
            if (isEmptyColumn) {
                //handle all values
                integers.add(new ArrayList<>(values));
                operators.add(operator);
                values.clear();
                operator = ' ';
                isEmptyColumn = false;
                continue;
            }


            StringBuilder valueStringBuilder = new StringBuilder();
            for (int y = 0; y < matrix.length-1; y++) {
                if (matrix[y][x] != '\u0000') {
                    valueStringBuilder.append(matrix[y][x]);
                }
            }

            values.add(Integer.parseInt(valueStringBuilder.toString().strip()));


            if (matrix[matrix.length-1][x] != '\u0000' && matrix[matrix.length-1][x] != ' ') {
                operator = matrix[matrix.length-1][x];
                isEmptyColumn = true;
            }
        }

        long total = getTotal(integers, operators);

        System.out.println("2: " + total);
    }

    public static void main(String[] args) {
        Day6 day = new Day6();
        day.puzzleOne();
        day.puzzleTwo();
    }
}
