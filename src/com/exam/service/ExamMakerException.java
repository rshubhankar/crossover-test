/**
 * 
 */
package com.exam.service;

/**
 * @author shubhankar_roy
 *
 */
public class ExamMakerException extends RuntimeException {

	/**
	 * Serial Version UID
	 */
	private static final long serialVersionUID = -2157123570293629184L;

	/**
	 * 
	 */
	public ExamMakerException() {
		
	}

	/**
	 * @param message
	 */
	public ExamMakerException(String message) {
		super(message);
	}

	/**
	 * @param cause
	 */
	public ExamMakerException(Throwable cause) {
		super(cause);
	}

	/**
	 * @param message
	 * @param cause
	 */
	public ExamMakerException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * @param message
	 * @param cause
	 * @param enableSuppression
	 * @param writableStackTrace
	 */
	public ExamMakerException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
