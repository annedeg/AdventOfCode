package main;

import main.helpers.Helper;
import main.helpers.MatrixLocation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

public class Day6 {
    char[][] matrix;

    {
        matrix = Helper.toMatrix("C:\\Users\\agraaff\\Dev\\AdventOfCode2022\\src\\main\\input\\06");
    }

    public void puzzleOne() {

    }

    public void puzzleTwo() {

    }

    public MatrixLocation findPlayer() {
        for (int y = 0; y < matrix.length; y++) {
            for (int x = 0; x < matrix[y].length; x++) {
                if (matrix[y][x] == '^')
            }
        }
    }


    public static void main(String[] args) {
        Day6 day = new Day6();
        day.puzzleOne();
        day.puzzleTwo();
    }
}
