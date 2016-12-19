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
import javax.persistence.Transient;

import org.springframework.format.annotation.DateTimeFormat;

import com.infotree.qliktest.admin.entity.BaseQTAdminEntity;

/**
 * Details of ObjectSpy Configuration.
 */
@Entity
@Table(name = "configurations")
public class ObjectSpy implements Serializable,BaseQTAdminEntity {

	private static final long serialVersionUID = 1;

	@Column(name = "id", nullable = false)
	@Basic(fetch = FetchType.EAGER)
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "configurationname", length = 100, nullable = false)
	@Basic(fetch = FetchType.EAGER)
	private String configurationname;

	@Column(name = "browser", nullable = false)
	@Basic(fetch = FetchType.EAGER)
	private String browser;

	@Column(name = "waitTime", length = 50, nullable = false)
	@Basic(fetch = FetchType.EAGER)
	private int elementwaittime;

	@Column(name = "url", nullable = false)
	@Basic(fetch = FetchType.EAGER)
	private String url;
	
	@Column(name = "portNumber", nullable = false)
	@Basic(fetch = FetchType.EAGER)
	private Integer portNumber;
	
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
	
	@DateTimeFormat(pattern = "yyyy-mm-dd HH:MM:SS")
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "modified_date")
	@Basic(fetch = FetchType.EAGER)
	private Date modifiedDate;
	
	@Column(name = "project_id", length = 50)
	@Basic(fetch = FetchType.EAGER)
	private Integer projectId;
	
	@Column(name = "version", length = 50)
	@Basic(fetch = FetchType.EAGER)
	private String version;
	
	@Transient
	private String createdName;
	
	@Transient
	private String projectName;
	
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
	 * @return the browser
	 */
	public String getBrowser() {
		return browser;
	}

	/**
	 * @param browser the browser to set
	 */
	public void setBrowser(String browser) {
		this.browser = browser;
	}

	/**
	 * @return the elementwaittime
	 */
	public int getElementwaittime() {
		return elementwaittime;
	}

	/**
	 * @param elementwaittime the elementwaittime to set
	 */
	public void setElementwaittime(int elementwaittime) {
		this.elementwaittime = elementwaittime;
	}

	/**
	 * @return the url
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * @param url the url to set
	 */
	public void setUrl(String url) {
		this.url = url;
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
	 * @return the portNumber
	 */
	public Integer getPortNumber() {
		return portNumber;
	}

	/**
	 * @param portNumber the portNumber to set
	 */
	public void setPortNumber(Integer portNumber) {
		this.portNumber = portNumber;
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
	 * @return the projectId
	 */
	public Integer getProjectId() {
		return projectId;
	}

	/**
	 * @param projectId the projectId to set
	 */
	public void setProjectId(Integer projectId) {
		this.projectId = projectId;
	}

	/**
	 * @return the projectName
	 */
	public String getProjectName() {
		return projectName;
	}

	/**
	 * @param projectName the projectName to set
	 */
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	/**
	 * @return the version
	 */
	public String getVersion() {
		return version;
	}

	/**
	 * @param version the version to set
	 */
	public void setVersion(String version) {
		this.version = version;
	}
	
	
}
