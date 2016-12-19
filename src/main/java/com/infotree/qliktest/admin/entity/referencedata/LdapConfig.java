/**
 * @author koteswara.g;
 */
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

@Entity
@Table(name="ldap_config")
public class LdapConfig implements BaseQTAdminEntity, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Column(name="id", nullable=false)
	@Basic(fetch= FetchType.EAGER)
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name="serverip",length=50,nullable=false)
	@Basic(fetch = FetchType.EAGER)
	@NotNull 
	private String serverip;
	
	@Column(name = "port", length = 100, nullable = false)
	@Basic(fetch = FetchType.EAGER)
	@NotNull 
	private Integer port;
	
	@Column(name = "base", length = 500, nullable = false)
	@Basic(fetch = FetchType.EAGER)
	@NotNull 
	private String base;
	
	@Column(name = "username", length = 100, nullable = false)
	@Basic(fetch = FetchType.EAGER)
	@NotNull 
	private String username;
	
	@Column(name = "password", length = 100, nullable = false)
	@Basic(fetch = FetchType.EAGER)
	@NotNull 
	private String password;
	
	@Column(name = "tenant_id", length = 100, nullable = false)
	@Basic(fetch = FetchType.EAGER)
	@NotNull
	private Integer tenantId;
	
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

	
	@Transient
	private String tenantName;
	
	@Transient
	private String createdName;
	
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
	 * @return the serverip
	 */
	public String getServerip() {
		return serverip;
	}

	/**
	 * @param serverip the serverip to set
	 */
	public void setServerip(String serverip) {
		this.serverip = serverip;
	}

	/**
	 * @return the port
	 */
	public Integer getPort() {
		return port;
	}

	/**
	 * @param port the port to set
	 */
	public void setPort(Integer port) {
		this.port = port;
	}

	/**
	 * @return the base
	 */
	public String getBase() {
		return base;
	}

	/**
	 * @param base the base to set
	 */
	public void setBase(String base) {
		this.base = base;
	}

	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * @param username the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
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
	 * @return the tenantId
	 */
	public Integer getTenantId() {
		return tenantId;
	}

	/**
	 * @param tenantId the tenantId to set
	 */
	public void setTenantId(Integer tenantId) {
		this.tenantId = tenantId;
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
	 * @return the tenantName
	 */
	public String getTenantName() {
		return tenantName;
	}

	/**
	 * @param tenantName the tenantName to set
	 */
	public void setTenantName(String tenantName) {
		this.tenantName = tenantName;
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
}
