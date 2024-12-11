package year_2020.main;

import helpers.Day;
import helpers.Helper;

import java.util.ArrayList;

public class Day1 implements Day {
    @Override
    public void partOne() {
        ArrayList<String> strings = Helper.readToStringArrayList(2020, 1);
        for (String string : strings) {
            for (String string2 : strings) {
                int i = Integer.parseInt(string);
                int j = Integer.parseInt(string2);

                if (i + j == 2020) {
                    System.out.println(i*j);
                    return;
                }
            }
        }
    }

    @Override
    public void partTwo() {
        ArrayList<String> strings = Helper.readToStringArrayList(2020, 1);
        for (String string : strings) {
            for (String string2 : strings) {
                for (String string3 : strings) {
                    int i = Integer.parseInt(string);
                    int j = Integer.parseInt(string2);
                    int k = Integer.parseInt(string3);

                    if (i + j + k == 2020) {
                        System.out.println(i*j*k);
                        return;
                    }
                }
            }
        }
    }

    public static void main(String[] args) {
        Day day = new Day1();
        day.partOne();
        day.partTwo();
    }
}
