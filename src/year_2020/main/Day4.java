package year_2020.main;

import helpers.Day;
import helpers.Helper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day4 implements Day {
    @Override
    public void partOne() {
        int sum = Arrays.stream(Helper.readToString(2020, 4).split("\n\n"))
                .map(str -> String.join(" ", str.split("\n")))
                .mapToInt(str -> checkPassport(str) ? 1 : 0)
                .sum();
        System.out.println(sum);
    }

    public boolean checkPassport(String passport) {
        ArrayList<String> requiredFields = Stream.of("byr", "iyr", "eyr", "hgt", "hcl", "ecl", "pid").collect(Collectors.toCollection(ArrayList::new));

        return requiredFields.stream()
                .allMatch(str -> Arrays.stream(passport.split(" ")).noneMatch(s -> s.split(":").length == 1) && passport.contains(str + ":"));
    }


    public boolean checkPassportStrict(String passport) {
        if (!checkPassport(passport)) {
            return false;
        }

        for (String field : passport.split(" ")) {
            String[] split = field.split(":");

            String toCheck = split[1];
            boolean valid = true;
            try {
                switch (split[0]) {
                    case "byr" -> {
                        valid = Pattern.compile("[0-9]{4}").matcher(toCheck).matches();
                        int i = Integer.parseInt(toCheck);
                        if (!(i >= 1920 && i <= 2002)) {
                            return false;
                        }
                    }
                    case "iyr" -> {
                        valid = Pattern.compile("[0-9]{4}").matcher(toCheck).matches();
                        int j = Integer.parseInt(toCheck);
                        if (!(j >= 2010 && j <= 2020)) {
                            return false;
                        }
                    }
                    case "eyr" -> {
                        valid = Pattern.compile("[0-9]{4}").matcher(toCheck).matches();
                        int k = Integer.parseInt(toCheck);
                        if (!(k >= 2020 && k <= 2030)) {
                            return false;
                        }
                    }
                    case "hgt" -> {
                        boolean cm = split[1].contains("cm");
                        int l = Integer.parseInt(toCheck.substring(0, toCheck.length()-2));

                        if (!(cm && l >= 150 && l <= 193) && !(!cm && l >= 59 && l <= 76)) {
                            return false;
                        }
                    }
                    case "hcl" -> valid = Pattern.compile("#[0-9a-f]{6}").matcher(toCheck).matches();
                    case "ecl" -> valid = List.of("amb", "blu", "brn", "gry", "grn", "hzl", "oth").contains(toCheck);
                    case "pid" -> valid = Pattern.compile("[0-9]{9}").matcher(toCheck).matches();
                }
            } catch (Exception e) {
                valid = false;
            }

            if (!valid) {
                return false;
            }
        }

        ArrayList<String> requiredFields = Stream.of("byr", "iyr", "eyr", "hgt", "hcl", "ecl", "pid").collect(Collectors.toCollection(ArrayList::new));

        return requiredFields.stream()
                .allMatch(str -> Arrays.stream(passport.split(" ")).noneMatch(s -> s.split(":").length == 1) && passport.contains(str + ":"));
    }

    @Override
    public void partTwo() {
        int sum = Arrays.stream(Helper.readToString(2020, 4).split("\n\n"))
                .map(str -> String.join(" ", str.split("\n")))
                .mapToInt(str -> checkPassportStrict(str) ? 1 : 0)
                .sum();
        System.out.println(sum);
    }

    public static void main(String[] args) {
        Day day = new Day4();
        day.partOne();
        day.partTwo();
    }
}
