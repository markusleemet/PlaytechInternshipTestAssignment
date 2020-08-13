package cs.ut;

import java.util.*;

public class Puzzle15 {
    List<List<Integer>> puzzleGameBoard;
    private TilePosition emptyTilePosition;
    private final int emptyTile = 0;
    private int movesCounter = 0;

    public Puzzle15(List<List<Integer>> puzzleGameBoard) {
        this.puzzleGameBoard = puzzleGameBoard;

        this.emptyTilePosition = locateEmptyTilePosition(0);
    }

    /**
     * Swap two tiles on game board
     * @param tileToSwap shows in which direction empty tile must be moved
     */
    void swapTwoTiles(SwapTile tileToSwap) {
        TilePosition emptyTileNewPosition = new TilePosition(0, 0);

        switch (tileToSwap) {
            case TOP -> {
                emptyTileNewPosition.xPosition = emptyTilePosition.xPosition;
                emptyTileNewPosition.yPosition = emptyTilePosition.yPosition - 1;
            }
            case BOTTOM -> {
                emptyTileNewPosition.xPosition = emptyTilePosition.xPosition;
                emptyTileNewPosition.yPosition = emptyTilePosition.yPosition + 1;
            }
            case LEFT -> {
                emptyTileNewPosition.xPosition = emptyTilePosition.xPosition - 1;
                emptyTileNewPosition.yPosition = emptyTilePosition.yPosition;
            }
            case RIGHT -> {
                emptyTileNewPosition.xPosition = emptyTilePosition.xPosition + 1;
                emptyTileNewPosition.yPosition = emptyTilePosition.yPosition;
            }
        }
        int emptyTileNewPositionOldValue = puzzleGameBoard.get(emptyTileNewPosition.yPosition).get(emptyTileNewPosition.xPosition);
        puzzleGameBoard.get(emptyTilePosition.yPosition).set(emptyTilePosition.xPosition, emptyTileNewPositionOldValue);
        puzzleGameBoard.get(emptyTileNewPosition.yPosition).set(emptyTileNewPosition.xPosition, emptyTile);

        emptyTilePosition.xPosition = emptyTileNewPosition.xPosition;
        emptyTilePosition.yPosition = emptyTileNewPosition.yPosition;

        // Increase moves counter
        this.movesCounter++;

        printOutCurrentState();
    }


    /**
     * Locate specified tile on the game board
     * @param tileToLocate
     * @return tileToLocate position on game board
     */
    TilePosition locateEmptyTilePosition(int tileToLocate) {
        int tileXPosition = 0;
        int tileYPosition = 0;

        for (List<Integer> matrixRow : puzzleGameBoard) {
            if (matrixRow.contains(tileToLocate)) {
                tileYPosition = puzzleGameBoard.indexOf(matrixRow);
                for (Integer tile : matrixRow) {
                    if (tile == tileToLocate) {
                        tileXPosition = matrixRow.indexOf(tile);
                    }
                }
            }
        }

        return new TilePosition(tileXPosition, tileYPosition);
    }


    /**
     * Print out puzzle current state to command line
     */
    void printOutCurrentState() {
        System.out.println("--------------------------------");
        System.out.println("Moves counter: " + this.movesCounter);
        System.out.println("Empty tile position: " + this.emptyTilePosition);
        for (List<Integer> matrixRow : puzzleGameBoard) {
            System.out.println(matrixRow);
        }
        System.out.println("--------------------------------");
    }




    void moveTileToPosition(int tileToMove, TilePosition endPosition){
        TilePosition tileCurrentPosition = locateEmptyTilePosition(tileToMove);

    }


    List<TilePosition> calculatePathForTile(TilePosition currentPosition, TilePosition endPosition) {
        List<TilePosition> pathForTile = new ArrayList<>();



        while (currentPosition.yPosition != endPosition.yPosition) {
            TilePosition step = new TilePosition(currentPosition.xPosition, currentPosition.yPosition - 1);
            pathForTile.add(step);
            currentPosition = step;
        }

        while (currentPosition.xPosition != endPosition.xPosition) {
            TilePosition step = new TilePosition(0, currentPosition.yPosition);

            if (currentPosition.xPosition > endPosition.xPosition) {
                step.xPosition = currentPosition.xPosition + 1;
            }else {
                step.xPosition = currentPosition.xPosition - 1;
            }
            pathForTile.add(step);
            currentPosition = step;
        }

        return pathForTile;
    }



    /**
     * Solve puzzle
     * @return number of turns in which puzzle was solved
     */
    int solvePuzzle15() {
        // Move tile 1 to final position
        TilePosition tile1endPosition = new TilePosition(0, 0);

        moveTileToPosition(1, tile1endPosition);


        printOutCurrentState();

        swapTwoTiles(SwapTile.TOP);
        swapTwoTiles(SwapTile.LEFT);
        swapTwoTiles(SwapTile.LEFT);
        swapTwoTiles(SwapTile.LEFT);
        swapTwoTiles(SwapTile.BOTTOM);

        return new Random().nextInt(10);
    }
}
