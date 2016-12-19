package com.infotree.qliktest.admin.entity.referencedata;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import com.infotree.qliktest.admin.entity.BaseQTAdminEntity;

/**
 * Details of licenses granted to Tenants and Projects.
 */
@Entity
@Table(name="module")
public class Module implements BaseQTAdminEntity, Serializable {

	/**
	 * generated serialVersionUID
	 */
	private static final long serialVersionUID = 1;
	
	@Column(name="id", nullable=false)
	@Basic(fetch= FetchType.EAGER)
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name = "name", length = 100, nullable = false)
	@Basic(fetch = FetchType.EAGER)
	@NotNull 
	private String name;
	
	
	
	@Column(name = "created_by", length = 50, nullable = false)
	@Basic(fetch = FetchType.EAGER)
	private String createdBy;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "created_date", nullable = false)
	@Basic(fetch = FetchType.EAGER)
	private Date createdDate;
	
	@Column(name = "modified_by", length = 50)
	@Basic(fetch = FetchType.EAGER)
	private String modifiedBy;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "modified_date")
	@Basic(fetch = FetchType.EAGER)
	private Date modifiedDate;

	@Column(name = "description", length = 50)
	@Basic(fetch = FetchType.EAGER)
	private String description;
	
	
	@Transient 
	private String permissionNames;
	
	@Transient
	private List<Integer> permissions;
	
	@Transient
	private List<Integer> permissionsId;
	
	@Transient
	private List<Integer> reportIds;
	
	@Transient
	private List<Integer> reports;
	
	@Transient
	private String noOfPermissions;
	
	@Transient
	private String createdName;
	
	@Transient
	private String constraintId;
	
	@Transient
	private String value;
	
	@Transient
	private String permissionId;
	
	@Transient
	private String roleId;
	
	@Transient
	private String reportId;
	
	@Transient
	private String conId;
	
	
	
	
	
	/**
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Integer id) {
		this.id = id;
	}

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
	 * @return the permissionNames
	 */
	public String getPermissionNames() {
		return permissionNames;
	}

	/**
	 * @param permissionNames the permissionNames to set
	 */
	public void setPermissionNames(String permissionNames) {
		this.permissionNames = permissionNames;
	}

	/**
	 * @return the noOfPermissions
	 */
	public String getNoOfPermissions() {
		return noOfPermissions;
	}

	/**
	 * @param noOfPermissions the noOfPermissions to set
	 */
	public void setNoOfPermissions(String noOfPermissions) {
		this.noOfPermissions = noOfPermissions;
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
	 * @return the reportIds
	 */
	public List<Integer> getReportIds() {
		return reportIds;
	}

	/**
	 * @param reportIds the reportIds to set
	 */
	public void setReportIds(List<Integer> reportIds) {
		this.reportIds = reportIds;
	}

	/**
	 * @return the reports
	 */
	public List<Integer> getReports() {
		return reports;
	}

	/**
	 * @param reports the reports to set
	 */
	public void setReports(List<Integer> reports) {
		this.reports = reports;
	}

	/**
	 * @return the createdName
	 */
	public String getCreatedName() {
		return createdName;
	}

	/**
	 * @param createdName the createdName to set
	 */
	public void setCreatedName(String createdName) {
		this.createdName = createdName;
	}

	/**
	 * @return the constraintId
	 */
	public String getConstraintId() {
		return constraintId;
	}

	/**
	 * @param constraintId the constraintId to set
	 */
	public void setConstraintId(String constraintId) {
		this.constraintId = constraintId;
	}

	/**
	 * @return the value
	 */
	public String getValue() {
		return value;
	}

	/**
	 * @param value the value to set
	 */
	public void setValue(String value) {
		this.value = value;
	}

	/**
	 * @return the permissionId
	 */
	public String getPermissionId() {
		return permissionId;
	}

	/**
	 * @param permissionId the permissionId to set
	 */
	public void setPermissionId(String permissionId) {
		this.permissionId = permissionId;
	}

	/**
	 * @return the roleId
	 */
	public String getRoleId() {
		return roleId;
	}

	/**
	 * @param roleId the roleId to set
	 */
	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	/**
	 * @return the reportId
	 */
	public String getReportId() {
		return reportId;
	}

	/**
	 * @param reportId the reportId to set
	 */
	public void setReportId(String reportId) {
		this.reportId = reportId;
	}

	/**
	 * @return the conId
	 */
	public String getConId() {
		return conId;
	}

	/**
	 * @param conId the conId to set
	 */
	public void setConId(String conId) {
		this.conId = conId;
	}
}
