import helpers.Helper;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day23 extends CodeDay {
    @Override
    public void puzzleOne() {
        ArrayList<String> values = Helper.readToStringArrayList("C:\\Users\\adgra\\IdeaProjects\\AdventOfCode2022\\src\\input\\day23");
        char[][] map = new char[values.size()][values.get(0).length()];

        Location start = null;
        SmallLocation end = null;

        int x = 0;
        for (String value : values) {
            map[x] = value.toCharArray();

            if (x == 0 || x == values.size() - 1) {
                char toPut = 'E';
                if (x == 0) {
                    toPut = 'S';
                }

                int y = 0;
                for (char c : map[x]) {
                    if (c == '.') {
                        map[x][y] = toPut;

                        if (toPut == 'S') {
                            start = new Location(x, y, 0, new ArrayList<>());
                        } else {
                            end = new SmallLocation(x, y);
                        }
                    }
                    y++;
                }
            }
            x += 1;
        }

        Location.map = map;
        Location.maxX = map.length;
        Location.maxY = map[0].length;
        Location.end = end;

        PriorityQueue<Location> queue = new PriorityQueue<>();
        queue.add(start);

        int highest = 0;
        while (!queue.isEmpty()) {
            Location poll = queue.poll();

            if (poll.getChar() == 'E') {
                if (poll.length > highest) {
                    System.out.println(poll.length);
                    highest = poll.length;
                }
            }

            ArrayList<Location> allNextPositions = poll.getAllNextPositions();

            queue.addAll(allNextPositions);
        }

        System.out.println(highest);
    }

    class Location implements Comparable, Cloneable {
        private static SmallLocation end = null;
        private static char[][] map = null;
        private static int maxX;
        private static int maxY;
        private final int x;
        private final int y;
        private final int length;
        private final int distanceFromEnd;
        private static AtomicInteger highest;
        Direction foredDirection;

        ArrayList<SmallLocation> prevs;

        Location(int x, int y, int length, ArrayList<SmallLocation> prevs) {
            this.x = x;
            this.y = y;
            this.length = length;

            if (map != null && valid() && map[x][y] != '.') {
                foredDirection = switch (map[x][y]) {
                    case '>' -> Direction.RIGHT;
                    case '<' -> Direction.LEFT;
                    case '^' -> Direction.UP;
                    case 'v' -> Direction.DOWN;
                    default -> null;
                };
            }

            SmallLocation smallLocation = new SmallLocation(this);
            prevs.add(smallLocation);
            this.prevs = prevs;

            if (end != null) {
                distanceFromEnd = (end.x - x) + (end.y - y);
            } else {
                distanceFromEnd = 0;
            }
        }

        public char getChar() {
            return map[x][y];
        }

        @Override
        public int compareTo(Object o) {
            if (!(o instanceof Location otherState)) {
                return 0;
            }

            ArrayList<Integer> values = Stream.of(this.length, distanceFromEnd, this.x, this.y).collect(Collectors.toCollection(ArrayList::new));
            ArrayList<Integer> otherValues = Stream.of(otherState.length, distanceFromEnd, otherState.x, otherState.y).collect(Collectors.toCollection(ArrayList::new));


            for (int i = 0; i < values.size(); i++) {
                if (!Objects.equals(values.get(i), otherValues.get(i))) {
                    return values.get(i) - otherValues.get(i);
                }
            }

            return 0;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Location location = (Location) o;
            return x == location.x && y == location.y;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y, length, foredDirection, prevs);
        }

        public ArrayList<Location> getAllNextPositions() {
            ArrayList<Location> positions = new ArrayList<>();

            if (foredDirection == null) {
                positions.add(new Location(x + 1, y, length + 1, new ArrayList<>(prevs)));
                positions.add(new Location(x - 1, y, length + 1, new ArrayList<>(prevs)));
                positions.add(new Location(x, y + 1, length + 1, new ArrayList<>(prevs)));
                positions.add(new Location(x, y - 1, length + 1, new ArrayList<>(prevs)));
            } else {
                positions.add(new Location(x + foredDirection.getX(), y + foredDirection.getY(), length + 1, prevs));
            }

            ArrayList<Location> collect = positions.stream().filter(Location::valid).collect(Collectors.toCollection(ArrayList::new));
            return collect;
        }

        private boolean valid() {
            if (!(x >= 0 && y >= 0 && x < maxX && y < maxY) || Location.map[x][y] == '#') {
                return false;
            }
            if (prevs != null) {
                ArrayList<SmallLocation> collect = prevs.stream().distinct().collect(Collectors.toCollection(ArrayList::new));

                if (collect.size() != prevs.size()) {
                    return false;
                }

                if (prevs.stream().anyMatch(smallLocation -> !smallLocation.valid())) {
                    return false;
                }

            }

            return true;
        }

        @Override
        public Location clone() {
            try {
                Location clone = (Location) super.clone();
                clone.prevs = new ArrayList<>(this.prevs);
                return clone;
            } catch (CloneNotSupportedException e) {
                throw new AssertionError();
            }
        }
    }

    class SmallLocation {
        private final int x;
        private final int y;

        SmallLocation(int x, int y) {
            this.x = x;
            this.y = y;
        }

        SmallLocation(Location location) {
            this(location.x, location.y);
        }

        private boolean valid() {
            return (x >= 0 && y >= 0 && x < Location.maxX && y < Location.maxY) && (Location.map[x][y] != '#');
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            SmallLocation that = (SmallLocation) o;
            return x == that.x && y == that.y;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y);
        }
    }

    enum Direction {
        UP(-1, 0), RIGHT(0, 1), DOWN(1, 0), LEFT(0, -1);

        int x;
        int y;

        Direction(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }
    }

    class LocationPartTwo {
        public static char[][] matrix;

        public LocationPartTwo(int x, int y) {
            this.x = x;
            this.y = y;
        }

        int x;
        int y;

        public int getAmountOfNextPositions() {
            ArrayList<LocationPartTwo> positions = new ArrayList<>();

            positions.add(new LocationPartTwo(x + 1, y));
            positions.add(new LocationPartTwo(x - 1, y));
            positions.add(new LocationPartTwo(x, y + 1));
            positions.add(new LocationPartTwo(x, y - 1));

            ArrayList<LocationPartTwo> collect = positions.stream().filter(LocationPartTwo::valid).collect(Collectors.toCollection(ArrayList::new));
            return collect.size();
        }

        private boolean valid() {
            return (x >= 0 && x < matrix.length && y >= 0 && y < matrix[0].length) && (matrix[x][y] == '.' || matrix[x][y] == 'S' || matrix[x][y] == 'E');
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            LocationPartTwo that = (LocationPartTwo) o;
            return x == that.x && y == that.y;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y);
        }
    }

    @Override
    public void puzzleTwo() {
        ArrayList<String> values = Helper.readToStringArrayList("C:\\Users\\adgra\\IdeaProjects\\AdventOfCode2022\\src\\input\\day23");
        char[][] map = new char[values.size()][values.get(0).length()];

        int x = 0;
        for (String value : values) {
            map[x] = value.toCharArray();


            if (x == 0 || x == values.size() - 1) {
                char toPut = 'E';
                if (x == 0) {
                    toPut = 'S';
                }

                int y = 0;
                for (char c : map[x]) {
                    if (c == '.') {
                        map[x][y] = toPut;
                    }

                    y++;
                }
            }
            x += 1;
        }

        for (int xx = 0; xx < map.length; xx++) {
            for (int y = 0; y < map[0].length; y++) {
                char c = map[xx][y];
                if (c != 'S' && c != 'E' && c != '.' && c != '#') {
                    map[xx][y] = '.';
                }
            }
        }

        LocationPartTwo.matrix = map;
        Location.map = map;
        Location.maxX = map.length;
        Location.maxY = map[0].length;

        ArrayList<Location> nodes = new ArrayList<>();
        nodes.add(new Location(0, 1, -1, new ArrayList<>()));
        nodes.add(new Location(map.length - 1, map[0].length - 2, -1, new ArrayList<>()));
        for (int i = 0; i < map.length; i++) {
            for (int y = 0; y < map.length; y++) {
                LocationPartTwo locationPartTwo = new LocationPartTwo(i, y);
                if (!locationPartTwo.valid()) {
                    continue;
                }
                int size = new Location(i, y, -1, new ArrayList<>()).getAllNextPositions().size();
                if (size > 2) {
                    nodes.add(new Location(i, y, -1, new ArrayList<>()));
                }
            }
        }

        int a = 0;


        Graph graph = new Graph();
        nodes.forEach(graph::addVertex);
        for (Location node : nodes) {
            for (Location node2 : nodes) {
                Location nodeC = node.clone();
                Location node2C = node2.clone();

                if (nodeC.equals(node2C)) {
                    continue;
                }

                PriorityQueue<Location> queue = new PriorityQueue<>();
                queue.add(nodeC);

                int highest = 0;
                while (!queue.isEmpty()) {
                    Location poll = queue.poll();

                    if (!poll.equals(nodeC) && !poll.equals(node2C) && nodes.contains(poll)) {
                        continue;
                    }

                    if (poll.equals(node2C)) {

                        int size = poll.prevs.size();
                        if (size > highest) {
                            highest = size;
                        }
                    }

                    ArrayList<Location> allNextPositions = poll.getAllNextPositions();
                    queue.addAll(allNextPositions);
                }
                if (highest != 0) {
                    graph.addEdge(node, node2, highest);
                }
            }
        }

        LinkedList<Location> stack = new LinkedList<>();
        stack.push(nodes.get(0));
        Location end = nodes.get(1);
        ArrayList<ArrayList<Location>> visited = new ArrayList<>();

        findAllPaths(graph, nodes.get(0), nodes.get(1), new Stack<>());

        int highest = 0;
        for (Stack<Location> locations : connectionPaths) {

            locations.add(0, nodes.get(0));
            locations.add(nodes.get(1));
            int total = 0;
            for (int z = 1; z < locations.size() - 1; z++) {
                total += graph.vertices.get(locations.get(z - 1)).get(locations.get(z));
            }

            if (total > highest) {
                highest = total;
            }
        }

        System.out.println(highest + 2);


        System.out.println("f");


    }
    List<Stack<Location>> connectionPaths = new ArrayList<>();
    void findAllPaths(Graph graph, Location start, Location end, Stack<Location> connectionPath) {
        for (Location nextNode: graph.vertices.get(start).keySet()) {
            if (nextNode.equals(end)) {
                Stack<Location> temp = new Stack<>();
                temp.addAll(connectionPath);
                connectionPaths.add(temp);
            } else if (!connectionPath.contains(nextNode)) {
                connectionPath.push(nextNode);
                findAllPaths(graph, nextNode, end, connectionPath);
                connectionPath.pop();
            }
        }
    }

    class Graph {
        HashMap<Location, HashMap<Location, Integer>> vertices = new HashMap<>();

        Graph() {

        }

        void addVertex(Location locationPartTwo) {
            vertices.put(locationPartTwo, new HashMap<>());
        }

        void addEdge(Location locationPartTwo, Location locationPartTwo2, Integer distance) {
            vertices.get(locationPartTwo).put(locationPartTwo2, distance);
            vertices.get(locationPartTwo2).put(locationPartTwo, distance);
        }
    }
}