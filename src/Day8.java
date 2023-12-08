import helpers.Helper;

import java.util.*;
import java.util.stream.Collectors;

public class Day8 extends CodeDay {
    @Override
    public void puzzleOne() {
        var input = Helper.readToStringArrayList("src/input/dayeight.txt");
        String moves = input.get(0);
        List<String> sequence = input.subList(2, input.size());

        HashMap<String, ArrayList<String>> mapFromSequence = createMapFromSequence(sequence);

        int maxMovesLength = moves.length() - 1;
        int pointer = 0;
        int counter = 0;
        String currentKey = "AAA";
        while (true) {
            char move = moves.charAt(pointer);
            ArrayList<String> strings = mapFromSequence.get(currentKey);
            currentKey = strings.get((move == 'L') ? 0 : 1);
            counter++;
            if (currentKey.equals("ZZZ")) {
                break;
            }

            if (pointer == maxMovesLength) {
                pointer = 0;
                continue;
            }

            pointer += 1;
        }
        System.out.println(counter);
    }

    public HashMap<String, ArrayList<String>> createMapFromSequence(List<String> sequence) {
        HashMap<String, ArrayList<String>> map = new HashMap<>();
        for (String row : sequence) {
            String[] split = row.split(" = ");
            String key = split[0];
            String rawValue = split[1];
            rawValue = rawValue.substring(1, rawValue.length() - 1);
            ArrayList<String> value = Arrays.stream(rawValue.split(", ")).collect(Collectors.toCollection(ArrayList::new));

            map.put(key, value);
        }

        return map;
    }

    @Override
    public void puzzleTwo() {
        var input = Helper.readToStringArrayList("src/input/dayeight.txt");
        String moves = input.get(0);
        List<String> sequence = input.subList(2, input.size());

        HashMap<String, ArrayList<String>> mapFromSequence = createMapFromSequence(sequence);

        int pointer = 0;
        String[] currentKeys = mapFromSequence.keySet().stream().filter(key -> key.endsWith("A")).toArray(String[]::new);

        long[] val = new long[6];
        int i = -1;
        for (String key : currentKeys) {
            int c = 0;
            i++;
            while (true) {
                if (key.endsWith("Z")) {
                    val[i] = c;
                    break;
                }

                key = mapFromSequence.get(key).get((moves.charAt(pointer) == 'L') ? 0 : 1);
                c += 1;
                if (pointer + 1 == moves.length()) {
                    pointer = 0;
                } else {
                    pointer += 1;
                }
            }

        }

        System.out.println(lcm(val));
    }

    private long lcm(long... values) {
        if (values.length == 2) {
            return lcm(values[0], values[1]);
        }

        long[] newValues = Arrays.copyOfRange(values, 1, values.length);

        long lcm = lcm(newValues);
        long first = values[0];
        return lcm(first, lcm);
    }

    public long lcm(long number1, long number2) {
        if (number1 == 0 || number2 == 0) {
            return 0;
        }
        long absNumber1 = Math.abs(number1);
        long absNumber2 = Math.abs(number2);
        long absHigherNumber = Math.max(absNumber1, absNumber2);
        long absLowerNumber = Math.min(absNumber1, absNumber2);
        long lcm = absHigherNumber;
        while (lcm % absLowerNumber != 0) {
            lcm += absHigherNumber;
        }
        return lcm;
    }
}
