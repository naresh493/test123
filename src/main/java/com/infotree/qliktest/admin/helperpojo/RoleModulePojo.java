package com.infotree.qliktest.admin.helperpojo;

import java.util.Date;
import java.util.List;

public class RoleModulePojo {
	
	private Integer moduleId;
	private List<Integer> roles;
	
	private List<Integer> roleIds;
 	private String createdBy;
	private Date createdDate;
	private String modifiedBy;
	private Date modifiedDate;
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
	 * @return the createdDate
	 */
	public Date getCreatedDate() {
		return createdDate;
	}
	/**
	 * @param createdDate the createdDate to set
	 */
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
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
	 * @return the roles
	 */
	public List<Integer> getRoles() {
		return roles;
	}
	/**
	 * @param roles the roles to set
	 */
	public void setRoles(List<Integer> roles) {
		this.roles = roles;
	}
	/**
	 * @return the roleIds
	 */
	public List<Integer> getRoleIds() {
		return roleIds;
	}
	/**
	 * @param roleIds the roleIds to set
	 */
	public void setRoleIds(List<Integer> roleIds) {
		this.roleIds = roleIds;
	}
	
}
