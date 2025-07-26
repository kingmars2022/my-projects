#include "Meeting.h"
#include <iostream>
//constructor
Meeting::Meeting() {//default constructor
	name = "COMP218";
	date = "2022-02-12";
	mode = 0;
	capacity = 100;
}
Meeting::Meeting(string _name) {//custom constructor taking one parameter string _name
	name = _name;
	date = "2022-02-22";
	mode = 1;
	capacity = 1;
}
Meeting::Meeting(string _name, string _date, int _mode, int _capacity) {//custom constructor taking four parameter string _name,_date;int _mode,_capacity;
	name = _name;
	date = _date;
	mode = _mode;
	capacity = _capacity;
}
//accessors and mutators
string Meeting::getName() {
	return name;
}
string Meeting::getDate() {
	return date;
}
int Meeting::getMode() {
	return mode;
}
int Meeting::getCapacity() {
	return capacity;
}
void Meeting::setName(string n) {
	name = n;
}
void Meeting::setDate(string d) {
	date = d;
}
void Meeting::setMode(int m) {
	mode = m;
}
void Meeting::setCapacity(int c) {
	capacity = c;
}
//functions
bool Meeting::isOnline(int m) {//return a bool to tell user if it is online
	if (m == 1) {
		return true;
	}
	else
	{
		return false;
	}
}
void Meeting::changeOnline(int number) {//function to ask if the user want to change the meeting to online
	if (!isOnline(getMode())) {
		cout << "Would you like to change the meeting " << number << " " << "online? ";
		string temp;
		cin >> temp;
		if (temp.compare("yes") == 0) {
			setMode(1);
		}
	}
}

bool Meeting::equals(Meeting m) {//check if two meetings are equal
	if (m.getMode() == mode && m.getCapacity() == capacity && m.getName().compare(name) == 0 && m.getDate().compare(date) == 0) {
		return true;
	}
	return false;
}
void Meeting::postpone(string newDate) {//actually update the date
	date = newDate;
}
void Meeting::printInfo() {//print information
	string tempOnline;
	string tempPlural;
	if (mode == 1) {
		tempOnline = "online.";
	}
	else {
		tempOnline = "in person.";
	}
	if (capacity > 1) {
		tempPlural = "students";
	}
	else
	{
		tempPlural = "student";
	}
	cout << name << " on " << date << " with capacity: " << capacity << " " << tempPlural << " " << tempOnline;
}