package com.infotree.qliktest.admin.helperpojo;

import java.util.Date;
import java.util.List;

public class ModuleReportsPojo {
	private Integer moduleId;
	private List<Integer> reportIds;
	private Integer reportId;
	
	
	
	private List<Integer> reports;
	private Date createdDate;
	private String createdBy;
	private String modifiedBy;
	private Date modifiedDate;
	/**
	 * @return the reportId
	 */
	public Integer getReportId() {
		return reportId;
	}
	/**
	 * @param reportId the reportId to set
	 */
	public void setReportId(Integer reportId) {
		this.reportId = reportId;
	}
	
	/**
	 * @return the roleId
	 */
	public Integer getModuleId() {
		return moduleId;
	}
	/**
	 * @param roleId the roleId to set
	 */
	public void setModuleId(Integer moduleId) {
		this.moduleId = moduleId;
	}
	/**
	 * @return the reportIds
	 */
	public List<Integer> getReportIds() {
		return reportIds;
	}
	/**
	 * @param reportIds the reportIds to set
	 */
	public void setReportIds(List<Integer> reportIds) {
		this.reportIds = reportIds;
	}
	/**
	 * @return the createDate
	 */
	public Date getCreatedDate() {
		return createdDate;
	}
	/**
	 * @param createDate the createDate to set
	 */
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	/**
	 * @return the createdBy
	 */
	public String getCreatedBy() {
		return createdBy;
	}
	/**
	 * @param createdBy the createdBy to set
	 */
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	/**
	 * @return the modifiedBy
	 */
	public String getModifiedBy() {
		return modifiedBy;
	}
	/**
	 * @param modifiedBy the modifiedBy to set
	 */
	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}
	/**
	 * @return the modifiedDate
	 */
	public Date getModifiedDate() {
		return modifiedDate;
	}
	/**
	 * @param modifiedDate the modifiedDate to set
	 */
	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}
	/**
	 * @return the reports
	 */
	public List<Integer> getReports() {
		return reports;
	}
	/**
	 * @param reports the reports to set
	 */
	public void setReports(List<Integer> reports) {
		this.reports = reports;
	}
	
	
	

}
