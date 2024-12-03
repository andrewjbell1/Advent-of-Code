package andrewjbell1;

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

    public String readInput() throws IOException, URISyntaxException {
        return readFile("day%s/input.txt".formatted(day));
    }

    public String readExample() throws IOException, URISyntaxException {
        return readFile("day%s/example.txt".formatted(day));
    }

    public String readExamplePart1Result() throws IOException, URISyntaxException {
        return readFile("day%s/example_part1_result.txt".formatted(day));
    }

    public String readExamplePart2Result() throws IOException, URISyntaxException {
        return readFile("day%s/example_part2_result.txt".formatted(day));
    }

    public String readFile(String name) throws IOException, URISyntaxException {
        return Files.readString(Paths.get(Objects.requireNonNull(this.getClass().getClassLoader().getResource (name)).toURI()));
    }

    public record InputFiles (String testcase, String example, String examplePart1Result, String examplePart2Result){}

    public InputFiles loadFiles(){
        try {
            return new InputFiles(readInput(), readExample(), readExamplePart1Result(), readExamplePart2Result());
        }
        catch (IOException | URISyntaxException exception){
            throw new RuntimeException("Failed to load files", exception);
        }

    }

    public void runPart1() {
        InputFiles files = loadFiles();
        String exampleResult = part1(files.example);
        if (!exampleResult.equals(files.examplePart1Result)){
            throw new RuntimeException("Part 1 - Expected: " + files.examplePart1Result + " but got: " + exampleResult);
        }
        System.out.println("Part 1: " + part1(files.testcase));
    }

    public void runPart2() {
        InputFiles files = loadFiles();
        String exampleResult = part2(files.example);
        if (!exampleResult.equals(files.examplePart2Result())){
            throw new RuntimeException("Part 2 - Expected: " + files.examplePart2Result + " but got: " + exampleResult);
        }
        System.out.println("Part 2: "+ part2(files.testcase));
    }

    public abstract String part1(String input);

    public abstract String part2(String input);

}
