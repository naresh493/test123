package com.infotree.qliktest.admin.entity.configs;

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

import com.infotree.qliktest.admin.entity.BaseQTAdminEntity;

/**
 * Details of ObjectSpy Configuration.
 */
@Entity
@Table(name = "entpconfigurations")
public class EnterpriseSpy implements Serializable,BaseQTAdminEntity {

	private static final long serialVersionUID = 1;

	@Column(name = "id_entpc", nullable = false)
	@Basic(fetch = FetchType.EAGER)
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "name_entpc", length = 100, nullable = false)
	@Basic(fetch = FetchType.EAGER)
	private String configurationname;
	
	@Column(name = "time_entpc", length = 100, nullable = false)
	@Basic(fetch = FetchType.EAGER)
	private Integer elementwaittime;
	
	@Column(name = "type_entpc", length = 100, nullable = false)
	@Basic(fetch = FetchType.EAGER)
	private String typeEntpc;
	
	
	@Column(name = "proj_entpc", length = 100, nullable = false)
	@Basic(fetch = FetchType.EAGER)
	private Integer projectid;
	
	@Column(name = "path_entpc", nullable = false)
	@Basic(fetch = FetchType.EAGER)
	private String path;

	
	@Column(name = "addedby_entpc", length = 50, nullable = false)
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
	 * @return the configurationname
	 */
	public String getConfigurationname() {
		return configurationname;
	}

	/**
	 * @param configurationname the configurationname to set
	 */
	public void setConfigurationname(String configurationname) {
		this.configurationname = configurationname;
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
	 * @return the path
	 */
	public String getPath() {
		return path;
	}

	/**
	 * @param path the path to set
	 */
	public void setPath(String path) {
		this.path = path;
	}

	/**
	 * @return the elementwaittime
	 */
	public Integer getElementwaittime() {
		return elementwaittime;
	}

	/**
	 * @param elementwaittime the elementwaittime to set
	 */
	public void setElementwaittime(Integer elementwaittime) {
		this.elementwaittime = elementwaittime;
	}

	

	/**
	 * @return the projectid
	 */
	public Integer getProjectid() {
		return projectid;
	}

	/**
	 * @param projectid the projectid to set
	 */
	public void setProjectid(Integer projectid) {
		this.projectid = projectid;
	}

	/**
	 * @return the typeEntpc
	 */
	public String getTypeEntpc() {
		return typeEntpc;
	}

	/**
	 * @param typeEntpc the typeEntpc to set
	 */
	public void setTypeEntpc(String typeEntpc) {
		this.typeEntpc = typeEntpc;
	}

	
}
