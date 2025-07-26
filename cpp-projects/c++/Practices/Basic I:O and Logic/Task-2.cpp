
#include <iostream> // for cin, cout
using namespace std;
/* number generator program, generate a 3-digit random number between 100-999*/
int main()
{
    // variable declaration
    const int NUM = 100; //constant variable as request
    int random_num;
    int num1, num2, num3;
    int sum, subtraction, multiplication, reminder;
    
    // welcome message
    cout << "***********************************************\n" <<
    "   Welcome to Number Generator Program\n" <<
    "***********************************************\n\n";
    // generate a 3-digit radom number
    cout << "Now generating a 3-digit random number...\n";
    srand ( time(NULL) );
    random_num = rand() % 900 + 100;
    cout<<"The random number is : " << random_num << endl << endl;
    // find the 3 numbers in the generated number
    num1 = random_num / NUM;
    num2 = random_num % NUM / 10;
    num3 = random_num % NUM % 10;
    // calculation and finds all the result
    sum = num1+num2+num3;
    subtraction = num1-num2-num3;
    multiplication = num1*num2*num3;
    reminder = random_num % sum;
    // display the info
    cout << "The sum of the 3 digits is: " << sum << endl
    << "The substraction of the 3 digits is: " << subtraction << endl
    << "The multiplication of the 3 digits is: " << multiplication << endl
    << "The reminder is: " << reminder << endl;
    // closing message
    cout << "\n\nThank you for using Number Generator Program!\n\n";
    return 0;
}


