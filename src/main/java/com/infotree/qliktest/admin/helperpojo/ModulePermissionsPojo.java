package com.infotree.qliktest.admin.helperpojo;

import java.util.Date;
import java.util.List;

public class ModulePermissionsPojo {

	private Integer moduleId;
	
	private List<Integer> permissions;
	private List<Integer> permissionsId;
	private String createdBy;
	private Date createdDate;
	private Date modifiedDate;
	private String modifiedBy;
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
	 * @return the permissionsId
	 */
	public List<Integer> getPermissionsId() {
		return permissionsId;
	}
	/**
	 * @param permissionsId the permissionsId to set
	 */
	public void setPermissionsId(List<Integer> permissionsId) {
		this.permissionsId = permissionsId;
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
	 * @return the permissions
	 */
	public List<Integer> getPermissions() {
		return permissions;
	}
	/**
	 * @param permissions the permissions to set
	 */
	public void setPermissions(List<Integer> permissions) {
		this.permissions = permissions;
	}
}
