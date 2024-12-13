import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class Day12 extends Day {

    public Day12(int day) {
        super(day);
    }

    public static void main(String[] args) {
        var day = new Day12(12);
        day.runPart1();
        day.runPart2();
    }

    public long part1(String input) {
        var grid = asMatrix(input);

        HashSet<Position> visited = new HashSet<>();
        long totalCost = 0;
        for (int y = 0; y < grid.length; y++) {
            for (int x = 0; x < grid[0].length; x++) {
                var val = grid[y][x];
                if (!visited.contains(Position.of(x, y))){
                    List<Position> positions = search(Position.of(x, y), grid, val, new ArrayList<>(), visited);
                    long perimeter = calculatePerimeter(positions);
                    totalCost += perimeter * positions.size();
                }
            }
        }

        return totalCost;
    }

    public long calculatePerimeter(List<Position> region){
        AtomicInteger perimeter = new AtomicInteger();
        for (Position position: region){
            region.stream().filter(p -> p.equals(position.nextPosition(Direction.NORTH))).findFirst().ifPresentOrElse(p -> {}, perimeter::incrementAndGet);
            region.stream().filter(p -> p.equals(position.nextPosition(Direction.EAST))).findFirst().ifPresentOrElse(p -> {}, perimeter::incrementAndGet);
            region.stream().filter(p -> p.equals(position.nextPosition(Direction.SOUTH))).findFirst().ifPresentOrElse(p -> {}, perimeter::incrementAndGet);
            region.stream().filter(p -> p.equals(position.nextPosition(Direction.WEST))).findFirst().ifPresentOrElse(p -> {}, perimeter::incrementAndGet);
        }

        return perimeter.get();
    }

    public List<Position> search(Position position, char[][] grid, char target, ArrayList<Position> region, HashSet<Position> visited){
        if (!position.inGrid(grid)){
            return region;
        }
        if (visited.contains(position)){
            return region;
        }
        if (region.contains(position)){
            return region;
        }
        if (grid[position.y()][position.x()] != target){
            return region;
        }
        visited.add(position);
        region.add(position);

        search(position.nextPosition(Direction.NORTH), grid, target, region, visited);
        search(position.nextPosition(Direction.EAST), grid, target, region, visited);
        search(position.nextPosition(Direction.SOUTH), grid, target, region, visited);
        search(position.nextPosition(Direction.WEST), grid, target, region, visited);
        return region;
    }

    public long part2(String input) {
        return 0;
    }

}
