package year_2024.main;

import helpers.Helper;

import java.util.*;

public class Day23 {
    HashMap<String, ArrayList<String>> connectingComputers;

    public void puzzleOne() {
        handleInput();

        HashSet<HashSet<String>> allCombinations = new HashSet<>();
        for (String key : connectingComputers.keySet()) {
            allCombinations.addAll(goIntoAll(3, false, key, key, new HashSet<>(), new HashSet<>()));
        }
        System.out.println(allCombinations.size());
    }

    private void handleInput() {
        connectingComputers = new HashMap<>();
        ArrayList<String> connections = Helper.readToStringArrayList(2024, 23);
        for (String connection : connections) {
            String[] split = connection.split("-");
            if (!connectingComputers.containsKey(split[0])) {
                connectingComputers.put(split[0], new ArrayList<>());
            }
            if (!connectingComputers.containsKey(split[1])) {
                connectingComputers.put(split[1], new ArrayList<>());
            }
            connectingComputers.get(split[0]).add(split[1]);
            connectingComputers.get(split[1]).add(split[0]);
        }
    }

    public HashSet<HashSet<String>> goIntoAll(int depth, boolean t, String current, String start, HashSet<String> build, HashSet<HashSet<String>> unique) {
        String key = current;
        if (key.charAt(0) == 't') {
            t = true;
        }

        build.add(key);

        if (depth == 0) {
            if (current.equals(start) && t) {
                unique.add(build);
            }
            return unique;
        }

        ArrayList<String> strings = connectingComputers.get(key);
        for (String nextKey : strings) {
            HashSet<String> clone = new HashSet<>(build);
            unique = goIntoAll(depth - 1, t, nextKey, start, clone, unique);
        }

        return unique;
    }

    public Set<String> allConnected(String start, Set<String> currentList, Set<String> bestList, Set<String> keys) {
        if (currentList.contains(start) || keys.isEmpty()) {
            if (currentList.size() > bestList.size()) {
                return currentList;
            } else {
                return bestList;
            }
        }

        for (String current : currentList) {
            if (!isConnected(start, current)) {
                if (currentList.size() > bestList.size()) {
                    return currentList;
                } else {
                    return bestList;
                }
            }
        }

        currentList.add(start);

        for (String key : keys) {
            Set<String> newKeys = new HashSet<>(keys);
            newKeys.remove(key);
            bestList = allConnected(key, currentList, bestList, newKeys);
        }
        return bestList;
    }

    private boolean isConnected(String start, String start1) {
        return connectingComputers.get(start).contains(start1) || connectingComputers.get(start1).contains(start);
    }


    public void puzzleTwo() {
        handleInput();

        Set<String> best = new HashSet<>();
        int max = 0;
        for (String key : connectingComputers.keySet()) {
            Set<String> strings = allConnected(key, new HashSet<>(), new HashSet<>(), connectingComputers.keySet());
            if (strings.size() > max) {
                max = strings.size();
                best = strings;
            }
        }
        System.out.println(best.size());
        List<String> bestList = new ArrayList<>(best.stream().toList());
        Collections.sort(bestList);
        String join = String.join(",", bestList);
        System.out.println(join);

    }

    public static void main(String[] args) {
        Day23 day = new Day23();
        day.puzzleOne();
        day.puzzleTwo();
    }
}
