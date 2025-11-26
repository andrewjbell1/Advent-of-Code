import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Day7 extends Day {

    public Day7(int day) {
        super(day);
    }

    public static void main(String[] args) {
        var day = new Day7(7);
        day.runPart1();
        day.runPart2();
    }

    public long part1(String input) {
        List<String> lines = splitLines(input);
        var allOperations = getAllOperations(14, false);
        return readOperations(lines, allOperations);
    }

    public long part2(String input) {
        List<String> lines = splitLines(input);
        var allOperations = getAllOperations(14, true);
        return readOperations(lines, allOperations);
    }

    public HashMap<Integer, Set<String>> getAllOperations(int maxOperators, boolean includeConcat){
        HashMap<Integer, Set<String>> allOps = new HashMap<>();
        var initialSet = includeConcat ? Set.of("+", "*", "|") : Set.of("+", "*");
        allOps.put(0, initialSet);
        for (int i = 1; i < maxOperators; i++) {
            allOps.put(i, getPosOperations(allOps.get(i-1), includeConcat));
        }
        return allOps;
    }

    public Set<String> getPosOperations(Set<String> oldOperations, boolean includeConcat){
        Set<String> operationSet = new HashSet<>();
        for (String oldOp : oldOperations){
            operationSet.add(oldOp + "+");
            operationSet.add(oldOp + "*");
            if (includeConcat){
                operationSet.add(oldOp+ "|");
            }
        }
        return operationSet;
    }

    private long readOperations(List<String> lines, HashMap<Integer, Set<String>> allOperations){
        long calibrationTotal = 0L;
        for(String line: lines){
            var lineSplit = line.split(":");
            var answer = Long.parseLong(lineSplit[0]);
            var operators = lineSplit[1].trim().split(" ");
            var operations = allOperations.get(operators.length-2);
            for (String operation: operations){
                long sum = attemptOperation(operation, operators);
                if (sum == answer){
                    calibrationTotal+=sum;
                    break;
                }
            }
        }
        return calibrationTotal;
    }

    private static long attemptOperation(String operation, String[] operators) {
        long sum = Long.parseLong(operators[0]);
        char[] operationsSplit = operation.toCharArray();
        for (int i = 0; i < operators.length-1; i++) {
            var val = Integer.parseInt(operators[i+1]);
            var op = operationsSplit[i];
            switch (op){
                case '*' -> sum *= val;
                case '+' -> sum += val;
                case '|' -> sum = Long.parseLong(String.valueOf(sum) + val);
            }
        }
        return sum;
    }

}
