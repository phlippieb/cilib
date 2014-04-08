package simgen;

import java.util.ArrayList;
import java.io.BufferedWriter;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.IOException;
import simgen.function.*;
import simgen.algorithm.*;

public class SimGen {






	//
	//
	// This is the part you should edit.
	//
	//
	public static Simulation getSimulation() {
		Simulation simulation = new Simulation();

		// Samples:
		simulation.setNumberOfSamples(30);

		// Algorithms:
		simulation.addAlgorithm(new Gbest());

		// Populations:
		simulation.addPopulation(20);

		// Functions:
		simulation.addFunction(new Ackley());

		// Dimensions:
		simulation.addDimension(5);
		simulation.addDimension(10);

		return simulation;
	}
	//
	//
	//
	//
	//


	//
	//
	// TODO: read simulation data from file
	//
	//





	////////////////////////////////////////////////////////////
	// Nothing below here should probably be edited
	////////////////////////////////////////////////////////////

	/**
	 *	Usage:
	 *  	SimGen [filename]
	 */
	public static void main (String [] args) {

		//
		//
		// 0: Get simulation
		//
		System.out.println("Getting simulation details...");
		Simulation simulation = getSimulation();
		//simulation = getSimulation();
		simulation = getSimulationFromFiles("algorithms.txt", "populations.txt", "functions.txt", "dimensions.txt");


		//
		//
		//	1: Open output file and writer
		//

		System.out.println("Opening file...");
		//TODO: test args logic
		String filename;
		if (args.length < 1)
			filename = "sims.xml";
		else
			filename = args[0];

		File outf = null;
		try {
			outf = new File(filename);
			if (!outf.exists())
				outf.createNewFile();
		} catch (IOException ioe) {
			System.out.println("\n[!] Error creating new file!\n");
			ioe.printStackTrace();
			System.out.println();
			return;
		}

		BufferedWriter writer;
		try {
			writer = new BufferedWriter(new FileWriter(outf.getAbsoluteFile()));
		} catch (IOException ioe) {
			System.out.println("\n[!] Error creating buffered file writer!\n");
			ioe.printStackTrace();
			System.out.println();
			return;	
		}

		//
		//
		// 2: Create document preamble
		//

		System.out.println("Writing start of document...");
		try {
			writeDocumentPreamble(writer);
		} catch (IOException ioe) {
			System.out.println("\n[!] Error writing to file!\n");
			ioe.printStackTrace();
			System.out.println();
			return;		
		}

		//
		//
		// 3: Create algorithm xml
		//
		System.out.println("Writing algorithms...");
		try {
			writeAlgorithmXML(writer, simulation);
		} catch (IOException ioe) {
			System.out.println("\n[!] Error writing to file!\n");
			ioe.printStackTrace();
			System.out.println();
			return;		
		}

		System.out.println("Writing functions...");
		//
		//
		// 4: Create problem (function) xml
		//
		try {
			writeFunctionXML(writer, simulation);
		} catch (IOException ioe) {
			System.out.println("\n[!] Error writing to file!\n");
			ioe.printStackTrace();
			System.out.println();
			return;		
		}

		//
		//
		// 5: Create measurements xml
		//
		System.out.println("Writing measurements...");
		try {
			writeMeasurementsXML(writer);
		} catch (IOException ioe) {
			System.out.println("\n[!] Error writing to file!\n");
			ioe.printStackTrace();
			System.out.println();
			return;		
		}

		//
		//
		// 6: Create simulations xml
		//
		System.out.println("Writing simulations...");
		try {
			writeSimulationsXML(writer, simulation);
		} catch (IOException ioe) {
			System.out.println("\n[!] Error writing to file!\n");
			ioe.printStackTrace();
			System.out.println();
			return;		
		}

		//
		//
		// 7: Create document postamble (whatever)
		//
		System.out.println("Writing end of document...");
		try {
			writeDocumentPostamble(writer);
		} catch (IOException ioe) {
			System.out.println("\n[!] Error writing to file!\n");
			ioe.printStackTrace();
			System.out.println();
			return;		
		}

		//
		//
		// 8: Close file
		//
		System.out.println("Closing file....");
		try {
			writer.close();
		} catch (IOException ioe) {
			System.out.println("\n[!] Error closing file!\n");
			ioe.printStackTrace();
			System.out.println();
			return;
		}

		System.out.println("Done.");
	}

