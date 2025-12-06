package year_2025;

import helpers.Helper;

import java.util.*;
import java.util.stream.Collectors;

public class Day5 {
    public void puzzleOne() {
        String[] split = Helper.readToString(2025, 5).split("\n\n");

        String ranges = split[0];
        String ingredients = split[1];

        ArrayList<ArrayList<Long>> allowedValues = new ArrayList<>();
        Arrays.stream(ranges.split("\n"))
                .map(s -> s.split("-"))
                .forEach(startAndEnd -> {
                    ArrayList<Long> arr = new ArrayList<>();
                    arr.add(Long.parseLong(startAndEnd[0]));
                    arr.add(Long.parseLong(startAndEnd[1]));
                    allowedValues.add(arr);
                });

        int total = 0;
        for (String s : ingredients.split("\n")) {
            long ingredient = Long.parseLong(s);

            for (ArrayList<Long> minAndMax : allowedValues) {
                if (ingredient >= minAndMax.get(0) && ingredient <= minAndMax.get(1)) {
                    total+=1;
                    break;
                }
            }
        }

        System.out.println("1: " + total);
    }

    public void puzzleTwo() {
        String[] split = Helper.readToString(2025, 5).split("\n\n");

        List<Range> ranges = Arrays.stream(split[0].split("\n"))
                .map(Range::new)
                .distinct()
                .sorted(Comparator.comparing(Range::getMin))
                .collect(Collectors.toCollection(ArrayList::new));

        int size = ranges.size();
        for (int i = 0; i < size; i++) {
            boolean done = false;
            Range currentRange = ranges.get(i);
            while (!done) {
                Range nextMatchingRange = findNextMatchingRange(currentRange, ranges.subList(i + 1, ranges.size()));
                if (nextMatchingRange == null) {
                    done = true;
                    continue;
                }

                currentRange.merge(nextMatchingRange);
            }
        }

        ranges = ranges.stream().distinct().collect(Collectors.toCollection(ArrayList::new));
        ArrayList<Range> rangesWithoutFullOverlap = new ArrayList<>();

        for (Range range1 : ranges) {
            boolean shouldAdd = true;
            for (Range range2 : ranges) {
                if (range1.equals(range2)) {
                    continue;
                }
                if (range2.doesEncapsulate(range1)) {
                    shouldAdd = false;
                }

                if (range1.getMin() == range2.getMin()) {
                    range1.merge(range2);
                }

                if (range1.getMax() == range2.getMax()) {
                    range1.merge(range2);
                }
            }

            if (shouldAdd) {
                rangesWithoutFullOverlap.add(range1);
            }
        }

        rangesWithoutFullOverlap = rangesWithoutFullOverlap.stream().distinct().sorted(Comparator.comparing(Range::getMin)).collect(Collectors.toCollection(ArrayList::new));

        long prevMax = -1L;

        for (Range range : rangesWithoutFullOverlap) {
            if (range.getMin() < prevMax) {
                System.out.println("wtf");
            }
            prevMax = range.getMax();
        }

        long sum = rangesWithoutFullOverlap.stream()
                .map(Range::getAmountOfValue)
                .mapToLong(Long::longValue)
                .sum();

        System.out.println("2: " + sum);
    }

    private Range findNextMatchingRange(Range currentRange, List<Range> ranges) {
        return ranges.stream()
                .filter(range -> currentRange.isInBetween(range.getMin()))
                .filter(range -> range.getMax() > currentRange.getMax())
                .findFirst()
                .orElse(null);
    }

    class Range {
        long min;
        long max;

        public Range(String range) {
            String[] split = range.split("-");
            min = Long.parseLong(split[0]);
            max = Long.parseLong(split[1]);
        }

        boolean isInBetween(long val) {
            return val >= (min-1) && val <= (max+1);
        }

        public long getMin() {
            return min;
        }

        public long getMax() {
            return max;
        }

        public void setMin(long min) {
            this.min = min;
        }

        public void setMax(long max) {
            this.max = max;
        }

        @Override
        public boolean equals(Object o) {
            if (o == null || getClass() != o.getClass()) return false;
            Range range = (Range) o;
            return min == range.min && max == range.max;
        }

        @Override
        public int hashCode() {
            return Objects.hash(min, max);
        }

        public long getAmountOfValue() {
            return (max-min) + 1;
        }

        public boolean doesEncapsulate(Range range) {
            return min < range.getMin() && max > range.getMin();
        }

        public void merge(Range range) {
            long minMin = Math.min(range.getMin(), min);
            long maxMax = Math.max(range.getMax(), max);

            this.min = minMin;
            this.max = maxMax;
            range.setMin(minMin);
            range.setMax(maxMax);
        }
    }

    public static void main(String[] args) {
        Day5 day = new Day5();
        day.puzzleOne();
        day.puzzleTwo();
    }
}
