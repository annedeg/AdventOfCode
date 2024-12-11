package year_2024.main;

import helpers.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

public class Day5 {
    String input;
    ArrayList<ArrayList<Character>> characterMatrix;
    ArrayList<PageNumberList> pageNumberLists;
    ArrayList<Rule> rules;

    public void handleInput() {
        characterMatrix = new ArrayList<>();
        input = Helper.readToString(2024, 5);

        String[] split = input.split("\n\n");
        String ruleString = split[0];
        String list = split[1];
        rules = Arrays.stream(ruleString.split("\n"))
            .map(str -> new Rule(str.split("\\|")))
            .collect(Collectors.toCollection(ArrayList::new));

        pageNumberLists = Arrays.stream(list.split("\n"))
            .map(PageNumberList::new)
            .collect(Collectors.toCollection(ArrayList::new));
    }

    public void puzzleOne() {
        handleInput();
        int sum = pageNumberLists.stream()
            .filter(pageList -> rules.stream().allMatch(rule -> rule.valid(pageList.list)))
            .map(PageNumberList::getMiddle)
            .mapToInt(p -> p)
            .sum();
        System.out.println(sum);
    }

    public void puzzleTwo() {
        handleInput();
        int sum = pageNumberLists.stream()
            .filter(pageList -> !(rules.stream().allMatch(rule -> rule.valid(pageList.list))))
            .map(pageNumberList -> pageNumberList.fixList(rules))
            .map(PageNumberList::getMiddle)
            .map(f -> {
                System.out.println(f);
                return f;
            })
            .mapToInt(p -> p)
            .sum();

        System.out.println(sum);
    }

    class Rule {
        int first;
        int second;

        Rule(int first, int second) {
            this.first = first;
            this.second = second;
        }

        public Rule(String[] split) {
            this(Integer.parseInt(split[0]), Integer.parseInt(split[1]));
        }

        boolean valid(ArrayList<Integer> values) {
            if(!values.contains(first) || !values.contains(second)) {
                return true;
            }

            return values.indexOf(first) < values.indexOf(second);
        }
    }

    class PageNumberList {
        ArrayList<Integer> list = new ArrayList<>();

        PageNumberList(String stringList) {
            list = Arrays.stream(stringList.split(",")).map(Integer::parseInt).collect(Collectors.toCollection(ArrayList::new));
        }

        int getMiddle() {
            return list.get(Math.round((float)list.size() / 2)-1);
        }

        private PageNumberList fixList(ArrayList<Rule> rules) {
            for (int i = 0; i < this.list.size(); i++) {
                for (Rule rule : rules) {
                    if (rule.valid(list)) {
                        continue;
                    }

                    int l1 = list.indexOf(rule.first);
                    int l2 = list.indexOf(rule.second);
                    list.set(l1, rule.second);
                    list.set(l2, rule.first);
                }


            }
            return this;
        }
    }

    public static void main(String[] args) {
        Day5 day = new Day5();
        day.puzzleOne();
        day.puzzleTwo();
    }
}
