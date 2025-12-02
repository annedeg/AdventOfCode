package year_2025;

import helpers.Helper;

public class Day1 {
    public void puzzleOne() {
        int amountAtZero = 0;
        int currentRotation = 50;
        for (String s : Helper.readToStringArrayList(2025, 1)) {
            int val = Integer.parseInt(s.substring(1));

            if (s.charAt(0) == 'L') {
                val = -val;
            }

            currentRotation += val;

            currentRotation = currentRotation % 100;

            if (currentRotation < 0) {
                currentRotation += 100;
            }

            if (currentRotation == 0) {
                amountAtZero++;
            }
        }

        System.out.println("1:" + amountAtZero);
    }

    public void puzzleTwo() {
        Rotator rotator = new Rotator();

        for (String s : Helper.readToStringArrayList(2025, 1)) {
            int val = Integer.parseInt(s.substring(1));

            if (s.charAt(0) == 'L') {
                val = -val;
            }

            rotator.rotate(val);
        }

        System.out.println("1:" + rotator.countOnZero);
    }

    public static void main(String[] args) {
        Day1 day = new Day1();
        day.puzzleOne();
        day.puzzleTwo();
    }

    class Rotator {
        int currentVal = 50;
        int countOnZero = 0;

        private int min = 0;
        private int max = 99;

        public Rotator() {

        }

        public void rotate(int value) {
            boolean positive = true;
            if (value < 0) {
                positive = false;
                value = -value;
            }

            for (int i = 0; i < value; i++) {
                if (positive) {
                    currentVal++;
                } else {
                    currentVal--;
                }

                if (currentVal < min) {
                    currentVal = max;
                }

                if (currentVal > max) {
                    currentVal = min;
                }

                if (currentVal == 0) {
                    countOnZero++;
                }
            }
        }
    }
}
