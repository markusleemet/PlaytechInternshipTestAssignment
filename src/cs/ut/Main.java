package cs.ut;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {

    /**
     * Method asks for user input and returns it as a String
     * @return user input as a string
     * @throws IOException
     */
    static String readUserInput() throws IOException {
        InputStreamReader inputStreamReader = new InputStreamReader(System.in);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

        return bufferedReader.readLine();
    }

    public static void main(String[] args) throws IOException {
        String userInput = readUserInput();
        System.out.println(userInput);
    }
}
