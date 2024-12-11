package year_2022.main;

import helpers.CodeDay;
import helpers.*;

import java.util.ArrayList;

public class Day10 extends CodeDay {
    @Override
    public void puzzleOne() {
        var input = Helper.readToStringArrayList(2022, 10);

        int x = 1;
        int totalSignalStrength = 0;
        int c = 0;

        for (var cycle : input) {
            if (cycle.contains("addx")) {
                for (int i = 0; i < 2; i++) {
                    c++;
                    totalSignalStrength = doSignalStrength(c, x, totalSignalStrength);
                }
                x+=Integer.parseInt(cycle.split("addx ")[1]);
            } else {
                c++;
                totalSignalStrength = doSignalStrength(c, x, totalSignalStrength);
            }
        }

        System.out.println(totalSignalStrength);
    }

    public int doSignalStrength(int c, int x, int totalSignalStrength) {
        if (c == 20 || (c - 20) % 40 == 0)
            totalSignalStrength += c*(x);

        return totalSignalStrength;
    }

    @Override
    public void puzzleTwo() {
        var input = Helper.readToStringArrayList(2022, 10);
        int c = 0;
        int x = 1;
        ArrayList<ArrayList<String>> crtOutput = new ArrayList<>();
        crtOutput.add(new ArrayList<>());
        for (var cycle : input)
            if (cycle.contains("addx")) {
                for (int i = 0; i < 2; i++)
                    c = doCycle(crtOutput, c, x);
                x += Integer.parseInt(cycle.split("addx ")[1]);
            } else {
                c = doCycle(crtOutput, c, x);
            }

        printCrt(crtOutput);
    }

    public int doCycle(ArrayList<ArrayList<String>> crtOutput, int c, int x) {
        var currentRow = crtOutput.get(crtOutput.size()-1);
        writeToCurrent(currentRow, c, x);
        if (++c % 40 == 0)
            crtOutput.add(new ArrayList<>());
        return c;
    }

    public void printCrt(ArrayList<ArrayList<String>> crtOutput) {
        for (var row : crtOutput) {
            System.out.println(row.toString());
        }
    }
    private void writeToCurrent(ArrayList<String> currentRow, int c, int x) {
        int cf = c % 40;
        currentRow.add((cf == x || cf == x+1 || cf == x-1) ? "#" : ".");
    }
}
