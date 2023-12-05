import helpers.Helper;

import java.lang.reflect.Array;
import java.util.*;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.LongStream;

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
                i+=1;
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
                i+=1;
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
        ArrayList<Long> checked = new ArrayList<>();

        ExecutorService executors = Executors.newFixedThreadPool(10);
        for (int iz = 0; iz < seeds.length; iz+=2) {
            System.out.println(iz + "/" + seeds.length);

            long start = seeds[iz];
            long length = seeds[iz+1];

            executors.submit(() -> {
                long min = Long.MAX_VALUE;
                for (long seed : LongStream.range(start, start + length).toArray()) {
                    if (checked.contains(seed)) {
                        continue;
                    }

                    for (SpecialMapper specialMapper : specialMappers) {
                        seed = specialMapper.getDestination(seed);
                    }

                    checked.add(seed);
                    if (seed < min) {
                        min = seed;
                    }
                }
                System.out.println(min);
            });
        }
    }

    class SpecialMapper {
        private ArrayList<Long> starts = new ArrayList<>();
        private ArrayList<Long> ends = new ArrayList<>();
        private ArrayList<Long> diffs = new ArrayList<>();

        public SpecialMapper(ArrayList<String> values) {
            for (String value : values) {
                String[] strings = value.split(" ");
                long sourceRangeStart = Long.parseUnsignedLong(strings[1]);
                long destinationRangeStart = Long.parseUnsignedLong(strings[0]);
                long rangeLength = Long.parseUnsignedLong(strings[2]);

                diffs.add(destinationRangeStart - sourceRangeStart);
                starts.add(sourceRangeStart);
                ends.add(sourceRangeStart+rangeLength);
            }
        }

        public long getDestination(long source) {
            for (int i = 0; i < diffs.size(); i++) {
                if (source >= starts.get(i) && source <= ends.get(i)) {
                    return source + diffs.get(i);
                }
            }

            return source;
        }
    }
  }
