package driver;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import a3.UniqueWords;

public class Driver {
    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Usage: java Driver <filename>");
            return;
        }

        String filename = args[0];
        UniqueWords uniqueWords = new UniqueWords();

        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] words = line.split("\\s+");
                for (String word : words) {
                    uniqueWords.addWord(word);
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }

        System.out.println(String.format("We found %d unique words and the max size of our words array is %d.", uniqueWords.size(), uniqueWords.maxSize()));
        uniqueWords.printWords();
    }
}

