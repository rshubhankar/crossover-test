/**
 * 
 */
package com.exam.domain;

import org.springframework.stereotype.Component;

/**
 * Represents single answer in a question. 
 * 
 * @author shubhankar_roy
 *
 */
@Component
public class Answer extends ValueObject {

	/**
	 * Serial Version UID
	 */
	private static final long serialVersionUID = -139262283102508741L;

	/**
	 * Answer details
	 */
	private String detail;

	/**
	 * question
	 */
	private final Question question;
	
	/**
	 * Default Constructor
	 */
	public Answer(Question question) {
		super();
		this.question = question;
	}

	/**
	 * @return the detail
	 */
	public String getDetail() {
		return detail;
	}

	/**
	 * @param detail the detail to set
	 */
	public void setDetail(String detail) {
		this.detail = detail;
	}

	/**
	 * @return the question
	 */
	public Question getQuestion() {
		return question;
	}
}
