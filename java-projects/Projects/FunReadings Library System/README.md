# FunReadings Library System

A modular Java application designed to manage a digital library system for FunReadings. The system supports book, journal, and media records, along with client management and item leasing. Built using object-oriented design principles such as inheritance, encapsulation, polymorphism, and static tracking.

## Key Features

- **Item Management**  
  - Add, edit, delete library items (books, journals, media)
  - List all items or filter by type (book, journal, media)
  - Track individual copies with unique IDs
  - Search for the largest book by page count
  - Deep copy of the book inventory

- **Client Management**  
  - Add, edit, and delete client records  
  - Store client ID, name, phone number, and email  

- **Leasing System**  
  - Lease items to clients and handle returns  
  - Show all leased items globally or per client  

- **Menu-Driven Interface**  
  - Option to run a dynamic interactive menu  
  - Option to run a predefined test scenario (demonstrating object creation, equality testing, and leasing)

## Object-Oriented Design

- Abstract base class for general library items
- Subclasses:
  - `Book` – with page count
  - `Journal` – with volume number
  - `Media` – with type (audio/video/interactive)
- Inheritance and method overriding
- `equals()` and `toString()` methods implemented in all classes
- Constructors:
  - Default
  - Parameterized (for full attribute initialization)
  - Copy constructor (generates a new item with new ID)

## Packages Structure

- `driver` – Contains the main application
- `client` – Manages all client-related classes
- `libraryitems` – Contains all item-related classes (Book, Journal, Media, etc.)

## Utility Methods

- `getBiggestBook()` – Returns the book with the highest number of pages
- `copyBooks()` – Performs a deep copy of the book array

## Concepts Demonstrated

- Class inheritance and polymorphism
- Static counters for object tracking
- Arrays and arrays of objects
- Deep copying and memory independence
- Defensive programming and input validation
- Information hiding and access control

## How to Run

 1. Compile all source files using:
   ```bash
javac */*.java

2.Run the main program:
java driver.FunReadingsApp
Note: This program is designed to run in a terminal environment. All interactions are text-based.

Status
This project demonstrates the practical implementation of advanced Java OOP principles, and simulates a small-scale digital library backend.
