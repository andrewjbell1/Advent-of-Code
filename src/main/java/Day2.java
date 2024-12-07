import java.util.ArrayList;
import java.util.List;

public class Day2 extends Day {

    public Day2(int day) {
        super(day);
    }

    public static void main(String[] args) {
        var day = new Day2(2);
        day.runPart1();
        day.runPart2();
    }

    public long part1(String input) {
        return splitLines(input)
                .stream()
                .map(line -> line.split(" "))
                .filter(line -> isReportSafe(line, true) || isReportSafe(line, false))
                .count();
    }

    public long part2(String input) {
        return splitLines(input)
                .stream()
                .map(line -> line.split(" "))
                .filter(this::isReportSafeWithDampener)
                .count();
    }

    private boolean isReportSafe(String[] levels, boolean ascending) {
        for (int i = 0; i < levels.length - 1; i++) {
            int first = Integer.parseInt(levels[i]);
            int second = Integer.parseInt(levels[i + 1]);
            int diff = ascending ? second - first : first - second;
            if (diff <= 0 || diff > 3) {
                return false;
            }
        }
        return true;
    }

    private boolean isReportSafeWithDampener(String[] levels) {
        for (int i = 0; i < levels.length; i++) {
            var newList = new ArrayList<>(List.of(levels));
            newList.remove(i);
            var levelsWithOneRemoved = newList.toArray(String[]::new);
            if (isReportSafe(levelsWithOneRemoved, false) || isReportSafe(levelsWithOneRemoved, true)) {
                return true;
            }
        }
        return false;
    }
}
