package com.infotree.qliktest.admin.helperpojo;

import java.util.Date;
import java.util.List;

public class RolePermissionsPojo {

	private List<Integer> permissions;
	private List<Integer> permissionIds;
	
	private Integer roleId;
	private String createdBy;
	private String modifiedBy;
	private Date createdDate;
	private Date modifiedDate;
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
	/**
	 * @return the id
	 */
	public Integer getRoleId() {
		return roleId;
	}
	/**
	 * @param id the id to set
	 */
	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
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
	 * @return the permissionIds
	 */
	public List<Integer> getPermissionIds() {
		return permissionIds;
	}
	/**
	 * @param permissionIds the permissionIds to set
	 */
	public void setPermissionIds(List<Integer> permissionIds) {
		this.permissionIds = permissionIds;
	}
}
