package year_2023.main;

import helpers.CodeDay;
import helpers.*;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day7 extends CodeDay {
    @Override
    public void puzzleOne() {
        var input = Helper.readToStringArrayList(2023, 7);
        ArrayList<Hand> collect = input.stream()
            .map((String line) -> new Hand(line, Stream.of('2', '3', '4', '5', '6', '7', '8', '9', 'T', 'J', 'Q', 'K', 'A').collect(Collectors.toCollection(ArrayList::new)), -1))
            .sorted()
            .collect(Collectors.toCollection(ArrayList::new));

        Collections.reverse(collect);

        long total = 0;

        for (int rank = 0; rank < collect.size(); rank++) {
            Hand hand = collect.get(rank);
            System.out.print(hand.getType() + ": ");
            System.out.println(hand.getCards());
            total += ((long) (rank + 1) * hand.getWorth());
        }

        System.out.println(total);
    }

    class Hand implements Comparable {
        public static ArrayList<Character> characters;
        int worth;
        int type;
        String cards;

        Hand(String line, ArrayList<Character> characters, int type) {
            Hand.characters = characters;
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

            if (type != -1) {
                this.type = type;
                return;
            }

            if (size == 1) {
                this.type = 7;
            } else if (size == 2) {
                if (values.get(0) == 4 || values.get(1) == 4) {
                    this.type = 6;
                } else {
                    this.type = 5;
                }
            } else if (size == 3) {
                if (values.get(0) == 3 || values.get(1) == 3 || values.get(2) == 3) {
                    this.type = 4;
                } else {
                    this.type = 3;
                }
            } else if (size == 4) {
                this.type = 2;
            } else if (size == 5) {
                this.type = 1;
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
                    return characters.indexOf(hChars[i]) - characters.indexOf(tChars[i]);
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
        var input = Helper.readToStringArrayList(2023, 7);

        ArrayList<Hand> collect = input.stream()
                .map((String line) -> new Hand(line, Stream.of('J', '2', '3', '4', '5', '6', '7', '8', '9', 'T', 'Q', 'K', 'A').collect(Collectors.toCollection(ArrayList::new)), getType(line)))
                .sorted()
                .collect(Collectors.toCollection(ArrayList::new));

        Collections.reverse(collect);

        long total = 0;

        for (int rank = 0; rank < collect.size(); rank++) {
            Hand hand = collect.get(rank);
            System.out.print(hand.getType() + ": ");
            System.out.println(hand.getCards());
            total += ((long) (rank + 1) * hand.getWorth());
        }

        System.out.println(total);
    }

    public int getType(String hand) {
        String[] split = hand.split(" ");
        hand = split[0];
        String worth = split[1];
        ArrayList<Character> collect = Stream.of('2', '3', '4', '5', '6', '7', '8', '9', 'T', 'Q', 'K', 'A').collect(Collectors.toCollection(ArrayList::new));
        ArrayList<ArrayList<Character>> allOptions = new ArrayList<>();
        for (char h : hand.toCharArray()) {
            if (h != 'J') {
                ArrayList<Character> f = new ArrayList<>();
                f.add(h);
                allOptions.add(f);
                continue;
            }

            allOptions.add(collect);
        }

        ArrayList<String> options = new ArrayList<>();

        for (int i = 0; i < allOptions.size(); i++) {
            for (char one : allOptions.get(0)) {
                for (char two : allOptions.get(1)) {
                    for (char three : allOptions.get(2)) {
                        for (char four : allOptions.get(3)) {
                            for (char five : allOptions.get(4)) {
                                options.add((concat(one, two, three, four, five) + " " + worth));
                            }
                        }
                    }
                }
            }
        }


        Optional<Hand> first = options.stream()
                .map(s -> new Hand(s, Stream.of('J', '2', '3', '4', '5', '6', '7', '8', '9', 'T', 'Q', 'K', 'A').collect(Collectors.toCollection(ArrayList::new)), -1))
                .sorted((a, b) -> b.getType() - a.getType())
                .findFirst();

        return first.map(Hand::getType).orElse(0);

    }

    String concat(char... chars) {
        if (chars.length == 0) {
            return "";
        }
        StringBuilder s = new StringBuilder(chars.length);
        for (char c : chars) {
            s.append(c);
        }
        return s.toString();
    }
}
