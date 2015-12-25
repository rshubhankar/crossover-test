package com.exam.domain;

/**
 * Candidate who is appearing for the test.
 * 
 * @author shubhankar_roy
 *
 */
public class Candidate extends ValueObject {

	/**
	 * Serial Version UID
	 */
	private static final long serialVersionUID = -2170711074588154610L;

	/**
	 * username 
	 */
	private String username;
	
	/**
	 * password
	 */
	private String password;

	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * @param username the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Candidate [username=" + username + "]";
	}
}
