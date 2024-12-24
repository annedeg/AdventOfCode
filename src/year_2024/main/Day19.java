package year_2024.main;

import helpers.Helper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.stream.Collectors;

public class Day19 {
    HashMap<String, Long> cache = new HashMap<>();

    public void puzzleOne() {
        String input = Helper.readToString(2024, 19);
        String[] split = input.split("\n\n");
        String validStrings = split[0];
        String stringsToCheck = split[1];

        String[] validStringsArray = validStrings.split(", ");
        String[] stringsToCheckArray = stringsToCheck.split("\n");

        ArrayList<String> towels = Arrays.stream(validStringsArray).sorted(Comparator.comparingInt(String::length)).collect(Collectors.toCollection(ArrayList::new));

        Long c = 0L;
        for (String lineToCheck : stringsToCheckArray) {
            Long l = howManySolves(1, lineToCheck, towels);
            if (l > 0) {
                c++;
            }
        }
        System.out.println(c);
    }

    private void puzzleTwo() {
        String input = Helper.readToString(2024, 19);
        String[] split = input.split("\n\n");
        String validStrings = split[0];
        String stringsToCheck = split[1];

        String[] validStringsArray = validStrings.split(", ");
        String[] stringsToCheckArray = stringsToCheck.split("\n");

        ArrayList<String> towels = Arrays.stream(validStringsArray).sorted(Comparator.comparingInt(String::length)).collect(Collectors.toCollection(ArrayList::new));

        Long c = 0L;
        for (String lineToCheck : stringsToCheckArray) {
            c+= howManySolves(1, lineToCheck, towels);
        }
        System.out.println(c);
    }

    public Long howManySolves(int lastChar, String lineToCheck, ArrayList<String> valid) {
        if (cache.containsKey(lineToCheck)) {
            return cache.get(lineToCheck);
        }

       if (lineToCheck.isEmpty()) {
           return 1L;
       }

       if (lastChar > lineToCheck.length()) {
           return 0L;
       }

       if (valid.contains(lineToCheck.substring(0, lastChar))) {
           long total = howManySolves(1, lineToCheck.substring(lastChar), valid) + howManySolves(lastChar+1, lineToCheck, valid);
           cache.put(lineToCheck, total);
           return total;
       }

       long total = howManySolves(lastChar+1, lineToCheck, valid);
       cache.put(lineToCheck, total);
       return total;
    }

    public static void main(String[] args) {
        Day19 day = new Day19();
        day.puzzleOne();
        day.puzzleTwo();
    }
}
