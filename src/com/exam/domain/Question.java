package com.exam.domain;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Represents single question in a test. 
 * 
 * @author shubhankar_roy
 *
 */
@Component
public class Question extends ValueObject {

	/**
	 * Serial Version UID
	 */
	private static final long serialVersionUID = -5853193964073919186L;

	/**
	 * Question description 
	 */
	private String description;
	
	/**
	 * list of answers
	 */
	private List<Answer> answers;

	/**
	 * Default Constructor
	 */
	public Question() {
		super();
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the answers
	 */
	public List<Answer> getAnswers() {
		return answers;
	}

	/**
	 * @param answers the answers to set
	 */
	@Autowired
	public void setAnswers(List<Answer> answers) {
		this.answers = answers;
	}
}
