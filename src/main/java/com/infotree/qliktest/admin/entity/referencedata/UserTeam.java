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
import com.infotree.qliktest.admin.entity.accesscontrolled.AccessControlled;


/**
 * A role that can be used to grant access to functions within the system.
 */
@Entity
@Table(name = "user_team")
//@Audited("User Profile")
public class UserTeam implements BaseQTAdminEntity, Serializable,AccessControlled {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private UserTeamComp userTeamComp;
	
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
	
	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public String getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public Date getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	/**
	 * Constructor. Sets all properties to default (null) values.
	 */
	public UserTeam() {
	}

	

	public Serializable getId() {
		return null;
	}

	/**
	 * @return the userTeamComp
	 */
	public UserTeamComp getUserTeamComp() {
		return userTeamComp;
	}

	/**
	 * @param userTeamComp the userTeamComp to set
	 */
	public void setUserTeamComp(UserTeamComp userTeamComp) {
		this.userTeamComp = userTeamComp;
	}
}
