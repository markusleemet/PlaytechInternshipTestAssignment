package cs.ut;

import cs.ut.exception.ImpossiblePuzzleSetupException;
import cs.ut.exception.InvalidPuzzleInput;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {

    /**
     * Create matrix from file content or throw error in case of incorrect input
     *
     * @param file     which content is used to create matrix
     * @param fileName that is used in case of error
     * @return List<List < Integer>> (matrix)
     * @throws Exception
     */
    static List<List<Integer>> createMatrixFromFile(File file, String fileName) throws FileNotFoundException, InvalidPuzzleInput {

        // Create empty matrix that will be populated with numbers later on
        List<List<Integer>> matrix = new ArrayList<>();

        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                String nextLine = scanner.nextLine();
                String stripedNextLine = nextLine.strip();
                String[] numbers = stripedNextLine.split("\\s+");

                // Create row for matrix
                List<Integer> matrixRow = new ArrayList<>();


                // Add numbers into matrix row unless there is invalid input
                for (String stringRepresentationOfNumber : numbers) {
                    try {
                        int actualNumber = Integer.parseInt(stringRepresentationOfNumber);
                        matrixRow.add(actualNumber);
                    } catch (NumberFormatException exception) {
                        throw new InvalidPuzzleInput(fileName);
                    }
                }

                // Add created row into matrix
                matrix.add(matrixRow);
            }
        } catch (FileNotFoundException fileNotFoundException) {
            throw new FileNotFoundException(fileName);
        }
        return matrix;
    }


    /**
     * There are 2 things that need checking:
     * Each number can occur only once
     * Each number must be greater or equivalent to 0 and lesser or equivalent to 15
     *
     * @param matrix that is checked for those criteria
     * @return if matrix meets these criteria or not
     */
    static boolean matrixContentMeetsCriteria(List<List<Integer>> matrix, String fileName) throws InvalidPuzzleInput {
        List<Integer> alreadyCheckedElements = new ArrayList<>();

        for (List<Integer> matrixRow : matrix) {
            for (Integer matrixElement : matrixRow) {
                if (matrixElement >= 0 && matrixElement <= 15) {
                    if (!alreadyCheckedElements.contains(matrixElement)) {
                        alreadyCheckedElements.add(matrixElement);
                    } else {
                        throw new InvalidPuzzleInput(fileName);
                    }
                } else {
                    throw new InvalidPuzzleInput(fileName);
                }
            }
        }

        return true;
    }


    public static void main(String[] args) {

        // Check if command line argument is provided
        try {
            try (Stream<Path> paths = Files.walk(Paths.get(args[0]))) {
                List<String> filesPathsInProvidedDirectory = paths.filter(path -> path.getFileName().toString().endsWith(".p15")).map(path -> path.toString()).collect(Collectors.toList());


                for (String path : filesPathsInProvidedDirectory) {
                    File file = new File(path);

                    // File name for command line outputs
                    String fileName = file.getName();


                    try {
                        List<List<Integer>> matrix = createMatrixFromFile(file, fileName);
                        matrixContentMeetsCriteria(matrix, fileName);
                        Puzzle15 puzzle = new Puzzle15(matrix);
                        System.out.println(fileName + " - " + puzzle.solvePuzzle15());
                    } catch (InvalidPuzzleInput invalidPuzzleInput) {
                        System.err.println(invalidPuzzleInput.getMessage() + " - " + "-2");
                    } catch (IOException ioException) {
                        System.err.println(ioException.getMessage() + " - " + "-3");
                    } catch (ImpossiblePuzzleSetupException impossiblePuzzleSetupException) {
                        System.err.println(impossiblePuzzleSetupException.getMessage() + " - " + "-1");
                    }
                }

            } catch (IOException exception) {
                System.err.println("Exception on reading files from provided directory");
            }
        } catch (IndexOutOfBoundsException exception) {
            System.err.println("Path for directory was not provided.");
        }
    }
}
