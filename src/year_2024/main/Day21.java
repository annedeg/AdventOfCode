package year_2024.main;

import helpers.Direction;
import helpers.Helper;
import helpers.MatrixLocation;
import helpers.Node;

import java.util.*;
import java.util.stream.Collectors;

public class Day21 {
    private HashMap<String, Long> lengthCache = new HashMap<>();
    enum KeyPadPatterns {
        NUMERIC, DIRECTIONAL;

        char[][] getMap() {
            switch (this) {
                case NUMERIC -> {
                    char[][] map = new char[4][3];
                    map[0][0] = '7';
                    map[0][1] = '8';
                    map[0][2] = '9';
                    map[1][0] = '4';
                    map[1][1] = '5';
                    map[1][2] = '6';
                    map[2][0] = '1';
                    map[2][1] = '2';
                    map[2][2] = '3';
                    map[3][1] = '0';
                    map[3][2] = 'A';
                    return map;
                }
                case DIRECTIONAL -> {
                    char[][] map = new char[2][3];
                    map[0][1] = '^';
                    map[0][2] = 'A';
                    map[1][0] = '<';
                    map[1][1] = 'v';
                    map[1][2] = '>';
                    return map;
                }
            }

            return null;
        }
    }

    class KeyPad {
        MatrixLocation currentLocation;
        char[][] keyPadMap;
        private LinkedHashMap<String, ArrayList<String>> cache = new LinkedHashMap<>();
        private HashMap<Character, Node> positionCache = new HashMap<>();

        KeyPad(char[][] keyPadMap) {
            this.keyPadMap = keyPadMap;
            MatrixLocation a = Helper.findFirstCharInMap(keyPadMap, 'A');

            if (a == null) {
                throw new RuntimeException("No A found in keypad");
            }

            currentLocation = a;
        }

        public Node getCurrentLocation() {
            return currentLocation.toNode(0, 0);
        }

        private ArrayList<String> getPossiblePathsToKey(String line) {
            ArrayList<String> strings = getPaths(getCurrentLocation(), line);

            List<String> shortestMoves = strings.stream().distinct().sorted(Comparator.comparing(String::length)).toList();
            int shortest = shortestMoves.get(0).length();
            return shortestMoves.stream().filter(str -> str.length() == shortest).collect(Collectors.toCollection(ArrayList::new));
        }

        public Optional<ArrayList<String>> getCache(String key) {
            if (cache.containsKey(key)) {
                return Optional.ofNullable(cache.get(key));
            }

            return Optional.empty();
        }

        public void putCache(String key, ArrayList<String> value) {
            cache.put(key, value);
        }

        private ArrayList<String> getPaths(Node start, String lineToCheck) {
            if (positionCache.containsKey(lineToCheck.charAt(0))) {
                start.copyFrom(positionCache.get(lineToCheck.charAt(0)));
            }

            String cacheKey = start.getX() + "," + start.getY() + "," + lineToCheck;

            Optional<ArrayList<String>> cacheResult = getCache(cacheKey);
            if (cacheResult.isPresent()) {
                return cacheResult.get();
            }

            if (lineToCheck.length() == 1) {
                ArrayList<String> paths = new ArrayList<>();
                if (!positionCache.containsKey(lineToCheck.charAt(0))) {
                    positionCache.put(lineToCheck.charAt(0), start.deepClone());
                }
                Node paths1 = getPaths(0, lineToCheck, start, "", paths);
                start.copyFrom(paths1);
                OptionalInt min = paths.stream().mapToInt(String::length).min();
                if (min.isPresent()) {
                    paths = paths.stream().filter(str -> str.length() == min.getAsInt()).collect(Collectors.toCollection(ArrayList::new));
                }
                putCache(cacheKey, paths);
                return paths;
            }

            ArrayList<String> result = new ArrayList<>();

            for (int split = 1; split < lineToCheck.length(); split++) {
                String leftPart = lineToCheck.substring(0, split);
                String rightPart = lineToCheck.substring(split);

                ArrayList<String> left = getPaths(start, leftPart);
                ArrayList<String> right = getPaths(start, rightPart);
                result.addAll(generatePermutations(left, right));
            }

            putCache(cacheKey, result);
            return result;
        }

        public ArrayList<String> generatePermutations(ArrayList<String> left, ArrayList<String> right) {
            ArrayList<String> result = new ArrayList<>();
            left = left.stream().distinct().collect(Collectors.toCollection(ArrayList::new));
            right = right.stream().distinct().collect(Collectors.toCollection(ArrayList::new));
            for (String leftElement : left) {
                for (String rightElement : right) {
                    String combinedPath = leftElement + rightElement;
                    if (result.isEmpty() || combinedPath.length() < result.get(0).length()) {
                        result.clear();
                        result.add(combinedPath);
                    } else if (combinedPath.length() == result.get(0).length()) {
                        result.add(combinedPath);
                    }
                }
            }
            return result;
        }

