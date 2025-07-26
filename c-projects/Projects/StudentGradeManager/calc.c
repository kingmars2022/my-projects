#include <stdio.h>
#include <stdlib.h>
#include <string.h>

#include "calc.h"
#include "ordering.h"

// Display Spreadsheet
void display_spreadsheet(struct System *system) {
    int i;

    printf("COMP 348 GRADE SHEET\n\n");

    printf("%-6s%-12s%-12s%-4s%-4s%-4s%-10s%-10s%-10s%s\n", "ID", "Last", "First", "A1", "A2", "A3",
            "Midterm", "Exam", "Total", "Grade");
    printf("%-6s%-12s%-12s%-4s%-4s%-4s%-10s%-10s%-10s%s\n", "--", "----", "----", "--", "--", "--",
            "-------", "----", "-----", "-----");

    //sort students
    sort_students(system);

    for (i = 0; i < system->size; i++) {
        struct Student *stu = &system->students[i];

        printf("%-6d%-12s%-12s%-4d%-4d%-4d%-10d%-10d%-10.2lf%c\n", stu->id, stu->lastname,
                stu->firstname, stu->grades[0], stu->grades[1], stu->grades[2], stu->grades[3],
                stu->grades[4], stu->total, stu->grade);
    }
}


// Display Histogram
void display_histogram(struct System *system) {
    int i, j;
    int histogram[256]; //counts for ABCDF, all letters are less than 256
    const char *letters = "ABCDF";

    //set all counts to 0
    for (i = 0; i < 256; i++) {
        histogram[i] = 0;
    }

    for (i = 0; i < system->size; i++) {

        struct Student *stu = &system->students[i];
        double total = calculate_total(stu);
        char grade = calculate_grade(total, system->baselines);

        histogram[(int) grade]++;
    }

    printf("COMP 348 GRADE Distribution\n\n");

    for (i = 0; i < 5; i++) {
        printf("%c: ", letters[i]);
        for (j = 0; j < histogram[(int) (letters[i])]; j++) {
            printf("*");
        }
        printf("\n");
    }

    printf("\n");
}

// Set sort column
void set_sort_column(struct System *system) {

    int column;

    printf("Column Options\n");
    printf("--------------\n");
    printf("1. Student ID\n");
    printf("2. Last name\n");
    printf("3. Exam\n");
    printf("4. Total\n");

    printf("\nSort Column: ");
    scanf("%d", &column);

    if (column >= 1 && column <= 4) {
        system->sortColumn = column;
        printf("\nSort column updated\n");
    } else {
        printf("\nInvalid input\n");
    }
}

// Find student index by id.
int get_student_index_by_id(struct System *system, int id) {
    int i;

    for (i = 0; i < system->size; i++) {
        struct Student *stu = &system->students[i];
        if (stu->id == id) {
            return i;
        }
    }

    return -1;
}

// Find student by id.
struct Student* get_student_by_id(struct System *system, int id) {
    int index = get_student_index_by_id(system, id);
    if (index >= 0) {
        return &system->students[index];
    } else {
        return NULL;
    }
}

// Update Last Name
void update_lastname(struct System *system) {
    int id;
    char lastname[50];
    struct Student *stu;

    display_spreadsheet(system);

    printf("\n\nEnter Student ID: ");
    scanf("%d", &id);

    printf("\nEnter updated Last Name: ");
    scanf("%s", lastname);

    stu = get_student_by_id(system, id);
    if (stu != NULL) {
        strcpy(stu->lastname, lastname);
        printf("\nLast name updated\n");
    } else {
        printf("The student not found\n");
    }
}

// Update exam
void update_exam(struct System *system) {
    int id;
    int score;
    struct Student *stu;

    display_spreadsheet(system);

    printf("\n\nEnter Student ID: ");
    scanf("%d", &id);

    printf("\nEnter updated exam grade: ");
    scanf("%d", &score);

    stu = get_student_by_id(system, id);
    if (stu != NULL) {
        stu->grades[4] = score;
        printf("\nExam grade updated\n");
    } else {
        printf("The student not found\n");
    }
}

// Update grade mapping
void update_grade_mapping(struct System *system) {
    printf("Current Mapping:\n");
    printf(" A: >= %d\n", system->baselines[0]);
    printf(" B: >= %d\n", system->baselines[1]);
    printf(" C: >= %d\n", system->baselines[2]);
    printf(" D: >= %d\n", system->baselines[3]);
    printf(" F:  < %d\n", system->baselines[3]);

    printf("\n\nEnter new A baseline: ");
    scanf("%d", &system->baselines[0]);
    printf("Enter new B baseline: ");
    scanf("%d", &system->baselines[1]);
    printf("Enter new C baseline: ");
    scanf("%d", &system->baselines[2]);
    printf("Enter new D baseline: ");
    scanf("%d", &system->baselines[3]);

    printf("\nNew Mapping:\n");
    printf(" A: >= %d\n", system->baselines[0]);
    printf(" B: >= %d\n", system->baselines[1]);
    printf(" C: >= %d\n", system->baselines[2]);
    printf(" D: >= %d\n", system->baselines[3]);
    printf(" F:  < %d\n", system->baselines[3]);
}

// Delete Student
void delete_student(struct System *system) {
    int id;
    int index;

    display_spreadsheet(system);

    printf("\n\nEnter Student ID: ");
    scanf("%d", &id);

    index = get_student_index_by_id(system, id);
    if (index != -1) {
        int i;

        //remove student at 'index'
        for (i = index; i < system->size - 1; i++) {
            system->students[i] = system->students[i + 1];
        }
        system->size--;

        printf("\nStudent successfully deleted\n");
    } else {
        printf("The student not found\n");
    }
}

// Show system menu.
void show_menu(struct System *sys) {
    int option;
    char proceed;

    while (1) {
        system("clear");
        printf("Spreadsheet Menu\n");
        printf("----------------\n");
        printf("1. Display Spreadsheet\n");
        printf("2. Display Histogram\n");
        printf("3. Set sort column\n");
        printf("4. Update Last Name\n");
        printf("5. Update Exam Grade\n");
        printf("6. Update Grade Mapping\n");
        printf("7. Delete Student\n");
        printf("8. Exit\n");
        printf("\n");
        printf("Selection: ");

        scanf("%d", &option);

        if (option == 1) {
            display_spreadsheet(sys);
        } else if (option == 2) {
            display_histogram(sys);
        } else if (option == 3) {
            set_sort_column(sys);
        } else if (option == 4) {
            update_lastname(sys);
        } else if (option == 5) {
            update_exam(sys);
        } else if (option == 6) {
            update_grade_mapping(sys);
        } else if (option == 7) {
            delete_student(sys);
        } else if (option == 8) {
            printf("Goodbye and thanks for using our spreadsheet app\n");
            break;
        }

        printf("\nPress 'c' or 'C' to continue ");
        do {
            scanf("%c", &proceed);
        } while ((proceed != 'c') && (proceed != 'C'));
    }
}
