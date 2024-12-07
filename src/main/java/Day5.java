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

    public long part1(String input) {
        var inputLines = splitLines(input);
        var orderingRules = mapRules(inputLines);
        var updateLines = inputLines.stream().filter(line -> line.contains(",")).toList();

        int total =  0;
        for(String updateLine: updateLines){
            List<Integer> updateList = Arrays.stream(updateLine.split(",")).map(Integer::parseInt).toList();

            var orderingRulesFiltered = orderingRules.stream()
                    .filter(s -> updateList.contains(s.x()) && updateList.contains(s.y()))
                    .toList();

            int middleNumber = updateList.get(updateList.size() / 2);

            if (isValid(updateList, orderingRulesFiltered)){
                total+=middleNumber;
            }
        }

        return total;
    }

    public long part2(String input) {
        var inputLines = splitLines(input);
        var orderingRules =  mapRules(inputLines);
        var updateLines = inputLines.stream().filter(line -> line.contains(",")).toList();

        int total = 0;
        for (String updateLine: updateLines){
            List<Integer> updateList = Arrays.stream(updateLine.split(",")).map(Integer::parseInt).toList();

            var orderingRulesFiltered = orderingRules.stream()
                    .filter(s -> updateList.contains(s.x()) && updateList.contains(s.y()))
                    .toList();

            Integer middleIfSwapped = findMiddleOfOrderedList(updateList, orderingRulesFiltered);
            total += middleIfSwapped;
        }

        return total;
    }

    private Integer findMiddleOfOrderedList(List<Integer> line, List<Position> rules){
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

    private List<Integer> attemptOrder(List<Integer> line, List<Position> rules){
        boolean ruleSuccess;
        ArrayList<Integer> updatedList = new ArrayList<>(line);
        for (Position rule : rules){
            ruleSuccess= updatedList.indexOf(rule.x()) < updatedList.indexOf(rule.y());
            if (!ruleSuccess){
                updatedList.remove(rule.x());
                updatedList.add(updatedList.indexOf(rule.y()), rule.x());
            }
        }
        return updatedList;
    }

    private List<Position> mapRules(List<String> inputLines) {
        return inputLines.stream()
                .filter(line -> line.contains("|"))
                .map(s -> Position.of(Integer.parseInt(s.split("\\|")[0]), Integer.parseInt(s.split("\\|")[1])))
                .toList();
    }

    private boolean isValid(List<Integer> line, List<Position> rules){
        boolean valid = true;
        for (Position rule : rules) {
            if (valid) {
                valid = line.indexOf(rule.x()) < line.indexOf(rule.y());
            }
        }
        return valid;
    }

}
