import helpers.Helper;

import java.util.List;
import java.util.stream.IntStream;

public class Day4 extends CodeDay {
    @Override
    public void puzzleOne() {
        var input = Helper.readToStringArrayList("src/input/dayfour.txt");
        int output = 0;

        for (var row:input) {
            String[] ranges = row.split(",");
            output += areInEachother(ranges);
        }

        System.out.println(output);
    }

    public int areInEachother(String[] strings) {
        int amount = 0;
        var nums1 = strings[0].split("-");
        var nums2 = strings[1].split("-");
        int r11 = Integer.parseInt(nums1[0]);
        int r12 = Integer.parseInt(nums1[1]);
        int r21 = Integer.parseInt(nums2[0]);
        int r22 = Integer.parseInt(nums2[1]);
        if ((r11 <= r21 && r12 >= r22) || (r21 <= r11 && r22 >= r12))
            amount++;

        return amount;
    }
    @Override
    public void puzzleTwo() {
        var input = Helper.readToStringArrayList("src/input/dayfour.txt");
        int output = 0;

//        return;
        for (var row:input) {

            String[] ranges = row.split(",");
            System.out.println(row);
            System.out.println(areAnyInEachOther(ranges));
            output += areAnyInEachOther(ranges);
        }

        System.out.println(output);
    }

    private int areAnyInEachOther(String[] strings) {
        int amount = 0;
        var nums1 = strings[0].split("-");
        var nums2 = strings[1].split("-");
        int r11 = Integer.parseInt(nums1[0]);
        int r12 = Integer.parseInt(nums1[1]);
        int r21 = Integer.parseInt(nums2[0]);
        int r22 = Integer.parseInt(nums2[1]);

        List<Integer> range1 = IntStream.rangeClosed(r11, r12)
                .boxed().toList();

        List<Integer> range2 = IntStream.rangeClosed(r21, r22)
                .boxed().toList();

        for (var i : range1)
            if (range2.contains(i)) {
                return 1;
            }

        for (var i : range2)
            if (range1.contains(i)) {
                return 1;
            }

        return amount;
    }
}
