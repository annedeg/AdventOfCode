package year_2023;

import helpers.Helper;

import java.util.*;
import java.util.stream.Collectors;

public class Day22 extends CodeDay {

    @Override
    public void puzzleOne() {
        var input = Helper.readToStringArrayList("src/input/day22");

        ArrayList<Boolean> checks = new ArrayList<>();
        checks.add(new Brick("2,2,2~2,2,2").getVolume() == 1);
        checks.add(new Brick("0,0,10~1,0,10").getVolume() == 2);
        checks.add(new Brick("0,0,10~0,1,10").getVolume() == 2);
        checks.add(new Brick("0,0,1~0,0,10").getVolume() == 10);

        if (checks.stream().anyMatch(x -> !x)) {
            System.out.println("nope");
        }

        Point.lowestAll = new Point(Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE);
        Point.highestAll = new Point(Integer.MIN_VALUE, Integer.MIN_VALUE, Integer.MIN_VALUE);
        ArrayList<Brick> bricks = input.stream()
                .map(Brick::new)
                .collect(Collectors.toCollection(ArrayList::new));


        int[][][] matrix = new int[Point.highestAll.x + 1][Point.highestAll.y + 1][Point.highestAll.z + 1];
        for (Brick brick : bricks) {
            ArrayList<Point> allPoints = brick.getAllPoints();

            for (Point point : allPoints) {
                matrix[point.x][point.y][point.z] = 1;
            }
        }

        int[][] side = new int[matrix[0].length][matrix[0][0].length];

        for (int x = 0; x < matrix.length; x++) {
            for (int y = 0; y < matrix[0].length; y++) {
                for (int z = 0; z < matrix[0][0].length; z++) {
                    if (matrix[x][y][z] == 1) {
                        side[y][z] = 1;
                    }
                }
            }
        }

        bricks = bricks.stream().sorted(Comparator.comparing(brick -> brick.start.z)).collect(Collectors.toCollection(ArrayList::new));
        for (Brick brick : bricks) {
            while (tryMove(matrix, brick)) {
                doMove(matrix, brick);
            }
        }

        side = new int[matrix[0].length][matrix[0][0].length];

        for (int x = 0; x < matrix.length; x++) {
            for (int y = 0; y < matrix[0].length; y++) {
                for (int z = 0; z < matrix[0][0].length; z++) {
                    if (matrix[x][y][z] == 1) {
                        side[y][z] = 1;
                    }
                }
            }
        }

        bricks = bricks.stream().sorted(Comparator.comparing(brick -> brick.start.z)).collect(Collectors.toCollection(ArrayList::new));

        int total = 0;
        int c= 0;
        for (Brick brick : bricks) {
            if (tryToRemoveAndSeeIfChangesHappen(matrix, brick, bricks)) {
                total +=1;
            }

            System.out.println(c);
            c+=1;
        }

        System.out.println(total);
    }

    public boolean tryToRemoveAndSeeIfChangesHappen(int[][][] matrix, Brick remove, ArrayList<Brick> bricks) {
        int[][][] cloned = cloneMatrix(matrix);
        removeFromMatrix(cloned, remove);

        for (Brick brick : bricks) {
            if (brick.equals(remove)) {
                continue;
            }

            Brick clonedBrick = brick.clone();

            if (tryMove(cloned, clonedBrick)) {
                return false;
            }
        }

        return true;
    }

    public int amountWillFall(int[][][] matrix, Brick remove, ArrayList<Brick> bricks) {
        int am = 0;
        int[][][] cloned = cloneMatrix(matrix);
        removeFromMatrix(cloned, remove);


        ArrayList<Brick> willFall = new ArrayList<>();

        for (Brick brick : bricks) {
            if (brick.equals(remove)) {
                continue;
            }

            Brick clonedBrick = brick.clone();
            while (tryMove(cloned, clonedBrick)) {
                if (!willFall.contains(clonedBrick)) {
                    willFall.add(clonedBrick);
                }

                doMove(cloned, clonedBrick);
                am += 1;
            }
        }

        return willFall.size();
    }


    public int[][][] doMove(int[][][] matrix, Brick brick) {
        removeFromMatrix(matrix, brick);
        brick.fall();
        try {
            addToMatrix(matrix, brick);
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("hoe dan");
        }
        return matrix;
    }

    public boolean tryMove(int[][][] matrix, Brick brick) {
        int[][][] matrixClone = cloneMatrix(matrix);
        Brick brickClone = brick.clone();
        removeFromMatrix(matrixClone, brickClone);

        brickClone.fall();

        try {
            addToMatrix(matrixClone, brickClone);
            return valid(matrixClone);
        } catch (ArrayIndexOutOfBoundsException e) {
            return false;
        }
    }

    public int[][][] cloneMatrix(int[][][] matrix) {
        int[][][] newMatrix = new int[matrix.length][matrix[0].length][matrix[0][0].length];

        for (int x = 0; x < matrix.length; x++) {
            for (int y = 0; y < matrix[0].length; y++) {
                for (int z = 0; z < matrix[0][0].length; z++) {
                    newMatrix[x][y][z] = matrix[x][y][z];
                }
            }
        }

        return newMatrix;
    }

    public boolean valid(int[][][] matrix) {
        return Arrays.stream(matrix).flatMap(Arrays::stream).flatMapToInt(Arrays::stream).noneMatch(value -> value > 1);
    }

    public void removeFromMatrix(int[][][] matrix, Brick brick) {
        for (Point point : brick.getAllPoints()) {
            matrix[point.x][point.y][point.z] = 0;
        }
    }

    public void addToMatrix(int[][][] matrix, Brick brick) {
        for (Point point : brick.getAllPoints()) {
            matrix[point.x][point.y][point.z] += 1;
        }
    }

