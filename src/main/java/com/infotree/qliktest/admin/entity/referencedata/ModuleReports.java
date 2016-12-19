package com.infotree.qliktest.admin.entity.referencedata;

import java.util.Date;

public class ModuleReports {
	
	private String name;
	private String description;
	private String permissionsCount;
	private String permissionsNames;
	private Integer permissionId;
	private Date createdDate;
	private Date modifiedDate;
	private Integer moduleId;
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}
	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	/**
	 * @return the permissionsCount
	 */
	public String getPermissionsCount() {
		return permissionsCount;
	}
	/**
	 * @param permissionsCount the permissionsCount to set
	 */
	public void setPermissionsCount(String permissionsCount) {
		this.permissionsCount = permissionsCount;
	}
	/**
	 * @return the permissionsNames
	 */
	public String getPermissionsNames() {
		return permissionsNames;
	}
	/**
	 * @param permissionsNames the permissionsNames to set
	 */
	public void setPermissionsNames(String permissionsNames) {
		this.permissionsNames = permissionsNames;
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
