package com.infotree.qliktest.admin.entity.referencedata;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.validation.constraints.NotNull;

public class TeamRoleComp implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Column(name="team_id", nullable=false)
	@Basic(fetch= FetchType.EAGER)
	private Integer teamId;
	
	

	@Column(name = "role_id", nullable = false)
	@Basic(fetch = FetchType.EAGER)
	@NotNull 
	private Integer roleId;



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
	
}
