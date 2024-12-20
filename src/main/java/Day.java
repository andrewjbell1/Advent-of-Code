import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.List;
import java.util.Objects;

public abstract class Day {
    private final int day;

    public Day(int day){
        this.day = day;
    }

    public List<String> splitLines(String input) {
        return Arrays.asList(input.split(System.lineSeparator()));
    }

    public char[][] asMatrix(String input) {
        String[] lines = input.split(System.lineSeparator());

        char[][] matrix = new char[lines.length][lines[0].length()];

        for (int y = 0; y < lines.length; y++) {
            for (int x = 0; x < lines[0].length(); x++) {
                matrix[y][x] = lines[y].charAt(x);
            }
        }
        return matrix;
    }

    public String readFile(String name) throws IOException, URISyntaxException {
        return Files.readString(Paths.get(Objects.requireNonNull(this.getClass().getClassLoader().getResource (name)).toURI()));
    }

    public record InputFiles (String testcase, String examplePart1, String examplePart2, String examplePart1Result, String examplePart2Result){}

    public InputFiles loadFiles(){
        try {
            return new InputFiles(
                    readFile("day%s/input.txt".formatted(day)),
                    readFile("day%s/example_part1.txt".formatted(day)),
                    readFile("day%s/example_part2.txt".formatted(day)),
                    readFile("day%s/example_part1_result.txt".formatted(day)),
                    readFile("day%s/example_part2_result.txt".formatted(day)));
        }
        catch (IOException | URISyntaxException exception){
            throw new RuntimeException("Failed to load files", exception);
        }

    }

    public void runPart1() {
        InputFiles files = loadFiles();
        String exampleResult = String.valueOf(part1(files.examplePart1));
        if (!exampleResult.equals(files.examplePart1Result)){
            throw new RuntimeException("Part 1 - Expected: " + files.examplePart1Result + " but got: " + exampleResult);
        }
        System.out.println("Part 1: example is correct");
        System.out.println("Part 1: " + part1(files.testcase));
    }

    public void runPart2() {
        InputFiles files = loadFiles();
        String exampleResult = String.valueOf(part2(files.examplePart2));
        if (!exampleResult.equals(files.examplePart2Result())){
            throw new RuntimeException("Part 2 - Expected: " + files.examplePart2Result + " but got: " + exampleResult);
        }
        System.out.println("Part 2: example is correct");
        System.out.println("Part 2: "+ part2(files.testcase));
    }

    public abstract long part1(String input);

    public abstract long part2(String input);

    public record Position(int x, int y){

        public static Position of(int x, int y){
            return new Position(x, y);
        }

        public Position subtract(Position positionToSubtract){
            return Position.of(this.x - positionToSubtract.x, this.y - positionToSubtract.y);
        }

        public boolean inGrid(char[][] grid){
            return this.y() < grid.length && this.x() < grid[0].length && this.y() >= 0 && this.x() >= 0;
        }

        public Position nextPosition(Direction direction) {
            return switch (direction) {
                case NORTH -> Position.of(this.x(), this.y() - 1);
                case EAST -> Position.of(this.x() + 1, this.y());
                case SOUTH -> Position.of(this.x(), this.y() + 1);
                case WEST -> Position.of(this.x() - 1, this.y());
                default -> throw new IllegalArgumentException("Not implemented");
            };
        }

        @Override
        public boolean equals(Object o) {
            Position position = (Position) o;

            if (x != position.x) return false;
            return y == position.y;
        }
    };


    public enum Direction {NORTH, EAST, SOUTH, WEST, NORTHEAST, NORTHWEST, SOUTHEAST, SOUTHWEST;

        public Direction opposite(){
            return switch (this) {
                case NORTH -> SOUTH;
                case EAST -> WEST;
                case SOUTH -> NORTH;
                case WEST -> EAST;
                default -> throw new IllegalArgumentException("Not implemented");
            };
        }

        public Direction rotateClockwise(){
            return switch (this) {
                case NORTH -> EAST;
                case EAST -> SOUTH;
                case SOUTH -> WEST;
                case WEST -> NORTH;
                default -> throw new IllegalArgumentException("Not implemented");
            };
        }

        public Direction rotateAntiClockwise() {
            return switch (this) {
                case NORTH -> WEST;
                case WEST -> SOUTH;
                case SOUTH -> EAST;
                case EAST -> NORTH;
                default -> throw new IllegalArgumentException("Not implemented");
            };
        }
    }

}
