# Vocabulary Control Center

A console-based Java application designed to manage categorized vocabulary words. It provides a flexible menu interface for loading, browsing, editing, and saving topic-based word collections, ideal for managing structured word data.

## Features

- Browse vocabulary under a specific topic
- Insert a new topic before or after an existing one
- Remove or rename topics
- Add, delete, or modify words within a topic
- Load vocabulary data from a structured text file
- Search for topics containing a specific word
- List all words starting with a given letter (sorted alphabetically)
- Save the updated vocabulary data to file

## Data Structures

- **Custom Doubly Linked List**  
  Used to manage a dynamic sequence of vocabulary topics.

- **Custom Singly Linked List**  
  Each topic maintains its own list of words using a singly linked list.

- **Private Inner Node Classes**  
  All linked list nodes are implemented internally to reinforce encapsulation.

- **ArrayList (Selective Use)**  
  Used only in the letter-based filtering feature to collect and sort matching words.

> Note: This project avoids using Java’s built-in data structure libraries (except for one isolated use of `ArrayList`), focusing instead on low-level implementation.

## Input File Format

The vocabulary file should follow this structure:

TopicName1
word1
word2

TopicName2
wordA
wordB


- Lines starting with `#` indicate a new topic
- Subsequent lines list words under that topic
- Blank lines are ignored

## Sample Menu

===========================
Vocabulary Control Center
1 browse a topic
2 insert a new topic before another one
3 insert a new topic after another one
4 remove a topic
5 modify a topic
6 search topics for a word
7 load from a file
8 show all words starting with a given letter
9 save to file
0 exit

Enter Your Choice:

## How to Run

1. Compile:
   ```bash
   javac *.java

2.Execute:
java MainClassName

3.Follow the on-screen instructions to interact with the menu.

Highlights
* Fully interactive terminal interface
* Manual implementation of linked list operations
* Real-time vocabulary editing and structure manipulation
* Designed for efficient in-memory operations without external dependencies
