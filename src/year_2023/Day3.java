package year_2023;

import helpers.Helper;

import java.util.*;

public class Day3 extends CodeDay {
    @Override
    public void puzzleOne() {
        ArrayList<String> values = Helper.readToStringArrayList("src/input/daythree.txt");
        int total = 0;
        for (var value : values) {
            var partone = toHashSet(value.substring(0, value.length() / 2));
            var parttwo = toHashSet(value.substring(value.length() / 2));
            System.out.println(partone);
            System.out.println(parttwo);
            partone.retainAll(parttwo);
            System.out.println(partone);

            for (Integer item : partone) {


                int val = 0;
                if ((int) item >= 97) {
                    val = item-96;
                } else {
                    val = item-38;
                }

                total += val;


            }
        }
        System.out.println(total);
    }


    public HashSet<Integer> toHashSet(String str) {
        ArrayList<Integer> temp = new ArrayList<>();
        for (char c : str.toCharArray())
            temp.add((int)c);
        return new HashSet<>(temp);
    }

    @Override
    public void puzzleTwo() {
        ArrayList<String> values = Helper.readToStringArrayList("src/input/daythree.txt");
        int total = 0;
        for (int i = 0; i < values.size() -2;i+=3) {
            var partone = toHashSet(values.get(i));
            var parttwo = toHashSet(values.get(i+1));
            var partthree = toHashSet(values.get(i+2));

            System.out.println(i);
            var item = inAllThree(partone, parttwo, partthree);

            System.out.println(item);

            int val = 0;
            if ((int) item >= 97) {
                val = item-96;
            } else {
                val = item-38;
            }

            total += val;
        }
        System.out.println(total);
    }

    private Integer inAllThree(HashSet<Integer> partone, HashSet<Integer> parttwo, HashSet<Integer> partthree) {
        HashMap<Integer, Integer> c = new HashMap<>();
        for (var part : List.of(partone, parttwo, partthree))
            for (var item : part) {
                if (c.containsKey(item))
                    c.put(item, c.get(item) + 1);
                else
                    c.put(item, 1);
            }

        for (var key : c.keySet())
            if (c.get(key) == 3)
                return key;
        return null;
    }

}
