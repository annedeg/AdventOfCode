package main;

import main.helpers.Helper;

import java.util.*;
import java.util.stream.Collectors;

public class Day7 {

    public void puzzleOne() {
        ArrayList<String> strings = Helper.readToStringArrayList("C:\\Users\\adgra\\IdeaProjects\\AdventOfCode2022\\src\\input\\07");

        long total = 0;
        for (String string : strings) {
            String[] split = string.split(": ");
            long res = Long.parseLong(split[0]);
            Deque<Long> values = Arrays.stream(split[1].split(" ")).map(Long::parseLong).collect(Collectors.toCollection(ArrayDeque::new));
            char[] operations = new char[]{'+', '*'};

            Long startValue = values.pop();
            boolean result = calcWithOp(res, values, startValue, operations, new ArrayList<>());
            if (result) {
                total+=res;
            }
        }
        System.out.println(total);
    }

    boolean calcWithOp(long search, Deque<Long> todo, long value, char[] operations, ArrayList<Character> opHistory) {
        if (todo.isEmpty()) {
            return (value == search);
        }

        Long pop = todo.pop();

        for (char operation : operations) {
            long tempValue = switch (operation) {
                case '+': {
                    opHistory.add(operation);
                    yield value + pop;
                }

                case '*': {
                    opHistory.add(operation);
                    yield value * pop;
                }

                case '|': {
                    opHistory.add(operation);
                    yield Long.parseLong(value + String.valueOf(pop));
                }
                default:
                    throw new IllegalStateException("Unexpected value: " + operation);
            };

            ArrayDeque<Long> clone = new ArrayDeque<>(todo);
            boolean res = calcWithOp(search, clone, tempValue, operations, opHistory);
            if (res) {
                return true;
            }
        }

        return false;
    }

    public void puzzleTwo() {
        ArrayList<String> strings = Helper.readToStringArrayList("C:\\Users\\adgra\\IdeaProjects\\AdventOfCode2022\\src\\input\\07");

        long total = 0;
        for (String string : strings) {
            String[] split = string.split(": ");
            long res = Long.parseLong(split[0]);
            Deque<Long> values = Arrays.stream(split[1].split(" ")).map(Long::parseLong).collect(Collectors.toCollection(ArrayDeque::new));
            char[] operations = new char[]{'+', '*', '|'};

            Long startValue = values.pop();
            boolean result = calcWithOp(res, values, startValue, operations, new ArrayList<>());
            if (result) {
                total+=res;
            }
        }
        System.out.println(total);
    }

    public static void main(String[] args) {
        Day7 day = new Day7();
        day.puzzleOne();
        day.puzzleTwo();
    }
}
