package andrewjbell1;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Day1 extends Day {

    public Day1(int day) {
        super(day);
    }

    public static void main(String[] args) {
        var day = new Day1(1);
        day.runPart1();
        day.runPart2();
    }

    public String part1(String input){
        List<Integer> leftList =  listSplitAndOrdered(input, 0);
        List<Integer> rightList = listSplitAndOrdered(input, 1);

        int total = 0;
        for (int i = 0; i < leftList.size(); i++) {
            total += Math.abs(rightList.get(i) - leftList.get(i));
        }
        return String.valueOf(total);
    }

    public String part2(String input) {
        List<Integer> leftList =  listSplitAndOrdered(input, 0);
        List<Integer> rightList = listSplitAndOrdered(input, 1);

        var map = rightList.stream().collect(Collectors.toMap(Function.identity(), v -> 1, Integer::sum));

        int similarityScore = leftList.stream().mapToInt(i ->
         i * map.getOrDefault(i, 0))
                .reduce(Integer::sum).orElseThrow();

        return String.valueOf(similarityScore);
    }

    private List<Integer> listSplitAndOrdered(String input, int index){
        return splitLines(input).stream()
                .map(s ->  Integer.parseInt(s.split(" {3}")[index]))
                .sorted()
                .toList();
    }
}
