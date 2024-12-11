package year_2020.main;

import helpers.Day;
import helpers.Helper;

import java.util.ArrayList;

public class Day2 implements Day {
    @Override
    public void partOne() {
        ArrayList<String> strings = Helper.readToStringArrayList(2020, 2);
        long count = strings.stream()
                .filter(line -> {
                    String[] split = line.split(": ");
                    String[] rule = split[0].split(" ");
                    String[] minMax = rule[0].split("-");
                    int min = Integer.parseInt(minMax[0]);
                    int max = Integer.parseInt(minMax[1]);
                    char character = rule[1].charAt(0);
                    String password = split[1];

                    int total = 0;
                    for (var ch : password.toCharArray()) {
                        int i = (ch == character) ? ++total : total;
                    }

                    return total >= min && total <= max;
                }).count();
        System.out.println(count);
    }

    @Override
    public void partTwo() {
        ArrayList<String> strings = Helper.readToStringArrayList(2020, 2);
        long count = strings.stream()
                .filter(line -> {
                    String[] split = line.split(": ");
                    String[] rule = split[0].split(" ");
                    String[] minMax = rule[0].split("-");
                    int min = Integer.parseInt(minMax[0]) -1;
                    int max = Integer.parseInt(minMax[1]) -1;
                    char character = rule[1].charAt(0);
                    String password = split[1];

                    try {
                        char minC = password.charAt(min);
                        char maxC = password.charAt(max);

                        return (minC == character || maxC == character) && (minC != maxC);
                    } catch (StringIndexOutOfBoundsException e) {
                        return true;
                    }


                }).count();
        System.out.println(count);
    }

    public static void main(String[] args) {
        Day day = new Day2();
        day.partOne();
        day.partTwo();
    }
}
