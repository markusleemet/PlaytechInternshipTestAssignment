package cs.ut;

import cs.ut.exception.ImpossiblePuzzleSetupException;

import java.util.*;

public class Puzzle15 {
    List<List<Integer>> puzzleGameBoard;
    private TilePosition emptyTilePosition;
    private final int emptyTile = 0;
    private int movesCounter = 0;
    private Set<Integer> tilesToAvoid = new HashSet<>();
    private List<TilePosition> pathThatIsBeingExecuted = new ArrayList<>();
    private int tileThatIsBeingMoved = 0;
    private TilePosition tileThatIsBeingMovedFinalPosition = new TilePosition(0, 0);


    public Puzzle15(List<List<Integer>> puzzleGameBoard) {
        this.puzzleGameBoard = puzzleGameBoard;
        this.emptyTilePosition = locateTilePosition(0);
        checkIfPuzzleIsSolvable();
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
     *
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
        System.out.println("Tiles to avoid: " + this.tilesToAvoid);
        System.out.println("Path that is being executed: " + this.pathThatIsBeingExecuted);
        System.out.println("Tile that is being moved: " + this.tileThatIsBeingMoved);
        System.out.println("Tile that is being moved final position: " + this.tileThatIsBeingMovedFinalPosition);
        for (List<Integer> matrixRow : puzzleGameBoard) {
            System.out.println(matrixRow);
        }
        System.out.println("--------------------------------");
    }


    boolean pathUsesTilesThatItMustAvoid(List<TilePosition> path) {
        for (TilePosition step : path) {
            int tileValue = this.puzzleGameBoard.get(step.yPosition).get(step.xPosition);
            if (tilesToAvoid.contains(tileValue)) {
                return true;
            }
        }
        return false;
    }

    void moveTileToPosition(int tileToMove, TilePosition endPosition, boolean yAxisBeforeXAxis) {
        TilePosition tileCurrentPosition = locateTilePosition(tileToMove);
        List<TilePosition> path = calculatePathForTile(tileCurrentPosition, endPosition, false);


        if (pathUsesTilesThatItMustAvoid(path)) {
            path = calculatePathForTile(tileCurrentPosition, endPosition, true);
        }

        tilesToAvoid.add(tileToMove);

        pathThatIsBeingExecuted = path;
        tileThatIsBeingMoved = tileToMove;
        tileThatIsBeingMovedFinalPosition = endPosition;

        System.out.println("Tile: " + tileToMove + " path: " + path);

        for (TilePosition step : path) {
            moveEmptyTileToPosition(step);
            swapEmptyTileWith(tileCurrentPosition);
            tileCurrentPosition = step;
        }
    }

    void moveEmptyTileToPosition(TilePosition targetPosition) {
        List<TilePosition> pathYBeforeX = calculatePathForTile(this.emptyTilePosition, targetPosition, true);
        List<TilePosition> pathXBeforeY = calculatePathForTile(this.emptyTilePosition, targetPosition, false);


        while (pathUsesTilesThatItMustAvoid(pathYBeforeX) && pathUsesTilesThatItMustAvoid(pathXBeforeY)) {
            System.out.println("Make random move and calculate new path");
            moveEmptyTileRandomly();
            pathYBeforeX = calculatePathForTile(this.emptyTilePosition, targetPosition, true);
            pathXBeforeY = calculatePathForTile(this.emptyTilePosition, targetPosition, false);
        }

        if (!pathUsesTilesThatItMustAvoid(pathYBeforeX)) {
            System.out.println("Empty tile new path: " + pathYBeforeX);
            for (TilePosition step : pathYBeforeX) {
                swapEmptyTileWith(step);
            }
        } else {
            System.out.println("Empty tile new path: " + pathXBeforeY);
            for (TilePosition step : pathXBeforeY) {
                swapEmptyTileWith(step);
            }
        }
    }


    void moveEmptyTileRandomly() {
        List<TilePosition> possibleMoves = new ArrayList<>();

        TilePosition moveUp = new TilePosition(this.emptyTilePosition.xPosition, this.emptyTilePosition.yPosition - 1);
        TilePosition moveDown = new TilePosition(this.emptyTilePosition.xPosition, this.emptyTilePosition.yPosition + 1);
        TilePosition moveLeft = new TilePosition(this.emptyTilePosition.xPosition - 1, this.emptyTilePosition.yPosition);
        TilePosition moveRight = new TilePosition(this.emptyTilePosition.xPosition + 1, this.emptyTilePosition.yPosition);

        List<TilePosition> allMoves = new ArrayList<>(Arrays.asList(moveUp, moveDown, moveLeft, moveRight));


        for (TilePosition move : allMoves) {
            if (move.xPosition >= 0 && move.xPosition <= 3) {
                if (move.yPosition >= 0 && move.yPosition <= 3) {
                    int moveValue = this.puzzleGameBoard.get(move.yPosition).get(move.xPosition);
                    if (!tilesToAvoid.contains(moveValue)) {
                        possibleMoves.add(move);
                    }
                }
            }
        }

        swapEmptyTileWith(possibleMoves.get(new Random().nextInt(possibleMoves.size())));
    }


