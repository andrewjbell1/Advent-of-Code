import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day3 extends Day {

    public Day3(int day) {
        super(day);
    }

    public static void main(String[] args) {
        var day = new Day3(3);
        day.runPart1();
        day.runPart2();
    }

    public long part1(String input) {
        Pattern p = Pattern.compile("mul\\(([0-9]+),([0-9]+)\\)");
        Matcher matcher = p.matcher(input);
        int total = 0;
        while (matcher.find()) {
            total += Integer.parseInt(matcher.group(1)) * Integer.parseInt(matcher.group(2));
        }
        return total;
    }

    public long part2(String input) {
        Pattern p = Pattern.compile("do\\(\\)|don't\\(\\)|mul\\(([0-9]+),([0-9]+)\\)");
        Matcher matcher = p.matcher(input);
        boolean enabled = true;
        int total = 0;

        while (matcher.find()) {
            if (matcher.group().startsWith("mul")) {
                if (enabled) {
                    total += Integer.parseInt(matcher.group(1)) * Integer.parseInt(matcher.group(2));
                }
            } else if (matcher.group().equals("don't()")) {
                enabled = false;

            } else if (matcher.group().equals("do()")) {
                enabled = true;
            }
        }
        return total;
    }

}