    @Override
    public void puzzleTwo() {
        var input = Helper.readToStringArrayList("src/input/day22");

        ArrayList<Boolean> checks = new ArrayList<>();
        checks.add(new Brick("2,2,2~2,2,2").getVolume() == 1);
        checks.add(new Brick("0,0,10~1,0,10").getVolume() == 2);
        checks.add(new Brick("0,0,10~0,1,10").getVolume() == 2);
        checks.add(new Brick("0,0,1~0,0,10").getVolume() == 10);

        if (checks.stream().anyMatch(x -> !x)) {
            System.out.println("nope");
        }

        Point.lowestAll = new Point(Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE);
        Point.highestAll = new Point(Integer.MIN_VALUE, Integer.MIN_VALUE, Integer.MIN_VALUE);
        ArrayList<Brick> bricks = input.stream()
                .map(Brick::new)
                .collect(Collectors.toCollection(ArrayList::new));


        int[][][] matrix = new int[Point.highestAll.x + 1][Point.highestAll.y + 1][Point.highestAll.z + 1];
        for (Brick brick : bricks) {
            ArrayList<Point> allPoints = brick.getAllPoints();

            for (Point point : allPoints) {
                matrix[point.x][point.y][point.z] = 1;
            }
        }

        int[][] side = new int[matrix[0].length][matrix[0][0].length];

        for (int x = 0; x < matrix.length; x++) {
            for (int y = 0; y < matrix[0].length; y++) {
                for (int z = 0; z < matrix[0][0].length; z++) {
                    if (matrix[x][y][z] == 1) {
                        side[y][z] = 1;
                    }
                }
            }
        }

        bricks = bricks.stream().sorted(Comparator.comparing(brick -> brick.start.z)).collect(Collectors.toCollection(ArrayList::new));
        for (Brick brick : bricks) {
            while (tryMove(matrix, brick)) {
                doMove(matrix, brick);
            }
        }

        side = new int[matrix[0].length][matrix[0][0].length];

        for (int x = 0; x < matrix.length; x++) {
            for (int y = 0; y < matrix[0].length; y++) {
                for (int z = 0; z < matrix[0][0].length; z++) {
                    if (matrix[x][y][z] == 1) {
                        side[y][z] = 1;
                    }
                }
            }
        }

        bricks = bricks.stream().sorted(Comparator.comparing(brick -> brick.start.z)).collect(Collectors.toCollection(ArrayList::new));

        int total = 0;
        int c= 0;
        for (Brick brick : bricks) {
            total += amountWillFall(matrix, brick, bricks);
            System.out.println(c +  "/" + bricks.size());
            c+=1;
        }

        System.out.println(total);
    }

    class Brick implements Cloneable {
        Point start;
        Point end;

        ArrayList<Point> allPoints = null;

        Brick(String line) {
            String[] split = line.split("~");
            ArrayList<Integer> collect = Arrays.stream(split[0].split(","))
                    .mapToInt(Integer::parseInt)
                    .boxed()
                    .collect(Collectors.toCollection(ArrayList::new));

            start = new Point(collect.get(0), collect.get(1), collect.get(2));

            collect = Arrays.stream(split[1].split(","))
                    .mapToInt(Integer::parseInt)
                    .boxed()
                    .collect(Collectors.toCollection(ArrayList::new));

            end = new Point(collect.get(0), collect.get(1), collect.get(2));
            getAllPoints();
        }

        public ArrayList<Point> getAllPoints() {
            if (allPoints != null) {
                return allPoints;
            }

            allPoints = new ArrayList<>();

            for (int x = start.x; x <= end.x; x++) {
                for (int y = start.y; y <= end.y; y++) {
                    for (int z = start.z; z <= end.z; z++) {
                        allPoints.add(new Point(x, y, z));
                    }
                }
            }

            return allPoints;
        }

        public boolean overlap(Brick brick) {
            for (Point point : brick.getAllPoints()) {
                for (Point point2 : this.getAllPoints()) {
                    if (point.x == point2.x && point.y == point2.y) {
                        return true;
                    }
                }
            }
            return false;
        }

        public int getVolume() {
            return getAllPoints().size();
        }

        public void fall() {
            start.z--;
            end.z--;
            allPoints = null;
            getAllPoints();
        }

        @Override
        public Brick clone() {
            try {
                Brick clone = (Brick) super.clone();
                clone.allPoints = new ArrayList<>(allPoints);
                clone.start = new Point(this.start.x, this.start.y, this.start.z);
                clone.end = new Point(this.end.x, this.end.y, this.end.z);
                return clone;
            } catch (CloneNotSupportedException e) {
                throw new AssertionError();
            }
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Brick brick = (Brick) o;
            return Objects.equals(start, brick.start) && Objects.equals(end, brick.end) && Objects.equals(allPoints, brick.allPoints);
        }

        @Override
        public int hashCode() {
            return Objects.hash(start, end, allPoints);
        }
    }

    class Point {
        static Point lowestAll = null;
        static Point highestAll = null;
        int x;
        int y;
        int z;

        Point(int x, int y, int z) {
            this.x = x;
            this.y = y;
            this.z = z;

            if (lowestAll != null && highestAll != null) {
                checkHiLo();
            }
        }


        private void checkHiLo() {
            if (this.x > highestAll.x) {
                highestAll.x = x;
            }

            if (this.y > highestAll.y) {
                highestAll.y = y;
            }

            if (this.z > highestAll.z) {
                highestAll.z = z;
            }

            if (this.x < lowestAll.x) {
                lowestAll.x = x;
            }

            if (this.y < lowestAll.y) {
                lowestAll.y = y;
            }

            if (this.z < lowestAll.z) {
                lowestAll.z = z;
            }
        }
    }
}