	static void writeDocumentPreamble(BufferedWriter w) throws IOException {
		w.write("<?xml version=\"1.0\"?>\n");
		w.write("<!DOCTYPE simulator [\n");
		w.write("<!ATTLIST algorithm id ID #IMPLIED>\n");
		w.write("<!ATTLIST problem id ID #IMPLIED>\n");
		w.write("<!ATTLIST measurements id ID #IMPLIED>\n");
		w.write("]>\n");
		w.write("<simulator>\n\n");
	}

	static void writeAlgorithmXML(BufferedWriter w, Simulation s) throws IOException {
		w.write("<algorithms>\n");
		for (Algorithm a : s.algorithms) {
			for (Integer p : s.populations) {
				a.setPopulation(p);
				w.write(a.toString());
			}
		}
		w.write("</algorithms>\n\n");
	}

	static void writeFunctionXML(BufferedWriter w, Simulation s) throws IOException {
		w.write("<problems>\n");
		for (Function f : s.functions) {
			for (Integer d : s.dimensions) {
				Function fun = f.clone();
				fun.setDimensions(d);
				w.write(fun.toString());
			}
		}
		w.write("</problems>\n\n");
	}

	static void writeMeasurementsXML(BufferedWriter w) throws IOException {
		w.write("<measurements id=\"both\" class=\"simulator.MeasurementSuite\" resolution=\"10\">\n");
        w.write("<addMeasurement class=\"measurement.single.Fitness\"/>\n");
        w.write("<addMeasurement class=\"measurement.single.diversity.AverageDiversityAroundAllEntities\"/>\n");
    	w.write("</measurements>\n\n");
	}

	static void writeSimulationsXML(BufferedWriter w, Simulation s) throws IOException {
		w.write("<simulations>\n");
		Algorithm tmpA;
		Function tmpF;
		for (Algorithm a : s.algorithms) {
			for (Integer p : s.populations) {
				for (Function f : s.functions) {
					for (Integer d : s.dimensions) {
						a.setPopulation(p);
						f.setDimensions(d);
						w.write("\t<simulation samples=\"" + s.getNumberOfSamples() + "\">\n");
						w.write("\t\t<algorithm idref=\"" + a.getId() + "\"/>\n");
						w.write("\t\t<problem idref=\"" + f.getId() + "\"/>\n");
						w.write("\t\t<measurements idref=\"both\"/>\n");
						w.write("\t\t<output format=\"TXT\" file=\"data/");
						w.write(a.getId() + "." + f.getId() + ".txt\"/>\n");
						w.write("\t</simulation>\n\n");
					}
				}
			}
		}
		w.write("</simulations>\n\n");
	}

	static void writeDocumentPostamble(BufferedWriter w) throws IOException {
		w.write("</simulator>\n\n");
	}



	static Algorithm getAlgorithmFromName(String name) throws Exception {
		return (Algorithm)Class.forName(name).getConstructor().newInstance();
	}

	static Function getFunctionFromName(String name) throws Exception {
		return (Function)Class.forName(name).getConstructor().newInstance();
	}

