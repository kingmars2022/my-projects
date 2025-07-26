#include "Meeting.h"
#include <iostream>

//main method
int main() {
	//declare the things we need for the Meeting input
	string meeting2Name,meeting3Name, meeting3Date;
	int meeting3Mode, meeting3Capacity;
	Meeting meeting1;//using default constructor
	//don't worry about the line below, it's a welcome banner
	cout << "__________________________________________________________\n\n\tWelcome to Meeting Information Program\n__________________________________________________________\n";
	cout << "Please enter the name of meeting2 please: ";//ask for input
	cin >> meeting2Name;
	Meeting meeting2(meeting2Name);//using second constructor
	cout << "Please enter the name, date, mode and capacity of meeting3 please:\n";//ask for input
	cin >> meeting3Name >> meeting3Date >> meeting3Mode >> meeting3Capacity;
	cout << "\n";
	Meeting meeting3(meeting3Name, meeting3Date, meeting3Mode, meeting3Capacity);//using third constructor

	//printing info for every meeting
	cout << "\nMeeting 1 is " ;
	meeting1.printInfo();
	cout << "\nMeeting 2 is " ;
	meeting2.printInfo();
	cout << "\nMeeting 3 is ";
	meeting3.printInfo();
	cout << "\n\n";

	//check if need to change and ask for decision
	meeting1.changeOnline(1);
	meeting2.changeOnline(2);
	meeting3.changeOnline(3);

	//integer array
	int arr[3] = { meeting1.getCapacity(), meeting2.getCapacity(), meeting3.getCapacity() };
	int sum = 0;
	for (int i = 0; i < 3;i++) {
		sum += arr[i];
	}//add all value to sum
	//print sum
	cout << "\n\nThe total capacity of 3 meetings is: " << sum<<"\n";
	cout << "\nNow, the capacity of meeting2 is changed...\n";
	arr[1] = arr[0] + arr[2];
	meeting2.setCapacity(arr[1]);//actually change the capacity value in meeting2
	sum = 0;
	for (int i = 0; i < 3; i++) {
		sum += arr[i];
	}
	cout << "The total capacity of 3 meetings is: " << sum << "\n";
	cout << "\nWould you like to postpone the meeting1? ";//ask if postpone meeting1
	string postponeDecision;
	cin >> postponeDecision;//cin to store input string postponeDecision
	if (postponeDecision.compare("yes")==0) {//yes and put new date in the function, else do nothing
		string postponeDate;
		cout << "Please enter the new date: ";
		cin >> postponeDate;
		meeting1.postpone(postponeDate);
	}
	//check if meetings are eaqual or not and print the information
	cout << "\nMeeting 1 & 2";
	if (meeting1.equals(meeting2)) {
		cout << " are equal.";
	}
	else {
		cout << " are not equal.";
	}
	cout << "\nMeeting 1 & 3";
	if (meeting1.equals(meeting3)) {
		cout << " are equal.";
	}
	else {
		cout << " are not equal.";
	}
	cout << "\nMeeting 2 & 3";
	if (meeting2.equals(meeting3)) {
		cout << " are equal.";
	}
	else {
		cout << " are not equal.";
	}
	//print information again
	cout << "\n\nMeeting 1 is ";
	meeting1.printInfo();
	cout << "\nMeeting 2 is ";
	meeting2.printInfo();
	cout << "\nMeeting 3 is ";
	meeting3.printInfo();

	//cout display ending message
	cout << "\n\nThank you for using this program!";

	return 0;
}