package year_2023;

import helpers.Helper;

import java.util.*;
import java.util.stream.Collectors;

public class Day9 extends CodeDay {
    @Override
    public void puzzleOne() {
        var input = Helper.readToStringArrayList("src/input/daynine.txt");
        long sum = input.stream()
                .map(History::new)
                .map(History::nextValue)
                .mapToLong(a -> a)
                .sum();
        System.out.println(sum);
    }


    @Override
    public void puzzleTwo() {
        var input = Helper.readToStringArrayList("src/input/daynine.txt");
        ArrayList<String> reversed = new ArrayList<>();
        for (String in : input) {
            String[] split = in.split(" ");
            for(int i = 0; i < split.length / 2; i++)
            {
                String temp = split[i];
                split[i] = split[split.length - i - 1];
                split[split.length - i - 1] = temp;
            }
            String join = String.join(" ", split);
            reversed.add(join);
        }


        long sum = reversed.stream()
                .map(History::new)
                .map(History::nextValue)
                .mapToLong(a -> a)
                .sum();
        System.out.println(sum);

    }

    class History {
        ArrayList<ArrayList<Long>> history = new ArrayList<>();
        String line;

        public History(String line) {
            this.line = line;
            ArrayList<Long> firstLine = Arrays.stream(line.split(" ")).map(Long::parseLong).collect(Collectors.toCollection(ArrayList::new));
            history.add(firstLine);

            int p = 0;
            while (!checkDone()) {
                ArrayList<Long> nextLine = new ArrayList<>();
                for (int i = 0; i < history.get(p).size()-1; i++) {
                    long i1 = history.get(p).get(i + 1) - history.get(p).get(i);
                    nextLine.add(i1);
                }
                p+=1;
                history.add(nextLine);
            }
        }

        private boolean checkDone() {
            long sum = history.get(history.size() - 1).stream().filter(s -> s == 0).count();
            return sum == history.get(history.size() - 1).size();
        }

        public long nextValue() {
            for (int p = history.size()-1; p >= 1; p--) {
                long lastItem = history.get(p).get(history.get(p).size()-1);
                history.get(p-1).add(lastItem + history.get(p-1).get(history.get(p-1).size()-1));
            }

            return (history.get(0).get(history.get(0).size()-1));
        }
    }
}
