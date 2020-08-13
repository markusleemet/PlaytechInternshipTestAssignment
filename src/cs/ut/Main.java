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

    public static void main(String[] args) throws IOException, Exception {

        // Check if command line argument is provided
        try {
            try (Stream<Path> paths = Files.walk(Paths.get(args[0]))) {
                List<String> filesPathsInProvidedDirectory = paths.filter(path -> path.getFileName().toString().endsWith(".p15")).map(path -> path.toString()).collect(Collectors.toList());



                for (String path : filesPathsInProvidedDirectory) {
                    File file = new File(path);
                    List<List<Integer>> matrix = createMatrixFromFile(file);
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
