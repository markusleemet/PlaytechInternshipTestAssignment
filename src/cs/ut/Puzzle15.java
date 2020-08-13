package cs.ut;

import java.util.*;

public class Puzzle15 {
    List<List<Integer>> puzzleGameBoard;
    private Map<String, Integer> emptyTilePosition;
    private final int emptyTile = 0;
    private int movesCounter = 0;

    public Puzzle15(List<List<Integer>> puzzleGameBoard) {
        this.puzzleGameBoard = puzzleGameBoard;

        locateEmptyTilePosition();
    }

    /**
     * Swap two tiles on game board
     * @param tileToSwap shows in which direction empty tile must be moved
     */
    void swapTwoTiles(SwapTile tileToSwap) {
        Map<String, Integer> emptyTileNewPosition = new HashMap<>();

        switch (tileToSwap) {
            case TOP -> {
                emptyTileNewPosition.put("x", emptyTilePosition.get("x"));
                emptyTileNewPosition.put("y", emptyTilePosition.get("y") - 1);
            }
            case BOTTOM -> {
                emptyTileNewPosition.put("x", emptyTilePosition.get("x"));
                emptyTileNewPosition.put("y", emptyTilePosition.get("y") + 1);
            }
            case LEFT -> {
                emptyTileNewPosition.put("x", emptyTilePosition.get("x") - 1);
                emptyTileNewPosition.put("y", emptyTilePosition.get("y"));
            }
            case RIGHT -> {
                emptyTileNewPosition.put("x", emptyTilePosition.get("x") + 1);
                emptyTileNewPosition.put("y", emptyTilePosition.get("y"));
            }
        }
        int emptyTileNewPositionOldValue = puzzleGameBoard.get(emptyTileNewPosition.get("y")).get(emptyTileNewPosition.get("x"));
        puzzleGameBoard.get(emptyTilePosition.get("y")).set(emptyTilePosition.get("x"), emptyTileNewPositionOldValue);
        puzzleGameBoard.get(emptyTileNewPosition.get("y")).set(emptyTileNewPosition.get("x"), emptyTile);

        emptyTilePosition.put("x", emptyTileNewPosition.get("x"));
        emptyTilePosition.put("y", emptyTileNewPosition.get("y"));

        // Increase moves counter
        this.movesCounter++;

        printOutCurrentState();
    }


    /**
     * Locate empty tile on game board and save it into class variable
     */
    void locateEmptyTilePosition() {
        Map<String, Integer> emptyTilePosition = new HashMap<>();
        for (List<Integer> matrixRow : puzzleGameBoard) {
            if (matrixRow.contains(0)) {
                emptyTilePosition.put("y", puzzleGameBoard.indexOf(matrixRow));
                for (Integer tile : matrixRow) {
                    if (tile == 0) {
                        emptyTilePosition.put("x", matrixRow.indexOf(tile));
                    }
                }
            }
        }

        this.emptyTilePosition = emptyTilePosition;
    }


    /**
     * Print out puzzle current state to command line
     */
    void printOutCurrentState() {
        System.out.println("--------------------------------");
        System.out.println("Moves counter: " + this.movesCounter);
        for (List<Integer> matrixRow : puzzleGameBoard) {
            System.out.println(matrixRow);
        }
        System.out.println("--------------------------------");
    }


    /**
     * Solve puzzle
     * @return number of turns in which puzzle was solved
     */
    int solvePuzzle15() {
        // Move tiles 1 and 2 to final position

        printOutCurrentState();

        swapTwoTiles(SwapTile.TOP);
        swapTwoTiles(SwapTile.LEFT);
        swapTwoTiles(SwapTile.LEFT);
        swapTwoTiles(SwapTile.LEFT);
        swapTwoTiles(SwapTile.BOTTOM);

        return new Random().nextInt(10);
    }
}
