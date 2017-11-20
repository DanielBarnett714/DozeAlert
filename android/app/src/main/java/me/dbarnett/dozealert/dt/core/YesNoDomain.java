package me.dbarnett.dozealert.dt.core;

/**
 * A YesNoDomain consists of the following two values:
 * "Yes" and "No".
 */
public class YesNoDomain extends Domain {
	
	public static final String NO = "No";
	public static final String YES = "Yes";
	
	public YesNoDomain() {
		super(NO, YES);
	}

}
