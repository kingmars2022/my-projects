# CostLessBites POS System

This project is a console-based Point-of-Sale (PoS) management system for a catering business. It allows the recording, tracking, and comparison of on-demand meal sales and prepaid card usage, offering a full menu-based interface for operations and reporting.

## Overview

The system simulates a real-world POS where customers can pay via direct purchase or through pre-paid cards. It tracks sales across multiple meal categories and maintains an array of prepaid card records, each with owner ID, diet type, and expiry date.

## Key Functionalities

- Store and manipulate sales data for 5 meal sizes:
  - Junior ($5), Teen ($10), Medium ($12), Big ($15), Family ($20)
- Track prepaid cards with details:
  - Card type (Carnivore, Halal, Kosher, etc.)
  - Owner ID (employee or customer ID)
  - Expiry date (validated)
- Manage multiple PoS terminals, each with independent sales and prepaid card data
- Add, remove, or update prepaid cards
- Add new sales records to any PoS
- Compare PoS objects based on:
  - Total sales value
  - Meal distribution
  - Number of prepaid cards
  - Combined equality conditions
- Display all PoS data with clear formatting

## Architecture

### Classes Implemented

- **Sales**
  - Stores meal counts and calculates total dollar value
  - Provides accessors, mutators, copy constructor, equality check, and `toString()`

- **PrePaiCard**
  - Holds card type, owner ID, and expiry date
  - Includes validation logic, date formatting, copy constructor, equality check, and `toString()`

- **PoS**
  - Aggregates a `Sales` object and an array of `PrePaiCard` objects
  - Supports dynamic array resizing for card management
  - Implements comparison, addition, removal, and update methods

- **PoSDemo (Driver)**
  - Builds multiple PoS objects
  - Presents a user menu for all available operations
  - Avoids redundancy using helper static methods

## Example Menu

1. See the content of all PoSs
2. See the content of one PoS
3. List PoSs with same $ amount of sales
4. List PoSs with same number of sales per category
5. List PoSs with same sales and prepaid card count
6. Add a prepaid card to a PoS
7. Remove a prepaid card from a PoS
8. Update the expiry date of a prepaid card
9. Add new sales to a PoS
10. Quit


## Sample Use Case

- Initialize five PoS instances with varying configurations:
  - Some identical in meal counts or sales totals
  - Others with or without prepaid cards
- Perform actions such as:
  - Compare PoSs for equivalency
  - Add/remove cards dynamically
  - Simulate new sales
  - Print full breakdowns

## Notable Constraints

- No usage of external libraries beyond `java.util.Scanner`
- No use of `java.util.Arrays` or collection frameworks
- All arrays managed manually with resizing logic

## Concepts Demonstrated

- Class encapsulation and object arrays  
- Manual array manipulation (add/remove/resize)  
- Conditional logic and formatted string output  
- Static method utility and DRY principle  
- Defensive programming (date validation, null handling)











