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

    public long part1(String input) {
        char[][] grid = asMatrix(input);
        Direction initialDirection = Direction.NORTH;
        Position initialPosition = null;
        List<Direction> directions = List.of(Direction.NORTH, Direction.EAST, Direction.SOUTH, Direction.WEST);

        HashMap<Position, PositionMetadata> positionMap = new HashMap<>();
        for (int y = 0; y < grid.length; y++) {
            for (int x = 0; x < grid[0].length; x++) {
                char currentChar = grid[y][x];
                if (Character.toString(currentChar).equals("^")) {
                    initialPosition = Position.of(x, y);
                }
                positionMap.put(Position.of(x, y), new PositionMetadata(Character.toString(currentChar).equals("#"), Set.of()));
            }
        }
        positionMap.put(initialPosition, new PositionMetadata(false, Set.of(initialDirection)));


        long count = walkGrid(initialPosition, initialDirection, grid, positionMap, directions).orElseThrow();
        return count;
    }

    public Position nextCoordinates(Position currentPos, Direction direction) {
        return switch (direction) {
            case NORTH -> Position.of(currentPos.x(), currentPos.y() - 1);
            case EAST -> Position.of(currentPos.x() + 1, currentPos.y());
            case SOUTH -> Position.of(currentPos.x(), currentPos.y() + 1);
            case WEST -> Position.of(currentPos.x() - 1, currentPos.y());
            default -> null;
        };
    }

    public long part2(String input) {
        char[][] grid = asMatrix(input);
        Direction initialDirection = Direction.NORTH;
        Position initialPosition = null;

        List<Direction> directions = List.of(Direction.NORTH, Direction.EAST, Direction.SOUTH, Direction.WEST);

        HashMap<Position, PositionMetadata> positionMap = new HashMap<>();
        for (int y = 0; y < grid.length; y++) {
            for (int x = 0; x < grid[0].length; x++) {
                char currentChar = grid[y][x];
                if (Character.toString(currentChar).equals("^")) {
                    initialPosition = Position.of(x, y);
                }
                positionMap.put(Position.of(x, y), new PositionMetadata(Character.toString(currentChar).equals("#"), Set.of()));
            }
        }

        positionMap.put(initialPosition, new PositionMetadata(false, Set.of(initialDirection)));

        Position finalInitialPosition = initialPosition;

        var potentialObstacles = positionMap.entrySet().stream().
                filter(entry -> entry.getKey() != finalInitialPosition && !entry.getValue().obstacle)
                        .map(Map.Entry::getKey)
                                .toList();

        int count = 0;
        for (Position potentialObstaclePosition : potentialObstacles) {
            HashMap<Position, PositionMetadata> positionMapWithNewObstacle = new HashMap<>(positionMap);
            positionMapWithNewObstacle.put(potentialObstaclePosition, new PositionMetadata(true, Set.of()));

            if (walkGrid(initialPosition, initialDirection, grid, positionMapWithNewObstacle, directions).isEmpty()) {
                count++;
            }
        }
        return count;
    }

    private Optional<Long> walkGrid(Position position, Direction direction, char[][] grid, HashMap<Position, PositionMetadata> positionMap, List<Direction> directions) {
        boolean positionInGrid;
        boolean obstacleCreatesLoop = false;

        while (true) {
            var possibleNextPos = nextCoordinates(position, direction);
            positionInGrid = possibleNextPos.x() < grid[0].length && possibleNextPos.y() < grid.length && possibleNextPos.x() >= 0 && possibleNextPos.y() >= 0;
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
