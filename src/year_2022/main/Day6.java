package year_2022.main;

import helpers.CodeDay;
import helpers.*;

import java.util.*;

public class Day6 extends CodeDay {
    @Override
    public void puzzleOne() {
        var input = Helper.readToString(2022, 6);

        LinkedList<Character> list = new LinkedList<>();
        int counter = 0;
        OUTER: for (char c : input.toCharArray()) {
            list.add(c);
            if (list.size() == 5) {
                list.removeFirst();
                System.out.println(list);
                if (new LinkedHashSet<>(list).size() == list.size()) {
                    System.out.println(counter+1);
                    break OUTER;
                }
            }

            counter++;

        }

    }

    @Override
    public void puzzleTwo() {
        var input = Helper.readToString(2022, 6);

        LinkedList<Character> list = new LinkedList<>();
        int counter = 0;
        OUTER: for (char c : input.toCharArray()) {
            list.add(c);
            if (list.size() == 15) {
                list.removeFirst();
                System.out.println(list);
                if (new LinkedHashSet<>(list).size() == list.size()) {
                    System.out.println(counter+1);
                    break OUTER;
                }
            }

            counter++;

        }
    }
  }
