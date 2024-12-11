package year_2023.main;

import helpers.*;

import java.util.*;
import java.util.stream.Collectors;

public class Day21 extends CodeDay {
    ArrayList<Position> positions = new ArrayList<Position>();

    @Override
    public void puzzleOne() {
        var input = Helper.readToStringArrayList(2023, 21);
        char[][] matrix = input.stream()
                .map(String::toCharArray)
                .toArray(size -> new char[size][input.size()]);

        for (int x = 0; x < matrix.length; x++) {
            for (int y = 0; y < matrix[0].length; y++) {
                if (matrix[x][y] == 'S') {
                    matrix[x][y] = 'O';
                    positions.add(new Position(x,y));
                }
            }
        }

        for (int step = 0; step < 64; step++) {
            positions = positions.stream()
                    .map(position -> position.getAllNextPositions(matrix))
                    .flatMap(ArrayList::stream)
                    .filter(position -> position.valid(matrix))
                    .distinct()
                    .collect(Collectors.toCollection(ArrayList::new));

            for (int x = 0; x < matrix.length; x++) {
                for (int y = 0; y < matrix[0].length; y++) {
                    if (matrix[x][y] == 'O') {
                        matrix[x][y] = '.';
                    }

                    if (positions.contains(new Position(x,y))) {
                        matrix[x][y] = 'O';
                    }
                }
            }
        }

        int count = 0;

        for (int x = 0; x < matrix.length; x++) {
            for (int y = 0; y < matrix[0].length; y++) {
                if (matrix[x][y] == 'O') {
                    count++;
                }
            }
        }

        System.out.println(count);
    }

    class Position {
        int x;
        int y;
        public Position(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public ArrayList<Position> getAllNextPositions(char[][] matrix) {
            ArrayList<Position> positions = new ArrayList<>();

            positions.add(new Position(x+1, y));
            positions.add(new Position(x-1, y));
            positions.add(new Position(x, y+1));
            positions.add(new Position(x, y-1));

            return positions.stream().filter(position -> position.valid(matrix)).collect(Collectors.toCollection(ArrayList::new));
        }

        public boolean valid(char[][] matrix) {
            if (!(x >= 0 && y >= 0 && x < matrix.length && y < matrix.length)) {
                return false;
            }

            if (matrix[x][y] == '#') {
                return false;
            }

            return true;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Position position = (Position) o;
            return x == position.x && y == position.y;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y);
        }
    }
    @Override
    public void puzzleTwo() {
        var input = Helper.readToStringArrayList(2023, 21);
        char[][] matrix = input.stream()
                .map(String::toCharArray)
                .toArray(size -> new char[size][input.size()]);

        int increase = 99;


        for (int x = 0; x < matrix.length; x++) {
            for (int y = 0; y < matrix[0].length; y++) {
                if (matrix[x][y] == 'S') {
                    matrix[x][y] = '.';
                }
            }
        }

        char[][] superMatrix = new char[matrix.length * increase][matrix[0].length*increase];

        for (int rx = 0; rx < matrix.length*increase; rx++) {
            for (int ry = 0; ry < matrix[0].length*increase;ry++) {
                superMatrix[rx][ry] = matrix[rx % matrix.length][ry % matrix.length];
            }
        }

        superMatrix[(superMatrix.length-1) / 2][(superMatrix[0].length-1) / 2] = 'S';

        for (int x = 0; x < superMatrix.length; x++) {
            for (int y = 0; y < superMatrix[0].length; y++) {
                if (superMatrix[x][y] == 'S') {
                    superMatrix[x][y] = '.';
                    positions.add(new Position(x,y));
                }
            }
        }

//        for (char[] line : superMatrix) {
//            for (char c : line) {
//                System.out.print(c);
//            }
//            System.out.println();
//        }

        for (int step = 0; step < 100; step++) {
            positions = positions.stream()
                    .map(position -> position.getAllNextPositions(superMatrix))
                    .flatMap(ArrayList::stream)
                    .filter(position -> position.valid(superMatrix))
                    .distinct()
                    .collect(Collectors.toCollection(ArrayList::new));

            for (int x = 0; x < superMatrix.length; x++) {
                for (int y = 0; y < superMatrix[0].length; y++) {
                    if (superMatrix[x][y] == 'O') {
                        superMatrix[x][y] = '.';
                    }

                    if (positions.contains(new Position(x,y))) {
                        superMatrix[x][y] = 'O';
                    }
                }
            }

            long count = 0;

            for (int x = 0; x < superMatrix.length; x++) {
                for (int y = 0; y < superMatrix[0].length; y++) {
                    if (superMatrix[x][y] == 'O') {
                        count++;
                    }
                }
            }

            System.out.println(count);
        }




//        System.out.println(count);
    }
}
