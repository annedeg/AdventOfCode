package year_2024.main;

import helpers.Helper;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.SingleGraph;
import org.graphstream.ui.view.Viewer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

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
        HashMap<String, Integer> variables = createVariables();
        ArrayList<Operation> operations = createOperations(variables);
        System.out.println(getZOutput(operations, variables));
    }

    public void puzzleTwo() {
        System.setProperty("org.graphstream.ui", "swing");
        ArrayList<Operation> operations = createOperations(createVariables());

        Graph graph = new SingleGraph("todo");
        for (Operation operation : operations) {
            addToGraph(graph, operation.v1Name, operation);
            addToGraph(graph, operation.v2Name, operation);
            addToGraph(graph, operation.resultName, operation);
            graph.addEdge(operation.v1Name + operation.resultName, operation.v1Name, operation.resultName);
            graph.addEdge(operation.v2Name + operation.resultName,operation.v2Name, operation.resultName);
        }

        graph.setAttribute("ui.stylesheet", """
                    node {
                        text-size: 14px;
                    }
                    node.or {
                        fill-color: orange;
                    }
                    node.and {
                        fill-color: purple;
                    }
                    node.xor {
                        fill-color: yellow;
                    }
                    node.marked {
                        fill-color: red;
                        text-size: 30px;
                    }
                    node.zet {
                        fill-color: blue;
                        text-size: 30px;
                    }
                """);
        Viewer display = graph.display();
    }

    private Graph addToGraph(Graph graph, String f, Operation operation) {
        if (graph.getNode(f) == null) {
            Node edges = graph.addNode(f);
            if (operation instanceof OR) {
                edges.setAttribute("ui.class", "or");
            }
            if (operation instanceof AND) {
                edges.setAttribute("ui.class", "and");
            }
            if (operation instanceof XOR) {
                edges.setAttribute("ui.class", "xor");
            }


            if (f.startsWith("y")) {
                edges.setAttribute("ui.class", "zet");
                edges.setAttribute("ui.label", f);
            }
            if (f.startsWith("x")) {
                edges.setAttribute("ui.class", "zet");
                edges.setAttribute("ui.label", f);
            }
            if (f.startsWith("z")) {
                edges.setAttribute("ui.class", "marked");
                edges.setAttribute("ui.label", f);
            }

            edges.setAttribute("ui.label", f);
        }
        return graph;
    }

    private Long getZOutputWithDifferentInput(String x, String y) {
        if (x.length() != y.length()) {
            return 0L;
        }

        HashMap<String, Integer> variables = createVariables();

        ArrayList<String> xl = new ArrayList<>();
        ArrayList<String> yl = new ArrayList<>();

        for (String key : variables.keySet()) {
            if (key.startsWith("x")) {
                xl.add(key);
            }

            if (key.startsWith("y")) {
                yl.add(key);
            }
        }

        if (x.length() != xl.size()) {
            System.out.println("size is fout");
            return 0L;
        }

        Collections.sort(xl);
        Collections.sort(yl);

        for (int i = 0; i < x.length(); i++) {
            int xc = Integer.parseInt(String.valueOf(x.charAt(i)));
            int yc = Integer.parseInt(String.valueOf(y.charAt(i)));

            variables.put(xl.get(i), xc);
            variables.put(yl.get(i), yc);
        }

        ArrayList<Operation> operations = createOperations(variables);
        return getZOutput(operations, variables);
    }

    private HashMap<String, Integer> createVariables() {
        String s = Helper.readToString(2024, 24);
        String[] input = s.split("\n\n");
        String[] setup = input[0].split("\n");

        HashMap<String, Integer> variables = new HashMap<>();

        for (String var : setup) {
            String[] split = var.split(": ");
            variables.put(split[0], Integer.parseInt(split[1]));
        }

        return variables;
    }

    private ArrayList<Operation> createOperations(HashMap<String, Integer> variables) {
        String s = Helper.readToString(2024, 24);
        String[] input = s.split("\n\n");
        String[] operations = input[1].split("\n");

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

        return operationArrayList;
    }

    private Long getZOutput(ArrayList<Operation> operations, HashMap<String, Integer> variables) {
        while (!operations.stream().allMatch(Operation::done)) {
            List<Operation> readies = operations.stream().filter(Operation::ready).toList();

            for (Operation ready : readies) {
                String calculate = ready.calculate();
                variables.put(calculate, ready.result);
                operations.stream()
                    .filter(operation -> operation.getV1Name().equals(calculate))
                    .forEach(operation -> operation.setV1(ready.result));

                operations.stream()
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

        return Long.parseLong(z2.reverse().toString(),2);
    }

    public static void main(String[] args) {
        Day24 day = new Day24();
        day.puzzleOne();
        day.puzzleTwo();
    }
}
