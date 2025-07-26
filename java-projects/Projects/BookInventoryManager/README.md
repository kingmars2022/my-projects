# BookInventoryManager

A console-based Java application that manages a bookstore's inventory using object-oriented principles. This project emphasizes class design, encapsulation, static tracking, and interactive user input/output.

## Features

- Define and use a `Book` class with the following attributes:
  - Title (String)
  - Author (String)
  - ISBN (long)
  - Price (double)
- Support:
  - Constructor-based initialization
  - Attribute getters and setters
  - `toString()` method for display
  - `equals()` method comparing ISBN and price
  - Static method to track number of created `Book` objects

- Driver program features:
  - Password-protected options for inventory modification
  - Track inventory using an array of `Book` objects
  - Interactive menu with the following options:
    1. Enter new books (requires password)
    2. Modify attributes of an existing book (requires password)
    3. Display all books by a specific author
    4. Display all books below a certain price
    5. Exit the program

- Password handling:
  - Password is hardcoded as `"249"`
  - Multiple failed attempts lead to menu reset or program termination (after repeated misuse)

## Concepts Demonstrated

- Object-Oriented Design
- Constructors and Overloading
- Static Attributes and Methods
- Array of Objects
- Menu-Driven Console Interface
- Input Validation and Control Flow
- Defensive Programming and User Restrictions

## How to Run

1. Compile all `.java` files:

   ```text
javac *.java
```

2.Run the main program:
java BookInventoryManager

Notes
* All user interaction occurs via terminal (console)
* Inventory capacity is defined at runtime
* Password misuse is monitored and restricted
* Designed for self-assessment and foundational OOP practice
