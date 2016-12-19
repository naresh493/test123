package com.infotree.qliktest.admin.entity.referencedata;

import java.io.Serializable;
import java.util.Date;

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
 * Details of Permissions
 */
@Entity
@Table(name="permissions")
public class Permissions implements BaseQTAdminEntity, Serializable {

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
	
	@Column(name = "aliasname", length = 100, nullable = false)
	@Basic(fetch = FetchType.EAGER)
	@NotNull 
	private String aliasName;
	
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
	
	@Column(name="is_disabled",length=50)
	@Basic(fetch = FetchType.EAGER)
	private Byte disabled;
	
	@Transient
	private String createdName;
	
	@Transient
	private String disableStatus;
	
	@Transient
	private String modifiedName;
	
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
	 * @return the aliasName
	 */
	public String getAliasName() {
		return aliasName;
	}

	/**
	 * @param aliasName the aliasName to set
	 */
	public void setAliasName(String aliasName) {
		this.aliasName = aliasName;
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
	 */
	public void setDisabled(Byte disabled) {
		this.disabled = disabled;
	}

	public void setDisabled(boolean disabled) {
		this.disabled = (byte)(disabled ? 1 : 0);
	}

	/**
	 */
	public Byte getDisabled() {
		return this.disabled;
	}
	
	public boolean isDisabled() {
		return (disabled != null && disabled > 0);
	}

	/**
	 * @return the disableStatus
	 */
	public String getDisableStatus() {
		return disableStatus;
	}

	/**
	 * @param disableStatus the disableStatus to set
	 */
	public void setDisableStatus(String disableStatus) {
		this.disableStatus = disableStatus;
	}

	/**
	 * @return the modifiedName
	 */
	public String getModifiedName() {
		return modifiedName;
	}

	/**
	 * @param modifiedName the modifiedName to set
	 */
	public void setModifiedName(String modifiedName) {
		this.modifiedName = modifiedName;
	}

	

}
