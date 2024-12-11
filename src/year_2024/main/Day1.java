package year_2024.main;

import helpers.*;

import java.util.ArrayList;
import java.util.List;

public class Day1 {
    ArrayList<Integer> listOne;
    ArrayList<Integer> listTwo;
    public void handleInput() {
        listOne = new ArrayList<>();
        listTwo = new ArrayList<>();

        ArrayList<String> rows = Helper.readToStringArrayList(2024, 1);
        rows.stream().map(row -> row.split(" {3}")).forEach(arr -> {
            String s1 = arr[0];
            String s2 = arr[1];

            listOne.add(Integer.parseInt(s1));
            listTwo.add(Integer.parseInt(s2));
        });
    }
    public void puzzleOne() {
        handleInput();
        List<Integer> sortedListOne = listOne.stream().sorted().toList();
        List<Integer> sortedListTwo = listTwo.stream().sorted().toList();
        int total = 0;
        for (int i = 0; i < sortedListOne.size(); i++) {
            total += Math.abs(sortedListOne.get(i) - sortedListTwo.get(i));
        }
        System.out.println(total);
    }

    public void puzzleTwo() {
        handleInput();
        int total = 0;
        for (int i = 0; i < listOne.size(); i++) {
            total += listOne.get(i) * sim(listTwo, listOne.get(i));
        }
        System.out.println(total);
    }

    public int sim(List<Integer> list, int num) {
        return (int) list.stream().filter(integer -> integer == num).count();
    }

    public static void main(String[] args) {
        Day1 day1 = new Day1();
        day1.puzzleOne();
        day1.puzzleTwo();
    }
}
