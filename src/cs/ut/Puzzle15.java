package cs.ut;

import cs.ut.exceptions.ImpossiblePuzzleSetupException;

import java.util.*;

public class Puzzle15 {
    List<List<Integer>> puzzleGameBoard;
    private TilePosition emptyTilePosition;
    private int movesCounter = 0;
    private final boolean solveWithAdditionalOutput = false;
    private Set<Integer> tilesAtTheirFinalPosition = new HashSet<>();


    public List<List<Integer>> getPuzzleGameBoard() {
        return puzzleGameBoard;
    }

    /**
     * At the creation of the puzzle instance two actions must be done:
     * 1. Locate empty tile position
     * 2. Check if puzzle is solvable
     *
     * @param puzzleGameBoard
     */
    public Puzzle15(List<List<Integer>> puzzleGameBoard) {
        this.puzzleGameBoard = puzzleGameBoard;
        this.emptyTilePosition = locateTilePosition(0);
        checkIfPuzzleIsSolvable();

        // Add tiles that are already at their final position and don't need moving to class variable
        for (int i = 1; i <= 15; i++) {
            TilePosition tilePosition = locateTilePosition(i);
            TilePosition tileFinalPosition = getTileFinalPosition(i);
            if (tilePosition.equals(tileFinalPosition)) {
                tilesAtTheirFinalPosition.add(i);
            }else{
                break;
            }
        }
    }


    /**
     * Method swaps empty tile with bottom or right tile to find valid path.
     * It doest swap with tiles that are already in their final position.
     */
    void moveEmptyTileRandomly() {
        TilePosition moveDown = new TilePosition(this.emptyTilePosition.xPosition, this.emptyTilePosition.yPosition + 1);
        TilePosition moveRight = new TilePosition(this.emptyTilePosition.xPosition + 1, this.emptyTilePosition.yPosition);
        TilePosition moveLeft = new TilePosition(this.emptyTilePosition.xPosition - 1, this.emptyTilePosition.yPosition);
        TilePosition moveUp = new TilePosition(this.emptyTilePosition.xPosition, this.emptyTilePosition.yPosition - 1);

        List<TilePosition> allMoves = new ArrayList<>(Arrays.asList(moveDown, moveRight, moveLeft, moveUp));

        for (TilePosition move : allMoves) {
            if (move.xPosition >= 0 && move.xPosition <= 3) {
                if (move.yPosition >= 0 && move.yPosition <= 3) {
                    int moveValue = this.puzzleGameBoard.get(move.yPosition).get(move.xPosition);
                    if (!tilesAtTheirFinalPosition.contains(moveValue)) {
                        swapEmptyTileWith(move);
                        return;
                    }
                }
            }
        }

        // If empty tile has no valid moves throw RuntimeException
        throw new RuntimeException();
    }


    /**
     * Method to shuffle game board. Not need for solving puzzle.
     */
    void shuffleGameBoard(){
        tilesAtTheirFinalPosition.clear();

        for (int i = 0; i < 1000; i++) {

            TilePosition moveDown = new TilePosition(this.emptyTilePosition.xPosition, this.emptyTilePosition.yPosition + 1);
            TilePosition moveRight = new TilePosition(this.emptyTilePosition.xPosition + 1, this.emptyTilePosition.yPosition);
            TilePosition moveUp = new TilePosition(this.emptyTilePosition.xPosition, this.emptyTilePosition.yPosition - 1);
            TilePosition moveLeft = new TilePosition(this.emptyTilePosition.xPosition - 1, this.emptyTilePosition.yPosition);

            List<TilePosition> allMoves = new ArrayList<>(Arrays.asList(moveDown, moveUp, moveLeft, moveRight));
            List<TilePosition> validMoves = new ArrayList<>();

            for (TilePosition move : allMoves) {
                if (move.xPosition >= 0 && move.xPosition <= 3) {
                    if (move.yPosition >= 0 && move.yPosition <= 3) {
                        validMoves.add(move);
                    }
                }
            }
            swapEmptyTileWith(validMoves.get(new Random().nextInt(validMoves.size())));
        }
        movesCounter = 0;
    }


    /**
     * Method will swap any given tile with empty tile and increase turn counter by one.
     *
     * @param tileToSwapWith - tiles position that will be swapped wih empty tile. Must be adjacent to empty tile.
     */
    void swapEmptyTileWith(TilePosition tileToSwapWith) {
        int tileToSwapWithValue = puzzleGameBoard.get(tileToSwapWith.yPosition).get(tileToSwapWith.xPosition);

        puzzleGameBoard.get(this.emptyTilePosition.yPosition).set(this.emptyTilePosition.xPosition, tileToSwapWithValue);
        puzzleGameBoard.get(tileToSwapWith.yPosition).set(tileToSwapWith.xPosition, 0);
        this.emptyTilePosition = tileToSwapWith;
        this.movesCounter++;

        if (solveWithAdditionalOutput) {
            printOutCurrentState();
        }
    }


