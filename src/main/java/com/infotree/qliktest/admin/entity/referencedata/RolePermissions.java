package com.infotree.qliktest.admin.entity.referencedata;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.infotree.qliktest.admin.entity.BaseQTAdminEntity;

/**
 * Details of Permissions
 */
@Entity
@Table(name="role_permissions")
public class RolePermissions implements BaseQTAdminEntity, Serializable  {

	private static final long serialVersionUID = 1;
	
	@EmbeddedId
	private RolePermissionsComp userPermissionsComp;
	
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

	/*@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name="PERMISSIONS",
		joinColumns=@JoinColumn(name="USER_ID", referencedColumnName="ID"),
		inverseJoinColumns=@JoinColumn(name="PERMISSION_ID", referencedColumnName="ID")
	)
	private Set<UserPermissions> userPermissions;*/

	
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

	public Serializable getId() {
		return null;
	}

	/**
	 * @return the userPermissionsComp
	 */
	public RolePermissionsComp getUserPermissionsComp() {
		return userPermissionsComp;
	}

	/**
	 * @param userPermissionsComp the userPermissionsComp to set
	 */
	public void setUserPermissionsComp(RolePermissionsComp userPermissionsComp) {
		this.userPermissionsComp = userPermissionsComp;
	}

/*	*//**
	 * @return the userPermissions
	 *//*
	public Set<UserPermissions> getUserPermissions() {
		return userPermissions;
	}

	*//**
	 * @param userPermissions the userPermissions to set
	 *//*
	public void setUserPermissions(Set<UserPermissions> userPermissions) {
		this.userPermissions = userPermissions;
	}
*/	
}
