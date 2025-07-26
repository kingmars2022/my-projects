#include <iostream> //for cout and cin
#include <string>// for stoi and other string methods
using namespace std;

int main() {
	// declaring the variable that I'm going to use below
	int doseNum,ageNum,locationNum,scheduleNum;
	string date,location,schedule;
	// cout statement to output the welcome message
	std::cout
		<< "+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++" << std::endl
		<< "   Welcome to Covid Vaccine Appointment Program!" << std::endl
		<< "+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++" << std::endl;
	// cout statement to output the table of vaccine appointment information 
	std::cout
		<< "    Age\tDate\t\t| Location\t\t| Schedule\n"
		<< "    >= 55\tJan.4\t| 1.Pharmacy\t\t| 1. 10:00-10:15 AM\n"
		<< "    50-54\tJan.6\t| 2.Jean Coutu\t\t| 2. 10:30-10:45 AM\n"
		<< "    40-49\tJan.12\t| 3.Olympic Stadium\t| 3. 11:15-11:30 AM\n"
		<< "    30-39\tJan.17\t| 4.Uniprix Clinique\t| 4. 13:30-13:45 PM\n"
		<< "    20-29\tJan.19\t| 5.Health Center\t| 5. 15:00-15:15 PM\n"
		<< "    >= 18\tJan.21\t| \t\t\t| \n"
		<< "    < 18\tN/A\t  N/A \t\t\t  N/A \n";
	// cout and cin to ask user to enter dose number
	std::cout << "\n+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++\n";
	std::cout << "How many Covid Vaccine dose(s) completed? ";
	cin >> doseNum;
	// if statement to check if the dose number is less than 3
	// to check if the appointment is needed
	if (doseNum < 3) {
		// if appointment is needed
		// while loops to ask user to enter information
		while (true) {
			std::cout << "Please enter your age (>=18): ";
			cin >> ageNum;
			if (ageNum < 18) {
				continue;
			}
			break;
		}
		while (true) {
			std::cout << "Please enter the location (1-5):";
			cin >> locationNum;
			if (locationNum < 1 || locationNum>5) {
				continue;
			}
			break;
		}
		while (true) {
			std::cout << "Please enter the schedule (1-5):";
			cin >> scheduleNum;
			if (scheduleNum < 1 || scheduleNum>5) {
				continue;
			}
			break;
		}
		// switch statement to find and display the correct date, location and schedule
		switch (ageNum) {
		case 18:case 19:
			date = "Jan.21";
			break;
		case 20:case 21:case 22:case 23:case 24:case 25:case 26:case 27:case 28:case 29:
			date = "Jan.19";
			break;
		case 30:case 31:case 32:case 33:case 34:case 35:case 36:case 37:case 38:case 39:
			date = "Jan.17";
			break;
		case 40:case 41:case 42:case 43:case 44:case 45:case 46:case 47:case 48:case 49:
			date = "Jan.12";
			break;
		case 50:case 51:case 52:case 53:case 54:
			date = "Jan.6";
			break;
		default:
			date = "Jan.4";
			break;
		}
		switch (locationNum) {
		case 1:
			location = "Pharmacy";
			break;
		case 2:
			location = "Jean Coutu";
			break;
		case 3:
			location = "Olympic Stadium";
			break;
		case 4:
			location = "Uniprix Clinique";
			break;
		case 5:
			location = "Health Center";
			break;
		}
		switch (scheduleNum) {
		case 1:
			schedule = "10:00-10:15 AM";
			break;
		case 2:
			schedule = "10:30-10:45 AM";
			break;
		case 3:
			schedule = "11:15-11:30 AM";
			break;
		case 4:
			schedule = "13:30-13:45 PM";
			break;
		case 5:
			schedule = "15:00-15:15 PM";
			break;
		}
		// cout to display the appointment information
		std::cout
			<< "\nYour age is " << ageNum << ". You can start to take the vaccine on "
			<< date << ".\n"
			<< "Your appointment is at "
			<< location << " @ "
			<< schedule << ". Take care!\n";
	}
	// display the closing thank message
	std::cout << "\nThank you for using Covid Vaccine Appointment Program!";
	return 0;
}
