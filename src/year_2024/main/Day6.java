package year_2024.main;

import year_2024.main.helpers.Direction;
import year_2024.main.helpers.Helper;
import year_2024.main.helpers.MatrixLocation;

import java.util.ArrayList;

public class Day6 {
    char[][] matrix;

    Direction currDirection = Direction.UP;

    {
        matrix = Helper.toMatrix(2024, 6);
    }

    public void puzzleOne() {
        while (!finished()) {
            movePlayer();
            matrix = Helper.rotateCW(matrix);
        }

        int total = 0;
        for (int y = 0; y < matrix.length; y++) {
            for (int x = 0; x < matrix[y].length; x++) {
                if (matrix[y][x] == 'X' || matrix[y][x] == '^') {
                    total += 1;
                }
            }
        }

        System.out.println(total);
//        long count = history.stream().distinct().count();
//        System.out.println(count);
    }

    public void puzzleTwo() {
        while (!finished()) {
            movePlayer();
            matrix = Helper.rotateCW(matrix);
            currDirection = currDirection.getNext(currDirection);
        }

        while (currDirection != Direction.RIGHT) {
            matrix = Helper.rotateCW(matrix);
            currDirection = currDirection.getNext(currDirection);
        }

        ArrayList<MatrixLocation> todo = new ArrayList<>();
        for (int y = 0; y < matrix.length; y++) {
            for (int x = 0; x < matrix[y].length; x++) {
                if (matrix[y][x] == 'X' || matrix[y][x] == '^') {
                    todo.add(new MatrixLocation(x, y));
                }
            }
        }


        matrix = Helper.toMatrix(2024, 6);
        MatrixLocation currentLocation = findPlayer();
        int total = 0;

        for (MatrixLocation location : todo) {
            int x = location.getX();
            int y = location.getY();

            if (currentLocation.getY() == y && currentLocation.getX() == x) {
                continue;
            }

            ArrayList<MatrixLocation> historyBeforeRotate = new ArrayList<>();

            matrix = Helper.toMatrix(2024, 6);
            matrix[y][x] = '0';

            while (!finished()) {
                movePlayer();
                historyBeforeRotate.add(findPlayer());
                matrix = Helper.rotateCW(matrix);
                currDirection = currDirection.getNext(currDirection);

                if (loopFound(historyBeforeRotate)) {
                    System.out.println("y:" + y + " x:" + x);
                    total += 1;
                    break;
                }
            }
        }

//            System.out.println(y);


        System.out.println(total);

    }

    private boolean loopFound(ArrayList<MatrixLocation> history) {
        boolean b = history.stream().distinct().count() != history.size();
        return b;
    }

    public MatrixLocation findPlayer() {
        for (int y = 0; y < matrix.length; y++) {
            for (int x = 0; x < matrix[y].length; x++) {
                if (matrix[y][x] == '^') {
                    return new MatrixLocation(x, y, currDirection);
                }
            }
        }
        return null;
    }

    public void movePlayer() {
        MatrixLocation playerLocation = findPlayer();
        int cx = playerLocation.getX();
        int cy = playerLocation.getY();

        while (true) {
            if (cy - 1 == 0 && matrix[cy - 1][cx] != '#' && matrix[cy - 1][cx] != '0') {
                //fin
                matrix[playerLocation.getY()][playerLocation.getX()] = 'X';
                matrix[cy][cx] = 'X';
                matrix[cy - 1][cx] = '^';
                return;
            }
            char matrix1 = matrix[cy - 1][cx];
            if (matrix1 == '#' || matrix1 == '0') {
                //ready
                matrix[playerLocation.getY()][playerLocation.getX()] = 'X';
                matrix[cy][cx] = '^';
                return;
            }

            matrix[cy][cx] = 'X';
            cy--;
        }
    }

    public void movePlayerAlt() {
        MatrixLocation playerLocation = findPlayer();
        int cx = playerLocation.getX();
        int cy = playerLocation.getY();

        while (true) {
            if (cy - 1 == 0 && matrix[cy - 1][cx] != '#') {
                //fin
                matrix[playerLocation.getY()][playerLocation.getX()] = 'X';
                matrix[cy][cx] = 'X';
                matrix[cy - 1][cx] = '^';
                return;
            }
            char matrix1 = matrix[cy - 1][cx];
            if (matrix1 == '#') {
                //ready
                matrix[playerLocation.getY()][playerLocation.getX()] = 'X';
                matrix[cy][cx] = '^';
                return;
            }

            matrix[cy][cx] = 'X';
            cy--;
        }
    }

    public boolean finished() {
        MatrixLocation player = findPlayer();
        return (player.getY() >= matrix.length || player.getX() == 0 || player.getY() == 0);
    }

    public static void main(String[] args) {
        Day6 day = new Day6();
        day.puzzleOne();
        day.puzzleTwo();
    }
}
