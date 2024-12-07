import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
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
    };

    public class Tuple<X, Y> {
        public final X x;
        public final Y y;
        public Tuple(X x, Y y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public String toString() {
            return "Tuple{" +
                    "x=" + x +
                    ", y=" + y +
                    '}';
        }

        @Override
        public boolean equals(Object object) {
            if (this == object) return true;
            if (object == null || getClass() != object.getClass()) return false;

            Tuple<?, ?> tuple = (Tuple<?, ?>) object;
            return Objects.equals(x, tuple.x) && Objects.equals(y, tuple.y);
        }

        @Override
        public int hashCode() {
            int result = Objects.hashCode(x);
            result = 31 * result + Objects.hashCode(y);
            return result;
        }
    }

    public enum Direction {NORTH, EAST, SOUTH, WEST, NORTHEAST, NORTHWEST, SOUTHEAST, SOUTHWEST}

}
