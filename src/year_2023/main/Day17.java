package year_2023.main;

import helpers.*;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day17 extends CodeDay {

    @Override
    public void puzzleOne() {
        var input = Helper.readToStringArrayList(2023, 17);
        char[][] matrix = input.stream()
                .map(String::toCharArray)
                .toArray(size -> new char[size][input.size()]);


        int[][] dirs = new int[][]{{0, 1}, {1, 0}, {0, -1}, {-1, 0}};

        PriorityQueue<State> queue = new PriorityQueue<>();
        queue.add(new State(0, 0, 0, -1));
        Set<SmallState> seen = new HashSet<>();
        HashMap<SmallState, Integer> costs = new HashMap<>();
        int minDist = 1;
        int maxDist = 3;

        while (!queue.isEmpty()) {
            State pop = queue.poll();
            SmallState smallState = pop.getSmallState();
            if (pop.x == matrix.length - 1 && pop.y == matrix[0].length - 1) {
                System.out.println(pop.cost);
                return;
            }

            if (seen.contains(smallState)) {
                continue;
            }

            seen.add(smallState);

            for (int direction = 0; direction < 4; direction++) {
                int costincrease = 0;

                if (direction == pop.disallowedDirection || ((direction + 2) % 4) == pop.disallowedDirection) {
                    continue;
                }

                for (int distance = minDist; distance < maxDist + 1; distance++) {
                    int xx = pop.x + dirs[direction][0] * distance;
                    int yy = pop.y + dirs[direction][1] * distance;

                    if (inRange(xx, yy, matrix)) {
                        costincrease += Integer.parseInt(String.valueOf(matrix[xx][yy]));

                        int newCost = pop.cost + costincrease;
                        SmallState ss = new SmallState(xx, yy, direction);

                        int value = Integer.MAX_VALUE;
                        if (costs.containsKey(ss)) {
                            value = costs.get(ss);
                        }

                        if (value <= newCost) {
                            continue;
                        }

                        costs.replace(ss, newCost);
                        queue.add(new State(newCost, xx, yy, direction));
                    }
                }
            }
        }

        System.out.println("emnd");
    }

    public boolean inRange(int x, int y, char[][] matrix) {
        return x >= 0 && x < matrix.length && y >= 0 && y < matrix[0].length;
    }

    @Override
    public void puzzleTwo() {
        var input = Helper.readToStringArrayList(2023, 17);
        char[][] matrix = input.stream()
                .map(String::toCharArray)
                .toArray(size -> new char[size][input.size()]);


        int[][] dirs = new int[][]{{0, 1}, {1, 0}, {0, -1}, {-1, 0}};

        PriorityQueue<State> queue = new PriorityQueue<>();
        queue.add(new State(0, 0, 0, -1));
        Set<SmallState> seen = new HashSet<>();
        HashMap<SmallState, Integer> costs = new HashMap<>();
        int minDist = 4;
        int maxDist = 10;
        while (!queue.isEmpty()) {
            State pop = queue.poll();
            SmallState smallState = pop.getSmallState();
            if (pop.x == matrix.length - 1 && pop.y == matrix[0].length - 1) {
                System.out.println(pop.cost);
                return;
            }

            if (seen.contains(smallState)) {
                continue;
            }

            seen.add(smallState);

            for (int direction = 0; direction < 4; direction++) {
                int costincrease = 0;

                if (direction == pop.disallowedDirection || ((direction + 2) % 4) == pop.disallowedDirection) {
                    continue;
                }

                for (int distance = 1; distance < maxDist + 1; distance++) {
                    int xx = pop.x + dirs[direction][0] * distance;
                    int yy = pop.y + dirs[direction][1] * distance;

                    if (!inRange(xx, yy, matrix)) {
                        continue;
                    }
                    costincrease += Integer.parseInt(String.valueOf(matrix[xx][yy]));

                    if (distance < minDist) {
                        continue;
                    }

                    int newCost = pop.cost + costincrease;
                    SmallState ss = new SmallState(xx, yy, direction);

                    int value = Integer.MAX_VALUE;
                    if (costs.containsKey(ss)) {
                        value = costs.get(ss);
                    }

                    if (value <= newCost) {
                        continue;
                    }

                    costs.replace(ss, newCost);
                    queue.add(new State(newCost, xx, yy, direction));
                }
            }
        }

        System.out.println("emnd");
    }

    class State implements Comparable {
        public final int cost;
        public final int x;
        public final int y;
        public final int disallowedDirection;

        public State(int cost, int x, int y, int disallowedDirection) {
            this.cost = cost;
            this.x = x;
            this.y = y;
            this.disallowedDirection = disallowedDirection;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            State state = (State) o;
            return cost == state.cost && x == state.x && y == state.y && disallowedDirection == state.disallowedDirection;
        }

        @Override
        public int hashCode() {
            return Objects.hash(cost, x, y, disallowedDirection);
        }

        public SmallState getSmallState() {
            return new SmallState(x, y, disallowedDirection);
        }

        @Override
        public int compareTo(Object o) {
            if (!(o instanceof State otherState)) {
                return 0;
            }

            ArrayList<Integer> values = Stream.of(this.cost, this.x, this.y, this.disallowedDirection).collect(Collectors.toCollection(ArrayList::new));
            ArrayList<Integer> otherValues = Stream.of(otherState.cost, otherState.x, otherState.y, otherState.disallowedDirection).collect(Collectors.toCollection(ArrayList::new));

            for (int i = 0; i < values.size(); i++) {
                if (!Objects.equals(values.get(i), otherValues.get(i))) {
                    return values.get(i) - otherValues.get(i);
                }
            }

            return 0;
        }
    }

    class SmallState {
        public final int dd;
        public final int x;
        public final int y;

        public SmallState(int x, int y, int dd) {
            this.dd = dd;
            this.x = x;
            this.y = y;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            SmallState that = (SmallState) o;
            return dd == that.dd && x == that.x && y == that.y;
        }

        @Override
        public int hashCode() {
            return Objects.hash(dd, x, y);
        }
    }
}
