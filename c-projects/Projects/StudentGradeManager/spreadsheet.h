#ifndef SPREADSHEET_H_
#define SPREADSHEET_H_

//struct of a student
struct Student {

    //student ID, last name,  first name, A1 grade, A2 grade, A3 grade, midterm grade, exam grade
    int id;
    char lastname[50];
    char firstname[50];
    int grades[5]; // A1, A2, A3, Mid, Final

    double total;
    char grade;
};

//struct of the data of the spreedsheet system.
struct System {
    struct Student *students; //the array of all students
    int capacity; //the capacity of array
    int size; //the number of students in array

    int baselines[4]; //the baselines for A,B,C,D
    int sortColumn; //1-ID, 2-lastname, 3-exam, 4-total
};

// Creates a new system with 0 students.
struct System* create_system();

// Free the system data.
void free_system(struct System *system);

#endif
