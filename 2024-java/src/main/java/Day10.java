import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class Day10 extends Day {

    public Day10(int day) {
        super(day);
    }

    public static void main(String[] args) {
        var day = new Day10(10);
        day.runPart1();
        day.runPart2();
    }

    public long part1(String input) {
        var grid  = asMatrix(input);
        long totalTrailScore = 0;
        for (int y = 0; y < grid.length; y++) {
            for (int x = 0; x < grid[y].length; x++) {
                var currentVal = grid[y][x];
                var currentPos = Position.of(x,y);
                if (Character.getNumericValue(currentVal) ==0){
                   List<Position> trailEndingPos =  traverseDistinctEndings(grid, currentPos, 0, List.of());
                   long distinctTrailScore = trailEndingPos.stream().distinct().count();
                   totalTrailScore+=distinctTrailScore;
                }
            }
        }
        return totalTrailScore;
    }

    public List<Position> traverseDistinctEndings(char[][] grid, Position position, int val, List<Position> trailEndings) {

        if (position.inGrid(grid)) {
            var currentVal = Character.getNumericValue(grid[position.y()][position.x()]);

            if (currentVal == val) {
                if (currentVal==9){ // Found trail end
                    return Stream.concat(trailEndings.stream(), Stream.of(position)).toList();
                }
                else {
                    List<Position> trailsSouth =   traverseDistinctEndings(grid, position.nextPosition(Direction.SOUTH), currentVal + 1, trailEndings);
                    List<Position>  trailsNorth=  traverseDistinctEndings(grid, position.nextPosition(Direction.NORTH), currentVal + 1, trailEndings) ;
                    List<Position>  trailsEast = traverseDistinctEndings(grid, position.nextPosition(Direction.EAST), currentVal + 1, trailEndings);
                    List<Position>  trailsWest = traverseDistinctEndings(grid, position.nextPosition(Direction.WEST), currentVal + 1, trailEndings);

                    ArrayList<Position> updatedList = new ArrayList<>(trailsSouth);
                    updatedList.addAll(trailsNorth);
                    updatedList.addAll(trailsEast);
                    updatedList.addAll(trailsWest);
                    return  updatedList;

                }

            }// else System.out.println("This path is too steep");
        }
         return trailEndings;
    }

    public long part2(String input) {
        var grid  = asMatrix(input);

        long totalTrailScore = 0;
        for (int y = 0; y < grid.length; y++) {
            for (int x = 0; x < grid[y].length; x++) {
                var currentVal = grid[y][x];
                var currentPos = Position.of(x,y);
                if (Character.getNumericValue(currentVal) ==0){
                    List<Position> trailEndingPos =  traverseDistinctEndings(grid, currentPos, 0, List.of());
                    totalTrailScore+=trailEndingPos.size();
                }
            }
        }
        return totalTrailScore;
    }

}
