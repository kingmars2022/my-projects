# StudentGradeManager

This is a command-line spreadsheet application implemented in C, designed to process student grade data from a text file, display statistics, and allow updates interactively.

## Features

- Load student data from a structured text file
- Display a formatted spreadsheet with calculated totals and letter grades
- Sort records by ID, last name, exam grade, or final grade
- Show a visual histogram of grade distribution
- Update last names or exam grades for specific students
- Adjust numeric-to-letter grade mapping
- Remove students by ID
- Exit cleanly with a farewell message

## Grading Logic

- Assignments (A1–A3): each out of 40 → combined 25%  
- Midterm: out of 25 → 25%  
- Final Exam: out of 40 → 50%  
- Final numeric grade = weighted average of the above  
- Default letter mapping:  
  - A: 80 and above  
  - B: 70–79  
  - C: 60–69  
  - D: 50–59  
  - F: below 50  

## File Structure (Suggested)

- `spreadsheet.c` – main program, menu interface
- `data.c` – file loading, parsing
- `ordering.c` – sorting logic
- `calc.c` – grade calculation and processing
- Corresponding header files (`.h`) for modularization

## Notes

- Input file: `students.txt`, pipe-delimited format  
  `<ID>|<Last>|<First>|<A1>|<A2>|<A3>|<Mid>|<Exam>`
- The program sorts students by default using ID
- All inputs are assumed clean; only basic user error checking is implemented
- Console screen is cleared on each menu update (`system("clear")`)
- Memory is dynamically allocated and freed properly

## Build Instructions

To compile all source files (Linux/macOS):

```bash
gcc spreadsheet.c data.c ordering.c calc.c -o gradeApp -Wall -g
./gradeApp

Example Input (students.txt)
5640|Blanden|Drona|32|18|14|21|36
5054|Thraves|Lucilia|12|28|31|11|32
5385|Marsay|Gnni|5|28|24|16|26
5184|Bygrove|Lennard|22|21|27|17|31
5459|Close|Alva|25|19|37|18|23
5875|Lockless|Demetra|12|15|16|7|14
5904|Drane|Benjy|39|38|37|24|40
5040|Harty|Conant|33|32|35|23|37
5557|Deetlof|Ada|34|27|34|19|22
5236|Mew|Sylvia|35|16|25|14|24

Dependencies
* Standard C libraries (stdio.h, stdlib.h, string.h)
* Runs on Linux/macOS (Docker-compatible)

