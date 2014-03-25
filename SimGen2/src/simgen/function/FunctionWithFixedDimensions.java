package simgen.function;

public abstract class FunctionWithFixedDimensions extends Function {
	@Override
	public void setFixedDimensions() { this.fixedDimensions = true; }

	@Override
	public void setDimensions(int d) {
		setDimensions();
	}

	abstract void setDimensions();
}