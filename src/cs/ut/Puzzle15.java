package cs.ut;

import java.util.*;

public class Puzzle15 {
    List<List<Integer>> puzzleGameBoard;
    private TilePosition emptyTilePosition;
    private final int emptyTile = 0;
    private int movesCounter = 0;

    public Puzzle15(List<List<Integer>> puzzleGameBoard) {
        this.puzzleGameBoard = puzzleGameBoard;

        this.emptyTilePosition = locateTilePosition(0);
    }



    void swapEmptyTileWith(TilePosition tileToSwapWith) {
        int tileToSwapWithValue = puzzleGameBoard.get(tileToSwapWith.yPosition).get(tileToSwapWith.xPosition);

        puzzleGameBoard.get(this.emptyTilePosition.yPosition).set(this.emptyTilePosition.xPosition, tileToSwapWithValue);
        puzzleGameBoard.get(tileToSwapWith.yPosition).set(tileToSwapWith.xPosition, emptyTile);
        this.emptyTilePosition = tileToSwapWith;
        this.movesCounter++;

        printOutCurrentState();
    }


    /**
     * Locate specified tile on the game board
     * @param tileToLocate
     * @return tileToLocate position on game board
     */
    TilePosition locateTilePosition(int tileToLocate) {
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



    boolean pathUsesTilesThatItMustAvoid(List<TilePosition> path, List<TilePosition> tilesToAvoid) {
        for (TilePosition step : path) {
            if (tilesToAvoid.contains(step)) {
                return true;
            }
        }
        return false;
    }

    void moveTileToPosition(int tileToMove, TilePosition endPosition){
        TilePosition tileCurrentPosition = locateTilePosition(tileToMove);
        List<TilePosition> path = calculatePathForTile(tileCurrentPosition, endPosition, true);


        List<TilePosition> tilesToAvoid = new ArrayList<>();
        tilesToAvoid.add(tileCurrentPosition);
        for (TilePosition step : path) {
            moveEmptyTileToPosition(step, tilesToAvoid);
            swapEmptyTileWith(tileCurrentPosition);
            tileCurrentPosition = step;
        }
        System.out.println(path);
    }

    void moveEmptyTileToPosition(TilePosition targetPosition, List<TilePosition> tilesToAvoid) {
        List<TilePosition> path = calculatePathForTile(this.emptyTilePosition, targetPosition, true);

        if (pathUsesTilesThatItMustAvoid(path, tilesToAvoid)) {
            path = calculatePathForTile(this.emptyTilePosition, targetPosition, false);
        }

        for (TilePosition step : path) {
            swapEmptyTileWith(step);
        }
    }


    List<TilePosition> calculatePathForTile(TilePosition currentPosition, TilePosition endPosition, boolean yAxisBeforeXAxis) {
        List<TilePosition> pathForTile = new ArrayList<>();


        if (yAxisBeforeXAxis) {
            while (currentPosition.yPosition != endPosition.yPosition) {
                TilePosition step = new TilePosition(currentPosition.xPosition, currentPosition.yPosition - 1);
                pathForTile.add(step);
                currentPosition = step;
            }

            while (currentPosition.xPosition != endPosition.xPosition) {
                TilePosition step = new TilePosition(0, currentPosition.yPosition);

                if (currentPosition.xPosition > endPosition.xPosition) {
                    step.xPosition = currentPosition.xPosition - 1;
                }else {
                    step.xPosition = currentPosition.xPosition + 1;
                }
                pathForTile.add(step);
                currentPosition = step;
            }
        }



        System.out.println("Path -> " + pathForTile);
        return pathForTile;
    }



    /**
     * Solve puzzle
     * @return number of turns in which puzzle was solved
     */
    int solvePuzzle15() {
        printOutCurrentState();

        // Move tile 1 to final position
        TilePosition tile1endPosition = new TilePosition(0, 0);

        moveTileToPosition(1, tile1endPosition);




        return new Random().nextInt(10);
    }
}
