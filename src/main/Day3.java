package main;

import main.helpers.Helper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Day3 {
    String input;

    public void handleInput() {
        input = Helper.readToString("C:\\Users\\agraaff\\Dev\\AdventOfCode2022\\src\\main\\input\\03");
    }

    public void puzzleOne() {
        handleInput();
        System.out.println(getInstructionValue(input));
    }

    public void puzzleTwo() {
        handleInput();
        System.out.println(getInstructionValueWithDont(input));
    }

    private int getInstructionValueWithDont(String input) {
        Pattern compile = Pattern.compile("mul\\(([0-9]{1,3}),([0-9]{1,3})\\)|(don't\\(\\))|(do\\(\\))");
        Matcher matcher = compile.matcher(input);
        boolean disable = false;
        int total = 0;
        while (matcher.find()) {
            if (matcher.group(0).contains("don't")) {
                disable = true;
                continue;
            }

            if (matcher.group(0).equals("do()")) {
                disable = false;
                continue;
            }

            if (!disable) {
                total += Integer.parseInt(matcher.group(1)) * Integer.parseInt(matcher.group(2));
            }
        }

        return total;
    }

    public static void main(String[] args) {
        Day3 day = new Day3();
        day.puzzleOne();
        day.puzzleTwo();
    }

    public int getInstructionValue(String input) {
        Pattern compile = Pattern.compile("mul\\(([0-9]{1,3}),([0-9]{1,3})\\)");
        Matcher matcher = compile.matcher(input);

        int total = 0;
        while (matcher.find()) {
            total += Integer.parseInt(matcher.group(1)) * Integer.parseInt(matcher.group(2));
        }

        return total;
    }
}
