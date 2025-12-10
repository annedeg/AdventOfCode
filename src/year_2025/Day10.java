package year_2025;

import helpers.Helper;
import helpers.MatrixLocation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class Day10 {
    public void puzzleOne() {
        int result = Helper.readToStringArrayList(2025, 10).stream()
                .map(MachinePuzzle::new)
                .map(MachinePuzzle::solveLowest)
                .mapToInt(Integer::intValue)
                .sum();

        System.out.println("1: " + result);
    }

    public void puzzleTwo() {
        int result = Helper.readToStringArrayList(2025, 10).stream()
                .map(SecondMachinePuzzle::new)
                .map(SecondMachinePuzzle::solveLowest)
                .mapToInt(Integer::intValue)
                .sum();

        System.out.println("1: " + result);
    }

    public static void main(String[] args) {
        Day10 day = new Day10();
        day.puzzleOne();
        day.puzzleTwo();
    }
}

class MachinePuzzle {
    int currentLowest = Integer.MAX_VALUE;
    char[] desiredState;
    ArrayList<Set<Integer>> buttonWiring = new ArrayList<>();

    MachinePuzzle(String inputLine) {
        String[] inputLineSplitted = inputLine.split(" ");

        desiredState = inputLineSplitted[0].substring(1, inputLineSplitted[0].length()-1).toCharArray();

        for (int i = 1; i < inputLineSplitted.length-1; i++) {
            buttonWiring.add(Arrays.stream(inputLineSplitted[i].substring(1, inputLineSplitted[i].length() - 1).split(","))
                    .map(Integer::parseInt)
                    .collect(Collectors.toCollection(HashSet::new)));
        }
    }

    int solveLowest() {
        int lowest = Integer.MAX_VALUE;
        for (Set<Integer> startMove : buttonWiring) {
            char[] initialState = new char[desiredState.length];
            for (int i = 0; i < desiredState.length; i++) {
                initialState[i] =  '.';
            }
            tryLowest(initialState, startMove, new ArrayList<>());
        }

        return this.currentLowest;
    }

    int tryLowest(char[] currentState, Set<Integer> currentTry, ArrayList<Set<Integer>> history) {
        if (Arrays.equals(desiredState, currentState)) {
            // doel bereikt, stop!
            String string = history.stream().map(String::valueOf).collect(Collectors.joining(" "));
            return history.size();
        }

        if (history.size() + 1 >= currentLowest || (!history.isEmpty() && history.get(history.size()-1).equals(currentTry)) || history.size() > 10) {
            // prevent double actions after each other
            return Integer.MAX_VALUE;
        }

        history.add(currentTry);

        for (Integer buttonToChange : currentTry) {
            currentState[buttonToChange] = currentState[buttonToChange] == '.' ? '#' : '.';
        }

        for (Set<Integer> possibleTry : buttonWiring) {
            int i = tryLowest(Helper.deepCopy(currentState), possibleTry, new ArrayList<>(history));

            if (i < currentLowest) {
                currentLowest = i;
            }
        }

        return Integer.MAX_VALUE;
    }
}

class SecondMachinePuzzle {
    int[] desiredState;
    int currentLowest = Integer.MAX_VALUE;
    ArrayList<Set<Integer>> buttonWiring = new ArrayList<>();

    SecondMachinePuzzle(String inputLine) {
        String[] inputLineSplitted = inputLine.split(" ");

        desiredState = Arrays.stream(inputLineSplitted[inputLineSplitted.length - 1].substring(1, inputLineSplitted[0].length() - 1).split(","))
                .mapToInt(Integer::parseInt)
                .toArray();

        for (int i = 1; i < inputLineSplitted.length-1; i++) {
            buttonWiring.add(Arrays.stream(inputLineSplitted[i].substring(1, inputLineSplitted[i].length() - 1).split(","))
                    .map(Integer::parseInt)
                    .collect(Collectors.toCollection(HashSet::new)));
        }
    }

    int tryLowest(int[] currentState, Set<Integer> currentTry, ArrayList<Set<Integer>> history) {
        if (Arrays.equals(currentState, desiredState)) {
            return history.size();
        }

        for (Integer tryPart : currentTry) {
            currentState[tryPart]++;
        }

        history.add(currentTry);

        for (Set<Integer> possibleTry : buttonWiring) {
            int tryLowest = tryLowest(currentState, possibleTry, history);

            if (tryLowest < currentLowest) {
                currentLowest = tryLowest;
            }
        }

        return Integer.MAX_VALUE;
    }

    int solveLowest() {
        for (Set<Integer> startMove : buttonWiring) {
            int[] initialState = new int[desiredState.length];
            for (int i = 0; i < desiredState.length; i++) {
                initialState[i] =  0;
            }
            tryLowest(initialState, startMove, new ArrayList<>());
        }

        return this.currentLowest;
    }
}