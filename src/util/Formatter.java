package util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Stack;

/**
 * This class was only written to transform the characters in the book "Gadsby",
 * so that it only contains lowercase alphabetical characters.
 * This way it will be easier to prepare the {@link main.InformationSource} for testing.
 * A period is added at the end as an End Of Data character.
 */
public class Formatter {

    private static String formatLine(String line) {
        StringBuilder sb = new StringBuilder(line.trim());
        Stack<Integer> indexesOfNonAlphabeticalCharacters = new Stack<>();
        for (int i = 0; i < sb.length(); i ++) {
            char c = sb.charAt(i);
            if (!Character.isAlphabetic(c))
                indexesOfNonAlphabeticalCharacters.push(i);
            else sb.setCharAt(i, Character.toLowerCase(c));
        }
        while (!indexesOfNonAlphabeticalCharacters.isEmpty())
            sb.deleteCharAt(indexesOfNonAlphabeticalCharacters.pop());

        return sb.toString();
    }

    public static void main(String[] args) throws Exception{

        String inFilename = args[0];
        String outFilename = args[1];

        BufferedReader reader = new BufferedReader(new FileReader(inFilename));
        BufferedWriter writer = new BufferedWriter(new FileWriter(outFilename));

        for (String line = reader.readLine(); line != null; line = reader.readLine())
            writer.write(formatLine(line));

        writer.write('.');

        reader.close();
        writer.close();
    }
}
