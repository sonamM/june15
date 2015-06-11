/*
 * 
 */
package com.etouch.taf.core.config;

import java.util.List;

// TODO: Auto-generated Javadoc
/**
 * The Class TestngConfig.
 */
public class TestngConfig extends TafConfig {
	
	/** The listener. */
	private String listener;
		
	private List<TestngClass> testngClass;
	
	/** The reporter. */
	private String reporter;
	
	/** Parallel Mode */
	private String parallelMode;
	

	/**
	 * Gets the listener.
	 *
	 * @return the listener
	 */
	public String getListener() {
		return listener;
	}
	
	/**
	 * Sets the listener.
	 *
	 * @param listener the new listener
	 */
	public void setListener(String listener) {
		this.listener = listener;
	}
	
	
	

	/**
	 * Gets the reporter.
	 *
	 * @return the reporter
	 */
	public String getReporter() {
		return reporter;
	}

	/**
	 * Sets the reporter.
	 *
	 * @param reporter the new reporter
	 */
	public void setReporter(String reporter) {
		this.reporter = reporter;
	}
	
	
	
	
	public List<TestngClass> getTestngClass() {
		return testngClass;
	}

	public void setTestngClass(List<TestngClass> testngClass) {
		this.testngClass = testngClass;
	}

	public String getParallelMode() {
		return parallelMode;
	}

	public void setParallelMode(String parallelMode) {
		this.parallelMode = parallelMode;
	}

	
}
