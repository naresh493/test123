package com.infotree.qliktest.admin.entity.referencedata;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import com.infotree.qliktest.admin.entity.BaseQTAdminEntity;
import com.infotree.qliktest.admin.entity.accesscontrolled.AccessControlled;


/**
 * A role that can be used to grant access to functions within the system.
 */
@Entity
@Table(name = "role")
//@Audited("User Profile")
public class Role implements BaseQTAdminEntity,Serializable,AccessControlled {
	private static final long serialVersionUID = 1L;

	@Column(name = "id", nullable = false)
	@Basic(fetch = FetchType.EAGER)
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name = "name", length = 100, nullable = false)
	@Basic(fetch = FetchType.EAGER)
	private String name;
	
	@Column(name = "created_by", length = 50, nullable = false)
	@Basic(fetch = FetchType.EAGER)
	private String createdBy;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "created_date")
	@Basic(fetch = FetchType.EAGER)
	private Date createdDate;
	
	@Column(name = "modified_by", length = 50)
	@Basic(fetch = FetchType.EAGER)
	private String modifiedBy;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "modified_date")
	@Basic(fetch = FetchType.EAGER)
	private Date modifiedDate;
	
	@OneToMany(fetch = FetchType.EAGER)
	@JoinColumn(name="role_id")
	private Set<UserRole> userRoles;
	
	@Column(name="description",length=100,nullable=false)
	@Basic(fetch=FetchType.EAGER)
	private String description;
	
	@Column(name="roleandresponsibility",length=300)
	@Basic(fetch=FetchType.EAGER)
	private String roleAndResponsibility;
	
	@Transient
	private Integer permissionsCount;
	
	
	@Transient
	private String permissionsName;
	
	@Transient
	private Integer permissionId;
	
	@Transient
	private List<Integer> permissions;
	
	@Transient
	private List<Integer> permissionsIds;
	
	@Transient
	private String createdName;
	
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
	 * Constructor. Sets all properties to default (null) values.
	 */
	public Role() {
	}
	

	/**
	 * Sets the database primary key for this entity.
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * Gets the database primary key for this entity.
	 */
	public Integer getId() {
		return this.id;
	}

	/**
	 * Sets the name/description of this profile.
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Gets the name/description of this profile.
	 */
	public String getName() {
		return this.name;
	}

	/**
	 */
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	/**
	 */
	public String getCreatedBy() {
		return this.createdBy;
	}

	/**
	 */
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	/**
	 */
	public Date getCreatedDate() {
		return this.createdDate;
	}

	/**
	 */
	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	/**
	 */
	public String getModifiedBy() {
		return this.modifiedBy;
	}

	/**
	 */
	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	/**
	 */
	public Date getModifiedDate() {
		return this.modifiedDate;
	}

	/**
	 * Returns a string representation of this object.
	 */
	public String toString() {
		return this.name;
	}
	
	public Set<UserRole> getUserRoles() {
		return userRoles;
	}


	public void setUserRoles(Set<UserRole> userRoles) {
		this.userRoles = userRoles;
	}
	
	public static Role fromId(String id) {
		
		if(id == null || id.length() == 0) {
			return null;
		}
		String[] tokens = id.split(":");
		Role role = null;
		for (String string : tokens) {
			role = new Role();
			role.setId(Integer.valueOf(string));
		}
		role.setId(Integer.valueOf(tokens[0]));
		/*if(tokens.length > 1) {
			role.setBrand(EnumUtil.getEnumObject(Integer.valueOf(tokens[1]), Brand.class));
		}*/
		return role;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = (int) (prime * result + ((id == null) ? 0 : id.hashCode()));
		return result;
	}

	public boolean equals(Object obj) {
		if(obj == null){
			return false;
		}
		if (obj == this){
			return true;
		}
		if (!(obj instanceof Role)){
			return false;
		}
		if(this.getId().equals(((Role)obj).getId())){
			return true;
		}
		return false;
	}


	/**
	 * @return the permissionsCount
	 */
	public Integer getPermissionsCount() {
		return permissionsCount;
	}


	/**
	 * @param permissionsCount the permissionsCount to set
	 */
	public void setPermissionsCount(Integer permissionsCount) {
		this.permissionsCount = permissionsCount;
	}


	/**
	 * @return the permissionsName
	 */
	public String getPermissionsName() {
		return permissionsName;
	}


	/**
	 * @param permissionsName the permissionsName to set
	 */
	public void setPermissionsName(String permissionsName) {
		this.permissionsName = permissionsName;
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
	 * @return the permissionsIds
	 */
	public List<Integer> getPermissionsIds() {
		return permissionsIds;
	}


	/**
	 * @param permissionsIds the permissionsIds to set
	 */
	public void setPermissionsIds(List<Integer> permissionsIds) {
		this.permissionsIds = permissionsIds;
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
	 * @return the roleAndResponsibility
	 */
	public String getRoleAndResponsibility() {
		return roleAndResponsibility;
	}


	/**
	 * @param roleAndResponsibility the roleAndResponsibility to set
	 */
	public void setRoleAndResponsibility(String roleAndResponsibility) {
		this.roleAndResponsibility = roleAndResponsibility;
	}
	
}
