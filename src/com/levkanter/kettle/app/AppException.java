package com.levkanter.kettle.app;

public class AppException 
	extends RuntimeException
{

	private static final long serialVersionUID = 5381651262171631904L;
		
	public AppException() {
		this("An exception caused by my app");
	}
	
	public AppException(String message) {
		this(message, true);
	}
	
	public AppException(String message, Exception cause) {
		this(message, cause, true);
	}
	
	public AppException(String message, boolean shouldPrint) {
		this(message, null, shouldPrint);
	}
	
	public AppException(String message, Exception cause, boolean shouldPrint) {
		super(message, cause);
		if (shouldPrint) System.err.println(message);
	}

}
