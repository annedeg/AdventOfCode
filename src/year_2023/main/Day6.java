package year_2023.main;

import helpers.CodeDay;
import helpers.*;

import java.util.*;
import java.util.stream.Collectors;

public class Day6 extends CodeDay {
    @Override
    public void puzzleOne() {
        ArrayList<String> values = Helper.readToStringArrayList(2023, 6);

        ArrayList<Integer> times = Arrays.stream(values.get(0).split(":")[1].split(" "))
            .filter(str -> !str.isEmpty())
            .mapToInt(Integer::parseInt).boxed().collect(Collectors.toCollection(ArrayList::new));
        ArrayList<Integer> distances = Arrays.stream(values.get(1).split(":")[1].split(" "))
            .filter(str -> !str.isEmpty())
            .mapToInt(Integer::parseInt).boxed().collect(Collectors.toCollection(ArrayList::new));

        ArrayList<Integer> amountOfOptions = new ArrayList<>();
        for (int race = 0; race < times.size(); race++) {
            ArrayList<Integer> options = findOptions(times.get(race), distances.get(race));
            amountOfOptions.add(options.size());
        }

        Integer reduce = amountOfOptions.stream().reduce(1, (a, b) -> a * b);
        System.out.println(reduce);
    }

    public ArrayList<Integer> findOptions(int time, int distance) {
        ArrayList<Integer> options = new ArrayList<>();
        for (int i = 0; i < time; i++) {
            if (doRace(i, time) > distance) {
                options.add(i);
            }
        }

        return options;
    }

    public ArrayList<Long> findOptions(Long time, Long distance) {
        ArrayList<Long> options = new ArrayList<>();
        for (long i = 0; i < time; i++) {
            if (doRace(i, time) > distance) {
                options.add(i);
            }
        }

        return options;
    }

    public long doRace(long waited, long time) {
        return waited * (time-waited);
    }

    public int doRace(int waited, int time) {
        return waited * (time-waited);
    }

    @Override
    public void puzzleTwo() {
        ArrayList<String> values = Helper.readToStringArrayList(2023, 6);

        long time = Long.parseLong(values.get(0).split(":")[1].replaceAll(" ", ""));
        long distance = Long.parseLong(values.get(1).split(":")[1].replaceAll(" ", ""));

        ArrayList<Long> options = findOptions(time, distance);
        System.out.println(options.size());
    }
  }
