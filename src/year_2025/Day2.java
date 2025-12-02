package year_2025;

import helpers.Helper;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicLong;

public class Day2 {
    public void puzzleOne() {
        AtomicLong totalIds = new AtomicLong();
        String s = Helper.readToString(2025, 2);
        Arrays.stream(s.split(",")).forEach(ids -> {
            String[] splittedIds = ids.split("-");
            String firstId = splittedIds[0].replace("\n", "").replace("\r", "");;
            String secondId = splittedIds[1].replace("\n", "").replace("\r", "");;

            long firstIdLong = Long.parseLong(firstId);
            long secondIdLong = Long.parseLong(secondId);

            for (;firstIdLong <= secondIdLong;firstIdLong++) {
                if (invalidId(firstIdLong)) {
                    totalIds.addAndGet(firstIdLong);
                }
            }
        });

        System.out.println("1:" + totalIds.get());
    }

    public boolean invalidId(long id) {
        String longInString = String.valueOf(id);
        return longInString.substring(0, (longInString.length() / 2)).equals(longInString.substring((longInString.length() / 2)));
    }

    public void puzzleTwo() {
        AtomicLong totalIds = new AtomicLong();
        String s = Helper.readToString(2025, 2);
        Arrays.stream(s.split(",")).forEach(ids -> {
            String[] splittedIds = ids.split("-");
            String firstId = splittedIds[0].replace("\n", "").replace("\r", "");;
            String secondId = splittedIds[1].replace("\n", "").replace("\r", "");;

            long firstIdLong = Long.parseLong(firstId);
            long secondIdLong = Long.parseLong(secondId);

            for (;firstIdLong <= secondIdLong;firstIdLong++) {
                if (invalidId(firstIdLong)) {
                    totalIds.addAndGet(firstIdLong);
                } else if (extraInvalid(firstIdLong)) {
                    totalIds.addAndGet(firstIdLong);
                }
            }
        });

        System.out.println("2:" + totalIds.get());
    }

    private boolean extraInvalid(long firstIdLong) {
        String firstIdString = String.valueOf(firstIdLong);
        for (int i = 1; i <= firstIdString.length() / 2; i++) {
            String subString = firstIdString.substring(0, i);

            if (firstIdString.length() % subString.length() != 0) {
                continue;
            }

            int amountNeeded = firstIdString.length() / subString.length();
            StringBuilder reconstructedString = new StringBuilder();

            reconstructedString.append(subString.repeat(amountNeeded));

            if (reconstructedString.toString().equals(firstIdString)) {
                return true;
            }
        }

        return false;
    }

    public static void main(String[] args) {
        Day2 day = new Day2();
        day.puzzleOne();
        day.puzzleTwo();
    }
}
