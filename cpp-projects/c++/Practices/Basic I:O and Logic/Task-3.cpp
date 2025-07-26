/* new Word Program, generate a word with input string and numbers*/
#include <iostream> // for cin, cout
#include <string> // for string
using namespace std;

int main()
{
    string word1, word2, word3, newWord;
    int num1, num2, num3;
    
    // welcome message
    cout << "***********************************************\n"
    << "   Welcome to New Word Program \n"
    << "***********************************************\n\n";
    // ask user for the input string
    cout << "Please enter 3 words : ";
    cin >> word1 >> word2 >> word3;
    // ask user for the input numbers
    cout << "\nPlease enter the 1st number less than " << word1.length() << ": " ;
    cin >> num1;
    cout << "\nPlease enter the 2nd number less than " << word2.length() << ": " ;
    cin >> num2;
    cout << "\nPlease enter the 3rd number less than " << word3.length() << ": " ;
    cin >> num3;
    // generate the new word (assemble the new word)
    newWord = word1.substr(num1%2,num1)+word2.substr(num2%2,num2)+word3.substr(num3%2, num3);
    // display the Results
    cout << " \nThe new word is: " << newWord << endl;
    // closing message
    cout << "\nThank you for using New Word Program!\n\n";
    
    return 0;
}
