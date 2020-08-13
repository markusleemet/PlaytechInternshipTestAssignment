package cs.ut;

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
     * @param file which content is used to create matrix
     * @param fileName that is used in case of error
     * @return List<List<Integer>> (matrix)
     * @throws Exception
     */
    static List<List<Integer>> createMatrixFromFile(File file, String fileName) throws Exception{

        // Create empty matrix that will be populated with numbers later on
        List<List<Integer>> matrix = new ArrayList<>();

        try(Scanner scanner = new Scanner(file)){
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
                        throw new Exception(fileName + "- -2");
                    }
                }

                // Add created row into matrix
                matrix.add(matrixRow);
            }
        }catch (FileNotFoundException exception){
            throw new Exception(fileName + "- -3");
        }
        return matrix;
    }


    /**
     * There are 2 things that need checking:
     * Each number can occur only once
     * Each number must be greater or equivalent to 0 and lesser or equivalent to 15
     * @param matrix that is checked for those criteria
     * @return if matrix meets these criteria or not
     */
    static boolean matrixContentMeetsCriteria(List<List<Integer>> matrix) {
        List<Integer> alreadyCheckedElements = new ArrayList<>();

        for (List<Integer> matrixRow : matrix) {
            for (Integer matrixElement : matrixRow) {
                if (matrixElement >= 0 && matrixElement <= 15) {
                    if (!alreadyCheckedElements.contains(matrixElement)) {
                        alreadyCheckedElements.add(matrixElement);
                    }else {
                        return false;
                    }
                }else{
                    return false;
                }
            }
        }

        return true;
    }



    public static void main(String[] args) throws IOException, Exception {

        // Check if command line argument is provided
        try {
            try (Stream<Path> paths = Files.walk(Paths.get(args[0]))) {
                List<String> filesPathsInProvidedDirectory = paths.filter(path -> path.getFileName().toString().endsWith(".p15")).map(path -> path.toString()).collect(Collectors.toList());



                for (String path : filesPathsInProvidedDirectory) {
                    File file = new File(path);

                    // File name for command line outputs
                    String fileName = file.getName();

                    List<List<Integer>> matrix = createMatrixFromFile(file, fileName);
                    if (matrixContentMeetsCriteria(matrix)) {
                        Puzzle15 puzzle = new Puzzle15(matrix);

                        System.out.println(fileName + puzzle.solvePuzzle15());
                    }else{
                        System.out.println(fileName + "- -2");
                    }
                }
            } catch (IOException exception) {
                throw new Exception("-3");
            }
        } catch (IndexOutOfBoundsException exception) {
            System.out.println("Path for directory was not provided.");
        }
    }
}
