package year_2015.main;

import helpers.Helper;

public class Day1 {
    public void puzzleOne() {
        String string = Helper.readToString(2015, 1);
        long left = string.chars().filter(ch -> ch == '(').count();
        long right = string.chars().filter(ch -> ch == ')').count();
        System.out.println(left - right);
    }

    public void puzzleTwo() {
        String string = Helper.readToString(2015, 1);
        int b = 1;
        char[] charArray = string.toCharArray();
        for (int i = 0; i < charArray.length; i++) {
            char c = charArray[i];
            b += c == '(' ? 1 : -1;
            if (b == -1) {
                System.out.println(i);
                return;
            }
        }
    }

    public static void main(String[] args) {
        Day1 day = new Day1();
        day.puzzleOne();
        day.puzzleTwo();
    }
}
