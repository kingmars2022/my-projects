#ifndef ORDERING_H_
#define ORDERING_H_

#include "spreadsheet.h"

// Get the total score of a student.
double calculate_total(struct Student *student);

// Get grade letter of a total score.
char calculate_grade(double total, int baselines[4]);

// Set the grades of students.
void update_grades(struct System *system);

// Sort the array of students.
void sort_students(struct System *system);


#endif 
