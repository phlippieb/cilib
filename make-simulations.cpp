#include <iostream>
#include <vector>
using namespace std;

void printSimulation( int, string, int, string, int);

int main() {

	// samples
	int samples = 30; // pretty high


	#include "simVectors.cpp"


	//
	// 	runs through and prints all the simulation combinations
	//
	// 	do not edit this part
	//

	cout 	<< "<!-- simulations for every combination of these dimensions:" << endl
			<< "		algorithms:" << endl;
	for (vector<string>::iterator a = algorithms.begin(); a < algorithms.end(); a++) {
		cout 	<< "			" << *a << endl;
	}
	cout 	<< "		population sizes:" << endl;
	for (vector<int>::iterator p = populations.begin(); p < populations.end(); p++) {
		cout 	<< "			" << *p << endl;
	}
	cout 	<< "		functions:" << endl;
	for (vector<string>::iterator f = functions.begin(); f < functions.end(); f++) {
		cout	<< "			" << *f << endl;
	}
	cout 	<< "		dimensions:" << endl;
	for (vector<int>::iterator d = dimensions.begin(); d < dimensions.end(); d++) {
		cout 	<< "			" << *d << endl;
	}
	cout 	<< "-->" << endl << endl;
			
	for (vector<string>::iterator a = algorithms.begin(); a < algorithms.end(); a++) {
		for (vector<int>::iterator p = populations.begin(); p < populations.end(); p++) {
			for (vector<string>::iterator f = functions.begin(); f < functions.end(); f++) {
				for (vector<int>::iterator d = dimensions.begin(); d < dimensions.end(); d++) {				

					printSimulation (samples, *a, *p, *f, *d);

				}
			}
		}
	}

	return 0;
}

//
//	for a given combination of number of samples,
//	algorithm name, size of population, problem name,
//	and number of problem dimensions, prints the xml
//	simulation.
//
//	do not edit this part.
//

void printSimulation(	int samples,
			string algorithm,
			int algorithmPopulation,
			string problem,
			int problemDimensions)
{
	cout 	<< "<simulation samples=\"" << samples << "\">" << endl
		<< '\t' << "<algorithm idref=\"" << algorithm << '.' << algorithmPopulation << "\"/>" << endl
		<< '\t' << "<problem idref=\"" << problem << '.' << problemDimensions << "\"/>" << endl
		<< '\t' << "<measurements idref=\"both\"/>" << endl
		<< '\t' << "<output format=\"TXT\" file=\"data/" << algorithm << '.' << algorithmPopulation << '.' << problem << '.' << problemDimensions << ".txt\"/>" << endl
		<< "</simulation>" << endl << endl;
}

