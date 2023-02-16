//I do apologize this dosent really work that well because I am not really good at Java 
//Hope its enough for a pass!

import java.util.Scanner;
import java.util.ArrayList;

public class Main {

    //Find the minimum of the three values 
    public static int min(int a, int b, int c) {
        return Math.min(a, Math.min(b, c));
    }


    //Calculates the Levenshtein distance dynamically 
    //Using a matrix structure 
    //String_1 reads from the left to right and string_2 vis versa


    public static int find_distance(String string_1, String string_2) {
        int m = string_1.length() + 1;
        int n = string_2.length() + 1;
        int[][] matrix = new int[m][n];

        //Transform elements in string_1 can be into an empty string 
        for (int i = 1; i < m; i++) {
            matrix[i][0] = i;
        }

        // Transform elements in string_1 to elements in string_2
        for (int j = 1; j < n; j++) {
            matrix[0][j] = j;
        }

        // Fill remaining matrix with edit distances between the substrings in matrix[i][j]
        for (int i = 1; i < m; i++) {

            for (int j = 1; j < n; j++) {

                int replace = 0;

                // If the last letter isnt the same
                if (string_1.charAt(i - 1) != string_2.charAt(j - 1)) 
                { 
                    replace = 1;
                }

                //The edit capabilities for each cell in the matrix
                matrix[i][j] = min(matrix[i - 1][j] + 1, // Deletion
                        matrix[i][j - 1] + 1,            // Insertion
                        matrix[i - 1][j - 1] + replace); // Replacement
            }
        }

        return matrix[m - 1][n - 1];
    }


    //Here is where the problems start 

    //The scanner is supposed to get the word list from the arrey 
    //then get the mispelled words and then 
    //Calculate the distance between mispelled words and the rest of the words in the list
    //It dont do that properly!

    public static void main(String[] args) {
        try (Scanner scan = new Scanner(System.in)) {

            ArrayList<String> wordList = new ArrayList<>();
            while (true) {
                String word = scan.nextLine().trim();

                //When reaches end of the list
                if (word.equals("#")) { 
                    break;
                }
                wordList.add(word);
            }


            //Scans through and clears previous matches for new distance 
            while (scan.hasNextLine()) {
                String misspellings = scan.nextLine().trim();
                int shortest_distance = Integer.MAX_VALUE;
                ArrayList<String> matched_words = new ArrayList<>(); 

                for (String word : wordList) {
                    int distance = find_distance(misspellings, word);

                    if (distance <= shortest_distance) 
                    {
                        if (distance < shortest_distance) 
                        {
                            matched_words.clear();
                            shortest_distance = distance; 
                        }
                        matched_words.add(word);
                    }
                }

                
                System.out.print(misspellings + " (" + shortest_distance + ")");
                for (String word : matched_words) {
                    System.out.print(" " + word);
                }
                System.out.println();
            }
        }
    }
}
