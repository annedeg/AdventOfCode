package year_2024.main;

import helpers.Helper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

public class Day25 {
    public void puzzleOne() {
        String string = Helper.readToString(2024, 25);
        String[] split = string.split("\n\n");

        ArrayList<ArrayList<Integer>> locks = new ArrayList<>();
        ArrayList<ArrayList<Integer>> keys = new ArrayList<>();

        for (String lockOrKey : split) {
            boolean lock = lockOrKey.charAt(0) == '#';
            char[][] matrix = Helper.toMatrix(Arrays.stream(lockOrKey.split("\n")).collect(Collectors.toCollection(ArrayList::new)));

            if (!lock) {
                matrix = Helper.rotateCW(Helper.rotateCW(matrix));
            }

            ArrayList<Integer> row = new ArrayList<>();
            for (int x = 0; x < matrix[0].length; x++) {
                for (int y = 0; y < matrix.length;y++) {
                    if (matrix[y][x] != '#') {
                        row.add(lock ? row.size(): 0,y-1);
                        break;
                    }
                }

            }
            if (lock) locks.add(row);
            else keys.add(row);
        }

        int total = 0;
        for (ArrayList<Integer> key : keys) {
            for (ArrayList<Integer> lock : locks) {
                total += fits(key, lock) ? 1 : 0;
            }
        }
        System.out.println(total);
    }

    public boolean fits(ArrayList<Integer> lock, ArrayList<Integer> key) {
        for (int x = 0; x < lock.size(); x++) {
            if ((lock.get(x) + key.get(x)) > 5) {
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        Day25 day = new Day25();
        day.puzzleOne();
    }
}
