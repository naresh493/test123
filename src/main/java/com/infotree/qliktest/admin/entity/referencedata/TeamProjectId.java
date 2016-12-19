/**
 * @author koteswara.g
 */
package com.infotree.qliktest.admin.entity.referencedata;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.FetchType;

public class TeamProjectId implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	@Column(name = "team_id", length = 50, nullable = false)
	@Basic(fetch = FetchType.EAGER)
	Integer teamId; 
	
	@Column(name = "project_id", length = 50, nullable = false)
	@Basic(fetch = FetchType.EAGER)
	Integer projectId;

	/**
	 * @return the teamId
	 */
	public Integer getTeamId() {
		return teamId;
	}

	/**
	 * @param teamId the teamId to set
	 */
	public void setTeamId(Integer teamId) {
		this.teamId = teamId;
	}

	/**
	 * @return the projectId
	 */
	public Integer getProjectId() {
		return projectId;
	}

	/**
	 * @param projectId the projectId to set
	 */
	public void setProjectId(Integer projectId) {
		this.projectId = projectId;
	}
	
}
