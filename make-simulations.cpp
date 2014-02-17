#include <iostream>
#include <vector>
using namespace std;

void printSimulation( int, string, int, string, int, int);

int main() {

	//
	// 	seting up all the simulation combinations.
	//
	//	only edit this part
	//

	// samples
	int samples = 30; // pretty high


	// problems
	vector<string> problems;
	//problems.push_back("schwefel");
	//problems.push_back("elliptic");
	//problems.push_back("rastrigin");
	//problems.push_back("ackley");
	//problems.push_back("rosenbrock");
	//problems.push_back("alpine");
	problems.push_back("beale");


	// problem dimensions
	vector<int> problemDimensions;
	problemDimensions.push_back(25);


	// algorithms
	vector<string> algorithms;
	algorithms.push_back("gbest");
	algorithms.push_back("lbest");
	algorithms.push_back("gcpso");
	algorithms.push_back("spso");
	algorithms.push_back("bb");
	algorithms.push_back("bbe");

	
	// algorithm populations
	vector<int> algorithmPopulations;
	algorithmPopulations.push_back(25);


	//
	// 	runs through and prints all the simulation combinations
	//
	// 	do not edit this part
	//

	int i,j,k,l;
	cout 	<< "<!-- simulations for every combination of these dimensions:" << endl
		<< "		problems:" << endl;
	for ( i=0; i< problems.size(); i++ ) {
	cout 	<< "			" << problems[i] << endl;
	}
	cout 	<< "		problem dimesions:" << endl;
	for ( j=0; j<problemDimensions.size(); j++ ) {
	cout 	<< "			" << problemDimensions[j] << endl;
	}
	cout 	<< "		algorithms:" << endl;
	for ( k=0; k<algorithms.size(); k++ ) {
	cout	<< "			" << algorithms[k] << endl;
	}
	cout 	<< "		algorithm populations:" << endl;
	for ( l=0; l<algorithmPopulations.size(); l++ ) {
	cout 	<< "			" << algorithmPopulations[l] << endl;
	}
	cout 	<< "-->" << endl << endl;
				
	int simulationNumber = 1;		
	for ( i=0; i<problems.size(); i++ ) {
		for ( j=0; j<problemDimensions.size(); j++ ) {
			for ( k=0; k<algorithms.size(); k++ ) {
				for ( l=0; l<algorithmPopulations.size(); l++ ) {
					
					printSimulation (	samples,
								algorithms[k],
								algorithmPopulations[l],
								problems[i],
								problemDimensions[j],
								simulationNumber++
							);	

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
			int problemDimensions,
			int simulationNumber)
{
	cout	<< "<!-- sim number " << simulationNumber << " - " << algorithm << '.' << algorithmPopulation << '.' << problem << '.' << problemDimensions << "  -->" << endl;
	cout 	<< "<simulation samples=\"" << samples << "\">" << endl
		<< '\t' << "<algorithm idref=\"" << algorithm << '.' << algorithmPopulation << "\"/>" << endl
		<< '\t' << "<problem idref=\"" << problem << '.' << problemDimensions << "\"/>" << endl
		<< '\t' << "<measurements idref=\"both\"/>" << endl
		<< '\t' << "<output format=\"TXT\" file=\"data/" << algorithm << '.' << algorithmPopulation << '.' << problem << '.' << problemDimensions << ".txt\"/>" << endl
		<< "</simulation>" << endl << endl;
}

