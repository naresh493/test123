package com.infotree.qliktest.admin.entity.errorlogs;

import org.springframework.data.mongodb.core.mapping.Field;

public class LoggerNameClass {

	@Field
	private String className;

	/**
	 * @return the className
	 */
	public String getClassName() {
		return className;
	}

	/**
	 * @param className the className to set
	 */
	public void setClassName(String className) {
		this.className = className;
	}
}
