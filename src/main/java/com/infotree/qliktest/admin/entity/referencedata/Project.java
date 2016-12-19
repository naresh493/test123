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

import com.infotree.qliktest.admin.entity.BaseQTAdminEntity;

/**
 * Details of Projects.
 */
@Entity
@Table(name="project")
public class Project implements BaseQTAdminEntity, Serializable {

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
	//@NotNull 
	private String name;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "start_date", nullable = false)
	@Basic(fetch = FetchType.EAGER)
	private Date startDate;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "end_date", nullable = false)
	@Basic(fetch = FetchType.EAGER)
	private Date endDate;
	
	
	@Column(name = "is_disabled", nullable = false)
	@Basic(fetch = FetchType.EAGER)
	private Byte disabled;
	
	@Column(name = "description", nullable = false)
	@Basic(fetch = FetchType.EAGER)
	private String description;
	
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
	
	
	
	@Transient
	private Integer[] modules;
	
	@Transient
	private String tenantName;
	
	@Transient
	private Integer tenantId;
	
	@Transient
	private int moduleCount;
	
	@Transient
	private String moduleNames;
	
	@Transient
	private List<Integer> moduleId;
	
	@Transient
	private String startingDate;
	
	@Transient
	private String endingDate;
	
	@Transient
	private String createdName;
	
	@Transient
	private String modId;
	

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
	 * @return the startDate
	 */
	public Date getStartDate() {
		return startDate;
	}

	/**
	 * @param startDate the startDate to set
	 */
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	/**
	 * @return the endDate
	 */
	public Date getEndDate() {
		return endDate;
	}

	/**
	 * @param endDate the endDate to set
	 */
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	
	public static Project fromId(String id) {
		if(id == null || id.length() == 0) {
			return null;
		}
		String[] tokens = id.split(":");
		Project project = null;
		for (String string : tokens) {
			project = new Project();
			project.setId(Integer.valueOf(string));
		}
		project.setId(Integer.valueOf(tokens[0]));
		/*if(tokens.length > 1) {
			role.setBrand(EnumUtil.getEnumObject(Integer.valueOf(tokens[1]), Brand.class));
		}*/
		return project;
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
	 * @return the modules
	 */
	public Integer[] getModules() {
		return modules;
	}

	/**
	 * @param modules the modules to set
	 */
	public void setModules(Integer[] modules) {
		this.modules = modules;
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
	 * @return the moduleCount
	 */
	public int getModuleCount() {
		return moduleCount;
	}



	/**
	 * @param moduleCount the moduleCount to set
	 */
	public void setModuleCount(int moduleCount) {
		this.moduleCount = moduleCount;
	}



	/**
	 * @return the moduleNames
	 */
	public String getModuleNames() {
		return moduleNames;
	}



	/**
	 * @param moduleNames the moduleNames to set
	 */
	public void setModuleNames(String moduleNames) {
		this.moduleNames = moduleNames;
	}



	


	/**
	 * @return the startingDate
	 */
	public String getStartingDate() {
		return startingDate;
	}



	/**
	 * @param startingDate the startingDate to set
	 */
	public void setStartingDate(String startingDate) {
		this.startingDate = startingDate;
	}



	/**
	 * @return the endingDate
	 */
	public String getEndingDate() {
		return endingDate;
	}



	/**
	 * @param endingDate the endingDate to set
	 */
	public void setEndingDate(String endingDate) {
		this.endingDate = endingDate;
	}



	/**
	 * @return the moduleId
	 */
	public List<Integer> getModuleId() {
		return moduleId;
	}



	/**
	 * @param moduleId the moduleId to set
	 */
	public void setModuleId(List<Integer> moduleId) {
		this.moduleId = moduleId;
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
	 * @return the modId
	 */
	public String getModId() {
		return modId;
	}



	/**
	 * @param modId the modId to set
	 */
	public void setModId(String modId) {
		this.modId = modId;
	}
}
