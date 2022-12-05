import helpers.Helper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.IntStream;

public class Day5 extends CodeDay {
    @Override
    public void puzzleOne() {
//[H]                 [Z]         [J]
//[L]     [W] [B]     [G]         [R]
//[R]     [G] [S]     [J] [H]     [Q]
//[F]     [N] [T] [J] [P] [R]     [F]
//[B]     [C] [M] [R] [Q] [F] [G] [P]
//[C] [D] [F] [D] [D] [D] [T] [M] [G]
//[J] [C] [J] [J] [C] [L] [Z] [V] [B]
//[M] [Z] [H] [P] [N] [W] [P] [L] [C]
// 1   2   3   4   5   6   7   8   9
        ArrayList<LinkedList<Character>> crates = new ArrayList<>();
        crates.add(new LinkedList<Character>(Arrays.asList('M', 'J', 'C', 'B', 'F', 'R', 'L', 'H')));
        crates.add(new LinkedList<Character>(Arrays.asList('Z', 'C', 'D')));
        crates.add(new LinkedList<Character>(Arrays.asList('H', 'J', 'F', 'C', 'N', 'G', 'W')));
        crates.add(new LinkedList<Character>(Arrays.asList('P', 'J', 'D', 'M', 'T', 'S', 'B')));
        crates.add(new LinkedList<Character>(Arrays.asList('N', 'C', 'D', 'R', 'J')));
        crates.add(new LinkedList<Character>(Arrays.asList('W', 'L', 'D', 'Q', 'P', 'J', 'G', 'Z')));
        crates.add(new LinkedList<Character>(Arrays.asList('P', 'Z', 'T', 'F', 'R', 'H')));
        crates.add(new LinkedList<Character>(Arrays.asList('L', 'V', 'M', 'G')));
        crates.add(new LinkedList<Character>(Arrays.asList('C', 'B', 'G', 'P', 'F', 'Q', 'R', 'J')));
//        crates.add(new LinkedList<>(Arrays.asList('Z', 'N')));
//        crates.add(new LinkedList<>(Arrays.asList('M', 'C', 'D')));
//        crates.add(new LinkedList<>(List.of('P')));



        var input = Helper.readToStringArrayList("src/input/dayfive.txt");
        System.out.println(crates);
        for (var line : input) {
            var res = line.split(" ");
            var amount = Integer.parseInt(res[1]);
            var from = Integer.parseInt(res[3]) - 1;
            var to = Integer.parseInt(res[5]) - 1;

            for (int i = 0; i < amount; i++) {
                crates.get(to).addLast(crates.get(from).removeLast());
            }

            System.out.println(crates);

        }


        for (int i = 0; i < crates.size(); i++) {
            System.out.print(crates.get(i).getLast());
        }
    }

    @Override
    public void puzzleTwo() {
        var input = Helper.readToStringArrayList("src/input/dayfive.txt");
        ArrayList<LinkedList<Character>> crates = new ArrayList<>();
        crates.add(new LinkedList<Character>(Arrays.asList('M', 'J', 'C', 'B', 'F', 'R', 'L', 'H')));
        crates.add(new LinkedList<Character>(Arrays.asList('Z', 'C', 'D')));
        crates.add(new LinkedList<Character>(Arrays.asList('H', 'J', 'F', 'C', 'N', 'G', 'W')));
        crates.add(new LinkedList<Character>(Arrays.asList('P', 'J', 'D', 'M', 'T', 'S', 'B')));
        crates.add(new LinkedList<Character>(Arrays.asList('N', 'C', 'D', 'R', 'J')));
        crates.add(new LinkedList<Character>(Arrays.asList('W', 'L', 'D', 'Q', 'P', 'J', 'G', 'Z')));
        crates.add(new LinkedList<Character>(Arrays.asList('P', 'Z', 'T', 'F', 'R', 'H')));
        crates.add(new LinkedList<Character>(Arrays.asList('L', 'V', 'M', 'G')));
        crates.add(new LinkedList<Character>(Arrays.asList('C', 'B', 'G', 'P', 'F', 'Q', 'R', 'J')));
//        crates.add(new LinkedList<>(Arrays.asList('Z', 'N')));
//        crates.add(new LinkedList<>(Arrays.asList('M', 'C', 'D')));
//        crates.add(new LinkedList<>(List.of('P')));


        System.out.println(crates);
        for (var line : input) {
            var res = line.split(" ");
            var amount = Integer.parseInt(res[1]);
            var from = Integer.parseInt(res[3]) - 1;
            var to = Integer.parseInt(res[5]) - 1;

            ArrayList<Character> remember = new ArrayList<>();
            for (int i = 0; i < amount; i++) {
                remember.add(crates.get(from).removeLast());
            }

            for (int i = remember.size() - 1; i >= 0; i--) {
                crates.get(to).addLast(remember.get(i));
            }

            System.out.println(crates);

        }


        for (int i = 0; i < crates.size(); i++) {
            System.out.print(crates.get(i).getLast());
        }
    }

  }
