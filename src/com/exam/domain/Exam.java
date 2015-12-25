package com.exam.domain;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Examination domain object
 * 
 * @author shubhankar_roy
 *
 */
@Component
public class Exam extends ValueObject {
	/**
	 * Serial Version UID 
	 */
	private static final long serialVersionUID = 5598917607642417672L;
	
	/**
	 * Description 
	 */
	private String description;

	/**
	 * List of questions
	 */
	private List<Question> questions;
	
	/**
	 * timer for the exam
	 */
	private Timer timer;
	
	/**
	 * Default Constructor
	 */
	public Exam() {
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
	 * @return the questions
	 */
	public List<Question> getQuestions() {
		return questions;
	}

	/**
	 * @param questions the questions to set
	 */
	@Autowired
	public void setQuestions(List<Question> questions) {
		this.questions = questions;
	}

	/**
	 * @return the timer
	 */
	public Timer getTimer() {
		return timer;
	}

	/**
	 * @param timer the timer to set
	 */
	public void setTimer(Timer timer) {
		this.timer = timer;
	}
}
