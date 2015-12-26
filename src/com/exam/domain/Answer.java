/**
 * 
 */
package com.exam.domain;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * Represents single answer in a question. 
 * 
 * @author shubhankar_roy
 *
 */
@Component
@Scope(value=ConfigurableBeanFactory.SCOPE_PROTOTYPE)
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
	private Question question;
	
	/**
	 * Default Constructor
	 */
	public Answer() {
		super();
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((detail == null) ? 0 : detail.hashCode());
		result = prime * result + ((question == null) ? 0 : question.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		return super.equals(obj);
	}

	@Override
	public String toString() {
		return "Answer [getId()=" + getId() + "]";
	}
	
}
