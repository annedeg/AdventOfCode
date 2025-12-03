package year_2025;

import helpers.Helper;

import java.util.*;
import java.util.stream.Collectors;

public class Day3 {
    public void puzzleOne() {
        ArrayList<String> batteryBanks = Helper.readToStringArrayList(2025, 3);

        int total = 0;

        for (String bank : batteryBanks) {
            Map<Integer, Integer> locationAndValue = new HashMap<>();
            for (int i = 0; i < bank.length(); i++) {
                int val = Integer.parseInt(bank.substring(i, i+1));

                locationAndValue.put(i, val);
            }

            Map<Integer, Integer> sortedMap =
                    locationAndValue.entrySet().stream()
                            .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                            .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                                    (e1, e2) -> e1, LinkedHashMap::new));

            Integer[] keyOrder = sortedMap.keySet().toArray(Integer[]::new);
            ArrayList<Integer> possibleValues = new ArrayList<>();
            for (int i = 0; i < sortedMap.size(); i++) {
                for (int j = 0; j < sortedMap.size(); j++) {
                    if (i == j) {
                        continue;
                    }

                    if (keyOrder[i] > keyOrder[j]) {
                        //invalid
                        continue;
                    }

                    possibleValues.add(Integer.parseInt(String.valueOf(sortedMap.get(keyOrder[i])) + sortedMap.get(keyOrder[j])));
                }
            }

            possibleValues.sort(Comparator.reverseOrder());

            total += possibleValues.get(0);
        }

        System.out.println("1: " + total);
    }

    public void puzzleTwo() {
        ArrayList<String> batteryBanks = Helper.readToStringArrayList(2025, 3);
        long total = 0;
        int goalLength = 12;

        for (String bankString : batteryBanks) {
            List<Integer> bankInArrayList = new ArrayList<>();
            for (int i = 0; i < bankString.length(); i++) {
                int key = Integer.parseInt(bankString.substring(i, i + 1));
                bankInArrayList.add(key);
            }

            StringBuilder stringBuilder = new StringBuilder();
            int windowSize = bankInArrayList.size() - goalLength + 1;

            ArrayList<Integer> indexesTaken = new ArrayList<>();
            for (int i = 0; i < bankString.length()-windowSize+1; i++) {
                List<Integer> window = bankInArrayList.subList(i, i + windowSize);

                int highestOption = -1;
                int highestOptionIndex = -1;
                for (int w = 0; w < windowSize; w++) {
                    if (!indexesTaken.contains(i + w) && window.get(w) > highestOption) {
                        highestOption = window.get(w);
                        highestOptionIndex = i + w;
                    }
                }

                indexesTaken.add(highestOptionIndex);

                windowSize -= highestOptionIndex - i;
                i += highestOptionIndex - i;
                stringBuilder.append(highestOption);
            }

            total += Long.parseLong(stringBuilder.toString());
        }

        System.out.println("2: " + total);
    }

    public static void main(String[] args) {
        Day3 day = new Day3();
        day.puzzleOne();
        day.puzzleTwo();
    }
}
