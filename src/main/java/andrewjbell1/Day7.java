package andrewjbell1;

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

    public String part1(String input) {
        List<String> lines = splitLines(input);
        var allOperations = getAllOperations(14, false);
        return String.valueOf(readOperations(lines, allOperations));
    }

    public String part2(String input) {
        List<String> lines = splitLines(input);
        var allOperations = getAllOperations(14, true);

        return String.valueOf(readOperations(lines, allOperations));
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

    public Set<String> getPosOperations(Set<String> operationsMinus1, boolean includeConcat){
        Set<String> operationSet = new HashSet<>();

        for (String opLess : operationsMinus1){
            operationSet.add(opLess +"+");
            operationSet.add(opLess + "*");
            if (includeConcat){
                operationSet.add(opLess+"|");
            }
        }
        return operationSet;
    }

    private long readOperations(List<String> lines, HashMap<Integer, Set<String>> allOperations){
        long calibrationTotal = 0L;
        for(String line: lines){
            var lineSplit = line.split(":");
            var answer = Long.parseLong(lineSplit[0]);
            var operatorLine = lineSplit[1];

            var operators = operatorLine.trim().split(" ");

            var operations = allOperations.get(operators.length-2);
            for (String operation: operations){
                long sum = Long.parseLong(operators[0]);
                var operationsSplit = operation.split("");
                for (int i = 0; i < operators.length-1; i++) {
                    var val2 = Integer.parseInt(operators[i+1]);
                    var op = operationsSplit[i];

                    if (op.equals("*")){
                        sum = sum * val2;
                    }
                    if (op.equals("+")){
                        sum = sum + val2;
                    }
                    if (op.equals("|")){
                        sum =Long.parseLong(String.valueOf(sum) + val2);
                    }

                }
                if (sum == answer){
                    calibrationTotal+=sum;
                    break;
                }
            }
        }
        return calibrationTotal;
    }

}
