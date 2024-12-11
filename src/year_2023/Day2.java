package year_2023;

import helpers.Helper;

import java.util.ArrayList;

public class Day2 extends CodeDay {
    @Override
    public void puzzleOne() {
        ArrayList<String> values = Helper.readToStringArrayList("src/input/daytwo.txt");
        int total = 0;
        for (var value : values) {
            String[] both = value.split(" ");
            total += getPoints(both[0], both[1]);
        }
        System.out.println(total);
    }

    public int getPoints(String first, String second) {
        int point = 0;
        switch (second) {
            case "X":
                point+=1;
                break;
            case "Y":
                point+=2;
                break;
            case "Z":
                point+=3;
                break;
        }

        char you = second.charAt(0);
        char them = first.charAt(0);

        int result = ((you - them) - 23);
        if (result == 0) {
            point += 3;
        } else if ((you == 'X' && them == 'C') || (you == 'Y' && them == 'A') || (you == 'Z' && them == 'B'))
            point += 6;
        System.out.println(first + " " + second + " " + point);
        return point;

    }

    @Override
    public void puzzleTwo() {
        ArrayList<String> values = Helper.readToStringArrayList("src/input/daytwo.txt");
        int total = 0;
        for (var value : values) {
            String[] both = value.split(" ");
            total += getPoints(both[0].charAt(0), both[1].charAt(0));
        }
        System.out.println(total);
    }

    public int getPoints(char first, char second) {
        int point = 0;
        switch (second) {
            case 'X':
                int val;
                return ((val = ((int) first - 64)) > 1) ? val - 1 : 3;
            case 'Y':
                point = ((int) first - 64);
                point += 3;
                return point;
            case 'Z':
                point = ((val = ((int) first - 64)) < 3) ? val + 1 : 1;
                return point + 6;
        }
        return point;
    }
}
