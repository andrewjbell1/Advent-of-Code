import java.util.HashSet;

public class Day8 extends Day{

    public static void main(String[] args) {
        Day8 day8 = new Day8(8);
        day8.runPart1();
        day8.runPart2();
    }

    public Day8(int day) {
        super(day);
    }

    @Override
    public long part1(String input) {
        var grid = asMatrix(input);
        var antinodePositions = new HashSet<Position>();

        for (int y = 0; y < grid.length; y++) {
            for (int x = 0; x < grid[y].length; x++) {
                if (grid[y][x] !='.'){
                    var antPosition = Position.of(x, y);
                    var antenna = grid[y][x];
                    scanForMatchingAntenna(antPosition, antenna, grid, antinodePositions);
                }
            }
        }

        antinodePositions.forEach(System.out::println);
        return antinodePositions.size();
    }

    private void scanForMatchingAntenna(Position antPosition, char antenna, char[][] grid, HashSet<Position> antinodePositions) {
        for (int y = 0; y < grid.length; y++) {
            for (int x = 0; x < grid[0].length; x++) {
                Position currentPosition = Position.of(x, y);
                char possibleAntenna = grid[currentPosition.y()][currentPosition.x()];
                if (possibleAntenna == antenna) {
                    Position positionDiff = antPosition.subtract(currentPosition);
                    Position antiNodePosition = currentPosition.subtract(positionDiff);
                    if (currentPosition.x() != antPosition.x() && currentPosition.y() != antPosition.y()) {
                        if (antiNodePosition.inGrid(grid)) {
                            antinodePositions.add(antiNodePosition);
                        }
                    }
                }
            }
        }
    }

    private void scanForMatchingAntennaPart2(Position antPosition, char antenna, char[][] grid, HashSet<Position> antinodePositions) {
        for (int y = 0; y < grid.length; y++) {
            for (int x = 0; x < grid[0].length; x++) {
                Position currentPosition = Position.of(x, y);
                char possibleAntenna = grid[currentPosition.y()][currentPosition.x()];
                if (possibleAntenna == antenna) {
                    Position positionDiff = antPosition.subtract(currentPosition);

                    while (true) {
                        Position antiNodePosition = currentPosition.subtract(positionDiff);
                        if (antiNodePosition.inGrid(grid)) {
                            antinodePositions.add(antiNodePosition);
                        }
                        else{
                            break;
                        }
                        if (positionDiff.x()==0 && positionDiff.y()==0){ // Only add antenna once
                            break;
                        }
                        currentPosition = antiNodePosition;
                    }
                }
            }
        }
    }

    @Override
    public long part2(String input) {
        var grid = asMatrix(input);
        var antinodePositions = new HashSet<Position>();

        for (int y = 0; y < grid.length; y++) {
            for (int x = 0; x < grid[y].length; x++) {
                if (grid[y][x] !='.'){
                    var antPosition = Position.of(x, y);
                    var antenna = grid[y][x];
                    scanForMatchingAntennaPart2(antPosition, antenna, grid, antinodePositions);
                }

            }
        }
        antinodePositions.forEach(System.out::println);
        return antinodePositions.size();
    }
}
