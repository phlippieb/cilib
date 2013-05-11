#include <iostream>
#include <string>
using namespace std;

void generateSimulationXML (string algorithm, int populationSize, int iterations, string problem, int dimensions) {
	cout << "<simulation samples=\"30\">" << endl;
	cout << "	<algorithm idref=\"" << algorithm << '.' << populationSize << '.' << iterations <<"\"/>" << endl;
	cout << "	<problem idref=\"" << problem << '.' << dimensions << "\"/>" << endl;
	cout << "	<measurement idref=\"fitness\"/>" << endl;
	cout << "	<output format=\"TXT\" file=\"" << algorithm << '.' << populationSize << '.' << iterations << '.' << problem << '.' << dimensions << "\"/>" << endl;
	cout << "</simulation>" << endl;
	cout << endl;
}

int main () {
	int algorithmsSize = 4;
	string algorithms [] = {"dif", "evo", "gen", "pso"};

	int populationSizesSize = 6;
	int populationSizes [] = {5, 25, 100, 500, 1000, 3000};

	int iterationValuesSize = 3;
	int iterationValues [] = {2000, 5000, 10000};

	int problemsSize = 6;
	string problems [] = {"schwefel", "elliptic", "rastrigin",  "ackley", "griewank", "weierstrass"};

	int dimensionValuesSize = 3;
	int dimensionValues [] = {5, 50, 500};

	for (int i = algorithmsSize; i > 0; i--) {
		for (int j = populationSizesSize; j > 0; j--) {
			for (int k = iterationValuesSize; k > 0; k--) {
				for (int l = problemsSize; l > 0; l--) {
					for (int m = dimensionValuesSize; m > 0; m--) {
						generateSimulationXML (
							algorithms[i-1],
							populationSizes[j-1],
							iterationValues[k-1],
							problems[l-1],
							dimensionValues[m-1]);
					}
				}
			}
		}
	}

	return 0;
}
