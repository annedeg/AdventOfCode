package year_2023.main;

import helpers.CodeDay;
import helpers.Helper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class Day19 extends CodeDay {
    @Override
    public void puzzleOne() {
        var input2 = Helper.readToString(2023, 19);
        String[] split = input2.split("\n\n");
        String rules = split[0];
        String input = split[1];
        ArrayList<Input> collect = Arrays.stream(input.split("\n"))
                .map(Input::new)
                .collect(Collectors.toCollection(ArrayList::new));

        for (String f : rules.split("\n")) {
            String[] split1 = f.split("\\{");
            String name = split1[0];

            String s = split1[1].split("\\}")[0];
            String[] split2 = s.split(",");
            ArrayList<Rule> ruleArrayList = Arrays.stream(split2).map(Rule::new).collect(Collectors.toCollection(ArrayList::new));
            new WorkFlow(name, ruleArrayList);
        }

        int total = 0;
        for (Input input1 : collect) {
            int i = WorkFlow.allWorkFlows.get("in").goFlow("in", input1);

            if (i == 1) {
                total += input1.getTotal();
            }
        }
        System.out.println(total);
    }


    @Override
    public void puzzleTwo() {
        var input = Helper.readToStringArrayList(2023, 19);

        var input2 = Helper.readToString(2023, 19);
        String[] split = input2.split("\n\n");
        String rules = split[0];

        for (String f : rules.split("\n")) {
            String[] split1 = f.split("\\{");
            String name = split1[0];

            String s = split1[1].split("\\}")[0];
            String[] split2 = s.split(",");
            ArrayList<Rule> ruleArrayList = Arrays.stream(split2).map(Rule::new).collect(Collectors.toCollection(ArrayList::new));
            new WorkFlow(name, ruleArrayList);
        }

        List<Range> correctRanges = new ArrayList<>();
        getCorrectRanges(new Range(), WorkFlow.allWorkFlows.get("in"), correctRanges);
        long sum = correctRanges.stream().mapToLong(Range::amountOfCombinations).sum();
        System.out.println(sum);
    }

    private void getCorrectRanges(Range range, WorkFlow workFlow, List<Range> correctRanges) {
        for (Rule rule : workFlow.rules) {
            if (rule.property == 'f') {
                if (rule.caseA.equals("A")) {
                    correctRanges.add(range);
                } else if (!rule.caseA.equals("R")) {
                    getCorrectRanges(range, WorkFlow.allWorkFlows.get(rule.caseA), correctRanges);
                }
            } else {
                Range[] ranges = range.split(rule);

                if (rule.caseA.equals("A")) {
                    correctRanges.add(ranges[0]);
                } else if (!rule.caseA.equals("R")) {
                    getCorrectRanges(ranges[0], WorkFlow.allWorkFlows.get(rule.caseA), correctRanges);
                }
                range = ranges[1];
            }
        }
    }

    class WorkFlow {
        static HashMap<String, WorkFlow> allWorkFlows = new HashMap<>();
        ArrayList<Rule> rules;

        WorkFlow(String name, ArrayList<Rule> rules) {
            this.rules = rules;
            allWorkFlows.put(name, this);
        }

        int goFlow(String name, Input input) {
            for (Rule rule : allWorkFlows.get(name).rules) {
                int res = rule.getRes(input);
                if (res == 1) {
                    return 1;
                } else if (res == -1) {
                    return -1;
                }
            }

            return 0;
        }
    }

    class Rule {
        boolean biggerThen = false;
        char property = 'f';
        int valueToCheck;
        String rule;
        String caseA;

        Rule(String rule) {
            this.rule = rule;
            biggerThen = rule.contains(">");
            boolean smallerThen = rule.contains("<");

            String iff = rule.split(":")[0];

            if (biggerThen) {
                property = iff.split(">")[0].charAt(0);
                valueToCheck = Integer.parseInt(iff.split(">")[1]);
            } else if (smallerThen) {
                property = iff.split("<")[0].charAt(0);
                valueToCheck = Integer.parseInt(iff.split("<")[1]);
            } else {
                caseA = iff;
                return;
            }

            String res = rule.split(":")[1];
            caseA = res;
        }

        int getVal(Input input) {
            char c = rule.charAt(0);

            return switch (c) {
                case 'x' -> input.x;
                case 'm' -> input.m;
                case 'a' -> input.a;
                case 's' -> input.s;
                default -> 0;
            };
        }

        int getRes(Input input) {
            if (!rule.contains(">") && !rule.contains("<") && WorkFlow.allWorkFlows.containsKey(rule)) {
                return WorkFlow.allWorkFlows.get(rule).goFlow(rule, input);
            }

            boolean b = getVal(input) > valueToCheck;
            if (!biggerThen) {
                b = !b;
            }

            if (!b) {
                return 0;
            }

            if (caseA.equals("A")) {
                return 1;
            } else if (caseA.equals("R")) {
                return -1;
            }

            return WorkFlow.allWorkFlows.get(caseA).goFlow(caseA, input);
        }
    }

    class Range {
        long minx = 1;
        long maxx = 4000;
        long minm = 1;
        long maxm = 4000;
        long mina = 1;
        long maxa = 4000;
        long mins = 1;
        long maxs = 4000;

        public Range() {

        }

        public Range(long minx, long maxx, long minm, long maxm, long mina, long maxa, long mins, long maxs) {
            this.minx = minx;
            this.maxx = maxx;
            this.minm = minm;
            this.maxm = maxm;
            this.mina = mina;
            this.maxa = maxa;
            this.mins = mins;
            this.maxs = maxs;
        }

        public long amountOfCombinations() {
            return (maxx - minx + 1) * (maxm - minm + 1) * (maxa - mina + 1) * (maxs - mins + 1);
        }

        public Range[] split(Rule rule) {

            Range[] ranges = new Range[2];

            if (rule.property == 'f') {
                return ranges;
            }

            if (rule.property == 'x') {
                ranges[rule.biggerThen ? 1 : 0] = new Range(Math.max(rule.biggerThen ? rule.valueToCheck + 1 : 1, minx), Math.min(rule.biggerThen ? 4000 : rule.valueToCheck-1, maxx), minm, maxm, mina, maxa, mins, maxs);
                ranges[rule.biggerThen ? 0 : 1] = new Range(Math.max(rule.biggerThen ? 1 : rule.valueToCheck, minx), Math.min(rule.biggerThen ? rule.valueToCheck : 4000, maxx), minm, maxm, mina, maxa, mins, maxx);
            } else if (rule.property == 'm') {
                ranges[rule.biggerThen ? 1 : 0] = new Range(minx, maxx, Math.max(rule.biggerThen ? rule.valueToCheck + 1 : 1, minx), Math.min(rule.biggerThen ? 4000 : rule.valueToCheck-1, maxx), mina, maxa, mins, maxs);
                ranges[rule.biggerThen ? 0 : 1] = new Range(minx, maxx, Math.max(rule.biggerThen ? 1 : rule.valueToCheck, minx), Math.min(rule.biggerThen ? rule.valueToCheck : 4000, maxx), mina, maxa, mins, maxx);
            } else if (rule.property == 'a') {
                ranges[rule.biggerThen ? 1 : 0] = new Range(minx, maxx, minm, maxm, Math.max(rule.biggerThen ? rule.valueToCheck + 1 : 1, minx), Math.min(rule.biggerThen ? 4000 : rule.valueToCheck-1, maxx), mins, maxs);
                ranges[rule.biggerThen ? 0 : 1] = new Range(minx, maxx, minm, maxm, Math.max(rule.biggerThen ? 1 : rule.valueToCheck, minx), Math.min(rule.biggerThen ? rule.valueToCheck : 4000, maxx), mins, maxx);
            } else if (rule.property == 's') {
                ranges[rule.biggerThen ? 1 : 0] = new Range(minx, maxx, minm, maxm, mina, maxa, Math.max(rule.biggerThen ? rule.valueToCheck + 1 : 1, minx), Math.min(rule.biggerThen ? 4000 : rule.valueToCheck-1, maxx));
                ranges[rule.biggerThen ? 0 : 1] = new Range(minx, maxx, minm, maxm, mina, maxa, Math.max(rule.biggerThen ? 1 : rule.valueToCheck, minx), Math.min(rule.biggerThen ? rule.valueToCheck : 4000, maxx));
            }

            return ranges;
        }
    }

    class Input {
        int x;
        int m;
        int a;
        int s;

        Input(String input) {
            String substring = input.substring(1, input.length() - 1);
            String[] array = Arrays.stream(substring.split(","))
                    .map(str -> str.split("=")[1])
                    .toArray(String[]::new);

            this.x = Integer.parseInt(array[0]);
            this.m = Integer.parseInt(array[1]);
            this.a = Integer.parseInt(array[2]);
            this.s = Integer.parseInt(array[3]);
        }

        Input(int x, int m, int a, int s) {
            this.x = x;
            this.m = m;
            this.a = a;
            this.s = s;
        }

        int getTotal() {
            return x + m + a + s;
        }

        public int getX() {
            return x;
        }

        public int getM() {
            return m;
        }

        public int getA() {
            return a;
        }

        public int getS() {
            return s;
        }
    }
}
