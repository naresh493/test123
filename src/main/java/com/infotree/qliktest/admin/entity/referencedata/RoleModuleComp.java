package com.infotree.qliktest.admin.entity.referencedata;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.validation.constraints.NotNull;

public class RoleModuleComp implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;



	@Column(name="role_id", nullable=false)
	@Basic(fetch= FetchType.EAGER)
	private Integer roleId;
	
	

	@Column(name = "module_id", nullable = false)
	@Basic(fetch = FetchType.EAGER)
	@NotNull 
	private Integer moduleId;



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
