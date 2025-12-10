package year_2025;

import helpers.Helper;
import helpers.Node3D;

import java.util.*;
import java.util.stream.Collectors;

public class Day8 {
    public void puzzleOne() {
        ArrayList<Node3D> nodes = Helper.readToStringArrayList(2025, 8).stream()
                .map(str -> {
                    String[] split = str.split(",");
                    return new Node3D(Long.parseLong(split[0]), Long.parseLong(split[1]), Long.parseLong(split[2]));
                })
                .collect(Collectors.toCollection(ArrayList::new));

        HashMap<ArrayList<Node3D>, Double> distance = new HashMap<>();
         for (Node3D node3D1 : nodes) {
            for (Node3D node3D2 : nodes) {
                if (node3D1.equals(node3D2)) {
                    continue;
                }

                ArrayList<Node3D> list = new ArrayList<>(List.of(node3D1, node3D2));
                list.sort(Comparator.comparing(node3D -> node3D.nodeNum));

                if (!distance.containsKey(list)) {
                    distance.put(list, node3D1.calcDistance(node3D2));
                }
            }
        }

        //sort
        HashMap<ArrayList<Node3D>, Double> sortedMap = sortMap(distance);


        ArrayList<ArrayList<Node3D>> circuits = new ArrayList<>();

        for (Node3D node3D : nodes) {
            circuits.add(new ArrayList<>(Collections.singleton(node3D)));
        }

        List<ArrayList<Node3D>> collect = new ArrayList<>(sortedMap.keySet());



        for (int i = 0; i < 1000; i++) {
            ArrayList<Node3D> bestCombination = collect.get(i);
            Node3D left = bestCombination.get(0);
            Node3D right = bestCombination.get(1);


            if (inCombinations(left, circuits).equals(inCombinations(right, circuits))) {
                // zitten al bij elkaar
                continue;
            }

            ArrayList<Node3D> left3ds = (inCombinations(left, circuits));
            ArrayList<Node3D> right3ds = (inCombinations(right, circuits));

            left3ds.addAll(right3ds);
            right3ds.clear();
        }

        circuits.sort(Comparator.comparing(l -> -l.size()));

        long total = (long) circuits.get(0).size() * circuits.get(1).size() * circuits.get(2).size();
        System.out.println("1: " + total);
    }

    public <T> HashMap<ArrayList<T>, Double> sortMap(HashMap<ArrayList<T>, Double> unsorted) {
        return unsorted.entrySet().stream()
                        .sorted(Map.Entry.comparingByValue())
                        .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                                (e1, e2) -> e1, LinkedHashMap::new));
    }

    public ArrayList<Node3D> inCombinations(Node3D node, ArrayList<ArrayList<Node3D>> circuits) {
        for (ArrayList<Node3D> circuit : circuits) {
            if (circuit.contains(node)) {
                return circuit;
            }
        }

        return new ArrayList<>();
    }

    public void puzzleTwo() {
        ArrayList<Node3D> nodes = Helper.readToStringArrayList(2025, 8).stream()
                .map(str -> {
                    String[] split = str.split(",");
                    return new Node3D(Long.parseLong(split[0]), Long.parseLong(split[1]), Long.parseLong(split[2]));
                })
                .collect(Collectors.toCollection(ArrayList::new));

        HashMap<ArrayList<Node3D>, Double> distance = new HashMap<>();
        for (Node3D node3D1 : nodes) {
            for (Node3D node3D2 : nodes) {
                if (node3D1.equals(node3D2)) {
                    continue;
                }

                ArrayList<Node3D> list = new ArrayList<>(List.of(node3D1, node3D2));
                list.sort(Comparator.comparing(node3D -> node3D.nodeNum));

                if (!distance.containsKey(list)) {
                    distance.put(list, node3D1.calcDistance(node3D2));
                }
            }
        }

        //sort
        HashMap<ArrayList<Node3D>, Double> sortedMap = sortMap(distance);


        ArrayList<ArrayList<Node3D>> circuits = new ArrayList<>();

        for (Node3D node3D : nodes) {
            circuits.add(new ArrayList<>(Collections.singleton(node3D)));
        }

        List<ArrayList<Node3D>> collect = new ArrayList<>(sortedMap.keySet());


        int i = 0;
        ArrayList<Node3D> prevCom = null;
        while (true) {
            if (circuits.get(1).isEmpty()) {
                System.out.println("2: " + prevCom.get(0).x * prevCom.get(1).x);
                break;
            }

            ArrayList<Node3D> bestCombination = collect.get(i);
            prevCom = bestCombination;

            i++;

            Node3D left = bestCombination.get(0);
            Node3D right = bestCombination.get(1);


            if (inCombinations(left, circuits).equals(inCombinations(right, circuits))) {
                // zitten al bij elkaar
                continue;
            }

            ArrayList<Node3D> left3ds = (inCombinations(left, circuits));
            ArrayList<Node3D> right3ds = (inCombinations(right, circuits));

            left3ds.addAll(right3ds);
            right3ds.clear();


            circuits.sort(Comparator.comparing(l -> -l.size()));
        }
    }



    public static void main(String[] args) {
        Day8 day = new Day8();
        day.puzzleOne();
        day.puzzleTwo();
    }
}
