import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    // Fin the minimum of the three values
    static int min(int a, int b, int c) {
        return Math.min(a, Math.min(b, c));
    }


    static int calc_edit_distance(String str1, String str2) {
        // str1 spans the left side while str2 spans the top
        int m = str1.length() + 1;
        int n = str2.length() + 1;
        int[][] matrix = new int[m][n];


        for (int i = 1; i < m; i++) {
            matrix[i][0] = i;
        }


        for (int j = 1; j < n; j++) {
            matrix[0][j] = j;
        }


        for (int i = 1; i < m; i++) {
            for (int j = 1; j < n; j++) {
                int replace_cost = 0;
                if (str1.charAt(i - 1) != str2.charAt(j - 1)) { // If the current last letters are not the same
                    replace_cost = 1;
                }


                matrix[i][j] = min(
                        matrix[i - 1][j] + 1,                 // Deletion
                        matrix[i][j - 1] + 1,                // Insertion
                        matrix[i - 1][j - 1] + replace_cost // Replacement
                );
            }
        }

        return matrix[m - 1][n - 1];
    }

    public static void main(String[] args) {
        // Get word list
        ArrayList<String> wordList = new ArrayList<>();
        Scanner scanner = new Scanner(System.in);
        while (true) {
            String word = scanner.nextLine().trim();
            if (word.equals("#")) {
                break;
            }
            wordList.add(word);
        }

        // Get misspelled words
        while (scanner.hasNextLine()) {
            String misspelledWord = scanner.nextLine().trim();
            int shortestDistance = Integer.MAX_VALUE;
            ArrayList<String> matchingWords = new ArrayList<>();


            for (String word : wordList) {
                int distance = calc_edit_distance(misspelledWord, word);

                if (distance <= shortestDistance) {
                    // Clear previous matches words if new shortest edit distance is found
                    if (distance < shortestDistance) {
                        matchingWords.clear();
                        shortestDistance = distance; // Update shortest distance
                    }
                    matchingWords.add(word);
                }
            }

            // Print result
            System.out.print(misspelledWord + " (" + shortestDistance + ")");
            for (String word : matchingWords) {
                System.out.print(" " + word);
            }
            System.out.println();
        }
    }
}
