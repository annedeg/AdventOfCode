package year_2020.main;

import helpers.Day;
import helpers.Helper;

import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day3 implements Day {
    @Override
    public void partOne() {
        char[][] map = Helper.toMatrix(2020, 3);

        System.out.println(checkSlope(map, 3, 1));
    }

    public int checkSlope(char[][] map, int cr, int cd) {
        int cx = 0;
        int cy = 0;

        int totalTrees = 0;
        while (cy != map.length-1) {
            cx = (cx + cr) % map[0].length;
            cy = (cy + cd) % map.length;
            totalTrees += map[cy][cx] == '#' ? 1 : 0;
        }
        return totalTrees;
    }

    @Override
    public void partTwo() {
        char[][] strings = Helper.toMatrix(2020, 3);

        ArrayList<Integer> collect = Stream.of(1, 3, 5, 7, 1).collect(Collectors.toCollection(ArrayList::new));
        ArrayList<Integer> collect2 = Stream.of(1,1,1,1,2).collect(Collectors.toCollection(ArrayList::new));

        int allSlopes = 1;
        for (int i = 0; i < collect.size(); i++) {
            int i1 = checkSlope(strings, collect.get(i), collect2.get(i));
            allSlopes*=i1;
        }
        System.out.println(allSlopes);
    }

    public static void main(String[] args) {
        Day day = new Day3();
        day.partOne();
        day.partTwo();
    }
}
