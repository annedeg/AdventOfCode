package year_2023;

import helpers.Helper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class Day15 extends CodeDay {
    HashMap<Integer, ArrayList<String>> hm = new HashMap<>();

    @Override
    public void puzzleOne() {
        var input = Helper.readToString("src/input/day15");
        input = input.replaceAll("\\n", "");
        String[] split = input.split(",");
        int sum = Arrays.stream(split)
                .map(this::hash)
                .mapToInt(x -> x)
                .sum();

        System.out.println(sum);
    }

    private Integer hash(String s) {
        int val = 0;
        for (char c : s.toCharArray()) {
            val += (int) c;
            val *= 17;
            val %= 256;

        }
        return val;
    }

    @Override
    public void puzzleTwo() {
        var input = Helper.readToString("src/input/day15");
        input = input.replaceAll("\\n", "");
        String[] split = input.split(",");

        OUTER:
        for (String sp : split) {
            if (sp.contains("-")) {
                String[] split1 = sp.split("-");
                Integer hash = hash(split1[0]);

                if (hm.containsKey(hash) && hm.get(hash).stream().anyMatch(str -> str.contains(split1[0]))) {
                    ArrayList<String> strings = hm.get(hash);

                    ArrayList<String> newString = new ArrayList<>();

                    for (String string : strings) {
                        if (string.contains(split1[0])) {
                            continue;
                        }

                        newString.add(string);
                    }
                    hm.put(hash, newString);
                }
            } else if (sp.contains("=")) {
                String[] split1 = sp.split("=");
                Integer hash = hash(split1[0]);

                if (!hm.containsKey(hash)) {
                    hm.put(hash, new ArrayList<>());
                }

                ArrayList<String> strings = new ArrayList<>(hm.get(hash));

//                if (strings.stream().anyMatch(str -> str.contains(split1[0]))) {
                int c = 0;
                for (String str : strings) {
                    if (str.contains(split1[0])) {
                        strings.set(c, split1[0] + " " + split1[1]);
                        hm.put(hash, new ArrayList<>(strings));
                        continue OUTER;
                    }
                    c++;
                }
                strings.add(split1[0] + " " + split1[1]);
                hm.put(hash, strings);
            }
        }

        long total = 0;
        int count = 0;
        ArrayList<Integer> integers = new ArrayList<>(hm.keySet());
        for (ArrayList<String> l : hm.values()) {
//            if (count == 255) {
//                break;
//            }

            int slot = 1;
            for (String s : l) {
                long i = (long) (integers.get(count) + 1) * slot * Integer.parseInt(s.split(" ")[1]);
                System.out.println(slot);
                total += i;
                slot += 1;
            }

            count += 1;
        }

        System.out.println(total);
    }
}
