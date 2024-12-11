package year_2024.main;

import helpers.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day4 {
    String input;
    ArrayList<ArrayList<Character>> characterMatrix;

    public void handleInput() {
        characterMatrix = new ArrayList<>();
        input = Helper.readToString(2024, 4);
        int x = 0;
        for (String line : input.split("\n")) {
            characterMatrix.add(new ArrayList<>());
            for (Character character : line.toCharArray()) {
                characterMatrix.get(x).add(character);
            }
            x++;
        }

    }

    public void puzzleOne() {
        handleInput();
        int allXmasses = findAllXmasses();
        System.out.println(allXmasses);
        System.out.println("t");
//        System.out.println(getInstructionValue(resources.year_2024.input));
    }

    public int findAllXmasses() {
        int total = 0;
        for (int x = 0; x < characterMatrix.size(); x++) {
            for (int y = 0; y < characterMatrix.get(x).size(); y++) {
                Character currentChar = characterMatrix.get(x).get(y);
//                System.out.println("zoek nu " + x + ":" + y);
                if (currentChar != 'X') {
                    //Invalid start
                    continue;
                }

                for (Direction direction : Direction.values()) {
                    if (getCharDirectionValid(x, y, direction)) {
                        total += 1;
//                        System.out.println("found valid: " + x + ":" + y);
                    }
                }
            }
        }

        return total;
    }

    public int findAllXXmasses() {
        int total = 0;
        for (int x = 0; x < characterMatrix.size(); x++) {
            for (int y = 0; y < characterMatrix.get(x).size(); y++) {//
                if (getSurroundingValidChar(x, y)) {
                    total += 1;
                }
            }
        }

        return total;
    }

    private boolean getSurroundingValidChar(int x, int y) {
        try {
            ArrayList<Character> one = new ArrayList<>(List.of(getChar(x - 1, y - 1), getChar(x,y), getChar(x + 1, y + 1)));
            ArrayList<Character> two = new ArrayList<>(List.of(getChar(x + 1, y - 1), getChar(x,y), getChar(x - 1, y + 1)));

            return one.contains('M') && one.contains('A') && one.contains('S') && two.contains('M') && two.contains('A') && two.contains('S') && getChar(x,y).equals('A');
        } catch (Exception e) {
            return false;
        }
    }

    public Character getChar(int x, int y) {
        return characterMatrix.get(x).get(y);
    }

    public boolean getCharDirectionValid(int x, int y, Direction direction) {
        String result = "";
        if (direction == Direction.LEFT) {
            try {
                result = Stream.of(getChar(x, y), getChar(x, y - 1), getChar(x, y - 2), getChar(x, y - 3)).map(Object::toString).collect(Collectors.joining());
            } catch (Exception e) {
                // locatie kon niet opgehaald worden
            }
        }

        if (direction == Direction.RIGHT) {
            try {
                result = Stream.of(getChar(x, y), getChar(x, y + 1), getChar(x, y + 2), getChar(x, y + 3)).map(Object::toString).collect(Collectors.joining());
            } catch (Exception e) {
                // locatie kon niet opgehaald worden
            }
        }

        if (direction == Direction.UP) {
            try {
                result = Stream.of(getChar(x, y), getChar(x - 1, y), getChar(x - 2, y), getChar(x - 3, y)).map(Object::toString).collect(Collectors.joining());
            } catch (Exception e) {
                // locatie kon niet opgehaald worden
            }
        }

        if (direction == Direction.DOWN) {
            try {
                result = Stream.of(getChar(x, y), getChar(x + 1, y), getChar(x + 2, y), getChar(x + 3, y)).map(Object::toString).collect(Collectors.joining());
            } catch (Exception e) {
                // locatie kon niet opgehaald worden
            }
        }

        if (direction == Direction.LEFTDOWN) {
            try {
                result = Stream.of(getChar(x, y), getChar(x + 1, y - 1), getChar(x + 2, y - 2), getChar(x + 3, y - 3)).map(Object::toString).collect(Collectors.joining());
            } catch (Exception e) {
                // locatie kon niet opgehaald worden
            }
        }

        if (direction == Direction.RIGHTDOWN) {
            try {
                result = Stream.of(getChar(x, y), getChar(x + 1, y + 1), getChar(x + 2, y + 2), getChar(x + 3, y + 3)).map(Object::toString).collect(Collectors.joining());
            } catch (Exception e) {
                // locatie kon niet opgehaald worden
            }
        }

        if (direction == Direction.LEFTUP) {
            try {
                result = Stream.of(getChar(x, y), getChar(x - 1, y - 1), getChar(x - 2, y - 2), getChar(x - 3, y - 3)).map(Object::toString).collect(Collectors.joining());
            } catch (Exception e) {
                // locatie kon niet opgehaald worden
            }
        }

        if (direction == Direction.RIGHTUP) {
            try {
                result = Stream.of(getChar(x, y), getChar(x - 1, y + 1), getChar(x - 2, y + 2), getChar(x - 3, y + 3)).map(Object::toString).collect(Collectors.joining());
            } catch (Exception e) {
                // locatie kon niet opgehaald worden
            }
        }

        return result.equals("XMAS");
    }

    enum Direction {
        UP, DOWN, LEFT, RIGHT, RIGHTDOWN, RIGHTUP, LEFTUP, LEFTDOWN
    }

    public void puzzleTwo() {
        handleInput();
        int allXXmasses = findAllXXmasses();
        System.out.println(allXXmasses);
//        System.out.println(getInstructionValueWithDont(resources.year_2024.input));
    }


    public static void main(String[] args) {
        Day4 day = new Day4();
        day.puzzleOne();
        day.puzzleTwo();
    }
}
