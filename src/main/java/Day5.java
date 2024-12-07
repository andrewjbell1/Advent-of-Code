import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Day5 extends Day {
    public Day5(int day) {
        super(day);
    }

    public static void main(String[] args) {
        var day = new Day5(5);
        day.runPart1();
        day.runPart2();
    }

    public String part1(String input) {
        var inputLines = splitLines(input);
        var orderingRules = mapRules(inputLines);
        var updateLines = inputLines.stream().filter(line -> line.contains(",")).toList();

        int total =  0;
        for(String updateLine: updateLines){
            List<Integer> updateList = Arrays.stream(updateLine.split(",")).map(Integer::parseInt).toList();

            var orderingRulesFiltered = orderingRules.stream()
                    .filter(s -> updateList.contains(s.x) && updateList.contains(s.y))
                    .toList();

            int middleNumber = updateList.get(updateList.size() / 2);

            if (isValid(updateList, orderingRulesFiltered)){
                total+=middleNumber;
            }
        }

        return String.valueOf(total);
    }

    public String part2(String input) {
        var inputLines = splitLines(input);
        var orderingRules =  mapRules(inputLines);
        var updateLines = inputLines.stream().filter(line -> line.contains(",")).toList();

        int total = 0;
        for (String updateLine: updateLines){
            List<Integer> updateList = Arrays.stream(updateLine.split(",")).map(Integer::parseInt).toList();

            var orderingRulesFiltered = orderingRules.stream()
                    .filter(s -> updateList.contains(s.x) && updateList.contains(s.y))
                    .toList();

            Integer middleIfSwapped = orderAndGetMiddle(updateList, orderingRulesFiltered);
            total += middleIfSwapped;
        }

        return String.valueOf(total);
    }

    private Integer orderAndGetMiddle(List<Integer> line, List<Tuple<Integer, Integer>> rules){
        int swapMade = 0;
        while(!isValid(line, rules)) {
            swapMade++;
            line = attemptOrder(line, rules);
        }
        int middleNumber = line.get(line.size() / 2);
        if (swapMade>0){
            return middleNumber;
        }
        else return 0;
    }

    private List<Integer> attemptOrder(List<Integer> line, List<Tuple<Integer, Integer>> rules){
        boolean ruleSuccess;
        ArrayList<Integer> updatedList = new ArrayList<>(line);
        for (Tuple<Integer, Integer> rule : rules){
            ruleSuccess= updatedList.indexOf(rule.x) < updatedList.indexOf(rule.y);
            if (!ruleSuccess){
                updatedList.remove(rule.x);
                updatedList.add(updatedList.indexOf(rule.y), rule.x);
            }
        }
        return updatedList;
    }

    private List<Tuple<Integer, Integer>> mapRules(List<String> inputLines) {
        return inputLines.stream()
                .filter(line -> line.contains("|"))
                .map(s -> new Tuple<>(Integer.parseInt(s.split("\\|")[0]), Integer.parseInt(s.split("\\|")[1])))
                .toList();
    }

    private boolean isValid(List<Integer> line, List<Tuple<Integer, Integer>> rules){
        boolean valid = true;
        for (Tuple<Integer, Integer> rule : rules) {
            if (valid) {
                valid = line.indexOf(rule.x) < line.indexOf(rule.y);
            }
        }
        return valid;
    }

}
