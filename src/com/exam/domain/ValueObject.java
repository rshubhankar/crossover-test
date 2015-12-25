package com.exam.domain;

import java.io.Serializable;

/**
 * Abstract Domain class
 * 
 * @author shubhankar_roy
 *
 */
public abstract class ValueObject implements Serializable{

	/**
	 * Serial Version UID
	 */
	private static final long serialVersionUID = 7501673076701567219L;

	private int id;

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ValueObject other = (ValueObject) obj;
		if (id != other.id)
			return false;
		return true;
	}
}
