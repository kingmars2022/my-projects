# Movie Data Processing System

A console-based Java application designed to process and manage movie records from the 1990s. This program demonstrates exception handling, file I/O, object serialization, and interactive navigation.

## Project Overview

1. **Data Cleaning and Classification**
   - Reads raw movie records from multiple input files.
   - Filters out syntactic and semantic errors.
   - Valid records are classified by genre and written to individual CSV files (e.g., `comedy_movies.csv`).
   - All invalid records are saved to `bad_movie_records.txt`.

2. **Object Serialization**
   - Reads genre-based CSV files and converts them into arrays of `Movie` objects.
   - Each array is serialized into a binary `.ser` file (e.g., `comedy.ser`).

3. **Interactive Deserialization and Navigation**
   - Deserializes each `.ser` file into a 2D array of `Movie` objects.
   - Offers a terminal-based menu to:
     - Select a movie genre.
     - Navigate through movie records (next/previous).
     - Jump to a specific record or display a range of records.

## Technologies Used

- **Language**: Java  
- **Core Concepts**:
  - Custom exception classes and exception propagation
  - Multi-file I/O: reading/writing text and binary files
  - Object serialization and deserialization using `Serializable`
  - Class encapsulation and method overloading/overriding
  - Menu-driven terminal navigation with input validation

## Main Components

- `Movie.java` – Defines the `Movie` class with 10 attributes (title, year, length, genre, rating, score, director, actors, etc.)
- `Main.java` – Contains `do_part1()`, `do_part2()`, and `do_part3()` methods for processing each phase
- `bad_movie_records.txt` – Logs malformed or invalid records
- `*.csv` – Genre-specific cleaned movie datasets
- `*.ser` – Serialized object files
- `part*_manifest.txt` – Track intermediate output files during each stage

## Execution Steps

1. **Compile the program**
   ```bash
   javac *.java

2. Run the main class
java Main


3. Follow the menu instructions
* Start with Part 1 (data filtering)
* Proceed to Part 2 (serialization)
* Then use Part 3 (interactive viewer)


Educational Value
This project illustrates:
* Full-cycle data processing from raw input to user interface
* Robust error handling with custom exception classes
* Practical use of Java I/O and object streams
* Clean object-oriented design with real-world data
