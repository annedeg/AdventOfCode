package year_2025;

import helpers.MatrixLocation;

import java.util.*;
import java.util.stream.Collectors;

public class SmallMatrix {
    char[][] matrix;
    Map<Edge, Long> edgeLength = new HashMap<>();
    Map<MatrixLocation, MatrixLocation> newToOld = new HashMap<>();

    public SmallMatrix(List<String> input) {
        ArrayList<MatrixLocation> matrixLocations = input.stream()
                .map(str -> {
                    String[] split = str.split(",");
                    return new MatrixLocation(Integer.parseInt(split[0]), Integer.parseInt(split[1]));
                })
                .collect(Collectors.toCollection(ArrayList::new));

        matrix = shrinkMatrix(matrixLocations);
        calcEdgeLength(matrixLocations);
    }

    private char[][] shrinkMatrix(ArrayList<MatrixLocation> matrixLocations) {
        List<Integer> avoidX = matrixLocations.stream()
                .map(MatrixLocation::getX)
                .distinct()
                .collect(Collectors.toCollection(ArrayList::new));

        int maxX = avoidX.stream().mapToInt(Integer::intValue).max().orElse(0);

        List<Integer> avoidY = matrixLocations.stream()
                .map(MatrixLocation::getY)
                .distinct()
                .collect(Collectors.toCollection(ArrayList::new));

        int maxY = avoidY.stream().mapToInt(Integer::intValue).max().orElse(0);

        Collections.sort(avoidX);
        Collections.sort(avoidY);

        char[][] m = new char[avoidY.size()][avoidX.size()];
        int dy = 0;
        for (int y = 0; y <= maxY; y++) {
            int dx = 0;

            for (int x = 0; x <= maxX; x++) {
                MatrixLocation possiblePoint = new MatrixLocation(x, y);
                boolean isPoint = matrixLocations.contains(possiblePoint);

                if (isPoint) {
                    newToOld.put(new MatrixLocation(dx, dy), possiblePoint);
                    m[dy][dx] = '#';
                }

                if (avoidX.contains(x)) {
                    dx++;
                }
            }

            if (avoidY.contains(y)) {
                dy++;
            }
        }

        return m;
    }

    private void calcEdgeLength(List<MatrixLocation> matrixLocations) {
        for (MatrixLocation m1 : matrixLocations) {
            for (MatrixLocation m2 : matrixLocations) {
                if (m1.equals(m2)) {
                     continue;
                }
                Edge edge = new Edge(m1, m2);

                MatrixLocation oldm1 = newToOld.get(m1);
                MatrixLocation oldm2 = newToOld.get(m2);

                long xChange = Math.abs(oldm1.getX() - oldm2.getX());
                long yChange = Math.abs(oldm1.getY() - oldm2.getY());

                long change = xChange != 0 ? xChange : yChange;
                edgeLength.put(edge, change);
            }
        }
    }

    public char[][] getMatrix() {
        return matrix;
    }

    public Long getEdgeLength(Edge edge) {
        return edgeLength.get(edge);
    }

    public long getLengthBetweenMatrixLocations(MatrixLocation m1, MatrixLocation m2) {
        Edge edge = new Edge(m1, m2);
        if (edgeLength.containsKey(edge)) {
            return edgeLength.get(edge);
        }

        return -1;
    }
}
