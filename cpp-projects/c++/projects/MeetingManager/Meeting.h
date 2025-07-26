#ifndef MEETING_H // test if flag is set
#define MEETING_H
#include <string>
using namespace std;
class Meeting {
private:
	string name;
	string date;
	int mode;
	int capacity;
public:
	Meeting();
	Meeting(string n);
	Meeting(string n, string d, int m, int c);
	string getName();
	string getDate();
	int getMode();
	int getCapacity();
	void setName(string n);
	void setDate(string d);
	void setMode(int m);
	void setCapacity(int c);
	bool isOnline(int m);
	void changeOnline(int number);
	bool equals(Meeting m);
	void postpone(string newDate);
	void printInfo();
};
#endif