    /**
     * Method moves empty tile to specified position.
     *
     * @param targetPosition position where to move empty tile.
     */
    void moveEmptyTileToPosition(TilePosition targetPosition) {
        List<TilePosition> path = calculatePathForTile(this.emptyTilePosition, targetPosition);

        // If no valid path was generated, move randomly and try again
        while (path == null) {
            moveEmptyTileRandomly();
            path = calculatePathForTile(this.emptyTilePosition, targetPosition);
        }

        // Execute generated path
        for (TilePosition step : path) {
            swapEmptyTileWith(step);
        }
    }


    /**
     * Locate specified tile on the game board.
     *
     * @param tileToLocate value of the tile to be located
     * @return TilePosition instance that points to specified tile
     */
    TilePosition locateTilePosition(int tileToLocate) {
        for (List<Integer> matrixRow : puzzleGameBoard) {
            if (matrixRow.contains(tileToLocate)) {
                int tileYPosition = puzzleGameBoard.indexOf(matrixRow);
                for (Integer tile : matrixRow) {
                    if (tile == tileToLocate) {
                        int tileXPosition = matrixRow.indexOf(tile);
                        return new TilePosition(tileXPosition, tileYPosition);
                    }
                }
            }
        }
        // If tile wasn't found on game board throw runtime exception
        throw new RuntimeException();
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
     * Method checks if specified path uses tiles that it must avoid.
     * Some tiles must be avoided because they are already at their final position.
     *
     * @param path that is checked
     * @return true if path uses tiles that it must avoid
     */
    boolean pathUsesTilesThatItMustAvoid(List<TilePosition> path) {
        for (TilePosition step : path) {
            int tileValue = this.puzzleGameBoard.get(step.yPosition).get(step.xPosition);
            if (tilesAtTheirFinalPosition.contains(tileValue)) {
                return true;
            }
        }
        return false;
    }


    /**
     * Method moves tile to any given position on game board.
     *
     * @param tileToMove  tile to move on game board to specified position.
     * @param endPosition end position for tile to be moved.
     */
    void moveTileToPosition(int tileToMove, TilePosition endPosition) {
        TilePosition tileCurrentPosition = locateTilePosition(tileToMove);
        List<TilePosition> path = calculatePathForTile(tileCurrentPosition, endPosition);

        tilesAtTheirFinalPosition.add(tileToMove);

        for (TilePosition step : path) {
            moveEmptyTileToPosition(step);
            swapEmptyTileWith(tileCurrentPosition);
            tileCurrentPosition = step;
        }
    }


    /**
     * Calculates path between two locations so that the path avoids tiles that are already in their final location.
     *
     * @param startingPosition starting position of the path.
     * @param endPosition      ending position of the path.
     * @return list of TilePosition instances that represent path.
     */
    List<TilePosition> calculatePathForTile(TilePosition startingPosition, TilePosition endPosition) {
        for (boolean booleanValue : Arrays.asList(true, false)) {
            List<TilePosition> path = new ArrayList<>();
            TilePosition currentPosition = startingPosition;

            // Generate steps until current position and final position are equal
            while (currentPosition.xPosition != endPosition.xPosition || currentPosition.yPosition != endPosition.yPosition) {
                currentPosition = getNextStepInPath(currentPosition, endPosition, booleanValue);
                path.add(currentPosition);
            }

            // Check if generated path uses tiles that it must avoid
            // If true, new path is generated
            if (pathUsesTilesThatItMustAvoid(path)) {
                continue;
            }
            return path;
        }
        return null;
    }


    /**
     * This is support method for calculatePathForTile() that returns first step in path between two locations on game board.
     *
     * @param currentPosition  starting position of the path to be generated.
     * @param endPosition      end position of the path to be generated.
     * @param yAxisBeforeXAxis indicates whether to move on y axis before x axis or vice versa.
     * @return first step of the path between two positions as TilePosition instance.
     */
    TilePosition getNextStepInPath(TilePosition currentPosition, TilePosition endPosition, boolean yAxisBeforeXAxis) {
        if (yAxisBeforeXAxis) {
            if (currentPosition.yPosition != endPosition.yPosition) {
                int newYPosition = (currentPosition.yPosition - endPosition.yPosition) > 0 ? currentPosition.yPosition - 1 : currentPosition.yPosition + 1;
                return new TilePosition(currentPosition.xPosition, newYPosition);
            } else {
                int newXPosition = (currentPosition.xPosition - endPosition.xPosition) > 0 ? currentPosition.xPosition - 1 : currentPosition.xPosition + 1;
                return new TilePosition(newXPosition, currentPosition.yPosition);
            }
        } else {
            if (currentPosition.xPosition != endPosition.xPosition) {
                int newXPosition = (currentPosition.xPosition - endPosition.xPosition) > 0 ? currentPosition.xPosition - 1 : currentPosition.xPosition + 1;
                return new TilePosition(newXPosition, currentPosition.yPosition);
            } else {
                int newYPosition = (currentPosition.yPosition - endPosition.yPosition) > 0 ? currentPosition.yPosition - 1 : currentPosition.yPosition + 1;
                return new TilePosition(currentPosition.xPosition, newYPosition);
            }
        }
    }


    /**
     * This method returns specified tile position in solved puzzle
     *
     * @param tile its position in solved puzzle will be returned
     * @return TileLocation instance
     */
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
        List<Integer> supportListToCheckInversions = new ArrayList<>();
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
     * This method solves puzzle based on following algorithm:
     * https://www.instructables.com/id/How-To-Solve-The-15-Puzzle/
     *
     * @return number of turns it took to solve the puzzle
     */
    int solvePuzzle15() {
        // Move following tiles to final position if not there yet
        if (!tilesAtTheirFinalPosition.contains(1) || !tilesAtTheirFinalPosition.contains(2)) {
            moveTileToPosition(1, getTileFinalPosition(1));
            moveTileToPosition(2, getTileFinalPosition(2));
        }

        // Move following tiles to SETUP position and then to FINAL position if not there yet
        if (!tilesAtTheirFinalPosition.contains(3) || !tilesAtTheirFinalPosition.contains(4)) {
            moveTileToPosition(3, getTileFinalPosition(5));
            this.tilesAtTheirFinalPosition.remove(3);
            moveTileToPosition(4, getTileFinalPosition(3));
            moveTileToPosition(3, getTileFinalPosition(7));
            moveTileToPosition(4, getTileFinalPosition(4));
            moveTileToPosition(3, getTileFinalPosition(3));
        }

        // Move following tiles to final position if not there yet
        if (!tilesAtTheirFinalPosition.contains(5) || !tilesAtTheirFinalPosition.contains(6)) {
            moveTileToPosition(5, getTileFinalPosition(5));
            moveTileToPosition(6, getTileFinalPosition(6));
        }

        // Move following tiles to SETUP position and then to FINAL position if not there yet
        if (!tilesAtTheirFinalPosition.contains(7) || !tilesAtTheirFinalPosition.contains(8)) {
            moveTileToPosition(7, getTileFinalPosition(9));
            this.tilesAtTheirFinalPosition.remove(7);
            moveTileToPosition(8, getTileFinalPosition(7));
            moveTileToPosition(7, getTileFinalPosition(11));
            moveTileToPosition(8, getTileFinalPosition(8));
            moveTileToPosition(7, getTileFinalPosition(7));
        }

        // Move following tiles to SETUP position and then to FINAL position if not there yet
        if (!tilesAtTheirFinalPosition.contains(9) || !tilesAtTheirFinalPosition.contains(13)) {
            moveTileToPosition(9, getTileFinalPosition(12));
            this.tilesAtTheirFinalPosition.remove(9);
            moveTileToPosition(13, getTileFinalPosition(9));
            moveTileToPosition(9, getTileFinalPosition(10));
            moveTileToPosition(13, getTileFinalPosition(13));
            moveTileToPosition(9, getTileFinalPosition(9));
        }

        // Move following tiles to SETUP position and then to FINAL position if not there yet
        if (!tilesAtTheirFinalPosition.contains(10) || !tilesAtTheirFinalPosition.contains(14)) {
            moveTileToPosition(10, getTileFinalPosition(12));
            this.tilesAtTheirFinalPosition.remove(10);
            moveTileToPosition(14, getTileFinalPosition(10));
            moveTileToPosition(10, getTileFinalPosition(11));
            moveTileToPosition(14, getTileFinalPosition(14));
            moveTileToPosition(10, getTileFinalPosition(10));
        }

        // Move following tiles to final position if not there yet
        if (!tilesAtTheirFinalPosition.contains(11) || !tilesAtTheirFinalPosition.contains(12) || !tilesAtTheirFinalPosition.contains(15)) {
            moveTileToPosition(11, getTileFinalPosition(11));
            moveTileToPosition(12, getTileFinalPosition(12));
            moveTileToPosition(15, getTileFinalPosition(15));
        }

        return this.movesCounter;
    }
}