	static Simulation getSimulationFromFiles(	String algorithmsFilesName,
										String populationsFileName,
										String functionsFileName,
										String dimensionsFileName) {
		Simulation simulator = new Simulation();

		BufferedReader reader;
		String line;

		// 1: Read algorithms
		try {
			reader = new BufferedReader(new FileReader(algorithmsFilesName));
			line = reader.readLine();
			while (line != null) {
				if (line.indexOf("#") != 0 && line.indexOf("//") != 0) {
					// process this line
					try {
						simulator.addAlgorithm(getAlgorithmFromName("simgen.algorithm."+line));
					} catch (Exception e) {
						System.out.println("[!] Could not add algorithm from string name!");
						System.out.println("[!] Name: " + line);
						System.out.println("[!] You should probably abort now!");
						try {
							Thread.sleep(1500);
						} catch (InterruptedException ie) {}
					}
				}
				System.out.println("Added "+line);
				line = reader.readLine();
			}
		}catch (IOException ioe) {
			System.out.println("[!] Error reading file!");
			System.out.println("[!] File name: " + algorithmsFilesName);
			System.out.println("[!] You should probably abort now!");
			try {
				Thread.sleep(1500);
			} catch (InterruptedException ie) {}
			return null;
		}

		// 2: Read population sizes
		try {
			reader = new BufferedReader(new FileReader(populationsFileName));
			line = reader.readLine();
			while (line != null) {
				if (line.indexOf("#") != 0 && line.indexOf("//") != 0) {
					// process this line
					try {
						simulator.addPopulation(Integer.parseInt(line));
					} catch (Exception e) {
						System.out.println("[!] Could not parse population size from string!");
						System.out.println("[!] Size: " + line);
						System.out.println("[!] You should probably abort now!");
						try {
							Thread.sleep(1500);
						} catch (InterruptedException ie) {}
					}
				}
				System.out.println("Added "+line);
				line = reader.readLine();
			}
		} catch (IOException ioe) {
			System.out.println("[!] Error reading file!");
			System.out.println("[!] File name: " + populationsFileName);
			System.out.println("[!] You should probably abort now!");
			try {
				Thread.sleep(1500);
			} catch (InterruptedException ie) {}
			return null;
		}

		// 3: Read functions
		try {
			reader = new BufferedReader(new FileReader(functionsFileName));
			line = reader.readLine();
			while (line != null) {
				if (line.indexOf("#") != 0 && line.indexOf("//") != 0) {
					// process this line
					try {
						simulator.addFunction(getFunctionFromName("simgen.function."+line));
					} catch (Exception e) {
						System.out.println("[!] Could not add function from string name!");
						System.out.println("[!] Name: " + line);
						System.out.println("[!] You should probably abort now!");
						try {
							Thread.sleep(1500);
						} catch (InterruptedException ie) {}
					}
				}
				System.out.println("Added "+line);
				line = reader.readLine();
			}
		} catch (IOException ioe) {
			System.out.println("[!] Error reading file!");
			System.out.println("[!] File name: " + functionsFileName);
			System.out.println("[!] You should probably abort now!");
			try {
				Thread.sleep(1500);
			} catch (InterruptedException ie) {}
			return null;
		}

		// 4: Read dimensions
		try {
			reader = new BufferedReader(new FileReader(dimensionsFileName));
			line = reader.readLine();
			while (line != null) {
				if (line.indexOf("#") != 0 && line.indexOf("//") != 0) {
					// process this line
					try {
						simulator.addDimension(Integer.parseInt(line));
					} catch (Exception e) {
						System.out.println("[!] Could not parse dimensions from string!");
						System.out.println("[!] Dimensions: " + line);
						System.out.println("[!] You should probably abort now!");
						try {
							Thread.sleep(1500);
						} catch (InterruptedException ie) {}
					}
				}
				System.out.println("Added "+line);
				line = reader.readLine();
			}
		} catch (IOException ioe) {
			System.out.println("[!] Error reading file!");
			System.out.println("[!] File name: " + dimensionsFileName);
			System.out.println("[!] You should probably abort now!");
			try {
				Thread.sleep(1500);
			} catch (InterruptedException ie) {}
			return null;
		}

		simulator.setNumberOfSamples(30);
		return simulator;
	}

}