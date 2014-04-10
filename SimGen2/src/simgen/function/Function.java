package simgen.function;

public abstract class Function implements Cloneable {

	String name;
	boolean fixedDimensions;
	int dimensions;
	double domainMin, domainMax;
	String definition;

	public Function() {
		setName();
		setFixedDimensions();
		dimensions = -1;
		setDomains();
		setDefinition();
	}

	public void setDimensions(int d) {
		this.dimensions = d;
	}

	@Override
	public Function clone() {
		// below doesn't work in Java, since Function is abstract.
		//Function f = new Function();

		// below solution obtained from http://stackoverflow.com/questions/18146319/java-cloning-abstract-objects
		// (though everyone on the thread agreed that it is a bit of a hack)
		try {
			Function f = (Function)getClass().getDeclaredConstructor().newInstance();
			f.setDimensions(this.dimensions);
			return f;
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Clone failed - you should probably not continue using this program.");
			try {
				Thread.sleep(1500);
			} catch (InterruptedException ie) {}
		}
		return null;
	}

	@Override
	public String toString() {
		String result = "";
		result += "\t<problem id=\"";
		result += this.name;
		result += ".";
		result += this.dimensions;
		result += "\" class=\"problem.FunctionOptimisationProblem\" ";
		result += "domain=\"R(";
		result += this.domainMin;
		result += ":";
		result += this.domainMax;
		result += ")^";
		result += this.dimensions;
		result += "\">\n\t\t";
		result += this.definition;
		result += "\n";
		result += "\t</problem>\n";
		return result;
	}

	public String getId() {
		return name + "." + dimensions;
	}

	abstract void setName();
	void setFixedDimensions() { fixedDimensions = false; }
	abstract void setDomains();
	abstract void setDefinition();

	public String getName() {
		return name;
	}

	public int getDimensions() {
		return dimensions;
	}

}