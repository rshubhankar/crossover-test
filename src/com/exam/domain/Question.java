package com.exam.domain;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * Represents single question in a test. 
 * 
 * @author shubhankar_roy
 *
 */
@Component
@Scope(value=ConfigurableBeanFactory.SCOPE_PROTOTYPE)
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
	private final List<Answer> answers;

	/**
	 * Default Constructor
	 */
	public Question() {
		super();
		answers = new ArrayList<>();
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
	/*@Autowired
	public void setAnswers(List<Answer> answers) {
		this.answers = answers;
	}*/

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		return super.equals(obj);
	}
}
