import Exceptions.*;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

public class Main {
    /**
     * The main method where the execution of the program starts.
     * @param args The command line arguments passed to the program.
     */
    public static void main(String[] args) {
        // Setting up file paths and folders
        String path = System.getProperty("user.dir")+ "/" +"src/";
        String inputFileFolder = "inputfiles/";
        String outputFileFolder = "output/";
        // Initialize the environment
        initialize( System.getProperty("user.dir"), inputFileFolder, outputFileFolder);

        // part 1’s manifest file
        String part1_manifest = "part1_manifest.txt";

        String part2_manifest = do_part1(part1_manifest, path, inputFileFolder, outputFileFolder);  // partition
        // Read movie records from existing files

        // First, read part1_manifest.txt, where each line represents a filename of a movie record to be read.
        // If valid, write into the corresponding Genre CSV file. If the Genre does not exist, generate a corresponding CSV file and record the newly generated Genre CSV filename in part2_manifest.txt.
        // If invalid, write the details of invalid records into bad_movie_records.txt.
        // part 3’s manifest file
        String part3_manifest = do_part2(part2_manifest , path, outputFileFolder);  // serialize
        // Read part2_manifest.txt first, where each line represents a filename of a movie record to be read, and then read all movie instances within the respective files.
        // Write each read Movie instance into the corresponding .ser file (binary file).

        do_part3(part3_manifest,path, outputFileFolder);
        // deserialize
        // and navigate
        // Restore Movie instances from .ser files and navigate.

    }
    /**
     * Initializes the environment by deleting existing files and folders.
     * @param path The base path of the environment.
     * @param inputFileFolder The folder containing input files.
     * @param outputFolder The folder containing output files.
     */
    private static void initialize(String path, String inputFileFolder, String outputFolder){

        try {
            // Delete existing files and folders
            Files.deleteIfExists(Paths.get(path + "/src/part2_manifest.txt"));
            Files.deleteIfExists(Paths.get(path + "/src/part3_manifest.txt"));
            Files.deleteIfExists(Paths.get( path + "/bad_movie_records.txt"));
            Files.deleteIfExists(Paths.get( path + "/bad_movie_records2.txt"));
            // Delete existing files in the output folder
            File file = new File(path , "src/"+outputFolder);

            File[] entries = file.listFiles();
            if (entries != null) {
                for (File entry : entries) {
                    Files.deleteIfExists(entry.toPath());
                }
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    /**
     * Performs part 1 of the process.
     * @param part1_manifest The file name of part 1's manifest.
     * @param path The base path of the environment.
     * @param inputFileFolder The folder containing input files.
     * @param outputFolder The folder containing output files.
     * @return The file name of part 2's manifest（will be returned).
     */
    private static String do_part1(String part1_manifest, String path, String inputFileFolder, String outputFolder) {

        //read     part1_manifest.txt
        String filepath = path + part1_manifest;
        Movie[] movieList = null;

        Scanner scanner = null;
        PrintWriter outputFile = null;

        String[] csvNameList = {"musical.csv", "comedy.csv", "animation.csv",
                "adventure.csv", "drama.csv", "crime.csv", "biography.csv",
                "horror.csv", "action.csv", "documentary.csv", "fantasy.csv",
                "mystery.csv", "sci-fi.csv", "family.csv", "western.csv", "romance.csv",
                "thriller.csv"};

        File part2_manifest = new File(path ,"part2_manifest.txt");

        for (String csvName : csvNameList){
            File genreCsv = new File(path + outputFolder, Movie.Capitalize(csvName));
            if (!genreCsv.exists()) {
                try {
                    genreCsv.createNewFile();

                    outputFile = new PrintWriter(new FileOutputStream(part2_manifest, true));

                    outputFile.println(genreCsv.getName());

                    outputFile.flush();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }

        if (outputFile!=null) outputFile.close();

        //int movieCounter =0;
        try {
            scanner = new Scanner(new FileReader(filepath));

            String csvFileName;

            // Read all movie records, if there are no errors, add them to the movieList.
            while (scanner.hasNextLine()) {

                csvFileName = scanner.nextLine();
                Movie[] newMovieList = readOneFile(path + inputFileFolder , csvFileName, "bad_movie_records.txt");

                movieList = mergeMovieList(movieList, newMovieList);

            }

            for (Movie movie : movieList) {

                if (movie!=null) {
                    String genre = movie.getGenres().toString();

                    if (movie.getGenres().equals(Movie.Genre.SCI_FI)) genre="sci-fi";
                    File genreCsv = new File(path + outputFolder, Movie.Capitalize(genre) + ".csv");

                    outputFile = new PrintWriter(new FileOutputStream(genreCsv, true));
                    outputFile.println(movie);

                    outputFile.close();

                }
            }

        } catch (IOException e) {
            e.printStackTrace();

        }

        scanner.close();

        return "part2_manifest.txt";
    }
    /**
     * Performs part 2 of the process.
     * @param part2_manifest The file name of part 2's manifest.
     * @param path The base path of the environment.
     * @param outputFolder The folder containing output files.
     * @return The file name of part 3's manifest.
     */
    private static String do_part2(String part2_manifest,String path, String outputFolder) {
        // Build the filepath to part 2's manifest
        String filepath = path + part2_manifest;

        Scanner scanner = null;
        PrintWriter outputFile = null;
        ObjectOutputStream outputStreamName = null;

        //int movieCounter =0;
        // Prepare to create part 3's manifest file
        File part3_manifest = new File(path ,"part3_manifest.txt");
        // Open the scanner to read from part 2's manifest
        try {
            scanner = new Scanner(new FileReader(filepath));
            String csvFileName;
            String serFileName;
            // Loop through each line in part 2's manifest
            while (scanner.hasNextLine()) {

                csvFileName = scanner.nextLine();
                serFileName =  csvFileName.substring(0,csvFileName.indexOf("."))+ ".ser";
                // Create the serialized file if it doesn't exist
                File genreSer;
                genreSer = new File(path + outputFolder, Movie.Capitalize(serFileName));

                if (!genreSer.exists()) {
                    try {
                        genreSer.createNewFile();

                        outputFile = new PrintWriter(new FileOutputStream(part3_manifest, true));

                        outputFile.println(genreSer.getName());

                        outputFile.flush();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
                // Read movie records from CSV file
                Movie[] newMovieList = readOneFile(path + outputFolder , csvFileName, "bad_movie_records.txt");
                // Merge movie lists
                Movie[] movieList = mergeMovieList(null, newMovieList);

                // Write serialized movie list to file
                try {
                    outputStreamName = new ObjectOutputStream(new
                            FileOutputStream(genreSer,true));

                    outputStreamName.writeObject(movieList);

                    outputStreamName.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

                outputFile.close();
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        // Close resources
        if (outputFile!=null) outputFile.close();

        scanner.close();

        return "part3_manifest.txt";
    }
    /**
     * Performs part 3 of the process.
     * @param part3_manifest The file name of part 3's manifest.
     * @param path The base path of the environment.
     * @param outputFileFolder The folder containing output files.
     */
    private static void do_part3(String part3_manifest,String path,String outputFileFolder) {
        // Build the filepath to part 3's manifest
        String filepath = path + part3_manifest;
        Movie[] movieList = null;
        Movie[][] all_movies = new Movie[17][];
        String[] genreNames = new String[17];
        Scanner scanner = null;

        ObjectInputStream inputStreamName = null;
        // Open the scanner to read from part 3's manifest
        try {
            scanner = new Scanner(new FileReader(filepath));
            String serFileName;

            File genreSer;
            int genreIndex = 0;
            // Loop through each line in part 3's manifest
            while (scanner.hasNextLine()) {

                serFileName = scanner.nextLine();
                genreNames[genreIndex] = serFileName.substring(0,serFileName.indexOf("."));

                genreSer = new File(path + outputFileFolder, Movie.Capitalize(serFileName));
                // Open input stream to read serialized objects
                try {
                    inputStreamName = new ObjectInputStream(new FileInputStream(genreSer));


                    try {

                        movieList = (Movie[]) inputStreamName.readObject();

                    } catch (ClassNotFoundException e) {
                        throw new RuntimeException(e);
                    }

                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                // Store the Movie array
                all_movies[genreIndex++] = movieList;
            }

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        view(all_movies,genreNames);

    }
    /**
     * Reads movie records from a file, processes them, and returns an array of Movie objects.
     * Records errors encountered during processing and outputs them to a specified file.
     * @param path The path to the file.
     * @param fileName The name of the file to read.
     * @param errorOutputFileName The name of the file to output errors.
     * @return An array of Movie objects.
     */
    private static Movie[] readOneFile(String path, String fileName, String errorOutputFileName) {
        Movie[] result = new Movie[100]; // Initial size
        PrintWriter outputWriter = null;
        try {
            outputWriter = new PrintWriter(new FileOutputStream(errorOutputFileName, true));

            int index = 0;

            int currentLineNumber = 0;

            try (BufferedReader in = new BufferedReader(new FileReader(path+fileName))) {
                String str;
                while ((str = in.readLine()) != null) {

                    currentLineNumber++;

                    if (str.trim().isEmpty()) continue; //如果是空行就跳下行

                    Movie entry = null;
                    try {

                        entry = scanEntryString(str);

                    } catch (BadSemanticException e) {

                        recordErrorIntoFile(outputWriter, e.getMessage(), str, fileName, currentLineNumber);

                    } catch (SyntaxErrorException e) {

                        recordErrorIntoFile(outputWriter, e.getMessage(), str, fileName, currentLineNumber);

                    }

                    if (index == result.length) {
                        result = resizeArray(result); //Resize the array if it's full
                    }
                    if (entry!=null) result[index++] = entry;
                }

            } catch (IOException e) {
                e.printStackTrace();
                // Error handling for IOExceptions

            }

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        return result;
    }
    /**
     * Parses a string representing a movie entry and creates a Movie object.
     * @param entry The string representing a movie entry.
     * @return The Movie object created from the string.
     * @throws BadSemanticException If there is a semantic error in the movie entry.
     * @throws SyntaxErrorException If there is a syntax error in the movie entry.
     */
    private static Movie scanEntryString(String entry)
            throws BadSemanticException, SyntaxErrorException
    {
        boolean openQuate = false;
        String[] values = new String[10];
        int index = 0;
        String currentValue = "";

        try {
            for (int i = 0; i < entry.length(); i++) {
                char ch = entry.charAt(i);
                if (ch == ',' && !openQuate) {
                    // When a comma is encountered and not within quotes, complete a field
                    values[index++] = currentValue.trim();
                    currentValue = "";
                } else if (ch == '"') {
                    // Handle opening or closing quotes
                    openQuate = !openQuate;
                } else {
                    currentValue += ch; // Concatenating characters to the current value
                }
            }

            if (openQuate) {
                throw new MissingQuotesException("missing quotes");
            }

            if (!currentValue.trim().isEmpty()) {
                // Process the last field
                values[index++] = currentValue.trim();
            }

        } catch (ArrayIndexOutOfBoundsException e) {
            // Exception for too many fields
            throw new ExcessFieldsException("excess Field(s)");
        }

        if (index < 10) {
            // Exception for not enough fields
            throw new MissingFieldsException("missing Field(s)");
        }

        return new Movie(values); // Assuming a Movie constructor that accepts a String array
    }
    /**
     * Resizes the given array of Movie objects to accommodate additional elements.
     * This method creates a new array with a length increased by 100 compared to the original array.
     * It copies all elements from the original array to the new array and returns the new array.
     * @param original The original array of Movie objects to be resized.
     * @return A new array of Movie objects with increased length.
     */
    private static Movie[] resizeArray(Movie[] original) {
        Movie[] newArray = new Movie[original.length + 100];
        System.arraycopy(original, 0, newArray, 0, original.length);
        return newArray;
    }
    /**
     * Merges two arrays of Movie objects into one.
     * @param oldList The first array of Movie objects.
     * @param newList The second array of Movie objects.
     * @return The merged array of Movie objects.
     */
    private static Movie[] mergeMovieList(Movie[] oldList, Movie[] newList) {

        int oldLength = 0;

        if (oldList!=null) {
            for (Movie movie:oldList){
                if (movie!=null) oldLength++;
            }
            //oldLength++;
        }

        int newLength = 0;
        if (newList!=null) {
            for (Movie movie : newList) {
                if (movie != null) newLength++;
            }
            //newLength++;

        }

        // Create a new array with the combined length
        Movie[] mergedList = new Movie[oldLength+newLength];

        // Copy oldList into mergedList
        if (oldList!=null) System.arraycopy(oldList, 0, mergedList, 0, oldLength);

        // Copy newList into mergedList
        if (newList!=null) System.arraycopy(newList, 0, mergedList, oldLength, newLength);

        return mergedList;
    }
    /**
     * Records an error message, original record, file name, and line number into a specified file.
     * @param outputFile The PrintWriter object for writing to the output file.
     * @param errorMessage The error message to record.
     * @param originalRecord The original record causing the error.
     * @param fileName The name of the file where the error occurred.
     * @param currentLineNumber The line number where the error occurred.
     */
    private static void recordErrorIntoFile(PrintWriter outputFile, String errorMessage,
                                            String originalRecord, String fileName, int currentLineNumber) {
        try {
            if (outputFile != null) {
                outputFile.println(errorMessage + " " + originalRecord + "; " + fileName+ "; " + currentLineNumber);

                outputFile.flush();
            } else {
                System.err.println("Output file is not initialized.");
            }
        } catch (Exception e) {
            System.err.println("Error occurred while writing to output file: " + e.getMessage());
            e.printStackTrace();
        }
    }
    /**
     * Displays the main menu and handles user interactions.
     * @param movies An array containing arrays of Movie objects (genres).
     * @param genreNames An array containing the names of genres.
     */
    // Method to display the main menu and handle user interactions
    private static void view(Movie[][] movies,String[] genreNames) {
        Scanner sc = new Scanner(System.in);
        String choice;
        int selectedGenreIndex = 0; // Holds the index of the selected genre

        // Main interaction loop
        do {
            // Display the main menu options
            System.out.println("-----------------------------");
            System.out.println("          Main Menu          ");
            System.out.println("-----------------------------");
            System.out.println("s Select a movie array to navigate");
            System.out.println("n Navigate "+ genreNames[selectedGenreIndex] + " movies (" + movies[selectedGenreIndex].length + " records)");
            System.out.println("x Exit");
            System.out.println("-----------------------------");
            System.out.print("Enter Your Choice: ");
            choice = sc.nextLine();

            // Handle user choices
            switch (choice.toLowerCase()) {
                case "s":
                    // Select a genre array to navigate
                    selectedGenreIndex = selectMovieArray(movies,genreNames);
                    break;
                case "n":
                    // Navigate the selected genre's movies
                    if (selectedGenreIndex != -1) {
                        navigateMovies(movies, selectedGenreIndex);
                    } else {
                        System.out.println("Please select a genre first.");
                    }
                    break;
                case "x":
                    // Exit the program
                    System.out.println("Exiting the program.");
                    break;
                default:
                    // Invalid choice handler
                    System.out.println("Invalid choice. Please enter s, n, or x.");
                    break;
            }
        } while (!choice.equalsIgnoreCase("x"));
    }
    /**
     * Allows the user to select a genre array (genre) to navigate.
     * @param movies An array containing arrays of Movie objects (genres).
     * @param genreNames An array containing the names of genres.
     * @return The index of the selected genre array, or -1 if exit is chosen.
     */
    // Method for selecting a movie array (genre) to navigate
    private static int selectMovieArray(Movie[][] movies,String[] genreNames) {
        Scanner sc = new Scanner(System.in);
        int index = -1;
        System.out.println("------------------------------------");
        System.out.println("          Genre Sub-Menu            ");
        System.out.println("------------------------------------");

        // Display the genres and the number of movies in each
        for (int i = 0; i < movies.length; i++) {
            String genreName = genreNames[i]; // Assuming the genre name can be obtained from the first movie
            int movieCount = movies[i].length; // Number of movies in the genre
            System.out.printf("%2d %-15s (%d movies)%n", (i + 1), genreName, movieCount);
        }
        System.out.println("18 Exit");
        System.out.println("------------------------------------");
        System.out.print("Enter Your Choice: ");

        // Get the user's choice and handle it
        int choice = sc.nextInt();
        sc.nextLine(); // Consume the newline

        // Return the selected genre index or -1 if exit is chosen
        if (choice == 18) {
            index = -1;
        } else if (choice > 0 && choice < 18) {
            index = choice - 1;
        } else {
            System.out.println("Invalid choice. Please try again.");
        }
        return index;
    }

    /**
     * Navigates through an array of movies, allowing the user to move forwards or backwards through the list.
     * 
     * @param movies An array of movies categorized by genre.
     * @param genreIndex The index of the genre to navigate through.
     */
    private static void navigateMovies(Movie[][] movies, int genreIndex) {
        Scanner sc = new Scanner(System.in);
        int currentPosition = 0; // Start at the first movie record
        int movieCount = movies[genreIndex].length; // Total number of movies in the genre

        // Check if the genre has movies to navigate
        if (movieCount == 0) {
            System.out.println("No movies to navigate in this genre.");
            return;
        }

        while (true) {
            // Print the current position
            System.out.println("currentPosition: " + (currentPosition + 1));
            System.out.print("Enter the number of records to navigate (negative to go up, positive to go down, 0 to exit): ");

            int n = sc.nextInt();
            sc.nextLine();

            int step = 1; // Default step size

            if (n > 0) step = 1; // Move forward
            if (n == 0) break; // Exit the loop if 0 is entered
            if (n < 0) step = -1; // Move backward

            // Loop through the movies, moving the currentPosition
            for (int i = 0; i < Math.abs(n) && currentPosition >= 0 && currentPosition < movieCount; currentPosition += step, i++) {
                System.out.println("Movie " + (currentPosition + 1) + ": " + movies[genreIndex][currentPosition]);
            }

            // Adjust the currentPosition to stay within bounds and notify about boundary hits
            if (currentPosition - step >= 0 && currentPosition - step < movieCount) {
                if (currentPosition - step == 0 && n < 0) System.out.println("BOF (Beginning of File) has been reached.");
                if (currentPosition == movieCount && n > 0) System.out.println("EOF (End of File) has been reached.");
                currentPosition -= step;
            }
        }
    }
}
