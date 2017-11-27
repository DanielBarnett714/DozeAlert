package me.dbarnett.dozealert.dt.core;

/**
 * A YesNoDomain consists of the following two values:
 * "Yes" and "No".
 */
public class YesNoDomain extends Domain {
	
	public static final String NO = "false";
	public static final String YES = "true";
	
	public YesNoDomain() {
		super(NO, YES);
	}

}
