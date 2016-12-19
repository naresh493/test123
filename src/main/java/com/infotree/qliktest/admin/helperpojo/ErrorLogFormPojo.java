package com.infotree.qliktest.admin.helperpojo;

import java.util.Date;

public class ErrorLogFormPojo {
	
	private String projectType;
	private Date startDate;
	private Date endDate;
	private String submit;
	private String startingDate;
	private String endingDate;
	private String level;
	/**
	 * @return the startingDate
	 */
	public String getStartingDate() {
		return startingDate;
	}
	/**
	 * @param startingDate the startingDate to set
	 */
	public void setStartingDate(String startingDate) {
		this.startingDate = startingDate;
	}
	/**
	 * @return the endingDate
	 */
	public String getEndingDate() {
		return endingDate;
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
	 * @param endingDate the endingDate to set
	 */
	public void setEndingDate(String endingDate) {
		this.endingDate = endingDate;
	}
	/**
	 * @return the projectType
	 */
	public String getProjectType() {
		return projectType;
	}
	/**
	 * @param projectType the projectType to set
	 */
	public void setProjectType(String projectType) {
		this.projectType = projectType;
	}
	/**
	 * @return the startDate
	 */
	public Date getStartDate() {
		return startDate;
	}
	/**
	 * @param startDate the startDate to set
	 */
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	/**
	 * @return the endDate
	 */
	public Date getEndDate() {
		return endDate;
	}
	/**
	 * @param endDate the endDate to set
	 */
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	/**
	 * @return the submit
	 */
	public String getSubmit() {
		return submit;
	}
	/**
	 * @param submit the submit to set
	 */
	public void setSubmit(String submit) {
		this.submit = submit;
	}

}
