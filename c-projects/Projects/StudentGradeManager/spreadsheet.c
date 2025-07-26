#include <stdio.h>
#include <stdlib.h>
#include <string.h>

#include "spreadsheet.h"
#include "data.h"
#include "calc.h"


int main() {

    struct System *system = create_system(); //create system
    load_students(system); //load file
    show_menu(system); //show menus
    free_system(system); //free the allocated memories

    return 0;
}

// Creates a new system with 0 students.
struct System* create_system() {

    struct System *system = (struct System*) malloc(sizeof(struct System));
    system->size = 0;
    system->capacity = 1;
    system->students = (struct Student*) malloc(sizeof(struct Student) * system->capacity);

    //set initial baseline
    system->baselines[0] = 80;
    system->baselines[1] = 70;
    system->baselines[2] = 60;
    system->baselines[3] = 50;

    system->sortColumn = 1;

    return system;
}

// Free the system data.
void free_system(struct System *system) {
    free(system->students);
    free(system);
}
