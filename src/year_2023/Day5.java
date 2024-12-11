package year_2023;

import helpers.Helper;

import java.util.*;
import java.util.concurrent.*;
import java.util.stream.Collectors;

public class Day5 extends CodeDay {
    @Override
    public void puzzleOne() {
        ArrayList<String> values = Helper.readToStringArrayList("src/input/dayfive.txt");

        long[] seeds = Arrays.stream(values.get(0).split(": ")[1].split(" ")).mapToLong(Long::parseUnsignedLong).toArray();

        ArrayList<String> collect = values.stream().skip(1).collect(Collectors.toCollection(ArrayList::new));

        ArrayList<ArrayList<String>> groupedValues = new ArrayList<>();

        int i = -1;
        for (String line : collect) {
            if (line.equals("")) {
                i += 1;
                groupedValues.add(new ArrayList<>());
                continue;
            }

            if (line.contains(":")) {
                continue;
            }

            groupedValues.get(i).add(line);
        }

        ArrayList<SpecialMapper> specialMappers = groupedValues.stream()
                .map(SpecialMapper::new)
                .collect(Collectors.toCollection(ArrayList::new));

        long min = Long.MAX_VALUE;
        for (long seed : seeds) {

            for (SpecialMapper specialMapper : specialMappers) {
                seed = specialMapper.getDestination(seed);
            }

            if (seed < min) {
                min = seed;
            }
        }

        System.out.println(min);
    }

    @Override
    public void puzzleTwo() {
        ArrayList<String> values = Helper.readToStringArrayList("src/input/dayfive.txt");
        ArrayList<String> collect = values.stream().skip(1).collect(Collectors.toCollection(ArrayList::new));

        ArrayList<ArrayList<String>> groupedValues = new ArrayList<>();

        int i = -1;
        for (String line : collect) {
            if (line.equals("")) {
                i += 1;
                groupedValues.add(new ArrayList<>());
                continue;
            }

            if (line.contains(":")) {
                continue;
            }

            groupedValues.get(i).add(line);
        }

        ArrayList<SpecialMapper> specialMappers = groupedValues.stream()
                .map(SpecialMapper::new)
                .collect(Collectors.toCollection(ArrayList::new));

        ArrayList<Long> lowest = new ArrayList<>();

        long[] seeds = Arrays.stream(values.get(0).split(": ")[1].split(" ")).mapToLong(Long::parseUnsignedLong).toArray();


        ArrayList<Long> finals = new ArrayList<>();

        ExecutorService executorService = Executors.newFixedThreadPool(10);

        for (int iz = 0; iz < seeds.length; iz += 2) {
            int finalIz = iz;
            executorService.submit(() -> {
                long min = Long.MAX_VALUE;

                for (long l = seeds[finalIz]; l < seeds[finalIz] + seeds[finalIz + 1]; l++) {
                    long res = l;
                    for (SpecialMapper specialMapper : specialMappers) {
                        res = specialMapper.getDestination(res);
                    }

                    if (res < min) {
                        min = res;
                        System.out.println(min);
                    }
                }

                finals.add(min);

                System.out.println("done");
                System.out.println(finals.size());

                if (finals.size() == 10) {
                    executorService.shutdown();
                }
            });
        }

        try {
            boolean b = executorService.awaitTermination(10, TimeUnit.DAYS);
            System.out.println(finals.stream().mapToLong(x -> x).min().getAsLong());
            executorService.shutdown();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    class SpecialMapper {
        ArrayList<String> values;
        private long[] starts = new long[50];
        private long[] ends = new long[50];
        private long[] diffs = new long[50];

        public SpecialMapper(ArrayList<String> values) {
            this.values = values;
            int c = 0;
            for (String value : values) {
                String[] strings = value.split(" ");
                long sourceRangeStart = Long.parseUnsignedLong(strings[1]);
                long destinationRangeStart = Long.parseUnsignedLong(strings[0]);
                long rangeLength = Long.parseUnsignedLong(strings[2]);

                diffs[c] = (destinationRangeStart - sourceRangeStart);
                starts[c] = (sourceRangeStart);
                ends[c] = (sourceRangeStart + rangeLength);

                c += 1;
            }
        }

        public long getDestination(long source) {
            for (int i = 0; i < diffs.length; i++) {
                if (source >= starts[i] && source < ends[i]) {
                    return source + diffs[i];
                }
            }

            return source;
        }
    }
}
