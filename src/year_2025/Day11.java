package year_2025;

import helpers.Helper;

import java.util.*;
import java.util.stream.Collectors;

public class Day11 {
    public void puzzleOne() {
        Map<String, List<String>> deviceToOutputDevice = new HashMap<>();
        Helper.readToStringArrayList(2025, 11).forEach(str -> {
                    String[] split = str.split(": ");
                    List<String> list = Arrays.stream(split[1].split(" ")).collect(Collectors.toCollection(ArrayList::new));
                    deviceToOutputDevice.put(split[0], list);
                });

        int amount = walkAndFindAllPaths(deviceToOutputDevice,"you", "out", 0, new ArrayList<>());
        System.out.println("1: "+ amount);
    }

    private int walkAndFindAllPaths(Map<String, List<String>> deviceMap, String start, String end, int amountFound, List<String> history) {
        if (history.contains(start)) {
            return amountFound;
        }
        history.add(start);

        if (start.equals(end)) {
            amountFound++;
            return amountFound;
        }

        for (String nextStep : deviceMap.get(start)) {
            amountFound = walkAndFindAllPaths(deviceMap, nextStep, end, amountFound, new ArrayList<>(history));
        }

        return amountFound;
    }

    private long walkAndFindAllPaths(Map<String, List<String>> deviceMap, String start, String end, Map<String, Long> mem) {
        if (start.equals(end)) {
            return 1;
        }

        if (mem.containsKey(start)) {
            return mem.get(start);
        }

        if (start.equals("out")) {
            return 0;
        }

        long i = 0;
        for (String nextStep : deviceMap.get(start)) {
            i += walkAndFindAllPaths(deviceMap, nextStep, end, mem);
        }
        if (!mem.containsKey(start)) {
            mem.put(start, i);
        }

        return mem.get(start);
    }

    public void puzzleTwo() {
        Map<String, List<String>> deviceToOutputDevice = new HashMap<>();
        Helper.readToStringArrayList(2025, 11).forEach(str -> {
            String[] split = str.split(": ");
            List<String> list = Arrays.stream(split[1].split(" ")).collect(Collectors.toCollection(ArrayList::new));
            deviceToOutputDevice.put(split[0], list);
        });

        long amountDF = walkAndFindAllPaths(deviceToOutputDevice,"dac", "fft",  new HashMap<>());
        long amountFD = walkAndFindAllPaths(deviceToOutputDevice,"fft", "dac",  new HashMap<>());
//
        long amountSF = walkAndFindAllPaths(deviceToOutputDevice,"svr", "fft",  new HashMap<>());
        long amountSD = walkAndFindAllPaths(deviceToOutputDevice,"svr", "dac",  new HashMap<>());

        long amountFO = walkAndFindAllPaths(deviceToOutputDevice,"fft", "out",  new HashMap<>());
        long amountDO = walkAndFindAllPaths(deviceToOutputDevice,"dac", "out",  new HashMap<>());

        if (amountDF == 0) {
            System.out.println(amountSF * amountFD * amountDO);
        } else {
            System.out.println(amountSD * amountDF * amountFO);
        }
    }

    public static void main(String[] args) {
        Day11 day = new Day11();
//        day.puzzleOne();
        day.puzzleTwo();
    }
}
