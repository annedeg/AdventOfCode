package year_2023.main;

import helpers.*;

import java.util.*;
import java.util.stream.Collectors;

public class Day20 extends CodeDay {
    HashMap<String, Step> steps = new HashMap<>();
    long totalH = 0;
    long totalL = 0;
    @Override
    public void puzzleOne() {
        ArrayList<String> lines = Helper.readToStringArrayList(2023, 20);

        for (String line : lines) {
            String[] split = line.split(" -> ");
            if (split[0].contains("%")) {
                new FlipFlop(split[0].split("%")[1], Arrays.stream(split[1].split(", ")).collect(Collectors.toCollection(ArrayList::new)));
            } else if (split[0].contains("&")) {
                new Conjunction(split[0].split("&")[1], Arrays.stream(split[1].split(", ")).collect(Collectors.toCollection(ArrayList::new)));
            } else {
                new Broadcaster(Arrays.stream(split[1].split(", ")).collect(Collectors.toCollection(ArrayList::new)));
            }
        }

        ArrayList<Conjunction> conjunctions = steps.values().stream().filter(Conjunction.class::isInstance).map(Conjunction.class::cast).sorted(Comparator.comparing(a -> a.name)).collect(Collectors.toCollection(ArrayList::new));
        for (Conjunction conjunction : conjunctions) {
            ArrayList<String> inputForConjunction = steps.values().stream()
                    .filter(map->map.getDestination().contains(conjunction.getName()))
                    .map(Step::getName)
                    .collect(Collectors.toCollection(ArrayList::new));
            conjunction.setInputs(inputForConjunction);
        }

        LinkedList<Step> queue = new LinkedList<>();
        long i = 0;
        HashMap<String, Long> first = new HashMap<>();
        HashMap<String, Long> second = new HashMap<>();
        while (true) {
            queue.push(steps.get("broadcaster"));
            while (!queue.isEmpty()) {
                Step currentStep = queue.pop();
                boolean input = currentStep.handleInput();

                for (String dest : currentStep.getDestination()) {
                    if (dest.equals("rx") && !input) {
                        System.out.println(i);
                        System.out.println("yeahhh");
                        System.exit(1);
                    }

                    Step step = steps.get(dest);
                    if (step instanceof FlipFlop && input) {
                        totalH += 1;
//                        System.out.println(currentStep.getName() + " -high-> " + step.getName());
                        continue;
                    }

                    if (step == null) {
//                        System.out.println(currentStep.getName() + " -" + (input ? "high" : "low") + "-> " + "output");
                        if (input) {
                            totalH += 1;
                        } else {
                            totalL += 1;
                        }
                        continue;
                    }

//                    System.out.println(currentStep.getName() + " -" + (input ? "high" : "low") + "-> " + step.getName());
                    if (step instanceof Conjunction) {
                        ((Conjunction) step).setInputAndName(input, currentStep.getName());
                    } else {
                        step.setInput(input);
                    }

                    queue.add(step);
                }

                if (i != 0) {
                    HashMap<String, Boolean> xr = ((Conjunction) steps.get("kl")).inputs;
                    for (String key : xr.keySet()) {
                        boolean b = steps.get(key).handleInput();
                        if (b && !first.containsKey(key)) {
                            first.put(key, i);
                        } else if (b && first.containsKey(key) && !second.containsKey(key) && first.get(key) != i) {
                            second.put(key, i);
                        }
                    }
                }
            }

            long total = 1;

            if (first.size() == 4 && second.size() == 4) {
                HashMap<String, Boolean> xr = ((Conjunction) steps.get("kl")).inputs;
                for (String key : xr.keySet()) {
                    total *= first.get(key) - second.get(key);
                }

                System.out.println(total);
                System.exit(1);
            }

            i+=1;
        }




//        System.out.println(totalL);
//        System.out.println(totalH);
//        System.out.println(totalH*totalL);
    }

    public boolean flipFlopsEmpty() {
        return steps.values().stream()
                .filter(FlipFlop.class::isInstance)
                .map(FlipFlop.class::cast)
                .noneMatch(flipFlop -> flipFlop.on);
    }

    @Override
    public void puzzleTwo() {

    }

    class FlipFlop implements Step {
        String name;
        boolean on = false;
        boolean input;
        boolean stop = false;
        ArrayList<String> destinations;
        FlipFlop(String name, ArrayList<String> goals) {
            this.name = name;
            this.destinations = goals;
            steps.put(name, this);
        }

        @Override
        public ArrayList<String> getDestination() {
            return destinations;
        }

        @Override
        public void setInput(boolean input) {
            if (!input) {
                totalL+=1;
            } else{
                totalH+=1;
            }
            this.input = input;
        }

        public boolean handleInput() {
            if (!input) {
                on = !on;
            }

            if (input) {
                stop = true;
            }
            return on;
        }

        public boolean isStop() {
            return stop;
        }

        @Override
        public String getName() {
            return name;
        }
    }

    class Conjunction implements Step {
        String name;
        ArrayList<String> destinations = new ArrayList<>();
        HashMap<String, Boolean> inputs = new HashMap<>();
        private boolean input;
        String line;

        public Conjunction(String name, ArrayList<String> goals) {
            this.name = name;
            destinations = goals;
            steps.put(name, this);
        }

        public void setInputs(ArrayList<String> inputs) {
            for (String input : inputs) {
                this.inputs.put(input, false);
            }
        }

        @Override
        public ArrayList<String> getDestination() {
            return destinations;
        }

        public void setInputAndName(boolean input, String name) {
            if (!input) {
                totalL+=1;
            } else{
                totalH+=1;
            }
            this.inputs.replace(name, input);
        }
        @Override
        public void setInput(boolean input) {
            this.input = input;
        }

        @Override
        public String getName() {
            return name;
        }

        public boolean handleInput() {
            boolean b = this.inputs.values().stream().allMatch((bool -> bool));
            return !b;
        }
    }

    class Broadcaster implements Step {
        ArrayList<String> destinations;
        private boolean input = false;

        public Broadcaster(ArrayList<String> goals) {
            this.destinations = goals;
            steps.put("broadcaster", this);
        }

        @Override
        public ArrayList<String> getDestination() {
            return destinations;
        }

        @Override
        public boolean handleInput() {
            if (!input) {
                totalL+=1;
            } else{
                totalH+=1;
            }
            return input;
        }

        @Override
        public void setInput(boolean input) {
            this.input = input;
        }

        @Override
        public String getName() {
            return "broadcaster";
        }
    }

    interface Step {
        ArrayList<String> getDestination();
        boolean handleInput();

        void setInput(boolean input);

        String getName();
    }
}
