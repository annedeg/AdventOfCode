//package year_2025;
//
//import com.microsoft.z3.*;
//import helpers.Helper;
//
//import java.util.*;
//import java.util.function.IntFunction;
//import java.util.stream.Collectors;
//import java.util.stream.IntStream;
//
//
//public class Day10 {
//    public void puzzleOne() {
//        int result = Helper.readToStringArrayList(2025, 10).stream()
//                .map(MachinePuzzle::new)
//                .map(MachinePuzzle::solveLowest)
//                .mapToInt(Integer::intValue)
//                .sum();
//
//        System.out.println("1: " + result);
//    }
//
//    public void puzzleTwo() {
//        int result = Helper.readToStringArrayList(2025, 10).stream()
//                .map(SecondMachinePuzzle::new)
//                .map(SecondMachinePuzzle::solve)
//                .mapToInt(Integer::intValue)
//                .sum();
//
//        System.out.println("2: " + result);
//    }
//
//    public static void main(String[] args) {
//        Day10 day = new Day10();
////        day.puzzleOne();
//        day.puzzleTwo();
//    }
//}
//
//class MachinePuzzle {
//    int currentLowest = Integer.MAX_VALUE;
//    char[] desiredState;
//    ArrayList<Set<Integer>> buttonWiring = new ArrayList<>();
//
//    MachinePuzzle(String inputLine) {
//        String[] inputLineSplitted = inputLine.split(" ");
//
//        desiredState = inputLineSplitted[0].substring(1, inputLineSplitted[0].length()-1).toCharArray();
//
//        for (int i = 1; i < inputLineSplitted.length-1; i++) {
//            buttonWiring.add(Arrays.stream(inputLineSplitted[i].substring(1, inputLineSplitted[i].length() - 1).split(","))
//                    .map(Integer::parseInt)
//                    .collect(Collectors.toCollection(HashSet::new)));
//        }
//    }
//
//    int solveLowest() {
//        int lowest = Integer.MAX_VALUE;
//        for (Set<Integer> startMove : buttonWiring) {
//            char[] initialState = new char[desiredState.length];
//            for (int i = 0; i < desiredState.length; i++) {
//                initialState[i] =  '.';
//            }
//            tryLowest(initialState, startMove, new ArrayList<>());
//        }
//
//        return this.currentLowest;
//    }
//
//    int tryLowest(char[] currentState, Set<Integer> currentTry, ArrayList<Set<Integer>> history) {
//        if (Arrays.equals(desiredState, currentState)) {
//            // doel bereikt, stop!
//            String string = history.stream().map(String::valueOf).collect(Collectors.joining(" "));
//            return history.size();
//        }
//
//        if (history.size() + 1 >= currentLowest || (!history.isEmpty() && history.get(history.size()-1).equals(currentTry)) || history.size() > 10) {
//            // prevent double actions after each other
//            return Integer.MAX_VALUE;
//        }
//
//        history.add(currentTry);
//
//        for (Integer buttonToChange : currentTry) {
//            currentState[buttonToChange] = currentState[buttonToChange] == '.' ? '#' : '.';
//        }
//
//        for (Set<Integer> possibleTry : buttonWiring) {
//            int i = tryLowest(Helper.deepCopy(currentState), possibleTry, new ArrayList<>(history));
//
//            if (i < currentLowest) {
//                currentLowest = i;
//            }
//        }
//
//        return Integer.MAX_VALUE;
//    }
//}
//
//class SecondMachinePuzzle {
//    int[] desiredState;
//    ArrayList<Set<Integer>> buttonWiring = new ArrayList<>();
//    HashMap<Integer, ArrayList<IntExpr>> counterToButtons = new HashMap<>();
//
//    SecondMachinePuzzle(String inputLine) {
//        String[] inputLineSplitted = inputLine.split(" ");
//
//        desiredState = Arrays.stream(inputLineSplitted[inputLineSplitted.length - 1].substring(1, inputLineSplitted[inputLineSplitted.length - 1].length() - 1).split(","))
//                .mapToInt(Integer::parseInt)
//                .toArray();
//
//        for (int i = 1; i < inputLineSplitted.length-1; i++) {
//            buttonWiring.add(Arrays.stream(inputLineSplitted[i].substring(1, inputLineSplitted[i].length() - 1).split(","))
//                    .map(Integer::parseInt)
//                    .collect(Collectors.toCollection(HashSet::new)));
//        }
//    }
//
//    int solve() {
//        Context context = new Context();
//        Optimize optimize = context.mkOptimize();
//        IntExpr presses = context.mkIntConst("presses");
//
//        IntExpr[] buttonVars = IntStream.range(0, buttonWiring.size())
//                .mapToObj((IntFunction<?>) i -> context.mkIntConst("button" + i))
//                .toArray(IntExpr[]::new);
//
//        int buttonNum = 0;
//        for (Set<Integer> wiring : buttonWiring) {
//            IntExpr buttonVar = buttonVars[buttonNum];
//
//            for (Integer wire : wiring) {
//                if (!counterToButtons.containsKey(wire)) {
//                    counterToButtons.put(wire, new ArrayList<>());
//                }
//                counterToButtons.get(wire).add(buttonVar);
//            }
//            buttonNum++;
//        }
//
//        for (Map.Entry<Integer, ArrayList<IntExpr>> entry : counterToButtons.entrySet()) {
//            int index = entry.getKey();
//            ArrayList<IntExpr> counterButtons = entry.getValue();
//
//            IntExpr target = context.mkInt(desiredState[index]);
//
//            IntExpr[] buttonPressesArray = counterButtons.toArray(new IntExpr[0]);
//            IntExpr sum = (IntExpr) context.mkAdd(buttonPressesArray);
//
//            BoolExpr eq = context.mkEq(target, sum);
//            optimize.Add(eq);
//        }
//
//        IntNum zero = context.mkInt(0);
//        for (IntExpr but : buttonVars) {
//            BoolExpr eq = context.mkGe(but, zero);
//            optimize.Add(eq);
//        }
//
//        IntExpr sumOfAllButtonVars = (IntExpr) context.mkAdd(buttonVars);
//        BoolExpr totalPressesEq = context.mkEq(presses, sumOfAllButtonVars);
//        optimize.Add(totalPressesEq);
//
//        optimize.MkMinimize(presses);
//
//        Status status = optimize.Check();
//
//        if (status == Status.SATISFIABLE) {
//            Model model = optimize.getModel();
//            IntNum outputValue = (IntNum) model.evaluate(presses, false);
//            return outputValue.getInt();
//        }
//
//        return -1000;
//    }
//}