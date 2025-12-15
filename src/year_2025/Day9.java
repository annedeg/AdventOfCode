package year_2025;

import helpers.Helper;
import helpers.MatrixLocation;

import java.util.*;
import java.util.stream.Collectors;

public class Day9 {
    public void puzzleOne() {
        ArrayList<MatrixLocation> matrixLocs = Helper.readToStringArrayList(2025, 9).stream()
                .map(str -> {
                    String[] split = str.split(",");
                    return new MatrixLocation(Integer.parseInt(split[0]), Integer.parseInt(split[1]));
                })
                .collect(Collectors.toCollection(ArrayList::new));


        long highest = 0;
        for (MatrixLocation tile1 : matrixLocs) {
            for (MatrixLocation tile2 : matrixLocs) {
                long res = ((long)Math.abs(tile1.x - tile2.getX()) + 1) * (Math.abs(tile1.y - tile2.getY()) + 1);

                if (res > highest) {
                    highest = res;
                }
            }
        }

        System.out.println("1: " + highest);
    }

    public void puzzleTwo() {
        ArrayList<String> matrixLocs = Helper.readToStringArrayList(2025, 9);

//        HashMap<Set<MatrixLocation>, Long> bestLocations = new HashMap<>();
//        for (MatrixLocation m1 : matrixLocs) {
//            for (MatrixLocation m2 : matrixLocs) {
//                if (m1.equals(m2)) {
//                    continue;
//                }
//
//                long res = ((long)Math.abs(m1.x - m2.getX()) + 1) * (Math.abs(m1.y - m2.getY()) + 1);
//                bestLocations.put(Set.of(m1,m2), res);
//            }
//        }

        System.out.println("f");
    }

    public static void main(String[] args) {
        Day9 day = new Day9();
//        day.puzzleOne();
        day.puzzleTwo();
    }
}