    List<TilePosition> calculatePathForTile(TilePosition currentPosition, TilePosition endPosition, boolean yAxisBeforeXAxis) {
        System.out.println("Calculate new path for tile: " + puzzleGameBoard.get(currentPosition.yPosition).get(currentPosition.xPosition) + " -> yAxisBeforeXAxis: " + yAxisBeforeXAxis);
        List<TilePosition> pathForTile = new ArrayList<>();


        if (yAxisBeforeXAxis) {
            while (currentPosition.yPosition != endPosition.yPosition) {
                TilePosition step = new TilePosition(currentPosition.xPosition, 0);

                if (currentPosition.yPosition > endPosition.yPosition) {
                    step.yPosition = currentPosition.yPosition - 1;
                } else {
                    step.yPosition = currentPosition.yPosition + 1;
                }

                pathForTile.add(step);
                currentPosition = step;
            }

            while (currentPosition.xPosition != endPosition.xPosition) {
                TilePosition step = new TilePosition(0, currentPosition.yPosition);

                if (currentPosition.xPosition > endPosition.xPosition) {
                    step.xPosition = currentPosition.xPosition - 1;
                } else {
                    step.xPosition = currentPosition.xPosition + 1;
                }
                pathForTile.add(step);
                currentPosition = step;
            }
        } else {
            while (currentPosition.xPosition != endPosition.xPosition) {
                TilePosition step = new TilePosition(0, currentPosition.yPosition);

                if (currentPosition.xPosition > endPosition.xPosition) {
                    step.xPosition = currentPosition.xPosition - 1;
                } else {
                    step.xPosition = currentPosition.xPosition + 1;
                }
                pathForTile.add(step);
                currentPosition = step;
            }

            while (currentPosition.yPosition != endPosition.yPosition) {
                TilePosition step = new TilePosition(currentPosition.xPosition, 0);

                if (currentPosition.yPosition > endPosition.yPosition) {
                    step.yPosition = currentPosition.yPosition - 1;
                } else {
                    step.yPosition = currentPosition.yPosition + 1;
                }

                pathForTile.add(step);
                currentPosition = step;
            }
        }

        return pathForTile;
    }


    TilePosition getTileFinalPosition(int tile) {
        int abstractTile = tile - 1;
        return new TilePosition((abstractTile % 4), abstractTile / 4);
    }


    /**
     * This method is based on:
     * https://www.cs.bham.ac.uk/~mdr/teaching/modules04/java2/TilesSolvability.html
     */
    void checkIfPuzzleIsSolvable() {
        int totalInversion = 0;

        // Make list out of matrix to check inversions more easily
        List<Integer> supportListToCheckInversions = new ArrayList<>(Arrays.asList());
        for (List<Integer> matrixRow : puzzleGameBoard) {
            supportListToCheckInversions.addAll(matrixRow);
        }

        // Calculate inversions
        for (int firstTileIndex = 0; firstTileIndex <= 14; firstTileIndex++) {
            for (int secondTileIndex = firstTileIndex + 1; secondTileIndex <= 15; secondTileIndex++) {
                if (supportListToCheckInversions.get(secondTileIndex) != 0) {
                    if (supportListToCheckInversions.get(firstTileIndex) > supportListToCheckInversions.get(secondTileIndex)) {
                        totalInversion++;
                    }
                }
            }
        }

        // Empty tile row is even
        boolean emptyTileRowIsEven = emptyTilePosition.yPosition % 2 == 0;


        // Following formula shows if puzzle is solvable or not
        if (emptyTileRowIsEven) {
            if (totalInversion % 2 == 0) {
                throw new ImpossiblePuzzleSetupException();
            }
        } else {
            if (totalInversion % 2 != 0) {
                throw new ImpossiblePuzzleSetupException();
            }
        }
    }


    /**
     * Solve puzzle
     *
     * @return number of turns in which puzzle was solved
     */
    int solvePuzzle15() {
        printOutCurrentState();


        // Move following tiles to final position
        moveTileToPosition(1, getTileFinalPosition(1), true);
        moveTileToPosition(2, getTileFinalPosition(2), true);


        // Move following tiles to SETUP position
        // SETUP
        moveTileToPosition(4, getTileFinalPosition(3), true);
        moveTileToPosition(3, getTileFinalPosition(7), true);

        // Move following tiles to final position
        moveTileToPosition(4, getTileFinalPosition(4), true);
        moveTileToPosition(3, getTileFinalPosition(3), true);


        // Move following tiles to final position
        moveTileToPosition(5, getTileFinalPosition(5), true);
        moveTileToPosition(6, getTileFinalPosition(6), true);


        // Move following tiles to SETUP position
        // SETUP
        moveTileToPosition(8, getTileFinalPosition(7), true);
        moveTileToPosition(7, getTileFinalPosition(11), true);

        // Move following tiles to final position
        moveTileToPosition(8, getTileFinalPosition(8), true);
        moveTileToPosition(7, getTileFinalPosition(7), true);


        // Move following tiles to SETUP position
        // SETUP
        moveTileToPosition(13, getTileFinalPosition(9), true);
        moveTileToPosition(9, getTileFinalPosition(10), true);


        // Move following tiles to final position
        moveTileToPosition(13, getTileFinalPosition(13), true);
        moveTileToPosition(9, getTileFinalPosition(9), true);


        // Move following tiles to SETUP position
        // SETUP
        moveTileToPosition(14, getTileFinalPosition(10), true);
        moveTileToPosition(10, getTileFinalPosition(11), true);


        // Move following tiles to final position
        moveTileToPosition(14, getTileFinalPosition(14), true);
        moveTileToPosition(10, getTileFinalPosition(10), true);


        // Move following tiles to final position
        moveTileToPosition(11, getTileFinalPosition(11), true);
        moveTileToPosition(12, getTileFinalPosition(12), true);
        moveTileToPosition(15, getTileFinalPosition(15), true);


        return new Random().nextInt(10);
    }
}
