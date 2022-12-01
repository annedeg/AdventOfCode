import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DayOne extends CodeDay {
    @Override
    public void puzzleOne() {
        ArrayList<ArrayList<Integer>> values;
        try(var br = new BufferedReader(new FileReader("src/input/dayone.txt"))) {
            values = new ArrayList<>();
            values.add(new ArrayList<>());
            int counter = 0;

            String line;
            while((line = br.readLine()) != null) {
                if (line.length() == 0) {
                    values.add(new ArrayList<>());
                    counter++;
                } else {
                    values.get(counter).add(Integer.valueOf(line));
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        List<Integer> sums = new ArrayList<>();
        for (var list : values) {
            int sum = 0;
            for (var val : list)
                sum += val;
            sums.add(sum);
        }

        sums.sort(Collections.reverseOrder());
        System.out.println(sums.get(0));
    }

    @Override
    public void puzzleTwo() {
        ArrayList<ArrayList<Integer>> values;
        try(var br = new BufferedReader(new FileReader("src/input/dayone.txt"))) {
            values = new ArrayList<>();
            values.add(new ArrayList<>());
            int counter = 0;

            String line;
            while((line = br.readLine()) != null) {
                if (line.length() == 0) {
                    values.add(new ArrayList<>());
                    counter++;
                } else {
                    values.get(counter).add(Integer.valueOf(line));
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        List<Integer> sums = new ArrayList<>();
        for (var list : values) {
            int sum = 0;
            for (var val : list)
                sum += val;
            sums.add(sum);
        }

        sums.sort(Collections.reverseOrder());
        System.out.println(sums.get(0) + sums.get(1) + sums.get(2));
    }
}
