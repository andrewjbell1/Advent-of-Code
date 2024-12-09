import java.util.Arrays;
import java.util.LinkedList;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

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

        System.out.println("free spaces:");
        freeSpacePositions.forEach(System.out::println);
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

        AtomicInteger count = new AtomicInteger();
        AtomicLong sum = new AtomicLong();
        sb.forEach(strings -> {
            for (String s: strings){
                if (s.equals(".")){
                    break;
                }
                int addToTotal = count.get() *  Integer.parseInt(s);
                count.incrementAndGet();
                sum.addAndGet(addToTotal);
            }
        });

        return sum.get();
    }


    private static LinkedList<String[]> mapArray(String input) {
        AtomicBoolean file = new AtomicBoolean(true);
        AtomicInteger fileCount = new AtomicInteger();
        LinkedList<String[]> linkedList = new LinkedList<>();

        input.chars()
                .forEach(c -> {
                    String[] stringArray = new String[Character.getNumericValue(c)];
                    if (file.get()){
                        Arrays.fill(stringArray, String.valueOf(fileCount.get()));
                        fileCount.getAndIncrement();
                    }
                    else Arrays.fill(stringArray, ".");

                    file.set(!file.get());
                    linkedList.add(stringArray);
                });
        return linkedList;
    }

    public long part2(String input) {
        LinkedList<String[]> sb = mapArray(input);

        LinkedList<Position> freeSpacePositions = new LinkedList<>();
        for (int y = 0; y < sb.size(); y++) {
            var arrayAtIndex = sb.get(y);
            for (int x = 0; x < arrayAtIndex.length; x++) {
                var itemAtArrayIndex = arrayAtIndex[x];
                if (itemAtArrayIndex.equals(".")) {
                    freeSpacePositions.add(Position.of(x, y));
                }
            }
        }

            // reverse through linked list of arrays
            for (int i = sb.size() - 1; i >= 0; i--) {
                StringBuilder newStringBuilder = new StringBuilder();

                for (int j = 0; j < sb.get(i).length; j++) {
                    var valAt = sb.get(i)[j];
                    if (!valAt.equals(".")) {
                        newStringBuilder.append(valAt);
                    } else {
                        break;
                    }
                }
                var newString = newStringBuilder.toString();
                System.out.println("try: "+newString);

                if (newString.isBlank()){
                    System.out.println("empty, move on");
                }
                else {
                    System.out.println("check free space starting:"+ freeSpacePositions.get(0));

                    boolean beside = true;
                    int startingX= 0;
                    while (beside) {
                        Position pos = freeSpacePositions.get(startingX);
                        Position pos2 = freeSpacePositions.get(startingX + 1);
                        if (isPosBesideXAxis(pos, pos2)) {
                            System.out.println("Space is beside");
                            startingX=pos2.x();
                            beside = isPosBesideXAxis(pos, pos2);
                        }
                        else{
                            beside= false;
                        }
                    }

                    boolean enoughSpace = startingX>newString.length();

                    if (enoughSpace) {
                        System.out.println("enough space: "+newString.length() +" > "+ startingX);
                        for (int j = 0; j < newString.length(); j++) {
                            System.out.println("Adding pos");
                            Position freePos = freeSpacePositions.get(j);
                            sb.get(freePos.y())[freePos.x()] = Character.toString(newString.charAt(j));
                        }
                        Arrays.fill(sb.get(i), ".");
                    }
                    else{
                        System.out.println("not enough space: "+newString.length() +" <= "+ startingX);
                    }

                    for (int m = 0; m < startingX; m++) {
                        System.out.println("removing free space");
                        freeSpacePositions.poll();
                    }

                    if (newString.contains("0")) {
                        System.out.println("tried string with final index");
                        break;
                    }
                    sb.forEach(strings -> System.out.print(Arrays.toString(strings)));
                    System.out.println();
                    System.out.println();

                }
            }

            return 0;
        }

    private boolean isPosBesideXAxis(Position pos1, Position pos2){
        return pos1.y() == pos2.y() && pos2.x()- pos1.x() ==1;
    }

}

