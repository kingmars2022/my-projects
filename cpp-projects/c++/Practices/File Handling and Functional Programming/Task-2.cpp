#include <iostream> //for cout and cin
#include <string>// for stoi and other string methods
using namespace std;
/*
Function Name: displayHeader()
Parameters: None
Return: None
Purpose: This function will display the welcome banner.
*/
void displayHeader() {
	// welcome banner
	cout
		<< "+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++" << endl
		<< "   Welcome to Covid Vaccine Appointment Program!" << endl
		<< "+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++" << endl;
}
/*
Function Name: displaySchedule()
Parameters: None
Return: None
Purpose: This function displays the vaccine schedule info to the user
*/
void displaySchedule() {
	// cout statement to output the table of vaccine appointment information 
	cout
		<< "    Age\tDate\t\t| Location\t\t| Schedule\n"
		<< "    >= 55\tJan.4\t| 1.Pharmacy\t\t| 1. 10:00-10:15 AM\n"
		<< "    50-54\tJan.6\t| 2.Jean Coutu\t\t| 2. 10:30-10:45 AM\n"
		<< "    40-49\tJan.12\t| 3.Olympic Stadium\t| 3. 11:15-11:30 AM\n"
		<< "    30-39\tJan.17\t| 4.Uniprix Clinique\t| 4. 13:30-13:45 PM\n"
		<< "    20-29\tJan.19\t| 5.Health Center\t| 5. 15:00-15:15 PM\n"
		<< "    >= 18\tJan.21\t| \t\t\t| \n"
		<< "    < 18\tN/A\t  N/A \t\t\t  N/A \n";
	cout << "\n+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++\n";
}
/*
Function Name: getVaccineDose()
Parameters: None
Return: the valid choice from user
Purpose: This function prompts them for a valid vaccine dose (0-3)
*/
int getVaccineDose() {
	// cout and cin to ask user to enter dose number
	int temp_Int;
	cout << "How many Covid Vaccine dose(s) completed? ";
	cin >> temp_Int;
	return temp_Int;
}
/*
Function Name: getAgeInfo()
Parameters: The variable that holds the choice of age.
Return: the starting date.
Purpose: This function asks the user’s valid input of age.
*/
string GetAgeInfo(int& ageNum) {
	while (true) {
		cout << "Please enter your age (>=18): ";
		cin >> ageNum;
		if (ageNum < 18) {
			continue;
		}
		break;
	}
	// switch statement to find and display the correct date, location and schedule
	switch (ageNum) {
	case 18:case 19:
		return "Jan.21";
		break;
	case 20:case 21:case 22:case 23:case 24:case 25:case 26:case 27:case 28:case 29:
		return "Jan.19";
		break;
	case 30:case 31:case 32:case 33:case 34:case 35:case 36:case 37:case 38:case 39:
		return "Jan.17";
		break;
	case 40:case 41:case 42:case 43:case 44:case 45:case 46:case 47:case 48:case 49:
		return "Jan.12";
		break;
	case 50:case 51:case 52:case 53:case 54:
		return "Jan.6";
		break;
	default:
		return "Jan.4";
		break;
	}
	return "";
}
/*
Function Name: getLocationInfo()
Parameters: The variable that holds the choice of location.
Return: The schedule time
Purpose: This function asks the user’s valid input of location.
*/
string GetLocationInfo(int locationNum) {
	while (true) {
		cout << "Please enter the location (1-5):";
		cin >> locationNum;
		if (locationNum < 1 || locationNum>5) {
			continue;
		}
		break;
	}
	switch (locationNum) {
	case 1:
		return "Pharmacy";
		break;
	case 2:
		return "Jean Coutu";
		break;
	case 3:
		return "Olympic Stadium";
		break;
	case 4:
		return "Uniprix Clinique";
		break;
	case 5:
		return "Health Center";
		break;
	}
	return "";
}
/*
Function Name: getScheduleInfo()
Parameters: The variable that holds the choice of schedule.
Return: the schedule time
Purpose: This function asks the user’s valid input of schedule.
*/
string getScheduleInfo(int scheduleNum) {
	while (true) {
		cout << "Please enter the schedule (1-5):";
		cin >> scheduleNum;
		if (scheduleNum < 1 || scheduleNum>5) {
			continue;
		}
		break;
	}
	switch (scheduleNum) {
	case 1:
		return "10:00-10:15 AM";
		break;
	case 2:
		return "10:30-10:45 AM";
		break;
	case 3:
		return "11:15-11:30 AM";
		break;
	case 4:
		return "13:30-13:45 PM";
		break;
	case 5:
		return "15:00-15:15 PM";
		break;
	}
	return "";
}
/*
Function Name: displayResult()
Parameters: The variables that hold the age, starting date, location and
schedule info in main, passing by value.
Return: none.
Purpose: This function displays the booked vaccine information.
*/
void displayResult(int ageNum, string date, string location, string schedule) {
	// cout to display the appointment information
	cout
		<< "\nYour age is " << ageNum << ". You can start to take the vaccine on "
		<< date << ".\n"
		<< "Your appointment is at "
		<< location << " @ "
		<< schedule << ". Take care!\n";
}
/*
Function Name: processMenuChoice()
Parameters: The variable that holds the choice of age, location and schedule
entered by the user, passing by value; The variable that holds the starting
date, location and schedule values, passing by reference.
Return: none
Purpose: This function will call the appropriate function based on the choice
that is passed.
*/
void processMenuChoice(int ageNum, int locationNum, int scheduleNum, string& date, string& location, string& schedule) {
	// if statement to check if the dose number is less than 3
	// to check if the appointment is needed
	if (getVaccineDose() < 3) {
		//if appointment is needed
		//while loops to ask user to enter information
		date = GetAgeInfo(ageNum);
		location = GetLocationInfo(locationNum);
		schedule = getScheduleInfo(scheduleNum);
		displayResult(ageNum, date, location, schedule);
	}
}

int main() {

	// Question 2
	// declaring the variable that I'm going to use below
	int doseNum = 0;
	int ageNum = 0;
	int locationNum = 0;
	int scheduleNum = 0;
	string date = "";
	string location = "";
	string schedule = "";

	displayHeader();// display the welcome banner
	displaySchedule();// displays the vaccine schedule info to the user
	processMenuChoice(ageNum, locationNum, scheduleNum, date, location, schedule); //This function will call the appropriate function based on the choice that is passed.

	// display the closing message
	cout << "\nThank you for using Covid Vaccine Appointment Program!";
	return 0;
}