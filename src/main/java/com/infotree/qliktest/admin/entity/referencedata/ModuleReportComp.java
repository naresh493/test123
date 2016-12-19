/**
 * @author koteswara.g
 */
package com.infotree.qliktest.admin.entity.referencedata;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.FetchType;
 
public class ModuleReportComp implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Column(name = "MODULE_ID",nullable = false)
	@Basic(fetch = FetchType.EAGER)
	private Integer moduleId;
	
	@Column(name = "REPORT_ID", nullable = false)
	@Basic(fetch = FetchType.EAGER)
	private Integer reportId;

	/**
	 * @return the projectId
	 */
	public Integer getReportId() {
		return reportId;
	}

	/**
	 * @param projectId the projectId to set
	 */
	public void setReportId(Integer reportId) {
		this.reportId = reportId;
	}

	/**
	 * @return the moduleId
	 */
	public Integer getModuleId() {
		return moduleId;
	}

	/**
	 * @param moduleId the moduleId to set
	 */
	public void setModuleId(Integer moduleId) {
		this.moduleId = moduleId;
	}

}