        private Node getPaths(int i, String line, Node start, String v, ArrayList<String> all) {
            if (i == line.length()) {
                all.add(v);
                return start;
            }

            char key = line.charAt(i);
            Node goal = Helper.findFirstCharInMap(keyPadMap, key).toNode(0, 0);
            ArrayList<Node> paths = findPaths(start, goal, keyPadMap);

            Node returnGoal = null;

            for (Node newStart : paths) {
                String string = v + newStart.toDirectionString();
                if (newStart.equals(start)) {
                    string = v + "A";
                }
                Node newStartClone = new Node(newStart.getX(), newStart.getY(), 0, 0);
                returnGoal = getPaths(i + 1, line, newStartClone, string, all);
            }

            return returnGoal;
        }

        private static String getDirectionString(Direction direction, String currentPathFound) {
            String newPath = currentPathFound;
            if (direction != null) {
                newPath = switch (direction) {
                    case UP:
                        yield currentPathFound + '^';
                    case DOWN:
                        yield currentPathFound + 'v';
                    case LEFT:
                        yield currentPathFound + '<';
                    case RIGHT:
                        yield currentPathFound + '>';
                };
            }

            return newPath;
        }

    }

    public void puzzleOne() {
        long solve = solve(2, Helper.readToStringArrayList(2024, 21));
        System.out.println(solve);
    }

    private long solve(int depth, ArrayList<String> input) {
        long sum = 0;
        for (String line : input) {
            long best = Long.MAX_VALUE;
            List<String> robot1Moves = new KeyPad(KeyPadPatterns.NUMERIC.getMap()).getPossiblePathsToKey(line);
            for (String robot1Move : robot1Moves) {
                long count = countAmountOfPushes(robot1Move, depth);
                best = Math.min(best, count);
            }
            sum += best * code(line);
        }
        return sum;
    }

    private long countAmountOfPushes(String move, int depth) {
        if (depth == 0) {
            return move.length();
        }
        String cacheKey = move + ":" + depth;
        if (lengthCache.containsKey(cacheKey)) {
            return lengthCache.get(cacheKey);
        }

        Node start = new Node(2, 0,0,0);
        long sum = 0;

        for (char c : move.toCharArray()) {
            long min = Long.MAX_VALUE;
            MatrixLocation firstCharInMap = Helper.findFirstCharInMap(KeyPadPatterns.DIRECTIONAL.getMap(), c);
            Node goal = firstCharInMap.toNode(0, 0);
            for (Node road : findPaths(start, goal, KeyPadPatterns.DIRECTIONAL.getMap())) {
                String roadString = road.toDirectionString();
                min = Long.min(min, countAmountOfPushes(roadString, depth-1));
            }
            sum+=min;
            start.copyFrom(goal);
        }
        System.out.println(move +" " + depth + " " +sum);
        lengthCache.put(cacheKey, sum);
        return sum;
    }

    private long code(String input) {
        return Long.parseLong(input.substring(0, input.length()-1));
    }

    private ArrayList<Node> findPaths(Node start, Node goal, char[][] map) {
        ArrayList<Node> paths = new ArrayList<>();
        ArrayList<Node> openList = new ArrayList<>();
        ArrayList<Node> closedList = new ArrayList<>();
        openList.add(start);

        if (start.equals(goal)) {
            paths.add(start);
        }

        while (!openList.isEmpty()) {
            Node curr = openList.remove(getLowestNode(openList));
            ArrayList<Node> successors = genSuccessors(curr, map);

            for (Node successor : successors) {
                if (successor.equals(goal)) {
                    paths.add(successor);
                    continue;
                }


                int index = openList.indexOf(successor);
                if (index != -1) {
                    if (openList.get(index).getF() < successor.getF()) continue;
                }

                index = closedList.indexOf(successor);
                if (index == -1) {
                    openList.add(successor);
                }
            }
            closedList.add(curr);
        }

        ArrayList<Node> newPaths = new ArrayList<>();
        for (Node p : paths) {
            if (!p.hasDuplicateParents()) {
                newPaths.add(p);
            }
        }

        return newPaths;
    }

    private int getLowestNode(ArrayList<Node> openList) {
        double low = Double.MAX_VALUE;
        int i = -1;
        for (int j = 0; j < openList.size(); j++) {
            Node node = openList.get(j);
            if (node.getG() < low) {
                low = node.getG();
                i = j;
            }
        }
        return i;
    }

    private ArrayList<Node> genSuccessors(Node node, char[][] keyPadMap) {
        return Helper.surroundingTiles(keyPadMap, new MatrixLocation(node.getX(), node.getY()), false).stream()
                .filter(ml -> ml.getValue(keyPadMap) != 0)
                .map(ml -> new Node(ml.getX(), ml.getY(), node.getG() + 1, getH(new MatrixLocation(node.getX(), node.getY()), ml), node))
                .collect(Collectors.toCollection(ArrayList::new));
    }

    private int getH(MatrixLocation a1, MatrixLocation a2) {
        return Math.abs(a1.getX() - a2.getX()) + Math.abs(a1.getY() - a2.getY());
    }

    public void puzzleTwo() {
        long solve = solve(25, Helper.readToStringArrayList(2024, 21));
        System.out.println(solve);
    }

    public static void main(String[] args) {
        Day21 day = new Day21();
        day.puzzleOne();
        day.puzzleTwo();
    }
}
