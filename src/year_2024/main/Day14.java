package year_2024.main;

import helpers.Helper;
import helpers.MatLocVel;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class Day14 {
    private static MatLocVel stringToMatLocVel(String str, int maxX, int maxY) {
        String[] line = str.split(" ");
        String[] p = line[0].split("=")[1].split(",");
        String[] v = line[1].split("=")[1].split(",");
        return new MatLocVel(Integer.parseInt(p[0]), Integer.parseInt(p[1]), Integer.parseInt(v[0]), Integer.parseInt(v[1]), maxX, maxY);
    }

    public void puzzleOne() {
        int maxX = 101;
        int maxY = 103;
        int iterations = 10000;
        int maxIterations = 10000;
        ArrayList<String> strings = Helper.readToStringArrayList(2024, 14);
        ArrayList<MatLocVel> matLocVels = strings.stream()
                .map(str -> stringToMatLocVel(str, maxX, maxY))
                .collect(Collectors.toCollection(ArrayList::new));


        char[][] matrix = initMatrix(maxX, maxY);
        updateView(matrix, matLocVels);
        printMatrix(matrix);

        int seconds =0;
        for (;iterations>0;iterations--) {
            matrix = initMatrix(maxX, maxY);
            matLocVels.forEach(MatLocVel::getNextPosition);

            updateView(matrix, matLocVels);

            boolean all0or1 = true;
            for (char[] row : matrix) {
                for (char c : row) {
                    if (!(Integer.parseInt(String.valueOf(c)) <= 1)) {
                        all0or1 = false;
                        break;
                    }
                }
            }

            if (all0or1) {
                printMatrix(matrix);
                System.out.println(seconds);
                System.out.println(maxIterations - iterations);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }

            seconds+=1;
        }

        int halfMaxX = (((maxX - 1) / 2)) - 1;
        int halfMaxY = (((maxY - 1) / 2)) - 1;
        int halfMaxx2 = (((maxX - 1) / 2)) + 1;
        int halfMaxy2 = (((maxY - 1) / 2)) + 1;

        int q1 = calcQuadrant(matLocVels, 0, halfMaxX, 0, halfMaxY);
        int q2 = calcQuadrant(matLocVels, halfMaxx2, maxX, 0, halfMaxY);
        int q3 = calcQuadrant(matLocVels, 0, halfMaxX, halfMaxy2, maxY);
        int q4 = calcQuadrant(matLocVels, halfMaxx2, maxX, halfMaxy2, maxY);

        System.out.println(q1);
        System.out.println(q2);
        System.out.println(q3);
        System.out.println(q4);
        System.out.println();
        System.out.println(q1*q2*q3*q4);
    }

    private char[][] initMatrix(int maxX, int maxY) {
        char[][] matrix = new char[maxY][maxX];
        for (int y = 0; y < maxY; y++) {
            for (int x = 0; x < maxX; x++) {
                matrix[y][x] = '0';
            }
        }
        return matrix;
    }

    public void printMatrix(char[][] matrix){
        for (char[] chars : matrix) {
            for (char aChar : chars) {
                if (aChar == '0') {
                    System.out.print('.');
                } else {
                    System.out.print(aChar);
                }
            }
            System.out.println();
        }
        System.out.println();
    }

    public void updateView(char[][] matrix, ArrayList<MatLocVel> matLocVels) {
        matLocVels.forEach(matLocVel -> {
            char curValue = matrix[matLocVel.getY()][matLocVel.getX()];
            matrix[matLocVel.getY()][matLocVel.getX()] = String.valueOf(Integer.parseInt(String.valueOf(curValue)) + 1).charAt(0);
        });
    }

    public int calcQuadrant(ArrayList<MatLocVel> matLocVels, int xm, int xM, int ym, int yM) {
        System.out.println("tussen:" + xm + ":" + ym + " en " + xM + ":" + yM);
        return (int) matLocVels.stream().filter(matLocVel -> matLocVel.getX() >= xm && matLocVel.getX() <= xM && matLocVel.getY() >= ym && matLocVel.getY() <= yM).count();
    }

    public void puzzleTwo() {

    }

    public static void main(String[] args) {
        Day14 day = new Day14();
        day.puzzleOne();
        day.puzzleTwo();
    }
}
