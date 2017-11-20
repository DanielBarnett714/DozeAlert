package me.dbarnett.dozealert.dt.core;

import java.util.HashMap;
import java.util.Map;

/**
 * An Example (or instance) is a value for each input Variable
 * and a single output value (or classification).
 * Something of a pain to create by hand but you would normally
 * read these out of a data file anyway.
 * You could make this faster by compiling the variables in a
 * Description down to indexes and storing the values for an Example
 * in an array rather than a Map. Give it a try!
 */
public class Example {
	
	protected Map<Variable,String> inputValues = new HashMap<Variable,String>();
	protected String outputValue;
	
	public Example() {
	}
	
	public void setInputValue(Variable variable, String value) {
		inputValues.put(variable, value);
	}

	public String getInputValue(Variable variable) {
		return inputValues.get(variable);
	}

	public void setOutputValue(String value) {
		outputValue = value;
	}
	
	public String getOutputValue() {
		return outputValue;
	}
	
	public String toString() {
		return inputValues.values().toString() + " -> " + outputValue;
	}

}
