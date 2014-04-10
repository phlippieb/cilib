package simgen.function;

public class Alpine extends Function {
	public void setName() {
		this.name = "alpine";
	}

	public void setDomains() {
		this.domainMin = -10;
		this.domainMax =  10;
	}

	public void setDefinition() {
        this.definition = "<function class=\"functions.continuous.unconstrained.Alpine\" />";
	}
}