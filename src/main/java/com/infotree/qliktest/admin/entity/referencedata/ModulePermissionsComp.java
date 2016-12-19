package com.infotree.qliktest.admin.entity.referencedata;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.validation.constraints.NotNull;

public class ModulePermissionsComp implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Column(name="module_id", nullable=false)
	@Basic(fetch= FetchType.EAGER)
	private Integer moduleId;
	
	

	@Column(name = "permission_id", nullable = false)
	@Basic(fetch = FetchType.EAGER)
	@NotNull 
	private Integer permissionId;



	/**
	 * @return the userId
	 */
	public Integer getModuleId() {
		return moduleId;
	}



	/**
	 * @param userId the userId to set
	 */
	public void setModuleId(Integer moduleId) {
		this.moduleId = moduleId;
	}



	/**
	 * @return the permissionId
	 */
	public Integer getPermissionId() {
		return permissionId;
	}



	/**
	 * @param permissionId the permissionId to set
	 */
	public void setPermissionId(Integer permissionId) {
		this.permissionId = permissionId;
	}
}
