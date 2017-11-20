package me.dbarnett.dozealert.dt.core;
/**
 * A Variable has a name and a Domain of possible values.
 */
public class Variable {
	
	protected String name;
	protected Domain domain;
	
	public Variable(String name, Domain domain) {
		this.name = name;
		this.domain = domain;
	}
	
	public String toString() {
		return name;
	}

}
