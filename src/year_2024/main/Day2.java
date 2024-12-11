package year_2024.main;

import helpers.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class Day2 {
    ArrayList<ArrayList<Integer>> levels;

    public void handleInput() {
        ArrayList<String> rows = Helper.readToStringArrayList(2024, 2);
        levels = rows.stream().map(str -> Arrays.stream(str.split(" ")).map(Integer::parseInt)).map(integerStream -> integerStream.collect(Collectors.toCollection(ArrayList::new))).collect(Collectors.toCollection(ArrayList::new));
    }
    public void puzzleOne() {
        handleInput();
        long count = levels.stream().filter(this::safe).count();
        System.out.println(count);
    }

    public boolean safe(List<Integer> integers) {
        boolean negative = false;
        boolean positive = false;

        for (int i = 1; i < integers.size(); i++) {
            int prev = integers.get(i-1);
            int cur = integers.get(i);

            int change = cur - prev;
            if (change < 0) {
                negative = true;
            }

            if (change > 0) {
                positive = true;
            }

            if (negative && positive) {
                return false;
            }

            if (Math.abs(change) < 1 || Math.abs(change) > 3) {
                return false;
            }
        }

        return true;
    }

    public boolean safeDampener(List<Integer> integers) {
        if (safe(integers)) {
            return true;
        }

        for (int toRemove = 0; toRemove < integers.size(); toRemove++) {
            ArrayList<Integer> clone = new ArrayList<>(integers);
            Collections.copy(clone, integers);
            clone.remove(toRemove);
            if (safe(clone)) {
                return true;
            }
        }

        return false;
    }

    public void puzzleTwo() {
        handleInput();
        long count = levels.stream().filter(this::safeDampener).count();
        System.out.println(count);
    }

    public static void main(String[] args) {
        Day2 day2 = new Day2();
        day2.puzzleOne();
        day2.puzzleTwo();
    }
}
