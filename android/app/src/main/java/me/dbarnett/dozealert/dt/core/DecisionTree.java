package me.dbarnett.dozealert.dt.core;


import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * A DecisionTree is a recursive, dynamic tree structure.
 * There are two types of DecisionTree: either its label
 * is a Variable, in which case it is an internal node
 * and can have children in the order of its Variable's
 * values, or its label is a String, in which case it
 * is a leaf (result) node.
 */
public class DecisionTree {
	
	Variable variable;
	public List<DecisionTree> children;

	/**
	 * Construct and return a new non-leaf DecisionTree
	 * labeled with the given Variable.
	 */
	public DecisionTree(Variable variable) {
		this.variable = variable;
		this.children = new ArrayList<>();
	}

	String value;
	
	/**
	 * Construct and return a new leaf DecisionTree
	 * labeled with the given String value.
	 */
	public DecisionTree(String value) {
		this.value = value;
	}
	
	/**
	 * Dump this DecisionTree to stdout.
	 */
	public void dump() {
		dump(0);
	}
	
	protected void dump(int depth) {
		indent(depth);
		System.out.println(depth);
		if (variable != null) {
			System.out.println(variable);
			for (int i=0; i < variable.domain.size(); i++) {
				String value = variable.domain.get(i);
				DecisionTree child = children.get(i);
				indent(depth+2);
				System.out.println(value + ":");
				child.dump(depth+4);
			}
		} else {
			System.out.println(value);
		}
	}
	
	protected void indent(int n) {
		for (int i=0; i < n; i++) {
			System.out.print(' ');
		}
	}
	
	/**
	 * Return the value computed by this DecisionTree for the given Example.
	 */
	public String eval(Example example) {
		if (this.value != null) {
			// We are a leaf
			return this.value;
		} else {
			// Example has value vk for this variable
			String vk = example.getInputValue(this.variable);
			// Find child for that value in our children in domain order
			for (int i=0; i < variable.domain.size(); i++) {
				String value = variable.domain.get(i);
				if (value.equals(vk)) {
					return this.children.get(i).eval(example);
				}
			}
			System.out.println(children.toString());
			// Error
			return null;
		}
	}
	
	/**
	 * Run this DecisionTree on the given Examples and print results and
	 * summary statistics.
	 */
	public void test(Set<Example> examples) {
		int ntested = 0;
		int ncorrect = 0;
		for (Example e : examples) {
			String result = this.eval(e);
			//System.out.println(e + "\t" + result);
			ntested += 1;
			if (result.equals(e.getOutputValue())) {
				ncorrect += 1;
			}
		}
		double pct = (double)ncorrect / ntested * 100;
		System.out.format("correct: %d/%d (%.2f)%%", ncorrect, ntested, pct);
	}

	public double validate(Set<Example> examples) {
		System.out.println("validating");
		int ntested = 0;
		int ncorrect = 0;
		for (Example e : examples) {
			String result = this.eval(e);
			ntested += 1;
			if (result.equals(e.getOutputValue())) {
				ncorrect += 1;
			}
		}
		double pct = (double)ncorrect / ntested * 100;
		return pct;
	}



}
