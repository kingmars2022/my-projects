#include <stdio.h>
#include <stdlib.h>
#include <string.h>

#include "ordering.h"

// Coompare two students by id.
static int compare_student_by_id(const void *a, const void *b);

// Coompare two students by last name.
static int compare_student_by_lastname(const void *a, const void *b);

// Coompare two students by exam.
static int compare_student_by_exam(const void *a, const void *b);

// Coompare two students by total.
static int compare_student_by_total(const void *a, const void *b);

// Coompare two students by id.
static int compare_student_by_id(const void *a, const void *b) {

    // get the struct pointers at address 'a' and address 'b'
    const struct Student *struct_a = (const struct Student*) a;
    const struct Student *struct_b = (const struct Student*) b;

    if (struct_a->id < struct_b->id)
        return -1;
    if (struct_a->id > struct_b->id)
        return 1;
    return 0; // if the first two conditions are not true
}

// Coompare two students by last name.
static int compare_student_by_lastname(const void *a, const void *b) {

    // get the struct pointers at address 'a' and address 'b'
    const struct Student *struct_a = (const struct Student*) a;
    const struct Student *struct_b = (const struct Student*) b;

    return strcmp(struct_a->lastname, struct_b->lastname);
}

// Coompare two students by exam.
static int compare_student_by_exam(const void *a, const void *b) {

    // get the struct pointers at address 'a' and address 'b'
    const struct Student *struct_a = (const struct Student*) a;
    const struct Student *struct_b = (const struct Student*) b;

    if (struct_a->grades[4] < struct_b->grades[4])
        return 1;
    if (struct_a->grades[4] > struct_b->grades[4])
        return -1;
    return 0; // if the first two conditions are not true
}

// Coompare two students by total.
static int compare_student_by_total(const void *a, const void *b) {
    
    // get the struct pointers at address 'a' and address 'b'
    const struct Student *struct_a = (const struct Student*) a;
    const struct Student *struct_b = (const struct Student*) b;

    if (struct_a->total < struct_b->total)
        return 1;
    if (struct_a->total > struct_b->total)
        return -1;
    return 0; // if the first two conditions are not true
}

// Get the total score of a student.
double calculate_total(struct Student *student) {
    return 25.0 * (student->grades[0] + student->grades[1] + student->grades[2]) / 120.0
            + 25.0 * student->grades[3] / 25.0 + 50.0 * student->grades[4] / 40.0;
}

// Get grade letter of a total score.
char calculate_grade(double total, int baselines[4]) {
    if (total >= baselines[0]) {
        return 'A';
    }

    if (total >= baselines[1]) {
        return 'B';
    }

    if (total >= baselines[2]) {
        return 'C';
    }

    if (total >= baselines[3]) {
        return 'D';
    }

    return 'F';
}

// Set the grades of students.
void update_grades(struct System *system) {
    int i;

    for (i = 0; i < system->size; i++) {
        struct Student *stu = &system->students[i];
        double total = calculate_total(stu);
        char grade = calculate_grade(total, system->baselines);
        stu->total = total;
        stu->grade = grade;
    }
}

// Sort the array of students.
void sort_students(struct System *system) {
    update_grades(system);

    if (system->sortColumn == 1) {
        qsort(system->students, system->size, sizeof(struct Student), compare_student_by_id);
    }
    if (system->sortColumn == 2) {
        qsort(system->students, system->size, sizeof(struct Student), compare_student_by_lastname);
    }
    if (system->sortColumn == 3) {
        qsort(system->students, system->size, sizeof(struct Student), compare_student_by_exam);
    }
    if (system->sortColumn == 4) {
        qsort(system->students, system->size, sizeof(struct Student), compare_student_by_total);
    }
}

