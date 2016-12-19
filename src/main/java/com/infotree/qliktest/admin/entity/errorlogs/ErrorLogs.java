package com.infotree.qliktest.admin.entity.errorlogs;

import java.io.Serializable;

import org.springframework.data.mongodb.core.mapping.Field;


public abstract class ErrorLogs  implements Serializable{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -323382550439436470L;

	@Field
	private String level;
	
	@Field
	private String message;
	
	@Field
	private String method;
	
	@Field
	private String lineNumber;
	
	@Field
	private java.util.Date timestamp;
	
	@Field
	private ErrorLogHost host;
	
	@Field
	private LoggerNameClass loggerName;
	
	/**
	 * @return the projectType
	 */
	public String getProjectType() {
		return ProjectType;
	}

	/**
	 * @param projectType the projectType to set
	 */
	public void setProjectType(String projectType) {
		ProjectType = projectType;
	}

	private String ProjectType;
	//private QualifiedClassName Class;
	
	
	/**
	 * @return the loggerName
	 */
	public LoggerNameClass getLoggerName() {
		return loggerName;
	}

	/**
	 * @param loggerName the loggerName to set
	 */
	public void setLoggerName(LoggerNameClass loggerName) {
		this.loggerName = loggerName;
	}

	/**
	 * @return the host
	 */
	public ErrorLogHost getHost() {
		return host;
	}

	/**
	 * @param host the host to set
	 */
	public void setHost(ErrorLogHost host) {
		this.host = host;
	}

	
	/**
	 * @return the level
	 */
	public String getLevel() {
		return level;
	}

	/**
	 * @param level the level to set
	 */
	public void setLevel(String level) {
		this.level = level;
	}

	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * @param message the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * @return the method
	 */
	public String getMethod() {
		return method;
	}

	/**
	 * @param method the method to set
	 */
	public void setMethod(String method) {
		this.method = method;
	}

	
	/**
	 * @return the lineNumber
	 */
	public String getLineNumber() {
		return lineNumber;
	}

	/**
	 * @param lineNumber the lineNumber to set
	 */
	public void setLineNumber(String lineNumber) {
		this.lineNumber = lineNumber;
	}

	/**
	 * @return the timestamp
	 */
	public java.util.Date getTimestamp() {
		return timestamp;
	}

	/**
	 * @param timestamp the timestamp to set
	 */
	public void setTimestamp(java.util.Date timestamp) {
		this.timestamp = timestamp;
	}

	
	
}
