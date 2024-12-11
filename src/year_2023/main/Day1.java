package year_2023.main;

import helpers.CodeDay;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day1 extends CodeDay {
    @Override
    public void puzzleOne() {
        ArrayList<ArrayList<Integer>> values;
        try (var br = new BufferedReader(new FileReader("src/input/dayone.txt"))) {
            values = new ArrayList<>();
            values.add(new ArrayList<>());
            int counter = 0;

            int sum = 0;
            String line;
            while ((line = br.readLine()) != null) {
                String nLine = line.replaceAll("[^\\d]", "");
                String t = String.valueOf(nLine.substring(0, 1) + nLine.substring(nLine.length() - 1, nLine.length()));
                sum += Integer.parseInt(t);
            }

            System.out.println(sum);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void puzzleTwo() {
        ArrayList<ArrayList<Integer>> values;
        try (var br = new BufferedReader(new FileReader("src/input/dayone.txt"))) {
            values = new ArrayList<>();
            values.add(new ArrayList<>());
            int counter = 0;

            int sum = 0;
            String line;
            while ((line = br.readLine()) != null) {
                Pattern pattern = Pattern.compile("[1-9]");

                line = line.replaceAll("one", "on1e");
                line = line.replaceAll("two", "tw2o");
                line = line.replaceAll("three", "thr3e");
                line = line.replaceAll("four", "fo4ur");
                line = line.replaceAll("five", "fi5ve");
                line = line.replaceAll("six", "si6x");
                line = line.replaceAll("seven", "sev7en");
                line = line.replaceAll("eight", "eigh8t");
                line = line.replaceAll("nine", "nin9e");

                Matcher matcher = pattern.matcher(line);

                ArrayList<String> found = new ArrayList<>();
                while (matcher.find()) {
                    String group = matcher.group(0);
                    if (group == null || group.length() == 0) {
                        continue;
                    }

                    found.add(group);
                }


                String s = found.get(0) + found.get(found.size()-1);
                sum += Integer.parseInt(s);
            }

            System.out.println(sum);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
