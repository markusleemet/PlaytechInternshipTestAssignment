package cs.ut;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class Puzzle15 {
    List<List<Integer>> puzzleGameBoard;
    private Map<String, Integer> emptyTilePosition;

    public Puzzle15(List<List<Integer>> puzzleGameBoard) {
        this.puzzleGameBoard = puzzleGameBoard;
    }

    void swapTwoTiles(List<List<Integer>> matrix, SwapTile tileToSwap, Map<String, Integer> emptyTilePosition) {
        Map<String, Integer> emptyTileNewPosition = new HashMap<>();

        switch (tileToSwap) {
            case TOP -> {
                emptyTileNewPosition.put("x", emptyTilePosition.get("x"));
                emptyTileNewPosition.put("y", emptyTilePosition.get("y") - 1);
            }
            case BOTTOM -> {

            }
            case LEFT -> {

            }
            case RIGHT -> {

            }
        }
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


    int solvePuzzle15() {
        int movesCounter = 0;

        // Move tiles 1 and 2 to final position

        return new Random().nextInt(10);
    }
}
