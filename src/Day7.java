import helpers.Directory;
import helpers.File;
import helpers.Helper;

import java.lang.reflect.Array;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day7 extends CodeDay {
    @Override
    public void puzzleOne() {
        var input = Helper.readToStringArrayList("src/input/dayseven.txt");
        ArrayList<Hand> collect = input.stream()
            .map(Hand::new)
            .sorted()
            .collect(Collectors.toCollection(ArrayList::new));

        Collections.reverse(collect);

        long total = 0;

        for (int rank = 0; rank < collect.size(); rank++) {
            total += ((long) (rank + 1) * collect.get(rank).getWorth());
        }

        System.out.println(total);
    }

    class Hand implements Comparable {
        ArrayList<Character> characters = Stream.of('2', '3', '4', '5', '6', '7', '8', '9', 'T', 'J', 'Q', 'K', 'A').collect(Collectors.toCollection(ArrayList::new));
        int worth;
        int type;
        String cards;

        Hand(String line) {
            String[] s = line.split(" ");
            worth = Integer.parseInt(s[1]);
            cards = s[0];

            HashMap<Character, Integer> map = new HashMap<>();
            for (char c : s[0].toCharArray()) {
                if (map.containsKey(c)) {
                   map.put(c, map.get(c)+1);
                   continue;
                }

                map.put(c, 1);
            }

            int size= map.keySet().size();
            ArrayList<Integer> values = new ArrayList<>(map.values());

            if (size == 1) {
                type = 7;
            }

            if (size == 2) {
                if (values.get(0) == 4 || values.get(1) == 4) {
                    type = 6;
                } else {
                    type = 5;
                }
            }

            if (size == 3) {
                if (values.get(0) == 3 || values.get(1) == 3) {
                    type = 4;
                } else {
                    type = 3;
                }
            }

            if (size == 4) {
                type = 2;
            }

            if (size == 5) {
                type = 1;
            }
        }

        public int getType() {
            return type;
        }

        private String getCards() {
            return cards;
        }

        @Override
        public int compareTo(Object o) {
            if (!(o instanceof Hand h)) {
                return 0;
            }

            if (h.getType() != this.getType()) {
                return h.getType() - this.getType();
            }

            return better(h);
        }

        private int better(Hand h) {
            char[] hChars = h.getCards().toCharArray();
            char[] tChars = this.getCards().toCharArray();
            for (int i = 0; i < hChars.length; i++) {
                if (hChars[i] != tChars[i]) {
                    return characters.indexOf(Character.valueOf(hChars[i])) - characters.indexOf(Character.valueOf(tChars[i]));
                }
            }

            return 0;
        }

        private int getWorth() {
            return worth;
        }
    }


    @Override
    public void puzzleTwo() {
        var input = Helper.readToStringArrayList("src/input/dayseven.txt");
    }
}
