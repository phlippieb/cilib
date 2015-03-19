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
	// To edit an experiment, edit the files
	//  - algorithms.txt
	//  - populations.txt
	//	- functions.txt
	//  - dimensions.txt
	//
	//
	//
	//
	//
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
		// A: Get simulation details from file
		//
		System.out.println("Getting simulation details...");
		Simulation simulation = getSimulationFromFiles("algorithms.txt", "populations.txt", "functions.txt", "dimensions.txt", "samples.txt", "iterations.txt", "resolution.txt");

		//
		//
		// B: Write simulations to file
		//
		System.out.println("Writing simulations file...");
		//
		//
		//	1: Open output file and writer
		//

		System.out.println("- Opening file...");
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

		System.out.println("- Writing start of document...");
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
		System.out.println("- Writing algorithms...");
		try {
			writeAlgorithmXML(writer, simulation);
		} catch (IOException ioe) {
			System.out.println("\n[!] Error writing to file!\n");
			ioe.printStackTrace();
			System.out.println();
			return;		
		}

		System.out.println("- Writing functions...");
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
		System.out.println("- Writing measurements...");
		try {
			writeMeasurementsXML(writer, simulation);
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
		System.out.println("- Writing simulations...");
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
		System.out.println("- Writing end of document...");
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
		System.out.println("- Closing file....");
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
				a.setIterations(s.getNumberOfIterations());
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

	static void writeMeasurementsXML(BufferedWriter w, Simulation s) throws IOException {
		for (Integer r : s.resolutions) {
			w.write("<measurements id=\"meas-" + r + "\" " + 
				"class=\"simulator.MeasurementSuite\" " +
				"resolution=\"" + r + "\">\n");
        	//w.write("\t<addMeasurement " + 
        	//	"class=\"measurement.single.Fitness\"/>\n");
        	w.write("\t<addMeasurement " +
        		"class=\"measurement.single.diversity.AverageDiversityAroundAllEntities\"/>\n");
    		w.write("</measurements>\n\n");
        }
	}

	static void writeSimulationsXML(BufferedWriter w, Simulation s) throws IOException {
		w.write("<simulations>\n");
		int simulationNumber = 1;
		for (Algorithm a : s.algorithms) {
			for (Integer p : s.populations) {
				for (Function f : s.functions) {
					for (Integer d : s.dimensions) {
						for (Integer r : s.resolutions) {
							w.write("\t<!-- simulation #" + simulationNumber++ + " -->\n");
							a.setPopulation(p);
							f.setDimensions(d);
							w.write("\t<simulation samples=\"" + s.getNumberOfSamples() + "\">\n");
							w.write("\t\t<algorithm idref=\"" + a.getId() + "\"/>\n");
							w.write("\t\t<problem idref=\"" + f.getId() + "\"/>\n");
							w.write("\t\t<measurements idref=\"meas-" + r + "\"/>\n");
							w.write("\t\t<output format=\"TXT\" file=\"data/");
							//w.write(a.getId() + "." + f.getId() + ".txt\"/>\n");
							w.write(a.getId() + "." + f.getId() + "." + r + ".txt\"/>\n");
							w.write("\t</simulation>\n\n");
						}
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
										String dimensionsFileName,
										String samplesFilesName,
										String iterationsFileName,
										String resolutionFileName) {
		Simulation simulator = new Simulation();

		BufferedReader reader;
		String line;

		// 1: Read algorithms
		System.out.println("- Getting algorithms...");
		try {
			reader = new BufferedReader(new FileReader(algorithmsFilesName));
			line = reader.readLine();
			while (line != null) {
				if (line.indexOf("#") != 0 && line.indexOf("//") != 0&& line.trim().length() > 0) {
					// process this line
					try {
						simulator.addAlgorithm(getAlgorithmFromName("simgen.algorithm."+line));
						System.out.println("  - Added "+line);
					} catch (Exception e) {
						System.out.println("[!] Could not add algorithm from string name!");
						System.out.println("[!] Name: " + line);
						System.out.println("[!] You should probably abort now!");
						try {
							Thread.sleep(1500);
						} catch (InterruptedException ie) {}
					}
				}
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
		System.out.println("- Getting population sizes...");
		try {
			reader = new BufferedReader(new FileReader(populationsFileName));
			line = reader.readLine();
			while (line != null) {
				if (line.indexOf("#") != 0 && line.indexOf("//") != 0 && line.trim().length() > 0) {
					// process this line
					try {
						simulator.addPopulation(Integer.parseInt(line));
						System.out.println("  - Added "+line);
					} catch (Exception e) {
						System.out.println("[!] Could not parse population size from string!");
						System.out.println("[!] Size: " + line);
						System.out.println("[!] You should probably abort now!");
						try {
							Thread.sleep(1500);
						} catch (InterruptedException ie) {}
					}
				}
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
		System.out.println("- Getting functions...");
		try {
			reader = new BufferedReader(new FileReader(functionsFileName));
			line = reader.readLine();
			while (line != null) {
				if (line.indexOf("#") != 0 && line.indexOf("//") != 0 && line.trim().length() > 0) {
					// process this line
					try {
						simulator.addFunction(getFunctionFromName("simgen.function."+line));
						System.out.println("  - Added "+line);
					} catch (Exception e) {
						System.out.println("[!] Could not add function from string name!");
						System.out.println("[!] Name: " + line);
						System.out.println("[!] You should probably abort now!");
						try {
							Thread.sleep(1500);
						} catch (InterruptedException ie) {}
					}
				}
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
		System.out.println("- Getting function dimensions...");
		try {
			reader = new BufferedReader(new FileReader(dimensionsFileName));
			line = reader.readLine();
			while (line != null) {
				if (line.indexOf("#") != 0 && line.indexOf("//") != 0 && line.trim().length() > 0) {
					// process this line
					try {
						simulator.addDimension(Integer.parseInt(line));
						System.out.println("  - Added "+line);
					} catch (Exception e) {
						System.out.println("[!] Could not parse dimensions from string!");
						System.out.println("[!] Dimensions: " + line);
						System.out.println("[!] You should probably abort now!");
						try {
							Thread.sleep(1500);
						} catch (InterruptedException ie) {}
					}
				}
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

		// 5: Read samples
		System.out.println("- Getting samples...");
		try {
			reader = new BufferedReader(new FileReader(samplesFilesName));
			line = reader.readLine();
			while (line != null) {
				if (line.indexOf("#") != 0 && line.indexOf("//") != 0 && line.trim().length() > 0) {
					// process this line
					try {
						simulator.setNumberOfSamples(Integer.parseInt(line));
						System.out.println("  - Added "+line);
						break;
					} catch (Exception e) {
						System.out.println("[!] Could not parse samples from string!");
						System.out.println("[!] Samples: " + line);
						System.out.println("[!] You should probably abort now!");
						try {
							Thread.sleep(1500);
						} catch (InterruptedException ie) {}
					}
				}
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

		// 6: Read iterations
		System.out.println("- Getting iterations...");
		try {
			reader = new BufferedReader(new FileReader(iterationsFileName));
			line = reader.readLine();
			while (line != null) {
				if (line.indexOf("#") != 0 && line.indexOf("//") != 0 && line.trim().length() > 0) {
					// process this line
					try {
						simulator.setNumberOfIterations(Integer.parseInt(line));
						System.out.println("  - Added "+line);
						break;
					} catch (Exception e) {
						System.out.println("[!] Could not parse iterations from string!");
						System.out.println("[!] Iterations: " + line);
						System.out.println("[!] You should probably abort now!");
						try {
							Thread.sleep(1500);
						} catch (InterruptedException ie) {}
					}
				}
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

		// 6: Read resolutions
		System.out.println("- Getting resolutions...");
		try {
			reader = new BufferedReader(new FileReader(resolutionFileName));
			line = reader.readLine();
			while (line != null) {
				if (line.indexOf("#") != 0 && line.indexOf("//") != 0 && line.trim().length() > 0) {
					// process this line
					try {
						simulator.addResolution(Integer.parseInt(line));
						System.out.println("  - Added "+line);
					} catch (Exception e) {
						System.out.println("[!] Could not parse resolution from string!");
						System.out.println("[!] Resolution: " + line);
						System.out.println("[!] Exception: " + e.getMessage());
						System.out.println("[!] You should probably abort now!");
						try {
							Thread.sleep(1500);
						} catch (InterruptedException ie) {}
					}
				}
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

		return simulator;
	}
}