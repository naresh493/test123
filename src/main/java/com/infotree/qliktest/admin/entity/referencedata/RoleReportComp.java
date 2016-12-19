/**
 * @author koteswara.g
 */
package com.infotree.qliktest.admin.entity.referencedata;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.FetchType;

public class RoleReportComp implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Column(name = "role_id", nullable = false)
	@Basic(fetch = FetchType.EAGER)
	private Integer roleId;
	
	@Column(name = "report_id",nullable = false)
	@Basic(fetch = FetchType.EAGER)
	private Integer reportId;

	/**
	 * @return the roleId
	 */
	public Integer getRoleId() {
		return roleId;
	}

	/**
	 * @param roleId the roleId to set
	 */
	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

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
	
}
