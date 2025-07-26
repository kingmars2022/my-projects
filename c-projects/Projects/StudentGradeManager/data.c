#include <stdio.h>
#include <stdlib.h>
#include <string.h>

#include "data.h"

// Load all students from "students.txt".
void load_students(struct System *system) {
    FILE *file_ptr;
    char row_buffer[200];

    file_ptr = fopen("students.txt", "r");
    if (file_ptr) {
        
        //read all lines
        while (fgets(row_buffer, 200, file_ptr) != NULL) {
            struct Student stu;
            int i;
            int length;

            //set '|' to ' '
            length = (int) strlen(row_buffer);
            for (i = 0; i < length; i++) {
                if (row_buffer[i] == '|') {
                    row_buffer[i] = ' ';
                }
            }
            printf("%s\n", row_buffer);

            //parse student from line string
            sscanf(row_buffer, "%d%s%s%d%d%d%d%d", &stu.id, stu.lastname, stu.firstname,
                    &stu.grades[0], &stu.grades[1], &stu.grades[2], &stu.grades[3], &stu.grades[4]);

            //enlarge the space of array if necessary
            if (system->size >= system->capacity) {
                system->capacity += 1;
                system->students = (struct Student*) realloc(system->students,
                        sizeof(struct Student) * system->capacity);
            }

            //add new student to array
            system->students[system->size++] = stu;
        }

        fclose(file_ptr);
    } else {
        printf("File students.txt not found.\n");
    }
}
