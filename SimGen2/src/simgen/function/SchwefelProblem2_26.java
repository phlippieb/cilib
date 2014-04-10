package simgen.function;

public class SchwefelProblem2_26 extends Function {
	public void setName() {
		this.name = "schwefel2_26";
	}

	public void setDomains() {
		this.domainMin = -500;
		this.domainMax =  500;
	}

	public void setDefinition() {
        this.definition = "<function class=\"functions.continuous.SchwefelProblem2_26\"/>";
	}
}