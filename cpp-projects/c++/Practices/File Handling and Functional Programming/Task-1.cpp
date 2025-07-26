#include <iostream> //for cout and cin
#include <fstream> // for file IO
#include <string>// for stoi and other string methods
#include <vector>// to have a dynamic string list 
using namespace std;

int main() {
	// declaring the variable that I'm going to use below
	string read_line;// string variable for read file
	string input_studentID;
	string complete_course = "";
	double GPA_sum = 0.0;
	double counter = 0.0;//to count how many course the student has taken
	vector<string> studentInfo;// To store all the information in the list
	// Cout, display the welcome message
	cout
		<< "***************************************************\n"
		<< "\t\tAverage GPA Checker\t\t\n"
		<< "***************************************************"
		<< endl;
	// try to open file
	ifstream myfile_read("A3Q1_student.txt");//declare ifstream to read file
	if (myfile_read.is_open())
	{
		while (getline(myfile_read, read_line))
		{
			studentInfo.push_back(read_line);// add a line from the file to the vector list at the end
		}
		myfile_read.close();//close the ifstream
	}
	else {
		//output the error message and close the program
		cout << "Sorry! Could not open the file A3Q1_student.txt Program ending ...";
		return 0;
	}
	// asking for input
	cout << "\nPlease enter the student ID to check: ";
	cin >> input_studentID;
	reverse(studentInfo.begin(), studentInfo.end());//reverse the vector list so that the order is right
	// calculate the GPA sum and counter
	for (auto ir = studentInfo.crbegin(); ir != studentInfo.crend(); ++ir) {
		if ((*ir).find(input_studentID) != std::string::npos) {
			GPA_sum += stod((*ir).substr((*ir).rfind(" ") + 1));
			complete_course += (*ir).substr((*ir).find(" "), (*ir).rfind(" ") - 5);
			counter += 1;
		}
	}
	//declare ofstream to write a file
	ofstream myfile_write("output.txt");
	if (myfile_write.is_open())
	{
		//if GPA_sum == 0, then the student ID was not in the list, if statement to excuted the corresponding statement
		if (GPA_sum != 0) {
			myfile_write << "The student ID " << input_studentID << " average GPA is : " << GPA_sum / counter << "\n";
			myfile_write << "The complete course list is: " << complete_course << "\n";
		}
		else {
			myfile_write << "No student ID " << input_studentID << " is found from the list.";
		}
		myfile_write.close();//close the ofstream
	}
	// output the necessary message
	cout << "\nSearch is done! Please check the results in the output file.\n";
	cout << "\nThank you for using Average GPA Checker program!!\n";

	return 0;
}