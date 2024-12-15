package year_2024.main;

import helpers.Helper;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class Day13 {

    public void puzzleOne() {
        String s = Helper.readToString(2024, 13);
        String[] puzzles = s.split("\n\n");

        long total = 0;

        for (String puzzle : puzzles) {
            String[] split = puzzle.split("\n");
            String ba = split[0];
            String bb = split[1];

            long[] location = Arrays.stream(split[2].split("Prize: ")[1].split(", ")).mapToLong(str -> Long.parseLong(str.split("=")[1])).toArray();

            List<Button> buttons = Stream.of(ba,bb).map(str -> {
                int[] loc = Arrays.stream(str.split(": ")[1].split(", "))
                        .mapToInt(ss -> Integer.parseInt(ss.split("\\+")[1]))
                        .toArray();
                return new Button(loc[0], loc[1], str.contains("A") ? 1 : 3);
            }).toList();

            long lowestToken = getLowestTokenP2(buttons, location);
            if (lowestToken != Long.MAX_VALUE) {
                total += lowestToken;
            }
        }

        System.out.println(total);
    }

    private static long getLowestToken(List<Button> buttons, long[] location) {
        long lowestToken = Long.MAX_VALUE;
        Button buttonA = buttons.get(0);
        Button buttonB = buttons.get(1);

        long a = 0;
        long b = 0;
        for (long aP = 0; aP <= 100; aP++) {
            for (long bP = 0; bP <= 100; bP++) {
                long tokenValue = bP + (aP * 3);

                if (tokenValue > lowestToken) {
                    continue;
                }

                long nX = (buttonA.xc * aP) + (buttonB.xc * bP);
                long nY = (buttonA.yc * aP) + (buttonB.yc * bP);
                if (!(nX == location[0] && nY == location[1])) {
                    continue;
                }

                lowestToken = tokenValue;
                a = aP;
                b = bP;
            }
        }
        System.out.println(a);
        System.out.println(b);
        return lowestToken;
    }


    private static long getLowestTokenP2(List<Button> buttons, long[] location) {
//        long lowestToken = Long.MAX_VALUE;
        Button buttonA = buttons.get(0);
        Button buttonB = buttons.get(1);

        long xL = location[0];
        long yL = location[1];

        long denom = ((long) buttonA.xc * buttonB.yc - (long) buttonB.xc * buttonA.yc);
        long num = xL * buttonB.yc - yL * buttonB.xc;

        if (num % denom != 0) {
            return 0;
        }

        long a = num / denom;
        long b = (xL - a * buttonA.xc) / buttonB.xc;



//        for (long aP = Math.round((float) location[0] / buttonA.xc); aP >= 0; aP--) {
//            for (long bP = Math.round((float) location[1] / buttonA.yc); bP >= 0; bP--) {
//                long tokenValue = bP + (aP * 3);
//
//                if (tokenValue > lowestToken) {
//                    continue;
//                }
//
//                long nX = (buttonA.xc * aP) + (buttonB.xc * bP);
//                long nY = (buttonA.yc * aP) + (buttonB.yc * bP);
//                if (!(nX == location[0] && nY == location[1])) {
//                    continue;
//                }
//
//                lowestToken = tokenValue;
//                a = aP;
//                b = bP;
//            }
//        }
        return 3 * a + b;
    }


    class Button {
        final int xc;
        final int yc;
        final int cost;

        Button(int xc, int yc, int cost) {
            this.xc = xc;
            this.yc = yc;
            this.cost = cost;
        }
    }

    public void puzzleTwo() {
        String s = Helper.readToString(2024, 13);
        String[] puzzles = s.split("\n\n");

        long total = 0;

        for (String puzzle : puzzles) {
            String[] split = puzzle.split("\n");
            String ba = split[0];
            String bb = split[1];

            long[] location = Arrays.stream(split[2].split("Prize: ")[1].split(", ")).mapToLong(str -> Long.parseLong((str.split("=")[1])) + 10_000_000_000_000L).toArray();

            List<Button> buttons = Stream.of(ba,bb).map(str -> {
                int[] loc = Arrays.stream(str.split(": ")[1].split(", "))
                        .mapToInt(ss -> Integer.parseInt(ss.split("\\+")[1]))
                        .toArray();
                return new Button(loc[0], loc[1], str.contains("A") ? 1 : 3);
            }).toList();

            long lowestToken = getLowestTokenP2(buttons, location);
            if (lowestToken != Long.MAX_VALUE) {
                total += lowestToken;
            }
        }

        System.out.println(total);
    }

    public static void main(String[] args) {
        Day13 day = new Day13();
        day.puzzleOne();
        day.puzzleTwo();
    }
}
