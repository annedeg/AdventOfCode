package year_2024.main;

import helpers.Helper;

import java.util.*;

public class Day24 {
    class Operation {
        Integer v1;
        String v1Name;
        Integer v2;
        String v2Name;

        String resultName;

        public Operation(String v1Name, String v2Name, String resultName) {
            this.v1Name = v1Name;
            this.v2Name = v2Name;
            this.resultName = resultName;
        }

        public void setV1(Integer v1) {
            this.v1 = v1;
        }

        public void setV2(Integer v2) {
            this.v2 = v2;
        }

        public String getV1Name() {
            return v1Name;
        }

        public String getV2Name() {
            return v2Name;
        }

        public String calculate() {
            return resultName;
        }

        Integer result;
        public boolean ready() {
            return v1 != null && v2 != null;
        }

        public boolean done() {
            return ready() && result != null;
        }
    }

    class AND extends Operation {
        public AND(String v1Name, String v2Name, String resultName) {
            super(v1Name, v2Name, resultName);
        }

        @Override
        public String calculate() {
            this.result = v1 & v2;
            return super.calculate();
        }
    }

    class OR extends Operation {
        public OR(String v1Name, String v2Name, String resultName) {
            super(v1Name, v2Name, resultName);
        }

        @Override
        public String calculate() {
            this.result = v1 | v2;
            return super.calculate();
        }
    }

    class XOR extends Operation {
        public XOR(String v1Name, String v2Name, String resultName) {
            super(v1Name, v2Name, resultName);
        }

        @Override
        public String calculate() {
            this.result = v1 ^ v2;
            return super.calculate();
        }
    }

    public void puzzleOne() {
        String s = Helper.readToString(2024, 24);
        String[] input = s.split("\n\n");
        String[] setup = input[0].split("\n");
        String[] operations = input[1].split("\n");

        HashMap<String, Integer> variables = new HashMap<>();

        for (String var : setup) {
            String[] split = var.split(": ");
            variables.put(split[0], Integer.parseInt(split[1]));
        }

        ArrayList<Operation> operationArrayList = new ArrayList<>();
        for (String operation : operations) {
            String[] split = operation.split(" -> ");
            String[] values = split[0].split(" ");

            Operation newOperation = switch (values[1]) {
                case "AND" -> new AND(values[0], values[2], split[1]);
                case "XOR" -> new XOR(values[0], values[2], split[1]);
                case "OR" -> new OR(values[0], values[2], split[1]);
                default -> throw new IllegalStateException("Unexpected value: " + values[1]);
            };

            if (variables.containsKey(newOperation.getV1Name())) {
                newOperation.setV1(variables.get(newOperation.getV1Name()));
            }

            if (variables.containsKey(newOperation.getV2Name())) {
                newOperation.setV2(variables.get(newOperation.getV2Name()));
            }

            operationArrayList.add(newOperation);
        }

        while (!operationArrayList.stream().allMatch(Operation::done)) {
            List<Operation> readies = operationArrayList.stream().filter(Operation::ready).toList();

            for (Operation ready : readies) {
                String calculate = ready.calculate();
                variables.put(calculate, ready.result);
                operationArrayList.stream()
                    .filter(operation -> operation.getV1Name().equals(calculate))
                    .forEach(operation -> operation.setV1(ready.result));

                operationArrayList.stream()
                    .filter(operation -> operation.getV2Name().equals(calculate))
                    .forEach(operation -> operation.setV2(ready.result));
            }
        }

        List<Integer> z = variables.keySet().stream()
            .filter(key -> key.startsWith("z"))
            .sorted()
            .map(variables::get)
            .toList();

        StringBuilder z2 = new StringBuilder();
        for (Integer z1 : z) {
            z2.append(z1);
        }

        System.out.println(Long.parseLong(z2.reverse().toString(),2));
    }

    public void puzzleTwo() {

    }

    public static void main(String[] args) {
        Day24 day = new Day24();
        day.puzzleOne();
        day.puzzleTwo();
    }
}
