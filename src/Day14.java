import helpers.Helper;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Day14 extends CodeDay {

    public HashMap<Integer, char[][]> cache = new HashMap<>();
    @Override
    public void puzzleOne() {
        var input = Helper.readToStringArrayList("src/input/day14");
        char[][] array = input.stream()
                .map(String::toCharArray)
                .toArray(size -> new char[size][input.size()]);

        char[][] arrayEmpty = input.stream()
                .map(str -> str.replaceAll("O", "."))
                .map(String::toCharArray)
                .toArray(size -> new char[size][input.size()]);

        for (int x = 0; x < array.length; x++) {
            for (int y = 0; y < array[0].length; y++) {
                if (array[x][y] == '.' || array[x][y] == '#') {
                    continue;
                }
                int newLocation = getNewLocation(x, y, arrayEmpty);
                arrayEmpty[newLocation][y] = 'O';
            }
        }

        long total = 0;
        int w = arrayEmpty.length;
        for (int x = 0; x < arrayEmpty.length; x++) {
            for (int y = 0; y < arrayEmpty[0].length; y++) {
                if (arrayEmpty[x][y] == 'O') {
                    total += w;
                }
            }

            w--;
        }

        System.out.println(total);
    }

    public int getNewLocation(int currx, int curry, char[][] emptyArr) {
        for (int i = currx-1; i >= 0; i--) {
            if (emptyArr[i][curry] == '#') {
                return i+1;
            }

            if (emptyArr[i][curry] == 'O') {
                return i+1;
            }

            if (i == 0) {
                return i;
            }

            if (emptyArr[i][curry] == '.') {
                continue;
            }
        }

        return currx;
    }

    public char[][] rotate(char[][] matrix){
        int m = matrix.length;
        int n = matrix[0].length;

        char[][] newMatrix = new char[n][m];

        IntStream.range(0, m).forEach(i ->
                IntStream.range(0, n).forEach(j -> {
                    // turn matrix 90º clockwise ⟳
                    newMatrix[j][m - 1 - i] = matrix[i][j];
                }));
        return newMatrix;
    }

    @Override
    public void puzzleTwo() {
        var input = Helper.readToStringArrayList("src/input/day14");
        char[][] array = input.stream()
                .map(String::toCharArray)
                .toArray(size -> new char[size][input.size()]);

        int first = 0;
        ArrayList<Integer> val = new ArrayList<>();
        int toStop = 1_000_000_000;
        for (int i = 0; i < 1_000_000_000; i++) {
            if (i % 100000 == 0 && i != 0) {
                System.out.println("at " + i + "/" + 1000000000 + " ()" + ((i / 1000000000L) * 100L));
            }

            int hashCode = Arrays.deepHashCode(array);

            if (cache.containsKey(hashCode) && first == 0) {
                first = i;
            }

            if (cache.containsKey(hashCode) && first != 0) {
//                array = cache.get(hashCode);
                toStop = (first + ((1000000000 - first) % 10));
//                if (i <= (1000000000 - ((cache.size()) * 1000))) {
//                    i += ((cache.size()) * 10);
//                }
//                continue;
            }

            array = doCycle(array);

            int total = 0;
            int w = array.length;
            for (int x = 0; x < array.length; x++) {
                for (int y = 0; y < array[0].length; y++) {
                    if (array[x][y] == 'O') {
                        total += w;
                    }
                }

                w--;
            }

            if (val.contains(total) && first != 0) {
                System.out.println("nm");
            }
            val.add(total);

            cache.put(hashCode, array);

        }

//        long total = 0;
//        int w = array.length;
//        for (int x = 0; x < array.length; x++) {
//            for (int y = 0; y < array[0].length; y++) {
//                if (array[x][y] == 'O') {
//                    total += w;
//                }
//            }
//
//            w--;
//        }
//        System.out.println(total);
    }

    public char[][] doCycle(char[][] input) {
        char[][] array = input;

        for (int i = 0; i < 4; i++) {
            char[][] emptyArray = emptyArray(array);

            for (int x = 0; x < array.length; x++) {
                for (int y = 0; y < array[0].length; y++) {
                    if (array[x][y] == '.' || array[x][y] == '#') {
                        continue;
                    }
                    int newLocation = getNewLocation(x, y, emptyArray);
                    emptyArray[newLocation][y] = 'O';
                }
            }
            array = rotate(emptyArray);
        }

        return array;
    }

    private void printArray(char[][] array) {
        System.out.println("");
        for (int x = 0; x < array.length; x++) {
            for (int y = 0; y < array[0].length; y++) {
                System.out.print(array[x][y]);
            }
            System.out.println("");
        }
        System.out.println("");
    }

    public char[][] emptyArray(char[][] array) {
        char[][] arrayEmpty = new char[array.length][array[0].length];
        for (int x = 0; x < array.length; x++) {
            for (int y = 0; y < array[0].length; y++) {
                if (array[x][y] == '.' || array[x][y] == '#') {
                    arrayEmpty[x][y] = array[x][y];
                } else {
                    arrayEmpty[x][y] = '.';
                }
            }
        }

        return arrayEmpty;
    }
}