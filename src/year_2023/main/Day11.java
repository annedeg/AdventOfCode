package year_2023.main;

import helpers.*;

import java.util.*;

public class Day11 extends CodeDay {
    @Override
    public void puzzleOne() {
        var input = Helper.readToStringArrayList(2023, 11);

        new Image(input, 1);
    }

    @Override
    public void puzzleTwo() {
        var input = Helper.readToStringArrayList(2023, 11);
        new Image(input, 1000000);
    }

    public class Image {
        char[][] image;

        public Image(ArrayList<String> input, int emptyAmount) {
            image = new char[input.size()][input.get(0).length()];
            int y = 0;
            for (String inputRow : input) {
                int x = 0;
                for (char inputChar : inputRow.toCharArray()) {
                    image[y][x] = inputChar;
                    x+=1;
                }
                y+=1;
            }

            ArrayList<Pair> allPairs = expandSpaceAndGetPairs(image, emptyAmount);
            long sum = allPairs.stream().map(Pair::getDistance).mapToLong(x -> x).sum();
            System.out.println(sum);
        }

        public ArrayList<Pair> expandSpaceAndGetPairs(char[][] image, int emptyAmount) {
            ArrayList<Pair> allPairs = getAllPairs(image);
            ArrayList<Pair> newPairs = new ArrayList<>();
            emptyAmount -= 1;
            for (Pair pair : allPairs) {
                ArrayList<Long> emptyRows = getAllEmptyRows();
                ArrayList<Long> allEmptyColumns = getAllEmptyColumns();

                Location one = pair.one;
                Location two = pair.two;

                long newOneX = one.x + (amountFound(one.x, emptyRows) * emptyAmount);
                long newOneY = one.y + (amountFound(one.y, allEmptyColumns) * emptyAmount);
                long newTwoX = two.x + (amountFound(two.x, emptyRows) * emptyAmount);
                long newTwoY = two.y + (amountFound(two.y, allEmptyColumns) * emptyAmount);

                newPairs.add(new Pair(new Location(newOneX, newOneY), new Location(newTwoX, newTwoY)));
            }

            return newPairs;
        }

        private long amountFound(Long old, ArrayList<Long> list) {
            return list.stream().filter(i -> old >= i).count();
        }

        private ArrayList<Long> getAllEmptyRows() {
            ArrayList<Long> emptyRows = new ArrayList<>();
            for (int x = 0; x < image.length; x++) {
                boolean rowEmpty = true;
                for (int y = 0; y < image[0].length; y++) {
                    if (image[x][y] == '#') {
                        rowEmpty = false;
                    }
                }

                if (rowEmpty) {
                    emptyRows.add((long) x);
                }
            }

            return emptyRows;
        }

        private ArrayList<Long> getAllEmptyColumns() {
            ArrayList<Long> emptyColumns = new ArrayList<>();
            for (int y = 0; y < image[0].length; y++) {
                boolean columnEmpty = true;
                for (int x = 0; x < image.length; x++) {
                    if (image[x][y] == '#') {
                        columnEmpty = false;
                    }
                }

                if (columnEmpty) {
                    emptyColumns.add((long) y);
                }
            }

            return emptyColumns;
        }

        private ArrayList<Pair> getAllPairs(char[][] image) {
            ArrayList<Location> locations = new ArrayList<>();
            for (int x = 0; x < image.length; x++) {
                for (int y = 0; y < image[0].length; y++) {
                    char ch = image[x][y];

                    if (ch == '#') {
                        locations.add(new Location(x, y));
                    }
                }
            }

            ArrayList<Pair> pairs = new ArrayList<>();
            for (Location location : locations) {
                for (Location location2 : locations) {
                    if (location.equals(location2)) {
                        continue;
                    }

                    Pair pair = new Pair(location, location2);
                    if (pairs.contains(pair)) {
                        continue;
                    }

                    pairs.add(pair);
                }
            }

            return pairs;
        }
    }

    class Pair {
        public final Location one;
        public final Location two;
        Pair(Location one, Location two) {
            this.one = one;
            this.two = two;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            Pair pair = (Pair) o;
            return (Objects.equals(one, pair.one) && Objects.equals(two, pair.two)) || (Objects.equals(one, pair.two) && Objects.equals(two, pair.one));
        }

        @Override
        public int hashCode() {
            return Objects.hash(one, two);
        }

        public long getDistance() {
            return Math.abs(one.x - two.x) + Math.abs(one.y - two.y);
        }
    }

    class Location {
        public final long x;
        public final long y;

        Location(long x, long y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            Location location = (Location) o;
            return x == location.x && y == location.y;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y);
        }
    }
}
