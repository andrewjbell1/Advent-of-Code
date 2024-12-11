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
        //find the biggest odd number
        // Work out how many numbers it divides into after 25 iterations

        //125 17
        //253000 1 7
        //253 0 2024 14168

        String[] values = input.split("\\s+");

        String updatedString = "";

        long total = 0;
        //System.out.println(input);
        //String[] values2 = new String[]{"125"};
        for (String val : values) {
            //System.out.println();
            //System.out.println("Starting blink on val "+val);
            var updatedVal = blinkAtValue(val.trim(), 0, 75, new HashMap<>());
            //// System.out.println("for i: "+ i+" and value: "+val+" updating to: "+ updatedVal );;
            total += updatedVal;
        }

        //updatedString = Arrays.toString(values);
//        updatedString = String.join(",", values);
////        System.out.println(updatedString);


        return total;
    }

    long blinkAtValue(String input, int blinks, int blinksRequired, HashMap<String, String> cache){
        //System.out.println("blink "+ blinks+"  at "+input);

        if (blinks == blinksRequired){
            //System.out.println("blinks complete");
            return input.split(" ").length;
        }

        String [] inputSplit = input.split("\\s+");
        StringBuilder returnVal= new StringBuilder();
        //System.out.println("Splitting");
        for (String s: inputSplit){
            //System.out.println("Look at "+s);
            if (cache.containsKey(s)){
                System.out.println("found cache val");
                return blinkAtValue(cache.get(s), blinks +1, blinksRequired, cache);
            }
            s = s.trim();
            if (s.equals("0")){
                //System.out.println("Found a 0");
                cache.put(s, "1");
                returnVal.append("1 ");
            }
            else if (s.length()%2==0){
                //System.out.println("Found even ");
                var middleIndex = s.length() / 2;
                var leftSplit = s.substring(0, middleIndex);
                var rightSplit = s.substring(middleIndex, s.length());
                leftSplit = leftSplit.replaceFirst("^0+(?!$)", ""); // String.valueOf(Long.parseLong(leftSplit));
                rightSplit = rightSplit.replaceFirst("^0+(?!$)", ""); // String.valueOf(Long.parseLong(rightSplit.startsWith("0") ? "0" : rightSplit));
                var returnV = leftSplit + " "+ rightSplit+" ";
                cache.put(s, returnV);
                returnVal.append(returnV);

            }
            else{
                //System.out.println("not even and not a 0");
                var returnV = Long.parseLong(s) * 2024+" ";
                cache.put(s, returnV);
                returnVal.append(returnV);
            }
            //System.out.println("Updated return val: "+returnVal);
        }
      //  cache.put(input, returnVal.toString().trim());
        return blinkAtValue(returnVal.toString(), blinks +1, blinksRequired, cache);
    }

    public long part2(String input) {
        return 0;
    }

}
