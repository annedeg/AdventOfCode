package main;

import main.helpers.Helper;
import main.helpers.MatrixLocation;

import java.lang.reflect.Array;
import java.math.BigInteger;
import java.util.*;
import java.util.stream.Collectors;

public class Day11 {
    HashMap<BlinkKey, BigInteger> cache = new HashMap<>();

    public void puzzleOne() {
        String input = Helper.readToString("C:\\Users\\adgra\\IdeaProjects\\AdventOfCode2022\\src\\input\\11");
        input = input.replaceAll("\n", "");
        ArrayList<BigInteger> values = Arrays.stream(input.split(" ")).map(BigInteger::new).collect(Collectors.toCollection(ArrayList::new));
        for (int i = 0; i < 25; i++) {
            values = blink(values);
        }

        System.out.println(values.size());
    }

    public ArrayList<BigInteger> blink(ArrayList<BigInteger> values) {
        ArrayList<BigInteger> clone = new ArrayList<>(values);

        int cdelta = 0;
        for (int c = 0; c < values.size(); c++) {
            BigInteger value = values.get(c);
            if (value.equals(new BigInteger("0"))) {
                clone.set(c + cdelta, new BigInteger("1"));
                continue;
            }

            String string = value.toString();
            if (string.length() % 2 == 0) {
                clone.set(c + cdelta, new BigInteger(string.substring(0, string.length() / 2)));
                cdelta += 1;
                clone.add(c+ cdelta, new BigInteger(string.substring(string.length()/2)));
                continue;
            }
            clone.set(c + cdelta, value.multiply(new BigInteger("2024")));
        }

        return clone;
    }

    class BlinkKey {
        public final BigInteger value;
        public final int iterations;

        public BlinkKey(BigInteger value, int iterations) {
            this.value = value;
            this.iterations = iterations;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            BlinkKey blinkKey = (BlinkKey) o;
            return iterations == blinkKey.iterations && Objects.equals(value, blinkKey.value);
        }

        @Override
        public int hashCode() {
            return Objects.hash(value, iterations);
        }
    }

    public BigInteger blinkSmart(BigInteger value, int iterations) {
        BlinkKey blinkKey = new BlinkKey(value, iterations);
        if (iterations == 0) {
            return new BigInteger("1");
        }

        if (cache.containsKey(blinkKey)) {
            return new BigInteger(cache.get(blinkKey).toString());
        }

        ArrayList<BigInteger> results = transformStone(value);
        BigInteger result = new BigInteger("0");
        for (BigInteger res : results) {
             result = result.add(blinkSmart(res, iterations-1));
        }
        cache.put(new BlinkKey(value, iterations), result);
        return result;
    }

    public ArrayList<BigInteger> transformStone(BigInteger bigInteger) {
        ArrayList<BigInteger> bigIntegers = new ArrayList<>();
        if (bigInteger.equals(new BigInteger("0"))) {
            bigIntegers.add(new BigInteger("1"));
            return bigIntegers;
        }

        String string = bigInteger.toString();
        if (string.length() % 2 == 0) {
            bigIntegers.add(new BigInteger(string.substring(0, string.length() / 2)));
            bigIntegers.add(new BigInteger(string.substring(string.length()/2)));
            return bigIntegers;
        }
        bigIntegers.add(bigInteger.multiply(new BigInteger("2024")));
        return bigIntegers;
    }

    public void puzzleTwo() {
        String input = Helper.readToString("C:\\Users\\adgra\\IdeaProjects\\AdventOfCode2022\\src\\input\\11");
        input = input.replaceAll("\n", "");
        ArrayList<BigInteger> values = Arrays.stream(input.split(" ")).map(BigInteger::new).collect(Collectors.toCollection(ArrayList::new));

        BigInteger total = new BigInteger("0");
        for (BigInteger value : values) {
            total = total.add(blinkSmart(value, 75));
        }
        System.out.println(total);
    }


    public static void main(String[] args) {
        Day11 day = new Day11();
        day.puzzleOne();
        day.puzzleTwo();
    }
}
