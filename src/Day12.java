import helpers.*;

import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;

public class Day12 extends CodeDay {

    @Override
    public void puzzleOne() {
        var input = Helper.readToStringArrayList("src/input/day12");

        int total = 0;
        for (String line : input) {
            int amountOfArrangements = getAmountOfArrangements(line);
            total += amountOfArrangements;
        }

        System.out.println(total);
    }

    public int getAmountOfArrangements(String line) {
        String[] s = line.split(" ");
        line = s[0];
        ArrayList<Integer> hint = Arrays.stream(s[1].split(",")).mapToInt(Integer::parseInt).boxed().collect(Collectors.toCollection(ArrayList::new));

        System.out.println(line);

        ArrayList<String> parts = new ArrayList<>();
        binSeq(line.length(), hint.stream().mapToInt(x->x).sum(), "", parts, line, hint);

        return 0;
    }

    private boolean checkPart(String line, String referenceLine, ArrayList<Integer> hint) {
        if (line.length() == 0) {return true;}
        line = line.replaceAll("0", ".").replaceAll("1", "#");
        for (int i = 0; i < line.length(); i ++) {
            if (referenceLine.charAt(i) == '#' && line.charAt(i) != '#') {
                System.out.println(line + " nope");
                return false;
            }

            if (referenceLine.charAt(i) == '.' && line.charAt(i) != '.') {
                System.out.println(line + " nope");
                return false;
            }
        }

        ArrayList<Integer> res = Arrays.stream(line.split("\\.")).filter(str -> !str.isEmpty()).map(String::length).collect(Collectors.toCollection(ArrayList::new));

        for (int c = 0; c < res.size(); c++) {
            if (!res.get(c).equals(hint.get(c))) {
                System.out.println(line + " nope");
                return false;
            }
        }

        System.out.println(line + " yes");
        return true;
    }

    private void binSeq(int n, int k, String seq, ArrayList<String> add, String reference, ArrayList<Integer> hint) {
        System.out.println(seq);
//        if (!checkPart(seq, reference, hint)) {
//            return;
//        }

        if (n == 0) {
            add.add(seq);
            return;
        }

        if (n > k) {
            binSeq(n - 1, k, seq + "0", add, reference, hint);
        }

        if (k > 0) {
            binSeq(n - 1, k - 1, seq + "1", add, reference, hint);
        }
    }

    public boolean isAllowed(String line, String referenceLine, ArrayList<Integer> hint) {
        line = line.replaceAll("0", ".").replaceAll("1", "#");
        if (line.length() != referenceLine.length()) {
            return false;
        }

        for (int i = 0; i < line.length(); i ++) {
            if (referenceLine.charAt(i) == '#' && line.charAt(i) != '#') {
                return false;
            }

            if (referenceLine.charAt(i) == '.' && line.charAt(i) != '.') {
                return false;
            }
        }

        ArrayList<Integer> res = Arrays.stream(line.split("\\.")).filter(str -> !str.isEmpty()).map(String::length).collect(Collectors.toCollection(ArrayList::new));
        return res.equals(hint);
    }


    @Override
    public void puzzleTwo() {
        var input = Helper.readToStringArrayList("src/input/day12");
        int total = 0;
        for (String line : input) {
            String[] s = line.split(" ");

            StringBuilder newLine = new StringBuilder();
            StringBuilder newHint = new StringBuilder();
            for (int i = 0; i < 5; i++) {
                if (i == 4) {
                    newLine.append(s[0]);
                    newHint.append(s[1]);
                    continue;
                }
                newLine.append(s[0]).append("?");
                newHint.append(s[1]).append(",");
            }

            String fullLine = newLine.toString() + " " + newHint.toString();
            System.out.println(fullLine);
            int amountOfArrangements = getAmountOfArrangements(fullLine);
            total += amountOfArrangements;
        }

        System.out.println(total);
    }

}