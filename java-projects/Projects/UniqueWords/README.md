# UniqueWords

A lightweight Java utility for extracting and managing unique words from a text file. It demonstrates dynamic array handling, case-insensitive string comparison, and file input processing in a clean, modular design.

## Overview

This program reads a text file containing multiple lines of text, processes each word, and builds a list of unique words (ignoring case and duplication). It dynamically resizes its internal array as needed and prints the final result, including:

- Total number of unique words
- Maximum capacity the array reached
- Ordered list of all unique words (based on first occurrence)

## Features

- Case-insensitive word comparison  
- Maintains insertion order of unique words  
- Automatically doubles the array size when full  
- Provides utility methods:
  - `addWord(String word)`
  - `printWords()`
  - `maxSize()` – current array capacity
  - `size()` – actual number of unique words stored

## Implementation Details

- Class: `UniqueWords`
- Fields:
  - `String[] words` – initialized with a capacity of 4
  - `int size` – number of unique words added
- The `addWord` method handles:
  - Duplicate check (case-insensitive)
  - Array resizing (doubling)
  - Word insertion

## How to Use

1. **Compile the code**:
   ```bash
   javac a3/UniqueWords.java driver/Driver.java DebugRunner.java

2.Run with a sample file:
java driver.Driver example1.txt

3.Example output:
We found 45 unique words and the max size of our words array is 64.
When, in, Rome, Do, as, the, Romans, Practice, makes, ...

File Structure
```text
prj/
├── a3/
│   └── UniqueWords.java
├── driver/
│   └── Driver.java
├── DebugRunner.java
├── config.xml
├── example1.txt
└── example2.txt
```


Notes
* Words are compared in a case-insensitive manner.
* The program only accepts .txt input files containing space-separated words.
* The provided DebugRunner can be used to test your implementation using predefined configurations.

Concepts Demonstrated
* Array resizing and dynamic memory allocation
* Case-insensitive string comparison
* File input handling in Java
* Basic data deduplication logic without using collections
