package com.exam.config;

import java.util.ArrayList;
import java.util.List;

/**
 * Factory class to create instances of Lists.
 * 
 * @author shubhankar_roy
 *
 */
public final class ListFactory {

	private static final ListFactory FACTORY_INSTANCE = new ListFactory();
	
	/**
	 * Default Private Constructor
	 */
	private ListFactory() {
		
	}
	
	/**
	 * @return instance of {@link ListFactory}
	 */
	public static ListFactory getInstance() {
		return FACTORY_INSTANCE;
	}
	
	/**
	 * Creates an instance of ArrayList<T>.
	 * 
	 * @param clazz
	 * @return ArrayList of type T
	 */
	public <T> List<T> getArrayList(Class<T> clazz) {
		return new ArrayList<T>();
	}
}
