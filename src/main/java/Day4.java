public class Day4 extends Day {
    private final static String WORD = "XMAS";

    private final static String SEARCH_TERM_1 = """
            M.S
            .A.
            M.S
            """;

    private final static String SEARCH_TERM_2 = """
            S.M
            .A.
            S.M
            """;

    private final static String SEARCH_TERM_3 = """
            S.S
            .A.
            M.M
            """;

    private final static String SEARCH_TERM_4 = """
            M.M
            .A.
            S.S
            """;

    public Day4(int day) {
        super(day);
    }

    public static void main(String[] args) {
        var day = new Day4(4);
        day.runPart1();
        day.runPart2();
    }

    public String part1(String input) {
        char[][] matrix = asMatrix(input);
        int matches = 0;
        for (int y = 0; y < matrix.length; y++) {
            for (int x = 0; x < matrix[y].length; x++) {
                if (Character.toString(matrix[y][x]).equals("X")) {

                    for (Direction direction : Direction.values()) {
                        var nextCoordinates = findNextCoordinates(direction, y, x);
                        boolean foundWord = search(matrix, nextCoordinates.y, nextCoordinates.x, 1, direction);
                        if (foundWord) {
                            matches++;
                        }
                    }
                }
            }
        }
        return String.valueOf(matches);
    }

    public String part2(String input) {
        var searchMatrix = asMatrix(SEARCH_TERM_1);
        var searchMatrix2 = asMatrix(SEARCH_TERM_2);
        var searchMatrix3 = asMatrix(SEARCH_TERM_3);
        var searchMatrix4 = asMatrix(SEARCH_TERM_4);

        var wordSearch = asMatrix(input);

        int allMatches = findMatches(wordSearch, searchMatrix)
                + findMatches(wordSearch, searchMatrix2)
                + findMatches(wordSearch, searchMatrix3)
                + findMatches(wordSearch, searchMatrix4);

        return String.valueOf(allMatches);
    }

    private boolean search(char[][] wordSearch, int yPos, int xPos, int wordPos, Direction searchDirection) {
        if (wordPos == WORD.length()) {
            return true;
        }

        if (yPos < 0 || xPos < 0 || yPos > wordSearch.length - 1 || xPos > wordSearch[0].length - 1) {
            return false;
        }
        int nextWordPos = wordPos + 1;
        char searchChar = WORD.charAt(nextWordPos);
        char currentChar = wordSearch[yPos][xPos];
        if (currentChar == searchChar) {
            var nextCoordinates = findNextCoordinates(searchDirection, yPos, xPos);
            return search(wordSearch, nextCoordinates.y, nextCoordinates.x, nextWordPos, searchDirection);
        }
        return false;
    }

    private Tuple<Integer, Integer> findNextCoordinates(Direction searchDirection, int yPos, int xPos) {
        return switch (searchDirection) {
            case NORTH -> new Tuple<>(xPos, yPos - 1);
            case EAST -> new Tuple<>(xPos - 1, yPos);
            case SOUTH -> new Tuple<>(xPos, yPos + 1);
            case WEST -> new Tuple<>(xPos + 1, yPos);
            case NORTHEAST -> new Tuple<>(xPos - 1, yPos - 1);
            case NORTHWEST -> new Tuple<>(xPos + 1, yPos - 1);
            case SOUTHEAST -> new Tuple<>(xPos - 1, yPos + 1);
            case SOUTHWEST -> new Tuple<>(xPos + 1, yPos + 1);
        };
    }


    private int findMatches(char[][] wordSearch, char[][] searchMatrix) {
        int matches = 0;
        for (int y = 0; y < wordSearch.length; y++) {
            for (int x = 0; x < wordSearch[y].length; x++) {
                if (wordSearch[y][x] == searchMatrix[0][0]) {
                    boolean foundMatrix = search(wordSearch, searchMatrix, new Tuple<>(x + 1, y), new Tuple<>(1, 0));
                    if (foundMatrix) {
                        matches++;
                    }
                }
            }
        }
        return matches;
    }

    private Tuple<Integer, Integer> matrixNextPos(char[][] matrix, Tuple<Integer, Integer> currentPos) {
        if (currentPos.x < matrix[0].length - 1) {
            return new Tuple<>(currentPos.x + 1, currentPos.y);
        } else {
            return new Tuple<>(0, currentPos.y + 1);
        }
    }

    private boolean search(char[][] wordSearch, char[][] searchMatrix, Tuple<Integer, Integer> wordSearchPos, Tuple<Integer, Integer> searchMatrixPos) {
        if (searchMatrixPos.y >= searchMatrix.length) {
            System.out.println("Matrix found");
            return true;
        }

        if (wordSearchPos.y >= wordSearch.length || wordSearchPos.x >= wordSearch[0].length) {
            System.out.println("Out of bounds");
            return false;
        }

        char searchChar = searchMatrix[searchMatrixPos.y][searchMatrixPos.x];
        char currentChar = wordSearch[wordSearchPos.y][wordSearchPos.x];

        if (searchChar == currentChar || searchChar == '.') {
            var searchMatrixNextPos = matrixNextPos(searchMatrix, searchMatrixPos);
            var wordSearchNextPos = new Tuple<>(wordSearchPos.x + (searchMatrixNextPos.x - searchMatrixPos.x), wordSearchPos.y + (searchMatrixNextPos.y - searchMatrixPos.y));
            return search(wordSearch, searchMatrix, wordSearchNextPos, searchMatrixNextPos);
        }

        return false;
    }
}
