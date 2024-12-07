package andrewjbell1;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

public class Day6 extends Day {

    public Day6(int day) {
        super(day);
    }

    public static void main(String[] args) {
        var day = new Day6(6);
        day.runPart1();
        day.runPart2();
    }


    public String part1(String input) {
        char[][] grid = asMatrix(input);
        Direction initialDirection = Direction.NORTH;
        Tuple<Integer, Integer> initialPosition = null;

        List<Direction> directions = List.of(Direction.NORTH, Direction.EAST, Direction.SOUTH, Direction.WEST);

        HashMap<Tuple<Integer, Integer>, PositionMetadata> positionMap = new HashMap<>();
        for (int y = 0; y < grid.length; y++) {
            for (int x = 0; x < grid[0].length; x++) {
                char currentChar = grid[y][x];
                if (Character.toString(currentChar).equals("^")) {
                    initialPosition = new Tuple<>(x, y);
                }
                positionMap.put(new Tuple<>(x, y), new PositionMetadata(Character.toString(currentChar).equals("#"), Set.of()));
            }
        }
        positionMap.put(initialPosition, new PositionMetadata(false, Set.of(initialDirection)));


        long count = walkGrid(initialPosition, initialDirection, grid, positionMap, directions).orElseThrow();
        return String.valueOf(count);
    }

    public Tuple<Integer, Integer> nextCoordinates(Tuple<Integer, Integer> currentPos, Direction direction) {
        return switch (direction) {
            case NORTH -> new Tuple<>(currentPos.x, currentPos.y - 1);
            case EAST -> new Tuple<>(currentPos.x + 1, currentPos.y);
            case SOUTH -> new Tuple<>(currentPos.x, currentPos.y + 1);
            case WEST -> new Tuple<>(currentPos.x - 1, currentPos.y);
            default -> null;
        };
    }

    public String part2(String input) {
        char[][] grid = asMatrix(input);
        Direction initialDirection = Direction.NORTH;
        Tuple<Integer, Integer> initialPosition = null;

        List<Direction> directions = List.of(Direction.NORTH, Direction.EAST, Direction.SOUTH, Direction.WEST);

        HashMap<Tuple<Integer, Integer>, PositionMetadata> positionMap = new HashMap<>();
        for (int y = 0; y < grid.length; y++) {
            for (int x = 0; x < grid[0].length; x++) {
                char currentChar = grid[y][x];
                if (Character.toString(currentChar).equals("^")) {
                    initialPosition = new Tuple<>(x, y);
                }
                positionMap.put(new Tuple<>(x, y), new PositionMetadata(Character.toString(currentChar).equals("#"), Set.of()));
            }
        }

        positionMap.put(initialPosition, new PositionMetadata(false, Set.of(initialDirection)));

        Tuple<Integer, Integer> finalInitialPosition = initialPosition;

        var potentialObstacles = positionMap.entrySet().stream().
                filter(entry -> entry.getKey() != finalInitialPosition && !entry.getValue().obstacle)
                        .map(Map.Entry::getKey)
                                .toList();

        int count = 0;
        for (Tuple<Integer, Integer> potentialObstaclePosition : potentialObstacles) {
            HashMap<Tuple<Integer, Integer>, PositionMetadata> positionMapWithNewObstacle = new HashMap<>(positionMap);
            positionMapWithNewObstacle.put(potentialObstaclePosition, new PositionMetadata(true, Set.of()));

            if (walkGrid(initialPosition, initialDirection, grid, positionMapWithNewObstacle, directions).isEmpty()) {
                count++;
            }
        }

        return String.valueOf(count);
    }

    private Optional<Long> walkGrid(Tuple<Integer, Integer> position, Direction direction, char[][] grid, HashMap<Tuple<Integer, Integer>, PositionMetadata> positionMap, List<Direction> directions) {
        boolean positionInGrid;
        boolean obstacleCreatesLoop = false;

        while (true) {
            var possibleNextPos = nextCoordinates(position, direction);
            positionInGrid = possibleNextPos.x < grid[0].length && possibleNextPos.y < grid.length && possibleNextPos.x >= 0 && possibleNextPos.y >= 0;
            if (!positionInGrid) {
                break;
            }

            if (positionMap.get(possibleNextPos).visited.contains(direction)) {
                obstacleCreatesLoop = true;
                break;
            }

            if (!positionMap.get(possibleNextPos).obstacle) {
                position = possibleNextPos;
                var setOfDirections = positionMap.get(position).visited;
                Set<Direction> newSet = new HashSet<>(setOfDirections);
                newSet.add(direction);
                positionMap.put(position, new PositionMetadata(false, newSet));
            } else {
                direction = directions.get(directions.indexOf(direction) == 3 ? 0 : directions.indexOf(direction) + 1);
            }
        }

        if (obstacleCreatesLoop) {
            return Optional.empty();
        } else {
            return Optional.of(positionMap.values().stream().filter(positionMetadata -> !positionMetadata.visited.isEmpty()).count());
        }
    }

    record PositionMetadata(boolean obstacle, Set<Direction> visited) {}
}
