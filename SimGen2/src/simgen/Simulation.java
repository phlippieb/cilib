package simgen;

import java.util.ArrayList;
import simgen.function.*;
import simgen.algorithm.*;

public class Simulation {
	public ArrayList<Algorithm> algorithms;
	public ArrayList<Integer> populations;
	public ArrayList<Function> functions;
	public ArrayList<Integer> dimensions;
	public Integer numberOfSamples;
	public Integer numberOfIterations;
	Integer resolution;

	public Simulation() {
		algorithms = new ArrayList<Algorithm>();
		populations = new ArrayList<Integer>();
		functions = new ArrayList<Function>();
		dimensions = new ArrayList<Integer>();
	}

	public void addAlgorithm(Algorithm a) {
		algorithms.add(a);
	}

	public void addPopulation(Integer p) {
		populations.add(p);
	}

	public void addFunction(Function f) {
		functions.add(f);
	}

	public void addDimension(Integer d) {
		dimensions.add(d);
	}

	public void setNumberOfSamples(Integer i) {
		numberOfSamples = i;
	}

	public void setNumberOfIterations(Integer i) {
		numberOfIterations = i;
	}

	public void setResolution(Integer r) {
		resolution = r;
	}

	public ArrayList<Algorithm> getAlgorithms() {
		return algorithms;
	}

	public ArrayList<Integer> getPopulations() {
		return populations;
	}

	public ArrayList<Function> getFunctions() {
		return functions;
	}

	public ArrayList<Integer> getDimensions() {
		return dimensions;
	}

	public Integer getNumberOfSamples() {
		return numberOfSamples;
	}

	public Integer getNumberOfIterations() {
		return numberOfIterations;
	}

	public ArrayList<Integer> getResolution() {
		return resolution;
	}

	public int getTotalNumberOfSimulations() {
		return
			algorithms.size() *
			populations.size() *
			functions.size() *
			dimensions.size();
	}
}