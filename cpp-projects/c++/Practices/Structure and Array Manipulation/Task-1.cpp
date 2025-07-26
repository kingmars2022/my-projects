#include <iostream> //for cout and cin

using namespace std;
//struct Meeting
//has variable: string Name,Date; int Capacity,Mode; bool isOnline
struct Meeting
{
	string Name;
	string Date;
	int Capacity;
	int Mode;
	bool isOnline;
};
//Setupmeeting function
//parameter: string name,date; int capacity,mode;
//retrun: Meeting
Meeting SetupMeeting(string name, string date, int capacity, int mode) {
	Meeting meeting;
	meeting.Name = name;
	meeting.Date = date;
	meeting.Capacity = capacity;
	meeting.Mode = mode;
	if (mode) meeting.isOnline = true;//if mode != 0, isOnline = true;
	return meeting;
}
int main() {
	//decalre variables
	Meeting meeting1;
	string name;
	string date;
	int capacity;
	int mode;
	bool isContinue = true;
	string isContinueText = "";
	//welcome message
	cout << "****************************************************\n\t\tMeeting Information\n****************************************************\n\n";
	do {
		// do while loop to at least take one set of input
		cout << "Please enter the following information:\n";
		cout << "Meeting's name: ";
		cin >> name;
		cout << "Meeting's date: ";
		cin >> date;
		cout << "Meeting's capacity: ";
		cin >> capacity;
		cout << "Meeting's mode: ";
		cin >> mode;
		//using the SetupMeeting function to set up meeting1
		meeting1 = SetupMeeting(name, date, capacity, mode);
		//variable for later display of information
		string pluralInfo;
		string inPersonInfo;
		//if statement to change the string student ,if plural capacity -> students, else -> student
		if (meeting1.Capacity > 1)
		{
			pluralInfo = "students";
		}
		else
		{
			pluralInfo = "student";
		}
		//if statement to change the mode string, online and offline(in person)
		if (meeting1.Mode == 1)
		{
			inPersonInfo = "online";
		}
		else {
			inPersonInfo = "in person";
		}
		//display meeting1's information
		cout << "\nThe meeting name is : " << meeting1.Name
			<< "\nThe meeting date is : " << meeting1.Date
			<< "\nThe meeting capacity is : " << meeting1.Capacity << " " << pluralInfo
			<< "\nThe meeting mode is : " << inPersonInfo << endl;
		cout << "\nHere is the summary of the meeting:\n";
		//summary
		cout << meeting1.Name << " is on " << meeting1.Date << " with the capacity of " << meeting1.Capacity << " " << pluralInfo << " " << inPersonInfo << ".\n";
		//ask whether if the user want another meeting. 
		cout << "\nWould you like to set up another meeting? ";
		cin >> isContinueText;
		if (isContinueText.compare("yes") == 0) {
			isContinue = true;
		}
		else {
			isContinue = false;
		}
	} while (isContinue);
	//cout output ending message
	cout << "\nThank you for using Meeting information Program!";
	return 0;
}