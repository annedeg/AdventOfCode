package year_2024.main;

import year_2024.main.helpers.Helper;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Day9 {
    String input;

    public void puzzleOne() {
        input = Helper.readToString(2024, 9);
        ArrayList<Integer> integerList = createIntegerList(input);
        ArrayList<Integer> integers = moveDiskBlocks(integerList);
        long total = calcChecksum(integers);
        System.out.println(total);
    }

    private long calcChecksum(ArrayList<Integer> integers) {
        long total = 0;
        long c = 0;
        for (Integer integer : integers) {
            if (integer == -1) {
                c++;
                continue;
            }

            total += integer * c;
            c++;
        }

        return total;
    }

    public void puzzleTwo() {
        input = Helper.readToString(2024, 9);
        ArrayList<Integer> integerList = createIntegerList(input);
        ArrayList<Integer> integers = moveDiskBlocksByNumber(integerList);
        long total = calcChecksum(integers);
        System.out.println(total);
    }

    public ArrayList<Integer> moveDiskBlocksByNumber(ArrayList<Integer> disk) {
        ArrayList<ArrayList<Integer>> blocks = getLastValueIndexAndLength(disk);
        for (ArrayList<Integer> indexAndLength : blocks) {
            int lastValueIndex = indexAndLength.get(0);
            int lastValueLength = indexAndLength.get(1);
            int nextEmptyLocation = getNextEmptyLocationBlock(disk, lastValueLength);

            if (nextEmptyLocation == -1 || nextEmptyLocation >= lastValueIndex) {
                continue;
            }

            int lastValue = disk.get(lastValueIndex);

            for(int i = 0; i < lastValueLength; i++) {
                disk.set(nextEmptyLocation+i, lastValue);
                disk.set(lastValueIndex+i, -1);
            }
        }

        return disk;
    }

    private ArrayList<ArrayList<Integer>> getLastValueIndexAndLength(ArrayList<Integer> disk) {
        int value = -1;
        int length = 0;

        ArrayList<ArrayList<Integer>> blocks = new ArrayList<>();
        for (int i = disk.size()-1; i >= 0; i--) {
            int current = disk.get(i);

            if (current != -1 && value == -1) {
                value = current;
                length = 1;
                continue;
            }

            if (current == value && value != -1) {
                length++;
                continue;
            }

            if (current != value) {
                if (length > 0) {
                    blocks.add(Stream.of(i+1, length).collect(Collectors.toCollection(ArrayList::new)));
                }

                value = current;
                length = 1;
            }
        }
        return blocks;
    }

    public int getNextEmptyLocationBlock(ArrayList<Integer> disk, int lengthRequired) {
        int total = 0;
        for (int i = 0; i < disk.size(); i++) {
            int val = disk.get(i);

            if (val == -1) {
                total++;
            }

            if (val != -1 && total >= lengthRequired) {
                return i - total;
            } else if (val != -1) {
                total = 0;
            }
        }

        return -1;
    }

    public ArrayList<Integer> createIntegerList(String input) {
        ArrayList<Integer> disk = new ArrayList<>();
        AtomicBoolean value = new AtomicBoolean(true);
        char[] arr = input.toCharArray();
        AtomicInteger v = new AtomicInteger();
        IntStream.range(0, arr.length).mapToObj(i -> arr[i]).forEach(
            character -> {
                int charVal;
                try {
                    charVal = Integer.parseInt(character.toString());
                } catch (NumberFormatException numberFormatException) {return;}
                if (value.get()) {
                    IntStream.range(0, charVal).forEach(ignore -> disk.add(v.get()));
                    value.set(false);
                    v.addAndGet(1);
                    return;
                }

                IntStream.range(0, charVal).forEach(ignore -> disk.add(-1));
                value.set(true);
            }
        );

        return disk;
    }

    public ArrayList<Integer> moveDiskBlocks(ArrayList<Integer> disk) {
        while (!finished(disk)) {
            int nextEmptyLocation = getNextEmptyLocation(disk);
            int lastValueIndex = getLastValueIndex(disk);
            int lastValue = disk.get(lastValueIndex);

            disk.set(nextEmptyLocation, lastValue);
            disk.set(lastValueIndex, -1);
        }

        return disk;
    }

    private boolean finished(ArrayList<Integer> disk) {
        boolean possibleEnd = false;
        for (Integer integer : disk) {
            if (integer == -1) {
                possibleEnd = true;
                continue;
            }

            if (possibleEnd) {
                return false;
            }
        }

        return possibleEnd;
    }

    private Integer getNextEmptyLocation(ArrayList<Integer> disk) {
        int c = 0;
        for (Integer integer : disk) {
            if (integer == -1) {
                return c;
            }
            c+=1;
        }

        return -1;
    }

    private int getLastValueIndex(ArrayList<Integer> disk) {
        for (int i = disk.size()-1; i >= 0; i--) {
            int v = disk.get(i);

            if (v != -1) {
                return i;
            }
        }

        return -1;
    }

    public static void main(String[] args) {
        Day9 day = new Day9();
        day.puzzleOne();
        day.puzzleTwo();
    }
}
