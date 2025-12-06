package helpers;

import java.io.File;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.stream.Collectors;

public class Helper {
    public static String toFileName(int year, int day) {
        String fileName = "resources/year_" + year + "/" + day;
        checkOrGet(fileName, year, day);
        return fileName;
    }

    public static void checkOrGet(String location, int year, int day) {
        File file = new File(location);
        if (file.exists() && !file.isDirectory()) {
            return;
        }


        try {
            String html = getHTML("https://adventofcode.com/" + year + "/day/" + day + "/input");
            try (var fileWriter = new BufferedWriter(new FileWriter(location))) {
                fileWriter.append(html);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static String getHTML(String urlToRead) throws Exception {
        StringBuilder result = new StringBuilder();
        URL url = new URL(urlToRead);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");

        StringBuilder stringBuilder = new StringBuilder();
        try (var conf = new BufferedReader(new FileReader("config/config.conf"))) {
            String line;
            while ((line = conf.readLine()) != null) {
                if (line.startsWith("#")) {
                    continue;
                }
                stringBuilder.append(line);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        conn.setRequestProperty("Cookie", stringBuilder.toString());
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(conn.getInputStream()))) {
            for (String line; (line = reader.readLine()) != null; ) {
                result.append(line).append("\n");
            }
        }
        return result.toString();
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
        return toMatrix(strings);
    }

    public static char[][] toMatrix(ArrayList<String> strings) {
        int longestString = strings.stream()
                .sorted(Comparator.comparing(String::length).reversed())
                .toList().get(0).length();
        char[][] charArray = new char[strings.size()][longestString];
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
        int maxY = matrix.length - 1;
        int maxX = matrix[0].length - 1;

        int x = matrixLocation.x;
        int y = matrixLocation.y;


        ArrayList<MatrixLocation> locations = new ArrayList<>();
        locations.add(new MatrixLocation(x - 1, y));
        locations.add(new MatrixLocation(x, y - 1));
        locations.add(new MatrixLocation(x + 1, y));
        locations.add(new MatrixLocation(x, y + 1));

        if (addDiagonalNeighbour) {
            locations.add(new MatrixLocation(x - 1, y - 1));
            locations.add(new MatrixLocation(x + 1, y - 1));
            locations.add(new MatrixLocation(x + 1, y + 1));
            locations.add(new MatrixLocation(x - 1, y + 1));
        }

        return locations.stream()
                .filter(location -> validTile(min, maxY, maxX, location))
                .collect(Collectors.toCollection(ArrayList::new));
    }

    public static ArrayList<MatrixLocation> surroundingTiles(char[][] matrix, MatrixLocation matrixLocation, boolean addDiagonalNeighbour) {
        int min = 0;
        int maxY = matrix.length - 1;
        int maxX = matrix[0].length - 1;

        int x = matrixLocation.x;
        int y = matrixLocation.y;


        ArrayList<MatrixLocation> locations = new ArrayList<>();
        locations.add(new MatrixLocation(x - 1, y));
        locations.add(new MatrixLocation(x, y - 1));
        locations.add(new MatrixLocation(x + 1, y));
        locations.add(new MatrixLocation(x, y + 1));

        if (addDiagonalNeighbour) {
            locations.add(new MatrixLocation(x - 1, y - 1));
            locations.add(new MatrixLocation(x + 1, y - 1));
            locations.add(new MatrixLocation(x + 1, y + 1));
            locations.add(new MatrixLocation(x - 1, y + 1));
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

    public static void printMap(char[][] map) {
        int x = 0;
        System.out.print(" yx ");
        for (char[] chars : map) {
            System.out.printf(" %02d ", x);
            x++;
        }
        System.out.println();

        int y = 0;
        for (char[] chars : map) {
            System.out.printf(" %02d ", y);
            for (char aChar : chars) {
                System.out.printf(" %c  ", aChar);
            }
            System.out.println();
            y++;
        }
        System.out.println();
    }

    public static void printMap(int[][] map) {
        int x = 0;
        System.out.print(" yx ");
        for (int[] chars : map) {
            System.out.printf(" %02d ", x);
            x++;
        }
        System.out.println();

        int y = 0;
        for (int[] chars : map) {
            System.out.printf(" %02d ", y);
            for (int aChar : chars) {
                System.out.printf(" %02d ", aChar);
            }
            System.out.println();
            y++;
        }
        System.out.println();
    }

    public static int[][] toIntMatrix(int year, int day) {
        ArrayList<String> strings = readToStringArrayList(year, day);
        int[][] charArray = new int[strings.size()][strings.get(0).length()];
        int y = 0;
        for (String line : strings) {
            int x = 0;
            for (Character character : line.toCharArray()) {
                if (character.toString().equals(" ")) {
                    continue;
                }
                if (character == '.') {
                    charArray[y][x] = -1;
                    x += 1;
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
                matrixLocations.add(new MatrixLocation(x, y));
            }
        }
        return matrixLocations;
    }

    public static ArrayList<MatrixLocation> allLocations(char[][] matrix) {
        ArrayList<MatrixLocation> matrixLocations = new ArrayList<>();
        for (int y = 0; y < matrix.length; y++) {
            for (int x = 0; x < matrix[y].length; x++) {
                matrixLocations.add(new MatrixLocation(x, y));
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

    public static MatrixLocation findFirstCharInMap(char[][] map, char c) {
        for (int y = 0; y < map.length; y++) {
            for (int x = 0; x < map[y].length; x++) {
                if (map[y][x] == c) {
                    return new MatrixLocation(x,y);
                }
            }
        }
        return null;
    }

    public static char[][] deepCopy(char[][] original) {
        if (original == null) {
            return null;
        }

        return Arrays.stream(original).map(chars -> Arrays.copyOf(chars, chars.length)).toArray(char[][]::new);
    }
}
