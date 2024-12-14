import java.util.Arrays;
import java.util.LinkedList;

public class Day9 extends Day {

    public Day9(int day) {
        super(day);
    }

    public static void main(String[] args) {
        var day = new Day9(9);
        day.runPart1();
        day.runPart2();
    }

    public long part1(String input) {
        LinkedList<String[]> sb = mapArray(input);
        LinkedList<Position> freeSpacePositions = new LinkedList<>();
        for (int y = 0; y < sb.size(); y++) {
            var arrayAtIndex = sb.get(y);
            for (int x = 0; x < arrayAtIndex.length; x++) {
                var itemAtArrayIndex = arrayAtIndex[x];
                if (itemAtArrayIndex.equals(".")){
                    freeSpacePositions.add(Position.of(x, y));
                }
            }
        }

        int updatesToPerform = freeSpacePositions.size();

        while (updatesToPerform>0){
            // reverse through linked list of arrays
            for (int i = sb.size()-1; i >=0 ; i--) {
                for (int j = sb.get(i).length-1; j >=0 ; j--) {
                    if (updatesToPerform==0){
                        break;
                    }
                    var valAt = sb.get(i)[j];
                    if (!valAt.equals(".")) {
                        Position firstPosition = freeSpacePositions.poll();
                        sb.get(i)[j] = ".";
                        sb.get(firstPosition.y())[firstPosition.x()] = valAt;
                    }
                    else sb.get(i)[j] = ".";
                    updatesToPerform--;
                }
            }

        }

        int count = 0;
        int sum = 0;
        for(String[] line: sb){
            for(String val : line){
                if (val.equals(".")){
                    break;
                }
                sum+=count *  Integer.parseInt(val);;
                count++;
            }
        }

        return sum;
    }


    private static LinkedList<String[]> mapArray(String input) {
        boolean file = true;
        int fileCount = 0;
        LinkedList<String[]> linkedList = new LinkedList<>();

        for(char c: input.toCharArray()){
            String[] stringArray = new String[Character.getNumericValue(c)];
            if (file){
                Arrays.fill(stringArray, String.valueOf(fileCount));
                fileCount++;
            }
            else Arrays.fill(stringArray, ".");

            file=!file;
            linkedList.add(stringArray);
        }
        return linkedList;
    }

    public long part2(String input) {
            return 0;
        }

}

