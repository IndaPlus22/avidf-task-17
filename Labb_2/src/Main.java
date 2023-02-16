//I do apologize this dosent really work that well because I am not really good at Java 
//Hope its enough for a pass!

import java.util.Scanner;
import java.util.ArrayList;

public class Main {

    //Find the minimum of the three values 
    public static int min(int a, int b, int c) {
        return Math.min(a, Math.min(b, c));
    }


    // Calculates the Levenshtein distance dynamically 
    //Using a matrix structure 
    //String_1 reads from the left to right and string_2 vis versa

    public static int find_distance(String string_1, String string_2) {
        int m = string_1.length() + 1;
        int n = string_2.length() + 1;
        int[][] matrix = new int[m][n];

        // Prefixes of str1 can be transformed into the empty string (0th prefix of str2)
        // By removing all characters
        for (int i = 1; i < m; i++) {
            matrix[i][0] = i;
        }

        // Empty str1 can be transformed into the prefixes of str2 by adding every character
        for (int j = 1; j < n; j++) {
            matrix[0][j] = j;
        }

        // Fill remaining matrix with edit distances between the substrings in matrix[i][j]
        for (int i = 1; i < m; i++) {
            for (int j = 1; j < n; j++) {
                int replace = 0;
                if (string_1.charAt(i - 1) != string_2.charAt(j - 1)) { // If the current last letters are not the same
                    replace = 1;
                }

                // This cell's edit distance is the minimum out of the three possible edits
                matrix[i][j] = min(matrix[i - 1][j] + 1, // Deletion
                        matrix[i][j - 1] + 1, // Insertion
                        matrix[i - 1][j - 1] + replace); // Replacement
            }
        }

        return matrix[m - 1][n - 1];
    }

    public static void main(String[] args) {
        try (Scanner scan = new Scanner(System.in)) {
            // Get word list
            ArrayList<String> wordList = new ArrayList<>();
            while (true) {
                String word = scan.nextLine().trim();

                if (word.equals("#")) { // End of word list
                    break;
                }
                wordList.add(word);
            }

            // Get misspelled words
            while (scan.hasNextLine()) {
                String misspelledWord = scan.nextLine().trim();
                int shortestDistance = Integer.MAX_VALUE;
                ArrayList<String> matchingWords = new ArrayList<>(); // Words with lowest edit distance

                // Calculate edit distance between misspelled word and every word in the word list
                // Save the ones with the lowest edit distance
                for (String word : wordList) {
                    int distance = find_distance(misspelledWord, word);

                    if (distance <= shortestDistance) {
                        // Clear previous matches words if new shortest edit distance is found
                        if (distance < shortestDistance) {
                            matchingWords.clear();
                            shortestDistance = distance; // Update shortest distance
                        }
                        matchingWords.add(word);
                    }
                }

                
                System.out.print(misspelledWord + " (" + shortestDistance + ")");
                for (String word : matchingWords) {
                    System.out.print(" " + word);
                }
                System.out.println();
            }
        }
    }
}
