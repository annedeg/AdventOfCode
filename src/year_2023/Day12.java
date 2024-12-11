package year_2023;

import helpers.*;

import java.util.*;
import java.util.stream.Collectors;

public class Day12 extends CodeDay {
    HashMap<String, Long> h = new HashMap<>();

    @Override
    public void puzzleOne() {
        var input = Helper.readToStringArrayList("src/input/day12");

        long total = 0;
        for (String line : input) {
            total += getAmountOfArrangements(line);
        }

        System.out.println(total);
    }

    public long getAmountOfArrangements(String line) {
        String[] s = line.split(" ");
        line = s[0];
        ArrayList<Integer> hint = Arrays.stream(s[1].split(",")).mapToInt(Integer::parseInt).boxed().collect(Collectors.toCollection(ArrayList::new));

        ArrayList<String> parts = new ArrayList<>();
        seq("", parts, line, hint);

        return parts.size();
    }

    private boolean checkPart(String line, String referenceLine, ArrayList<Integer> hint) {
//        (line+hint).hashCode()
        if (line.isEmpty()) {return true;}
        if (line.length() > referenceLine.length()) {return false;}
        line = line.replaceAll("0", ".").replaceAll("1", "#");

        long sum = Arrays.stream(line.split("")).filter(str -> str.equals("#")).count();
        if (sum > hint.stream().mapToInt(x->x).sum()) {
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

        for (int c = 0; c < res.size() - 1; c++) {
            if (c < hint.size() && !res.get(c).equals(hint.get(c))) {
                return false;
            }
        }

        return true;
    }

    private void seq(String line, ArrayList<String> add, String reference, ArrayList<Integer> hint) {
        if (!checkPart(line, reference, hint)) {
            return;
        }

        if (reference.length() == line.length() && isAllowed(line, reference, hint)) {
            add.add(line);
            return;
        }

        seq(line + "#", add, reference, hint);
        seq(line + ".", add, reference, hint);
    }

    private void binSeq(int n, int k, String seq, ArrayList<String> add, String reference, ArrayList<Integer> hint) {
        if (!checkPart(seq, reference, hint)) {
            return;
        }

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
        long total = 0;
////        int c = 0;
////
////        ExecutorService executorService = Executors.newCachedThreadPool();
////        ArrayList<Callable<Long>> callables = new ArrayList<>();
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

//            int[] array = Arrays.stream(newHint.toString().split(",")).mapToInt(Integer::parseInt).toArray();
            int[] array = Arrays.stream(s[1].split(",")).mapToInt(Integer::parseInt).toArray();
            ArrayList<Boolean> f = new ArrayList<>();
            f.add(false);
            for (int a : array) {
                for (int i = 0; i < a; i++) {
                    f.add(true);
                }
                f.add(false);
            }

            boolean[] booleans = new boolean[f.size()];
            int c=0;
            for (boolean ff : f) {
                booleans[c] = ff;
                c+=1;
            }

            long l = countArrangements(("."+ s[0] +".").toCharArray(), booleans);
            total+=l;
        }
////
////        try {
////            List<Future<Long>> results = executorService.invokeAll(callables);
////
////            for (Future<Long> res : results) {
////                Long l = res.get();
////                System.out.println(l);
////                total += l;
////            }
////
////            System.out.println(total);
////            System.exit(1);
////        } catch (InterruptedException | ExecutionException e) {
////            throw new RuntimeException(e);
////        }

        System.out.println(total);
    }

    private static long countArrangements(char[] chars, boolean[] springs){
        int n = chars.length;
        int m = springs.length;
        long[][] dp = new long[n+1][m+1];
        dp[n][m] = 1;

        for (int i = n - 1; i >= 0; i--) {
            for (int j = m - 1; j >= 0; j--) {
                boolean damaged = false, operational = false;
                switch (chars[i]){
                    case '#': {
                        damaged = true;
                        break;
                    }
                    case '.':{
                        operational = true;
                        break;
                    }
                    default:{
                        operational = true;
                        damaged = true;
                    }
                }
                long sum = 0;
                if(damaged && springs[j]){
                    sum += dp[i+1][j+1];
                } else if (operational && !springs[j]) {
                    sum += dp[i+1][j+1] + dp[i+1][j];
                }
                dp[i][j] = sum;
            }
        }
        return dp[0][0];
    }

}