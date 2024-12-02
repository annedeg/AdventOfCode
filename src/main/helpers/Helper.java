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
}
