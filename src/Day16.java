import helpers.Helper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class Day16 extends CodeDay {

    @Override
    public void puzzleOne() {
        var input = Helper.readToStringArrayList("src/input/day16");

        char[][] matrix = input.stream()
                .map(String::toCharArray)
                .toArray(size -> new char[size][input.size()]);

        ArrayList<Position> energized = new ArrayList<>();
        HashMap<Position, ArrayList<Direction>> disabled = new HashMap<>();
        ArrayList<Beam> beams = new ArrayList<>();
        beams.add(new Beam(new Position(0, -1), Direction.RIGHT));

        boolean done = false;


        while (!done) {
            beams.forEach(Beam::setNextPosition);
            ArrayList<Beam> toAdd = new ArrayList<>();
            ArrayList<Beam> toRemove = new ArrayList<>();

            for (Beam beam : beams) {

                Position position = beam.position;

                if (disabled.containsKey(position)) {
                    if (disabled.get(position).contains(beam.direction)) {
                        toRemove.add(beam);
                        continue;
                    }
                } else {
                    disabled.put(position, new ArrayList<>());
                }

                if (!(position.x >= 0 && position.x < matrix.length && position.y >= 0 && position.y < matrix[0].length)) {
                    toRemove.add(beam);
                    continue;
                }

                char nextLocation = matrix[position.x][position.y];

                if (!energized.contains(beam.position)) {
                    energized.add(beam.position);
                }

                switch (nextLocation) {
                    case '/':
                        disabled.get(position).add(beam.direction);

                        if (beam.direction == Direction.DOWN) {
                            beam.direction = Direction.LEFT;
                        } else if (beam.direction == Direction.UP) {
                            beam.direction = Direction.RIGHT;
                        } else if (beam.direction == Direction.LEFT) {
                            beam.direction = Direction.DOWN;
                        } else if (beam.direction == Direction.RIGHT) {
                            beam.direction = Direction.UP;
                        }
                        break;
                    case '\\':
                        disabled.get(position).add(beam.direction);

                        if (beam.direction == Direction.DOWN) {
                            beam.direction = Direction.RIGHT;
                        } else if (beam.direction == Direction.UP) {
                            beam.direction = Direction.LEFT;
                        } else if (beam.direction == Direction.LEFT) {
                            beam.direction = Direction.UP;
                        } else if (beam.direction == Direction.RIGHT) {
                            beam.direction = Direction.DOWN;
                        }
                        break;
                    case '|':
                        if (beam.direction == Direction.LEFT || beam.direction == Direction.RIGHT) {
                            disabled.get(position).add(beam.direction);
                            beam.direction = Direction.UP;
                            toAdd.add(new Beam(beam.position, Direction.DOWN));
                        }
                        break;
                    case '-':
                        if (beam.direction == Direction.UP || beam.direction == Direction.DOWN) {
                            disabled.get(position).add(beam.direction);
                            beam.direction = Direction.LEFT;
                            toAdd.add(new Beam(beam.position, Direction.RIGHT));
                        }
                        break;
                }
            }

            beams.addAll(toAdd);
            beams.removeAll(toRemove);

            if (beams.isEmpty()) {
                done = true;
            }
        }

        System.out.println(energized.size());
    }




    @Override
    public void puzzleTwo() {
        var input = Helper.readToStringArrayList("src/input/day16");

        char[][] matrix = input.stream()
                .map(String::toCharArray)
                .toArray(size -> new char[size][input.size()]);

        HashMap<Position, ArrayList<Direction>> todo = new HashMap<>();

        for (int x = -1; x <= matrix.length; x++) {
            for (int y = -1; y <= matrix[0].length; y++) {
                if (!(x == -1 || x == matrix.length || y == -1 || y == matrix[0].length)) {
                    continue;
                }

                Position position = new Position(x, y);
                if (!todo.containsKey(position)) {
                    todo.put(position, new ArrayList<>());
                }
                ArrayList<Direction> directions = todo.get(position);

                if (x == -1 && y == -1) {
                    directions.add(Direction.RIGHT);
                    directions.add(Direction.DOWN);
                    continue;
                }

                if (x == -1 && y == matrix[0].length) {
                    directions.add(Direction.LEFT);
                    directions.add(Direction.DOWN);
                    continue;
                }

                if (x == matrix.length && y == -1) {
                    directions.add(Direction.RIGHT);
                    directions.add(Direction.UP);
                    continue;
                }

                if (x == matrix.length && y == matrix[0].length) {
                    directions.add(Direction.LEFT);
                    directions.add(Direction.UP);
                    continue;
                }

                if (x == -1) {
                    directions.add(Direction.DOWN);
                }

                if (x == matrix.length) {
                    directions.add(Direction.UP);
                }

                if (y == -1) {
                    directions.add(Direction.RIGHT);
                }

                if (y == matrix[0].length) {
                    directions.add(Direction.LEFT);
                }
            }
        }

        int highest = 0;

        for (Position pos : todo.keySet()) {
            for (Direction direction : todo.get(pos)) {
                ArrayList<Position> energized = new ArrayList<>();
                HashMap<Position, ArrayList<Direction>> disabled = new HashMap<>();
                ArrayList<Beam> beams = new ArrayList<>();
                beams.add(new Beam(pos, direction));

                boolean done = false;


                while (!done) {
                    beams.forEach(Beam::setNextPosition);
                    ArrayList<Beam> toAdd = new ArrayList<>();
                    ArrayList<Beam> toRemove = new ArrayList<>();

                    for (Beam beam : beams) {

                        Position position = beam.position;

                        if (disabled.containsKey(position)) {
                            if (disabled.get(position).contains(beam.direction)) {
                                toRemove.add(beam);
                                continue;
                            }
                        } else {
                            disabled.put(position, new ArrayList<>());
                        }

                        if (!(position.x >= 0 && position.x < matrix.length && position.y >= 0 && position.y < matrix[0].length)) {
                            toRemove.add(beam);
                            continue;
                        }

                        char nextLocation = matrix[position.x][position.y];

                        if (!energized.contains(beam.position)) {
                            energized.add(beam.position);
                        }

                        switch (nextLocation) {
                            case '/':
                                disabled.get(position).add(beam.direction);

                                if (beam.direction == Direction.DOWN) {
                                    beam.direction = Direction.LEFT;
                                } else if (beam.direction == Direction.UP) {
                                    beam.direction = Direction.RIGHT;
                                } else if (beam.direction == Direction.LEFT) {
                                    beam.direction = Direction.DOWN;
                                } else if (beam.direction == Direction.RIGHT) {
                                    beam.direction = Direction.UP;
                                }
                                break;
                            case '\\':
                                disabled.get(position).add(beam.direction);

                                if (beam.direction == Direction.DOWN) {
                                    beam.direction = Direction.RIGHT;
                                } else if (beam.direction == Direction.UP) {
                                    beam.direction = Direction.LEFT;
                                } else if (beam.direction == Direction.LEFT) {
                                    beam.direction = Direction.UP;
                                } else if (beam.direction == Direction.RIGHT) {
                                    beam.direction = Direction.DOWN;
                                }
                                break;
                            case '|':
                                if (beam.direction == Direction.LEFT || beam.direction == Direction.RIGHT) {
                                    disabled.get(position).add(beam.direction);
                                    beam.direction = Direction.UP;
                                    toAdd.add(new Beam(beam.position, Direction.DOWN));
                                }
                                break;
                            case '-':
                                if (beam.direction == Direction.UP || beam.direction == Direction.DOWN) {
                                    disabled.get(position).add(beam.direction);
                                    beam.direction = Direction.LEFT;
                                    toAdd.add(new Beam(beam.position, Direction.RIGHT));
                                }
                                break;
                        }
                    }

                    beams.addAll(toAdd);
                    beams.removeAll(toRemove);

                    if (beams.isEmpty()) {
                        done = true;

                        if (energized.size() > highest) {
                            highest = energized.size();
                        }
                    }
                }
            }
        }

        System.out.println(highest);
    }

    class Beam {
        Position position;
        Direction direction;

        public Beam (Position position, Direction direction) {
            this.position = position;
            this.direction = direction;
        }

        public void setNextPosition() {

            this.position = switch (this.direction) {
                case UP -> new Position(position.x-1, position.y);
                case DOWN -> new Position(position.x+1, position.y);
                case LEFT -> new Position(position.x, position.y-1);
                case RIGHT -> new Position(position.x, position.y+1);
            };
        }
    }

    class Position {
        int x;
        int y;
        public Position(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Position position = (Position) o;
            return x == position.x && y == position.y;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y);
        }
    }

    enum Direction {
        UP, DOWN, LEFT, RIGHT
    }
}
