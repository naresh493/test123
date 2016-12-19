package com.infotree.qliktest.admin.entity.referencedata;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.FetchType;

public class UserProjectComp implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Column(name = "user_id", nullable = false)
	@Basic(fetch = FetchType.EAGER)
	private Integer userId;
	
	@Column(name = "project_id",nullable = false)
	@Basic(fetch = FetchType.EAGER)
	private Integer projectId;

	/**
	 * @return the userId
	 */
	public Integer getUserId() {
		return userId;
	}

	/**
	 * @param userId the userId to set
	 */
	public void setUserId(Integer userId) {
		this.userId = userId;
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
