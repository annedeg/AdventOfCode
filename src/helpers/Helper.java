package helpers;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class Helper {
    public static String toFileName(int year, int day) {
        return "resources/year_" + year + "/" + day;
    }
    public static String readToString(int year, int day) {
        StringBuilder output = new StringBuilder();

        try (var br = new BufferedReader(new FileReader(toFileName(year, day)))) {
            String line;
            while ((line = br.readLine()) != null) {
                output.append(line).append("\n");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return output.toString();
    }

    public static ArrayList<String> readToStringArrayList(int year, int day) {
        ArrayList<String> output = new ArrayList<>();
        try (var br = new BufferedReader(new FileReader(toFileName(year, day)))) {
            String line;
            while ((line = br.readLine()) != null) {
                output.add(line);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return output;
    }

    public static char[][] toMatrix(int year, int day) {
        ArrayList<String> strings = readToStringArrayList(year, day);
        char[][] charArray = new char[strings.size()][strings.get(0).length()];
        int y = 0;
        for (String line : strings) {
            int x = 0;
            for (Character character : line.toCharArray()) {
                charArray[y][x] = character;
                x += 1;
            }
            y += 1;
        }
        return charArray;
    }

    public static ArrayList<MatrixLocation> surroundingTiles(int[][] matrix, MatrixLocation matrixLocation, boolean addDiagonalNeighbour) {
        int min = 0;
        int maxY = matrix.length-1;
        int maxX = matrix[0].length-1;

        int x = matrixLocation.x;
        int y = matrixLocation.y;


        ArrayList<MatrixLocation> locations = new ArrayList<>();
        locations.add(new MatrixLocation(x-1, y));
        locations.add(new MatrixLocation(x, y-1));
        locations.add(new MatrixLocation(x+1, y));
        locations.add(new MatrixLocation(x, y+1));

        if (addDiagonalNeighbour) {
            locations.add(new MatrixLocation(x-1, y-1));
            locations.add(new MatrixLocation(x+1, y-1));
            locations.add(new MatrixLocation(x+1, y+1));
            locations.add(new MatrixLocation(x-1, y+1));
        }

        return locations.stream()
            .filter(location -> validTile(min, maxY, maxX, location))
            .collect(Collectors.toCollection(ArrayList::new));
    }

    private static boolean validTile(int min, int maxY, int maxX, MatrixLocation matrixLocation) {
        int x = matrixLocation.getX();
        int y = matrixLocation.getY();

        return x >= min && y >= min && x <= maxX && y <= maxY;
    }

    public static int[][] toIntMatrix(int year, int day) {
        ArrayList<String> strings = readToStringArrayList(year, day);
        int[][] charArray = new int[strings.size()][strings.get(0).length()];
        int y = 0;
        for (String line : strings) {
            int x = 0;
            for (Character character : line.toCharArray()) {
                if (character ==  '.') {
                    charArray[y][x] = -1;
                    x+=1;
                    continue;
                }
                charArray[y][x] = Integer.parseInt(String.valueOf(character));
                x += 1;
            }
            y += 1;
        }
        return charArray;
    }

    public static ArrayList<MatrixLocation> allLocations(int[][] matrix) {
        ArrayList<MatrixLocation> matrixLocations = new ArrayList<>();
        for (int y = 0; y < matrix.length; y++) {
            for (int x = 0; x < matrix[y].length; x++) {
                matrixLocations.add(new MatrixLocation(x,y));
            }
        }
        return matrixLocations;
    }

    //    public static char[][] rotateCW(char[][] mat) {
//        final int M = mat.length;
//        final int N = mat[0].length;
//        char[][] ret = new char[N][M];
//        for (int r = 0; r < M; r++) {
//            for (int c = 0; c < N; c++) {
//                ret[c][M-1-r] = mat[r][c];
//            }
//        }
//        return ret;
//    }
    public static char[][] rotateCW(char[][] orig) {
        for (int x = 0; x < 3; x++) {
            orig = rotateCCW(orig);
        }
        return orig;
    }

    public static char[][] rotateCCW(char[][] orig) {
        final int rows = orig.length;
        final int cols = orig[0].length;

        final char[][] neo = new char[cols][rows];

        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                neo[c][rows - 1 - r] = orig[r][c];
            }
        }

        return neo;
    }
}