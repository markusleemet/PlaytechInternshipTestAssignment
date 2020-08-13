package cs.ut;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {

    static List<List<Integer>> createMatrixFromFile(File file) throws Exception{
        String fileName = file.getName();

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

                    // FIle name for command line outputs
                    String fileName = file.getName();

                    List<List<Integer>> matrix = createMatrixFromFile(file);
                    if (matrixContentMeetsCriteria(matrix)) {
                        System.out.println(fileName + "random number");
                    }else{
                        System.out.println(fileName + "- -2");
                    }

                    System.out.println(matrix);
                }
            } catch (IOException exception) {
                throw new Exception("-3");
            }
        } catch (IndexOutOfBoundsException exception) {
            System.out.println("Path for directory was not provided.");
        }
    }
}
