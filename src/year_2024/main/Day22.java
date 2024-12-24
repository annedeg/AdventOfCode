package year_2024.main;

import helpers.Helper;

import java.util.*;
import java.util.stream.Collectors;

public class Day22 {
    public void puzzleOne() {
        ArrayList<String> secrets = Helper.readToStringArrayList(2024, 22);
        long res = secrets.stream()
                .map(secret -> getXSecret(Long.parseLong(secret), 2000))
                .mapToLong(a -> a)
                .sum();
        System.out.println(res);
    }

    public long getXSecret(long secret, long amountOfNumbers) {
        for (int i = 0; i < amountOfNumbers; i++) {
            secret = calculateNextSecret(secret);
        }
        return secret;
    }

    public long calculateNextSecret(long secret) {
        secret = prune(mix(secret * 64, secret));
        secret = prune(mix((int) Math.floor((double) secret / 32), secret));
        secret = prune(mix(secret * 2048, secret));

        return secret;
    }

    public int getLastValue(long secret) {
        String string = String.valueOf(secret);
        return Integer.parseInt(string.substring(string.length() - 1));
    }

    public long prune(long secret) {
        return secret % 16777216;
    }

    public long mix(long newPart, long secret) {
        return newPart ^ secret;
    }

    public void puzzleTwo() {
        ArrayList<String> sequences = Helper.readToStringArrayList(2024, 22).stream()
                .mapToLong(Long::parseLong)
                .mapToObj(this::getSequence)
                .collect(Collectors.toCollection(ArrayList::new));

        List<Sequence> list = sequences.stream()
                .map(Sequence::new)
                .toList();


        LinkedHashMap<String, Integer> sortedResults = sortByValue(mergeHashmaps(list));
        Optional<String> firstKey = sortedResults.keySet().stream().findFirst();

        if (firstKey.isEmpty()) {
            System.out.println("no result found");
            return;
        }
        System.out.println(sortedResults.get(firstKey.get()));
    }

    private static LinkedHashMap<String, Integer> mergeHashmaps(List<Sequence> list) {
        LinkedHashMap<String, Integer> totalSequencePart = new LinkedHashMap<>();


        for (Sequence sequence : list) {
            HashMap<String, Integer> allSubSequences = sequence.getAllSubSequences();
            for (String key : allSubSequences.keySet()) {
                Integer value = allSubSequences.get(key);

                if (!totalSequencePart.containsKey(key)) {
                    totalSequencePart.put(key, value);
                } else {
                    totalSequencePart.put(key, totalSequencePart.get(key) + value);
                }
            }
        }
        return totalSequencePart;
    }

    public static LinkedHashMap<String, Integer> sortByValue(HashMap<String, Integer> map) {
        List<Map.Entry<String, Integer>> entryList = new ArrayList<>(map.entrySet());

        entryList.sort(Map.Entry.comparingByValue());
        Collections.reverse(entryList);

        LinkedHashMap<String, Integer> sortedMap = new LinkedHashMap<>();
        for (Map.Entry<String, Integer> entry : entryList) {
            sortedMap.put(entry.getKey(), entry.getValue());
        }

        return sortedMap;
    }

    public String getSequence(long secret) {
        StringBuilder stringBuilder = new StringBuilder();
        StringBuilder valueBuilder = new StringBuilder();
        int prev = getLastValue(secret);
        for (int i = 0; i < 2000; i++) {
            secret = calculateNextSecret(secret);

            int lastValue = getLastValue(secret);
            stringBuilder.append(lastValue - prev);
            valueBuilder.append(lastValue);
            if (i != 1999) {
                stringBuilder.append(",");
                valueBuilder.append(",");
            } else {
                stringBuilder.append(":").append(valueBuilder);
            }
            prev = lastValue;
        }
        return stringBuilder.toString();
    }

    class Sequence {
        String sequenceString;
        LinkedHashMap<String, Integer> allSubSequences = new LinkedHashMap<>();

        Sequence(String sequenceString) {
            this.sequenceString = sequenceString;
            String[] temp = sequenceString.split(":");
            String[] split = temp[0].split(",");
            String[] valueSplit = temp[1].split(",");
            for (int i = 1; i < split.length - 4; i++) {
                String subSequence = String.join(",", Arrays.copyOfRange(split, i, i + 4));
                int sequenceBananaValue = Integer.parseInt(valueSplit[i + 3]);

                if (allSubSequences.containsKey(subSequence)) {
                    continue;
                }

                allSubSequences.put(subSequence, sequenceBananaValue);
            }
        }

        public HashMap<String, Integer> getAllSubSequences() {
            return allSubSequences;
        }
    }

    public static void main(String[] args) {
        Day22 day = new Day22();
        day.puzzleOne();
        day.puzzleTwo();
    }
}
