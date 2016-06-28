package org.mashup.entertainment;

public class MovieServiceException extends Exception {

	public MovieServiceException(String message, Throwable cause) {
		super(message, cause);
	}

	public MovieServiceException(String message) {
		super(message);
	}

}
