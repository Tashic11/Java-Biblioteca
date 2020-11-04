package it.corso.accenture.utils;

public class ClassNotSupportedException extends Exception {

	private static final long serialVersionUID = 1L;
	
	public <T> ClassNotSupportedException(Class<T> c) {
		super("The class "+c.getName()+" passed isn't supported in this method");
	}

}
