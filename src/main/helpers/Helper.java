package main.helpers;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Helper {
    public static String readToString(String fileName) {
        StringBuilder output = new StringBuilder();

        try(var br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while((line = br.readLine()) != null) {
               output.append(line).append("\n");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return output.toString();
    }

    public static ArrayList<String> readToStringArrayList(String fileName) {
        ArrayList<String> output = new ArrayList<>();
        try(var br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while((line = br.readLine()) != null) {
                output.add(line);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return output;
    }

    public static char[][] toMatrix(String fileName) {
        ArrayList<String> strings = readToStringArrayList(fileName);
        char[][] charArray = new char[strings.size()][strings.get(0).length()];
        int y = 0;
        for (String line : strings) {
            int x = 0;
            for (Character character : line.toCharArray()) {
                charArray[y][x] = character;
                x+=1;
            }
            y+=1;
        }
        return charArray;
    }

    public static char[][] rotateCW(char[][] mat) {
        final int M = mat.length;
        final int N = mat[0].length;
        char[][] ret = new char[N][M];
        for (int r = 0; r < M; r++) {
            for (int c = 0; c < N; c++) {
                ret[c][M-1-r] = mat[r][c];
            }
        }
        return ret;
    }
}
