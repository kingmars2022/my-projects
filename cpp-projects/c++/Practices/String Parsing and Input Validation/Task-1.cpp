#include <iostream> //for cout and cin
#include <map>// for the list of places
#include <string>// for stoi and other string methods
using namespace std;

int main() {
	// cout statement to output the welcome message
	std::cout
		<< "+++++++++++++++++++++++++++++++++++++++++++++" << std::endl
		<< "   Welcome to Covid Vaccine Program!" << std::endl
		<< "+++++++++++++++++++++++++++++++++++++++++++++" << std::endl;
	//declaring the variable that I'm going to use below
	string inputString, inputNumberOne, inputNumberTwo, firstName, familyName;
	std::size_t _at, _hash, _dash;
	// using map<int,string> as a dictionary for locations
	// and initializing the map<int, string> listOfLocations
	std::map<int, string> listOfLocations
	{ {1,"Pharmacy"}, {2,"Jean Coutu"}, {3,"Olympic Stadium"},{4,"Uniprix Clinique"},
	 {5, "Health Center"}, {6, "Pharmacy"}, {7, "Jean Coutu"},{8, "Not Available"} };
	//while loop to ask the user to enter information again
	//while(true) because we want it to run until we manully stop it
	while (true) {
		// cout statement to output the message that ask for the user input
		std::cout
			<< "Please enter the input information :" << std::endl
			<< "For example, Robin_Morin@1#2" << std::endl;
		// cin statement to get user's input
		std::cin >> inputString;
		//using find() to get the index of the letter/symbol
		_at = inputString.find('@');
		_hash = inputString.find('#');
		_dash = inputString.find('_');
		//using the index of the symbol to substring correctly
		inputNumberOne = inputString.substr(_at + 1, _hash - _at - 1);
		inputNumberTwo = inputString.substr(_hash + 1);
		//if statement to check if the user entered the correct information
		if (_at == std::string::npos ||
			_hash == std::string::npos ||
			_dash == std::string::npos) {
			continue;
		}
		// using stoi to transfor string number to int type
		int numberOne = stoi(inputNumberOne);
		//check if number1 is valid or not
		if (numberOne > 7) continue;
		int numberTwo = stoi(inputNumberTwo);
		//using substr() to get first name and family name 
		firstName = inputString.substr(0, _dash);
		familyName = inputString.substr(_dash + 1, _at - _dash - 1);
		//output the result info
		std::cout << "\n" << familyName << ", " << firstName << " has completed " << numberTwo << " dose(s) of Covid 19 vaccine at " << listOfLocations[numberOne] << ".\n";
		//if the user take less than 3 shots of vaccine
		if (3 - numberTwo > 0) {
			//if yes, output the number of remaining dose(s)
			std::cout << "Please complete the remaining " << 3 - numberTwo << " dose(s) soon. Take care!\n";
		}
		// output the ending thank message
		std::cout << "\nThank you for using Covid Vaccine Program!";
		break;
	}
	return 0;
}
