package com.exam.domain;

import java.util.concurrent.TimeUnit;

/**
 * Timer class to manage exam timer.
 * 
 * @author shubhankar_roy
 *
 */
public class Timer {

	/**
	 * Value of time, depending on the {@link TimeUnit}
	 */
	private long duration;
	
	/**
	 * TimeUnit based on which timer will display the time.
	 */
	private TimeUnit timeUnit;

	/**
	 * @return the duration
	 */
	public long getDuration() {
		return duration;
	}

	/**
	 * @param duration the duration to set
	 */
	public void setDuration(long duration) {
		this.duration = duration;
	}

	/**
	 * @return the timeUnit
	 */
	public TimeUnit getTimeUnit() {
		return timeUnit;
	}

	/**
	 * @param timeUnit the timeUnit to set
	 */
	public void setTimeUnit(TimeUnit timeUnit) {
		this.timeUnit = timeUnit;
	}
	
	/**
	 * @return duration left in the format hh:mm:ss
	 */
	public String durationLeft() {
		return timeUnit.toHours(duration) + ":" + timeUnit.toMinutes(duration) + ":" + timeUnit.toSeconds(duration);
	}
	
	/**
	 * Reduces time duration.
	 * 
	 * @param duration
	 * @param timeUnit
	 */
	public void reduceTime(long duration, TimeUnit timeUnit) {
		long currentDuration = timeUnit.convert(duration, timeUnit);
		currentDuration -= duration;
		currentDuration = currentDuration < 0L ? 0L : currentDuration;
		this.duration = currentDuration;
	}
	
	/**
	 * Adds time duration.
	 * 
	 * @param duration
	 * @param timeUnit
	 */
	public void addTime(long duration, TimeUnit timeUnit) {
		long currentDuration = timeUnit.convert(duration, timeUnit);
		currentDuration += duration;
		this.duration = currentDuration;
	}
}
