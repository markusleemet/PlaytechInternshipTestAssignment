package cs.ut;
import cs.ut.exception.ImpossiblePuzzleSetupException;
import cs.ut.exception.InvalidPuzzleInputException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Puzzle15Test {


    /**
     * Assert that puzzle that can't be solved throws ImpossiblePuzzleSetupException.
     */
    @Test
    void testError1() {
        List<List<Integer>> matrix = new ArrayList<>();
        matrix.add(new ArrayList<>(Arrays.asList(1, 2, 3, 4)));
        matrix.add(new ArrayList<>(Arrays.asList(5, 6, 7, 8)));
        matrix.add(new ArrayList<>(Arrays.asList(9, 10, 11, 0)));
        matrix.add(new ArrayList<>(Arrays.asList(13, 14, 12, 15)));

        Assertions.assertThrows(ImpossiblePuzzleSetupException.class,
                () -> {new Puzzle15(matrix);});
    }


    /**
     * Assert that creating puzzle from file with invalid syntax throws InvalidPuzzleInputException.
     */
    @Test
    void testError2() {
        File fileWithIncorrectSyntax = new File("assignment files/invalidSyntax.p15");

        Assertions.assertThrows(InvalidPuzzleInputException.class,
                () -> {Main.createMatrixFromFile(fileWithIncorrectSyntax);});
    }


    /**
     * Assert that solving puzzle returns positive integer or 0.
     * @throws FileNotFoundException
     */
    @Test
    void testSolvePuzzleMethod() throws FileNotFoundException {
        File easyPuzzle = new File("assignment files/easy.p15");

        List<List<Integer>> matrix = Main.createMatrixFromFile(easyPuzzle);
        Puzzle15 puzzle15 = new Puzzle15(matrix);

        int turnsToSolvePuzzle = puzzle15.solvePuzzle15();

        Assertions.assertTrue(turnsToSolvePuzzle >= 0);
    }


    /**
     * Assert that solving completed puzzle takes 0 turns.
     * @throws FileNotFoundException
     */
    @Test
    void testCompletedPuzzle() throws FileNotFoundException {
        File completedPuzzle = new File("assignment files/completed.p15");

        List<List<Integer>> matrix = Main.createMatrixFromFile(completedPuzzle);
        Puzzle15 puzzle15 = new Puzzle15(matrix);

        int turnsToSolvePuzzle = puzzle15.solvePuzzle15();

        Assertions.assertTrue(turnsToSolvePuzzle == 0);
    }

}
