package year_2023.main;

import helpers.*;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.stream.Collectors;

public class Day24 extends CodeDay {
    @Override
    public void puzzleOne() {
        ArrayList<String> strings = Helper.readToStringArrayList(2023, 24);

        BigDecimal min = BigDecimal.valueOf(200000000000000L);
        BigDecimal max = BigDecimal.valueOf(400000000000000L);

        ArrayList<Line> lines = new ArrayList<>();
        for (String line : strings) {
            String[] split = line.split("@");
            ArrayList<BigDecimal> positions = Arrays.stream(split[0].replaceAll(" ", "").split(",")).mapToDouble(Double::parseDouble).boxed().map(BigDecimal::new).collect(Collectors.toCollection(ArrayList::new));
            ArrayList<BigDecimal> velocities = Arrays.stream(split[1].replaceAll(" ", "").split(",")).mapToDouble(Double::parseDouble).boxed().map(BigDecimal::new).collect(Collectors.toCollection(ArrayList::new));
            lines.add(new Line(positions.get(0), positions.get(1), velocities.get(0), velocities.get(1)));
        }

        ArrayList<Integer> history = new ArrayList<>();
        int total = 0;
        for (Line line : lines) {
            for (Line line2 : lines) {
                int i = line.hashCode() + line2.hashCode();
                if (history.contains(i)) {
                    continue;
                }

                history.add(i);

                if (line.equals(line2)) {
                    continue;
                }



//                System.out.println("chcking 1 " + line.xs + " " + line.ys);
//                System.out.println("chcking 2 " + line2.xs + " " + line2.ys);
                Optional<AccuratePoint> accuratePoint = calculateIntersectionPoint(line, line2);
                if (accuratePoint.isEmpty()) {
                    continue;
                }

                AccuratePoint ac = accuratePoint.get();
//                System.out.println(ac.x + " " + ac.y);
                if (ac.x.compareTo(min) < 0 || ac.x.compareTo(max) > 0 || ac.y.compareTo(min) < 0 || ac.y.compareTo(max) > 0) {
//                    System.out.println();
                    continue;
                }

                if (line.ys.compareTo(ac.y) > 0 && line.yv.compareTo(BigDecimal.valueOf(0)) > 0 || line.ys.compareTo(ac.y) < 0 && line.yv.compareTo(BigDecimal.valueOf(0)) < 0) {
                    continue;
                }

                if (line2.ys.compareTo(ac.y) > 0 && line2.yv.compareTo(BigDecimal.valueOf(0)) > 0 || line2.ys.compareTo(ac.y) < 0 && line2.yv.compareTo(BigDecimal.valueOf(0)) < 0) {
                    continue;
                }

//                System.out.println("yes");
                total += 1;
//                System.out.println();
            }
        }

        System.out.println(total);
    }


    public Optional<AccuratePoint> calculateIntersectionPoint(Line line1, Line line2) {
        BigDecimal m1 = line1.getM();
        BigDecimal b1 = line1.getB();
        BigDecimal m2 = line2.getM();
        BigDecimal b2 = line2.getB();

        if (Objects.equals(m1, m2)) {
            return Optional.empty();
        }

        BigDecimal x = (b2.subtract(b1)).divide(m1.subtract(m2), 20, RoundingMode.HALF_UP);
        BigDecimal y = (m1.multiply(x)).add(b1);

        return Optional.of(new AccuratePoint(x,y));
    }

    record AccuratePoint (BigDecimal x,BigDecimal y) {

    }

    class Line {
        private final BigDecimal xs;
        private final BigDecimal ys;
        private final BigDecimal xv;
        private final BigDecimal yv;
        private final BigDecimal xe;
        private final BigDecimal ye;

        public Line(BigDecimal xs, BigDecimal ys, BigDecimal xv, BigDecimal yv) {
            this.xs = xs;
            this.ys = ys;
            this.xv = xv;
            this.yv = yv;

            this.xe = xs.add(xv);
            this.ye = ys.add(yv);
        }

        BigDecimal getM() {
            return (ye.subtract(ys)).divide((xe.subtract(xs)), 20, RoundingMode.HALF_UP);
        }

        BigDecimal getB() {
            return (getM().multiply(xs).multiply(new BigDecimal(-1))).add(ys);
        }
    }

    class Line3d {
        private final BigDecimal xs;
        private final BigDecimal ys;
        private final BigDecimal zs;
        private final BigDecimal xv;
        private final BigDecimal yv;
        private final BigDecimal zv;
        private final BigDecimal xe;
        private final BigDecimal ye;
        private final BigDecimal ze;

        Line3d(BigDecimal xs, BigDecimal ys, BigDecimal zs, BigDecimal xv, BigDecimal yv, BigDecimal zv) {
            this.xs = xs;
            this.ys = ys;
            this.zs = zs;
            this.xv = xv;
            this.yv = yv;
            this.zv = zv;
            xe = new BigDecimal("0");
            ye = new BigDecimal("0");
            ze = new BigDecimal("0");
        }

        BigDecimal getM() {
            return (ye.subtract(ys)).divide((xe.subtract(xs)), 20, RoundingMode.HALF_UP);
        }

        BigDecimal getB() {
            return (getM().multiply(xs).multiply(new BigDecimal(-1))).add(ys);
        }
    }
    @Override
    public void puzzleTwo() {
        ArrayList<String> strings = Helper.readToStringArrayList(2023, 24);
        BigDecimal min = BigDecimal.valueOf(200000000000000L);
        BigDecimal max = BigDecimal.valueOf(400000000000000L);

        ArrayList<Line3d> lines = new ArrayList<>();
        for (String line : strings) {
            String[] split = line.split("@");
            ArrayList<BigDecimal> positions = Arrays.stream(split[0].replaceAll(" ", "").split(",")).mapToDouble(Double::parseDouble).boxed().map(BigDecimal::new).collect(Collectors.toCollection(ArrayList::new));
            ArrayList<BigDecimal> velocities = Arrays.stream(split[1].replaceAll(" ", "").split(",")).mapToDouble(Double::parseDouble).boxed().map(BigDecimal::new).collect(Collectors.toCollection(ArrayList::new));
            lines.add(new Line3d(positions.get(0), positions.get(1), positions.get(2), velocities.get(0), velocities.get(1), positions.get(2)));
        }
    }
}