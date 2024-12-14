import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class Day11 extends Day {

    public Day11(int day) {
        super(day);
    }

    public static void main(String[] args) {
        var day = new Day11(11);
        day.runPart1();
        day.runPart2();
    }

    public long part1(String input) {

        String[] values = input.split("\\s+");

        long total = 0;
        for (String val : values) {
            var updatedVal = blinkAtValue(val.trim(), 0, 25);
            total += updatedVal;
        }

        return total;
    }

    long blinkAtValue(String input, int blinks, int blinksRequired){
        if (blinks == blinksRequired){
            return input.split(" ").length;
        }

        String [] inputSplit = input.split("\\s+");
        StringBuilder returnVal= new StringBuilder();
        for (String s: inputSplit){
            s = s.trim();
            if (s.equals("0")){
                returnVal.append("1 ");
            }
            else if (s.length()%2==0){
                var middleIndex = s.length() / 2;
                var leftSplit = s.substring(0, middleIndex).replaceFirst("^0+(?!$)", "");
                var rightSplit = s.substring(middleIndex).replaceFirst("^0+(?!$)", "");
                returnVal.append(leftSplit).append(" ").append(rightSplit).append(" ");

            }
            else{
                returnVal.append(Long.parseLong(s) * 2024).append(" ");
            }
        }
        return blinkAtValue(returnVal.toString(), blinks +1, blinksRequired);
    }

    public long part2(String input) {
        return 0;
    }

}
