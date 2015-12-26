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
	private final TimeUnit timeUnit = TimeUnit.SECONDS;

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
	 * @return timeUnit
	 */
	public TimeUnit getTimeUnit() {
		return timeUnit;
	}

	/**
	 * @return duration left in the format hh:mm:ss
	 */
	public String getDurationLeft() {
		StringBuilder builder = new StringBuilder();
		long currentDuration = getDuration();
		long hours = currentDuration / 3600;
		currentDuration = currentDuration > 3600 ? currentDuration % 3600 : currentDuration;
		builder.append(hours).append(":");
		long minutes = currentDuration / 60;
		currentDuration = currentDuration > 60 ? currentDuration % 60 : currentDuration;
		builder.append(minutes).append(":").append(currentDuration);
		return builder.toString();
		//return timeUnit.toHours(duration) + ":" + timeUnit.toMinutes(duration) + ":" + timeUnit.toSeconds(duration);
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
