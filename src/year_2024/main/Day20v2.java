package year_2024.main;

import helpers.Helper;
import helpers.MatrixLocation;

import java.util.*;
import java.util.stream.Collectors;

public class Day20v2 {
    private int[][] distanceMap = getDistanceMap();
    public void puzzleOne() {
        ArrayList<MatrixLocation> allValidStartPositions = Helper.allLocations(distanceMap).stream()
                .filter(ml -> ml.getValue(distanceMap) != -1)
                .collect(Collectors.toCollection(ArrayList::new));

        long total = 0;
        for (MatrixLocation cheatStart : allValidStartPositions) {
            for (MatrixLocation cheatEndResult : allValidStartPositions) {
                if (cheatStart.locationEquals(cheatEndResult)) {
                    continue;
                }

                int manhattenDistance = manhattenDistance(cheatStart, cheatEndResult);
                if (manhattenDistance == 2) {
                    int cheatStartValue = cheatStart.getValue(distanceMap);
                    int cheatEndResultValue = cheatEndResult.getValue(distanceMap);
                    int cheatSaved = cheatEndResultValue - cheatStartValue - manhattenDistance;
                    if(cheatSaved >= 100) {
                        total++;
                    }
                }
            }
        }

        System.out.println(total);
    }

    private int[][] getDistanceMap() {
        char[][] matrix = Helper.toMatrix(2024, 20);
        MatrixLocation start = Helper.findFirstCharInMap(matrix, 'S');
        MatrixLocation end = Helper.findFirstCharInMap(matrix, 'E');

        int[][] distanceMap = distanceMap(matrix, start, end);
        return distanceMap;
    }

    public void puzzleTwo() {
        ArrayList<MatrixLocation> allValidStartPositions = Helper.allLocations(distanceMap).stream()
                .filter(ml -> ml.getValue(distanceMap) != -1)
                .collect(Collectors.toCollection(ArrayList::new));

        int total = 0;
        for (MatrixLocation cheatStart : allValidStartPositions) {
            for (MatrixLocation cheatEndResult : allValidStartPositions) {
                if (cheatStart.locationEquals(cheatEndResult)) {
                    continue;
                }

                int manhattenDistance = manhattenDistance(cheatStart, cheatEndResult);
                if (manhattenDistance >= 2 && manhattenDistance <= 20) {
                    int cheatStartValue = cheatStart.getValue(distanceMap);
                    int cheatEndResultValue = cheatEndResult.getValue(distanceMap);
                    int cheatSaved = cheatEndResultValue - cheatStartValue - manhattenDistance;
                    if(cheatSaved >= 100) {
                        total++;
                    }
                }
            }
        }
        System.out.println(total);
    }

    public int[][] distanceMap(char[][] matrix, MatrixLocation start, MatrixLocation end) {
        int[][] distToEndFromEveryLocation = new int[matrix.length][matrix[0].length];

        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                distToEndFromEveryLocation[i][j] = Integer.MAX_VALUE;
            }
        }

        end.setDist(0);
        Stack<HashMap<MatrixLocation, ArrayList<MatrixLocation>>> todo = new Stack<>();

        HashMap<MatrixLocation, ArrayList<MatrixLocation>> x = new HashMap<>();
        x.put(start, new ArrayList<>());
        todo.push(x);
        shortestPath(matrix, distToEndFromEveryLocation, end, todo);

        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                if (distToEndFromEveryLocation[i][j] != Integer.MAX_VALUE) {
                    continue;
                }

                distToEndFromEveryLocation[i][j] = -1;
            }
        }
        return distToEndFromEveryLocation;
    }

    public void shortestPath(char[][] matrix, int[][] distToEndFromEveryLocation, MatrixLocation end, Stack<HashMap<MatrixLocation, ArrayList<MatrixLocation>>> toDo) {
        while (!toDo.isEmpty()) {
            HashMap<MatrixLocation, ArrayList<MatrixLocation>> pop = toDo.pop();
            MatrixLocation start = pop.keySet().stream().findFirst().get();
            ArrayList<MatrixLocation> history = pop.get(start);

            if (history.contains(start)) {
                return;
            }

            history.add(start);

            if (start.locationEquals(end)) {
                int x = 0;
                for (MatrixLocation historyItem : history) {
                    int currValue = historyItem.getValue(distToEndFromEveryLocation);
                    int newValue = x;
                    if (newValue < currValue) {
                        distToEndFromEveryLocation[historyItem.getY()][historyItem.getX()] = newValue;
                    }
                    x++;
                }
                return;
            }

            List<MatrixLocation> list = Helper.surroundingTiles(matrix, start, false).stream()
                    .filter(ml -> !history.contains(ml))
                    .filter(matrixLocation -> matrix[matrixLocation.getY()][matrixLocation.getX()] != '#')
                    .toList();

            for (MatrixLocation newStart : list) {
                ArrayList<MatrixLocation> clone = new ArrayList<>(history);
                HashMap<MatrixLocation, ArrayList<MatrixLocation>> todo = new HashMap<>();
                todo.put(newStart, clone);
                toDo.add(todo);
            }
        }
    }



    public int manhattenDistance(MatrixLocation a, MatrixLocation b) {
        return Math.abs(a.getX() - b.getX()) + Math.abs(a.getY() - b.getY());
    }

    public static void main(String[] args) {
        Day20v2 day = new Day20v2();
        day.distanceMap = day.getDistanceMap();
        day.puzzleOne();
        day.puzzleTwo();
    }
}
